/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.course.server.domain.RdmsSysgm;
import com.course.server.domain.RdmsSysgmExample;
import com.course.server.dto.PageDto;
import com.course.server.dto.rdms.RdmsSysgmDto;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsSysgmMapper;
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
public class RdmsSysgmService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsSysgmService.class);

    @Resource
    private RdmsSysgmMapper rdmsSysgmMapper;

    public void list(PageDto<RdmsSysgmDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsSysgmExample rdmsSysgmExample = new RdmsSysgmExample();
        rdmsSysgmExample.createCriteria().andCustomerIdEqualTo(pageDto.getKeyWord()).andDeletedEqualTo(0);
        List<RdmsSysgm> rdmsSysgms = rdmsSysgmMapper.selectByExample(rdmsSysgmExample);

        PageInfo<RdmsSysgm> pageInfo = new PageInfo<>(rdmsSysgms);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsSysgmDto> rdmsBossDtos = CopyUtil.copyList(rdmsSysgms, RdmsSysgmDto.class);
        pageDto.setList(rdmsBossDtos);
    }

    /**
     */
    @Transactional
    public RdmsSysgmDto getSysgmByCustomerId(String customerId){
        RdmsSysgmExample rdmsSysgmExample = new RdmsSysgmExample();
        rdmsSysgmExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andDeletedEqualTo(0);
        List<RdmsSysgm> rdmsSysgms = rdmsSysgmMapper.selectByExample(rdmsSysgmExample);
        if(CollectionUtils.isEmpty(rdmsSysgms)){
            return null;
        }
        if(rdmsSysgms.size() == 1){
            List<RdmsSysgmDto> rdmsSysgmDtos = CopyUtil.copyList(rdmsSysgms, RdmsSysgmDto.class);
            return rdmsSysgmDtos.get(0);
        }
        throw new BusinessException(BusinessExceptionCode.DB_RECORD_ERROR);
    }

    public RdmsSysgm selectByPrimaryKey(String id) {
        RdmsSysgm rdmsSysgm = rdmsSysgmMapper.selectByPrimaryKey(id);
        return rdmsSysgm;
    }
    /**
     * 保存
     */
    public String save(RdmsSysgm rdmsSysgm) {
        if(rdmsSysgm.getCreateTime()==null){
            rdmsSysgm.setCreateTime(new Date());
        }
        rdmsSysgm.setDeleted(0);

        RdmsSysgmExample sysgmExample = new RdmsSysgmExample();
        sysgmExample.createCriteria().andCustomerIdEqualTo(rdmsSysgm.getCustomerId());
        List<RdmsSysgm> rdmsSysgms = rdmsSysgmMapper.selectByExample(sysgmExample);
        if(!rdmsSysgms.isEmpty()){
            rdmsSysgm.setId(rdmsSysgms.get(0).getId());
            return this.update(rdmsSysgm);
        }else{
            return this.insert(rdmsSysgm);
        }
    }

    /**
     * 新增
     */
    private String insert(RdmsSysgm rdmsSysgm) {
        if(org.springframework.util.ObjectUtils.isEmpty(rdmsSysgm.getId())){  //当前端页面给出projectID时,将不为空
            rdmsSysgm.setId(UuidUtil.getShortUuid());
        }
        RdmsSysgmExample sysgmExample = new RdmsSysgmExample();
        sysgmExample.createCriteria().andIdEqualTo(rdmsSysgm.getId());
        List<RdmsSysgm> rdmsSysgms = rdmsSysgmMapper.selectByExample(sysgmExample);
        if(!rdmsSysgms.isEmpty()){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            rdmsSysgmMapper.insert(rdmsSysgm);
            return rdmsSysgm.getId();
        }
    }

    /**
     * 更新
     */
    private String update(RdmsSysgm rdmsSysgm) {
        RdmsSysgmExample sysgmExample = new RdmsSysgmExample();
        sysgmExample.createCriteria().andCustomerIdEqualTo(rdmsSysgm.getCustomerId());
        List<RdmsSysgm> rdmsSysgms = rdmsSysgmMapper.selectByExample(sysgmExample);
        if(!rdmsSysgms.isEmpty()){
            rdmsSysgm.setId(rdmsSysgms.get(0).getId());
            rdmsSysgmMapper.updateByPrimaryKey(rdmsSysgm);
            return rdmsSysgm.getId();
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsSysgm rdmsSysgms = rdmsSysgmMapper.selectByPrimaryKey(id);
        if(!ObjectUtils.isEmpty(rdmsSysgms)){
            rdmsSysgms.setDeleted(1);
            this.update(rdmsSysgms);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

}
