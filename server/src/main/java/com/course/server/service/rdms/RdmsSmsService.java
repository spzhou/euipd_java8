/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.tea.TeaModel;
import com.aliyun.teautil.Common;
import com.course.server.domain.RdmsSmsExample;
import com.course.server.domain.RdmsSms;
import com.course.server.dto.*;
import com.course.server.dto.rdms.RdmsSmsDto;
import com.course.server.enums.SmsStatusEnum;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.exception.SmsCodeException;
import com.course.server.mapper.RdmsSmsMapper;
import com.course.server.util.AliSmsSend;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class RdmsSmsService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsSmsService.class);

    @Resource
    private RdmsSmsMapper rdmsSmsMapper;

    @Value("${sms.signature}")
    private String signature;

    @Value("${sms.verification.code}")
    private String verificationTemplate;

    @Value("${customer.access.key}")
    private String customerAccessKey;

    @Value("${customer.access.key.secret}")
    private String customerAccessKeySecret;

    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsSmsExample rdmsSmsExample = new RdmsSmsExample();
        List<RdmsSms> smsList = rdmsSmsMapper.selectByExample(rdmsSmsExample);
        PageInfo<RdmsSms> pageInfo = new PageInfo<>(smsList);
        pageDto.setTotal(pageInfo.getTotal());
        List<RdmsSmsDto> smsDtoList = CopyUtil.copyList(smsList, RdmsSmsDto.class);
        pageDto.setList(smsDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(RdmsSmsDto rdmsSmsDto) {
        RdmsSms rdmsSms = CopyUtil.copy(rdmsSmsDto, RdmsSms.class);
        if (ObjectUtils.isEmpty(rdmsSmsDto.getId())) {
            this.insert(rdmsSms);
        } else {
            this.update(rdmsSms);
        }
    }

    /**
     * 新增
     */
    private void insert(RdmsSms rdmsSms) {
        if(!ObjectUtils.isEmpty(rdmsSms.getAt())){
            rdmsSms.setAt(new Date());
        }
        rdmsSms.setId(UuidUtil.getShortUuid());
        rdmsSmsMapper.insert(rdmsSms);
    }

    /**
     * 更新
     */
    private void update(RdmsSms rdmsSms) {
        rdmsSmsMapper.updateByPrimaryKey(rdmsSms);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        rdmsSmsMapper.deleteByPrimaryKey(id);
    }

    /**
     * 发送短信验证码
     * 同手机号同操作1分钟内不能重复发送短信
     * @param rdmsSmsDto
     */
    public void sendCode(RdmsSmsDto rdmsSmsDto) {
        RdmsSmsExample rdmsSmsExample = new RdmsSmsExample();
        RdmsSmsExample.Criteria criteria = rdmsSmsExample.createCriteria();
        // 查找1分钟内有没有同手机号同操作发送记录且没被用过
        criteria.andMobileEqualTo(rdmsSmsDto.getMobile())
                .andUseEqualTo(rdmsSmsDto.getUse())
                .andStatusEqualTo(SmsStatusEnum.NOT_USED.getCode())
                .andAtGreaterThan(new Date(new Date().getTime() - 1 * 60 * 1000));
        List<RdmsSms> smsList = rdmsSmsMapper.selectByExample(rdmsSmsExample);

        if (smsList == null || smsList.size() == 0) {
            saveAndSend(rdmsSmsDto);
        } else {
            LOG.warn("短信请求过于频繁, {}", rdmsSmsDto.getMobile());
            throw new BusinessException(BusinessExceptionCode.MOBILE_CODE_TOO_FREQUENT);
        }
    }

    /**
     * 保存并发送短信验证码
     * @param rdmsSmsDto
     */
    private void saveAndSend(RdmsSmsDto rdmsSmsDto) {
        // 生成6位数字
        String code = String.valueOf((int)(((Math.random() * 9) + 1) * 100000));
        rdmsSmsDto.setAt(new Date());
        rdmsSmsDto.setStatus(SmsStatusEnum.NOT_USED.getCode());
        rdmsSmsDto.setCode(code);
        this.save(rdmsSmsDto);

        // TODO 调第三方短信接口发送短信
        //发送验证码
        try {
            com.aliyun.dysmsapi20170525.Client client = AliSmsSend.createClient(customerAccessKey, customerAccessKeySecret);
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setPhoneNumbers(rdmsSmsDto.getMobile())
                    .setSignName(signature)
//                    .setSignName("瑞柯恩")  //从proterties中读取会乱码
                    .setTemplateCode(verificationTemplate)
                    .setTemplateParam("{\"code\":\""+code+"\"}");
            SendSmsResponse resp = client.sendSms(sendSmsRequest);
            com.aliyun.teaconsole.Client.log(Common.toJSONString(TeaModel.buildMap(resp)));
        }catch (Exception e){
            throw new SmsCodeException();
        }
    }

    /**
     * 验证码5分钟内有效，且操作类型要一致
     * @param rdmsSmsDto
     */
    public void validCode(RdmsSmsDto rdmsSmsDto) {
        RdmsSmsExample example = new RdmsSmsExample();
        example.setOrderByClause("at desc");
        RdmsSmsExample.Criteria criteria = example.createCriteria();
        // 查找5分钟内同手机号同操作发送记录
        criteria.andMobileEqualTo(rdmsSmsDto.getMobile())
                .andUseEqualTo(rdmsSmsDto.getUse())
                .andAtGreaterThan(new Date(new Date().getTime() - 5 * 60 * 1000));
        List<RdmsSms> smsList = rdmsSmsMapper.selectByExample(example);

        if (smsList != null && smsList.size() > 0) {
            RdmsSms smsDb = smsList.get(0);
            if (!smsDb.getCode().equals(rdmsSmsDto.getCode())) {
                LOG.warn("短信验证码不正确");
                throw new BusinessException(BusinessExceptionCode.MOBILE_CODE_ERROR);
            } else {
                smsDb.setStatus(SmsStatusEnum.USED.getCode());
                rdmsSmsMapper.updateByPrimaryKey(smsDb);
            }
        } else {
            LOG.warn("短信验证码不存在或已过期，请重新发送短信");
            throw new BusinessException(BusinessExceptionCode.MOBILE_CODE_EXPIRED);
        }
    }
}
