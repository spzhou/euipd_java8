/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.course.server.domain.RdmsManhourDeclare;
import com.course.server.domain.RdmsManhourDeclareExample;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsManhourDeclareMapper;
import com.course.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class RdmsManhourDeclareService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsManhourDeclareService.class);

    @Resource
    private RdmsManhourDeclareMapper rdmsManhourDeclareMapper;

    public RdmsManhourDeclare selectByPrimaryKey(String id) {
        return rdmsManhourDeclareMapper.selectByPrimaryKey(id);
    }
    /**
     * 保存
     */
    public String save(RdmsManhourDeclare rdmsManhourDeclare) {
        if(rdmsManhourDeclare.getId()==null){
            LOG.error("id不能为空");
            throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
        }
        if(rdmsManhourDeclare.getCreateTime()==null){
            rdmsManhourDeclare.setCreateTime(new Date());
        }
        rdmsManhourDeclare.setDeleted(0);

        RdmsManhourDeclare rdmsManhourDeclare_db = rdmsManhourDeclareMapper.selectByPrimaryKey(rdmsManhourDeclare.getId());
        if(!ObjectUtils.isEmpty(rdmsManhourDeclare_db)){
            return this.update(rdmsManhourDeclare);
        }else{
            return this.insert(rdmsManhourDeclare);
        }
    }

    /**
     * 新增
     */
    private String insert(RdmsManhourDeclare rdmsManhourDeclare) {
        if(ObjectUtils.isEmpty(rdmsManhourDeclare.getId())){  //ID必须为传入参数
            LOG.error("id不能为空");
            throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
        }
        RdmsManhourDeclare rdmsManhourDeclare_db = rdmsManhourDeclareMapper.selectByPrimaryKey(rdmsManhourDeclare.getId());
        if(!ObjectUtils.isEmpty(rdmsManhourDeclare_db)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            rdmsManhourDeclareMapper.insert(rdmsManhourDeclare);
            return rdmsManhourDeclare.getId();
        }
    }

    /**
     * 更新
     */
    private String update(RdmsManhourDeclare rdmsManhourDeclare) {
        if(ObjectUtils.isEmpty(rdmsManhourDeclare.getId())){  //ID必须为传入参数
            LOG.error("id不能为空");
            throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
        }
        RdmsManhourDeclare rdmsManhourDeclare_db = rdmsManhourDeclareMapper.selectByPrimaryKey(rdmsManhourDeclare.getId());
        if(!ObjectUtils.isEmpty(rdmsManhourDeclare_db)){
            rdmsManhourDeclareMapper.updateByPrimaryKey(rdmsManhourDeclare);
            return rdmsManhourDeclare.getId();
        }else{
            LOG.error("输入参数错误");
            throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
        }
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsManhourDeclare manhourDeclareList = rdmsManhourDeclareMapper.selectByPrimaryKey(id);
        if(!ObjectUtils.isEmpty(manhourDeclareList)){
            manhourDeclareList.setDeleted(1);
            this.update(manhourDeclareList);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

}
