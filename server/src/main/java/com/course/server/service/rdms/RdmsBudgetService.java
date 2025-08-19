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
import com.course.server.dto.rdms.RdmsBossDto;
import com.course.server.dto.rdms.RdmsBudgetAdjustDto;
import com.course.server.dto.rdms.RdmsBudgetDto;
import com.course.server.enums.rdms.*;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsBudgetMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
//import java.util.logging.Logger;
//import java.util.logging.Level;

@Service
public class RdmsBudgetService {
    private static final Logger LOG = LoggerFactory.getLogger(RdmsBudgetService.class);
    @Resource
    private RdmsBudgetMapper rdmsBudgetMapper;
    @Resource
    private RdmsProjectService rdmsProjectService;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private RdmsBudgetAdjustService rdmsBudgetAdjustService;
    @Autowired
    private RdmsPreProjectService rdmsPreProjectService;
    @Resource
    private RdmsBudgetResearchService rdmsBudgetResearchService;
    @Resource
    private RdmsBossService rdmsBossService;
    @Resource
    private RdmsJobItemService rdmsJobItemService;

    @Transactional
    public RdmsBudgetDto getProjectAndApplicationBudgetItems(String budgetId) {
        RdmsBudget rdmsBudget = this.selectByPrimaryKey(budgetId);
        if (rdmsBudget == null) {
            return new RdmsBudgetDto();
        }
        RdmsBudgetDto rdmsBudgetDto = CopyUtil.copy(rdmsBudget, RdmsBudgetDto.class);
        rdmsBudgetDto.setDocType(DocTypeEnum.BUDGET.getType());
        if (!ObjectUtils.isEmpty(rdmsBudgetDto)) {
            if (rdmsBudget.getType().equals(BudgetTypeEnum.PRE_PROJECT.getType())) {
                RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(rdmsBudget.getProjectId());
                if (!ObjectUtils.isEmpty(rdmsPreProject)) {
                    rdmsBudgetDto.setItemName(rdmsPreProject.getPreProjectName());  //为了统一前端字段 名称
                }
            } else if (rdmsBudget.getType().equals(BudgetTypeEnum.PRODUCT.getType())) {
                RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(rdmsBudget.getProjectId());
                if (!ObjectUtils.isEmpty(rdmsProject)) {
                    rdmsBudgetDto.setItemName(rdmsProject.getProjectName());
                }
            } else if (rdmsBudget.getType().equals(BudgetTypeEnum.PROJECT.getType())) {
                RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(rdmsBudget.getProjectId());
                if (!ObjectUtils.isEmpty(rdmsProject)) {
                    rdmsBudgetDto.setItemName(rdmsProject.getProjectName());  //为了统一前端字段 名称
                }
            } else {
                LOG.error("Error method is: {}", "getProjectAndApplicationBudgetItems, line 72");
                throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
            }
        }

        if (!ObjectUtils.isEmpty(rdmsBudgetDto)) {
            int index = 1;
            List<BudgetTypeEnum> budgetTypeList = new ArrayList<>();
            budgetTypeList.add(BudgetTypeEnum.PRE_PROJECT);
            budgetTypeList.add(BudgetTypeEnum.PROJECT);
            budgetTypeList.add(BudgetTypeEnum.PRODUCT);

            List<RdmsBudgetAdjustDto> budgetListByProjectId = new ArrayList<>();
            if (rdmsBudgetDto.getType().equals(BudgetTypeEnum.PRE_PROJECT.getType())) {
                budgetListByProjectId = rdmsBudgetAdjustService.getBudgetListByPreProjectIdAndStatusTypeList(rdmsBudget.getProjectId() /*其实是preProjectId*/, BudgetApplicantStatusEnum.SUBMIT.getStatus(), budgetTypeList);

            } else if (rdmsBudgetDto.getType().equals(BudgetTypeEnum.PRODUCT.getType())) {
                budgetListByProjectId = rdmsBudgetAdjustService.getBudgetListByProductIdAndStatusTypeList(rdmsBudget.getProjectId() /*其实是preProjectId*/, BudgetApplicantStatusEnum.SUBMIT.getStatus(), budgetTypeList);

            } else {
                budgetListByProjectId = rdmsBudgetAdjustService.getBudgetListByProjectIdAndStatusTypeList(rdmsBudget.getProjectId(), BudgetApplicantStatusEnum.SUBMIT.getStatus(), budgetTypeList);
            }

            for (RdmsBudgetAdjustDto budgetAdjustDto : budgetListByProjectId) {
                budgetAdjustDto.setDocType(DocTypeEnum.BUDGET_ADJUST.getType());
                budgetAdjustDto.setItemName(budgetAdjustDto.getTitle());  //为了统一线段字段 名称
                budgetAdjustDto.setIndex(index++);
            }
            rdmsBudgetDto.setChildren(budgetListByProjectId);
        }
        return rdmsBudgetDto;
    }

