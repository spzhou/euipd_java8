/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.course.server.domain.*;
import com.course.server.dto.PageDto;
import com.course.server.dto.RdmsBudgetResearchDto;
import com.course.server.dto.rdms.RdmsBudgetAdjustDto;
import com.course.server.enums.rdms.BudgetClassifyStdEnum;
import com.course.server.enums.rdms.BudgetTypeEnum;
import com.course.server.enums.rdms.ProjectTypeEnum;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsBudgetResearchMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ObjectUtils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class RdmsBudgetResearchService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsBudgetResearchService.class);

    @Resource
    private RdmsBudgetResearchMapper rdmsBudgetResearchMapper;
    @Resource
    private RdmsProjectService rdmsProjectService;
    @Resource
    private RdmsSubprojectService rdmsSubprojectService;
    @Autowired
    private RdmsBudgetService rdmsBudgetService;

    public String adjustSubprojectBudget(@NotNull RdmsBudgetResearchDto budgetData) {
        //更新预算记录
        RdmsBudgetResearchDto record_origin = this.getRecordByCustomerIdAndSubprojectId(budgetData.getCustomerId(), budgetData.getSubprojectId());
        if (!ObjectUtils.isEmpty(record_origin) && record_origin.getHasRecord()) {
            budgetData.setProjectId(record_origin.getProjectId());
            budgetData.setParentId(record_origin.getParentId());
            budgetData.setCreaterId(record_origin.getCreaterId());

            budgetData.setEquipmentFeeApprovedBt(record_origin.getEquipmentFeeApprovedBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
            budgetData.setEquipmentFeeApprovedZc(record_origin.getEquipmentFeeApprovedZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));

            budgetData.setTestFeeApprovedBt(record_origin.getTestFeeApprovedBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
            budgetData.setTestFeeApprovedZc(record_origin.getTestFeeApprovedZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));

            budgetData.setMaterialFeeApprovedBt(record_origin.getMaterialFeeApprovedBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
            budgetData.setMaterialFeeApprovedZc(record_origin.getMaterialFeeApprovedZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));

            budgetData.setPowerFeeApprovedBt(record_origin.getPowerFeeApprovedBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
            budgetData.setPowerFeeApprovedZc(record_origin.getPowerFeeApprovedZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));

            budgetData.setConferenceFeeApprovedBt(record_origin.getConferenceFeeApprovedBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
            budgetData.setConferenceFeeApprovedZc(record_origin.getConferenceFeeApprovedZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));

            budgetData.setBusinessFeeApprovedBt(record_origin.getBusinessFeeApprovedBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
            budgetData.setBusinessFeeApprovedZc(record_origin.getBusinessFeeApprovedZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));

            budgetData.setCooperationFeeApprovedBt(record_origin.getCooperationFeeApprovedBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
            budgetData.setCooperationFeeApprovedZc(record_origin.getCooperationFeeApprovedZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));

            budgetData.setPropertyFeeApprovedBt(record_origin.getPropertyFeeApprovedBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
            budgetData.setPropertyFeeApprovedZc(record_origin.getPropertyFeeApprovedZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));

            budgetData.setLaborFeeApprovedBt(record_origin.getLaborFeeApprovedBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
            budgetData.setLaborFeeApprovedZc(record_origin.getLaborFeeApprovedZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));

            budgetData.setStaffFeeApprovedBt(record_origin.getStaffFeeApprovedBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
            budgetData.setStaffFeeApprovedZc(record_origin.getStaffFeeApprovedZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));

            budgetData.setConsultingFeeApprovedBt(record_origin.getConsultingFeeApprovedBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
            budgetData.setConsultingFeeApprovedZc(record_origin.getConsultingFeeApprovedZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));

            budgetData.setManagementFeeApprovedBt(record_origin.getManagementFeeApprovedBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
            budgetData.setManagementFeeApprovedZc(record_origin.getManagementFeeApprovedZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));

            budgetData.setOtherFeeApprovedBt(record_origin.getOtherFeeApprovedBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
            budgetData.setOtherFeeApprovedZc(record_origin.getOtherFeeApprovedZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));

            budgetData.setPerformanceFeeApprovedBt(record_origin.getPerformanceFeeApprovedBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
            budgetData.setPerformanceFeeApprovedZc(record_origin.getPerformanceFeeApprovedZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));

            budgetData.setInfrastructureFeeApprovedBt(record_origin.getInfrastructureFeeApprovedBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
            budgetData.setInfrastructureFeeApprovedZc(record_origin.getInfrastructureFeeApprovedZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));

            //原记录设置为删除状态
            this.delete(record_origin.getId());

            RdmsBudgetResearch record_new = CopyUtil.copy(budgetData, RdmsBudgetResearch.class);
            this.transformBudgetToYuan(record_new);

            record_new.setId(null);
            String newId = this.save(record_new);

            //重新计算调整差值
            BigDecimal itemBudget_origin = this.getItemBudgetOfSubproject(record_origin);
            BigDecimal itemBudget_new = this.getItemBudgetOfSubproject(budgetData.getSubprojectId());
            BigDecimal adjustBudget = itemBudget_origin.subtract(itemBudget_new);

            //修改当前subproject的addChange
            RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(budgetData.getSubprojectId());
            subproject.setAddCharge(subproject.getAddCharge().subtract(adjustBudget));
//            if(ObjectUtils.isEmpty(subproject.getBudget()) || subproject.getBudget().doubleValue()==0.0){
//                subproject.setChargeRate(BigDecimal.ZERO);
//            }else{
//                subproject.setChargeRate(subproject.getAddCharge().divide(subproject.getBudget().add(subproject.getAddCharge()), 4, RoundingMode.HALF_UP));
//            }
            rdmsSubprojectService.save(subproject);

            //修改project的addChange
            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(subproject.getProjectId());
            rdmsProject.setAddCharge(rdmsProject.getAddCharge().subtract(adjustBudget));
//            if(ObjectUtils.isEmpty(rdmsProject.getBudget()) || rdmsProject.getBudget().doubleValue()==0.0){
//                rdmsProject.setChargeRate(BigDecimal.ZERO);
//            }else{
//                rdmsProject.setChargeRate(rdmsProject.getAddCharge().divide(rdmsProject.getBudget().add(rdmsProject.getAddCharge()), 4, RoundingMode.HALF_UP));
//            }
            rdmsProjectService.update(rdmsProject);

            return newId;
        } else {
            return null;
        }
    }

    public String adjustMainProjectSubjectBudget(@NotNull RdmsBudgetAdjustDto budgetAdjustDto) {
        //更新预算记录
        RdmsBudgetResearchDto record_origin = this.getRecordByProjectIdAndStage(budgetAdjustDto.getProjectId(), BudgetTypeEnum.PROJECT);

        if (!ObjectUtils.isEmpty(record_origin) && record_origin.getHasRecord()) {
            //对主项目特定的预算科目进行调整
            BudgetClassifyStdEnum budgetClassifyEnum = BudgetClassifyStdEnum.getBudgetClassifyEnumByClassify(budgetAdjustDto.getBudgetSubject());
            switch (Objects.requireNonNull(budgetClassifyEnum)) {
                case EQUIPMENT:
                    record_origin.setEquipmentFeeApprovedZc(record_origin.getEquipmentFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setEquipmentFeeZc(record_origin.getEquipmentFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case MATERIAL:
                    record_origin.setMaterialFeeApprovedZc(record_origin.getMaterialFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setMaterialFeeZc(record_origin.getMaterialFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case TEST:
                    record_origin.setTestFeeApprovedZc(record_origin.getTestFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setTestFeeZc(record_origin.getTestFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case POWER:
                    record_origin.setPowerFeeApprovedZc(record_origin.getPowerFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setPowerFeeZc(record_origin.getPowerFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case CONFERENCE:
                    record_origin.setConferenceFeeApprovedZc(record_origin.getConferenceFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setConferenceFeeZc(record_origin.getConferenceFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case BUSINESS:
                    record_origin.setBusinessFeeApprovedZc(record_origin.getBusinessFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setBusinessFeeZc(record_origin.getBusinessFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case COOPERATION:
                    record_origin.setCooperationFeeApprovedZc(record_origin.getCooperationFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setCooperationFeeZc(record_origin.getCooperationFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case PROPERTY:
                    record_origin.setPropertyFeeApprovedZc(record_origin.getPropertyFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setPropertyFeeZc(record_origin.getPropertyFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case CONSULTING:
                    record_origin.setConsultingFeeApprovedZc(record_origin.getConsultingFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setConsultingFeeZc(record_origin.getConsultingFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case OTHER:
                    record_origin.setOtherFeeApprovedZc(record_origin.getOtherFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setOtherFeeZc(record_origin.getOtherFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case LABOR:
                    record_origin.setLaborFeeApprovedZc(record_origin.getLaborFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setLaborFeeZc(record_origin.getLaborFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case MANAGEMENT:
                    record_origin.setManagementFeeApprovedZc(record_origin.getManagementFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setManagementFeeZc(record_origin.getManagementFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case PERFORMANCE:
                    record_origin.setPerformanceFeeApprovedZc(record_origin.getPerformanceFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setPerformanceFeeZc(record_origin.getPerformanceFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case STAFF:
                    record_origin.setStaffFeeApprovedZc(record_origin.getStaffFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setStaffFeeZc(record_origin.getStaffFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case INFRASTRUCTURE:
                    record_origin.setInfrastructureFeeApprovedZc(record_origin.getInfrastructureFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setInfrastructureFeeZc(record_origin.getInfrastructureFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                default:
                    throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
            }

            //添加修改人
            record_origin.setModifierId(budgetAdjustDto.getApproverId());
            String update = this.update(record_origin);

            //修改作为子项目的主项目预算记录
            RdmsBudgetResearchDto record_notRoot = this.getRecordBySubProjectIdAndStage(budgetAdjustDto.getProjectId(), BudgetTypeEnum.SUB_PROJECT);
            if (!ObjectUtils.isEmpty(record_notRoot)) {
                //对主项目特定的预算科目进行调整
                switch (Objects.requireNonNull(budgetClassifyEnum)) {
                    case EQUIPMENT:
                        record_notRoot.setEquipmentFeeApprovedZc(record_notRoot.getEquipmentFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                        record_notRoot.setEquipmentFeeZc(record_notRoot.getEquipmentFeeZc().add(budgetAdjustDto.getAmount()));
                        break;
                    case MATERIAL:
                        record_notRoot.setMaterialFeeApprovedZc(record_notRoot.getMaterialFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                        record_notRoot.setMaterialFeeZc(record_notRoot.getMaterialFeeZc().add(budgetAdjustDto.getAmount()));
                        break;
                    case TEST:
                        record_notRoot.setTestFeeApprovedZc(record_notRoot.getTestFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                        record_notRoot.setTestFeeZc(record_notRoot.getTestFeeZc().add(budgetAdjustDto.getAmount()));
                        break;
                    case POWER:
                        record_notRoot.setPowerFeeApprovedZc(record_notRoot.getPowerFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                        record_notRoot.setPowerFeeZc(record_notRoot.getPowerFeeZc().add(budgetAdjustDto.getAmount()));
                        break;
                    case CONFERENCE:
                        record_notRoot.setConferenceFeeApprovedZc(record_notRoot.getConferenceFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                        record_notRoot.setConferenceFeeZc(record_notRoot.getConferenceFeeZc().add(budgetAdjustDto.getAmount()));
                        break;
                    case BUSINESS:
                        record_notRoot.setBusinessFeeApprovedZc(record_notRoot.getBusinessFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                        record_notRoot.setBusinessFeeZc(record_notRoot.getBusinessFeeZc().add(budgetAdjustDto.getAmount()));
                        break;
                    case COOPERATION:
                        record_notRoot.setCooperationFeeApprovedZc(record_notRoot.getCooperationFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                        record_notRoot.setCooperationFeeZc(record_notRoot.getCooperationFeeZc().add(budgetAdjustDto.getAmount()));
                        break;
                    case PROPERTY:
                        record_notRoot.setPropertyFeeApprovedZc(record_notRoot.getPropertyFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                        record_notRoot.setPropertyFeeZc(record_notRoot.getPropertyFeeZc().add(budgetAdjustDto.getAmount()));
                        break;
                    case CONSULTING:
                        record_notRoot.setConsultingFeeApprovedZc(record_notRoot.getConsultingFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                        record_notRoot.setConsultingFeeZc(record_notRoot.getConsultingFeeZc().add(budgetAdjustDto.getAmount()));
                        break;
                    case OTHER:
                        record_notRoot.setOtherFeeApprovedZc(record_notRoot.getOtherFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                        record_notRoot.setOtherFeeZc(record_notRoot.getOtherFeeZc().add(budgetAdjustDto.getAmount()));
                        break;
                    case LABOR:
                        record_notRoot.setLaborFeeApprovedZc(record_notRoot.getLaborFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                        record_notRoot.setLaborFeeZc(record_notRoot.getLaborFeeZc().add(budgetAdjustDto.getAmount()));
                        break;
                    case MANAGEMENT:
                        record_notRoot.setManagementFeeApprovedZc(record_notRoot.getManagementFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                        record_notRoot.setManagementFeeZc(record_notRoot.getManagementFeeZc().add(budgetAdjustDto.getAmount()));
                        break;
                    case PERFORMANCE:
                        record_notRoot.setPerformanceFeeApprovedZc(record_notRoot.getPerformanceFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                        record_notRoot.setPerformanceFeeZc(record_notRoot.getPerformanceFeeZc().add(budgetAdjustDto.getAmount()));
                        break;
                    case STAFF:
                        record_notRoot.setStaffFeeApprovedZc(record_notRoot.getStaffFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                        record_notRoot.setStaffFeeZc(record_notRoot.getStaffFeeZc().add(budgetAdjustDto.getAmount()));
                        break;
                    case INFRASTRUCTURE:
                        record_notRoot.setInfrastructureFeeApprovedZc(record_notRoot.getInfrastructureFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                        record_notRoot.setInfrastructureFeeZc(record_notRoot.getInfrastructureFeeZc().add(budgetAdjustDto.getAmount()));
                        break;
                    default:
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                }
                //添加修改人
                record_notRoot.setModifierId(budgetAdjustDto.getApproverId());
                this.update(record_notRoot);
            }

            //修改主项目总预算
            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(budgetAdjustDto.getProjectId());
            rdmsProject.setAddCharge(rdmsProject.getAddCharge().add(budgetAdjustDto.getAmount()));
            rdmsProjectService.update(rdmsProject);
            //修改作为子项目的主项目总预算
            RdmsProjectSubproject rdmsProjectSubproject = rdmsSubprojectService.selectByPrimaryKey(budgetAdjustDto.getProjectId());
            rdmsProjectSubproject.setAddCharge(rdmsProjectSubproject.getAddCharge().add(budgetAdjustDto.getAmount()));
            rdmsSubprojectService.update(rdmsProjectSubproject);

            return update;
        } else {
            return null;
        }
    }

    public String adjustProductSubjectBudget(@NotNull RdmsBudgetAdjustDto budgetAdjustDto) {
        //更新预算记录
        RdmsBudgetResearchDto record_origin = this.getRecordByProjectIdAndStage(budgetAdjustDto.getProjectId(), BudgetTypeEnum.PRODUCT);

        if (!ObjectUtils.isEmpty(record_origin) && record_origin.getHasRecord()) {
            //对主项目特定的预算科目进行调整
            BudgetClassifyStdEnum budgetClassifyEnum = BudgetClassifyStdEnum.getBudgetClassifyEnumByClassify(budgetAdjustDto.getBudgetSubject());
            switch (Objects.requireNonNull(budgetClassifyEnum)) {
                case EQUIPMENT:
                    record_origin.setEquipmentFeeApprovedZc(record_origin.getEquipmentFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setEquipmentFeeZc(record_origin.getEquipmentFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case MATERIAL:
                    record_origin.setMaterialFeeApprovedZc(record_origin.getMaterialFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setMaterialFeeZc(record_origin.getMaterialFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case TEST:
                    record_origin.setTestFeeApprovedZc(record_origin.getTestFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setTestFeeZc(record_origin.getTestFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case POWER:
                    record_origin.setPowerFeeApprovedZc(record_origin.getPowerFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setPowerFeeZc(record_origin.getPowerFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case CONFERENCE:
                    record_origin.setConferenceFeeApprovedZc(record_origin.getConferenceFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setConferenceFeeZc(record_origin.getConferenceFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case BUSINESS:
                    record_origin.setBusinessFeeApprovedZc(record_origin.getBusinessFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setBusinessFeeZc(record_origin.getBusinessFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case COOPERATION:
                    record_origin.setCooperationFeeApprovedZc(record_origin.getCooperationFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setCooperationFeeZc(record_origin.getCooperationFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case PROPERTY:
                    record_origin.setPropertyFeeApprovedZc(record_origin.getPropertyFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setPropertyFeeZc(record_origin.getPropertyFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case CONSULTING:
                    record_origin.setConsultingFeeApprovedZc(record_origin.getConsultingFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setConsultingFeeZc(record_origin.getConsultingFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case OTHER:
                    record_origin.setOtherFeeApprovedZc(record_origin.getOtherFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setOtherFeeZc(record_origin.getOtherFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case LABOR:
                    record_origin.setLaborFeeApprovedZc(record_origin.getLaborFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setLaborFeeZc(record_origin.getLaborFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case MANAGEMENT:
                    record_origin.setManagementFeeApprovedZc(record_origin.getManagementFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setManagementFeeZc(record_origin.getManagementFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case PERFORMANCE:
                    record_origin.setPerformanceFeeApprovedZc(record_origin.getPerformanceFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setPerformanceFeeZc(record_origin.getPerformanceFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case STAFF:
                    record_origin.setStaffFeeApprovedZc(record_origin.getStaffFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setStaffFeeZc(record_origin.getStaffFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case INFRASTRUCTURE:
                    record_origin.setInfrastructureFeeApprovedZc(record_origin.getInfrastructureFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setInfrastructureFeeZc(record_origin.getInfrastructureFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                default:
                    throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
            }

            //添加修改人
            record_origin.setModifierId(budgetAdjustDto.getApproverId());
            String update = this.update(record_origin);

            //修改作为子项目的主项目预算记录
            RdmsBudgetResearchDto record_notRoot = this.getRecordBySubProjectIdAndStage(budgetAdjustDto.getProjectId(), BudgetTypeEnum.SUB_PROJECT);
            if(ObjectUtils.isEmpty(record_notRoot.getProjectId())){
                //创建对应主项目的子项目记录
                RdmsBudgetResearch budgetResearch = this.selectByPrimaryKey(update);
                RdmsBudgetResearch budgetResearchCopy = CopyUtil.copy(budgetResearch, RdmsBudgetResearch.class);
                budgetResearchCopy.setId(null);
                budgetResearchCopy.setParentId(budgetResearch.getProjectId());
                budgetResearchCopy.setSubprojectId(budgetResearch.getProjectId());
                budgetResearchCopy.setStage(BudgetTypeEnum.SUB_PROJECT.getType());   //任然使用SUB_project
                budgetResearchCopy.setIsRoot(0);
                String save = this.save(budgetResearchCopy);

                RdmsBudgetResearch budgetResearch1 = this.selectByPrimaryKey(save);
                record_notRoot = CopyUtil.copy(budgetResearch1, RdmsBudgetResearchDto.class);
            }

            if (!ObjectUtils.isEmpty(record_notRoot)) {
                //对主项目特定的预算科目进行调整
                switch (Objects.requireNonNull(budgetClassifyEnum)) {
                    case EQUIPMENT:
                        record_notRoot.setEquipmentFeeApprovedZc(record_notRoot.getEquipmentFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                        record_notRoot.setEquipmentFeeZc(record_notRoot.getEquipmentFeeZc().add(budgetAdjustDto.getAmount()));
                        break;
                    case MATERIAL:
                        record_notRoot.setMaterialFeeApprovedZc(record_notRoot.getMaterialFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                        record_notRoot.setMaterialFeeZc(record_notRoot.getMaterialFeeZc().add(budgetAdjustDto.getAmount()));
                        break;
                    case TEST:
                        record_notRoot.setTestFeeApprovedZc(record_notRoot.getTestFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                        record_notRoot.setTestFeeZc(record_notRoot.getTestFeeZc().add(budgetAdjustDto.getAmount()));
                        break;
                    case POWER:
                        record_notRoot.setPowerFeeApprovedZc(record_notRoot.getPowerFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                        record_notRoot.setPowerFeeZc(record_notRoot.getPowerFeeZc().add(budgetAdjustDto.getAmount()));
                        break;
                    case CONFERENCE:
                        record_notRoot.setConferenceFeeApprovedZc(record_notRoot.getConferenceFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                        record_notRoot.setConferenceFeeZc(record_notRoot.getConferenceFeeZc().add(budgetAdjustDto.getAmount()));
                        break;
                    case BUSINESS:
                        record_notRoot.setBusinessFeeApprovedZc(record_notRoot.getBusinessFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                        record_notRoot.setBusinessFeeZc(record_notRoot.getBusinessFeeZc().add(budgetAdjustDto.getAmount()));
                        break;
                    case COOPERATION:
                        record_notRoot.setCooperationFeeApprovedZc(record_notRoot.getCooperationFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                        record_notRoot.setCooperationFeeZc(record_notRoot.getCooperationFeeZc().add(budgetAdjustDto.getAmount()));
                        break;
                    case PROPERTY:
                        record_notRoot.setPropertyFeeApprovedZc(record_notRoot.getPropertyFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                        record_notRoot.setPropertyFeeZc(record_notRoot.getPropertyFeeZc().add(budgetAdjustDto.getAmount()));
                        break;
                    case CONSULTING:
                        record_notRoot.setConsultingFeeApprovedZc(record_notRoot.getConsultingFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                        record_notRoot.setConsultingFeeZc(record_notRoot.getConsultingFeeZc().add(budgetAdjustDto.getAmount()));
                        break;
                    case OTHER:
                        record_notRoot.setOtherFeeApprovedZc(record_notRoot.getOtherFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                        record_notRoot.setOtherFeeZc(record_notRoot.getOtherFeeZc().add(budgetAdjustDto.getAmount()));
                        break;
                    case LABOR:
                        record_notRoot.setLaborFeeApprovedZc(record_notRoot.getLaborFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                        record_notRoot.setLaborFeeZc(record_notRoot.getLaborFeeZc().add(budgetAdjustDto.getAmount()));
                        break;
                    case MANAGEMENT:
                        record_notRoot.setManagementFeeApprovedZc(record_notRoot.getManagementFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                        record_notRoot.setManagementFeeZc(record_notRoot.getManagementFeeZc().add(budgetAdjustDto.getAmount()));
                        break;
                    case PERFORMANCE:
                        record_notRoot.setPerformanceFeeApprovedZc(record_notRoot.getPerformanceFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                        record_notRoot.setPerformanceFeeZc(record_notRoot.getPerformanceFeeZc().add(budgetAdjustDto.getAmount()));
                        break;
                    case STAFF:
                        record_notRoot.setStaffFeeApprovedZc(record_notRoot.getStaffFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                        record_notRoot.setStaffFeeZc(record_notRoot.getStaffFeeZc().add(budgetAdjustDto.getAmount()));
                        break;
                    case INFRASTRUCTURE:
                        record_notRoot.setInfrastructureFeeApprovedZc(record_notRoot.getInfrastructureFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                        record_notRoot.setInfrastructureFeeZc(record_notRoot.getInfrastructureFeeZc().add(budgetAdjustDto.getAmount()));
                        break;
                    default:
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                }
                //添加修改人
                record_notRoot.setModifierId(budgetAdjustDto.getApproverId());
                this.update(record_notRoot);
            }

            //修改主项目总预算
            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(budgetAdjustDto.getProjectId());
            rdmsProject.setAddCharge(rdmsProject.getAddCharge().add(budgetAdjustDto.getAmount()));
            rdmsProjectService.update(rdmsProject);
            //修改作为子项目的主项目总预算
            RdmsProjectSubproject rdmsProjectSubproject = rdmsSubprojectService.selectByPrimaryKey(budgetAdjustDto.getProjectId());
            rdmsProjectSubproject.setAddCharge(rdmsProjectSubproject.getAddCharge().add(budgetAdjustDto.getAmount()));
            rdmsSubprojectService.update(rdmsProjectSubproject);

            return update;
        } else {
            return null;
        }
    }

    public String adjustPreProjectSubjectBudget(@NotNull RdmsBudgetAdjustDto budgetAdjustDto) {
        if(budgetAdjustDto.getPreProjectId() == null){
            LOG.error("预申请项目id为空, 方法名称:adjustPreProjectSubjectBudget");
            throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
        }
        //更新预算记录
        RdmsBudgetResearchDto record_origin = this.getRecordByPreProjectIdAndStage(budgetAdjustDto.getPreProjectId());

        if (!ObjectUtils.isEmpty(record_origin) && record_origin.getHasRecord()) {
            //对主项目特定的预算科目进行调整
            BudgetClassifyStdEnum budgetClassifyEnum = BudgetClassifyStdEnum.getBudgetClassifyEnumByClassify(budgetAdjustDto.getBudgetSubject());
            switch (Objects.requireNonNull(budgetClassifyEnum)) {
                case EQUIPMENT:
                    record_origin.setEquipmentFeeApprovedZc(record_origin.getEquipmentFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setEquipmentFeeZc(record_origin.getEquipmentFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case MATERIAL:
                    record_origin.setMaterialFeeApprovedZc(record_origin.getMaterialFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setMaterialFeeZc(record_origin.getMaterialFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case TEST:
                    record_origin.setTestFeeApprovedZc(record_origin.getTestFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setTestFeeZc(record_origin.getTestFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case POWER:
                    record_origin.setPowerFeeApprovedZc(record_origin.getPowerFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setPowerFeeZc(record_origin.getPowerFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case CONFERENCE:
                    record_origin.setConferenceFeeApprovedZc(record_origin.getConferenceFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setConferenceFeeZc(record_origin.getConferenceFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case BUSINESS:
                    record_origin.setBusinessFeeApprovedZc(record_origin.getBusinessFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setBusinessFeeZc(record_origin.getBusinessFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case COOPERATION:
                    record_origin.setCooperationFeeApprovedZc(record_origin.getCooperationFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setCooperationFeeZc(record_origin.getCooperationFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case PROPERTY:
                    record_origin.setPropertyFeeApprovedZc(record_origin.getPropertyFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setPropertyFeeZc(record_origin.getPropertyFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case CONSULTING:
                    record_origin.setConsultingFeeApprovedZc(record_origin.getConsultingFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setConsultingFeeZc(record_origin.getConsultingFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case OTHER:
                    record_origin.setOtherFeeApprovedZc(record_origin.getOtherFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setOtherFeeZc(record_origin.getOtherFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case LABOR:
                    record_origin.setLaborFeeApprovedZc(record_origin.getLaborFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setLaborFeeZc(record_origin.getLaborFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case MANAGEMENT:
                    record_origin.setManagementFeeApprovedZc(record_origin.getManagementFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setManagementFeeZc(record_origin.getManagementFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case PERFORMANCE:
                    record_origin.setPerformanceFeeApprovedZc(record_origin.getPerformanceFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setPerformanceFeeZc(record_origin.getPerformanceFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case STAFF:
                    record_origin.setStaffFeeApprovedZc(record_origin.getStaffFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setStaffFeeZc(record_origin.getStaffFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                case INFRASTRUCTURE:
                    record_origin.setInfrastructureFeeApprovedZc(record_origin.getInfrastructureFeeApprovedZc().add(budgetAdjustDto.getAmount()));
                    record_origin.setInfrastructureFeeZc(record_origin.getInfrastructureFeeZc().add(budgetAdjustDto.getAmount()));
                    break;
                default:
                    throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
            }

            //添加修改人
            record_origin.setModifierId(budgetAdjustDto.getApproverId());
            String update = this.update(record_origin);

            //修改预立项项目总预算
            RdmsBudget budgetItemByPreProjectId = rdmsBudgetService.getBudgetItemByPreProjectId(budgetAdjustDto.getPreProjectId());
            budgetItemByPreProjectId.setBudget(budgetItemByPreProjectId.getBudget().add(budgetAdjustDto.getAmount()));
            rdmsBudgetService.update(budgetItemByPreProjectId);

            return update;
        } else {
            return null;
        }
    }

    /**
     * 向已有项目中追加功能时, 会造成Budget的增加追加
     * 单位为 元
     *
     * @param customerId
     * @param subprojectId
     * @param modifierId
     * @param incrementBudgetData
     * @return
     */
    public String incrementSubprojectBudget(String customerId, String subprojectId, String modifierId, RdmsBudgetResearchDto incrementBudgetData) {

        //更新预算记录
        RdmsBudgetResearchDto record_origin = this.getRecordByCustomerIdAndSubprojectId(customerId, subprojectId);
        if (!ObjectUtils.isEmpty(record_origin) && record_origin.getHasRecord()) {
            RdmsBudgetResearch record_new = CopyUtil.copy(record_origin, RdmsBudgetResearch.class);
            this.delete(record_origin.getId());

            record_new.setId(null);
            record_new.setProjectId(record_origin.getProjectId());
            record_new.setParentId(record_origin.getParentId());
            record_new.setCreaterId(record_origin.getCreaterId());
            record_new.setModifierId(modifierId);
            record_new.setCreateTime(record_origin.getCreateTime());
            record_new.setUpdateTime(new Date());

            //注意 单位为 元
            record_new.setEquipmentFeeBt(record_origin.getEquipmentFeeBt().add(incrementBudgetData.getEquipmentFeeBt()));
            record_new.setEquipmentFeeZc(record_origin.getEquipmentFeeZc().add(incrementBudgetData.getEquipmentFeeZc()));

            record_new.setTestFeeBt(record_origin.getTestFeeBt().add(incrementBudgetData.getTestFeeBt()));
            record_new.setTestFeeZc(record_origin.getTestFeeZc().add(incrementBudgetData.getTestFeeZc()));

            record_new.setMaterialFeeBt(record_origin.getMaterialFeeBt().add(incrementBudgetData.getMaterialFeeBt()));
            record_new.setMaterialFeeZc(record_origin.getMaterialFeeZc().add(incrementBudgetData.getMaterialFeeZc()));

            record_new.setPowerFeeBt(record_origin.getPowerFeeBt().add(incrementBudgetData.getPowerFeeBt()));
            record_new.setPowerFeeZc(record_origin.getPowerFeeZc().add(incrementBudgetData.getPowerFeeZc()));

            record_new.setConferenceFeeBt(record_origin.getConferenceFeeBt().add(incrementBudgetData.getConferenceFeeBt()));
            record_new.setConferenceFeeZc(record_origin.getConferenceFeeZc().add(incrementBudgetData.getConferenceFeeZc()));

            record_new.setBusinessFeeBt(record_origin.getBusinessFeeBt().add(incrementBudgetData.getBusinessFeeBt()));
            record_new.setBusinessFeeZc(record_origin.getBusinessFeeZc().add(incrementBudgetData.getBusinessFeeZc()));

            record_new.setCooperationFeeBt(record_origin.getCooperationFeeBt().add(incrementBudgetData.getCooperationFeeBt()));
            record_new.setCooperationFeeZc(record_origin.getCooperationFeeZc().add(incrementBudgetData.getCooperationFeeZc()));

            record_new.setPropertyFeeBt(record_origin.getPropertyFeeBt().add(incrementBudgetData.getPropertyFeeBt()));
            record_new.setPropertyFeeZc(record_origin.getPropertyFeeZc().add(incrementBudgetData.getPropertyFeeZc()));

            record_new.setLaborFeeBt(record_origin.getLaborFeeBt().add(incrementBudgetData.getLaborFeeBt()));
            record_new.setLaborFeeZc(record_origin.getLaborFeeZc().add(incrementBudgetData.getLaborFeeZc()));

            record_new.setStaffFeeBt(record_origin.getStaffFeeBt().add(incrementBudgetData.getStaffFeeBt()));
            record_new.setStaffFeeZc(record_origin.getStaffFeeZc().add(incrementBudgetData.getStaffFeeZc()));

            record_new.setConsultingFeeBt(record_origin.getConsultingFeeBt().add(incrementBudgetData.getConsultingFeeBt()));
            record_new.setConsultingFeeZc(record_origin.getConsultingFeeZc().add(incrementBudgetData.getConsultingFeeZc()));

            record_new.setManagementFeeBt(record_origin.getManagementFeeBt().add(incrementBudgetData.getManagementFeeBt()));
            record_new.setManagementFeeZc(record_origin.getManagementFeeZc().add(incrementBudgetData.getManagementFeeZc()));

            record_new.setOtherFeeBt(record_origin.getOtherFeeBt().add(incrementBudgetData.getOtherFeeBt()));
            record_new.setOtherFeeZc(record_origin.getOtherFeeZc().add(incrementBudgetData.getOtherFeeZc()));

            record_new.setPerformanceFeeBt(record_origin.getPerformanceFeeBt().add(incrementBudgetData.getPerformanceFeeBt()));
            record_new.setPerformanceFeeZc(record_origin.getPerformanceFeeZc().add(incrementBudgetData.getPerformanceFeeZc()));

            record_new.setInfrastructureFeeBt(record_origin.getInfrastructureFeeBt().add(incrementBudgetData.getInfrastructureFeeBt()));
            record_new.setInfrastructureFeeZc(record_origin.getInfrastructureFeeZc().add(incrementBudgetData.getInfrastructureFeeZc()));

            String newId = this.save(record_new);

            //重新计算调整差值
            BigDecimal itemBudget_origin = this.getItemBudgetOfSubproject(record_origin);
            BigDecimal itemBudget_new = this.getItemBudgetOfSubproject(record_new);
            BigDecimal adjustBudget = itemBudget_origin.subtract(itemBudget_new);

            //修改当前subproject的addChange
            RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(subprojectId);
            subproject.setBudget(subproject.getBudget().subtract(adjustBudget));
//            if(ObjectUtils.isEmpty(subproject.getBudget()) || subproject.getBudget().doubleValue()==0.0){
//                subproject.setChargeRate(BigDecimal.ZERO);
//            }else{
//                subproject.setChargeRate(subproject.getAddCharge().divide(subproject.getBudget().add(subproject.getAddCharge()), 4, RoundingMode.HALF_UP));
//            }
            RdmsProjectSubproject save = rdmsSubprojectService.save(subproject);

            //修改project的addChange
            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(subproject.getProjectId());
            rdmsProject.setBudget(rdmsProject.getBudget().subtract(adjustBudget));
//            if(ObjectUtils.isEmpty(rdmsProject.getBudget()) || rdmsProject.getBudget().doubleValue()==0.0){
//                rdmsProject.setChargeRate(BigDecimal.ZERO);
//            }else{
//                rdmsProject.setChargeRate(rdmsProject.getAddCharge().divide(rdmsProject.getBudget().add(rdmsProject.getAddCharge()), 4, RoundingMode.HALF_UP));
//            }
            rdmsProjectService.update(rdmsProject);

            return newId;
        } else {
            return null;
        }
    }

    /**
     * 向子项目中分配功能
     * 单位为 元
     *
     * @param customerId
     * @param parentProjectId
     * @param targetProjectId
     * @param incrementBudgetData
     * @return
     */
    @Transactional
    public void budgetAdjustForDistributeCharacter(String customerId, String parentProjectId, String targetProjectId, RdmsBudgetResearchDto incrementBudgetData) {
        //更新预算记录


        RdmsBudgetResearchDto record_parent = this.getRecordByCustomerIdAndSubprojectId(customerId, parentProjectId);
        RdmsBudgetResearchDto record_target = this.getRecordByCustomerIdAndSubprojectId(customerId, targetProjectId);

        BigDecimal itemBudget_parent_origin = this.getItemBudgetOfSubproject(record_parent);
        BigDecimal itemBudget_target_origin = this.getItemBudgetOfSubproject(record_target);

        {
            //注意 单位为 元
            record_parent.setEquipmentFeeBt(record_parent.getEquipmentFeeBt().subtract(incrementBudgetData.getEquipmentFeeBt()));
            record_parent.setEquipmentFeeZc(record_parent.getEquipmentFeeZc().subtract(incrementBudgetData.getEquipmentFeeZc()));
            record_target.setEquipmentFeeBt(record_target.getEquipmentFeeBt().add(incrementBudgetData.getEquipmentFeeBt()));
            record_target.setEquipmentFeeZc(record_target.getEquipmentFeeZc().add(incrementBudgetData.getEquipmentFeeZc()));


            record_parent.setTestFeeBt(record_parent.getTestFeeBt().subtract(incrementBudgetData.getTestFeeBt()));
            record_parent.setTestFeeZc(record_parent.getTestFeeZc().subtract(incrementBudgetData.getTestFeeZc()));
            record_target.setTestFeeBt(record_target.getTestFeeBt().add(incrementBudgetData.getTestFeeBt()));
            record_target.setTestFeeZc(record_target.getTestFeeZc().add(incrementBudgetData.getTestFeeZc()));

            record_parent.setMaterialFeeBt(record_parent.getMaterialFeeBt().subtract(incrementBudgetData.getMaterialFeeBt()));
            record_parent.setMaterialFeeZc(record_parent.getMaterialFeeZc().subtract(incrementBudgetData.getMaterialFeeZc()));
            record_target.setMaterialFeeBt(record_target.getMaterialFeeBt().add(incrementBudgetData.getMaterialFeeBt()));
            record_target.setMaterialFeeZc(record_target.getMaterialFeeZc().add(incrementBudgetData.getMaterialFeeZc()));

            record_parent.setPowerFeeBt(record_parent.getPowerFeeBt().subtract(incrementBudgetData.getPowerFeeBt()));
            record_parent.setPowerFeeZc(record_parent.getPowerFeeZc().subtract(incrementBudgetData.getPowerFeeZc()));
            record_target.setPowerFeeBt(record_target.getPowerFeeBt().add(incrementBudgetData.getPowerFeeBt()));
            record_target.setPowerFeeZc(record_target.getPowerFeeZc().add(incrementBudgetData.getPowerFeeZc()));

            record_parent.setConferenceFeeBt(record_parent.getConferenceFeeBt().subtract(incrementBudgetData.getConferenceFeeBt()));
            record_parent.setConferenceFeeZc(record_parent.getConferenceFeeZc().subtract(incrementBudgetData.getConferenceFeeZc()));
            record_target.setConferenceFeeBt(record_target.getConferenceFeeBt().add(incrementBudgetData.getConferenceFeeBt()));
            record_target.setConferenceFeeZc(record_target.getConferenceFeeZc().add(incrementBudgetData.getConferenceFeeZc()));

            record_parent.setBusinessFeeBt(record_parent.getBusinessFeeBt().subtract(incrementBudgetData.getBusinessFeeBt()));
            record_parent.setBusinessFeeZc(record_parent.getBusinessFeeZc().subtract(incrementBudgetData.getBusinessFeeZc()));
            record_target.setBusinessFeeBt(record_target.getBusinessFeeBt().add(incrementBudgetData.getBusinessFeeBt()));
            record_target.setBusinessFeeZc(record_target.getBusinessFeeZc().add(incrementBudgetData.getBusinessFeeZc()));

            record_parent.setCooperationFeeBt(record_parent.getCooperationFeeBt().subtract(incrementBudgetData.getCooperationFeeBt()));
            record_parent.setCooperationFeeZc(record_parent.getCooperationFeeZc().subtract(incrementBudgetData.getCooperationFeeZc()));
            record_target.setCooperationFeeBt(record_target.getCooperationFeeBt().add(incrementBudgetData.getCooperationFeeBt()));
            record_target.setCooperationFeeZc(record_target.getCooperationFeeZc().add(incrementBudgetData.getCooperationFeeZc()));

            record_parent.setPropertyFeeBt(record_parent.getPropertyFeeBt().subtract(incrementBudgetData.getPropertyFeeBt()));
            record_parent.setPropertyFeeZc(record_parent.getPropertyFeeZc().subtract(incrementBudgetData.getPropertyFeeZc()));
            record_target.setPropertyFeeBt(record_target.getPropertyFeeBt().add(incrementBudgetData.getPropertyFeeBt()));
            record_target.setPropertyFeeZc(record_target.getPropertyFeeZc().add(incrementBudgetData.getPropertyFeeZc()));

            record_parent.setLaborFeeBt(record_parent.getLaborFeeBt().subtract(incrementBudgetData.getLaborFeeBt()));
            record_parent.setLaborFeeZc(record_parent.getLaborFeeZc().subtract(incrementBudgetData.getLaborFeeZc()));
            record_target.setLaborFeeBt(record_target.getLaborFeeBt().add(incrementBudgetData.getLaborFeeBt()));
            record_target.setLaborFeeZc(record_target.getLaborFeeZc().add(incrementBudgetData.getLaborFeeZc()));

            record_parent.setStaffFeeBt(record_parent.getStaffFeeBt().subtract(incrementBudgetData.getStaffFeeBt()));
            record_parent.setStaffFeeZc(record_parent.getStaffFeeZc().subtract(incrementBudgetData.getStaffFeeZc()));
            record_target.setStaffFeeBt(record_target.getStaffFeeBt().add(incrementBudgetData.getStaffFeeBt()));
            record_target.setStaffFeeZc(record_target.getStaffFeeZc().add(incrementBudgetData.getStaffFeeZc()));

            record_parent.setConsultingFeeBt(record_parent.getConsultingFeeBt().subtract(incrementBudgetData.getConsultingFeeBt()));
            record_parent.setConsultingFeeZc(record_parent.getConsultingFeeZc().subtract(incrementBudgetData.getConsultingFeeZc()));
            record_target.setConsultingFeeBt(record_target.getConsultingFeeBt().add(incrementBudgetData.getConsultingFeeBt()));
            record_target.setConsultingFeeZc(record_target.getConsultingFeeZc().add(incrementBudgetData.getConsultingFeeZc()));

            record_parent.setManagementFeeBt(record_parent.getManagementFeeBt().subtract(incrementBudgetData.getManagementFeeBt()));
            record_parent.setManagementFeeZc(record_parent.getManagementFeeZc().subtract(incrementBudgetData.getManagementFeeZc()));
            record_target.setManagementFeeBt(record_target.getManagementFeeBt().add(incrementBudgetData.getManagementFeeBt()));
            record_target.setManagementFeeZc(record_target.getManagementFeeZc().add(incrementBudgetData.getManagementFeeZc()));

            record_parent.setOtherFeeBt(record_parent.getOtherFeeBt().subtract(incrementBudgetData.getOtherFeeBt()));
            record_parent.setOtherFeeZc(record_parent.getOtherFeeZc().subtract(incrementBudgetData.getOtherFeeZc()));
            record_target.setOtherFeeBt(record_target.getOtherFeeBt().add(incrementBudgetData.getOtherFeeBt()));
            record_target.setOtherFeeZc(record_target.getOtherFeeZc().add(incrementBudgetData.getOtherFeeZc()));

            record_parent.setPerformanceFeeBt(record_parent.getPerformanceFeeBt().subtract(incrementBudgetData.getPerformanceFeeBt()));
            record_parent.setPerformanceFeeZc(record_parent.getPerformanceFeeZc().subtract(incrementBudgetData.getPerformanceFeeZc()));
            record_target.setPerformanceFeeBt(record_target.getPerformanceFeeBt().add(incrementBudgetData.getPerformanceFeeBt()));
            record_target.setPerformanceFeeZc(record_target.getPerformanceFeeZc().add(incrementBudgetData.getPerformanceFeeZc()));

            record_parent.setInfrastructureFeeBt(record_parent.getInfrastructureFeeBt().subtract(incrementBudgetData.getInfrastructureFeeBt()));
            record_parent.setInfrastructureFeeZc(record_parent.getInfrastructureFeeZc().subtract(incrementBudgetData.getInfrastructureFeeZc()));
            record_target.setInfrastructureFeeBt(record_target.getInfrastructureFeeBt().add(incrementBudgetData.getInfrastructureFeeBt()));
            record_target.setInfrastructureFeeZc(record_target.getInfrastructureFeeZc().add(incrementBudgetData.getInfrastructureFeeZc()));

            this.save(record_parent);
            this.save(record_target);

            //重新计算调整差值
            BigDecimal itemBudget_target = this.getItemBudgetOfSubproject(record_target);
            BigDecimal adjustBudget_target = itemBudget_target.subtract(itemBudget_target_origin);
            //修改当前subproject的addChange
            RdmsProjectSubproject subproject_target = rdmsSubprojectService.selectByPrimaryKey(targetProjectId);
            subproject_target.setBudget(subproject_target.getBudget().add(adjustBudget_target));
//            if(ObjectUtils.isEmpty(subproject_target.getBudget()) || subproject_target.getBudget().doubleValue()==0.0){
//                subproject_target.setChargeRate(BigDecimal.ZERO);
//            }else {
//                subproject_target.setChargeRate(subproject_target.getAddCharge().divide(subproject_target.getBudget().add(subproject_target.getAddCharge()), 4, RoundingMode.HALF_UP));
//            }
            rdmsSubprojectService.save(subproject_target);

            BigDecimal itemBudget_parent = this.getItemBudgetOfSubproject(record_parent);
            BigDecimal adjustBudget_parent = itemBudget_parent.subtract(itemBudget_parent_origin);
            //修改project的addChange
            RdmsProjectSubproject subproject_parent = rdmsSubprojectService.selectByPrimaryKey(parentProjectId);
            subproject_parent.setBudget(subproject_parent.getBudget().add(adjustBudget_parent));
//            if(ObjectUtils.isEmpty(subproject_parent.getBudget()) || subproject_parent.getBudget().doubleValue()==0.0){
//                subproject_parent.setChargeRate(BigDecimal.ZERO);
//            }else{
//                subproject_parent.setChargeRate(subproject_parent.getAddCharge().divide(subproject_parent.getBudget().add(subproject_parent.getAddCharge()), 4, RoundingMode.HALF_UP));
//            }
            rdmsSubprojectService.save(subproject_parent);
        }
    }

    public BigDecimal getItemBudgetOfSubproject(String subprojectId) {
        RdmsBudgetResearch budgetBySubprojectId = this.getBudgetBySubprojectId(subprojectId);
        return budgetBySubprojectId.getEquipmentFeeBt()
                .add(budgetBySubprojectId.getEquipmentFeeZc())
                .add(budgetBySubprojectId.getTestFeeBt())
                .add(budgetBySubprojectId.getTestFeeZc())
                .add(budgetBySubprojectId.getMaterialFeeBt())
                .add(budgetBySubprojectId.getMaterialFeeZc())
                .add(budgetBySubprojectId.getPowerFeeBt())
                .add(budgetBySubprojectId.getPowerFeeZc())
                .add(budgetBySubprojectId.getConferenceFeeBt())
                .add(budgetBySubprojectId.getConferenceFeeZc())
                .add(budgetBySubprojectId.getBusinessFeeBt())
                .add(budgetBySubprojectId.getBusinessFeeZc())
                .add(budgetBySubprojectId.getCooperationFeeBt())
                .add(budgetBySubprojectId.getCooperationFeeZc())
                .add(budgetBySubprojectId.getPropertyFeeBt())
                .add(budgetBySubprojectId.getPropertyFeeZc())
                .add(budgetBySubprojectId.getLaborFeeBt())
                .add(budgetBySubprojectId.getLaborFeeZc())
                .add(budgetBySubprojectId.getStaffFeeBt())
                .add(budgetBySubprojectId.getStaffFeeZc())
                .add(budgetBySubprojectId.getConsultingFeeBt())
                .add(budgetBySubprojectId.getConsultingFeeZc())
                .add(budgetBySubprojectId.getManagementFeeBt())
                .add(budgetBySubprojectId.getManagementFeeZc())
                .add(budgetBySubprojectId.getPerformanceFeeBt())
                .add(budgetBySubprojectId.getPerformanceFeeZc())
                .add(budgetBySubprojectId.getInfrastructureFeeBt())
                .add(budgetBySubprojectId.getInfrastructureFeeZc())
                .add(budgetBySubprojectId.getOtherFeeBt())
                .add(budgetBySubprojectId.getOtherFeeZc());
    }

    public BigDecimal getItemBudgetOfSubproject(RdmsBudgetResearch budgetItem) {
        return budgetItem.getEquipmentFeeBt()
                .add(budgetItem.getEquipmentFeeZc())
                .add(budgetItem.getTestFeeBt())
                .add(budgetItem.getTestFeeZc())
                .add(budgetItem.getMaterialFeeBt())
                .add(budgetItem.getMaterialFeeZc())
                .add(budgetItem.getPowerFeeBt())
                .add(budgetItem.getPowerFeeZc())
                .add(budgetItem.getConferenceFeeBt())
                .add(budgetItem.getConferenceFeeZc())
                .add(budgetItem.getBusinessFeeBt())
                .add(budgetItem.getBusinessFeeZc())
                .add(budgetItem.getCooperationFeeBt())
                .add(budgetItem.getCooperationFeeZc())
                .add(budgetItem.getPropertyFeeBt())
                .add(budgetItem.getPropertyFeeZc())
                .add(budgetItem.getLaborFeeBt())
                .add(budgetItem.getLaborFeeZc())
                .add(budgetItem.getStaffFeeBt())
                .add(budgetItem.getStaffFeeZc())
                .add(budgetItem.getConsultingFeeBt())
                .add(budgetItem.getConsultingFeeZc())
                .add(budgetItem.getManagementFeeBt())
                .add(budgetItem.getManagementFeeZc())
                .add(budgetItem.getPerformanceFeeBt())
                .add(budgetItem.getPerformanceFeeZc())
                .add(budgetItem.getInfrastructureFeeBt())
                .add(budgetItem.getInfrastructureFeeZc())
                .add(budgetItem.getOtherFeeBt())
                .add(budgetItem.getOtherFeeZc());
    }

    /**
     * 根据subprojectId获取子项目预算
     *
     * @param subprojectId
     */
    public RdmsBudgetResearch getBudgetBySubprojectId(String subprojectId) {
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            return rdmsBudgetResearches.get(0);
        } else {
            return null;
        }
    }

    @Data
    public static class ClassifyBudget {
        private BigDecimal approvedBudgetBt;
        private BigDecimal approvedBudgetZc;
        private BigDecimal approvedBudget;

        private BigDecimal budgetBt;
        private BigDecimal budgetZc;
        private BigDecimal budget;

        private String classifyName;
        private String classify;
    }

    /**
     * 获取子项目设备费预算
     *
     * @param subprojectId
     */
    public ClassifyBudget getEquipmentBudgetBySubprojectId(String subprojectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getEquipmentFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getEquipmentFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getEquipmentFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getEquipmentFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.EQUIPMENT.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.EQUIPMENT.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.EQUIPMENT.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.EQUIPMENT.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getEquipmentBudgetByPreProjectId(String preprojectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andPreprojectIdEqualTo(preprojectId)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getEquipmentFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getEquipmentFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getEquipmentFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getEquipmentFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.EQUIPMENT.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.EQUIPMENT.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.EQUIPMENT.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.EQUIPMENT.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getEquipmentBudgetByStage(String customerId, @NotNull BudgetTypeEnum stage) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andStageEqualTo(stage.getType())
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getEquipmentFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getEquipmentFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getEquipmentFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getEquipmentFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.EQUIPMENT.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.EQUIPMENT.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.EQUIPMENT.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.EQUIPMENT.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getEquipmentBudgetByCustomer(String customerId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        List<String> stageList = new ArrayList<>();
        stageList.add(BudgetTypeEnum.PRE_PROJECT.getType());
        stageList.add(BudgetTypeEnum.PROJECT.getType());
        stageList.add(BudgetTypeEnum.PRODUCT.getType());
        budgetExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andStageIn(stageList)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getEquipmentFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getEquipmentFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getEquipmentFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getEquipmentFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.EQUIPMENT.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.EQUIPMENT.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.EQUIPMENT.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.EQUIPMENT.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getTestBudgetByStage(String customerId, @NotNull BudgetTypeEnum stage) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andStageEqualTo(stage.getType())
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getTestFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getTestFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getTestFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getTestFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.TEST.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.TEST.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.TEST.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.TEST.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getTestBudgetByCustomer(String customerId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        List<String> stageList = new ArrayList<>();
        stageList.add(BudgetTypeEnum.PRE_PROJECT.getType());
        stageList.add(BudgetTypeEnum.PROJECT.getType());
        stageList.add(BudgetTypeEnum.PRODUCT.getType());
        budgetExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andStageIn(stageList)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getTestFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getTestFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getTestFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getTestFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.TEST.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.TEST.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.TEST.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.TEST.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getMaterialBudgetByStage(String customerId, @NotNull BudgetTypeEnum stage) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andStageEqualTo(stage.getType())
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getMaterialFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getMaterialFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getMaterialFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getMaterialFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.MATERIAL.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.MATERIAL.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.MATERIAL.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.MATERIAL.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getMaterialBudgetByCustomer(String customerId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        List<String> stageList = new ArrayList<>();
        stageList.add(BudgetTypeEnum.PRE_PROJECT.getType());
        stageList.add(BudgetTypeEnum.PROJECT.getType());
        stageList.add(BudgetTypeEnum.PRODUCT.getType());
        budgetExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andStageIn(stageList)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getMaterialFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getMaterialFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getMaterialFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getMaterialFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.MATERIAL.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.MATERIAL.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.MATERIAL.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.MATERIAL.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getPowerBudgetByStage(String customerId, @NotNull BudgetTypeEnum stage) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andStageEqualTo(stage.getType())
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getPowerFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getPowerFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getPowerFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getPowerFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.POWER.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.POWER.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.POWER.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.POWER.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getPowerBudgetByCustomer(String customerId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        List<String> stageList = new ArrayList<>();
        stageList.add(BudgetTypeEnum.PRE_PROJECT.getType());
        stageList.add(BudgetTypeEnum.PROJECT.getType());
        stageList.add(BudgetTypeEnum.PRODUCT.getType());
        budgetExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andStageIn(stageList)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getPowerFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getPowerFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getPowerFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getPowerFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.POWER.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.POWER.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.POWER.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.POWER.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getConferenceBudgetByStage(String customerId, @NotNull BudgetTypeEnum stage) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andStageEqualTo(stage.getType())
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getConferenceFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getConferenceFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getConferenceFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getConferenceFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.CONFERENCE.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.CONFERENCE.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.CONFERENCE.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.CONFERENCE.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getConferenceBudgetByCustomer(String customerId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        List<String> stageList = new ArrayList<>();
        stageList.add(BudgetTypeEnum.PRE_PROJECT.getType());
        stageList.add(BudgetTypeEnum.PROJECT.getType());
        stageList.add(BudgetTypeEnum.PRODUCT.getType());
        budgetExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andStageIn(stageList)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getConferenceFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getConferenceFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getConferenceFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getConferenceFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.CONFERENCE.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.CONFERENCE.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.CONFERENCE.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.CONFERENCE.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getBusinessBudgetByStage(String customerId, @NotNull BudgetTypeEnum stage) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andStageEqualTo(stage.getType())
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getBusinessFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getBusinessFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getBusinessFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getBusinessFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.BUSINESS.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.BUSINESS.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.BUSINESS.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.BUSINESS.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getBusinessBudgetByCustomer(String customerId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        List<String> stageList = new ArrayList<>();
        stageList.add(BudgetTypeEnum.PRE_PROJECT.getType());
        stageList.add(BudgetTypeEnum.PROJECT.getType());
        stageList.add(BudgetTypeEnum.PRODUCT.getType());
        budgetExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andStageIn(stageList)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getBusinessFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getBusinessFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getBusinessFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getBusinessFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.BUSINESS.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.BUSINESS.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.BUSINESS.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.BUSINESS.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getCooperationBudgetByStage(String customerId, @NotNull BudgetTypeEnum stage) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andStageEqualTo(stage.getType())
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getCooperationFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getCooperationFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getCooperationFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getCooperationFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.COOPERATION.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.COOPERATION.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.COOPERATION.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.COOPERATION.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getCooperationBudgetByCustomer(String customerId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        List<String> stageList = new ArrayList<>();
        stageList.add(BudgetTypeEnum.PRE_PROJECT.getType());
        stageList.add(BudgetTypeEnum.PROJECT.getType());
        stageList.add(BudgetTypeEnum.PRODUCT.getType());
        budgetExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andStageIn(stageList)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getCooperationFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getCooperationFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getCooperationFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getCooperationFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.COOPERATION.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.COOPERATION.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.COOPERATION.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.COOPERATION.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getPropertyBudgetByStage(String customerId, @NotNull BudgetTypeEnum stage) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andStageEqualTo(stage.getType())
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getPropertyFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getPropertyFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getPropertyFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getPropertyFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.PROPERTY.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.PROPERTY.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.PROPERTY.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.PROPERTY.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getPropertyBudgetByCustomer(String customerId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        List<String> stageList = new ArrayList<>();
        stageList.add(BudgetTypeEnum.PRE_PROJECT.getType());
        stageList.add(BudgetTypeEnum.PROJECT.getType());
        stageList.add(BudgetTypeEnum.PRODUCT.getType());
        budgetExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andStageIn(stageList)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getPropertyFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getPropertyFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getPropertyFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getPropertyFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.PROPERTY.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.PROPERTY.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.PROPERTY.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.PROPERTY.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getLaborBudgetByStage(String customerId, @NotNull BudgetTypeEnum stage) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andStageEqualTo(stage.getType())
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getLaborFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getLaborFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getLaborFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getLaborFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.LABOR.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.LABOR.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.LABOR.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.LABOR.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getLaborBudgetByCustomer(String customerId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        List<String> stageList = new ArrayList<>();
        stageList.add(BudgetTypeEnum.PRE_PROJECT.getType());
        stageList.add(BudgetTypeEnum.PROJECT.getType());
        stageList.add(BudgetTypeEnum.PRODUCT.getType());
        budgetExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andStageIn(stageList)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getLaborFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getLaborFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getLaborFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getLaborFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.LABOR.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.LABOR.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.LABOR.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.LABOR.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getStaffBudgetByStage(String customerId, @NotNull BudgetTypeEnum stage) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andStageEqualTo(stage.getType())
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getStaffFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getStaffFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getStaffFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getStaffFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.STAFF.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.STAFF.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.STAFF.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.STAFF.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getStaffBudgetByCustomer(String customerId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        List<String> stageList = new ArrayList<>();
        stageList.add(BudgetTypeEnum.PRE_PROJECT.getType());
        stageList.add(BudgetTypeEnum.PROJECT.getType());
        stageList.add(BudgetTypeEnum.PRODUCT.getType());
        budgetExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andStageIn(stageList)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getStaffFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getStaffFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getStaffFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getStaffFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.STAFF.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.STAFF.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.STAFF.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.STAFF.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getConsultingBudgetByStage(String customerId, @NotNull BudgetTypeEnum stage) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andStageEqualTo(stage.getType())
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getConsultingFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getConsultingFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getConsultingFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getConsultingFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.CONSULTING.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.CONSULTING.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.CONSULTING.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.CONSULTING.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getConsultingBudgetByCustomer(String customerId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        List<String> stageList = new ArrayList<>();
        stageList.add(BudgetTypeEnum.PRE_PROJECT.getType());
        stageList.add(BudgetTypeEnum.PROJECT.getType());
        stageList.add(BudgetTypeEnum.PRODUCT.getType());
        budgetExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andStageIn(stageList)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getConsultingFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getConsultingFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getConsultingFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getConsultingFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.CONSULTING.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.CONSULTING.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.CONSULTING.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.CONSULTING.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getManagementBudgetByStage(String customerId, @NotNull BudgetTypeEnum stage) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andStageEqualTo(stage.getType())
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getManagementFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getManagementFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getManagementFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getManagementFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.MANAGEMENT.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.MANAGEMENT.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.MANAGEMENT.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.MANAGEMENT.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getManagementBudgetByCustomer(String customerId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        List<String> stageList = new ArrayList<>();
        stageList.add(BudgetTypeEnum.PRE_PROJECT.getType());
        stageList.add(BudgetTypeEnum.PROJECT.getType());
        stageList.add(BudgetTypeEnum.PRODUCT.getType());
        budgetExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andStageIn(stageList)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getManagementFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getManagementFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getManagementFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getManagementFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.MANAGEMENT.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.MANAGEMENT.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.MANAGEMENT.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.MANAGEMENT.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getOtherBudgetByStage(String customerId, @NotNull BudgetTypeEnum stage) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andStageEqualTo(stage.getType())
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getOtherFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getOtherFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getOtherFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getOtherFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.OTHER.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.OTHER.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.OTHER.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.OTHER.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getOtherBudgetByCustomer(String customerId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        List<String> stageList = new ArrayList<>();
        stageList.add(BudgetTypeEnum.PRE_PROJECT.getType());
        stageList.add(BudgetTypeEnum.PROJECT.getType());
        stageList.add(BudgetTypeEnum.PRODUCT.getType());
        budgetExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andStageIn(stageList)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getOtherFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getOtherFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getOtherFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getOtherFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.OTHER.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.OTHER.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.OTHER.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.OTHER.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getPerformanceBudgetByStage(String customerId, @NotNull BudgetTypeEnum stage) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andStageEqualTo(stage.getType())
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getPerformanceFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getPerformanceFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getPerformanceFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getPerformanceFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.PERFORMANCE.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.PERFORMANCE.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.PERFORMANCE.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.PERFORMANCE.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getPerformanceBudgetByCustomer(String customerId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        List<String> stageList = new ArrayList<>();
        stageList.add(BudgetTypeEnum.PRE_PROJECT.getType());
        stageList.add(BudgetTypeEnum.PROJECT.getType());
        stageList.add(BudgetTypeEnum.PRODUCT.getType());
        budgetExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andStageIn(stageList)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getPerformanceFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getPerformanceFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getPerformanceFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getPerformanceFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.PERFORMANCE.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.PERFORMANCE.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.PERFORMANCE.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.PERFORMANCE.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getInfrastructureBudgetByStage(String customerId, @NotNull BudgetTypeEnum stage) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andStageEqualTo(stage.getType())
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getInfrastructureFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getInfrastructureFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getInfrastructureFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getInfrastructureFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.INFRASTRUCTURE.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.INFRASTRUCTURE.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.INFRASTRUCTURE.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.INFRASTRUCTURE.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getInfrastructureBudgetByCustomer(String customerId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        List<String> stageList = new ArrayList<>();
        stageList.add(BudgetTypeEnum.PRE_PROJECT.getType());
        stageList.add(BudgetTypeEnum.PROJECT.getType());
        stageList.add(BudgetTypeEnum.PRODUCT.getType());
        budgetExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andStageIn(stageList)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getInfrastructureFeeApprovedBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getInfrastructureFeeApprovedZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getInfrastructureFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudgetZc(rdmsBudgetResearches.stream().map(RdmsBudgetResearch::getInfrastructureFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add));
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.INFRASTRUCTURE.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.INFRASTRUCTURE.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.INFRASTRUCTURE.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.INFRASTRUCTURE.getClassify());
            return classifyBudget;
        }
    }

    /**
     * 获取主项目设备费预算
     *
     * @param projectId
     */
    public ClassifyBudget getEquipmentBudgetByProjectId(String projectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andSubprojectIdIsNull()
                .andIsRootEqualTo(1)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getEquipmentFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getEquipmentFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            //实际的预算,应该是所有子项目的实际预算之和
            RdmsBudgetResearchExample subprojectBudgetExample = new RdmsBudgetResearchExample();
            subprojectBudgetExample.createCriteria()
                    .andProjectIdEqualTo(projectId)
                    .andSubprojectIdIsNotNull()
                    .andIsRootEqualTo(0)
                    .andDeletedEqualTo(0);
            List<RdmsBudgetResearch> subProjectBudgetList = rdmsBudgetResearchMapper.selectByExample(subprojectBudgetExample);
            BigDecimal sumBudgetBt = subProjectBudgetList.stream().map(RdmsBudgetResearch::getEquipmentFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal sumBudgetZc = subProjectBudgetList.stream().map(RdmsBudgetResearch::getEquipmentFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add);

            classifyBudget.setBudgetBt(sumBudgetBt);
            classifyBudget.setBudgetZc(sumBudgetZc);
            classifyBudget.setBudget(sumBudgetBt.add(sumBudgetZc));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.EQUIPMENT.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.EQUIPMENT.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.EQUIPMENT.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.EQUIPMENT.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getEquipmentBudgetByProductId(String productId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andProjectIdEqualTo(productId)
                .andStageEqualTo(ProjectTypeEnum.PRODUCT.getType())
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getEquipmentFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getEquipmentFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getEquipmentFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getEquipmentFeeZc());
            classifyBudget.setBudget(rdmsBudgetResearch.getEquipmentFeeBt().add(rdmsBudgetResearch.getEquipmentFeeZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.EQUIPMENT.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.EQUIPMENT.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.EQUIPMENT.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.EQUIPMENT.getClassify());
            return classifyBudget;
        }
    }

    public void initBudgetResearchItem(String customerId, String projectId, String parentId, String subprojectId, String preprojectId, BudgetTypeEnum stage, Integer isRoot) {
        RdmsBudgetResearch budgetResearch = new RdmsBudgetResearch();
        budgetResearch.setCustomerId(customerId);
        budgetResearch.setProjectId(projectId);
        budgetResearch.setParentId(parentId);
        budgetResearch.setSubprojectId(subprojectId);
        budgetResearch.setPreprojectId(preprojectId);
        budgetResearch.setStage(stage.getType());
        budgetResearch.setIsRoot(isRoot);  //0:非根节点，1:根节点
        budgetResearch.setEquipmentFeeApprovedBt(BigDecimal.ZERO);
        budgetResearch.setEquipmentFeeApprovedZc(BigDecimal.ZERO);
        budgetResearch.setEquipmentFeeBt(BigDecimal.ZERO);
        budgetResearch.setEquipmentFeeZc(BigDecimal.ZERO);
        budgetResearch.setTestFeeApprovedBt(BigDecimal.ZERO);
        budgetResearch.setTestFeeApprovedZc(BigDecimal.ZERO);
        budgetResearch.setTestFeeBt(BigDecimal.ZERO);
        budgetResearch.setTestFeeZc(BigDecimal.ZERO);
        budgetResearch.setMaterialFeeApprovedBt(BigDecimal.ZERO);
        budgetResearch.setMaterialFeeApprovedZc(BigDecimal.ZERO);
        budgetResearch.setMaterialFeeBt(BigDecimal.ZERO);
        budgetResearch.setMaterialFeeZc(BigDecimal.ZERO);
        budgetResearch.setPowerFeeApprovedBt(BigDecimal.ZERO);
        budgetResearch.setPowerFeeApprovedZc(BigDecimal.ZERO);
        budgetResearch.setPowerFeeBt(BigDecimal.ZERO);
        budgetResearch.setPowerFeeZc(BigDecimal.ZERO);
        budgetResearch.setConferenceFeeApprovedBt(BigDecimal.ZERO);
        budgetResearch.setConferenceFeeApprovedZc(BigDecimal.ZERO);
        budgetResearch.setConferenceFeeBt(BigDecimal.ZERO);
        budgetResearch.setConferenceFeeZc(BigDecimal.ZERO);
        budgetResearch.setBusinessFeeApprovedBt(BigDecimal.ZERO);
        budgetResearch.setBusinessFeeApprovedZc(BigDecimal.ZERO);
        budgetResearch.setBusinessFeeBt(BigDecimal.ZERO);
        budgetResearch.setBusinessFeeZc(BigDecimal.ZERO);
        budgetResearch.setCooperationFeeApprovedBt(BigDecimal.ZERO);
        budgetResearch.setCooperationFeeApprovedZc(BigDecimal.ZERO);
        budgetResearch.setCooperationFeeBt(BigDecimal.ZERO);
        budgetResearch.setCooperationFeeZc(BigDecimal.ZERO);
        budgetResearch.setPropertyFeeApprovedBt(BigDecimal.ZERO);
        budgetResearch.setPropertyFeeApprovedZc(BigDecimal.ZERO);
        budgetResearch.setPropertyFeeBt(BigDecimal.ZERO);
        budgetResearch.setPropertyFeeZc(BigDecimal.ZERO);
        budgetResearch.setLaborFeeApprovedBt(BigDecimal.ZERO);
        budgetResearch.setLaborFeeApprovedZc(BigDecimal.ZERO);
        budgetResearch.setLaborFeeBt(BigDecimal.ZERO);
        budgetResearch.setLaborFeeZc(BigDecimal.ZERO);
        budgetResearch.setStaffFeeApprovedBt(BigDecimal.ZERO);
        budgetResearch.setStaffFeeApprovedZc(BigDecimal.ZERO);
        budgetResearch.setStaffFeeBt(BigDecimal.ZERO);
        budgetResearch.setStaffFeeZc(BigDecimal.ZERO);
        budgetResearch.setConsultingFeeApprovedBt(BigDecimal.ZERO);
        budgetResearch.setConsultingFeeApprovedZc(BigDecimal.ZERO);
        budgetResearch.setConsultingFeeBt(BigDecimal.ZERO);
        budgetResearch.setConsultingFeeZc(BigDecimal.ZERO);
        budgetResearch.setManagementFeeApprovedBt(BigDecimal.ZERO);
        budgetResearch.setManagementFeeApprovedZc(BigDecimal.ZERO);
        budgetResearch.setManagementFeeBt(BigDecimal.ZERO);
        budgetResearch.setManagementFeeZc(BigDecimal.ZERO);
        budgetResearch.setOtherFeeApprovedBt(BigDecimal.ZERO);
        budgetResearch.setOtherFeeApprovedZc(BigDecimal.ZERO);
        budgetResearch.setOtherFeeBt(BigDecimal.ZERO);
        budgetResearch.setOtherFeeZc(BigDecimal.ZERO);
        budgetResearch.setPerformanceFeeApprovedBt(BigDecimal.ZERO);
        budgetResearch.setPerformanceFeeApprovedZc(BigDecimal.ZERO);
        budgetResearch.setPerformanceFeeBt(BigDecimal.ZERO);
        budgetResearch.setPerformanceFeeZc(BigDecimal.ZERO);
        budgetResearch.setInfrastructureFeeApprovedBt(BigDecimal.ZERO);
        budgetResearch.setInfrastructureFeeApprovedZc(BigDecimal.ZERO);
        budgetResearch.setInfrastructureFeeBt(BigDecimal.ZERO);
        budgetResearch.setInfrastructureFeeZc(BigDecimal.ZERO);
        this.save(budgetResearch);
    }

    public ClassifyBudget getMaterialBudgetByProductId(String productId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andProjectIdEqualTo(productId)
                .andStageEqualTo(ProjectTypeEnum.PRODUCT.getType())
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getMaterialFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getMaterialFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getMaterialFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getMaterialFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.MATERIAL.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.MATERIAL.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.MATERIAL.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.MATERIAL.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getTestBudgetByProductId(String productId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andProjectIdEqualTo(productId)
                .andStageEqualTo(ProjectTypeEnum.PRODUCT.getType())
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getTestFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getTestFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getTestFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getTestFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.TEST.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.TEST.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.TEST.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.TEST.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getPowerBudgetByProductId(String productId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andProjectIdEqualTo(productId)
                .andStageEqualTo(ProjectTypeEnum.PRODUCT.getType())
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getPowerFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getPowerFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getPowerFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getPowerFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.POWER.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.POWER.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.POWER.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.POWER.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getConferenceBudgetByProductId(String productId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andProjectIdEqualTo(productId)
                .andStageEqualTo(ProjectTypeEnum.PRODUCT.getType())
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getConferenceFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getConferenceFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getConferenceFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getConferenceFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.CONFERENCE.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.CONFERENCE.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.CONFERENCE.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.CONFERENCE.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getBusinessBudgetByProductId(String productId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andProjectIdEqualTo(productId)
                .andStageEqualTo(ProjectTypeEnum.PRODUCT.getType())
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getBusinessFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getBusinessFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getBusinessFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getBusinessFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.BUSINESS.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.BUSINESS.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.BUSINESS.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.BUSINESS.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getCooperationBudgetByProductId(String productId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andProjectIdEqualTo(productId)
                .andStageEqualTo(ProjectTypeEnum.PRODUCT.getType())
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getCooperationFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getCooperationFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getCooperationFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getCooperationFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.COOPERATION.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.COOPERATION.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.COOPERATION.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.COOPERATION.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getPropertyBudgetByProductId(String productId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andProjectIdEqualTo(productId)
                .andStageEqualTo(ProjectTypeEnum.PRODUCT.getType())
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getPropertyFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getPropertyFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getPropertyFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getPropertyFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.PROPERTY.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.PROPERTY.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.PROPERTY.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.PROPERTY.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getConsultingBudgetByProductId(String productId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andProjectIdEqualTo(productId)
                .andStageEqualTo(ProjectTypeEnum.PRODUCT.getType())
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getConsultingFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getConsultingFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getConsultingFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getConsultingFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.CONSULTING.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.CONSULTING.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.CONSULTING.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.CONSULTING.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getOtherBudgetByProductId(String productId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andProjectIdEqualTo(productId)
                .andStageEqualTo(ProjectTypeEnum.PRODUCT.getType())
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getOtherFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getOtherFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getOtherFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getOtherFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.OTHER.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.OTHER.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.OTHER.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.OTHER.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getLaborBudgetByProductId(String productId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andProjectIdEqualTo(productId)
                .andStageEqualTo(ProjectTypeEnum.PRODUCT.getType())
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getLaborFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getLaborFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getLaborFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getLaborFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.LABOR.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.LABOR.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.LABOR.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.LABOR.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getManagementBudgetByProductId(String productId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andProjectIdEqualTo(productId)
                .andStageEqualTo(ProjectTypeEnum.PRODUCT.getType())
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getManagementFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getManagementFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getManagementFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getManagementFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.MANAGEMENT.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.MANAGEMENT.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.MANAGEMENT.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.MANAGEMENT.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getPerformanceBudgetByProductId(String productId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andProjectIdEqualTo(productId)
                .andStageEqualTo(ProjectTypeEnum.PRODUCT.getType())
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getPerformanceFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getPerformanceFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getPerformanceFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getPerformanceFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.PERFORMANCE.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.PERFORMANCE.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.PERFORMANCE.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.PERFORMANCE.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getStaffBudgetByProductId(String productId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andProjectIdEqualTo(productId)
                .andStageEqualTo(ProjectTypeEnum.PRODUCT.getType())
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getStaffFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getStaffFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getStaffFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getStaffFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.STAFF.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.STAFF.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.STAFF.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.STAFF.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getInfrastructureBudgetByProductId(String productId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andProjectIdEqualTo(productId)
                .andStageEqualTo(ProjectTypeEnum.PRODUCT.getType())
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getInfrastructureFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getInfrastructureFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getInfrastructureFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getInfrastructureFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.INFRASTRUCTURE.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.INFRASTRUCTURE.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.INFRASTRUCTURE.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.INFRASTRUCTURE.getClassify());
            return classifyBudget;
        }
    }

    /**
     * 获取子项目测试费预算
     *
     * @param subprojectId
     */
    public ClassifyBudget getTestBudgetBySubprojectId(String subprojectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getTestFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getTestFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getTestFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getTestFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.TEST.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.TEST.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.TEST.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.TEST.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getTestBudgetByPreProjectId(String preprojectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andPreprojectIdEqualTo(preprojectId)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getTestFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getTestFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getTestFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getTestFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.TEST.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.TEST.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.TEST.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.TEST.getClassify());
            return classifyBudget;
        }
    }

    /**
     * 获取主项目测试费预算
     *
     * @param projectId
     */
    public ClassifyBudget getTestBudgetByProjectId(String projectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andSubprojectIdIsNull()
                .andIsRootEqualTo(1)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getTestFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getTestFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            //实际的预算,应该是所有子项目的实际预算之和
            RdmsBudgetResearchExample subprojectBudgetExample = new RdmsBudgetResearchExample();
            subprojectBudgetExample.createCriteria()
                    .andProjectIdEqualTo(projectId)
                    .andSubprojectIdIsNotNull()
                    .andIsRootEqualTo(0)
                    .andDeletedEqualTo(0);
            List<RdmsBudgetResearch> subProjectBudgetList = rdmsBudgetResearchMapper.selectByExample(subprojectBudgetExample);
            BigDecimal sumBudgetBt = subProjectBudgetList.stream().map(RdmsBudgetResearch::getTestFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal sumBudgetZc = subProjectBudgetList.stream().map(RdmsBudgetResearch::getTestFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add);

            classifyBudget.setBudgetBt(sumBudgetBt);
            classifyBudget.setBudgetZc(sumBudgetZc);
            classifyBudget.setBudget(sumBudgetBt.add(sumBudgetZc));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.TEST.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.TEST.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.TEST.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.TEST.getClassify());
            return classifyBudget;
        }
    }

    /**
     * 获取子项目材料费预算
     *
     * @param subprojectId
     */
    public ClassifyBudget getMaterialBudgetBySubprojectId(String subprojectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getMaterialFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getMaterialFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getMaterialFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getMaterialFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.MATERIAL.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.MATERIAL.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.MATERIAL.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.MATERIAL.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getMaterialBudgetByPreProjectId(String preprojectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andPreprojectIdEqualTo(preprojectId)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getMaterialFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getMaterialFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getMaterialFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getMaterialFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.MATERIAL.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.MATERIAL.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.MATERIAL.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.MATERIAL.getClassify());
            return classifyBudget;
        }
    }

    /**
     * 获取主项目材料费预算
     *
     * @param projectId
     */
    public ClassifyBudget getMaterialBudgetByProjectId(String projectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andSubprojectIdIsNull()
                .andIsRootEqualTo(1)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getMaterialFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getMaterialFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            //实际的预算,应该是所有子项目的实际预算之和
            RdmsBudgetResearchExample subprojectBudgetExample = new RdmsBudgetResearchExample();
            subprojectBudgetExample.createCriteria()
                    .andProjectIdEqualTo(projectId)
                    .andSubprojectIdIsNotNull()
                    .andIsRootEqualTo(0)
                    .andDeletedEqualTo(0);
            List<RdmsBudgetResearch> subProjectBudgetList = rdmsBudgetResearchMapper.selectByExample(subprojectBudgetExample);
            BigDecimal sumBudgetBt = subProjectBudgetList.stream().map(RdmsBudgetResearch::getMaterialFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal sumBudgetZc = subProjectBudgetList.stream().map(RdmsBudgetResearch::getMaterialFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add);

            classifyBudget.setBudgetBt(sumBudgetBt);
            classifyBudget.setBudgetZc(sumBudgetZc);
            classifyBudget.setBudget(sumBudgetBt.add(sumBudgetZc));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.MATERIAL.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.MATERIAL.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.MATERIAL.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.MATERIAL.getClassify());
            return classifyBudget;
        }
    }

    /**
     * 获取子项目动力费预算
     *
     * @param subprojectId
     */
    public ClassifyBudget getPowerBudgetBySubprojectId(String subprojectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getPowerFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getPowerFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getPowerFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getPowerFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.POWER.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.POWER.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.POWER.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.POWER.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getPowerBudgetByPreProjectId(String preprojectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andPreprojectIdEqualTo(preprojectId)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getPowerFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getPowerFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getPowerFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getPowerFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.POWER.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.POWER.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.POWER.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.POWER.getClassify());
            return classifyBudget;
        }
    }

    /**
     * 获取主项目动力费预算
     *
     * @param projectId
     */
    public ClassifyBudget getPowerBudgetByProjectId(String projectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andSubprojectIdIsNull()
                .andIsRootEqualTo(1)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getPowerFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getPowerFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            //实际的预算,应该是所有子项目的实际预算之和
            RdmsBudgetResearchExample subprojectBudgetExample = new RdmsBudgetResearchExample();
            subprojectBudgetExample.createCriteria()
                    .andProjectIdEqualTo(projectId)
                    .andSubprojectIdIsNotNull()
                    .andIsRootEqualTo(0)
                    .andDeletedEqualTo(0);
            List<RdmsBudgetResearch> subProjectBudgetList = rdmsBudgetResearchMapper.selectByExample(subprojectBudgetExample);
            BigDecimal sumBudgetBt = subProjectBudgetList.stream().map(RdmsBudgetResearch::getPowerFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal sumBudgetZc = subProjectBudgetList.stream().map(RdmsBudgetResearch::getPowerFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add);

            classifyBudget.setBudgetBt(sumBudgetBt);
            classifyBudget.setBudgetZc(sumBudgetZc);
            classifyBudget.setBudget(sumBudgetBt.add(sumBudgetZc));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.POWER.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.POWER.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.POWER.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.POWER.getClassify());
            return classifyBudget;
        }
    }

    /**
     * 获取子项目会议费预算
     *
     * @param subprojectId
     */
    public ClassifyBudget getConferenceBudgetBySubprojectId(String subprojectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getConferenceFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getConferenceFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getConferenceFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getConferenceFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.CONFERENCE.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.CONFERENCE.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.CONFERENCE.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.CONFERENCE.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getConferenceBudgetByPreProjectId(String preprojectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andPreprojectIdEqualTo(preprojectId)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getConferenceFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getConferenceFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getConferenceFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getConferenceFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.CONFERENCE.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.CONFERENCE.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.CONFERENCE.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.CONFERENCE.getClassify());
            return classifyBudget;
        }
    }

    /**
     * 获取主项目会议费预算
     *
     * @param projectId
     */
    public ClassifyBudget getConferenceBudgetByProjectId(String projectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andSubprojectIdIsNull()
                .andIsRootEqualTo(1)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getConferenceFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getConferenceFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            //实际的预算,应该是所有子项目的实际预算之和
            RdmsBudgetResearchExample subprojectBudgetExample = new RdmsBudgetResearchExample();
            subprojectBudgetExample.createCriteria()
                    .andProjectIdEqualTo(projectId)
                    .andSubprojectIdIsNotNull()
                    .andIsRootEqualTo(0)
                    .andDeletedEqualTo(0);
            List<RdmsBudgetResearch> subProjectBudgetList = rdmsBudgetResearchMapper.selectByExample(subprojectBudgetExample);
            BigDecimal sumBudgetBt = subProjectBudgetList.stream().map(RdmsBudgetResearch::getConferenceFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal sumBudgetZc = subProjectBudgetList.stream().map(RdmsBudgetResearch::getConferenceFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add);

            classifyBudget.setBudgetBt(sumBudgetBt);
            classifyBudget.setBudgetZc(sumBudgetZc);
            classifyBudget.setBudget(sumBudgetBt.add(sumBudgetZc));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.CONFERENCE.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.CONFERENCE.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.CONFERENCE.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.CONFERENCE.getClassify());
            return classifyBudget;
        }
    }

    /**
     * 获取子项目差旅费预算
     *
     * @param subprojectId
     */
    public ClassifyBudget getBusinessBudgetBySubprojectId(String subprojectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getBusinessFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getBusinessFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getBusinessFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getBusinessFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.BUSINESS.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.BUSINESS.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.BUSINESS.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.BUSINESS.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getBusinessBudgetByPreProjectId(String preprojectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andPreprojectIdEqualTo(preprojectId)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getBusinessFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getBusinessFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getBusinessFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getBusinessFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.BUSINESS.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.BUSINESS.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.BUSINESS.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.BUSINESS.getClassify());
            return classifyBudget;
        }
    }

    /**
     * 获取主项目差旅费预算
     *
     * @param projectId
     */
    public ClassifyBudget getBusinessBudgetByProjectId(String projectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andSubprojectIdIsNull()
                .andIsRootEqualTo(1)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getBusinessFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getBusinessFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            //实际的预算,应该是所有子项目的实际预算之和
            RdmsBudgetResearchExample subprojectBudgetExample = new RdmsBudgetResearchExample();
            subprojectBudgetExample.createCriteria()
                    .andProjectIdEqualTo(projectId)
                    .andSubprojectIdIsNotNull()
                    .andIsRootEqualTo(0)
                    .andDeletedEqualTo(0);
            List<RdmsBudgetResearch> subProjectBudgetList = rdmsBudgetResearchMapper.selectByExample(subprojectBudgetExample);
            BigDecimal sumBudgetBt = subProjectBudgetList.stream().map(RdmsBudgetResearch::getBusinessFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal sumBudgetZc = subProjectBudgetList.stream().map(RdmsBudgetResearch::getBusinessFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add);

            classifyBudget.setBudgetBt(sumBudgetBt);
            classifyBudget.setBudgetZc(sumBudgetZc);
            classifyBudget.setBudget(sumBudgetBt.add(sumBudgetZc));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.BUSINESS.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.BUSINESS.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.BUSINESS.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.BUSINESS.getClassify());
            return classifyBudget;
        }
    }

    /**
     * 获取子项目合作费预算
     *
     * @param subprojectId
     */
    public ClassifyBudget getCooperationBudgetBySubprojectId(String subprojectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getCooperationFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getCooperationFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getCooperationFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getCooperationFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.COOPERATION.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.COOPERATION.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.COOPERATION.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.COOPERATION.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getCooperationBudgetByPreProjectId(String preprojectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andPreprojectIdEqualTo(preprojectId)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getCooperationFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getCooperationFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getCooperationFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getCooperationFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.COOPERATION.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.COOPERATION.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.COOPERATION.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.COOPERATION.getClassify());
            return classifyBudget;
        }
    }

    /**
     * 获取主项目合作费预算
     *
     * @param projectId
     */
    public ClassifyBudget getCooperationBudgetByProjectId(String projectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andSubprojectIdIsNull()
                .andIsRootEqualTo(1)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getCooperationFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getCooperationFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            //实际的预算,应该是所有子项目的实际预算之和
            RdmsBudgetResearchExample subprojectBudgetExample = new RdmsBudgetResearchExample();
            subprojectBudgetExample.createCriteria()
                    .andProjectIdEqualTo(projectId)
                    .andSubprojectIdIsNotNull()
                    .andIsRootEqualTo(0)
                    .andDeletedEqualTo(0);
            List<RdmsBudgetResearch> subProjectBudgetList = rdmsBudgetResearchMapper.selectByExample(subprojectBudgetExample);
            BigDecimal sumBudgetBt = subProjectBudgetList.stream().map(RdmsBudgetResearch::getCooperationFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal sumBudgetZc = subProjectBudgetList.stream().map(RdmsBudgetResearch::getCooperationFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add);

            classifyBudget.setBudgetBt(sumBudgetBt);
            classifyBudget.setBudgetZc(sumBudgetZc);
            classifyBudget.setBudget(sumBudgetBt.add(sumBudgetZc));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.COOPERATION.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.COOPERATION.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.COOPERATION.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.COOPERATION.getClassify());
            return classifyBudget;
        }
    }

    /**
     * 获取子项目知识产权费预算
     *
     * @param subprojectId
     */
    public ClassifyBudget getPropertyBudgetBySubprojectId(String subprojectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getPropertyFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getPropertyFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getPropertyFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getPropertyFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.PROPERTY.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.PROPERTY.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.PROPERTY.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.PROPERTY.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getPropertyBudgetByPreProjectId(String preprojectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andPreprojectIdEqualTo(preprojectId)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getPropertyFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getPropertyFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getPropertyFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getPropertyFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.PROPERTY.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.PROPERTY.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.PROPERTY.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.PROPERTY.getClassify());
            return classifyBudget;
        }
    }

    /**
     * 获取主项目知识产权费预算
     *
     * @param projectId
     */
    public ClassifyBudget getPropertyBudgetByProjectId(String projectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andSubprojectIdIsNull()
                .andIsRootEqualTo(1)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getPropertyFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getPropertyFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            //实际的预算,应该是所有子项目的实际预算之和
            RdmsBudgetResearchExample subprojectBudgetExample = new RdmsBudgetResearchExample();
            subprojectBudgetExample.createCriteria()
                    .andProjectIdEqualTo(projectId)
                    .andSubprojectIdIsNotNull()
                    .andIsRootEqualTo(0)
                    .andDeletedEqualTo(0);
            List<RdmsBudgetResearch> subProjectBudgetList = rdmsBudgetResearchMapper.selectByExample(subprojectBudgetExample);
            BigDecimal sumBudgetBt = subProjectBudgetList.stream().map(RdmsBudgetResearch::getPropertyFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal sumBudgetZc = subProjectBudgetList.stream().map(RdmsBudgetResearch::getPropertyFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add);

            classifyBudget.setBudgetBt(sumBudgetBt);
            classifyBudget.setBudgetZc(sumBudgetZc);
            classifyBudget.setBudget(sumBudgetBt.add(sumBudgetZc));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.PROPERTY.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.PROPERTY.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.PROPERTY.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.PROPERTY.getClassify());
            return classifyBudget;
        }
    }

    /**
     * 获取子项目劳务费预算
     *
     * @param subprojectId
     */
    public ClassifyBudget getLaborBudgetBySubprojectId(String subprojectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getLaborFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getLaborFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getLaborFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getLaborFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.LABOR.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.LABOR.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.LABOR.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.LABOR.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getLaborBudgetByPreProjectId(String preprojectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andPreprojectIdEqualTo(preprojectId)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getLaborFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getLaborFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getLaborFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getLaborFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.LABOR.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.LABOR.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.LABOR.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.LABOR.getClassify());
            return classifyBudget;
        }
    }

    /**
     * 获取主项目劳务费预算
     *
     * @param projectId
     */
    public ClassifyBudget getLaborBudgetByProjectId(String projectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andSubprojectIdIsNull()
                .andIsRootEqualTo(1)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getLaborFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getLaborFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            //实际的预算,应该是所有子项目的实际预算之和
            RdmsBudgetResearchExample subprojectBudgetExample = new RdmsBudgetResearchExample();
            subprojectBudgetExample.createCriteria()
                    .andProjectIdEqualTo(projectId)
                    .andSubprojectIdIsNotNull()
                    .andIsRootEqualTo(0)
                    .andDeletedEqualTo(0);
            List<RdmsBudgetResearch> subProjectBudgetList = rdmsBudgetResearchMapper.selectByExample(subprojectBudgetExample);
            BigDecimal sumBudgetBt = subProjectBudgetList.stream().map(RdmsBudgetResearch::getLaborFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal sumBudgetZc = subProjectBudgetList.stream().map(RdmsBudgetResearch::getLaborFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add);

            classifyBudget.setBudgetBt(sumBudgetBt);
            classifyBudget.setBudgetZc(sumBudgetZc);
            classifyBudget.setBudget(sumBudgetBt.add(sumBudgetZc));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.LABOR.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.LABOR.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.LABOR.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.LABOR.getClassify());
            return classifyBudget;
        }
    }

    /**
     * 获取子项目人员费预算
     *
     * @param subprojectId
     */
    public ClassifyBudget getStaffBudgetBySubprojectId(String subprojectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getStaffFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getStaffFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getStaffFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getStaffFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.STAFF.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.STAFF.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.STAFF.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.STAFF.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getStaffBudgetByPreProjectId(String preprojectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andPreprojectIdEqualTo(preprojectId)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getStaffFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getStaffFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getStaffFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getStaffFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.STAFF.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.STAFF.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.STAFF.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.STAFF.getClassify());
            return classifyBudget;
        }
    }

    /**
     * 获取主项目人员费预算
     *
     * @param projectId
     */
    public ClassifyBudget getStaffBudgetByProjectId(String projectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andSubprojectIdIsNull()
                .andIsRootEqualTo(1)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getStaffFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getStaffFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            //实际的预算,应该是所有子项目的实际预算之和
            RdmsBudgetResearchExample subprojectBudgetExample = new RdmsBudgetResearchExample();
            subprojectBudgetExample.createCriteria()
                    .andProjectIdEqualTo(projectId)
                    .andSubprojectIdIsNotNull()
                    .andIsRootEqualTo(0)
                    .andDeletedEqualTo(0);
            List<RdmsBudgetResearch> subProjectBudgetList = rdmsBudgetResearchMapper.selectByExample(subprojectBudgetExample);
            BigDecimal sumBudgetBt = subProjectBudgetList.stream().map(RdmsBudgetResearch::getStaffFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal sumBudgetZc = subProjectBudgetList.stream().map(RdmsBudgetResearch::getStaffFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add);

            classifyBudget.setBudgetBt(sumBudgetBt);
            classifyBudget.setBudgetZc(sumBudgetZc);
            classifyBudget.setBudget(sumBudgetBt.add(sumBudgetZc));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.STAFF.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.STAFF.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.STAFF.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.STAFF.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getInfrastructureBudgetBySubprojectId(String subprojectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getInfrastructureFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getInfrastructureFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getInfrastructureFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getInfrastructureFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.INFRASTRUCTURE.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.INFRASTRUCTURE.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.INFRASTRUCTURE.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.INFRASTRUCTURE.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getInfrastructureBudgetByPreProjectId(String preprojectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andPreprojectIdEqualTo(preprojectId)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getInfrastructureFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getInfrastructureFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getInfrastructureFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getInfrastructureFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.INFRASTRUCTURE.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.INFRASTRUCTURE.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.INFRASTRUCTURE.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.INFRASTRUCTURE.getClassify());
            return classifyBudget;
        }
    }

    /**
     * 获取主项目人员费预算
     *
     * @param projectId
     */
    public ClassifyBudget getInfrastructureBudgetByProjectId(String projectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andSubprojectIdIsNull()
                .andIsRootEqualTo(1)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getInfrastructureFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getInfrastructureFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            //实际的预算,应该是所有子项目的实际预算之和
            RdmsBudgetResearchExample subprojectBudgetExample = new RdmsBudgetResearchExample();
            subprojectBudgetExample.createCriteria()
                    .andProjectIdEqualTo(projectId)
                    .andSubprojectIdIsNotNull()
                    .andIsRootEqualTo(0)
                    .andDeletedEqualTo(0);
            List<RdmsBudgetResearch> subProjectBudgetList = rdmsBudgetResearchMapper.selectByExample(subprojectBudgetExample);
            BigDecimal sumBudgetBt = subProjectBudgetList.stream().map(RdmsBudgetResearch::getInfrastructureFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal sumBudgetZc = subProjectBudgetList.stream().map(RdmsBudgetResearch::getInfrastructureFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add);

            classifyBudget.setBudgetBt(sumBudgetBt);
            classifyBudget.setBudgetZc(sumBudgetZc);
            classifyBudget.setBudget(sumBudgetBt.add(sumBudgetZc));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.INFRASTRUCTURE.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.INFRASTRUCTURE.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.INFRASTRUCTURE.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.INFRASTRUCTURE.getClassify());
            return classifyBudget;
        }
    }

    /**
     * 获取子项目专家费预算
     *
     * @param subprojectId
     */
    public ClassifyBudget getConsultingBudgetBySubprojectId(String subprojectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getConsultingFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getConsultingFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getConsultingFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getConsultingFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.CONSULTING.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.CONSULTING.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.CONSULTING.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.CONSULTING.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getConsultingBudgetByPreProjectId(String preprojectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andPreprojectIdEqualTo(preprojectId)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getConsultingFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getConsultingFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getConsultingFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getConsultingFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.CONSULTING.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.CONSULTING.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.CONSULTING.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.CONSULTING.getClassify());
            return classifyBudget;
        }
    }

    /**
     * 获取主项目专家费预算
     *
     * @param projectId
     */
    public ClassifyBudget getConsultingBudgetByProjectId(String projectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andSubprojectIdIsNull()
                .andIsRootEqualTo(1)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getConsultingFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getConsultingFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            //实际的预算,应该是所有子项目的实际预算之和
            RdmsBudgetResearchExample subprojectBudgetExample = new RdmsBudgetResearchExample();
            subprojectBudgetExample.createCriteria()
                    .andProjectIdEqualTo(projectId)
                    .andSubprojectIdIsNotNull()
                    .andIsRootEqualTo(0)
                    .andDeletedEqualTo(0);
            List<RdmsBudgetResearch> subProjectBudgetList = rdmsBudgetResearchMapper.selectByExample(subprojectBudgetExample);
            BigDecimal sumBudgetBt = subProjectBudgetList.stream().map(RdmsBudgetResearch::getConsultingFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal sumBudgetZc = subProjectBudgetList.stream().map(RdmsBudgetResearch::getConsultingFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add);

            classifyBudget.setBudgetBt(sumBudgetBt);
            classifyBudget.setBudgetZc(sumBudgetZc);
            classifyBudget.setBudget(sumBudgetBt.add(sumBudgetZc));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.CONSULTING.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.CONSULTING.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.CONSULTING.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.CONSULTING.getClassify());
            return classifyBudget;
        }
    }

    /**
     * 获取子项目管理费预算
     *
     * @param subprojectId
     */
    public ClassifyBudget getManagementBudgetBySubprojectId(String subprojectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getManagementFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getManagementFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getManagementFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getManagementFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.MANAGEMENT.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.MANAGEMENT.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.MANAGEMENT.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.MANAGEMENT.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getManagementBudgetByPreProjectId(String preprojectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andPreprojectIdEqualTo(preprojectId)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getManagementFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getManagementFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getManagementFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getManagementFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.MANAGEMENT.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.MANAGEMENT.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.MANAGEMENT.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.MANAGEMENT.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getManagementBudgetByProjectId(String projectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andSubprojectIdIsNull()
                .andIsRootEqualTo(1)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getManagementFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getManagementFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            //实际的预算,应该是所有子项目的实际预算之和
            RdmsBudgetResearchExample subprojectBudgetExample = new RdmsBudgetResearchExample();
            subprojectBudgetExample.createCriteria()
                    .andProjectIdEqualTo(projectId)
                    .andSubprojectIdIsNotNull()
                    .andIsRootEqualTo(0)
                    .andDeletedEqualTo(0);
            List<RdmsBudgetResearch> subProjectBudgetList = rdmsBudgetResearchMapper.selectByExample(subprojectBudgetExample);
            BigDecimal sumBudgetBt = subProjectBudgetList.stream().map(RdmsBudgetResearch::getManagementFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal sumBudgetZc = subProjectBudgetList.stream().map(RdmsBudgetResearch::getManagementFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add);

            classifyBudget.setBudgetBt(sumBudgetBt);
            classifyBudget.setBudgetZc(sumBudgetZc);
            classifyBudget.setBudget(sumBudgetBt.add(sumBudgetZc));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.MANAGEMENT.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.MANAGEMENT.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.MANAGEMENT.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.MANAGEMENT.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getPerformanceBudgetBySubprojectId(String subprojectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getPerformanceFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getPerformanceFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getPerformanceFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getPerformanceFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.PERFORMANCE.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.PERFORMANCE.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.PERFORMANCE.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.PERFORMANCE.getClassify());
            return classifyBudget;
        }
    }

     public ClassifyBudget getPerformanceBudgetByPreProjectId(String preprojectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andPreprojectIdEqualTo(preprojectId)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getPerformanceFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getPerformanceFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getPerformanceFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getPerformanceFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.PERFORMANCE.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.PERFORMANCE.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.PERFORMANCE.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.PERFORMANCE.getClassify());
            return classifyBudget;
        }
     }

    /**
     * 获取主项目管理费预算
     *
     * @param projectId
     */
    public ClassifyBudget getPerformanceBudgetByProjectId(String projectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andSubprojectIdIsNull()
                .andIsRootEqualTo(1)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getPerformanceFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getPerformanceFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            //实际的预算,应该是所有子项目的实际预算之和
            RdmsBudgetResearchExample subprojectBudgetExample = new RdmsBudgetResearchExample();
            subprojectBudgetExample.createCriteria()
                    .andProjectIdEqualTo(projectId)
                    .andSubprojectIdIsNotNull()
                    .andIsRootEqualTo(0)
                    .andDeletedEqualTo(0);
            List<RdmsBudgetResearch> subProjectBudgetList = rdmsBudgetResearchMapper.selectByExample(subprojectBudgetExample);
            BigDecimal sumBudgetBt = subProjectBudgetList.stream().map(RdmsBudgetResearch::getPerformanceFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal sumBudgetZc = subProjectBudgetList.stream().map(RdmsBudgetResearch::getPerformanceFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add);

            classifyBudget.setBudgetBt(sumBudgetBt);
            classifyBudget.setBudgetZc(sumBudgetZc);
            classifyBudget.setBudget(sumBudgetBt.add(sumBudgetZc));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.PERFORMANCE.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.PERFORMANCE.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.PERFORMANCE.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.PERFORMANCE.getClassify());
            return classifyBudget;
        }
    }

    /**
     * 获取子项目其他费用预算
     *
     * @param subprojectId
     */
    public ClassifyBudget getOtherBudgetBySubprojectId(String subprojectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getOtherFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getOtherFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getOtherFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getOtherFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.OTHER.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.OTHER.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.OTHER.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.OTHER.getClassify());
            return classifyBudget;
        }
    }

    public ClassifyBudget getOtherBudgetByPreProjectId(String preprojectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andPreprojectIdEqualTo(preprojectId)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getOtherFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getOtherFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            classifyBudget.setBudgetBt(rdmsBudgetResearch.getOtherFeeBt());
            classifyBudget.setBudgetZc(rdmsBudgetResearch.getOtherFeeZc());
            classifyBudget.setBudget(classifyBudget.getBudgetBt().add(classifyBudget.getBudgetZc()));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.OTHER.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.OTHER.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.OTHER.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.OTHER.getClassify());
            return classifyBudget;
        }
    }

    /**
     * 获取主项目其他费用预算
     *
     * @param projectId
     */
    public ClassifyBudget getOtherBudgetByProjectId(String projectId) {
        ClassifyBudget classifyBudget = new ClassifyBudget();
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andSubprojectIdIsNull()
                .andIsRootEqualTo(1)
                .andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            classifyBudget.setApprovedBudgetBt(rdmsBudgetResearch.getOtherFeeApprovedBt());
            classifyBudget.setApprovedBudgetZc(rdmsBudgetResearch.getOtherFeeApprovedZc());
            classifyBudget.setApprovedBudget(classifyBudget.getApprovedBudgetBt().add(classifyBudget.getApprovedBudgetZc()));

            //实际的预算,应该是所有子项目的实际预算之和
            RdmsBudgetResearchExample subprojectBudgetExample = new RdmsBudgetResearchExample();
            subprojectBudgetExample.createCriteria()
                    .andProjectIdEqualTo(projectId)
                    .andSubprojectIdIsNotNull()
                    .andIsRootEqualTo(0)
                    .andDeletedEqualTo(0);
            List<RdmsBudgetResearch> subProjectBudgetList = rdmsBudgetResearchMapper.selectByExample(subprojectBudgetExample);
            BigDecimal sumBudgetBt = subProjectBudgetList.stream().map(RdmsBudgetResearch::getOtherFeeBt).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal sumBudgetZc = subProjectBudgetList.stream().map(RdmsBudgetResearch::getOtherFeeZc).reduce(BigDecimal.ZERO, BigDecimal::add);

            classifyBudget.setBudgetBt(sumBudgetBt);
            classifyBudget.setBudgetZc(sumBudgetZc);
            classifyBudget.setBudget(sumBudgetBt.add(sumBudgetZc));

            classifyBudget.setClassifyName(BudgetClassifyStdEnum.OTHER.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.OTHER.getClassify());
            return classifyBudget;
        } else {
            classifyBudget.setClassifyName(BudgetClassifyStdEnum.OTHER.getClassifyName());
            classifyBudget.setClassify(BudgetClassifyStdEnum.OTHER.getClassify());
            return classifyBudget;
        }
    }

    public void list(PageDto<RdmsBudgetResearchDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsBudgetResearchExample budgetExample = new RdmsBudgetResearchExample();
        budgetExample.createCriteria().andDeletedEqualTo(0);
        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetExample);

        PageInfo<RdmsBudgetResearch> pageInfo = new PageInfo<>(rdmsBudgetResearches);
        pageDto.setTotal(pageInfo.getTotal());
        List<RdmsBudgetResearchDto> rdmsBudgetResearchDtos = CopyUtil.copyList(rdmsBudgetResearches, RdmsBudgetResearchDto.class);

        pageDto.setList(rdmsBudgetResearchDtos);
    }

    public RdmsBudgetResearch selectByPrimaryKey(String id) {
        return rdmsBudgetResearchMapper.selectByPrimaryKey(id);
    }

    public RdmsBudgetResearchDto getRecordByProjectIdAndStage(String projectId, BudgetTypeEnum stage) {
        RdmsBudgetResearchExample budgetResearchExample = new RdmsBudgetResearchExample();
        RdmsBudgetResearchExample.Criteria criteria = budgetResearchExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andStageEqualTo(stage.getType())
                .andDeletedEqualTo(0);

        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetResearchExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearchDto budgetResearchDto = CopyUtil.copy(rdmsBudgetResearches.get(0), RdmsBudgetResearchDto.class);
            budgetResearchDto.setHasRecord(true);
            return budgetResearchDto;
        } else {
            RdmsBudgetResearchDto budgetResearchDto = new RdmsBudgetResearchDto();
            budgetResearchDto.setHasRecord(false);
            return budgetResearchDto;
        }
    }

    public RdmsBudgetResearchDto getRecordByPreProjectIdAndStage(String preprojectId) {
        RdmsBudgetResearchExample budgetResearchExample = new RdmsBudgetResearchExample();
        RdmsBudgetResearchExample.Criteria criteria = budgetResearchExample.createCriteria()
                .andPreprojectIdEqualTo(preprojectId)
                .andStageEqualTo(BudgetTypeEnum.PRE_PROJECT.getType())
                .andDeletedEqualTo(0);

        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetResearchExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearchDto budgetResearchDto = CopyUtil.copy(rdmsBudgetResearches.get(0), RdmsBudgetResearchDto.class);
            budgetResearchDto.setHasRecord(true);
            return budgetResearchDto;
        } else {
            RdmsBudgetResearchDto budgetResearchDto = new RdmsBudgetResearchDto();
            budgetResearchDto.setHasRecord(false);
            return budgetResearchDto;
        }
    }

    public List<RdmsBudgetResearch> getListByStage(String customerId, BudgetTypeEnum stage) {
        RdmsBudgetResearchExample budgetResearchExample = new RdmsBudgetResearchExample();
        RdmsBudgetResearchExample.Criteria criteria = budgetResearchExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andStageEqualTo(stage.getType())
                .andDeletedEqualTo(0);

        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetResearchExample);
        return rdmsBudgetResearches;
    }

    public RdmsBudgetResearchDto getRecordBySubProjectIdAndStage(String subprojectId, BudgetTypeEnum stage) {
        RdmsBudgetResearchExample budgetResearchExample = new RdmsBudgetResearchExample();
        RdmsBudgetResearchExample.Criteria criteria = budgetResearchExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andStageEqualTo(stage.getType())
                .andDeletedEqualTo(0);

        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetResearchExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearchDto budgetResearchDto = CopyUtil.copy(rdmsBudgetResearches.get(0), RdmsBudgetResearchDto.class);
            budgetResearchDto.setHasRecord(true);
            return budgetResearchDto;
        } else {
            RdmsBudgetResearchDto budgetResearchDto = new RdmsBudgetResearchDto();
            budgetResearchDto.setHasRecord(false);
            return budgetResearchDto;
        }
    }

    public RdmsBudgetResearchDto getRecordByCustomerIdAndSubprojectId(String customerId, String subprojectId) {
        RdmsBudgetResearchExample budgetResearchExample = new RdmsBudgetResearchExample();
        RdmsBudgetResearchExample.Criteria criteria = budgetResearchExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andSubprojectIdEqualTo(subprojectId)
                .andDeletedEqualTo(0);

        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetResearchExample);
        if (!CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            RdmsBudgetResearchDto budgetResearchDto = CopyUtil.copy(rdmsBudgetResearches.get(0), RdmsBudgetResearchDto.class);
            budgetResearchDto.setHasRecord(true);
            return budgetResearchDto;
        } else {
            RdmsBudgetResearchDto budgetResearchDto = new RdmsBudgetResearchDto();
            budgetResearchDto.setHasRecord(false);
            return budgetResearchDto;
        }
    }

    /**
     * 获得项目的总预算
     * 注意: 如果预算进行了调整, 这里不反应调整后的数据
     *
     * @param customerId
     * @param projectId
     * @return
     */
    public double getDirectSumBudgetByCustomerIdAndProjectId(String customerId, String projectId) {
        RdmsBudgetResearchExample budgetResearchExample = new RdmsBudgetResearchExample();
        RdmsBudgetResearchExample.Criteria criteria = budgetResearchExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andProjectIdEqualTo(projectId)
                .andIsRootEqualTo(1)
                .andDeletedEqualTo(0);

        List<RdmsBudgetResearch> rdmsBudgetResearches = rdmsBudgetResearchMapper.selectByExample(budgetResearchExample);
        if (CollectionUtils.isEmpty(rdmsBudgetResearches)) {
            return 0.00;
        } else {
            RdmsBudgetResearch rdmsBudgetResearch = rdmsBudgetResearches.get(0);
            BigDecimal sumBudget = BigDecimal.ZERO;
            BigDecimal add = sumBudget
                    .add(rdmsBudgetResearch.getEquipmentFeeBt())
                    .add(rdmsBudgetResearch.getEquipmentFeeZc())
                    .add(rdmsBudgetResearch.getTestFeeBt())
                    .add(rdmsBudgetResearch.getTestFeeZc())
                    .add(rdmsBudgetResearch.getMaterialFeeBt())
                    .add(rdmsBudgetResearch.getMaterialFeeZc())
                    .add(rdmsBudgetResearch.getPowerFeeBt())
                    .add(rdmsBudgetResearch.getPowerFeeZc())
                    .add(rdmsBudgetResearch.getConferenceFeeBt())
                    .add(rdmsBudgetResearch.getConferenceFeeZc())
                    .add(rdmsBudgetResearch.getBusinessFeeBt())
                    .add(rdmsBudgetResearch.getBusinessFeeZc())
                    .add(rdmsBudgetResearch.getCooperationFeeBt())
                    .add(rdmsBudgetResearch.getCooperationFeeZc())
                    .add(rdmsBudgetResearch.getPropertyFeeBt())
                    .add(rdmsBudgetResearch.getPropertyFeeZc())
                    .add(rdmsBudgetResearch.getLaborFeeBt())
                    .add(rdmsBudgetResearch.getLaborFeeZc())
                    .add(rdmsBudgetResearch.getStaffFeeBt())
                    .add(rdmsBudgetResearch.getStaffFeeZc())
                    .add(rdmsBudgetResearch.getConsultingFeeBt())
                    .add(rdmsBudgetResearch.getConsultingFeeZc())
                    .add(rdmsBudgetResearch.getManagementFeeBt())
                    .add(rdmsBudgetResearch.getManagementFeeZc())
                    .add(rdmsBudgetResearch.getPerformanceFeeBt())
                    .add(rdmsBudgetResearch.getPerformanceFeeZc())
                    .add(rdmsBudgetResearch.getInfrastructureFeeBt())
                    .add(rdmsBudgetResearch.getInfrastructureFeeZc())
                    .add(rdmsBudgetResearch.getOtherFeeBt())
                    .add(rdmsBudgetResearch.getOtherFeeZc());

            return add.doubleValue();
        }
    }

    public void transformBudgetToYuan(RdmsBudgetResearch rdmsBudgetResearch) {
        //将万元转换为元
        rdmsBudgetResearch.setEquipmentFeeApprovedBt(rdmsBudgetResearch.getEquipmentFeeApprovedBt().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setEquipmentFeeApprovedZc(rdmsBudgetResearch.getEquipmentFeeApprovedZc().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setEquipmentFeeBt(rdmsBudgetResearch.getEquipmentFeeBt().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setEquipmentFeeZc(rdmsBudgetResearch.getEquipmentFeeZc().multiply(BigDecimal.valueOf(10000)));

        rdmsBudgetResearch.setTestFeeApprovedBt(rdmsBudgetResearch.getTestFeeApprovedBt().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setTestFeeApprovedZc(rdmsBudgetResearch.getTestFeeApprovedZc().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setTestFeeBt(rdmsBudgetResearch.getTestFeeBt().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setTestFeeZc(rdmsBudgetResearch.getTestFeeZc().multiply(BigDecimal.valueOf(10000)));

        rdmsBudgetResearch.setMaterialFeeApprovedBt(rdmsBudgetResearch.getMaterialFeeApprovedBt().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setMaterialFeeApprovedZc(rdmsBudgetResearch.getMaterialFeeApprovedZc().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setMaterialFeeBt(rdmsBudgetResearch.getMaterialFeeBt().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setMaterialFeeZc(rdmsBudgetResearch.getMaterialFeeZc().multiply(BigDecimal.valueOf(10000)));

        rdmsBudgetResearch.setPowerFeeApprovedBt(rdmsBudgetResearch.getPowerFeeApprovedBt().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setPowerFeeApprovedZc(rdmsBudgetResearch.getPowerFeeApprovedZc().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setPowerFeeBt(rdmsBudgetResearch.getPowerFeeBt().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setPowerFeeZc(rdmsBudgetResearch.getPowerFeeZc().multiply(BigDecimal.valueOf(10000)));

        rdmsBudgetResearch.setConferenceFeeApprovedBt(rdmsBudgetResearch.getConferenceFeeApprovedBt().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setConferenceFeeApprovedZc(rdmsBudgetResearch.getConferenceFeeApprovedZc().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setConferenceFeeBt(rdmsBudgetResearch.getConferenceFeeBt().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setConferenceFeeZc(rdmsBudgetResearch.getConferenceFeeZc().multiply(BigDecimal.valueOf(10000)));

        rdmsBudgetResearch.setBusinessFeeApprovedBt(rdmsBudgetResearch.getBusinessFeeApprovedBt().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setBusinessFeeApprovedZc(rdmsBudgetResearch.getBusinessFeeApprovedZc().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setBusinessFeeBt(rdmsBudgetResearch.getBusinessFeeBt().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setBusinessFeeZc(rdmsBudgetResearch.getBusinessFeeZc().multiply(BigDecimal.valueOf(10000)));

        rdmsBudgetResearch.setCooperationFeeApprovedBt(rdmsBudgetResearch.getCooperationFeeApprovedBt().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setCooperationFeeApprovedZc(rdmsBudgetResearch.getCooperationFeeApprovedZc().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setCooperationFeeBt(rdmsBudgetResearch.getCooperationFeeBt().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setCooperationFeeZc(rdmsBudgetResearch.getCooperationFeeZc().multiply(BigDecimal.valueOf(10000)));

        rdmsBudgetResearch.setPropertyFeeApprovedBt(rdmsBudgetResearch.getPropertyFeeApprovedBt().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setPropertyFeeApprovedZc(rdmsBudgetResearch.getPropertyFeeApprovedZc().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setPropertyFeeBt(rdmsBudgetResearch.getPropertyFeeBt().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setPropertyFeeZc(rdmsBudgetResearch.getPropertyFeeZc().multiply(BigDecimal.valueOf(10000)));

        rdmsBudgetResearch.setLaborFeeApprovedBt(rdmsBudgetResearch.getLaborFeeApprovedBt().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setLaborFeeApprovedZc(rdmsBudgetResearch.getLaborFeeApprovedZc().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setLaborFeeBt(rdmsBudgetResearch.getLaborFeeBt().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setLaborFeeZc(rdmsBudgetResearch.getLaborFeeZc().multiply(BigDecimal.valueOf(10000)));

        rdmsBudgetResearch.setStaffFeeApprovedBt(rdmsBudgetResearch.getStaffFeeApprovedBt().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setStaffFeeApprovedZc(rdmsBudgetResearch.getStaffFeeApprovedZc().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setStaffFeeBt(rdmsBudgetResearch.getStaffFeeBt().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setStaffFeeZc(rdmsBudgetResearch.getStaffFeeZc().multiply(BigDecimal.valueOf(10000)));

        rdmsBudgetResearch.setConsultingFeeApprovedBt(rdmsBudgetResearch.getConsultingFeeApprovedBt().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setConsultingFeeApprovedZc(rdmsBudgetResearch.getConsultingFeeApprovedZc().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setConsultingFeeBt(rdmsBudgetResearch.getConsultingFeeBt().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setConsultingFeeZc(rdmsBudgetResearch.getConsultingFeeZc().multiply(BigDecimal.valueOf(10000)));

        rdmsBudgetResearch.setManagementFeeApprovedBt(rdmsBudgetResearch.getManagementFeeApprovedBt().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setManagementFeeApprovedZc(rdmsBudgetResearch.getManagementFeeApprovedZc().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setManagementFeeBt(rdmsBudgetResearch.getManagementFeeBt().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setManagementFeeZc(rdmsBudgetResearch.getManagementFeeZc().multiply(BigDecimal.valueOf(10000)));

        rdmsBudgetResearch.setOtherFeeApprovedBt(rdmsBudgetResearch.getOtherFeeApprovedBt().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setOtherFeeApprovedZc(rdmsBudgetResearch.getOtherFeeApprovedZc().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setOtherFeeBt(rdmsBudgetResearch.getOtherFeeBt().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setOtherFeeZc(rdmsBudgetResearch.getOtherFeeZc().multiply(BigDecimal.valueOf(10000)));

        rdmsBudgetResearch.setPerformanceFeeApprovedBt(rdmsBudgetResearch.getPerformanceFeeApprovedBt().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setPerformanceFeeApprovedZc(rdmsBudgetResearch.getPerformanceFeeApprovedZc().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setPerformanceFeeBt(rdmsBudgetResearch.getPerformanceFeeBt().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setPerformanceFeeZc(rdmsBudgetResearch.getPerformanceFeeZc().multiply(BigDecimal.valueOf(10000)));

        rdmsBudgetResearch.setInfrastructureFeeApprovedBt(rdmsBudgetResearch.getInfrastructureFeeApprovedBt().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setInfrastructureFeeApprovedZc(rdmsBudgetResearch.getInfrastructureFeeApprovedZc().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setInfrastructureFeeBt(rdmsBudgetResearch.getInfrastructureFeeBt().multiply(BigDecimal.valueOf(10000)));
        rdmsBudgetResearch.setInfrastructureFeeZc(rdmsBudgetResearch.getInfrastructureFeeZc().multiply(BigDecimal.valueOf(10000)));

    }

    public void transformBudgetToWanYuan(RdmsBudgetResearchDto rdmsBudgetResearch) {
        //将元转换为万元
        rdmsBudgetResearch.setEquipmentFeeApprovedBt(rdmsBudgetResearch.getEquipmentFeeApprovedBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setEquipmentFeeApprovedZc(rdmsBudgetResearch.getEquipmentFeeApprovedZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setEquipmentFeeBt(rdmsBudgetResearch.getEquipmentFeeBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setEquipmentFeeZc(rdmsBudgetResearch.getEquipmentFeeZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));

        rdmsBudgetResearch.setTestFeeApprovedBt(rdmsBudgetResearch.getTestFeeApprovedBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setTestFeeApprovedZc(rdmsBudgetResearch.getTestFeeApprovedZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setTestFeeBt(rdmsBudgetResearch.getTestFeeBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setTestFeeZc(rdmsBudgetResearch.getTestFeeZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));

        rdmsBudgetResearch.setMaterialFeeApprovedBt(rdmsBudgetResearch.getMaterialFeeApprovedBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setMaterialFeeApprovedZc(rdmsBudgetResearch.getMaterialFeeApprovedZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setMaterialFeeBt(rdmsBudgetResearch.getMaterialFeeBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setMaterialFeeZc(rdmsBudgetResearch.getMaterialFeeZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));

        rdmsBudgetResearch.setPowerFeeApprovedBt(rdmsBudgetResearch.getPowerFeeApprovedBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setPowerFeeApprovedZc(rdmsBudgetResearch.getPowerFeeApprovedZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setPowerFeeBt(rdmsBudgetResearch.getPowerFeeBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setPowerFeeZc(rdmsBudgetResearch.getPowerFeeZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));

        rdmsBudgetResearch.setConferenceFeeApprovedBt(rdmsBudgetResearch.getConferenceFeeApprovedBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setConferenceFeeApprovedZc(rdmsBudgetResearch.getConferenceFeeApprovedZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setConferenceFeeBt(rdmsBudgetResearch.getConferenceFeeBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setConferenceFeeZc(rdmsBudgetResearch.getConferenceFeeZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));

        rdmsBudgetResearch.setBusinessFeeApprovedBt(rdmsBudgetResearch.getBusinessFeeApprovedBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setBusinessFeeApprovedZc(rdmsBudgetResearch.getBusinessFeeApprovedZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setBusinessFeeBt(rdmsBudgetResearch.getBusinessFeeBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setBusinessFeeZc(rdmsBudgetResearch.getBusinessFeeZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));

        rdmsBudgetResearch.setCooperationFeeApprovedBt(rdmsBudgetResearch.getCooperationFeeApprovedBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setCooperationFeeApprovedZc(rdmsBudgetResearch.getCooperationFeeApprovedZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setCooperationFeeBt(rdmsBudgetResearch.getCooperationFeeBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setCooperationFeeZc(rdmsBudgetResearch.getCooperationFeeZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));

        rdmsBudgetResearch.setPropertyFeeApprovedBt(rdmsBudgetResearch.getPropertyFeeApprovedBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setPropertyFeeApprovedZc(rdmsBudgetResearch.getPropertyFeeApprovedZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setPropertyFeeBt(rdmsBudgetResearch.getPropertyFeeBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setPropertyFeeZc(rdmsBudgetResearch.getPropertyFeeZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));

        rdmsBudgetResearch.setLaborFeeApprovedBt(rdmsBudgetResearch.getLaborFeeApprovedBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setLaborFeeApprovedZc(rdmsBudgetResearch.getLaborFeeApprovedZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setLaborFeeBt(rdmsBudgetResearch.getLaborFeeBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setLaborFeeZc(rdmsBudgetResearch.getLaborFeeZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));

        rdmsBudgetResearch.setStaffFeeApprovedBt(rdmsBudgetResearch.getStaffFeeApprovedBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setStaffFeeApprovedZc(rdmsBudgetResearch.getStaffFeeApprovedZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setStaffFeeBt(rdmsBudgetResearch.getStaffFeeBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setStaffFeeZc(rdmsBudgetResearch.getStaffFeeZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));

        rdmsBudgetResearch.setConsultingFeeApprovedBt(rdmsBudgetResearch.getConsultingFeeApprovedBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setConsultingFeeApprovedZc(rdmsBudgetResearch.getConsultingFeeApprovedZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setConsultingFeeBt(rdmsBudgetResearch.getConsultingFeeBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setConsultingFeeZc(rdmsBudgetResearch.getConsultingFeeZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));

        rdmsBudgetResearch.setManagementFeeApprovedBt(rdmsBudgetResearch.getManagementFeeApprovedBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setManagementFeeApprovedZc(rdmsBudgetResearch.getManagementFeeApprovedZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setManagementFeeBt(rdmsBudgetResearch.getManagementFeeBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setManagementFeeZc(rdmsBudgetResearch.getManagementFeeZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));

        rdmsBudgetResearch.setOtherFeeApprovedBt(rdmsBudgetResearch.getOtherFeeApprovedBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setOtherFeeApprovedZc(rdmsBudgetResearch.getOtherFeeApprovedZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setOtherFeeBt(rdmsBudgetResearch.getOtherFeeBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setOtherFeeZc(rdmsBudgetResearch.getOtherFeeZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));

        rdmsBudgetResearch.setPerformanceFeeApprovedBt(rdmsBudgetResearch.getPerformanceFeeApprovedBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setPerformanceFeeApprovedZc(rdmsBudgetResearch.getPerformanceFeeApprovedZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setPerformanceFeeBt(rdmsBudgetResearch.getPerformanceFeeBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setPerformanceFeeZc(rdmsBudgetResearch.getPerformanceFeeZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));

        rdmsBudgetResearch.setInfrastructureFeeApprovedBt(rdmsBudgetResearch.getInfrastructureFeeApprovedBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setInfrastructureFeeApprovedZc(rdmsBudgetResearch.getInfrastructureFeeApprovedZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setInfrastructureFeeBt(rdmsBudgetResearch.getInfrastructureFeeBt().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
        rdmsBudgetResearch.setInfrastructureFeeZc(rdmsBudgetResearch.getInfrastructureFeeZc().divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP));
    }

    /**
     * 保存
     */
    public String save(RdmsBudgetResearch budget) {
        if (ObjectUtils.isEmpty(budget.getId())) {
            return this.insert(budget);
        } else {
            RdmsBudgetResearch rdmsBudgetResearch = this.selectByPrimaryKey(budget.getId());
            if (ObjectUtils.isEmpty(rdmsBudgetResearch)) {
                return this.insert(budget);
            } else {
                return this.update(budget);
            }
        }
    }

    private String insert(RdmsBudgetResearch budget) {
        if (ObjectUtils.isEmpty(budget.getId())) {  //当前端页面给出projectID时,将不为空
            budget.setId(UuidUtil.getShortUuid());
        }
        RdmsBudgetResearch rdmsBudgetResearch = this.selectByPrimaryKey(budget.getId());
        if (ObjectUtils.isEmpty(rdmsBudgetResearch)) {
            budget.setDeleted(0);
            budget.setCreateTime(new Date());
            if (ObjectUtils.isEmpty(budget.getIsRoot())) {
                budget.setIsRoot(0);
            }
            rdmsBudgetResearchMapper.insert(budget);
            return budget.getId();
        } else {
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }
    }

    public String update(RdmsBudgetResearch budget) {
        if (ObjectUtils.isEmpty(budget.getId())) {
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsBudgetResearch rdmsBudgetResearch = this.selectByPrimaryKey(budget.getId());
        if (ObjectUtils.isEmpty(rdmsBudgetResearch)) {
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        } else {
            budget.setDeleted(0);
            budget.setIsRoot(rdmsBudgetResearch.getIsRoot());
            budget.setCreateTime(rdmsBudgetResearch.getCreateTime());
            budget.setUpdateTime(new Date());
            rdmsBudgetResearchMapper.updateByPrimaryKey(budget);
            return budget.getId();
        }
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsBudgetResearch rdmsBudgetResearch = this.selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(rdmsBudgetResearch)) {
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        } else {
            rdmsBudgetResearch.setDeleted(1);
            rdmsBudgetResearch.setUpdateTime(new Date());
            rdmsBudgetResearchMapper.updateByPrimaryKey(rdmsBudgetResearch);
        }
    }

}
