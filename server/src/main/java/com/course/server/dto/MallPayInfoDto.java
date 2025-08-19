/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class MallPayInfoDto {
    private String id;

    private String customerId;

    private String orderNo;

    private Integer payType;

    private String paymentSerial;

    private BigDecimal payAmount;

    private String platformNumber;

    private Integer payStatus;

    private Date createTime;

    private Date updateTime;

}