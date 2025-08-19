/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import com.course.server.domain.RdmsCustomer;
import lombok.Data;

import java.util.Date;

@Data
public class RdmsCustomerDto extends RdmsCustomer {

    private String statusName;

    private String createTimeStr;

    private String imageCode;

    private String imageCodeToken;

    private String smsCode;

    private String superManagerId;

    private String bossId;
    private String bossName;

    private String nickname;

    private Integer sex;

    private String headimgurl;

    private String unionid;

    private String openid;

    private String password; //一次MD5

    private String regType; //注册类型, 比如:睿慕课用户注册为: aiimooc
}
