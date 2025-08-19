/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.course.server.domain.*;
import com.course.server.domain.RdmsJobItemProcessExample;
import com.course.server.dto.PageDto;
import com.course.server.dto.rdms.RdmsCharacterDto;
import com.course.server.dto.rdms.RdmsFileDto;
import com.course.server.dto.rdms.RdmsHmiCharacterPlainDto;
import com.course.server.dto.rdms.RdmsJobItemProcessDto;
import com.course.server.enums.rdms.JobItemProcessStatusEnum;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsJobItemProcessMapper;
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
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RdmsJobItemProcessService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsJobItemProcessService.class);

    @Resource
    private RdmsJobItemProcessMapper rdmsJobItemProcessMapper;
    @Resource
    private RdmsJobItemService rdmsJobItemsService;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private RdmsFileService rdmsFileService;
    @Resource
    private RdmsCharacterService rdmsCharacterService;

    /**
    * 分页列表查询
    */
    public void list(PageDto<RdmsJobItemProcessDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsJobItemProcessExample rdmsJobItemProcessExample = new RdmsJobItemProcessExample();
        List<RdmsJobItemProcess> rdmsJobItemProcesses = rdmsJobItemProcessMapper.selectByExample(rdmsJobItemProcessExample);
        List<RdmsJobItemProcessDto> rdmsJobItemProcessDtos = CopyUtil.copyList(rdmsJobItemProcesses, RdmsJobItemProcessDto.class);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(RdmsJobItemProcessDto jobItemProcessDto : rdmsJobItemProcessDtos){
            jobItemProcessDto.setCreateTimeStr(sdf.format(jobItemProcessDto.getCreateTime()));
        }
        PageInfo<RdmsJobItemProcessDto> pageInfo = new PageInfo<>(rdmsJobItemProcessDtos);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(rdmsJobItemProcessDtos);
    }


    /**
    * 根据工单号进行列表查询
    */
    public List<RdmsJobItemProcess> getJobProcessListByJobId(String jobId) {
        RdmsJobItemProcessExample rdmsJobItemProcessExample = new RdmsJobItemProcessExample();
        rdmsJobItemProcessExample.createCriteria().andJobItemIdEqualTo(jobId).andDeletedEqualTo(0);
        return rdmsJobItemProcessMapper.selectByExample(rdmsJobItemProcessExample);
    }

    /**
    */
    public String getJobitemProcessStatus(String jobitemId) {
        RdmsJobItemProcessExample rdmsJobItemProcessExample = new RdmsJobItemProcessExample();
        rdmsJobItemProcessExample.createCriteria().andJobItemIdEqualTo(jobitemId).andDeletedEqualTo(0);
        List<RdmsJobItemProcess> rdmsJobItemProcesses = rdmsJobItemProcessMapper.selectByExample(rdmsJobItemProcessExample);
        List<RdmsJobItemProcess> collectList = rdmsJobItemProcesses.stream().sorted(Comparator.comparing(RdmsJobItemProcess::getDeep).reversed()).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(collectList)){
            return collectList.get(0).getProcessStatus();
        }
        return null;
    }

    /**
    * 根据执行者进行分页列表查询
    */
    public RdmsJobItemProcess selectByPrimaryKey(String id) {
        return rdmsJobItemProcessMapper.selectByPrimaryKey(id);
    }

    /**
     * 保存
     */
    @Transactional
    public String save(RdmsJobItemProcess jobItemProcess) {
        if(jobItemProcess.getCreateTime()==null){
            jobItemProcess.setCreateTime(new Date());
        }
        jobItemProcess.setDeleted(0);
        String jobItemProcessId;
        if(ObjectUtils.isEmpty(jobItemProcess.getId())){
            jobItemProcessId = this.insert(jobItemProcess);
        }else{
            RdmsJobItemProcess rdmsJobItemProcess = rdmsJobItemProcessMapper.selectByPrimaryKey(jobItemProcess.getId());
            if(! ObjectUtils.isEmpty(rdmsJobItemProcess)){
                jobItemProcessId = this.update(jobItemProcess);
            }else{
                jobItemProcessId = this.insert(jobItemProcess);
            }
        }

        return jobItemProcessId;

    }

    /**
     * 提交
     */
    @Transactional
    public String submit(RdmsJobItemProcess jobItemProcess) {
        if(jobItemProcess.getCreateTime()==null){
            jobItemProcess.setCreateTime(new Date());
        }
        jobItemProcess.setDeleted(0);
        String jobItemProcessId;
        if(ObjectUtils.isEmpty(jobItemProcess.getId())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }else{
            RdmsJobItemProcess rdmsJobItemProcess = rdmsJobItemProcessMapper.selectByPrimaryKey(jobItemProcess.getId());
            if(! ObjectUtils.isEmpty(rdmsJobItemProcess)){
                //创建一条新的空过程记录
                //1. 查出项目经理,并将执行人赋值为项目经理ID
                RdmsJobItem jobItemById = rdmsJobItemsService.selectByPrimaryKey(jobItemProcess.getJobItemId());
                RdmsJobItemProcess jobItemProcessNew = new RdmsJobItemProcess();
                jobItemProcessNew.setJobItemId(jobItemProcess.getJobItemId());
                jobItemProcessNew.setExecutorId(jobItemProcess.getNextNode());  //下一节点的执行人, 是当前节点的nextNode
                //2. deep值加1
                jobItemProcessNew.setDeep(jobItemProcess.getDeep() + 1);
                jobItemProcessNew.setDeleted(0);
                jobItemProcessNew.setCreateTime(new Date());
                String jobItemProcessNewId = this.insert(jobItemProcessNew);

                //处理当前过程记录
                jobItemProcess.setNextNode(jobItemProcess.getNextNode());  //当前节点的nextNode, 是下一节点的执行人
                jobItemProcess.setActualSubmissionTime(new Date());
                jobItemProcessId = this.update(jobItemProcess);

                RdmsJobItem jobItem = rdmsJobItemsService.selectByPrimaryKey(jobItemProcess.getJobItemId());
                jobItem.setExecutorId(jobItemById.getProjectManagerId());
                jobItem.setNextNode(jobItemProcess.getNextNode());
                jobItem.setActualSubmissionTime(jobItemProcess.getActualSubmissionTime());
                rdmsJobItemsService.update(jobItem);
            }else{
                throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
            }
        }

        return jobItemProcessId;

    }
    /**
     * 根据特性编号,进行查询
     */
    public long getJobItemProcessCount(String jobItemId) {
        RdmsJobItemProcessExample rdmsJobItemProcessExample = new RdmsJobItemProcessExample();
        rdmsJobItemProcessExample.createCriteria().andJobItemIdEqualTo(jobItemId);
        return rdmsJobItemProcessMapper.countByExample(rdmsJobItemProcessExample);
    }

    /**
     * 新增
     */
    private String insert(RdmsJobItemProcess jobItemProcess) {
        if(ObjectUtils.isEmpty(jobItemProcess.getId())){  //当前端页面给出projectID时,将不为空
            jobItemProcess.setId(UuidUtil.getShortUuid());
        }
        jobItemProcess.setCreateTime(new Date());
        RdmsJobItemProcess rdmsJobItemProcess = rdmsJobItemProcessMapper.selectByPrimaryKey(jobItemProcess.getId());
        if(! ObjectUtils.isEmpty(rdmsJobItemProcess)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            long processCount = this.getJobItemProcessCount(jobItemProcess.getJobItemId());
            jobItemProcess.setDeep((int)processCount);
            rdmsJobItemProcessMapper.insert(jobItemProcess);
            return jobItemProcess.getId();
        }
    }

    /**
     * 更新
     */
    private String update(RdmsJobItemProcess jobItemProcess) {
        RdmsJobItemProcess rdmsJobItemProcess = rdmsJobItemProcessMapper.selectByPrimaryKey(jobItemProcess.getId());
        if(! ObjectUtils.isEmpty(rdmsJobItemProcess)){
            rdmsJobItemProcessMapper.updateByPrimaryKey(jobItemProcess);
            return jobItemProcess.getId();
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsJobItemProcess rdmsJobItemProcess = rdmsJobItemProcessMapper.selectByPrimaryKey(id);
        if(! ObjectUtils.isEmpty(rdmsJobItemProcess)){
            rdmsJobItemProcess.setDeleted(1);
            rdmsJobItemProcessMapper.updateByPrimaryKey(rdmsJobItemProcess);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

    /**
     * 列表查询
     */
    public RdmsJobItemProcess getJobItemProcessInfo(String itemId, String executorId) {
        RdmsJobItemProcessExample processExample = new RdmsJobItemProcessExample();
        processExample.createCriteria()
                .andJobItemIdEqualTo(itemId)
                .andExecutorIdEqualTo(executorId)
                .andDeletedEqualTo(0);
        List<RdmsJobItemProcess> rdmsJobItemProcesses = rdmsJobItemProcessMapper.selectByExample(processExample);
        List<RdmsJobItemProcess> collect = rdmsJobItemProcesses.stream().sorted(Comparator.comparing(RdmsJobItemProcess::getDeep).reversed()).collect(Collectors.toList());

        return collect.get(0);
    }

    /**
     * 列表查询
     */
    public List<RdmsJobItemProcessDto> getJobItemProcessInfoList(String jobItemId) {
        RdmsJobItemProcessExample processExample = new RdmsJobItemProcessExample();
        processExample.setOrderByClause("create_time desc");
        processExample.createCriteria()
                .andJobItemIdEqualTo(jobItemId)
                .andDeletedEqualTo(0);
        List<RdmsJobItemProcess> rdmsJobItemProcesses = rdmsJobItemProcessMapper.selectByExample(processExample);
        List<RdmsJobItemProcess> collect = rdmsJobItemProcesses.stream().sorted(Comparator.comparing(RdmsJobItemProcess::getDeep)).collect(Collectors.toList());
        List<RdmsJobItemProcessDto> rdmsJobItemProcessDtos = CopyUtil.copyList(collect, RdmsJobItemProcessDto.class);
        for(RdmsJobItemProcessDto itemProcess : rdmsJobItemProcessDtos){
            //执行人姓名
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(itemProcess.getExecutorId());
            if(!ObjectUtils.isEmpty(rdmsCustomerUser)){
                itemProcess.setExecutorName(rdmsCustomerUser.getTrueName());
            }else{
                itemProcess.setExecutorName("");
            }

            //下一节点执行人
            if(!ObjectUtils.isEmpty(itemProcess.getNextNode())){
                RdmsCustomerUser rdmsCustomerUser1 = rdmsCustomerUserService.selectByPrimaryKey(itemProcess.getNextNode());
                if(!ObjectUtils.isEmpty(rdmsCustomerUser1)){
                    itemProcess.setNextNodeExecutorName(rdmsCustomerUser1.getTrueName());
                }else{
                    itemProcess.setNextNodeExecutorName("");
                }
            }

            //附件列表_通过过程表的file_list_str进行查询
            List<RdmsFile> fileList = new ArrayList<>();
            if(itemProcess.getFileListStr() != null && itemProcess.getFileListStr().length()>6){
                List<String> stringList = JSONObject.parseArray(itemProcess.getFileListStr(), String.class);
                for(String fileId : stringList){
                    fileList.add(rdmsFileService.selectByPrimaryKey(fileId));
                }
                List<RdmsFileDto> fileDtos = CopyUtil.copyList(fileList, RdmsFileDto.class);
                itemProcess.setFileList(fileDtos);
            }

            if(itemProcess.getProcessStatus().equals(JobItemProcessStatusEnum.SUBMIT.getStatus())){
                List<RdmsCharacter> characterList = new ArrayList<>();
                List<String> characterIdList = JSON.parseArray(itemProcess.getCharacterIdListStr(), String.class);
                if(! CollectionUtils.isEmpty(characterIdList)){
                    for(String id: characterIdList){
                        RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(id);
                        characterList.add(rdmsCharacter);
                    }
                }
                List<RdmsCharacterDto> characterDtos = CopyUtil.copyList(characterList, RdmsCharacterDto.class);
                List<RdmsHmiCharacterPlainDto> rdmsHmiCharacterPlainDtos = characterDtos.stream().map(e -> new RdmsHmiCharacterPlainDto(e.getId(), e.getCharacterName())).collect(Collectors.toList());
                if(CollectionUtils.isEmpty(rdmsHmiCharacterPlainDtos)){
                    itemProcess.setCharacterPlainDtos(null);
                }else{
                    itemProcess.setCharacterPlainDtos(rdmsHmiCharacterPlainDtos);
                }
            }

            //计划提交时间\实际提交时间\创建时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            if(itemProcess.getActualSubmissionTime() != null){
                itemProcess.setActualSubmissionTimeStr(sdf.format(itemProcess.getActualSubmissionTime()));
            }
            if(itemProcess.getCreateTime() != null){
                itemProcess.setCreateTimeStr(sdf.format(itemProcess.getCreateTime()));
            }
        }
        List<RdmsJobItemProcessDto> processDtos = rdmsJobItemProcessDtos.stream().sorted(Comparator.comparing(RdmsJobItemProcessDto::getDeep).reversed()).collect(Collectors.toList());

        return processDtos;
    }
}
