// This file is auto-generated, don't edit it. Thanks.
/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.util;

import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.tea.TeaModel;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.Common;

import static com.course.server.constants.Constants.SUPER_ADMIN_LOGIN_NAME;
import static com.course.server.service.common.constant.Constant.ACCESS_KEY_ID;
import static com.course.server.service.common.constant.Constant.ACCESS_KEY_SECRET;


public class AliSmsSend {

    /**
     * 使用AK&SK初始化账号Client
     * @param accessKeyId
     * @param accessKeySecret
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.dysmsapi20170525.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new com.aliyun.dysmsapi20170525.Client(config);
    }

    public static void main(String[] args_) throws Exception {
        java.util.List<String> args = java.util.Arrays.asList(args_);
        com.aliyun.dysmsapi20170525.Client client = AliSmsSend.createClient(ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(SUPER_ADMIN_LOGIN_NAME)
                .setSignName("睿慕课")
                .setTemplateCode("SMS_177540484")
                .setTemplateParam("{\"code\":\"654321\"}");
        SendSmsResponse resp = client.sendSms(sendSmsRequest);
        com.aliyun.teaconsole.Client.log(Common.toJSONString(TeaModel.buildMap(resp)));
    }
}
