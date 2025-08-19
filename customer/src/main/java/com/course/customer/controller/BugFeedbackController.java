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
import com.course.server.enums.rdms.JobItemProcessStatusEnum;
import com.course.server.enums.rdms.JobItemStatusEnum;
import com.course.server.enums.rdms.QualityStatusEnum;
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
@RequestMapping("/bugfeedback")
public class BugFeedbackController {
    private static final Logger LOG = LoggerFactory.getLogger(BugFeedbackController.class);
    public static final String BUSINESS_NAME = "缺陷管理";

    @Resource
    private RdmsBugFeedbackService rdmsBugFeedbackService;
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
    @Autowired
    private RdmsTgmService rdmsTgmService;

    @PostMapping("/getBugFeedbackNumByUserId/{userId}")
    public ResponseDto<Long> getBugFeedbackNumByUserId(@PathVariable String userId) {
        ResponseDto<Long> responseDto = new ResponseDto<>();
        long testManageNum = rdmsBugFeedbackService.getBugFeedbackNumByUserId(userId);
        responseDto.setContent(testManageNum);
        return responseDto;
    }




    @PostMapping("/getBugRecheckNumByUserId/{userId}")
    public ResponseDto<Long> getBugRecheckNumByUserId(@PathVariable String userId) {
        ResponseDto<Long> responseDto = new ResponseDto<>();
        long testManageNum = rdmsBugFeedbackService.getBugRecheckNumByUserId(userId);
        responseDto.setContent(testManageNum);
        return responseDto;
    }

    @PostMapping("/getBugDefineSubmitNumByPdm/{userId}")
    public ResponseDto<Long> getBugDefineSubmitNumByPdm(@PathVariable String userId) {
        ResponseDto<Long> responseDto = new ResponseDto<>();
        long qualityItemNum = rdmsBugFeedbackService.countBugDefineSubmitNumByPdm(userId);
        responseDto.setContent(qualityItemNum);
        return responseDto;
    }

    @PostMapping("/getBugAndSubmitJobitems/{bugfeedbackId}")
    public ResponseDto<RdmsBugFeedbackDto> getBugAndSubmitJobitems(@PathVariable String bugfeedbackId) {
        ResponseDto<RdmsBugFeedbackDto> responseDto = new ResponseDto<>();
        RdmsBugFeedbackDto bugAndSubmitJobitems = rdmsBugFeedbackService.getBugAndSubmitJobitems(bugfeedbackId);
        responseDto.setContent(bugAndSubmitJobitems);
        return responseDto;
    }

    @PostMapping("/getBugCostListByProjectId/{projectId}")
    public ResponseDto<List<RdmsBugFeedbackDto>> getBugCostListByProjectId(@PathVariable String projectId) {
        ResponseDto<List<RdmsBugFeedbackDto>> responseDto = new ResponseDto<>();
        List<RdmsBugFeedbackDto> qualityCostListByProjectId = rdmsBugFeedbackService.getBugCostListByProjectId(projectId);
        responseDto.setContent(qualityCostListByProjectId);
        return responseDto;
    }

    @PostMapping("/getBugHasListFlag/{userId}")
    public ResponseDto<Boolean> getBugHasListFlag(@PathVariable String userId) {
        ResponseDto<Boolean> responseDto = new ResponseDto<>();
        long l = rdmsBugFeedbackService.countBugJobNumByPdm(userId);
        responseDto.setContent(l >0);
        return responseDto;
    }

    @PostMapping("/getBugAndAnalysisJobList/{qualityId}")
    public ResponseDto<RdmsBugFeedbackDto> getCharacterAndJobitemList(@PathVariable String qualityId) {
        ResponseDto<RdmsBugFeedbackDto> responseDto = new ResponseDto<>();
        RdmsBugFeedbackDto qualityAndAnalysisJobList = rdmsBugFeedbackService.getBugAndAnalysisJobList(qualityId);
        responseDto.setContent(qualityAndAnalysisJobList);
        return responseDto;
    }

