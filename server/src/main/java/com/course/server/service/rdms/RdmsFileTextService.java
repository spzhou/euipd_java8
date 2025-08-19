/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.course.server.domain.*;
import com.course.server.dto.PageDto;
import com.course.server.dto.rdms.RdmsCustomerDto;
import com.course.server.dto.rdms.RdmsFileTextDto;
import com.course.server.enums.rdms.CustomerStatusEnum;
import com.course.server.enums.rdms.DemandStatusEnum;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsFileTextMapper;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.course.server.enums.rdms.CustomerStatusEnum.getCustomerEnumByStatus;

@Service
public class RdmsFileTextService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsFileTextService.class);

    @Resource
    private RdmsFileTextMapper rdmsFileTextMapper;


    public void listFileTextByCustomerId(PageDto<RdmsFileTextDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsFileTextExample fileTextExample = new RdmsFileTextExample();
        List<RdmsFileText> rdmsFileTexts = rdmsFileTextMapper.selectByExample(fileTextExample);

        PageInfo<RdmsFileText> pageInfo = new PageInfo<>(rdmsFileTexts);
        pageDto.setTotal(pageInfo.getTotal());
        List<RdmsFileTextDto> rdmsFileTextDtos = CopyUtil.copyList(rdmsFileTexts, RdmsFileTextDto.class);

        pageDto.setList(rdmsFileTextDtos);
    }

    public RdmsFileText selectByPrimaryKey(String id){
        return rdmsFileTextMapper.selectByPrimaryKey(id);
    }

    /**
     * 保存
     */
    public String save(RdmsFileText text) {
        text.setDeleted(0);

        if(ObjectUtils.isEmpty(text.getId())){
            return this.insert(text);
        }else{
            RdmsFileTextExample rdmsFileTextExample = new RdmsFileTextExample();
            rdmsFileTextExample.createCriteria().andIdEqualTo(text.getId());
            List<RdmsFileText> rdmsDemandDiscerns = rdmsFileTextMapper.selectByExample(rdmsFileTextExample);
            if(!CollectionUtils.isEmpty(rdmsDemandDiscerns)){
                return this.update(text);
            }else{
                return this.insert(text);
            }
        }

    }

    private String insert(RdmsFileText text) {
        if(org.springframework.util.ObjectUtils.isEmpty(text.getId())){  //当前端页面给出projectID时,将不为空
            text.setId(UuidUtil.getUuid());
        }
        RdmsFileTextExample rdmsFileTextExample = new RdmsFileTextExample();
        rdmsFileTextExample.createCriteria().andIdEqualTo(text.getId());
        List<RdmsFileText> rdmsDemandDiscerns = rdmsFileTextMapper.selectByExample(rdmsFileTextExample);
        if(!CollectionUtils.isEmpty(rdmsDemandDiscerns)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            rdmsFileTextMapper.insert(text);
            return text.getId();
        }
    }

    public String update(RdmsFileText text) {
        RdmsFileTextExample rdmsFileTextExample = new RdmsFileTextExample();
        rdmsFileTextExample.createCriteria().andIdEqualTo(text.getId());
        List<RdmsFileText> rdmsDemandDiscerns = rdmsFileTextMapper.selectByExample(rdmsFileTextExample);
        if(!CollectionUtils.isEmpty(rdmsDemandDiscerns)){
            text.setDeleted(0);
            rdmsFileTextMapper.updateByPrimaryKey(text);
            return text.getId();
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsFileTextExample rdmsFileTextExample = new RdmsFileTextExample();
        rdmsFileTextExample.createCriteria().andIdEqualTo(id);
        List<RdmsFileText> rdmsDemandDiscerns = rdmsFileTextMapper.selectByExample(rdmsFileTextExample);
        if(!CollectionUtils.isEmpty(rdmsDemandDiscerns)){
            RdmsFileText rdmsDemandDiscern = rdmsDemandDiscerns.get(0);
            rdmsDemandDiscern.setDeleted(1);
            rdmsFileTextMapper.updateByPrimaryKey(rdmsDemandDiscern);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

}
