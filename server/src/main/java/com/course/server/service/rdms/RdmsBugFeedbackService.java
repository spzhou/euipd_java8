/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.*;
import com.course.server.dto.PageDto;
import com.course.server.dto.rdms.*;
import com.course.server.enums.rdms.DocTypeEnum;
import com.course.server.enums.rdms.JobItemStatusEnum;
import com.course.server.enums.rdms.JobItemTypeEnum;
import com.course.server.enums.rdms.QualityStatusEnum;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsBugFeedbackMapper;
import com.course.server.service.util.CodeUtil;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RdmsBugFeedbackService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsBugFeedbackService.class);

    @Resource
    private RdmsBugFeedbackMapper rdmsBugFeedbackMapper;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private RdmsSubprojectService rdmsSubprojectService;
    @Resource
    private RdmsFileService rdmsFileService;
    @Resource
    private RdmsJobItemService rdmsJobItemService;
    @Resource
    private RdmsMaterialManageService rdmsMaterialManageService;
    @Resource
    private RdmsFeeManageService rdmsFeeManageService;
    @Resource
    private RdmsCharacterService rdmsCharacterService;
    @Resource
    private RdmsManhourService rdmsManhourService;
    @Resource
    private RdmsProjectService rdmsProjectService;

    @Transactional
    public long getBugFeedbackNumByUserId(String userId){
        RdmsBugFeedbackExample bugFeedbackExample = new RdmsBugFeedbackExample();
        List<String> statusEnumList = new ArrayList<>();
        statusEnumList.add(QualityStatusEnum.SUBMIT.getStatus());
        statusEnumList.add(QualityStatusEnum.SAVE_ANALYSIS.getStatus());
        statusEnumList.add(QualityStatusEnum.HANDLING.getStatus());
        statusEnumList.add(QualityStatusEnum.JOB_SUBMIT.getStatus());
        statusEnumList.add(QualityStatusEnum.REJECT.getStatus());
        bugFeedbackExample.createCriteria()
                .andTestManagerIdEqualTo(userId)
                .andStatusIn(statusEnumList)
                .andDeletedEqualTo(0);
        return rdmsBugFeedbackMapper.countByExample(bugFeedbackExample);
    }


    @Transactional
    public long getBugRecheckNumByUserId(String userId){
        RdmsBugFeedbackExample bugFeedbackExample = new RdmsBugFeedbackExample();
        bugFeedbackExample.createCriteria()
                .andWriterIdEqualTo(userId)
                .andStatusEqualTo(QualityStatusEnum.RECHECK.getStatus())
                .andDeletedEqualTo(0);
        return rdmsBugFeedbackMapper.countByExample(bugFeedbackExample);
    }

    @Transactional
    public  List<RdmsBugFeedback> getBugListByPdm(String pdmId){
        RdmsBugFeedbackExample bugFeedbackExample = new RdmsBugFeedbackExample();
        bugFeedbackExample.createCriteria()
                .andProductManagerIdEqualTo(pdmId)
                .andDeletedEqualTo(0);
        List<RdmsBugFeedback> rdmsBugs = rdmsBugFeedbackMapper.selectByExample(bugFeedbackExample);
        return rdmsBugs;
    }

    @Transactional
    public RdmsBugFeedbackDto getBugAndSubmitJobitems(String bugFeedbackId) {
        RdmsBugFeedback rdmsBugFeedback = this.selectByPrimaryKey(bugFeedbackId);
        RdmsBugFeedbackDto rdmsBugFeedbackDto = CopyUtil.copy(rdmsBugFeedback, RdmsBugFeedbackDto.class);

        rdmsBugFeedbackDto.setItemName(rdmsBugFeedbackDto.getName());  //为了统一前端字段 名称
        rdmsBugFeedbackDto.setDocType(DocTypeEnum.BUG.getType());

        if (!ObjectUtils.isEmpty(rdmsBugFeedbackDto)) {
            int index = 1;
            List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
            jobitemTypeList.add(JobItemTypeEnum.TASK_BUG);

            List<RdmsHmiJobItemSimple> jobItemSimpleList = new ArrayList<>();
            List<RdmsJobItemDto> jobItemDtos = rdmsJobItemService.getJobitemListByBugIdAndStatusTypeList(bugFeedbackId, JobItemStatusEnum.SUBMIT.getStatus(), jobitemTypeList);
            for (RdmsJobItemDto jobItemDto : jobItemDtos) {
                RdmsHmiJobItemSimple simple = new RdmsHmiJobItemSimple();
                simple.setId(jobItemDto.getId());
                simple.setJobSerial(jobItemDto.getJobSerial());
                simple.setJobName(jobItemDto.getJobName());
                simple.setItemName(jobItemDto.getJobName());  //为了统一线段字段 名称
                simple.setStatus(jobItemDto.getStatus());
                simple.setType(jobItemDto.getType());
                simple.setProjectType(jobItemDto.getProjectType());
                simple.setDocType(DocTypeEnum.JOBITEM.getType());
                simple.setIndex(index++);
                jobItemSimpleList.add(simple);
            }
            rdmsBugFeedbackDto.setChildren(jobItemSimpleList);
        }
        return rdmsBugFeedbackDto;
    }

    @Transactional
    public List<RdmsBugFeedbackDto> getBugCostListByProjectId(String projectId) {
        RdmsBugFeedbackExample bugFeedbackExample = new RdmsBugFeedbackExample();
        bugFeedbackExample.setOrderByClause("create_time desc");
        bugFeedbackExample.createCriteria().andProjectIdEqualTo(projectId).andDeletedEqualTo(0);
        List<RdmsBugFeedback> rdmsBugFeedbacks = rdmsBugFeedbackMapper.selectByExample(bugFeedbackExample);
        List<RdmsBugFeedbackDto> rdmsBugFeedbackDtos = CopyUtil.copyList(rdmsBugFeedbacks, RdmsBugFeedbackDto.class);
        if(! rdmsBugFeedbackDtos.isEmpty()){
            for(RdmsBugFeedbackDto bugFeedbackDto: rdmsBugFeedbackDtos){
                RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(bugFeedbackDto.getSubprojectId());
                bugFeedbackDto.setSubprojectName(subproject.getLabel());
                if(!ObjectUtils.isEmpty(bugFeedbackDto.getCharacterId())){
                    RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(bugFeedbackDto.getCharacterId());
                    bugFeedbackDto.setCharacterName(rdmsCharacter.getCharacterName());
                }
                if(!ObjectUtils.isEmpty(bugFeedbackDto.getJobitemId())){
                    RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(bugFeedbackDto.getJobitemId());
                    bugFeedbackDto.setJobSerial(rdmsJobItem.getJobSerial());
                }
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(bugFeedbackDto.getProjectManagerId());
                bugFeedbackDto.setProjectManagerName(rdmsCustomerUser.getTrueName());

                //计算工单成本
                if(! ObjectUtils.isEmpty(bugFeedbackDto.getJobitemId()))
                {
                    RdmsHmiJobSuitChargeDto jobSuitChargeDto = rdmsManhourService.calJobitemSuitCharge(bugFeedbackDto.getJobitemId());
                    bugFeedbackDto.setSumCost(jobSuitChargeDto.getSumCharge());
                    bugFeedbackDto.setSumManhourCost(jobSuitChargeDto.getSumManhourCharge());
                    bugFeedbackDto.setSumMaterialCost(jobSuitChargeDto.getSumMaterialCharge());
                    bugFeedbackDto.setSumFeeCost(jobSuitChargeDto.getSumFeeCharge());
                }
            }
        List<RdmsBugFeedbackDto> collect = rdmsBugFeedbackDtos.stream().filter(item -> ! ObjectUtils.isEmpty(item.getSumCost()) && ! item.getSumCost().equals(BigDecimal.ZERO)).collect(Collectors.toList());
            return collect;
        }else{
            return null;
        }

    }

    @Transactional
    public RdmsBugFeedbackDto getBugAndAnalysisJobList(String bugFeedbackId) {

        RdmsBugFeedback rdmsBugFeedback = this.selectByPrimaryKey(bugFeedbackId);
        RdmsBugFeedbackDto rdmsBugFeedbackDto = CopyUtil.copy(rdmsBugFeedback, RdmsBugFeedbackDto.class);

        if (!ObjectUtils.isEmpty(rdmsBugFeedbackDto)) {
            int index = 1;
            List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
            jobitemTypeList.add(JobItemTypeEnum.TASK_BUG);

            List<JobItemStatusEnum> jobItemStatusList = new ArrayList<>();
            jobItemStatusList.add(JobItemStatusEnum.HANDLING);
            jobItemStatusList.add(JobItemStatusEnum.SUBMIT);
            jobItemStatusList.add(JobItemStatusEnum.REFUSE);
            jobItemStatusList.add(JobItemStatusEnum.EVALUATE);
            jobItemStatusList.add(JobItemStatusEnum.APPROVED);
            jobItemStatusList.add(JobItemStatusEnum.COMPLETED);

            List<RdmsJobItemDto> jobItemDtos = rdmsJobItemService.getJobitemListByQualityIdAndStatusListTypeList(bugFeedbackId, jobItemStatusList, jobitemTypeList);
            for (RdmsJobItemDto jobItemDto : jobItemDtos) {
                jobItemDto.setDocType(DocTypeEnum.JOBITEM.getType());
                jobItemDto.setItemName(jobItemDto.getJobName());  //为了统一线段字段 名称
                jobItemDto.setIndex(index++);
            }
            List<RdmsHmiJobItemSimple> rdmsHmiJobItemSimples = CopyUtil.copyList(jobItemDtos, RdmsHmiJobItemSimple.class);
            rdmsBugFeedbackDto.setChildren(rdmsHmiJobItemSimples);
            rdmsBugFeedbackDto.setItemName(rdmsBugFeedbackDto.getName());  //为了统一前端字段 名称
            rdmsBugFeedbackDto.setDocType(DocTypeEnum.BUG.getType());
        }
        return rdmsBugFeedbackDto;
    }

    public void list(PageDto<RdmsBugFeedbackDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsBugFeedbackExample bugFeedbackExample = new RdmsBugFeedbackExample();

        List<String> statusList = new ArrayList<>();
        statusList.add(QualityStatusEnum.SAVE.getStatus());
        statusList.add(QualityStatusEnum.SUBMIT.getStatus());
        statusList.add(QualityStatusEnum.SAVE_ANALYSIS.getStatus());
        statusList.add(QualityStatusEnum.HANDLING.getStatus());
        statusList.add(QualityStatusEnum.JOB_SUBMIT.getStatus());
        statusList.add(QualityStatusEnum.RECHECK.getStatus());
        statusList.add(QualityStatusEnum.REJECT.getStatus());
        RdmsBugFeedbackExample.Criteria criteria = bugFeedbackExample.createCriteria();
        criteria.andDeletedEqualTo(0)
                .andWriterIdEqualTo(pageDto.getActor())
                .andStatusIn(statusList)
                .andCustomerIdEqualTo(pageDto.getCustomerId());

        List<RdmsBugFeedback> rdmsBugFeedbacks = rdmsBugFeedbackMapper.selectByExample(bugFeedbackExample);
        PageInfo<RdmsBugFeedback> pageInfo = new PageInfo<>(rdmsBugFeedbacks);
        pageDto.setTotal(pageInfo.getTotal());
        List<RdmsBugFeedbackDto> rdmsBugFeedbackDtos = CopyUtil.copyList(rdmsBugFeedbacks, RdmsBugFeedbackDto.class);
        if(!CollectionUtils.isEmpty(rdmsBugFeedbackDtos)){
            for(RdmsBugFeedbackDto  bugFeedbackDto: rdmsBugFeedbackDtos){
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(bugFeedbackDto.getWriterId());
                bugFeedbackDto.setWriterName(rdmsCustomerUser.getTrueName());
                RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(bugFeedbackDto.getProjectId());
                bugFeedbackDto.setProjectName(rdmsProject.getProjectName());
                RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(bugFeedbackDto.getSubprojectId());
                bugFeedbackDto.setSubprojectName(subproject.getLabel());
                if(!ObjectUtils.isEmpty(bugFeedbackDto.getCharacterId())){
                    RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(bugFeedbackDto.getCharacterId());
                    bugFeedbackDto.setCharacterName(rdmsCharacter.getCharacterName());
                }
            }
        }
        pageDto.setList(rdmsBugFeedbackDtos);
    }

    public void listByProjectId(PageDto<RdmsBugFeedbackDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsBugFeedbackExample bugFeedbackExample = new RdmsBugFeedbackExample();

        List<String> statusList = new ArrayList<>();
        statusList.add(QualityStatusEnum.SUBMIT.getStatus());
//        statusList.add(QualityStatusEnum.SAVE.getStatus());
//        statusList.add(QualityStatusEnum.SAVE_ANALYSIS.getStatus());
//        statusList.add(QualityStatusEnum.HANDLING.getStatus());
//        statusList.add(QualityStatusEnum.JOB_SUBMIT.getStatus());
//        statusList.add(QualityStatusEnum.RECHECK.getStatus());
//        statusList.add(QualityStatusEnum.REJECT.getStatus());
        RdmsBugFeedbackExample.Criteria criteria = bugFeedbackExample.createCriteria();
        criteria.andDeletedEqualTo(0)
                .andProjectIdEqualTo(pageDto.getKeyWord())
                .andStatusIn(statusList)
                .andCustomerIdEqualTo(pageDto.getCustomerId());

        List<RdmsBugFeedback> rdmsBugFeedbacks = rdmsBugFeedbackMapper.selectByExample(bugFeedbackExample);
        PageInfo<RdmsBugFeedback> pageInfo = new PageInfo<>(rdmsBugFeedbacks);
        pageDto.setTotal(pageInfo.getTotal());
        List<RdmsBugFeedbackDto> rdmsBugFeedbackDtos = CopyUtil.copyList(rdmsBugFeedbacks, RdmsBugFeedbackDto.class);
        if(!CollectionUtils.isEmpty(rdmsBugFeedbackDtos)){
            for(RdmsBugFeedbackDto  bugFeedbackDto: rdmsBugFeedbackDtos){
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(bugFeedbackDto.getWriterId());
                bugFeedbackDto.setWriterName(rdmsCustomerUser.getTrueName());
                RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(bugFeedbackDto.getProjectId());
                bugFeedbackDto.setProjectName(rdmsProject.getProjectName());
                RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(bugFeedbackDto.getSubprojectId());
                bugFeedbackDto.setSubprojectName(subproject.getLabel());
                if(!ObjectUtils.isEmpty(bugFeedbackDto.getCharacterId())){
                    RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(bugFeedbackDto.getCharacterId());
                    bugFeedbackDto.setCharacterName(rdmsCharacter.getCharacterName());
                }
            }
        }
        pageDto.setList(rdmsBugFeedbackDtos);
    }

    public void listByProjectId_handling(PageDto<RdmsBugFeedbackDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsBugFeedbackExample bugFeedbackExample = new RdmsBugFeedbackExample();

        List<String> statusList = new ArrayList<>();
//        statusList.add(QualityStatusEnum.SAVE.getStatus());
//        statusList.add(QualityStatusEnum.SUBMIT.getStatus());
        statusList.add(QualityStatusEnum.JOB_SUBMIT.getStatus());
        statusList.add(QualityStatusEnum.SAVE_ANALYSIS.getStatus());
        statusList.add(QualityStatusEnum.HANDLING.getStatus());
        statusList.add(QualityStatusEnum.JOB_SUBMIT.getStatus());
        statusList.add(QualityStatusEnum.RECHECK.getStatus());
//        statusList.add(QualityStatusEnum.REJECT.getStatus());
        RdmsBugFeedbackExample.Criteria criteria = bugFeedbackExample.createCriteria();
        criteria.andDeletedEqualTo(0)
                .andProjectIdEqualTo(pageDto.getKeyWord())
                .andStatusIn(statusList)
                .andJobitemIdIsNotNull()
                .andCustomerIdEqualTo(pageDto.getCustomerId());

        List<RdmsBugFeedback> rdmsBugFeedbacks = rdmsBugFeedbackMapper.selectByExample(bugFeedbackExample);
        PageInfo<RdmsBugFeedback> pageInfo = new PageInfo<>(rdmsBugFeedbacks);
        pageDto.setTotal(pageInfo.getTotal());
        List<RdmsBugFeedbackDto> rdmsBugFeedbackDtos = CopyUtil.copyList(rdmsBugFeedbacks, RdmsBugFeedbackDto.class);
        if(!CollectionUtils.isEmpty(rdmsBugFeedbackDtos)){
            for(RdmsBugFeedbackDto  bugFeedbackDto: rdmsBugFeedbackDtos){
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(bugFeedbackDto.getWriterId());
                bugFeedbackDto.setWriterName(rdmsCustomerUser.getTrueName());
                RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(bugFeedbackDto.getProjectId());
                bugFeedbackDto.setProjectName(rdmsProject.getProjectName());
                RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(bugFeedbackDto.getSubprojectId());
                bugFeedbackDto.setSubprojectName(subproject.getLabel());
                if(!ObjectUtils.isEmpty(bugFeedbackDto.getCharacterId())){
                    RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(bugFeedbackDto.getCharacterId());
                    bugFeedbackDto.setCharacterName(rdmsCharacter.getCharacterName());
                }
            }
        }
        pageDto.setList(rdmsBugFeedbackDtos);
    }

    /**
     */
    @Transactional
    public RdmsBugFeedbackDto getBugAndJobitemList(String bugfeedbackId) {

        RdmsBugFeedback rdmsBugFeedback = this.selectByPrimaryKey(bugfeedbackId);
        RdmsBugFeedbackDto rdmsBugFeedbackDto = CopyUtil.copy(rdmsBugFeedback, RdmsBugFeedbackDto.class);

        if (!ObjectUtils.isEmpty(rdmsBugFeedbackDto)) {
            int index = 1;
            List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
            jobitemTypeList.add(JobItemTypeEnum.TASK_BUG);

            List<JobItemStatusEnum> jobItemStatusList = new ArrayList<>();
            jobItemStatusList.add(JobItemStatusEnum.HANDLING);
            jobItemStatusList.add(JobItemStatusEnum.SUBMIT);
            jobItemStatusList.add(JobItemStatusEnum.TESTING);

            List<RdmsJobItemDto> jobItemDtos = rdmsJobItemService.getJobitemListByQualityIdAndStatusListTypeList(bugfeedbackId, jobItemStatusList, jobitemTypeList);
            for (RdmsJobItemDto jobItemDto : jobItemDtos) {
                jobItemDto.setDocType(DocTypeEnum.JOBITEM.getType());
                jobItemDto.setItemName(jobItemDto.getJobName());  //为了统一线段字段 名称
                jobItemDto.setIndex(index++);
            }
            List<RdmsHmiJobItemSimple> rdmsHmiJobItemSimples = CopyUtil.copyList(jobItemDtos, RdmsHmiJobItemSimple.class);
            rdmsBugFeedbackDto.setChildren(rdmsHmiJobItemSimples);
            rdmsBugFeedbackDto.setItemName(rdmsBugFeedbackDto.getName());  //为了统一前端字段 名称
            rdmsBugFeedbackDto.setDocType(DocTypeEnum.BUG.getType());
            RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(rdmsBugFeedbackDto.getProductManagerId());
            rdmsBugFeedbackDto.setProductManagerName(customerUser.getTrueName());
        }
        return rdmsBugFeedbackDto;
    }

    /**
     * Customer 查询时, 还是使用PreProjectId 作为参数,
     */
    @Transactional
    public void listBugByPjm(PageDto<RdmsBugFeedbackDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsBugFeedbackExample rdmsBugFeedbackExample = new RdmsBugFeedbackExample();
        rdmsBugFeedbackExample.setOrderByClause("create_time desc");

        List<String> statusEnumList = new ArrayList<>();
        statusEnumList.add(QualityStatusEnum.SUBMIT.getStatus());
        statusEnumList.add(QualityStatusEnum.SAVE_ANALYSIS.getStatus());
        statusEnumList.add(QualityStatusEnum.HANDLING.getStatus());
        statusEnumList.add(QualityStatusEnum.JOB_SUBMIT.getStatus());
        statusEnumList.add(QualityStatusEnum.REJECT.getStatus());
        rdmsBugFeedbackExample.createCriteria()
                .andCustomerIdEqualTo(pageDto.getKeyWord())
                .andProjectManagerIdEqualTo(pageDto.getActor())
                .andStatusIn(statusEnumList)
                .andDeletedEqualTo(0);
        List<RdmsBugFeedback> rdmsBugFeedbacks = rdmsBugFeedbackMapper.selectByExample(rdmsBugFeedbackExample);
        PageInfo<RdmsBugFeedback> pageInfo = new PageInfo<>(rdmsBugFeedbacks);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsBugFeedbackDto> rdmsBugFeedbackDtos = CopyUtil.copyList(rdmsBugFeedbacks, RdmsBugFeedbackDto.class);
        for(RdmsBugFeedbackDto bugFeedbackDto : rdmsBugFeedbackDtos){
            this.appendRecordSimpleInfo(bugFeedbackDto, pageDto.getLoginUserId());

            List<JobItemStatusEnum> statusList = new ArrayList<>();
            statusList.add(JobItemStatusEnum.SUBMIT);
            Integer jobitemNumberByCharacterAndStatus = rdmsJobItemService.getJobitemNumberByQualityAndStatus(bugFeedbackDto.getId(), statusList);
            bugFeedbackDto.setSubmittedJobNum(jobitemNumberByCharacterAndStatus);
            Integer applicationNum = rdmsMaterialManageService.getQualityMaterialApplicationNum(bugFeedbackDto.getId());
            bugFeedbackDto.setMaterialAppNum(applicationNum);
            Integer feeApplicationNum = rdmsFeeManageService.getQualityFeeApplicationNum(bugFeedbackDto.getId());
            bugFeedbackDto.setFeeAppNum(feeApplicationNum);
        }
        pageDto.setList(rdmsBugFeedbackDtos);
    }

    @Transactional
    public void listBugByTestManager(PageDto<RdmsBugFeedbackDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsBugFeedbackExample rdmsBugFeedbackExample = new RdmsBugFeedbackExample();
        rdmsBugFeedbackExample.setOrderByClause("create_time desc");

        List<String> statusEnumList = new ArrayList<>();
        statusEnumList.add(QualityStatusEnum.SUBMIT.getStatus());
        statusEnumList.add(QualityStatusEnum.SAVE_ANALYSIS.getStatus());
        statusEnumList.add(QualityStatusEnum.HANDLING.getStatus());
        statusEnumList.add(QualityStatusEnum.JOB_SUBMIT.getStatus());
        statusEnumList.add(QualityStatusEnum.REJECT.getStatus());
        rdmsBugFeedbackExample.createCriteria()
                .andCustomerIdEqualTo(pageDto.getCustomerId())
                .andTestManagerIdEqualTo(pageDto.getActor())
                .andStatusIn(statusEnumList)
                .andDeletedEqualTo(0);
        List<RdmsBugFeedback> rdmsBugFeedbacks = rdmsBugFeedbackMapper.selectByExample(rdmsBugFeedbackExample);
        PageInfo<RdmsBugFeedback> pageInfo = new PageInfo<>(rdmsBugFeedbacks);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsBugFeedbackDto> rdmsBugFeedbackDtos = CopyUtil.copyList(rdmsBugFeedbacks, RdmsBugFeedbackDto.class);
        for(RdmsBugFeedbackDto bugFeedbackDto : rdmsBugFeedbackDtos){
            this.appendRecordSimpleInfo(bugFeedbackDto, pageDto.getLoginUserId());

            List<JobItemStatusEnum> statusList = new ArrayList<>();
            statusList.add(JobItemStatusEnum.SUBMIT);
            Integer jobitemNumberByCharacterAndStatus = rdmsJobItemService.getJobitemNumberByQualityAndStatus(bugFeedbackDto.getId(), statusList);
            bugFeedbackDto.setSubmittedJobNum(jobitemNumberByCharacterAndStatus);
            Integer applicationNum = rdmsMaterialManageService.getQualityMaterialApplicationNum(bugFeedbackDto.getId());
            bugFeedbackDto.setMaterialAppNum(applicationNum);
            Integer feeApplicationNum = rdmsFeeManageService.getQualityFeeApplicationNum(bugFeedbackDto.getId());
            bugFeedbackDto.setFeeAppNum(feeApplicationNum);
        }
        pageDto.setList(rdmsBugFeedbackDtos);
    }

    @Transactional
    public void getBuaArchiveListBySubprojectId(PageDto<RdmsBugFeedbackDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsBugFeedbackExample rdmsBugFeedbackExample = new RdmsBugFeedbackExample();
        rdmsBugFeedbackExample.setOrderByClause("create_time desc");

        List<String> statusEnumList = new ArrayList<>();
        statusEnumList.add(QualityStatusEnum.ARCHIVED.getStatus());
        rdmsBugFeedbackExample.createCriteria()
                .andCustomerIdEqualTo(pageDto.getCustomerId())
                .andSubprojectIdEqualTo(pageDto.getSubprojectId())
                .andStatusIn(statusEnumList)
                .andDeletedEqualTo(0);
        List<RdmsBugFeedback> rdmsBugFeedbacks = rdmsBugFeedbackMapper.selectByExample(rdmsBugFeedbackExample);
        PageInfo<RdmsBugFeedback> pageInfo = new PageInfo<>(rdmsBugFeedbacks);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsBugFeedbackDto> rdmsBugFeedbackDtos = CopyUtil.copyList(rdmsBugFeedbacks, RdmsBugFeedbackDto.class);
        for(RdmsBugFeedbackDto bugFeedbackDto : rdmsBugFeedbackDtos){
            this.appendRecordSimpleInfo(bugFeedbackDto, pageDto.getLoginUserId());

            List<JobItemStatusEnum> statusList = new ArrayList<>();
            statusList.add(JobItemStatusEnum.SUBMIT);
            Integer jobitemNumberByCharacterAndStatus = rdmsJobItemService.getJobitemNumberByQualityAndStatus(bugFeedbackDto.getId(), statusList);
            bugFeedbackDto.setSubmittedJobNum(jobitemNumberByCharacterAndStatus);
            Integer applicationNum = rdmsMaterialManageService.getQualityMaterialApplicationNum(bugFeedbackDto.getId());
            bugFeedbackDto.setMaterialAppNum(applicationNum);
            Integer feeApplicationNum = rdmsFeeManageService.getQualityFeeApplicationNum(bugFeedbackDto.getId());
            bugFeedbackDto.setFeeAppNum(feeApplicationNum);
        }
        pageDto.setList(rdmsBugFeedbackDtos);
    }

    @Transactional
    public List<RdmsBugFeedbackDto> listBugByCharacterId(String characterId, String loginUserId) {
        RdmsBugFeedbackExample rdmsBugFeedbackExample = new RdmsBugFeedbackExample();
        rdmsBugFeedbackExample.setOrderByClause("create_time desc");

        List<String> statusEnumList = new ArrayList<>();
        statusEnumList.add(QualityStatusEnum.COMPLETED.getStatus());
        statusEnumList.add(QualityStatusEnum.ARCHIVED.getStatus());
        rdmsBugFeedbackExample.createCriteria()
                .andCharacterIdEqualTo(characterId)
                .andStatusIn(statusEnumList)
                .andDeletedEqualTo(0);
        List<RdmsBugFeedback> rdmsBugFeedbacks = rdmsBugFeedbackMapper.selectByExample(rdmsBugFeedbackExample);
        List<RdmsBugFeedbackDto> rdmsBugFeedbackDtos = CopyUtil.copyList(rdmsBugFeedbacks, RdmsBugFeedbackDto.class);
        for(RdmsBugFeedbackDto bugFeedbackDto : rdmsBugFeedbackDtos){
            this.appendRecordSimpleInfo(bugFeedbackDto, loginUserId);
        }
        return rdmsBugFeedbackDtos;
    }

    @Transactional
    public long countBugJobNumByPdm(String userId) {
        RdmsBugFeedbackExample rdmsBugFeedbackExample = new RdmsBugFeedbackExample();
        List<String> statusEnumList = new ArrayList<>();
        statusEnumList.add(QualityStatusEnum.SUBMIT.getStatus());
        statusEnumList.add(QualityStatusEnum.SAVE_ANALYSIS.getStatus());
        statusEnumList.add(QualityStatusEnum.HANDLING.getStatus());
        statusEnumList.add(QualityStatusEnum.JOB_SUBMIT.getStatus());
        statusEnumList.add(QualityStatusEnum.REJECT.getStatus());
        rdmsBugFeedbackExample.createCriteria()
                .andProductManagerIdEqualTo(userId)
                .andStatusIn(statusEnumList)
                .andDeletedEqualTo(0);
        return rdmsBugFeedbackMapper.countByExample(rdmsBugFeedbackExample);
    }

    @Transactional
    public long countBugDefineSubmitNumByPdm(String userId) {
        RdmsBugFeedbackExample rdmsBugFeedbackExample = new RdmsBugFeedbackExample();
        List<String> statusEnumList = new ArrayList<>();
        statusEnumList.add(QualityStatusEnum.SUBMIT.getStatus());
        rdmsBugFeedbackExample.createCriteria()
                .andProjectManagerIdEqualTo(userId)
                .andStatusIn(statusEnumList)
                .andDeletedEqualTo(0);
        return rdmsBugFeedbackMapper.countByExample(rdmsBugFeedbackExample);
    }

    public void appendRecordSimpleInfo(RdmsBugFeedbackDto bugFeedbackDto, String loginUserId){
        //private String writerName;
        RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(bugFeedbackDto.getWriterId());
        bugFeedbackDto.setWriterName(rdmsCustomerUser.getTrueName());
        //private String projectManagerName;
        RdmsCustomerUser rdmsCustomerUser1 = rdmsCustomerUserService.selectByPrimaryKey(bugFeedbackDto.getProjectManagerId());
        bugFeedbackDto.setProjectManagerName(rdmsCustomerUser1.getTrueName());
        //private String executorName;
        RdmsCustomerUser rdmsCustomerUser2 = rdmsCustomerUserService.selectByPrimaryKey(bugFeedbackDto.getExecutorId());
        if(!ObjectUtils.isEmpty(rdmsCustomerUser2)){
            bugFeedbackDto.setExecutorName(rdmsCustomerUser2.getTrueName());
        }
        //private String projectName;
        RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(bugFeedbackDto.getProjectId());
        bugFeedbackDto.setProjectName(rdmsProject.getProjectName());
        //private String subprojectName;
        RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(bugFeedbackDto.getSubprojectId());
        bugFeedbackDto.setSubprojectName(subproject.getLabel());
        //private String associateProjectNameStr;
        StringBuilder associationNameStr = new StringBuilder();
        List<String> strings = JSON.parseArray(bugFeedbackDto.getAssociateProjectStr(), String.class);
        if(! CollectionUtils.isEmpty(strings)){
            for(String id: strings){
                RdmsProjectSubproject subproject1 = rdmsSubprojectService.selectByPrimaryKey(id);
                if(ObjectUtils.isEmpty(associationNameStr)){
                    associationNameStr.append(subproject1.getLabel());
                }else{
                    associationNameStr.append("->").append(subproject1.getLabel());
                }
            }
        }
        bugFeedbackDto.setAssociateProjectNameStr(associationNameStr.toString());
        //private String characterName;
        if(!ObjectUtils.isEmpty(bugFeedbackDto.getCharacterId())){
            RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(bugFeedbackDto.getCharacterId());
            bugFeedbackDto.setCharacterName(rdmsCharacter.getCharacterName());
        }
        //private List<RdmsFileDto> fileList;
        List<RdmsFileDto> fileList = rdmsFileService.getFileListByIdListStr(bugFeedbackDto.getFileListStr(), loginUserId);
        bugFeedbackDto.setFileList(fileList);
    }

    public void listByManager(PageDto<RdmsBugFeedbackDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsBugFeedbackExample bugFeedbackExample = new RdmsBugFeedbackExample();

        RdmsBugFeedbackExample.Criteria criteria = bugFeedbackExample.createCriteria();
        criteria.andDeletedEqualTo(0)
                .andProjectManagerIdEqualTo(pageDto.getActor())
                .andCustomerIdEqualTo(pageDto.getCustomerId());

        List<RdmsBugFeedback> rdmsBugFeedbacks = rdmsBugFeedbackMapper.selectByExample(bugFeedbackExample);
        PageInfo<RdmsBugFeedback> pageInfo = new PageInfo<>(rdmsBugFeedbacks);
        pageDto.setTotal(pageInfo.getTotal());
        List<RdmsBugFeedbackDto> rdmsBugFeedbackDtos = CopyUtil.copyList(rdmsBugFeedbacks, RdmsBugFeedbackDto.class);
        if(!CollectionUtils.isEmpty(rdmsBugFeedbackDtos)){
            for(RdmsBugFeedbackDto  bugFeedbackDto: rdmsBugFeedbackDtos){
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(bugFeedbackDto.getWriterId());
                bugFeedbackDto.setWriterName(rdmsCustomerUser.getTrueName());
                RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(bugFeedbackDto.getSubprojectId());
                bugFeedbackDto.setSubprojectName(subproject.getLabel());
            }
        }
        pageDto.setList(rdmsBugFeedbackDtos);
    }


    public RdmsBugFeedback selectByPrimaryKey(String id){
        return rdmsBugFeedbackMapper.selectByPrimaryKey(id);
    }

    public RdmsBugFeedbackDto getBugDetailInfo(String bugfeedbackId, String loginUserId){
        RdmsBugFeedback rdmsBugFeedback = rdmsBugFeedbackMapper.selectByPrimaryKey(bugfeedbackId);
        RdmsBugFeedbackDto bugFeedbackDto = CopyUtil.copy(rdmsBugFeedback, RdmsBugFeedbackDto.class);
        if(! ObjectUtils.isEmpty(bugFeedbackDto)){
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(bugFeedbackDto.getWriterId());
            bugFeedbackDto.setWriterName(rdmsCustomerUser.getTrueName());

            RdmsCustomerUser rdmsCustomerUser1 = rdmsCustomerUserService.selectByPrimaryKey(bugFeedbackDto.getProjectManagerId());
            bugFeedbackDto.setProjectManagerName(rdmsCustomerUser1.getTrueName());

            RdmsCustomerUser rdmsCustomerUser3 = rdmsCustomerUserService.selectByPrimaryKey(bugFeedbackDto.getProductManagerId());
            bugFeedbackDto.setProductManagerName(rdmsCustomerUser3.getTrueName());

            RdmsCustomerUser rdmsCustomerUser4 = rdmsCustomerUserService.selectByPrimaryKey(bugFeedbackDto.getTestManagerId());
            bugFeedbackDto.setTestManagerName(rdmsCustomerUser4.getTrueName());

            if(! ObjectUtils.isEmpty(bugFeedbackDto.getExecutorId())){
                RdmsCustomerUser rdmsCustomerUser2 = rdmsCustomerUserService.selectByPrimaryKey(bugFeedbackDto.getExecutorId());
                bugFeedbackDto.setExecutorName(rdmsCustomerUser2.getTrueName());
            }
            if(!ObjectUtils.isEmpty(bugFeedbackDto.getJobitemId())){
                RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(bugFeedbackDto.getJobitemId());
                bugFeedbackDto.setJobSerial(rdmsJobItem.getJobSerial());
                bugFeedbackDto.setJobName(rdmsJobItem.getJobName());
                bugFeedbackDto.setJobCompleteTime(rdmsJobItem.getCompleteTime());
            }

            RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(bugFeedbackDto.getSubprojectId());
            bugFeedbackDto.setSubprojectName(subproject.getLabel());

            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(bugFeedbackDto.getProjectId());
            bugFeedbackDto.setProjectName(rdmsProject.getProjectName());

            StringBuilder associationNameStr = new StringBuilder();
            List<String> strings = JSON.parseArray(bugFeedbackDto.getAssociateProjectStr(), String.class);
            if(! CollectionUtils.isEmpty(strings)){
                for(String id: strings){
                    RdmsProjectSubproject subproject1 = rdmsSubprojectService.selectByPrimaryKey(id);
                    if(ObjectUtils.isEmpty(associationNameStr)){
                        associationNameStr.append(subproject1.getLabel());
                    }else{
                        associationNameStr.append("->").append(subproject1.getLabel());
                    }
                }
            }
            bugFeedbackDto.setAssociateProjectNameStr(associationNameStr.toString());
            if(!ObjectUtils.isEmpty(bugFeedbackDto.getCharacterId())){
                RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(bugFeedbackDto.getCharacterId());
                bugFeedbackDto.setCharacterName(rdmsCharacter.getCharacterName());
            }
            if(!ObjectUtils.isEmpty(bugFeedbackDto.getFileListStr())){
                List<RdmsFileDto> fileList = rdmsFileService.getFileListByIdListStr(bugFeedbackDto.getFileListStr(), loginUserId);
                bugFeedbackDto.setFileList(fileList);
            }

            if(!ObjectUtils.isEmpty(bugFeedbackDto.getFeedbackFileIdListStr())){
                List<RdmsFileDto> fileList = rdmsFileService.getFileListByIdListStr(bugFeedbackDto.getFeedbackFileIdListStr(), loginUserId);
                bugFeedbackDto.setFeedbackFileList(fileList);
            }

            if(!ObjectUtils.isEmpty(rdmsBugFeedback.getJobitemId())){
                RdmsJobItemDto jobitemDetailInfo = rdmsJobItemService.getJobitemRecordDetailInfo(rdmsBugFeedback.getJobitemId(), loginUserId);
                if(!ObjectUtils.isEmpty(jobitemDetailInfo.getPropertyDto())){
                    bugFeedbackDto.setPropertyDto(jobitemDetailInfo.getPropertyDto());
                }
            }

            //如果发了质量处理工单, 则工单必须完成; 如果不需要发工单, 产品经理直接反馈, 则默认处于完成状态
            if(ObjectUtils.isEmpty(bugFeedbackDto.getJobitemId())){
                bugFeedbackDto.setJobCompleteFlag(true);
            }else{
                RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(bugFeedbackDto.getJobitemId());
                if(rdmsJobItem.getStatus().equals(JobItemStatusEnum.COMPLETED.getStatus())){
                    bugFeedbackDto.setJobCompleteFlag(true);
                }else{
                    bugFeedbackDto.setJobCompleteFlag(false);
                }
            }
        }
        return bugFeedbackDto;
    }

    /**
     * 保存
     */
    public String save(RdmsBugFeedback bugFeedback) {
        if(ObjectUtils.isEmpty(bugFeedback.getId())){
            return this.insert(bugFeedback);
        }else{
            RdmsBugFeedback RdmsBugFeedback = this.selectByPrimaryKey(bugFeedback.getId());
            if(ObjectUtils.isEmpty(RdmsBugFeedback)){
                return this.insert(bugFeedback);
            }else{
                return this.update(bugFeedback);
            }
        }
    }

    /**
     * 新增
     */
    private String insert(RdmsBugFeedback bugFeedback) {
        if(ObjectUtils.isEmpty(bugFeedback.getId())){
            bugFeedback.setId(UuidUtil.getShortUuid());
        }
        RdmsBugFeedback RdmsBugFeedback = rdmsBugFeedbackMapper.selectByPrimaryKey(bugFeedback.getId());
        if(! ObjectUtils.isEmpty(RdmsBugFeedback)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            bugFeedback.setBugSerial(CodeUtil.randomQualityNum());
            bugFeedback.setCreateTime(new Date());
            bugFeedback.setUpdateTime(new Date());
            bugFeedback.setDeleted(0);
            rdmsBugFeedbackMapper.insert(bugFeedback);
            return bugFeedback.getId();
        }
    }

    /**
     * 更新
     */
    public String update(RdmsBugFeedback bugFeedback) {
        if(ObjectUtils.isEmpty(bugFeedback.getId())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsBugFeedback RdmsBugFeedback = this.selectByPrimaryKey(bugFeedback.getId());
        if(ObjectUtils.isEmpty(RdmsBugFeedback)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            bugFeedback.setUpdateTime(new Date());
            bugFeedback.setDeleted(0);
            rdmsBugFeedbackMapper.updateByPrimaryKey(bugFeedback);
            return bugFeedback.getId();
        }
    }

    public String updateByPrimaryKeySelective(RdmsBugFeedback bugFeedback){
        rdmsBugFeedbackMapper.updateByPrimaryKeySelective(bugFeedback);
        return bugFeedback.getId();
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsBugFeedback bugFeedback = rdmsBugFeedbackMapper.selectByPrimaryKey(id);
        if(!ObjectUtils.isEmpty(bugFeedback)){
            bugFeedback.setDeleted(1);
            this.update(bugFeedback);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

}