    @PostMapping("/list")
    public ResponseDto<PageDto<RdmsBugFeedbackDto>> list(@RequestBody PageDto<RdmsBugFeedbackDto> pageDto) {
        ResponseDto<PageDto<RdmsBugFeedbackDto>> responseDto = new ResponseDto<>();
        rdmsBugFeedbackService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/listByProjectId")
    public ResponseDto<PageDto<RdmsBugFeedbackDto>> listByProjectId(@RequestBody PageDto<RdmsBugFeedbackDto> pageDto) {
        ResponseDto<PageDto<RdmsBugFeedbackDto>> responseDto = new ResponseDto<>();
        rdmsBugFeedbackService.listByProjectId(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/listByProjectId_handling")
    public ResponseDto<PageDto<RdmsBugFeedbackDto>> listByProjectId_handling(@RequestBody PageDto<RdmsBugFeedbackDto> pageDto) {
        ResponseDto<PageDto<RdmsBugFeedbackDto>> responseDto = new ResponseDto<>();
        rdmsBugFeedbackService.listByProjectId_handling(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/getBugAndJobitemList/{bugId}")
    public ResponseDto<RdmsBugFeedbackDto> getBugAndJobitemList(@PathVariable String bugId) {
        ResponseDto<RdmsBugFeedbackDto> responseDto = new ResponseDto<>();
        RdmsBugFeedbackDto bugAndJobitemList = rdmsBugFeedbackService.getBugAndJobitemList(bugId);
        responseDto.setContent(bugAndJobitemList);
        return responseDto;
    }


    @PostMapping("/listBugByPjm")
    public ResponseDto<PageDto<RdmsBugFeedbackDto>> listBugByPjm(@RequestBody PageDto<RdmsBugFeedbackDto> pageDto) {
        ResponseDto<PageDto<RdmsBugFeedbackDto>> responseDto = new ResponseDto<>();
        rdmsBugFeedbackService.listBugByPjm(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/listByTestManager")
    public ResponseDto<PageDto<RdmsBugFeedbackDto>> listByTestManager(@RequestBody PageDto<RdmsBugFeedbackDto> pageDto) {
        ResponseDto<PageDto<RdmsBugFeedbackDto>> responseDto = new ResponseDto<>();
        rdmsBugFeedbackService.listBugByTestManager(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/getBuaArchiveListBySubprojectId")
    public ResponseDto<PageDto<RdmsBugFeedbackDto>> getBuaArchiveListBySubprojectId(@RequestBody PageDto<RdmsBugFeedbackDto> pageDto) {
        ResponseDto<PageDto<RdmsBugFeedbackDto>> responseDto = new ResponseDto<>();
        rdmsBugFeedbackService.getBuaArchiveListBySubprojectId(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/listBugByCharacterId")
    public ResponseDto<List<RdmsBugFeedbackDto>> listBugByCharacterId(@RequestParam String characterId, String loginUserId) {
        ResponseDto<List<RdmsBugFeedbackDto>> responseDto = new ResponseDto<>();
        List<RdmsBugFeedbackDto> rdmsBugDtos = rdmsBugFeedbackService.listBugByCharacterId(characterId, loginUserId);
        responseDto.setContent(rdmsBugDtos);
        return responseDto;
    }

    @PostMapping("/listByManager")
    public ResponseDto<PageDto<RdmsBugFeedbackDto>> listByManager(@RequestBody PageDto<RdmsBugFeedbackDto> pageDto) {
        ResponseDto<PageDto<RdmsBugFeedbackDto>> responseDto = new ResponseDto<>();
        rdmsBugFeedbackService.listByManager(pageDto);
        responseDto.setContent(pageDto);
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

    @PostMapping("/getBugDetailInfo")
    public ResponseDto<RdmsBugFeedbackDto> getBugDetailInfo(@RequestParam String bugfeedbackId, @RequestParam String loginUserId) {
        ResponseDto<RdmsBugFeedbackDto> responseDto = new ResponseDto<>();
        RdmsBugFeedbackDto bugDetailInfo = rdmsBugFeedbackService.getBugDetailInfo(bugfeedbackId, loginUserId);
        responseDto.setContent(bugDetailInfo);
        return responseDto;
    }

    @PostMapping("/getProjectTreeListByCustomerId/{customerId}")
    public ResponseDto<List<RdmsHmiTree<RdmsHmiTreeInfoDto>>> getProjectTreeListByCustomerId(@PathVariable String customerId) {
        ResponseDto<List<RdmsHmiTree<RdmsHmiTreeInfoDto>>> responseDto = new ResponseDto<>();
        List<RdmsHmiTree<RdmsHmiTreeInfoDto>> projectTreeListByCustomerId = rdmsSubprojectService.getOnGoingProjectTreeListByCustomerId(customerId);
        if(!CollectionUtils.isEmpty(projectTreeListByCustomerId)){
            for(RdmsHmiTree<RdmsHmiTreeInfoDto> hmiTree: projectTreeListByCustomerId){
                hmiTree.setValue(hmiTree.getId());
                hmiTree.setParent(customerId);
            }
        }
        responseDto.setContent(projectTreeListByCustomerId);
        return responseDto;
    }

    @PostMapping("/save")
    @Transactional
    public ResponseDto<RdmsBugFeedbackDto> save(@RequestBody RdmsBugFeedbackDto bugFeedbackDto) {
        ResponseDto<RdmsBugFeedbackDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(bugFeedbackDto.getCustomerId(), "客户ID");
        ValidatorUtil.require(bugFeedbackDto.getWriterId(), "编制者");
        ValidatorUtil.require(bugFeedbackDto.getId(), "缺陷ID");
        ValidatorUtil.require(bugFeedbackDto.getSubprojectId(), "关联项目");
        ValidatorUtil.require(bugFeedbackDto.getName(), "缺陷标题");
        ValidatorUtil.require(bugFeedbackDto.getBugDescription(), "缺陷描述");
        ValidatorUtil.require(bugFeedbackDto.getPlanSubmissionTime(), "责成提交时间");

        ValidatorUtil.length(bugFeedbackDto.getBugDescription(), "缺陷描述", 10, 2000);
        ValidatorUtil.length(bugFeedbackDto.getName(), "缺陷标题", 4, 50);

        RdmsBugFeedback rdmsBug = CopyUtil.copy(bugFeedbackDto, RdmsBugFeedback.class);
        rdmsBug.setStatus(QualityStatusEnum.SAVE.getStatus());
        RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(bugFeedbackDto.getSubprojectId());
        rdmsBug.setProjectManagerId(subproject.getProjectManagerId());
        rdmsBug.setProductManagerId(subproject.getProductManagerId());
        rdmsBug.setTestManagerId(subproject.getTestManagerId());

        rdmsBugFeedbackService.save(rdmsBug);
        //填写文件的访问权限人员
        if(!ObjectUtils.isEmpty(rdmsBug.getFileListStr())){
            List<String> fileIdList = JSON.parseArray(rdmsBug.getFileListStr(), String.class);
            {
                //设置文件授权 权限
                RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                roleUsersDto.setLoginUserId(bugFeedbackDto.getWriterId());
                roleUsersDto.setReceiverId(null);
                RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(bugFeedbackDto.getCustomerId());
                roleUsersDto.setBossId(rdmsBossDto.getBossId());
                roleUsersDto.setSuperId(null);
                roleUsersDto.setIpmtId(null);
                roleUsersDto.setPjmId(subproject.getProjectManagerId());
                roleUsersDto.setPdmId(null);
                roleUsersDto.setTmId(subproject.getTestManagerId());  //给测试主管权限
                RdmsTgmDto tgmByCustomerId = rdmsTgmService.getTgmByCustomerId(subproject.getCustomerId());
                roleUsersDto.setTgmId(tgmByCustomerId.getTgmId());
                rdmsFileAuthService.setFileAuthUser(fileIdList, roleUsersDto);
            }
        }

        responseDto.setContent(bugFeedbackDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/saveBugFeedbackInfo")
    @Transactional
    public ResponseDto<String> saveBugFeedbackInfo(@RequestBody RdmsBugFeedbackDto bugFeedbackDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();

        ValidatorUtil.require(bugFeedbackDto.getId(), "缺陷ID");
        ValidatorUtil.require(bugFeedbackDto.getFeedbackDescription(), "反馈描述");
        ValidatorUtil.length(bugFeedbackDto.getFeedbackDescription(), "需求描述", 10, 2000);

        RdmsBugFeedback bugfeedback = new RdmsBugFeedback();
        bugfeedback.setId(bugFeedbackDto.getId());
        bugfeedback.setFeedbackDescription(bugFeedbackDto.getFeedbackDescription());
        bugfeedback.setFeedbackFileIdListStr(bugFeedbackDto.getFeedbackFileIdListStr());
        String id = rdmsBugFeedbackService.updateByPrimaryKeySelective(bugfeedback);

        //填写文件的访问权限人员
        if(!ObjectUtils.isEmpty(bugFeedbackDto.getFeedbackFileIdListStr())){
            List<String> fileIdList = JSON.parseArray(bugFeedbackDto.getFeedbackFileIdListStr(), String.class);
            {
                //设置文件授权 权限
                RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                roleUsersDto.setLoginUserId(bugFeedbackDto.getWriterId());
                roleUsersDto.setReceiverId(null);
                RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(bugFeedbackDto.getCustomerId());
                roleUsersDto.setBossId(rdmsBossDto.getBossId());
                roleUsersDto.setSuperId(null);
                roleUsersDto.setIpmtId(null);
                RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(bugFeedbackDto.getSubprojectId());
                roleUsersDto.setPjmId(subproject.getProjectManagerId());
                roleUsersDto.setPdmId(null);
                roleUsersDto.setTmId(subproject.getTestManagerId());  //给测试主管权限
                RdmsTgmDto tgmByCustomerId = rdmsTgmService.getTgmByCustomerId(subproject.getCustomerId());
                roleUsersDto.setTgmId(tgmByCustomerId.getTgmId());
                rdmsFileAuthService.setFileAuthUser(fileIdList, roleUsersDto);
            }
        }

        responseDto.setContent(id);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/submitBugFeedbackInfo")
    @Transactional
    public ResponseDto<String> submitBugFeedbackInfo(@RequestBody RdmsBugFeedbackDto bugFeedbackDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();

        ValidatorUtil.require(bugFeedbackDto.getId(), "缺陷ID");
        ValidatorUtil.require(bugFeedbackDto.getFeedbackDescription(), "反馈描述");
        ValidatorUtil.length(bugFeedbackDto.getFeedbackDescription(), "需求描述", 10, 2000);

        RdmsBugFeedback bugfeedback = new RdmsBugFeedback();
        bugfeedback.setId(bugFeedbackDto.getId());
        bugfeedback.setFeedbackDescription(bugFeedbackDto.getFeedbackDescription());
        bugfeedback.setFeedbackFileIdListStr(bugFeedbackDto.getFeedbackFileIdListStr());
        bugfeedback.setStatus(QualityStatusEnum.RECHECK.getStatus());
        String id = rdmsBugFeedbackService.updateByPrimaryKeySelective(bugfeedback);

        //填写文件的访问权限人员
        if(!ObjectUtils.isEmpty(bugFeedbackDto.getFeedbackFileIdListStr())){
            List<String> fileIdList = JSON.parseArray(bugFeedbackDto.getFeedbackFileIdListStr(), String.class);
            {
                //设置文件授权 权限
                RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                roleUsersDto.setLoginUserId(bugFeedbackDto.getWriterId());
                roleUsersDto.setReceiverId(null);
                RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(bugFeedbackDto.getCustomerId());
                roleUsersDto.setBossId(rdmsBossDto.getBossId());
                roleUsersDto.setSuperId(null);
                roleUsersDto.setIpmtId(null);
                RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(bugFeedbackDto.getSubprojectId());
                roleUsersDto.setPjmId(subproject.getProjectManagerId());
                roleUsersDto.setPdmId(null);
                roleUsersDto.setTmId(subproject.getTestManagerId());  //给测试主管权限
                RdmsTgmDto tgmByCustomerId = rdmsTgmService.getTgmByCustomerId(subproject.getCustomerId());
                roleUsersDto.setTgmId(tgmByCustomerId.getTgmId());
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
    public ResponseDto<RdmsBugFeedbackDto> submit(@RequestBody RdmsBugFeedbackDto bugFeedbackDto) {
        ResponseDto<RdmsBugFeedbackDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(bugFeedbackDto.getCustomerId(), "客户ID");
        ValidatorUtil.require(bugFeedbackDto.getWriterId(), "编制者");
        ValidatorUtil.require(bugFeedbackDto.getId(), "缺陷ID");
        ValidatorUtil.require(bugFeedbackDto.getSubprojectId(), "关联项目");
        ValidatorUtil.require(bugFeedbackDto.getName(), "缺陷标题");
        ValidatorUtil.require(bugFeedbackDto.getBugDescription(), "缺陷描述");
        ValidatorUtil.require(bugFeedbackDto.getPlanSubmissionTime(), "责成提交时间");

        ValidatorUtil.length(bugFeedbackDto.getBugDescription(), "缺陷描述", 10, 2000);
        ValidatorUtil.length(bugFeedbackDto.getName(), "缺陷标题", 4, 50);

        RdmsBugFeedback rdmsBug = CopyUtil.copy(bugFeedbackDto, RdmsBugFeedback.class);
        rdmsBug.setStatus(QualityStatusEnum.SUBMIT.getStatus());

        RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(bugFeedbackDto.getSubprojectId());
        rdmsBug.setProjectManagerId(subproject.getProjectManagerId());
        rdmsBug.setProductManagerId(subproject.getProductManagerId());
        rdmsBug.setTestManagerId(subproject.getTestManagerId());

        rdmsBugFeedbackService.save(rdmsBug);
        //填写文件的访问权限人员
        if(!ObjectUtils.isEmpty(rdmsBug.getFileListStr())){
            List<String> fileIdList = JSON.parseArray(rdmsBug.getFileListStr(), String.class);
            {
                //设置文件授权 权限
                RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                roleUsersDto.setLoginUserId(bugFeedbackDto.getWriterId());
                roleUsersDto.setReceiverId(null);
                RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(bugFeedbackDto.getCustomerId());
                roleUsersDto.setBossId(rdmsBossDto.getBossId());
                roleUsersDto.setSuperId(null);
                roleUsersDto.setIpmtId(null);
                roleUsersDto.setPjmId(subproject.getProjectManagerId());
                roleUsersDto.setPdmId(null);
                roleUsersDto.setTmId(subproject.getTestManagerId());  //给测试主管权限
                RdmsTgmDto tgmByCustomerId = rdmsTgmService.getTgmByCustomerId(subproject.getCustomerId());
                roleUsersDto.setTgmId(tgmByCustomerId.getTgmId());
                rdmsFileAuthService.setFileAuthUser(fileIdList, roleUsersDto);
            }
        }

        responseDto.setContent(bugFeedbackDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }


    @PostMapping("/saveAnalysis")
    @Transactional
    public ResponseDto<RdmsBugFeedbackDto> saveAnalysis(@RequestBody RdmsBugFeedbackDto bugFeedbackDto) {
        ResponseDto<RdmsBugFeedbackDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(bugFeedbackDto.getCustomerId(), "机构ID");
        ValidatorUtil.require(bugFeedbackDto.getWriterId(), "编制者");
        ValidatorUtil.require(bugFeedbackDto.getId(), "缺陷ID");
        ValidatorUtil.require(bugFeedbackDto.getSubprojectId(), "关联项目");
        ValidatorUtil.require(bugFeedbackDto.getName(), "缺陷标题");
        ValidatorUtil.require(bugFeedbackDto.getBugDescription(), "缺陷描述");
        ValidatorUtil.require(bugFeedbackDto.getAnalysisName(), "缺陷分析标题");
        ValidatorUtil.require(bugFeedbackDto.getEssentialAnalysis(), "缺陷问题本质分析");
        ValidatorUtil.require(bugFeedbackDto.getSolution(), "解决方案/处理措施");
        ValidatorUtil.require(bugFeedbackDto.getOutputIndicator(), "输出逻辑/交付指标");
        ValidatorUtil.require(bugFeedbackDto.getTestMethod(), "验证方法/测试方法");
        ValidatorUtil.require(bugFeedbackDto.getJobitemId(), "任务ID");

        ValidatorUtil.length(bugFeedbackDto.getBugDescription(), "缺陷描述", 10, 2000);
        ValidatorUtil.length(bugFeedbackDto.getAnalysisName(), "缺陷分析标题", 4, 50);
        ValidatorUtil.length(bugFeedbackDto.getEssentialAnalysis(), "缺陷问题本质分析", 10, 2000);
        ValidatorUtil.length(bugFeedbackDto.getSolution(), "解决方案/处理措施", 10, 2000);
        ValidatorUtil.length(bugFeedbackDto.getOutputIndicator(), "输出逻辑/交付指标", 10, 2000);
        ValidatorUtil.length(bugFeedbackDto.getTestMethod(), "验证方法/测试方法", 10, 2000);
        ValidatorUtil.length(bugFeedbackDto.getName(), "缺陷标题", 4, 50);

        RdmsBugFeedback rdmsBug = CopyUtil.copy(bugFeedbackDto, RdmsBugFeedback.class);
        rdmsBug.setStatus(bugFeedbackDto.getStatus());
        rdmsBugFeedbackService.save(rdmsBug);
        //填写文件的访问权限人员
        if(!ObjectUtils.isEmpty(bugFeedbackDto.getJobitemPropertyFileIdListStr())){
            List<String> fileIdList = JSON.parseArray(bugFeedbackDto.getJobitemPropertyFileIdListStr(), String.class);
            {
                //设置文件授权 权限
                RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                roleUsersDto.setLoginUserId(bugFeedbackDto.getWriterId());
                roleUsersDto.setReceiverId(null);
                RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(bugFeedbackDto.getCustomerId());
                roleUsersDto.setBossId(rdmsBossDto.getBossId());
                roleUsersDto.setSuperId(null);
                roleUsersDto.setIpmtId(null);
                RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(bugFeedbackDto.getSubprojectId());
                roleUsersDto.setPjmId(subproject.getProjectManagerId());
                roleUsersDto.setPdmId(null);
                roleUsersDto.setTmId(subproject.getTestManagerId());  //给测试主管权限
                RdmsTgmDto tgmByCustomerId = rdmsTgmService.getTgmByCustomerId(subproject.getCustomerId());
                roleUsersDto.setTgmId(tgmByCustomerId.getTgmId());
                rdmsFileAuthService.setFileAuthUser(fileIdList, roleUsersDto);
            }
        }

        RdmsJobItem jobItem = new RdmsJobItem();
        jobItem.setId(bugFeedbackDto.getJobitemId());
        if(bugFeedbackDto.getStatus().equals(QualityStatusEnum.SAVE_ANALYSIS.getStatus())){
            jobItem.setStatus(JobItemStatusEnum.HANDLING.getStatus());
        }
        if(bugFeedbackDto.getStatus().equals(QualityStatusEnum.JOB_SUBMIT.getStatus())){
            jobItem.setStatus(JobItemStatusEnum.SUBMIT.getStatus());
            jobItem.setActualSubmissionTime(new Date());
        }
        jobItem.setUpdateTime(new Date());
        rdmsJobItemService.updateByPrimaryKeySelective(jobItem);

        //保存附件等资产信息
        RdmsJobItemProperty jobItemProperty = new RdmsJobItemProperty();
        jobItemProperty.setJobItemId(bugFeedbackDto.getJobitemId());
        jobItemProperty.setJobDescription("对缺陷问题进行分析");
        jobItemProperty.setFileListStr(bugFeedbackDto.getJobitemPropertyFileIdListStr());
        String propertyId = rdmsJobItemPropertyService.save(jobItemProperty);

        RdmsJobItem jobItem1 = new RdmsJobItem();
        jobItem1.setId(bugFeedbackDto.getJobitemId());
        jobItem1.setPropertyId(propertyId);
        jobItem1.setUpdateTime(new Date());
        rdmsJobItemService.updateByPrimaryKeySelective(jobItem1);

        RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
        jobItemProcess.setJobItemId(bugFeedbackDto.getJobitemId());
        jobItemProcess.setExecutorId(bugFeedbackDto.getLoginUserId());
        long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(bugFeedbackDto.getJobitemId());
        jobItemProcess.setDeep((int)jobItemProcessCount);
        jobItemProcess.setJobDescription("缺陷处理工单提交");
        jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.SUBMIT.getStatus());
        jobItemProcess.setNextNode(bugFeedbackDto.getTestManagerId());
        rdmsJobItemProcessService.save(jobItemProcess);

        responseDto.setContent(bugFeedbackDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/accept/{bugfeedbackId}")
    @Transactional
    public ResponseDto<String> accept(@PathVariable String bugfeedbackId) {
        ResponseDto<String> responseDto = new ResponseDto<>();

        RdmsBugFeedback rdmsBug = new RdmsBugFeedback();
        rdmsBug.setId(bugfeedbackId);
        rdmsBug.setStatus(QualityStatusEnum.ARCHIVED.getStatus());
        rdmsBug.setUpdateTime(new Date());
        rdmsBugFeedbackService.updateByPrimaryKeySelective(rdmsBug);

        responseDto.setContent(bugfeedbackId);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/reject/{bugfeedbackId}")
    @Transactional
    public ResponseDto<String> reject(@PathVariable String bugfeedbackId) {
        ResponseDto<String> responseDto = new ResponseDto<>();

        RdmsBugFeedback rdmsBug = new RdmsBugFeedback();
        rdmsBug.setId(bugfeedbackId);
        rdmsBug.setStatus(QualityStatusEnum.REJECT.getStatus());
        rdmsBug.setUpdateTime(new Date());
        rdmsBugFeedbackService.updateByPrimaryKeySelective(rdmsBug);

        responseDto.setContent(bugfeedbackId);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }
}
