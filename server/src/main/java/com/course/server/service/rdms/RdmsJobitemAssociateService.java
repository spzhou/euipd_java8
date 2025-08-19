/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.*;
import com.course.server.dto.rdms.RdmsJobItemDto;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsJobitemAssociateMapper;
import com.course.server.util.UuidUtil;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RdmsJobitemAssociateService {
    private static final Logger LOG = LoggerFactory.getLogger(RdmsJobitemAssociateService.class);
    @Resource
    private RdmsJobitemAssociateMapper rdmsJobitemAssociateMapper;
    @Resource
    private RdmsJobItemService rdmsJobItemService;
    @Resource
    private RdmsJobItemPropertyService rdmsJobItemPropertyService;

    public RdmsJobitemAssociate selectByPrimaryKey(String id){
        return rdmsJobitemAssociateMapper.selectByPrimaryKey(id);
    }

    public List<String> getJobitemIdListByAssociatedJobId(String associatedId){
        RdmsJobitemAssociateExample associateExample = new RdmsJobitemAssociateExample();
        associateExample.createCriteria()
                .andAssociateIdEqualTo(associatedId);
        List<RdmsJobitemAssociate> rdmsJobitemAssociates = rdmsJobitemAssociateMapper.selectByExample(associateExample);
        if(!CollectionUtils.isEmpty(rdmsJobitemAssociates)){
            return rdmsJobitemAssociates.stream().map(RdmsJobitemAssociate::getJobitemId).collect(Collectors.toList());
        }else{
            return null;
        }
    }

    /**
     * 获得关联任务的交付附件ID列表
     * @param jobitemId
     * @return
     */
    public List<String> getAssociateTaskPropertyFileIdList(String jobitemId){
        List<String> jobitemIdList = this.getJobitemIdListByAssociatedJobId(jobitemId);
        if(!CollectionUtils.isEmpty(jobitemIdList)){
            List<RdmsJobItemDto> jobitemListByIdList = rdmsJobItemService.getJobitemListByIdList(jobitemIdList);
            if(!CollectionUtils.isEmpty(jobitemListByIdList)){
                List<String> fileIdList = new ArrayList<>();
                for(RdmsJobItemDto jobItemDto: jobitemListByIdList){
                    if(!ObjectUtils.isEmpty(jobItemDto)){
                        RdmsJobItemProperty rdmsJobItemProperty = rdmsJobItemPropertyService.selectByPrimaryKey(jobItemDto.getPropertyId());
                        if (!(ObjectUtils.isEmpty(rdmsJobItemProperty) || ObjectUtils.isEmpty(rdmsJobItemProperty.getFileListStr()))) {
                            List<String> stringList = JSON.parseArray(rdmsJobItemProperty.getFileListStr(), String.class);
                            fileIdList.addAll(stringList);
                        }
                    }
                }
                HashSet<String> objects = new HashSet<>(fileIdList);
                List<String> list = new ArrayList<>(objects);
                return list;
            }
            return null;
        }
        return null;
    }


    public List<RdmsJobitemAssociate> getJobitemAssociateList(String associatedJobId){
        List<RdmsJobitemAssociate> associateListByAssociatedJobId = this.getAssociateListByAssociatedJobId(associatedJobId);
        if(!CollectionUtils.isEmpty(associateListByAssociatedJobId)){
            return associateListByAssociatedJobId;
        }
        return null;
    }

    public List<RdmsJobitemAssociate> getAssociateListByAssociatedJobId(String associatedId){
        RdmsJobitemAssociateExample associateExample = new RdmsJobitemAssociateExample();
        associateExample.createCriteria()
                .andAssociateIdEqualTo(associatedId);
        List<RdmsJobitemAssociate> rdmsJobitemAssociates = rdmsJobitemAssociateMapper.selectByExample(associateExample);
        return rdmsJobitemAssociates;
    }

    public Boolean getJobitemAssocitateFlag(String jobitemId){
        RdmsJobitemAssociateExample associateExample = new RdmsJobitemAssociateExample();
        associateExample.createCriteria()
                .andJobitemIdEqualTo(jobitemId);
        List<RdmsJobitemAssociate> rdmsJobitemAssociates = rdmsJobitemAssociateMapper.selectByExample(associateExample);
        if(!CollectionUtils.isEmpty(rdmsJobitemAssociates)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 保存
     */
    public String save(@NotNull RdmsJobitemAssociate associate) {
        if(ObjectUtils.isEmpty(associate.getId())){
            return this.insert(associate);
        }else{
            RdmsJobitemAssociate jobitemAssociate = this.selectByPrimaryKey(associate.getId());
            if(ObjectUtils.isEmpty(jobitemAssociate)){
                return this.insert(associate);
            }else{
                return this.update(associate);
            }
        }
    }


    private String insert(@NotNull RdmsJobitemAssociate associate) {
        if(ObjectUtils.isEmpty(associate.getId())){  //当前端页面给出projectID时,将不为空
            associate.setId(UuidUtil.getShortUuid());
        }
        RdmsJobitemAssociate jobitemAssociate = this.selectByPrimaryKey(associate.getId());
        if(ObjectUtils.isEmpty(jobitemAssociate)){
            rdmsJobitemAssociateMapper.insert(associate);
            return associate.getId();
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }
    }

    public String update(@NotNull RdmsJobitemAssociate associate) {
        if(ObjectUtils.isEmpty(associate.getId())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsJobitemAssociate jobitemAssociate = this.selectByPrimaryKey(associate.getId());
        if(ObjectUtils.isEmpty(jobitemAssociate)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            rdmsJobitemAssociateMapper.updateByPrimaryKey(associate);
            return associate.getId();
        }
    }

    public String updateByPrimaryKeySelective(@NotNull RdmsJobitemAssociate associate){
        rdmsJobitemAssociateMapper.updateByPrimaryKeySelective(associate);
        return associate.getId();
    }
    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsJobitemAssociate jobitemAssociate = this.selectByPrimaryKey(id);
        if(ObjectUtils.isEmpty(jobitemAssociate)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            rdmsJobitemAssociateMapper.updateByPrimaryKey(jobitemAssociate);
        }
    }

}
