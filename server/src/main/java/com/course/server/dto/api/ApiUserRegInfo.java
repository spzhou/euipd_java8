/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.api;

import lombok.Data;

import java.util.Date;

@Data
public class ApiUserRegInfo {
    private String customerId;          // 客户id 在获取token时获得
    private String jobNum;              //工号
    private String loginName;           //用于登录的电话号码
    private String trueName;            //真实姓名
    private String subject;             //专业
    private String degree;              //学位
    private String educationBackground; //学历
    private String jobPosition;         //职位
    private String title;               //头衔
    private String jobLevel;            //职级
    private Date joinTime;              //入职时间
    private String description;         //能力描述
    private String officeEmail;         //工作邮件(选填)
    private String officeTel;           //工作电话(选填)
    private String officeAddress;       //办公地址(选填)
    private Date birthday;              //生日(选填)

}
