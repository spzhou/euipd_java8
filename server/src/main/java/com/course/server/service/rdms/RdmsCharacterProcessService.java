/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.alibaba.fastjson.JSONObject;
import com.course.server.domain.*;
import com.course.server.dto.PageDto;
import com.course.server.dto.rdms.*;
import com.course.server.enums.FileGroupingEnum;
import com.course.server.enums.rdms.*;

import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsCharacterProcessMapper;
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
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RdmsCharacterProcessService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsCharacterProcessService.class);

    @Resource
    private RdmsCharacterProcessMapper rdmsCharacterProcessMapper;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private RdmsCharacterService rdmsCharacterService;
    @Resource
    private RdmsFileService rdmsFileService;


    /**
    * 分页列表查询
    */
    public void list(PageDto<RdmsCharacterProcessDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsCharacterProcessExample rdmsCharacterProcessExample = new RdmsCharacterProcessExample();
        List<RdmsCharacterProcess> rdmsCharacterProcesses = rdmsCharacterProcessMapper.selectByExample(rdmsCharacterProcessExample);
        List<RdmsCharacterProcessDto> rdmsCharacterProcessDtos = CopyUtil.copyList(rdmsCharacterProcesses, RdmsCharacterProcessDto.class);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(RdmsCharacterProcessDto characterProcessDto : rdmsCharacterProcessDtos){
            characterProcessDto.setCreateTimeStr(sdf.format(characterProcessDto.getCreateTime()));
        }
        PageInfo<RdmsCharacterProcess> pageInfo = new PageInfo<>(rdmsCharacterProcesses);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(rdmsCharacterProcessDtos);
    }

    /**
    * 根据审批状态进行分页列表查询
    */
    public void listByStatusId(PageDto<RdmsCharacterProcessDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsCharacterProcessExample rdmsCharacterProcessExample = new RdmsCharacterProcessExample();
        rdmsCharacterProcessExample.createCriteria().andApprovalStatusEqualTo(pageDto.getKeyWord()).andDeletedEqualTo(0);
        List<RdmsCharacterProcess> rdmsCharacterProcesses = rdmsCharacterProcessMapper.selectByExample(rdmsCharacterProcessExample);
        List<RdmsCharacterProcessDto> rdmsCharacterProcessDtos = CopyUtil.copyList(rdmsCharacterProcesses, RdmsCharacterProcessDto.class);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(RdmsCharacterProcessDto characterProcessDto : rdmsCharacterProcessDtos){
            characterProcessDto.setCreateTimeStr(sdf.format(characterProcessDto.getCreateTime()));
        }
        PageInfo<RdmsCharacterProcess> pageInfo = new PageInfo<>(rdmsCharacterProcesses);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(rdmsCharacterProcessDtos);
    }

    /**
    * 根据特性号进行分页列表查询
    */
    public List<RdmsCharacterProcessDto> listByCharacterId(String id) {
        RdmsCharacterProcessExample rdmsCharacterProcessExample = new RdmsCharacterProcessExample();
        rdmsCharacterProcessExample.setOrderByClause("create_time desc");
        rdmsCharacterProcessExample.createCriteria().andCharacterIdEqualTo(id).andDeletedEqualTo(0);
        List<RdmsCharacterProcess> rdmsCharacterProcesses = rdmsCharacterProcessMapper.selectByExample(rdmsCharacterProcessExample);
        List<RdmsCharacterProcessDto> rdmsCharacterProcessDtos = CopyUtil.copyList(rdmsCharacterProcesses, RdmsCharacterProcessDto.class);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(RdmsCharacterProcessDto characterProcessDto : rdmsCharacterProcessDtos){
            characterProcessDto.setCreateTimeStr(sdf.format(characterProcessDto.getCreateTime()));

            if(characterProcessDto.getNextNode() != null){
                RdmsCustomerUser nextNode = rdmsCustomerUserService.selectByPrimaryKey(characterProcessDto.getNextNode());
                characterProcessDto.setNextNodeName(nextNode.getTrueName());
            }
            if(characterProcessDto.getProcessStatus() != null){
                CharacterProcessStatusEnum jobItemStatusEnumByStatus = CharacterProcessStatusEnum.getJobItemStatusEnumByStatus(characterProcessDto.getProcessStatus());
                assert jobItemStatusEnumByStatus != null;
                characterProcessDto.setProcessStatusName(jobItemStatusEnumByStatus.getStatusName());
            }
            if(characterProcessDto.getExecutorId() != null){
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(characterProcessDto.getExecutorId());
                characterProcessDto.setExecutorName(rdmsCustomerUser.getTrueName());
            }
        }
        return rdmsCharacterProcessDtos;
    }

    public RdmsHmiCharacterProcessDto listProcessAllInfoByCharacterId(String id) {
        RdmsCharacterProcessExample rdmsCharacterProcessExample = new RdmsCharacterProcessExample();
        rdmsCharacterProcessExample.setOrderByClause("create_time desc");
        rdmsCharacterProcessExample.createCriteria().andCharacterIdEqualTo(id).andDeletedEqualTo(0);
        List<RdmsCharacterProcess> rdmsCharacterProcesses = rdmsCharacterProcessMapper.selectByExample(rdmsCharacterProcessExample);

        List<RdmsCharacterProcessDto> rdmsCharacterProcessDtos = CopyUtil.copyList(rdmsCharacterProcesses, RdmsCharacterProcessDto.class);
        List<RdmsHmiProcessAttachmentDto> processAttachmentDtos = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(RdmsCharacterProcessDto characterProcessDto : rdmsCharacterProcessDtos){
            characterProcessDto.setCreateTimeStr(sdf.format(characterProcessDto.getCreateTime()));

            if(characterProcessDto.getNextNode() != null){
                RdmsCustomerUser nextNode = rdmsCustomerUserService.selectByPrimaryKey(characterProcessDto.getNextNode());
                characterProcessDto.setNextNodeName(nextNode.getTrueName());
            }

            if(characterProcessDto.getProcessStatus() != null){
                CharacterProcessStatusEnum jobItemStatusEnumByStatus = CharacterProcessStatusEnum.getJobItemStatusEnumByStatus(characterProcessDto.getProcessStatus());
                characterProcessDto.setProcessStatusName(jobItemStatusEnumByStatus.getStatusName());
            }
            if(characterProcessDto.getExecutorId() != null){
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(characterProcessDto.getExecutorId());
                characterProcessDto.setExecutorName(rdmsCustomerUser.getTrueName());
            }

        }
        RdmsHmiCharacterProcessDto hmiCharacterProcessDto = new RdmsHmiCharacterProcessDto();
        hmiCharacterProcessDto.setCharacterProcessDtos(rdmsCharacterProcessDtos);
        hmiCharacterProcessDto.setProcessAttachmentDtos(processAttachmentDtos);
        return hmiCharacterProcessDto;
    }

    /**
    * 根据执行者进行分页列表查询
    */
    public void listByExecutorId(PageDto<RdmsCharacterProcessDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsCharacterProcessExample rdmsCharacterProcessExample = new RdmsCharacterProcessExample();
        rdmsCharacterProcessExample.createCriteria().andExecutorIdEqualTo(pageDto.getKeyWord()).andDeletedEqualTo(0);
        List<RdmsCharacterProcess> rdmsCharacterProcesses = rdmsCharacterProcessMapper.selectByExample(rdmsCharacterProcessExample);
        List<RdmsCharacterProcessDto> rdmsCharacterProcessDtos = CopyUtil.copyList(rdmsCharacterProcesses, RdmsCharacterProcessDto.class);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(RdmsCharacterProcessDto characterProcessDto : rdmsCharacterProcessDtos){
            characterProcessDto.setCreateTimeStr(sdf.format(characterProcessDto.getCreateTime()));
        }
        PageInfo<RdmsCharacterProcess> pageInfo = new PageInfo<>(rdmsCharacterProcesses);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(rdmsCharacterProcessDtos);
    }

    /**
    * 根据特性编号,进行查询
    */
    public RdmsCharacterProcess getCharacterProcessById(String id) {
        return rdmsCharacterProcessMapper.selectByPrimaryKey(id);
    }

    /**
    * 根据特性编号,进行查询
    */
    public long getCharacterProcessCount(String characterId) {
        RdmsCharacterProcessExample rdmsCharacterProcessExample = new RdmsCharacterProcessExample();
        rdmsCharacterProcessExample.createCriteria().andCharacterIdEqualTo(characterId);
        return rdmsCharacterProcessMapper.countByExample(rdmsCharacterProcessExample);
    }

    /**
     * 保存
     * 1. Character创建的时候, 创建一条Process
     * 2. Character持续保存的过程中, 识别相应的工单是否有相同审批状态的过程记录,如果存在, 则update Process记录
     * 3. 如果工单的保存过程中, 审批状态不一致, 则新建一条Process记录
     */
    public String save(RdmsCharacterProcess characterProcess) {
        if(characterProcess.getCreateTime()==null){
            characterProcess.setCreateTime(new Date());
        }
        characterProcess.setDeleted(0);
        if(ObjectUtils.isEmpty(characterProcess.getId())){
            characterProcess.setId(UuidUtil.getShortUuid());
            return this.insert(characterProcess);
        }else{
            RdmsCharacterProcess characterProcess1 = rdmsCharacterProcessMapper.selectByPrimaryKey(characterProcess.getId());
            if(ObjectUtils.isEmpty(characterProcess1)){
                return this.insert(characterProcess);
            }else{
                return this.update(characterProcess);
            }
        }
    }


    public void submitDecomposeCharacterProcessItems(String characterId){
        //获得所有的composeCharacter清单
        List<RdmsCharacterDto> characterListByParentId = rdmsCharacterService.getCharacterListByParentId(characterId);
        if(! CollectionUtils.isEmpty(characterListByParentId)){
            for(RdmsCharacterDto characterDto : characterListByParentId){
                RdmsCharacterProcess characterProcess = new RdmsCharacterProcess();
                characterProcess.setCharacterId(characterDto.getId());
                characterProcess.setExecutorId(characterDto.getWriterId());
                long characterProcessCount = this.getCharacterProcessCount(characterDto.getId());
                characterProcess.setDeep((int)characterProcessCount);
                characterProcess.setJobDescription("提交组件定义表");
                characterProcess.setProcessStatus(CharacterProcessStatusEnum.SUBMIT.getStatus());
                characterProcess.setNextNode(characterDto.getProductManagerId());
                this.save(characterProcess);
            }
        }
    }

    public void submitDemandCharacterProcessItems(String jobItemId){
        //获得所有的demandCharacter清单
        List<RdmsCharacterDto> characterListByJobItemId = rdmsCharacterService.getCharacterListByJobItemIdAndStatus(jobItemId, null);
        if(! CollectionUtils.isEmpty(characterListByJobItemId)){
            for(RdmsCharacterDto characterDto : characterListByJobItemId){
                RdmsCharacterProcess characterProcess = new RdmsCharacterProcess();
                characterProcess.setCharacterId(characterDto.getId());
                characterProcess.setExecutorId(characterDto.getWriterId());
                long characterProcessCount = this.getCharacterProcessCount(characterDto.getId());
                characterProcess.setDeep((int)characterProcessCount);
                characterProcess.setJobDescription("提交组件定义表");
                characterProcess.setProcessStatus(CharacterProcessStatusEnum.SUBMIT.getStatus());
                characterProcess.setNextNode(characterDto.getProductManagerId());
                this.save(characterProcess);
            }
        }
    }


    /**
     * 提交
     */
    @Transactional
    public String submit(RdmsCharacterProcess characterProcess) {
        if(characterProcess.getCreateTime()==null){
            characterProcess.setCreateTime(new Date());
        }
        characterProcess.setProcessStatus(CharacterProcessStatusEnum.SUBMIT.getStatus());
        characterProcess.setDeleted(0);
        String characterProcessId;
        if(characterProcess.getId() == null || characterProcess.getId().equals("")){
            characterProcessId = this.insert(characterProcess);
        }else{
            RdmsCharacterProcess rdmsCharacterProcesses = rdmsCharacterProcessMapper.selectByPrimaryKey(characterProcess.getId());
            if(! ObjectUtils.isEmpty(rdmsCharacterProcesses)){
                characterProcessId = this.update(characterProcess);
            }else{
                characterProcessId = this.insert(characterProcess);
            }
        }
        RdmsCharacter productCharacter = rdmsCharacterService.selectByPrimaryKey(characterProcess.getCharacterId());
        productCharacter.setStatus(CharacterStatusEnum.SUBMIT.getStatus());
        /*productCharacter.setApproverId(characterProcess.getExecutorId());
        productCharacter.setApprovedTime(new Date());*/
        productCharacter.setNextNode(productCharacter.getProductManagerId());

        RdmsAttachmentDto attachmentDto = new RdmsAttachmentDto();
        attachmentDto.setUse(FileGroupingEnum.CHARACTER.getCode());
        attachmentDto.setItemId(productCharacter.getId());
        List<RdmsFileDto> fileDtos = rdmsFileService.listFileInfo(attachmentDto);
        List<String> fileIds = fileDtos.stream().map(RdmsFileDto::getId).collect(Collectors.toList());
        String fileIdsStr = JSONObject.toJSONString(fileIds);
        productCharacter.setFileListStr(fileIdsStr);
        rdmsCharacterService.update(productCharacter);
        return characterProcessId;
    }

    /**
     * 驳回
     */
    @Transactional
    public String reject(RdmsCharacterProcess characterProcess) {
        if(characterProcess.getCreateTime()==null){
            characterProcess.setCreateTime(new Date());
        }
        characterProcess.setProcessStatus(CharacterProcessStatusEnum.EDITING.getStatus());
        characterProcess.setDeleted(0);
        String characterProcessId = new String();
        if(characterProcess.getId() == null || characterProcess.getId().equals("")){
            characterProcessId = this.insert(characterProcess);
        }else{
            RdmsCharacterProcessExample rdmsCharacterProcessExample = new RdmsCharacterProcessExample();
            rdmsCharacterProcessExample.createCriteria().andIdEqualTo(characterProcess.getId());
            List<RdmsCharacterProcess> rdmsCharacterProcesses = rdmsCharacterProcessMapper.selectByExample(rdmsCharacterProcessExample);
            if(rdmsCharacterProcesses.size()>0){
                characterProcessId = this.update(characterProcess);
            }else{
                characterProcessId = this.insert(characterProcess);
            }
        }
        RdmsCharacter productCharacter = rdmsCharacterService.selectByPrimaryKey(characterProcess.getCharacterId());
        productCharacter.setStatus(CharacterStatusEnum.SAVED.getStatus());
//        productCharacter.setApproverId(characterProcess.getExecutorId());
        productCharacter.setNextNode(productCharacter.getWriterId());

        RdmsAttachmentDto attachmentDto = new RdmsAttachmentDto();
        attachmentDto.setUse(FileGroupingEnum.CHARACTER.getCode());
        attachmentDto.setItemId(productCharacter.getId());
        List<RdmsFileDto> fileDtos = rdmsFileService.listFileInfo(attachmentDto);
        List<String> fileIds = fileDtos.stream().map(RdmsFileDto::getId).collect(Collectors.toList());
        String fileIdsStr = JSONObject.toJSONString(fileIds);
        productCharacter.setFileListStr(fileIdsStr);
        rdmsCharacterService.update(productCharacter);
        return characterProcessId;
    }

    @Transactional
    public String transfer(RdmsCharacterProcess characterProcess) {
        if(characterProcess.getCreateTime()==null){
            characterProcess.setCreateTime(new Date());
        }
        characterProcess.setProcessStatus(CharacterProcessStatusEnum.SUBMIT.getStatus());
        characterProcess.setDeleted(0);
        String characterProcessId = new String();
        if(characterProcess.getId() == null || characterProcess.getId().equals("")){
            characterProcessId = this.insert(characterProcess);
        }else{
            RdmsCharacterProcessExample rdmsCharacterProcessExample = new RdmsCharacterProcessExample();
            rdmsCharacterProcessExample.createCriteria().andIdEqualTo(characterProcess.getId());
            List<RdmsCharacterProcess> rdmsCharacterProcesses = rdmsCharacterProcessMapper.selectByExample(rdmsCharacterProcessExample);
            if(rdmsCharacterProcesses.size()>0){
                characterProcessId = this.update(characterProcess);
            }else{
                characterProcessId = this.insert(characterProcess);
            }
        }
        RdmsCharacter productCharacter = rdmsCharacterService.selectByPrimaryKey(characterProcess.getCharacterId());
        productCharacter.setStatus(CharacterStatusEnum.SUBMIT.getStatus());
//        productCharacter.setApproverId(characterProcess.getExecutorId());
        productCharacter.setNextNode(characterProcess.getNextNode());

        RdmsAttachmentDto attachmentDto = new RdmsAttachmentDto();
        attachmentDto.setUse(FileGroupingEnum.CHARACTER.getCode());
        attachmentDto.setItemId(productCharacter.getId());
        List<RdmsFileDto> fileDtos = rdmsFileService.listFileInfo(attachmentDto);
        List<String> fileIds = fileDtos.stream().map(RdmsFileDto::getId).collect(Collectors.toList());
        String fileIdsStr = JSONObject.toJSONString(fileIds);
        productCharacter.setFileListStr(fileIdsStr);
        rdmsCharacterService.update(productCharacter);
        return characterProcessId;
    }

    /**
     * 新增
     */
    private String insert(RdmsCharacterProcess characterProcess) {
        if(ObjectUtils.isEmpty(characterProcess.getId())){  //当前端页面给出projectID时,将不为空
            characterProcess.setId(UuidUtil.getShortUuid());
        }
        characterProcess.setCreateTime(new Date());
        characterProcess.setDeleted(0);
        RdmsCharacterProcess rdmsCharacterProcess = rdmsCharacterProcessMapper.selectByPrimaryKey(characterProcess.getId());
        if(! ObjectUtils.isEmpty(rdmsCharacterProcess)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            rdmsCharacterProcessMapper.insert(characterProcess);
            return characterProcess.getId();
        }
    }

    /**
     * 更新
     */
    private String update(RdmsCharacterProcess characterProcess) {
        RdmsCharacterProcess rdmsCharacterProcess = rdmsCharacterProcessMapper.selectByPrimaryKey(characterProcess.getId());
        if(rdmsCharacterProcess != null){
            rdmsCharacterProcessMapper.updateByPrimaryKey(characterProcess);
            return characterProcess.getId();
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsCharacterProcess rdmsCharacterProcess = rdmsCharacterProcessMapper.selectByPrimaryKey(id);
        if(rdmsCharacterProcess != null){
            rdmsCharacterProcess.setDeleted(1);
            rdmsCharacterProcessMapper.updateByPrimaryKey(rdmsCharacterProcess);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

}
