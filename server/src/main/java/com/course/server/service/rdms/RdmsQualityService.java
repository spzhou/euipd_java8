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
import com.course.server.enums.rdms.*;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsQualityMapper;
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
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class RdmsQualityService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsQualityService.class);

    @Resource
    private RdmsQualityMapper rdmsQualityMapper;
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
    public long getQualityRecheckNumByUserId(String userId){
        RdmsQualityExample qualityExample = new RdmsQualityExample();
        qualityExample.createCriteria()
                .andWriterIdEqualTo(userId)
                .andStatusEqualTo(QualityStatusEnum.RECHECK.getStatus())
                .andDeletedEqualTo(0);
        return rdmsQualityMapper.countByExample(qualityExample);
    }

    @Transactional
    public  List<RdmsQuality> getQualityListByPdm(String pdmId){
        RdmsQualityExample qualityExample = new RdmsQualityExample();
        qualityExample.createCriteria()
                .andProductManagerIdEqualTo(pdmId)
                .andDeletedEqualTo(0);
        List<RdmsQuality> rdmsQualities = rdmsQualityMapper.selectByExample(qualityExample);
        return rdmsQualities;
    }

    @Transactional
    public RdmsQualityDto getQualityAndSubmitJobitems(String qualityId) {
        RdmsQuality rdmsQuality = this.selectByPrimaryKey(qualityId);
        RdmsQualityDto rdmsQualityDto = CopyUtil.copy(rdmsQuality, RdmsQualityDto.class);

        rdmsQualityDto.setItemName(rdmsQualityDto.getName());  //为了统一前端字段 名称
        rdmsQualityDto.setDocType(DocTypeEnum.QUALITY.getType());

        if (!ObjectUtils.isEmpty(rdmsQualityDto)) {
            int index = 1;
            List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
            jobitemTypeList.add(JobItemTypeEnum.QUALITY);

            List<RdmsHmiJobItemSimple> jobItemSimpleList = new ArrayList<>();
            List<RdmsJobItemDto> jobItemDtos = rdmsJobItemService.getJobitemListByQualityIdAndStatusTypeList(qualityId, JobItemStatusEnum.SUBMIT.getStatus(), jobitemTypeList);
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
            rdmsQualityDto.setChildren(jobItemSimpleList);
        }
        return rdmsQualityDto;
    }

    @Transactional
    public List<RdmsQualityDto> getQualityCostListByProjectId(String projectId) {
        RdmsQualityExample qualityExample = new RdmsQualityExample();
        qualityExample.setOrderByClause("create_time desc");
        qualityExample.createCriteria().andProjectIdEqualTo(projectId).andDeletedEqualTo(0);
        List<RdmsQuality> rdmsQualities = rdmsQualityMapper.selectByExample(qualityExample);
        List<RdmsQualityDto> rdmsQualityDtos = CopyUtil.copyList(rdmsQualities, RdmsQualityDto.class);
        if(! rdmsQualityDtos.isEmpty()){
            for(RdmsQualityDto qualityDto: rdmsQualityDtos){
                RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(qualityDto.getSubprojectId());
                qualityDto.setSubprojectName(subproject.getLabel());
                if(!ObjectUtils.isEmpty(qualityDto.getCharacterId())){
                    RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(qualityDto.getCharacterId());
                    qualityDto.setCharacterName(rdmsCharacter.getCharacterName());
                }
                if(!ObjectUtils.isEmpty(qualityDto.getJobitemId())){
                    RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(qualityDto.getJobitemId());
                    qualityDto.setJobSerial(rdmsJobItem.getJobSerial());
                }
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(qualityDto.getProjectManagerId());
                qualityDto.setProjectManagerName(rdmsCustomerUser.getTrueName());

                //计算工单成本
                if(! ObjectUtils.isEmpty(qualityDto.getJobitemId()))
                {
                    RdmsHmiJobSuitChargeDto jobSuitChargeDto = rdmsManhourService.calJobitemSuitCharge(qualityDto.getJobitemId());
                    qualityDto.setSumCost(jobSuitChargeDto.getSumCharge());
                    qualityDto.setSumManhourCost(jobSuitChargeDto.getSumManhourCharge());
                    qualityDto.setSumMaterialCost(jobSuitChargeDto.getSumMaterialCharge());
                    qualityDto.setSumFeeCost(jobSuitChargeDto.getSumFeeCharge());
                }
            }
        List<RdmsQualityDto> collect = rdmsQualityDtos.stream().filter(item -> ! ObjectUtils.isEmpty(item.getSumCost()) && ! item.getSumCost().equals(BigDecimal.ZERO)).collect(Collectors.toList());
            return collect;
        }else{
            return null;
        }

    }

    @Transactional
    public RdmsQualityDto getQualityAndAnalysisJobList(String qualityId) {

        RdmsQuality rdmsQuality = this.selectByPrimaryKey(qualityId);
        RdmsQualityDto rdmsQualityDto = CopyUtil.copy(rdmsQuality, RdmsQualityDto.class);

        if (!ObjectUtils.isEmpty(rdmsQualityDto)) {
            int index = 1;
            List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
            jobitemTypeList.add(JobItemTypeEnum.QUALITY);

            List<JobItemStatusEnum> jobItemStatusList = new ArrayList<>();
            jobItemStatusList.add(JobItemStatusEnum.HANDLING);
            jobItemStatusList.add(JobItemStatusEnum.SUBMIT);
            jobItemStatusList.add(JobItemStatusEnum.REFUSE);
            jobItemStatusList.add(JobItemStatusEnum.EVALUATE);
            jobItemStatusList.add(JobItemStatusEnum.APPROVED);
            jobItemStatusList.add(JobItemStatusEnum.COMPLETED);

            List<RdmsJobItemDto> jobItemDtos = rdmsJobItemService.getJobitemListByQualityIdAndStatusListTypeList(qualityId, jobItemStatusList, jobitemTypeList);
            for (RdmsJobItemDto jobItemDto : jobItemDtos) {
                jobItemDto.setDocType(DocTypeEnum.JOBITEM.getType());
                jobItemDto.setItemName(jobItemDto.getJobName());  //为了统一线段字段 名称
                jobItemDto.setIndex(index++);
            }
            List<RdmsHmiJobItemSimple> rdmsHmiJobItemSimples = CopyUtil.copyList(jobItemDtos, RdmsHmiJobItemSimple.class);
            rdmsQualityDto.setChildren(rdmsHmiJobItemSimples);
            rdmsQualityDto.setItemName(rdmsQualityDto.getName());  //为了统一前端字段 名称
            rdmsQualityDto.setDocType(DocTypeEnum.QUALITY.getType());
        }
        return rdmsQualityDto;
    }

    public void list(PageDto<RdmsQualityDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsQualityExample qualityExample = new RdmsQualityExample();

        List<String> statusList = new ArrayList<>();
        statusList.add(QualityStatusEnum.SAVE.getStatus());
        statusList.add(QualityStatusEnum.SUBMIT.getStatus());
        statusList.add(QualityStatusEnum.SAVE_ANALYSIS.getStatus());
        statusList.add(QualityStatusEnum.HANDLING.getStatus());
        statusList.add(QualityStatusEnum.JOB_SUBMIT.getStatus());
        statusList.add(QualityStatusEnum.RECHECK.getStatus());
        statusList.add(QualityStatusEnum.REJECT.getStatus());
        RdmsQualityExample.Criteria criteria = qualityExample.createCriteria();
        criteria.andDeletedEqualTo(0)
                .andWriterIdEqualTo(pageDto.getActor())
                .andStatusIn(statusList)
                .andCustomerIdEqualTo(pageDto.getCustomerId());

        List<RdmsQuality> rdmsQualities = rdmsQualityMapper.selectByExample(qualityExample);
        PageInfo<RdmsQuality> pageInfo = new PageInfo<>(rdmsQualities);
        pageDto.setTotal(pageInfo.getTotal());
        List<RdmsQualityDto> rdmsQualityDtos = CopyUtil.copyList(rdmsQualities, RdmsQualityDto.class);
        if(!CollectionUtils.isEmpty(rdmsQualityDtos)){
            for(RdmsQualityDto  qualityDto: rdmsQualityDtos){
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(qualityDto.getWriterId());
                qualityDto.setWriterName(rdmsCustomerUser.getTrueName());
                RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(qualityDto.getProjectId());
                qualityDto.setProjectName(rdmsProject.getProjectName());
                RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(qualityDto.getSubprojectId());
                qualityDto.setSubprojectName(subproject.getLabel());
                if(!ObjectUtils.isEmpty(qualityDto.getCharacterId())){
                    RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(qualityDto.getCharacterId());
                    qualityDto.setCharacterName(rdmsCharacter.getCharacterName());
                }
            }
        }
        pageDto.setList(rdmsQualityDtos);
    }

    public void listByProjectId(PageDto<RdmsQualityDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsQualityExample qualityExample = new RdmsQualityExample();

        List<String> statusList = new ArrayList<>();
        statusList.add(QualityStatusEnum.SUBMIT.getStatus());
//        statusList.add(QualityStatusEnum.SAVE.getStatus());
//        statusList.add(QualityStatusEnum.SAVE_ANALYSIS.getStatus());
//        statusList.add(QualityStatusEnum.HANDLING.getStatus());
//        statusList.add(QualityStatusEnum.JOB_SUBMIT.getStatus());
//        statusList.add(QualityStatusEnum.RECHECK.getStatus());
//        statusList.add(QualityStatusEnum.REJECT.getStatus());
        RdmsQualityExample.Criteria criteria = qualityExample.createCriteria();
        criteria.andDeletedEqualTo(0)
                .andProjectIdEqualTo(pageDto.getKeyWord())
                .andStatusIn(statusList)
                .andCustomerIdEqualTo(pageDto.getCustomerId());

        List<RdmsQuality> rdmsQualities = rdmsQualityMapper.selectByExample(qualityExample);
        PageInfo<RdmsQuality> pageInfo = new PageInfo<>(rdmsQualities);
        pageDto.setTotal(pageInfo.getTotal());
        List<RdmsQualityDto> rdmsQualityDtos = CopyUtil.copyList(rdmsQualities, RdmsQualityDto.class);
        if(!CollectionUtils.isEmpty(rdmsQualityDtos)){
            for(RdmsQualityDto  qualityDto: rdmsQualityDtos){
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(qualityDto.getWriterId());
                qualityDto.setWriterName(rdmsCustomerUser.getTrueName());
                RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(qualityDto.getProjectId());
                qualityDto.setProjectName(rdmsProject.getProjectName());
                RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(qualityDto.getSubprojectId());
                qualityDto.setSubprojectName(subproject.getLabel());
                if(!ObjectUtils.isEmpty(qualityDto.getCharacterId())){
                    RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(qualityDto.getCharacterId());
                    qualityDto.setCharacterName(rdmsCharacter.getCharacterName());
                }
            }
        }
        pageDto.setList(rdmsQualityDtos);
    }

    public void listByProjectId_handling(PageDto<RdmsQualityDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsQualityExample qualityExample = new RdmsQualityExample();

        List<String> statusList = new ArrayList<>();
//        statusList.add(QualityStatusEnum.SAVE.getStatus());
//        statusList.add(QualityStatusEnum.SUBMIT.getStatus());
        statusList.add(QualityStatusEnum.JOB_SUBMIT.getStatus());
        statusList.add(QualityStatusEnum.SAVE_ANALYSIS.getStatus());
        statusList.add(QualityStatusEnum.HANDLING.getStatus());
        statusList.add(QualityStatusEnum.JOB_SUBMIT.getStatus());
        statusList.add(QualityStatusEnum.RECHECK.getStatus());
//        statusList.add(QualityStatusEnum.REJECT.getStatus());
        RdmsQualityExample.Criteria criteria = qualityExample.createCriteria();
        criteria.andDeletedEqualTo(0)
                .andProjectIdEqualTo(pageDto.getKeyWord())
                .andStatusIn(statusList)
                .andJobitemIdIsNotNull()
                .andCustomerIdEqualTo(pageDto.getCustomerId());

        List<RdmsQuality> rdmsQualities = rdmsQualityMapper.selectByExample(qualityExample);
        PageInfo<RdmsQuality> pageInfo = new PageInfo<>(rdmsQualities);
        pageDto.setTotal(pageInfo.getTotal());
        List<RdmsQualityDto> rdmsQualityDtos = CopyUtil.copyList(rdmsQualities, RdmsQualityDto.class);
        if(!CollectionUtils.isEmpty(rdmsQualityDtos)){
            for(RdmsQualityDto  qualityDto: rdmsQualityDtos){
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(qualityDto.getWriterId());
                qualityDto.setWriterName(rdmsCustomerUser.getTrueName());
                RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(qualityDto.getProjectId());
                qualityDto.setProjectName(rdmsProject.getProjectName());
                RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(qualityDto.getSubprojectId());
                qualityDto.setSubprojectName(subproject.getLabel());
                if(!ObjectUtils.isEmpty(qualityDto.getCharacterId())){
                    RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(qualityDto.getCharacterId());
                    qualityDto.setCharacterName(rdmsCharacter.getCharacterName());
                }
            }
        }
        pageDto.setList(rdmsQualityDtos);
    }

    /**
     */
    @Transactional
    public RdmsQualityDto getQualityAndJobitemList(String qualityId) {

        RdmsQuality rdmsQuality = this.selectByPrimaryKey(qualityId);
        RdmsQualityDto rdmsQualityDto = CopyUtil.copy(rdmsQuality, RdmsQualityDto.class);

        if (!ObjectUtils.isEmpty(rdmsQualityDto)) {
            int index = 1;
            List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
            jobitemTypeList.add(JobItemTypeEnum.QUALITY);

            List<JobItemStatusEnum> jobItemStatusList = new ArrayList<>();
            jobItemStatusList.add(JobItemStatusEnum.HANDLING);
            jobItemStatusList.add(JobItemStatusEnum.SUBMIT);
            jobItemStatusList.add(JobItemStatusEnum.TESTING);

            List<RdmsJobItemDto> jobItemDtos = rdmsJobItemService.getJobitemListByQualityIdAndStatusListTypeList(qualityId, jobItemStatusList, jobitemTypeList);
            for (RdmsJobItemDto jobItemDto : jobItemDtos) {
                jobItemDto.setDocType(DocTypeEnum.JOBITEM.getType());
                jobItemDto.setItemName(jobItemDto.getJobName());  //为了统一线段字段 名称
                jobItemDto.setIndex(index++);
            }
            List<RdmsHmiJobItemSimple> rdmsHmiJobItemSimples = CopyUtil.copyList(jobItemDtos, RdmsHmiJobItemSimple.class);
            rdmsQualityDto.setChildren(rdmsHmiJobItemSimples);
            rdmsQualityDto.setItemName(rdmsQualityDto.getName());  //为了统一前端字段 名称
            rdmsQualityDto.setDocType(DocTypeEnum.QUALITY.getType());
            RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(rdmsQualityDto.getProductManagerId());
            rdmsQualityDto.setProductManagerName(customerUser.getTrueName());
        }
        return rdmsQualityDto;
    }

    /**
     * Customer 查询时, 还是使用PreProjectId 作为参数,
     */
    @Transactional
    public void listQualityByPjm(PageDto<RdmsQualityDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsQualityExample rdmsQualityExample = new RdmsQualityExample();
        rdmsQualityExample.setOrderByClause("create_time desc");

        List<String> statusEnumList = new ArrayList<>();
        statusEnumList.add(QualityStatusEnum.SUBMIT.getStatus());
        statusEnumList.add(QualityStatusEnum.SAVE_ANALYSIS.getStatus());
        statusEnumList.add(QualityStatusEnum.HANDLING.getStatus());
        statusEnumList.add(QualityStatusEnum.JOB_SUBMIT.getStatus());
        statusEnumList.add(QualityStatusEnum.REJECT.getStatus());
        rdmsQualityExample.createCriteria()
                .andCustomerIdEqualTo(pageDto.getKeyWord())
                .andProjectManagerIdEqualTo(pageDto.getActor())
                .andStatusIn(statusEnumList)
                .andDeletedEqualTo(0);
        List<RdmsQuality> rdmsQualities = rdmsQualityMapper.selectByExample(rdmsQualityExample);
        PageInfo<RdmsQuality> pageInfo = new PageInfo<>(rdmsQualities);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsQualityDto> rdmsQualityDtos = CopyUtil.copyList(rdmsQualities, RdmsQualityDto.class);
        for(RdmsQualityDto qualityDto : rdmsQualityDtos){
            this.appendRecordSimpleInfo(qualityDto, pageDto.getLoginUserId());

            List<JobItemStatusEnum> statusList = new ArrayList<>();
            statusList.add(JobItemStatusEnum.SUBMIT);
            Integer jobitemNumberByCharacterAndStatus = rdmsJobItemService.getJobitemNumberByQualityAndStatus(qualityDto.getId(), statusList);
            qualityDto.setSubmittedJobNum(jobitemNumberByCharacterAndStatus);
            Integer applicationNum = rdmsMaterialManageService.getQualityMaterialApplicationNum(qualityDto.getId());
            qualityDto.setMaterialAppNum(applicationNum);
            Integer feeApplicationNum = rdmsFeeManageService.getQualityFeeApplicationNum(qualityDto.getId());
            qualityDto.setFeeAppNum(feeApplicationNum);
        }
        pageDto.setList(rdmsQualityDtos);
    }

    @Transactional
    public void listQualityByPdm(PageDto<RdmsQualityDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsQualityExample rdmsQualityExample = new RdmsQualityExample();
        rdmsQualityExample.setOrderByClause("create_time desc");

        List<String> statusEnumList = new ArrayList<>();
        statusEnumList.add(QualityStatusEnum.SUBMIT.getStatus());
        statusEnumList.add(QualityStatusEnum.SAVE_ANALYSIS.getStatus());
        statusEnumList.add(QualityStatusEnum.HANDLING.getStatus());
        statusEnumList.add(QualityStatusEnum.JOB_SUBMIT.getStatus());
        statusEnumList.add(QualityStatusEnum.REJECT.getStatus());
        rdmsQualityExample.createCriteria()
                .andCustomerIdEqualTo(pageDto.getKeyWord())
                .andProductManagerIdEqualTo(pageDto.getActor())
                .andStatusIn(statusEnumList)
                .andDeletedEqualTo(0);
        List<RdmsQuality> rdmsQualities = rdmsQualityMapper.selectByExample(rdmsQualityExample);
        PageInfo<RdmsQuality> pageInfo = new PageInfo<>(rdmsQualities);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsQualityDto> rdmsQualityDtos = CopyUtil.copyList(rdmsQualities, RdmsQualityDto.class);
        for(RdmsQualityDto qualityDto : rdmsQualityDtos){
            this.appendRecordSimpleInfo(qualityDto, pageDto.getLoginUserId());

            List<JobItemStatusEnum> statusList = new ArrayList<>();
            statusList.add(JobItemStatusEnum.SUBMIT);
            Integer jobitemNumberByCharacterAndStatus = rdmsJobItemService.getJobitemNumberByQualityAndStatus(qualityDto.getId(), statusList);
            qualityDto.setSubmittedJobNum(jobitemNumberByCharacterAndStatus);
            Integer applicationNum = rdmsMaterialManageService.getQualityMaterialApplicationNum(qualityDto.getId());
            qualityDto.setMaterialAppNum(applicationNum);
            Integer feeApplicationNum = rdmsFeeManageService.getQualityFeeApplicationNum(qualityDto.getId());
            qualityDto.setFeeAppNum(feeApplicationNum);
        }
        pageDto.setList(rdmsQualityDtos);
    }

    @Transactional
    public List<RdmsQualityDto> listQualityByCharacterId(String characterId, String loginUserId) {
        RdmsQualityExample rdmsQualityExample = new RdmsQualityExample();
        rdmsQualityExample.setOrderByClause("create_time desc");

        List<String> statusEnumList = new ArrayList<>();
        statusEnumList.add(QualityStatusEnum.COMPLETED.getStatus());
        statusEnumList.add(QualityStatusEnum.ARCHIVED.getStatus());
        rdmsQualityExample.createCriteria()
                .andCharacterIdEqualTo(characterId)
                .andStatusIn(statusEnumList)
                .andDeletedEqualTo(0);
        List<RdmsQuality> rdmsQualities = rdmsQualityMapper.selectByExample(rdmsQualityExample);
        List<RdmsQualityDto> rdmsQualityDtos = CopyUtil.copyList(rdmsQualities, RdmsQualityDto.class);
        for(RdmsQualityDto qualityDto : rdmsQualityDtos){
            this.appendRecordSimpleInfo(qualityDto, loginUserId);
        }
        return rdmsQualityDtos;
    }

    @Transactional
    public long countQualityJobNumByPdm(String userId) {
        RdmsQualityExample rdmsQualityExample = new RdmsQualityExample();
        List<String> statusEnumList = new ArrayList<>();
        statusEnumList.add(QualityStatusEnum.SUBMIT.getStatus());
        statusEnumList.add(QualityStatusEnum.SAVE_ANALYSIS.getStatus());
        statusEnumList.add(QualityStatusEnum.HANDLING.getStatus());
        statusEnumList.add(QualityStatusEnum.JOB_SUBMIT.getStatus());
        statusEnumList.add(QualityStatusEnum.REJECT.getStatus());
        rdmsQualityExample.createCriteria()
                .andProductManagerIdEqualTo(userId)
                .andStatusIn(statusEnumList)
                .andDeletedEqualTo(0);
        return rdmsQualityMapper.countByExample(rdmsQualityExample);
    }

    @Transactional
    public long countQualityDefineSubmitNumByPdm(String userId) {
        RdmsQualityExample rdmsQualityExample = new RdmsQualityExample();
        List<String> statusEnumList = new ArrayList<>();
        statusEnumList.add(QualityStatusEnum.SUBMIT.getStatus());
        rdmsQualityExample.createCriteria()
                .andProjectManagerIdEqualTo(userId)
                .andStatusIn(statusEnumList)
                .andDeletedEqualTo(0);
        return rdmsQualityMapper.countByExample(rdmsQualityExample);
    }

    public void appendRecordSimpleInfo(RdmsQualityDto qualityDto, String loginUserId){
        //private String writerName;
        RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(qualityDto.getWriterId());
        qualityDto.setWriterName(rdmsCustomerUser.getTrueName());
        //private String projectManagerName;
        RdmsCustomerUser rdmsCustomerUser1 = rdmsCustomerUserService.selectByPrimaryKey(qualityDto.getProjectManagerId());
        qualityDto.setProjectManagerName(rdmsCustomerUser1.getTrueName());
        //private String executorName;
        RdmsCustomerUser rdmsCustomerUser2 = rdmsCustomerUserService.selectByPrimaryKey(qualityDto.getExecutorId());
        if(!ObjectUtils.isEmpty(rdmsCustomerUser2)){
            qualityDto.setExecutorName(rdmsCustomerUser2.getTrueName());
        }
        //private String projectName;
        RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(qualityDto.getProjectId());
        qualityDto.setProjectName(rdmsProject.getProjectName());
        //private String subprojectName;
        RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(qualityDto.getSubprojectId());
        qualityDto.setSubprojectName(subproject.getLabel());
        //private String associateProjectNameStr;
        StringBuilder associationNameStr = new StringBuilder();
        List<String> strings = JSON.parseArray(qualityDto.getAssociateProjectStr(), String.class);
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
        qualityDto.setAssociateProjectNameStr(associationNameStr.toString());
        //private String characterName;
        if(!ObjectUtils.isEmpty(qualityDto.getCharacterId())){
            RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(qualityDto.getCharacterId());
            qualityDto.setCharacterName(rdmsCharacter.getCharacterName());
        }
        //private List<RdmsFileDto> fileList;
        List<RdmsFileDto> fileList = rdmsFileService.getFileListByIdListStr(qualityDto.getFileListStr(), loginUserId);
        qualityDto.setFileList(fileList);
    }

    public void listByManager(PageDto<RdmsQualityDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsQualityExample qualityExample = new RdmsQualityExample();

        RdmsQualityExample.Criteria criteria = qualityExample.createCriteria();
        criteria.andDeletedEqualTo(0)
                .andProjectManagerIdEqualTo(pageDto.getActor())
                .andCustomerIdEqualTo(pageDto.getCustomerId());

        List<RdmsQuality> rdmsQualities = rdmsQualityMapper.selectByExample(qualityExample);
        PageInfo<RdmsQuality> pageInfo = new PageInfo<>(rdmsQualities);
        pageDto.setTotal(pageInfo.getTotal());
        List<RdmsQualityDto> rdmsQualityDtos = CopyUtil.copyList(rdmsQualities, RdmsQualityDto.class);
        if(!CollectionUtils.isEmpty(rdmsQualityDtos)){
            for(RdmsQualityDto  qualityDto: rdmsQualityDtos){
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(qualityDto.getWriterId());
                qualityDto.setWriterName(rdmsCustomerUser.getTrueName());
                RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(qualityDto.getSubprojectId());
                qualityDto.setSubprojectName(subproject.getLabel());
            }
        }
        pageDto.setList(rdmsQualityDtos);
    }


    public RdmsQuality selectByPrimaryKey(String id){
        return rdmsQualityMapper.selectByPrimaryKey(id);
    }

    public RdmsQualityDto getQualityDetailInfo(String qualityId, String loginUserId){
        RdmsQuality rdmsQuality = rdmsQualityMapper.selectByPrimaryKey(qualityId);
        RdmsQualityDto qualityDto = CopyUtil.copy(rdmsQuality, RdmsQualityDto.class);
        if(! ObjectUtils.isEmpty(qualityDto)){
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(qualityDto.getWriterId());
            qualityDto.setWriterName(rdmsCustomerUser.getTrueName());

            RdmsCustomerUser rdmsCustomerUser1 = rdmsCustomerUserService.selectByPrimaryKey(qualityDto.getProjectManagerId());
            qualityDto.setProjectManagerName(rdmsCustomerUser1.getTrueName());

            RdmsCustomerUser rdmsCustomerUser3 = rdmsCustomerUserService.selectByPrimaryKey(qualityDto.getProductManagerId());
            qualityDto.setProductManagerName(rdmsCustomerUser3.getTrueName());

            if(! ObjectUtils.isEmpty(qualityDto.getExecutorId())){
                RdmsCustomerUser rdmsCustomerUser2 = rdmsCustomerUserService.selectByPrimaryKey(qualityDto.getExecutorId());
                qualityDto.setExecutorName(rdmsCustomerUser2.getTrueName());
            }
            if(!ObjectUtils.isEmpty(qualityDto.getJobitemId())){
                RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(qualityDto.getJobitemId());
                qualityDto.setJobSerial(rdmsJobItem.getJobSerial());
                qualityDto.setJobName(rdmsJobItem.getJobName());
                qualityDto.setJobCompleteTime(rdmsJobItem.getCompleteTime());
            }

            RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(qualityDto.getSubprojectId());
            qualityDto.setSubprojectName(subproject.getLabel());

            StringBuilder associationNameStr = new StringBuilder();
            List<String> strings = JSON.parseArray(qualityDto.getAssociateProjectStr(), String.class);
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
            qualityDto.setAssociateProjectNameStr(associationNameStr.toString());
            if(!ObjectUtils.isEmpty(qualityDto.getCharacterId())){
                RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(qualityDto.getCharacterId());
                qualityDto.setCharacterName(rdmsCharacter.getCharacterName());
            }
            if(!ObjectUtils.isEmpty(qualityDto.getFileListStr())){
                List<RdmsFileDto> fileList = rdmsFileService.getFileListByIdListStr(qualityDto.getFileListStr(), loginUserId);
                qualityDto.setFileList(fileList);
            }

            if(!ObjectUtils.isEmpty(qualityDto.getFeedbackFileIdListStr())){
                List<RdmsFileDto> fileList = rdmsFileService.getFileListByIdListStr(qualityDto.getFeedbackFileIdListStr(), loginUserId);
                qualityDto.setFeedbackFileList(fileList);
            }

            if(!ObjectUtils.isEmpty(rdmsQuality.getJobitemId())){
                RdmsJobItemDto jobitemDetailInfo = rdmsJobItemService.getJobitemRecordDetailInfo(rdmsQuality.getJobitemId(), loginUserId);
                if(!ObjectUtils.isEmpty(jobitemDetailInfo.getPropertyDto())){
                    qualityDto.setPropertyDto(jobitemDetailInfo.getPropertyDto());
                }
            }

            //如果发了质量处理工单, 则工单必须完成; 如果不需要发工单, 产品经理直接反馈, 则默认处于完成状态
            if(ObjectUtils.isEmpty(qualityDto.getJobitemId())){
                qualityDto.setJobCompleteFlag(true);
            }else{
                RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(qualityDto.getJobitemId());
                if(rdmsJobItem.getStatus().equals(JobItemStatusEnum.COMPLETED.getStatus())){
                    qualityDto.setJobCompleteFlag(true);
                }else{
                    qualityDto.setJobCompleteFlag(false);
                }
            }
        }
        return qualityDto;
    }

    /**
     * 保存
     */
    public String save(RdmsQuality quality) {
        if(ObjectUtils.isEmpty(quality.getId())){
            return this.insert(quality);
        }else{
            RdmsQuality RdmsQuality = this.selectByPrimaryKey(quality.getId());
            if(ObjectUtils.isEmpty(RdmsQuality)){
                return this.insert(quality);
            }else{
                return this.update(quality);
            }
        }
    }

    /**
     * 新增
     */
    private String insert(RdmsQuality quality) {
        if(ObjectUtils.isEmpty(quality.getId())){
            quality.setId(UuidUtil.getShortUuid());
        }
        RdmsQuality RdmsQuality = rdmsQualityMapper.selectByPrimaryKey(quality.getId());
        if(! ObjectUtils.isEmpty(RdmsQuality)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            quality.setQualitySerial(CodeUtil.randomQualityNum());
            quality.setCreateTime(new Date());
            quality.setUpdateTime(new Date());
            quality.setDeleted(0);
            rdmsQualityMapper.insert(quality);
            return quality.getId();
        }
    }

    /**
     * 更新
     */
    public String update(RdmsQuality quality) {
        if(ObjectUtils.isEmpty(quality.getId())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsQuality RdmsQuality = this.selectByPrimaryKey(quality.getId());
        if(ObjectUtils.isEmpty(RdmsQuality)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            quality.setUpdateTime(new Date());
            quality.setDeleted(0);
            rdmsQualityMapper.updateByPrimaryKey(quality);
            return quality.getId();
        }
    }

    public String updateByPrimaryKeySelective(RdmsQuality quality){
        rdmsQualityMapper.updateByPrimaryKeySelective(quality);
        return quality.getId();
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsQuality quality = rdmsQualityMapper.selectByPrimaryKey(id);
        if(!ObjectUtils.isEmpty(quality)){
            quality.setDeleted(1);
            this.update(quality);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

}
