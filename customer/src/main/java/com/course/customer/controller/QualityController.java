/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.*;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.*;
import com.course.server.enums.rdms.*;
import com.course.server.service.rdms.*;
import com.course.server.util.CopyUtil;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/quality")
public class QualityController {
    private static final Logger LOG = LoggerFactory.getLogger(QualityController.class);
    public static final String BUSINESS_NAME = "质量管理";

    @Resource
    private RdmsQualityService rdmsQualityService;
    @Resource
    private RdmsSubprojectService rdmsSubprojectService;
    @Resource
    private RdmsJobItemService rdmsJobItemService;
    @Resource
    private RdmsJobItemPropertyService rdmsJobItemPropertyService;
    @Resource
    private RdmsJobItemProcessService rdmsJobItemProcessService;
    @Resource
    private RdmsCharacterService rdmsCharacterService;
    @Resource
    private RdmsFileAuthService rdmsFileAuthService;
    @Autowired
    private RdmsBossService rdmsBossService;

    @PostMapping("/getQualityRecheckNumByUserId/{userId}")
    public ResponseDto<Long> getQualityRecheckNumByUserId(@PathVariable String userId) {
        ResponseDto<Long> responseDto = new ResponseDto<>();
        long testManageNum = rdmsQualityService.getQualityRecheckNumByUserId(userId);
        responseDto.setContent(testManageNum);
        return responseDto;
    }

    @PostMapping("/getQualityDefineSubmitNumByPdm/{userId}")
    public ResponseDto<Long> getQualityDefineSubmitNumByPdm(@PathVariable String userId) {
        ResponseDto<Long> responseDto = new ResponseDto<>();
        long qualityItemNum = rdmsQualityService.countQualityDefineSubmitNumByPdm(userId);
        responseDto.setContent(qualityItemNum);
        return responseDto;
    }

    @PostMapping("/getQualityAndSubmitJobitems/{qualityId}")
    public ResponseDto<RdmsQualityDto> getQualityAndSubmitJobitems(@PathVariable String qualityId) {
        ResponseDto<RdmsQualityDto> responseDto = new ResponseDto<>();
        RdmsQualityDto qualityAndSubmitJobitems = rdmsQualityService.getQualityAndSubmitJobitems(qualityId);
        responseDto.setContent(qualityAndSubmitJobitems);
        return responseDto;
    }

    @PostMapping("/getQualityCostListByProjectId/{projectId}")
    public ResponseDto<List<RdmsQualityDto>> getQualityCostListByProjectId(@PathVariable String projectId) {
        ResponseDto<List<RdmsQualityDto>> responseDto = new ResponseDto<>();
        List<RdmsQualityDto> qualityCostListByProjectId = rdmsQualityService.getQualityCostListByProjectId(projectId);
        responseDto.setContent(qualityCostListByProjectId);
        return responseDto;
    }

    @PostMapping("/getQualityHasListFlag/{userId}")
    public ResponseDto<Boolean> getQualityHasListFlag(@PathVariable String userId) {
        ResponseDto<Boolean> responseDto = new ResponseDto<>();
        long l = rdmsQualityService.countQualityJobNumByPdm(userId);
        responseDto.setContent(l >0);
        return responseDto;
    }

    @PostMapping("/getQualityAndAnalysisJobList/{qualityId}")
    public ResponseDto<RdmsQualityDto> getCharacterAndJobitemList(@PathVariable String qualityId) {
        ResponseDto<RdmsQualityDto> responseDto = new ResponseDto<>();
        RdmsQualityDto qualityAndAnalysisJobList = rdmsQualityService.getQualityAndAnalysisJobList(qualityId);
        responseDto.setContent(qualityAndAnalysisJobList);
        return responseDto;
    }

