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
public class RdmsCharacterBudgetDto {
    private String id;  //characterId

    private String characterName;

    private String characterSerial;

    private String customerId;

    private String projectId;

    private String subprojectId;

    private String milestoneId;

    private String productManagerId;

    private Double devManhour;

    private Double devManhourApproved;

    private Double testManhour;

    private Double testManhourApproved;

    private BigDecimal materialFee;

    private BigDecimal materialFeeApproved;

    private BigDecimal testFee;

    private BigDecimal powerFee;

    private BigDecimal conferenceFee;

    private BigDecimal businessFee;

    private BigDecimal cooperationFee;

    private BigDecimal propertyFee;

    private BigDecimal laborFee;

    private BigDecimal consultingFee;

    private BigDecimal managementFee;

    private BigDecimal sumOtherFee;

    private BigDecimal budget;

    private List<RdmsCharacterBudgetDto> decomposeCharacterBudgets;

    private BigDecimal standDevManhourFee;
    private BigDecimal standTestManhourFee;

    private double remainDevManhour;
    private double remainTestManhour;
    private BigDecimal remainMaterialBudget;
    private BigDecimal remainOtherBudget;

    private BigDecimal remainEquipmentFee;
    private BigDecimal remainTestFee;
    private BigDecimal remainPowerFee;
    private BigDecimal remainConferenceFee;
    private BigDecimal remainBusinessFee;
    private BigDecimal remainCooperationFee;
    private BigDecimal remainPropertyFee;
    private BigDecimal remainLaborFee;
    private BigDecimal remainConsultingFee;
    private BigDecimal remainManagementFee;

    private BigDecimal remainBudget;
}
