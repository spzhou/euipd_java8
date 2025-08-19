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
import com.course.server.dto.rdms.RdmsFileDto;
import com.course.server.dto.rdms.RdmsFeeManageDto;
import com.course.server.dto.rdms.RdmsMaterialManageDto;
import com.course.server.enums.rdms.*;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsFeeManageMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.naming.spi.DirObjectFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RdmsFeeManageService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsFeeManageService.class);

    @Resource
    private RdmsFeeManageMapper rdmsFeeManageMapper;
    @Resource
    private RdmsCharacterService rdmsCharacterService;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private RdmsCustomerService rdmsCustomerService;
    @Resource
    private RdmsFileService rdmsFileService;
    @Resource
    private RdmsQualityService rdmsQualityService;
    @Autowired
    private RdmsJobItemService rdmsJobItemService;


    public List<RdmsFeeManage> getListByNextNodeId(String nextNodeId) {
        RdmsFeeManageExample feeExample = new RdmsFeeManageExample();
        feeExample.setOrderByClause("update_time desc");
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.APPLICATION.getStatus());
        statusList.add(ApplicationStatusEnum.APPROVED.getStatus());
        statusList.add(ApplicationStatusEnum.SUBMIT.getStatus());
        statusList.add(ApplicationStatusEnum.PRE_COMPLETE.getStatus());
        feeExample.createCriteria()
                .andNextNodeEqualTo(nextNodeId)
                .andStatusIn(statusList)
                .andDeletedEqualTo(0);
        List<RdmsFeeManage> rdmsFeeManages = rdmsFeeManageMapper.selectByExample(feeExample);
        return rdmsFeeManages;
    }

    public List<RdmsFeeManageDto> getInCostListByJobitemId(String jobitemId){
        RdmsFeeManageExample feeExample = new RdmsFeeManageExample();
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.APPROVED.getStatus());
        statusList.add(ApplicationStatusEnum.SUBMIT.getStatus());
        statusList.add(ApplicationStatusEnum.SUPPLEMENT.getStatus());
        statusList.add(ApplicationStatusEnum.PRE_COMPLETE.getStatus());
        statusList.add(ApplicationStatusEnum.COMPLETE.getStatus());
        feeExample.createCriteria()
                .andJobitemIdEqualTo(jobitemId)
                .andStatusIn(statusList)
                .andDeletedEqualTo(0);
        List<RdmsFeeManage> rdmsFeeManages = rdmsFeeManageMapper.selectByExample(feeExample);
        return CopyUtil.copyList(rdmsFeeManages, RdmsFeeManageDto.class);
    }

    public Long getApplicationNumByUserId(String customerUserId){
        List<RdmsQuality> qualityListByPdm = rdmsQualityService.getQualityListByPdm(customerUserId);
        if(!CollectionUtils.isEmpty(qualityListByPdm)){
            List<String> stringList = qualityListByPdm.stream().map(RdmsQuality::getId).collect(Collectors.toList());
            RdmsFeeManageExample feeManageExample = new RdmsFeeManageExample();
            List<String> statusList = new ArrayList<>();
            statusList.add(ApplicationStatusEnum.APPLICATION.getStatus());
            statusList.add(ApplicationStatusEnum.SUBMIT.getStatus());
            feeManageExample.createCriteria()
                    .andNextNodeEqualTo(customerUserId)
                    .andQualityIdIn(stringList)
                    .andStatusIn(statusList)
                    .andDeletedEqualTo(0);
            return rdmsFeeManageMapper.countByExample(feeManageExample);
        }else{
            return 0L;
        }
    }

    public Long getApplicationNumByJobitemId(String jobitemId){
        RdmsFeeManageExample feeManageExample = new RdmsFeeManageExample();
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.APPLICATION.getStatus());
        statusList.add(ApplicationStatusEnum.SUBMIT.getStatus());
        feeManageExample.createCriteria()
                .andJobitemIdEqualTo(jobitemId)
                .andStatusIn(statusList)
                .andDeletedEqualTo(0);
        return rdmsFeeManageMapper.countByExample(feeManageExample);
    }

    public void listByCustomerId(PageDto<RdmsFeeManageDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsFeeManageExample feeManageExample = new RdmsFeeManageExample();
        feeManageExample.createCriteria().andCustomerIdEqualTo(pageDto.getKeyWord()).andDeletedEqualTo(0);
        List<RdmsFeeManage> RdmsFeeManages = rdmsFeeManageMapper.selectByExample(feeManageExample);

        PageInfo<RdmsFeeManage> pageInfo = new PageInfo<>(RdmsFeeManages);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsFeeManageDto> rdmsFeeDtos = CopyUtil.copyList(RdmsFeeManages, RdmsFeeManageDto.class);
        pageDto.setList(rdmsFeeDtos);
    }

    public void listByProjectId(PageDto<RdmsFeeManageDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsFeeManageExample feeManageExample = new RdmsFeeManageExample();
        feeManageExample.createCriteria().andProjectIdEqualTo(pageDto.getKeyWord()).andDeletedEqualTo(0);
        List<RdmsFeeManage> RdmsFeeManages = rdmsFeeManageMapper.selectByExample(feeManageExample);

        PageInfo<RdmsFeeManage> pageInfo = new PageInfo<>(RdmsFeeManages);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsFeeManageDto> rdmsFeeDtos = CopyUtil.copyList(RdmsFeeManages, RdmsFeeManageDto.class);
        pageDto.setList(rdmsFeeDtos);
    }

    public void listBySubprojectId(PageDto<RdmsFeeManageDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsFeeManageExample feeManageExample = new RdmsFeeManageExample();
        feeManageExample.createCriteria().andSubprojectIdEqualTo(pageDto.getKeyWord()).andDeletedEqualTo(0);
        List<RdmsFeeManage> RdmsFeeManages = rdmsFeeManageMapper.selectByExample(feeManageExample);

        PageInfo<RdmsFeeManage> pageInfo = new PageInfo<>(RdmsFeeManages);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsFeeManageDto> rdmsFeeDtos = CopyUtil.copyList(RdmsFeeManages, RdmsFeeManageDto.class);
        pageDto.setList(rdmsFeeDtos);
    }

    public void listByCharacterId(PageDto<RdmsFeeManageDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsFeeManageExample feeManageExample = new RdmsFeeManageExample();
        feeManageExample.setOrderByClause("create_time desc");
        feeManageExample.createCriteria().andCharacterIdEqualTo(pageDto.getKeyWord()).andDeletedEqualTo(0);
        List<RdmsFeeManage> RdmsFeeManages = rdmsFeeManageMapper.selectByExample(feeManageExample);
        PageInfo<RdmsFeeManage> pageInfo = new PageInfo<>(RdmsFeeManages);
        pageDto.setTotal(pageInfo.getTotal());
        List<RdmsFeeManageDto> rdmsFeeDtos = CopyUtil.copyList(RdmsFeeManages, RdmsFeeManageDto.class);
        List<RdmsFeeManageDto> feeManageDtos = new ArrayList<>();
        if(!CollectionUtils.isEmpty(rdmsFeeDtos)){
            for(RdmsFeeManageDto feeManage: rdmsFeeDtos){
                RdmsFeeManageDto simpleRecordInfo = this.getSimpleRecordInfo(feeManage.getId());
                feeManageDtos.add(simpleRecordInfo);
            }
        }
        pageDto.setList(feeManageDtos);
    }

    /**
     */
    public List<RdmsFeeManageDto> getListByCustomerId(String customerId){
        RdmsFeeManageExample feeManageExample = new RdmsFeeManageExample();
        feeManageExample.createCriteria().andCustomerIdEqualTo(customerId).andDeletedEqualTo(0);
        List<RdmsFeeManage> RdmsFeeManages = rdmsFeeManageMapper.selectByExample(feeManageExample);
        return CopyUtil.copyList(RdmsFeeManages, RdmsFeeManageDto.class);
    }

    public List<RdmsFeeManageDto> getListByProjectId(String projectId){
        RdmsFeeManageExample feeManageExample = new RdmsFeeManageExample();
        feeManageExample.setOrderByClause("update_time desc");
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.REJECT.getStatus());
        statusList.add(ApplicationStatusEnum.SUPPLEMENT.getStatus());
        statusList.add(ApplicationStatusEnum.CANCEL.getStatus());

        List<String> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.TASK_PRODUCT.getType());
        typeList.add(JobItemTypeEnum.ASSIST.getType());
        feeManageExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andStatusNotIn(statusList)
                .andJobTypeIn(typeList)
                .andAuxStatusEqualTo(DocTypeEnum.PROJECT.getType())
                .andDeletedEqualTo(0);
        List<RdmsFeeManage> RdmsFeeManages = rdmsFeeManageMapper.selectByExample(feeManageExample);
        List<RdmsFeeManageDto> feeManageDtos = new ArrayList<>();
        if(!CollectionUtils.isEmpty(RdmsFeeManages)){
            for(RdmsFeeManage feeManage: RdmsFeeManages){
                RdmsFeeManageDto simpleRecordInfo = this.getSimpleRecordInfo(feeManage.getId());
                feeManageDtos.add(simpleRecordInfo);
            }
        }
        return feeManageDtos;
    }

    public List<RdmsFeeManageDto> getFeeListBySubprojectId(String subprojectId){
        RdmsFeeManageExample feeManageExample = new RdmsFeeManageExample();
        feeManageExample.setOrderByClause("update_time desc");
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.REJECT.getStatus());
        statusList.add(ApplicationStatusEnum.SUPPLEMENT.getStatus());
        statusList.add(ApplicationStatusEnum.CANCEL.getStatus());

        feeManageExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andStatusNotIn(statusList)
                .andDeletedEqualTo(0);
        List<RdmsFeeManage> rdmsFeeManages = rdmsFeeManageMapper.selectByExample(feeManageExample);
        List<RdmsFeeManageDto> feeManageDtos = new ArrayList<>();
        if(!CollectionUtils.isEmpty(rdmsFeeManages)){
            for(RdmsFeeManage fee: rdmsFeeManages){
                RdmsFeeManageDto simpleRecordInfo = this.getSimpleRecordInfo(fee.getId());
                feeManageDtos.add(simpleRecordInfo);
            }
        }
        return feeManageDtos;
    }


    public List<RdmsFeeManageDto> getListByCharacterId(String characterId){
        RdmsFeeManageExample feeManageExample = new RdmsFeeManageExample();
        feeManageExample.setOrderByClause("update_time desc");
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.REJECT.getStatus());
        statusList.add(ApplicationStatusEnum.SUPPLEMENT.getStatus());
        statusList.add(ApplicationStatusEnum.CANCEL.getStatus());
        feeManageExample.createCriteria()
                .andCharacterIdEqualTo(characterId)
                .andStatusNotIn(statusList)
                .andJobTypeNotEqualTo(JobItemTypeEnum.TASK_SUBP.getType())
                .andDeletedEqualTo(0);
        List<RdmsFeeManage> RdmsFeeManages = rdmsFeeManageMapper.selectByExample(feeManageExample);
        List<RdmsFeeManageDto> feeManageDtos = new ArrayList<>();
        if(!CollectionUtils.isEmpty(RdmsFeeManages)){
            for(RdmsFeeManage feeManage: RdmsFeeManages){
                RdmsFeeManageDto simpleRecordInfo = this.getSimpleRecordInfo(feeManage.getId());
                feeManageDtos.add(simpleRecordInfo);
            }
        }
        return feeManageDtos;
    }

    public List<RdmsFeeManageDto> getListBySubprojectId(String subprojectId){
        RdmsFeeManageExample feeManageExample = new RdmsFeeManageExample();
        feeManageExample.setOrderByClause("update_time desc");
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.REJECT.getStatus());
        statusList.add(ApplicationStatusEnum.SUPPLEMENT.getStatus());
        statusList.add(ApplicationStatusEnum.CANCEL.getStatus());

        List<String> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.TASK_SUBP.getType());
        typeList.add(JobItemTypeEnum.ASSIST.getType());
        typeList.add(JobItemTypeEnum.TASK_TEST.getType());
        feeManageExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andStatusNotIn(statusList)
                .andJobTypeIn(typeList)
                .andDeletedEqualTo(0);
        List<RdmsFeeManage> RdmsFeeManages = rdmsFeeManageMapper.selectByExample(feeManageExample);
        List<RdmsFeeManageDto> feeManageDtos = new ArrayList<>();
        if(!CollectionUtils.isEmpty(RdmsFeeManages)){
            for(RdmsFeeManage feeManage: RdmsFeeManages){
                RdmsFeeManageDto simpleRecordInfo = this.getSimpleRecordInfo(feeManage.getId());
                feeManageDtos.add(simpleRecordInfo);
            }
        }
        return feeManageDtos;
    }

    public List<RdmsFeeManageDto> getListByPreProjectId(String preprojectId){
        RdmsFeeManageExample feeManageExample = new RdmsFeeManageExample();
        feeManageExample.setOrderByClause("update_time desc");
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.REJECT.getStatus());
        statusList.add(ApplicationStatusEnum.SUPPLEMENT.getStatus());
        statusList.add(ApplicationStatusEnum.CANCEL.getStatus());

        List<String> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.TASK_FUNCTION.getType());
        typeList.add(JobItemTypeEnum.ASSIST.getType());
        feeManageExample.createCriteria()
                .andPreProjectIdEqualTo(preprojectId)
                .andStatusNotIn(statusList)
                .andJobTypeIn(typeList)
                .andDeletedEqualTo(0);
        List<RdmsFeeManage> RdmsFeeManages = rdmsFeeManageMapper.selectByExample(feeManageExample);
        List<RdmsFeeManageDto> feeManageDtos = new ArrayList<>();
        if(!CollectionUtils.isEmpty(RdmsFeeManages)){
            for(RdmsFeeManage feeManage: RdmsFeeManages){
                RdmsFeeManageDto simpleRecordInfo = this.getSimpleRecordInfo(feeManage.getId());
                feeManageDtos.add(simpleRecordInfo);
            }
        }
        return feeManageDtos;
    }

    public List<RdmsFeeManageDto> getListByQualityId(String qualityId){
        RdmsFeeManageExample feeManageExample = new RdmsFeeManageExample();
        feeManageExample.setOrderByClause("update_time desc");
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.REJECT.getStatus());
        statusList.add(ApplicationStatusEnum.SUPPLEMENT.getStatus());
        statusList.add(ApplicationStatusEnum.CANCEL.getStatus());
        feeManageExample.createCriteria()
                .andQualityIdEqualTo(qualityId)
                .andStatusNotIn(statusList)
                .andDeletedEqualTo(0);
        List<RdmsFeeManage> RdmsFeeManages = rdmsFeeManageMapper.selectByExample(feeManageExample);
        List<RdmsFeeManageDto> feeManageDtos = new ArrayList<>();
        if(!CollectionUtils.isEmpty(RdmsFeeManages)){
            for(RdmsFeeManage feeManage: RdmsFeeManages){
                RdmsFeeManageDto simpleRecordInfo = this.getSimpleRecordInfo(feeManage.getId());
                feeManageDtos.add(simpleRecordInfo);
            }
        }
        return feeManageDtos;
    }

    public List<RdmsFeeManageDto> getListByCharacterIdForStatistics(String characterId){
        RdmsFeeManageExample feeManageExample = new RdmsFeeManageExample();
        feeManageExample.setOrderByClause("update_time desc");
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.REJECT.getStatus());
        statusList.add(ApplicationStatusEnum.CANCEL.getStatus());
        statusList.add(ApplicationStatusEnum.APPLICATION.getStatus());
        feeManageExample.createCriteria()
                .andCharacterIdEqualTo(characterId)
                .andStatusNotIn(statusList)
                .andDeletedEqualTo(0);
        List<RdmsFeeManage> RdmsFeeManages = rdmsFeeManageMapper.selectByExample(feeManageExample);
        List<RdmsFeeManageDto> feeManageDtos = new ArrayList<>();
        if(!CollectionUtils.isEmpty(RdmsFeeManages)){
            for(RdmsFeeManage feeManage: RdmsFeeManages){
                RdmsFeeManageDto simpleRecordInfo = this.getSimpleRecordInfo(feeManage.getId());
                feeManageDtos.add(simpleRecordInfo);
            }
        }
        return feeManageDtos;
    }

    public List<RdmsFeeManageDto> getHaveApprovedListByCharacterId(String characterId){
        RdmsFeeManageExample feeManageExample = new RdmsFeeManageExample();
        feeManageExample.setOrderByClause("update_time desc");
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.REJECT.getStatus());
        statusList.add(ApplicationStatusEnum.CANCEL.getStatus());
        statusList.add(ApplicationStatusEnum.APPLICATION.getStatus());
        feeManageExample.createCriteria()
                .andCharacterIdEqualTo(characterId)
                .andStatusNotIn(statusList)
                .andDeletedEqualTo(0);
        List<RdmsFeeManage> RdmsFeeManages = rdmsFeeManageMapper.selectByExample(feeManageExample);
        List<RdmsFeeManageDto> feeManageDtos = new ArrayList<>();
        if(!CollectionUtils.isEmpty(RdmsFeeManages)){
            for(RdmsFeeManage feeManage: RdmsFeeManages){
                RdmsFeeManageDto simpleRecordInfo = this.getSimpleRecordInfo(feeManage.getId());
                feeManageDtos.add(simpleRecordInfo);
            }
        }
        return feeManageDtos;
    }

    public List<RdmsFeeManageDto> getFeeApplicationListByWriterId(String writerId){
        RdmsFeeManageExample feeManageExample = new RdmsFeeManageExample();
        feeManageExample.setOrderByClause("create_time desc");
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.SUBMIT.getStatus());
        statusList.add(ApplicationStatusEnum.COMPLETE.getStatus());
        statusList.add(ApplicationStatusEnum.CANCEL.getStatus());
        statusList.add(ApplicationStatusEnum.NOTSET.getStatus());
        feeManageExample.createCriteria()
                .andWriterIdEqualTo(writerId)
                .andStatusNotIn(statusList)
                .andDeletedEqualTo(0);
        List<RdmsFeeManage> RdmsFeeManages = rdmsFeeManageMapper.selectByExample(feeManageExample);
        List<RdmsFeeManageDto> feeManageDtos = new ArrayList<>();
        if(!CollectionUtils.isEmpty(RdmsFeeManages)){
            for(RdmsFeeManage feeManage: RdmsFeeManages){
                RdmsFeeManageDto simpleRecordInfo = this.getSimpleRecordInfo(feeManage.getId());
                feeManageDtos.add(simpleRecordInfo);
            }
        }
        return feeManageDtos;
    }

