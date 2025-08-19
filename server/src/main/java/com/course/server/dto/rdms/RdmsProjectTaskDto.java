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
public class RdmsProjectTaskDto {
    private String id;

    private String taskSerialNo;

    private String customerId;

    private String preProjectId;

    private String projectId;

    private String subprojectId;

    private String productManagerId;

    private String projectManagerId;

    private String taskName;

    private String taskDescription;

    private String testMethod;

    private String fileListStr;

    private String managerId;

    private String status;

    private String jobitemType;

    private String jobitemId;

    private Integer treeDeep;

    private Date createTime;

    private Date submitTime;

    private BigDecimal budget;

    private String parentTaskId;

    private Integer deleted;

}
