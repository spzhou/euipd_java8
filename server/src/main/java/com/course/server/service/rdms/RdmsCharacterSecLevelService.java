/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.course.server.domain.RdmsCharacterSecLevel;
import com.course.server.domain.RdmsCharacterSecLevelExample;
import com.course.server.dto.rdms.RdmsProjectDto;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsCharacterSecLevelMapper;
import com.course.server.mapper.RdmsProjectSecLevelMapper;
import com.course.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RdmsCharacterSecLevelService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsCharacterSecLevelService.class);

    @Resource
    private RdmsCharacterSecLevelMapper rdmsCharacterSecLevelMapper;


    /**
     * 根据主键查询角色二级信息
     * 通过ID查询角色二级详细信息
     * 
     * @param id 角色二级ID
     * @return 返回角色二级对象
     */
    public RdmsCharacterSecLevel selectByPrimaryKey(String id){
        return rdmsCharacterSecLevelMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据项目ID查询角色二级列表
     * 通过项目ID查询对应的角色二级信息列表
     * 
     * @param projectId 项目ID
     * @return 返回角色二级信息列表
     */
    public List<RdmsCharacterSecLevel> selectByProjectId(String projectId){
        RdmsCharacterSecLevelExample rdmsCharacterSecLevelExample = new RdmsCharacterSecLevelExample();
        rdmsCharacterSecLevelExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andDeletedEqualTo(0);
        List<RdmsCharacterSecLevel> rdmsCharacterSecLevels = rdmsCharacterSecLevelMapper.selectByExample(rdmsCharacterSecLevelExample);
        return rdmsCharacterSecLevels;
    }

    /**
     * 根据子项目ID查询角色二级列表
     * 通过子项目ID查询对应的角色二级信息列表
     * 
     * @param subprojectId 子项目ID
     * @return 返回角色二级信息列表
     */
    public List<RdmsCharacterSecLevel> selectBySubProjectId(String subprojectId){
        RdmsCharacterSecLevelExample rdmsCharacterSecLevelExample = new RdmsCharacterSecLevelExample();
        rdmsCharacterSecLevelExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andDeletedEqualTo(0);
        List<RdmsCharacterSecLevel> rdmsCharacterSecLevels = rdmsCharacterSecLevelMapper.selectByExample(rdmsCharacterSecLevelExample);
        return rdmsCharacterSecLevels;
    }

    /**
     * 根据角色ID查询角色二级列表
     * 通过角色ID查询对应的角色二级信息列表
     * 
     * @param characterId 角色ID
     * @return 返回角色二级信息列表
     */
    public List<RdmsCharacterSecLevel> selectByCharacterId(String characterId){
        RdmsCharacterSecLevelExample rdmsCharacterSecLevelExample = new RdmsCharacterSecLevelExample();
        rdmsCharacterSecLevelExample.createCriteria()
                .andCharacterIdEqualTo(characterId)
                .andDeletedEqualTo(0);
        List<RdmsCharacterSecLevel> rdmsCharacterSecLevels = rdmsCharacterSecLevelMapper.selectByExample(rdmsCharacterSecLevelExample);
        return rdmsCharacterSecLevels;
    }

    /**
     * 保存
     */
    public String save(RdmsCharacterSecLevel characterSecLevel) {
        if(ObjectUtils.isEmpty(characterSecLevel.getId())){
            return this.insert(characterSecLevel);
        }else{
            RdmsCharacterSecLevel rdmsCharacterSecLevel = this.selectByPrimaryKey(characterSecLevel.getId());
            if(ObjectUtils.isEmpty(rdmsCharacterSecLevel)){
                return this.insert(characterSecLevel);
            }else{
                return this.update(characterSecLevel);
            }
        }
    }

    /**
     * 根据角色ID保存角色二级信息
     * 如果角色ID已存在则更新，否则新增
     * 
     * @param characterSecLevel 角色二级对象
     * @return 返回保存后的ID
     * @throws BusinessException 输入信息错误异常
     */
    public String saveByCharacterId(RdmsCharacterSecLevel characterSecLevel) {
        if(!ObjectUtils.isEmpty(characterSecLevel.getCharacterId())){
            List<RdmsCharacterSecLevel> rdmsCharacterSecLevels = this.selectByCharacterId(characterSecLevel.getCharacterId());
            if(CollectionUtils.isEmpty(rdmsCharacterSecLevels)){
                //insert
                return this.insert(characterSecLevel);
            }else{
                //update
                characterSecLevel.setId(rdmsCharacterSecLevels.get(0).getId());
                return this.update(characterSecLevel);
            }
        }else{
            throw new BusinessException(BusinessExceptionCode.INPUT_INFORMATION_ERROR);
        }
    }

    private String insert(RdmsCharacterSecLevel characterSecLevel) {
        if(ObjectUtils.isEmpty(characterSecLevel.getId())){  //当前端页面给出projectID时,将不为空
            characterSecLevel.setId(UuidUtil.getShortUuid());
        }
        RdmsCharacterSecLevel rdmsCharacterSecLevel = this.selectByPrimaryKey(characterSecLevel.getId());
        if(ObjectUtils.isEmpty(rdmsCharacterSecLevel)){
            characterSecLevel.setDeleted(0);
            rdmsCharacterSecLevelMapper.insert(characterSecLevel);
            return characterSecLevel.getId();
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }
    }

    /**
     * 更新角色二级信息
     * 根据ID更新角色二级信息
     * 
     * @param characterSecLevel 角色二级对象
     * @return 返回更新后的ID
     * @throws BusinessException 数据记录错误或记录不存在异常
     */
    public String update(RdmsCharacterSecLevel characterSecLevel) {
        if(ObjectUtils.isEmpty(characterSecLevel.getId())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsCharacterSecLevel rdmsCharacterSecLevel = this.selectByPrimaryKey(characterSecLevel.getId());
        if(ObjectUtils.isEmpty(rdmsCharacterSecLevel)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            characterSecLevel.setDeleted(0);
            rdmsCharacterSecLevelMapper.updateByPrimaryKey(characterSecLevel);
            return characterSecLevel.getId();
        }
    }

    /**
     * 选择性更新角色二级信息
     * 根据ID选择性更新角色二级信息
     * 
     * @param characterSecLevel 角色二级对象
     * @return 返回更新后的ID
     */
    public String updateByPrimaryKeySelective(RdmsCharacterSecLevel characterSecLevel){
        rdmsCharacterSecLevelMapper.updateByPrimaryKeySelective(characterSecLevel);
        return characterSecLevel.getId();
    }
    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsCharacterSecLevel rdmsCharacterSecLevel = this.selectByPrimaryKey(id);
        if(ObjectUtils.isEmpty(rdmsCharacterSecLevel)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            rdmsCharacterSecLevel.setDeleted(1);
            rdmsCharacterSecLevelMapper.updateByPrimaryKey(rdmsCharacterSecLevel);
        }
    }

}
