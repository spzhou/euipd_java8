/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import com.course.server.domain.RdmsBudgetResearch;
import com.course.server.dto.PageDto;
import com.course.server.dto.RdmsBudgetResearchDto;
import com.course.server.dto.ResponseDto;
import com.course.server.enums.rdms.BudgetTypeEnum;
import com.course.server.service.rdms.RdmsBudgetResearchService;
import com.course.server.service.rdms.RdmsProjectService;
import com.course.server.service.rdms.RdmsSubprojectService;
import com.course.server.util.CopyUtil;
import com.course.server.util.ValidatorUtil;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

@RestController
@RequestMapping("/budget-direct")
public class BudgetResearchController {
    private static final Logger LOG = LoggerFactory.getLogger(BudgetResearchController.class);
    public static final String BUSINESS_NAME = "直接立项预算管理";

    @Resource
    private RdmsBudgetResearchService rdmsBudgetResearchService;

    @PostMapping("/list")
    public ResponseDto<PageDto<RdmsBudgetResearchDto>> list(@RequestBody PageDto<RdmsBudgetResearchDto> pageDto) {
        ResponseDto<PageDto<RdmsBudgetResearchDto>> responseDto = new ResponseDto<>();
        rdmsBudgetResearchService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/saveBudget")
    @Transactional
    public ResponseDto<String> saveBudget(@RequestBody RdmsBudgetResearchDto budgetData) {
        ResponseDto<String> responseDto = new ResponseDto<>();

        ValidatorUtil.require(budgetData.getCustomerId(), "客户ID");
        ValidatorUtil.require(budgetData.getProjectId(), "项目ID");
        ValidatorUtil.require(budgetData.getStage(), "预算阶段");

        ValidatorUtil.length(budgetData.getCustomerId(), "需求名称", 8, 8);
        ValidatorUtil.length(budgetData.getProjectId(), "项目ID", 8, 8);

        RdmsBudgetResearchDto recordByProjectAndStage = rdmsBudgetResearchService.getRecordByProjectIdAndStage(budgetData.getProjectId(), Objects.requireNonNull(BudgetTypeEnum.getBudgetTypeEnumByType(budgetData.getStage())));
        //重复点击保存按钮时,更新数据
        if(!ObjectUtils.isEmpty(recordByProjectAndStage) && recordByProjectAndStage.getHasRecord()){
            budgetData.setId(recordByProjectAndStage.getId());
            budgetData.setSubprojectId(null);
            budgetData.setStage(BudgetTypeEnum.PROJECT.getType());
            budgetData.setParentId(null);
            budgetData.setIsRoot(1);
            RdmsBudgetResearch rdmsBudgetResearch = CopyUtil.copy(budgetData, RdmsBudgetResearch.class);
            rdmsBudgetResearchService.transformBudgetToYuan(rdmsBudgetResearch);
            String s = rdmsBudgetResearchService.save(rdmsBudgetResearch);//保存主项目记录

            //作为子项目处理的主项目记录
            RdmsBudgetResearchDto dbRecord = rdmsBudgetResearchService.getRecordBySubProjectIdAndStage(budgetData.getProjectId(), BudgetTypeEnum.SUB_PROJECT);
            if(!ObjectUtils.isEmpty(dbRecord) && dbRecord.getHasRecord()){
                rdmsBudgetResearch.setId(dbRecord.getId());
                rdmsBudgetResearch.setParentId(budgetData.getProjectId());
                rdmsBudgetResearch.setSubprojectId(budgetData.getProjectId());
                rdmsBudgetResearch.setStage(BudgetTypeEnum.SUB_PROJECT.getType());
                rdmsBudgetResearch.setIsRoot(0);
                rdmsBudgetResearchService.update(rdmsBudgetResearch); //保存作为子项目使用的copy
                responseDto.setContent(s);
            }else{
                rdmsBudgetResearch.setId(null);
                rdmsBudgetResearch.setParentId(budgetData.getProjectId());
                rdmsBudgetResearch.setSubprojectId(budgetData.getProjectId());
                rdmsBudgetResearch.setStage(BudgetTypeEnum.SUB_PROJECT.getType());
                rdmsBudgetResearch.setIsRoot(0);
                rdmsBudgetResearchService.save(rdmsBudgetResearch); //保存作为子项目使用的copy
                responseDto.setContent(s);
            }
        }else{
            RdmsBudgetResearch rdmsBudgetResearch = CopyUtil.copy(budgetData, RdmsBudgetResearch.class);
            rdmsBudgetResearch.setStage(BudgetTypeEnum.SUB_PROJECT.getType());
            rdmsBudgetResearchService.transformBudgetToYuan(rdmsBudgetResearch);
            String id = rdmsBudgetResearchService.save(rdmsBudgetResearch); //保存作为子项目使用的记录

            //保存作为主项目的原始批准预算记录
            rdmsBudgetResearch.setId(null);
            rdmsBudgetResearch.setSubprojectId(null);
            rdmsBudgetResearch.setParentId(null);
            rdmsBudgetResearch.setStage(BudgetTypeEnum.PROJECT.getType());
            rdmsBudgetResearch.setIsRoot(1);
            rdmsBudgetResearchService.save(rdmsBudgetResearch); //保存主项目记录

            responseDto.setContent(id);
        }

        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/saveBudget_subproject")
    @Transactional
    public ResponseDto<String> saveBudget_subproject(@RequestBody RdmsBudgetResearchDto budgetData) {
        ResponseDto<String> responseDto = new ResponseDto<>();

        ValidatorUtil.require(budgetData.getCustomerId(), "客户ID");
        ValidatorUtil.require(budgetData.getSubprojectId(), "项目ID");

        ValidatorUtil.length(budgetData.getCustomerId(), "需求名称", 8, 8);
        ValidatorUtil.length(budgetData.getSubprojectId(), "项目ID", 8, 8);

        String newId = rdmsBudgetResearchService.adjustSubprojectBudget(budgetData);

        responseDto.setContent(newId);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @Data
    public static class CustomerIdAndProjectId{
        String customerId;
        String projectId;
        String subprojectId;
    }
    @PostMapping("/getBudget")
    @Transactional
    public ResponseDto<RdmsBudgetResearchDto> getBudget(@RequestParam String projectId, String stage) {
        ResponseDto<RdmsBudgetResearchDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(projectId, "项目ID");
        ValidatorUtil.require(stage, "预算阶段");

        RdmsBudgetResearchDto rdmsBudgetResearch = rdmsBudgetResearchService.getRecordByProjectIdAndStage(projectId, Objects.requireNonNull(BudgetTypeEnum.getBudgetTypeEnumByType(stage)));
        if(rdmsBudgetResearch.getHasRecord()){
            rdmsBudgetResearchService.transformBudgetToWanYuan(rdmsBudgetResearch);
        }

        responseDto.setContent(rdmsBudgetResearch);
        return responseDto;
    }

    @PostMapping("/getBudget_subproject")
    @Transactional
    public ResponseDto<RdmsBudgetResearchDto> getBudget_subproject(@RequestBody CustomerIdAndProjectId budgetData) {
        ResponseDto<RdmsBudgetResearchDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(budgetData.getCustomerId(), "客户ID");
        ValidatorUtil.require(budgetData.getSubprojectId(), "项目ID");

        ValidatorUtil.length(budgetData.getCustomerId(), "需求名称", 8, 8);
        ValidatorUtil.length(budgetData.getSubprojectId(), "项目ID", 8, 8);

        RdmsBudgetResearchDto rdmsBudgetResearch = rdmsBudgetResearchService.getRecordByCustomerIdAndSubprojectId(budgetData.getCustomerId(), budgetData.getSubprojectId());
        if(rdmsBudgetResearch.getHasRecord()){
            rdmsBudgetResearchService.transformBudgetToWanYuan(rdmsBudgetResearch);
        }

        responseDto.setContent(rdmsBudgetResearch);
        return responseDto;
    }

    @PostMapping("/getDirectSumBudget")
    @Transactional
    public ResponseDto<Double> getDirectSumBudget(@RequestBody CustomerIdAndProjectId budgetData) {
        ResponseDto<Double> responseDto = new ResponseDto<>();

        ValidatorUtil.require(budgetData.getCustomerId(), "客户ID");
        ValidatorUtil.require(budgetData.getProjectId(), "项目ID");

        ValidatorUtil.length(budgetData.getCustomerId(), "需求名称", 8, 8);
        ValidatorUtil.length(budgetData.getProjectId(), "项目ID", 8, 8);

        double directSumBudgetByCustomerIdAndProjectId = rdmsBudgetResearchService.getDirectSumBudgetByCustomerIdAndProjectId(budgetData.getCustomerId(), budgetData.getProjectId());
        responseDto.setContent(directSumBudgetByCustomerIdAndProjectId);
        return responseDto;
    }

    /**
     * 删除
     */
    @DeleteMapping("/del/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        rdmsBudgetResearchService.delete(id);
        return responseDto;
    }

}