    @Transactional
    public long getBudgetManageJobNumByUserId(String userId) {
        RdmsBudgetExample budgetExample = new RdmsBudgetExample();
        budgetExample.createCriteria()
                .andApproverIdEqualTo(userId)
                .andDeletedEqualTo(0);
        List<RdmsBudget> budgetList = rdmsBudgetMapper.selectByExample(budgetExample);

        if (!CollectionUtils.isEmpty(budgetList)) {
            List<String> collectIdList = budgetList.stream().map(RdmsBudget::getId).collect(Collectors.toList());
            List<RdmsBudgetAdjust> budgetAdjustListByBudgetIdList = rdmsBudgetAdjustService.getBudgetAdjustListByBudgetIdList(collectIdList);
            if (budgetAdjustListByBudgetIdList != null) {
                return budgetAdjustListByBudgetIdList.size();
            } else {
                return 0L;
            }
        } else {
            return 0L;
        }
    }

    @Transactional
    public RdmsBudgetDto getProjectAndApplicationCompleteBudgetItems(String budgetId) {
        RdmsBudget rdmsBudget = this.selectByPrimaryKey(budgetId);
        if (rdmsBudget == null) {
            return new RdmsBudgetDto();
        }
        RdmsBudgetDto rdmsBudgetDto = CopyUtil.copy(rdmsBudget, RdmsBudgetDto.class);
        rdmsBudgetDto.setDocType(DocTypeEnum.BUDGET.getType());
        RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(rdmsBudget.getProjectId());
        if (!ObjectUtils.isEmpty(rdmsProject)) {
            rdmsBudgetDto.setItemName(rdmsProject.getProjectName());  //为了统一前端字段 名称
        }
        if (!ObjectUtils.isEmpty(rdmsBudgetDto)) {
            int index = 1;
            List<BudgetTypeEnum> budgetTypeList = new ArrayList<>();
            budgetTypeList.add(BudgetTypeEnum.PRE_PROJECT);
            budgetTypeList.add(BudgetTypeEnum.PROJECT);
            budgetTypeList.add(BudgetTypeEnum.PRODUCT);

            List<RdmsBudgetAdjustDto> budgetListByProjectId = rdmsBudgetAdjustService.getBudgetListByProjectIdAndStatusTypeList(rdmsBudget.getProjectId(), BudgetApplicantStatusEnum.COMPLETE.getStatus(), budgetTypeList);
            for (RdmsBudgetAdjustDto budgetAdjustDto : budgetListByProjectId) {
                budgetAdjustDto.setDocType(DocTypeEnum.BUDGET_ADJUST.getType());
                budgetAdjustDto.setItemName(budgetAdjustDto.getTitle());  //为了统一线段字段 名称
                budgetAdjustDto.setIndex(index++);
            }
            rdmsBudgetDto.setChildren(budgetListByProjectId);
        }
        return rdmsBudgetDto;
    }

