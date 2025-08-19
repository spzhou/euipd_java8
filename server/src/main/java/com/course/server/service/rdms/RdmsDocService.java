/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.RdmsJobItem;
import com.course.server.domain.RdmsJobItemProperty;
import com.course.server.domain.RdmsJobitemAssociate;
import com.course.server.dto.rdms.RdmsJobItemDto;
import com.course.server.enums.rdms.JobItemStatusEnum;
import com.course.server.enums.rdms.JobItemTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class RdmsDocService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsDocService.class);

    @Resource
    private RdmsJobItemService rdmsJobItemService;
    @Resource
    private RdmsJobItemPropertyService rdmsJobItemPropertyService;
    @Resource
    private RdmsJobitemAssociateService rdmsJobitemAssociateService;

    /**
     * 获得当前工单的工单附件ID列表
     * @param jobitemId
     * @return
     */
    public List<String> getJobitemFileIdList(String jobitemId){
        RdmsJobItem rdmsJobItem = rdmsJobItemService.selectByPrimaryKey(jobitemId);
        return JSON.parseArray(rdmsJobItem.getFileListStr(), String.class);
    }

    /**
     * 获得当前工单property交付附件ID列表
     * @param jobitemId
     * @return
     */
    public List<String> getJobitemPropertyFileIdList(String jobitemId){
        List<RdmsJobItemProperty> jobItemPropertyByJobItemId = rdmsJobItemPropertyService.getJobItemPropertyByJobItemId(jobitemId);
        List<String> fileIdList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(jobItemPropertyByJobItemId)){
            List<String> stringList = jobItemPropertyByJobItemId.stream().map(RdmsJobItemProperty::getFileListStr).collect(Collectors.toList());
            for(String fileIdListStr : stringList){
                List<String> strings = JSON.parseArray(fileIdListStr, String.class);
                fileIdList.addAll(strings);
            }
        }
        HashSet<String> objects = new HashSet<>(fileIdList);
        List<String> list = new ArrayList<>(objects);
        return list;
    }

    /**
     * 获得当前工单的测试工单Id列表
     * @param jobitemId
     * @return
     */
    public List<String> getTestJobitemIdList(String jobitemId){
        List<JobItemStatusEnum> jobItemStatusList = new ArrayList<>();
        jobItemStatusList.add(JobItemStatusEnum.COMPLETED);
        jobItemStatusList.add(JobItemStatusEnum.ARCHIVED);
        List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
        jobitemTypeList.add(JobItemTypeEnum.TEST);
        List<RdmsJobItemDto> testJobItemList = rdmsJobItemService.getJobitemListByTestedJobiemIdStatusListTypeList(jobitemId, jobItemStatusList, jobitemTypeList);
        return testJobItemList.stream().map(RdmsJobItemDto::getId).collect(Collectors.toList());
    }

    /**
     * 获得当前工单的评审工单Id列表
     * @param jobitemId
     * @return
     */
    public List<String> getReviewJobitemIdList(String jobitemId){
        List<JobItemStatusEnum> jobItemStatusList = new ArrayList<>();
        jobItemStatusList.add(JobItemStatusEnum.COMPLETED);
        jobItemStatusList.add(JobItemStatusEnum.ARCHIVED);
        List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
        jobitemTypeList.add(JobItemTypeEnum.REVIEW);
        List<RdmsJobItemDto> testJobItemList = rdmsJobItemService.getJobitemListByTestedJobiemIdStatusListTypeList(jobitemId, jobItemStatusList, jobitemTypeList);
        return testJobItemList.stream().map(RdmsJobItemDto::getId).collect(Collectors.toList());
    }

    /**
     * 获得当前工单的联合COO评审工单Id列表
     * @param jobitemId
     * @return
     */
    public List<String> getReviewCooJobitemIdList(String jobitemId){
        List<JobItemStatusEnum> jobItemStatusList = new ArrayList<>();
        jobItemStatusList.add(JobItemStatusEnum.COMPLETED);
        jobItemStatusList.add(JobItemStatusEnum.ARCHIVED);
        List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
        jobitemTypeList.add(JobItemTypeEnum.REVIEW_COO);
        List<RdmsJobItemDto> testJobItemList = rdmsJobItemService.getJobitemListByTestedJobiemIdStatusListTypeList(jobitemId, jobItemStatusList, jobitemTypeList);
        return testJobItemList.stream().map(RdmsJobItemDto::getId).collect(Collectors.toList());
    }

    /**
     * 获得工单的BUG工单Id列表
     * @param testJobitemId
     * @return
     */
    public List<String> getBugJobitemIdList(String testJobitemId){
        List<JobItemStatusEnum> jobItemStatusList = new ArrayList<>();
        jobItemStatusList.add(JobItemStatusEnum.COMPLETED);
        jobItemStatusList.add(JobItemStatusEnum.ARCHIVED);
        List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
        jobitemTypeList.add(JobItemTypeEnum.BUG);
        List<RdmsJobItemDto> bugJobItemList = rdmsJobItemService.getJobitemListByParentAndStatusListTypeList(testJobitemId, jobItemStatusList, jobitemTypeList);
        return bugJobItemList.stream().map(RdmsJobItemDto::getId).collect(Collectors.toList());
    }

    /**
     * 获得工单的协作工单Id列表
     * @param jobitemId
     * @return
     */
    public List<String> getAssistJobitemIdList(String jobitemId){
        List<JobItemStatusEnum> jobItemStatusList = new ArrayList<>();
        jobItemStatusList.add(JobItemStatusEnum.COMPLETED);
        jobItemStatusList.add(JobItemStatusEnum.ARCHIVED);
        List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
        jobitemTypeList.add(JobItemTypeEnum.ASSIST);
        List<RdmsJobItemDto> assistJobItemList = rdmsJobItemService.getJobitemListByParentAndStatusListTypeList(jobitemId, jobItemStatusList, jobitemTypeList);
        return assistJobItemList.stream().map(RdmsJobItemDto::getId).collect(Collectors.toList());
    }

    /**
     * 获取一个工单的所有相关附件
     * @param jobitemId
     * @return
     */
    @Transactional
    public List<String> getJobitemAllRelatedDocIdList(String jobitemId) {
        //自身工单附件
        List<String> jobitemFileIdList = this.getJobitemFileIdList(jobitemId);
        List<String> fileIdList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(jobitemFileIdList)){
            fileIdList.addAll(jobitemFileIdList);
        }
        //自身交付附件
        List<String> jobitemPropertyFileIdList = this.getJobitemPropertyFileIdList(jobitemId);
        if(!CollectionUtils.isEmpty(jobitemPropertyFileIdList)){
            fileIdList.addAll(jobitemPropertyFileIdList);
        }

        //得到自身的测试工单附件列表
        List<String> testJobitemIdList = this.getTestJobitemIdList(jobitemId);
        if(!CollectionUtils.isEmpty(testJobitemIdList)){
            for(String testJobId: testJobitemIdList){
                List<String> jobitemAllRelatedDocIdList = this.getJobitemAllRelatedDocIdList(testJobId);
                fileIdList.addAll(jobitemAllRelatedDocIdList);
            }
        }
        //得到自身的BUG工单附件列表
        List<String> bugJobitemIdList = this.getBugJobitemIdList(jobitemId);
        if(!CollectionUtils.isEmpty(bugJobitemIdList)){
            for(String bugJobId: bugJobitemIdList){
                List<String> jobitemAllRelatedDocIdList = this.getJobitemAllRelatedDocIdList(bugJobId);
                fileIdList.addAll(jobitemAllRelatedDocIdList);
            }
        }
        //得到自身的ASSIST工单附件列表
        List<String> assistJobitemIdList = this.getAssistJobitemIdList(jobitemId);
        if(!CollectionUtils.isEmpty(assistJobitemIdList)){
            for(String assistJobId: assistJobitemIdList){
                List<String> jobitemAllRelatedDocIdList = this.getJobitemAllRelatedDocIdList(assistJobId);
                fileIdList.addAll(jobitemAllRelatedDocIdList);
            }
        }
        //得到自身的Review工单附件列表
        List<String> reviewJobitemIdList = this.getReviewJobitemIdList(jobitemId);
        if(!CollectionUtils.isEmpty(reviewJobitemIdList)){
            for(String reviewJobId: reviewJobitemIdList){
                List<String> jobitemAllRelatedDocIdList = this.getJobitemAllRelatedDocIdList(reviewJobId);
                fileIdList.addAll(jobitemAllRelatedDocIdList);
            }
        }

        //得到自身的ReviewCoo工单附件列表
        List<String> reviewCooJobitemIdList = this.getReviewCooJobitemIdList(jobitemId);
        if(!CollectionUtils.isEmpty(reviewCooJobitemIdList)){
            for(String reviewCooJobId: reviewCooJobitemIdList){
                List<String> jobitemAllRelatedDocIdList = this.getJobitemAllRelatedDocIdList(reviewCooJobId);
                fileIdList.addAll(jobitemAllRelatedDocIdList);
            }
        }

        //获得关联任务的附件
        List<String> associateTaskPropertyFileIdList = rdmsJobitemAssociateService.getAssociateTaskPropertyFileIdList(jobitemId);
        if(!CollectionUtils.isEmpty(associateTaskPropertyFileIdList)){
            fileIdList.addAll(associateTaskPropertyFileIdList);
        }
        //如果关联任务声明替换某个附件,则排除之
        List<RdmsJobitemAssociate> associateList = rdmsJobitemAssociateService.getJobitemAssociateList(jobitemId);
        if(!CollectionUtils.isEmpty(associateList)){
            for(RdmsJobitemAssociate associate: associateList){
                if(!ObjectUtils.isEmpty(associate.getReplaceFileIdsStr())){
                    List<String> fileIdList2= JSON.parseArray(associate.getReplaceFileIdsStr(), String.class);
                    if(!CollectionUtils.isEmpty(fileIdList2)){
                        for(String fileId: fileIdList2){
                            fileIdList.remove(fileId);
                        }
                    }
                }
            }
        }

        HashSet<String> objects = new HashSet<>(fileIdList);
        return new ArrayList<>(objects);
    }

}
