/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.account;

import com.course.server.domain.MallPayInfo;
import com.course.server.domain.MallPayInfoExample;
import com.course.server.mapper.MallPayInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PayInfoService {

    @Resource
    private MallPayInfoMapper mallPayInfoMapper;

    public int deleteByPrimaryKey(String id) {
        return mallPayInfoMapper.deleteByPrimaryKey(id);
    }

    public int insert(MallPayInfo record) {
        return mallPayInfoMapper.insert(record);
    }

    public int insertSelective(MallPayInfo record) {
        return mallPayInfoMapper.insertSelective(record);
    }

    public MallPayInfo selectByPrimaryKey(String id) {
        return mallPayInfoMapper.selectByPrimaryKey(id);
    }

    public List<MallPayInfo> selectByOrderNo(String orderNo) {
        MallPayInfoExample mallPayInfoExample = new MallPayInfoExample();
        MallPayInfoExample.Criteria criteria = mallPayInfoExample.createCriteria();
        criteria.andOrderNoEqualTo(orderNo);
        return mallPayInfoMapper.selectByExample(mallPayInfoExample);
    }

    public List<MallPayInfo> selectByPaymentSerial(String paymentSerial) {
        MallPayInfoExample mallPayInfoExample = new MallPayInfoExample();
        MallPayInfoExample.Criteria criteria = mallPayInfoExample.createCriteria();
        criteria.andPaymentSerialEqualTo(paymentSerial);
        return mallPayInfoMapper.selectByExample(mallPayInfoExample);
    }

    public int updateByPrimaryKeySelective(MallPayInfo record) {
        return mallPayInfoMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByOrderNoSelective(MallPayInfo record) {
        MallPayInfoExample mallPayInfoExample = new MallPayInfoExample();
        MallPayInfoExample.Criteria criteria = mallPayInfoExample.createCriteria();
        criteria.andOrderNoEqualTo(record.getOrderNo());
        return mallPayInfoMapper.updateByExample(record,mallPayInfoExample);
    }

    public int updateByPrimaryKey(MallPayInfo record) {
        return mallPayInfoMapper.updateByPrimaryKey(record);
    }
}
