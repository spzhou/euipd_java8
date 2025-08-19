/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.course.server.domain.RdmsFggm;
import com.course.server.domain.RdmsFggm;
import com.course.server.domain.RdmsFggmExample;
import com.course.server.dto.PageDto;
import com.course.server.dto.rdms.RdmsFggmDto;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsFggmMapper;
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
public class RdmsFggmService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsFggmService.class);

    @Resource
    private RdmsFggmMapper rdmsFggmMapper;

    public void list(PageDto<RdmsFggmDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsFggmExample rdmsFggmExample = new RdmsFggmExample();
        rdmsFggmExample.createCriteria().andCustomerIdEqualTo(pageDto.getKeyWord()).andDeletedEqualTo(0);
        List<RdmsFggm> rdmsFggms = rdmsFggmMapper.selectByExample(rdmsFggmExample);

        PageInfo<RdmsFggm> pageInfo = new PageInfo<>(rdmsFggms);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsFggmDto> rdmsFggmDtos = CopyUtil.copyList(rdmsFggms, RdmsFggmDto.class);
        pageDto.setList(rdmsFggmDtos);
    }

    /**
     */
    @Transactional
    public RdmsFggmDto getFggmByCustomerId(String customerId){
        RdmsFggmExample rdmsFggmExample = new RdmsFggmExample();
        rdmsFggmExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andDeletedEqualTo(0);
        List<RdmsFggm> rdmsFggms = rdmsFggmMapper.selectByExample(rdmsFggmExample);
        if(CollectionUtils.isEmpty(rdmsFggms)){
            return null;
        }
        if(rdmsFggms.size() == 1){
            List<RdmsFggmDto> rdmsFggmsDtos = CopyUtil.copyList(rdmsFggms, RdmsFggmDto.class);
            return rdmsFggmsDtos.get(0);
        }
        throw new BusinessException(BusinessExceptionCode.DB_RECORD_ERROR);
    }

    public RdmsFggm selectByPrimaryKey(String id) {
        RdmsFggm rdmsFggm = rdmsFggmMapper.selectByPrimaryKey(id);
        return rdmsFggm;
    }
    /**
     * 保存
     */
    public String save(RdmsFggm rdmsFggm) {
        if(rdmsFggm.getCreateTime()==null){
            rdmsFggm.setCreateTime(new Date());
        }
        rdmsFggm.setDeleted(0);

        RdmsFggmExample fggmExample = new RdmsFggmExample();
        fggmExample.createCriteria().andCustomerIdEqualTo(rdmsFggm.getCustomerId());
        List<RdmsFggm> rdmsFggms = rdmsFggmMapper.selectByExample(fggmExample);
        if(!rdmsFggms.isEmpty()){
            rdmsFggm.setId(rdmsFggms.get(0).getId());
            return this.update(rdmsFggm);
        }else{
            return this.insert(rdmsFggm);
        }
    }

    /**
     * 新增
     */
    private String insert(RdmsFggm rdmsFggm) {
        if(org.springframework.util.ObjectUtils.isEmpty(rdmsFggm.getId())){  //当前端页面给出projectID时,将不为空
            rdmsFggm.setId(UuidUtil.getShortUuid());
        }
        RdmsFggmExample fggmExample = new RdmsFggmExample();
        fggmExample.createCriteria().andIdEqualTo(rdmsFggm.getId());
        List<RdmsFggm> rdmsFggms = rdmsFggmMapper.selectByExample(fggmExample);
        if(!rdmsFggms.isEmpty()){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            rdmsFggmMapper.insert(rdmsFggm);
            return rdmsFggm.getId();
        }
    }

    /**
     * 更新
     */
    private String update(RdmsFggm rdmsFggm) {
        RdmsFggmExample fggmExample = new RdmsFggmExample();
        fggmExample.createCriteria().andCustomerIdEqualTo(rdmsFggm.getCustomerId());
        List<RdmsFggm> rdmsFggms = rdmsFggmMapper.selectByExample(fggmExample);
        if(!rdmsFggms.isEmpty()){
            rdmsFggm.setId(rdmsFggms.get(0).getId());
            rdmsFggmMapper.updateByPrimaryKey(rdmsFggm);
            return rdmsFggm.getId();
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsFggm rdmsFggms = rdmsFggmMapper.selectByPrimaryKey(id);
        if(!ObjectUtils.isEmpty(rdmsFggms)){
            rdmsFggms.setDeleted(1);
            this.update(rdmsFggms);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

}
