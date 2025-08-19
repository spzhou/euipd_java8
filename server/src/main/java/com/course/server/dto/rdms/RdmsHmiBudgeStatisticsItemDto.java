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
public class RdmsHmiBudgeStatisticsItemDto {

    private String customerId;
    private String projectId;
    private String subprojectId;
    private String preprojectId;

    private String classify;
    private String classifyName;

    private BigDecimal approvedBudgetBt;
    private BigDecimal approvedBudgetZc;
    private BigDecimal approvedBudget;

    private BigDecimal budgetBt;
    private BigDecimal budgetZc;
    private BigDecimal budget;

    private BigDecimal budgetExeBt;
    private BigDecimal budgetExeZc;
    private BigDecimal budgetExe;

    private BigDecimal budgetExeConfirmBt;
    private BigDecimal budgetExeConfirmZc;
    private BigDecimal budgetExeConfirm;

    private BigDecimal budgetOngoingBt;
    private BigDecimal budgetOngoingZc;
    private BigDecimal budgetOngoing;

    private BigDecimal budgetResidueBt;
    private BigDecimal budgetResidueZc;
    private BigDecimal budgetResidue;

    public RdmsHmiBudgeStatisticsItemDto(){
        this.setApprovedBudgetBt(BigDecimal.ZERO);
        this.setApprovedBudgetZc(BigDecimal.ZERO);
        this.setApprovedBudget(BigDecimal.ZERO);

        this.setBudgetBt(BigDecimal.ZERO);
        this.setBudgetZc(BigDecimal.ZERO);
        this.setBudget(BigDecimal.ZERO);

        this.setBudgetExeBt(BigDecimal.ZERO);
        this.setBudgetExeZc(BigDecimal.ZERO);
        this.setBudgetExe(BigDecimal.ZERO);

        this.setBudgetExeConfirmBt(BigDecimal.ZERO);
        this.setBudgetExeConfirmZc(BigDecimal.ZERO);
        this.setBudgetExeConfirm(BigDecimal.ZERO);

        this.setBudgetOngoingBt(BigDecimal.ZERO);
        this.setBudgetOngoingZc(BigDecimal.ZERO);
        this.setBudgetOngoing(BigDecimal.ZERO);

        this.setBudgetResidueBt(BigDecimal.ZERO);
        this.setBudgetResidueZc(BigDecimal.ZERO);
        this.setBudgetResidue(BigDecimal.ZERO);
    }
}
