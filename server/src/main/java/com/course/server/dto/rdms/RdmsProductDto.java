/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class RdmsProductDto {
    private String id;

    private String customerId;
    private String customerName;

    private String productCode;
    private String productName;

    private String description;

    private BigDecimal budget;

    private String ipmtId;
    private String ipmtSuperviseName;

    private String productManagerId;
    private String productManagerName;

    private String projectManagerId;
    private String projectManagerName;

    private String projectId;
    private String projectName;

    private String status;

    private Date setupTime;

    private Date releaseTimePlan;

    private Date createTime;

    private Integer deleted;
}
