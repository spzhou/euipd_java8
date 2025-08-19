/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.course.server.domain.*;
import com.course.server.dto.rdms.RdmsProjectDto;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
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
public class RdmsProjectSecLevelService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsProjectSecLevelService.class);

    @Resource
    private RdmsProjectSecLevelMapper rdmsProjectSecLevelMapper;
    @Resource
    private RdmsProjectService rdmsProjectService;
    @Resource
    private RdmsCharacterService rdmsCharacterService;

    @Transactional
    public List<String> getProjectTeamIdsByProjectId(String projectId){
        //项目团队成员包括:IPMT、产品经理、项目经理、测试主管、核心团队
        RdmsProjectDto projectRecordInfo = rdmsProjectService.getProjectRecordInfo(projectId);
        List<String> teamIdList = new ArrayList<>();
        if(!ObjectUtils.isEmpty(projectRecordInfo.getSupervise())){
            teamIdList.add(projectRecordInfo.getSupervise());
        }
        if(!ObjectUtils.isEmpty(projectRecordInfo.getProductManagerId())){
            teamIdList.add(projectRecordInfo.getProductManagerId());
        }
        if(!ObjectUtils.isEmpty(projectRecordInfo.getProjectManagerId())){
            teamIdList.add(projectRecordInfo.getProjectManagerId());
        }
        if(!ObjectUtils.isEmpty(projectRecordInfo.getKeyMemberList())){
            teamIdList.addAll(projectRecordInfo.getKeyMemberList());
        }
        List<String> stringList = teamIdList.stream().distinct().collect(Collectors.toList());
        return stringList;
    }

    @Transactional
    public List<String> getProjectTeamIdsByCharacterId(String characterId){
        //项目团队成员包括:IPMT、产品经理、项目经理、测试主管、核心团队
        List<String> teamIdList = new ArrayList<>();
        RdmsCharacter character = rdmsCharacterService.selectByPrimaryKey(characterId);
        if(!ObjectUtils.isEmpty(character.getProjectId())){
            teamIdList = this.getProjectTeamIdsByProjectId(character.getProjectId());
        }
        return teamIdList;
    }

    public RdmsProjectSecLevel selectByPrimaryKey(String id){
        return rdmsProjectSecLevelMapper.selectByPrimaryKey(id);
    }

    public List<RdmsProjectSecLevel> getByProjectId(String projectId){
        if(ObjectUtils.isEmpty(projectId)){
            return null;
        }
        RdmsProjectSecLevelExample rdmsProjectSecLevelExample = new RdmsProjectSecLevelExample();
        rdmsProjectSecLevelExample.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andDeletedEqualTo(0);
        List<RdmsProjectSecLevel> rdmsProjectSecLevels = rdmsProjectSecLevelMapper.selectByExample(rdmsProjectSecLevelExample);
        return rdmsProjectSecLevels;
    }

    public List<RdmsProjectSecLevel> getByCharacterId(String characterId){
        List<RdmsProjectSecLevel> rdmsProjectSecLevels = new ArrayList<>();
        RdmsCharacter character = rdmsCharacterService.selectByPrimaryKey(characterId);
        if(!ObjectUtils.isEmpty(character.getProjectId())){
            rdmsProjectSecLevels = this.getByProjectId(character.getProjectId());
        }
        return rdmsProjectSecLevels;
    }

    public List<RdmsProjectSecLevel> getBySubProjectId(String subprojectId){
        RdmsProjectSecLevelExample rdmsProjectSecLevelExample = new RdmsProjectSecLevelExample();
        rdmsProjectSecLevelExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andDeletedEqualTo(0);
        List<RdmsProjectSecLevel> rdmsProjectSecLevels = rdmsProjectSecLevelMapper.selectByExample(rdmsProjectSecLevelExample);
        return rdmsProjectSecLevels;
    }

    /**
     * 保存
     */
/*    public String save(RdmsProjectSecLevel projectSecLevel) {
        if(ObjectUtils.isEmpty(projectSecLevel.getId())){
            return this.insert(projectSecLevel);
        }else{
            RdmsProjectSecLevel rdmsProjectSecLevel = this.selectByPrimaryKey(projectSecLevel.getId());
            if(ObjectUtils.isEmpty(rdmsProjectSecLevel)){
                return this.insert(projectSecLevel);
            }else{
                return this.update(projectSecLevel);
            }
        }
    }*/

    public String saveBySubprojectId(RdmsProjectSecLevel projectSecLevel) {
        if(!ObjectUtils.isEmpty(projectSecLevel.getSubprojectId())){
            List<RdmsProjectSecLevel> rdmsProjectSecLevels = this.getBySubProjectId(projectSecLevel.getSubprojectId());
            if(CollectionUtils.isEmpty(rdmsProjectSecLevels)){
                //insert
                return this.insert(projectSecLevel);
            }else{
                //update
                projectSecLevel.setId(rdmsProjectSecLevels.get(0).getId());
                return this.update(projectSecLevel);
            }
        }else{
            throw new BusinessException(BusinessExceptionCode.INPUT_INFORMATION_ERROR);
        }
    }

    private String insert(RdmsProjectSecLevel projectSecLevel) {
        if(ObjectUtils.isEmpty(projectSecLevel.getId())){  //当前端页面给出projectID时,将不为空
            projectSecLevel.setId(UuidUtil.getShortUuid());
        }
        RdmsProjectSecLevel rdmsProjectSecLevel = this.selectByPrimaryKey(projectSecLevel.getId());
        if(ObjectUtils.isEmpty(rdmsProjectSecLevel)){
            projectSecLevel.setDeleted(0);
            rdmsProjectSecLevelMapper.insert(projectSecLevel);
            return projectSecLevel.getId();
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }
    }

    public String update(RdmsProjectSecLevel projectSecLevel) {
        if(ObjectUtils.isEmpty(projectSecLevel.getId())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsProjectSecLevel rdmsProjectSecLevel = this.selectByPrimaryKey(projectSecLevel.getId());
        if(ObjectUtils.isEmpty(rdmsProjectSecLevel)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            projectSecLevel.setDeleted(0);
            rdmsProjectSecLevelMapper.updateByPrimaryKey(projectSecLevel);
            return projectSecLevel.getId();
        }
    }

    public String updateByPrimaryKeySelective(RdmsProjectSecLevel projectSecLevel){
        rdmsProjectSecLevelMapper.updateByPrimaryKeySelective(projectSecLevel);
        return projectSecLevel.getId();
    }
    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsProjectSecLevel rdmsProjectSecLevel = this.selectByPrimaryKey(id);
        if(ObjectUtils.isEmpty(rdmsProjectSecLevel)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            rdmsProjectSecLevel.setDeleted(1);
            rdmsProjectSecLevelMapper.updateByPrimaryKey(rdmsProjectSecLevel);
        }
    }

}
