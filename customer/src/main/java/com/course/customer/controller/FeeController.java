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
import com.course.server.dto.rdms.RdmsFeeManageDto;
import com.course.server.enums.rdms.*;
import com.course.server.service.rdms.*;
import com.course.server.util.CopyUtil;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/fee")
public class FeeController {
    private static final Logger LOG = LoggerFactory.getLogger(FeeController.class);
    public static final String BUSINESS_NAME = "费用管理";

    @Resource
    private RdmsFeeManageService rdmsFeeManageService;
    @Resource
    private RdmsFeeProcessService rdmsFeeProcessService;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private RdmsSubprojectService rdmsSubprojectService;
    @Resource
    private RdmsCharacterService rdmsCharacterService;
    @Resource
    private RdmsJobItemService rdmsJobItemService;
    @Resource
    private RdmsBudgetResearchExeService rdmsBudgetResearchExeService;

    @PostMapping("/getQualityFeeApplicationNumByUserId/{userId}")
    public ResponseDto<Long> getQualityFeeApplicationNumByUserId(@PathVariable String userId) {
        ResponseDto<Long> responseDto = new ResponseDto<>();
        Long applicationNumByUserId = rdmsFeeManageService.getApplicationNumByUserId(userId);
        responseDto.setContent(applicationNumByUserId);
        return responseDto;
    }