    @PostMapping("/list")
    public ResponseDto<PageDto<RdmsQualityDto>> list(@RequestBody PageDto<RdmsQualityDto> pageDto) {
        ResponseDto<PageDto<RdmsQualityDto>> responseDto = new ResponseDto<>();
        rdmsQualityService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/listByProjectId")
    public ResponseDto<PageDto<RdmsQualityDto>> listByProjectId(@RequestBody PageDto<RdmsQualityDto> pageDto) {
        ResponseDto<PageDto<RdmsQualityDto>> responseDto = new ResponseDto<>();
        rdmsQualityService.listByProjectId(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/listByProjectId_handling")
    public ResponseDto<PageDto<RdmsQualityDto>> listByProjectId_handling(@RequestBody PageDto<RdmsQualityDto> pageDto) {
        ResponseDto<PageDto<RdmsQualityDto>> responseDto = new ResponseDto<>();
        rdmsQualityService.listByProjectId_handling(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/getQualityAndJobitemList/{qualityId}")
    public ResponseDto<RdmsQualityDto> getQualityAndJobitemList(@PathVariable String qualityId) {
        ResponseDto<RdmsQualityDto> responseDto = new ResponseDto<>();
        RdmsQualityDto qualityAndJobitemList = rdmsQualityService.getQualityAndJobitemList(qualityId);
        responseDto.setContent(qualityAndJobitemList);
        return responseDto;
    }


    @PostMapping("/listQualityByPjm")
    public ResponseDto<PageDto<RdmsQualityDto>> listQualityByPjm(@RequestBody PageDto<RdmsQualityDto> pageDto) {
        ResponseDto<PageDto<RdmsQualityDto>> responseDto = new ResponseDto<>();
        rdmsQualityService.listQualityByPjm(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/listQualityByPdm")
    public ResponseDto<PageDto<RdmsQualityDto>> listQualityByPdm(@RequestBody PageDto<RdmsQualityDto> pageDto) {
        ResponseDto<PageDto<RdmsQualityDto>> responseDto = new ResponseDto<>();
        rdmsQualityService.listQualityByPdm(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/listQualityByCharacterId")
    public ResponseDto<List<RdmsQualityDto>> listQualityByCharacterId(@RequestParam String characterId, String loginUserId) {
        ResponseDto<List<RdmsQualityDto>> responseDto = new ResponseDto<>();
        List<RdmsQualityDto> rdmsQualityDtos = rdmsQualityService.listQualityByCharacterId(characterId, loginUserId);
        responseDto.setContent(rdmsQualityDtos);
        return responseDto;
    }

    @PostMapping("/listByManager")
    public ResponseDto<PageDto<RdmsQualityDto>> listByManager(@RequestBody PageDto<RdmsQualityDto> pageDto) {
        ResponseDto<PageDto<RdmsQualityDto>> responseDto = new ResponseDto<>();
        rdmsQualityService.listByManager(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/getProjectTreeListByCustomerId/{customerId}")
    public ResponseDto<List<RdmsHmiTree<RdmsHmiTreeInfoDto>>> getProjectTreeListByCustomerId(@PathVariable String customerId) {
        ResponseDto<List<RdmsHmiTree<RdmsHmiTreeInfoDto>>> responseDto = new ResponseDto<>();
        List<RdmsHmiTree<RdmsHmiTreeInfoDto>> projectTreeListByCustomerId = rdmsSubprojectService.getCompleteProjectTreeListByCustomerId(customerId);
        if(!CollectionUtils.isEmpty(projectTreeListByCustomerId)){
            for(RdmsHmiTree<RdmsHmiTreeInfoDto> hmiTree: projectTreeListByCustomerId){
                hmiTree.setValue(hmiTree.getId());
                hmiTree.setParent(customerId);
            }
        }
        responseDto.setContent(projectTreeListByCustomerId);
        return responseDto;
    }

    @PostMapping("/getCharacterListIncludeNextLevelBySubprojectId/{subprojectId}")
    public ResponseDto<List<RdmsCharacterDto>> getCharacterListIncludeNextLevelBySubprojectId(@PathVariable String subprojectId) {
        ResponseDto<List<RdmsCharacterDto>> responseDto = new ResponseDto<>();
        List<String> subprojectIdList = rdmsSubprojectService.getSubProjectIdListIncludeNextLevel(subprojectId);
        List<RdmsCharacterDto> characterDtoList = new ArrayList<>();
        if(! CollectionUtils.isEmpty(subprojectIdList)){
            for(String subId: subprojectIdList){
                List<RdmsCharacterDto> characterList = rdmsCharacterService.getReleaseCharacterListBySubprojectId(subId);
                characterDtoList.addAll(characterList);
            }
        }
        List<RdmsCharacterDto> characterDtos = new ArrayList<>();
        if(!CollectionUtils.isEmpty(characterDtoList)){
            List<String> stringList = characterDtoList.stream().map(RdmsCharacterDto::getId).collect(Collectors.toList());
            List<String> stringList1 = stringList.stream().distinct().collect(Collectors.toList());
            characterDtos = characterDtoList.stream().filter(item -> stringList1.contains(item.getId())).collect(Collectors.toList());
        }
        responseDto.setContent(characterDtos);
        return responseDto;
    }

    @PostMapping("/getQualityDetailInfo")
    public ResponseDto<RdmsQualityDto> getQualityDetailInfo(@RequestParam String qualityId, String loginUserId) {
        ResponseDto<RdmsQualityDto> responseDto = new ResponseDto<>();
        RdmsQualityDto qualityDetailInfo = rdmsQualityService.getQualityDetailInfo(qualityId, loginUserId);
        responseDto.setContent(qualityDetailInfo);
        return responseDto;
    }

    @PostMapping("/save")
    @Transactional
    public ResponseDto<RdmsQualityDto> save(@RequestBody RdmsQualityDto qualityDto) {
        ResponseDto<RdmsQualityDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(qualityDto.getCustomerId(), "客户ID");
        ValidatorUtil.require(qualityDto.getWriterId(), "编制者");
        ValidatorUtil.require(qualityDto.getId(), "质量ID");
        ValidatorUtil.require(qualityDto.getSubprojectId(), "关联项目");
        ValidatorUtil.require(qualityDto.getName(), "质量标题");
        ValidatorUtil.require(qualityDto.getQualityDescription(), "问题描述");
//        ValidatorUtil.require(qualityDto.getAssociateCharacterId(), "问题描述");

        ValidatorUtil.length(qualityDto.getQualityDescription(), "问题描述", 10, 2000);
        ValidatorUtil.length(qualityDto.getName(), "质量标题", 4, 50);

        RdmsQuality rdmsQuality = CopyUtil.copy(qualityDto, RdmsQuality.class);
        rdmsQuality.setStatus(QualityStatusEnum.SAVE.getStatus());
        RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(qualityDto.getSubprojectId());
        rdmsQuality.setProjectManagerId(subproject.getProjectManagerId());
        rdmsQuality.setProductManagerId(subproject.getProductManagerId());

        rdmsQualityService.save(rdmsQuality);
        //填写文件的访问权限人员
        if(!ObjectUtils.isEmpty(rdmsQuality.getFileListStr())){
            List<String> fileIdList = JSON.parseArray(rdmsQuality.getFileListStr(), String.class);
            {
                //设置文件授权 权限
                RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                roleUsersDto.setLoginUserId(qualityDto.getWriterId());
                roleUsersDto.setReceiverId(null);
                RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(qualityDto.getCustomerId());
                roleUsersDto.setBossId(rdmsBossDto.getBossId());
                roleUsersDto.setSuperId(null);
                roleUsersDto.setIpmtId(null);
                roleUsersDto.setPjmId(null);
                roleUsersDto.setPdmId(rdmsQuality.getProductManagerId());
                rdmsFileAuthService.setFileAuthUser(fileIdList, roleUsersDto);
            }
        }

        responseDto.setContent(qualityDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/saveQualityFeedbackInfo")
    @Transactional
    public ResponseDto<String> saveQualityFeedbackInfo(@RequestBody RdmsQualityDto qualityDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();

        ValidatorUtil.require(qualityDto.getId(), "质量ID");
        ValidatorUtil.require(qualityDto.getFeedbackDescription(), "反馈描述");
        ValidatorUtil.length(qualityDto.getFeedbackDescription(), "需求描述", 10, 2000);

        RdmsQuality quality = new RdmsQuality();
        quality.setId(qualityDto.getId());
        quality.setFeedbackDescription(qualityDto.getFeedbackDescription());
        quality.setFeedbackFileIdListStr(qualityDto.getFeedbackFileIdListStr());
        String id = rdmsQualityService.updateByPrimaryKeySelective(quality);

        //填写文件的访问权限人员
        if(!ObjectUtils.isEmpty(qualityDto.getFeedbackFileIdListStr())){
            List<String> fileIdList = JSON.parseArray(qualityDto.getFeedbackFileIdListStr(), String.class);
            {
                //设置文件授权 权限
                RdmsQuality rdmsQuality = rdmsQualityService.selectByPrimaryKey(id);
                RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                roleUsersDto.setLoginUserId(qualityDto.getLoginUserId());
                roleUsersDto.setReceiverId(null);
                RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(qualityDto.getCustomerId());
                roleUsersDto.setBossId(rdmsBossDto.getBossId());
                roleUsersDto.setSuperId(null);
                roleUsersDto.setIpmtId(null);
                roleUsersDto.setPjmId(null);
                roleUsersDto.setPdmId(rdmsQuality.getProductManagerId());
                rdmsFileAuthService.setFileAuthUser(fileIdList, roleUsersDto);
            }
        }

        responseDto.setContent(id);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/submitQualityFeedbackInfo")
    @Transactional
    public ResponseDto<String> submitQualityFeedbackInfo(@RequestBody RdmsQualityDto qualityDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();

        ValidatorUtil.require(qualityDto.getId(), "质量ID");
        ValidatorUtil.require(qualityDto.getFeedbackDescription(), "反馈描述");
        ValidatorUtil.length(qualityDto.getFeedbackDescription(), "需求描述", 10, 2000);

        RdmsQuality quality = new RdmsQuality();
        quality.setId(qualityDto.getId());
        quality.setFeedbackDescription(qualityDto.getFeedbackDescription());
        quality.setFeedbackFileIdListStr(qualityDto.getFeedbackFileIdListStr());
        quality.setStatus(QualityStatusEnum.RECHECK.getStatus());
        String id = rdmsQualityService.updateByPrimaryKeySelective(quality);

        //填写文件的访问权限人员
        if(!ObjectUtils.isEmpty(qualityDto.getFeedbackFileIdListStr())){
            List<String> fileIdList = JSON.parseArray(qualityDto.getFeedbackFileIdListStr(), String.class);
            {
                //设置文件授权 权限
                RdmsQuality rdmsQuality = rdmsQualityService.selectByPrimaryKey(id);
                RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                roleUsersDto.setLoginUserId(qualityDto.getLoginUserId());
                roleUsersDto.setReceiverId(null);
                RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(qualityDto.getCustomerId());
                roleUsersDto.setBossId(rdmsBossDto.getBossId());
                roleUsersDto.setSuperId(null);
                roleUsersDto.setIpmtId(null);
                roleUsersDto.setPjmId(null);
                roleUsersDto.setPdmId(rdmsQuality.getProductManagerId());
                rdmsFileAuthService.setFileAuthUser(fileIdList, roleUsersDto);
            }
        }

        responseDto.setContent(id);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/submit")
    @Transactional
    public ResponseDto<RdmsQualityDto> submit(@RequestBody RdmsQualityDto qualityDto) {
        ResponseDto<RdmsQualityDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(qualityDto.getCustomerId(), "客户ID");
        ValidatorUtil.require(qualityDto.getWriterId(), "编制者");
        ValidatorUtil.require(qualityDto.getId(), "质量ID");
        ValidatorUtil.require(qualityDto.getSubprojectId(), "关联项目");
        ValidatorUtil.require(qualityDto.getName(), "质量标题");
        ValidatorUtil.require(qualityDto.getQualityDescription(), "问题描述");
//        ValidatorUtil.require(qualityDto.getAssociateCharacterId(), "关联功能");

        ValidatorUtil.length(qualityDto.getQualityDescription(), "问题描述", 10, 5000);
        ValidatorUtil.length(qualityDto.getName(), "质量标题", 4, 50);

        RdmsQuality rdmsQuality = CopyUtil.copy(qualityDto, RdmsQuality.class);
        rdmsQuality.setStatus(QualityStatusEnum.SUBMIT.getStatus());

        RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(qualityDto.getSubprojectId());
        rdmsQuality.setProjectManagerId(subproject.getProjectManagerId());
        rdmsQuality.setProductManagerId(subproject.getProductManagerId());

        rdmsQualityService.save(rdmsQuality);
        //填写文件的访问权限人员
        if(!ObjectUtils.isEmpty(rdmsQuality.getFileListStr())){
            List<String> fileIdList = JSON.parseArray(rdmsQuality.getFileListStr(), String.class);
            {
                //设置文件授权 权限
                RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                roleUsersDto.setLoginUserId(qualityDto.getWriterId());
                roleUsersDto.setReceiverId(null);
                RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(qualityDto.getCustomerId());
                roleUsersDto.setBossId(rdmsBossDto.getBossId());
                roleUsersDto.setSuperId(null);
                roleUsersDto.setIpmtId(null);
                roleUsersDto.setPjmId(null);
                roleUsersDto.setPdmId(rdmsQuality.getProductManagerId());
                rdmsFileAuthService.setFileAuthUser(fileIdList, roleUsersDto);
            }
        }

        responseDto.setContent(qualityDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }


    @PostMapping("/saveAnalysis")
    @Transactional
    public ResponseDto<RdmsQualityDto> saveAnalysis(@RequestBody RdmsQualityDto qualityDto) {
        ResponseDto<RdmsQualityDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(qualityDto.getCustomerId(), "客户ID");
        ValidatorUtil.require(qualityDto.getWriterId(), "编制者");
        ValidatorUtil.require(qualityDto.getId(), "质量ID");
        ValidatorUtil.require(qualityDto.getSubprojectId(), "关联项目");
        ValidatorUtil.require(qualityDto.getName(), "质量标题");
        ValidatorUtil.require(qualityDto.getQualityDescription(), "问题描述");
        ValidatorUtil.require(qualityDto.getAnalysisName(), "质量分析标题");
        ValidatorUtil.require(qualityDto.getEssentialAnalysis(), "质量问题本质分析");
        ValidatorUtil.require(qualityDto.getSolution(), "解决方案/处理措施");
        ValidatorUtil.require(qualityDto.getOutputIndicator(), "输出逻辑/交付指标");
        ValidatorUtil.require(qualityDto.getTestMethod(), "验证方法/测试方法");
        ValidatorUtil.require(qualityDto.getJobitemId(), "工单ID");

        ValidatorUtil.length(qualityDto.getQualityDescription(), "需求描述", 10, 2000);
        ValidatorUtil.length(qualityDto.getAnalysisName(), "质量分析标题", 4, 50);
        ValidatorUtil.length(qualityDto.getEssentialAnalysis(), "质量问题本质分析", 10, 2000);
        ValidatorUtil.length(qualityDto.getSolution(), "解决方案/处理措施", 10, 2000);
        ValidatorUtil.length(qualityDto.getOutputIndicator(), "输出逻辑/交付指标", 10, 2000);
        ValidatorUtil.length(qualityDto.getTestMethod(), "验证方法/测试方法", 10, 2000);
        ValidatorUtil.length(qualityDto.getName(), "质量标题", 4, 50);

        RdmsQuality rdmsQuality = CopyUtil.copy(qualityDto, RdmsQuality.class);
        rdmsQuality.setStatus(qualityDto.getStatus());
        rdmsQualityService.save(rdmsQuality);
        //填写文件的访问权限人员
        if(!ObjectUtils.isEmpty(qualityDto.getJobitemPropertyFileIdListStr())){
            List<String> fileIdList = JSON.parseArray(qualityDto.getJobitemPropertyFileIdListStr(), String.class);
            {
                //设置文件授权 权限
                RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                roleUsersDto.setLoginUserId(qualityDto.getWriterId());
                roleUsersDto.setReceiverId(null);
                RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(qualityDto.getCustomerId());
                roleUsersDto.setBossId(rdmsBossDto.getBossId());
                roleUsersDto.setSuperId(null);
                roleUsersDto.setIpmtId(null);
                roleUsersDto.setPjmId(null);
                roleUsersDto.setPdmId(rdmsQuality.getProductManagerId());
                rdmsFileAuthService.setFileAuthUser(fileIdList, roleUsersDto);
            }
        }


        RdmsJobItem jobItem = new RdmsJobItem();
        jobItem.setId(qualityDto.getJobitemId());
        if(qualityDto.getStatus().equals(QualityStatusEnum.SAVE_ANALYSIS.getStatus())){
            jobItem.setStatus(JobItemStatusEnum.HANDLING.getStatus());
        }
        if(qualityDto.getStatus().equals(QualityStatusEnum.JOB_SUBMIT.getStatus())){
            jobItem.setStatus(JobItemStatusEnum.SUBMIT.getStatus());
            jobItem.setActualSubmissionTime(new Date());
        }
        jobItem.setUpdateTime(new Date());
        rdmsJobItemService.updateByPrimaryKeySelective(jobItem);

        //保存附件等资产信息
        RdmsJobItemProperty jobItemProperty = new RdmsJobItemProperty();
        jobItemProperty.setJobItemId(qualityDto.getJobitemId());
        jobItemProperty.setJobDescription("对质量问题进行分析");
        jobItemProperty.setFileListStr(qualityDto.getJobitemPropertyFileIdListStr());
        String propertyId = rdmsJobItemPropertyService.save(jobItemProperty);

        RdmsJobItem jobItem1 = new RdmsJobItem();
        jobItem1.setId(qualityDto.getJobitemId());
        jobItem1.setPropertyId(propertyId);
        jobItem1.setUpdateTime(new Date());
        rdmsJobItemService.updateByPrimaryKeySelective(jobItem1);

        RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(jobItem1.getId());
        RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
        jobItemProcess.setJobItemId(qualityDto.getJobitemId());
        jobItemProcess.setExecutorId(qualityDto.getLoginUserId());
        long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(qualityDto.getJobitemId());
        jobItemProcess.setDeep((int)jobItemProcessCount);
        jobItemProcess.setJobDescription("质量处理工单提交");
        jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.SUBMIT.getStatus());
        jobItemProcess.setNextNode(rdmsJobItem.getProductManagerId());
        rdmsJobItemProcessService.save(jobItemProcess);

        responseDto.setContent(qualityDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/accept/{qualityId}")
    @Transactional
    public ResponseDto<String> accept(@PathVariable String qualityId) {
        ResponseDto<String> responseDto = new ResponseDto<>();

        RdmsQuality rdmsQuality = new RdmsQuality();
        rdmsQuality.setId(qualityId);
        rdmsQuality.setStatus(QualityStatusEnum.ARCHIVED.getStatus());
        rdmsQuality.setUpdateTime(new Date());
        rdmsQualityService.updateByPrimaryKeySelective(rdmsQuality);

        responseDto.setContent(qualityId);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/reject/{qualityId}")
    @Transactional
    public ResponseDto<String> reject(@PathVariable String qualityId) {
        ResponseDto<String> responseDto = new ResponseDto<>();

        RdmsQuality rdmsQuality = new RdmsQuality();
        rdmsQuality.setId(qualityId);
        rdmsQuality.setStatus(QualityStatusEnum.REJECT.getStatus());
        rdmsQuality.setUpdateTime(new Date());
        rdmsQualityService.updateByPrimaryKeySelective(rdmsQuality);

        responseDto.setContent(qualityId);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }


}
