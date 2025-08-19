/**
 * Author：周朔鹏
 * 编制时间：2025-08-09

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import com.course.server.domain.RdmsUser;
import lombok.Data;

import java.util.Date;

@Data
public class RdmsUserDto extends RdmsUser {
    private String selectedCustomerId;

    private String createTimeStr;

    /**
     * 验证码
     */
    private String imageCode;

    /**
     * 图片验证码token
     */
    private String imageCodeToken;

    /**
     * 短信二维码
     * */
    private String smsCode;

    boolean authFlag;  //是否微信认证

    private String token;

    private String loginType;

}
