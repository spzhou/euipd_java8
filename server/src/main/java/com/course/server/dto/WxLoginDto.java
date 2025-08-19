/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class WxLoginDto {
    private String code;
    private String nickName;
    private String avatarUrl;
    private Number gender;
    private String country;
    private String province;
    private String city;
    private String language;

    private String openid;
    private String session_key;
    private String unionid;
    private String errmsg;
    private Number errcode;


}
