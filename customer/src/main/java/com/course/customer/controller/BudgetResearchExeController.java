/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import com.course.server.domain.RdmsCustomerUser;
import com.course.server.domain.RdmsProject;
import com.course.server.dto.PageDto;
import com.course.server.dto.RdmsBudgetResearchExeDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.RdmsHmiBudgeStatisticsDto;
import com.course.server.enums.rdms.BudgetTypeEnum;
import com.course.server.service.rdms.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/budget-direct-exe")
public class BudgetResearchExeController {
    private static final Logger LOG = LoggerFactory.getLogger(BudgetResearchExeController.class);
    public static final String BUSINESS_NAME = "直接立项预算执行管理";

    @Resource
    private RdmsBudgetResearchExeService rdmsBudgetResearchExeService;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;

    @Resource
    private RdmsIpmtService rdmsIpmtService;
    @Autowired
    private RdmsProjectService rdmsProjectService;

    @PostMapping("/listByKeywordIsSubprojectId")
    public ResponseDto<PageDto<RdmsBudgetResearchExeDto>> listByKeywordIsSubprojectId(@RequestBody PageDto<RdmsBudgetResearchExeDto> pageDto) {
        ResponseDto<PageDto<RdmsBudgetResearchExeDto>> responseDto = new ResponseDto<>();
        rdmsBudgetResearchExeService.listByKeywordIsSubprojectId(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/listByKeywordIsProjectId")
    public ResponseDto<PageDto<RdmsBudgetResearchExeDto>> listByKeywordIsProjectId(@RequestBody PageDto<RdmsBudgetResearchExeDto> pageDto) {
        ResponseDto<PageDto<RdmsBudgetResearchExeDto>> responseDto = new ResponseDto<>();
        rdmsBudgetResearchExeService.listByKeywordIsProjectId(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存客户信息
     */
    @PostMapping("/saveBudgetExeList")
    @Transactional
    public ResponseDto<List<String>> saveBudgetExeList(@RequestBody List<RdmsBudgetResearchExeDto> budgetExeList) {
        ResponseDto<List<String>> responseDto = new ResponseDto<>();
        List<String> strings = rdmsBudgetResearchExeService.saveBudgetExeList(budgetExeList);
        responseDto.setContent(strings);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/save")
    @Transactional
    public ResponseDto<String> save(@RequestBody RdmsBudgetResearchExeDto feeItem) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        String save = rdmsBudgetResearchExeService.save(feeItem);
        responseDto.setContent(save);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/getSumBudgetBySubprojectId/{subprojectId}")
    @Transactional
    public ResponseDto<BigDecimal> getSumBudgetBySubprojectId(@PathVariable String subprojectId) {
        ResponseDto<BigDecimal> responseDto = new ResponseDto<>();
        BigDecimal sumBudget = rdmsBudgetResearchExeService.getActiveBudgetBySubprojectId(subprojectId);
        responseDto.setContent(sumBudget);
        return responseDto;
    }

    @PostMapping("/getBudgetExecutionSummary/{subprojectId}")
    @Transactional
    public ResponseDto<RdmsHmiBudgeStatisticsDto> getBudgetExecutionSummary(@PathVariable String subprojectId) {
        ResponseDto<RdmsHmiBudgeStatisticsDto> responseDto = new ResponseDto<>();
        RdmsHmiBudgeStatisticsDto budgetExecutionSummary = rdmsBudgetResearchExeService.getBudgetExecutionSummary(subprojectId);
        responseDto.setContent(budgetExecutionSummary);
        return responseDto;
    }

    @PostMapping("/getBudgetExecutionSummary_preproject/{preprojectId}")
    @Transactional
    public ResponseDto<RdmsHmiBudgeStatisticsDto> getBudgetExecutionSummary_preproject(@PathVariable String preprojectId) {
        ResponseDto<RdmsHmiBudgeStatisticsDto> responseDto = new ResponseDto<>();
        RdmsHmiBudgeStatisticsDto budgetExecutionSummary = rdmsBudgetResearchExeService.getBudgetExecutionSummary_preproject(preprojectId);
        responseDto.setContent(budgetExecutionSummary);
        return responseDto;
    }

    @PostMapping("/getBudgetExecutionSummary_mainProject/{projectId}")
    @Transactional
    public ResponseDto<RdmsHmiBudgeStatisticsDto> getBudgetExecutionSummary_mainProject(@PathVariable String projectId) {
        ResponseDto<RdmsHmiBudgeStatisticsDto> responseDto = new ResponseDto<>();
        RdmsHmiBudgeStatisticsDto budgetExecutionSummary = rdmsBudgetResearchExeService.getBudgetExecutionSummary_mainProject(projectId);
        responseDto.setContent(budgetExecutionSummary);
        return responseDto;
    }

    @PostMapping("/getBudgetExecutionSummary_product/{projectId}")
    @Transactional
    public ResponseDto<RdmsHmiBudgeStatisticsDto> getBudgetExecutionSummary_product(@PathVariable String projectId) {
        ResponseDto<RdmsHmiBudgeStatisticsDto> responseDto = new ResponseDto<>();
        RdmsHmiBudgeStatisticsDto budgetExecutionSummary = rdmsBudgetResearchExeService.getBudgetExecutionSummary_product(projectId);
        responseDto.setContent(budgetExecutionSummary);
        return responseDto;
    }

    @PostMapping("/getMainProjectBudgetExecutionSummaryList/{customerId}")
    @Transactional
    public ResponseDto<List<RdmsHmiBudgeStatisticsDto>> getMainProjectBudgetExecutionSummaryList(@PathVariable String customerId) {
        ResponseDto<List<RdmsHmiBudgeStatisticsDto>> responseDto = new ResponseDto<>();
        List<RdmsHmiBudgeStatisticsDto> mainProjectBudgetExecutionSummaryList = rdmsBudgetResearchExeService.getMainProjectBudgetExecutionSummaryList(customerId);
        responseDto.setContent(mainProjectBudgetExecutionSummaryList);
        return responseDto;
    }

    @PostMapping("/getBudgetStageExecutionSummary_project/{customerId}")
    @Transactional
    public ResponseDto<RdmsHmiBudgeStatisticsDto> getBudgetStageExecutionSummary_project(@PathVariable String customerId) {
        ResponseDto<RdmsHmiBudgeStatisticsDto> responseDto = new ResponseDto<>();
        RdmsHmiBudgeStatisticsDto budgetExecutionSummary = rdmsBudgetResearchExeService.getBudgetStageExecutionSummary(customerId, BudgetTypeEnum.PROJECT);
        responseDto.setContent(budgetExecutionSummary);
        return responseDto;
    }

    @PostMapping("/getBudgetStageExecutionSummary_product/{customerId}")
    @Transactional
    public ResponseDto<RdmsHmiBudgeStatisticsDto> getBudgetStageExecutionSummary_product(@PathVariable String customerId) {
        ResponseDto<RdmsHmiBudgeStatisticsDto> responseDto = new ResponseDto<>();
        RdmsHmiBudgeStatisticsDto budgetExecutionSummary = rdmsBudgetResearchExeService.getBudgetStageExecutionSummary(customerId, BudgetTypeEnum.PRODUCT);
        responseDto.setContent(budgetExecutionSummary);
        return responseDto;
    }

    @PostMapping("/getBudgetStageExecutionSummary_preProject/{customerId}")
    @Transactional
    public ResponseDto<RdmsHmiBudgeStatisticsDto> getBudgetStageExecutionSummary_preProject(@PathVariable String customerId) {
        ResponseDto<RdmsHmiBudgeStatisticsDto> responseDto = new ResponseDto<>();
        RdmsHmiBudgeStatisticsDto budgetExecutionSummary = rdmsBudgetResearchExeService.getBudgetStageExecutionSummary(customerId, BudgetTypeEnum.PRE_PROJECT);
        responseDto.setContent(budgetExecutionSummary);
        return responseDto;
    }

    @PostMapping("/getBudgetExecutionSummary_customer/{customerId}")
    @Transactional
    public ResponseDto<RdmsHmiBudgeStatisticsDto> getBudgetExecutionSummary_customer(@PathVariable String customerId) {
        ResponseDto<RdmsHmiBudgeStatisticsDto> responseDto = new ResponseDto<>();
        RdmsHmiBudgeStatisticsDto budgetExecutionSummary = rdmsBudgetResearchExeService.getCustomerBudgetExecutionSummary(customerId);
        responseDto.setContent(budgetExecutionSummary);
        return responseDto;
    }

    @PostMapping("/getMainProjectBudgetExecutionSummary/{projectId}")
    @Transactional
    public ResponseDto<RdmsHmiBudgeStatisticsDto> getMainProjectBudgetExecutionSummary(@PathVariable String projectId) {
        ResponseDto<RdmsHmiBudgeStatisticsDto> responseDto = new ResponseDto<>();
        RdmsHmiBudgeStatisticsDto mainProjectBudgetExecutionSummary = rdmsBudgetResearchExeService.getMainProjectBudgetExecutionSummary(projectId);
        responseDto.setContent(mainProjectBudgetExecutionSummary);
        return responseDto;
    }

    //应该已经不用了, 需要确认一下
/*    @PostMapping("/getMainProjectBudgetExecutionSummaryListByIPMT/{customerUserId}")
    @Transactional
    public ResponseDto<List<RdmsHmiBudgeStatisticsDto>> getMainProjectBudgetExecutionSummaryListByIPMT(@PathVariable String customerUserId) {
        ResponseDto<List<RdmsHmiBudgeStatisticsDto>> responseDto = new ResponseDto<>();
        List<RdmsHmiBudgeStatisticsDto> mainProjectBudgetExecutionSummaryList = rdmsBudgetResearchExeService.getMainProjectBudgetExecutionSummaryListByIPMT(customerUserId);
        responseDto.setContent(mainProjectBudgetExecutionSummaryList);
        return responseDto;
    }*/

    @PostMapping("/getUnderlineProjectBudgetExecutionSummaryList")
    @Transactional
    public ResponseDto<List<RdmsHmiBudgeStatisticsDto>> getUnderlineProjectBudgetExecutionSummaryList(@RequestParam String customerUserId, String customerId) {
        ResponseDto<List<RdmsHmiBudgeStatisticsDto>> responseDto = new ResponseDto<>();
        RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(customerUserId);
        if(!ObjectUtils.isEmpty(rdmsCustomerUser)){
            List<String> ipmtIdList = rdmsIpmtService.listByCustomerId(customerId);
            if(!ObjectUtils.isEmpty(ipmtIdList)){
                if(ipmtIdList.contains(rdmsCustomerUser.getId())){
                    //IPMT
                    List<RdmsHmiBudgeStatisticsDto> mainProjectBudgetExecutionSummaryList = rdmsBudgetResearchExeService.getMainProjectBudgetExecutionSummaryListByIPMT(customerUserId);
                    responseDto.setContent(mainProjectBudgetExecutionSummaryList);
                }else{
                    List<RdmsHmiBudgeStatisticsDto> mainProjectBudgetExecutionSummaryList = rdmsBudgetResearchExeService.getUnderlineProjectBudgetExecutionSummaryList(customerUserId);
                    responseDto.setContent(mainProjectBudgetExecutionSummaryList);
                }
            }
        }
        return responseDto;
    }

    /**
     * 删除
     */
    @DeleteMapping("/del/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        rdmsBudgetResearchExeService.delete(id);
        return responseDto;
    }

}
