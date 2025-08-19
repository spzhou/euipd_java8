/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto;

import com.course.server.enums.EnterStatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
@Data
public class UserEnterDto {
    private String id;

    private String loginName;

    private String name;

    private String trueName;

    private String institution;

    private String title;

    private String education;

    private String university;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date registerTime;

    private String smsCode;
    private String status = EnterStatusEnum.WAITING.getCode();

}