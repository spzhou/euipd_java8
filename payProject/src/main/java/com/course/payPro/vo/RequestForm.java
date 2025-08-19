/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.payPro.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RequestForm {
    private String orderNo;
    private String orderName;
    private BigDecimal amount;
    protected Integer payType;
}