/*    public String updateFeeManageAuxStatus(RdmsFeeManage feeManage){
        if(ObjectUtils.isEmpty(feeManage.getAuxStatus()) && ObjectUtils.isEmpty(feeManage.getId())){
            LOG.error("输入错误" + this.getClass().getSimpleName() + "updateMaterialManageAuxStatus",feeManage);
            throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
        }
        AuxStatusEnum auxStatusByStatus = AuxStatusEnum.getAuxStatusByStatus(feeManage.getAuxStatus());
        if(ObjectUtils.isEmpty(auxStatusByStatus)){
            LOG.error("输入错误" + this.getClass().getSimpleName() + "updateMaterialManageAuxStatus",feeManage);
            throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
        }

        RdmsFeeManage RdmsFeeManage = new RdmsFeeManage();
        RdmsFeeManage.setId(feeManage.getId());
        RdmsFeeManage.setAuxStatus(feeManage.getAuxStatus());
        this.updateByPrimaryKeySelective(RdmsFeeManage);

        return RdmsFeeManage.getId();
    }*/

    public String updateFeeApproveFileListStr(RdmsFeeManage feeManage){
        RdmsFeeManage RdmsFeeManage = new RdmsFeeManage();
        RdmsFeeManage.setId(feeManage.getId());
        RdmsFeeManage.setApproveFileListStr(feeManage.getApproveFileListStr());
        this.updateByPrimaryKeySelective(RdmsFeeManage);

        return RdmsFeeManage.getId();
    }

     public String updateFeeDealWithFileListStr(RdmsFeeManage feeManage){
        RdmsFeeManage RdmsFeeManage = new RdmsFeeManage();
        RdmsFeeManage.setId(feeManage.getId());
        RdmsFeeManage.setDealWithFileListStr(feeManage.getDealWithFileListStr());
        this.updateByPrimaryKeySelective(RdmsFeeManage);

        return RdmsFeeManage.getId();
    }

    public String updateFeeCompleteFileListStr(RdmsFeeManage feeManage){
        RdmsFeeManage RdmsFeeManage = new RdmsFeeManage();
        RdmsFeeManage.setId(feeManage.getId());
        RdmsFeeManage.setCompleteFileListStr(feeManage.getCompleteFileListStr());
        this.updateByPrimaryKeySelective(RdmsFeeManage);

        return RdmsFeeManage.getId();
    }

    /**
     */
    public List<RdmsFeeManageDto> getListByJobitemId(String jobitemId){
        RdmsFeeManageExample feeManageExample = new RdmsFeeManageExample();
        List<String> applicationStatusEnums = new ArrayList<>();
        applicationStatusEnums.add(ApplicationStatusEnum.APPROVED.getStatus());
        applicationStatusEnums.add(ApplicationStatusEnum.SUBMIT.getStatus());
        applicationStatusEnums.add(ApplicationStatusEnum.PRE_COMPLETE.getStatus());
        applicationStatusEnums.add(ApplicationStatusEnum.COMPLETE.getStatus());
        feeManageExample.createCriteria()
                .andJobitemIdEqualTo(jobitemId)
                .andStatusIn(applicationStatusEnums)
                .andDeletedEqualTo(0);
        List<RdmsFeeManage> RdmsFeeManages = rdmsFeeManageMapper.selectByExample(feeManageExample);
        return CopyUtil.copyList(RdmsFeeManages, RdmsFeeManageDto.class);
    }

    public Integer getApplicationNum(String characterId){
        RdmsFeeManageExample feeManageExample = new RdmsFeeManageExample();
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.APPLICATION.getStatus());
        statusList.add(ApplicationStatusEnum.SUBMIT.getStatus());
        feeManageExample.createCriteria()
                .andCharacterIdEqualTo(characterId)
                .andStatusIn(statusList)
                .andJobTypeNotEqualTo(JobItemTypeEnum.TASK_SUBP.getType())
                .andDeletedEqualTo(0);
        return (Integer) (int)rdmsFeeManageMapper.countByExample(feeManageExample);
    }

    public Integer getQualityFeeApplicationNum(String qualityId){
        RdmsFeeManageExample feeManageExample = new RdmsFeeManageExample();
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.APPLICATION.getStatus());
        statusList.add(ApplicationStatusEnum.SUBMIT.getStatus());
        feeManageExample.createCriteria()
                .andQualityIdEqualTo(qualityId)
                .andStatusIn(statusList)
                .andJobTypeNotEqualTo(JobItemTypeEnum.TASK_SUBP.getType())
                .andDeletedEqualTo(0);
        return (Integer) (int)rdmsFeeManageMapper.countByExample(feeManageExample);
    }

    public Integer getApplicationNumBySubprojectId(String subprojectId){
        RdmsFeeManageExample feeManageExample = new RdmsFeeManageExample();
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.APPLICATION.getStatus());
        statusList.add(ApplicationStatusEnum.SUBMIT.getStatus());
        List<String> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.TASK_SUBP.getType());
        typeList.add(JobItemTypeEnum.ASSIST.getType());
        typeList.add(JobItemTypeEnum.TASK_TEST.getType());
        feeManageExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andStatusIn(statusList)
                .andJobTypeIn(typeList)
                .andDeletedEqualTo(0);
        return (Integer) (int)rdmsFeeManageMapper.countByExample(feeManageExample);
    }
    public Integer getApplicationNumByPreProjectId(String preprojectId){
        RdmsFeeManageExample feeManageExample = new RdmsFeeManageExample();
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.APPLICATION.getStatus());
        statusList.add(ApplicationStatusEnum.SUBMIT.getStatus());
        List<String> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.TASK_FUNCTION.getType());
        typeList.add(JobItemTypeEnum.ASSIST.getType());
        feeManageExample.createCriteria()
                .andPreProjectIdEqualTo(preprojectId)
                .andStatusIn(statusList)
                .andJobTypeIn(typeList)
                .andDeletedEqualTo(0);
        return (Integer) (int)rdmsFeeManageMapper.countByExample(feeManageExample);
    }
    public Integer getApplicationNumByProjectId(String projectId){
        RdmsFeeManageExample feeManageExample = new RdmsFeeManageExample();
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.APPLICATION.getStatus());
        statusList.add(ApplicationStatusEnum.SUBMIT.getStatus());
        List<String> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.TASK_PRODUCT.getType());
        typeList.add(JobItemTypeEnum.ASSIST.getType());
        feeManageExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andAuxStatusEqualTo(DocTypeEnum.PROJECT.getType())
                .andStatusIn(statusList)
                .andJobTypeIn(typeList)
                .andDeletedEqualTo(0);
        return (Integer) (int)rdmsFeeManageMapper.countByExample(feeManageExample);
    }
    public Integer getFeeApplicationNum(String userId){
        RdmsFeeManageExample feeManageExample = new RdmsFeeManageExample();
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.APPLICATION.getStatus());
        statusList.add(ApplicationStatusEnum.SUBMIT.getStatus());
        List<String> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.TASK_SUBP.getType());
        typeList.add(JobItemTypeEnum.ASSIST.getType());
        typeList.add(JobItemTypeEnum.TASK_TEST.getType());
        feeManageExample.createCriteria()
                .andNextNodeEqualTo(userId)
                .andStatusIn(statusList)
                .andJobTypeNotIn(typeList)
                .andDeletedEqualTo(0);
        return (Integer) (int)rdmsFeeManageMapper.countByExample(feeManageExample);
    }

    public Integer getFeeApplicationNum_project(String userId){
        RdmsFeeManageExample feeManageExample = new RdmsFeeManageExample();
        List<String> statusList = new ArrayList<>();
        statusList.add(ApplicationStatusEnum.APPLICATION.getStatus());
        statusList.add(ApplicationStatusEnum.SUBMIT.getStatus());
        List<String> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.TASK_SUBP.getType());
        feeManageExample.createCriteria()
                .andNextNodeEqualTo(userId)
                .andStatusIn(statusList)
                .andJobTypeIn(typeList)
                .andDeletedEqualTo(0);
        return (Integer) (int)rdmsFeeManageMapper.countByExample(feeManageExample);
    }
    /**
     */
    public RdmsFeeManageDto getFeeApplicationByCode(String applicationCode){
        RdmsFeeManageExample feeManageExample = new RdmsFeeManageExample();
        feeManageExample.createCriteria().andCodeEqualTo(applicationCode).andDeletedEqualTo(0);
        List<RdmsFeeManage> RdmsFeeManages = rdmsFeeManageMapper.selectByExample(feeManageExample);
        List<RdmsFeeManageDto> RdmsFeeManageDtos = CopyUtil.copyList(RdmsFeeManages, RdmsFeeManageDto.class);
        if(RdmsFeeManageDtos.size() != 1){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsFeeManageDto feeManageDto = RdmsFeeManageDtos.get(0);
        //添加审批附件文件信息
        List<String> appFileIdList = JSON.parseArray(feeManageDto.getApproveFileListStr(), String.class);
        if(! CollectionUtils.isEmpty(appFileIdList)){
            List<RdmsFileDto> fileDtoList = new ArrayList<>();
            for(String fileId: appFileIdList){
                RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(fileId);
                fileDtoList.add(fileDto);
            }
            feeManageDto.setApproveFileList(fileDtoList);
        }
        //添加物料处置附件文件信息
        List<String> dealFileIdList = JSON.parseArray(feeManageDto.getDealWithFileListStr(), String.class);
        if(! CollectionUtils.isEmpty(dealFileIdList)){
            List<RdmsFileDto> fileDtoList = new ArrayList<>();
            for(String fileId: dealFileIdList){
                RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(fileId);
                fileDtoList.add(fileDto);
            }
            feeManageDto.setDealWithFileList(fileDtoList);
        }
         //添加物料处置附件文件信息
        List<String> compFileIdList = JSON.parseArray(feeManageDto.getCompleteFileListStr(), String.class);
        if(! CollectionUtils.isEmpty(compFileIdList)){
            List<RdmsFileDto> fileDtoList = new ArrayList<>();
            for(String fileId: compFileIdList){
                RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(fileId);
                fileDtoList.add(fileDto);
            }
            feeManageDto.setCompleteFileList(fileDtoList);
        }

        return feeManageDto;
    }

    public RdmsFeeManageDto getFeeManageSimpleInfoByCode(String code){
        RdmsFeeManageExample feeManageExample = new RdmsFeeManageExample();
        feeManageExample.createCriteria().andCodeEqualTo(code).andDeletedEqualTo(0);
        List<RdmsFeeManage> RdmsFeeManages = rdmsFeeManageMapper.selectByExample(feeManageExample);
        List<RdmsFeeManageDto> RdmsFeeManageDtos = CopyUtil.copyList(RdmsFeeManages, RdmsFeeManageDto.class);
        if(RdmsFeeManageDtos.size() != 1){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        return RdmsFeeManageDtos.get(0);
    }

    public RdmsFeeManage selectByPrimaryKey(String id){
        return rdmsFeeManageMapper.selectByPrimaryKey(id);
    }

    public RdmsFeeManageDto getSimpleRecordInfo(String id){
        RdmsFeeManage RdmsFeeManage = rdmsFeeManageMapper.selectByPrimaryKey(id);
        RdmsFeeManageDto feeManageDto = CopyUtil.copy(RdmsFeeManage, RdmsFeeManageDto.class);
        if(!ObjectUtils.isEmpty(feeManageDto.getCharacterId())){
            RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(feeManageDto.getCharacterId());
            feeManageDto.setCharacterName(rdmsCharacter.getCharacterName());
        }
        RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(feeManageDto.getCustomerId());
        feeManageDto.setCustomerName(rdmsCustomer.getCustomerName());
        RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(feeManageDto.getWriterId());
        feeManageDto.setWriterName(rdmsCustomerUser.getTrueName());
        RdmsCustomerUser rdmsCustomerUser1 = rdmsCustomerUserService.selectByPrimaryKey(feeManageDto.getNextNode());
        feeManageDto.setNextNodeName(rdmsCustomerUser1.getTrueName());
        RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(RdmsFeeManage.getJobitemId());
        feeManageDto.setJobitemName(rdmsJobItem.getJobName());
        RdmsCustomerUser rdmsCustomerUser2 = rdmsCustomerUserService.selectByPrimaryKey(RdmsFeeManage.getApproverId());
        feeManageDto.setApproverName(rdmsCustomerUser2.getTrueName());
        feeManageDto.setAuxType(rdmsJobItem.getAuxType());
        return feeManageDto;
    }
    /**
     * 保存
     */
    public String save(RdmsFeeManage feeManage) {
        if(ObjectUtils.isEmpty(feeManage.getId())){
            return this.insert(feeManage);
        }else{
            RdmsFeeManage RdmsFeeManage = this.selectByPrimaryKey(feeManage.getId());
            if(ObjectUtils.isEmpty(RdmsFeeManage)){
                return this.insert(feeManage);
            }else{
                return this.update(feeManage);
            }
        }
    }

    /**
     * 新增
     */
    private String insert(RdmsFeeManage feeManage) {
        if(ObjectUtils.isEmpty(feeManage.getId())){
            feeManage.setId(UuidUtil.getShortUuid());
        }
        RdmsFeeManage RdmsFeeManage = rdmsFeeManageMapper.selectByPrimaryKey(feeManage.getId());
        if(! ObjectUtils.isEmpty(RdmsFeeManage)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            feeManage.setCreateTime(new Date());
            feeManage.setUpdateTime(new Date());
            feeManage.setDeleted(0);
            if(ObjectUtils.isEmpty(feeManage.getAuxStatus())){
                feeManage.setAuxStatus(ApplicationStatusEnum.NOTSET.getStatus());
            }
            rdmsFeeManageMapper.insert(feeManage);
            return feeManage.getId();
        }
    }

    /**
     * 更新
     */
    public String update(RdmsFeeManage feeManage) {
        if(ObjectUtils.isEmpty(feeManage.getId())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsFeeManage RdmsFeeManage = this.selectByPrimaryKey(feeManage.getId());
        if(ObjectUtils.isEmpty(RdmsFeeManage)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            feeManage.setUpdateTime(new Date());
            feeManage.setDeleted(0);
            rdmsFeeManageMapper.updateByPrimaryKey(feeManage);
            return feeManage.getId();
        }
    }

    public String updateByPrimaryKeySelective(RdmsFeeManage feeManage){
        feeManage.setUpdateTime(new Date());
        feeManage.setDeleted(0);
        rdmsFeeManageMapper.updateByPrimaryKeySelective(feeManage);
        return feeManage.getId();
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsFeeManage RdmsFeeManage = rdmsFeeManageMapper.selectByPrimaryKey(id);
        if(!ObjectUtils.isEmpty(RdmsFeeManage)){
            RdmsFeeManage.setDeleted(1);
            RdmsFeeManage.setUpdateTime(new Date());
            this.update(RdmsFeeManage);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

}
