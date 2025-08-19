/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.course.server.domain.RdmsPdgm;
import com.course.server.domain.RdmsPdgmExample;
import com.course.server.dto.PageDto;
import com.course.server.dto.rdms.RdmsPdgmDto;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsPdgmMapper;
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
public class RdmsPdgmService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsPdgmService.class);

    @Resource
    private RdmsPdgmMapper rdmsPdgmMapper;

    public void list(PageDto<RdmsPdgmDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsPdgmExample rdmsPdgmExample = new RdmsPdgmExample();
        rdmsPdgmExample.createCriteria().andCustomerIdEqualTo(pageDto.getKeyWord()).andDeletedEqualTo(0);
        List<RdmsPdgm> rdmsPdgms = rdmsPdgmMapper.selectByExample(rdmsPdgmExample);

        PageInfo<RdmsPdgm> pageInfo = new PageInfo<>(rdmsPdgms);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsPdgmDto> rdmsPdgmDtos = CopyUtil.copyList(rdmsPdgms, RdmsPdgmDto.class);
        pageDto.setList(rdmsPdgmDtos);
    }

    /**
     */
    @Transactional
    public RdmsPdgmDto getPdgmByCustomerId(String customerId){
        RdmsPdgmExample rdmsPdgmExample = new RdmsPdgmExample();
        rdmsPdgmExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andDeletedEqualTo(0);
        List<RdmsPdgm> rdmsPdgms = rdmsPdgmMapper.selectByExample(rdmsPdgmExample);
        if(CollectionUtils.isEmpty(rdmsPdgms)){
            return null;
        }
        if(rdmsPdgms.size() == 1){
            List<RdmsPdgmDto> rdmsPdgmDtos = CopyUtil.copyList(rdmsPdgms, RdmsPdgmDto.class);
            return rdmsPdgmDtos.get(0);
        }
        throw new BusinessException(BusinessExceptionCode.DB_RECORD_ERROR);
    }

    public RdmsPdgm selectByPrimaryKey(String id) {
        RdmsPdgm rdmsPdgm = rdmsPdgmMapper.selectByPrimaryKey(id);
        return rdmsPdgm;
    }
    /**
     * 保存
     */
    public String save(RdmsPdgm rdmsPdgm) {
        if(rdmsPdgm.getCreateTime()==null){
            rdmsPdgm.setCreateTime(new Date());
        }
        rdmsPdgm.setDeleted(0);

        RdmsPdgmExample pdgmExample = new RdmsPdgmExample();
        pdgmExample.createCriteria().andCustomerIdEqualTo(rdmsPdgm.getCustomerId());
        List<RdmsPdgm> rdmsPdgms = rdmsPdgmMapper.selectByExample(pdgmExample);
        if(!rdmsPdgms.isEmpty()){
            rdmsPdgm.setId(rdmsPdgms.get(0).getId());
            return this.update(rdmsPdgm);
        }else{
            return this.insert(rdmsPdgm);
        }
    }

    /**
     * 新增
     */
    private String insert(RdmsPdgm rdmsPdgm) {
        if(org.springframework.util.ObjectUtils.isEmpty(rdmsPdgm.getId())){  //当前端页面给出projectID时,将不为空
            rdmsPdgm.setId(UuidUtil.getShortUuid());
        }
        RdmsPdgmExample pdgmExample = new RdmsPdgmExample();
        pdgmExample.createCriteria().andIdEqualTo(rdmsPdgm.getId());
        List<RdmsPdgm> rdmsPdgms = rdmsPdgmMapper.selectByExample(pdgmExample);
        if(!rdmsPdgms.isEmpty()){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            rdmsPdgmMapper.insert(rdmsPdgm);
            return rdmsPdgm.getId();
        }
    }

    /**
     * 更新
     */
    private String update(RdmsPdgm rdmsPdgm) {
        RdmsPdgmExample pdgmExample = new RdmsPdgmExample();
        pdgmExample.createCriteria().andCustomerIdEqualTo(rdmsPdgm.getCustomerId());
        List<RdmsPdgm> rdmsPdgms = rdmsPdgmMapper.selectByExample(pdgmExample);
        if(!rdmsPdgms.isEmpty()){
            rdmsPdgm.setId(rdmsPdgms.get(0).getId());
            rdmsPdgmMapper.updateByPrimaryKey(rdmsPdgm);
            return rdmsPdgm.getId();
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsPdgm rdmsPdgms = rdmsPdgmMapper.selectByPrimaryKey(id);
        if(!ObjectUtils.isEmpty(rdmsPdgms)){
            rdmsPdgms.setDeleted(1);
            this.update(rdmsPdgms);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

}
