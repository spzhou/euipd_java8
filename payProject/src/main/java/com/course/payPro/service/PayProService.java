/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.payPro.service;

import com.course.payPro.enums.PayPlatformEnum;
import com.course.server.domain.MallOrder;
import com.course.server.domain.MallPayInfo;
import com.course.server.domain.MallWxPayInfo;
import com.course.server.domain.MallWxPayInfoExample;
import com.course.server.enums.PayStatusEnum;
import com.course.server.enums.PayTypeEnum;
import com.course.server.mapper.MallWxPayInfoMapper;
import com.course.server.service.account.OrderService;
import com.course.server.service.account.PayInfoService;
import com.course.server.util.UuidUtil;
import com.lly835.bestpay.enums.BestPayPlatformEnum;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.enums.OrderStatusEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.BestPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 */
@Slf4j
@Service
public class PayProService {

    private final static String QUEUE_PAY_NOTIFY = "payNotify";

    @Resource
    private BestPayService bestPayService;
/*

    @Resource
    private AmqpTemplate amqpTemplate;
*/

    @Resource
    private PayInfoService payInfoService;

    @Resource
    private OrderService orderService;

    @Resource
    private MallWxPayInfoMapper payInfoMapper;

    /**
     * 创建/发起支付
     *
     * @param orderNo
     * @param amount
     */
    /**
     * 创建支付订单
     * 生成支付信息并写入数据库，然后发起支付请求
     * 
     * @param orderNo 订单号
     * @param amount 支付金额
     * @param bestPayTypeEnum 支付类型枚举
     * @return 返回支付响应对象
     */
    public PayResponse create(String orderNo, BigDecimal amount, BestPayTypeEnum bestPayTypeEnum) {
        //写入数据库
        MallWxPayInfo payInfo = new MallWxPayInfo();/*(orderNo,
                PayPlatformEnum.getByBestPayTypeEnum(bestPayTypeEnum).getCode(),
                OrderStatusEnum.NOTPAY.name(),
                amount);*/
        payInfo.setOrderNo(orderNo);
        payInfo.setPayAmount(amount);
        payInfo.setPayPlatform(PayPlatformEnum.getByBestPayTypeEnum(bestPayTypeEnum).getCode());
        payInfo.setPlatformStatus(OrderStatusEnum.NOTPAY.name());

        String uuid = UuidUtil.getShortUuid();
        payInfo.setId(uuid);
        payInfoMapper.insertSelective(payInfo);

        PayRequest request = new PayRequest();
        request.setOrderName("AIiSCI商城支付系统");
        request.setOrderId(orderNo);
        request.setOrderAmount(amount.doubleValue());
        request.setPayTypeEnum(bestPayTypeEnum);

        PayResponse response = bestPayService.pay(request);
        log.info("发起支付 response={}", response);

        return response;

    }

    /**
     * 取消支付订单
     * 根据订单号取消支付，删除未支付成功的订单记录
     * 
     * @param orderNo 订单号
     * @return 返回删除的记录数，0表示没有删除任何记录
     */
    public Integer payCancel(String orderNo) {
        MallWxPayInfoExample mallWxPayInfoExample = new MallWxPayInfoExample();
        MallWxPayInfoExample.Criteria criteria = mallWxPayInfoExample.createCriteria();
        criteria.andOrderNoEqualTo(orderNo);
        List<MallWxPayInfo> payInfos = payInfoMapper.selectByExample(mallWxPayInfoExample);
        if(payInfos.size()>0){
            MallWxPayInfo payInfo = payInfos.get(0);
            if(payInfo != null && !payInfo.getPlatformStatus().equals("SUCCESS")){
                return payInfoMapper.deleteByPrimaryKey(payInfo.getId());
            }
        }
        return 0;
    }




