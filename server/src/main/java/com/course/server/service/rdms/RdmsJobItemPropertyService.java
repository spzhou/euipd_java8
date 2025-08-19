/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.RdmsJobItemProcessExample;
import com.course.server.domain.RdmsJobItemProperty;
import com.course.server.domain.RdmsJobItemPropertyExample;
import com.course.server.dto.PageDto;
import com.course.server.dto.rdms.RdmsJobItemPropertyDto;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsJobItemPropertyMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
public class RdmsJobItemPropertyService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsJobItemPropertyService.class);

    @Resource
    private RdmsJobItemPropertyMapper rdmsJobItemPropertyMapper;


    public void list(PageDto<RdmsJobItemPropertyDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsJobItemPropertyExample propertyExample = new RdmsJobItemPropertyExample();
        List<RdmsJobItemProperty> rdmsJobItemProperties = rdmsJobItemPropertyMapper.selectByExample(propertyExample);
        List<RdmsJobItemPropertyDto> rdmsJobItemPropertyDtos = CopyUtil.copyList(rdmsJobItemProperties, RdmsJobItemPropertyDto.class);

        PageInfo<RdmsJobItemPropertyDto> pageInfo = new PageInfo<>(rdmsJobItemPropertyDtos);
        pageDto.setTotal(pageInfo.getTotal());

        pageDto.setList(rdmsJobItemPropertyDtos);
    }

    public RdmsJobItemProperty selectByPrimaryKey(String id){
        return rdmsJobItemPropertyMapper.selectByPrimaryKey(id);
    }


    /**
     * 根据特性编号,进行查询
     */
    public List<RdmsJobItemProperty> getJobItemPropertyByJobItemId(String jobItemId) {
        RdmsJobItemPropertyExample propertyExample = new RdmsJobItemPropertyExample();
        propertyExample.createCriteria().andJobItemIdEqualTo(jobItemId).andDeletedEqualTo(0);
        return rdmsJobItemPropertyMapper.selectByExample(propertyExample);
    }

    public List<RdmsJobItemProperty> getListByJobitemIdList(List<String> jobitemIdList) {
        RdmsJobItemPropertyExample propertyExample = new RdmsJobItemPropertyExample();
        propertyExample.createCriteria()
                .andJobItemIdIn(jobitemIdList);
        return rdmsJobItemPropertyMapper.selectByExample(propertyExample);
    }

    /**
     * 保存
     */
    public String save(RdmsJobItemProperty property) {
        if(ObjectUtils.isEmpty(property.getJobItemId())){
            return this.insert(property);
        }else{
            List<RdmsJobItemProperty> jobItemPropertyByJobItemId = this.getJobItemPropertyByJobItemId(property.getJobItemId());
            if(!CollectionUtils.isEmpty(jobItemPropertyByJobItemId)){
                property.setId(jobItemPropertyByJobItemId.get(0).getId());
                property.setCreateTime(jobItemPropertyByJobItemId.get(0).getCreateTime());
                this.strictFileList(property);
                return this.update(property);
            }else{
                this.strictFileList(property);
                return this.insert(property);
            }
        }
    }

    public void strictFileList(RdmsJobItemProperty property) {
        List<String> strings = JSON.parseArray(property.getFileListStr(), String.class);
        HashSet<String> objects = new HashSet<>(strings);
        List<String> list = new ArrayList<String>(objects);
        String jsonString = JSON.toJSONString(list);
        property.setFileListStr(jsonString);
    }

    private String insert(RdmsJobItemProperty property) {
        if(ObjectUtils.isEmpty(property.getId())){  //当前端页面给出projectID时,将不为空
            property.setId(UuidUtil.getShortUuid());
        }
        RdmsJobItemProperty rdmsJobItemProperty = this.selectByPrimaryKey(property.getId());
        if(ObjectUtils.isEmpty(rdmsJobItemProperty)){
            property.setDeleted(0);
            property.setCreateTime(new Date());
            rdmsJobItemPropertyMapper.insert(property);
            return property.getId();
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }
    }

    public String update(RdmsJobItemProperty property) {
        if(ObjectUtils.isEmpty(property.getId())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsJobItemProperty rdmsJobItemProperty = this.selectByPrimaryKey(property.getId());
        if(ObjectUtils.isEmpty(rdmsJobItemProperty)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            property.setDeleted(0);
            rdmsJobItemPropertyMapper.updateByPrimaryKey(property);
            return property.getId();
        }
    }

    public String updateByPrimaryKeySelective(RdmsJobItemProperty property){
        rdmsJobItemPropertyMapper.updateByPrimaryKeySelective(property);
        return property.getId();
    }
    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsJobItemProperty rdmsJobItemProperty = this.selectByPrimaryKey(id);
        if(ObjectUtils.isEmpty(rdmsJobItemProperty)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            rdmsJobItemProperty.setDeleted(1);
            rdmsJobItemPropertyMapper.updateByPrimaryKey(rdmsJobItemProperty);
        }
    }

}
