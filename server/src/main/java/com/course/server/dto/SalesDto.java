/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SalesDto {
    private String id;

    private String trueName;

    private String companyName;

    private String desc;

    private String contactPhone;

    private String region;

    private String city;

    private Date createTime;

    private String salesClassify;

    private String staffPhone; //接收短信的工作人员电话

}