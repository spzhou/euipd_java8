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
public class DemandDto {
    private String id;

    private String demandName;

    private String demandInst;

    private String desc;

    private String demandContactName;

    private String demandContactPhone;

    private String recommend;

    private String involved;

    private String region;

    private String companyName;

    private String productName;

    private Date date1;

    private String name;

    private String phone;

    private String cooperation;

    private Date createTime;

    private String demandClassify;

    private String staffPhone;  //接收短信的工作人员电话
}