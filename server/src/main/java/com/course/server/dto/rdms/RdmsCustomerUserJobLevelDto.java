/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RdmsCustomerUserJobLevelDto {
    private String id;

    private String customerId;

    private String levelCode;

    private String leveName;

    private BigDecimal manHourFee;

    private String deleted;
}
