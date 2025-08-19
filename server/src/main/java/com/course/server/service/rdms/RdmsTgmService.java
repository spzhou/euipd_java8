/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.course.server.domain.RdmsTgm;
import com.course.server.domain.RdmsTgm;
import com.course.server.domain.RdmsTgmExample;
import com.course.server.dto.PageDto;
import com.course.server.dto.rdms.RdmsTgmDto;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsPdgmMapper;
import com.course.server.mapper.RdmsTgmMapper;
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
public class RdmsTgmService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsTgmService.class);

    @Resource
    private RdmsTgmMapper rdmsTgmMapper;

    public void list(PageDto<RdmsTgmDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsTgmExample rdmsTgmExample = new RdmsTgmExample();
        rdmsTgmExample.createCriteria().andCustomerIdEqualTo(pageDto.getKeyWord()).andDeletedEqualTo(0);
        List<RdmsTgm> rdmsTgms = rdmsTgmMapper.selectByExample(rdmsTgmExample);

        PageInfo<RdmsTgm> pageInfo = new PageInfo<>(rdmsTgms);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsTgmDto> rdmsTgmDtos = CopyUtil.copyList(rdmsTgms, RdmsTgmDto.class);
        pageDto.setList(rdmsTgmDtos);
    }

    /**
     */
    @Transactional
    public RdmsTgmDto getTgmByCustomerId(String customerId){
        RdmsTgmExample rdmsTgmExample = new RdmsTgmExample();
        rdmsTgmExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andDeletedEqualTo(0);
        List<RdmsTgm> rdmsTgms = rdmsTgmMapper.selectByExample(rdmsTgmExample);
        if(CollectionUtils.isEmpty(rdmsTgms)){
            return null;
        }
        if(rdmsTgms.size() == 1){
            List<RdmsTgmDto> rdmsTgmDtos = CopyUtil.copyList(rdmsTgms, RdmsTgmDto.class);
            return rdmsTgmDtos.get(0);
        }
        throw new BusinessException(BusinessExceptionCode.DB_RECORD_ERROR);
    }

    public RdmsTgm selectByPrimaryKey(String id) {
        return rdmsTgmMapper.selectByPrimaryKey(id);
    }
    /**
     * 保存
     */
    public String save(RdmsTgm rdmsTgm) {
        if(rdmsTgm.getCreateTime()==null){
            rdmsTgm.setCreateTime(new Date());
        }
        rdmsTgm.setDeleted(0);

        RdmsTgmExample tgmExample = new RdmsTgmExample();
        tgmExample.createCriteria().andCustomerIdEqualTo(rdmsTgm.getCustomerId());
        List<RdmsTgm> rdmsTgms = rdmsTgmMapper.selectByExample(tgmExample);
        if(!rdmsTgms.isEmpty()){
            rdmsTgm.setId(rdmsTgms.get(0).getId());
            return this.update(rdmsTgm);
        }else{
            return this.insert(rdmsTgm);
        }
    }

    /**
     * 新增
     */
    private String insert(RdmsTgm rdmsTgm) {
        if(org.springframework.util.ObjectUtils.isEmpty(rdmsTgm.getId())){  //当前端页面给出projectID时,将不为空
            rdmsTgm.setId(UuidUtil.getShortUuid());
        }
        RdmsTgmExample tgmExample = new RdmsTgmExample();
        tgmExample.createCriteria().andIdEqualTo(rdmsTgm.getId());
        List<RdmsTgm> rdmsTgms = rdmsTgmMapper.selectByExample(tgmExample);
        if(!rdmsTgms.isEmpty()){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            rdmsTgmMapper.insert(rdmsTgm);
            return rdmsTgm.getId();
        }
    }

    /**
     * 更新
     */
    private String update(RdmsTgm rdmsTgm) {
        RdmsTgmExample tgmExample = new RdmsTgmExample();
        tgmExample.createCriteria().andCustomerIdEqualTo(rdmsTgm.getCustomerId());
        List<RdmsTgm> rdmsTgms = rdmsTgmMapper.selectByExample(tgmExample);
        if(!rdmsTgms.isEmpty()){
            rdmsTgm.setId(rdmsTgms.get(0).getId());
            rdmsTgmMapper.updateByPrimaryKey(rdmsTgm);
            return rdmsTgm.getId();
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsTgm rdmsTgms = rdmsTgmMapper.selectByPrimaryKey(id);
        if(!ObjectUtils.isEmpty(rdmsTgms)){
            rdmsTgms.setDeleted(1);
            this.update(rdmsTgms);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

}
