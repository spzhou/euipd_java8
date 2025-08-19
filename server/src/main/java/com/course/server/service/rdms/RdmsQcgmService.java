/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.course.server.domain.RdmsQcgm;
import com.course.server.domain.RdmsQcgmExample;
import com.course.server.dto.PageDto;
import com.course.server.dto.rdms.RdmsQcgmDto;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsQcgmMapper;
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

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class RdmsQcgmService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsQcgmService.class);

    @Resource
    private RdmsQcgmMapper rdmsQcgmMapper;

    public void list(PageDto<RdmsQcgmDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsQcgmExample rdmsQcgmExample = new RdmsQcgmExample();
        rdmsQcgmExample.createCriteria().andCustomerIdEqualTo(pageDto.getKeyWord()).andDeletedEqualTo(0);
        List<RdmsQcgm> rdmsQcgms = rdmsQcgmMapper.selectByExample(rdmsQcgmExample);

        PageInfo<RdmsQcgm> pageInfo = new PageInfo<>(rdmsQcgms);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsQcgmDto> rdmsQcgmDtos = CopyUtil.copyList(rdmsQcgms, RdmsQcgmDto.class);
        pageDto.setList(rdmsQcgmDtos);
    }

    @Transactional
    public RdmsQcgmDto getQcgmByCustomerId(String customerId){
        RdmsQcgmExample rdmsQcgmExample = new RdmsQcgmExample();
        rdmsQcgmExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andDeletedEqualTo(0);
        List<RdmsQcgm> rdmsQcgms = rdmsQcgmMapper.selectByExample(rdmsQcgmExample);
        if(CollectionUtils.isEmpty(rdmsQcgms)){
            return null;
        }
        if(rdmsQcgms.size() == 1){
            List<RdmsQcgmDto> rdmsQcgmDtos = CopyUtil.copyList(rdmsQcgms, RdmsQcgmDto.class);
            return rdmsQcgmDtos.get(0);
        }
        throw new BusinessException(BusinessExceptionCode.DB_RECORD_ERROR);
    }

    public RdmsQcgm selectByPrimaryKey(String id) {
        RdmsQcgm rdmsQcgm = rdmsQcgmMapper.selectByPrimaryKey(id);
        return rdmsQcgm;
    }
    /**
     * 保存
     */
    public String save(RdmsQcgm rdmsQcgm) {
        if(rdmsQcgm.getCreateTime()==null){
            rdmsQcgm.setCreateTime(new Date());
        }
        rdmsQcgm.setDeleted(0);

        RdmsQcgmExample qcgmExample = new RdmsQcgmExample();
        qcgmExample.createCriteria().andCustomerIdEqualTo(rdmsQcgm.getCustomerId());
        List<RdmsQcgm> rdmsQcgms = rdmsQcgmMapper.selectByExample(qcgmExample);
        if(!rdmsQcgms.isEmpty()){
            rdmsQcgm.setId(rdmsQcgms.get(0).getId());
            return this.update(rdmsQcgm);
        }else{
            return this.insert(rdmsQcgm);
        }
    }

    /**
     * 新增
     */
    private String insert(RdmsQcgm rdmsQcgm) {
        if(org.springframework.util.ObjectUtils.isEmpty(rdmsQcgm.getId())){  //当前端页面给出projectID时,将不为空
            rdmsQcgm.setId(UuidUtil.getShortUuid());
        }
        RdmsQcgmExample qcgmExample = new RdmsQcgmExample();
        qcgmExample.createCriteria().andIdEqualTo(rdmsQcgm.getId());
        List<RdmsQcgm> rdmsQcgms = rdmsQcgmMapper.selectByExample(qcgmExample);
        if(!rdmsQcgms.isEmpty()){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            rdmsQcgmMapper.insert(rdmsQcgm);
            return rdmsQcgm.getId();
        }
    }

    /**
     * 更新
     */
    private String update(RdmsQcgm rdmsQcgm) {
        RdmsQcgmExample qcgmExample = new RdmsQcgmExample();
        qcgmExample.createCriteria().andCustomerIdEqualTo(rdmsQcgm.getCustomerId());
        List<RdmsQcgm> rdmsQcgms = rdmsQcgmMapper.selectByExample(qcgmExample);
        if(!rdmsQcgms.isEmpty()){
            rdmsQcgm.setId(rdmsQcgms.get(0).getId());
            rdmsQcgmMapper.updateByPrimaryKey(rdmsQcgm);
            return rdmsQcgm.getId();
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsQcgm rdmsQcgms = rdmsQcgmMapper.selectByPrimaryKey(id);
        if(!ObjectUtils.isEmpty(rdmsQcgms)){
            rdmsQcgms.setDeleted(1);
            this.update(rdmsQcgms);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

}
