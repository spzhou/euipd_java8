/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto;

import com.course.server.domain.RdmsBudgetResearch;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RdmsBudgetResearchDto extends RdmsBudgetResearch {
    private Boolean hasRecord;
    private String stage;  //预算阶段

    public RdmsBudgetResearchDto(){
        super.setEquipmentFeeZc(BigDecimal.ZERO);
        super.setTestFeeZc(BigDecimal.ZERO);
        super.setMaterialFeeZc(BigDecimal.ZERO);
        super.setPowerFeeZc(BigDecimal.ZERO);
        super.setConferenceFeeZc(BigDecimal.ZERO);
        super.setBusinessFeeZc(BigDecimal.ZERO);
        super.setCooperationFeeZc(BigDecimal.ZERO);
        super.setPropertyFeeZc(BigDecimal.ZERO);
        super.setLaborFeeZc(BigDecimal.ZERO);
        super.setStaffFeeZc(BigDecimal.ZERO);
        super.setConsultingFeeZc(BigDecimal.ZERO);
        super.setManagementFeeZc(BigDecimal.ZERO);
        super.setOtherFeeZc(BigDecimal.ZERO);
        super.setPerformanceFeeZc(BigDecimal.ZERO);
        super.setInfrastructureFeeZc(BigDecimal.ZERO);

        super.setEquipmentFeeApprovedZc(BigDecimal.ZERO);
        super.setTestFeeApprovedZc(BigDecimal.ZERO);
        super.setMaterialFeeApprovedZc(BigDecimal.ZERO);
        super.setPowerFeeApprovedZc(BigDecimal.ZERO);
        super.setConferenceFeeApprovedZc(BigDecimal.ZERO);
        super.setBusinessFeeApprovedZc(BigDecimal.ZERO);
        super.setCooperationFeeApprovedZc(BigDecimal.ZERO);
        super.setPropertyFeeApprovedZc(BigDecimal.ZERO);
        super.setLaborFeeApprovedZc(BigDecimal.ZERO);
        super.setStaffFeeApprovedZc(BigDecimal.ZERO);
        super.setConsultingFeeApprovedZc(BigDecimal.ZERO);
        super.setManagementFeeApprovedZc(BigDecimal.ZERO);
        super.setOtherFeeApprovedZc(BigDecimal.ZERO);
        super.setPerformanceFeeApprovedZc(BigDecimal.ZERO);
        super.setInfrastructureFeeApprovedZc(BigDecimal.ZERO);

        super.setEquipmentFeeBt(BigDecimal.ZERO);
        super.setTestFeeBt(BigDecimal.ZERO);
        super.setMaterialFeeBt(BigDecimal.ZERO);
        super.setPowerFeeBt(BigDecimal.ZERO);
        super.setConferenceFeeBt(BigDecimal.ZERO);
        super.setBusinessFeeBt(BigDecimal.ZERO);
        super.setCooperationFeeBt(BigDecimal.ZERO);
        super.setPropertyFeeBt(BigDecimal.ZERO);
        super.setLaborFeeBt(BigDecimal.ZERO);
        super.setStaffFeeBt(BigDecimal.ZERO);
        super.setConsultingFeeBt(BigDecimal.ZERO);
        super.setManagementFeeBt(BigDecimal.ZERO);
        super.setOtherFeeBt(BigDecimal.ZERO);
        super.setPerformanceFeeBt(BigDecimal.ZERO);
        super.setInfrastructureFeeBt(BigDecimal.ZERO);

        super.setEquipmentFeeApprovedBt(BigDecimal.ZERO);
        super.setTestFeeApprovedBt(BigDecimal.ZERO);
        super.setMaterialFeeApprovedBt(BigDecimal.ZERO);
        super.setPowerFeeApprovedBt(BigDecimal.ZERO);
        super.setConferenceFeeApprovedBt(BigDecimal.ZERO);
        super.setBusinessFeeApprovedBt(BigDecimal.ZERO);
        super.setCooperationFeeApprovedBt(BigDecimal.ZERO);
        super.setPropertyFeeApprovedBt(BigDecimal.ZERO);
        super.setLaborFeeApprovedBt(BigDecimal.ZERO);
        super.setStaffFeeApprovedBt(BigDecimal.ZERO);
        super.setConsultingFeeApprovedBt(BigDecimal.ZERO);
        super.setManagementFeeApprovedBt(BigDecimal.ZERO);
        super.setOtherFeeApprovedBt(BigDecimal.ZERO);
        super.setPerformanceFeeApprovedBt(BigDecimal.ZERO);
        super.setInfrastructureFeeApprovedBt(BigDecimal.ZERO);

    }
}
