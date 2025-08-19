/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import com.course.server.enums.rdms.BudgetExeTypeEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RdmsHmiBudgeStatisticsDto {

    private String customerId;
    private String projectId;
    private String parentId;
    private String subprojectId;
    private String preprojectId;
    private String projectCode;

    private String customerName;
    private String projectName;
    private String parentName;
    private String subprojectName;

    private String projectManagerId;
    private String projectManagerName;

    private String sysgmId;
    private String sysgmName;

    List<RdmsHmiBudgeStatisticsItemDto> statisticsItemDtoList;

    private BigDecimal sumManhourBudget;   //总计划工时
    private BigDecimal sumActiveManhourBudget;  //累计工时
    private BigDecimal sumMaterialBudget;  //总物料费预算
    private BigDecimal sumActiveMaterialBudget;  //经过批准的总物料费用
    private BigDecimal sumEquipmentBudget;
    private BigDecimal sumActiveEquipmentBudget;  //经过批准的总设备费用
    private BigDecimal sumOtherBudget;
    private BigDecimal sumActiveOtherBudget;  //经过批准的总其他费用
    private BigDecimal sumBudget;
    private BigDecimal sumActiveBudget;
    private BigDecimal budgetExeRate;

    List<RdmsHmiBudgeStatisticsDto> subprojectStatisticsItemList;
}
