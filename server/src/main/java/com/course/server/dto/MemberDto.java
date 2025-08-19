/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class MemberDto {

    /**
     * id
     */
    private String id;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String name;

    /**
     * 头像url
     */
    private String photo;

    /**
     * 注册时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date registerTime;

    /**
     * 图片验证码
     */
    private String imageCode;

    /**
     * 图片验证码token
     */
    private String imageCodeToken;

    /**
     * 短信验证码
     */
    private String smsCode;

    private String intro;

    private String comId;

    private Integer isDeleted;

    private String level;

    private String county;

    private String city;

    private String title;

}
