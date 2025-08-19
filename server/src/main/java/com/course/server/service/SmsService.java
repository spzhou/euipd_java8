/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service;

import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.tea.TeaModel;
import com.aliyun.teautil.Common;
import com.course.server.domain.Sms;
import com.course.server.domain.SmsExample;
import com.course.server.dto.*;
import com.course.server.enums.SmsStatusEnum;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.exception.SmsCodeException;
import com.course.server.mapper.SmsMapper;
import com.course.server.util.AliSmsSend;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.course.server.constants.Constants.SUPER_ADMIN_LOGIN_NAME;
import static com.course.server.service.common.constant.Constant.ACCESS_KEY_ID;
import static com.course.server.service.common.constant.Constant.ACCESS_KEY_SECRET;

@Service
public class SmsService {

    private static final Logger LOG = LoggerFactory.getLogger(SmsService.class);

    @Resource
    private SmsMapper smsMapper;

    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        SmsExample smsExample = new SmsExample();
        List<Sms> smsList = smsMapper.selectByExample(smsExample);
        PageInfo<Sms> pageInfo = new PageInfo<>(smsList);
        pageDto.setTotal(pageInfo.getTotal());
        List<SmsDto> smsDtoList = CopyUtil.copyList(smsList, SmsDto.class);
        pageDto.setList(smsDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(SmsDto smsDto) {
        Sms sms = CopyUtil.copy(smsDto, Sms.class);
        if (ObjectUtils.isEmpty(smsDto.getId())) {
            this.insert(sms);
        } else {
            this.update(sms);
        }
    }

    /**
     * 新增
     */
    private void insert(Sms sms) {
        Date now = new Date();
        sms.setId(UuidUtil.getShortUuid());
        smsMapper.insert(sms);
    }

    /**
     * 更新
     */
    private void update(Sms sms) {
        smsMapper.updateByPrimaryKey(sms);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        smsMapper.deleteByPrimaryKey(id);
    }

    /**
     * 发送短信验证码
     * 同手机号同操作1分钟内不能重复发送短信
     * 
     * @param smsDto 短信数据传输对象
     */
    public void sendCode(SmsDto smsDto) {
        SmsExample example = new SmsExample();
        SmsExample.Criteria criteria = example.createCriteria();
        // 查找1分钟内有没有同手机号同操作发送记录且没被用过
        criteria.andMobileEqualTo(smsDto.getMobile())
                .andUseEqualTo(smsDto.getUse())
                .andStatusEqualTo(SmsStatusEnum.NOT_USED.getCode())
                .andAtGreaterThan(new Date(new Date().getTime() - 1 * 60 * 1000));
        List<Sms> smsList = smsMapper.selectByExample(example);

        if (smsList == null || smsList.size() == 0) {
            saveAndSend(smsDto);
        } else {
            LOG.warn("短信请求过于频繁, {}", smsDto.getMobile());
            throw new BusinessException(BusinessExceptionCode.MOBILE_CODE_TOO_FREQUENT);
        }
    }

    /**
     * 保存并发送短信验证码
     * 生成验证码并调用第三方短信接口发送
     * 
     * @param smsDto 短信数据传输对象
     */
    private void saveAndSend(SmsDto smsDto) {
        // 生成6位数字
        String code = String.valueOf((int)(((Math.random() * 9) + 1) * 100000));
        smsDto.setAt(new Date());
        smsDto.setStatus(SmsStatusEnum.NOT_USED.getCode());
        smsDto.setCode(code);
        this.save(smsDto);

        // TODO 调第三方短信接口发送短信
        //发送验证码
        try {
            com.aliyun.dysmsapi20170525.Client client = AliSmsSend.createClient(ACCESS_KEY_ID, ACCESS_KEY_SECRET);
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setPhoneNumbers(smsDto.getMobile())
                    .setSignName("AiiSCI")
                    .setTemplateCode("SMS_177540484")
                    .setTemplateParam("{\"code\":\""+code+"\"}");
            SendSmsResponse resp = client.sendSms(sendSmsRequest);
            com.aliyun.teaconsole.Client.log(Common.toJSONString(TeaModel.buildMap(resp)));
        }catch (Exception e){
            throw new SmsCodeException();
        }
    }

    /**
     * 当客户点击购物车或者提交询价单时,发送订单提醒短信
     * 发送订单通知短信给指定手机号
     * 
     * @param smsNotifyDto 短信通知数据传输对象
     */
    public void sendOrderNotify(SmsNotifyDto smsNotifyDto) {
        //发送验证码
        try {
            com.aliyun.dysmsapi20170525.Client client = AliSmsSend.createClient(ACCESS_KEY_ID, ACCESS_KEY_SECRET);
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setPhoneNumbers(smsNotifyDto.getPhone())
                    .setSignName("AiiSCI")
                    .setTemplateCode("SMS_248775223")
                    .setTemplateParam(
                            "{"+
                            "\"user\":\""+smsNotifyDto.getUser()+"\"," +
                            "\"phone\":\""+smsNotifyDto.getPhone()+"\","+
                            "\"name\":\""+smsNotifyDto.getCustomer()+"\","+
                            "\"phone1\":\""+smsNotifyDto.getPhone1()+"\","+
                            "\"order\":\""+smsNotifyDto.getOrder()+"\""+
                            "}");
            SendSmsResponse resp = client.sendSms(sendSmsRequest);
            com.aliyun.teaconsole.Client.log(Common.toJSONString(TeaModel.buildMap(resp)));
        }catch (Exception e){
            throw new SmsCodeException();
        }
    }

    /**
     * 当前端用户提交需求时,工作人员会收到这条短信
     * 发送需求通知短信给工作人员
     * 
     * @param demandDto 需求数据传输对象
     */
    public void sendDemandNotify(DemandDto demandDto) {
        if(demandDto.getStaffPhone() == null){
            demandDto.setStaffPhone(SUPER_ADMIN_LOGIN_NAME);
        }
        try {
            com.aliyun.dysmsapi20170525.Client client = AliSmsSend.createClient(ACCESS_KEY_ID, ACCESS_KEY_SECRET);
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setPhoneNumbers(demandDto.getStaffPhone())
                    .setSignName("AiiSCI")
                    .setTemplateCode("SMS_251835244")
                    .setTemplateParam(
                            "{"+
                            "\"name\":\""+demandDto.getName()+"\","+
                            "\"title\":\""+demandDto.getDemandName()+"\","+
                            "\"phone\":\""+demandDto.getPhone()+"\","+
                            "}");
            SendSmsResponse resp = client.sendSms(sendSmsRequest);
            com.aliyun.teaconsole.Client.log(Common.toJSONString(TeaModel.buildMap(resp)));
        }catch (Exception e){
            throw new SmsCodeException();
        }
    }

    /**
     * 当前端用户提交需求时,工作人员会收到这条短信
     * 发送销售通知短信给工作人员
     * 
     * @param salesDto 销售数据传输对象
     */
    public void sendSalesNotify(SalesDto salesDto) {
        if(salesDto.getStaffPhone() == null){
            salesDto.setStaffPhone(SUPER_ADMIN_LOGIN_NAME);
        }
        try {
            com.aliyun.dysmsapi20170525.Client client = AliSmsSend.createClient(ACCESS_KEY_ID, ACCESS_KEY_SECRET);
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setPhoneNumbers(salesDto.getStaffPhone())
                    .setSignName("AiiSCI")
                    .setTemplateCode("SMS_251745425")
                    .setTemplateParam(
                            "{"+
                            "\"name\":\""+salesDto.getTrueName()+"\","+
                            "\"phone\":\""+salesDto.getContactPhone()+"\","+
                            "}");
            SendSmsResponse resp = client.sendSms(sendSmsRequest);
            com.aliyun.teaconsole.Client.log(Common.toJSONString(TeaModel.buildMap(resp)));
        }catch (Exception e){
            throw new SmsCodeException();
        }
    }



    /**
     * 验证码5分钟内有效，且操作类型要一致
     * 验证短信验证码的有效性和正确性
     * 
     * @param smsDto 短信数据传输对象
     */
    public void validCode(SmsDto smsDto) {
        SmsExample example = new SmsExample();
        example.setOrderByClause("at desc");
        SmsExample.Criteria criteria = example.createCriteria();
        // 查找5分钟内同手机号同操作发送记录
        criteria.andMobileEqualTo(smsDto.getMobile()).andUseEqualTo(smsDto.getUse()).andAtGreaterThan(new Date(new Date().getTime() - 5 * 60 * 1000));
        List<Sms> smsList = smsMapper.selectByExample(example);

        if (smsList != null && smsList.size() > 0) {
            Sms smsDb = smsList.get(0);
            if (!smsDb.getCode().equals(smsDto.getCode())) {
                LOG.warn("短信验证码不正确");
                throw new BusinessException(BusinessExceptionCode.MOBILE_CODE_ERROR);
            } else {
                smsDb.setStatus(SmsStatusEnum.USED.getCode());
                smsMapper.updateByPrimaryKey(smsDb);
            }
        } else {
            LOG.warn("短信验证码不存在或已过期，请重新发送短信");
            throw new BusinessException(BusinessExceptionCode.MOBILE_CODE_EXPIRED);
        }
    }
}
