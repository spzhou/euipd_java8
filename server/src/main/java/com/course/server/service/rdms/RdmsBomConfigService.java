/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.course.server.domain.RdmsBomConfig;
import com.course.server.domain.RdmsBomConfigExample;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsBomConfigMapper;
import com.course.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Service
public class RdmsBomConfigService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsBomConfigService.class);

    @Resource
    private RdmsBomConfigMapper rdmsBomConfigMapper;


    public RdmsBomConfig selectByPrimaryKey(String id){
        return rdmsBomConfigMapper.selectByPrimaryKey(id);
    }

    public List<RdmsBomConfig> getActiveBomConfigByProjectId(String projectId){
        RdmsBomConfigExample bomConfigExample = new RdmsBomConfigExample();
        bomConfigExample.createCriteria().andProjectIdEqualTo(projectId).andIsActiveEqualTo(1).andDeletedEqualTo(0);
        return rdmsBomConfigMapper.selectByExample(bomConfigExample);
    }

    /**
     * 保存
     */
    public String save(RdmsBomConfig bomConfig) {
        if(ObjectUtils.isEmpty(bomConfig.getId())){
            return this.insert(bomConfig);
        }else{
            RdmsBomConfig rdmsBomConfig = this.selectByPrimaryKey(bomConfig.getId());
            if(ObjectUtils.isEmpty(rdmsBomConfig)){
                return this.insert(bomConfig);
            }else{
                return this.update(bomConfig);
            }
        }
    }

    public void resetActiveFlagByProjectId(String projectId) {
        RdmsBomConfigExample bomConfigExample = new RdmsBomConfigExample();
        bomConfigExample.createCriteria().andProjectIdEqualTo(projectId).andDeletedEqualTo(0);
        List<RdmsBomConfig> rdmsBomConfigs = rdmsBomConfigMapper.selectByExample(bomConfigExample);
        if(!CollectionUtils.isEmpty(rdmsBomConfigs)){
            for(RdmsBomConfig bomConfig: rdmsBomConfigs){
                if(! ObjectUtils.isEmpty(bomConfig.getIsActive()) && bomConfig.getIsActive() == 1){
                    bomConfig.setIsActive(0);
                    this.update(bomConfig);
                }
            }
        }
    }

    /**
     * 新增
     */
    private String insert(RdmsBomConfig bomConfig) {
        if(ObjectUtils.isEmpty(bomConfig.getId())){
            bomConfig.setId(UuidUtil.getShortUuid());
        }
        RdmsBomConfig rdmsBomConfig = rdmsBomConfigMapper.selectByPrimaryKey(bomConfig.getId());
        if(! ObjectUtils.isEmpty(rdmsBomConfig)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            bomConfig.setCreateTime(new Date());
            bomConfig.setUpdateTime(new Date());
            bomConfig.setDeleted(0);
            rdmsBomConfigMapper.insert(bomConfig);
            return bomConfig.getId();
        }
    }

    /**
     * 更新
     */
    public String update(RdmsBomConfig bomConfig) {
        if(ObjectUtils.isEmpty(bomConfig.getId())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsBomConfig rdmsBomConfig = this.selectByPrimaryKey(bomConfig.getId());
        if(ObjectUtils.isEmpty(rdmsBomConfig)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            bomConfig.setUpdateTime(new Date());
            bomConfig.setDeleted(0);
            bomConfig.setCreateTime(rdmsBomConfig.getCreateTime());
            rdmsBomConfigMapper.updateByPrimaryKey(bomConfig);
            return bomConfig.getId();
        }
    }

    public String updateByPrimaryKeySelective(RdmsBomConfig bomConfig){
        rdmsBomConfigMapper.updateByPrimaryKeySelective(bomConfig);
        return bomConfig.getId();
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsBomConfig bomConfig = rdmsBomConfigMapper.selectByPrimaryKey(id);
        if(!ObjectUtils.isEmpty(bomConfig)){
            bomConfig.setDeleted(1);
            this.update(bomConfig);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

}
