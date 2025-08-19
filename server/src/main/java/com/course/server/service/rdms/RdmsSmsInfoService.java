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
import com.course.server.domain.RdmsSmsInfo;
import com.course.server.domain.RdmsSmsInfoExample;
import com.course.server.dto.PageDto;
import com.course.server.dto.rdms.RdmsSmsInfoDto;
import com.course.server.mapper.RdmsSmsInfoMapper;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.course.server.service.common.constant.Constant.CX_ACCESS_KEY_ID;
import static com.course.server.service.common.constant.Constant.CX_ACCESS_KEY_SECRET;

@Service
public class RdmsSmsInfoService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsSmsInfoService.class);

    @Resource
    private RdmsSmsInfoMapper rdmsSmsInfoMapper;

    @Value("${sms.signature}")
    private String signature;

    @Value("${sms.notify.job}")
    private String jobNotify;

    @Value("${customer.access.key}")
    private String customerAccessKey;

    @Value("${customer.access.key.secret}")
    private String customerAccessKeySecret;

    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsSmsInfoExample rdmsSmsInfoExample = new RdmsSmsInfoExample();
        List<RdmsSmsInfo> smsInfoList = rdmsSmsInfoMapper.selectByExample(rdmsSmsInfoExample);
        PageInfo<RdmsSmsInfo> pageInfo = new PageInfo<>(smsInfoList);
        pageDto.setTotal(pageInfo.getTotal());
        List<RdmsSmsInfoDto> smsInfoDtoList = CopyUtil.copyList(smsInfoList, RdmsSmsInfoDto.class);
        pageDto.setList(smsInfoDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(RdmsSmsInfo rdmsSmsInfo) {
        if (ObjectUtils.isEmpty(rdmsSmsInfo.getId())) {
            this.insert(rdmsSmsInfo);
        } else {
            this.update(rdmsSmsInfo);
        }
    }

    /**
     * 新增
     */
    private void insert(RdmsSmsInfo rdmsSmsInfo) {
        if(ObjectUtils.isEmpty(rdmsSmsInfo.getCreateTime())){
            rdmsSmsInfo.setCreateTime(new Date());
        }
        rdmsSmsInfo.setId(UuidUtil.getShortUuid());
        rdmsSmsInfoMapper.insert(rdmsSmsInfo);
    }

    /**
     * 更新
     */
    private void update(RdmsSmsInfo rdmsSmsInfo) {
        rdmsSmsInfoMapper.updateByPrimaryKey(rdmsSmsInfo);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        rdmsSmsInfoMapper.deleteByPrimaryKey(id);
    }

    public void sendJobNotify(RdmsSmsInfo rdmsSmsInfo) {
        this.save(rdmsSmsInfo);
        rdmsSmsInfo.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String timeStr = simpleDateFormat.format(rdmsSmsInfo.getTime());
        //发送验证码
        // 遨博
        try {
            com.aliyun.dysmsapi20170525.Client client = AliSmsSend.createClient(customerAccessKey, customerAccessKeySecret);
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setPhoneNumbers(rdmsSmsInfo.getMobile())
//                    .setSignName("遨博北京智能科技")  //遨博  这里使用变量会有问题,可能是签名为中文的缘故
                    .setSignName(signature)
//                    .setSignName("瑞柯恩")  //中文字符从properties中读过来是乱码!!
                    .setTemplateCode(jobNotify)
                    .setTemplateParam("{" +
                            "\"name\":\"" + rdmsSmsInfo.getName() + "\"," +
                            "\"jobName\":\"" + rdmsSmsInfo.getJobName() + "\"," +
                            "\"time\":\"" + timeStr + "\"," +
                            "}");
            SendSmsResponse resp = client.sendSms(sendSmsRequest);
            com.aliyun.teaconsole.Client.log(Common.toJSONString(TeaModel.buildMap(resp)));
        } catch (Exception e) {
            throw new RuntimeException("短信发送未成功,请检查网络连接");
        }
    }

    public void sendRegisterRecheckNotify(RdmsSmsInfo rdmsSmsInfo) {
        this.save(rdmsSmsInfo);
        //发送验证码
        try {
            com.aliyun.dysmsapi20170525.Client client = AliSmsSend.createClient(CX_ACCESS_KEY_ID, CX_ACCESS_KEY_SECRET);
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setPhoneNumbers(rdmsSmsInfo.getMobile())
                    .setSignName("EUIPD")
                    .setTemplateCode("SMS_465455281")
                    .setTemplateParam("{" +
                            "\"name\":\"" + rdmsSmsInfo.getName() + "\"," +
                            "\"phone\":\"" + rdmsSmsInfo.getPhone() + "\"," +
                            "}");
            SendSmsResponse resp = client.sendSms(sendSmsRequest);
            com.aliyun.teaconsole.Client.log(Common.toJSONString(TeaModel.buildMap(resp)));
        } catch (Exception e) {
            throw new RuntimeException("短信发送未成功,请检查网络连接");
        }
    }

    public void sendRegisterRecheckPassNotify(RdmsSmsInfo rdmsSmsInfo) {
        this.save(rdmsSmsInfo);
        //发送验证码
        try {
            com.aliyun.dysmsapi20170525.Client client = AliSmsSend.createClient(CX_ACCESS_KEY_ID, CX_ACCESS_KEY_SECRET);
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setPhoneNumbers(rdmsSmsInfo.getMobile())
                    .setSignName("EUIPD")
                    .setTemplateCode("SMS_465455282")
                    .setTemplateParam("{" +
                            "\"name\":\"" + rdmsSmsInfo.getName() + "\"," +
                            "}");
            SendSmsResponse resp = client.sendSms(sendSmsRequest);
            com.aliyun.teaconsole.Client.log(Common.toJSONString(TeaModel.buildMap(resp)));
        } catch (Exception e) {
            throw new RuntimeException("短信发送未成功,请检查网络连接");
        }
    }

}
