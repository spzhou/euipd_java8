/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.account;

import com.course.server.domain.*;
import com.course.server.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PayService {
    private static final Logger LOG = LoggerFactory.getLogger(PayService.class);
    @Resource
    private MallPayInfoMapper mallPayInfoMapper;


    /**
     * 插入支付信息记录
     * 新增商城支付信息到数据库
     * 
     * @param record 支付信息记录
     * @return 返回插入影响的记录数
     */
    public int insert(MallPayInfo record) {
        return mallPayInfoMapper.insert(record);
    }

    /**
     * 根据支付流水号查询支付信息
     * 通过支付流水号查询对应的支付记录列表
     * 
     * @param paymentSerial 支付流水号
     * @return 返回支付信息列表，如果不存在则返回null
     */
    public List<MallPayInfo> selectByPaymentSerial(String paymentSerial) {
        MallPayInfoExample mallPayInfoExample = new MallPayInfoExample();
        MallPayInfoExample.Criteria criteria = mallPayInfoExample.createCriteria();
        criteria.andPaymentSerialEqualTo(paymentSerial);
        List<MallPayInfo> mallPayInfos = mallPayInfoMapper.selectByExample(mallPayInfoExample);
        if(mallPayInfos.size()>0){
            return mallPayInfos;
        }
        return null;
    }

    /**
     * 根据订单号查询支付信息
     * 通过订单号查询对应的支付记录
     * 
     * @param orderNo 订单号
     * @return 返回支付信息对象，如果不存在则返回null
     */
    public MallPayInfo selectByOrderNo(String orderNo) {
        MallPayInfoExample mallPayInfoExample = new MallPayInfoExample();
        MallPayInfoExample.Criteria criteria = mallPayInfoExample.createCriteria();
        criteria.andOrderNoEqualTo(orderNo);
        List<MallPayInfo> mallPayInfos = mallPayInfoMapper.selectByExample(mallPayInfoExample);
        if(mallPayInfos.size()>0){
            return mallPayInfos.get(0);
        }
        return null;
    }
}
