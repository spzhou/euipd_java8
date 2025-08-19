/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.course.server.domain.*;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsSystemMapper;
import com.course.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class RdmsSystemService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsSystemService.class);

    @Resource
    private RdmsSystemMapper rdmsSystemMapper;




    public RdmsSystem selectByPrimaryKey(String id){
        return rdmsSystemMapper.selectByPrimaryKey(id);
    }
    /**
     * 保存
     */
    public String save(RdmsSystem system) {
        if(ObjectUtils.isEmpty(system.getId())){
            return this.insert(system);
        }else{
            RdmsSystem rdmsSystem = this.selectByPrimaryKey(system.getId());
            if(ObjectUtils.isEmpty(rdmsSystem)){
                return this.insert(system);
            }else{
                return this.update(system);
            }
        }
    }

    /**
     * 新增
     */
    private String insert(RdmsSystem system) {
        if(ObjectUtils.isEmpty(system.getId())){
            system.setId(UuidUtil.getShortUuid());
        }
        RdmsSystem rdmsSystem = rdmsSystemMapper.selectByPrimaryKey(system.getId());
        if(! ObjectUtils.isEmpty(rdmsSystem)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            system.setCreateTime(new Date());
            system.setDeleted(0);
            rdmsSystemMapper.insert(system);
            return system.getId();
        }
    }

    /**
     * 更新
     */
    public String update(RdmsSystem system) {
        if(ObjectUtils.isEmpty(system.getId())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsSystem rdmsSystem = this.selectByPrimaryKey(system.getId());
        if(ObjectUtils.isEmpty(rdmsSystem)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            system.setDeleted(0);
            rdmsSystemMapper.updateByPrimaryKey(system);
            return system.getId();
        }
    }

    public String updateByPrimaryKeySelective(RdmsSystem system){
        rdmsSystemMapper.updateByPrimaryKeySelective(system);
        return system.getId();
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsSystem system = rdmsSystemMapper.selectByPrimaryKey(id);
        if(!ObjectUtils.isEmpty(system)){
            system.setDeleted(1);
            this.update(system);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

}
