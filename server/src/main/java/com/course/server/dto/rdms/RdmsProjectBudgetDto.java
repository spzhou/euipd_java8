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
public class RdmsProjectBudgetDto {
    private String id;

    private String customerId;

    private String projectId;

    private BigDecimal devManhour;

    private BigDecimal devManhourFee;

    private BigDecimal testManhour;

    private BigDecimal testManhourFee;

    private BigDecimal materialFee;

    private BigDecimal otherFee;

    private Date createTime;

    private String deleted;

}
