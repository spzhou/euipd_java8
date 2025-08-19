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
import com.course.server.dto.rdms.RdmsCategoryFileDto;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsCategoryFileMapper;
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
import java.util.ArrayList;
import java.util.List;


@Service
public class RdmsCategoryFileService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsCategoryFileService.class);

    @Resource
    private RdmsCategoryFileMapper rdmsCategoryFileMapper;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private RdmsCategoryService rdmsCategoryService;

    @Transactional
    public void list(PageDto<RdmsCategoryFileDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());

        RdmsCategoryFileExample categoryFileExample = new RdmsCategoryFileExample();
        categoryFileExample.setOrderByClause("file_create_time desc");
        RdmsCategoryFileExample.Criteria criteria = categoryFileExample.createCriteria()
                .andProjectIdEqualTo(pageDto.getKeyWord());

        if(!(ObjectUtils.isEmpty(pageDto.getActor()) || pageDto.getActor().equals("00000000"))){
            criteria.andCategoryIdStrLike('%'+pageDto.getActor()+'%');
        }

        List<RdmsCategoryFile> rdmsCategoryFiles = rdmsCategoryFileMapper.selectByExample(categoryFileExample);
        PageInfo<RdmsCategoryFile> pageInfo = new PageInfo<>(rdmsCategoryFiles);
        pageDto.setTotal(pageInfo.getTotal());
        List<RdmsCategoryFileDto> rdmsCategoryFileDtos = CopyUtil.copyList(rdmsCategoryFiles, RdmsCategoryFileDto.class);
        if(!CollectionUtils.isEmpty(rdmsCategoryFileDtos)){
            for(RdmsCategoryFileDto categoryFileDto: rdmsCategoryFileDtos){
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(categoryFileDto.getWriter());
                categoryFileDto.setWriterName(rdmsCustomerUser.getTrueName());

                if(!ObjectUtils.isEmpty(categoryFileDto.getCategoryIdStr())){
                    List<String> categoryIdList = JSON.parseArray(categoryFileDto.getCategoryIdStr(), String.class);
                    if(!CollectionUtils.isEmpty(categoryIdList)){
                        String categoryNameListStr = "";
                        for(String id: categoryIdList){
                            RdmsCategory category = rdmsCategoryService.selectByPrimaryKey(id);
                            if(ObjectUtils.isEmpty(categoryNameListStr)){
                                categoryNameListStr = category.getName();
                            }else{
                                categoryNameListStr = categoryNameListStr + ", " + category.getName();
                            }
                        }
                        categoryFileDto.setCategoryNameListStr(categoryNameListStr);
                    }
                }
            }
        }

        pageDto.setList(rdmsCategoryFileDtos);
    }

    public RdmsCategoryFile selectByPrimaryKey(String id){
        return rdmsCategoryFileMapper.selectByPrimaryKey(id);
    }

    public RdmsCategoryFileDto selectByFileId(String fileId){
        RdmsCategoryFileExample categoryFileExample = new RdmsCategoryFileExample();
        categoryFileExample.createCriteria().andFileIdEqualTo(fileId);
        List<RdmsCategoryFile> rdmsCategoryFiles = rdmsCategoryFileMapper.selectByExample(categoryFileExample);
        if(!CollectionUtils.isEmpty(rdmsCategoryFiles)){
            List<RdmsCategoryFileDto> rdmsCategoryFileDtos = CopyUtil.copyList(rdmsCategoryFiles, RdmsCategoryFileDto.class);
            if(!CollectionUtils.isEmpty(rdmsCategoryFileDtos)){
                for(RdmsCategoryFileDto categoryFileDto: rdmsCategoryFileDtos){
                    RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(categoryFileDto.getWriter());
                    categoryFileDto.setWriterName(rdmsCustomerUser.getTrueName());

                    if(!ObjectUtils.isEmpty(categoryFileDto.getCategoryIdStr())){
                        List<String> categoryIdList = JSON.parseArray(categoryFileDto.getCategoryIdStr(), String.class);
                        categoryFileDto.setCategoryIdList(categoryIdList);
                    }
                }
            }
            return rdmsCategoryFileDtos.get(0);
        }else{
            return null;
        }
    }

    public List<RdmsCategoryFileDto> getAllCategoryFilesByProjectId(String projectId){
        RdmsCategoryFileExample categoryFileExample = new RdmsCategoryFileExample();
        RdmsCategoryFileExample.Criteria criteria = categoryFileExample.createCriteria().andProjectIdEqualTo(projectId);
        List<RdmsCategoryFile> rdmsCategoryFiles = rdmsCategoryFileMapper.selectByExample(categoryFileExample);
        List<RdmsCategoryFileDto> rdmsCategoryFileDtos = CopyUtil.copyList(rdmsCategoryFiles, RdmsCategoryFileDto.class);
        return rdmsCategoryFileDtos;
    }

    /**
     * 保存
     */
    public String save(RdmsCategoryFile categoryFile) {
        //避免保存相同fileId的文件
        RdmsCategoryFile rdmsCategoryFile1 = this.selectByFileId(categoryFile.getFileId());
        if(ObjectUtils.isEmpty(rdmsCategoryFile1)){
            if(ObjectUtils.isEmpty(categoryFile.getId())){
                return this.insert(categoryFile);
            }else{
                RdmsCategoryFile rdmsCategoryFile = this.selectByPrimaryKey(categoryFile.getId());
                if(ObjectUtils.isEmpty(rdmsCategoryFile)){
                    return this.insert(categoryFile);
                }else{
                    return this.update(categoryFile);
                }
            }
        }else{
            categoryFile.setId(rdmsCategoryFile1.getId());
            categoryFile.setProjectId(rdmsCategoryFile1.getProjectId());
            return this.update(categoryFile);
        }
    }

    /**
     * 新增
     */
    private String insert(RdmsCategoryFile categoryFile) {
        if(ObjectUtils.isEmpty(categoryFile.getId())){
            categoryFile.setId(UuidUtil.getShortUuid());
        }
        RdmsCategoryFile rdmsCategoryFile = rdmsCategoryFileMapper.selectByPrimaryKey(categoryFile.getId());
        if(! ObjectUtils.isEmpty(rdmsCategoryFile)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            rdmsCategoryFileMapper.insert(categoryFile);
            return categoryFile.getId();
        }
    }

    /**
     * 更新
     */
    public String update(RdmsCategoryFile categoryFile) {
        if(ObjectUtils.isEmpty(categoryFile.getId())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsCategoryFile rdmsCategoryFile = this.selectByPrimaryKey(categoryFile.getId());
        if(ObjectUtils.isEmpty(rdmsCategoryFile)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            rdmsCategoryFileMapper.updateByPrimaryKey(categoryFile);
            return categoryFile.getId();
        }
    }

    public String updateByPrimaryKeySelective(RdmsCategoryFile categoryFile){
        rdmsCategoryFileMapper.updateByPrimaryKeySelective(categoryFile);
        return categoryFile.getId();
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsCategoryFile categoryFile = rdmsCategoryFileMapper.selectByPrimaryKey(id);
        if(!ObjectUtils.isEmpty(categoryFile)){
            this.update(categoryFile);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

}
