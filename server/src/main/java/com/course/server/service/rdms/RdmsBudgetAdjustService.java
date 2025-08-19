/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.course.server.domain.RdmsBudgetAdjust;
import com.course.server.domain.RdmsBudgetAdjustExample;
import com.course.server.domain.RdmsPreProject;
import com.course.server.domain.RdmsProject;
import com.course.server.dto.rdms.RdmsBudgetAdjustDto;
import com.course.server.enums.rdms.BudgetApplicantStatusEnum;
import com.course.server.enums.rdms.BudgetStatusEnum;
import com.course.server.enums.rdms.BudgetTypeEnum;
import com.course.server.enums.rdms.ProjectTypeEnum;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsBudgetAdjustMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RdmsBudgetAdjustService {
    private static final Logger LOG = LoggerFactory.getLogger(RdmsBudgetAdjustService.class);
    @Resource
    private RdmsBudgetAdjustMapper rdmsBudgetAdjustMapper;
    @Autowired
    private RdmsProjectService rdmsProjectService;
    @Autowired
    private RdmsPreProjectService rdmsPreProjectService;

    public RdmsBudgetAdjust selectByPrimaryKey(String id){
        return rdmsBudgetAdjustMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public Integer getBudgetAdjustListByProjectIdAndStatusList(String projectId, List<BudgetApplicantStatusEnum> statusEnumList) {
        if (!CollectionUtils.isEmpty(statusEnumList)) {
            for (BudgetApplicantStatusEnum statusEnum : statusEnumList) {
                if (!ObjectUtils.isEmpty(statusEnum.getStatus())) {
                    BudgetApplicantStatusEnum budgetApplicantStatusEnumByStatus = BudgetApplicantStatusEnum.getBudgetApplicantStatusEnumByStatus(statusEnum.getStatus());
                    if (ObjectUtils.isEmpty(budgetApplicantStatusEnumByStatus)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }
        RdmsBudgetAdjustExample budgetAdjustExample = new RdmsBudgetAdjustExample();
        budgetAdjustExample.setOrderByClause("create_time desc");
        RdmsBudgetAdjustExample.Criteria criteria = budgetAdjustExample.createCriteria().andDeletedEqualTo(0);
        criteria.andProjectIdEqualTo(projectId);

        List<String> statusList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(statusEnumList)) {
            for (BudgetApplicantStatusEnum statusEnum : statusEnumList) {
                statusList.add(statusEnum.getStatus());
            }
            criteria.andStatusIn(statusList);
        }
        return (Integer) (int) rdmsBudgetAdjustMapper.countByExample(budgetAdjustExample);
    }

    @Transactional
    public List<RdmsBudgetAdjust> getBudgetAdjustListByProjectId(String projectId) {
        RdmsBudgetAdjustExample budgetAdjustExample = new RdmsBudgetAdjustExample();
        budgetAdjustExample.setOrderByClause("create_time desc");
        RdmsBudgetAdjustExample.Criteria criteria = budgetAdjustExample.createCriteria().andDeletedEqualTo(0);
        criteria.andProjectIdEqualTo(projectId);
        List<RdmsBudgetAdjust> rdmsBudgetAdjusts = rdmsBudgetAdjustMapper.selectByExample(budgetAdjustExample);
        return rdmsBudgetAdjusts;
    }

    @Transactional
    public List<RdmsBudgetAdjustDto> getBudgetListByProjectIdAndStatusTypeList(String projectId, String budgetApplicantStatus, List<BudgetTypeEnum> budgetTypeList) {
        if (!org.springframework.util.ObjectUtils.isEmpty(budgetApplicantStatus)) {
            BudgetApplicantStatusEnum budgetApplicantStatusEnumByStatus = BudgetApplicantStatusEnum.getBudgetApplicantStatusEnumByStatus(budgetApplicantStatus);
            if (ObjectUtils.isEmpty(budgetApplicantStatusEnumByStatus)) {
                throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
            }
        }
        if (!CollectionUtils.isEmpty(budgetTypeList)) {
            for (BudgetTypeEnum typeEnum : budgetTypeList) {
                if (!org.springframework.util.ObjectUtils.isEmpty(typeEnum.getType())) {
                    BudgetTypeEnum budgetTypeEnumByType = BudgetTypeEnum.getBudgetTypeEnumByType(typeEnum.getType());
                    if (ObjectUtils.isEmpty(budgetTypeEnumByType)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }

        RdmsBudgetAdjustExample budgetAdjustExample = new RdmsBudgetAdjustExample();
        budgetAdjustExample.setOrderByClause("create_time desc");
        RdmsBudgetAdjustExample.Criteria criteria1 = budgetAdjustExample.createCriteria();
        criteria1.andProjectIdEqualTo(projectId)
                .andTypeEqualTo(BudgetTypeEnum.PROJECT.getType())
                .andDeletedEqualTo(0);
        if (!org.springframework.util.ObjectUtils.isEmpty(budgetApplicantStatus)) {
            criteria1.andStatusEqualTo(budgetApplicantStatus);
        }

        List<String> typeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(budgetTypeList)) {
            for (BudgetTypeEnum typeEnum : budgetTypeList) {
                typeList.add(typeEnum.getType());
            }
            if (!org.springframework.util.ObjectUtils.isEmpty(typeList)) {
                criteria1.andTypeIn(typeList);
            }
        }

        List<RdmsBudgetAdjust> rdmsBudgetAdjusts = rdmsBudgetAdjustMapper.selectByExample(budgetAdjustExample);
        List<RdmsBudgetAdjustDto> rdmsBudgetAdjustDtos = CopyUtil.copyList(rdmsBudgetAdjusts, RdmsBudgetAdjustDto.class);
        for (RdmsBudgetAdjustDto itemDto : rdmsBudgetAdjustDtos) {
            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(itemDto.getProjectId());
            itemDto.setProjectName(rdmsProject.getProjectName());
        }

        return rdmsBudgetAdjustDtos;
    }

    @Transactional
    public List<RdmsBudgetAdjust> getBudgetAdjustListByBudgetIdList(List<String> budgetIdList) {
        RdmsBudgetAdjustExample budgetAdjustExample = new RdmsBudgetAdjustExample();
        budgetAdjustExample.setOrderByClause("create_time desc");
        budgetAdjustExample.createCriteria()
                .andBudgetIdIn(budgetIdList)
                .andStatusEqualTo(BudgetApplicantStatusEnum.SUBMIT.getStatus())
                .andDeletedEqualTo(0);
        List<RdmsBudgetAdjust> rdmsBudgetAdjusts = rdmsBudgetAdjustMapper.selectByExample(budgetAdjustExample);
        return rdmsBudgetAdjusts;
    }

    @Transactional
    public List<RdmsBudgetAdjustDto> getBudgetListByPreProjectIdAndStatusTypeList(String preprojectId, String budgetApplicantStatus, List<BudgetTypeEnum> budgetTypeList) {
        if (!org.springframework.util.ObjectUtils.isEmpty(budgetApplicantStatus)) {
            BudgetApplicantStatusEnum budgetApplicantStatusEnumByStatus = BudgetApplicantStatusEnum.getBudgetApplicantStatusEnumByStatus(budgetApplicantStatus);
            if (ObjectUtils.isEmpty(budgetApplicantStatusEnumByStatus)) {
                throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
            }
        }
        if (!CollectionUtils.isEmpty(budgetTypeList)) {
            for (BudgetTypeEnum typeEnum : budgetTypeList) {
                if (!org.springframework.util.ObjectUtils.isEmpty(typeEnum.getType())) {
                    BudgetTypeEnum budgetTypeEnumByType = BudgetTypeEnum.getBudgetTypeEnumByType(typeEnum.getType());
                    if (ObjectUtils.isEmpty(budgetTypeEnumByType)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }

        RdmsBudgetAdjustExample budgetAdjustExample = new RdmsBudgetAdjustExample();
        budgetAdjustExample.setOrderByClause("create_time desc");
        RdmsBudgetAdjustExample.Criteria criteria1 = budgetAdjustExample.createCriteria();
        criteria1.andPreProjectIdEqualTo(preprojectId)
                .andTypeEqualTo(BudgetTypeEnum.PRE_PROJECT.getType())
                .andDeletedEqualTo(0);
        if (!org.springframework.util.ObjectUtils.isEmpty(budgetApplicantStatus)) {
            criteria1.andStatusEqualTo(budgetApplicantStatus);
        }

        List<String> typeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(budgetTypeList)) {
            for (BudgetTypeEnum typeEnum : budgetTypeList) {
                typeList.add(typeEnum.getType());
            }
            if (!org.springframework.util.ObjectUtils.isEmpty(typeList)) {
                criteria1.andTypeIn(typeList);
            }
        }

        List<RdmsBudgetAdjust> rdmsBudgetAdjusts = rdmsBudgetAdjustMapper.selectByExample(budgetAdjustExample);
        List<RdmsBudgetAdjustDto> rdmsBudgetAdjustDtos = CopyUtil.copyList(rdmsBudgetAdjusts, RdmsBudgetAdjustDto.class);
        for (RdmsBudgetAdjustDto itemDto : rdmsBudgetAdjustDtos) {
            RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(itemDto.getPreProjectId());
            itemDto.setProjectName(rdmsPreProject.getPreProjectName());
        }

        return rdmsBudgetAdjustDtos;
    }

    @Transactional
    public List<RdmsBudgetAdjustDto> getBudgetListByProductIdAndStatusTypeList(String productId, String budgetApplicantStatus, List<BudgetTypeEnum> budgetTypeList) {
        if (!org.springframework.util.ObjectUtils.isEmpty(budgetApplicantStatus)) {
            BudgetApplicantStatusEnum budgetApplicantStatusEnumByStatus = BudgetApplicantStatusEnum.getBudgetApplicantStatusEnumByStatus(budgetApplicantStatus);
            if (ObjectUtils.isEmpty(budgetApplicantStatusEnumByStatus)) {
                throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
            }
        }
        if (!CollectionUtils.isEmpty(budgetTypeList)) {
            for (BudgetTypeEnum typeEnum : budgetTypeList) {
                if (!org.springframework.util.ObjectUtils.isEmpty(typeEnum.getType())) {
                    BudgetTypeEnum budgetTypeEnumByType = BudgetTypeEnum.getBudgetTypeEnumByType(typeEnum.getType());
                    if (ObjectUtils.isEmpty(budgetTypeEnumByType)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }

        RdmsBudgetAdjustExample budgetAdjustExample = new RdmsBudgetAdjustExample();
        budgetAdjustExample.setOrderByClause("create_time desc");
        RdmsBudgetAdjustExample.Criteria criteria1 = budgetAdjustExample.createCriteria();
        criteria1.andProjectIdEqualTo(productId)
                .andTypeEqualTo(BudgetTypeEnum.PRODUCT.getType())
                .andDeletedEqualTo(0);
        if (!org.springframework.util.ObjectUtils.isEmpty(budgetApplicantStatus)) {
            criteria1.andStatusEqualTo(budgetApplicantStatus);
        }

        List<String> typeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(budgetTypeList)) {
            for (BudgetTypeEnum typeEnum : budgetTypeList) {
                typeList.add(typeEnum.getType());
            }
            if (!org.springframework.util.ObjectUtils.isEmpty(typeList)) {
                criteria1.andTypeIn(typeList);
            }
        }

        List<RdmsBudgetAdjust> rdmsBudgetAdjusts = rdmsBudgetAdjustMapper.selectByExample(budgetAdjustExample);
        List<RdmsBudgetAdjustDto> rdmsBudgetAdjustDtos = CopyUtil.copyList(rdmsBudgetAdjusts, RdmsBudgetAdjustDto.class);
        if(!CollectionUtils.isEmpty(rdmsBudgetAdjustDtos)){
            for (RdmsBudgetAdjustDto itemDto : rdmsBudgetAdjustDtos) {
                RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(itemDto.getProjectId());
                itemDto.setProjectName(rdmsProject.getProjectName());
            }
        }

        return rdmsBudgetAdjustDtos;
    }

    @Transactional
    public Boolean hasSubmitBudgetAdjust(String budgetId) {
        RdmsBudgetAdjustExample budgetAdjustExample = new RdmsBudgetAdjustExample();
        budgetAdjustExample.setOrderByClause("create_time desc");
        budgetAdjustExample.createCriteria()
                .andBudgetIdEqualTo(budgetId)
                .andStatusEqualTo(BudgetApplicantStatusEnum.SUBMIT.getStatus())
                .andDeletedEqualTo(0);
        List<RdmsBudgetAdjust> rdmsBudgetAdjusts = rdmsBudgetAdjustMapper.selectByExample(budgetAdjustExample);
        if(!CollectionUtils.isEmpty(rdmsBudgetAdjusts)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 保存
     */
    public String save(RdmsBudgetAdjust budgetAdjust) {
        if(ObjectUtils.isEmpty(budgetAdjust.getId())){
            return this.insert(budgetAdjust);
        }else{
            RdmsBudgetAdjust rdmsBudgetAdjust = this.selectByPrimaryKey(budgetAdjust.getId());
            if(ObjectUtils.isEmpty(rdmsBudgetAdjust)){
                return this.insert(budgetAdjust);
            }else{
                return this.update(budgetAdjust);
            }
        }
    }

    private String insert(RdmsBudgetAdjust budgetAdjust) {
        if(ObjectUtils.isEmpty(budgetAdjust.getId())){  //当前端页面给出projectID时,将不为空
            budgetAdjust.setId(UuidUtil.getShortUuid());
        }
        RdmsBudgetAdjust rdmsBudgetAdjust = this.selectByPrimaryKey(budgetAdjust.getId());
        if(ObjectUtils.isEmpty(rdmsBudgetAdjust)){
            budgetAdjust.setUpdateTime(new Date());
            budgetAdjust.setCreateTime(new Date());
            budgetAdjust.setDeleted(0);
            rdmsBudgetAdjustMapper.insert(budgetAdjust);
            return budgetAdjust.getId();
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }
    }

    public String update(RdmsBudgetAdjust budgetAdjust) {
        if(ObjectUtils.isEmpty(budgetAdjust.getId())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsBudgetAdjust rdmsBudgetAdjust = this.selectByPrimaryKey(budgetAdjust.getId());
        if(ObjectUtils.isEmpty(rdmsBudgetAdjust)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            budgetAdjust.setDeleted(0);
            budgetAdjust.setUpdateTime(new Date());
            rdmsBudgetAdjustMapper.updateByPrimaryKey(budgetAdjust);
            return budgetAdjust.getId();
        }
    }

    public String updateByPrimaryKeySelective(RdmsBudgetAdjust budgetAdjust){
        budgetAdjust.setUpdateTime(new Date());
        rdmsBudgetAdjustMapper.updateByPrimaryKeySelective(budgetAdjust);
        return budgetAdjust.getId();
    }
    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsBudgetAdjust rdmsBudgetAdjust = this.selectByPrimaryKey(id);
        if(ObjectUtils.isEmpty(rdmsBudgetAdjust)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            rdmsBudgetAdjust.setDeleted(1);
            rdmsBudgetAdjustMapper.updateByPrimaryKey(rdmsBudgetAdjust);
        }
    }

}
