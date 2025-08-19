/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain.calculate;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CalManhour {
    private String customerId;
    private String projectId;
    private String subprojectId;
    //rdms_character
    private String characterId;
    private Integer devCharacterManhour;
    private Integer testCharacterManhour;
    //rdms_job_item
    private String jobItemId;
    private Integer jobItemManhour;
    private String executorId;
    //rdms_manhour_standard
    private BigDecimal devStandardManhourFee;
    private BigDecimal testStandardManhourFee;
    //rdms_customer_user
    private String jobLevel;
    //rdms_customer_user_job_level
    private String userLevelCode;
    private BigDecimal userManHourFee;

}
