/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import com.course.server.domain.*;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.RdmsBudgetAdjustDto;
import com.course.server.dto.rdms.RdmsBudgetDto;
import com.course.server.enums.rdms.*;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.service.rdms.*;
import com.course.server.util.CopyUtil;
import com.course.server.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import javax.annotation.Resource;

@RestController
@RequestMapping("/budget-mng")
public class BudgetController {
    private static final Logger LOG = LoggerFactory.getLogger(BudgetController.class);
    public static final String BUSINESS_NAME = "预算管理";

    @Resource
    private RdmsBudgetService rdmsBudgetService;
    @Resource
    private RdmsBudgetAdjustService rdmsBudgetAdjustService;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private RdmsBudgetResearchService rdmsBudgetResearchService;
    @Resource
    private RdmsProjectService rdmsProjectService;
    @Autowired
    private RdmsPreProjectService rdmsPreProjectService;

    @PostMapping("/list")
    public ResponseDto<PageDto<RdmsBudgetDto>> list(@RequestBody PageDto<RdmsBudgetDto> pageDto) {
        ResponseDto<PageDto<RdmsBudgetDto>> responseDto = new ResponseDto<>();

        ValidatorUtil.require(pageDto.getPage(), "页码");
        ValidatorUtil.require(pageDto.getSize(), "条目数");
        ValidatorUtil.require(pageDto.getCustomerId(), "机构ID");
        ValidatorUtil.require(pageDto.getLoginUserId(), "登录用户");

        ValidatorUtil.length(pageDto.getCustomerId(), "需求名称", 8, 8);

        rdmsBudgetService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/getBudgetManageJobNumByUserId/{userId}")
    public ResponseDto<Long> getBudgetManageJobNumByUserId(@PathVariable String userId) {
        ResponseDto<Long> responseDto = new ResponseDto<>();
        long testManageNum = rdmsBudgetService.getBudgetManageJobNumByUserId(userId);
        responseDto.setContent(testManageNum);
        return responseDto;
    }

    @PostMapping("/getProjectAndApplicationBudgetItems/{budgetId}")
    public ResponseDto<RdmsBudgetDto> getProjectAndApplicationBudgetItems(@PathVariable String budgetId) {
        ResponseDto<RdmsBudgetDto> responseDto = new ResponseDto<>();
        RdmsBudgetDto projectAndBudgetList = rdmsBudgetService.getProjectAndApplicationBudgetItems(budgetId);
        responseDto.setContent(projectAndBudgetList);
        return responseDto;
    }

    @PostMapping("/getProjectAndApplicationCompleteBudgetItems/{budgetId}")
    public ResponseDto<RdmsBudgetDto> getProjectAndApplicationCompleteBudgetItems(@PathVariable String budgetId) {
        ResponseDto<RdmsBudgetDto> responseDto = new ResponseDto<>();
        RdmsBudgetDto projectAndBudgetList = rdmsBudgetService.getProjectAndApplicationCompleteBudgetItems(budgetId);
        responseDto.setContent(projectAndBudgetList);
        return responseDto;
    }

    @PostMapping("/saveBudgetApplication")
    @Transactional
    public ResponseDto<String> saveBudgetApplication(@RequestBody RdmsBudgetAdjustDto budgetAdjustDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        ValidatorUtil.require(budgetAdjustDto.getCode(), "申请单号");
        ValidatorUtil.require(budgetAdjustDto.getTitle(), "申请标题");
        ValidatorUtil.require(budgetAdjustDto.getCustomerId(), "公司Id");
        ValidatorUtil.require(budgetAdjustDto.getAmount(), "申请金额");
        ValidatorUtil.require(budgetAdjustDto.getReason(), "申请原因");
        ValidatorUtil.require(budgetAdjustDto.getApplicantId(), "申请人");
        ValidatorUtil.require(budgetAdjustDto.getApproverId(), "审批人");
        ValidatorUtil.require(budgetAdjustDto.getBudgetSubject(), "费用科目");
        ValidatorUtil.require(budgetAdjustDto.getType(), "预算类型");   //后台程序对应BudgetTypeEnums

        ValidatorUtil.length(budgetAdjustDto.getTitle(), "申请标题", 2, 30);
        ValidatorUtil.length(budgetAdjustDto.getReason(), "申请原因", 6, 800);

        RdmsBudgetAdjust budgetAdjust = new RdmsBudgetAdjustDto();
        if(budgetAdjustDto.getType().equals(BudgetTypeEnum.PRE_PROJECT.getType())){
            ValidatorUtil.require(budgetAdjustDto.getPreProjectId(), "项目Id");
            ValidatorUtil.require(budgetAdjustDto.getType(), "项目类型");

            ValidatorUtil.length(budgetAdjustDto.getType(), "项目类型", 2, 20);

            budgetAdjust = CopyUtil.copy(budgetAdjustDto, RdmsBudgetAdjust.class);
            //查看budget表, 如果没有预算管理记录,则创建一个; 如果有了,就读取出来
            RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(budgetAdjustDto.getPreProjectId());
            RdmsBudget budgetItemByProjectId = rdmsBudgetService.getBudgetItemByPreProjectId(budgetAdjustDto.getPreProjectId());
            if(ObjectUtils.isEmpty(budgetItemByProjectId)){
                //初始化预算表
                String budgetId = rdmsBudgetService.initPreProjectBudgetSheetItem(rdmsPreProject.getSystemManagerId(), rdmsPreProject);
                budgetAdjust.setBudgetId(budgetId);
            }else{
                budgetAdjust.setBudgetId(budgetItemByProjectId.getId());
            }
            rdmsBudgetAdjustService.save(budgetAdjust);

        }else if(budgetAdjustDto.getType().equals(BudgetTypeEnum.PROJECT.getType())){
            ValidatorUtil.require(budgetAdjustDto.getProjectId(), "项目Id");
            ValidatorUtil.require(budgetAdjustDto.getType(), "项目类型");

            ValidatorUtil.length(budgetAdjustDto.getType(), "项目类型", 2, 20);
            //保存申请表
            budgetAdjust = CopyUtil.copy(budgetAdjustDto, RdmsBudgetAdjust.class);
            RdmsBudget budgetItemByProjectId = rdmsBudgetService.getBudgetItemByProjectId(budgetAdjustDto.getProjectId());
            if(ObjectUtils.isEmpty(budgetItemByProjectId)){
                RdmsBudget rdmsBudget = new RdmsBudget();
                rdmsBudget.setCustomerId(budgetAdjustDto.getCustomerId());
                rdmsBudget.setProjectId(budgetAdjustDto.getProjectId());
                RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(budgetAdjustDto.getProjectId());
                rdmsBudget.setName(rdmsProject.getProjectName());
                rdmsBudget.setType(budgetAdjustDto.getType());
                rdmsBudget.setApplicantId(budgetAdjustDto.getApplicantId());
                rdmsBudget.setApproverId(budgetAdjustDto.getApproverId());
                rdmsBudget.setBudget(rdmsProject.getBudget().add(rdmsProject.getAddCharge()));
                rdmsBudget.setStatus(BudgetStatusEnum.CREATED.getStatus());
                String id = rdmsBudgetService.save(rdmsBudget);

                budgetAdjust.setBudgetId(id);
                rdmsBudgetAdjustService.save(budgetAdjust);
            }else{
                budgetAdjust.setBudgetId(budgetItemByProjectId.getId());
                rdmsBudgetAdjustService.save(budgetAdjust);
            }
        }else if(budgetAdjustDto.getType().equals(BudgetTypeEnum.PRODUCT.getType())){
            ValidatorUtil.require(budgetAdjustDto.getProjectId(), "项目Id");
            ValidatorUtil.require(budgetAdjustDto.getType(), "项目类型");

            ValidatorUtil.length(budgetAdjustDto.getType(), "项目类型", 2, 20);
            //保存申请表
            budgetAdjust = CopyUtil.copy(budgetAdjustDto, RdmsBudgetAdjust.class);
            RdmsBudget budgetItemByProjectId = rdmsBudgetService.getBudgetItemByProductId(budgetAdjustDto.getProjectId());
            if(ObjectUtils.isEmpty(budgetItemByProjectId)){
                RdmsBudget rdmsBudget = new RdmsBudget();
                rdmsBudget.setCustomerId(budgetAdjustDto.getCustomerId());
                rdmsBudget.setProjectId(budgetAdjustDto.getProjectId());
                RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(budgetAdjustDto.getProjectId());
                rdmsBudget.setName(rdmsProject.getProjectName());
                rdmsBudget.setType(budgetAdjustDto.getType());
                rdmsBudget.setApplicantId(budgetAdjustDto.getApplicantId());
                rdmsBudget.setApproverId(budgetAdjustDto.getApproverId());
                rdmsBudget.setBudget(rdmsProject.getBudget().add(rdmsProject.getAddCharge()));
                rdmsBudget.setStatus(BudgetStatusEnum.CREATED.getStatus());
                String id = rdmsBudgetService.save(rdmsBudget);

                budgetAdjust.setBudgetId(id);
                rdmsBudgetAdjustService.save(budgetAdjust);
            }else{
                budgetAdjust.setBudgetId(budgetItemByProjectId.getId());
                rdmsBudgetAdjustService.save(budgetAdjust);
            }
        }else{
            LOG.error("保存预算申请失败, 函数名称 saveBudgetApplication");
            throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
        }
        responseDto.setContent(budgetAdjust.getId());
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/saveBudgetDistribution")
    @Transactional
    public ResponseDto<String> saveBudgetDistribution(@RequestBody RdmsBudgetAdjustDto budgetAdjustDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        ValidatorUtil.require(budgetAdjustDto.getCode(), "申请单号");
        ValidatorUtil.require(budgetAdjustDto.getTitle(), "申请标题");
        ValidatorUtil.require(budgetAdjustDto.getCustomerId(), "公司Id");
        ValidatorUtil.require(budgetAdjustDto.getProjectId(), "项目Id");
        ValidatorUtil.require(budgetAdjustDto.getAmount(), "申请金额");
        ValidatorUtil.require(budgetAdjustDto.getReason(), "申请原因");
//        ValidatorUtil.require(budgetAdjustDto.getApplicantId(), "申请人");
        ValidatorUtil.require(budgetAdjustDto.getApproverId(), "审批人");
        ValidatorUtil.require(budgetAdjustDto.getType(), "费用科目");

        ValidatorUtil.length(budgetAdjustDto.getTitle(), "申请标题", 2, 30);
        ValidatorUtil.length(budgetAdjustDto.getReason(), "申请原因", 6, 800);
        ValidatorUtil.length(budgetAdjustDto.getType(), "费用科目", 2, 20);

        //保存申请表
        RdmsBudgetAdjust budgetAdjust = CopyUtil.copy(budgetAdjustDto, RdmsBudgetAdjust.class);
//        RdmsBudget budgetItemByProjectId = rdmsBudgetService.getBudgetItemByProjectId(budgetAdjustDto.getProjectId());
//        budgetAdjust.setBudgetId(budgetItemByProjectId.getId());
        rdmsBudgetAdjustService.save(budgetAdjust);

        responseDto.setContent(budgetAdjust.getId());
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/getExamineNum/{projectId}")
    public ResponseDto<Integer> getExamineNum(@PathVariable String projectId) {
        ResponseDto<Integer> responseDto = new ResponseDto<>();
        List<BudgetApplicantStatusEnum> statusEnumList = new ArrayList<>();
        statusEnumList.add(BudgetApplicantStatusEnum.SUBMIT);
        Integer examineNum = rdmsBudgetAdjustService.getBudgetAdjustListByProjectIdAndStatusList(projectId, statusEnumList);
        responseDto.setContent(examineNum);
        return responseDto;
    }

    @PostMapping("/getBudgetSimpleInfo/{budgetId}")
    public ResponseDto<RdmsBudgetDto> getBudgetSimpleInfo(@PathVariable String budgetId) {
        ResponseDto<RdmsBudgetDto> responseDto = new ResponseDto<>();
        RdmsBudget rdmsBudget = rdmsBudgetService.selectByPrimaryKey(budgetId);
        RdmsBudgetDto budgetDto = CopyUtil.copy(rdmsBudget, RdmsBudgetDto.class);
        responseDto.setContent(budgetDto);
        return responseDto;
    }

    @PostMapping("/getBudgetAdjustSimpleInfo/{budgetAdjustId}")
    public ResponseDto<RdmsBudgetAdjustDto> getBudgetAdjustSimpleInfo(@PathVariable String budgetAdjustId) {
        ResponseDto<RdmsBudgetAdjustDto> responseDto = new ResponseDto<>();
        RdmsBudgetAdjust budgetAdjust = rdmsBudgetAdjustService.selectByPrimaryKey(budgetAdjustId);
        RdmsBudgetAdjustDto budgetAdjustDto = CopyUtil.copy(budgetAdjust, RdmsBudgetAdjustDto.class);
        if (!ObjectUtils.isEmpty(budgetAdjustDto)) {
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(budgetAdjustDto.getApplicantId());
            budgetAdjustDto.setApplicantName(rdmsCustomerUser.getTrueName());
            RdmsCustomerUser rdmsCustomerUser1 = rdmsCustomerUserService.selectByPrimaryKey(budgetAdjustDto.getApproverId());
            budgetAdjustDto.setApproverName(rdmsCustomerUser1.getTrueName());
        }
        responseDto.setContent(budgetAdjustDto);
        return responseDto;
    }

    @Transactional
    @PostMapping("/approvalPass")
    public ResponseDto<String> approvalPass(@RequestBody RdmsBudgetAdjustDto budgetAdjustDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        if (!ObjectUtils.isEmpty(budgetAdjustDto) && !ObjectUtils.isEmpty(budgetAdjustDto.getId())) {
            RdmsBudgetAdjust budgetAdjust = rdmsBudgetAdjustService.selectByPrimaryKey(budgetAdjustDto.getId());
            budgetAdjust.setApprovalOpinion(budgetAdjustDto.getApprovalOpinion());
            budgetAdjust.setStatus(BudgetApplicantStatusEnum.COMPLETE.getStatus());
            String adjustId = rdmsBudgetAdjustService.update(budgetAdjust);

            //审批完成,修改预算
            if(budgetAdjustDto.getType().equals(BudgetTypeEnum.PRE_PROJECT.getType())){
                rdmsBudgetResearchService.adjustPreProjectSubjectBudget(budgetAdjustDto);
            }else if(budgetAdjustDto.getType().equals(BudgetTypeEnum.PRODUCT.getType())){
                rdmsBudgetResearchService.adjustProductSubjectBudget(budgetAdjustDto);
            }else {
                rdmsBudgetResearchService.adjustMainProjectSubjectBudget(budgetAdjustDto);
            }

            //添加通知信息
            rdmsBudgetService.addNotifyInfo(budgetAdjust, "预算申请通过通知", budgetAdjustDto.getType());

            responseDto.setContent(adjustId);
            return responseDto;
        } else {
            throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
        }
    }

    @Transactional
    @PostMapping("/approvalReject")
    public ResponseDto<String> approvalReject(@RequestBody RdmsBudgetAdjustDto budgetAdjustDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        if (!ObjectUtils.isEmpty(budgetAdjustDto) && !ObjectUtils.isEmpty(budgetAdjustDto.getId())) {
            RdmsBudgetAdjust budgetAdjust = rdmsBudgetAdjustService.selectByPrimaryKey(budgetAdjustDto.getId());
            budgetAdjust.setApprovalOpinion(budgetAdjustDto.getApprovalOpinion());
            budgetAdjust.setStatus(BudgetApplicantStatusEnum.REFUSED.getStatus());
            String update = rdmsBudgetAdjustService.update(budgetAdjust);
            //添加通知信息
            rdmsBudgetService.addNotifyInfo(budgetAdjust, "预算申请驳回通知", budgetAdjustDto.getType());

            responseDto.setContent(update);
            return responseDto;
        } else {
            throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
        }
    }

}