    public String initPreProjectBudgetSheetItem(String applicantId /*预算使用人*/, @NotNull RdmsPreProject rdmsPreProject) {
        //创建一条分项预算记录
        RdmsBudgetResearchDto budgetData = new RdmsBudgetResearchDto();
        budgetData.setId(null);
        budgetData.setCustomerId(rdmsPreProject.getCustomerId());
        budgetData.setProjectId(null);
        budgetData.setParentId(null);
        budgetData.setSubprojectId(null);
        budgetData.setPreprojectId(rdmsPreProject.getId());
        budgetData.setStage(BudgetTypeEnum.PRE_PROJECT.getType());
        initBudgetResearchContent(budgetData);
        budgetData.setCreaterId(rdmsPreProject.getProductManagerId());
        budgetData.setIsRoot(1);
        RdmsBudgetResearch rdmsBudgetResearch = CopyUtil.copy(budgetData, RdmsBudgetResearch.class);
        rdmsBudgetResearchService.transformBudgetToYuan(rdmsBudgetResearch);
        String researchId = rdmsBudgetResearchService.save(rdmsBudgetResearch);//保存主项目记录

        //向Budget表中写入一条项目预算记录
        RdmsBudget budget = new RdmsBudget();
        budget.setCustomerId(rdmsPreProject.getCustomerId());
        budget.setProjectId(rdmsPreProject.getId());
        budget.setName(rdmsPreProject.getPreProjectName());
        budget.setType(BudgetTypeEnum.PRE_PROJECT.getType());
        budget.setApplicantId(applicantId);  //预算的使用者
        RdmsBossDto bossByCustomerId = rdmsBossService.getBossByCustomerId(rdmsPreProject.getCustomerId());
        budget.setApproverId(bossByCustomerId.getBossId());
        budget.setBudget(BigDecimal.ZERO);
        budget.setStatus(BudgetStatusEnum.CREATED.getStatus());
        String budgetId = this.save(budget);
        return budgetId;
    }

    public String initProductBudgetSheetItem(String applicantId /*预算使用人*/, @NotNull RdmsProject rdmsProject) {
        //创建一条分项预算记录
        RdmsBudgetResearchDto budgetData = new RdmsBudgetResearchDto();
        budgetData.setId(null);
        budgetData.setCustomerId(rdmsProject.getCustomerId());
        budgetData.setProjectId(rdmsProject.getId());
        budgetData.setParentId(null);
        budgetData.setSubprojectId(null);
        budgetData.setPreprojectId(null);
        budgetData.setStage(BudgetTypeEnum.PRODUCT.getType());

        initBudgetResearchContent(budgetData);
        budgetData.setCreaterId(rdmsProject.getProductManagerId());
        budgetData.setIsRoot(1);
        RdmsBudgetResearch rdmsBudgetResearch = CopyUtil.copy(budgetData, RdmsBudgetResearch.class);
        rdmsBudgetResearchService.transformBudgetToYuan(rdmsBudgetResearch);
        String researchId = rdmsBudgetResearchService.save(rdmsBudgetResearch);//保存主项目记录

        //创建对应主项目的子项目记录
        RdmsBudgetResearch budgetResearch = rdmsBudgetResearchService.selectByPrimaryKey(researchId);
        RdmsBudgetResearch budgetResearchCopy = CopyUtil.copy(budgetResearch, RdmsBudgetResearch.class);
        budgetResearchCopy.setId(null);
        budgetResearchCopy.setParentId(budgetResearch.getProjectId());
        budgetResearchCopy.setSubprojectId(budgetResearch.getProjectId());
        budgetResearchCopy.setStage(BudgetTypeEnum.SUB_PROJECT.getType());   //任然使用SUB_project
        budgetResearchCopy.setIsRoot(0);
        rdmsBudgetResearchService.save(budgetResearchCopy);

        //向Budget表中写入一条项目预算记录
        RdmsBudget budget = new RdmsBudget();
        budget.setCustomerId(rdmsProject.getCustomerId());
        budget.setProjectId(rdmsProject.getId());
        budget.setName(rdmsProject.getProjectName());
        budget.setType(BudgetTypeEnum.PRODUCT.getType());
        budget.setApplicantId(applicantId);  //预算的使用者
        RdmsBossDto bossByCustomerId = rdmsBossService.getBossByCustomerId(rdmsProject.getCustomerId());
        budget.setApproverId(bossByCustomerId.getBossId());
        budget.setBudget(BigDecimal.ZERO);
        budget.setStatus(BudgetStatusEnum.CREATED.getStatus());
        String budgetId = this.save(budget);
        return budgetId;
    }

