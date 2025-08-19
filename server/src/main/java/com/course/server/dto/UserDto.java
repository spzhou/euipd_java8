/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto;

import lombok.Data;

@Data
public class UserDto {

    private String id;

    private String loginName;

    private String name;

    private String password;

    private String registName;

    private String profileImg;

    private String title;

    private String motto;

    private String intro;

    /**
     * 密码
     */
    private String channelId;

    /**
     * 验证码
     */
    private String imageCode;

    /**
     * 图片验证码token
     */
    private String imageCodeToken;

    private String teacherUrl;
    private String showUrl;
    private String channelPassword;
    private String creatorLoginname;
    private String liveStatus;

    private String smsCode;

    private String institutionId;
    private String institutionName;
    private String logo;
    private String orgLink;



}