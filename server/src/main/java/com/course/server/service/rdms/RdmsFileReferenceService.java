/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.course.server.domain.*;
import com.course.server.dto.PageDto;
import com.course.server.dto.rdms.*;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsFileReferenceMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class RdmsFileReferenceService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsFileReferenceService.class);

    @Resource
    private RdmsFileReferenceMapper rdmsFileReferenceMapper;

    public void list(PageDto<RdmsFileReferenceDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsFileReferenceExample fileReferenceExample = new RdmsFileReferenceExample();

        RdmsFileReferenceExample.Criteria criteria = fileReferenceExample.createCriteria();
        criteria.andDeletedEqualTo(0)
                .andProposerIdEqualTo(pageDto.getActor())
                .andCustomerIdEqualTo(pageDto.getCustomerId());

        List<RdmsFileReference> rdmsFileReferenceList = rdmsFileReferenceMapper.selectByExample(fileReferenceExample);
        PageInfo<RdmsFileReference> pageInfo = new PageInfo<>(rdmsFileReferenceList);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsFileReferenceDto> fileReferenceDtoList = CopyUtil.copyList(rdmsFileReferenceList, RdmsFileReferenceDto.class);
/*        if(!CollectionUtils.isEmpty(fileReferenceDtoList)){
            //添加其他属性
        }*/
        pageDto.setList(fileReferenceDtoList);
    }

    public RdmsFileReference selectByPrimaryKey(String id){
        return rdmsFileReferenceMapper.selectByPrimaryKey(id);
    }

    /**
     * 保存
     */
    public String save(RdmsFileReference fileReference) {
        if(ObjectUtils.isEmpty(fileReference.getId())){
            return this.insert(fileReference);
        }else{
            RdmsFileReference rdmsFileReference = this.selectByPrimaryKey(fileReference.getId());
            if(ObjectUtils.isEmpty(rdmsFileReference)){
                return this.insert(fileReference);
            }else{
                return this.update(fileReference);
            }
        }
    }

    /**
     * 新增
     */
    private String insert(RdmsFileReference fileReference) {
        if(ObjectUtils.isEmpty(fileReference.getId())){
            fileReference.setId(UuidUtil.getShortUuid());
        }
        RdmsFileReference rdmsFileReference = rdmsFileReferenceMapper.selectByPrimaryKey(fileReference.getId());
        if(! ObjectUtils.isEmpty(rdmsFileReference)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            fileReference.setCreateTime(new Date());
            fileReference.setUpdateTime(new Date());
            fileReference.setDeleted(0);
            rdmsFileReferenceMapper.insert(fileReference);
            return fileReference.getId();
        }
    }

    /**
     * 更新
     */
    public String update(RdmsFileReference fileReference) {
        if(ObjectUtils.isEmpty(fileReference.getId())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsFileReference rdmsFileReference = this.selectByPrimaryKey(fileReference.getId());
        if(ObjectUtils.isEmpty(rdmsFileReference)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            fileReference.setUpdateTime(new Date());
            fileReference.setDeleted(0);
            rdmsFileReferenceMapper.updateByPrimaryKey(fileReference);
            return fileReference.getId();
        }
    }

    public String updateByPrimaryKeySelective(RdmsFileReference fileReference){
        rdmsFileReferenceMapper.updateByPrimaryKeySelective(fileReference);
        return fileReference.getId();
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsFileReference fileReference = rdmsFileReferenceMapper.selectByPrimaryKey(id);
        if(!ObjectUtils.isEmpty(fileReference)){
            fileReference.setDeleted(1);
            this.update(fileReference);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

}