    private static void initBudgetResearchContent(RdmsBudgetResearchDto budgetData) {
        budgetData.setEquipmentFeeBt(BigDecimal.ZERO);
        budgetData.setEquipmentFeeZc(BigDecimal.ZERO);
        budgetData.setEquipmentFeeApprovedBt(BigDecimal.ZERO);
        budgetData.setEquipmentFeeApprovedZc(BigDecimal.ZERO);
        budgetData.setTestFeeBt(BigDecimal.ZERO);
        budgetData.setTestFeeZc(BigDecimal.ZERO);
        budgetData.setTestFeeApprovedBt(BigDecimal.ZERO);
        budgetData.setTestFeeApprovedZc(BigDecimal.ZERO);
        budgetData.setMaterialFeeBt(BigDecimal.ZERO);
        budgetData.setMaterialFeeZc(BigDecimal.ZERO);
        budgetData.setMaterialFeeApprovedBt(BigDecimal.ZERO);
        budgetData.setMaterialFeeApprovedZc(BigDecimal.ZERO);
        budgetData.setPowerFeeBt(BigDecimal.ZERO);
        budgetData.setPowerFeeZc(BigDecimal.ZERO);
        budgetData.setPowerFeeApprovedBt(BigDecimal.ZERO);
        budgetData.setPowerFeeApprovedZc(BigDecimal.ZERO);
        budgetData.setConferenceFeeBt(BigDecimal.ZERO);
        budgetData.setConferenceFeeZc(BigDecimal.ZERO);
        budgetData.setConferenceFeeApprovedBt(BigDecimal.ZERO);
        budgetData.setConferenceFeeApprovedZc(BigDecimal.ZERO);
        budgetData.setBusinessFeeBt(BigDecimal.ZERO);
        budgetData.setBusinessFeeZc(BigDecimal.ZERO);
        budgetData.setBusinessFeeApprovedBt(BigDecimal.ZERO);
        budgetData.setBusinessFeeApprovedZc(BigDecimal.ZERO);
        budgetData.setCooperationFeeBt(BigDecimal.ZERO);
        budgetData.setCooperationFeeZc(BigDecimal.ZERO);
        budgetData.setCooperationFeeApprovedBt(BigDecimal.ZERO);
        budgetData.setCooperationFeeApprovedZc(BigDecimal.ZERO);
        budgetData.setPropertyFeeBt(BigDecimal.ZERO);
        budgetData.setPropertyFeeZc(BigDecimal.ZERO);
        budgetData.setPropertyFeeApprovedBt(BigDecimal.ZERO);
        budgetData.setPropertyFeeApprovedZc(BigDecimal.ZERO);
        budgetData.setLaborFeeBt(BigDecimal.ZERO);
        budgetData.setLaborFeeZc(BigDecimal.ZERO);
        budgetData.setLaborFeeApprovedBt(BigDecimal.ZERO);
        budgetData.setLaborFeeApprovedZc(BigDecimal.ZERO);
        budgetData.setStaffFeeBt(BigDecimal.ZERO);
        budgetData.setStaffFeeZc(BigDecimal.ZERO);
        budgetData.setStaffFeeApprovedBt(BigDecimal.ZERO);
        budgetData.setStaffFeeApprovedZc(BigDecimal.ZERO);
        budgetData.setConsultingFeeBt(BigDecimal.ZERO);
        budgetData.setConsultingFeeZc(BigDecimal.ZERO);
        budgetData.setConsultingFeeApprovedBt(BigDecimal.ZERO);
        budgetData.setConsultingFeeApprovedZc(BigDecimal.ZERO);
        budgetData.setManagementFeeBt(BigDecimal.ZERO);
        budgetData.setManagementFeeZc(BigDecimal.ZERO);
        budgetData.setManagementFeeApprovedBt(BigDecimal.ZERO);
        budgetData.setManagementFeeApprovedZc(BigDecimal.ZERO);
        budgetData.setOtherFeeBt(BigDecimal.ZERO);
        budgetData.setOtherFeeZc(BigDecimal.ZERO);
        budgetData.setOtherFeeApprovedBt(BigDecimal.ZERO);
        budgetData.setOtherFeeApprovedZc(BigDecimal.ZERO);
        budgetData.setPerformanceFeeBt(BigDecimal.ZERO);
        budgetData.setPerformanceFeeZc(BigDecimal.ZERO);
        budgetData.setPerformanceFeeApprovedBt(BigDecimal.ZERO);
        budgetData.setPerformanceFeeApprovedZc(BigDecimal.ZERO);
        budgetData.setInfrastructureFeeBt(BigDecimal.ZERO);
        budgetData.setInfrastructureFeeZc(BigDecimal.ZERO);
        budgetData.setInfrastructureFeeApprovedBt(BigDecimal.ZERO);
        budgetData.setInfrastructureFeeApprovedZc(BigDecimal.ZERO);

    }