    /**
     * 保存客户信息
     */
    @PostMapping("/saveFeeApplication")
    @Transactional
    public ResponseDto<String> saveFeeApplication(@RequestBody RdmsFeeManageDto feeManageDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();

        ValidatorUtil.require(feeManageDto.getProjectType(), "项目类型");
        if(feeManageDto.getProjectType().equals(ProjectTypeEnum.PRE_PROJECT.getType())){
            ValidatorUtil.require(feeManageDto.getPreProjectId(), "项目Id");
        }else{
            ValidatorUtil.require(feeManageDto.getProjectId(), "项目Id");
            ValidatorUtil.require(feeManageDto.getSubprojectId(), "项目Id");
        }

        ValidatorUtil.require(feeManageDto.getCode(), "申请单号");
        ValidatorUtil.require(feeManageDto.getName(), "申请标题");
        ValidatorUtil.require(feeManageDto.getCustomerId(), "公司Id");
        ValidatorUtil.require(feeManageDto.getAuxStatus(), "费用归类");
        if(!feeManageDto.getAuxStatus().equals(DocTypeEnum.PRE_PROJECT.getType())){
            ValidatorUtil.require(feeManageDto.getProjectId(), "项目Id");
            ValidatorUtil.require(feeManageDto.getSubprojectId(), "项目Id");
        }
        ValidatorUtil.require(feeManageDto.getJobitemId(), "工单Id");
        ValidatorUtil.require(feeManageDto.getAppliedAmount(), "申请金额");
        ValidatorUtil.require(feeManageDto.getReason(), "领用事由");
        ValidatorUtil.require(feeManageDto.getWriterId(), "填表人");
        ValidatorUtil.require(feeManageDto.getNextNode(), "下一节点");
        ValidatorUtil.require(feeManageDto.getType(), "费用科目");

        ValidatorUtil.length(feeManageDto.getName(), "申请标题", 2, 30);
        ValidatorUtil.length(feeManageDto.getReason(), "领用事由", 6, 800);
        ValidatorUtil.length(feeManageDto.getType(), "费用科目", 2, 20);

        RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(feeManageDto.getJobitemId());
        if(!(rdmsJobItem.getType().equals(JobItemTypeEnum.QUALITY.getType())
                || rdmsJobItem.getType().equals(JobItemTypeEnum.TASK_BUG.getType()) || rdmsJobItem.getAuxType().equals(JobItemTypeEnum.TASK_BUG.getType())
                || rdmsJobItem.getType().equals(JobItemTypeEnum.TASK_SUBP.getType())
                || rdmsJobItem.getType().equals(JobItemTypeEnum.ASSIST.getType())
                || rdmsJobItem.getType().equals(JobItemTypeEnum.TEST.getType())
                || rdmsJobItem.getType().equals(JobItemTypeEnum.TASK_FUNCTION.getType())
                || rdmsJobItem.getType().equals(JobItemTypeEnum.TASK_PRODUCT.getType())
        )){
            ValidatorUtil.require(feeManageDto.getCharacterId(), "功能特性Id");
        }
        //保存申请表
        RdmsFeeManage feeManage = CopyUtil.copy(feeManageDto, RdmsFeeManage.class);
        feeManage.setJobType(rdmsJobItem.getType());
        feeManage.setApproverId(feeManageDto.getNextNode());  //记录审批人,是为了在后续单据上正确显示审批人
        feeManage.setAccountCost(feeManage.getAppliedAmount());  //使用申请金额进行临时记账
        rdmsFeeManageService.save(feeManage);

        //填写一条过程记录
        RdmsFeeProcess feeProcess = new RdmsFeeProcess();
        feeProcess.setFeeId(feeManage.getId());
        feeProcess.setExecutorId(feeManageDto.getWriterId());
        feeProcess.setDeep(1);
        feeProcess.setDescription("提交费用申请");
        feeProcess.setNextNode(feeManage.getNextNode());
        feeProcess.setSubmitTime(new Date());
        feeProcess.setStatus(ApplicationStatusEnum.APPLICATION.getStatus());
        rdmsFeeProcessService.save(feeProcess);

        responseDto.setContent(feeManage.getId());
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    /**
     * 保存客户信息
     */
    @PostMapping("/approveFeeApplication")
    @Transactional
    public ResponseDto<String> approveFeeApplication(@RequestBody RdmsFeeManageDto feeManageDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        ValidatorUtil.require(feeManageDto.getCode(), "申请单号");
        ValidatorUtil.require(feeManageDto.getApproveDescription(), "审批意见");
        ValidatorUtil.require(feeManageDto.getCapitalSource(), "资金来源");
        ValidatorUtil.length(feeManageDto.getApproveDescription(), "审批描述", 2, 500);

        //保存申请表
        RdmsFeeManage feeManage = CopyUtil.copy(feeManageDto, RdmsFeeManage.class);
        String executorId = feeManage.getNextNode();
        feeManage.setNextNode(feeManage.getWriterId());  //提交之后, 出现在谁的页面, 那么, 下一节点就是谁
        feeManage.setStatus(feeManageDto.getApproveStatus());
        feeManage.setAccountCost(feeManage.getActualCost());  //使用实际费用进行记账
        feeManage.setApproveTime(new Date());
        rdmsFeeManageService.updateByPrimaryKeySelective(feeManage);

        //项目费用
        if(!ObjectUtils.isEmpty(feeManageDto.getAuxStatus())){
            {
                //填写一条预算执行记录, 支付状态为应付未付 , 等物料单处理完成时, 标记为已支付
                RdmsJobItem jobItemDb = rdmsJobItemService.selectByPrimaryKey(feeManageDto.getJobitemId());
                RdmsBudgetResearchExe budgetResearchExe = new RdmsBudgetResearchExe();
                budgetResearchExe.setId(null);
                budgetResearchExe.setCustomerId(jobItemDb.getCustomerId());
                budgetResearchExe.setProjectId(jobItemDb.getProjectId());
                if(!ObjectUtils.isEmpty(jobItemDb.getSubprojectId())){
                    RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(jobItemDb.getSubprojectId());
                    budgetResearchExe.setParentId(subproject.getParent());
                    budgetResearchExe.setSubprojectId(jobItemDb.getSubprojectId());
                }
                budgetResearchExe.setSerialNo(feeManage.getCode());
                budgetResearchExe.setName(feeManage.getName());
                budgetResearchExe.setExecutorId(jobItemDb.getExecutorId());
                {
                    budgetResearchExe.setClassify(feeManageDto.getType());
                    budgetResearchExe.setCapitalSource(feeManageDto.getCapitalSource());
                    if(feeManageDto.getAuxStatus().equals(DocTypeEnum.PRE_PROJECT.getType())){
                        budgetResearchExe.setPayClassify(PayClassifyEnum.FUNCTION_FEE.getClassify());
                    }
                    if(feeManageDto.getAuxStatus().equals(DocTypeEnum.SUB_PROJECT.getType())){
                        budgetResearchExe.setPayClassify(PayClassifyEnum.SUBP_FEE.getClassify());
                    }
                    if(feeManageDto.getAuxStatus().equals(DocTypeEnum.PROJECT.getType())){
                        budgetResearchExe.setPayClassify(PayClassifyEnum.PROJ_FEE.getClassify());
                    }

                    RdmsBudgetResearchExe recordBySerialNo = rdmsBudgetResearchExeService.getRecordBySerialNo(feeManage.getCode());
                    if(!ObjectUtils.isEmpty(recordBySerialNo)){
                        budgetResearchExe.setId(recordBySerialNo.getId());
                    }
                    if(feeManageDto.getApproveStatus().equals(ApplicationStatusEnum.PRE_COMPLETE.getStatus())){
                        budgetResearchExe.setPaymentStatue(PaymentStatusEnum.PAID.getStatus());
                        budgetResearchExe.setAmount(feeManage.getAccountCost());
                        budgetResearchExe.setPaymentTime(new Date());
                        String save = rdmsBudgetResearchExeService.save(budgetResearchExe);
                    }else{
                        budgetResearchExe.setPaymentStatue(PaymentStatusEnum.UNPAID.getStatus());
                        budgetResearchExe.setAmount(feeManage.getAppliedAmount());
                        budgetResearchExe.setPaymentTime(null);
                        String save = rdmsBudgetResearchExeService.save(budgetResearchExe);
                    }
                }
            }
        }

        //填写一条过程记录
        RdmsFeeProcess feeProcess = new RdmsFeeProcess();
        feeProcess.setFeeId(feeManage.getId());
        feeProcess.setExecutorId(executorId);
        long feeProcessCount = rdmsFeeProcessService.getFeeProcessCount(feeManage.getId());
        feeProcess.setDeep((int)feeProcessCount);
        feeProcess.setDescription("批准申请");
        feeProcess.setNextNode(feeManage.getNextNode());
        feeProcess.setSubmitTime(new Date());
        feeProcess.setStatus(feeManageDto.getApproveStatus());
        rdmsFeeProcessService.save(feeProcess);

        responseDto.setContent(feeManage.getId());
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/listFeesByCharacterId")
    public ResponseDto<PageDto<RdmsFeeManageDto>> listFeesByCharacterId(@RequestBody PageDto<RdmsFeeManageDto> pageDto) {
        ResponseDto<PageDto<RdmsFeeManageDto>> responseDto = new ResponseDto<>();
        rdmsFeeManageService.listByCharacterId(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/getListByCharacterId/{characterId}")
    public ResponseDto<List<RdmsFeeManageDto>> getListByCharacterId(@PathVariable String characterId) {
        ResponseDto<List<RdmsFeeManageDto>> responseDto = new ResponseDto<>();
        List<RdmsFeeManageDto> listByCharacterId = rdmsFeeManageService.getListByCharacterId(characterId);
        responseDto.setContent(listByCharacterId);
        return responseDto;
    }

    @PostMapping("/getListBySubprojectId/{subprojectId}")
    public ResponseDto<List<RdmsFeeManageDto>> getListBySubprojectId(@PathVariable String subprojectId) {
        ResponseDto<List<RdmsFeeManageDto>> responseDto = new ResponseDto<>();
        List<RdmsFeeManageDto> listBySubprojectId = rdmsFeeManageService.getListBySubprojectId(subprojectId);
        responseDto.setContent(listBySubprojectId);
        return responseDto;
    }

    @PostMapping("/getListByPreProjectId/{preprojectId}")
    public ResponseDto<List<RdmsFeeManageDto>> getListByPreProjectId(@PathVariable String preprojectId) {
        ResponseDto<List<RdmsFeeManageDto>> responseDto = new ResponseDto<>();
        List<RdmsFeeManageDto> listByPreprojectId = rdmsFeeManageService.getListByPreProjectId(preprojectId);
        responseDto.setContent(listByPreprojectId);
        return responseDto;
    }

    @PostMapping("/getListByProjectId/{projectId}")
    public ResponseDto<List<RdmsFeeManageDto>> getListByProjectId(@PathVariable String projectId) {
        ResponseDto<List<RdmsFeeManageDto>> responseDto = new ResponseDto<>();
        List<RdmsFeeManageDto> listByPreprojectId = rdmsFeeManageService.getListByProjectId(projectId);
        responseDto.setContent(listByPreprojectId);
        return responseDto;
    }

    @PostMapping("/getListByQualityId/{qualityId}")
    public ResponseDto<List<RdmsFeeManageDto>> getListByQualityId(@PathVariable String qualityId) {
        ResponseDto<List<RdmsFeeManageDto>> responseDto = new ResponseDto<>();
        List<RdmsFeeManageDto> listByJobitemId = rdmsFeeManageService.getListByQualityId(qualityId);
        responseDto.setContent(listByJobitemId);

        return responseDto;
    }


    @PostMapping("/getFeeApplicationListByWriterId/{writerId}")
    public ResponseDto<List<RdmsFeeManageDto>> getFeeApplicationListByWriterId(@PathVariable String writerId) {
        ResponseDto<List<RdmsFeeManageDto>> responseDto = new ResponseDto<>();
        List<RdmsFeeManageDto> listByWriterId = rdmsFeeManageService.getFeeApplicationListByWriterId(writerId);
        responseDto.setContent(listByWriterId);
        return responseDto;
    }

    @PostMapping("/getFeeApplicationNum/{userId}")
    public ResponseDto<Integer> getFeeApplicationNum(@PathVariable String userId) {
        ResponseDto<Integer> responseDto = new ResponseDto<>();
        Integer feeApplicationNum = rdmsFeeManageService.getFeeApplicationNum(userId);
        responseDto.setContent(feeApplicationNum);
        return responseDto;
    }

    @PostMapping("/getFeeApplicationNum_project/{userId}")
    public ResponseDto<Integer> getFeeApplicationNum_project(@PathVariable String userId) {
        ResponseDto<Integer> responseDto = new ResponseDto<>();
        Integer feeApplicationNum = rdmsFeeManageService.getFeeApplicationNum_project(userId);
        responseDto.setContent(feeApplicationNum);
        return responseDto;
    }

    @PostMapping("/getFeeApplicationByCode/{code}")
    public ResponseDto<RdmsFeeManageDto> getFeeApplicationByCode(@PathVariable String code) {
        ResponseDto<RdmsFeeManageDto> responseDto = new ResponseDto<>();
        RdmsFeeManageDto feeApplicationByCode = rdmsFeeManageService.getFeeApplicationByCode(code);
        RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(feeApplicationByCode.getWriterId());
        feeApplicationByCode.setWriterName(rdmsCustomerUser.getTrueName());

        if(!ObjectUtils.isEmpty(feeApplicationByCode.getNextNode())){
            RdmsCustomerUser rdmsCustomerUser1 = rdmsCustomerUserService.selectByPrimaryKey(feeApplicationByCode.getNextNode());
            feeApplicationByCode.setNextNodeName(rdmsCustomerUser1.getTrueName());
        }
        if(!ObjectUtils.isEmpty(feeApplicationByCode.getApproverId())){
            RdmsCustomerUser rdmsCustomerUser2 = rdmsCustomerUserService.selectByPrimaryKey(feeApplicationByCode.getApproverId());
            feeApplicationByCode.setApproverName(rdmsCustomerUser2.getTrueName());  //项目经理
        }

        if(!ObjectUtils.isEmpty(feeApplicationByCode.getCharacterId())){
            RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(feeApplicationByCode.getCharacterId());
            feeApplicationByCode.setCharacterName(rdmsCharacter.getCharacterName());
        }
        if(!ObjectUtils.isEmpty(feeApplicationByCode.getJobitemId())){
            RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(feeApplicationByCode.getJobitemId());
            feeApplicationByCode.setJobitemName(rdmsJobItem.getJobName());
        }
        responseDto.setContent(feeApplicationByCode);
        return responseDto;
    }

    @PostMapping("/confirmCancel/{code}")
    public ResponseDto<String> confirmCancel(@PathVariable String code) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        RdmsFeeManageDto feeApplication = rdmsFeeManageService.getFeeManageSimpleInfoByCode(code);
        feeApplication.setStatus(ApplicationStatusEnum.CANCEL.getStatus());
        RdmsFeeManage copy = CopyUtil.copy(feeApplication, RdmsFeeManage.class);
        String s = rdmsFeeManageService.update(copy);

        //填写一条过程记录
        RdmsFeeProcess feeProcess = new RdmsFeeProcess();
        feeProcess.setFeeId(feeApplication.getId());
        feeProcess.setExecutorId(feeApplication.getWriterId());
        long feeProcessCount = rdmsFeeProcessService.getFeeProcessCount(feeApplication.getId());
        feeProcess.setDeep((int)feeProcessCount);
        feeProcess.setDescription("费用申请取消");
        feeProcess.setNextNode(null);
        feeProcess.setSubmitTime(new Date());
        feeProcess.setStatus(ApplicationStatusEnum.CANCEL.getStatus());
        rdmsFeeProcessService.save(feeProcess);

        responseDto.setContent(s);
        return responseDto;
    }
    @PostMapping("/updateFeeApproveFileListStr")
    public ResponseDto<String> updateMaterialApproveFileListStr(@RequestBody RdmsFeeManageDto feeManageDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        if(!ObjectUtils.isEmpty(feeManageDto.getApproveFileListStr())){
            RdmsFeeManage copy = CopyUtil.copy(feeManageDto, RdmsFeeManage.class);
            String s = rdmsFeeManageService.updateFeeApproveFileListStr(copy);
            responseDto.setContent(s);
        }else{
            responseDto.setContent(null);
        }
        return responseDto;
    }
    @PostMapping("/updateFeeDealWithFileListStr")
    public ResponseDto<String> updateFeeDealWithFileListStr(@RequestBody RdmsFeeManageDto feeManageDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        if(!ObjectUtils.isEmpty(feeManageDto.getApproveFileListStr())){
            RdmsFeeManage copy = CopyUtil.copy(feeManageDto, RdmsFeeManage.class);
            String s = rdmsFeeManageService.updateFeeDealWithFileListStr(copy);
            responseDto.setContent(s);
        }else{
            responseDto.setContent(null);
        }
        return responseDto;
    }
    @PostMapping("/updateFeeCompleteFileListStr")
    public ResponseDto<String> updateFeeCompleteFileListStr(@RequestBody RdmsFeeManageDto feeManageDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        if(!ObjectUtils.isEmpty(feeManageDto.getApproveFileListStr())){
            RdmsFeeManage copy = CopyUtil.copy(feeManageDto, RdmsFeeManage.class);
            String s = rdmsFeeManageService.updateFeeCompleteFileListStr(copy);
            responseDto.setContent(s);
        }else{
            responseDto.setContent(null);
        }
        return responseDto;
    }
    @PostMapping("/submit")
    public ResponseDto<String> submit(@RequestBody RdmsFeeManageDto feeManageDto) {
        ValidatorUtil.require(feeManageDto.getCode(), "申请单号");
        ValidatorUtil.require(feeManageDto.getLoginUserId(), "当前用户");
        ValidatorUtil.require(feeManageDto.getSubmitDescription(), "提交说明");
        ValidatorUtil.require(feeManageDto.getActualCost(), "实际费用");
        ValidatorUtil.length(feeManageDto.getType(), "领用事由", 2, 500);

        ResponseDto<String> responseDto = new ResponseDto<>();
        RdmsFeeManageDto feeApplication = rdmsFeeManageService.getFeeApplicationByCode(feeManageDto.getCode());
        feeApplication.setStatus(ApplicationStatusEnum.SUBMIT.getStatus());
        feeApplication.setActualCost(feeManageDto.getActualCost());
        feeApplication.setSubmitDescription(feeManageDto.getSubmitDescription());
        RdmsFeeManage copy = CopyUtil.copy(feeApplication, RdmsFeeManage.class);
        rdmsFeeManageService.update(copy);

        //填写一条过程记录
        RdmsFeeProcess feeProcess = new RdmsFeeProcess();
        feeProcess.setFeeId(feeApplication.getId());
        feeProcess.setExecutorId(feeManageDto.getLoginUserId());
        long feeProcessCount = rdmsFeeProcessService.getFeeProcessCount(feeApplication.getId());
        feeProcess.setDeep((int)feeProcessCount);
        feeProcess.setDescription("费用处理完成, 提交审批");
//        RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(feeApplication.getSubprojectId());
        feeProcess.setNextNode(feeManageDto.getNextNode());
        feeProcess.setSubmitTime(new Date());
        feeProcess.setStatus(ApplicationStatusEnum.SUBMIT.getStatus());
        rdmsFeeProcessService.save(feeProcess);

        responseDto.setContent(feeApplication.getCode());
        return responseDto;
    }

    @PostMapping("/complete/{code}")
    public ResponseDto<String> complete(@PathVariable String code) {
        ValidatorUtil.require(code, "申请单号");

        ResponseDto<String> responseDto = new ResponseDto<>();
        RdmsFeeManageDto feeApplication = rdmsFeeManageService.getFeeApplicationByCode(code);
        feeApplication.setStatus(ApplicationStatusEnum.COMPLETE.getStatus());
        RdmsFeeManage copy = CopyUtil.copy(feeApplication, RdmsFeeManage.class);
        rdmsFeeManageService.update(copy);

        //填写一条过程记录
        RdmsFeeProcess feeProcess = new RdmsFeeProcess();
        feeProcess.setFeeId(feeApplication.getId());
        feeProcess.setExecutorId(feeApplication.getWriterId());
        long feeProcessCount = rdmsFeeProcessService.getFeeProcessCount(feeApplication.getId());
        feeProcess.setDeep((int)feeProcessCount);
        feeProcess.setDescription("归档");
        feeProcess.setNextNode(null);
        feeProcess.setSubmitTime(new Date());
        feeProcess.setStatus(ApplicationStatusEnum.COMPLETE.getStatus());
        rdmsFeeProcessService.save(feeProcess);

        responseDto.setContent(feeApplication.getCode());
        return responseDto;
    }



}
