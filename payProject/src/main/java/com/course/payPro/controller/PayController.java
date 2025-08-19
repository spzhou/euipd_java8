/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.payPro.controller;

import com.course.payPro.service.PayProService;
import com.course.payPro.vo.PaySysResult;
import com.course.payPro.vo.RequestForm;
import com.course.payPro.vo.ResponseVO;
import com.course.server.domain.MallWxPayInfo;
import com.course.server.enums.ResponseEnum;
import com.lly835.bestpay.config.WxPayConfig;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by 廖师兄
 */
@RestController
@RequestMapping("/pay")
@Slf4j
public class PayController {

    @Autowired
    private PayProService payService;

    @Autowired
    private WxPayConfig wxPayConfig;

    /**
     * 创建支付订单
     * 根据支付类型创建对应的支付订单，支持微信支付和支付宝支付
     * 
     * @param requestForm 支付请求表单，包含订单号和支付金额
     * @return 返回支付结果，包含支付二维码URL等信息
     */
    @PostMapping("/create")
    @ResponseBody
    public ResponseVO create(@RequestBody RequestForm requestForm ) {

        BestPayTypeEnum bestPayTypeEnum = BestPayTypeEnum.WXPAY_NATIVE;
        if(requestForm.getPayType() == 2){
            bestPayTypeEnum = BestPayTypeEnum.WXPAY_NATIVE;
        }

        PayResponse response = payService.create(requestForm.getOrderNo(), requestForm.getAmount(), bestPayTypeEnum);

        //支付方式不同，渲染就不同, WXPAY_NATIVE使用codeUrl,  ALIPAY_PC使用body
        //Map<String, String> map = new HashMap<>();
        if (bestPayTypeEnum == BestPayTypeEnum.WXPAY_NATIVE) {
            PaySysResult paySysResult = new PaySysResult();
            paySysResult.setCodeUrl(response.getCodeUrl());
            paySysResult.setOrderNo(requestForm.getOrderNo());
            paySysResult.setReturnUrl(wxPayConfig.getReturnUrl());

            return ResponseVO.success(paySysResult);

        } else if (bestPayTypeEnum == BestPayTypeEnum.ALIPAY_PC) {
            PaySysResult paySysResult = new PaySysResult();
            paySysResult.setCodeUrl(response.getCodeUrl());
            paySysResult.setOrderNo(requestForm.getOrderNo());
            paySysResult.setReturnUrl(wxPayConfig.getReturnUrl());

            return ResponseVO.success(paySysResult);
        }
        throw new RuntimeException("暂不支持的支付类型");
    }

    /**
     * 取消支付订单
     * 根据订单号取消对应的支付订单
     * 
     * @param orderNo 订单号
     * @return 返回取消结果
     */
    @PostMapping("/cancel/{orderNo}")
    @ResponseBody
    public ResponseVO payCancel(@PathVariable String orderNo ) {

        if(payService.payCancel(orderNo)>0){
            return ResponseVO.success();
        }
        return ResponseVO.error(ResponseEnum.ERROR);
    }


    /**
     * 异步支付通知处理
     * 接收支付平台的异步通知并处理支付结果
     * 
     * @param notifyData 支付平台发送的通知数据
     * @return 返回处理结果字符串
     */
    @PostMapping("/notify")
    @ResponseBody
    public String asyncNotify(@RequestBody String notifyData) {
        return payService.asyncNotify(notifyData);
    }

    /**
     * 根据订单号查询支付信息
     * 查询指定订单的支付记录和状态信息
     * 
     * @param orderNo 订单号
     * @return 返回支付信息对象
     */
    @GetMapping("/queryByOrderId")
    @ResponseBody
    public MallWxPayInfo queryByOrderId(@RequestParam String orderNo) {
        log.info("查询支付记录...");
        return payService.queryByOrderId(orderNo);
    }
}