    @Transactional
    public RdmsBudget getBudgetItemByProjectId(String projectId) {
        RdmsBudgetExample budgetExample = new RdmsBudgetExample();
        budgetExample.setOrderByClause("create_time desc");
        budgetExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andTypeEqualTo(BudgetTypeEnum.PROJECT.getType())
                .andDeletedEqualTo(0);
        List<RdmsBudget> budgetList = rdmsBudgetMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(budgetList)) {
            return budgetList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public RdmsBudget getBudgetItemByPreProjectId(String preprojectId) {
        RdmsBudgetExample budgetExample = new RdmsBudgetExample();
        budgetExample.setOrderByClause("create_time desc");
        budgetExample.createCriteria()
                .andProjectIdEqualTo(preprojectId)
                .andTypeEqualTo(BudgetTypeEnum.PRE_PROJECT.getType())
                .andDeletedEqualTo(0);
        List<RdmsBudget> budgetList = rdmsBudgetMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(budgetList)) {
            return budgetList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public RdmsBudget getBudgetItemByProductId(String productId) {
        RdmsBudgetExample budgetExample = new RdmsBudgetExample();
        budgetExample.setOrderByClause("create_time desc");
        budgetExample.createCriteria()
                .andProjectIdEqualTo(productId)
                .andTypeEqualTo(BudgetTypeEnum.PRODUCT.getType())
                .andDeletedEqualTo(0);
        List<RdmsBudget> budgetList = rdmsBudgetMapper.selectByExample(budgetExample);
        if (!CollectionUtils.isEmpty(budgetList)) {
            return budgetList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public void list(PageDto<RdmsBudgetDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsBudgetExample budgetExample = new RdmsBudgetExample();
        budgetExample.setOrderByClause("create_time desc");
        budgetExample.createCriteria()
                .andCustomerIdEqualTo(pageDto.getCustomerId())
                .andApproverIdEqualTo(pageDto.getLoginUserId())  //审批人
                .andStatusEqualTo(BudgetStatusEnum.CREATED.getStatus())
                .andDeletedEqualTo(0);
        List<RdmsBudget> budgetList = rdmsBudgetMapper.selectByExample(budgetExample);
        PageInfo<RdmsBudget> pageInfo = new PageInfo<>(budgetList);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsBudgetDto> rdmsBudgetDtos = CopyUtil.copyList(budgetList, RdmsBudgetDto.class);
        if (!CollectionUtils.isEmpty(rdmsBudgetDtos)) {
            for (RdmsBudgetDto budgetDto : rdmsBudgetDtos) {
                if (!ObjectUtils.isEmpty(budgetDto.getProjectId())) {
                    if (budgetDto.getType().equals(BudgetTypeEnum.PROJECT.getType())) {
                        RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(budgetDto.getProjectId());
                        if (rdmsProject != null) {
                            budgetDto.setProjectName(rdmsProject.getProjectName());
                            budgetDto.setProjectCode(rdmsProject.getProjectCode());
                        }
                    } else if (budgetDto.getType().equals(BudgetTypeEnum.PRODUCT.getType())) {
                        RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(budgetDto.getProjectId());
                        if (rdmsProject != null) {
                            budgetDto.setProjectName(rdmsProject.getProjectName());
                            budgetDto.setProjectCode(rdmsProject.getProjectCode());
                        }
                    } else if (budgetDto.getType().equals(BudgetTypeEnum.PRE_PROJECT.getType())) {
                        RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(budgetDto.getProjectId());
                        if (rdmsPreProject != null) {
                            budgetDto.setProjectName(rdmsPreProject.getPreProjectName());
                            budgetDto.setProjectCode(rdmsPreProject.getProjectCode());
                        }
                    }
                }
                if (!ObjectUtils.isEmpty(budgetDto.getApplicantId())) {
                    RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(budgetDto.getApplicantId());
                    if (rdmsCustomerUser != null) {
                        budgetDto.setApplicantName(rdmsCustomerUser.getTrueName());
                    }
                }
                if (!ObjectUtils.isEmpty(budgetDto.getApproverId())) {
                    RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(budgetDto.getApproverId());
                    if (rdmsCustomerUser != null) {
                        budgetDto.setApproverName(rdmsCustomerUser.getTrueName());
                    }
                }
                //相应预算条目存在SUBMIT状态的预算申请
                budgetDto.setHasSubmitBudgetAdjust(rdmsBudgetAdjustService.hasSubmitBudgetAdjust(budgetDto.getId()));
            }
        }

        pageDto.setList(rdmsBudgetDtos);
    }

    @Transactional
    public void addNotifyInfo(@NotNull RdmsBudgetAdjust budgetAdjust, String jobName, String projectType) {
        //创建一个预算申请驳回通知 jobitem
        RdmsJobItem notifyJobitem = new RdmsJobItem();
        notifyJobitem.setId(null);
        notifyJobitem.setJobSerial(null);
        notifyJobitem.setJobName(jobName);
        StringBuilder descriptionStr = new StringBuilder();
        RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(budgetAdjust.getApproverId());

        if (projectType.equals(ProjectTypeEnum.PRE_PROJECT.getType())) {
            RdmsPreProject rdmsProject = rdmsPreProjectService.selectByPrimaryKey(budgetAdjust.getPreProjectId());
            descriptionStr
                    .append("项目编号: ").append(budgetAdjust.getCode()).append("<br/>")
                    .append("项目名称: ").append(rdmsProject.getPreProjectName()).append("<br/>")
                    .append("申请标题: ").append(budgetAdjust.getTitle()).append("<br/>")
                    .append("费用类型: ").append(Objects.requireNonNull(BudgetClassifyStdEnum.getBudgetClassifyEnumByClassify(budgetAdjust.getBudgetSubject())).getClassifyName()).append("<br/>")
                    .append("申请金额: ").append(budgetAdjust.getAmount()).append("元 <br/>")
                    .append("申请原因: ").append(budgetAdjust.getReason()).append("<br/>")
                    .append("审批结果: ").append(Objects.requireNonNull(BudgetApplicantStatusEnum.getBudgetApplicantStatusEnumByStatus(budgetAdjust.getStatus())).getName()).append("<br/>")
                    .append("审批意见: ").append(budgetAdjust.getApprovalOpinion()).append("<br/>")
                    .append("审批领导: ").append(rdmsCustomerUser.getTrueName()).append("<br/>");

            notifyJobitem.setTaskDescription(descriptionStr.toString());
            notifyJobitem.setPreProjectId(budgetAdjust.getProjectId());
            notifyJobitem.setProjectType(ProjectTypeEnum.PRE_PROJECT.getType());
        } else {
            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(budgetAdjust.getProjectId());
            descriptionStr
                    .append("项目编号: ").append(budgetAdjust.getCode()).append("<br/>")
                    .append("项目名称: ").append(rdmsProject.getProjectName()).append("<br/>")
                    .append("申请标题: ").append(budgetAdjust.getTitle()).append("<br/>")
                    .append("费用类型: ").append(Objects.requireNonNull(BudgetClassifyStdEnum.getBudgetClassifyEnumByClassify(budgetAdjust.getBudgetSubject())).getClassifyName()).append("<br/>")
                    .append("申请金额: ").append(budgetAdjust.getAmount()).append("元 <br/>")
                    .append("申请原因: ").append(budgetAdjust.getReason()).append("<br/>")
                    .append("审批结果: ").append(Objects.requireNonNull(BudgetApplicantStatusEnum.getBudgetApplicantStatusEnumByStatus(budgetAdjust.getStatus())).getName()).append("<br/>")
                    .append("审批意见: ").append(budgetAdjust.getApprovalOpinion()).append("<br/>")
                    .append("审批领导: ").append(rdmsCustomerUser.getTrueName()).append("<br/>");

            notifyJobitem.setTaskDescription(descriptionStr.toString());
            notifyJobitem.setProjectId(budgetAdjust.getProjectId());
            notifyJobitem.setProjectType(ProjectTypeEnum.PROJECT.getType());
        }

        notifyJobitem.setCustomerId(budgetAdjust.getCustomerId());
        notifyJobitem.setSubprojectId(null);
        notifyJobitem.setProjectManagerId(null);
        notifyJobitem.setProductManagerId(null);
        notifyJobitem.setFileListStr("[]");
        notifyJobitem.setManhour(0.0);

        notifyJobitem.setExecutorId(budgetAdjust.getApplicantId());
        notifyJobitem.setNextNode(null);
        notifyJobitem.setStatus(JobItemStatusEnum.HANDLING.getStatus());
        notifyJobitem.setReviewResult(null);
        notifyJobitem.setType(JobItemTypeEnum.BUDGET_ADJUST_NOTIFY.getType());
        notifyJobitem.setParentJobitemId(null);
        notifyJobitem.setPropertyId(null);
        notifyJobitem.setReviewWorkerIdStr(null);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 2);
        Date dateTime = calendar.getTime();
        notifyJobitem.setPlanSubmissionTime(dateTime);
        rdmsJobItemService.save(notifyJobitem);
    }


    public RdmsBudget selectByPrimaryKey(String id) {
        return rdmsBudgetMapper.selectByPrimaryKey(id);
    }

    /**
     * 保存
     */
    public String save(RdmsBudget budget) {
        if (ObjectUtils.isEmpty(budget.getId())) {
            return this.insert(budget);
        } else {
            RdmsBudget rdmsBudget = this.selectByPrimaryKey(budget.getId());
            if (ObjectUtils.isEmpty(rdmsBudget)) {
                return this.insert(budget);
            } else {
                return this.update(budget);
            }
        }
    }


    private String insert(RdmsBudget budget) {
        if (ObjectUtils.isEmpty(budget.getId())) {  //当前端页面给出projectID时,将不为空
            budget.setId(UuidUtil.getShortUuid());
        }
        RdmsBudget rdmsBudget = this.selectByPrimaryKey(budget.getId());
        if (ObjectUtils.isEmpty(rdmsBudget)) {
            budget.setUpdateTime(new Date());
            budget.setCreateTime(new Date());
            budget.setDeleted(0);
            rdmsBudgetMapper.insert(budget);
            return budget.getId();
        } else {
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }
    }

    public String update(RdmsBudget budget) {
        if (ObjectUtils.isEmpty(budget.getId())) {
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsBudget rdmsBudget = this.selectByPrimaryKey(budget.getId());
        if (ObjectUtils.isEmpty(rdmsBudget)) {
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        } else {
            budget.setDeleted(0);
            budget.setUpdateTime(new Date());
            rdmsBudgetMapper.updateByPrimaryKey(budget);
            return budget.getId();
        }
    }

    public String updateByPrimaryKeySelective(RdmsBudget budget) {
        budget.setUpdateTime(new Date());
        rdmsBudgetMapper.updateByPrimaryKeySelective(budget);
        return budget.getId();
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsBudget rdmsBudget = this.selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(rdmsBudget)) {
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        } else {
            rdmsBudget.setDeleted(1);
            rdmsBudgetMapper.updateByPrimaryKey(rdmsBudget);
        }
    }

}