    /**
     * 异步通知处理
     *
     * @param notifyData
     */
    public String asyncNotify(String notifyData) {
        //1. 签名检验
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("异步通知 response={}", payResponse);

        //2. 金额校验（从数据库查订单）
        //比较严重（正常情况下是不会发生的）发出告警：钉钉、短信
        MallWxPayInfoExample mallWxPayInfoExample = new MallWxPayInfoExample();
        MallWxPayInfoExample.Criteria criteria = mallWxPayInfoExample.createCriteria();
        criteria.andOrderNoEqualTo(payResponse.getOrderId());
        List<MallWxPayInfo> payInfos = payInfoMapper.selectByExample(mallWxPayInfoExample);
        if(payInfos.size()>0){
            MallWxPayInfo payInfo = payInfos.get(0);
            if(payInfo == null){
                throw new RuntimeException("通过orderNo查询到的结果是null");
            }
            //如果订单支付状态不是"已支付"
            if (!payInfo.getPlatformStatus().equals(OrderStatusEnum.SUCCESS.name())) {
                //Double类型比较大小，精度。1.00  1.0
                if (payInfo.getPayAmount().compareTo(BigDecimal.valueOf(payResponse.getOrderAmount())) != 0) {
                    //告警
                    throw new RuntimeException("异步通知中的金额和数据库里的不一致，orderNo=" + payResponse.getOrderId());
                }

                //3. 修改订单支付状态
                payInfo.setPlatformStatus(OrderStatusEnum.SUCCESS.name());
                payInfo.setPlatformNumber(payResponse.getOutTradeNo());
                payInfoMapper.updateByPrimaryKeySelective(payInfo);
            }


            //pay发送MQ消息，mall接受MQ消息
//            amqpTemplate.convertAndSend(QUEUE_PAY_NOTIFY, new Gson().toJson(payInfo));

            //本地处理支付状态,不需要在采用MQ异步通知的方式了
            processPayStatus(payInfo);

            if (payResponse.getPayPlatformEnum() == BestPayPlatformEnum.WX) {
                //4. 告诉微信不要再通知了
                return "<xml>\n" +
                        "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                        "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                        "</xml>";
            } else if (payResponse.getPayPlatformEnum() == BestPayPlatformEnum.ALIPAY) {
                return "success";
            }

        }

        throw new RuntimeException("异步通知中错误的支付平台");
    }

    /**
     * 根据订单号查询支付信息
     * 通过订单号查询对应的支付记录信息
     * 
     * @param orderNo 订单号
     * @return 返回支付信息对象，如果不存在则返回null
     */
    public MallWxPayInfo queryByOrderId(String orderNo) {
        MallWxPayInfoExample mallWxPayInfoExample = new MallWxPayInfoExample();
        MallWxPayInfoExample.Criteria criteria = mallWxPayInfoExample.createCriteria();
        criteria.andOrderNoEqualTo(orderNo);
        List<MallWxPayInfo> payInfos = payInfoMapper.selectByExample(mallWxPayInfoExample);
        if(payInfos.size()>0) {
            MallWxPayInfo payInfo = payInfos.get(0);
            return payInfo;
        }
        return null;
    }

    /**
     * 处理支付状态
     * 根据支付信息更新订单状态和支付状态，包括订单表和支付信息表的更新
     * 
     * @param payInfo 支付信息对象
     */
    public void processPayStatus(MallWxPayInfo payInfo) {

        if (payInfo.getPlatformStatus().equals("SUCCESS")) {
            //修改订单里的状态
            List<MallPayInfo> mallPayInfoList = payInfoService.selectByPaymentSerial(payInfo.getOrderNo());
            for(MallPayInfo mallPayInfo : mallPayInfoList){
                // Integer payType;
                mallPayInfo.setPayType(payInfo.getPayPlatform());
                // BigDecimal payAmount;
                //TODO:需要做核对,等软件正式上线后,需要对支付的金额进行核验

                // String platformNumber;
                mallPayInfo.setPlatformNumber(payInfo.getPlatformNumber());
                mallPayInfo.setPayStatus(PayStatusEnum.PAY_COMPLETE.getPayStatus());
                payInfoService.updateByOrderNoSelective(mallPayInfo);

                //更新order表的支付状态.
                MallOrder orderDetail = orderService.getMallOrderByOrderNo(mallPayInfo.getOrderNo());
                //TODO 把数字修改为常量定义
                if(orderDetail.getPayStatus()!=PayStatusEnum.PAY_COMPLETE.getPayStatus()){
                    if(mallPayInfo.getPayType()== PayTypeEnum.ALI_PAY.getPayType()){
                        orderDetail.setPayType(1);
                    }else if (mallPayInfo.getPayType()==PayTypeEnum.WEIXIN_PAY.getPayType()){
                        orderDetail.setPayType(2);
                    }
                    if(mallPayInfo.getPayStatus()==2){
                        orderDetail.setPayStatus(2);
                        orderDetail.setOrderStatus(1);
                    }else if(mallPayInfo.getPayStatus()==PayStatusEnum.PAY_COMPLETE.getPayStatus()) {
                        orderDetail.setPayStatus(1);
                        orderDetail.setOrderStatus(1);
                    }
                    orderService.updateByPrimaryKey(orderDetail);
                }
            }
        }
    }
}
