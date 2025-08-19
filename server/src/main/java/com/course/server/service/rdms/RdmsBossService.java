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
import com.course.server.dto.rdms.RdmsBossDto;
import com.course.server.dto.rdms.RdmsIpmtDto;
import com.course.server.dto.rdms.RdmsProjectDto;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsBossMapper;
import com.course.server.mapper.RdmsCustomerUserMapper;
import com.course.server.mapper.RdmsIpmtMapper;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RdmsBossService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsBossService.class);

    @Resource
    private RdmsBossMapper rdmsBossMapper;

    public void list(PageDto<RdmsBossDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsBossExample rdmsBossExample = new RdmsBossExample();
        rdmsBossExample.createCriteria().andCustomerIdEqualTo(pageDto.getKeyWord()).andDeletedEqualTo(0);
        List<RdmsBoss> rdmsBosses = rdmsBossMapper.selectByExample(rdmsBossExample);

        PageInfo<RdmsBoss> pageInfo = new PageInfo<>(rdmsBosses);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsBossDto> rdmsBossDtos = CopyUtil.copyList(rdmsBosses, RdmsBossDto.class);
        pageDto.setList(rdmsBossDtos);
    }

    /**
     */
    @Transactional
    public RdmsBossDto getBossByCustomerId(String customerId){
        RdmsBossExample rdmsBossExample = new RdmsBossExample();
        rdmsBossExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andDeletedEqualTo(0);
        List<RdmsBoss> rdmsBosses = rdmsBossMapper.selectByExample(rdmsBossExample);
        if(CollectionUtils.isEmpty(rdmsBosses)){
            return null;
        }
        if(rdmsBosses.size() == 1){
            List<RdmsBossDto> rdmsBossDtos = CopyUtil.copyList(rdmsBosses, RdmsBossDto.class);
            return rdmsBossDtos.get(0);
        }
        throw new BusinessException(BusinessExceptionCode.DB_RECORD_ERROR);
    }

    public RdmsBoss selectByPrimaryKey(String id) {
        RdmsBoss rdmsBoss = rdmsBossMapper.selectByPrimaryKey(id);
        return rdmsBoss;
    }
    /**
     * 保存
     */
    public String save(RdmsBoss rdmsBoss) {
        if(rdmsBoss.getCreateTime()==null){
            rdmsBoss.setCreateTime(new Date());
        }
        rdmsBoss.setDeleted(0);

        RdmsBossExample bossExample = new RdmsBossExample();
        bossExample.createCriteria().andCustomerIdEqualTo(rdmsBoss.getCustomerId());
        List<RdmsBoss> rdmsBosses = rdmsBossMapper.selectByExample(bossExample);
        if(!rdmsBosses.isEmpty()){
            rdmsBoss.setId(rdmsBosses.get(0).getId());
            return this.update(rdmsBoss);
        }else{
            return this.insert(rdmsBoss);
        }

    }

    /**
     * 新增
     */
    private String insert(RdmsBoss rdmsBoss) {
        if(org.springframework.util.ObjectUtils.isEmpty(rdmsBoss.getId())){  //当前端页面给出projectID时,将不为空
            rdmsBoss.setId(UuidUtil.getShortUuid());
        }
        RdmsBossExample bossExample = new RdmsBossExample();
        bossExample.createCriteria().andIdEqualTo(rdmsBoss.getId());
        List<RdmsBoss> rdmsBosses = rdmsBossMapper.selectByExample(bossExample);
        if(!rdmsBosses.isEmpty()){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            rdmsBossMapper.insert(rdmsBoss);
            return rdmsBoss.getId();
        }
    }

    /**
     * 更新
     */
    private String update(RdmsBoss rdmsBoss) {
        RdmsBossExample bossExample = new RdmsBossExample();
        bossExample.createCriteria().andCustomerIdEqualTo(rdmsBoss.getCustomerId());
        List<RdmsBoss> rdmsBosses = rdmsBossMapper.selectByExample(bossExample);
        if(!rdmsBosses.isEmpty()){
            rdmsBoss.setId(rdmsBosses.get(0).getId());
            rdmsBossMapper.updateByPrimaryKey(rdmsBoss);
            return rdmsBoss.getId();
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsBoss rdmsBosses = rdmsBossMapper.selectByPrimaryKey(id);
        if(!ObjectUtils.isEmpty(rdmsBosses)){
            rdmsBosses.setDeleted(1);
            this.update(rdmsBosses);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

}
