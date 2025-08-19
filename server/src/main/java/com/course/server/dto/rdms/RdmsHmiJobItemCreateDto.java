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
import java.util.List;

@Data
public class RdmsHmiJobItemCreateDto {
    private String jobName;
    private String testJobitemId; //被测试的jobitemId
    private String parentJobitemId; //任务工单
    private String qualityId; //质量反馈
    private String qualityName;
    private String bugfeedbackId; //缺陷反馈
    private String bugfeedbackName; //缺陷反馈
    private String executorId;
    private List<RdmsHmiExecutorPlainDto> executors;
    private String productManagerId;
    private String productManagerName;
    private String projectManagerId;
    private String projectManagerName;
    private String testManagerId;
    private String testManagerName;
    private String taskDescription;
    private String projectId;
    private String preProjectId;
    private String subprojectId;
    private String characterId;
    private String characterName;
    private String createCharacterJobitemId;
    private List<RdmsHmiDemandPlainDto> demandPlains;
    private Integer manhour;
    private Date planSubmissionTime;
    private String fileListStr;
    private List<RdmsFileDto> fileList;
//    private RdmsBudgetSummaryDto characterBudget;
    private RdmsBudgetSummaryDto budgetExeInfo;
//    private RdmsBudgetSummaryDto jobitemBudget;
    private BigDecimal stdDevManhourFee;
    private BigDecimal stdTestManhourFee;

    private String rdType;

    private Date planCompleteTime; //功能单元计划完成时间

}
