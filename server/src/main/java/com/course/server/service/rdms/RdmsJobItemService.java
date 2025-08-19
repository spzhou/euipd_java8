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
import com.course.server.domain.RdmsJobItemExample;
import com.course.server.dto.PageDto;

import com.course.server.dto.rdms.*;
import com.course.server.enums.FileGroupingEnum;
import com.course.server.enums.rdms.*;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsCharacterMapper;
import com.course.server.mapper.RdmsJobItemMapper;
import com.course.server.service.util.DateUtil;
import com.course.server.service.util.CodeUtil;
import com.course.server.util.CopyUtil;
import com.course.server.util.DeepCopyUtil;
import com.course.server.util.UuidUtil;
import com.course.server.util.ValidatorUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RdmsJobItemService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsJobItemService.class);

    @Resource
    private RdmsJobItemMapper rdmsJobItemsMapper;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private RdmsCustomerService rdmsCustomerService;
    @Resource
    private RdmsProjectService rdmsProjectService;
    @Resource
    private RdmsSubprojectService rdmsSubprojectService;
    @Resource
    private RdmsDocService rdmsDocService;
    @Resource
    private RdmsFileService rdmsFileService;
    @Resource
    private RdmsCharacterService rdmsCharacterService;
    @Resource
    private RdmsJobItemProcessService rdmsJobItemProcessService;
    @Resource
    private RdmsPreProjectService rdmsPreProjectService;
    @Resource
    private RdmsCharacterProcessService rdmsCharacterProcessService;
    @Resource
    private RdmsCharacterMapper rdmsCharacterMapper;
    @Resource
    private RdmsJobItemPropertyService rdmsJobItemPropertyService;
    @Resource
    private RdmsDemandService rdmsDemandService;
    @Resource
    private RdmsManhourService rdmsManhourService;
    @Resource
    private RdmsMaterialManageService rdmsMaterialManageService;
    @Resource
    private RdmsFeeManageService rdmsFeeManageService;
    @Resource
    private RdmsMilestoneService rdmsMilestoneService;
    @Resource
    private RdmsQualityService rdmsQualityService;
    @Resource
    private RdmsSmsInfoService rdmsSmsInfoService;
    @Resource
    private RdmsBudgetResearchExeService rdmsBudgetResearchExeService;
    @Resource
    private RdmsFileAuthService rdmsFileAuthService;
    @Resource
    private RdmsJobitemAssociateService rdmsJobItemAssociateService;
    @Autowired
    private RdmsBossService rdmsBossService;
    @Autowired
    private RdmsSysgmService rdmsSysgmService;
    @Autowired
    private RdmsGanttService rdmsGanttService;
    @Autowired
    private RdmsTgmService rdmsTgmService;
    @Autowired
    private RdmsBugFeedbackService rdmsBugFeedbackService;
    @Autowired
    private RdmsManhourDeclareService rdmsManhourDeclareService;
    @Autowired
    private RdmsCbbService rdmsCbbService;

    public List<RdmsJobItem> getMilestoneTaskJobList(String milestoneId, List<JobItemStatusEnum> statusEnumList) {
        if (!CollectionUtils.isEmpty(statusEnumList)) {
            for (JobItemStatusEnum statusEnum : statusEnumList) {
                if (!ObjectUtils.isEmpty(statusEnum.getStatus())) {
                    JobItemStatusEnum jobItemStatusEnumByStatus = JobItemStatusEnum.getJobItemStatusEnumByStatus(statusEnum.getStatus());
                    if (ObjectUtils.isEmpty(jobItemStatusEnumByStatus)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria = rdmsJobItemExample.createCriteria()
                .andDeletedEqualTo(0)
                .andNoteEqualTo(milestoneId)
                .andTypeEqualTo(JobItemTypeEnum.TASK_MILESTONE.getType());

        List<String> statusList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(statusEnumList)) {
            for (JobItemStatusEnum statusEnum : statusEnumList) {
                statusList.add(statusEnum.getStatus());
            }
            criteria.andStatusIn(statusList);
        }
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        return rdmsJobItems;
    }

    public List<RdmsJobItem> getSuggestOriginJobitem(String characterId) {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        RdmsJobItemExample.Criteria criteria = rdmsJobItemExample.createCriteria()
                .andDeletedEqualTo(0)
                .andCharacterIdEqualTo(characterId)
                .andTypeEqualTo(JobItemTypeEnum.SUGGEST.getType())
                .andProjectTypeEqualTo(ProjectTypeEnum.SUB_PROJECT.getType());
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        return rdmsJobItems;
    }

    @Transactional
    public String saveJobitem(RdmsJobItemDto jobItemsDto) throws ParseException {
        if(!ObjectUtils.isEmpty(jobItemsDto) && !ObjectUtils.isEmpty(jobItemsDto.getPlanSubmissionTime())){
            jobItemsDto.setPlanSubmissionTime(DateUtil.afternoon23_59pm(jobItemsDto.getPlanSubmissionTime()));
        }

        if (ObjectUtils.isEmpty(jobItemsDto.getLoginUserId())) {
            return null;
        }
        //添加创建人
        jobItemsDto.setCreaterId(jobItemsDto.getLoginUserId());

//        RdmsJobItemDto tempJobItemDto = CopyUtil.copy(jobItemsDto, RdmsJobItemDto.class);
        RdmsJobItemDto tempJobItemDto = DeepCopyUtil.deepCopy(jobItemsDto, RdmsJobItemDto.class);

        //对员工的工作负荷进行判断  ObjectUtils.isEmpty(jobItemsDto.getStatus())表示是手工下发的,不是从计划中自动下发的,自动下发时,这个值应该是 PLAN
        if (!ObjectUtils.isEmpty(tempJobItemDto.getExecutorId()) && ObjectUtils.isEmpty(tempJobItemDto.getStatus())) {
            List<RdmsHmiWorkLoadDto> customerUserWorkLoadData = rdmsCustomerUserService.getCustomerUserWorkLoadData(tempJobItemDto.getExecutorId(), tempJobItemDto);
            if (!CollectionUtils.isEmpty(customerUserWorkLoadData)) {
                double averageDayLoad = customerUserWorkLoadData.get(0).getAverageDayLoadOfWeek();
                if (averageDayLoad > 10) {
                    return OverLoadStatusEnum.OVERLOAD.getStatus();
                }
            }
        }

        //任务处理
        if (!ObjectUtils.isEmpty(jobItemsDto.getType())) {
            JobItemTypeEnum jobType = JobItemTypeEnum.getJobItemTypeEnumByType(jobItemsDto.getType());
            if (ObjectUtils.isEmpty(jobType)) {
                throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
            }
            ValidatorUtil.require(jobItemsDto.getCustomerId(), "客户ID");
            ValidatorUtil.require(jobItemsDto.getJobName(), "工作项名称");
            ValidatorUtil.require(jobItemsDto.getTaskDescription(), "任务描述");
            if (!(jobType.equals(JobItemTypeEnum.BUG)
                    || jobType.equals(JobItemTypeEnum.ASSIST)
                    || jobType.equals(JobItemTypeEnum.TEST)
                    || jobType.equals(JobItemTypeEnum.TASK_SUBP)
                    || jobType.equals(JobItemTypeEnum.TASK_MILESTONE)
                    || jobType.equals(JobItemTypeEnum.TASK_PRODUCT)
                    || jobType.equals(JobItemTypeEnum.TASK_FUNCTION)
                    || jobType.equals(JobItemTypeEnum.REVIEW_SUBP)
                    || jobType.equals(JobItemTypeEnum.MERGE)
                    || jobType.equals(JobItemTypeEnum.DEMAND)
                    || jobType.equals(JobItemTypeEnum.QUALITY)
                    || jobType.equals(JobItemTypeEnum.TASK_BUG)
                    || jobType.equals(JobItemTypeEnum.REVIEW_MILESTONE)
                    || jobType.equals(JobItemTypeEnum.REVIEW_MILESTONE_PRE)
            )) {
                if(!ObjectUtils.isEmpty(jobItemsDto.getFunctionType()) && jobItemsDto.getFunctionType().equals(JobItemTypeEnum.MERGE.getType())){
                    ValidatorUtil.require(jobItemsDto.getCharacterIdList(), "功能/特性Id");
                    String mainCharacterId = jobItemsDto.getCharacterIdList().get(0);
                    jobItemsDto.setCharacterId(mainCharacterId);
                    jobItemsDto.getCharacterIdList().remove(mainCharacterId);
                }else{
                    ValidatorUtil.require(jobItemsDto.getCharacterId(), "功能/特性Id");
                }

            }

            if (!(jobType.equals(JobItemTypeEnum.REVIEW)
                    || jobType.equals(JobItemTypeEnum.REVIEW_PRO)
                    || jobType.equals(JobItemTypeEnum.REVIEW_SUBP)
            )) {
                ValidatorUtil.require(jobItemsDto.getPlanSubmissionTime(), "计划提交时间");
                if (!(jobType.equals(JobItemTypeEnum.SUGGEST)
                        || jobType.equals(JobItemTypeEnum.SUGGEST_UPDATE))) {
                    ValidatorUtil.require(jobItemsDto.getExecutorId(), "执行人ID");
                }
                if (ObjectUtils.isEmpty(jobItemsDto.getManhour())) {
                    jobItemsDto.setManhour(0.0);
                }

                {//将工作人员工时,换算为标准工时
                    Double standManhour = rdmsManhourService.transformToStandManhour(jobItemsDto.getManhour(), jobItemsDto.getExecutorId(), jobItemsDto.getCustomerId(), OperateTypeEnum.DEVELOP);
                    jobItemsDto.setStandManhour(standManhour);
                    BigDecimal standManhourFee = rdmsManhourService.getStandManhourFee(jobItemsDto.getCustomerId(), OperateTypeEnum.DEVELOP);
                    jobItemsDto.setStandManhourFee(standManhourFee);  //标准工时
                }
            }
            ValidatorUtil.length(jobItemsDto.getJobName(), "工单名称", 2, 50);
            ValidatorUtil.length(jobItemsDto.getTaskDescription(), "任务描述", 4, 1000);

            switch (jobType) {
                case SUGGEST_UPDATE: {
                    suggetUpdateDealWith(jobItemsDto);
                    break;
                }
                case SUGGEST: {
                    suggestDealWith(jobItemsDto);
                    break;
                }
                case QUALITY: {
                    qualityDealWith(jobItemsDto);
                    break;
                }
                case TASK_BUG: {
                    bugfeedbackDealWith(jobItemsDto);
                    break;
                }
                case DEMAND: {
                    demandDealWith(jobItemsDto);
                    break;
                }
                case FUNCTION: {
                    ValidatorUtil.require(jobItemsDto.getPreProjectId(), "预立项项目ID");
                    ValidatorUtil.require(jobItemsDto.getJobName(), "任务名称ID");
                    ValidatorUtil.require(jobItemsDto.getCustomerId(), "机构ID");
                    ValidatorUtil.require(jobItemsDto.getCreaterId(), "创建者ID");
                    ValidatorUtil.require(jobItemsDto.getExecutorId(), "执行人ID");
//                    ValidatorUtil.require(jobItemsDto.getStandManhourFee(), "标准工时费");
                    ValidatorUtil.require(jobItemsDto.getType(), "任务类型");  //Funtion
                    ValidatorUtil.require(jobItemsDto.getManhour(), "计划工时");
                    ValidatorUtil.require(jobItemsDto.getTaskDescription(), "任务描述");
                    ValidatorUtil.require(jobItemsDto.getPlanSubmissionTime(), "计划提交时间");
                    ValidatorUtil.require(jobItemsDto.getLoginUserId(), "计划提交时间");
                    ValidatorUtil.require(jobItemsDto.getFunctionType(), "任务类型");  //迭代 分解 合并 归并
//                    ValidatorUtil.require(jobItemsDto.getProductManagerId(), "产品经理ID");

                    RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(jobItemsDto.getPreProjectId());
                    jobItemsDto.setProductManagerId(rdmsPreProject.getProductManagerId());
                    jobItemsDto.setCustomerId(rdmsPreProject.getCustomerId());
                    jobItemsDto.setProjectType(ProjectTypeEnum.PRE_PROJECT.getType());
                    //对计划提交时间进行处理_当日23:59为提交时间
                    jobItemsDto.setPlanSubmissionTime(DateUtil.afternoon23_59pm(jobItemsDto.getPlanSubmissionTime()));

                    RdmsJobItem jobItem = CopyUtil.copy(jobItemsDto, RdmsJobItem.class);
                    //将附件列表写入工单记录, 并创建工单
                    RdmsAttachmentDto attachmentDto = new RdmsAttachmentDto();
                    attachmentDto.setUse(FileGroupingEnum.JOBITEM.getCode());
                    attachmentDto.setItemId(jobItem.getId());
                    List<RdmsFileDto> fileDtos = rdmsFileService.listFileInfo(attachmentDto);
                    List<String> fileIds = fileDtos.stream().map(RdmsFileDto::getId).collect(Collectors.toList());
                    String fileIdsStr = JSONObject.toJSONString(fileIds);
                    jobItem.setFileListStr(fileIdsStr); //创建工单时, 页面上提供的附件,作为工单附件; 以后,在过程处理过程中提供的附件将记录为节点附件, 相应的file表中的 itemId为Process的id
                    //除以下两个字段外, 其他内容字段都需要前端给出
                    jobItem.setNextNode(jobItemsDto.getLoginUserId());
                    jobItem.setStatus(JobItemStatusEnum.HANDLING.getStatus());
                    {
                        jobItem.setManhour(jobItemsDto.getStandManhour());   //上面程序已经做好转换了
                        jobItem.setStandManhourFee(jobItemsDto.getStandManhourFee());   //上面程序已经做好转换了
                    }
                    String jobItemId = this.save(jobItem);
                    //填写文件的访问权限人员
                    if (!CollectionUtils.isEmpty(fileIds)) {
                        {
                            //设置文件授权 权限
                            RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                            roleUsersDto.setLoginUserId(jobItemsDto.getLoginUserId());
                            roleUsersDto.setExecutorId(jobItemsDto.getExecutorId());
                            roleUsersDto.setReceiverId(null);
                            RdmsJobItem rdmsJobItem = this.selectByPrimaryKey(jobItemId);
                            RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(rdmsJobItem.getCustomerId());
                            roleUsersDto.setBossId(rdmsBossDto.getBossId());
                            roleUsersDto.setSuperId(null);
                            roleUsersDto.setIpmtId(null);
                            roleUsersDto.setPjmId(null);
                            roleUsersDto.setPdmId(rdmsPreProject.getProductManagerId());
                            if (!ObjectUtils.isEmpty(rdmsPreProject)) {
                                roleUsersDto.setSmId(rdmsPreProject.getSystemManagerId());
                            }
                            rdmsFileAuthService.setFileAuthUser(fileIds, roleUsersDto);
                        }
                    }

                    //创建一个工单流程节点, 用户保存节点信息
                    RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                    jobItemProcess.setJobItemId(jobItemId);
                    jobItemProcess.setExecutorId(jobItemsDto.getLoginUserId());
                    jobItemProcess.setNextNode(jobItem.getExecutorId());
                    jobItemProcess.setJobDescription("请根据系统工程任务进行功能/特性定义");
                    jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.ISSUE.getStatus()); //工单下发
                    long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobItemId);
                    jobItemProcess.setDeep((int) jobItemProcessCount);
                    rdmsJobItemProcessService.save(jobItemProcess);

                    //保存后状态处理
                    //1. 将原记录拷贝一条新记录, jobItemType为function类型. parent记录为原功能记录
                    RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(jobItemsDto.getCharacterId());
                    rdmsCharacter.setAuxStatus(JobItemTypeEnum.FUNCTION.getType());  //标记这个功能经过了Function(系统工程)的处理.
                    rdmsCharacterService.updateByPrimaryKeySelective(rdmsCharacter);

                    RdmsCharacter copyCharacter = DeepCopyUtil.deepCopy(rdmsCharacter, RdmsCharacter.class);
                    copyCharacter.setId(null);
                    copyCharacter.setParent(rdmsCharacter.getId());
                    copyCharacter.setJobitemType(JobItemTypeEnum.FUNCTION.getType());
                    copyCharacter.setJobitemId(jobItemId);  //关联的任务
                    copyCharacter.setStatus(CharacterStatusEnum.UPDATED.getStatus());   //临时状态, 调用functionIteration或functionDecompose或functionMerge或被覆盖掉
                    copyCharacter.setAuxStatus(CharacterStatusEnum.NOTSET.getStatus()); //Copy记录去掉Function标记
                    String copyCharacterId = rdmsCharacterService.save(copyCharacter);
                    //为copy记录保存数据
                    RdmsCharacterData characterData = rdmsCharacterService.getCharacterData(jobItemsDto.getCharacterId());
                    characterData.setId(copyCharacterId);
                    rdmsCharacterService.saveCharacterData(characterData);

                    //处理系统工程任务的各种情况
                    if (jobItemsDto.getFunctionType().equals(JobItemTypeEnum.ITERATION.getType())) {
                        //修订
                        functionIteration(copyCharacterId);

                    } else if (jobItemsDto.getFunctionType().equals(JobItemTypeEnum.DECOMPOSE.getType())) {
                        //分解
                        functionDecompose(copyCharacterId);

                    } else if (jobItemsDto.getFunctionType().equals(JobItemTypeEnum.MERGE.getType())) {
                        //合并
                        if (!CollectionUtils.isEmpty(jobItemsDto.getCharacterIdList())) {
                            functionMerge(copyCharacterId, jobItemsDto);
                        }

                    } /*else if (jobItemsDto.getFunctionType().equals(JobItemTypeEnum.JOIN_TO.getType())) {
                        //归并
                        if (!CollectionUtils.isEmpty(jobItemsDto.getCharacterIdList())) {

                        }
                    }*/

                    break;
                }
                case DECOMPOSE: {
                    decomposeDealWith(jobItemsDto);
                    break;
                }
                case ITERATION: {
                    iterationJobDealwith(jobItemsDto);
                    break;
                }
                case MERGE: {
                    mergeDealWith(jobItemsDto);
                    break;
                }
                case UPDATE: {
                    ProjectTypeEnum projectType = ProjectTypeEnum.getProjectTypeEnumByType(jobItemsDto.getProjectType());
                    ValidatorUtil.require(jobItemsDto.getExecutorId(), "执行人ID");
                    ValidatorUtil.require(jobItemsDto.getCharacterId(), "功能/特性Id");
                    ValidatorUtil.require(jobItemsDto.getManhour(), "工时");
                    //获得原来的Character记录
                    RdmsCharacter currentOperatingCharacter = rdmsCharacterService.selectByPrimaryKey(jobItemsDto.getCharacterId());
                    if (ObjectUtils.isEmpty(currentOperatingCharacter)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }

                    if (Objects.requireNonNull(projectType) == ProjectTypeEnum.PRE_PROJECT) {
                        ValidatorUtil.require(jobItemsDto.getPreProjectId(), "预立项项目ID");
                        RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(jobItemsDto.getPreProjectId());
                        jobItemsDto.setProductManagerId(rdmsPreProject.getProductManagerId());

                        currentOperatingCharacter.setPreProjectId(rdmsPreProject.getId());
                    } else {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }

                    //对计划提交时间进行处理_当日17点为提交时间
                    jobItemsDto.setPlanSubmissionTime(DateUtil.afternoon23_59pm(jobItemsDto.getPlanSubmissionTime()));

                    //创建工单
                    RdmsJobItem jobItem = CopyUtil.copy(jobItemsDto, RdmsJobItem.class);
                    //将附件列表写入工单记录, 并创建工单
                    RdmsAttachmentDto attachmentDto = new RdmsAttachmentDto();
                    attachmentDto.setUse(FileGroupingEnum.JOBITEM.getCode());
                    attachmentDto.setItemId(jobItem.getId());
                    List<RdmsFileDto> fileDtos = rdmsFileService.listFileInfo(attachmentDto);
                    List<String> fileIds = fileDtos.stream().map(RdmsFileDto::getId).collect(Collectors.toList());
                    String fileIdsStr = JSONObject.toJSONString(fileIds);
                    jobItem.setFileListStr(fileIdsStr); //创建工单时, 页面上提供的附件,作为工单附件; 以后,在过程处理过程中提供的附件将记录为节点附件, 相应的file表中的 itemId为Process的id

                    jobItem.setProductManagerId(jobItemsDto.getProductManagerId());
//                    RdmsSysgmDto sysgmByCustomerId = rdmsSysgmService.getSysgmByCustomerId(jobItem.getCustomerId());
                    RdmsPreProject preProject = rdmsPreProjectService.selectByPrimaryKey(jobItemsDto.getPreProjectId());
                    if(!ObjectUtils.isEmpty(preProject)){
                        jobItem.setNextNode(preProject.getSystemManagerId());
                    }
                    jobItem.setStatus(JobItemStatusEnum.HANDLING.getStatus());
                    {
                        jobItem.setManhour(jobItemsDto.getStandManhour());
                        jobItem.setStandManhourFee(jobItemsDto.getStandManhourFee());
                    }
                    jobItem.setType(JobItemTypeEnum.UPDATE.getType());
                    jobItem.setProjectType(jobItemsDto.getProjectType());
                    String jobItemId = this.save(jobItem);
                    //填写文件的访问权限人员
                    if (!CollectionUtils.isEmpty(fileIds)) {
                        {
                            //设置文件授权 权限
                            RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                            roleUsersDto.setLoginUserId(jobItemsDto.getLoginUserId());
                            roleUsersDto.setExecutorId(jobItemsDto.getExecutorId());
                            roleUsersDto.setReceiverId(null);
                            RdmsJobItem rdmsJobItem = this.selectByPrimaryKey(jobItemId);
                            RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(rdmsJobItem.getCustomerId());
                            roleUsersDto.setBossId(rdmsBossDto.getBossId());
                            roleUsersDto.setSuperId(null);
                            roleUsersDto.setIpmtId(null);
                            roleUsersDto.setPjmId(null);
                            roleUsersDto.setPdmId(jobItemsDto.getProductManagerId());
                            if(!ObjectUtils.isEmpty(jobItemsDto.getPreProjectId())){
                                RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(jobItemsDto.getPreProjectId());
                                if (!ObjectUtils.isEmpty(rdmsPreProject)) {
                                    roleUsersDto.setSmId(rdmsPreProject.getSystemManagerId());
                                }
                            }
                            rdmsFileAuthService.setFileAuthUser(fileIds, roleUsersDto);
                        }
                    }

                    {
                        //创建一个工单流程节点, 用户保存节点信息
                        RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                        jobItemProcess.setJobItemId(jobItemId);
                        jobItemProcess.setExecutorId(jobItemsDto.getLoginUserId());
                        jobItemProcess.setNextNode(jobItemsDto.getExecutorId());
                        jobItemProcess.setJobDescription("请完成功能修订工单任务");
                        jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.ISSUE.getStatus()); //工单下发
                        long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobItemId);
                        jobItemProcess.setDeep((int) jobItemProcessCount);
                        String JobProcessId = rdmsJobItemProcessService.save(jobItemProcess);
                    }

                    //处理原记录
                    currentOperatingCharacter.setStatus(CharacterStatusEnum.SAVED.getStatus());  //修订过程中, 处于编辑状态的记录是save状态; 被修订的记录才是integration
                    currentOperatingCharacter.setProductManagerId(jobItemsDto.getProductManagerId());
                    currentOperatingCharacter.setNextNode(jobItemsDto.getProductManagerId());
                    currentOperatingCharacter.setProjectType(ProjectTypeEnum.PRE_PROJECT.getType());
                    currentOperatingCharacter.setJobitemId(jobItemId);  //将功能的创建\修订权交给工单
                    currentOperatingCharacter.setProjectId(null);
                    currentOperatingCharacter.setSubprojectId(null);
                    String originCharacterId = rdmsCharacterService.update(currentOperatingCharacter);
                    //为原记录创建Process记录
                    {
                        RdmsCharacterProcess originProcess = new RdmsCharacterProcess();
                        originProcess.setCharacterId(originCharacterId);
                        originProcess.setExecutorId(jobItemsDto.getExecutorId());
                        originProcess.setNextNode(currentOperatingCharacter.getNextNode());
                        long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(originCharacterId);
                        originProcess.setDeep((int) characterProcessCount);
                        originProcess.setJobDescription("标记为修订状态");
                        originProcess.setProcessStatus(CharacterProcessStatusEnum.EDITING.getStatus());
                        rdmsCharacterProcessService.save(originProcess);
                    }

                    //对产品经理的工单进行处理
                    List<RdmsJobItemDto> suggestUpdateJobitem = this.getSuggestUpdateJobitem(originCharacterId);
                    if (!CollectionUtils.isEmpty(suggestUpdateJobitem)) {
                        for (RdmsJobItemDto jobItemDto : suggestUpdateJobitem) {
                            jobItemDto.setStatus(JobItemStatusEnum.COMPLETED.getStatus());
                            String originJobItemId = this.update(jobItemDto);

                            //创建一个工单流程节点, 用户保存节点信息
                            RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                            jobItemProcess.setJobItemId(originJobItemId);
                            jobItemProcess.setExecutorId(jobItemsDto.getLoginUserId());
                            jobItemProcess.setNextNode(jobItemDto.getExecutorId());
                            jobItemProcess.setJobDescription("完成对功能/组件开发建议的处理");
                            jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.COMPLETE.getStatus()); //工单下发
                            long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(originJobItemId);
                            jobItemProcess.setDeep((int) jobItemProcessCount);
                            String JobProcessId = rdmsJobItemProcessService.save(jobItemProcess);
                        }
                    }

                    break;
                }
                case DEVELOP: {
                    ProjectTypeEnum projectType = ProjectTypeEnum.getProjectTypeEnumByType(jobItemsDto.getProjectType());
                    ValidatorUtil.require(jobItemsDto.getExecutorId(), "执行人ID");
                    switch (projectType) {
                        case SUB_PROJECT: {
                            ValidatorUtil.require(jobItemsDto.getCharacterId(), "功能/特性ID");
                            ValidatorUtil.require(jobItemsDto.getManhour(), "工时");
                            break;
                        }
                        default: {
                            throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                        }
                    }
                    //对计划提交时间进行处理_当日17点为提交时间
                    jobItemsDto.setPlanSubmissionTime(DateUtil.afternoon23_59pm(jobItemsDto.getPlanSubmissionTime()));

                    //创建工单
                    RdmsJobItem jobItem = CopyUtil.copy(jobItemsDto, RdmsJobItem.class);
                    //将附件列表写入工单记录, 并创建工单
                    RdmsAttachmentDto attachmentDto = new RdmsAttachmentDto();
                    attachmentDto.setUse(FileGroupingEnum.JOBITEM.getCode());
                    attachmentDto.setItemId(jobItem.getId());
                    List<RdmsFileDto> fileDtos = rdmsFileService.listFileInfo(attachmentDto);
                    List<String> fileIds = fileDtos.stream().map(RdmsFileDto::getId).collect(Collectors.toList());
                    String fileIdsStr = JSONObject.toJSONString(fileIds);
                    jobItem.setFileListStr(fileIdsStr); //创建工单时, 页面上提供的附件,作为工单附件; 以后,在过程处理过程中提供的附件将记录为节点附件, 相应的file表中的 itemId为Process的id

                    jobItem.setNextNode(jobItemsDto.getLoginUserId());
                    jobItem.setStatus(JobItemStatusEnum.HANDLING.getStatus());
                    {
                        jobItem.setManhour(jobItemsDto.getStandManhour());
                        jobItem.setStandManhourFee(jobItemsDto.getStandManhourFee());
                    }
                    jobItem.setProjectType(jobItemsDto.getProjectType());

                    RdmsCharacter character = rdmsCharacterService.selectByPrimaryKey(jobItemsDto.getCharacterId());
                    jobItem.setProjectId(character.getProjectId());
                    jobItem.setSubprojectId(character.getSubprojectId());
                    jobItem.setProductManagerId(character.getProductManagerId());
                    jobItem.setProjectManagerId(jobItemsDto.getLoginUserId());

                    String jobItemId = this.save(jobItem);

                    {
                        RdmsGantt gantt = new RdmsGantt();
                        gantt.setId(jobItem.getId());  //gantt的Id是任务Id
                        if(!ObjectUtils.isEmpty(jobItem.getCharacterId())){
                            gantt.setParent(jobItem.getCharacterId());
                        }else{
                            gantt.setParent(null);
                        }
                        gantt.setJobitemId(jobItem.getId());
                        gantt.setCharacterId(jobItem.getCharacterId());
                        gantt.setSubprojectId(jobItem.getSubprojectId());
                        gantt.setProjectId(jobItem.getProjectId());
                        gantt.setPreprojectId(jobItem.getPreProjectId());
                        gantt.setCustomerId(jobItem.getCustomerId());
                        gantt.setCreaterId(jobItem.getCreaterId());
                        gantt.setExecutorId(jobItem.getExecutorId());
                        gantt.setText(jobItem.getJobName());
                        gantt.setStartDate(jobItem.getCreateTime());
                        //用计划提交的日期减去创建的日期
                        if(!ObjectUtils.isEmpty(jobItem.getPlanSubmissionTime())){
                            gantt.setDuration((int) rdmsGanttService.calculateDuration(jobItem.getCreateTime(), jobItem.getPlanSubmissionTime()));
                        }else{
                            gantt.setDuration(0);
                        }
                        gantt.setManhour(jobItem.getManhour());
                        gantt.setProgress(0.0);
                        gantt.setStatus("going");
                        gantt.setType("task");
                        gantt.setOpen(0);
                        gantt.setDeleted(0);

                        if (gantt.getDuration().doubleValue() > 0) {
                            rdmsGanttService.save(gantt);
                        }
                    }

                    //填写文件的访问权限人员
                    if (!CollectionUtils.isEmpty(fileIds)) {
                        {
                            //设置文件授权 权限
                            RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                            roleUsersDto.setLoginUserId(jobItemsDto.getLoginUserId());
                            roleUsersDto.setExecutorId(jobItemsDto.getExecutorId());
                            roleUsersDto.setReceiverId(null);
                            RdmsJobItem rdmsJobItem = this.selectByPrimaryKey(jobItemId);
                            RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(rdmsJobItem.getCustomerId());
                            roleUsersDto.setBossId(rdmsBossDto.getBossId());
                            roleUsersDto.setSuperId(null);
                            roleUsersDto.setIpmtId(null);
                            if(!ObjectUtils.isEmpty(jobItemsDto.getProjectId())){
                                RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(rdmsJobItem.getProjectId());
                                if(!ObjectUtils.isEmpty(rdmsProject)){
                                    roleUsersDto.setMainPjmId(rdmsProject.getProjectManagerId());
                                }
                            }
                            roleUsersDto.setPjmId(jobItemsDto.getProjectManagerId());
                            roleUsersDto.setPdmId(null);
                            rdmsFileAuthService.setFileAuthUser(fileIds, roleUsersDto);
                        }
                    }

                    //创建一个工单流程节点, 用户保存节点信息
                    RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                    jobItemProcess.setJobItemId(jobItemId);
                    jobItemProcess.setExecutorId(jobItemsDto.getLoginUserId());
                    jobItemProcess.setNextNode(jobItemsDto.getExecutorId());
                    jobItemProcess.setJobDescription("功能开发工单");
                    jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.ISSUE.getStatus()); //工单下发
                    long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobItemId);
                    jobItemProcess.setDeep((int) jobItemProcessCount);
                    String JobProcessId = rdmsJobItemProcessService.save(jobItemProcess);

                    //授权对应的组件定义文件访问权限
                    //填写文件的访问权限人员
                    if (!ObjectUtils.isEmpty(character.getFileListStr())) {
                        List<String> fileIdList = JSON.parseArray(character.getFileListStr(), String.class);
                        if (!CollectionUtils.isEmpty(fileIdList)) {
                            {
                                //设置文件授权 权限
                                RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                                roleUsersDto.setLoginUserId(null);
                                roleUsersDto.setExecutorId(null);
                                roleUsersDto.setReceiverId(null);
                                RdmsJobItem rdmsJobItem = this.selectByPrimaryKey(jobItemId);
                                RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(rdmsJobItem.getCustomerId());
                                roleUsersDto.setBossId(rdmsBossDto.getBossId());
                                roleUsersDto.setSuperId(null);
                                roleUsersDto.setIpmtId(null);
                                roleUsersDto.setPjmId(null);
                                if(!ObjectUtils.isEmpty(jobItemsDto.getProjectId())){
                                    RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(rdmsJobItem.getProjectId());
                                    if(!ObjectUtils.isEmpty(rdmsProject)){
                                        roleUsersDto.setMainPjmId(rdmsProject.getProjectManagerId());
                                    }
                                }
                                roleUsersDto.setPdmId(jobItemsDto.getProductManagerId());
                                if(!ObjectUtils.isEmpty(jobItemsDto.getPreProjectId())){
                                    RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(jobItemsDto.getPreProjectId());
                                    if (!ObjectUtils.isEmpty(rdmsPreProject)) {
                                        roleUsersDto.setSmId(rdmsPreProject.getSystemManagerId());
                                    }
                                }
                                rdmsFileAuthService.setFileAuthUser(fileIds, roleUsersDto);
                            }
                        }
                    }

                    break;
                }
                case TASK:
                case TASK_SUBP:
                case TASK_PRODUCT:
                case TASK_FUNCTION:
                case TASK_MILESTONE:
                case TASK_TEST:
                case TASK_PRO: {
                    ProjectTypeEnum projectType = ProjectTypeEnum.getProjectTypeEnumByType(jobItemsDto.getProjectType());
                    ValidatorUtil.require(jobItemsDto.getExecutorId(), "执行人ID");
                    switch (Objects.requireNonNull(projectType)) {
                        case PROJECT: {
                            ValidatorUtil.require(jobItemsDto.getProjectId(), "项目ID");
                            ValidatorUtil.require(jobItemsDto.getManhour(), "工时");

                            jobItemsDto.setSubprojectId(jobItemsDto.getProjectId()); //为了协作\测试等任务能够使用子项目方式进行处理
                            break;
                        }
                        case SUB_PROJECT: {
                            ValidatorUtil.require(jobItemsDto.getSubprojectId(), "子项目ID");
                            ValidatorUtil.require(jobItemsDto.getManhour(), "工时");

                            RdmsProjectSubprojectDto subproject = rdmsProjectService.getSubprojectBySubprojectId(jobItemsDto.getSubprojectId());
                            if(!ObjectUtils.isEmpty(subproject)){
                                jobItemsDto.setProjectId(subproject.getProjectId());
                            }
                            break;
                        }
                        case PRE_PROJECT: {
                            ValidatorUtil.require(jobItemsDto.getPreProjectId(), "预立项ID");
                            ValidatorUtil.require(jobItemsDto.getManhour(), "工时");

                            jobItemsDto.setPreProjectId(jobItemsDto.getPreProjectId());
                            break;
                        }
                        default: {
                            throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                        }
                    }
                    //对计划提交时间进行处理_当日17点为提交时间
                    jobItemsDto.setPlanSubmissionTime(DateUtil.afternoon23_59pm(jobItemsDto.getPlanSubmissionTime()));

                    //创建工单
                    RdmsJobItem jobItem = CopyUtil.copy(jobItemsDto, RdmsJobItem.class);
                    //将附件列表写入工单记录, 并创建工单
                    RdmsAttachmentDto attachmentDto = new RdmsAttachmentDto();
                    attachmentDto.setUse(FileGroupingEnum.JOBITEM.getCode());
                    attachmentDto.setItemId(jobItem.getId());
                    List<RdmsFileDto> fileDtos = rdmsFileService.listFileInfo(attachmentDto);
                    List<String> fileIds = new ArrayList<>();
                    if (!CollectionUtils.isEmpty(fileDtos)) {
                        fileIds = fileDtos.stream().map(RdmsFileDto::getId).collect(Collectors.toList());
                        String fileIdsStr = JSONObject.toJSONString(fileIds);
                        jobItem.setFileListStr(fileIdsStr); //创建工单时, 页面上提供的附件,作为工单附件; 以后,在过程处理过程中提供的附件将记录为节点附件, 相应的file表中的 itemId为Process的id
                    }

                    jobItem.setNextNode(jobItemsDto.getLoginUserId());  //submit的时候,回到"我"这里
                    jobItem.setStatus(JobItemStatusEnum.HANDLING.getStatus());
                    {
                        jobItem.setManhour(jobItemsDto.getStandManhour());
                        jobItem.setStandManhourFee(jobItemsDto.getStandManhourFee());
                    }
                    jobItem.setProjectType(jobItemsDto.getProjectType());
                    jobItem.setProjectId(jobItemsDto.getProjectId());
                    jobItem.setSubprojectId(jobItemsDto.getSubprojectId());
                    jobItem.setPreProjectId(jobItemsDto.getPreProjectId());
                    jobItem.setProductManagerId(jobItemsDto.getProductManagerId());

                    if(!ObjectUtils.isEmpty(jobItemsDto.getSubprojectId())){
                        RdmsProjectSubproject rdmsProjectSubproject = rdmsSubprojectService.selectByPrimaryKey(jobItemsDto.getSubprojectId());
                        if(!ObjectUtils.isEmpty(rdmsProjectSubproject)){
                            jobItem.setProjectManagerId(rdmsProjectSubproject.getProjectManagerId());
                            jobItem.setTestManagerId(rdmsProjectSubproject.getTestManagerId());
                        }else{
                            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(jobItemsDto.getProjectId());
                            if(!ObjectUtils.isEmpty(rdmsProject)){
                                jobItem.setProjectManagerId(rdmsProject.getProjectManagerId());
                                jobItem.setTestManagerId(rdmsProject.getTestManagerId());
                            }
                        }
                    }else if(!ObjectUtils.isEmpty(jobItemsDto.getProjectId())){
                        RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(jobItemsDto.getProjectId());
                        jobItem.setProjectManagerId(rdmsProject.getProjectManagerId());
                        jobItem.setTestManagerId(rdmsProject.getTestManagerId());
                    }

                    String jobItemId = this.save(jobItem);

                    if (jobItem.getType().equals(JobItemTypeEnum.TASK_SUBP.getType())) {
                        RdmsGantt gantt = new RdmsGantt();
                        gantt.setId(jobItem.getId());  //gantt的Id是任务Id
                        if(!ObjectUtils.isEmpty(jobItem.getSubprojectId())){
                            gantt.setParent(jobItem.getSubprojectId());
                        }else{
                            gantt.setParent(null);
                        }
                        gantt.setJobitemId(jobItem.getId());
                        gantt.setCharacterId(jobItem.getCharacterId());
                        gantt.setSubprojectId(jobItem.getSubprojectId());
                        gantt.setProjectId(jobItem.getProjectId());
                        gantt.setPreprojectId(jobItem.getPreProjectId());
                        gantt.setCustomerId(jobItem.getCustomerId());
                        gantt.setCreaterId(jobItem.getCreaterId());
                        gantt.setExecutorId(jobItem.getExecutorId());
                        gantt.setText(jobItem.getJobName());
                        gantt.setStartDate(jobItem.getCreateTime());
                        //用计划提交的日期减去创建的日期
                        if(!ObjectUtils.isEmpty(jobItem.getPlanSubmissionTime())){
                            gantt.setDuration((int) rdmsGanttService.calculateDuration(jobItem.getCreateTime(), jobItem.getPlanSubmissionTime()));
                        }else{
                            gantt.setDuration(0);
                        }
                        gantt.setManhour(jobItem.getManhour());
                        gantt.setProgress(0.0);
                        gantt.setStatus("going");
                        gantt.setType("task");
                        gantt.setOpen(0);
                        gantt.setDeleted(0);

                        if (gantt.getDuration().doubleValue() > 0) {
                            rdmsGanttService.save(gantt);
                        }
                    }

                    //填写文件的访问权限人员
                    if (!CollectionUtils.isEmpty(fileIds)) {
                        {
                            //设置文件授权 权限
                            RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                            roleUsersDto.setLoginUserId(jobItemsDto.getLoginUserId());
                            roleUsersDto.setExecutorId(jobItemsDto.getExecutorId());
                            roleUsersDto.setReceiverId(null);
                            RdmsJobItem rdmsJobItem = this.selectByPrimaryKey(jobItemId);
                            RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(rdmsJobItem.getCustomerId());
                            roleUsersDto.setBossId(rdmsBossDto.getBossId());
                            roleUsersDto.setSuperId(null);
                            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(jobItem.getProjectId());
                            if (!ObjectUtils.isEmpty(rdmsProject)) {
                                roleUsersDto.setIpmtId(rdmsProject.getSupervise());
                            }

                            if (jobItemsDto.getType().equals(JobItemTypeEnum.TASK_PRODUCT.getType())) {
                                if (!ObjectUtils.isEmpty(jobItemsDto.getProductManagerId())) {
                                    roleUsersDto.setPdmId(jobItemsDto.getProductManagerId());
                                }
                                RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(jobItem.getPreProjectId());
                                if (!ObjectUtils.isEmpty(rdmsPreProject)) {
                                    roleUsersDto.setSmId(rdmsPreProject.getSystemManagerId());
                                }
                            } else {
                                if (!ObjectUtils.isEmpty(jobItemsDto.getProjectManagerId())) {
                                    roleUsersDto.setPjmId(jobItemsDto.getProjectManagerId());
                                }
                            }
                            if(!ObjectUtils.isEmpty(jobItemsDto.getProjectId())){
                                if(!ObjectUtils.isEmpty(rdmsProject)){
                                    roleUsersDto.setMainPjmId(rdmsProject.getProjectManagerId());
                                }
                            }
                            rdmsFileAuthService.setFileAuthUser(fileIds, roleUsersDto);
                        }
                    }

                    //创建一个工单流程节点, 用户保存节点信息
                    RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                    jobItemProcess.setJobItemId(jobItemId);
                    jobItemProcess.setExecutorId(jobItemsDto.getLoginUserId());
                    jobItemProcess.setNextNode(jobItemsDto.getExecutorId());
                    jobItemProcess.setJobDescription("请完成指定工作任务");
                    jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.ISSUE.getStatus()); //工单下发
                    long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobItemId);
                    jobItemProcess.setDeep((int) jobItemProcessCount);
                    rdmsJobItemProcessService.save(jobItemProcess);

                    //查看是否有关联任务设置
                    if (!ObjectUtils.isEmpty(jobItemsDto.getAssociatedJobitemId())) {
                        RdmsJobitemAssociate associate = new RdmsJobitemAssociate();
                        associate.setJobitemId(jobItemId);
                        associate.setAssociateId(jobItemsDto.getAssociatedJobitemId());
                        if (!ObjectUtils.isEmpty(jobItemsDto.getReplaceFileIdsStr())) {
                            associate.setReplaceFileIdsStr(jobItemsDto.getReplaceFileIdsStr());
                        }
                        rdmsJobItemAssociateService.save(associate);
                    }

                    break;
                }
                case TEST: {
                    ProjectTypeEnum projectType = ProjectTypeEnum.getProjectTypeEnumByType(jobItemsDto.getProjectType());
                    ValidatorUtil.require(jobItemsDto.getExecutorId(), "执行人ID");
                    if (Objects.requireNonNull(projectType) == ProjectTypeEnum.SUB_PROJECT) {
                        ValidatorUtil.require(jobItemsDto.getProjectId(), "项目ID");
                        ValidatorUtil.require(jobItemsDto.getSubprojectId(), "子项目ID");
                        ValidatorUtil.require(jobItemsDto.getCustomerId(), "客户ID");
                        ValidatorUtil.require(jobItemsDto.getTestedJobitemId(), "被测试工单ID");
                        ValidatorUtil.require(jobItemsDto.getManhour(), "工时");
                    } else if (Objects.requireNonNull(projectType) == ProjectTypeEnum.PROJECT) {
                        ValidatorUtil.require(jobItemsDto.getProjectId(), "项目ID");
                        ValidatorUtil.require(jobItemsDto.getCustomerId(), "客户ID");
                        ValidatorUtil.require(jobItemsDto.getTestedJobitemId(), "被测试工单ID");
                        ValidatorUtil.require(jobItemsDto.getManhour(), "工时");

                        jobItemsDto.setSubprojectId(jobItemsDto.getProjectId());
                    } else if (Objects.requireNonNull(projectType) == ProjectTypeEnum.PRE_PROJECT) {
                        ValidatorUtil.require(jobItemsDto.getPreProjectId(), "预立项项目ID");
                        ValidatorUtil.require(jobItemsDto.getCustomerId(), "客户ID");
                        ValidatorUtil.require(jobItemsDto.getTestedJobitemId(), "被测试工单ID");
                        ValidatorUtil.require(jobItemsDto.getManhour(), "工时");
                    } else {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }

                    //对计划提交时间进行处理_当日17点为提交时间
                    jobItemsDto.setPlanSubmissionTime(DateUtil.afternoon23_59pm(jobItemsDto.getPlanSubmissionTime()));

                    //创建工单
                    RdmsJobItem jobItem = CopyUtil.copy(jobItemsDto, RdmsJobItem.class);
                    {
                        //将附件列表写入工单记录, 并创建工单
                        RdmsAttachmentDto attachmentDto = new RdmsAttachmentDto();
                        attachmentDto.setUse(FileGroupingEnum.JOBITEM.getCode());
                        attachmentDto.setItemId(jobItem.getId());
                        List<RdmsFileDto> fileDtos = rdmsFileService.listFileInfo(attachmentDto);
                        List<String> fileIds = fileDtos.stream().map(RdmsFileDto::getId).collect(Collectors.toList());
                        //将勾选的附件加入附件清单
                        if (!CollectionUtils.isEmpty(jobItemsDto.getCheckedFileIdList())) {
                            fileIds.addAll(jobItemsDto.getCheckedFileIdList());
                        }
                        String fileIdsStr = JSONObject.toJSONString(fileIds);
                        jobItem.setFileListStr(fileIdsStr); //创建工单时, 页面上提供的附件,作为工单附件; 以后,在过程处理过程中提供的附件将记录为节点附件, 相应的file表中的 itemId为Process的id

                        if (Objects.requireNonNull(projectType) == ProjectTypeEnum.SUB_PROJECT) {
                            RdmsProjectSubprojectDto subproject = rdmsProjectService.getSubprojectBySubprojectId(jobItemsDto.getSubprojectId());
                            jobItem.setTestManagerId(subproject.getTestManagerId());  //测试主管
                            jobItem.setNextNode(subproject.getTestManagerId());  //测试主管
                            jobItem.setStatus(JobItemStatusEnum.HANDLING.getStatus());
                            {
                                //将工作人员工时,换算为标准工时
                                Double standManhour = rdmsManhourService.transformToStandManhour(jobItemsDto.getManhour(), jobItemsDto.getExecutorId(), jobItemsDto.getCustomerId(), OperateTypeEnum.TEST);
                                jobItem.setManhour(standManhour);
                                BigDecimal standManhourFee = rdmsManhourService.getStandManhourFee(jobItemsDto.getCustomerId(), OperateTypeEnum.TEST);
                                jobItem.setStandManhourFee(standManhourFee);  //标准工时
                            }
                            jobItem.setProjectType(jobItemsDto.getProjectType());
                            jobItem.setProjectManagerId(subproject.getProjectManagerId());
                            if (jobItem.getAuxType().equals(JobItemTypeEnum.CHARACTER_INT.getType())
                                    || jobItem.getAuxType().equals(JobItemTypeEnum.TASK_SUBP.getType())
                                    || jobItem.getAuxType().equals(JobItemTypeEnum.TASK.getType())
                                    || jobItem.getAuxType().equals(JobItemTypeEnum.QUALITY.getType())
                            ) {
                                RdmsJobItem testedJobItem = this.selectByPrimaryKey(jobItem.getTestedJobitemId());
                                jobItem.setParentJobitemId(testedJobItem.getParentJobitemId());

                                RdmsJobItem rdmsJobItem = this.selectByPrimaryKey(jobItem.getParentJobitemId());
                                if (!ObjectUtils.isEmpty(rdmsJobItem)) {
                                    if (rdmsJobItem.getType().equals(JobItemTypeEnum.QUALITY.getType())) {
                                        jobItem.setQualityId(rdmsJobItem.getQualityId());
                                    }
                                }
                            }
                        } else if (Objects.requireNonNull(projectType) == ProjectTypeEnum.PROJECT) {
                            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(jobItemsDto.getProjectId());
                            if (!ObjectUtils.isEmpty(rdmsProject)) {
                                jobItem.setTestManagerId(rdmsProject.getTestManagerId());  //测试主管
                                jobItem.setNextNode(rdmsProject.getTestManagerId());  //测试主管
                            }
                            jobItem.setStatus(JobItemStatusEnum.HANDLING.getStatus());
                            {
                                //将工作人员工时,换算为标准工时
                                Double standManhour = rdmsManhourService.transformToStandManhour(jobItemsDto.getManhour(), jobItemsDto.getExecutorId(), jobItemsDto.getCustomerId(), OperateTypeEnum.TEST);
                                jobItem.setManhour(standManhour);
                                BigDecimal standManhourFee = rdmsManhourService.getStandManhourFee(jobItemsDto.getCustomerId(), OperateTypeEnum.TEST);
                                jobItem.setStandManhourFee(standManhourFee);  //标准工时
                            }
                            jobItem.setProjectType(jobItemsDto.getProjectType());
                            jobItem.setProductManagerId(rdmsProject.getProductManagerId());
                            if (jobItem.getAuxType().equals(JobItemTypeEnum.TASK_PRODUCT.getType())) {
                                RdmsJobItem testedJobItem = this.selectByPrimaryKey(jobItem.getTestedJobitemId());
                                jobItem.setParentJobitemId(testedJobItem.getParentJobitemId());  //如果是协作工单的测试, parent是被协作的原始工单

                                RdmsJobItem rdmsJobItem = this.selectByPrimaryKey(jobItem.getParentJobitemId());
                                if (!ObjectUtils.isEmpty(rdmsJobItem)) {
                                    if (rdmsJobItem.getType().equals(JobItemTypeEnum.QUALITY.getType())) {
                                        jobItem.setQualityId(rdmsJobItem.getQualityId());
                                    }
                                }
                            }
                        } else {
                            throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                        }

                        String jobItemId = this.save(jobItem);
                        //填写文件的访问权限人员
                        if (!CollectionUtils.isEmpty(fileIds)) {
                            {
                                //设置文件授权 权限
                                RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                                roleUsersDto.setLoginUserId(jobItemsDto.getLoginUserId());
                                roleUsersDto.setExecutorId(jobItemsDto.getExecutorId());
                                //判断被测任务是不是Assist或者bug任务
                                if (!ObjectUtils.isEmpty(jobItem.getTestedJobitemId())) {
                                    RdmsJobItem testedJobitem = this.selectByPrimaryKey(jobItem.getTestedJobitemId());
                                    if (!ObjectUtils.isEmpty(testedJobitem)) {
                                        roleUsersDto.setReceiverId(testedJobitem.getExecutorId());
                                        if (testedJobitem.getType().equals(JobItemTypeEnum.ASSIST.getType()) || testedJobitem.getType().equals(JobItemTypeEnum.BUG.getType())) {
                                            RdmsJobItem parentJobitem = this.selectByPrimaryKey(testedJobitem.getParentJobitemId());
                                            roleUsersDto.setReceiverId(parentJobitem.getExecutorId());
                                        }
                                    }
                                }
                                RdmsJobItem rdmsJobItem = this.selectByPrimaryKey(jobItemId);
                                RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(rdmsJobItem.getCustomerId());
                                roleUsersDto.setBossId(rdmsBossDto.getBossId());
                                roleUsersDto.setSuperId(null);
                                roleUsersDto.setIpmtId(null);
                                roleUsersDto.setPjmId(jobItemsDto.getProjectManagerId());
                                roleUsersDto.setPdmId(null);
                                if(!ObjectUtils.isEmpty(jobItemsDto.getProjectId())){
                                    RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(rdmsJobItem.getProjectId());
                                    if(!ObjectUtils.isEmpty(rdmsProject)){
                                        roleUsersDto.setMainPjmId(rdmsProject.getProjectManagerId());
                                    }
                                }
                                rdmsFileAuthService.setFileAuthUser(fileIds, roleUsersDto);
                            }
                        }

                        //创建一个工单流程节点, 用户保存节点信息
                        RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                        jobItemProcess.setJobItemId(jobItemId);
                        jobItemProcess.setExecutorId(jobItemsDto.getLoginUserId());
                        jobItemProcess.setNextNode(jobItemsDto.getExecutorId());
                        jobItemProcess.setJobDescription("请完成工单内容测试任务");
                        jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.ISSUE.getStatus()); //工单下发
                        long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobItemId);
                        jobItemProcess.setDeep((int) jobItemProcessCount);
                        String JobProcessId = rdmsJobItemProcessService.save(jobItemProcess);
                    }

                    //处理被测试工单状态
                    {
                        RdmsJobItem testJobItem = this.selectByPrimaryKey(jobItem.getTestedJobitemId());
                        testJobItem.setNextNode(jobItemsDto.getExecutorId());
                        testJobItem.setStatus(JobItemStatusEnum.TESTING.getStatus());
                        String testJobItemId = this.update(testJobItem);

                        //创建一个工单流程节点, 用户保存节点信息
                        RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                        jobItemProcess.setJobItemId(testJobItemId);
                        jobItemProcess.setExecutorId(jobItemsDto.getLoginUserId());
                        jobItemProcess.setNextNode(jobItemsDto.getExecutorId());
                        jobItemProcess.setJobDescription("任务被提交测试");
                        jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.TESTING.getStatus());
                        long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(testJobItemId);
                        jobItemProcess.setDeep((int) jobItemProcessCount);
                        String JobProcessId = rdmsJobItemProcessService.save(jobItemProcess);
                    }
                    break;
                }
                case CHARACTER_INT: {
                    ProjectTypeEnum projectType = ProjectTypeEnum.getProjectTypeEnumByType(jobItemsDto.getProjectType());
                    ValidatorUtil.require(jobItemsDto.getExecutorId(), "执行人ID");
                    switch (projectType) {
                        case SUB_PROJECT: {
                            ValidatorUtil.require(jobItemsDto.getCharacterId(), "功能/特性ID");
                            ValidatorUtil.require(jobItemsDto.getManhour(), "工时");
                            break;
                        }
                        default: {
                            throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                        }
                    }
                    //对计划提交时间进行处理_当日17点为提交时间
                    jobItemsDto.setPlanSubmissionTime(DateUtil.afternoon23_59pm(jobItemsDto.getPlanSubmissionTime()));

                    //创建工单
                    RdmsJobItem jobItem = CopyUtil.copy(jobItemsDto, RdmsJobItem.class);
                    {
                        //将附件列表写入工单记录, 并创建工单
                        RdmsAttachmentDto attachmentDto = new RdmsAttachmentDto();
                        attachmentDto.setUse(FileGroupingEnum.JOBITEM.getCode());
                        attachmentDto.setItemId(jobItem.getId());
                        List<RdmsFileDto> fileDtos = rdmsFileService.listFileInfo(attachmentDto);
                        List<String> fileIds = fileDtos.stream().map(RdmsFileDto::getId).collect(Collectors.toList());
                        String fileIdsStr = JSONObject.toJSONString(fileIds);
                        jobItem.setFileListStr(fileIdsStr); //创建工单时, 页面上提供的附件,作为工单附件; 以后,在过程处理过程中提供的附件将记录为节点附件, 相应的file表中的 itemId为Process的id

                        jobItem.setNextNode(jobItemsDto.getLoginUserId());   // 下发工单的人
                        jobItem.setStatus(JobItemStatusEnum.HANDLING.getStatus());
                        jobItem.setProjectType(jobItemsDto.getProjectType());
                        {
                            jobItem.setManhour(jobItemsDto.getStandManhour());
                            jobItem.setStandManhourFee(jobItemsDto.getStandManhourFee());
                        }
                        RdmsCharacter character = rdmsCharacterService.selectByPrimaryKey(jobItemsDto.getCharacterId());
                        jobItem.setProjectId(character.getProjectId());
                        jobItem.setSubprojectId(character.getSubprojectId());
                        jobItem.setProductManagerId(character.getProductManagerId());
                        jobItem.setProjectManagerId(jobItemsDto.getLoginUserId());
                        jobItem.setCustomerId(character.getCustomerId());

                        String jobItemId = this.save(jobItem);
                        //填写文件的访问权限人员
                        if (!CollectionUtils.isEmpty(fileIds)) {
                            {
                                //设置文件授权 权限
                                RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                                roleUsersDto.setLoginUserId(jobItemsDto.getLoginUserId());
                                roleUsersDto.setExecutorId(jobItemsDto.getExecutorId());
                                roleUsersDto.setReceiverId(null);
                                RdmsJobItem rdmsJobItem = this.selectByPrimaryKey(jobItemId);
                                RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(rdmsJobItem.getCustomerId());
                                roleUsersDto.setBossId(rdmsBossDto.getBossId());
                                roleUsersDto.setSuperId(null);
                                roleUsersDto.setIpmtId(null);
                                roleUsersDto.setPjmId(jobItemsDto.getProjectManagerId());
                                if(!ObjectUtils.isEmpty(jobItemsDto.getProjectId())){
                                    RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(rdmsJobItem.getProjectId());
                                    if(!ObjectUtils.isEmpty(rdmsProject)){
                                        roleUsersDto.setMainPjmId(rdmsProject.getProjectManagerId());
                                    }
                                }
                                roleUsersDto.setPdmId(null);
                                rdmsFileAuthService.setFileAuthUser(fileIds, roleUsersDto);
                            }
                        }

                        //创建一个工单流程节点, 用户保存节点信息
                        RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                        jobItemProcess.setJobItemId(jobItemId);
                        jobItemProcess.setExecutorId(jobItemsDto.getLoginUserId());
                        jobItemProcess.setNextNode(jobItemsDto.getExecutorId());
                        jobItemProcess.setJobDescription("请完成功能集成开发任务");
                        jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.ISSUE.getStatus()); //工单下发
                        long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobItemId);
                        jobItemProcess.setDeep((int) jobItemProcessCount);
                        String JobProcessId = rdmsJobItemProcessService.save(jobItemProcess);
                    }

                    //处理相应的功能的状态
                    {
                        RdmsCharacter character = rdmsCharacterService.selectByPrimaryKey(jobItemsDto.getCharacterId());
//                        character.setNextNode(subproject.getProjectManagerId());  //发工单时不要改变Character的nextNode
                        character.setStatus(CharacterStatusEnum.INTEGRATION.getStatus());
                        rdmsCharacterService.update(character);

                        RdmsCharacterProcess characterProcess = new RdmsCharacterProcess();
                        characterProcess.setCharacterId(character.getId());
                        characterProcess.setExecutorId(jobItemsDto.getExecutorId());
                        characterProcess.setNextNode(character.getNextNode());
                        long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(character.getId());
                        characterProcess.setDeep((int) characterProcessCount);
                        characterProcess.setJobDescription("功能集成开发中");
                        characterProcess.setProcessStatus(CharacterProcessStatusEnum.ONGOING.getStatus());
                        rdmsCharacterProcessService.save(characterProcess);
                    }

                    break;
                }
                case REVIEW:
                case REVIEW_PRO:
                case REVIEW_SUBP: {
                    ProjectTypeEnum projectType = ProjectTypeEnum.getProjectTypeEnumByType(jobItemsDto.getProjectType());
                    switch (Objects.requireNonNull(projectType)) {
                        case SUB_PROJECT: {
                            ValidatorUtil.require(jobItemsDto.getDocType(), "DOC_TYPE");
                            if (jobItemsDto.getDocType().equals(DocTypeEnum.CHARACTER.getType())) {
                                ValidatorUtil.require(jobItemsDto.getCharacterId(), "功能/特性ID");
                            }
                            if (jobItemsDto.getDocType().equals(DocTypeEnum.SUB_PROJECT.getType())) {
                                ValidatorUtil.require(jobItemsDto.getProjectId(), "项目ID");
                                ValidatorUtil.require(jobItemsDto.getSubprojectId(), "子项目ID");
                            }
                            ValidatorUtil.require(jobItemsDto.getProductManagerId(), "产品经理ID");
                            ValidatorUtil.require(jobItemsDto.getReviewWorkerIdList(), "联合评审人");
                            break;
                        }
                        case PROJECT: {
                            ValidatorUtil.require(jobItemsDto.getProjectId(), "项目ID");
                            ValidatorUtil.require(jobItemsDto.getProductManagerId(), "产品经理ID");
                            ValidatorUtil.require(jobItemsDto.getReviewWorkerIdList(), "联合评审人");
                            ValidatorUtil.require(jobItemsDto.getDocType(), "DOC_TYPE");
                            break;
                        }
                        default: {
                            throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                        }
                    }
                    //对计划提交时间进行处理_当日17点为提交时间
//                    jobItemsDto.setPlanSubmissionTime(DateUtil.afternoon23_59pm(jobItemsDto.getPlanSubmissionTime()));

                    DocTypeEnum docTypeEnum = DocTypeEnum.getDocTypeEnumByType(jobItemsDto.getDocType());
                    switch (docTypeEnum) {
                        case CHARACTER: {
                            RdmsCharacter character = rdmsCharacterService.selectByPrimaryKey(jobItemsDto.getCharacterId());
                            RdmsProjectSubprojectDto subproject = rdmsProjectService.getSubprojectBySubprojectId(character.getSubprojectId());

                            RdmsJobItem jobItem = CopyUtil.copy(jobItemsDto, RdmsJobItem.class);
                            //创建工单
                            {
                                //将附件列表写入工单记录, 并创建工单
                                RdmsAttachmentDto attachmentDto = new RdmsAttachmentDto();
                                attachmentDto.setUse(FileGroupingEnum.JOBITEM.getCode());
                                attachmentDto.setItemId(jobItem.getId());
                                List<RdmsFileDto> fileDtos = rdmsFileService.listFileInfo(attachmentDto);
                                List<String> fileIds = fileDtos.stream().map(RdmsFileDto::getId).collect(Collectors.toList());
                                String fileIdsStr = JSONObject.toJSONString(fileIds);
                                jobItem.setFileListStr(fileIdsStr); //创建工单时, 页面上提供的附件,作为工单附件; 以后,在过程处理过程中提供的附件将记录为节点附件, 相应的file表中的 itemId为Process的id

                                List<RdmsJobItemDto> jobitemListByCharacterIdAndType = this.getJobitemListByCharacterIdAndStatusAndType(jobItemsDto.getCharacterId(), JobItemStatusEnum.COMPLETED.getStatus(), JobItemTypeEnum.CHARACTER_INT.getType());
                                if (!CollectionUtils.isEmpty(jobitemListByCharacterIdAndType)) {
                                    jobItem.setTestedJobitemId(jobitemListByCharacterIdAndType.get(0).getId());  //对应的集成工单
                                }
                                //功能评审/子项目评审 是 子项目经理组织 由 产品经理作为组长组织进行评审
                                //项目评审 是 主项目经理进行组织 由 监管委员作为组长评审
                                jobItem.setExecutorId(jobItemsDto.getLoginUserId());  //参数传进来的是子项目经理
                                jobItem.setNextNode(subproject.getProductManagerId());  //功能评审评审组长 产品经理
                                jobItem.setStatus(JobItemStatusEnum.SUBMIT.getStatus());  //直接处于提交状态
                                jobItem.setProjectType(jobItemsDto.getProjectType());

                                jobItem.setProjectId(character.getProjectId());
                                jobItem.setSubprojectId(character.getSubprojectId());
                                jobItem.setProductManagerId(character.getProductManagerId());
                                jobItem.setProjectManagerId(subproject.getProjectManagerId());  //子项目经理
                                jobItem.setCustomerId(character.getCustomerId());
                                //会签人中, 排除掉作为执行人的子项目经理
                                RdmsJobItem finalJobItem = jobItem;
                                List<String> collect = jobItemsDto.getReviewWorkerIdList().stream().filter(item -> !item.equals(finalJobItem.getExecutorId())).collect(Collectors.toList());
                                RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(jobItem.getProjectId());
                                if (!ObjectUtils.isEmpty(rdmsProject)) {
                                    collect.add(rdmsProject.getProjectManagerId());
                                }
                                List<String> reviewCooIdList = collect.stream().distinct().collect(Collectors.toList());
                                jobItemsDto.setReviewWorkerIdList(reviewCooIdList);
                                String jsonString = JSON.toJSONString(jobItemsDto.getReviewWorkerIdList());
                                jobItem.setReviewWorkerIdStr(jsonString);
                                String jobItemId = this.save(jobItem);  //保存给上一级项目经理的评审工单
                                //填写文件的访问权限人员
                                if (!CollectionUtils.isEmpty(fileIds)) {
                                    {
                                        //设置文件授权 权限
                                        RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                                        roleUsersDto.setLoginUserId(jobItemsDto.getLoginUserId());
                                        roleUsersDto.setExecutorId(jobItemsDto.getExecutorId());
                                        roleUsersDto.setReceiverId(null);
                                        RdmsJobItem rdmsJobItem = this.selectByPrimaryKey(jobItemId);
                                        RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(rdmsJobItem.getCustomerId());
                                        roleUsersDto.setBossId(rdmsBossDto.getBossId());
                                        roleUsersDto.setSuperId(null);
                                        roleUsersDto.setIpmtId(null);
                                        roleUsersDto.setPjmId(jobItemsDto.getProjectManagerId());
                                        roleUsersDto.setPdmId(jobItemsDto.getProductManagerId());
                                        if(!ObjectUtils.isEmpty(jobItemsDto.getPreProjectId())){
                                            RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(jobItemsDto.getPreProjectId());
                                            if (!ObjectUtils.isEmpty(rdmsPreProject)) {
                                                roleUsersDto.setSmId(rdmsPreProject.getSystemManagerId());
                                            }
                                        }
                                        roleUsersDto.setReviewWorkerIds(jobItemsDto.getReviewWorkerIdList());
                                        rdmsFileAuthService.setFileAuthUser(fileIds, roleUsersDto);
                                    }
                                }

                                //创建一个工单流程节点, 用户保存节点信息
                                RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                                jobItemProcess.setJobItemId(jobItemId);
                                jobItemProcess.setExecutorId(jobItemsDto.getLoginUserId());
                                jobItemProcess.setNextNode(jobItemsDto.getExecutorId());
                                jobItemProcess.setJobDescription("开发评审工单");
                                jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.SUBMIT.getStatus()); //工单提交
                                long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobItemId);
                                jobItemProcess.setDeep((int) jobItemProcessCount);
                                rdmsJobItemProcessService.save(jobItemProcess);

                                //发会签工单
                                if (!CollectionUtils.isEmpty(jobItemsDto.getReviewWorkerIdList())) {
                                    List<String> reviewMemberIdList = jobItemsDto.getReviewWorkerIdList();
                                    //评审组应当包含产品经理
                                    //reviewMemberIdList.add(character.getProductManagerId());
                                    reviewMemberIdList.add(subproject.getProjectManagerId());
                                    List<String> workerIdList = reviewMemberIdList.stream().distinct().collect(Collectors.toList());
                                    //因为评审工单是发给产品经理的, 如果产品经理在联合评审委员中重复, 则剔除掉
                                    List<String> stringList = workerIdList.stream().filter(item -> !item.equals(subproject.getProductManagerId())).collect(Collectors.toList());
                                    int index = 1;
                                    for (String workerId : stringList) {
                                        RdmsJobItem item = CopyUtil.copy(jobItem, RdmsJobItem.class);
                                        item.setId(null);
                                        item.setJobName("评审" + index + ":" + jobItem.getJobName());
                                        item.setExecutorId(workerId);
                                        item.setStatus(JobItemStatusEnum.HANDLING.getStatus());
                                        item.setNextNode(null);
                                        item.setType(JobItemTypeEnum.REVIEW_COO.getType());
                                        item.setTaskDescription("评审会签");
                                        item.setTestedJobitemId(jobItemId); //被会签的工单
                                        item.setPropertyId(null);
                                        item.setReviewWorkerIdStr(null);
                                        item.setReviewResult(null);
                                        item.setFileListStr(jobItem.getFileListStr());
                                        String itemId = this.save(item);

                                        //创建一个工单流程节点
                                        RdmsJobItemProcess itemProcess = new RdmsJobItemProcess();
                                        itemProcess.setJobItemId(itemId);
                                        itemProcess.setExecutorId(jobItemsDto.getLoginUserId());
                                        itemProcess.setNextNode(item.getExecutorId());
                                        itemProcess.setJobDescription("评审会签");
                                        itemProcess.setProcessStatus(JobItemProcessStatusEnum.ISSUE.getStatus()); //工单下发
                                        long itemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(itemId);
                                        itemProcess.setDeep((int) itemProcessCount);
                                        rdmsJobItemProcessService.save(itemProcess);
                                        index++;
                                    }
                                }
                            }

                            //处理相应的功能的状态
                            {
//                                character.setNextNode(jobItem.getProjectManagerId());  //提交给项目经理评审
                                character.setStatus(CharacterStatusEnum.REVIEW.getStatus());
                                rdmsCharacterService.update(character);

                                RdmsCharacterProcess characterProcess = new RdmsCharacterProcess();
                                characterProcess.setCharacterId(character.getId());
                                characterProcess.setExecutorId(jobItemsDto.getExecutorId());
                                characterProcess.setNextNode(null);
                                long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(character.getId());
                                characterProcess.setDeep((int) characterProcessCount);
                                characterProcess.setJobDescription("提交评审");
                                characterProcess.setProcessStatus(CharacterProcessStatusEnum.REVIEW.getStatus());
                                rdmsCharacterProcessService.save(characterProcess);
                            }

                            break;
                        }
                        case SUB_PROJECT: {
                            RdmsProjectSubproject currentSubproject = rdmsSubprojectService.selectByPrimaryKey(jobItemsDto.getSubprojectId());
                            RdmsProjectSubproject superSubproject = rdmsSubprojectService.selectByPrimaryKey(currentSubproject.getParent());
                            Boolean isProject = false;
                            if (currentSubproject.getId().equals(currentSubproject.getParent())) {
                                isProject = true;
                            }

                            RdmsJobItem reviewJobitem = CopyUtil.copy(jobItemsDto, RdmsJobItem.class);
                            //创建工单
                            {
                                //将附件列表写入工单记录, 并创建工单
                                RdmsAttachmentDto attachmentDto = new RdmsAttachmentDto();
                                attachmentDto.setUse(FileGroupingEnum.JOBITEM.getCode());
                                attachmentDto.setItemId(reviewJobitem.getId());
                                List<RdmsFileDto> fileDtos = rdmsFileService.listFileInfo(attachmentDto);
                                List<String> fileIds = fileDtos.stream().map(RdmsFileDto::getId).collect(Collectors.toList());
                                String fileIdsStr = JSONObject.toJSONString(fileIds);
                                reviewJobitem.setFileListStr(fileIdsStr); //创建工单时, 页面上提供的附件,作为工单附件; 以后,在过程处理过程中提供的附件将记录为节点附件, 相应的file表中的 itemId为Process的id

                                //功能评审/子项目评审 是 子项目经理组织 由 产品经理作为组长组织进行评审
                                //项目评审 是 主项目经理进行组织 由 监管委员作为组长评审
                                reviewJobitem.setExecutorId(jobItemsDto.getLoginUserId());  //工作的执行人是自己, 其实是当前子项目的项目经理
                                if (isProject) {
                                    reviewJobitem.setNextNode(superSubproject.getSupervise());  //下一节点是项目监管人
                                    reviewJobitem.setProjectManagerId(superSubproject.getProjectManagerId());  //主项目的项目经理
                                } else {
                                    //判断是否为一级子项目
                                    if (superSubproject.getParent().equals(superSubproject.getId())) {
                                        reviewJobitem.setNextNode(superSubproject.getProductManagerId());  //一级子项目产品经理是评审组长
                                        reviewJobitem.setProjectManagerId(superSubproject.getProjectManagerId());  //评审工单相当于上一级项目经理发的子项开发工单, 现在由子项目经理进行提交, 所以,评审工单记载的项目经理是上一级项目经理, 这个很重要, 与后续处理有关
                                    } else {
                                        reviewJobitem.setNextNode(superSubproject.getProductManagerId());  //子项目评审, 评审组长都是产品经理
                                        reviewJobitem.setProjectManagerId(superSubproject.getProjectManagerId());  //评审工单相当于上一级项目经理发的子项开发工单, 现在由子项目经理进行提交, 所以,评审工单记载的项目经理是上一级项目经理, 这个很重要, 与后续处理有关
                                    }
                                }

                                reviewJobitem.setStatus(JobItemStatusEnum.SUBMIT.getStatus());  //直接处于提交状态
                                reviewJobitem.setProjectType(jobItemsDto.getProjectType());

                                reviewJobitem.setCustomerId(superSubproject.getCustomerId());
                                //会签人中, 排除掉作为执行人的子项目经理
                                List<String> collect = jobItemsDto.getReviewWorkerIdList().stream().filter(item -> !item.equals(reviewJobitem.getExecutorId())).collect(Collectors.toList());
//                                List<String> collect1 = collect.stream().filter(item -> !item.equals(reviewJobitem.getNextNode())).collect(Collectors.toList());
                                jobItemsDto.setReviewWorkerIdList(collect);
                                String jsonString = JSON.toJSONString(jobItemsDto.getReviewWorkerIdList());
                                reviewJobitem.setReviewWorkerIdStr(jsonString);

                                String jobItemId = this.save(reviewJobitem);  //保存给上一级项目经理的评审工单
                                //填写文件的访问权限人员
                                if (!CollectionUtils.isEmpty(fileIds)) {
                                    {
                                        //设置文件授权 权限
                                        RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                                        roleUsersDto.setLoginUserId(jobItemsDto.getLoginUserId());
                                        roleUsersDto.setExecutorId(jobItemsDto.getExecutorId());
                                        roleUsersDto.setReceiverId(null);
                                        RdmsJobItem rdmsJobItem = this.selectByPrimaryKey(jobItemId);
                                        RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(rdmsJobItem.getCustomerId());
                                        roleUsersDto.setBossId(rdmsBossDto.getBossId());
                                        roleUsersDto.setSuperId(null);
                                        roleUsersDto.setIpmtId(null);
                                        roleUsersDto.setPjmId(jobItemsDto.getProjectManagerId());
                                        if(!ObjectUtils.isEmpty(jobItemsDto.getProjectId())){
                                            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(rdmsJobItem.getProjectId());
                                            if(!ObjectUtils.isEmpty(rdmsProject)){
                                                roleUsersDto.setMainPjmId(rdmsProject.getProjectManagerId());
                                            }
                                        }
                                        roleUsersDto.setPdmId(jobItemsDto.getProductManagerId());
                                        if(!ObjectUtils.isEmpty(jobItemsDto.getPreProjectId())){
                                            RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(jobItemsDto.getPreProjectId());
                                            if (!ObjectUtils.isEmpty(rdmsPreProject)) {
                                                roleUsersDto.setSmId(rdmsPreProject.getSystemManagerId());
                                            }
                                        }
                                        roleUsersDto.setReviewWorkerIds(jobItemsDto.getReviewWorkerIdList());
                                        rdmsFileAuthService.setFileAuthUser(fileIds, roleUsersDto);
                                    }
                                }

                                //创建一个工单流程节点, 用户保存节点信息
                                RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                                jobItemProcess.setJobItemId(jobItemId);
                                jobItemProcess.setExecutorId(jobItemsDto.getLoginUserId());
                                jobItemProcess.setNextNode(jobItemsDto.getExecutorId());
                                jobItemProcess.setJobDescription("子项目评审工单");
                                jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.SUBMIT.getStatus()); //工单提交
                                long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobItemId);
                                jobItemProcess.setDeep((int) jobItemProcessCount);
                                rdmsJobItemProcessService.save(jobItemProcess);

                                //发会签工单
                                if (!CollectionUtils.isEmpty(jobItemsDto.getReviewWorkerIdList())) {
                                    List<String> reviewMemberIdList = jobItemsDto.getReviewWorkerIdList();
                                    //评审组应当包含产品经理
                                    reviewMemberIdList.add(superSubproject.getProductManagerId());
                                    reviewMemberIdList.add(superSubproject.getProjectManagerId());
                                    List<String> workerIdList = reviewMemberIdList.stream().distinct().collect(Collectors.toList());
                                    //因为评审工单是发给产品经理的, 如果差评经理会签名单中, 需要剔除
                                    List<String> stringList = workerIdList.stream().filter(item -> !item.equals(reviewJobitem.getNextNode())).collect(Collectors.toList());
                                    int index = 1;
                                    for (String workerId : stringList) {
                                        RdmsJobItem item = CopyUtil.copy(reviewJobitem, RdmsJobItem.class);
                                        item.setId(null);
                                        item.setJobName("评审" + index + ":" + reviewJobitem.getJobName());
                                        item.setExecutorId(workerId);
                                        item.setStatus(JobItemStatusEnum.HANDLING.getStatus());
                                        item.setNextNode(null);
                                        item.setType(JobItemTypeEnum.REVIEW_COO.getType());
                                        item.setTaskDescription("评审会签");
                                        item.setTestedJobitemId(jobItemId); //被会签的工单
                                        item.setPropertyId(null);
                                        item.setReviewWorkerIdStr(null);
                                        item.setReviewResult(null);
                                        item.setFileListStr(reviewJobitem.getFileListStr());
                                        String itemId = this.save(item);

                                        //创建一个工单流程节点
                                        RdmsJobItemProcess itemProcess = new RdmsJobItemProcess();
                                        itemProcess.setJobItemId(itemId);
                                        itemProcess.setExecutorId(jobItemsDto.getLoginUserId());
                                        itemProcess.setNextNode(item.getExecutorId());
                                        itemProcess.setJobDescription("评审会签");
                                        itemProcess.setProcessStatus(JobItemProcessStatusEnum.ISSUE.getStatus()); //工单下发
                                        long itemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(itemId);
                                        itemProcess.setDeep((int) itemProcessCount);
                                        rdmsJobItemProcessService.save(itemProcess);
                                        index++;
                                    }
                                }
                            }

                            //处理子项目状态
                            RdmsProjectSubprojectDto subprojectDto = rdmsProjectService.getSubprojectBySubprojectId(jobItemsDto.getSubprojectId());
                            RdmsProjectSubproject subproject = CopyUtil.copy(subprojectDto, RdmsProjectSubproject.class);
                            subproject.setStatus(ProjectStatusEnum.REVIEW_SUBP.getStatus());
                            rdmsSubprojectService.updateByPrimaryKeySelective(subproject);

                            break;
                        }
                        default: {
                            throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                        }
                    }

                    break;
                }
                case REVIEW_MILESTONE_PRE: {
                    DocTypeEnum docTypeEnum = DocTypeEnum.getDocTypeEnumByType(jobItemsDto.getDocType());
                    switch (docTypeEnum) {
                        case MILESTONE: {
                            RdmsProjectSubproject currentSubproject = rdmsSubprojectService.selectByPrimaryKey(jobItemsDto.getSubprojectId());
                            RdmsProjectSubproject superSubproject = rdmsSubprojectService.selectByPrimaryKey(currentSubproject.getParent());
                            // 判断是否为主项目
                            if (currentSubproject.getId().equals(currentSubproject.getParent())) {
                                RdmsJobItem preReviewJobitem = CopyUtil.copy(jobItemsDto, RdmsJobItem.class);

                                preReviewJobitem.setFileListStr(null);
                                // 里程碑评审 主项目经理是 评审组组长
                                preReviewJobitem.setExecutorId(superSubproject.getProjectManagerId());  // 评审任务是由主项目经理发出的
                                preReviewJobitem.setNextNode(superSubproject.getProjectManagerId());  //下一节点是评审组长: 里程碑评审 主项目经理是 评审组组长
                                preReviewJobitem.setProjectManagerId(superSubproject.getProjectManagerId());  //主项目的项目经理

                                preReviewJobitem.setStatus(JobItemStatusEnum.HANDLING.getStatus());
                                preReviewJobitem.setProjectType(jobItemsDto.getProjectType());
                                preReviewJobitem.setCustomerId(superSubproject.getCustomerId());

                                String jobItemId = this.save(preReviewJobitem);

                                //创建一个工单流程节点, 用户保存节点信息
                                RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                                jobItemProcess.setJobItemId(jobItemId);
                                jobItemProcess.setExecutorId(jobItemsDto.getLoginUserId());
                                jobItemProcess.setNextNode(jobItemsDto.getExecutorId());
                                jobItemProcess.setJobDescription("里程碑评审发起工单");
                                jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.ISSUE.getStatus());
                                long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobItemId);
                                jobItemProcess.setDeep((int) jobItemProcessCount);
                                rdmsJobItemProcessService.save(jobItemProcess);
                            }
                            break;
                        }
                        default: {
                            throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                        }
                    }
                    break;
                }
                case REVIEW_MILESTONE: {
                    DocTypeEnum docTypeEnum = DocTypeEnum.getDocTypeEnumByType(jobItemsDto.getDocType());
                    switch (docTypeEnum) {
                        case MILESTONE: {
                            RdmsProjectSubproject currentSubproject = rdmsSubprojectService.selectByPrimaryKey(jobItemsDto.getSubprojectId());
                            RdmsProjectSubproject superSubproject = rdmsSubprojectService.selectByPrimaryKey(currentSubproject.getParent());

                            RdmsJobItem reviewJobitem = CopyUtil.copy(jobItemsDto, RdmsJobItem.class);
                            //创建工单
                            {
                                //将附件列表写入工单记录, 并创建工单
                                RdmsAttachmentDto attachmentDto = new RdmsAttachmentDto();
                                attachmentDto.setUse(FileGroupingEnum.JOBITEM.getCode());
                                attachmentDto.setItemId(reviewJobitem.getId());
                                List<RdmsFileDto> fileDtos = rdmsFileService.listFileInfo(attachmentDto);
                                List<String> fileIds = fileDtos.stream().map(RdmsFileDto::getId).collect(Collectors.toList());
                                String fileIdsStr = JSONObject.toJSONString(fileIds);
                                reviewJobitem.setFileListStr(fileIdsStr); //创建工单时, 页面上提供的附件,作为工单附件; 以后,在过程处理过程中提供的附件将记录为节点附件, 相应的file表中的 itemId为Process的id

                                //里程碑评审的评审组长是 主项目经理
                                reviewJobitem.setExecutorId(jobItemsDto.getLoginUserId());  //工作的执行人是自己
                                reviewJobitem.setNextNode(superSubproject.getProjectManagerId());  //评审组长 主项目经理
                                reviewJobitem.setProjectManagerId(superSubproject.getProjectManagerId());  //主项目的项目经理

                                reviewJobitem.setStatus(JobItemStatusEnum.SUBMIT.getStatus());  //直接处于提交状态
                                reviewJobitem.setProjectType(jobItemsDto.getProjectType());

                                reviewJobitem.setCustomerId(superSubproject.getCustomerId());
                                //会签人中, 排除掉作为执行人的子项目经理
                                List<String> collect = jobItemsDto.getReviewWorkerIdList().stream().filter(item -> !item.equals(reviewJobitem.getExecutorId())).collect(Collectors.toList());
                                List<String> collect1 = collect.stream().filter(item -> !item.equals(reviewJobitem.getNextNode())).collect(Collectors.toList());

                                jobItemsDto.setReviewWorkerIdList(collect1);
                                String jsonString = JSON.toJSONString(jobItemsDto.getReviewWorkerIdList());
                                reviewJobitem.setReviewWorkerIdStr(jsonString);
                                RdmsJobItem rdmsJobItem = this.selectByPrimaryKey(jobItemsDto.getOriginJobitemId());
                                reviewJobitem.setNote(rdmsJobItem.getNote());
                                String jobItemId = this.save(reviewJobitem);  //保存给上一级项目经理的评审工单
                                //填写文件的访问权限人员
                                if (!CollectionUtils.isEmpty(fileIds)) {
                                    {
                                        //设置文件授权 权限
                                        RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                                        roleUsersDto.setLoginUserId(jobItemsDto.getLoginUserId());
                                        roleUsersDto.setExecutorId(jobItemsDto.getExecutorId());
                                        roleUsersDto.setReceiverId(null);
                                        RdmsJobItem rdmsJobItem1 = this.selectByPrimaryKey(jobItemId);
                                        RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(rdmsJobItem1.getCustomerId());
                                        roleUsersDto.setBossId(rdmsBossDto.getBossId());
                                        roleUsersDto.setSuperId(null);
                                        roleUsersDto.setIpmtId(null);
                                        roleUsersDto.setPjmId(jobItemsDto.getProjectManagerId());
                                        if(!ObjectUtils.isEmpty(jobItemsDto.getProjectId())){
                                            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(rdmsJobItem.getProjectId());
                                            if(!ObjectUtils.isEmpty(rdmsProject)){
                                                roleUsersDto.setMainPjmId(rdmsProject.getProjectManagerId());
                                            }
                                        }
                                        roleUsersDto.setPdmId(jobItemsDto.getProductManagerId());
                                        roleUsersDto.setReviewWorkerIds(jobItemsDto.getReviewWorkerIdList());
                                        rdmsFileAuthService.setFileAuthUser(fileIds, roleUsersDto);
                                    }
                                }

                                //创建一个工单流程节点, 用户保存节点信息
                                RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                                jobItemProcess.setJobItemId(jobItemId);
                                jobItemProcess.setExecutorId(jobItemsDto.getLoginUserId());
                                jobItemProcess.setNextNode(jobItemsDto.getExecutorId());
                                jobItemProcess.setJobDescription("里程碑评审工单");
                                jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.SUBMIT.getStatus()); //工单提交
                                long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobItemId);
                                jobItemProcess.setDeep((int) jobItemProcessCount);
                                rdmsJobItemProcessService.save(jobItemProcess);

                                //发会签工单
                                if (!CollectionUtils.isEmpty(jobItemsDto.getReviewWorkerIdList())) {
                                    List<String> reviewMemberIdList = jobItemsDto.getReviewWorkerIdList();
                                    //评审组应当包含产品经理
                                    reviewMemberIdList.add(superSubproject.getProductManagerId());
                                    List<String> workerIdList = reviewMemberIdList.stream().distinct().collect(Collectors.toList());
                                    //因为评审工单是发给上一级项目经理的, 如果上一级项目经理在联合评审委员中重复, 则剔除掉
                                    List<String> stringList = workerIdList.stream().filter(item -> !item.equals(superSubproject.getProjectManagerId())).collect(Collectors.toList());
                                    int index = 1;
                                    for (String workerId : stringList) {
                                        RdmsJobItem item = CopyUtil.copy(reviewJobitem, RdmsJobItem.class);
                                        item.setId(null);
                                        item.setJobName("里程碑评审" + index + ":" + reviewJobitem.getJobName());
                                        item.setExecutorId(workerId);
                                        item.setStatus(JobItemStatusEnum.HANDLING.getStatus());
                                        item.setNextNode(null);
                                        item.setType(JobItemTypeEnum.REVIEW_COO.getType());
                                        item.setTaskDescription("里程碑评审会签");
                                        item.setTestedJobitemId(jobItemId); //被会签的工单
                                        item.setPropertyId(null);
                                        item.setReviewWorkerIdStr(null);
                                        item.setReviewResult(null);
                                        item.setFileListStr(reviewJobitem.getFileListStr());
                                        RdmsJobItem rdmsJobItem1 = this.selectByPrimaryKey(jobItemsDto.getOriginJobitemId());
                                        item.setNote(rdmsJobItem1.getNote());
                                        String itemId = this.save(item);

                                        //创建一个工单流程节点
                                        RdmsJobItemProcess itemProcess = new RdmsJobItemProcess();
                                        itemProcess.setJobItemId(itemId);
                                        itemProcess.setExecutorId(jobItemsDto.getLoginUserId());
                                        itemProcess.setNextNode(item.getExecutorId());
                                        itemProcess.setJobDescription("里程碑评审会签");
                                        itemProcess.setProcessStatus(JobItemProcessStatusEnum.ISSUE.getStatus()); //工单下发
                                        long itemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(itemId);
                                        itemProcess.setDeep((int) itemProcessCount);
                                        rdmsJobItemProcessService.save(itemProcess);
                                        index++;
                                    }
                                }
                            }
                            //将原始工单标记为完成状态
                            RdmsJobItem rdmsJobItem = this.selectByPrimaryKey(jobItemsDto.getOriginJobitemId());
                            rdmsJobItem.setStatus(JobItemStatusEnum.COMPLETED.getStatus());
                            this.update(rdmsJobItem);
                            break;
                        }
                        default: {
                            throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                        }
                    }
                    break;
                }
                case BUG:
                case ASSIST: {
                    ValidatorUtil.require(jobItemsDto.getExecutorId(), "执行人ID");
                    ValidatorUtil.require(jobItemsDto.getParentJobitemId(), "父级工单ID");

                    //对计划提交时间进行处理_当日17点为提交时间
                    jobItemsDto.setPlanSubmissionTime(DateUtil.afternoon23_59pm(jobItemsDto.getPlanSubmissionTime()));

                    //创建工单
                    RdmsJobItem jobItem = CopyUtil.copy(jobItemsDto, RdmsJobItem.class);
                    {
                        //将附件列表写入工单记录, 并创建工单
                        RdmsAttachmentDto attachmentDto = new RdmsAttachmentDto();
                        attachmentDto.setUse(FileGroupingEnum.JOBITEM.getCode());
                        attachmentDto.setItemId(jobItem.getId());
                        List<RdmsFileDto> fileDtos = rdmsFileService.listFileInfo(attachmentDto);
                        List<String> fileIds = fileDtos.stream().map(RdmsFileDto::getId).collect(Collectors.toList());
                        if (!CollectionUtils.isEmpty(jobItemsDto.getCheckedFileIdList())) {
                            fileIds.addAll(jobItemsDto.getCheckedFileIdList()); //添加复选框勾选附件
                        }
                        String fileIdsStr = JSONObject.toJSONString(fileIds);
                        jobItem.setFileListStr(fileIdsStr); //创建工单时, 页面上提供的附件,作为工单附件; 以后,在过程处理过程中提供的附件将记录为节点附件, 相应的file表中的 itemId为Process的id
                        jobItem.setNextNode(jobItemsDto.getLoginUserId());  // 工单的发起人
                        jobItem.setStatus(JobItemStatusEnum.HANDLING.getStatus());
                        {
                            jobItem.setManhour(jobItemsDto.getStandManhour());
                            jobItem.setStandManhourFee(jobItemsDto.getStandManhourFee());
                        }
                        jobItem.setProjectType(jobItemsDto.getProjectType());

                        RdmsJobItem jobItemParent = this.selectByPrimaryKey(jobItemsDto.getParentJobitemId());
                        if (!ObjectUtils.isEmpty(jobItemParent)) {
                            jobItem.setProjectId(jobItemParent.getProjectId());
                            jobItem.setPreProjectId(jobItemParent.getPreProjectId());
                            jobItem.setSubprojectId(jobItemParent.getSubprojectId());
                            jobItem.setProjectManagerId(jobItemParent.getProjectManagerId());
                            jobItem.setCustomerId(jobItemParent.getCustomerId());
                        }

                        if (jobItemsDto.getAuxType().equals(JobItemTypeEnum.QUALITY.getType())) {
                            jobItem.setQualityId(jobItemsDto.getQualityId());
                        }

                        String jobItemId = this.save(jobItem);
                        //填写文件的访问权限人员
                        if (!CollectionUtils.isEmpty(fileIds)) {
                            {
                                //设置文件授权 权限
                                RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                                roleUsersDto.setLoginUserId(jobItemsDto.getLoginUserId());
                                roleUsersDto.setExecutorId(jobItemsDto.getExecutorId());
                                RdmsJobItem rdmsJobItem = this.selectByPrimaryKey(jobItemId);
                                RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(rdmsJobItem.getCustomerId());
                                roleUsersDto.setBossId(rdmsBossDto.getBossId());
                                roleUsersDto.setSuperId(null);
                                roleUsersDto.setIpmtId(null);
                                roleUsersDto.setPjmId(jobItemsDto.getProjectManagerId());
                                if(!ObjectUtils.isEmpty(jobItemsDto.getProjectId())){
                                    RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(rdmsJobItem.getProjectId());
                                    if(!ObjectUtils.isEmpty(rdmsProject)){
                                        roleUsersDto.setMainPjmId(rdmsProject.getProjectManagerId());
                                    }
                                }
                                //如果工单是Bug工单
                                if (jobItem.getType().equals(JobItemTypeEnum.BUG.getType())) {
                                    if (!ObjectUtils.isEmpty(jobItem.getParentJobitemId())) {
                                        RdmsJobItem testJobitem = this.selectByPrimaryKey(jobItem.getParentJobitemId());
                                        if (!ObjectUtils.isEmpty(testJobitem)) {
                                            roleUsersDto.setReceiverId(testJobitem.getExecutorId());  //添加测试工单执行人
                                            //被测工单
                                            if (!ObjectUtils.isEmpty(testJobitem.getTestedJobitemId())) {
                                                RdmsJobItem testedJobitem = this.selectByPrimaryKey(testJobitem.getTestedJobitemId());
                                                if (!ObjectUtils.isEmpty(testedJobitem)) {
                                                    roleUsersDto.setReceiverId(testedJobitem.getExecutorId());  //添加被测工单执行人
                                                    if (testedJobitem.getType().equals(JobItemTypeEnum.ASSIST.getType())) {
                                                        RdmsJobItem intJobitem = this.selectByPrimaryKey(testedJobitem.getParentJobitemId());
                                                        if (!ObjectUtils.isEmpty(intJobitem)) {
                                                            roleUsersDto.setReceiverId(intJobitem.getExecutorId());  //添加集成工单执行人
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                //如果工单是Assist工单
                                if (jobItem.getType().equals(JobItemTypeEnum.ASSIST.getType())) {
                                    RdmsJobItem intJobitem = this.selectByPrimaryKey(jobItem.getParentJobitemId());
                                    if (!ObjectUtils.isEmpty(intJobitem)) {
                                        roleUsersDto.setReceiverId(intJobitem.getExecutorId());  //添加集成工单执行人
                                    }
                                }
                                roleUsersDto.setPdmId(null);
                                rdmsFileAuthService.setFileAuthUser(fileIds, roleUsersDto);
                            }
                        }

                        //创建一个工单流程节点, 用户保存节点信息
                        RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                        jobItemProcess.setJobItemId(jobItemId);
                        jobItemProcess.setExecutorId(jobItemsDto.getLoginUserId());
                        jobItemProcess.setNextNode(jobItemsDto.getExecutorId());
                        jobItemProcess.setJobDescription("工单下发");
                        jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.ISSUE.getStatus()); //工单下发
                        long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobItemId);
                        jobItemProcess.setDeep((int) jobItemProcessCount);
                        rdmsJobItemProcessService.save(jobItemProcess);
                    }

                    break;
                }

                default: {
                    throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                }
            }
            //TODO 发送收到工作任务的短信
            if (!ObjectUtils.isEmpty(jobItemsDto.getExecutorId())) {
                RdmsSmsInfo rdmsSmsInfo = new RdmsSmsInfo();
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getExecutorId());
                if (!ObjectUtils.isEmpty(rdmsCustomerUser)) {
                    rdmsSmsInfo.setName(rdmsCustomerUser.getTrueName());
                    rdmsSmsInfo.setMobile(rdmsCustomerUser.getLoginName());
                    rdmsSmsInfo.setJobName(jobItemsDto.getJobName());
                    Date planSubmissionTime = jobItemsDto.getPlanSubmissionTime();
                    rdmsSmsInfo.setTime(planSubmissionTime);
                    rdmsSmsInfo.setType(jobItemsDto.getType());
                    rdmsSmsInfoService.sendJobNotify(rdmsSmsInfo);
                }
            }
        }
        return OverLoadStatusEnum.NORMAL.getStatus();
    }

    private void mergeDealWith(RdmsJobItemDto jobItemsDto) throws ParseException {
        ProjectTypeEnum projectType = ProjectTypeEnum.getProjectTypeEnumByType(jobItemsDto.getProjectType());
        ValidatorUtil.require(jobItemsDto.getExecutorId(), "执行人ID");
        if (Objects.requireNonNull(projectType) == ProjectTypeEnum.PRE_PROJECT) {
            ValidatorUtil.require(jobItemsDto.getPreProjectId(), "预立项项目ID");
            RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(jobItemsDto.getPreProjectId());
            jobItemsDto.setProductManagerId(rdmsPreProject.getProductManagerId());
        } else {
            throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
        }

        ValidatorUtil.require(jobItemsDto.getCharacterIdList(), "功能/特性List");
        ValidatorUtil.require(jobItemsDto.getManhour(), "工时");

        //对计划提交时间进行处理_当日17点为提交时间
        jobItemsDto.setPlanSubmissionTime(DateUtil.afternoon23_59pm(jobItemsDto.getPlanSubmissionTime()));

        //创建合并工单
        RdmsJobItem jobItem = CopyUtil.copy(jobItemsDto, RdmsJobItem.class);
        //将附件列表写入工单记录, 并创建工单
        RdmsAttachmentDto attachmentDto = new RdmsAttachmentDto();
        attachmentDto.setUse(FileGroupingEnum.JOBITEM.getCode());
        attachmentDto.setItemId(jobItem.getId());
        List<RdmsFileDto> fileDtos = rdmsFileService.listFileInfo(attachmentDto);
        List<String> fileIds = fileDtos.stream().map(RdmsFileDto::getId).collect(Collectors.toList());
        String fileIdsStr = JSONObject.toJSONString(fileIds);
        jobItem.setFileListStr(fileIdsStr); //创建工单时, 页面上提供的附件,作为工单附件; 以后,在过程处理过程中提供的附件将记录为节点附件, 相应的file表中的 itemId为Process的id

        jobItem.setProductManagerId(jobItemsDto.getProductManagerId());
       /* RdmsSysgmDto sysgmByCustomerId = rdmsSysgmService.getSysgmByCustomerId(jobItem.getCustomerId());
        if(!ObjectUtils.isEmpty(sysgmByCustomerId)){
            jobItem.setNextNode(sysgmByCustomerId.getSysgmId());
        }*/
        RdmsPreProject preProject = rdmsPreProjectService.selectByPrimaryKey(jobItemsDto.getPreProjectId());
        if(!ObjectUtils.isEmpty(preProject)){
            jobItem.setNextNode(preProject.getSystemManagerId());
        }
        jobItem.setStatus(JobItemStatusEnum.HANDLING.getStatus());
        {
            jobItem.setManhour(jobItemsDto.getStandManhour());
            jobItem.setStandManhourFee(jobItemsDto.getStandManhourFee());
        }
        jobItem.setType(JobItemTypeEnum.MERGE.getType());
        jobItem.setProjectType(jobItemsDto.getProjectType());
        String jsonString = JSON.toJSONString(jobItemsDto.getCharacterIdList()); //重要
        jobItem.setMergeCharacterIdStr(jsonString);
        String jobItemId = this.save(jobItem);
        //填写文件的访问权限人员
        if (!CollectionUtils.isEmpty(fileIds)) {
            {
                //设置文件授权 权限
                RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                roleUsersDto.setLoginUserId(jobItemsDto.getLoginUserId());
                roleUsersDto.setExecutorId(jobItemsDto.getExecutorId());
                roleUsersDto.setReceiverId(null);
                RdmsJobItem rdmsJobItem = this.selectByPrimaryKey(jobItemId);
                RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(rdmsJobItem.getCustomerId());
                roleUsersDto.setBossId(rdmsBossDto.getBossId());
                roleUsersDto.setSuperId(null);
                roleUsersDto.setIpmtId(null);
                roleUsersDto.setPjmId(jobItemsDto.getProjectManagerId());
                roleUsersDto.setPdmId(jobItemsDto.getProductManagerId());
                if(!ObjectUtils.isEmpty(jobItemsDto.getPreProjectId())){
                    RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(jobItemsDto.getPreProjectId());
                    if (!ObjectUtils.isEmpty(rdmsPreProject)) {
                        roleUsersDto.setSmId(rdmsPreProject.getSystemManagerId());
                    }
                }
                rdmsFileAuthService.setFileAuthUser(fileIds, roleUsersDto);
            }
        }

        {
            //创建一个工单流程节点, 用户保存节点信息
            RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
            jobItemProcess.setJobItemId(jobItemId);
            jobItemProcess.setExecutorId(jobItemsDto.getLoginUserId());
            jobItemProcess.setNextNode(jobItemsDto.getExecutorId());
            jobItemProcess.setJobDescription("功能合并");
            jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.ISSUE.getStatus()); //工单下发
            long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobItemId);
            jobItemProcess.setDeep((int) jobItemProcessCount);
            rdmsJobItemProcessService.save(jobItemProcess);
        }

        //获取被合并的功能, 将功能标记为被合并状态
        for (String characterId : jobItemsDto.getCharacterIdList()) {
            RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(characterId);
            rdmsCharacter.setStatus(CharacterStatusEnum.MERGED.getStatus());
            rdmsCharacterService.update(rdmsCharacter);

            //为原记录创建Process记录
            {
                RdmsCharacterProcess characterProcess = new RdmsCharacterProcess();
                characterProcess.setCharacterId(characterId);
                characterProcess.setExecutorId(jobItemsDto.getExecutorId());
                characterProcess.setNextNode(null);
                long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(characterId);
                characterProcess.setDeep((int) characterProcessCount);
                characterProcess.setJobDescription("功能被合并");
                characterProcess.setProcessStatus(CharacterProcessStatusEnum.COMPLETE.getStatus());
                rdmsCharacterProcessService.save(characterProcess);
            }
        }

        //创建合并的功能记录
        RdmsCharacter mergeCharacter = new RdmsCharacter();
        mergeCharacter.setId(null);
        mergeCharacter.setCharacterSerial(null);
        mergeCharacter.setParent(null);
        mergeCharacter.setDeep(0);
        mergeCharacter.setIterationVersion(1);
        mergeCharacter.setCustomerId(jobItemsDto.getCustomerId());
        mergeCharacter.setPreProjectId(jobItemsDto.getPreProjectId());
        RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(jobItemsDto.getPreProjectId());
        mergeCharacter.setProductManagerId(rdmsPreProject.getProductManagerId());
        mergeCharacter.setWriterId(jobItemsDto.getExecutorId());
        mergeCharacter.setNextNode(rdmsPreProject.getProductManagerId());
        mergeCharacter.setStatus(CharacterStatusEnum.SAVED.getStatus());
        mergeCharacter.setJobitemId(jobItemId);
        mergeCharacter.setJobitemType(JobItemTypeEnum.MERGE.getType());
        mergeCharacter.setProjectType(jobItemsDto.getProjectType());
        mergeCharacter.setMergedCharacterIdStr(JSON.toJSONString(jobItemsDto.getCharacterIdList()));
        String mergeCharacterId = rdmsCharacterService.save(mergeCharacter);

        //为原记录创建Process记录
        {
            RdmsCharacterProcess characterProcess = new RdmsCharacterProcess();
            characterProcess.setCharacterId(mergeCharacterId);
            characterProcess.setExecutorId(jobItemsDto.getExecutorId());
            characterProcess.setNextNode(mergeCharacter.getNextNode());
            long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(mergeCharacterId);
            characterProcess.setDeep((int) characterProcessCount);
            characterProcess.setJobDescription("创建功能合并记录");
            characterProcess.setProcessStatus(CharacterProcessStatusEnum.EDITING.getStatus());
            rdmsCharacterProcessService.save(characterProcess);
        }
    }

    @Transactional
    public void functionMerge(String copyCharacterId, RdmsJobItemDto jobItemDto) {
        if (ObjectUtils.isEmpty(copyCharacterId)) {
            throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
        }
        RdmsCharacter currentOperatingCharacter = rdmsCharacterService.selectByPrimaryKey(copyCharacterId);
        if (ObjectUtils.isEmpty(currentOperatingCharacter)) {
            throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
        }
        currentOperatingCharacter.setStatus(CharacterStatusEnum.MERGED.getStatus());
        rdmsCharacterService.updateByPrimaryKeySelective(currentOperatingCharacter);

        String jobItemId = currentOperatingCharacter.getJobitemId();
        RdmsJobItem jobitem_createCurrentCharacter = this.selectByPrimaryKey(jobItemId);

        //创建组件定义流转过程记录
        RdmsCharacterProcess characterProcess = new RdmsCharacterProcess();
        characterProcess.setCharacterId(currentOperatingCharacter.getId());
        characterProcess.setExecutorId(jobitem_createCurrentCharacter.getExecutorId());
        characterProcess.setNextNode(null);
        long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(currentOperatingCharacter.getId());
        characterProcess.setDeep((int) characterProcessCount);
        characterProcess.setJobDescription("组件定义被合并");
        characterProcess.setProcessStatus(CharacterProcessStatusEnum.COMPLETE.getStatus());
        rdmsCharacterProcessService.save(characterProcess);

        //处理工单状态
        //将编辑记录的characterId更新到jobitem中
        RdmsJobItem rdmsJobItem_update = this.selectByPrimaryKey(jobItemId);
        rdmsJobItem_update.setCharacterId(currentOperatingCharacter.getId());
        rdmsJobItem_update.setType(JobItemTypeEnum.MERGE.getType());
        rdmsJobItem_update.setAuxType(JobItemTypeEnum.FUNCTION.getType());
        this.update(rdmsJobItem_update);

        //获取被合并的功能, 将功能标记为被合并状态
        for (String characterId : jobItemDto.getCharacterIdList()) {
            //1. 将原记录拷贝一条新记录, jobItemType为function类型. parent记录为原功能记录
            RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(characterId);
            RdmsCharacter copyCharacter = CopyUtil.copy(rdmsCharacter, RdmsCharacter.class);
            copyCharacter.setId(null);
            copyCharacter.setParent(rdmsCharacter.getId());
            copyCharacter.setJobitemType(JobItemTypeEnum.FUNCTION.getType());
            copyCharacter.setJobitemId(jobItemId);  //关联的任务
            copyCharacter.setStatus(CharacterStatusEnum.MERGED.getStatus());
            String copyCharacterId2 = rdmsCharacterService.save(copyCharacter);
            //为history记录保存数据
            RdmsCharacterData characterData = rdmsCharacterService.getCharacterData(jobItemDto.getCharacterId());
            characterData.setId(copyCharacterId2);
            rdmsCharacterService.saveCharacterData(characterData);

            //为原记录创建Process记录
            {
                RdmsCharacterProcess characterProcess2 = new RdmsCharacterProcess();
                characterProcess2.setCharacterId(copyCharacterId2);
                characterProcess2.setExecutorId(jobItemDto.getExecutorId());  //功能的执行人是 任务单的目标人
                characterProcess2.setNextNode(null);
                long characterProcess2Count = rdmsCharacterProcessService.getCharacterProcessCount(characterId);
                characterProcess2.setDeep((int) characterProcess2Count);
                characterProcess2.setJobDescription("功能被合并");
                characterProcess2.setProcessStatus(CharacterProcessStatusEnum.COMPLETE.getStatus());
                rdmsCharacterProcessService.save(characterProcess2);
            }
        }

        List<RdmsCharacterDto> characterList = rdmsCharacterService.getCharacterListByFunctionJobitemId(jobItemId);
        List<String> collect1 = characterList.stream().map(RdmsCharacterDto::getId).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(characterList)){
            //创建合并的功能记录
            RdmsCharacter mergeCharacter = new RdmsCharacter();
            mergeCharacter.setId(null);
            mergeCharacter.setCharacterSerial(null);
            mergeCharacter.setParent(null);
            mergeCharacter.setDeep(0);
            mergeCharacter.setCharacterName(characterList.get(0).getCharacterName());
            mergeCharacter.setIterationVersion(1);
            mergeCharacter.setCustomerId(jobItemDto.getCustomerId());
            mergeCharacter.setPreProjectId(jobItemDto.getPreProjectId());
            RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(jobItemDto.getPreProjectId());
            mergeCharacter.setProductManagerId(rdmsPreProject.getProductManagerId());
            mergeCharacter.setWriterId(jobItemDto.getExecutorId());
//            RdmsSysgmDto sysgmByCustomerId = rdmsSysgmService.getSysgmByCustomerId(jobItemDto.getCustomerId());
            RdmsPreProject rdmsPreProject1 = rdmsPreProjectService.selectByPrimaryKey(jobItemDto.getPreProjectId());
            mergeCharacter.setNextNode(rdmsPreProject1.getSystemManagerId());
            mergeCharacter.setStatus(CharacterStatusEnum.SAVED.getStatus());
            mergeCharacter.setJobitemId(jobItemId);
            mergeCharacter.setJobitemType(JobItemTypeEnum.MERGE.getType());
            mergeCharacter.setProjectType(jobItemDto.getProjectType());
            mergeCharacter.setMergedCharacterIdStr(JSON.toJSONString(collect1));
            String mergeCharacterId = rdmsCharacterService.save(mergeCharacter);

            //更新工单
            String jsonString = JSON.toJSONString(collect1);
            rdmsJobItem_update.setMergeCharacterIdStr(jsonString);
            this.update(rdmsJobItem_update);

            //为原记录创建Process记录
            {
                RdmsCharacterProcess characterProcess3 = new RdmsCharacterProcess();
                characterProcess3.setCharacterId(mergeCharacterId);
                characterProcess3.setExecutorId(jobItemDto.getExecutorId());
                characterProcess3.setNextNode(mergeCharacter.getNextNode());
                long characterProcess3Count = rdmsCharacterProcessService.getCharacterProcessCount(mergeCharacterId);
                characterProcess3.setDeep((int) characterProcess3Count);
                characterProcess3.setJobDescription("创建功能合并记录");
                characterProcess3.setProcessStatus(CharacterProcessStatusEnum.EDITING.getStatus());
                rdmsCharacterProcessService.save(characterProcess3);
            }
        }
    }

    @Transactional
    public void decomposeDealWith(RdmsJobItemDto jobItemsDto) throws ParseException {
        ProjectTypeEnum projectType = ProjectTypeEnum.getProjectTypeEnumByType(jobItemsDto.getProjectType());
        switch (Objects.requireNonNull(projectType)) {
            case PRE_PROJECT: {
                ValidatorUtil.require(jobItemsDto.getPreProjectId(), "预立项项目ID");
                ValidatorUtil.require(jobItemsDto.getManhour(), "工时");
                ValidatorUtil.require(jobItemsDto.getCharacterId(), "功能/特性Id");
                //对计划提交时间进行处理_当日17点为提交时间
                jobItemsDto.setPlanSubmissionTime(DateUtil.afternoon23_59pm(jobItemsDto.getPlanSubmissionTime()));
                RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(jobItemsDto.getPreProjectId());
                jobItemsDto.setProductManagerId(rdmsPreProject.getProductManagerId());

                //获得原来的Character记录
                RdmsCharacter currentOperatingCharacter = rdmsCharacterService.selectByPrimaryKey(jobItemsDto.getCharacterId());
                if (ObjectUtils.isEmpty(currentOperatingCharacter)) {
                    throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                }

                String decomposeOriginalCharacterId = "";
                //如果对修订提交的Character做分解, 首先将当前工单处理的Character处理成完成修订的状态
                if (currentOperatingCharacter.getStatus().equals(CharacterStatusEnum.SUBMIT.getStatus()) && currentOperatingCharacter.getJobitemType().equals(JobItemTypeEnum.ITERATION.getType())) {
                    //再次迭代工单
                    //1. 复制一条上一次迭代的记录
                    String iterationRecordId = currentOperatingCharacter.getId();
                    RdmsCharacter tempCharacter = CopyUtil.copy(currentOperatingCharacter, RdmsCharacter.class);

                    //2. 将上次迭代的记录标记为discard状态
                    tempCharacter.setStatus(CharacterStatusEnum.DISCARD.getStatus());
                    {//将相应的任务单标记为EVALUATE状态, 防止发生错误
                        RdmsJobItem rdmsJobItem = this.selectByPrimaryKey(tempCharacter.getJobitemId());
                        rdmsJobItem.setStatus(JobItemStatusEnum.EVALUATE.getStatus());
                        this.update(rdmsJobItem);

                        //注意:这里不需要创建process记录, 此处, 仅用于提前对状态进行标记, 以免发生错误, 后续再函数 setJobitemInfoAtDemandApproveOrIteDecJobSubmit 中,还会重新处理状态,并添加process记录
                    }
                    tempCharacter.setParent(null);
//                                tempCharacter.setDeep(null);
                    rdmsCharacterService.update(tempCharacter);  //将上一次更新的记录作废

                    //3. 将上一条迭代的记录作为"原始记录"
                    currentOperatingCharacter.setId(currentOperatingCharacter.getCharacterSerial());  //将修订记录作为原记录
                    currentOperatingCharacter.setStatus(CharacterStatusEnum.APPROVED.getStatus());   //处理成已经审批的状态
                    currentOperatingCharacter.setJobitemType(JobItemTypeEnum.ITERATION.getType());
                    rdmsCharacterService.update(currentOperatingCharacter);  //更新原始记录为上一次修订的结果
                    //为分解任务执行人添加原功能附件的下载权限
                    //填写文件的访问权限人员
                    if (!ObjectUtils.isEmpty(currentOperatingCharacter.getFileListStr())) {
                        List<String> fileIdList = JSON.parseArray(currentOperatingCharacter.getFileListStr(), String.class);
                        if (!CollectionUtils.isEmpty(fileIdList)) {
                            {
                                //设置文件授权 权限
                                RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                                roleUsersDto.setLoginUserId(jobItemsDto.getLoginUserId());
                                roleUsersDto.setExecutorId(jobItemsDto.getExecutorId());
                                roleUsersDto.setReceiverId(null);
                                RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(currentOperatingCharacter.getCustomerId());
                                roleUsersDto.setBossId(rdmsBossDto.getBossId());
                                roleUsersDto.setSuperId(null);
                                roleUsersDto.setIpmtId(null);
                                roleUsersDto.setPjmId(null);
                                roleUsersDto.setPdmId(jobItemsDto.getProductManagerId());
                                if(!ObjectUtils.isEmpty(jobItemsDto.getPreProjectId())){
                                    if (!ObjectUtils.isEmpty(rdmsPreProject)) {
                                        roleUsersDto.setSmId(rdmsPreProject.getSystemManagerId());
                                    }
                                }
                                rdmsFileAuthService.setFileAuthUser(fileIdList, roleUsersDto);
                            }
                        }
                    }

                    //将修订记录的数据也设定为原记录数据
                    RdmsCharacterData characterData = rdmsCharacterService.getCharacterData(iterationRecordId);
                    characterData.setId(currentOperatingCharacter.getCharacterSerial());
                    rdmsCharacterService.saveCharacterData(characterData);

                    decomposeOriginalCharacterId = currentOperatingCharacter.getCharacterSerial();
                } else {
                    decomposeOriginalCharacterId = jobItemsDto.getCharacterId();
                }

                //处理被分解记录状态
                RdmsCharacter character = new RdmsCharacter();
                character.setId(decomposeOriginalCharacterId);
                character.setStatus(CharacterStatusEnum.DECOMPOSED.getStatus());
                rdmsCharacterService.updateByPrimaryKeySelective(character);

                //创建组件定义流转过程记录
                RdmsCharacterProcess characterProcess = new RdmsCharacterProcess();
                characterProcess.setCharacterId(character.getId());
                characterProcess.setExecutorId(jobItemsDto.getExecutorId());
                characterProcess.setNextNode(null);
                long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(decomposeOriginalCharacterId);
                characterProcess.setDeep((int) characterProcessCount);
                characterProcess.setJobDescription("组件定义被分解");
                characterProcess.setProcessStatus(CharacterProcessStatusEnum.DECOMPOSE.getStatus());
                rdmsCharacterProcessService.save(characterProcess);

                //处理工单
                RdmsJobItem jobItem = CopyUtil.copy(jobItemsDto, RdmsJobItem.class);
                //将附件列表写入工单记录, 并创建工单
                RdmsAttachmentDto attachmentDto = new RdmsAttachmentDto();
                attachmentDto.setUse(FileGroupingEnum.JOBITEM.getCode());
                attachmentDto.setItemId(jobItem.getId());
                List<RdmsFileDto> fileDtos = rdmsFileService.listFileInfo(attachmentDto);
                List<String> fileIds = fileDtos.stream().map(RdmsFileDto::getId).collect(Collectors.toList());
                String fileIdsStr = JSONObject.toJSONString(fileIds);
                jobItem.setFileListStr(fileIdsStr); //创建工单时, 页面上提供的附件,作为工单附件; 以后,在过程处理过程中提供的附件将记录为节点附件, 相应的file表中的 itemId为Process的id

                //下一个节点是系统工程师
//                RdmsSysgmDto sysgmByCustomerId = rdmsSysgmService.getSysgmByCustomerId(jobItem.getCustomerId());
                RdmsPreProject preProject = rdmsPreProjectService.selectByPrimaryKey(jobItemsDto.getPreProjectId());
                if(!ObjectUtils.isEmpty(preProject)){
                    jobItem.setNextNode(preProject.getSystemManagerId());
                }else{
                    jobItem.setNextNode(jobItemsDto.getProductManagerId());
                }
                jobItem.setCharacterId(decomposeOriginalCharacterId);  //注意: 如果对一条迭代工单进行分解, 发出指令时, 对应的迭代工单其实是用于临时编辑的工单, 迭代后就discard了, 所以, 创建工单时, 要将CharacterID改为迭代的原始工单
                jobItem.setStatus(JobItemStatusEnum.HANDLING.getStatus());
                {
                    jobItem.setManhour(jobItemsDto.getStandManhour());
                    jobItem.setStandManhourFee(jobItemsDto.getStandManhourFee());
                }
                jobItem.setProjectType(jobItemsDto.getProjectType());
                String jobItemId = this.save(jobItem);
                //填写文件的访问权限人员
                if (!CollectionUtils.isEmpty(fileIds)) {
                    {
                        //设置文件授权 权限
                        RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                        roleUsersDto.setLoginUserId(jobItemsDto.getLoginUserId());
                        roleUsersDto.setExecutorId(jobItemsDto.getExecutorId());
                        roleUsersDto.setReceiverId(null);
                        RdmsJobItem rdmsJobItem = this.selectByPrimaryKey(jobItemId);
                        RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(rdmsJobItem.getCustomerId());
                        roleUsersDto.setBossId(rdmsBossDto.getBossId());
                        roleUsersDto.setSuperId(null);
                        roleUsersDto.setIpmtId(null);
                        roleUsersDto.setPjmId(null);
                        roleUsersDto.setPdmId(jobItemsDto.getProductManagerId());
                        if(!ObjectUtils.isEmpty(jobItemsDto.getPreProjectId())){
                            if (!ObjectUtils.isEmpty(rdmsPreProject)) {
                                roleUsersDto.setSmId(rdmsPreProject.getSystemManagerId());
                            }
                        }
                        rdmsFileAuthService.setFileAuthUser(fileIds, roleUsersDto);
                    }
                }

                //创建一个工单流程节点, 用户保存节点信息
                RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                jobItemProcess.setJobItemId(jobItemId);
                jobItemProcess.setExecutorId(jobItemsDto.getLoginUserId());
                jobItemProcess.setNextNode(jobItem.getExecutorId());
                jobItemProcess.setJobDescription("请完成功能分解工单任务");
                jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.ISSUE.getStatus()); //工单下发
                long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobItemId);
                jobItemProcess.setDeep((int) jobItemProcessCount);
                rdmsJobItemProcessService.save(jobItemProcess);

                //判断被迭代的Character对应的 工单, 是否全部Character都已经approve或者已经处于history状态, 如果是的, 进入工单评价过程
                //查看工单下的所有Character, 如果所有的Character都已经处于approve状态, 或者ITERATING状态, 则设置工单为完成转态, 否则不做处理
                String jobitemId_createCurrentCharacter = currentOperatingCharacter.getJobitemId();

                this.setJobitemInfoAtDemandApproveOrIteDecJobSubmit(jobitemId_createCurrentCharacter);
                break;
            }
            case DEV_PROJECT: {
                ValidatorUtil.require(jobItemsDto.getSubprojectId(), "项目ID");
                RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(jobItemsDto.getSubprojectId());
                jobItemsDto.setProductManagerId(subproject.getProductManagerId());
                ValidatorUtil.require(jobItemsDto.getCharacterId(), "功能/特性Id");

                //对计划提交时间进行处理_当日17点为提交时间
                jobItemsDto.setPlanSubmissionTime(DateUtil.afternoon23_59pm(jobItemsDto.getPlanSubmissionTime()));

                String decomposeOriginalCharacterId = "";
                //获得原来的Character记录
                RdmsCharacter currentOperatingCharacter = rdmsCharacterService.selectByPrimaryKey(jobItemsDto.getCharacterId());
                if (ObjectUtils.isEmpty(currentOperatingCharacter)) {
                    throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                }
                //为分解任务执行人添加原功能附件的下载权限
                //填写文件的访问权限人员
                if (!ObjectUtils.isEmpty(currentOperatingCharacter.getFileListStr())) {
                    List<String> fileIdList = JSON.parseArray(currentOperatingCharacter.getFileListStr(), String.class);
                    if (!CollectionUtils.isEmpty(fileIdList)) {
                        {
                            //设置文件授权 权限
                            RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                            roleUsersDto.setLoginUserId(jobItemsDto.getLoginUserId());
                            roleUsersDto.setExecutorId(jobItemsDto.getExecutorId());
                            roleUsersDto.setReceiverId(null);
                            RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(currentOperatingCharacter.getCustomerId());
                            roleUsersDto.setBossId(rdmsBossDto.getBossId());
                            roleUsersDto.setSuperId(null);
                            roleUsersDto.setIpmtId(null);
                            roleUsersDto.setPjmId(null);
                            roleUsersDto.setPdmId(jobItemsDto.getProductManagerId());
                            if(!ObjectUtils.isEmpty(jobItemsDto.getPreProjectId())){
                                RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(jobItemsDto.getPreProjectId());
                                if (!ObjectUtils.isEmpty(rdmsPreProject)) {
                                    roleUsersDto.setSmId(rdmsPreProject.getSystemManagerId());
                                }
                            }
                            rdmsFileAuthService.setFileAuthUser(fileIdList, roleUsersDto);
                        }
                    }
                }

                decomposeOriginalCharacterId = jobItemsDto.getCharacterId();

                //更新被分解记录状态
                RdmsCharacter character = new RdmsCharacter();
                character.setId(decomposeOriginalCharacterId);
                character.setStatus(CharacterStatusEnum.DECOMPOSED.getStatus());
                rdmsCharacterService.updateByPrimaryKeySelective(character);

                //创建组件定义流转过程记录
                RdmsCharacterProcess characterProcess = new RdmsCharacterProcess();
                characterProcess.setCharacterId(character.getId());
                characterProcess.setExecutorId(jobItemsDto.getExecutorId());
                characterProcess.setNextNode(null);
                long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(jobItemsDto.getCharacterId());
                characterProcess.setDeep((int) characterProcessCount);
                characterProcess.setJobDescription("组件定义被分解");
                characterProcess.setProcessStatus(CharacterProcessStatusEnum.DECOMPOSE.getStatus());
                rdmsCharacterProcessService.save(characterProcess);

                //创建分解工单
                RdmsJobItem jobItem = CopyUtil.copy(jobItemsDto, RdmsJobItem.class);
                //将附件列表写入工单记录, 并创建工单
                RdmsAttachmentDto attachmentDto = new RdmsAttachmentDto();
                attachmentDto.setUse(FileGroupingEnum.JOBITEM.getCode());
                attachmentDto.setItemId(jobItem.getId());
                List<RdmsFileDto> fileDtos = rdmsFileService.listFileInfo(attachmentDto);
                List<String> fileIds = fileDtos.stream().map(RdmsFileDto::getId).collect(Collectors.toList());
                String fileIdsStr = JSONObject.toJSONString(fileIds);
                jobItem.setFileListStr(fileIdsStr); //创建工单时, 页面上提供的附件,作为工单附件; 以后,在过程处理过程中提供的附件将记录为节点附件, 相应的file表中的 itemId为Process的id

                jobItem.setProjectManagerId(subproject.getProjectManagerId());
                jobItem.setNextNode(jobItemsDto.getLoginUserId());  //下一节点是当前发工单的人

                jobItem.setCharacterId(decomposeOriginalCharacterId);  //注意: 如果对一条迭代工单进行分解, 发出指令时, 对应的迭代工单其实是用于临时编辑的工单, 迭代后就discard了, 所以, 创建工单时, 要将CharacterID改为迭代的原始工单
                jobItem.setStatus(JobItemStatusEnum.HANDLING.getStatus());
                {
                    jobItem.setManhour(jobItemsDto.getStandManhour());
                    jobItem.setStandManhourFee(jobItemsDto.getStandManhourFee());
                }
                jobItem.setProjectType(jobItemsDto.getProjectType());
                String jobItemId = this.save(jobItem);
                //填写文件的访问权限人员
                if (!CollectionUtils.isEmpty(fileIds)) {
                    {
                        //设置文件授权 权限
                        RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                        roleUsersDto.setLoginUserId(jobItemsDto.getLoginUserId());
                        roleUsersDto.setExecutorId(jobItemsDto.getExecutorId());
                        roleUsersDto.setReceiverId(null);
                        RdmsJobItem rdmsJobItem = this.selectByPrimaryKey(jobItemId);
                        RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(rdmsJobItem.getCustomerId());
                        roleUsersDto.setBossId(rdmsBossDto.getBossId());
                        roleUsersDto.setSuperId(null);
                        roleUsersDto.setIpmtId(null);
                        roleUsersDto.setPjmId(jobItemsDto.getProjectManagerId());
                        roleUsersDto.setPdmId(jobItemsDto.getProductManagerId());
                        if(!ObjectUtils.isEmpty(jobItemsDto.getPreProjectId())){
                            RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(jobItemsDto.getPreProjectId());
                            if (!ObjectUtils.isEmpty(rdmsPreProject)) {
                                roleUsersDto.setSmId(rdmsPreProject.getSystemManagerId());
                            }
                        }
                        rdmsFileAuthService.setFileAuthUser(fileIds, roleUsersDto);
                    }
                }

                //创建一个工单流程节点, 用户保存节点信息
                RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                jobItemProcess.setJobItemId(jobItemId);
                jobItemProcess.setExecutorId(jobItemsDto.getLoginUserId());
                jobItemProcess.setNextNode(jobItemsDto.getExecutorId());
                jobItemProcess.setJobDescription("请完成功能分解工单任务");
                jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.ISSUE.getStatus()); //工单下发
                long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobItemId);
                jobItemProcess.setDeep((int) jobItemProcessCount);
                rdmsJobItemProcessService.save(jobItemProcess);
                break;
            }
            default: {
                throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
            }
        }
    }

    @Transactional
    public void functionDecompose(String copyCharacterId) {
        if (ObjectUtils.isEmpty(copyCharacterId)) {
            throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
        }
        RdmsCharacter currentOperatingCharacter = rdmsCharacterService.selectByPrimaryKey(copyCharacterId);
        currentOperatingCharacter.setStatus(CharacterStatusEnum.DECOMPOSED.getStatus());
        rdmsCharacterService.updateByPrimaryKeySelective(currentOperatingCharacter);

        String jobItemId = currentOperatingCharacter.getJobitemId();
        RdmsJobItem jobitem_createCurrentCharacter = this.selectByPrimaryKey(jobItemId);

        if (ObjectUtils.isEmpty(currentOperatingCharacter)) {
            throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
        }

        //创建组件定义流转过程记录
        RdmsCharacterProcess characterProcess = new RdmsCharacterProcess();
        characterProcess.setCharacterId(currentOperatingCharacter.getId());
        characterProcess.setExecutorId(jobitem_createCurrentCharacter.getExecutorId());
        characterProcess.setNextNode(null);
        long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(currentOperatingCharacter.getId());
        characterProcess.setDeep((int) characterProcessCount);
        characterProcess.setJobDescription("组件定义被分解");
        characterProcess.setProcessStatus(CharacterProcessStatusEnum.DECOMPOSE.getStatus());
        rdmsCharacterProcessService.save(characterProcess);

        //处理工单状态
        //将编辑记录的characterId更新到jobitem中
        RdmsJobItem rdmsJobItem_update = this.selectByPrimaryKey(jobItemId);
        rdmsJobItem_update.setCharacterId(currentOperatingCharacter.getId());
        rdmsJobItem_update.setType(JobItemTypeEnum.DECOMPOSE.getType());
        rdmsJobItem_update.setAuxType(JobItemTypeEnum.FUNCTION.getType());
        this.update(rdmsJobItem_update);

    }


    @Transactional
    public void demandDealWith(RdmsJobItemDto jobItemsDto) throws ParseException {
        ValidatorUtil.require(jobItemsDto.getPreProjectId(), "预立项项目ID");
        ValidatorUtil.require(jobItemsDto.getDemandIdStr(), "原始需求Id");
        RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(jobItemsDto.getPreProjectId());
        jobItemsDto.setProductManagerId(rdmsPreProject.getProductManagerId());
        //对计划提交时间进行处理_当日17点为提交时间
        jobItemsDto.setPlanSubmissionTime(DateUtil.afternoon23_59pm(jobItemsDto.getPlanSubmissionTime()));

        RdmsJobItem jobItem = CopyUtil.copy(jobItemsDto, RdmsJobItem.class);
        //将附件列表写入工单记录, 并创建工单
        RdmsAttachmentDto attachmentDto = new RdmsAttachmentDto();
        attachmentDto.setUse(FileGroupingEnum.JOBITEM.getCode());
        attachmentDto.setItemId(jobItem.getId());
        List<RdmsFileDto> fileDtos = rdmsFileService.listFileInfo(attachmentDto);
        List<String> fileIds = fileDtos.stream().map(RdmsFileDto::getId).collect(Collectors.toList());
        String fileIdsStr = JSONObject.toJSONString(fileIds);
        jobItem.setFileListStr(fileIdsStr); //创建工单时, 页面上提供的附件,作为工单附件; 以后,在过程处理过程中提供的附件将记录为节点附件, 相应的file表中的 itemId为Process的id
        //除以下两个字段外, 其他内容字段都需要前端给出
//        RdmsSysgmDto sysgmByCustomerId = rdmsSysgmService.getSysgmByCustomerId(jobItem.getCustomerId());
//        if(!ObjectUtils.isEmpty(sysgmByCustomerId)){
//            jobItem.setNextNode(sysgmByCustomerId.getSysgmId());
//        }
        RdmsPreProject preProject = rdmsPreProjectService.selectByPrimaryKey(jobItemsDto.getPreProjectId());
        if(!ObjectUtils.isEmpty(preProject)){
            jobItem.setNextNode(preProject.getSystemManagerId());
        }
        jobItem.setStatus(JobItemStatusEnum.HANDLING.getStatus());
        {
            jobItem.setManhour(jobItemsDto.getStandManhour());
            jobItem.setStandManhourFee(jobItemsDto.getStandManhourFee());
        }
        jobItem.setProjectType(jobItemsDto.getProjectType());
        String jobItemId = this.save(jobItem);
        //填写文件的访问权限人员
        if (!CollectionUtils.isEmpty(fileIds)) {
            {
                //设置文件授权 权限
                RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                roleUsersDto.setLoginUserId(jobItemsDto.getLoginUserId());
                roleUsersDto.setExecutorId(jobItemsDto.getExecutorId());
                roleUsersDto.setReceiverId(null);
                RdmsJobItem rdmsJobItem = this.selectByPrimaryKey(jobItemId);
                RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(rdmsJobItem.getCustomerId());
                roleUsersDto.setBossId(rdmsBossDto.getBossId());
                roleUsersDto.setSuperId(null);
                roleUsersDto.setIpmtId(null);
                roleUsersDto.setPjmId(null);
                roleUsersDto.setPdmId(rdmsPreProject.getProductManagerId());
                if(!ObjectUtils.isEmpty(jobItemsDto.getPreProjectId())){
                    if (!ObjectUtils.isEmpty(rdmsPreProject)) {
                        roleUsersDto.setSmId(rdmsPreProject.getSystemManagerId());
                    }
                }
                rdmsFileAuthService.setFileAuthUser(fileIds, roleUsersDto);
            }
        }

        //创建一个工单流程节点, 用户保存节点信息
        RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
        jobItemProcess.setJobItemId(jobItemId);
        jobItemProcess.setExecutorId(jobItemsDto.getLoginUserId());
        jobItemProcess.setNextNode(jobItem.getExecutorId());
        jobItemProcess.setJobDescription("请根据原始需求进行功能/特性定义");
        jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.ISSUE.getStatus()); //工单下发
        long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobItemId);
        jobItemProcess.setDeep((int) jobItemProcessCount);
        rdmsJobItemProcessService.save(jobItemProcess);

        //更新Demand状态
        RdmsDemand demandDiscern = new RdmsDemand();
        List<String> demandList = JSON.parseArray(jobItemsDto.getDemandIdStr(), String.class);
        if (!CollectionUtils.isEmpty(demandList) && !jobItemsDto.getDemandIdStr().equals("[]")) {
            for (String id : demandList) {
                demandDiscern.setId(id);
                demandDiscern.setStatus(DemandStatusEnum.HANDLING.getStatus());
                demandDiscern.setJobitemId(jobItemId);  // 这一行要注意,不要落下
                rdmsDemandService.updateByPrimaryKeySelective(demandDiscern);
            }
        }
    }

    @Transactional
    public void qualityDealWith(RdmsJobItemDto jobItemsDto) throws ParseException {
        ValidatorUtil.require(jobItemsDto.getQualityId(), "质量问题ID");
        ValidatorUtil.require(jobItemsDto.getSubprojectId(), "项目");
        ValidatorUtil.require(jobItemsDto.getProductManagerId(), "产品经理");

        //对计划提交时间进行处理_当日17点为提交时间
        jobItemsDto.setPlanSubmissionTime(DateUtil.afternoon23_59pm(jobItemsDto.getPlanSubmissionTime()));

        RdmsJobItem jobItem = CopyUtil.copy(jobItemsDto, RdmsJobItem.class);
        //将附件列表写入工单记录, 并创建工单
        RdmsAttachmentDto attachmentDto = new RdmsAttachmentDto();
        attachmentDto.setUse(FileGroupingEnum.JOBITEM.getCode());
        attachmentDto.setItemId(jobItem.getId());
        List<RdmsFileDto> fileDtos = rdmsFileService.listFileInfo(attachmentDto);
        List<String> fileIds = fileDtos.stream().map(RdmsFileDto::getId).collect(Collectors.toList());
        String fileIdsStr = JSONObject.toJSONString(fileIds);
        jobItem.setFileListStr(fileIdsStr); //创建工单时, 页面上提供的附件,作为工单附件; 以后,在过程处理过程中提供的附件将记录为节点附件, 相应的file表中的 itemId为Process的id

        jobItem.setNextNode(jobItemsDto.getProductManagerId());
        jobItem.setStatus(JobItemStatusEnum.HANDLING.getStatus());
        {
            jobItem.setManhour(jobItemsDto.getStandManhour());
            jobItem.setStandManhourFee(jobItemsDto.getStandManhourFee());
        }
        jobItem.setProjectType(jobItemsDto.getProjectType());
        String jobItemId = this.save(jobItem);
        //填写文件的访问权限人员
        if (!CollectionUtils.isEmpty(fileIds)) {
            {
                //设置文件授权 权限
                RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                if (!ObjectUtils.isEmpty(jobItemsDto.getLoginUserId())) {
                    roleUsersDto.setLoginUserId(jobItemsDto.getExecutorId());
                }
                roleUsersDto.setReceiverId(null);
                RdmsJobItem rdmsJobItem = this.selectByPrimaryKey(jobItemId);
                RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(rdmsJobItem.getCustomerId());
                roleUsersDto.setBossId(rdmsBossDto.getBossId());
                roleUsersDto.setSuperId(null);
                roleUsersDto.setIpmtId(null);
                roleUsersDto.setPjmId(null);
                if (!ObjectUtils.isEmpty(jobItemsDto.getProductManagerId())) {
                    roleUsersDto.setPdmId(jobItemsDto.getProductManagerId());
                }
                if(!ObjectUtils.isEmpty(jobItemsDto.getPreProjectId())){
                    RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(jobItemsDto.getPreProjectId());
                    if (!ObjectUtils.isEmpty(rdmsPreProject)) {
                        roleUsersDto.setSmId(rdmsPreProject.getSystemManagerId());
                    }
                }
                rdmsFileAuthService.setFileAuthUser(fileIds, roleUsersDto);
            }
        }

        //创建一个工单流程节点, 用户保存节点信息
        RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
        jobItemProcess.setJobItemId(jobItemId);
        jobItemProcess.setExecutorId(jobItemsDto.getLoginUserId());
        jobItemProcess.setNextNode(jobItem.getExecutorId());
        jobItemProcess.setJobDescription("对质量问题进行处理.");
        jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.ISSUE.getStatus()); //工单下发
        long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobItemId);
        jobItemProcess.setDeep((int) jobItemProcessCount);
        rdmsJobItemProcessService.save(jobItemProcess);

        //更新quality表状态
        RdmsQuality quality = new RdmsQuality();
        quality.setId(jobItemsDto.getQualityId());
        quality.setStatus(QualityStatusEnum.HANDLING.getStatus());
        quality.setJobitemId(jobItemId);
        quality.setProjectId(jobItemsDto.getProjectId());
        quality.setSubprojectId(jobItemsDto.getSubprojectId());  //最终是哪个项目和功能的问题,由产品经理下发工单的时候决定
        quality.setAssociateProjectStr(jobItemsDto.getAssociateProjectStr());
        quality.setCharacterId(jobItemsDto.getCharacterId());
        quality.setExecutorId(jobItemsDto.getExecutorId());
        quality.setUpdateTime(new Date());
        rdmsQualityService.updateByPrimaryKeySelective(quality);
    }

    @Transactional
    public void bugfeedbackDealWith(RdmsJobItemDto jobItemsDto) throws ParseException {
        ValidatorUtil.require(jobItemsDto.getQualityId(), "缺陷反馈ID");  //复用qualityId字段
        ValidatorUtil.require(jobItemsDto.getSubprojectId(), "项目");
        ValidatorUtil.require(jobItemsDto.getTestManagerId(), "测试主管");

        //对计划提交时间进行处理_当日17点为提交时间
        jobItemsDto.setPlanSubmissionTime(DateUtil.afternoon23_59pm(jobItemsDto.getPlanSubmissionTime()));

        RdmsJobItem jobItem = CopyUtil.copy(jobItemsDto, RdmsJobItem.class);
        //将附件列表写入工单记录, 并创建工单
        RdmsAttachmentDto attachmentDto = new RdmsAttachmentDto();
        attachmentDto.setUse(FileGroupingEnum.JOBITEM.getCode());
        attachmentDto.setItemId(jobItem.getId());
        List<RdmsFileDto> fileDtos = rdmsFileService.listFileInfo(attachmentDto);
        List<String> fileIds = fileDtos.stream().map(RdmsFileDto::getId).collect(Collectors.toList());
        String fileIdsStr = JSONObject.toJSONString(fileIds);
        jobItem.setFileListStr(fileIdsStr); //创建工单时, 页面上提供的附件,作为工单附件; 以后,在过程处理过程中提供的附件将记录为节点附件, 相应的file表中的 itemId为Process的id

        jobItem.setNextNode(jobItemsDto.getTestManagerId());
        jobItem.setStatus(JobItemStatusEnum.HANDLING.getStatus());
        {
            jobItem.setManhour(jobItemsDto.getStandManhour());
            jobItem.setStandManhourFee(jobItemsDto.getStandManhourFee());
        }
        jobItem.setProjectType(jobItemsDto.getProjectType());
        RdmsProjectSubproject rdmsProjectSubproject1 = rdmsSubprojectService.selectByPrimaryKey(jobItemsDto.getSubprojectId());
        jobItem.setProjectManagerId(rdmsProjectSubproject1 != null ? rdmsProjectSubproject1.getProjectManagerId() : null);
        jobItem.setProductManagerId(rdmsProjectSubproject1 != null ? rdmsProjectSubproject1.getProductManagerId() : null);
        String jobItemId = this.save(jobItem);
        //填写文件的访问权限人员
        if (!CollectionUtils.isEmpty(fileIds)) {
            {
                //设置文件授权 权限
                RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                if (!ObjectUtils.isEmpty(jobItemsDto.getLoginUserId())) {
                    roleUsersDto.setLoginUserId(jobItemsDto.getExecutorId());
                }
                roleUsersDto.setReceiverId(null);
                RdmsJobItem rdmsJobItem = this.selectByPrimaryKey(jobItemId);
                RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(rdmsJobItem.getCustomerId());
                roleUsersDto.setBossId(rdmsBossDto.getBossId());
                roleUsersDto.setSuperId(null);
                RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(jobItem.getProjectId());
                if (!ObjectUtils.isEmpty(rdmsProject)) {
                    roleUsersDto.setIpmtId(rdmsProject.getSupervise());
                }
                roleUsersDto.setMainPjmId(rdmsProject.getProjectManagerId());
                RdmsProjectSubproject rdmsProjectSubproject = rdmsSubprojectService.selectByPrimaryKey(jobItem.getSubprojectId());
                roleUsersDto.setPjmId(rdmsProjectSubproject != null ? rdmsProjectSubproject.getProjectManagerId() : null);
                roleUsersDto.setTmId(jobItemsDto.getTestManagerId());
                RdmsTgmDto tgmByCustomerId = rdmsTgmService.getTgmByCustomerId(jobItem.getCustomerId());
                roleUsersDto.setTgmId(tgmByCustomerId != null ? tgmByCustomerId.getTgmId() : null);
                roleUsersDto.setPdmId(null);
                if(!ObjectUtils.isEmpty(jobItemsDto.getPreProjectId())){
                    RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(jobItemsDto.getPreProjectId());
                    if (!ObjectUtils.isEmpty(rdmsPreProject)) {
                        roleUsersDto.setSmId(rdmsPreProject.getSystemManagerId());
                    }
                }
                rdmsFileAuthService.setFileAuthUser(fileIds, roleUsersDto);
            }
        }

        //创建一个工单流程节点, 用户保存节点信息
        RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
        jobItemProcess.setJobItemId(jobItemId);
        jobItemProcess.setExecutorId(jobItemsDto.getLoginUserId());
        jobItemProcess.setNextNode(jobItem.getExecutorId());
        jobItemProcess.setJobDescription("对缺陷问题进行处理.");
        jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.ISSUE.getStatus()); //工单下发
        long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobItemId);
        jobItemProcess.setDeep((int) jobItemProcessCount);
        rdmsJobItemProcessService.save(jobItemProcess);

        //更新bugfeedback表状态
        RdmsBugFeedback bugfeedback = new RdmsBugFeedback();
        bugfeedback.setId(jobItemsDto.getQualityId());
        bugfeedback.setStatus(QualityStatusEnum.HANDLING.getStatus());
        bugfeedback.setJobitemId(jobItemId);
        bugfeedback.setProjectId(jobItemsDto.getProjectId());
        bugfeedback.setSubprojectId(jobItemsDto.getSubprojectId());  //最终是哪个项目和功能的问题,由产品经理下发工单的时候决定
        bugfeedback.setAssociateProjectStr(jobItemsDto.getAssociateProjectStr());
        bugfeedback.setCharacterId(jobItemsDto.getCharacterId());
        bugfeedback.setExecutorId(jobItemsDto.getExecutorId());
        bugfeedback.setUpdateTime(new Date());
        rdmsBugFeedbackService.updateByPrimaryKeySelective(bugfeedback);
    }

    @Transactional
    public void suggestDealWith(RdmsJobItemDto jobItemsDto) throws ParseException {
        ValidatorUtil.require(jobItemsDto.getSubprojectId(), "项目");
        ValidatorUtil.require(jobItemsDto.getPlanSubmissionTime(), "计划提交时间");

        //对计划提交时间进行处理_当日17点为提交时间
        jobItemsDto.setPlanSubmissionTime(DateUtil.afternoon23_59pm(jobItemsDto.getPlanSubmissionTime()));

        RdmsJobItem jobItem = CopyUtil.copy(jobItemsDto, RdmsJobItem.class);
        //将附件列表写入工单记录, 并创建工单
        RdmsAttachmentDto attachmentDto = new RdmsAttachmentDto();
        attachmentDto.setUse(FileGroupingEnum.JOBITEM.getCode());
        attachmentDto.setItemId(jobItem.getId());
        List<RdmsFileDto> fileDtos = rdmsFileService.listFileInfo(attachmentDto);
        List<String> fileIds = fileDtos.stream().map(RdmsFileDto::getId).collect(Collectors.toList());
        String fileIdsStr = JSONObject.toJSONString(fileIds);
        jobItem.setFileListStr(fileIdsStr); //创建工单时, 页面上提供的附件,作为工单附件; 以后,在过程处理过程中提供的附件将记录为节点附件, 相应的file表中的 itemId为Process的id

        RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(jobItemsDto.getSubprojectId());
        if (!ObjectUtils.isEmpty(subproject)) {
            if (subproject.getRdType().equals(RdTypeEnum.MODULE.getType())) {
                jobItem.setExecutorId(subproject.getProductManagerId());
                jobItem.setProjectType(ProjectTypeEnum.PRE_PROJECT.getType());
            } else {
                jobItem.setExecutorId(subproject.getProjectManagerId());
                jobItem.setProjectType(ProjectTypeEnum.SUB_PROJECT.getType());
            }
        }
        jobItem.setProjectId(subproject.getProjectId());
        jobItem.setProjectManagerId(subproject.getProjectManagerId());
        jobItem.setProductManagerId(subproject.getProductManagerId());
//                    jobItem.setNextNode(subproject.getProjectManagerId());
        jobItem.setStatus(JobItemStatusEnum.HANDLING.getStatus());
        jobItem.setStandManhourFee(jobItemsDto.getStandManhourFee());
        String jobItemId = this.save(jobItem);
        //填写文件的访问权限人员
        if (!CollectionUtils.isEmpty(fileIds)) {
            {
                //设置文件授权 权限
                RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                roleUsersDto.setLoginUserId(jobItemsDto.getExecutorId());
                roleUsersDto.setReceiverId(null);
                RdmsJobItem rdmsJobItem = this.selectByPrimaryKey(jobItemId);
                RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(rdmsJobItem.getCustomerId());
                roleUsersDto.setBossId(rdmsBossDto.getBossId());
                roleUsersDto.setSuperId(null);
                roleUsersDto.setIpmtId(null);
                roleUsersDto.setPjmId(subproject.getProjectManagerId());
                roleUsersDto.setPdmId(subproject.getProductManagerId());
                if(!ObjectUtils.isEmpty(jobItemsDto.getPreProjectId())){
                    RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(jobItemsDto.getPreProjectId());
                    if (!ObjectUtils.isEmpty(rdmsPreProject)) {
                        roleUsersDto.setSmId(rdmsPreProject.getSystemManagerId());
                    }
                }
                rdmsFileAuthService.setFileAuthUser(fileIds, roleUsersDto);
            }
        }

        //创建一个工单流程节点, 用户保存节点信息
        RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
        jobItemProcess.setJobItemId(jobItemId);
        jobItemProcess.setExecutorId(jobItemsDto.getLoginUserId());
        jobItemProcess.setNextNode(jobItem.getExecutorId());
        jobItemProcess.setJobDescription("对功能建议进行审核.");
        jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.ISSUE.getStatus()); //工单下发
        long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobItemId);
        jobItemProcess.setDeep((int) jobItemProcessCount);
        rdmsJobItemProcessService.save(jobItemProcess);

        //更新character状态
        RdmsCharacter character = rdmsCharacterService.selectByPrimaryKey(jobItemsDto.getCharacterId());
        character.setProjectId(subproject.getProjectId());
        character.setSubprojectId(subproject.getId());
        character.setProductManagerId(subproject.getProductManagerId());
        character.setStatus(CharacterStatusEnum.SUBMIT.getStatus());
        character.setJobitemId(jobItemId);
        if (!ObjectUtils.isEmpty(subproject)) {
            if (subproject.getRdType().equals(RdTypeEnum.MODULE.getType())) {
                character.setProjectType(ProjectTypeEnum.PRE_PROJECT.getType());
            } else {
                character.setProjectType(ProjectTypeEnum.DEV_PROJECT.getType());
            }
        }
        rdmsCharacterService.update(character);
    }

    @Transactional
    public void suggetUpdateDealWith(RdmsJobItemDto jobItemsDto) throws ParseException {
        ValidatorUtil.require(jobItemsDto.getPlanSubmissionTime(), "计划提交时间");
        ValidatorUtil.require(jobItemsDto.getCharacterId(), "组件ID");
        //对计划提交时间进行处理_当日17点为提交时间
        jobItemsDto.setPlanSubmissionTime(DateUtil.afternoon23_59pm(jobItemsDto.getPlanSubmissionTime()));
        RdmsJobItem jobItem = CopyUtil.copy(jobItemsDto, RdmsJobItem.class);
        RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(jobItemsDto.getCharacterId());
        List<RdmsCbbDto> cbbByCbbCode = rdmsCbbService.getCbbByCbbCode(rdmsCharacter.getId());
        if(!CollectionUtils.isEmpty(cbbByCbbCode)){
            cbbByCbbCode.get(0).setStatus(CharacterStatusEnum.SUBMIT.getStatus());
            rdmsCbbService.update(cbbByCbbCode.get(0));
        }

        if(!ObjectUtils.isEmpty(rdmsCharacter) && !ObjectUtils.isEmpty(rdmsCharacter.getSubprojectId())){
            RdmsProjectSubproject moduleProject = rdmsSubprojectService.selectByPrimaryKey(rdmsCharacter.getSubprojectId());
            if(!ObjectUtils.isEmpty(moduleProject)){
                jobItem.setExecutorId(moduleProject.getProductManagerId());  //产品经理
                jobItem.setProductManagerId(moduleProject.getProductManagerId());
                jobItem.setStatus(JobItemStatusEnum.HANDLING.getStatus());
                jobItem.setStandManhourFee(jobItemsDto.getStandManhourFee());
                jobItem.setProjectType(ProjectTypeEnum.PRE_PROJECT.getType());
                String jobItemId = this.save(jobItem);

                //创建一个工单流程节点, 用户保存节点信息
                RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                jobItemProcess.setJobItemId(jobItemId);
                jobItemProcess.setExecutorId(jobItemsDto.getLoginUserId());
                jobItemProcess.setNextNode(jobItem.getExecutorId());
                jobItemProcess.setJobDescription("对功能升级建议进行审核.");
                jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.ISSUE.getStatus()); //工单下发
                long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobItemId);
                jobItemProcess.setDeep((int) jobItemProcessCount);
                rdmsJobItemProcessService.save(jobItemProcess);

                //更新character状态
                RdmsCharacter character = rdmsCharacterService.selectByPrimaryKey(jobItemsDto.getCharacterId());
                character.setProductManagerId(moduleProject.getProductManagerId());
                character.setStatus(CharacterStatusEnum.SUBMIT.getStatus());
                character.setAuxStatus(CharacterStatusEnum.SUBMIT.getStatus());
                character.setJobitemId(jobItemId);
                rdmsCharacterService.update(character);
            }
        }
    }

    @Transactional
    public void iterationJobDealwith(@NotNull RdmsJobItemDto jobItemsDto) throws ParseException {
        ProjectTypeEnum projectType = ProjectTypeEnum.getProjectTypeEnumByType(jobItemsDto.getProjectType());
        ValidatorUtil.require(jobItemsDto.getExecutorId(), "执行人ID");
        switch (Objects.requireNonNull(projectType)) {
            case PRE_PROJECT: {
                RdmsPreProject rdmsPreProject = new RdmsPreProject();
                if (jobItemsDto.getRdType() != null && jobItemsDto.getRdType().equals(RdTypeEnum.MODULE.getType())) {
                    rdmsPreProject = rdmsPreProjectService.getModulePreProject(jobItemsDto.getCustomerId()).get(0);
                    jobItemsDto.setPreProjectId(rdmsPreProject.getId());
                } else {
                    rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(jobItemsDto.getPreProjectId());
                    ValidatorUtil.require(jobItemsDto.getPreProjectId(), "预立项项目ID");
                }
                jobItemsDto.setProductManagerId(rdmsPreProject.getProductManagerId());
                ValidatorUtil.require(jobItemsDto.getCharacterId(), "功能/特性Id");
                ValidatorUtil.require(jobItemsDto.getManhour(), "工时");

                //对计划提交时间进行处理_当日17点为提交时间
                jobItemsDto.setPlanSubmissionTime(DateUtil.afternoon23_59pm(jobItemsDto.getPlanSubmissionTime()));

                //获得原来的Character记录
                RdmsCharacter currentOperatingCharacter = rdmsCharacterService.selectByPrimaryKey(jobItemsDto.getCharacterId());
                currentOperatingCharacter.setPreProjectId(rdmsPreProject.getId());
                currentOperatingCharacter.setProjectId(null);
                currentOperatingCharacter.setSubprojectId(null);

                String currentOriginStatus = currentOperatingCharacter.getStatus();
                //判断是否为再次修订的工单
                //判断： 根据传入的CharacterID对Character的 status进行判断 , 如果为 submit 则为在审批的过程中, 对迭代工单再次迭代
                if (ObjectUtils.isEmpty(currentOperatingCharacter)) {
                    throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                }
                //对提交的修订工单, 进行再次修订
                if (currentOperatingCharacter.getStatus().equals(CharacterStatusEnum.SUBMIT.getStatus()) && currentOperatingCharacter.getJobitemType().equals(JobItemTypeEnum.ITERATION.getType())) {
                    String iterationRecordId = currentOperatingCharacter.getId();
                    //1. 复制一条上一次迭代的记录
                    RdmsCharacter tempCharacter = CopyUtil.copy(currentOperatingCharacter, RdmsCharacter.class);

                    //2. 将上次迭代的记录标记为discard状态
                    tempCharacter.setStatus(CharacterStatusEnum.DISCARD.getStatus());
                    {//将相应的任务单标记为EVALUATE状态
                        RdmsJobItem rdmsJobItem = this.selectByPrimaryKey(tempCharacter.getJobitemId());
                        rdmsJobItem.setStatus(JobItemStatusEnum.EVALUATE.getStatus());
                        this.update(rdmsJobItem);
                        //注意:这里不需要创建process记录, 此处, 仅用于提前对状态进行标记, 以免发生错误, 后续再函数 setJobitemInfoAtDemandApproveOrIteDecJobSubmit 中,还会重新处理状态,并添加process记录

                    }
                    tempCharacter.setParent(null);
//                                tempCharacter.setDeep(null);
//                                tempCharacter.setNextNode(null);
                    rdmsCharacterService.update(tempCharacter);  //将上一次更新的记录作废

                    //3. 将上一条迭代的记录作为"原始记录"
                    currentOperatingCharacter.setId(currentOperatingCharacter.getCharacterSerial());  //将修订记录作为原记录
                    currentOperatingCharacter.setStatus(CharacterStatusEnum.APPROVED.getStatus());
                    currentOperatingCharacter.setJobitemType(JobItemTypeEnum.ITERATION.getType());
                    rdmsCharacterService.update(currentOperatingCharacter);  //更新原始记录为上一次修订的结果
                    //将修订记录的数据也设定为原记录数据
                    RdmsCharacterData characterData = rdmsCharacterService.getCharacterData(iterationRecordId);
                    characterData.setId(currentOperatingCharacter.getCharacterSerial());
                    rdmsCharacterService.saveCharacterData(characterData);
                }

                String jobitemId_createCurrentCharacter = currentOperatingCharacter.getJobitemId();
                {
                    //根据原记录创建一条history记录
                    RdmsCharacter characterHistory = CopyUtil.copy(currentOperatingCharacter, RdmsCharacter.class);
                    //处理History记录的状态
                    characterHistory.setId(null);
                    characterHistory.setStatus(CharacterStatusEnum.HISTORY.getStatus());
                    characterHistory.setSubmitTime(new Date());
                    String historyId = rdmsCharacterService.save(characterHistory);
                    //为history记录保存数据
                    RdmsCharacterData characterData = rdmsCharacterService.getCharacterData(currentOperatingCharacter.getId());
                    characterData.setId(historyId);
                    rdmsCharacterService.saveCharacterData(characterData);

                    //为history记录创建Process记录
                    //为被迭代的Character创建组件定义流转过程记录
                    {
                        RdmsCharacterProcess historyProcess = new RdmsCharacterProcess();
                        historyProcess.setCharacterId(historyId);
                        historyProcess.setExecutorId(jobItemsDto.getExecutorId());
                        historyProcess.setNextNode(null);
                        long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(historyId);
                        historyProcess.setDeep((int) characterProcessCount);
                        historyProcess.setJobDescription("创建迭代历史版本记录");
                        historyProcess.setProcessStatus(CharacterProcessStatusEnum.COMPLETE.getStatus());
                        rdmsCharacterProcessService.save(historyProcess);
                    }
                }

                //处理原记录
                //原记录需要完整保留,不能在迭代过程中修改掉, 因为原来的任务执行人, 还要用这条原来的记录, 直到迭代记录被审批通过
                currentOperatingCharacter.setStatus(CharacterStatusEnum.ITERATING.getStatus());
                String originId = rdmsCharacterService.update(currentOperatingCharacter);
                //为原记录创建Process记录
                {
                    RdmsCharacterProcess originProcess = new RdmsCharacterProcess();
                    originProcess.setCharacterId(originId);
                    originProcess.setExecutorId(jobItemsDto.getExecutorId());
                    originProcess.setNextNode(null);
                    long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(originId);
                    originProcess.setDeep((int) characterProcessCount);
                    originProcess.setJobDescription("标记为修订状态");
                    originProcess.setProcessStatus(CharacterProcessStatusEnum.ITERATION.getStatus());
                    rdmsCharacterProcessService.save(originProcess);
                }

                //创建工单
                RdmsJobItem jobItem = CopyUtil.copy(jobItemsDto, RdmsJobItem.class);
                //将附件列表写入工单记录, 并创建工单
                RdmsAttachmentDto attachmentDto = new RdmsAttachmentDto();
                attachmentDto.setUse(FileGroupingEnum.JOBITEM.getCode());
                attachmentDto.setItemId(jobItem.getId());
                List<RdmsFileDto> fileDtos = rdmsFileService.listFileInfo(attachmentDto);
                List<String> fileIds = fileDtos.stream().map(RdmsFileDto::getId).collect(Collectors.toList());
                String fileIdsStr = JSONObject.toJSONString(fileIds);
                jobItem.setFileListStr(fileIdsStr); //创建工单时, 页面上提供的附件,作为工单附件; 以后,在过程处理过程中提供的附件将记录为节点附件, 相应的file表中的 itemId为Process的id

                jobItem.setNextNode(jobItemsDto.getLoginUserId());  //工单的nextNode是回来时到谁那里  //可能是系统工程总监 或者是产品经理, 根据具体情况而定
                jobItem.setStatus(JobItemStatusEnum.HANDLING.getStatus());
                {
                    jobItem.setManhour(jobItemsDto.getStandManhour());
                    jobItem.setStandManhourFee(jobItemsDto.getStandManhourFee());
                }
                jobItem.setProjectType(jobItemsDto.getProjectType());
                jobItem.setCharacterId(currentOperatingCharacter.getCharacterSerial());  //由于每次修订后, 都把修订完成的记录放回到 原始记录中去, 所以, 使用最初ID的记录就是修订过的最新记录
                String jobItemId = this.save(jobItem);
                //填写文件的访问权限人员
                if (!CollectionUtils.isEmpty(fileIds)) {
                    {
                        //设置文件授权 权限
                        RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                        roleUsersDto.setLoginUserId(jobItemsDto.getLoginUserId());
                        roleUsersDto.setExecutorId(jobItemsDto.getExecutorId());
                        roleUsersDto.setReceiverId(null);
                        RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(currentOperatingCharacter.getCustomerId());
                        roleUsersDto.setBossId(rdmsBossDto.getBossId());
                        roleUsersDto.setSuperId(null);
                        roleUsersDto.setIpmtId(null);
                        roleUsersDto.setPjmId(jobItemsDto.getProjectManagerId());
                        roleUsersDto.setPdmId(jobItemsDto.getProductManagerId());
                        if(!ObjectUtils.isEmpty(jobItemsDto.getPreProjectId())){
                            if (!ObjectUtils.isEmpty(rdmsPreProject)) {
                                roleUsersDto.setSmId(rdmsPreProject.getSystemManagerId());
                            }
                        }
                        rdmsFileAuthService.setFileAuthUser(fileIds, roleUsersDto);
                    }
                }

                //创建一个工单流程节点, 用户保存节点信息
                RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                jobItemProcess.setJobItemId(jobItemId);
                jobItemProcess.setExecutorId(jobItemsDto.getLoginUserId()); //process的执行人是当前用户, nextNode是执行这项任务的人
                jobItemProcess.setNextNode(jobItemsDto.getExecutorId());
                jobItemProcess.setJobDescription("请完成功能修订工单任务");
                jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.ISSUE.getStatus()); //工单下发
                long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobItemId);
                jobItemProcess.setDeep((int) jobItemProcessCount);
                rdmsJobItemProcessService.save(jobItemProcess);

                //复制创建一条用于迭代编辑的新记录
                RdmsCharacter newIterationCharacter = CopyUtil.copy(currentOperatingCharacter, RdmsCharacter.class);
                newIterationCharacter.setId(null);
                newIterationCharacter.setJobitemId(jobItemId);  //关键代码
                newIterationCharacter.setJobitemType(JobItemTypeEnum.ITERATION.getType());
                newIterationCharacter.setStatus(CharacterStatusEnum.SAVED.getStatus());
                newIterationCharacter.setIterationVersion(currentOperatingCharacter.getIterationVersion() + 1);
                String iterationId = rdmsCharacterService.save(newIterationCharacter);
                //为迭代编辑的记录对应的文件添加新的授权
                if (!ObjectUtils.isEmpty(newIterationCharacter.getFileListStr())) {
                    List<String> fileIdList = JSON.parseArray(newIterationCharacter.getFileListStr(), String.class);
                    //填写文件的访问权限人员
                    if (!CollectionUtils.isEmpty(fileIdList)) {
                        {
                            //设置文件授权 权限
                            RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                            roleUsersDto.setLoginUserId(jobItemsDto.getLoginUserId());
                            roleUsersDto.setExecutorId(jobItemsDto.getExecutorId());
                            roleUsersDto.setReceiverId(null);
                            RdmsJobItem rdmsJobItem = this.selectByPrimaryKey(jobItemId);
                            RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(rdmsJobItem.getCustomerId());
                            roleUsersDto.setBossId(rdmsBossDto.getBossId());
                            roleUsersDto.setSuperId(null);
                            roleUsersDto.setIpmtId(null);
                            roleUsersDto.setPjmId(null);
                            roleUsersDto.setPdmId(jobItemsDto.getProductManagerId());
                            if(!ObjectUtils.isEmpty(jobItemsDto.getPreProjectId())){
                                if (!ObjectUtils.isEmpty(rdmsPreProject)) {
                                    roleUsersDto.setSmId(rdmsPreProject.getSystemManagerId());
                                }
                            }
                            rdmsFileAuthService.setFileAuthUser(fileIds, roleUsersDto);
                        }
                    }
                }

                //为用于编辑的记录保存数据
                RdmsCharacterData characterData = rdmsCharacterService.getCharacterData(currentOperatingCharacter.getId());
                characterData.setId(iterationId);
                rdmsCharacterService.saveCharacterData(characterData);

                //为iteration记录创建Process记录
                {
                    RdmsCharacterProcess iterationProcess = new RdmsCharacterProcess();
                    iterationProcess.setCharacterId(iterationId);
                    iterationProcess.setExecutorId(jobItemsDto.getExecutorId());
                    iterationProcess.setNextNode(null);
                    long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(iterationId);
                    iterationProcess.setDeep((int) characterProcessCount);
                    iterationProcess.setJobDescription("迭代记录被创建");
                    iterationProcess.setProcessStatus(CharacterProcessStatusEnum.EDITING.getStatus());
                    rdmsCharacterProcessService.save(iterationProcess);
                }


                if (currentOriginStatus.equals(CharacterStatusEnum.REFUSE.getStatus())) {
                    //如果当前Character为refuse状态, 则查看原工单下是否还有refuse记录,如果没有了, 并且 Character 的status 都不是 setup之前的状态,则 将工单标记为完成
                    //1. 查看当前记录的工单下是否还有refuse记录
                    List<RdmsCharacterDto> characterList = rdmsCharacterService.getCharacterListByJobitemId(jobitemId_createCurrentCharacter, null);
                    List<RdmsCharacterDto> collect = characterList.stream().filter(
                            item -> item.getStatus().equals(CharacterStatusEnum.REFUSE.getStatus())
                                    && !item.getId().equals(currentOperatingCharacter.getCharacterSerial())
                    ).collect(Collectors.toList());
                    if (CollectionUtils.isEmpty(collect)) {
                        //将工单标记为完成状态
                        RdmsJobItem rdmsJobItem = this.selectByPrimaryKey(jobitemId_createCurrentCharacter);
                        rdmsJobItem.setStatus(JobItemStatusEnum.COMPLETED.getStatus());
                        rdmsJobItem.setCompleteTime(new Date());
                        String updateJobitemId = this.update(rdmsJobItem);

                        //创建工单过程信息
                        //创建一个工单流程节点, 用户保存节点信息
                        RdmsJobItemProcess updateJobItemProcess = new RdmsJobItemProcess();
                        updateJobItemProcess.setJobItemId(updateJobitemId);
                        updateJobItemProcess.setExecutorId(jobItemsDto.getLoginUserId());
                        updateJobItemProcess.setNextNode(jobItemsDto.getExecutorId());
                        updateJobItemProcess.setJobDescription("请完成功能修订工单任务");
                        updateJobItemProcess.setProcessStatus(JobItemProcessStatusEnum.COMPLETE.getStatus()); //工单下发
                        long updateJobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(updateJobitemId);
                        updateJobItemProcess.setDeep((int) updateJobItemProcessCount);
                        rdmsJobItemProcessService.save(updateJobItemProcess);
                    }
                } else {
                    //判断被迭代的Character对应的 工单, 是否全部Character都已经approve或者已经处于history状态, 如果是的, 进入工单评价过程
                    //查看工单下的所有Character, 如果所有的Character都已经处于approve状态, 或者ITERATING状态, 则设置工单为完成转态, 否则不做处理
                    this.setJobitemInfoAtDemandApproveOrIteDecJobSubmit(jobitemId_createCurrentCharacter);
                }

                break;
            }
            case DEV_PROJECT: {
                ValidatorUtil.require(jobItemsDto.getProjectId(), "项目ID");
                RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(jobItemsDto.getSubprojectId());
                jobItemsDto.setProductManagerId(subproject.getProductManagerId());
                ValidatorUtil.require(jobItemsDto.getCharacterId(), "功能/特性Id");
                ValidatorUtil.require(jobItemsDto.getManhour(), "工时");

                //对计划提交时间进行处理_当日17点为提交时间
                jobItemsDto.setPlanSubmissionTime(DateUtil.afternoon23_59pm(jobItemsDto.getPlanSubmissionTime()));

                //获得原来的Character记录
                RdmsCharacter currentOperatingCharacter = rdmsCharacterService.selectByPrimaryKey(jobItemsDto.getCharacterId());
                String currentOriginStatus = currentOperatingCharacter.getStatus();
                //判断是否为再次修订的工单
                //判断： 根据传入的CharacterID对Character的 status进行判断 , 如果为 submit 则为在审批的过程中, 对迭代工单再次迭代
                if (ObjectUtils.isEmpty(currentOperatingCharacter)) {
                    throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                }

                //根据原记录创建一条history记录
                RdmsCharacter characterHistory = CopyUtil.copy(currentOperatingCharacter, RdmsCharacter.class);
                //处理History记录的状态
                characterHistory.setId(null);
                characterHistory.setStatus(CharacterStatusEnum.HISTORY.getStatus());
                characterHistory.setSubmitTime(new Date());
                String historyId = rdmsCharacterService.save(characterHistory);
                //为history记录保存数据
                RdmsCharacterData characterData = rdmsCharacterService.getCharacterData(currentOperatingCharacter.getId());
                characterData.setId(historyId);
                rdmsCharacterService.saveCharacterData(characterData);

                //为history记录创建Process记录
                //为被迭代的Character创建组件定义流转过程记录
                {
                    RdmsCharacterProcess historyProcess = new RdmsCharacterProcess();
                    historyProcess.setCharacterId(historyId);
                    historyProcess.setExecutorId(jobItemsDto.getExecutorId());
                    historyProcess.setNextNode(null);
                    long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(historyId);
                    historyProcess.setDeep((int) characterProcessCount);
                    historyProcess.setJobDescription("创建迭代历史版本记录");
                    historyProcess.setProcessStatus(CharacterProcessStatusEnum.COMPLETE.getStatus());
                    rdmsCharacterProcessService.save(historyProcess);
                }

                //处理原记录
                //原记录需要完整保留,不能在迭代过程中修改掉, 因为原来的任务执行人, 还要用这条原来的记录, 直到迭代记录被审批通过
                currentOperatingCharacter.setStatus(CharacterStatusEnum.ITERATING.getStatus());
                String originId = rdmsCharacterService.update(currentOperatingCharacter);
                //为原记录创建Process记录
                {
                    RdmsCharacterProcess originProcess = new RdmsCharacterProcess();
                    originProcess.setCharacterId(originId);
                    originProcess.setExecutorId(jobItemsDto.getExecutorId());
                    originProcess.setNextNode(null);
                    long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(originId);
                    originProcess.setDeep((int) characterProcessCount);
                    originProcess.setJobDescription("标记为修订状态");
                    originProcess.setProcessStatus(CharacterProcessStatusEnum.ITERATION.getStatus());
                    rdmsCharacterProcessService.save(originProcess);
                }

                //创建工单
                RdmsJobItem jobItem = CopyUtil.copy(jobItemsDto, RdmsJobItem.class);
                //将附件列表写入工单记录, 并创建工单
                RdmsAttachmentDto attachmentDto = new RdmsAttachmentDto();
                attachmentDto.setUse(FileGroupingEnum.JOBITEM.getCode());
                attachmentDto.setItemId(jobItem.getId());
                List<RdmsFileDto> fileDtos = rdmsFileService.listFileInfo(attachmentDto);
                List<String> fileIds = fileDtos.stream().map(RdmsFileDto::getId).collect(Collectors.toList());
                String fileIdsStr = JSONObject.toJSONString(fileIds);
                jobItem.setFileListStr(fileIdsStr); //创建工单时, 页面上提供的附件,作为工单附件; 以后,在过程处理过程中提供的附件将记录为节点附件, 相应的file表中的 itemId为Process的id

                jobItem.setNextNode(jobItemsDto.getLoginUserId());  //下一节点是当前发工单的人
                jobItem.setStatus(JobItemStatusEnum.HANDLING.getStatus());
                {
                    jobItem.setManhour(jobItemsDto.getStandManhour());
                    jobItem.setStandManhourFee(jobItemsDto.getStandManhourFee());
                }
                jobItem.setProjectType(jobItemsDto.getProjectType());
                jobItem.setCharacterId(currentOperatingCharacter.getCharacterSerial());  //由于每次修订后, 都把修订完成的记录放回到 原始记录中去, 所以, 使用最初ID的记录就是修订过的最新记录
                String jobItemId = this.save(jobItem);
                //填写文件的访问权限人员
                if (!CollectionUtils.isEmpty(fileIds)) {
                    {
                        //设置文件授权 权限
                        RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                        roleUsersDto.setLoginUserId(jobItemsDto.getLoginUserId());
                        roleUsersDto.setExecutorId(jobItemsDto.getExecutorId());
                        roleUsersDto.setReceiverId(null);
                        RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(currentOperatingCharacter.getCustomerId());
                        roleUsersDto.setBossId(rdmsBossDto.getBossId());
                        roleUsersDto.setSuperId(null);
                        roleUsersDto.setIpmtId(null);
                        roleUsersDto.setPjmId(jobItemsDto.getProjectManagerId());
                        roleUsersDto.setPdmId(jobItemsDto.getProductManagerId());
                        if(!ObjectUtils.isEmpty(jobItemsDto.getPreProjectId())){
                            RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(jobItemsDto.getPreProjectId());
                            if (!ObjectUtils.isEmpty(rdmsPreProject)) {
                                roleUsersDto.setSmId(rdmsPreProject.getSystemManagerId());
                            }
                        }
                        rdmsFileAuthService.setFileAuthUser(fileIds, roleUsersDto);
                    }
                }

                //创建一个工单流程节点, 用户保存节点信息
                RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                jobItemProcess.setJobItemId(jobItemId);
                jobItemProcess.setExecutorId(jobItemsDto.getLoginUserId());
                jobItemProcess.setNextNode(jobItemsDto.getExecutorId());
                jobItemProcess.setJobDescription("请完成功能修订工单任务");
                jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.ISSUE.getStatus()); //工单下发
                long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobItemId);
                jobItemProcess.setDeep((int) jobItemProcessCount);
                String JobProcessId = rdmsJobItemProcessService.save(jobItemProcess);

                //复制创建一条用于迭代编辑的新记录
                RdmsCharacter newIterationCharacter = CopyUtil.copy(currentOperatingCharacter, RdmsCharacter.class);
                newIterationCharacter.setId(null);
                newIterationCharacter.setJobitemId(jobItemId);  //关键代码
                newIterationCharacter.setJobitemType(JobItemTypeEnum.ITERATION.getType());
                newIterationCharacter.setStatus(CharacterStatusEnum.SAVED.getStatus());
                newIterationCharacter.setIterationVersion(currentOperatingCharacter.getIterationVersion() + 1);
                String iterationId = rdmsCharacterService.save(newIterationCharacter);
                //为迭代编辑的记录对应的文件添加新的授权
                if (!ObjectUtils.isEmpty(newIterationCharacter.getFileListStr())) {
                    List<String> fileIdList = JSON.parseArray(newIterationCharacter.getFileListStr(), String.class);
                    if (!CollectionUtils.isEmpty(fileIdList)) {
                        for (String fileId : fileIdList) {
                            List<String> fileAuthIds = new ArrayList<>();
                            //读取文件的权限
                            RdmsFileAuth byFileId = rdmsFileAuthService.getByFileId(fileId);
                            if (!ObjectUtils.isEmpty(byFileId)) {
                                List<String> strings = JSON.parseArray(byFileId.getAuthIdsStr(), String.class);
                                if (!CollectionUtils.isEmpty(strings)) {
                                    fileAuthIds.addAll(strings);
                                }
                            }
                            //文件的执行人
                            if (!ObjectUtils.isEmpty(jobItemsDto.getExecutorId())) {
                                fileAuthIds.add(jobItemsDto.getExecutorId());
                            }
                            //文件的管理人
                            if (!ObjectUtils.isEmpty(jobItemsDto.getProjectManagerId())) {
                                fileAuthIds.add(jobItemsDto.getProjectManagerId());
                            }
                            if (!ObjectUtils.isEmpty(jobItemsDto.getLoginUserId())) {
                                fileAuthIds.add(jobItemsDto.getLoginUserId());
                            }
                            //生成JSON字符串
                            List<String> stringList = fileAuthIds.stream().distinct().collect(Collectors.toList());
                            String authIdJsonStr = JSON.toJSONString(stringList);
                            RdmsFileAuth fileAuth = new RdmsFileAuth();
                            fileAuth.setFileId(fileId);
                            fileAuth.setAuthIdsStr(authIdJsonStr);
                            rdmsFileAuthService.saveByFileId(fileAuth);
                        }
                    }

                }

                //计算character 的 budget
                rdmsCharacterService.calAndUpdateCharacterSelfBudget(iterationId);
                //为编辑记录保存数据
                RdmsCharacterData iterationEditingRecord = rdmsCharacterService.getCharacterData(currentOperatingCharacter.getId());
                iterationEditingRecord.setId(iterationId);
                rdmsCharacterService.saveCharacterData(iterationEditingRecord);
                //为iteration记录创建Process记录
                {
                    RdmsCharacterProcess iterationProcess = new RdmsCharacterProcess();
                    iterationProcess.setCharacterId(iterationId);
                    iterationProcess.setExecutorId(jobItemsDto.getExecutorId());
                    iterationProcess.setNextNode(null);
                    long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(iterationId);
                    iterationProcess.setDeep((int) characterProcessCount);
                    iterationProcess.setJobDescription("迭代记录被创建");
                    iterationProcess.setProcessStatus(CharacterProcessStatusEnum.EDITING.getStatus());
                    rdmsCharacterProcessService.save(iterationProcess);
                }
                break;
            }
            default: {
                throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
            }
        }
    }

    @Transactional
    public void functionIteration(String copyCharacterId) {
        if (ObjectUtils.isEmpty(copyCharacterId)) {
            throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
        }
        RdmsCharacter currentOperatingCharacter = rdmsCharacterService.selectByPrimaryKey(copyCharacterId);
//        currentOperatingCharacter.setStatus(CharacterStatusEnum.ITERATING.getStatus());
//        rdmsCharacterService.update(currentOperatingCharacter);

        String jobItemId = currentOperatingCharacter.getJobitemId();
        RdmsJobItem jobitem_createCurrentCharacter = this.selectByPrimaryKey(jobItemId);
        String historyId = "";
        {
            //根据原记录创建一条history记录
            RdmsCharacter characterHistory = DeepCopyUtil.deepCopy(currentOperatingCharacter, RdmsCharacter.class);
            //处理History记录的状态
            characterHistory.setId(null);
            characterHistory.setStatus(CharacterStatusEnum.HISTORY.getStatus());
            characterHistory.setSubmitTime(new Date());
            historyId = rdmsCharacterService.save(characterHistory);

            //为history记录保存数据
            RdmsCharacterData characterData = rdmsCharacterService.getCharacterData(currentOperatingCharacter.getId());
            characterData.setId(historyId);
            rdmsCharacterService.saveCharacterData(characterData);

            //为history记录创建Process记录
            //为被迭代的Character创建组件定义流转过程记录
            {
                RdmsCharacterProcess historyProcess = new RdmsCharacterProcess();
                historyProcess.setCharacterId(historyId);
                historyProcess.setExecutorId(jobitem_createCurrentCharacter.getCreaterId());  //这一条记录的创建者
                historyProcess.setNextNode(null);
                long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(historyId);
                historyProcess.setDeep((int) characterProcessCount);
                historyProcess.setJobDescription("创建迭代历史版本记录");
                historyProcess.setProcessStatus(CharacterProcessStatusEnum.COMPLETE.getStatus());
                rdmsCharacterProcessService.save(historyProcess);
            }
        }

        //保留原记录,标记为迭代状态
        currentOperatingCharacter.setStatus(CharacterStatusEnum.ITERATING.getStatus());
        //parent 是历史记录
        currentOperatingCharacter.setParent(historyId);
        currentOperatingCharacter.setCharacterSerial(copyCharacterId); //重置serial
        currentOperatingCharacter.setIterationVersion(1);  //重置迭代版本
        /*//迭代版本通过历史记录的serial去查所有记录获得最大的iteration 加 1
        List<RdmsCharacter> characterListBySerial = rdmsCharacterService.getCharacterListBySerial(currentOperatingCharacter.getCharacterSerial());
        if (characterListBySerial.size() > 0) {
            Integer iterationVersion = characterListBySerial.get(0).getIterationVersion();
            currentOperatingCharacter.setIterationVersion(iterationVersion + 1);
        }*/
        String originId = rdmsCharacterService.update(currentOperatingCharacter);
        //为原记录创建Process记录
        {
            RdmsCharacterProcess originProcess = new RdmsCharacterProcess();
            originProcess.setCharacterId(originId);
            originProcess.setExecutorId(jobitem_createCurrentCharacter.getCreaterId());
            originProcess.setNextNode(null);
            long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(originId);
            originProcess.setDeep((int) characterProcessCount);
            originProcess.setJobDescription("标记为存档状态");
            originProcess.setProcessStatus(CharacterProcessStatusEnum.ITERATION.getStatus());
            rdmsCharacterProcessService.save(originProcess);
        }

        //复制创建一条用于迭代编辑的新记录
        RdmsCharacter newIterationCharacter = DeepCopyUtil.deepCopy(currentOperatingCharacter, RdmsCharacter.class);
        newIterationCharacter.setId(null);
        newIterationCharacter.setJobitemId(jobItemId);  //关键代码
        newIterationCharacter.setJobitemType(JobItemTypeEnum.ITERATION.getType());
        newIterationCharacter.setStatus(CharacterStatusEnum.SAVED.getStatus());
        String iterationId = rdmsCharacterService.save(newIterationCharacter);

        //为用于编辑的记录保存数据
        RdmsCharacterData characterData = rdmsCharacterService.getCharacterData(currentOperatingCharacter.getId());
        characterData.setId(iterationId);
        rdmsCharacterService.saveCharacterData(characterData);

        //将编辑记录的characterId更新到jobitem中
        RdmsJobItem rdmsJobItem_update = this.selectByPrimaryKey(jobItemId);
        rdmsJobItem_update.setCharacterId(iterationId);
        rdmsJobItem_update.setType(JobItemTypeEnum.ITERATION.getType());
        rdmsJobItem_update.setAuxType(JobItemTypeEnum.FUNCTION.getType());
        this.update(rdmsJobItem_update);

        //为迭代编辑的记录对应的文件添加新的授权
        if (!ObjectUtils.isEmpty(newIterationCharacter.getFileListStr())) {
            List<String> fileIdList = JSON.parseArray(newIterationCharacter.getFileListStr(), String.class);
            //填写文件的访问权限人员
            if (!CollectionUtils.isEmpty(fileIdList)) {
                {
                    //设置文件授权 权限
                    RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                    roleUsersDto.setLoginUserId(jobitem_createCurrentCharacter.getCreaterId());
                    roleUsersDto.setExecutorId(jobitem_createCurrentCharacter.getExecutorId());
                    roleUsersDto.setReceiverId(null);
                    RdmsJobItem rdmsJobItem = this.selectByPrimaryKey(jobItemId);
                    RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(rdmsJobItem.getCustomerId());
                    roleUsersDto.setBossId(rdmsBossDto.getBossId());
                    roleUsersDto.setSuperId(null);
                    roleUsersDto.setIpmtId(null);
                    roleUsersDto.setPjmId(null);
                    roleUsersDto.setPdmId(jobitem_createCurrentCharacter.getProductManagerId());
                    if(!ObjectUtils.isEmpty(currentOperatingCharacter.getPreProjectId())){
                        RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(currentOperatingCharacter.getPreProjectId());
                        if (!ObjectUtils.isEmpty(rdmsPreProject)) {
                            roleUsersDto.setSmId(rdmsPreProject.getSystemManagerId());
                        }
                    }
                    rdmsFileAuthService.setFileAuthUser(fileIdList, roleUsersDto);
                }
            }
        }

        //为iteration记录创建Process记录
        {
            RdmsCharacterProcess iterationProcess = new RdmsCharacterProcess();
            iterationProcess.setCharacterId(iterationId);
            iterationProcess.setExecutorId(jobitem_createCurrentCharacter.getExecutorId());
            iterationProcess.setNextNode(null);
            long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(iterationId);
            iterationProcess.setDeep((int) characterProcessCount);
            iterationProcess.setJobDescription("迭代记录被创建");
            iterationProcess.setProcessStatus(CharacterProcessStatusEnum.EDITING.getStatus());
            rdmsCharacterProcessService.save(iterationProcess);
        }
    }


    public double getAverageScoreOfMonth(List<String> jobitemIdList) {
        RdmsJobItemExample jobItemExample = new RdmsJobItemExample();
        if (!CollectionUtils.isEmpty(jobitemIdList)) {
            jobItemExample.createCriteria()
                    .andIdIn(jobitemIdList);
            List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(jobItemExample);
            List<RdmsJobItem> haveScoreItemList = rdmsJobItems.stream().filter(item -> item.getEvaluateScore() != null).collect(Collectors.toList());
            Integer sumScore = haveScoreItemList.stream().map(RdmsJobItem::getEvaluateScore).reduce(0, Integer::sum);
            return sumScore.doubleValue() / haveScoreItemList.size();
        } else {
            return 0;
        }
    }

    @Transactional
    public Integer getDevelopJobitemNumberByCharacterAndStatus(String characterId, List<JobItemStatusEnum> statusEnumList) {
        if (!CollectionUtils.isEmpty(statusEnumList)) {
            for (JobItemStatusEnum statusEnum : statusEnumList) {
                if (!ObjectUtils.isEmpty(statusEnum.getStatus())) {
                    JobItemStatusEnum jobItemStatusEnumByStatus = JobItemStatusEnum.getJobItemStatusEnumByStatus(statusEnum.getStatus());
                    if (ObjectUtils.isEmpty(jobItemStatusEnumByStatus)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        List<String> notInTypeList = new ArrayList<>();
        notInTypeList.add(JobItemTypeEnum.BUG.getType());
        notInTypeList.add(JobItemTypeEnum.TEST.getType());
        notInTypeList.add(JobItemTypeEnum.REVIEW.getType());
        notInTypeList.add(JobItemTypeEnum.REVIEW_COO.getType());
        notInTypeList.add(JobItemTypeEnum.REVIEW_SUBP.getType());
        notInTypeList.add(JobItemTypeEnum.REVIEW_MILESTONE.getType());
        notInTypeList.add(JobItemTypeEnum.REVIEW_PRO.getType());
        RdmsJobItemExample.Criteria criteria = rdmsJobItemExample.createCriteria().andDeletedEqualTo(0).andTypeNotIn(notInTypeList);
        criteria.andCharacterIdEqualTo(characterId);

        List<String> statusList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(statusEnumList)) {
            for (JobItemStatusEnum statusEnum : statusEnumList) {
                statusList.add(statusEnum.getStatus());
            }
            criteria.andStatusIn(statusList);
        }
        return (Integer) (int) rdmsJobItemsMapper.countByExample(rdmsJobItemExample);
    }

    @Transactional
    public Integer getDevelopJobitemNumberByCharacterAndStatus_notAssist(String characterId, List<JobItemStatusEnum> statusEnumList) {
        if (!CollectionUtils.isEmpty(statusEnumList)) {
            for (JobItemStatusEnum statusEnum : statusEnumList) {
                if (!ObjectUtils.isEmpty(statusEnum.getStatus())) {
                    JobItemStatusEnum jobItemStatusEnumByStatus = JobItemStatusEnum.getJobItemStatusEnumByStatus(statusEnum.getStatus());
                    if (ObjectUtils.isEmpty(jobItemStatusEnumByStatus)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        List<String> notInTypeList = new ArrayList<>();
        notInTypeList.add(JobItemTypeEnum.BUG.getType());
        notInTypeList.add(JobItemTypeEnum.TEST.getType());
        notInTypeList.add(JobItemTypeEnum.REVIEW.getType());
        notInTypeList.add(JobItemTypeEnum.REVIEW_COO.getType());
        notInTypeList.add(JobItemTypeEnum.REVIEW_SUBP.getType());
        notInTypeList.add(JobItemTypeEnum.REVIEW_MILESTONE.getType());
        notInTypeList.add(JobItemTypeEnum.REVIEW_PRO.getType());
        notInTypeList.add(JobItemTypeEnum.ASSIST.getType());
        RdmsJobItemExample.Criteria criteria = rdmsJobItemExample.createCriteria().andDeletedEqualTo(0).andTypeNotIn(notInTypeList);
        criteria.andCharacterIdEqualTo(characterId);

        List<String> statusList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(statusEnumList)) {
            for (JobItemStatusEnum statusEnum : statusEnumList) {
                statusList.add(statusEnum.getStatus());
            }
            criteria.andStatusIn(statusList);
        }
        return (Integer) (int) rdmsJobItemsMapper.countByExample(rdmsJobItemExample);
    }

    @Transactional
    public Integer getTestSchemeJobitemNumberByCharacterAndStatus_notAssist(String characterId, List<JobItemStatusEnum> statusEnumList) {
        if (!CollectionUtils.isEmpty(statusEnumList)) {
            for (JobItemStatusEnum statusEnum : statusEnumList) {
                if (!ObjectUtils.isEmpty(statusEnum.getStatus())) {
                    JobItemStatusEnum jobItemStatusEnumByStatus = JobItemStatusEnum.getJobItemStatusEnumByStatus(statusEnum.getStatus());
                    if (ObjectUtils.isEmpty(jobItemStatusEnumByStatus)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        List<String> notInTypeList = new ArrayList<>();
        notInTypeList.add(JobItemTypeEnum.BUG.getType());
        notInTypeList.add(JobItemTypeEnum.TEST.getType());
        notInTypeList.add(JobItemTypeEnum.REVIEW.getType());
        notInTypeList.add(JobItemTypeEnum.REVIEW_COO.getType());
        notInTypeList.add(JobItemTypeEnum.REVIEW_SUBP.getType());
        notInTypeList.add(JobItemTypeEnum.REVIEW_MILESTONE.getType());
        notInTypeList.add(JobItemTypeEnum.REVIEW_PRO.getType());
        notInTypeList.add(JobItemTypeEnum.ASSIST.getType());
        RdmsJobItemExample.Criteria criteria = rdmsJobItemExample.createCriteria().andDeletedEqualTo(0).andTypeNotIn(notInTypeList);
        criteria.andCharacterIdEqualTo(characterId);

        List<String> statusList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(statusEnumList)) {
            for (JobItemStatusEnum statusEnum : statusEnumList) {
                statusList.add(statusEnum.getStatus());
            }
            criteria.andStatusIn(statusList);
        }
        return (Integer) (int) rdmsJobItemsMapper.countByExample(rdmsJobItemExample);
    }

    @Transactional
    public Integer getTaskTestJobitemNumberByCharacterAndStatus(String characterId, List<JobItemStatusEnum> statusEnumList) {
        if (!CollectionUtils.isEmpty(statusEnumList)) {
            for (JobItemStatusEnum statusEnum : statusEnumList) {
                if (!ObjectUtils.isEmpty(statusEnum.getStatus())) {
                    JobItemStatusEnum jobItemStatusEnumByStatus = JobItemStatusEnum.getJobItemStatusEnumByStatus(statusEnum.getStatus());
                    if (ObjectUtils.isEmpty(jobItemStatusEnumByStatus)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        List<String> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.TASK_TEST.getType());
        RdmsJobItemExample.Criteria criteria = rdmsJobItemExample.createCriteria().andDeletedEqualTo(0).andTypeIn(typeList);
        criteria.andCharacterIdEqualTo(characterId);

        List<String> statusList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(statusEnumList)) {
            for (JobItemStatusEnum statusEnum : statusEnumList) {
                statusList.add(statusEnum.getStatus());
            }
            criteria.andStatusIn(statusList);
        }
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        return rdmsJobItems.size();
//        return (Integer) (int) rdmsJobItemsMapper.countByExample(rdmsJobItemExample);
    }

    @Transactional
    public Integer getJobitemNumberByQualityAndStatus(String qualityId, List<JobItemStatusEnum> statusEnumList) {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        List<String> notInTypeList = new ArrayList<>();
        notInTypeList.add(JobItemTypeEnum.BUG.getType());
        notInTypeList.add(JobItemTypeEnum.REVIEW.getType());
        notInTypeList.add(JobItemTypeEnum.REVIEW_COO.getType());
        notInTypeList.add(JobItemTypeEnum.REVIEW_SUBP.getType());
        notInTypeList.add(JobItemTypeEnum.REVIEW_MILESTONE.getType());
        notInTypeList.add(JobItemTypeEnum.REVIEW_PRO.getType());
        RdmsJobItemExample.Criteria criteria = rdmsJobItemExample.createCriteria().andDeletedEqualTo(0).andTypeNotIn(notInTypeList);
        criteria.andQualityIdEqualTo(qualityId);

        List<String> statusList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(statusEnumList)) {
            for (JobItemStatusEnum statusEnum : statusEnumList) {
                statusList.add(statusEnum.getStatus());
            }
            criteria.andStatusIn(statusList);
        }
        return (Integer) (int) rdmsJobItemsMapper.countByExample(rdmsJobItemExample);
    }

    @Transactional
    public Integer getJobitemNumberByBugFeedbackAndStatus(String bugId, List<JobItemStatusEnum> statusEnumList) {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria = rdmsJobItemExample.createCriteria()
                .andDeletedEqualTo(0)
                .andTypeEqualTo(JobItemTypeEnum.TASK_BUG.getType());
        criteria.andQualityIdEqualTo(bugId);  //复用qualityId

        List<String> statusList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(statusEnumList)) {
            for (JobItemStatusEnum statusEnum : statusEnumList) {
                statusList.add(statusEnum.getStatus());
            }
            criteria.andStatusIn(statusList);
        }
        return (Integer) (int) rdmsJobItemsMapper.countByExample(rdmsJobItemExample);
    }

    @Transactional
    public Integer getJobitemNumOfDevelopByNextNodeAndStatus(String nextNode, List<JobItemStatusEnum> statusEnumList) {
        if (!CollectionUtils.isEmpty(statusEnumList)) {
            for (JobItemStatusEnum statusEnum : statusEnumList) {
                if (!ObjectUtils.isEmpty(statusEnum.getStatus())) {
                    JobItemStatusEnum jobItemStatusEnumByStatus = JobItemStatusEnum.getJobItemStatusEnumByStatus(statusEnum.getStatus());
                    if (ObjectUtils.isEmpty(jobItemStatusEnumByStatus)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria = rdmsJobItemExample.createCriteria().andDeletedEqualTo(0);
        criteria.andNextNodeEqualTo(nextNode);
        criteria.andProjectTypeEqualTo(ProjectTypeEnum.SUB_PROJECT.getType());
        criteria.andCharacterIdIsNotNull();
        criteria.andTypeNotEqualTo(JobItemTypeEnum.TEST.getType());
        criteria.andTypeNotEqualTo(JobItemTypeEnum.BUG.getType());
        criteria.andTypeNotEqualTo(JobItemTypeEnum.ASSIST.getType());
        criteria.andTypeNotEqualTo(JobItemTypeEnum.REVIEW.getType());
        criteria.andTypeNotEqualTo(JobItemTypeEnum.REVIEW_SUBP.getType());
        criteria.andTypeNotEqualTo(JobItemTypeEnum.REVIEW_MILESTONE.getType());
        criteria.andTypeNotEqualTo(JobItemTypeEnum.REVIEW_PRO.getType());
        criteria.andTypeNotEqualTo(JobItemTypeEnum.REVIEW_NOTIFY.getType());
        criteria.andTypeNotEqualTo(JobItemTypeEnum.REVIEW_COO.getType());

        List<String> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.UPDATE.getType());
        typeList.add(JobItemTypeEnum.QUALITY.getType());
        criteria.andTypeNotIn(typeList);

        List<String> statusList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(statusEnumList)) {
            for (JobItemStatusEnum statusEnum : statusEnumList) {
                statusList.add(statusEnum.getStatus());
            }
            criteria.andStatusIn(statusList);
        }
        return (Integer) (int) rdmsJobItemsMapper.countByExample(rdmsJobItemExample);
    }

    @Transactional
    public Integer getJobitemOfQualityByNextNodeAndStatus(String nextNode, List<JobItemStatusEnum> statusEnumList) {
        if (!CollectionUtils.isEmpty(statusEnumList)) {
            for (JobItemStatusEnum statusEnum : statusEnumList) {
                if (!ObjectUtils.isEmpty(statusEnum.getStatus())) {
                    JobItemStatusEnum jobItemStatusEnumByStatus = JobItemStatusEnum.getJobItemStatusEnumByStatus(statusEnum.getStatus());
                    if (ObjectUtils.isEmpty(jobItemStatusEnumByStatus)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria = rdmsJobItemExample.createCriteria().andDeletedEqualTo(0);
        criteria.andNextNodeEqualTo(nextNode);
        criteria.andProjectTypeEqualTo(ProjectTypeEnum.SUB_PROJECT.getType());
        criteria.andCharacterIdIsNotNull();
        criteria.andTypeNotEqualTo(JobItemTypeEnum.TEST.getType());
        criteria.andTypeNotEqualTo(JobItemTypeEnum.BUG.getType());
        criteria.andTypeNotEqualTo(JobItemTypeEnum.ASSIST.getType());
        criteria.andTypeNotEqualTo(JobItemTypeEnum.REVIEW.getType());
        criteria.andTypeNotEqualTo(JobItemTypeEnum.REVIEW_SUBP.getType());
        criteria.andTypeNotEqualTo(JobItemTypeEnum.REVIEW_MILESTONE.getType());
        criteria.andTypeNotEqualTo(JobItemTypeEnum.REVIEW_PRO.getType());
        criteria.andTypeNotEqualTo(JobItemTypeEnum.REVIEW_NOTIFY.getType());
        criteria.andTypeNotEqualTo(JobItemTypeEnum.REVIEW_COO.getType());

        List<String> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.QUALITY.getType());
        criteria.andTypeIn(typeList);

        List<String> statusList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(statusEnumList)) {
            for (JobItemStatusEnum statusEnum : statusEnumList) {
                statusList.add(statusEnum.getStatus());
            }
            criteria.andStatusIn(statusList);
        }
        return (Integer) (int) rdmsJobItemsMapper.countByExample(rdmsJobItemExample);
    }

    @Transactional
    public Integer getSubmitSubTaskJobitemNumByPjm(String pjmId) {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        RdmsJobItemExample.Criteria criteria = rdmsJobItemExample.createCriteria().andDeletedEqualTo(0);
        criteria.andProjectManagerIdEqualTo(pjmId);

        List<String> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.REVIEW_PRO.getType());
        typeList.add(JobItemTypeEnum.REVIEW_SUBP.getType());
        typeList.add(JobItemTypeEnum.REVIEW_MILESTONE.getType());
        typeList.add(JobItemTypeEnum.REVIEW_COO.getType());
        typeList.add(JobItemTypeEnum.ASSIST.getType());

        criteria.andStatusEqualTo(JobItemStatusEnum.SUBMIT.getStatus())
                .andTypeNotIn(typeList)
                .andReviewResultIsNull();

        criteria.andCharacterIdIsNull();
        return (Integer) (int) rdmsJobItemsMapper.countByExample(rdmsJobItemExample);
    }

    @Transactional
    public Integer getSubmitSubTaskJobitemNumByPjmAndSubprojectId(RdmsHmiIdsDto idsDto) {

        List<JobItemTypeEnum> typeEnumList = new ArrayList<>();
        typeEnumList.add(JobItemTypeEnum.TASK_SUBP);
        typeEnumList.add(JobItemTypeEnum.SUBPROJECT_INT);

        List<JobItemStatusEnum> statusEnumList = new ArrayList<>();
        statusEnumList.add(JobItemStatusEnum.SUBMIT);

        List<RdmsJobItemDto> jobitemList = this.getJobitemListBySubProjectId(idsDto.getSubprojectId(), statusEnumList, typeEnumList);

        List<JobItemTypeEnum> typeEnumList_milestone = new ArrayList<>();
        typeEnumList_milestone.add(JobItemTypeEnum.TASK_MILESTONE);
        List<RdmsJobItemDto> jobitemList_milestone = this.getJobitemListByProjectId_mainPjm(idsDto.getSubprojectId(), statusEnumList, typeEnumList_milestone, idsDto.getProjectManagerId());
        jobitemList.addAll(jobitemList_milestone);

        return jobitemList.size();

    }

    @Transactional
    public Integer getSubmitPreTaskJobitemNumBySysgmAndPreprojectId(RdmsHmiIdsDto idsDto) {

        List<JobItemTypeEnum> typeEnumList = new ArrayList<>();
        typeEnumList.add(JobItemTypeEnum.TASK_FUNCTION);
        List<JobItemStatusEnum> statusEnumList = new ArrayList<>();
        statusEnumList.add(JobItemStatusEnum.SUBMIT);
        List<RdmsJobItemDto> jobitemList = this.getJobitemListByPreProjectId(idsDto.getPreProjectId(), statusEnumList, typeEnumList);
        return jobitemList.size();

    }

    @Transactional
    public Integer getSubmitTaskJobitemNumByPjmAndProjectId(RdmsHmiIdsDto idsDto) {

        List<JobItemTypeEnum> typeEnumList = new ArrayList<>();
        typeEnumList.add(JobItemTypeEnum.TASK_PRODUCT);

        List<JobItemStatusEnum> statusEnumList = new ArrayList<>();
        statusEnumList.add(JobItemStatusEnum.SUBMIT);

        List<RdmsJobItemDto> jobitemList = this.getJobitemListByProjectId(idsDto.getProjectId(), statusEnumList, typeEnumList);
        return jobitemList.size();
    }

    @Transactional
    public List<RdmsJobItem> getPerformanceTaskJobItemList(String userId, Date start, Date end) {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        List<String> jobTypeList = new ArrayList<>();
        jobTypeList.add(JobItemTypeEnum.CHARACTER_INT.getType());
//        jobTypeList.add(JobItemTypeEnum.TASK.getType());
        jobTypeList.add(JobItemTypeEnum.TASK_SUBP.getType());
        jobTypeList.add(JobItemTypeEnum.TASK_MILESTONE.getType());
        rdmsJobItemExample.createCriteria()
                .andExecutorIdEqualTo(userId)
                .andCompleteTimeBetween(start, end)
                .andTypeIn(jobTypeList)
                .andDeletedEqualTo(0);
        List<RdmsJobItem> taskJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        return taskJobItems;
    }

    @Transactional
    public List<RdmsJobItem> getJobitemListByWorkerId(String workerId) {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.createCriteria()
                .andExecutorIdEqualTo(workerId)
                .andDeletedEqualTo(0);
        List<RdmsJobItem> jobList = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        return jobList;
    }

    @Transactional
    public List<RdmsJobItem> getJobitemListByCharacterIdAndWorkerId(String characterId, String workerId) {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        RdmsJobItemExample.Criteria criteria = rdmsJobItemExample.createCriteria()
                .andCharacterIdEqualTo(characterId)
                .andDeletedEqualTo(0);
        if (!ObjectUtils.isEmpty(workerId)) {
            criteria.andExecutorIdEqualTo(workerId);
        }
        List<RdmsJobItem> jobList = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        return jobList;
    }

    @Transactional
    public List<RdmsJobItem> getPerformanceJobItemList(String userId, Date start, Date end) {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        //列举并排除不做绩效计算的任务类型
        List<String> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.BUG.getType());
        typeList.add(JobItemTypeEnum.REVIEW.getType());
        typeList.add(JobItemTypeEnum.REVIEW_PRO.getType());
        typeList.add(JobItemTypeEnum.REVIEW_SUBP.getType());
        typeList.add(JobItemTypeEnum.REVIEW_MILESTONE_PRE.getType());
        typeList.add(JobItemTypeEnum.REVIEW_MILESTONE.getType());
        typeList.add(JobItemTypeEnum.REVIEW_COO.getType());
        typeList.add(JobItemTypeEnum.REVIEW_NOTIFY.getType());
        typeList.add(JobItemTypeEnum.MILESTONE_NOTIFY.getType());
        typeList.add(JobItemTypeEnum.REFUSE_NOTIFY.getType());
        typeList.add(JobItemTypeEnum.BUDGET_ADJUST_NOTIFY.getType());
        typeList.add(JobItemTypeEnum.SUGGEST.getType());
        typeList.add(JobItemTypeEnum.SUGGEST_UPDATE.getType());

        //列举工作任务状态,排除不需要进行绩效计算的任务状态
        List<String> statusList = new ArrayList<>();
        statusList.add(JobItemStatusEnum.REFUSE.getStatus());
        statusList.add(JobItemStatusEnum.CANCEL.getStatus());

        rdmsJobItemExample.createCriteria()
                .andTypeNotIn(typeList)
                .andStatusNotIn(statusList)
                .andExecutorIdEqualTo(userId)
                .andCompleteTimeBetween(start, end)
                .andPerformanceManhourNotEqualTo(0.0)
                .andPerformanceManhourIsNotNull()
                .andStandManhourFeeIsNotNull()
                .andStandManhourFeeNotEqualTo(BigDecimal.ZERO)
                .andActualSubmissionTimeIsNotNull()
                .andEvaluateScoreIsNotNull()
                .andDeletedEqualTo(0);
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        return rdmsJobItems;
    }

    @Transactional
    public List<RdmsJobItem> getProjectPerformanceJobItemList(String userId, String projectId) {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.createCriteria()
                .andExecutorIdEqualTo(userId)
                .andProjectIdEqualTo(projectId)
                .andPerformanceManhourNotEqualTo(0.0)
                .andPerformanceManhourIsNotNull()
                .andStandManhourFeeIsNotNull()
                .andStandManhourFeeNotEqualTo(BigDecimal.ZERO)
                .andActualSubmissionTimeIsNotNull()
                .andEvaluateScoreIsNotNull()
                .andDeletedEqualTo(0);
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        return rdmsJobItems;
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemListByExecutorId(String workerId, List<JobItemStatusEnum> statusEnumList) {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        RdmsJobItemExample.Criteria criteria = rdmsJobItemExample.createCriteria().andDeletedEqualTo(0);
        criteria.andExecutorIdEqualTo(workerId);

        List<String> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.BUG.getType());
        typeList.add(JobItemTypeEnum.REVIEW.getType());
        typeList.add(JobItemTypeEnum.REVIEW_SUBP.getType());
        typeList.add(JobItemTypeEnum.REVIEW_MILESTONE.getType());
        typeList.add(JobItemTypeEnum.REVIEW_COO.getType());
        typeList.add(JobItemTypeEnum.REVIEW_NOTIFY.getType());
        typeList.add(JobItemTypeEnum.REFUSE_NOTIFY.getType());
        typeList.add(JobItemTypeEnum.BUDGET_ADJUST_NOTIFY.getType());
        typeList.add(JobItemTypeEnum.REVIEW_MILESTONE_PRE.getType());
        typeList.add(JobItemTypeEnum.SUGGEST_UPDATE.getType());
        typeList.add(JobItemTypeEnum.SUGGEST.getType());
        typeList.add(JobItemTypeEnum.CHA_RECHECK.getType());
        typeList.add(JobItemTypeEnum.MILESTONE_NOTIFY.getType());

        criteria.andTypeNotIn(typeList);
        List<String> statusList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(statusEnumList)) {
            for (JobItemStatusEnum statusEnum : statusEnumList) {
                statusList.add(statusEnum.getStatus());
            }
            criteria.andStatusIn(statusList);
        }
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        return CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemListByExecutorIdByTimeInterval(String workerId, List<JobItemStatusEnum> statusEnumList, Date startDay, Date endDay) throws ParseException {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();

        //对startDay进行时间调整, 因为startDay是00:00:00.000
        startDay = DateUtil.zeroTimeOfDay(startDay);
        endDay = DateUtil.afternoon23_59_59pm(endDay);

        RdmsJobItemExample.Criteria criteria = rdmsJobItemExample.createCriteria().andDeletedEqualTo(0);
        criteria.andExecutorIdEqualTo(workerId);
        criteria.andCompleteTimeBetween(startDay, endDay);

        List<String> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.BUG.getType());
        typeList.add(JobItemTypeEnum.REVIEW.getType());
        typeList.add(JobItemTypeEnum.REVIEW_SUBP.getType());
        typeList.add(JobItemTypeEnum.REVIEW_MILESTONE.getType());
        typeList.add(JobItemTypeEnum.REVIEW_COO.getType());
        typeList.add(JobItemTypeEnum.REVIEW_NOTIFY.getType());
        typeList.add(JobItemTypeEnum.REFUSE_NOTIFY.getType());
        typeList.add(JobItemTypeEnum.BUDGET_ADJUST_NOTIFY.getType());
        typeList.add(JobItemTypeEnum.REVIEW_MILESTONE_PRE.getType());
        typeList.add(JobItemTypeEnum.SUGGEST_UPDATE.getType());
        typeList.add(JobItemTypeEnum.SUGGEST.getType());
        typeList.add(JobItemTypeEnum.CHA_RECHECK.getType());
        typeList.add(JobItemTypeEnum.MILESTONE_NOTIFY.getType());

        criteria.andTypeNotIn(typeList);
        List<String> statusList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(statusEnumList)) {
            for (JobItemStatusEnum statusEnum : statusEnumList) {
                statusList.add(statusEnum.getStatus());
            }
            criteria.andStatusIn(statusList);
        }
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        return CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
    }

    @Transactional
    public List<RdmsJobItem> getJobitemListByNextNodeId(String nextNodeId, List<JobItemStatusEnum> statusEnumList) {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        RdmsJobItemExample.Criteria criteria = rdmsJobItemExample.createCriteria().andDeletedEqualTo(0);
        criteria.andNextNodeEqualTo(nextNodeId);

        List<String> statusList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(statusEnumList)) {
            for (JobItemStatusEnum statusEnum : statusEnumList) {
                statusList.add(statusEnum.getStatus());
            }
            criteria.andStatusIn(statusList);
        }
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        return rdmsJobItems;
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemListByExecutorId(String workerId) {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        RdmsJobItemExample.Criteria criteria = rdmsJobItemExample.createCriteria().andDeletedEqualTo(0);
        criteria.andExecutorIdEqualTo(workerId);

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        return CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemListByExecutorIdAndStatusListTypeList(String workerId, List<JobItemStatusEnum> statusEnumList, List<JobItemTypeEnum> typeEnumList) {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        RdmsJobItemExample.Criteria criteria = rdmsJobItemExample.createCriteria().andDeletedEqualTo(0);
        criteria.andExecutorIdEqualTo(workerId);

        List<String> statusList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(statusEnumList)) {
            for (JobItemStatusEnum statusEnum : statusEnumList) {
                statusList.add(statusEnum.getStatus());
            }
            criteria.andStatusIn(statusList);
        }
        List<String> typeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(typeEnumList)) {
            for (JobItemTypeEnum typeEnum : typeEnumList) {
                typeList.add(typeEnum.getType());
            }
            criteria.andTypeIn(typeList);
        }

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        return CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemListByExecutorIdCharacterIdAndStatusListTypeList(String workerId, String characterId, List<JobItemStatusEnum> statusEnumList, List<JobItemTypeEnum> typeEnumList) {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        RdmsJobItemExample.Criteria criteria = rdmsJobItemExample.createCriteria().andDeletedEqualTo(0);
        criteria.andExecutorIdEqualTo(workerId).andCharacterIdEqualTo(characterId);

        List<String> statusList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(statusEnumList)) {
            for (JobItemStatusEnum statusEnum : statusEnumList) {
                statusList.add(statusEnum.getStatus());
            }
            criteria.andStatusIn(statusList);
        }
        List<String> typeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(typeEnumList)) {
            for (JobItemTypeEnum typeEnum : typeEnumList) {
                typeList.add(typeEnum.getType());
            }
            criteria.andTypeIn(typeList);
        }

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        return CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
    }

    /**
     * 根据subProjectID进行分页列表查询
     */
    @Transactional
    public void listBySubprojectId(PageDto<RdmsJobItemDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsJobItemExample rdmsJobItemsExample = new RdmsJobItemExample();
        rdmsJobItemsExample.createCriteria().andSubprojectIdEqualTo(pageDto.getKeyWord()).andDeletedEqualTo(0);
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemsExample);
        PageInfo<RdmsJobItem> pageInfo = new PageInfo<>(rdmsJobItems);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (RdmsJobItemDto jobItemsDto : rdmsJobItemsDtos) {
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getExecutorId());
            jobItemsDto.setExecutorName(rdmsCustomerUser.getTrueName());
            if (jobItemsDto.getPlanSubmissionTime() != null) {
                jobItemsDto.setPlanSubmissionTimeStr(sdf.format(jobItemsDto.getPlanSubmissionTime()));
            }
            if (jobItemsDto.getCreateTime() != null) {
                jobItemsDto.setCreateTimeStr(sdf.format(jobItemsDto.getCreateTime()));
            }
            RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(jobItemsDto.getCustomerId());
            jobItemsDto.setCustomerName(rdmsCustomer.getCustomerName());

            RdmsProject project = rdmsProjectService.findProjectById(jobItemsDto.getProjectId());
            jobItemsDto.setProjectName(project.getProjectName());

            RdmsProjectSubproject subProjectInfo = rdmsSubprojectService.getSubProjectInfo(jobItemsDto.getSubprojectId());
            jobItemsDto.setSubprojectName(subProjectInfo.getLabel());

            RdmsCustomerUser rdmsCustomerUser1 = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getProjectManagerId());
            jobItemsDto.setProjectManagerName(rdmsCustomerUser1.getTrueName());

            //处理附件
            //附件列表_通过过程表的file_list_str进行查询
            List<RdmsFile> fileList = new ArrayList<>();
            if (jobItemsDto.getFileListStr() != null && jobItemsDto.getFileListStr().length() > 6) {
                List<String> stringList = JSONObject.parseArray(jobItemsDto.getFileListStr(), String.class);
                for (String fileId : stringList) {
                    fileList.add(rdmsFileService.selectByPrimaryKey(fileId));
                }
                List<RdmsFileDto> fileDtos = CopyUtil.copyList(fileList, RdmsFileDto.class);
                jobItemsDto.setFileList(fileDtos);
            }

            DateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
            Double remainDay = 0.0;
            try {
                Date star = new Date();
                Date endDay = dft.parse(jobItemsDto.getPlanSubmissionTimeStr());
                Long starTime = star.getTime();
                Long endTime = endDay.getTime();
                Long num = endTime - starTime;//时间戳相差的毫秒数
                remainDay = num / 24.0 / 60.0 / 60.0 / 1000.0;

            } catch (ParseException e) {
                e.printStackTrace();
            }

            jobItemsDto.setRemainManhour(remainDay.intValue());
        }
        pageDto.setList(rdmsJobItemsDtos);
    }

    /**
     * 根据projectID进行分页列表查询
     */
    @Transactional
    public void listByProjectId(PageDto<RdmsJobItemDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsJobItemExample rdmsJobItemsExample = new RdmsJobItemExample();
        rdmsJobItemsExample.createCriteria().andProjectIdEqualTo(pageDto.getKeyWord()).andDeletedEqualTo(0);
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemsExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (RdmsJobItemDto jobItemsDto : rdmsJobItemsDtos) {
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getExecutorId());
            jobItemsDto.setExecutorName(rdmsCustomerUser.getTrueName());

            jobItemsDto.setCreateTimeStr(sdf.format(jobItemsDto.getCreateTime()));
        }
        PageInfo<RdmsJobItem> pageInfo = new PageInfo<>(rdmsJobItems);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(rdmsJobItemsDtos);
    }

    @Transactional
    public void getJobitemListByPjm(PageDto<RdmsJobItemDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsJobItemExample rdmsJobItemsExample = new RdmsJobItemExample();
        rdmsJobItemsExample.setOrderByClause("plan_submission_time desc, create_time desc");
        RdmsJobItemExample.Criteria criteria = rdmsJobItemsExample.createCriteria()
                .andProjectManagerIdEqualTo(pageDto.getActor())
                .andDeletedEqualTo(0);

        List<String> jobTypeList = new ArrayList<>();
        jobTypeList.add(JobItemTypeEnum.DEVELOP.getType());
        jobTypeList.add(JobItemTypeEnum.ASSIST.getType());
        jobTypeList.add(JobItemTypeEnum.TEST.getType());
        jobTypeList.add(JobItemTypeEnum.CHARACTER_INT.getType());
        jobTypeList.add(JobItemTypeEnum.TASK_SUBP.getType());
        jobTypeList.add(JobItemTypeEnum.TASK_MILESTONE.getType());
        jobTypeList.add(JobItemTypeEnum.TASK.getType());
        jobTypeList.add(JobItemTypeEnum.QUALITY.getType());
        criteria.andTypeIn(jobTypeList);

        List<String> jobStatusList = new ArrayList<>();
        if (!ObjectUtils.isEmpty(pageDto.getStatus()) && pageDto.getStatus().equals(JobItemStatusEnum.COMPLETED.getStatus())) {
            jobStatusList.add(JobItemStatusEnum.COMPLETED.getStatus());
            jobStatusList.add(JobItemStatusEnum.ARCHIVED.getStatus());
        } else {
            jobStatusList.add(JobItemStatusEnum.HANDLING.getStatus());
            jobStatusList.add(JobItemStatusEnum.REFUSE.getStatus());
            jobStatusList.add(JobItemStatusEnum.SUBMIT.getStatus());
            jobStatusList.add(JobItemStatusEnum.TESTING.getStatus());
            jobStatusList.add(JobItemStatusEnum.CHA_RECHECK.getStatus());
            jobStatusList.add(JobItemStatusEnum.SUB_RECHECK.getStatus());
            jobStatusList.add(JobItemStatusEnum.EVALUATE.getStatus());
            jobStatusList.add(JobItemStatusEnum.APPROVED.getStatus());
        }
        criteria.andStatusIn(jobStatusList);

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemsExample);

        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
        if (!CollectionUtils.isEmpty(rdmsJobItemsDtos)) {
            for (RdmsJobItemDto jobItemDto : rdmsJobItemsDtos) {
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(jobItemDto.getExecutorId());
                jobItemDto.setExecutorName(rdmsCustomerUser.getTrueName());
                RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(jobItemDto.getSubprojectId());
                if (!ObjectUtils.isEmpty(subproject)) {
                    jobItemDto.setSubprojectName(subproject.getLabel());
                }
                long remainMillisecond = jobItemDto.getPlanSubmissionTime().getTime() - new Date().getTime();
                jobItemDto.setJobRemainSecond(remainMillisecond / 1000);
                if (!ObjectUtils.isEmpty(jobItemDto.getActualSubmissionTime())) {
                    long remainMillisecond_2 = jobItemDto.getPlanSubmissionTime().getTime() - jobItemDto.getActualSubmissionTime().getTime();
                    jobItemDto.setJobCompleteSecond(remainMillisecond_2 / 1000);
                }
                //物料申请单数量
                if (jobItemDto.getType().equals(JobItemTypeEnum.QUALITY.getType())) {
                    Long applicationNumByJobitemId = rdmsMaterialManageService.getApplicationNumByJobitemId(jobItemDto.getId());
                    jobItemDto.setMaterialApplicationNum(applicationNumByJobitemId);
                }
                //费用申请单数量
                if (jobItemDto.getType().equals(JobItemTypeEnum.QUALITY.getType())) {
                    Long applicationNumByJobitemId = rdmsFeeManageService.getApplicationNumByJobitemId(jobItemDto.getId());
                    jobItemDto.setFeeApplicationNum(applicationNumByJobitemId);
                }
                if (!ObjectUtils.isEmpty(jobItemDto.getCharacterId())) {
                    RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(jobItemDto.getCharacterId());
                    if (!ObjectUtils.isEmpty(rdmsCharacter.getMilestoneId())) {
                        RdmsMilestoneDto rdmsMilestoneDto = rdmsMilestoneService.selectByPrimaryKey(rdmsCharacter.getMilestoneId());
                        jobItemDto.setMilestoneTime(rdmsMilestoneDto.getReviewTime());
                    } else {
                        jobItemDto.setMilestoneTime(rdmsCharacter.getPlanCompleteTime());
                    }
                }
                if (!ObjectUtils.isEmpty(jobItemDto.getSubprojectId())
                        && (jobItemDto.getType().equals(JobItemTypeEnum.TASK_SUBP.getType())
                        || jobItemDto.getType().equals(JobItemTypeEnum.TASK_MILESTONE.getType()))) {
                    RdmsProjectSubproject subproject1 = rdmsSubprojectService.selectByPrimaryKey(jobItemDto.getSubprojectId());
                    if (!ObjectUtils.isEmpty(subproject1.getReleaseTime())) {
                        jobItemDto.setMilestoneTime(subproject1.getReleaseTime());
                    }
                }
            }
        }
        PageInfo<RdmsJobItem> pageInfo = new PageInfo<>(rdmsJobItems);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(rdmsJobItemsDtos);
    }

    @Transactional
    public void getJobitemListByCreater(PageDto<RdmsJobItemDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsJobItemExample rdmsJobItemsExample = new RdmsJobItemExample();
        rdmsJobItemsExample.setOrderByClause("plan_submission_time desc, create_time desc");
        RdmsJobItemExample.Criteria criteria = rdmsJobItemsExample.createCriteria()
                .andCreaterIdEqualTo(pageDto.getActor())
                .andDeletedEqualTo(0);

        List<String> jobTypeList = new ArrayList<>();
        jobTypeList.add(JobItemTypeEnum.DEVELOP.getType());
        jobTypeList.add(JobItemTypeEnum.ASSIST.getType());
        jobTypeList.add(JobItemTypeEnum.TEST.getType());
        jobTypeList.add(JobItemTypeEnum.CHARACTER_INT.getType());
        jobTypeList.add(JobItemTypeEnum.TASK_SUBP.getType());
        jobTypeList.add(JobItemTypeEnum.TASK_MILESTONE.getType());
        jobTypeList.add(JobItemTypeEnum.TASK.getType());
        jobTypeList.add(JobItemTypeEnum.QUALITY.getType());
        criteria.andTypeIn(jobTypeList);

        List<String> jobStatusList = new ArrayList<>();
        if (!ObjectUtils.isEmpty(pageDto.getStatus()) && pageDto.getStatus().equals(JobItemStatusEnum.COMPLETED.getStatus())) {
            jobStatusList.add(JobItemStatusEnum.COMPLETED.getStatus());
            jobStatusList.add(JobItemStatusEnum.ARCHIVED.getStatus());
        } else {
            jobStatusList.add(JobItemStatusEnum.HANDLING.getStatus());
            jobStatusList.add(JobItemStatusEnum.REFUSE.getStatus());
            jobStatusList.add(JobItemStatusEnum.SUBMIT.getStatus());
            jobStatusList.add(JobItemStatusEnum.TESTING.getStatus());
            jobStatusList.add(JobItemStatusEnum.CHA_RECHECK.getStatus());
            jobStatusList.add(JobItemStatusEnum.SUB_RECHECK.getStatus());
            jobStatusList.add(JobItemStatusEnum.EVALUATE.getStatus());
            jobStatusList.add(JobItemStatusEnum.APPROVED.getStatus());
        }
        criteria.andStatusIn(jobStatusList);

        if(!ObjectUtils.isEmpty(pageDto.getKeyWord())){
            criteria.andJobNameLike("%"+ pageDto.getKeyWord() +"%");
        }
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemsExample);

        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
        if (!CollectionUtils.isEmpty(rdmsJobItemsDtos)) {
            for (RdmsJobItemDto jobItemDto : rdmsJobItemsDtos) {
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(jobItemDto.getExecutorId());
                jobItemDto.setExecutorName(rdmsCustomerUser.getTrueName());
                RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(jobItemDto.getSubprojectId());
                if (!ObjectUtils.isEmpty(subproject)) {
                    jobItemDto.setSubprojectName(subproject.getLabel());
                }
                long remainMillisecond = jobItemDto.getPlanSubmissionTime().getTime() - new Date().getTime();
                jobItemDto.setJobRemainSecond(remainMillisecond / 1000);
                if (!ObjectUtils.isEmpty(jobItemDto.getActualSubmissionTime())) {
                    long remainMillisecond_2 = jobItemDto.getPlanSubmissionTime().getTime() - jobItemDto.getActualSubmissionTime().getTime();
                    jobItemDto.setJobCompleteSecond(remainMillisecond_2 / 1000);
                }
                //物料申请单数量
                if (jobItemDto.getType().equals(JobItemTypeEnum.QUALITY.getType())) {
                    Long applicationNumByJobitemId = rdmsMaterialManageService.getApplicationNumByJobitemId(jobItemDto.getId());
                    jobItemDto.setMaterialApplicationNum(applicationNumByJobitemId);
                }
                //费用申请单数量
                if (jobItemDto.getType().equals(JobItemTypeEnum.QUALITY.getType())) {
                    Long applicationNumByJobitemId = rdmsFeeManageService.getApplicationNumByJobitemId(jobItemDto.getId());
                    jobItemDto.setFeeApplicationNum(applicationNumByJobitemId);
                }
                if (!ObjectUtils.isEmpty(jobItemDto.getCharacterId())) {
                    RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(jobItemDto.getCharacterId());
                    if (!ObjectUtils.isEmpty(rdmsCharacter.getMilestoneId())) {
                        RdmsMilestoneDto rdmsMilestoneDto = rdmsMilestoneService.selectByPrimaryKey(rdmsCharacter.getMilestoneId());
                        jobItemDto.setMilestoneTime(rdmsMilestoneDto.getReviewTime());
                    } else {
                        jobItemDto.setMilestoneTime(rdmsCharacter.getPlanCompleteTime());
                    }
                }
                if (!ObjectUtils.isEmpty(jobItemDto.getSubprojectId())
                        && (jobItemDto.getType().equals(JobItemTypeEnum.TASK_SUBP.getType())
                        || jobItemDto.getType().equals(JobItemTypeEnum.TASK_MILESTONE.getType()))) {
                    RdmsProjectSubproject subproject1 = rdmsSubprojectService.selectByPrimaryKey(jobItemDto.getSubprojectId());
                    if (!ObjectUtils.isEmpty(subproject1.getReleaseTime())) {
                        jobItemDto.setMilestoneTime(subproject1.getReleaseTime());
                    }
                }
                //将标准工时转换成个人工时
                OperateTypeEnum opeType = OperateTypeEnum.DEVELOP;
                if (!ObjectUtils.isEmpty(jobItemDto.getType()) && jobItemDto.getType().equals(JobItemTypeEnum.TEST.getType())) {
                    opeType = OperateTypeEnum.TEST;
                }
                Double staffManhour = rdmsManhourService.transformToWorkerManhour(jobItemDto.getManhour(), jobItemDto.getExecutorId(), jobItemDto.getCustomerId(), opeType);
                jobItemDto.setManhour(staffManhour);
            }
        }
        PageInfo<RdmsJobItem> pageInfo = new PageInfo<>(rdmsJobItems);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(rdmsJobItemsDtos);
    }

    @Transactional
    public void listJobsByPdm(PageDto<RdmsJobItemDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsJobItemExample rdmsJobItemsExample = new RdmsJobItemExample();
        rdmsJobItemsExample.setOrderByClause("plan_submission_time desc, create_time desc");
        RdmsJobItemExample.Criteria criteria = rdmsJobItemsExample.createCriteria()
                .andCreaterIdEqualTo(pageDto.getActor())
                .andDeletedEqualTo(0);

        List<String> jobTypeList = new ArrayList<>();
        jobTypeList.add(JobItemTypeEnum.DEMAND.getType());
        jobTypeList.add(JobItemTypeEnum.ITERATION.getType());
        jobTypeList.add(JobItemTypeEnum.DECOMPOSE.getType());
        jobTypeList.add(JobItemTypeEnum.TASK_FUNCTION.getType());
        jobTypeList.add(JobItemTypeEnum.TASK_PRODUCT.getType());
        criteria.andTypeIn(jobTypeList);

        List<String> jobStatusList = new ArrayList<>();
        if (!ObjectUtils.isEmpty(pageDto.getStatus()) && pageDto.getStatus().equals(JobItemStatusEnum.COMPLETED.getStatus())) {
            jobStatusList.add(JobItemStatusEnum.COMPLETED.getStatus());
            jobStatusList.add(JobItemStatusEnum.ARCHIVED.getStatus());
        } else {
            jobStatusList.add(JobItemStatusEnum.HANDLING.getStatus());
            jobStatusList.add(JobItemStatusEnum.REFUSE.getStatus());
            jobStatusList.add(JobItemStatusEnum.SUBMIT.getStatus());
            jobStatusList.add(JobItemStatusEnum.TESTING.getStatus());
            jobStatusList.add(JobItemStatusEnum.CHA_RECHECK.getStatus());
            jobStatusList.add(JobItemStatusEnum.SUB_RECHECK.getStatus());
            jobStatusList.add(JobItemStatusEnum.EVALUATE.getStatus());
            jobStatusList.add(JobItemStatusEnum.APPROVED.getStatus());
        }
        criteria.andStatusIn(jobStatusList);
        if(!ObjectUtils.isEmpty(pageDto.getKeyWord())){
            criteria.andJobNameLike("%"+ pageDto.getKeyWord() +"%");
        }
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemsExample);

        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
        if (!CollectionUtils.isEmpty(rdmsJobItemsDtos)) {
            for (RdmsJobItemDto jobItemDto : rdmsJobItemsDtos) {
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(jobItemDto.getExecutorId());
                jobItemDto.setExecutorName(rdmsCustomerUser.getTrueName());
                RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(jobItemDto.getSubprojectId());
                if (!ObjectUtils.isEmpty(subproject)) {
                    jobItemDto.setSubprojectName(subproject.getLabel());
                }
                long remainMillisecond = jobItemDto.getPlanSubmissionTime().getTime() - new Date().getTime();
                jobItemDto.setJobRemainSecond(remainMillisecond / 1000);
                if (!ObjectUtils.isEmpty(jobItemDto.getActualSubmissionTime())) {
                    long remainMillisecond_2 = jobItemDto.getPlanSubmissionTime().getTime() - jobItemDto.getActualSubmissionTime().getTime();
                    jobItemDto.setJobCompleteSecond(remainMillisecond_2 / 1000);
                }
                //物料申请单数量
                if (jobItemDto.getType().equals(JobItemTypeEnum.QUALITY.getType())) {
                    Long applicationNumByJobitemId = rdmsMaterialManageService.getApplicationNumByJobitemId(jobItemDto.getId());
                    jobItemDto.setMaterialApplicationNum(applicationNumByJobitemId);
                }
                //费用申请单数量
                if (jobItemDto.getType().equals(JobItemTypeEnum.QUALITY.getType())) {
                    Long applicationNumByJobitemId = rdmsFeeManageService.getApplicationNumByJobitemId(jobItemDto.getId());
                    jobItemDto.setFeeApplicationNum(applicationNumByJobitemId);
                }
                if (!ObjectUtils.isEmpty(jobItemDto.getCharacterId())) {
                    RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(jobItemDto.getCharacterId());
                    if (!ObjectUtils.isEmpty(rdmsCharacter.getMilestoneId())) {
                        RdmsMilestoneDto rdmsMilestoneDto = rdmsMilestoneService.selectByPrimaryKey(rdmsCharacter.getMilestoneId());
                        jobItemDto.setMilestoneTime(rdmsMilestoneDto.getReviewTime());
                    } else {
                        jobItemDto.setMilestoneTime(rdmsCharacter.getPlanCompleteTime());
                    }
                }
                if (!ObjectUtils.isEmpty(jobItemDto.getSubprojectId())
                        && (jobItemDto.getType().equals(JobItemTypeEnum.TASK_SUBP.getType())
                        || jobItemDto.getType().equals(JobItemTypeEnum.TASK_MILESTONE.getType()))) {
                    RdmsProjectSubproject subproject1 = rdmsSubprojectService.selectByPrimaryKey(jobItemDto.getSubprojectId());
                    if (!ObjectUtils.isEmpty(subproject1.getReleaseTime())) {
                        jobItemDto.setMilestoneTime(subproject1.getReleaseTime());
                    }
                }
                //将标准工时转换成个人工时
                OperateTypeEnum opeType = OperateTypeEnum.DEVELOP;
                if (!ObjectUtils.isEmpty(jobItemDto.getType()) && jobItemDto.getType().equals(JobItemTypeEnum.TEST.getType())) {
                    opeType = OperateTypeEnum.TEST;
                }
                Double staffManhour = rdmsManhourService.transformToWorkerManhour(jobItemDto.getManhour(), jobItemDto.getExecutorId(), jobItemDto.getCustomerId(), opeType);
                jobItemDto.setManhour(staffManhour);
            }
        }
        PageInfo<RdmsJobItem> pageInfo = new PageInfo<>(rdmsJobItems);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(rdmsJobItemsDtos);
    }

    @Transactional
    public Boolean getJobitemHasListFlag(String userId) {
        RdmsJobItemExample rdmsJobItemsExample = new RdmsJobItemExample();
        RdmsJobItemExample.Criteria criteria = rdmsJobItemsExample.createCriteria()
                .andProjectManagerIdEqualTo(userId)
                .andDeletedEqualTo(0);

        List<String> jobTypeList = new ArrayList<>();
        jobTypeList.add(JobItemTypeEnum.DEVELOP.getType());
        jobTypeList.add(JobItemTypeEnum.ASSIST.getType());
        jobTypeList.add(JobItemTypeEnum.TEST.getType());
        jobTypeList.add(JobItemTypeEnum.CHARACTER_INT.getType());
        jobTypeList.add(JobItemTypeEnum.TASK_SUBP.getType());
        jobTypeList.add(JobItemTypeEnum.TASK_MILESTONE.getType());
        jobTypeList.add(JobItemTypeEnum.TASK.getType());
        jobTypeList.add(JobItemTypeEnum.QUALITY.getType());
        criteria.andTypeIn(jobTypeList);

        List<String> jobStatusList = new ArrayList<>();
        jobStatusList.add(JobItemStatusEnum.HANDLING.getStatus());
        jobStatusList.add(JobItemStatusEnum.REFUSE.getStatus());
        jobStatusList.add(JobItemStatusEnum.SUBMIT.getStatus());
        jobStatusList.add(JobItemStatusEnum.TESTING.getStatus());
        jobStatusList.add(JobItemStatusEnum.CHA_RECHECK.getStatus());
        jobStatusList.add(JobItemStatusEnum.SUB_RECHECK.getStatus());
        jobStatusList.add(JobItemStatusEnum.EVALUATE.getStatus());
        jobStatusList.add(JobItemStatusEnum.APPROVED.getStatus());

        criteria.andStatusIn(jobStatusList);

        long l = rdmsJobItemsMapper.countByExample(rdmsJobItemsExample);
        return l > 0;
    }

    @Transactional
    public void listJobitemListBySubProjectId(PageDto<RdmsJobItemDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsJobItemExample jobItemExample = new RdmsJobItemExample();
        jobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria = jobItemExample.createCriteria().andDeletedEqualTo(0);
        if (!ObjectUtils.isEmpty(pageDto.getKeyWord())) {
            criteria.andSubprojectIdEqualTo(pageDto.getKeyWord());
        }
        if (!ObjectUtils.isEmpty(pageDto.getType())) {
            criteria.andTypeEqualTo(pageDto.getType());
        }
        if (!ObjectUtils.isEmpty(pageDto.getStatus())) {
            criteria.andStatusEqualTo(pageDto.getStatus());
        }
        if (!ObjectUtils.isEmpty(pageDto.getActor())) {
            criteria.andExecutorIdEqualTo(pageDto.getActor());
        }

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(jobItemExample);
        List<RdmsJobItemDto> jobItemDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
        PageInfo<RdmsJobItem> pageInfo = new PageInfo<>(rdmsJobItems);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(jobItemDtos);
    }

    @Transactional
    public void listJobitemListByPreProjectId(PageDto<RdmsJobItemDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsJobItemExample jobItemExample = new RdmsJobItemExample();
        jobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria = jobItemExample.createCriteria().andDeletedEqualTo(0);
        if (!ObjectUtils.isEmpty(pageDto.getKeyWord())) {
            criteria.andPreProjectIdEqualTo(pageDto.getKeyWord());
        }
        if (!ObjectUtils.isEmpty(pageDto.getType())) {
            criteria.andTypeEqualTo(pageDto.getType());
        }
        if (!ObjectUtils.isEmpty(pageDto.getStatus())) {
            criteria.andStatusEqualTo(pageDto.getStatus());
        }
        if (!ObjectUtils.isEmpty(pageDto.getActor())) {
            criteria.andExecutorIdEqualTo(pageDto.getActor());
        }

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(jobItemExample);
        List<RdmsJobItemDto> jobItemDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
        PageInfo<RdmsJobItem> pageInfo = new PageInfo<>(rdmsJobItems);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(jobItemDtos);
    }

    /**
     * 根据给定的工单状态查询某个project下的工单列表
     * 给定的参数: projectId status
     */
    @Transactional
    public void listJobItemByProjectIdAndStatus(PageDto<RdmsJobItemDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsJobItemExample rdmsJobItemsExample = new RdmsJobItemExample();
        RdmsJobItemExample.Criteria criteria = rdmsJobItemsExample.createCriteria()
                .andProjectIdEqualTo(pageDto.getKeyWord())
                .andDeletedEqualTo(0);
        JobItemStatusEnum jobItemStatusEnumByStatus = JobItemStatusEnum.getJobItemStatusEnumByStatus(pageDto.getStatus());
        switch (jobItemStatusEnumByStatus) {
            case HANDLING: {
                criteria.andStatusEqualTo(JobItemStatusEnum.HANDLING.getStatus());
                break;
            }
            case SUBMIT: {
                criteria.andStatusEqualTo(JobItemStatusEnum.SUBMIT.getStatus());
                break;
            }
            case COMPLETED: {
                criteria.andStatusEqualTo(JobItemStatusEnum.COMPLETED.getStatus());
                break;
            }
            default: {
                LOG.info("RdmsJobItemService.listJobItemByProjectIdAndStatus: {}", pageDto);
                throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
            }
        }
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemsExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (RdmsJobItemDto jobItemsDto : rdmsJobItemsDtos) {
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getExecutorId());
            jobItemsDto.setExecutorName(rdmsCustomerUser.getTrueName());

            jobItemsDto.setCreateTimeStr(sdf.format(jobItemsDto.getCreateTime()));

            RdmsCustomer customer = rdmsCustomerService.selectByPrimaryKey(jobItemsDto.getCustomerId());
            jobItemsDto.setCustomerName(customer.getCustomerName());

            RdmsProject projectInfo = rdmsProjectService.selectByPrimaryKey(jobItemsDto.getProjectId());
            jobItemsDto.setProjectName(projectInfo.getProjectName());

            if (!ObjectUtils.isEmpty(jobItemsDto.getSubprojectId())) {
                RdmsProjectSubproject subProjectInfo = rdmsSubprojectService.getSubProjectInfo(jobItemsDto.getSubprojectId());
                jobItemsDto.setSubprojectName(subProjectInfo.getLabel());
            }

            RdmsCharacter character = rdmsCharacterService.selectByPrimaryKey(jobItemsDto.getCharacterId());
            jobItemsDto.setCharacterDto(CopyUtil.copy(character, RdmsCharacterDto.class));
            jobItemsDto.setCharacterName(character.getCharacterName());

            RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getProductManagerId());
            jobItemsDto.setProductManagerName(customerUser.getTrueName());

            if (!ObjectUtils.isEmpty(jobItemsDto.getProjectManagerId())) {
                RdmsCustomerUser customerUser1 = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getProjectManagerId());
                jobItemsDto.setProjectManagerName(customerUser1.getTrueName());
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getFileListStr()) && jobItemsDto.getFileListStr().length() > 6) {
                List<String> stringList = JSON.parseArray(jobItemsDto.getFileListStr(), String.class);
                List<RdmsFileDto> fileDtos = new ArrayList<>();
                for (String id : stringList) {
                    RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(id);
                    fileDtos.add(fileDto);
                }
                jobItemsDto.setFileList(fileDtos);
            }
        }
        PageInfo<RdmsJobItem> pageInfo = new PageInfo<>(rdmsJobItems);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(rdmsJobItemsDtos);
    }

    /**
     * 根据给定的工单状态查询某个project下的工单列表
     * 给定的参数: projectId status
     */
    @Transactional
    public void listJobItemByTypeAndExecutor(PageDto<RdmsJobItemDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsJobItemExample rdmsJobItemsExample = new RdmsJobItemExample();
        rdmsJobItemsExample.setOrderByClause("create_time desc");
        List<String> jobItemTypeEnums = new ArrayList<>();
        jobItemTypeEnums.add(JobItemTypeEnum.REVIEW.getType());
        jobItemTypeEnums.add(JobItemTypeEnum.REVIEW_SUBP.getType());
        jobItemTypeEnums.add(JobItemTypeEnum.REVIEW_MILESTONE.getType());
        jobItemTypeEnums.add(JobItemTypeEnum.REVIEW_PRO.getType());

        List<String> jobitemStatusList = new ArrayList<>();
        jobitemStatusList.add(JobItemStatusEnum.ARCHIVED.getStatus());
        rdmsJobItemsExample.createCriteria()
                .andTypeIn(jobItemTypeEnums)
                .andExecutorIdEqualTo(pageDto.getActor())
                .andStatusNotIn(jobitemStatusList)
                .andDeletedEqualTo(0);
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemsExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        for (RdmsJobItemDto jobItemsDto : rdmsJobItemsDtos) {
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getExecutorId());
            jobItemsDto.setExecutorName(rdmsCustomerUser.getTrueName());

            RdmsCustomer customer = rdmsCustomerService.selectByPrimaryKey(jobItemsDto.getCustomerId());
            jobItemsDto.setCustomerName(customer.getCustomerName());

            RdmsProject projectInfo = rdmsProjectService.selectByPrimaryKey(jobItemsDto.getProjectId());
            jobItemsDto.setProjectName(projectInfo.getProjectName());

            if (!ObjectUtils.isEmpty(jobItemsDto.getSubprojectId())) {
                RdmsProjectSubproject subProjectInfo = rdmsSubprojectService.getSubProjectInfo(jobItemsDto.getSubprojectId());
                jobItemsDto.setSubprojectName(subProjectInfo.getLabel());
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getCharacterId())) {
                RdmsCharacter character = rdmsCharacterService.selectByPrimaryKey(jobItemsDto.getCharacterId());
                jobItemsDto.setCharacterDto(CopyUtil.copy(character, RdmsCharacterDto.class));
                jobItemsDto.setCharacterName(character.getCharacterName());
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getProductManagerId())) {
                RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getProductManagerId());
                jobItemsDto.setProductManagerName(customerUser.getTrueName());
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getProjectManagerId())) {
                RdmsCustomerUser customerUser1 = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getProjectManagerId());
                jobItemsDto.setProjectManagerName(customerUser1.getTrueName());
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getFileListStr()) && jobItemsDto.getFileListStr().length() > 6) {
                List<String> stringList = JSON.parseArray(jobItemsDto.getFileListStr(), String.class);
                List<RdmsFileDto> fileDtos = new ArrayList<>();
                for (String id : stringList) {
                    RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(id);
                    fileDtos.add(fileDto);
                }
                jobItemsDto.setFileList(fileDtos);
            }
            if (!ObjectUtils.isEmpty(jobItemsDto.getId())) {
                List<ReviewResultEnum> resultEnumList = new ArrayList<>();
                resultEnumList.add(ReviewResultEnum.CONDITIONAL);
                resultEnumList.add(ReviewResultEnum.SUPPLEMENT);
                Integer numOfRelatedJobitem = this.getNumOfRelatedJobitemByParentAndResultStatus(jobItemsDto.getId(), JobItemStatusEnum.SUBMIT, resultEnumList);
                jobItemsDto.setSubmitJobNum(numOfRelatedJobitem);
            }

            Boolean allChildrenJobitemComplateStatus = this.getAllChildrenJobitemComplateStatus(jobItemsDto.getId());
            jobItemsDto.setChildrenJobComplateFlag(allChildrenJobitemComplateStatus);
        }
        PageInfo<RdmsJobItem> pageInfo = new PageInfo<>(rdmsJobItems);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(rdmsJobItemsDtos);
    }

    @Transactional
    public Integer getNumOfMilestoneReviewByNextNodeAndStatus(String nextNode, List<JobItemStatusEnum> statusEnumList) {
        RdmsJobItemExample jobItemExample = new RdmsJobItemExample();
        RdmsJobItemExample.Criteria criteria = jobItemExample.createCriteria()
                .andNextNodeEqualTo(nextNode)
                .andTypeEqualTo(JobItemTypeEnum.REVIEW_MILESTONE.getType())
                .andDeletedEqualTo(0);

        if (!CollectionUtils.isEmpty(statusEnumList)) {
            List<String> statusList = new ArrayList<>();
            for (JobItemStatusEnum statusEnum : statusEnumList) {
                statusList.add(statusEnum.getStatus());
            }
            criteria.andStatusIn(statusList);
        }
        return (Integer) (int) rdmsJobItemsMapper.countByExample(jobItemExample);
    }

    /**
     * 评审工单 用nexNode进行列表查询, NextNode才是评审负责人, executor是提交人
     * 给定的参数: projectId status
     */
    @Transactional
    public void listReviewJobitems(PageDto<RdmsJobItemDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsJobItemExample rdmsJobItemsExample = new RdmsJobItemExample();
        rdmsJobItemsExample.setOrderByClause("create_time desc");
        List<String> jobItemTypeEnums = new ArrayList<>();
        jobItemTypeEnums.add(JobItemTypeEnum.REVIEW.getType());
        jobItemTypeEnums.add(JobItemTypeEnum.REVIEW_SUBP.getType());
        jobItemTypeEnums.add(JobItemTypeEnum.REVIEW_PRO.getType());
        jobItemTypeEnums.add(JobItemTypeEnum.REVIEW_MILESTONE.getType());

        List<String> jobitemStatusList = new ArrayList<>();
        jobitemStatusList.add(JobItemStatusEnum.ARCHIVED.getStatus());
        jobitemStatusList.add(JobItemStatusEnum.CANCEL.getStatus());
        rdmsJobItemsExample.createCriteria()
                .andTypeIn(jobItemTypeEnums)
                .andNextNodeEqualTo(pageDto.getActor())
                .andStatusNotIn(jobitemStatusList)
                .andDeletedEqualTo(0);
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemsExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        for (RdmsJobItemDto jobItemsDto : rdmsJobItemsDtos) {
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getExecutorId());
            jobItemsDto.setExecutorName(rdmsCustomerUser.getTrueName());

            RdmsCustomer customer = rdmsCustomerService.selectByPrimaryKey(jobItemsDto.getCustomerId());
            jobItemsDto.setCustomerName(customer.getCustomerName());

            RdmsProject projectInfo = rdmsProjectService.selectByPrimaryKey(jobItemsDto.getProjectId());
            jobItemsDto.setProjectName(projectInfo.getProjectName());

            if (!ObjectUtils.isEmpty(jobItemsDto.getSubprojectId())) {
                RdmsProjectSubproject subProjectInfo = rdmsSubprojectService.getSubProjectInfo(jobItemsDto.getSubprojectId());
                jobItemsDto.setSubprojectName(subProjectInfo.getLabel());
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getCharacterId())) {
                RdmsCharacter character = rdmsCharacterService.selectByPrimaryKey(jobItemsDto.getCharacterId());
                jobItemsDto.setCharacterDto(CopyUtil.copy(character, RdmsCharacterDto.class));
                jobItemsDto.setCharacterName(character.getCharacterName());
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getProductManagerId())) {
                RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getProductManagerId());
                jobItemsDto.setProductManagerName(customerUser.getTrueName());
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getProjectManagerId())) {
                RdmsCustomerUser customerUser1 = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getProjectManagerId());
                jobItemsDto.setProjectManagerName(customerUser1.getTrueName());
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getFileListStr()) && jobItemsDto.getFileListStr().length() > 6) {
                List<String> stringList = JSON.parseArray(jobItemsDto.getFileListStr(), String.class);
                List<RdmsFileDto> fileDtos = new ArrayList<>();
                for (String id : stringList) {
                    RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(id);
                    fileDtos.add(fileDto);
                }
                jobItemsDto.setFileList(fileDtos);
            }
            if (!ObjectUtils.isEmpty(jobItemsDto.getId())) {
                List<ReviewResultEnum> resultEnumList = new ArrayList<>();
                resultEnumList.add(ReviewResultEnum.CONDITIONAL);
                resultEnumList.add(ReviewResultEnum.SUPPLEMENT);
                Integer numOfRelatedJobitem = this.getNumOfRelatedJobitemByParentAndResultStatus(jobItemsDto.getId(), JobItemStatusEnum.SUBMIT, resultEnumList);
                jobItemsDto.setSubmitJobNum(numOfRelatedJobitem);
            }

            Boolean allChildrenJobitemComplateStatus = this.getAllChildrenJobitemComplateStatus(jobItemsDto.getId());
            jobItemsDto.setChildrenJobComplateFlag(allChildrenJobitemComplateStatus);
        }
        PageInfo<RdmsJobItem> pageInfo = new PageInfo<>(rdmsJobItems);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(rdmsJobItemsDtos);
    }

    @Transactional
    public Integer getReviewHasListFlag(String userId) {
        RdmsJobItemExample rdmsJobItemsExample = new RdmsJobItemExample();
        List<String> jobItemTypeEnums = new ArrayList<>();
        jobItemTypeEnums.add(JobItemTypeEnum.REVIEW.getType());
        jobItemTypeEnums.add(JobItemTypeEnum.REVIEW_SUBP.getType());
        jobItemTypeEnums.add(JobItemTypeEnum.REVIEW_MILESTONE.getType());
        jobItemTypeEnums.add(JobItemTypeEnum.REVIEW_PRO.getType());
        List<String> jobitemStatusList = new ArrayList<>();
        jobitemStatusList.add(JobItemStatusEnum.ARCHIVED.getStatus());
        rdmsJobItemsExample.createCriteria()
                .andTypeIn(jobItemTypeEnums)
                .andNextNodeEqualTo(userId)
                .andStatusNotIn(jobitemStatusList)
                .andDeletedEqualTo(0);
        return (Integer) (int) rdmsJobItemsMapper.countByExample(rdmsJobItemsExample);
    }

    @Transactional
    public Integer getNumOfRelatedJobitemByParentAndResultStatus(String parent, JobItemStatusEnum statusEnum, List<ReviewResultEnum> resultEnumList) {

        if (!CollectionUtils.isEmpty(resultEnumList)) {
            for (ReviewResultEnum resultEnum : resultEnumList) {
                if (!ObjectUtils.isEmpty(resultEnum.getStatus())) {
                    ReviewResultEnum reviewResultEnumByStatus = ReviewResultEnum.getReviewResultEnumByStatus(resultEnum.getStatus());
                    if (ObjectUtils.isEmpty(reviewResultEnumByStatus)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria = rdmsJobItemExample.createCriteria().andDeletedEqualTo(0).andParentJobitemIdEqualTo(parent);

        if (!ObjectUtils.isEmpty(statusEnum)) {
            criteria.andStatusEqualTo(statusEnum.getStatus());
        }

        List<String> statusList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(resultEnumList)) {
            for (ReviewResultEnum statusEnumItem : resultEnumList) {
                statusList.add(statusEnumItem.getStatus());
            }
            criteria.andReviewResultIn(statusList);
        }
        return (Integer) (int) rdmsJobItemsMapper.countByExample(rdmsJobItemExample);
    }

    /**
     * 根据customerID进行分页列表查询
     */
    @Transactional
    public void listByCustomerId(PageDto<RdmsJobItemDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsJobItemExample rdmsJobItemsExample = new RdmsJobItemExample();
        rdmsJobItemsExample.createCriteria().andCustomerIdEqualTo(pageDto.getKeyWord()).andDeletedEqualTo(0);
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemsExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (RdmsJobItemDto jobItemsDto : rdmsJobItemsDtos) {
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getExecutorId());
            jobItemsDto.setExecutorName(rdmsCustomerUser.getTrueName());

            jobItemsDto.setCreateTimeStr(sdf.format(jobItemsDto.getCreateTime()));
            jobItemsDto.setPlanSubmissionTimeStr(sdf.format(jobItemsDto.getPlanSubmissionTime()));

            RdmsProjectSubproject subProjectInfo = rdmsSubprojectService.getSubProjectInfo(jobItemsDto.getSubprojectId());
            jobItemsDto.setSubprojectName(subProjectInfo.getLabel());

            RdmsCustomerUser rdmsCustomerUser1 = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getProjectManagerId());
            jobItemsDto.setProjectManagerName(rdmsCustomerUser1.getTrueName());

            RdmsProject projectById = rdmsProjectService.findProjectById(jobItemsDto.getProjectId());
            jobItemsDto.setProjectName(projectById.getProjectName());

            RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(jobItemsDto.getCustomerId());
            jobItemsDto.setCustomerName(rdmsCustomer.getCustomerName());

        }
        PageInfo<RdmsJobItem> pageInfo = new PageInfo<>(rdmsJobItems);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(rdmsJobItemsDtos);
    }

    /**
     * 根据loginUserID进行分页列表查询
     */
    @Transactional
    public void listByLoginUserId(PageDto<RdmsJobItemDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsJobItemExample rdmsJobItemsExample = new RdmsJobItemExample();
        rdmsJobItemsExample.setOrderByClause("create_time desc");
        //执行人的概念是真正干活的人,nextNode则记录下一节点的处理人,是任何参与流转处理的人员; 发给执行人处理时,执行人就是下一节点的人
        List<String> jobItemStatusList = new ArrayList<>();
        jobItemStatusList.add(JobItemStatusEnum.HANDLING.getStatus());

        RdmsJobItemExample.Criteria criteria = rdmsJobItemsExample.createCriteria()
                .andExecutorIdEqualTo(pageDto.getLoginUserId())
                .andStatusIn(jobItemStatusList)
                .andDeletedEqualTo(0);
        if(!ObjectUtils.isEmpty(pageDto.getKeyWord())){
            criteria.andJobNameLike("%"+ pageDto.getKeyWord() +"%");
        }
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemsExample);

        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (RdmsJobItemDto jobItemsDto : rdmsJobItemsDtos) {
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getExecutorId());
            jobItemsDto.setExecutorName(rdmsCustomerUser.getTrueName());

            if (!ObjectUtils.isEmpty(jobItemsDto.getCreateTime())) {
                jobItemsDto.setCreateTimeStr(sdf.format(jobItemsDto.getCreateTime()));
            }
            if (!ObjectUtils.isEmpty(jobItemsDto.getPlanSubmissionTime())) {
                jobItemsDto.setPlanSubmissionTimeStr(sdf.format(jobItemsDto.getPlanSubmissionTime()));
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getSubprojectId())) {
                RdmsProjectSubproject subProjectInfo = rdmsSubprojectService.getSubProjectInfo(jobItemsDto.getSubprojectId());
                if (!ObjectUtils.isEmpty(subProjectInfo)) {
                    jobItemsDto.setSubprojectName(subProjectInfo.getLabel());
                }
            }
            if (!ObjectUtils.isEmpty(jobItemsDto.getProjectManagerId())) {
                RdmsCustomerUser rdmsCustomerUser1 = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getProjectManagerId());
                jobItemsDto.setProjectManagerName(rdmsCustomerUser1.getTrueName());
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getProjectId())) {
                RdmsProject projectById = rdmsProjectService.findProjectById(jobItemsDto.getProjectId());
                if (!ObjectUtils.isEmpty(projectById)) {
                    jobItemsDto.setProjectName(projectById.getProjectName());
                }
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getPreProjectId())) {
                RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(jobItemsDto.getPreProjectId());
                if (!ObjectUtils.isEmpty(rdmsPreProject)) {
                    jobItemsDto.setPreProjectName(rdmsPreProject.getPreProjectName());
                }
            }

            if (jobItemsDto.getType().equals(JobItemTypeEnum.CHARACTER_INT.getType())
                    || jobItemsDto.getType().equals(JobItemTypeEnum.QUALITY.getType())
                    || jobItemsDto.getType().equals(JobItemTypeEnum.TASK_BUG.getType())
                    || jobItemsDto.getType().equals(JobItemTypeEnum.TASK_SUBP.getType())
                    || jobItemsDto.getType().equals(JobItemTypeEnum.TASK_PRODUCT.getType())
                    || jobItemsDto.getType().equals(JobItemTypeEnum.TASK_MILESTONE.getType())
//                    || jobItemsDto.getType().equals(JobItemTypeEnum.TASK_PRO.getType())
                    || jobItemsDto.getType().equals(JobItemTypeEnum.TEST.getType())
            ) {
                List<JobItemTypeEnum> typeEnumList = new ArrayList<>();
                typeEnumList.add(JobItemTypeEnum.ASSIST);
                typeEnumList.add(JobItemTypeEnum.BUG);
                List<JobItemStatusEnum> statusEnumList = new ArrayList<>();
                statusEnumList.add(JobItemStatusEnum.SUBMIT);
                Integer numOfAssistByTaskJobitemId = this.getNumOfAssistByTaskJobitemId(jobItemsDto.getId(), statusEnumList, typeEnumList);
                jobItemsDto.setSubmitJobNum(numOfAssistByTaskJobitemId);
            }

            String processStatus = this.getProcessStatus(jobItemsDto.getId());
            jobItemsDto.setProcessStatus(processStatus);

            RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(jobItemsDto.getCustomerId());
            jobItemsDto.setCustomerName(rdmsCustomer.getCustomerName());

        }
        PageInfo<RdmsJobItem> pageInfo = new PageInfo<>(rdmsJobItems);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(rdmsJobItemsDtos);
    }

    @Transactional
    public void getTestJobListBySubproject(PageDto<RdmsJobItemDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsJobItemExample rdmsJobItemsExample = new RdmsJobItemExample();
        rdmsJobItemsExample.setOrderByClause("create_time desc");
        //执行人的概念是真正干活的人,nextNode则记录下一节点的处理人,是任何参与流转处理的人员; 发给执行人处理时,执行人就是下一节点的人
        List<String> jobItemStatusList = new ArrayList<>();
        jobItemStatusList.add(JobItemStatusEnum.HANDLING.getStatus());
        jobItemStatusList.add(JobItemStatusEnum.SUBMIT.getStatus());
        jobItemStatusList.add(JobItemStatusEnum.TESTING.getStatus());
        jobItemStatusList.add(JobItemStatusEnum.EVALUATE.getStatus());

        List<String> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.TEST.getType());
        typeList.add(JobItemTypeEnum.TASK_TEST.getType());

        RdmsJobItemExample.Criteria criteria = rdmsJobItemsExample.createCriteria()
                .andSubprojectIdEqualTo(pageDto.getSubprojectId())
                .andStatusIn(jobItemStatusList)
                .andTypeIn(typeList)
                .andDeletedEqualTo(0);
        if(!ObjectUtils.isEmpty(pageDto.getKeyWord())){
            criteria.andJobNameLike("%"+ pageDto.getKeyWord() +"%");
        }
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemsExample);

        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (RdmsJobItemDto jobItemsDto : rdmsJobItemsDtos) {
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getExecutorId());
            jobItemsDto.setExecutorName(rdmsCustomerUser.getTrueName());

            if (!ObjectUtils.isEmpty(jobItemsDto.getCreateTime())) {
                jobItemsDto.setCreateTimeStr(sdf.format(jobItemsDto.getCreateTime()));
            }
            if (!ObjectUtils.isEmpty(jobItemsDto.getPlanSubmissionTime())) {
                jobItemsDto.setPlanSubmissionTimeStr(sdf.format(jobItemsDto.getPlanSubmissionTime()));
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getSubprojectId())) {
                RdmsProjectSubproject subProjectInfo = rdmsSubprojectService.getSubProjectInfo(jobItemsDto.getSubprojectId());
                if (!ObjectUtils.isEmpty(subProjectInfo)) {
                    jobItemsDto.setSubprojectName(subProjectInfo.getLabel());
                }
            }
            if (!ObjectUtils.isEmpty(jobItemsDto.getProjectManagerId())) {
                RdmsCustomerUser rdmsCustomerUser1 = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getProjectManagerId());
                jobItemsDto.setProjectManagerName(rdmsCustomerUser1.getTrueName());
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getProjectId())) {
                RdmsProject projectById = rdmsProjectService.findProjectById(jobItemsDto.getProjectId());
                if (!ObjectUtils.isEmpty(projectById)) {
                    jobItemsDto.setProjectName(projectById.getProjectName());
                }
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getPreProjectId())) {
                RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(jobItemsDto.getPreProjectId());
                if (!ObjectUtils.isEmpty(rdmsPreProject)) {
                    jobItemsDto.setPreProjectName(rdmsPreProject.getPreProjectName());
                }
            }


            String processStatus = this.getProcessStatus(jobItemsDto.getId());
            jobItemsDto.setProcessStatus(processStatus);

            RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(jobItemsDto.getCustomerId());
            jobItemsDto.setCustomerName(rdmsCustomer.getCustomerName());

        }
        PageInfo<RdmsJobItem> pageInfo = new PageInfo<>(rdmsJobItems);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(rdmsJobItemsDtos);
    }

    @Transactional
    public void getBugJobListBySubproject(PageDto<RdmsJobItemDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsJobItemExample rdmsJobItemsExample = new RdmsJobItemExample();
        rdmsJobItemsExample.setOrderByClause("create_time desc");
        //执行人的概念是真正干活的人,nextNode则记录下一节点的处理人,是任何参与流转处理的人员; 发给执行人处理时,执行人就是下一节点的人
        List<String> jobItemStatusList = new ArrayList<>();
        jobItemStatusList.add(JobItemStatusEnum.HANDLING.getStatus());
        jobItemStatusList.add(JobItemStatusEnum.SUBMIT.getStatus());
        jobItemStatusList.add(JobItemStatusEnum.TESTING.getStatus());
        jobItemStatusList.add(JobItemStatusEnum.EVALUATE.getStatus());

        List<String> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.BUG.getType());

        RdmsJobItemExample.Criteria criteria = rdmsJobItemsExample.createCriteria()
                .andSubprojectIdEqualTo(pageDto.getSubprojectId())
                .andStatusIn(jobItemStatusList)
                .andTypeIn(typeList)
                .andDeletedEqualTo(0);
        if(!ObjectUtils.isEmpty(pageDto.getKeyWord())){
            criteria.andJobNameLike("%"+ pageDto.getKeyWord() +"%");
        }
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemsExample);

        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (RdmsJobItemDto jobItemsDto : rdmsJobItemsDtos) {
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getExecutorId());
            jobItemsDto.setExecutorName(rdmsCustomerUser.getTrueName());

            if (!ObjectUtils.isEmpty(jobItemsDto.getCreateTime())) {
                jobItemsDto.setCreateTimeStr(sdf.format(jobItemsDto.getCreateTime()));
            }
            if (!ObjectUtils.isEmpty(jobItemsDto.getPlanSubmissionTime())) {
                jobItemsDto.setPlanSubmissionTimeStr(sdf.format(jobItemsDto.getPlanSubmissionTime()));
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getSubprojectId())) {
                RdmsProjectSubproject subProjectInfo = rdmsSubprojectService.getSubProjectInfo(jobItemsDto.getSubprojectId());
                if (!ObjectUtils.isEmpty(subProjectInfo)) {
                    jobItemsDto.setSubprojectName(subProjectInfo.getLabel());
                }
            }
            if (!ObjectUtils.isEmpty(jobItemsDto.getProjectManagerId())) {
                RdmsCustomerUser rdmsCustomerUser1 = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getProjectManagerId());
                jobItemsDto.setProjectManagerName(rdmsCustomerUser1.getTrueName());
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getProjectId())) {
                RdmsProject projectById = rdmsProjectService.findProjectById(jobItemsDto.getProjectId());
                if (!ObjectUtils.isEmpty(projectById)) {
                    jobItemsDto.setProjectName(projectById.getProjectName());
                }
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getPreProjectId())) {
                RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(jobItemsDto.getPreProjectId());
                if (!ObjectUtils.isEmpty(rdmsPreProject)) {
                    jobItemsDto.setPreProjectName(rdmsPreProject.getPreProjectName());
                }
            }


            String processStatus = this.getProcessStatus(jobItemsDto.getId());
            jobItemsDto.setProcessStatus(processStatus);

            RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(jobItemsDto.getCustomerId());
            jobItemsDto.setCustomerName(rdmsCustomer.getCustomerName());

        }
        PageInfo<RdmsJobItem> pageInfo = new PageInfo<>(rdmsJobItems);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(rdmsJobItemsDtos);
    }

    @Transactional
    public List<RdmsJobItemDto> jobListByLoginUserId(String loginUserId) {
        RdmsJobItemExample rdmsJobItemsExample = new RdmsJobItemExample();
        rdmsJobItemsExample.setOrderByClause("plan_submission_time asc, create_time asc");
        //执行人的概念是真正干活的人,nextNode则记录下一节点的处理人,是任何参与流转处理的人员; 发给执行人处理时,执行人就是下一节点的人
        List<String> jobItemStatusList = new ArrayList<>();
        jobItemStatusList.add(JobItemStatusEnum.HANDLING.getStatus());
        jobItemStatusList.add(JobItemStatusEnum.SUBMIT.getStatus());
        jobItemStatusList.add(JobItemStatusEnum.TESTING.getStatus());
        jobItemStatusList.add(JobItemStatusEnum.EVALUATE.getStatus());

        rdmsJobItemsExample.createCriteria()
                .andExecutorIdEqualTo(loginUserId)
                .andStatusIn(jobItemStatusList)
                .andDeletedEqualTo(0);
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemsExample);

        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (RdmsJobItemDto jobItemsDto : rdmsJobItemsDtos) {
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getExecutorId());
            jobItemsDto.setExecutorName(rdmsCustomerUser.getTrueName());

            if (!ObjectUtils.isEmpty(jobItemsDto.getCreateTime())) {
                jobItemsDto.setCreateTimeStr(sdf.format(jobItemsDto.getCreateTime()));
            }
            if (!ObjectUtils.isEmpty(jobItemsDto.getPlanSubmissionTime())) {
                jobItemsDto.setPlanSubmissionTimeStr(sdf.format(jobItemsDto.getPlanSubmissionTime()));
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getSubprojectId())) {
                RdmsProjectSubproject subProjectInfo = rdmsSubprojectService.getSubProjectInfo(jobItemsDto.getSubprojectId());
                if (!ObjectUtils.isEmpty(subProjectInfo)) {
                    jobItemsDto.setSubprojectName(subProjectInfo.getLabel());
                }
            }
            if (!ObjectUtils.isEmpty(jobItemsDto.getProjectManagerId())) {
                RdmsCustomerUser rdmsCustomerUser1 = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getProjectManagerId());
                jobItemsDto.setProjectManagerName(rdmsCustomerUser1.getTrueName());
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getProjectId())) {
                RdmsProject projectById = rdmsProjectService.findProjectById(jobItemsDto.getProjectId());
                if (!ObjectUtils.isEmpty(projectById)) {
                    jobItemsDto.setProjectName(projectById.getProjectName());
                }
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getPreProjectId())) {
                RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(jobItemsDto.getPreProjectId());
                if (!ObjectUtils.isEmpty(rdmsPreProject)) {
                    jobItemsDto.setPreProjectName(rdmsPreProject.getPreProjectName());
                }
            }

            if (jobItemsDto.getType().equals(JobItemTypeEnum.CHARACTER_INT.getType())
                    || jobItemsDto.getType().equals(JobItemTypeEnum.QUALITY.getType())
                    || jobItemsDto.getType().equals(JobItemTypeEnum.TASK_SUBP.getType())
                    || jobItemsDto.getType().equals(JobItemTypeEnum.TASK_PRODUCT.getType())
                    || jobItemsDto.getType().equals(JobItemTypeEnum.TASK_MILESTONE.getType())
//                    || jobItemsDto.getType().equals(JobItemTypeEnum.TASK_PRO.getType())
                    || jobItemsDto.getType().equals(JobItemTypeEnum.TEST.getType())
            ) {
                List<JobItemTypeEnum> typeEnumList = new ArrayList<>();
                typeEnumList.add(JobItemTypeEnum.ASSIST);
                typeEnumList.add(JobItemTypeEnum.BUG);
                List<JobItemStatusEnum> statusEnumList = new ArrayList<>();
                statusEnumList.add(JobItemStatusEnum.SUBMIT);
                Integer numOfAssistByTaskJobitemId = this.getNumOfAssistByTaskJobitemId(jobItemsDto.getId(), statusEnumList, typeEnumList);
                jobItemsDto.setSubmitJobNum(numOfAssistByTaskJobitemId);
            }

            String processStatus = this.getProcessStatus(jobItemsDto.getId());
            jobItemsDto.setProcessStatus(processStatus);

            RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(jobItemsDto.getCustomerId());
            jobItemsDto.setCustomerName(rdmsCustomer.getCustomerName());

        }
        return rdmsJobItemsDtos;
    }

    /**
     * 根据loginUserID进行分页列表查询
     */
    @Transactional
    public Integer countJobitemsByLoginUserId(String customerUserId) {
        RdmsJobItemExample rdmsJobItemsExample = new RdmsJobItemExample();
        List<String> jobItemStatusList = new ArrayList<>();
        jobItemStatusList.add(JobItemStatusEnum.HANDLING.getStatus());

        rdmsJobItemsExample.createCriteria()
                .andExecutorIdEqualTo(customerUserId)
                .andStatusIn(jobItemStatusList)
                .andDeletedEqualTo(0);
        return (Integer) (int) rdmsJobItemsMapper.countByExample(rdmsJobItemsExample);
    }

    @Transactional
    public Integer getNumOfAssistByTaskJobitemId(String taskJobitemId, List<JobItemStatusEnum> statusEnumList, List<JobItemTypeEnum> typeEnumList) {
        RdmsJobItemExample rdmsJobItemsExample = new RdmsJobItemExample();

        if (!CollectionUtils.isEmpty(statusEnumList)) {
            for (JobItemStatusEnum statusEnum : statusEnumList) {
                if (!ObjectUtils.isEmpty(statusEnum.getStatus())) {
                    JobItemStatusEnum jobItemStatusEnumByStatus = JobItemStatusEnum.getJobItemStatusEnumByStatus(statusEnum.getStatus());
                    if (ObjectUtils.isEmpty(jobItemStatusEnumByStatus)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }

        if (!CollectionUtils.isEmpty(typeEnumList)) {
            for (JobItemTypeEnum typeEnum : typeEnumList) {
                if (!ObjectUtils.isEmpty(typeEnum.getType())) {
                    JobItemTypeEnum jobItemTypeEnumByType = JobItemTypeEnum.getJobItemTypeEnumByType(typeEnum.getType());
                    if (ObjectUtils.isEmpty(jobItemTypeEnumByType)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }

        RdmsJobItemExample.Criteria criteria = rdmsJobItemsExample.createCriteria()
                .andParentJobitemIdEqualTo(taskJobitemId)
                .andDeletedEqualTo(0);

        List<String> statusList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(statusEnumList)) {
            for (JobItemStatusEnum statusEnum : statusEnumList) {
                statusList.add(statusEnum.getStatus());
            }
            if (!CollectionUtils.isEmpty(statusList)) {
                criteria.andStatusIn(statusList);
            }
        }

        List<String> typeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(typeEnumList)) {
            for (JobItemTypeEnum typeEnum : typeEnumList) {
                typeList.add(typeEnum.getType());
            }
            if (!CollectionUtils.isEmpty(typeList)) {
                criteria.andTypeIn(typeList);
            }
        }

        return (Integer) (int) rdmsJobItemsMapper.countByExample(rdmsJobItemsExample);
    }

    /**
     * 根据loginUserID进行分页列表查询
     */
    @Transactional
    public void listReviewsByLoginUserId(PageDto<RdmsJobItemDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsJobItemExample rdmsJobItemsExample = new RdmsJobItemExample();
        rdmsJobItemsExample.setOrderByClause("plan_submission_time desc, create_time desc");
        //执行人的概念是真正干活的人,nextNode则记录下一节点的处理人,是任何参与流转处理的人员; 发给执行人处理时,执行人就是下一节点的人
        rdmsJobItemsExample.createCriteria()
                .andExecutorIdEqualTo(pageDto.getKeyWord())   //产品经理
                .andStatusEqualTo(JobItemStatusEnum.HANDLING.getStatus())
                .andTypeEqualTo(JobItemTypeEnum.REVIEW_COO.getType())   //还要是评审工单
                .andDeletedEqualTo(0);
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemsExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (RdmsJobItemDto jobItemsDto : rdmsJobItemsDtos) {
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getExecutorId());
            jobItemsDto.setExecutorName(rdmsCustomerUser.getTrueName());

            jobItemsDto.setCreateTimeStr(sdf.format(jobItemsDto.getCreateTime()));
            jobItemsDto.setPlanSubmissionTimeStr(sdf.format(jobItemsDto.getPlanSubmissionTime()));

            if (!ObjectUtils.isEmpty(jobItemsDto.getSubprojectId())) {
                RdmsProjectSubproject subProjectInfo = rdmsSubprojectService.getSubProjectInfo(jobItemsDto.getSubprojectId());
                jobItemsDto.setSubprojectName(subProjectInfo.getLabel());
            }
            if (!ObjectUtils.isEmpty(jobItemsDto.getProjectManagerId())) {
                RdmsCustomerUser rdmsCustomerUser1 = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getProjectManagerId());
                jobItemsDto.setProjectManagerName(rdmsCustomerUser1.getTrueName());
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getProjectId())) {
                RdmsProject projectById = rdmsProjectService.findProjectById(jobItemsDto.getProjectId());
                jobItemsDto.setProjectName(projectById.getProjectName());
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getPreProjectId())) {

                RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(jobItemsDto.getPreProjectId());
                jobItemsDto.setPreProjectName(rdmsPreProject.getPreProjectName());
            }

            RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(jobItemsDto.getCustomerId());
            jobItemsDto.setCustomerName(rdmsCustomer.getCustomerName());

        }
        PageInfo<RdmsJobItem> pageInfo = new PageInfo<>(rdmsJobItems);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(rdmsJobItemsDtos);
    }

    /**
     * 根据loginUserID进行分页列表查询
     */
    @Transactional
    public List<RdmsJobItemDto> getReviewJobitemsByCharacterId(String characterId) {
        List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
        jobitemTypeList.add(JobItemTypeEnum.REVIEW);

        List<RdmsJobItemDto> jobItemDtos = this.getJobitemListByCharacterIdAndStatusTypeList(characterId, JobItemStatusEnum.SUBMIT.getStatus(), jobitemTypeList);

        return jobItemDtos;
    }

    /**
     * 根据loginUserID进行分页列表查询
     */
    @Transactional
    public List<RdmsJobItemDto> getReviewJobitemsByProjectId(String projectId) {
        List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
        jobitemTypeList.add(JobItemTypeEnum.REVIEW_PRO);

        List<RdmsJobItemDto> jobItemDtos = this.getJobitemListByProjectIdAndStatus(projectId, JobItemStatusEnum.SUBMIT.getStatus(), jobitemTypeList);

        return jobItemDtos;
    }

    /**
     * 对joblist进行简单查询, 没有给出各种夸表信息
     */
    @Transactional
    public void getJobitemHandlingList(PageDto<RdmsJobItemDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsJobItemExample rdmsJobItemsExample = new RdmsJobItemExample();
        rdmsJobItemsExample.setOrderByClause("create_time desc");
        //执行人的概念是真正干活的人,nextNode则记录下一节点的处理人,是任何参与流转处理的人员; 发给执行人处理时,执行人就是下一节点的人
        List<String> statusList = new ArrayList<>();
        statusList.add(JobItemStatusEnum.HANDLING.getStatus());
//        statusList.add(JobItemStatusEnum.REFUSE.getStatus());
        rdmsJobItemsExample.createCriteria()
                .andPreProjectIdEqualTo(pageDto.getKeyWord())
                .andStatusIn(statusList)
                .andProjectTypeEqualTo(pageDto.getType())
                .andDeletedEqualTo(0);
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemsExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        PageInfo<RdmsJobItem> pageInfo = new PageInfo<>(rdmsJobItems);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(rdmsJobItemsDtos);
    }

    /**
     * 对joblist进行简单查询, 没有给出各种夸表信息
     */
    @Transactional
    public void getSystemJobitemHandlingList(PageDto<RdmsJobItemDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsJobItemExample rdmsJobItemsExample = new RdmsJobItemExample();
        rdmsJobItemsExample.setOrderByClause("create_time desc");
        //执行人的概念是真正干活的人,nextNode则记录下一节点的处理人,是任何参与流转处理的人员; 发给执行人处理时,执行人就是下一节点的人
        RdmsJobItemExample.Criteria criteria = rdmsJobItemsExample.createCriteria()
                .andStatusEqualTo(JobItemStatusEnum.HANDLING.getStatus())
                .andProjectManagerIdEqualTo(pageDto.getActor())
                .andProjectTypeEqualTo(ProjectTypeEnum.DEV_PROJECT.getType())
                .andDeletedEqualTo(0);

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemsExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        PageInfo<RdmsJobItem> pageInfo = new PageInfo<>(rdmsJobItems);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(rdmsJobItemsDtos);
    }

    /**
     * 对joblist进行简单查询, 没有给出各种夸表信息
     */
    @Transactional
    public void getDevelopJobitemHandlingList(PageDto<RdmsJobItemDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsJobItemExample rdmsJobItemsExample = new RdmsJobItemExample();
        rdmsJobItemsExample.setOrderByClause("plan_submission_time desc, create_time desc");
        //执行人的概念是真正干活的人,nextNode则记录下一节点的处理人,是任何参与流转处理的人员; 发给执行人处理时,执行人就是下一节点的人
        rdmsJobItemsExample.createCriteria()
                .andSubprojectIdEqualTo(pageDto.getKeyWord())
                .andStatusEqualTo(JobItemStatusEnum.HANDLING.getStatus())
                .andProjectManagerIdEqualTo(pageDto.getActor())  //子项目经理
                .andProjectTypeEqualTo(ProjectTypeEnum.SUB_PROJECT.getType())
                .andDeletedEqualTo(0);
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemsExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        PageInfo<RdmsJobItem> pageInfo = new PageInfo<>(rdmsJobItems);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(rdmsJobItemsDtos);
    }

    /**
     * 对joblist进行简单查询, 没有给出各种夸表信息
     */
    @Transactional
    public void getJobitemEvaluateList(PageDto<RdmsJobItemDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsJobItemExample rdmsJobItemsExample = new RdmsJobItemExample();
        rdmsJobItemsExample.setOrderByClause("create_time desc");
        //执行人的概念是真正干活的人,nextNode则记录下一节点的处理人,是任何参与流转处理的人员; 发给执行人处理时,执行人就是下一节点的人
        rdmsJobItemsExample.createCriteria()
                .andPreProjectIdEqualTo(pageDto.getKeyWord())
                .andStatusEqualTo(JobItemStatusEnum.EVALUATE.getStatus())
                .andCreaterIdEqualTo(pageDto.getActor())
                .andDeletedEqualTo(0);
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemsExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        PageInfo<RdmsJobItem> pageInfo = new PageInfo<>(rdmsJobItems);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(rdmsJobItemsDtos);
    }

    /**
     * 对joblist进行简单查询, 没有给出各种夸表信息
     */
    @Transactional
    public void getSystemJobitemEvaluateList(PageDto<RdmsJobItemDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsJobItemExample rdmsJobItemsExample = new RdmsJobItemExample();
        rdmsJobItemsExample.setOrderByClause("create_time desc");
        //执行人的概念是真正干活的人,nextNode则记录下一节点的处理人,是任何参与流转处理的人员; 发给执行人处理时,执行人就是下一节点的人
        RdmsJobItemExample.Criteria criteria = rdmsJobItemsExample.createCriteria()
                .andStatusEqualTo(JobItemStatusEnum.EVALUATE.getStatus())
                .andProjectManagerIdEqualTo(pageDto.getActor())
                .andProjectTypeEqualTo(ProjectTypeEnum.DEV_PROJECT.getType())
                .andDeletedEqualTo(0);
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemsExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        PageInfo<RdmsJobItem> pageInfo = new PageInfo<>(rdmsJobItems);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(rdmsJobItemsDtos);
    }

    /**
     * 对joblist进行简单查询, 没有给出各种夸表信息
     */
    @Transactional
    public void getDevelopJobitemEvaluateList(PageDto<RdmsJobItemDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsJobItemExample rdmsJobItemsExample = new RdmsJobItemExample();
        rdmsJobItemsExample.setOrderByClause("plan_submission_time desc, create_time desc");
        //执行人的概念是真正干活的人,nextNode则记录下一节点的处理人,是任何参与流转处理的人员; 发给执行人处理时,执行人就是下一节点的人
        rdmsJobItemsExample.createCriteria()
                .andSubprojectIdEqualTo(pageDto.getKeyWord())
                .andStatusEqualTo(JobItemStatusEnum.EVALUATE.getStatus())
                .andProjectManagerIdEqualTo(pageDto.getActor())  //子项目经理
                .andProjectTypeEqualTo(ProjectTypeEnum.SUB_PROJECT.getType())
                .andDeletedEqualTo(0);
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemsExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        PageInfo<RdmsJobItem> pageInfo = new PageInfo<>(rdmsJobItems);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(rdmsJobItemsDtos);
    }

    /**
     * 对joblist进行简单查询, 没有给出各种夸表信息
     */
    @Transactional
    public void getDevelopJobitemSubmitList(PageDto<RdmsJobItemDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsJobItemExample rdmsJobItemsExample = new RdmsJobItemExample();
        rdmsJobItemsExample.setOrderByClause("plan_submission_time desc, create_time desc");
        //执行人的概念是真正干活的人,nextNode则记录下一节点的处理人,是任何参与流转处理的人员; 发给执行人处理时,执行人就是下一节点的人
        rdmsJobItemsExample.createCriteria()
                .andSubprojectIdEqualTo(pageDto.getKeyWord())
                .andStatusEqualTo(JobItemStatusEnum.SUBMIT.getStatus())
                .andProjectManagerIdEqualTo(pageDto.getActor())  //子项目经理
                .andProjectTypeEqualTo(ProjectTypeEnum.SUB_PROJECT.getType())
                .andDeletedEqualTo(0);
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemsExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        PageInfo<RdmsJobItem> pageInfo = new PageInfo<>(rdmsJobItems);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(rdmsJobItemsDtos);
    }

    /**
     * 对joblist进行简单查询, 没有给出各种夸表信息
     */
    @Transactional
    public void getDevelopApprovedJobItemList(PageDto<RdmsJobItemDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsJobItemExample rdmsJobItemsExample = new RdmsJobItemExample();
        rdmsJobItemsExample.setOrderByClause("plan_submission_time desc, create_time desc");
        //执行人的概念是真正干活的人,nextNode则记录下一节点的处理人,是任何参与流转处理的人员; 发给执行人处理时,执行人就是下一节点的人
        rdmsJobItemsExample.createCriteria()
                .andSubprojectIdEqualTo(pageDto.getKeyWord())
                .andStatusEqualTo(JobItemStatusEnum.COMPLETED.getStatus())
                .andProjectManagerIdEqualTo(pageDto.getActor())  //子项目经理
                .andProjectTypeEqualTo(ProjectTypeEnum.SUB_PROJECT.getType())
                .andDeletedEqualTo(0);
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemsExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        PageInfo<RdmsJobItem> pageInfo = new PageInfo<>(rdmsJobItems);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(rdmsJobItemsDtos);
    }

    /**
     * 对joblist进行简单查询, 没有给出各种夸表信息
     */
    @Transactional
    public void getJobitemExamineList(PageDto<RdmsJobItemDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsJobItemExample rdmsJobItemsExample = new RdmsJobItemExample();
        rdmsJobItemsExample.setOrderByClause("create_time desc");
        //执行人的概念是真正干活的人,nextNode则记录下一节点的处理人,是任何参与流转处理的人员; 发给执行人处理时,执行人就是下一节点的人
        rdmsJobItemsExample.createCriteria()
                .andPreProjectIdEqualTo(pageDto.getKeyWord())
                .andStatusEqualTo(JobItemStatusEnum.SUBMIT.getStatus())
                .andProjectTypeEqualTo(pageDto.getType())
                .andDeletedEqualTo(0);
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemsExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
        if (!CollectionUtils.isEmpty(rdmsJobItemsDtos)) {
            int index = 0;
            List<CharacterStatusEnum> statusEnumList = new ArrayList<>();
            statusEnumList.add(CharacterStatusEnum.SUBMIT);
            statusEnumList.add(CharacterStatusEnum.REFUSE);
            for (RdmsJobItemDto jobItemDto : rdmsJobItemsDtos) {
//                List<RdmsCharacterDto> characterList = rdmsCharacterService.getCharacterListByJobItemIdAndStatus(jobItemDto.getId(), CharacterStatusEnum.SUBMIT.getStatus());
                List<RdmsCharacterDto> characterList = rdmsCharacterService.getCharacterListByJobitemIdAndStatusList(jobItemDto.getId(), statusEnumList);
                for (RdmsCharacterDto characterDto : characterList) {
                    characterDto.setDocType(DocTypeEnum.CHARACTER.getType());
                    characterDto.setJobName(characterDto.getCharacterName());
                }
                jobItemDto.setChildren(characterList);
                jobItemDto.setShowFlag("jobitem");
                jobItemDto.setIndex(index += 1);
                jobItemDto.setDocType(DocTypeEnum.JOBITEM.getType());
            }
        }

        PageInfo<RdmsJobItem> pageInfo = new PageInfo<>(rdmsJobItems);
        pageDto.setTotal(pageInfo.getTotal());
        List<RdmsJobItemDto> collect = rdmsJobItemsDtos.stream().filter(item -> (item.getChildren() != null) && (item.getChildren().size()>0)).collect(Collectors.toList());
        pageDto.setList(collect);
    }

    public RdmsHmiJobItemDocPageDto getJobItemDocPageInfo(String jobitemId, String loginUserId) {
        RdmsHmiJobItemDocPageDto jobItemDocPage = new RdmsHmiJobItemDocPageDto();
        RdmsJobItem jobItem = this.selectByPrimaryKey(jobitemId);
        //private String id;
        jobItemDocPage.setId(jobItem.getId());
        //private String jobSerial;
        jobItemDocPage.setJobSerial(jobItem.getJobSerial());
        //private String jobName;
        jobItemDocPage.setJobName(jobItem.getJobName());
        //private String executorId;
        jobItemDocPage.setExecutorId(jobItem.getExecutorId());
        //private String executorName;
        jobItemDocPage.setExecutorName(rdmsCustomerUserService.selectByPrimaryKey(jobItem.getExecutorId()).getTrueName());
        //private String status;
        jobItemDocPage.setStatus(jobItem.getStatus());
        //private String type;
        jobItemDocPage.setType(jobItem.getType());
        //private String taskDescription;
        jobItemDocPage.setTaskDescription(jobItem.getTaskDescription());
        //private Double manhour;
        jobItemDocPage.setManhour(jobItem.getManhour());
        //private List<RdmsFileDto> taskFileList;  //任务附件
        List<RdmsFileDto> fileList = rdmsFileService.getFileListByIdListStr(jobItem.getFileListStr(), loginUserId);
        if (!CollectionUtils.isEmpty(fileList)) {
            for (RdmsFileDto fileDto : fileList) {
                //读取访问权限
                if (!ObjectUtils.isEmpty(fileDto)) {
                    //补充填写文件权限信息
                    RdmsFileAuth byFileId = rdmsFileAuthService.getByFileId(fileDto.getId());
                    if (ObjectUtils.isEmpty(byFileId)) {
                        fileDto.setAuthStatus(true);
                    } else {
                        String authIdsStr = byFileId.getAuthIdsStr();
                        if (ObjectUtils.isEmpty(authIdsStr)) {
                            fileDto.setAuthStatus(true);
                        } else {
                            List<String> authIds = JSON.parseArray(authIdsStr, String.class);
                            if (authIds.contains(loginUserId)) {
                                fileDto.setAuthStatus(true);
                            } else {
                                fileDto.setAuthStatus(false);
                            }
                        }
                    }
                }
            }
        }
        jobItemDocPage.setTaskFileList(fileList);

        //private String evaluateDesc;
        jobItemDocPage.setEvaluateDesc(jobItem.getEvaluateDesc());
        //private Integer evaluateScore;
        jobItemDocPage.setEvaluateScore(jobItem.getEvaluateScore());
        //private String submitDescription;
        List<RdmsJobItemProperty> jobItemProperty = rdmsJobItemPropertyService.getJobItemPropertyByJobItemId(jobitemId);
        if (!CollectionUtils.isEmpty(jobItemProperty)) {
            jobItemDocPage.setSubmitDescription(jobItemProperty.get(0).getJobDescription());
            //private List<RdmsFileDto> submitFileList;  //提交附件
            List<RdmsFileDto> propertyfileList = rdmsFileService.getFileListByIdListStr(jobItemProperty.get(0).getFileListStr(), loginUserId);
            jobItemDocPage.setSubmitFileList(propertyfileList);
        }

        //private List<RdmsFileDto> processFileList;  //执行过程附件
        List<RdmsJobItemProcess> jobProcessList = rdmsJobItemProcessService.getJobProcessListByJobId(jobitemId);
        if (!CollectionUtils.isEmpty(jobProcessList)) {
            List<String> fileIdListStrList = jobProcessList.stream().map(RdmsJobItemProcess::getFileListStr).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(fileIdListStrList)) {
                List<String> processFileIdList = new ArrayList<>();
                for (String fileIdListStr : fileIdListStrList) {
                    List<String> strings = JSON.parseArray(fileIdListStr, String.class);
                    if (!CollectionUtils.isEmpty(strings)) {
                        processFileIdList.addAll(strings);
                    }
                }
                List<RdmsFileDto> processFileList = rdmsFileService.getFileListByIdList(processFileIdList);
                jobItemDocPage.setProcessFileList(processFileList);
            }

        }

        return jobItemDocPage;
    }

    @Transactional
    public List<RdmsFileDto> getJobItemArchiveDocs(String jobitemId, String loginUserId) {
        RdmsJobItem jobItem = this.selectByPrimaryKey(jobitemId);
        List<RdmsFileDto> files = rdmsFileService.getFileListByIdListStr(jobItem.getFileListStr(), loginUserId);
        List<RdmsFileDto> fileList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(files)) {
            fileList.addAll(files);
        }

        List<RdmsJobItemProperty> jobItemProperty = rdmsJobItemPropertyService.getJobItemPropertyByJobItemId(jobitemId);
        if (!CollectionUtils.isEmpty(jobItemProperty)) {
            List<RdmsFileDto> propertyfileList = rdmsFileService.getFileListByIdListStr(jobItemProperty.get(0).getFileListStr(), loginUserId);
            if (!CollectionUtils.isEmpty(propertyfileList)) {
                fileList.addAll(propertyfileList);
            }
        }

        List<RdmsJobItemProcess> jobProcessList = rdmsJobItemProcessService.getJobProcessListByJobId(jobitemId);
        if (!CollectionUtils.isEmpty(jobProcessList)) {
            List<String> fileIdListStrList = jobProcessList.stream().map(RdmsJobItemProcess::getFileListStr).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(fileIdListStrList)) {
                List<String> processFileIdList = new ArrayList<>();
                for (String fileIdListStr : fileIdListStrList) {
                    List<String> strings = JSON.parseArray(fileIdListStr, String.class);
                    if (!CollectionUtils.isEmpty(strings)) {
                        processFileIdList.addAll(strings);
                    }
                }
                List<RdmsFileDto> processFileList = rdmsFileService.getFileListByIdList(processFileIdList);
                if (!CollectionUtils.isEmpty(processFileList)) {
                    fileList.addAll(processFileList);
                }
            }
        }

        //获得关联任务的附件
        List<String> associateTaskPropertyFileIdList = rdmsJobItemAssociateService.getAssociateTaskPropertyFileIdList(jobitemId);
        if (!CollectionUtils.isEmpty(associateTaskPropertyFileIdList)) {
            String jsonString = JSON.toJSONString(associateTaskPropertyFileIdList);
            List<RdmsFileDto> propertyfileList = rdmsFileService.getFileListByIdListStr(jsonString, loginUserId);
            if (!CollectionUtils.isEmpty(propertyfileList)) {
                fileList.addAll(propertyfileList);
            }
        }
        //如果关联任务声明替换某个附件,则排除之
        List<RdmsJobitemAssociate> associateList = rdmsJobItemAssociateService.getJobitemAssociateList(jobitemId);
        if (!CollectionUtils.isEmpty(associateList)) {
            for (RdmsJobitemAssociate associate : associateList) {
                if (!ObjectUtils.isEmpty(associate.getReplaceFileIdsStr())) {
                    List<String> fileIdList2 = JSON.parseArray(associate.getReplaceFileIdsStr(), String.class);
                    if (!CollectionUtils.isEmpty(fileIdList2)) {
                        fileList = fileList.stream().filter(file -> !fileIdList2.contains(file.getId())).collect(Collectors.toList());
                    }
                }
            }
        }

        return fileList;
    }

    /**
     * 对joblist进行简单查询, 没有给出各种夸表信息
     */
    @Transactional
    public RdmsCharacterDto getReCheckCharactersByJobitemId(String jobitemId) {
        RdmsJobItem reCheckJobItem = this.selectByPrimaryKey(jobitemId);
        RdmsJobItem recheckedJobitem = this.selectByPrimaryKey(reCheckJobItem.getTestedJobitemId());
        RdmsCharacterDto rdmsCharacterDto = new RdmsCharacterDto();
        if (recheckedJobitem.getType().equals(JobItemTypeEnum.DECOMPOSE.getType())) {
            RdmsCharacter originCharacter = rdmsCharacterService.selectByPrimaryKey(reCheckJobItem.getCharacterId());  //被分解的工单
            if (originCharacter.getStatus().equals(CharacterStatusEnum.DECOMPOSED.getStatus())) {
                rdmsCharacterDto = CopyUtil.copy(originCharacter, RdmsCharacterDto.class);
                rdmsCharacterDto.setDocType("原始功能");

                List<RdmsCharacterDto> characterList = rdmsCharacterService.getCharacterListByParentId(reCheckJobItem.getCharacterId());
                List<RdmsCharacterDto> characterDtoList = new ArrayList<>();
                for (RdmsCharacterDto characterDto : characterList) {
                    RdmsCharacterDto reCheckCharacterDto = CopyUtil.copy(characterDto, RdmsCharacterDto.class);
                    reCheckCharacterDto.setDocType("分解功能");
                    characterDtoList.add(reCheckCharacterDto);
                }
                rdmsCharacterDto.setChildrenList(characterDtoList);
            }
        }

        if (recheckedJobitem.getType().equals(JobItemTypeEnum.ITERATION.getType())) {
            RdmsCharacter iterationCharacter = rdmsCharacterService.selectByPrimaryKey(reCheckJobItem.getCharacterId());  //修订编辑的工单
            RdmsCharacterDto characterDto = CopyUtil.copy(iterationCharacter, RdmsCharacterDto.class);
            characterDto.setDocType("修订功能");
            List<RdmsCharacterDto> characterDtoList = new ArrayList<>();
            characterDtoList.add(characterDto);
            //获得原始功能
            RdmsCharacterExample characterExample = new RdmsCharacterExample();
            RdmsCharacterExample.Criteria criteria = characterExample.createCriteria()
                    .andCharacterSerialEqualTo(characterDto.getCharacterSerial())
                    .andStatusEqualTo(CharacterStatusEnum.HISTORY.getStatus());
            if (characterDto.getIterationVersion() > 0) {
                criteria.andIterationVersionEqualTo(characterDto.getIterationVersion() - 1);
            }
            List<RdmsCharacter> originCharacter = rdmsCharacterMapper.selectByExample(characterExample);
            List<RdmsCharacterDto> characterDtos = CopyUtil.copyList(originCharacter, RdmsCharacterDto.class);
            rdmsCharacterDto = characterDtos.get(0);
            rdmsCharacterDto.setDocType("原始功能");
            rdmsCharacterDto.setChildrenList(characterDtoList);
        }

        return rdmsCharacterDto;
    }

    /**
     * 对joblist进行简单查询, 没有给出各种夸表信息
     */
    @Transactional
    public void getSystemJobitemExamineList(PageDto<RdmsJobItemDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsJobItemExample rdmsJobItemsExample = new RdmsJobItemExample();
        rdmsJobItemsExample.setOrderByClause("create_time desc");
        //执行人的概念是真正干活的人,nextNode则记录下一节点的处理人,是任何参与流转处理的人员; 发给执行人处理时,执行人就是下一节点的人
        RdmsJobItemExample.Criteria criteria = rdmsJobItemsExample.createCriteria()
                .andStatusEqualTo(JobItemStatusEnum.SUBMIT.getStatus())
                .andProjectManagerIdEqualTo(pageDto.getActor())
                .andProjectIdEqualTo(pageDto.getKeyWord())
                .andProjectTypeEqualTo(ProjectTypeEnum.DEV_PROJECT.getType())
                .andDeletedEqualTo(0);
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemsExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
        if (!CollectionUtils.isEmpty(rdmsJobItemsDtos)) {
            int index = 0;
            for (RdmsJobItemDto jobItemDto : rdmsJobItemsDtos) {
                List<RdmsCharacterDto> characterList = rdmsCharacterService.getCharacterListByJobItemIdAndStatus(jobItemDto.getId(), CharacterStatusEnum.SUBMIT.getStatus());
                for (RdmsCharacterDto characterDto : characterList) {
                    characterDto.setDocType(DocTypeEnum.CHARACTER.getType());
                    characterDto.setJobName(characterDto.getCharacterName());
                }
                jobItemDto.setChildren(characterList);
                jobItemDto.setShowFlag("jobitem");
                jobItemDto.setIndex(index += 1);
                jobItemDto.setDocType(DocTypeEnum.JOBITEM.getType());

                RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(jobItemDto.getProjectId());
                jobItemDto.setRdType(rdmsProject.getRdType());
            }
        }

        PageInfo<RdmsJobItem> pageInfo = new PageInfo<>(rdmsJobItems);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(rdmsJobItemsDtos);
    }

    @Transactional
    public RdmsHmiIdsDto getIdsDtoByJobitemId(String jobitemId) {
        RdmsHmiIdsDto idsDto = new RdmsHmiIdsDto();
        RdmsJobItem rdmsJobItem = this.selectByPrimaryKey(jobitemId);
        if (!ObjectUtils.isEmpty(rdmsJobItem)) {
            idsDto = CopyUtil.copy(rdmsJobItem, RdmsHmiIdsDto.class);
            idsDto.setJobitemId(rdmsJobItem.getId());
            idsDto.setPlanCompleteTime(rdmsJobItem.getPlanSubmissionTime());
            return idsDto;
        }
        return null;
    }

    @Transactional
    public List<String> getJobitemIdListByCharacterIdAndStatusTypeList(String characterId, String jobItemStatus, List<JobItemTypeEnum> jobitemTypeList) {
        List<RdmsJobItemDto> jobitemList = this.getJobitemListByCharacterIdAndStatusTypeList(characterId, jobItemStatus, jobitemTypeList);
        return jobitemList.stream().map(RdmsJobItemDto::getId).collect(Collectors.toList());
    }

    /**
     * @param characterId
     */
    @Transactional
    public List<RdmsJobItemDto> getJobitemListByCharacterIdAndStatusTypeList(String characterId, String jobItemStatus, List<JobItemTypeEnum> jobitemTypeList) {
        if (!ObjectUtils.isEmpty(jobItemStatus)) {
            JobItemStatusEnum jobItemStatusEnumByStatus = JobItemStatusEnum.getJobItemStatusEnumByStatus(jobItemStatus);
            if (ObjectUtils.isEmpty(jobItemStatusEnumByStatus)) {
                throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
            }
        }
        if (!CollectionUtils.isEmpty(jobitemTypeList)) {
            for (JobItemTypeEnum typeEnum : jobitemTypeList) {
                if (!ObjectUtils.isEmpty(typeEnum.getType())) {
                    JobItemTypeEnum jobItemTypeEnumByType = JobItemTypeEnum.getJobItemTypeEnumByType(typeEnum.getType());
                    if (ObjectUtils.isEmpty(jobItemTypeEnumByType)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }

        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andCharacterIdEqualTo(characterId).andDeletedEqualTo(0);
        if (!ObjectUtils.isEmpty(jobItemStatus)) {
            criteria1.andStatusEqualTo(jobItemStatus);
        }

        List<String> statusList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jobitemTypeList)) {
            for (JobItemTypeEnum typeEnum : jobitemTypeList) {
                statusList.add(typeEnum.getType());
            }
            if (!CollectionUtils.isEmpty(statusList)) {
                criteria1.andTypeIn(statusList);
            }
        }

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
        for (RdmsJobItemDto itemDto : rdmsJobItemsDtos) {
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(itemDto.getExecutorId());
            itemDto.setExecutorName(rdmsCustomerUser.getTrueName());
        }

        return getRdmsJobItemDtos(rdmsJobItemsDtos);
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemListByQualityIdAndStatusTypeList(String qualityId, String jobItemStatus, List<JobItemTypeEnum> jobitemTypeList) {
        if (!ObjectUtils.isEmpty(jobItemStatus)) {
            JobItemStatusEnum jobItemStatusEnumByStatus = JobItemStatusEnum.getJobItemStatusEnumByStatus(jobItemStatus);
            if (ObjectUtils.isEmpty(jobItemStatusEnumByStatus)) {
                throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
            }
        }
        if (!CollectionUtils.isEmpty(jobitemTypeList)) {
            for (JobItemTypeEnum typeEnum : jobitemTypeList) {
                if (!ObjectUtils.isEmpty(typeEnum.getType())) {
                    JobItemTypeEnum jobItemTypeEnumByType = JobItemTypeEnum.getJobItemTypeEnumByType(typeEnum.getType());
                    if (ObjectUtils.isEmpty(jobItemTypeEnumByType)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }

        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andQualityIdEqualTo(qualityId).andDeletedEqualTo(0);
        if (!ObjectUtils.isEmpty(jobItemStatus)) {
            criteria1.andStatusEqualTo(jobItemStatus);
        }

        List<String> statusList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jobitemTypeList)) {
            for (JobItemTypeEnum typeEnum : jobitemTypeList) {
                statusList.add(typeEnum.getType());
            }
            if (!CollectionUtils.isEmpty(statusList)) {
                criteria1.andTypeIn(statusList);
            }
        }

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
        for (RdmsJobItemDto itemDto : rdmsJobItemsDtos) {
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(itemDto.getExecutorId());
            itemDto.setExecutorName(rdmsCustomerUser.getTrueName());
        }

        return getRdmsJobItemDtos(rdmsJobItemsDtos);
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemListByBugIdAndStatusTypeList(String bugId, String jobItemStatus, List<JobItemTypeEnum> jobitemTypeList) {
        if (!ObjectUtils.isEmpty(jobItemStatus)) {
            JobItemStatusEnum jobItemStatusEnumByStatus = JobItemStatusEnum.getJobItemStatusEnumByStatus(jobItemStatus);
            if (ObjectUtils.isEmpty(jobItemStatusEnumByStatus)) {
                throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
            }
        }
        if (!CollectionUtils.isEmpty(jobitemTypeList)) {
            for (JobItemTypeEnum typeEnum : jobitemTypeList) {
                if (!ObjectUtils.isEmpty(typeEnum.getType())) {
                    JobItemTypeEnum jobItemTypeEnumByType = JobItemTypeEnum.getJobItemTypeEnumByType(typeEnum.getType());
                    if (ObjectUtils.isEmpty(jobItemTypeEnumByType)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }

        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andQualityIdEqualTo(bugId)    //复用qualityId 字段
                .andDeletedEqualTo(0);
        if (!ObjectUtils.isEmpty(jobItemStatus)) {
            criteria1.andStatusEqualTo(jobItemStatus);
        }

        List<String> typeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jobitemTypeList)) {
            for (JobItemTypeEnum typeEnum : jobitemTypeList) {
                typeList.add(typeEnum.getType());
            }
            if (!CollectionUtils.isEmpty(typeList)) {
                criteria1.andTypeIn(typeList);
            }
        }

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
        for (RdmsJobItemDto itemDto : rdmsJobItemsDtos) {
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(itemDto.getExecutorId());
            itemDto.setExecutorName(rdmsCustomerUser.getTrueName());
        }

        return getRdmsJobItemDtos(rdmsJobItemsDtos);
    }

    /**
     * @param characterId
     */
    @Transactional
    public List<RdmsJobItemDto> getJobitemListByCharacterIdAndStatusListTypeList(String characterId, List<JobItemStatusEnum> jobItemStatusList, List<JobItemTypeEnum> jobitemTypeList) {
        if (!CollectionUtils.isEmpty(jobItemStatusList)) {
            for (JobItemStatusEnum statusEnum : jobItemStatusList) {
                if (!ObjectUtils.isEmpty(statusEnum.getStatus())) {
                    JobItemStatusEnum jobItemStatusEnumByStatus = JobItemStatusEnum.getJobItemStatusEnumByStatus(statusEnum.getStatus());
                    if (ObjectUtils.isEmpty(jobItemStatusEnumByStatus)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }
        if (!CollectionUtils.isEmpty(jobitemTypeList)) {
            for (JobItemTypeEnum typeEnum : jobitemTypeList) {
                if (!ObjectUtils.isEmpty(typeEnum.getType())) {
                    JobItemTypeEnum jobItemTypeEnumByType = JobItemTypeEnum.getJobItemTypeEnumByType(typeEnum.getType());
                    if (ObjectUtils.isEmpty(jobItemTypeEnumByType)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }

        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andCharacterIdEqualTo(characterId).andDeletedEqualTo(0);

        List<String> statusList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jobItemStatusList)) {
            for (JobItemStatusEnum statusEnum : jobItemStatusList) {
                statusList.add(statusEnum.getStatus());
            }
            if (!CollectionUtils.isEmpty(statusList)) {
                criteria1.andStatusIn(statusList);
            }
        }

        List<String> typeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jobitemTypeList)) {
            for (JobItemTypeEnum typeEnum : jobitemTypeList) {
                typeList.add(typeEnum.getType());
            }
            if (!CollectionUtils.isEmpty(typeList)) {
                criteria1.andTypeIn(typeList);
            }
        }

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        return getRdmsJobItemDtos(rdmsJobItemsDtos);
    }


    @Transactional
    public List<RdmsJobItemDto> getJobitemListByQualityIdAndStatusListTypeList(String qualityId, List<JobItemStatusEnum> jobItemStatusList, List<JobItemTypeEnum> jobitemTypeList) {
        if (!CollectionUtils.isEmpty(jobItemStatusList)) {
            for (JobItemStatusEnum statusEnum : jobItemStatusList) {
                if (!ObjectUtils.isEmpty(statusEnum.getStatus())) {
                    JobItemStatusEnum jobItemStatusEnumByStatus = JobItemStatusEnum.getJobItemStatusEnumByStatus(statusEnum.getStatus());
                    if (ObjectUtils.isEmpty(jobItemStatusEnumByStatus)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }
        if (!CollectionUtils.isEmpty(jobitemTypeList)) {
            for (JobItemTypeEnum typeEnum : jobitemTypeList) {
                if (!ObjectUtils.isEmpty(typeEnum.getType())) {
                    JobItemTypeEnum jobItemTypeEnumByType = JobItemTypeEnum.getJobItemTypeEnumByType(typeEnum.getType());
                    if (ObjectUtils.isEmpty(jobItemTypeEnumByType)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }

        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andQualityIdEqualTo(qualityId).andDeletedEqualTo(0);

        List<String> statusList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jobItemStatusList)) {
            for (JobItemStatusEnum statusEnum : jobItemStatusList) {
                statusList.add(statusEnum.getStatus());
            }
            criteria1.andStatusIn(statusList);
        }

        List<String> typeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jobitemTypeList)) {
            for (JobItemTypeEnum typeEnum : jobitemTypeList) {
                typeList.add(typeEnum.getType());
            }
            criteria1.andTypeIn(typeList);
        }

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        return getRdmsJobItemDtos(rdmsJobItemsDtos);
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemListByBugIdAndStatusListTypeList(String bugId, List<JobItemStatusEnum> jobItemStatusList, List<JobItemTypeEnum> jobitemTypeList) {
        if (!CollectionUtils.isEmpty(jobItemStatusList)) {
            for (JobItemStatusEnum statusEnum : jobItemStatusList) {
                if (!ObjectUtils.isEmpty(statusEnum.getStatus())) {
                    JobItemStatusEnum jobItemStatusEnumByStatus = JobItemStatusEnum.getJobItemStatusEnumByStatus(statusEnum.getStatus());
                    if (ObjectUtils.isEmpty(jobItemStatusEnumByStatus)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }
        if (!CollectionUtils.isEmpty(jobitemTypeList)) {
            for (JobItemTypeEnum typeEnum : jobitemTypeList) {
                if (!ObjectUtils.isEmpty(typeEnum.getType())) {
                    JobItemTypeEnum jobItemTypeEnumByType = JobItemTypeEnum.getJobItemTypeEnumByType(typeEnum.getType());
                    if (ObjectUtils.isEmpty(jobItemTypeEnumByType)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }

        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andQualityIdEqualTo(bugId)
                .andDeletedEqualTo(0);

        List<String> statusList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jobItemStatusList)) {
            for (JobItemStatusEnum statusEnum : jobItemStatusList) {
                statusList.add(statusEnum.getStatus());
            }
            criteria1.andStatusIn(statusList);
        }

        List<String> typeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jobitemTypeList)) {
            for (JobItemTypeEnum typeEnum : jobitemTypeList) {
                typeList.add(typeEnum.getType());
            }
            criteria1.andTypeIn(typeList);
        }

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        return getRdmsJobItemDtos(rdmsJobItemsDtos);
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemListByCharacterIdAndStatusListTypeListNotInAuxTypeList(String characterId, List<JobItemStatusEnum> statusList, List<JobItemTypeEnum> typeList, List<JobItemTypeEnum> notInAuxTypeList) {

        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria = rdmsJobItemExample.createCriteria();
        criteria.andCharacterIdEqualTo(characterId).andDeletedEqualTo(0);

        List<String> status = new ArrayList<>();
        if (!CollectionUtils.isEmpty(statusList)) {
            for (JobItemStatusEnum statusEnum : statusList) {
                status.add(statusEnum.getStatus());
            }
            if (!CollectionUtils.isEmpty(status)) {
                criteria.andStatusIn(status);
            }
        }

        List<String> types = new ArrayList<>();
        if (!CollectionUtils.isEmpty(typeList)) {
            for (JobItemTypeEnum typeEnum : typeList) {
                types.add(typeEnum.getType());
            }
            if (!CollectionUtils.isEmpty(types)) {
                criteria.andTypeIn(types);
            }
        }

        List<String> auxTypes = new ArrayList<>();
        if (!CollectionUtils.isEmpty(notInAuxTypeList)) {
            for (JobItemTypeEnum typeEnum : notInAuxTypeList) {
                auxTypes.add(typeEnum.getType());
            }
            if (!CollectionUtils.isEmpty(auxTypes)) {
                criteria.andAuxTypeNotIn(auxTypes);  //排除的类型
            }
        }

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        return getRdmsJobItemDtos(rdmsJobItemsDtos);
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemListBySubprojectIdAndStatusListTypeListNotInAuxTypeList(String subprojectId, List<JobItemStatusEnum> statusList, List<JobItemTypeEnum> typeList, List<JobItemTypeEnum> notInAuxTypeList) {

        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria = rdmsJobItemExample.createCriteria();
        criteria.andSubprojectIdEqualTo(subprojectId).andDeletedEqualTo(0);

        List<String> status = new ArrayList<>();
        if (!CollectionUtils.isEmpty(statusList)) {
            for (JobItemStatusEnum statusEnum : statusList) {
                status.add(statusEnum.getStatus());
            }
            if (!CollectionUtils.isEmpty(status)) {
                criteria.andStatusIn(status);
            }
        }

        List<String> types = new ArrayList<>();
        if (!CollectionUtils.isEmpty(typeList)) {
            for (JobItemTypeEnum typeEnum : typeList) {
                types.add(typeEnum.getType());
            }
            if (!CollectionUtils.isEmpty(types)) {
                criteria.andTypeIn(types);
            }
        }

        List<String> auxTypes = new ArrayList<>();
        if (!CollectionUtils.isEmpty(notInAuxTypeList)) {
            for (JobItemTypeEnum typeEnum : notInAuxTypeList) {
                auxTypes.add(typeEnum.getType());
            }
            if (!CollectionUtils.isEmpty(auxTypes)) {
                criteria.andAuxTypeNotIn(auxTypes);  //排除的类型
            }
        }

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        return getRdmsJobItemDtos(rdmsJobItemsDtos);
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemListBySubprojectIdAndStatusListTypeListAuxTypeList(String subprojectId, List<JobItemStatusEnum> statusList, List<JobItemTypeEnum> typeList, List<JobItemTypeEnum> auxTypeList) {

        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria = rdmsJobItemExample.createCriteria();
        criteria.andSubprojectIdEqualTo(subprojectId).andDeletedEqualTo(0);

        List<String> status = new ArrayList<>();
        if (!CollectionUtils.isEmpty(statusList)) {
            for (JobItemStatusEnum statusEnum : statusList) {
                status.add(statusEnum.getStatus());
            }
            if (!CollectionUtils.isEmpty(status)) {
                criteria.andStatusIn(status);
            }
        }

        List<String> types = new ArrayList<>();
        if (!CollectionUtils.isEmpty(typeList)) {
            for (JobItemTypeEnum typeEnum : typeList) {
                types.add(typeEnum.getType());
            }
            if (!CollectionUtils.isEmpty(types)) {
                criteria.andTypeIn(types);
            }
        }

        List<String> auxTypes = new ArrayList<>();
        if (!CollectionUtils.isEmpty(auxTypeList)) {
            for (JobItemTypeEnum typeEnum : auxTypeList) {
                auxTypes.add(typeEnum.getType());
            }
            if (!CollectionUtils.isEmpty(auxTypes)) {
                criteria.andAuxTypeIn(auxTypes);
            }
        }

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        return getRdmsJobItemDtos(rdmsJobItemsDtos);
    }

    /**
     * @param subprojectId
     */
    @Transactional
    public List<RdmsJobItemDto> getJobitemListBySubprojectIdAndStatus(String subprojectId, String jobItemStatus, List<JobItemTypeEnum> jobitemTypeList) {
        if (!ObjectUtils.isEmpty(jobItemStatus)) {
            JobItemStatusEnum jobItemStatusEnumByStatus = JobItemStatusEnum.getJobItemStatusEnumByStatus(jobItemStatus);
            if (ObjectUtils.isEmpty(jobItemStatusEnumByStatus)) {
                throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
            }
        }
        if (!CollectionUtils.isEmpty(jobitemTypeList)) {
            for (JobItemTypeEnum typeEnum : jobitemTypeList) {
                if (!ObjectUtils.isEmpty(typeEnum.getType())) {
                    JobItemTypeEnum jobItemTypeEnumByType = JobItemTypeEnum.getJobItemTypeEnumByType(typeEnum.getType());
                    if (ObjectUtils.isEmpty(jobItemTypeEnumByType)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }

        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andSubprojectIdEqualTo(subprojectId).andDeletedEqualTo(0);
        if (!ObjectUtils.isEmpty(jobItemStatus)) {
            criteria1.andStatusEqualTo(jobItemStatus);
        }

        List<String> statusList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jobitemTypeList)) {
            for (JobItemTypeEnum typeEnum : jobitemTypeList) {
                statusList.add(typeEnum.getType());
            }
            if (!CollectionUtils.isEmpty(statusList)) {
                criteria1.andTypeIn(statusList);
            }
        }

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        return getRdmsJobItemDtos(rdmsJobItemsDtos);
    }

    /**
     * @param projectId
     */
    @Transactional
    public List<RdmsJobItemDto> getJobitemListByProjectIdPjmIdAndStatus(String projectId, String pjmId, List<JobItemStatusEnum> statusList, ProjectTypeEnum projectType) {
        if (!CollectionUtils.isEmpty(statusList)) {
            for (JobItemStatusEnum statusEnum : statusList) {
                if (!ObjectUtils.isEmpty(statusEnum.getStatus())) {
                    JobItemStatusEnum jobItemStatusEnumByStatus = JobItemStatusEnum.getJobItemStatusEnumByStatus(statusEnum.getStatus());
                    if (ObjectUtils.isEmpty(jobItemStatusEnumByStatus)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }

        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andProjectIdEqualTo(projectId).andProjectManagerIdEqualTo(pjmId).andDeletedEqualTo(0);

        List<String> statusEnumList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(statusList)) {
            for (JobItemStatusEnum statusEnum : statusList) {
                statusEnumList.add(statusEnum.getStatus());
            }
        }
        criteria1.andStatusIn(statusEnumList);

        if (!ObjectUtils.isEmpty(projectType)) {
            ProjectTypeEnum projectTypeEnumByType = ProjectTypeEnum.getProjectTypeEnumByType(projectType.getType());
            if (!ObjectUtils.isEmpty(projectTypeEnumByType)) {
                criteria1.andProjectTypeEqualTo(projectType.getType());
            }
        }
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        return getRdmsJobItemDtos(rdmsJobItemsDtos);
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemListByProjectIdPdmIdAndStatus(String projectId, String pdmId, List<JobItemStatusEnum> statusList, ProjectTypeEnum projectType) {
        if (!CollectionUtils.isEmpty(statusList)) {
            for (JobItemStatusEnum statusEnum : statusList) {
                if (!ObjectUtils.isEmpty(statusEnum.getStatus())) {
                    JobItemStatusEnum jobItemStatusEnumByStatus = JobItemStatusEnum.getJobItemStatusEnumByStatus(statusEnum.getStatus());
                    if (ObjectUtils.isEmpty(jobItemStatusEnumByStatus)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }

        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andProjectIdEqualTo(projectId)
                .andProductManagerIdEqualTo(pdmId)
                .andDeletedEqualTo(0);

        List<String> statusEnumList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(statusList)) {
            for (JobItemStatusEnum statusEnum : statusList) {
                statusEnumList.add(statusEnum.getStatus());
            }
        }
        criteria1.andStatusIn(statusEnumList);

        if (!ObjectUtils.isEmpty(projectType)) {
            ProjectTypeEnum projectTypeEnumByType = ProjectTypeEnum.getProjectTypeEnumByType(projectType.getType());
            if (!ObjectUtils.isEmpty(projectTypeEnumByType)) {
                criteria1.andProjectTypeEqualTo(projectType.getType());
            }
        }
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        return getRdmsJobItemDtos(rdmsJobItemsDtos);
    }

    /**
     * @param subprojectId
     */
    @Transactional
    public List<RdmsJobItemDto> getJobitemListBySubProjectIdPjmIdAndStatus(String subprojectId, String pjmId, List<JobItemStatusEnum> statusList, ProjectTypeEnum projectType) {
        if (!CollectionUtils.isEmpty(statusList)) {
            for (JobItemStatusEnum statusEnum : statusList) {
                if (!ObjectUtils.isEmpty(statusEnum.getStatus())) {
                    JobItemStatusEnum jobItemStatusEnumByStatus = JobItemStatusEnum.getJobItemStatusEnumByStatus(statusEnum.getStatus());
                    if (ObjectUtils.isEmpty(jobItemStatusEnumByStatus)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }

        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andSubprojectIdEqualTo(subprojectId).andProjectManagerIdEqualTo(pjmId).andDeletedEqualTo(0);

        List<String> statusEnumList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(statusList)) {
            for (JobItemStatusEnum statusEnum : statusList) {
                statusEnumList.add(statusEnum.getStatus());
            }
        }
        criteria1.andStatusIn(statusEnumList);

        if (!ObjectUtils.isEmpty(projectType)) {
            ProjectTypeEnum projectTypeEnumByType = ProjectTypeEnum.getProjectTypeEnumByType(projectType.getType());
            if (!ObjectUtils.isEmpty(projectTypeEnumByType)) {
                criteria1.andProjectTypeEqualTo(projectType.getType());
            }
        }
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        return getRdmsJobItemDtos(rdmsJobItemsDtos);
    }

    /**
     * Task 工单 和功能开发工单的不同是 task工单的characterId为null
     *
     * @param subprojectId
     */
    @Transactional
    public List<RdmsJobItemDto> getTaskJobitemListBySubProjectIdPjmIdAndStatus(String subprojectId, String pjmId, List<JobItemStatusEnum> statusList, ProjectTypeEnum projectType) {
        if (!CollectionUtils.isEmpty(statusList)) {
            for (JobItemStatusEnum statusEnum : statusList) {
                if (!ObjectUtils.isEmpty(statusEnum.getStatus())) {
                    JobItemStatusEnum jobItemStatusEnumByStatus = JobItemStatusEnum.getJobItemStatusEnumByStatus(statusEnum.getStatus());
                    if (ObjectUtils.isEmpty(jobItemStatusEnumByStatus)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }

        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        List<String> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.REVIEW_PRO.getType());
        typeList.add(JobItemTypeEnum.REVIEW_SUBP.getType());
        typeList.add(JobItemTypeEnum.REVIEW_MILESTONE.getType());
        typeList.add(JobItemTypeEnum.REVIEW_COO.getType());
        typeList.add(JobItemTypeEnum.ASSIST.getType());
        typeList.add(JobItemTypeEnum.TEST.getType());
        typeList.add(JobItemTypeEnum.TASK_MILESTONE.getType());

        criteria1.andSubprojectIdEqualTo(subprojectId)
                .andProjectManagerIdEqualTo(pjmId)
                .andTypeNotIn(typeList)  //在项目管理中不计算评审工单
                .andReviewResultIsNull()
                .andDeletedEqualTo(0);

        List<String> statusEnumList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(statusList)) {
            for (JobItemStatusEnum statusEnum : statusList) {
                statusEnumList.add(statusEnum.getStatus());
            }
        }
        criteria1.andStatusIn(statusEnumList);
        criteria1.andCharacterIdIsNull();  //task 工单的特征

        if (!ObjectUtils.isEmpty(projectType)) {
            ProjectTypeEnum projectTypeEnumByType = ProjectTypeEnum.getProjectTypeEnumByType(projectType.getType());
            if (!ObjectUtils.isEmpty(projectTypeEnumByType)) {
                criteria1.andProjectTypeEqualTo(projectType.getType());
            }
        }
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        return getRdmsJobItemDtos(rdmsJobItemsDtos);
    }

    /**
     * @param customerId
     */
    @Transactional
    public List<RdmsJobItemDto> getJobitemListByCustomerIdPjmIdAndStatus(String customerId, String pjmId, List<JobItemStatusEnum> statusList, ProjectTypeEnum projectType) {
        if (!CollectionUtils.isEmpty(statusList)) {
            for (JobItemStatusEnum statusEnum : statusList) {
                if (!ObjectUtils.isEmpty(statusEnum.getStatus())) {
                    JobItemStatusEnum jobItemStatusEnumByStatus = JobItemStatusEnum.getJobItemStatusEnumByStatus(statusEnum.getStatus());
                    if (ObjectUtils.isEmpty(jobItemStatusEnumByStatus)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }

        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andCustomerIdEqualTo(customerId).andProjectManagerIdEqualTo(pjmId).andDeletedEqualTo(0);

        List<String> statusEnumList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(statusList)) {
            for (JobItemStatusEnum statusEnum : statusList) {
                statusEnumList.add(statusEnum.getStatus());
            }
        }
        criteria1.andStatusIn(statusEnumList);

        if (!ObjectUtils.isEmpty(projectType)) {
            ProjectTypeEnum projectTypeEnumByType = ProjectTypeEnum.getProjectTypeEnumByType(projectType.getType());
            if (!ObjectUtils.isEmpty(projectTypeEnumByType)) {
                criteria1.andProjectTypeEqualTo(projectType.getType());
            }
        }
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        return getRdmsJobItemDtos(rdmsJobItemsDtos);
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemListByCustomerIdAndJobTypeAndStatusList(String customerId, List<JobItemTypeEnum> jobTypeList, List<JobItemStatusEnum> statusList, ProjectTypeEnum projectType) {
        if (!CollectionUtils.isEmpty(statusList)) {
            for (JobItemStatusEnum statusEnum : statusList) {
                if (!ObjectUtils.isEmpty(statusEnum.getStatus())) {
                    JobItemStatusEnum jobItemStatusEnumByStatus = JobItemStatusEnum.getJobItemStatusEnumByStatus(statusEnum.getStatus());
                    if (ObjectUtils.isEmpty(jobItemStatusEnumByStatus)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }

        if (!CollectionUtils.isEmpty(jobTypeList)) {
            for (JobItemTypeEnum typeEnum : jobTypeList) {
                if (!ObjectUtils.isEmpty(typeEnum.getType())) {
                    JobItemTypeEnum jobItemTypeEnumByType = JobItemTypeEnum.getJobItemTypeEnumByType(typeEnum.getType());
                    if (ObjectUtils.isEmpty(jobItemTypeEnumByType)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }

        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andCustomerIdEqualTo(customerId)
                .andDeletedEqualTo(0);

        List<String> statusEnumList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(statusList)) {
            for (JobItemStatusEnum statusEnum : statusList) {
                statusEnumList.add(statusEnum.getStatus());
            }
        }
        criteria1.andStatusIn(statusEnumList);

        List<String> jobTypEnumList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jobTypeList)) {
            for (JobItemTypeEnum typeEnum : jobTypeList) {
                jobTypEnumList.add(typeEnum.getType());
            }
        }
        criteria1.andTypeIn(jobTypEnumList);

        if (!ObjectUtils.isEmpty(projectType)) {
            ProjectTypeEnum projectTypeEnumByType = ProjectTypeEnum.getProjectTypeEnumByType(projectType.getType());
            if (!ObjectUtils.isEmpty(projectTypeEnumByType)) {
                criteria1.andProjectTypeEqualTo(projectType.getType());
            }
        }

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        return getRdmsJobItemDtos(rdmsJobItemsDtos);
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemListByUserIdAndJobTypeAndStatusList(String customerId, String userId,  List<JobItemTypeEnum> jobTypeList, List<JobItemStatusEnum> statusList, ProjectTypeEnum projectType) {
        if (!CollectionUtils.isEmpty(statusList)) {
            for (JobItemStatusEnum statusEnum : statusList) {
                if (!ObjectUtils.isEmpty(statusEnum.getStatus())) {
                    JobItemStatusEnum jobItemStatusEnumByStatus = JobItemStatusEnum.getJobItemStatusEnumByStatus(statusEnum.getStatus());
                    if (ObjectUtils.isEmpty(jobItemStatusEnumByStatus)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }

        if (!CollectionUtils.isEmpty(jobTypeList)) {
            for (JobItemTypeEnum typeEnum : jobTypeList) {
                if (!ObjectUtils.isEmpty(typeEnum.getType())) {
                    JobItemTypeEnum jobItemTypeEnumByType = JobItemTypeEnum.getJobItemTypeEnumByType(typeEnum.getType());
                    if (ObjectUtils.isEmpty(jobItemTypeEnumByType)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }

        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andCustomerIdEqualTo(customerId)
                .andNextNodeEqualTo(userId)
                .andDeletedEqualTo(0);

        List<String> statusEnumList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(statusList)) {
            for (JobItemStatusEnum statusEnum : statusList) {
                statusEnumList.add(statusEnum.getStatus());
            }
        }
        criteria1.andStatusIn(statusEnumList);

        List<String> jobTypEnumList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jobTypeList)) {
            for (JobItemTypeEnum typeEnum : jobTypeList) {
                jobTypEnumList.add(typeEnum.getType());
            }
        }
        criteria1.andTypeIn(jobTypEnumList);

        if (!ObjectUtils.isEmpty(projectType)) {
            ProjectTypeEnum projectTypeEnumByType = ProjectTypeEnum.getProjectTypeEnumByType(projectType.getType());
            if (!ObjectUtils.isEmpty(projectTypeEnumByType)) {
                criteria1.andProjectTypeEqualTo(projectType.getType());
            }
        }

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        return getRdmsJobItemDtos(rdmsJobItemsDtos);
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemListByCustomerIdPdmIdAndStatus(String customerId, String pdmId, List<JobItemStatusEnum> statusList, ProjectTypeEnum projectType) {
        if (!CollectionUtils.isEmpty(statusList)) {
            for (JobItemStatusEnum statusEnum : statusList) {
                if (!ObjectUtils.isEmpty(statusEnum.getStatus())) {
                    JobItemStatusEnum jobItemStatusEnumByStatus = JobItemStatusEnum.getJobItemStatusEnumByStatus(statusEnum.getStatus());
                    if (ObjectUtils.isEmpty(jobItemStatusEnumByStatus)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }

        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andCustomerIdEqualTo(customerId)
                .andProductManagerIdEqualTo(pdmId)
                .andDeletedEqualTo(0);

        List<String> statusEnumList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(statusList)) {
            for (JobItemStatusEnum statusEnum : statusList) {
                statusEnumList.add(statusEnum.getStatus());
            }
        }
        criteria1.andStatusIn(statusEnumList);

        if (!ObjectUtils.isEmpty(projectType)) {
            ProjectTypeEnum projectTypeEnumByType = ProjectTypeEnum.getProjectTypeEnumByType(projectType.getType());
            if (!ObjectUtils.isEmpty(projectTypeEnumByType)) {
                criteria1.andProjectTypeEqualTo(projectType.getType());
            }
        }
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        return getRdmsJobItemDtos(rdmsJobItemsDtos);
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemListByCustomerIdExecutorIdAndNotInStatus(String customerId, String executorId, List<JobItemStatusEnum> statusList) {
        if (!CollectionUtils.isEmpty(statusList)) {
            for (JobItemStatusEnum statusEnum : statusList) {
                if (!ObjectUtils.isEmpty(statusEnum.getStatus())) {
                    JobItemStatusEnum jobItemStatusEnumByStatus = JobItemStatusEnum.getJobItemStatusEnumByStatus(statusEnum.getStatus());
                    if (ObjectUtils.isEmpty(jobItemStatusEnumByStatus)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }

        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andCustomerIdEqualTo(customerId)
                .andExecutorIdEqualTo(executorId)
                .andDeletedEqualTo(0);

        List<String> statusEnumList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(statusList)) {
            for (JobItemStatusEnum statusEnum : statusList) {
                statusEnumList.add(statusEnum.getStatus());
            }
        }
        criteria1.andStatusNotIn(statusEnumList);  //排除状态

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        return getRdmsJobItemDtos(rdmsJobItemsDtos);
    }

    @Transactional
    public List<RdmsJobItemDto> getTaskJobitemListByCustomerIdPjmIdAndStatus(String customerId, String pjmId, List<JobItemStatusEnum> statusList, ProjectTypeEnum projectType) {
        if (!CollectionUtils.isEmpty(statusList)) {
            for (JobItemStatusEnum statusEnum : statusList) {
                if (!ObjectUtils.isEmpty(statusEnum.getStatus())) {
                    JobItemStatusEnum jobItemStatusEnumByStatus = JobItemStatusEnum.getJobItemStatusEnumByStatus(statusEnum.getStatus());
                    if (ObjectUtils.isEmpty(jobItemStatusEnumByStatus)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }

        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        List<String> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.REVIEW_PRO.getType());
        typeList.add(JobItemTypeEnum.REVIEW_SUBP.getType());
        typeList.add(JobItemTypeEnum.REVIEW_MILESTONE.getType());
        typeList.add(JobItemTypeEnum.REVIEW_COO.getType());
        typeList.add(JobItemTypeEnum.ASSIST.getType());

  /*      List<String> resultStatus = new ArrayList<>();
        resultStatus.add(ReviewResultEnum.REJECT.getStatus());
        resultStatus.add(ReviewResultEnum.CONDITIONAL.getStatus());
        resultStatus.add(ReviewResultEnum.SUPPLEMENT.getStatus());
        resultStatus.add(ReviewResultEnum.PASS.getStatus());*/

        criteria1.andCustomerIdEqualTo(customerId)
                .andProjectManagerIdEqualTo(pjmId)
                .andTypeNotIn(typeList)
                .andReviewResultIsNull()
                .andDeletedEqualTo(0);

        List<String> statusEnumList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(statusList)) {
            for (JobItemStatusEnum statusEnum : statusList) {
                statusEnumList.add(statusEnum.getStatus());
            }
        }
        criteria1.andStatusIn(statusEnumList);
        criteria1.andCharacterIdIsNull();  //task 特征

        if (!ObjectUtils.isEmpty(projectType)) {
            ProjectTypeEnum projectTypeEnumByType = ProjectTypeEnum.getProjectTypeEnumByType(projectType.getType());
            if (!ObjectUtils.isEmpty(projectTypeEnumByType)) {
                criteria1.andProjectTypeEqualTo(projectType.getType());
            }
        }
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        return getRdmsJobItemDtos(rdmsJobItemsDtos);
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemListBySubprojectIdAndStatusListTypeList(String subprojectId, List<JobItemStatusEnum> jobItemStatusList, List<JobItemTypeEnum> jobitemTypeList) {
        if (!CollectionUtils.isEmpty(jobItemStatusList)) {
            for (JobItemStatusEnum statusEnum : jobItemStatusList) {
                if (!ObjectUtils.isEmpty(statusEnum.getStatus())) {
                    JobItemStatusEnum jobItemStatusEnumByStatus = JobItemStatusEnum.getJobItemStatusEnumByStatus(statusEnum.getStatus());
                    if (ObjectUtils.isEmpty(jobItemStatusEnumByStatus)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }
        if (!CollectionUtils.isEmpty(jobitemTypeList)) {
            for (JobItemTypeEnum typeEnum : jobitemTypeList) {
                if (!ObjectUtils.isEmpty(typeEnum.getType())) {
                    JobItemTypeEnum jobItemTypeEnumByType = JobItemTypeEnum.getJobItemTypeEnumByType(typeEnum.getType());
                    if (ObjectUtils.isEmpty(jobItemTypeEnumByType)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }


        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andSubprojectIdEqualTo(subprojectId).andDeletedEqualTo(0);

        List<String> statusList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jobItemStatusList)) {
            for (JobItemStatusEnum statusEnum : jobItemStatusList) {
                statusList.add(statusEnum.getStatus());
            }
            if (!CollectionUtils.isEmpty(statusList)) {
                criteria1.andStatusIn(statusList);
            }
        }

        List<String> typeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jobitemTypeList)) {
            for (JobItemTypeEnum typeEnum : jobitemTypeList) {
                typeList.add(typeEnum.getType());
            }
            if (!CollectionUtils.isEmpty(typeList)) {
                criteria1.andTypeIn(typeList);
            }
        }

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        return getRdmsJobItemDtos(rdmsJobItemsDtos);
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemListByPreProjectIdAndStatusListTypeList(String preProjectId, List<JobItemStatusEnum> jobItemStatusList, List<JobItemTypeEnum> jobitemTypeList) {
        if (!CollectionUtils.isEmpty(jobItemStatusList)) {
            for (JobItemStatusEnum statusEnum : jobItemStatusList) {
                if (!ObjectUtils.isEmpty(statusEnum.getStatus())) {
                    JobItemStatusEnum jobItemStatusEnumByStatus = JobItemStatusEnum.getJobItemStatusEnumByStatus(statusEnum.getStatus());
                    if (ObjectUtils.isEmpty(jobItemStatusEnumByStatus)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }
        if (!CollectionUtils.isEmpty(jobitemTypeList)) {
            for (JobItemTypeEnum typeEnum : jobitemTypeList) {
                if (!ObjectUtils.isEmpty(typeEnum.getType())) {
                    JobItemTypeEnum jobItemTypeEnumByType = JobItemTypeEnum.getJobItemTypeEnumByType(typeEnum.getType());
                    if (ObjectUtils.isEmpty(jobItemTypeEnumByType)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }


        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1
                .andPreProjectIdEqualTo(preProjectId)
                .andProjectTypeEqualTo(ProjectTypeEnum.PRE_PROJECT.getType())
                .andDeletedEqualTo(0);

        List<String> statusList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jobItemStatusList)) {
            for (JobItemStatusEnum statusEnum : jobItemStatusList) {
                statusList.add(statusEnum.getStatus());
            }
            if (!CollectionUtils.isEmpty(statusList)) {
                criteria1.andStatusIn(statusList);
            }
        }

        List<String> typeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jobitemTypeList)) {
            for (JobItemTypeEnum typeEnum : jobitemTypeList) {
                typeList.add(typeEnum.getType());
            }
            if (!CollectionUtils.isEmpty(typeList)) {
                criteria1.andTypeIn(typeList);
            }
        }

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        return getRdmsJobItemDtos(rdmsJobItemsDtos);
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemListBySubprojectIdList(List<String> subprojectIdList) {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andSubprojectIdIn(subprojectIdList)
                .andDeletedEqualTo(0);
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
        return rdmsJobItemsDtos;
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemListByProjectIdAndStatusListTypeList(String projectId, List<JobItemStatusEnum> jobItemStatusList, List<JobItemTypeEnum> jobitemTypeList) {
        if (!CollectionUtils.isEmpty(jobItemStatusList)) {
            for (JobItemStatusEnum statusEnum : jobItemStatusList) {
                if (!ObjectUtils.isEmpty(statusEnum.getStatus())) {
                    JobItemStatusEnum jobItemStatusEnumByStatus = JobItemStatusEnum.getJobItemStatusEnumByStatus(statusEnum.getStatus());
                    if (ObjectUtils.isEmpty(jobItemStatusEnumByStatus)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }
        if (!CollectionUtils.isEmpty(jobitemTypeList)) {
            for (JobItemTypeEnum typeEnum : jobitemTypeList) {
                if (!ObjectUtils.isEmpty(typeEnum.getType())) {
                    JobItemTypeEnum jobItemTypeEnumByType = JobItemTypeEnum.getJobItemTypeEnumByType(typeEnum.getType());
                    if (ObjectUtils.isEmpty(jobItemTypeEnumByType)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }


        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andProjectIdEqualTo(projectId).andDeletedEqualTo(0);

        List<String> statusList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jobItemStatusList)) {
            for (JobItemStatusEnum statusEnum : jobItemStatusList) {
                statusList.add(statusEnum.getStatus());
            }
            if (!CollectionUtils.isEmpty(statusList)) {
                criteria1.andStatusIn(statusList);
            }
        }

        List<String> typeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jobitemTypeList)) {
            for (JobItemTypeEnum typeEnum : jobitemTypeList) {
                typeList.add(typeEnum.getType());
            }
            if (!CollectionUtils.isEmpty(typeList)) {
                criteria1.andTypeIn(typeList);
            }
        }

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        return getRdmsJobItemDtos(rdmsJobItemsDtos);
    }

    /**
     * @param jobitemId
     */
    @Transactional
    public List<RdmsJobItemDto> getReviewJobitemListByResultEnum(String jobitemId, ReviewResultEnum reviewResultEnum) {
        if (!ObjectUtils.isEmpty(reviewResultEnum)) {
            ReviewResultEnum reviewResultEnumByStatus = ReviewResultEnum.getReviewResultEnumByStatus(reviewResultEnum.getStatus());
            if (ObjectUtils.isEmpty(reviewResultEnumByStatus)) {
                throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
            }
        }

        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andIdEqualTo(jobitemId).andDeletedEqualTo(0).andReviewResultEqualTo(reviewResultEnum.getStatus());

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        return getRdmsJobItemDtos(rdmsJobItemsDtos);
    }

    /**
     * @param parentJobitemId
     */
    @Transactional
    public List<RdmsJobItemDto> getReviewJobitemListByParentJobitemIdAndResultEnum(String parentJobitemId, ReviewResultEnum reviewResultEnum) {
        if (!ObjectUtils.isEmpty(reviewResultEnum)) {
            ReviewResultEnum reviewResultEnumByStatus = ReviewResultEnum.getReviewResultEnumByStatus(reviewResultEnum.getStatus());
            if (ObjectUtils.isEmpty(reviewResultEnumByStatus)) {
                throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
            }
        }

        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andParentJobitemIdEqualTo(parentJobitemId).andDeletedEqualTo(0).andReviewResultEqualTo(reviewResultEnum.getStatus());

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        return getRdmsJobItemDtos(rdmsJobItemsDtos);
    }

    /**
     * @param parentJobitemId
     */
    @Transactional
    public List<RdmsJobItemDto> getReviewRelatedJobListByStatusAndResultEnumList(String parentJobitemId, JobItemStatusEnum statusEnum, List<ReviewResultEnum> reviewResultEnumList) {

        List<String> resultEnumList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(reviewResultEnumList)) {
            for (ReviewResultEnum reviewResultEnum : reviewResultEnumList) {
                resultEnumList.add(reviewResultEnum.getStatus());
            }
        }
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andParentJobitemIdEqualTo(parentJobitemId)
                .andDeletedEqualTo(0);

        if (!ObjectUtils.isEmpty(statusEnum)) {
            criteria1.andStatusEqualTo(statusEnum.getStatus());
        }
        if (!CollectionUtils.isEmpty(resultEnumList)) {
            criteria1.andReviewResultIn(resultEnumList);
        }

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtoList = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
        List<RdmsJobItemDto> rdmsJobItemsDtos = new ArrayList<>(rdmsJobItemsDtoList);

        return rdmsJobItemsDtos;
    }

    /**
     *
     */
    @Transactional
    public RdmsHmiJobItemAndChildrenItemsDto getReviewJobitemAndRelatedJobitems(String jobitemId, JobItemStatusEnum statusEnum, List<ReviewResultEnum> resultEnumList) {
        RdmsHmiJobItemAndChildrenItemsDto jobItemAndChildrenItemsDto = new RdmsHmiJobItemAndChildrenItemsDto();
        RdmsJobItem jobItem = this.selectByPrimaryKey(jobitemId);
        if (!ObjectUtils.isEmpty(jobItem)) {
            RdmsJobItemDto jobItemDto = CopyUtil.copy(jobItem, RdmsJobItemDto.class);
            jobItemDto.setParentOrChildren(ParentOrChildrenEnum.PARENT.getKey());
            jobItemAndChildrenItemsDto.setParentJobitem(jobItemDto);
        }

        //获取Review_coo
        if (!ObjectUtils.isEmpty(jobItem) && statusEnum.equals(JobItemStatusEnum.HANDLING)) {
            List<RdmsJobItemDto> cooJobItemDtos = this.getJobitemListByTestedJobitemId(jobItem.getId(), JobItemStatusEnum.HANDLING.getStatus(), JobItemTypeEnum.REVIEW_COO.getType());
            for (RdmsJobItemDto jobitem : cooJobItemDtos) {
                jobitem.setParentOrChildren(ParentOrChildrenEnum.CHILDREN.getKey());
            }
            jobItemAndChildrenItemsDto.setChildJobitemList(cooJobItemDtos);
        }

        //获取相关task jobitem
        if (!ObjectUtils.isEmpty(jobItem)) {
            List<RdmsJobItemDto> reviewRelatedJobList = this.getReviewRelatedJobListByStatusAndResultEnumList(jobitemId, statusEnum, resultEnumList);
            for (RdmsJobItemDto jobitem : reviewRelatedJobList) {
                jobitem.setParentOrChildren(ParentOrChildrenEnum.CHILDREN.getKey());
            }
            if (!CollectionUtils.isEmpty(jobItemAndChildrenItemsDto.getChildJobitemList())) {
                jobItemAndChildrenItemsDto.getChildJobitemList().addAll(reviewRelatedJobList);
            } else {
                jobItemAndChildrenItemsDto.setChildJobitemList(reviewRelatedJobList);
            }
        }

        return jobItemAndChildrenItemsDto;
    }

    @Transactional
    public List<RdmsJobItemDto> getReviewJobitemRelatedSubmitJobitems(String jobitemId, List<ReviewResultEnum> resultEnumList) {
        RdmsJobItemExample jobItemExample = new RdmsJobItemExample();
        RdmsJobItemExample.Criteria criteria = jobItemExample.createCriteria().andParentJobitemIdEqualTo(jobitemId).andDeletedEqualTo(0);
        if (!CollectionUtils.isEmpty(resultEnumList)) {
            List<String> resultList = new ArrayList<>();
            for (ReviewResultEnum resultEnum : resultEnumList) {
                resultList.add(resultEnum.getStatus());
            }
            criteria.andReviewResultIn(resultList);
        }
        criteria.andStatusEqualTo(JobItemStatusEnum.SUBMIT.getStatus());
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(jobItemExample);

        return CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
    }

    @Transactional
    public List<RdmsJobItemDto> getReviewJobitemRelatedSubmitJobitemsByNextNode(String nextNode, List<ReviewResultEnum> resultEnumList) {
        RdmsJobItemExample jobItemExample = new RdmsJobItemExample();
        RdmsJobItemExample.Criteria criteria = jobItemExample.createCriteria().andNextNodeEqualTo(nextNode).andDeletedEqualTo(0);
        if (!CollectionUtils.isEmpty(resultEnumList)) {
            List<String> resultList = new ArrayList<>();
            for (ReviewResultEnum resultEnum : resultEnumList) {
                resultList.add(resultEnum.getStatus());
            }
            criteria.andReviewResultIn(resultList);
        }
        criteria.andStatusEqualTo(JobItemStatusEnum.SUBMIT.getStatus());
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(jobItemExample);

        return CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemListByProjectIdAndStatus(String projectId, String jobItemStatus, List<JobItemTypeEnum> jobitemTypeList) {
        if (!ObjectUtils.isEmpty(jobItemStatus)) {
            JobItemStatusEnum jobItemStatusEnumByStatus = JobItemStatusEnum.getJobItemStatusEnumByStatus(jobItemStatus);
            if (ObjectUtils.isEmpty(jobItemStatusEnumByStatus)) {
                throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
            }
        }
        if (!CollectionUtils.isEmpty(jobitemTypeList)) {
            for (JobItemTypeEnum typeEnum : jobitemTypeList) {
                if (!ObjectUtils.isEmpty(typeEnum.getType())) {
                    JobItemTypeEnum jobItemTypeEnumByType = JobItemTypeEnum.getJobItemTypeEnumByType(typeEnum.getType());
                    if (ObjectUtils.isEmpty(jobItemTypeEnumByType)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }

        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andProjectIdEqualTo(projectId).andDeletedEqualTo(0);
        if (!ObjectUtils.isEmpty(jobItemStatus)) {
            criteria1.andStatusEqualTo(jobItemStatus);
        }

        List<String> typeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jobitemTypeList)) {
            for (JobItemTypeEnum typeEnum : jobitemTypeList) {
                typeList.add(typeEnum.getType());
            }
            if (!CollectionUtils.isEmpty(typeList)) {
                criteria1.andTypeIn(typeList);
            }
        }

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        return getRdmsJobItemDtos(rdmsJobItemsDtos);
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemListBySubProjectIdAndStatus(String subprojectId, String jobItemStatus, List<JobItemTypeEnum> jobitemTypeList) {
        if (!ObjectUtils.isEmpty(jobItemStatus)) {
            JobItemStatusEnum jobItemStatusEnumByStatus = JobItemStatusEnum.getJobItemStatusEnumByStatus(jobItemStatus);
            if (ObjectUtils.isEmpty(jobItemStatusEnumByStatus)) {
                throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
            }
        }
        if (!CollectionUtils.isEmpty(jobitemTypeList)) {
            for (JobItemTypeEnum typeEnum : jobitemTypeList) {
                if (!ObjectUtils.isEmpty(typeEnum.getType())) {
                    JobItemTypeEnum jobItemTypeEnumByType = JobItemTypeEnum.getJobItemTypeEnumByType(typeEnum.getType());
                    if (ObjectUtils.isEmpty(jobItemTypeEnumByType)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }

        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andSubprojectIdEqualTo(subprojectId).andDeletedEqualTo(0);
        if (!ObjectUtils.isEmpty(jobItemStatus)) {
            criteria1.andStatusEqualTo(jobItemStatus);
        }

        List<String> typeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jobitemTypeList)) {
            for (JobItemTypeEnum typeEnum : jobitemTypeList) {
                typeList.add(typeEnum.getType());
            }
            if (!CollectionUtils.isEmpty(typeList)) {
                criteria1.andTypeIn(typeList);
            }
        }

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        return getRdmsJobItemDtos(rdmsJobItemsDtos);
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemListByPreProjectIdAndStatusAndTypeList(String preprojectId, String jobItemStatus, List<JobItemTypeEnum> jobitemTypeList) {
        if (!ObjectUtils.isEmpty(jobItemStatus)) {
            JobItemStatusEnum jobItemStatusEnumByStatus = JobItemStatusEnum.getJobItemStatusEnumByStatus(jobItemStatus);
            if (ObjectUtils.isEmpty(jobItemStatusEnumByStatus)) {
                throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
            }
        }
        if (!CollectionUtils.isEmpty(jobitemTypeList)) {
            for (JobItemTypeEnum typeEnum : jobitemTypeList) {
                if (!ObjectUtils.isEmpty(typeEnum.getType())) {
                    JobItemTypeEnum jobItemTypeEnumByType = JobItemTypeEnum.getJobItemTypeEnumByType(typeEnum.getType());
                    if (ObjectUtils.isEmpty(jobItemTypeEnumByType)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }

        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andPreProjectIdEqualTo(preprojectId).andDeletedEqualTo(0);
        if (!ObjectUtils.isEmpty(jobItemStatus)) {
            criteria1.andStatusEqualTo(jobItemStatus);
        }

        List<String> typeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jobitemTypeList)) {
            for (JobItemTypeEnum typeEnum : jobitemTypeList) {
                typeList.add(typeEnum.getType());
            }
            if (!CollectionUtils.isEmpty(typeList)) {
                criteria1.andTypeIn(typeList);
            }
        }

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        return getRdmsJobItemDtos(rdmsJobItemsDtos);
    }

    private List<RdmsJobItemDto> getRdmsJobItemDtos(List<RdmsJobItemDto> rdmsJobItemsDtos) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (RdmsJobItemDto jobItemsDto : rdmsJobItemsDtos) {
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getExecutorId());

            jobItemsDto.setExecutorName(rdmsCustomerUser.getTrueName());

            jobItemsDto.setCreateTimeStr(sdf.format(jobItemsDto.getCreateTime()));

            RdmsCustomer customer = rdmsCustomerService.selectByPrimaryKey(jobItemsDto.getCustomerId());
            jobItemsDto.setCustomerName(customer.getCustomerName());

            if (!ObjectUtils.isEmpty(jobItemsDto.getProjectId())) {
                RdmsProject projectInfo = rdmsProjectService.selectByPrimaryKey(jobItemsDto.getProjectId());
                if (!ObjectUtils.isEmpty(projectInfo)) {
                    jobItemsDto.setProjectName(projectInfo.getProjectName());

                    RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(projectInfo.getProductManagerId());
                    if (!ObjectUtils.isEmpty(customerUser)) {
                        jobItemsDto.setProductManagerName(customerUser.getTrueName());
                    }
                }
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getSubprojectId())) {
                RdmsProjectSubproject subProjectInfo = rdmsSubprojectService.getSubProjectInfo(jobItemsDto.getSubprojectId());
                if (!ObjectUtils.isEmpty(subProjectInfo)) {
                    jobItemsDto.setSubprojectName(subProjectInfo.getLabel());
                }
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getCharacterId())) {
                RdmsCharacter character = rdmsCharacterService.selectByPrimaryKey(jobItemsDto.getCharacterId());
                if (!ObjectUtils.isEmpty(character)) {
                    jobItemsDto.setCharacterDto(CopyUtil.copy(character, RdmsCharacterDto.class));
                    jobItemsDto.setCharacterName(character.getCharacterName());

                    RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(character.getProductManagerId());
                    if (!ObjectUtils.isEmpty(customerUser)) {
                        jobItemsDto.setProductManagerName(customerUser.getTrueName());
                    }
                }
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getProjectManagerId())) {
                RdmsCustomerUser customerUser1 = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getProjectManagerId());
                jobItemsDto.setProjectManagerName(customerUser1.getTrueName());
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getFileListStr()) && jobItemsDto.getFileListStr().length() > 6) {
                List<String> stringList = JSON.parseArray(jobItemsDto.getFileListStr(), String.class);
                List<RdmsFileDto> fileDtos = new ArrayList<>();
                for (String id : stringList) {
                    RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(id);
                    fileDtos.add(fileDto);
                }
                jobItemsDto.setFileList(fileDtos);
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getReviewWorkerIdStr()) && jobItemsDto.getReviewWorkerIdStr().length() > 6) {
                List<String> stringList = JSON.parseArray(jobItemsDto.getReviewWorkerIdStr(), String.class);
                List<String> reviewWorkerNameList = new ArrayList<>();
                List<String> reviewWorkerIdList = new ArrayList<>();
                for (String userId : stringList) {
                    RdmsCustomerUser worker = rdmsCustomerUserService.selectByPrimaryKey(userId);
                    reviewWorkerNameList.add(worker.getTrueName());
                    reviewWorkerIdList.add(worker.getId());
                }
                jobItemsDto.setReviewWorkerNameList(reviewWorkerNameList);
                jobItemsDto.setReviewWorkerIdList(reviewWorkerIdList);
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getPropertyId())) {
                RdmsJobItemProperty rdmsJobItemProperty = rdmsJobItemPropertyService.selectByPrimaryKey(jobItemsDto.getPropertyId());
                RdmsJobItemPropertyDto propertyDto = CopyUtil.copy(rdmsJobItemProperty, RdmsJobItemPropertyDto.class);
                if (!ObjectUtils.isEmpty(propertyDto.getReferenceFileListStr())) {
                    List<String> stringList = JSON.parseArray(propertyDto.getReferenceFileListStr(), String.class);
                    if (!CollectionUtils.isEmpty(stringList)) {
                        List<RdmsFileDto> fileDtos = new ArrayList<>();
                        for (String id : stringList) {
                            RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(id);
                            fileDtos.add(fileDto);
                        }
                        propertyDto.setReferenceFileList(fileDtos);
                    }
                }
                if (!ObjectUtils.isEmpty(propertyDto.getFileListStr())) {
                    List<String> stringList = JSON.parseArray(propertyDto.getFileListStr(), String.class);
                    if (!CollectionUtils.isEmpty(stringList)) {
                        List<RdmsFileDto> fileDtos = new ArrayList<>();
                        for (String id : stringList) {
                            RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(id);
                            fileDtos.add(fileDto);
                        }
                        propertyDto.setFileItems(fileDtos);
                    }
                }
                jobItemsDto.setPropertyDto(propertyDto);
            }
        }
        return rdmsJobItemsDtos;
    }

    /**
     * @param characterId
     */
    @Transactional
    public List<RdmsJobItemDto> getJobitemListByCharacterIdAndStatusAndType(String characterId, String jobitemStatus, String jobItemType) {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andCharacterIdEqualTo(characterId)
                .andStatusEqualTo(jobitemStatus)
                .andTypeEqualTo(jobItemType)
                .andDeletedEqualTo(0);
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (RdmsJobItemDto jobItemsDto : rdmsJobItemsDtos) {
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getExecutorId());
            jobItemsDto.setExecutorName(rdmsCustomerUser.getTrueName());

            jobItemsDto.setCreateTimeStr(sdf.format(jobItemsDto.getCreateTime()));

            RdmsCustomer customer = rdmsCustomerService.selectByPrimaryKey(jobItemsDto.getCustomerId());
            jobItemsDto.setCustomerName(customer.getCustomerName());

            RdmsProject projectInfo = rdmsProjectService.selectByPrimaryKey(jobItemsDto.getProjectId());
            if (!ObjectUtils.isEmpty(projectInfo)) {
                jobItemsDto.setProjectName(projectInfo.getProjectName());
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getSubprojectId())) {
                RdmsProjectSubproject subProjectInfo = rdmsSubprojectService.getSubProjectInfo(jobItemsDto.getSubprojectId());
                jobItemsDto.setSubprojectName(subProjectInfo.getLabel());
            }

            RdmsCharacter character = rdmsCharacterService.selectByPrimaryKey(jobItemsDto.getCharacterId());
            jobItemsDto.setCharacterDto(CopyUtil.copy(character, RdmsCharacterDto.class));
            jobItemsDto.setCharacterName(character.getCharacterName());

            RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getProductManagerId());
            if (!ObjectUtils.isEmpty(customerUser)) {
                jobItemsDto.setProductManagerName(customerUser.getTrueName());
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getProjectManagerId())) {
                RdmsCustomerUser customerUser1 = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getProjectManagerId());
                jobItemsDto.setProjectManagerName(customerUser1.getTrueName());
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getFileListStr()) && jobItemsDto.getFileListStr().length() > 6) {
                List<String> stringList = JSON.parseArray(jobItemsDto.getFileListStr(), String.class);
                List<RdmsFileDto> fileDtos = new ArrayList<>();
                for (String id : stringList) {
                    RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(id);
                    fileDtos.add(fileDto);
                }
                jobItemsDto.setFileList(fileDtos);
            }
            if (!ObjectUtils.isEmpty(jobItemsDto.getPropertyId())) {
                RdmsJobItemProperty rdmsJobItemProperty = rdmsJobItemPropertyService.selectByPrimaryKey(jobItemsDto.getPropertyId());
                RdmsJobItemPropertyDto propertyDto = CopyUtil.copy(rdmsJobItemProperty, RdmsJobItemPropertyDto.class);
                if (!ObjectUtils.isEmpty(propertyDto.getReferenceFileListStr())) {
                    List<String> stringList = JSON.parseArray(propertyDto.getReferenceFileListStr(), String.class);
                    if (!CollectionUtils.isEmpty(stringList)) {
                        List<RdmsFileDto> fileDtos = new ArrayList<>();
                        for (String id : stringList) {
                            RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(id);
                            fileDtos.add(fileDto);
                        }
                        propertyDto.setReferenceFileList(fileDtos);
                    }
                }
                if (!ObjectUtils.isEmpty(propertyDto.getFileListStr())) {
                    List<String> stringList = JSON.parseArray(propertyDto.getFileListStr(), String.class);
                    if (!CollectionUtils.isEmpty(stringList)) {
                        List<RdmsFileDto> fileDtos = new ArrayList<>();
                        for (String id : stringList) {
                            RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(id);
                            fileDtos.add(fileDto);
                        }
                        propertyDto.setFileItems(fileDtos);
                    }
                }
                jobItemsDto.setPropertyDto(propertyDto);
            }
        }
        return rdmsJobItemsDtos;
    }

    /**
     *
     */
    @Transactional
    public List<RdmsJobItemDto> getJobitemListByJobitemIdAndStatus(String parentJobitemId, String jobItemStatus, String jobItemType) {
        if (!ObjectUtils.isEmpty(jobItemStatus)) {
            JobItemStatusEnum jobItemStatusEnumByStatus = JobItemStatusEnum.getJobItemStatusEnumByStatus(jobItemStatus);
            if (ObjectUtils.isEmpty(jobItemStatusEnumByStatus)) {
                throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
            }
        }
        if (!ObjectUtils.isEmpty(jobItemType)) {
            JobItemTypeEnum jobItemTypeEnumByType = JobItemTypeEnum.getJobItemTypeEnumByType(jobItemType);
            if (ObjectUtils.isEmpty(jobItemTypeEnumByType)) {
                throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
            }
        }
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andParentJobitemIdEqualTo(parentJobitemId).andDeletedEqualTo(0);
        if (!ObjectUtils.isEmpty(jobItemStatus)) {
            criteria1.andStatusEqualTo(jobItemStatus);
        }
        if (!ObjectUtils.isEmpty(jobItemType)) {
            criteria1.andTypeEqualTo(jobItemType);
        }
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (RdmsJobItemDto jobItemsDto : rdmsJobItemsDtos) {
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getExecutorId());
            jobItemsDto.setExecutorName(rdmsCustomerUser.getTrueName());

            jobItemsDto.setCreateTimeStr(sdf.format(jobItemsDto.getCreateTime()));

            RdmsCustomer customer = rdmsCustomerService.selectByPrimaryKey(jobItemsDto.getCustomerId());
            jobItemsDto.setCustomerName(customer.getCustomerName());

            RdmsProject projectInfo = rdmsProjectService.selectByPrimaryKey(jobItemsDto.getProjectId());
            if (!ObjectUtils.isEmpty(projectInfo)) {
                jobItemsDto.setProjectName(projectInfo.getProjectName());
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getSubprojectId())) {
                RdmsProjectSubproject subProjectInfo = rdmsSubprojectService.getSubProjectInfo(jobItemsDto.getSubprojectId());
                jobItemsDto.setSubprojectName(subProjectInfo.getLabel());
            }

            RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getProductManagerId());
            if (!ObjectUtils.isEmpty(customerUser)) {
                jobItemsDto.setProductManagerName(customerUser.getTrueName());
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getProjectManagerId())) {
                RdmsCustomerUser customerUser1 = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getProjectManagerId());
                jobItemsDto.setProjectManagerName(customerUser1.getTrueName());
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getFileListStr()) && jobItemsDto.getFileListStr().length() > 6) {
                List<String> stringList = JSON.parseArray(jobItemsDto.getFileListStr(), String.class);
                List<RdmsFileDto> fileDtos = new ArrayList<>();
                for (String id : stringList) {
                    RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(id);
                    fileDtos.add(fileDto);
                }
                jobItemsDto.setFileList(fileDtos);
            }
            if (!ObjectUtils.isEmpty(jobItemsDto.getPropertyId())) {
                RdmsJobItemProperty rdmsJobItemProperty = rdmsJobItemPropertyService.selectByPrimaryKey(jobItemsDto.getPropertyId());
                RdmsJobItemPropertyDto propertyDto = CopyUtil.copy(rdmsJobItemProperty, RdmsJobItemPropertyDto.class);
                if (!ObjectUtils.isEmpty(propertyDto.getReferenceFileListStr())) {
                    List<String> stringList = JSON.parseArray(propertyDto.getReferenceFileListStr(), String.class);
                    if (!CollectionUtils.isEmpty(stringList)) {
                        List<RdmsFileDto> fileDtos = new ArrayList<>();
                        for (String id : stringList) {
                            RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(id);
                            fileDtos.add(fileDto);
                        }
                        propertyDto.setReferenceFileList(fileDtos);
                    }
                }
                if (!ObjectUtils.isEmpty(propertyDto.getFileListStr())) {
                    List<String> stringList = JSON.parseArray(propertyDto.getFileListStr(), String.class);
                    if (!CollectionUtils.isEmpty(stringList)) {
                        List<RdmsFileDto> fileDtos = new ArrayList<>();
                        for (String id : stringList) {
                            RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(id);
                            fileDtos.add(fileDto);
                        }
                        propertyDto.setFileItems(fileDtos);
                    }
                }
                jobItemsDto.setPropertyDto(propertyDto);
            }
        }
        return rdmsJobItemsDtos;
    }

    /**
     *
     */
    @Transactional
    public RdmsHmiJobItemAndChildrenItemsDto getJobitemAndAllAssistJobitems(String jobitemId) {
        RdmsHmiJobItemAndChildrenItemsDto jobItemAndAssistItemsDto = new RdmsHmiJobItemAndChildrenItemsDto();
        RdmsJobItem jobItem = this.selectByPrimaryKey(jobitemId);
        if (!ObjectUtils.isEmpty(jobItem)) {
            RdmsJobItemDto jobItemDto = CopyUtil.copy(jobItem, RdmsJobItemDto.class);
            jobItemDto.setParentOrChildren(ParentOrChildrenEnum.PARENT.getKey());
            jobItemAndAssistItemsDto.setParentJobitem(jobItemDto);
        }

        List<RdmsJobItemDto> assistJobItemDtos = new ArrayList<>();
        if (!ObjectUtils.isEmpty(jobItem)) {
            if (jobItem.getType().equals(JobItemTypeEnum.TEST.getType())) {
                assistJobItemDtos = this.getJobitemListByParentJobitemId(jobItem.getId(), null, JobItemTypeEnum.BUG.getType());
                for (RdmsJobItemDto jobitem : assistJobItemDtos) {
                    jobitem.setParentOrChildren(ParentOrChildrenEnum.CHILDREN.getKey());
                }
                jobItemAndAssistItemsDto.setChildJobitemList(assistJobItemDtos);
            } else {
                assistJobItemDtos = this.getJobitemListByParentJobitemId(jobItem.getId(), null, JobItemTypeEnum.ASSIST.getType());
                for (RdmsJobItemDto jobitem : assistJobItemDtos) {
                    jobitem.setParentOrChildren(ParentOrChildrenEnum.CHILDREN.getKey());
                }
                jobItemAndAssistItemsDto.setChildJobitemList(assistJobItemDtos);
            }
        }
        return jobItemAndAssistItemsDto;
    }

    /**
     *
     */
    @Transactional
    public RdmsHmiJobItemAndChildrenItemsDto getJobitemAndAllAssistJobitems_submit(String jobitemId) {
        RdmsHmiJobItemAndChildrenItemsDto jobItemAndAssistItemsDto = new RdmsHmiJobItemAndChildrenItemsDto();
        RdmsJobItem jobItem = this.selectByPrimaryKey(jobitemId);
        if (!ObjectUtils.isEmpty(jobItem)) {
            RdmsJobItemDto jobItemDto = CopyUtil.copy(jobItem, RdmsJobItemDto.class);
            jobItemDto.setParentOrChildren(ParentOrChildrenEnum.PARENT.getKey());
            jobItemAndAssistItemsDto.setParentJobitem(jobItemDto);
        }

        if (!ObjectUtils.isEmpty(jobItem)) {
            List<RdmsJobItemDto> assistJobItemDtos = this.getJobitemListByParentJobitemId(jobItem.getId(), JobItemStatusEnum.SUBMIT.getStatus(), JobItemTypeEnum.ASSIST.getType());
            for (RdmsJobItemDto jobitem : assistJobItemDtos) {
                jobitem.setParentOrChildren(ParentOrChildrenEnum.CHILDREN.getKey());
            }
            jobItemAndAssistItemsDto.setChildJobitemList(assistJobItemDtos);
        }
        return jobItemAndAssistItemsDto;
    }

    /**
     *
     */
    @Transactional
    public RdmsHmiJobItemAndChildrenItemsDto getJobitemAndAllEvaluateJobitems(String jobitemId) {
        RdmsHmiJobItemAndChildrenItemsDto jobItemAndChildrenItemsDto = new RdmsHmiJobItemAndChildrenItemsDto();
        RdmsJobItem jobItem = this.selectByPrimaryKey(jobitemId);
        if (!ObjectUtils.isEmpty(jobItem)) {
            RdmsJobItemDto jobItemDto = CopyUtil.copy(jobItem, RdmsJobItemDto.class);
            jobItemDto.setParentOrChildren(ParentOrChildrenEnum.PARENT.getKey());
            jobItemAndChildrenItemsDto.setParentJobitem(jobItemDto);
        }

        List<RdmsJobItemDto> evaluateJobItemDtos = new ArrayList<>();
        if (!ObjectUtils.isEmpty(jobItem)) {
            if (jobItem.getType().equals(JobItemTypeEnum.TEST.getType())) {
                evaluateJobItemDtos = this.getJobitemListByParentJobitemId(jobItem.getId(), JobItemStatusEnum.SUBMIT.getStatus(), JobItemTypeEnum.BUG.getType());
                for (RdmsJobItemDto jobitem : evaluateJobItemDtos) {
                    jobitem.setParentOrChildren(ParentOrChildrenEnum.CHILDREN.getKey());
                }
                jobItemAndChildrenItemsDto.setChildJobitemList(evaluateJobItemDtos);
            } else {
                evaluateJobItemDtos = this.getJobitemListByParentJobitemId(jobItem.getId(), JobItemStatusEnum.SUBMIT.getStatus(), JobItemTypeEnum.ASSIST.getType());
                for (RdmsJobItemDto jobitem : evaluateJobItemDtos) {
                    jobitem.setParentOrChildren(ParentOrChildrenEnum.CHILDREN.getKey());
                }
                jobItemAndChildrenItemsDto.setChildJobitemList(evaluateJobItemDtos);
            }
        }
        return jobItemAndChildrenItemsDto;
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemListBySubprojectId(String subprojectId, List<JobItemStatusEnum> statusEnumList, List<JobItemTypeEnum> typeEnumList) {
        RdmsJobItemExample jobItemExample = new RdmsJobItemExample();
        jobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria = jobItemExample.createCriteria().andDeletedEqualTo(0).andSubprojectIdEqualTo(subprojectId);

        if (!CollectionUtils.isEmpty(statusEnumList)) {
            List<String> statusList = new ArrayList<>();
            for (JobItemStatusEnum statusEnum : statusEnumList) {
                statusList.add(statusEnum.getStatus());
            }
            criteria.andStatusIn(statusList);
        }

        if (!CollectionUtils.isEmpty(typeEnumList)) {
            List<String> typeList = new ArrayList<>();
            for (JobItemTypeEnum typeEnum : typeEnumList) {
                typeList.add(typeEnum.getType());
            }
            criteria.andTypeIn(typeList);
        }

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(jobItemExample);
        return CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
    }

    @Transactional
    public List<String> getPropertyFileIdList(String jobitemId) {
        List<RdmsJobItemProperty> jobItemPropertyList = rdmsJobItemPropertyService.getJobItemPropertyByJobItemId(jobitemId);
        if (CollectionUtils.isEmpty(jobItemPropertyList)) {
            return null;
        }
        List<String> propertyFileIdList = new ArrayList<>();
        for (RdmsJobItemProperty property : jobItemPropertyList) {
            if (!(ObjectUtils.isEmpty(property) || ObjectUtils.isEmpty(property.getFileListStr()))) {
                List<String> fileIdList = JSON.parseArray(property.getFileListStr(), String.class);
                propertyFileIdList.addAll(fileIdList);
            }
        }
        return propertyFileIdList;
    }

    @Transactional
    public List<RdmsJobItemDto> getFirstLevelJobitemListBySubprojectId(String subprojectId) {
        RdmsJobItemExample jobItemExample = new RdmsJobItemExample();
        jobItemExample.setOrderByClause("create_time desc");
        List<String> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.DEVELOP.getType());
        typeList.add(JobItemTypeEnum.CHARACTER_INT.getType());
        typeList.add(JobItemTypeEnum.DECOMPOSE.getType());
        typeList.add(JobItemTypeEnum.ITERATION.getType());
        typeList.add(JobItemTypeEnum.TASK_SUBP.getType());
        typeList.add(JobItemTypeEnum.TASK_MILESTONE.getType());
        jobItemExample.createCriteria()
                .andDeletedEqualTo(0)
                .andTypeIn(typeList)
                .andSubprojectIdEqualTo(subprojectId);
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(jobItemExample);
        List<RdmsJobItemDto> jobItemDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        List<JobItemTypeEnum> typeList2 = new ArrayList<>();
        typeList2.add(JobItemTypeEnum.TEST);
        List<JobItemTypeEnum> auxTypeList = new ArrayList<>();
        auxTypeList.add(JobItemTypeEnum.DEVELOP);
        auxTypeList.add(JobItemTypeEnum.CHARACTER_INT);
        List<RdmsJobItemDto> jobitemList = this.getJobitemListBySubprojectIdAndStatusListTypeListAuxTypeList(subprojectId, null, typeList2, auxTypeList);

        jobItemDtos.addAll(jobitemList);

        return jobItemDtos;
    }

    @Transactional
    public Long getJobitemCountBySubprojectId(String subprojectId, List<JobItemStatusEnum> statusEnumList) {
        RdmsJobItemExample jobItemExample = new RdmsJobItemExample();
        jobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria = jobItemExample.createCriteria()
                .andDeletedEqualTo(0)
                .andSubprojectIdEqualTo(subprojectId);

        if (!CollectionUtils.isEmpty(statusEnumList)) {
            List<String> statusList = new ArrayList<>();
            for (JobItemStatusEnum statusEnum : statusEnumList) {
                statusList.add(statusEnum.getStatus());
            }
            criteria.andStatusIn(statusList);
        }

        return rdmsJobItemsMapper.countByExample(jobItemExample);
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemListByProjectId(String projectId, List<JobItemStatusEnum> statusEnumList, List<JobItemTypeEnum> typeEnumList) {
        RdmsJobItemExample jobItemExample = new RdmsJobItemExample();
        jobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria = jobItemExample.createCriteria().andDeletedEqualTo(0).andProjectIdEqualTo(projectId);

        if (!CollectionUtils.isEmpty(statusEnumList)) {
            List<String> statusList = new ArrayList<>();
            for (JobItemStatusEnum statusEnum : statusEnumList) {
                statusList.add(statusEnum.getStatus());
            }
            criteria.andStatusIn(statusList);
        }

        if (!CollectionUtils.isEmpty(typeEnumList)) {
            List<String> typeList = new ArrayList<>();
            for (JobItemTypeEnum typeEnum : typeEnumList) {
                typeList.add(typeEnum.getType());
            }
            criteria.andTypeIn(typeList);
        }

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(jobItemExample);
        return CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemListByProjectId_mainPjm(String projectId, List<JobItemStatusEnum> statusEnumList, List<JobItemTypeEnum> typeEnumList, String mainPjmId) {
        RdmsJobItemExample jobItemExample = new RdmsJobItemExample();
        jobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria = jobItemExample.createCriteria()
                .andDeletedEqualTo(0)
                .andProjectManagerIdEqualTo(mainPjmId)
                .andProjectIdEqualTo(projectId);

        if (!CollectionUtils.isEmpty(statusEnumList)) {
            List<String> statusList = new ArrayList<>();
            for (JobItemStatusEnum statusEnum : statusEnumList) {
                statusList.add(statusEnum.getStatus());
            }
            criteria.andStatusIn(statusList);
        }

        if (!CollectionUtils.isEmpty(typeEnumList)) {
            List<String> typeList = new ArrayList<>();
            for (JobItemTypeEnum typeEnum : typeEnumList) {
                typeList.add(typeEnum.getType());
            }
            criteria.andTypeIn(typeList);
        }

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(jobItemExample);
        return CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemListBySubProjectId(String subprojectId, List<JobItemStatusEnum> statusEnumList, List<JobItemTypeEnum> typeEnumList) {
        RdmsJobItemExample jobItemExample = new RdmsJobItemExample();
        jobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria = jobItemExample.createCriteria().andDeletedEqualTo(0).andSubprojectIdEqualTo(subprojectId);

        if (!CollectionUtils.isEmpty(statusEnumList)) {
            List<String> statusList = new ArrayList<>();
            for (JobItemStatusEnum statusEnum : statusEnumList) {
                statusList.add(statusEnum.getStatus());
            }
            criteria.andStatusIn(statusList);
        }

        if (!CollectionUtils.isEmpty(typeEnumList)) {
            List<String> typeList = new ArrayList<>();
            for (JobItemTypeEnum typeEnum : typeEnumList) {
                typeList.add(typeEnum.getType());
            }
            criteria.andTypeIn(typeList);
        }

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(jobItemExample);
        return CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemListByPreProjectId(String preprojectId, List<JobItemStatusEnum> statusEnumList, List<JobItemTypeEnum> typeEnumList) {
        RdmsJobItemExample jobItemExample = new RdmsJobItemExample();
        jobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria = jobItemExample.createCriteria()
                .andDeletedEqualTo(0)
                .andPreProjectIdEqualTo(preprojectId);

        if (!CollectionUtils.isEmpty(statusEnumList)) {
            List<String> statusList = new ArrayList<>();
            for (JobItemStatusEnum statusEnum : statusEnumList) {
                statusList.add(statusEnum.getStatus());
            }
            criteria.andStatusIn(statusList);
        }

        if (!CollectionUtils.isEmpty(typeEnumList)) {
            List<String> typeList = new ArrayList<>();
            for (JobItemTypeEnum typeEnum : typeEnumList) {
                typeList.add(typeEnum.getType());
            }
            criteria.andTypeIn(typeList);
        }

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(jobItemExample);
        return CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemListByIdList(List<String> jobIdList) {
        RdmsJobItemExample jobItemExample = new RdmsJobItemExample();
        jobItemExample.setOrderByClause("create_time desc");
        jobItemExample.createCriteria()
                .andDeletedEqualTo(0)
                .andIdIn(jobIdList);
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(jobItemExample);
        return CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemListByParentJobitemId(String parentJobitemId, String jobItemStatus, String jobItemType) {
        if (!ObjectUtils.isEmpty(jobItemStatus)) {
            JobItemStatusEnum jobItemStatusEnumByStatus = JobItemStatusEnum.getJobItemStatusEnumByStatus(jobItemStatus);
            if (ObjectUtils.isEmpty(jobItemStatusEnumByStatus)) {
                throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
            }
        }
        if (!ObjectUtils.isEmpty(jobItemType)) {
            JobItemTypeEnum jobItemTypeEnumByType = JobItemTypeEnum.getJobItemTypeEnumByType(jobItemType);
            if (ObjectUtils.isEmpty(jobItemTypeEnumByType)) {
                throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
            }
        }
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andParentJobitemIdEqualTo(parentJobitemId).andDeletedEqualTo(0);
        if (!ObjectUtils.isEmpty(jobItemStatus)) {
            criteria1.andStatusEqualTo(jobItemStatus);
        }
        if (!ObjectUtils.isEmpty(jobItemType)) {
            criteria1.andTypeEqualTo(jobItemType);
        }
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (RdmsJobItemDto jobItemsDto : rdmsJobItemsDtos) {
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getExecutorId());
            jobItemsDto.setExecutorName(rdmsCustomerUser.getTrueName());

            jobItemsDto.setCreateTimeStr(sdf.format(jobItemsDto.getCreateTime()));

            RdmsCustomer customer = rdmsCustomerService.selectByPrimaryKey(jobItemsDto.getCustomerId());
            jobItemsDto.setCustomerName(customer.getCustomerName());

            RdmsProject projectInfo = rdmsProjectService.selectByPrimaryKey(jobItemsDto.getProjectId());
            if (!ObjectUtils.isEmpty(projectInfo)) {
                jobItemsDto.setProjectName(projectInfo.getProjectName());
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getSubprojectId())) {
                RdmsProjectSubproject subProjectInfo = rdmsSubprojectService.getSubProjectInfo(jobItemsDto.getSubprojectId());
                jobItemsDto.setSubprojectName(subProjectInfo.getLabel());
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getCharacterId())) {
                RdmsCharacter character = rdmsCharacterService.selectByPrimaryKey(jobItemsDto.getCharacterId());
                if (!ObjectUtils.isEmpty(character)) {
                    jobItemsDto.setCharacterDto(CopyUtil.copy(character, RdmsCharacterDto.class));
                    jobItemsDto.setCharacterName(character.getCharacterName());
                }
            }

            RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getProductManagerId());
            if (!ObjectUtils.isEmpty(customerUser)) {
                jobItemsDto.setProductManagerName(customerUser.getTrueName());
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getProjectManagerId())) {
                RdmsCustomerUser customerUser1 = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getProjectManagerId());
                jobItemsDto.setProjectManagerName(customerUser1.getTrueName());
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getFileListStr()) && jobItemsDto.getFileListStr().length() > 6) {
                List<String> stringList = JSON.parseArray(jobItemsDto.getFileListStr(), String.class);
                List<RdmsFileDto> fileDtos = new ArrayList<>();
                for (String id : stringList) {
                    RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(id);
                    fileDtos.add(fileDto);
                }
                jobItemsDto.setFileList(fileDtos);
            }
        }
        return rdmsJobItemsDtos;
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemListByParentAndStatusListTypeList(String parentJobitemId, List<JobItemStatusEnum> statusEnumList, List<JobItemTypeEnum> typeEnumList) {

        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andParentJobitemIdEqualTo(parentJobitemId).andDeletedEqualTo(0);

        List<String> statusList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(statusEnumList)) {
            for (JobItemStatusEnum statusEnum : statusEnumList) {
                statusList.add(statusEnum.getStatus());
            }
            criteria1.andStatusIn(statusList);
        }
        List<String> typeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(typeEnumList)) {
            for (JobItemTypeEnum typeEnum : typeEnumList) {
                typeList.add(typeEnum.getType());
            }
            criteria1.andTypeIn(typeList);
        }

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        return rdmsJobItemsDtos;
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemListByParentIdStatusTypeAndAuxTypeList(String parentId, List<JobItemStatusEnum> statusList, List<JobItemTypeEnum> typeList, List<JobItemTypeEnum> auxTypeList) {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria = rdmsJobItemExample.createCriteria();
        criteria.andParentJobitemIdEqualTo(parentId).andDeletedEqualTo(0);
        List<String> status = new ArrayList<>();
        if (!CollectionUtils.isEmpty(statusList)) {
            for (JobItemStatusEnum statusEnum : statusList) {
                status.add(statusEnum.getStatus());
            }
            criteria.andStatusIn(status);
        }

        List<String> types = new ArrayList<>();
        if (!CollectionUtils.isEmpty(typeList)) {
            for (JobItemTypeEnum typeEnum : typeList) {
                types.add(typeEnum.getType());
            }
            criteria.andTypeIn(types);
        }

        List<String> auxTypes = new ArrayList<>();
        if (!CollectionUtils.isEmpty(auxTypeList)) {
            for (JobItemTypeEnum typeEnum : auxTypeList) {
                auxTypes.add(typeEnum.getType());
            }
            criteria.andAuxTypeIn(auxTypes);
        }
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
        return rdmsJobItemsDtos;
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemListByParentIdStatusListTypeAndNotInAuxTypeList(String parentId, List<JobItemStatusEnum> statusList, List<JobItemTypeEnum> typeList, List<JobItemTypeEnum> notInAuxTypeList) {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria = rdmsJobItemExample.createCriteria();
        criteria.andParentJobitemIdEqualTo(parentId).andDeletedEqualTo(0);
        List<String> status = new ArrayList<>();
        if (!CollectionUtils.isEmpty(statusList)) {
            for (JobItemStatusEnum statusEnum : statusList) {
                status.add(statusEnum.getStatus());
            }
            if (!CollectionUtils.isEmpty(status)) {
                criteria.andStatusIn(status);
            }
        }

        List<String> types = new ArrayList<>();
        if (!CollectionUtils.isEmpty(typeList)) {
            for (JobItemTypeEnum typeEnum : typeList) {
                types.add(typeEnum.getType());
            }
            criteria.andTypeIn(types);
        }

        List<String> auxTypes = new ArrayList<>();
        if (!CollectionUtils.isEmpty(notInAuxTypeList)) {
            for (JobItemTypeEnum typeEnum : notInAuxTypeList) {
                auxTypes.add(typeEnum.getType());
            }
            if (!CollectionUtils.isEmpty(auxTypes)) {
                criteria.andAuxTypeNotIn(auxTypes);  //排除的类型
            }
        }
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
        return rdmsJobItemsDtos;
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemListByParentIdStatusListTypeAndAuxTypeList(String parentId, List<JobItemStatusEnum> statusList, List<JobItemTypeEnum> typeList, List<JobItemTypeEnum> auxTypeList) {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria = rdmsJobItemExample.createCriteria();
        criteria.andParentJobitemIdEqualTo(parentId).andDeletedEqualTo(0);
        List<String> status = new ArrayList<>();
        if (!CollectionUtils.isEmpty(statusList)) {
            for (JobItemStatusEnum statusEnum : statusList) {
                status.add(statusEnum.getStatus());
            }
            if (!CollectionUtils.isEmpty(status)) {
                criteria.andStatusIn(status);
            }
        }

        List<String> types = new ArrayList<>();
        if (!CollectionUtils.isEmpty(typeList)) {
            for (JobItemTypeEnum typeEnum : typeList) {
                types.add(typeEnum.getType());
            }
            if (!CollectionUtils.isEmpty(types)) {
                criteria.andTypeIn(types);
            }
        }

        List<String> auxTypes = new ArrayList<>();
        if (!CollectionUtils.isEmpty(auxTypeList)) {
            for (JobItemTypeEnum typeEnum : auxTypeList) {
                auxTypes.add(typeEnum.getType());
            }
            if (!CollectionUtils.isEmpty(auxTypes)) {
                criteria.andAuxTypeIn(auxTypes);
            }
        }
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
        return rdmsJobItemsDtos;
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemListByTestedJobiemIdStatusListTypeAndaNotInAuxTypeList(String testedId, List<JobItemStatusEnum> statusList, List<JobItemTypeEnum> typeList, List<JobItemTypeEnum> notInAuxTypeList) {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria = rdmsJobItemExample.createCriteria();
        criteria.andTestedJobitemIdEqualTo(testedId).andDeletedEqualTo(0);
        List<String> status = new ArrayList<>();
        if (!CollectionUtils.isEmpty(statusList)) {
            for (JobItemStatusEnum statusEnum : statusList) {
                status.add(statusEnum.getStatus());
            }
            if (!CollectionUtils.isEmpty(status)) {
                criteria.andStatusIn(status);
            }
        }

        List<String> types = new ArrayList<>();
        if (!CollectionUtils.isEmpty(typeList)) {
            for (JobItemTypeEnum typeEnum : typeList) {
                types.add(typeEnum.getType());
            }
            if (!CollectionUtils.isEmpty(types)) {
                criteria.andTypeIn(types);
            }
        }

        List<String> auxTypes = new ArrayList<>();
        if (!CollectionUtils.isEmpty(notInAuxTypeList)) {
            for (JobItemTypeEnum typeEnum : notInAuxTypeList) {
                auxTypes.add(typeEnum.getType());
            }
            if (!CollectionUtils.isEmpty(auxTypes)) {
                criteria.andAuxTypeNotIn(auxTypes);  //排除的类型
            }
        }
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
        return rdmsJobItemsDtos;
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemListByTestedJobiemIdStatusListTypeList(String testedId, List<JobItemStatusEnum> statusList, List<JobItemTypeEnum> typeList) {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria = rdmsJobItemExample.createCriteria();
        criteria.andTestedJobitemIdEqualTo(testedId).andDeletedEqualTo(0);
        List<String> status = new ArrayList<>();
        if (!CollectionUtils.isEmpty(statusList)) {
            for (JobItemStatusEnum statusEnum : statusList) {
                status.add(statusEnum.getStatus());
            }
            if (!CollectionUtils.isEmpty(status)) {
                criteria.andStatusIn(status);
            }
        }

        List<String> types = new ArrayList<>();
        if (!CollectionUtils.isEmpty(typeList)) {
            for (JobItemTypeEnum typeEnum : typeList) {
                types.add(typeEnum.getType());
            }
            if (!CollectionUtils.isEmpty(types)) {
                criteria.andTypeIn(types);
            }
        }

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
        return rdmsJobItemsDtos;
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemListByTestedJobitemId(String testedJobitemId, String jobItemStatus, String jobItemType) {
        if (!ObjectUtils.isEmpty(jobItemStatus)) {
            JobItemStatusEnum jobItemStatusEnumByStatus = JobItemStatusEnum.getJobItemStatusEnumByStatus(jobItemStatus);
            if (ObjectUtils.isEmpty(jobItemStatusEnumByStatus)) {
                throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
            }
        }
        if (!ObjectUtils.isEmpty(jobItemType)) {
            JobItemTypeEnum jobItemTypeEnumByType = JobItemTypeEnum.getJobItemTypeEnumByType(jobItemType);
            if (ObjectUtils.isEmpty(jobItemTypeEnumByType)) {
                throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
            }
        }
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andTestedJobitemIdEqualTo(testedJobitemId).andDeletedEqualTo(0);
        if (!ObjectUtils.isEmpty(jobItemStatus)) {
            criteria1.andStatusEqualTo(jobItemStatus);
        }
        if (!ObjectUtils.isEmpty(jobItemType)) {
            criteria1.andTypeEqualTo(jobItemType);
        }
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> rdmsJobItemsDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (RdmsJobItemDto jobItemsDto : rdmsJobItemsDtos) {
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getExecutorId());
            jobItemsDto.setExecutorName(rdmsCustomerUser.getTrueName());

            jobItemsDto.setCreateTimeStr(sdf.format(jobItemsDto.getCreateTime()));

            RdmsCustomer customer = rdmsCustomerService.selectByPrimaryKey(jobItemsDto.getCustomerId());
            jobItemsDto.setCustomerName(customer.getCustomerName());

            RdmsProject projectInfo = rdmsProjectService.selectByPrimaryKey(jobItemsDto.getProjectId());
            if (!ObjectUtils.isEmpty(projectInfo)) {
                jobItemsDto.setProjectName(projectInfo.getProjectName());
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getSubprojectId())) {
                RdmsProjectSubproject subProjectInfo = rdmsSubprojectService.getSubProjectInfo(jobItemsDto.getSubprojectId());
                jobItemsDto.setSubprojectName(subProjectInfo.getLabel());
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getCharacterId())) {
                RdmsCharacter character = rdmsCharacterService.selectByPrimaryKey(jobItemsDto.getCharacterId());
                if (!ObjectUtils.isEmpty(character)) {
                    jobItemsDto.setCharacterDto(CopyUtil.copy(character, RdmsCharacterDto.class));
                    jobItemsDto.setCharacterName(character.getCharacterName());
                }
            }

            RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getProductManagerId());
            if (!ObjectUtils.isEmpty(customerUser)) {
                jobItemsDto.setProductManagerName(customerUser.getTrueName());
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getProjectManagerId())) {
                RdmsCustomerUser customerUser1 = rdmsCustomerUserService.selectByPrimaryKey(jobItemsDto.getProjectManagerId());
                jobItemsDto.setProjectManagerName(customerUser1.getTrueName());
            }

            if (!ObjectUtils.isEmpty(jobItemsDto.getFileListStr()) && jobItemsDto.getFileListStr().length() > 6) {
                List<String> stringList = JSON.parseArray(jobItemsDto.getFileListStr(), String.class);
                List<RdmsFileDto> fileDtos = new ArrayList<>();
                for (String id : stringList) {
                    RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(id);
                    fileDtos.add(fileDto);
                }
                jobItemsDto.setFileList(fileDtos);
            }
        }
        return rdmsJobItemsDtos;
    }

    /**
     * @param characterId
     */
    @Transactional
    public List<RdmsJobItemDto> getAllJobitemSimpleInfoByCharacterId(String characterId) {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andCharacterIdEqualTo(characterId).andDeletedEqualTo(0);
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        return CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
    }

    /**
     * @param parentJobitem
     */
    @Transactional
    public Boolean getAllChildrenJobitemComplateStatus(String parentJobitem) {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andParentJobitemIdEqualTo(parentJobitem).andDeletedEqualTo(0);
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        Boolean status = true;
        if (!CollectionUtils.isEmpty(rdmsJobItems)) {
            for (RdmsJobItem item : rdmsJobItems) {
                if (!item.getStatus().equals(JobItemStatusEnum.COMPLETED.getStatus())) {
                    status = false;
                    break;
                }
            }
        }
        return status;
    }

    @Transactional
    public List<RdmsJobItemDto> getDevelopJobitemsByCharacterId(String characterId, String loginUserId) {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andCharacterIdEqualTo(characterId).andDeletedEqualTo(0).andTypeEqualTo(JobItemTypeEnum.DEVELOP.getType());
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> jobItemDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
        if (!CollectionUtils.isEmpty(jobItemDtos)) {
            for (RdmsJobItemDto jobItemDto : jobItemDtos) {
                //获得工单任务附件,并加上absPath
                List<String> jobitemFileList = JSON.parseArray(jobItemDto.getFileListStr(), String.class);
                if (!CollectionUtils.isEmpty(jobitemFileList)) {
                    List<RdmsFileDto> fileDtoList = new ArrayList<>();
                    for (String fileId : jobitemFileList) {
                        RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(fileId);
                        //读取访问权限
                        if (!ObjectUtils.isEmpty(fileDto)) {
                            //补充填写文件权限信息
                            RdmsFileAuth byFileId = rdmsFileAuthService.getByFileId(fileDto.getId());
                            if (ObjectUtils.isEmpty(byFileId)) {
                                fileDto.setAuthStatus(true);
                            } else {
                                String authIdsStr = byFileId.getAuthIdsStr();
                                if (ObjectUtils.isEmpty(authIdsStr)) {
                                    fileDto.setAuthStatus(true);
                                } else {
                                    List<String> strings = JSON.parseArray(authIdsStr, String.class);
                                    if (strings.contains(loginUserId)) {
                                        fileDto.setAuthStatus(true);
                                    } else {
                                        fileDto.setAuthStatus(false);
                                    }
                                }
                            }
                        }
                        fileDtoList.add(fileDto);
                    }
                    jobItemDto.setFileList(fileDtoList);
                }

                //获得工单过程处理附件,并加上absPath
                List<String> processFileList = JSON.parseArray(jobItemDto.getProcessFileListStr(), String.class);
                if (!CollectionUtils.isEmpty(processFileList)) {
                    List<RdmsFileDto> fileDtoList = new ArrayList<>();
                    for (String fileId : processFileList) {
                        RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(fileId);
                        //读取访问权限
                        if (!ObjectUtils.isEmpty(fileDto)) {
                            //补充填写文件权限信息
                            RdmsFileAuth byFileId = rdmsFileAuthService.getByFileId(fileDto.getId());
                            if (ObjectUtils.isEmpty(byFileId)) {
                                fileDto.setAuthStatus(true);
                            } else {
                                String authIdsStr = byFileId.getAuthIdsStr();
                                if (ObjectUtils.isEmpty(authIdsStr)) {
                                    fileDto.setAuthStatus(true);
                                } else {
                                    List<String> strings = JSON.parseArray(authIdsStr, String.class);
                                    if (strings.contains(loginUserId)) {
                                        fileDto.setAuthStatus(true);
                                    } else {
                                        fileDto.setAuthStatus(false);
                                    }
                                }
                            }
                        }
                        fileDtoList.add(fileDto);
                    }
                    jobItemDto.setProcessFileList(fileDtoList);
                }

                //工单资产
                RdmsJobItemProperty rdmsJobItemProperty = rdmsJobItemPropertyService.selectByPrimaryKey(jobItemDto.getPropertyId());
                RdmsJobItemPropertyDto propertyDto = CopyUtil.copy(rdmsJobItemProperty, RdmsJobItemPropertyDto.class);
                List<String> stringList = JSON.parseArray(propertyDto.getFileListStr(), String.class);
                if (!CollectionUtils.isEmpty(stringList)) {
                    List<RdmsFileDto> fileDtoList = new ArrayList<>();
                    for (String fileId : stringList) {
                        RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(fileId);
                        //读取访问权限
                        if (!ObjectUtils.isEmpty(fileDto)) {
                            //补充填写文件权限信息
                            RdmsFileAuth byFileId = rdmsFileAuthService.getByFileId(fileDto.getId());
                            if (ObjectUtils.isEmpty(byFileId)) {
                                fileDto.setAuthStatus(true);
                            } else {
                                String authIdsStr = byFileId.getAuthIdsStr();
                                if (ObjectUtils.isEmpty(authIdsStr)) {
                                    fileDto.setAuthStatus(true);
                                } else {
                                    List<String> strings = JSON.parseArray(authIdsStr, String.class);
                                    if (strings.contains(loginUserId)) {
                                        fileDto.setAuthStatus(true);
                                    } else {
                                        fileDto.setAuthStatus(false);
                                    }
                                }
                            }
                        }
                        fileDtoList.add(fileDto);
                    }
                    propertyDto.setFileItems(fileDtoList);
                }
                jobItemDto.setPropertyDto(propertyDto);
            }
        }
        return jobItemDtos;
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemsByCharacterId(String characterId) {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andCharacterIdEqualTo(characterId).andDeletedEqualTo(0);
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> jobItemDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
        return jobItemDtos;
    }

    @Transactional
    public List<RdmsJobItemDto> getSuggestUpdateJobitem(String characterId) {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andCharacterIdEqualTo(characterId)
                .andTypeEqualTo(JobItemTypeEnum.SUGGEST_UPDATE.getType())
                .andDeletedEqualTo(0);
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> jobItemDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
        return jobItemDtos;
    }

    @Transactional
    public List<RdmsJobItemDto> getDevJobitemsByCharacterId(String characterId) {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andCharacterIdEqualTo(characterId).andDeletedEqualTo(0);

        List<String> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.DEVELOP.getType());
        typeList.add(JobItemTypeEnum.ITERATION.getType());
        typeList.add(JobItemTypeEnum.DECOMPOSE.getType());
        typeList.add(JobItemTypeEnum.CHARACTER_INT.getType());
        typeList.add(JobItemTypeEnum.ASSIST.getType());
        criteria1.andTypeIn(typeList);

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> jobItemDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
        return jobItemDtos;
    }

    @Transactional
    public List<RdmsJobItemDto> getTestJobitemsByCharacterId(String characterId) {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andCharacterIdEqualTo(characterId).andDeletedEqualTo(0);

        List<String> typeList = new ArrayList<>();
        typeList.add(JobItemTypeEnum.TEST.getType());
        typeList.add(JobItemTypeEnum.BUG.getType());
        criteria1.andTypeIn(typeList);

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> jobItemDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
        return jobItemDtos;
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemByTestedJobitemId(String testedJobitemId, String loginUserId, JobItemTypeEnum jobTypeEnum) {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andTestedJobitemIdEqualTo(testedJobitemId).andDeletedEqualTo(0);
        if (!ObjectUtils.isEmpty(jobTypeEnum)) {
            criteria1.andTypeEqualTo(jobTypeEnum.getType());
        }
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> jobItemDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
        if (!CollectionUtils.isEmpty(jobItemDtos)) {
            for (RdmsJobItemDto jobItemDto : jobItemDtos) {
                //获得工单任务附件,并加上absPath
                List<String> jobitemFileList = JSON.parseArray(jobItemDto.getFileListStr(), String.class);
                if (!CollectionUtils.isEmpty(jobitemFileList)) {
                    List<RdmsFileDto> fileDtoList = new ArrayList<>();
                    for (String fileId : jobitemFileList) {
                        RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(fileId);
                        //读取访问权限
                        if (!ObjectUtils.isEmpty(fileDto)) {
                            //补充填写文件权限信息
                            RdmsFileAuth byFileId = rdmsFileAuthService.getByFileId(fileDto.getId());
                            if (ObjectUtils.isEmpty(byFileId)) {
                                fileDto.setAuthStatus(true);
                            } else {
                                String authIdsStr = byFileId.getAuthIdsStr();
                                if (ObjectUtils.isEmpty(authIdsStr)) {
                                    fileDto.setAuthStatus(true);
                                } else {
                                    List<String> strings = JSON.parseArray(authIdsStr, String.class);
                                    if (strings.contains(loginUserId)) {
                                        fileDto.setAuthStatus(true);
                                    } else {
                                        fileDto.setAuthStatus(false);
                                    }
                                }
                            }
                        }
                        fileDtoList.add(fileDto);
                    }
                    jobItemDto.setFileList(fileDtoList);
                }

                //获得工单过程处理附件,并加上absPath
                List<String> processFileList = JSON.parseArray(jobItemDto.getProcessFileListStr(), String.class);
                if (!CollectionUtils.isEmpty(processFileList)) {
                    List<RdmsFileDto> fileDtoList = new ArrayList<>();
                    for (String fileId : processFileList) {
                        RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(fileId);
                        //读取访问权限
                        if (!ObjectUtils.isEmpty(fileDto)) {
                            //补充填写文件权限信息
                            RdmsFileAuth byFileId = rdmsFileAuthService.getByFileId(fileDto.getId());
                            if (ObjectUtils.isEmpty(byFileId)) {
                                fileDto.setAuthStatus(true);
                            } else {
                                String authIdsStr = byFileId.getAuthIdsStr();
                                if (ObjectUtils.isEmpty(authIdsStr)) {
                                    fileDto.setAuthStatus(true);
                                } else {
                                    List<String> strings = JSON.parseArray(authIdsStr, String.class);
                                    if (strings.contains(loginUserId)) {
                                        fileDto.setAuthStatus(true);
                                    } else {
                                        fileDto.setAuthStatus(false);
                                    }
                                }
                            }
                        }
                        fileDtoList.add(fileDto);
                    }
                    jobItemDto.setProcessFileList(fileDtoList);
                }

                RdmsJobItemProperty rdmsJobItemProperty = rdmsJobItemPropertyService.selectByPrimaryKey(jobItemDto.getPropertyId());
                if (!ObjectUtils.isEmpty(rdmsJobItemProperty)) {
                    RdmsJobItemPropertyDto propertyDto = CopyUtil.copy(rdmsJobItemProperty, RdmsJobItemPropertyDto.class);
                    if (!ObjectUtils.isEmpty(propertyDto.getFileListStr())) {
                        List<String> stringList = JSON.parseArray(propertyDto.getFileListStr(), String.class);
                        if (!CollectionUtils.isEmpty(stringList)) {
                            List<RdmsFileDto> fileDtoList = new ArrayList<>();
                            for (String fileId : stringList) {
                                RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(fileId);
                                //读取访问权限
                                if (!ObjectUtils.isEmpty(fileDto)) {
                                    //补充填写文件权限信息
                                    RdmsFileAuth byFileId = rdmsFileAuthService.getByFileId(fileDto.getId());
                                    if (ObjectUtils.isEmpty(byFileId)) {
                                        fileDto.setAuthStatus(true);
                                    } else {
                                        String authIdsStr = byFileId.getAuthIdsStr();
                                        if (ObjectUtils.isEmpty(authIdsStr)) {
                                            fileDto.setAuthStatus(true);
                                        } else {
                                            List<String> strings = JSON.parseArray(authIdsStr, String.class);
                                            if (strings.contains(loginUserId)) {
                                                fileDto.setAuthStatus(true);
                                            } else {
                                                fileDto.setAuthStatus(false);
                                            }
                                        }
                                    }
                                }
                                fileDtoList.add(fileDto);
                            }
                            propertyDto.setFileItems(fileDtoList);
                        }
                        jobItemDto.setPropertyDto(propertyDto);
                    }
                }
            }
        }
        return jobItemDtos;
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemByTestedJobitemIdAndTypeEnumList(String testedJobitemId, List<JobItemTypeEnum> jobTypeEnumList) {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andTestedJobitemIdEqualTo(testedJobitemId).andDeletedEqualTo(0);

        List<String> typeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jobTypeEnumList)) {
            for (JobItemTypeEnum typeEnum : jobTypeEnumList) {
                typeList.add(typeEnum.getType());
            }
            criteria1.andTypeIn(typeList);
        }

        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> jobItemDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
        if (!CollectionUtils.isEmpty(jobItemDtos)) {
            for (RdmsJobItemDto jobItemDto : jobItemDtos) {
                //获得工单任务附件,并加上absPath
                List<String> jobitemFileList = JSON.parseArray(jobItemDto.getFileListStr(), String.class);
                if (!CollectionUtils.isEmpty(jobitemFileList)) {
                    List<RdmsFileDto> fileDtoList = new ArrayList<>();
                    for (String fileId : jobitemFileList) {
                        RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(fileId);
                        fileDtoList.add(fileDto);
                    }
                    jobItemDto.setFileList(fileDtoList);
                }

                //获得工单过程处理附件,并加上absPath
                List<String> processFileList = JSON.parseArray(jobItemDto.getProcessFileListStr(), String.class);
                if (!CollectionUtils.isEmpty(processFileList)) {
                    List<RdmsFileDto> fileDtoList = new ArrayList<>();
                    for (String fileId : processFileList) {
                        RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(fileId);
                        fileDtoList.add(fileDto);
                    }
                    jobItemDto.setProcessFileList(fileDtoList);
                }

                RdmsJobItemProperty rdmsJobItemProperty = rdmsJobItemPropertyService.selectByPrimaryKey(jobItemDto.getPropertyId());
                if (!ObjectUtils.isEmpty(rdmsJobItemProperty)) {
                    RdmsJobItemPropertyDto propertyDto = CopyUtil.copy(rdmsJobItemProperty, RdmsJobItemPropertyDto.class);
                    if (!ObjectUtils.isEmpty(propertyDto.getFileListStr())) {
                        List<String> stringList = JSON.parseArray(propertyDto.getFileListStr(), String.class);
                        if (!CollectionUtils.isEmpty(stringList)) {
                            List<RdmsFileDto> fileDtoList = new ArrayList<>();
                            for (String fileId : stringList) {
                                RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(fileId);
                                fileDtoList.add(fileDto);
                            }
                            propertyDto.setFileItems(fileDtoList);
                        }
                        jobItemDto.setPropertyDto(propertyDto);
                    }
                }
            }
        }
        return jobItemDtos;
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemByParentJobitemId(String parentJobitemId, JobItemTypeEnum jobTypeEnum) {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andParentJobitemIdEqualTo(parentJobitemId).andDeletedEqualTo(0);
        if (!ObjectUtils.isEmpty(jobTypeEnum)) {
            criteria1.andTypeEqualTo(jobTypeEnum.getType());
        }
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        List<RdmsJobItemDto> jobItemDtos = CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
        if (!CollectionUtils.isEmpty(jobItemDtos)) {
            for (RdmsJobItemDto jobItemDto : jobItemDtos) {
                //获得工单任务附件,并加上absPath
                List<String> jobitemFileList = JSON.parseArray(jobItemDto.getFileListStr(), String.class);
                if (!CollectionUtils.isEmpty(jobitemFileList)) {
                    List<RdmsFileDto> fileDtoList = new ArrayList<>();
                    for (String fileId : jobitemFileList) {
                        RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(fileId);
                        fileDtoList.add(fileDto);
                    }
                    jobItemDto.setFileList(fileDtoList);
                }

                //获得工单过程处理附件,并加上absPath
                List<String> processFileList = JSON.parseArray(jobItemDto.getProcessFileListStr(), String.class);
                if (!CollectionUtils.isEmpty(processFileList)) {
                    List<RdmsFileDto> fileDtoList = new ArrayList<>();
                    for (String fileId : processFileList) {
                        RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(fileId);
                        fileDtoList.add(fileDto);
                    }
                    jobItemDto.setProcessFileList(fileDtoList);
                }

                RdmsJobItemProperty rdmsJobItemProperty = rdmsJobItemPropertyService.selectByPrimaryKey(jobItemDto.getPropertyId());
                if (!ObjectUtils.isEmpty(rdmsJobItemProperty)) {
                    RdmsJobItemPropertyDto propertyDto = CopyUtil.copy(rdmsJobItemProperty, RdmsJobItemPropertyDto.class);
                    if (!ObjectUtils.isEmpty(propertyDto.getFileListStr())) {
                        List<String> stringList = JSON.parseArray(propertyDto.getFileListStr(), String.class);
                        if (!CollectionUtils.isEmpty(stringList)) {
                            List<RdmsFileDto> fileDtoList = new ArrayList<>();
                            for (String fileId : stringList) {
                                RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(fileId);
                                fileDtoList.add(fileDto);
                            }
                            propertyDto.setFileItems(fileDtoList);
                        }
                        jobItemDto.setPropertyDto(propertyDto);
                    }
                }
            }
        }
        return jobItemDtos;
    }

    /**
     * 获得一个工单的直接附件
     * 1. 工单附件
     * 2. 资产附件
     * 3. 参考文献
     * 4. 流转附件
     *
     * @param jobItemId
     * @return
     */

    @Transactional
    public List<String> getAJobitemAttachmentIdList(String jobItemId) {
        RdmsJobItem jobItem = this.selectByPrimaryKey(jobItemId);
        List<String> fileIds = new ArrayList<>();
        //1. 工单附件
        if (!ObjectUtils.isEmpty(jobItem.getFileListStr())) {
            List<String> stringList = JSON.parseArray(jobItem.getFileListStr(), String.class);
            fileIds.addAll(stringList);
        }

        //2. 交付附件
        if (!ObjectUtils.isEmpty(jobItem.getPropertyId())) {
            RdmsJobItemProperty jobItemProperty = rdmsJobItemPropertyService.selectByPrimaryKey(jobItem.getPropertyId());
            //2.1 资产附件
            if (!ObjectUtils.isEmpty(jobItemProperty.getFileListStr()) && jobItemProperty.getFileListStr().length() > 6) {
                List<String> stringList1 = JSON.parseArray(jobItemProperty.getFileListStr(), String.class);
                fileIds.addAll(stringList1);
            }
            //2.2 参考文献
            if (!ObjectUtils.isEmpty(jobItemProperty.getReferenceFileListStr()) && jobItemProperty.getReferenceFileListStr().length() > 6) {
                List<String> stringList2 = JSON.parseArray(jobItemProperty.getReferenceFileListStr(), String.class);
                fileIds.addAll(stringList2);
            }
        }
        //3. 工单流转附件
        List<RdmsJobItemProcess> processListByJobId = rdmsJobItemProcessService.getJobProcessListByJobId(jobItemId);
        if (!CollectionUtils.isEmpty(processListByJobId)) {
            for (RdmsJobItemProcess process : processListByJobId) {
                if (!ObjectUtils.isEmpty(process.getFileListStr()) && process.getFileListStr().length() > 6) {
                    List<String> stringList1 = JSON.parseArray(process.getFileListStr(), String.class);
                    fileIds.addAll(stringList1);
                }
            }
        }
        List<String> fileIdList = fileIds.stream().distinct().collect(Collectors.toList());
        return fileIdList;
    }

    @Transactional
    public List<RdmsFileDto> getJobitemAllAttachments(String jobItemId) {
        List<String> jobitemAllRelatedDocIdList = rdmsDocService.getJobitemAllRelatedDocIdList(jobItemId);
        //通过ID转换为文件列表
        List<String> stringList = jobitemAllRelatedDocIdList.stream().distinct().collect(Collectors.toList());
        List<RdmsFileDto> fileList = rdmsFileService.getFileListByIdList(stringList);
        return fileList;
    }

    @Transactional
    public List<RdmsFileDto> getAllChildrenJobitemAttachments(String jobItemId) {
        List<RdmsFileDto> fileDtoList = new ArrayList<>();
        List<String> attachmentIdList = new ArrayList<>();
        //1. 获得children工单列表
        List<RdmsJobItemDto> childrenJobitemList = this.getJobitemByParentJobitemId(jobItemId, null);
        if (!CollectionUtils.isEmpty(childrenJobitemList)) {
            for (RdmsJobItemDto jobItemDto : childrenJobitemList) {
                List<String> aJobitemAttachmentIdList = this.getAJobitemAttachmentIdList(jobItemDto.getId());
                attachmentIdList.addAll(aJobitemAttachmentIdList);
            }
        }

        //通过ID转换为文件列表
        List<String> stringList = attachmentIdList.stream().distinct().collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(stringList)) {
            for (String fileId : stringList) {
                RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(fileId);
                fileDtoList.add(fileDto);
            }
        }
        return fileDtoList;
    }

    @Transactional
    public Long getChildrenJobitemNotCompleteNum(String jobItemId) {
        List<String> statusList = new ArrayList<>();
        statusList.add(JobItemStatusEnum.COMPLETED.getStatus());
        statusList.add(JobItemStatusEnum.ARCHIVED.getStatus());
        RdmsJobItemExample jobItemExample = new RdmsJobItemExample();
        jobItemExample.createCriteria().andStatusNotIn(statusList).andParentJobitemIdEqualTo(jobItemId).andDeletedEqualTo(0);
        return rdmsJobItemsMapper.countByExample(jobItemExample);
    }

    @Transactional
    public long getSumJobNumByCharacterId(String characterId, String jobType) {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andCharacterIdEqualTo(characterId).andDeletedEqualTo(0).andTypeEqualTo(jobType);
        return rdmsJobItemsMapper.countByExample(rdmsJobItemExample);
    }

    @Transactional
    public long getSumJobNumByParentJobitemId(String parentJobitemId, String jobType) {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andParentJobitemIdEqualTo(parentJobitemId).andDeletedEqualTo(0).andTypeEqualTo(jobType);
        return rdmsJobItemsMapper.countByExample(rdmsJobItemExample);
    }

    @Transactional
    public List<RdmsJobItem> getJobitemListByTestedIdAndJobType(String testedJobitemId, String jobType) {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andTestedJobitemIdEqualTo(testedJobitemId).andDeletedEqualTo(0).andTypeEqualTo(jobType);
        return rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
    }

    @Transactional
    public List<RdmsJobItem> getJobitemListByProjectIdAndJobType(String projectId, String jobType) {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andProjectIdEqualTo(projectId).andDeletedEqualTo(0).andTypeEqualTo(jobType);
        return rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
    }

    @Transactional
    public List<RdmsJobItem> getJobitemListByProjectIdAndStatusAndJobType(String projectId, String status, String jobType) {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andProjectIdEqualTo(projectId).andDeletedEqualTo(0).andTypeEqualTo(jobType).andStatusEqualTo(status);
        return rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
    }

    @Transactional
    public List<RdmsJobItem> getJobitemListByCharacterIdAndJobType(String characterId, String jobType) {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andCharacterIdEqualTo(characterId).andDeletedEqualTo(0).andTypeEqualTo(jobType);
        return rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
    }

    @Transactional
    public List<RdmsJobItem> getJobitemListByCharacterIdAndStatusAndJobType(String characterId, String status, String jobType) {
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();
        rdmsJobItemExample.setOrderByClause("create_time desc");
        RdmsJobItemExample.Criteria criteria1 = rdmsJobItemExample.createCriteria();
        criteria1.andCharacterIdEqualTo(characterId).andDeletedEqualTo(0).andTypeEqualTo(jobType).andStatusEqualTo(status);
        return rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
    }

    /**
     * 根据loginUserID进行分页列表查询
     */
    @Transactional
    public List<RdmsJobItem> listByCharacterId(String characterId) {
        RdmsJobItemExample rdmsJobItemsExample = new RdmsJobItemExample();
        rdmsJobItemsExample.createCriteria().andCharacterIdEqualTo(characterId).andDeletedEqualTo(0);
        return rdmsJobItemsMapper.selectByExample(rdmsJobItemsExample);
    }

    @Transactional
    public List<RdmsJobItem> getItemByCharacterIdAndType(String characterId, JobItemTypeEnum jobItemType) {
        RdmsJobItemExample jobItemsExample = new RdmsJobItemExample();
        jobItemsExample.createCriteria().andCharacterIdEqualTo(characterId).andTypeEqualTo(jobItemType.getType());
        return rdmsJobItemsMapper.selectByExample(jobItemsExample);
    }

    @Transactional
    public long getExamineItemsNum(String customerId, String preProjectId, String userId) {
        RdmsJobItemExample jobItemsExample = new RdmsJobItemExample();
        jobItemsExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andPreProjectIdEqualTo(preProjectId)
                .andNextNodeEqualTo(userId)
                .andStatusEqualTo(JobItemStatusEnum.SUBMIT.getStatus())
                .andProjectTypeEqualTo(ProjectTypeEnum.PRE_PROJECT.getType())
                .andDeletedEqualTo(0);
        return rdmsJobItemsMapper.countByExample(jobItemsExample);
    }

    @Transactional
    public long getSystemExamineItemsNum(String customerId, String subprojectId, String userId) {
        RdmsJobItemExample jobItemsExample = new RdmsJobItemExample();
        jobItemsExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andSubprojectIdEqualTo(subprojectId)
                .andNextNodeEqualTo(userId)
                .andStatusEqualTo(JobItemStatusEnum.SUBMIT.getStatus())
                .andProjectTypeEqualTo(ProjectTypeEnum.DEV_PROJECT.getType())
                .andDeletedEqualTo(0);
        return rdmsJobItemsMapper.countByExample(jobItemsExample);
    }

    @Transactional
    public long getQualityExamineItemsNum(String customerId, String qualityId, String userId) {
        RdmsJobItemExample jobItemsExample = new RdmsJobItemExample();
        jobItemsExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andQualityIdEqualTo(qualityId)
                .andNextNodeEqualTo(userId)
                .andStatusEqualTo(JobItemStatusEnum.SUBMIT.getStatus())
                .andTypeEqualTo(JobItemTypeEnum.QUALITY.getType())
                .andProjectTypeEqualTo(ProjectTypeEnum.SUB_PROJECT.getType())
                .andDeletedEqualTo(0);
        return rdmsJobItemsMapper.countByExample(jobItemsExample);
    }

    @Transactional
    public long getEvaluateItemsNum(String customerId, String preProjectId, String userId) {
        RdmsJobItemExample jobItemsExample = new RdmsJobItemExample();
        jobItemsExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andPreProjectIdEqualTo(preProjectId)
                .andCreaterIdEqualTo(userId)
                .andStatusEqualTo(JobItemStatusEnum.EVALUATE.getStatus())
                .andProjectTypeEqualTo(ProjectTypeEnum.PRE_PROJECT.getType())
                .andDeletedEqualTo(0);
        return rdmsJobItemsMapper.countByExample(jobItemsExample);
    }

    @Transactional
    public long getRefuseItemsNum(String customerId, String preProjectId, String userId) {
        RdmsJobItemExample jobItemsExample = new RdmsJobItemExample();
        jobItemsExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andPreProjectIdEqualTo(preProjectId)
                .andNextNodeEqualTo(userId)
                .andStatusEqualTo(JobItemStatusEnum.REFUSE.getStatus())
                .andProjectTypeEqualTo(ProjectTypeEnum.PRE_PROJECT.getType())
                .andDeletedEqualTo(0);
        return rdmsJobItemsMapper.countByExample(jobItemsExample);
    }

    @Transactional
    public long getRefuseNum(String customerId, String preProjectId, String userId) {
        RdmsJobItemExample jobItemsExample = new RdmsJobItemExample();
        jobItemsExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andPreProjectIdEqualTo(preProjectId)
                .andNextNodeEqualTo(userId)
                .andStatusEqualTo(JobItemStatusEnum.REFUSE.getStatus())
                .andProjectTypeEqualTo(ProjectTypeEnum.PRE_PROJECT.getType())
                .andDeletedEqualTo(0);
        return rdmsJobItemsMapper.countByExample(jobItemsExample);
    }

    @Transactional
    public long getEvaluateNumBySubprojectId(String customerId, String subprojectId, String status, String type) {
        RdmsJobItemExample jobItemsExample = new RdmsJobItemExample();
        jobItemsExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andSubprojectIdEqualTo(subprojectId)
                .andStatusEqualTo(status)
                .andTypeEqualTo(type)
                .andDeletedEqualTo(0);
        return rdmsJobItemsMapper.countByExample(jobItemsExample);
    }

    @Transactional
    public long getHandlingByPreProjectId(String customerId, String preProjectId, List<JobItemTypeEnum> jobitemTypeList) {
        if (!CollectionUtils.isEmpty(jobitemTypeList)) {
            for (JobItemTypeEnum typeEnum : jobitemTypeList) {
                if (!ObjectUtils.isEmpty(typeEnum.getType())) {
                    JobItemTypeEnum jobItemTypeEnumByType = JobItemTypeEnum.getJobItemTypeEnumByType(typeEnum.getType());
                    if (ObjectUtils.isEmpty(jobItemTypeEnumByType)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }
        RdmsJobItemExample jobItemsExample = new RdmsJobItemExample();
        RdmsJobItemExample.Criteria criteria = jobItemsExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andPreProjectIdEqualTo(preProjectId)
                .andStatusEqualTo(JobItemStatusEnum.HANDLING.getStatus())
                .andDeletedEqualTo(0);

        List<String> typeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jobitemTypeList)) {
            for (JobItemTypeEnum typeEnum : jobitemTypeList) {
                typeList.add(typeEnum.getType());
            }
            if (!CollectionUtils.isEmpty(typeList)) {
                criteria.andTypeIn(typeList);
            }
        }
        return rdmsJobItemsMapper.countByExample(jobItemsExample);
    }

    @Transactional
    public long getExamineNumByPreProjectId(String customerId, String preProjectId, List<JobItemTypeEnum> jobitemTypeList) {
        if (!CollectionUtils.isEmpty(jobitemTypeList)) {
            for (JobItemTypeEnum typeEnum : jobitemTypeList) {
                if (!ObjectUtils.isEmpty(typeEnum.getType())) {
                    JobItemTypeEnum jobItemTypeEnumByType = JobItemTypeEnum.getJobItemTypeEnumByType(typeEnum.getType());
                    if (ObjectUtils.isEmpty(jobItemTypeEnumByType)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }
        RdmsJobItemExample jobItemsExample = new RdmsJobItemExample();
        RdmsJobItemExample.Criteria criteria = jobItemsExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andPreProjectIdEqualTo(preProjectId)
                .andStatusEqualTo(JobItemStatusEnum.SUBMIT.getStatus())
                .andProjectTypeEqualTo(ProjectTypeEnum.PRE_PROJECT.getType())
                .andDeletedEqualTo(0);

        List<String> typeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jobitemTypeList)) {
            for (JobItemTypeEnum typeEnum : jobitemTypeList) {
                typeList.add(typeEnum.getType());
            }
            if (!CollectionUtils.isEmpty(typeList)) {
                criteria.andTypeIn(typeList);
            }
        }
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(jobItemsExample);
        if(!CollectionUtils.isEmpty(rdmsJobItems)){
            return rdmsJobItems.size();
        }else{
            return 0;
        }
    }

    @Transactional
    public long getEvaluateNumByPreProjectId(String customerId, String preProjectId, String createrId, String status, List<JobItemTypeEnum> jobitemTypeList) {
        if (!CollectionUtils.isEmpty(jobitemTypeList)) {
            for (JobItemTypeEnum typeEnum : jobitemTypeList) {
                if (!ObjectUtils.isEmpty(typeEnum.getType())) {
                    JobItemTypeEnum jobItemTypeEnumByType = JobItemTypeEnum.getJobItemTypeEnumByType(typeEnum.getType());
                    if (ObjectUtils.isEmpty(jobItemTypeEnumByType)) {
                        throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
                    }
                }
            }
        }
        RdmsJobItemExample jobItemsExample = new RdmsJobItemExample();
        RdmsJobItemExample.Criteria criteria = jobItemsExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andPreProjectIdEqualTo(preProjectId)
                .andStatusEqualTo(status)
                .andCreaterIdEqualTo(createrId)
                .andDeletedEqualTo(0);

        List<String> typeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jobitemTypeList)) {
            for (JobItemTypeEnum typeEnum : jobitemTypeList) {
                typeList.add(typeEnum.getType());
            }
            if (!CollectionUtils.isEmpty(typeList)) {
                criteria.andTypeIn(typeList);
            }
        }
        return rdmsJobItemsMapper.countByExample(jobItemsExample);
    }

    @Transactional
    public long getTestManageJobNumByUserId(String userId) {
        List<RdmsProjectSubprojectDto> subprojectListByTM = rdmsSubprojectService.getSubprojectListByTM(userId);
        List<String> subprojectIdList = subprojectListByTM.stream().map(RdmsProjectSubprojectDto::getId).collect(Collectors.toList());
        long num_1 = 0L;
        long num_2 = 0L;
        if (!CollectionUtils.isEmpty(subprojectIdList)) {
            RdmsJobItemExample jobItemsExample = new RdmsJobItemExample();
            jobItemsExample.createCriteria()
                    .andSubprojectIdIn(subprojectIdList)
                    .andStatusEqualTo(JobItemStatusEnum.SUBMIT.getStatus())
                    .andTypeEqualTo(JobItemTypeEnum.TEST.getType())
                    .andDeletedEqualTo(0);
            num_1 = rdmsJobItemsMapper.countByExample(jobItemsExample);

            RdmsJobItemExample jobItemsExample2 = new RdmsJobItemExample();
            jobItemsExample2.createCriteria()
                    .andSubprojectIdIn(subprojectIdList)
                    .andStatusEqualTo(JobItemStatusEnum.SUBMIT.getStatus())
                    .andTypeEqualTo(JobItemTypeEnum.TASK_TEST.getType())
                    .andDeletedEqualTo(0);
            num_2 = rdmsJobItemsMapper.countByExample(jobItemsExample2);
        }
        long num_3 = rdmsBugFeedbackService.getBugFeedbackNumByUserId(userId);

        return  num_1 + num_2 + num_3;
    }

    @Transactional
    public long getTestJobNumByUserId(String userId) {
        List<RdmsProjectSubprojectDto> subprojectListByTM = rdmsSubprojectService.getSubprojectListByTM(userId);
        List<String> subprojectIdList = subprojectListByTM.stream().map(RdmsProjectSubprojectDto::getId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(subprojectIdList)) {
            RdmsJobItemExample jobItemsExample = new RdmsJobItemExample();
            jobItemsExample.createCriteria()
                    .andSubprojectIdIn(subprojectIdList)
                    .andStatusEqualTo(JobItemStatusEnum.SUBMIT.getStatus())
                    .andTypeEqualTo(JobItemTypeEnum.TASK_TEST.getType())
                    .andDeletedEqualTo(0);
//            List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(jobItemsExample);
//            return rdmsJobItems.size();
            return rdmsJobItemsMapper.countByExample(jobItemsExample);
        }
        return 0L;
    }

    @Transactional
    public long getFunctionManageJobNumByUserId(String userId) {
        //获得公司所有处于提交状态的DEMAND工单
        RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(userId);
        RdmsSysgmDto sysgmByCustomerId = rdmsSysgmService.getSysgmByCustomerId(rdmsCustomerUser.getCustomerId());

        List<JobItemStatusEnum> statusList = new ArrayList<>();
        statusList.add(JobItemStatusEnum.SUBMIT);

        List<JobItemTypeEnum> jobTypeList = new ArrayList<>();
        jobTypeList.add(JobItemTypeEnum.DEMAND);
        jobTypeList.add(JobItemTypeEnum.ITERATION);
        jobTypeList.add(JobItemTypeEnum.DECOMPOSE);
        jobTypeList.add(JobItemTypeEnum.TASK_FUNCTION);
        jobTypeList.add(JobItemTypeEnum.MERGE);
        jobTypeList.add(JobItemTypeEnum.UPDATE);

        List<RdmsJobItemDto> jobitemList = new ArrayList<>();
        if (!ObjectUtils.isEmpty(sysgmByCustomerId)) {
            jobitemList = this.getJobitemListByUserIdAndJobTypeAndStatusList(rdmsCustomerUser.getCustomerId(), userId, jobTypeList, statusList, ProjectTypeEnum.PRE_PROJECT);
            /*if(sysgmByCustomerId.getSysgmId().equals(userId)) {
                jobitemList = this.getJobitemListByCustomerIdAndJobTypeAndStatusList(rdmsCustomerUser.getCustomerId(), jobTypeList, statusList, ProjectTypeEnum.PRE_PROJECT);
            }else{
            }*/
        }

        Integer materialAndFeeApplicationNum = rdmsPreProjectService.getMaterialAndFeeApplicationNum(userId);

        if (!CollectionUtils.isEmpty(jobitemList)) {
            return jobitemList.size() + materialAndFeeApplicationNum;
        } else {
            return materialAndFeeApplicationNum;
        }
    }

    @Transactional
    public Integer getRefuseNumByUserId(String userId) {
        RdmsJobItemExample jobItemsExample = new RdmsJobItemExample();
        jobItemsExample.createCriteria()
                .andNextNodeEqualTo(userId)
                .andStatusEqualTo(JobItemStatusEnum.REFUSE.getStatus())
                .andProjectTypeEqualTo(ProjectTypeEnum.PRE_PROJECT.getType())
                .andDeletedEqualTo(0);
        return (Integer) (int) rdmsJobItemsMapper.countByExample(jobItemsExample);
    }

    @Transactional
    public long getSystemEvaluateItemsNum(String customerId, String subprojectId, String userId) {
        RdmsJobItemExample jobItemsExample = new RdmsJobItemExample();
        jobItemsExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andSubprojectIdEqualTo(subprojectId)
                .andNextNodeEqualTo(userId)
                .andStatusEqualTo(JobItemStatusEnum.EVALUATE.getStatus())
                .andProjectTypeEqualTo(ProjectTypeEnum.DEV_PROJECT.getType())
                .andDeletedEqualTo(0);
        return rdmsJobItemsMapper.countByExample(jobItemsExample);
    }

    @Transactional
    public long getCountOfCompleteJobitemBySubproject(String subprojectId) {
        RdmsJobItemExample jobItemsExample = new RdmsJobItemExample();
        List<String> statusList = new ArrayList<>();
        statusList.add(JobItemStatusEnum.COMPLETED.getStatus());
        statusList.add(JobItemStatusEnum.ARCHIVED.getStatus());
        jobItemsExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andStatusIn(statusList)
                .andTypeNotEqualTo(JobItemTypeEnum.REVIEW_NOTIFY.getType())
                .andDeletedEqualTo(0);
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(jobItemsExample);
        return rdmsJobItemsMapper.countByExample(jobItemsExample);
    }

    @Transactional
    public long getCountOfJobitemBySubproject(String subprojectId) {
        RdmsJobItemExample jobItemsExample = new RdmsJobItemExample();
        List<String> statusList = new ArrayList<>();
        statusList.add(JobItemStatusEnum.CANCEL.getStatus());
        statusList.add(JobItemStatusEnum.REFUSE.getStatus());
        jobItemsExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andTypeNotEqualTo(JobItemTypeEnum.REVIEW_NOTIFY.getType())
                .andStatusNotIn(statusList)
                .andDeletedEqualTo(0);
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(jobItemsExample);
        return rdmsJobItemsMapper.countByExample(jobItemsExample);
    }

    @Transactional
    public RdmsHmiNotifyDto getCharacterJobNotify(RdmsHmiNotifyDto notifyDto) {
        RdmsJobItemExample jobItemsExample = new RdmsJobItemExample();
        RdmsJobItemExample.Criteria criteria = jobItemsExample.createCriteria().andDeletedEqualTo(0);
        if (!ObjectUtils.isEmpty(notifyDto.getCustomerId())) {
            criteria.andCustomerIdEqualTo(notifyDto.getCustomerId());
        }
        if (!ObjectUtils.isEmpty(notifyDto.getProjectId())) {
            criteria.andProjectIdEqualTo(notifyDto.getProjectId());
        }
        if (!ObjectUtils.isEmpty(notifyDto.getActorId())) {
            criteria.andNextNodeEqualTo(notifyDto.getActorId());
        }
        if (!ObjectUtils.isEmpty(notifyDto.getStatus())) {
            criteria.andStatusEqualTo(notifyDto.getStatus());
        }
        if (!ObjectUtils.isEmpty(notifyDto.getProjectType())) {
            criteria.andProjectTypeEqualTo(notifyDto.getProjectType());
        }
        long l = rdmsJobItemsMapper.countByExample(jobItemsExample);
        notifyDto.setCount((int) l);
        notifyDto.setFlag(l > 0);
        return notifyDto;
    }

    @Transactional
    public String getProcessStatus(String jobitemId) {
        return rdmsJobItemProcessService.getJobitemProcessStatus(jobitemId);
    }

    /**
     * 保存
     */
    @Transactional
    public String save(RdmsJobItem jobItem) {
        if (ObjectUtils.isEmpty(jobItem.getId())) {
            return this.insert(jobItem);
        } else {
            RdmsJobItem rdmsJobItems = rdmsJobItemsMapper.selectByPrimaryKey(jobItem.getId());
            if (!ObjectUtils.isEmpty(rdmsJobItems)) {
                jobItem.setDeleted(0);
                jobItem.setJobSerial(rdmsJobItems.getJobSerial());
                return this.update(jobItem);
            } else {
                return this.insert(jobItem);
            }
        }
    }

    @Transactional
    public void setJobitemInfoAtDemandApproveOrIteDecJobSubmit(String jobitemId) {
        List<RdmsCharacterDto> characterList = rdmsCharacterService.getCharacterListByJobitemId(jobitemId, null);

        List<CharacterStatusEnum> statusEnumList = new ArrayList<>();
        statusEnumList.add(CharacterStatusEnum.APPROVE);
        statusEnumList.add(CharacterStatusEnum.APPROVED);
        statusEnumList.add(CharacterStatusEnum.HISTORY);
        statusEnumList.add(CharacterStatusEnum.ITERATING);
        statusEnumList.add(CharacterStatusEnum.DECOMPOSED);
        //查看原工单下的所有Character, 如果所有的Character都已经处于approve或者iterating状态
        List<RdmsCharacterDto> characterListByJobitemIdAndStatusList = rdmsCharacterService.getCharacterListByJobitemIdAndStatusList(jobitemId, statusEnumList);

        List<String> examinePassCharacters = new ArrayList<>();
        List<String> examineIterationCharacters = new ArrayList<>();
        List<String> examineDecomposeCharacters = new ArrayList<>();

        if (!CollectionUtils.isEmpty(characterList)) {
            for (RdmsCharacterDto characterDto : characterList) {
                if (characterDto.getStatus().equals(CharacterStatusEnum.APPROVED.getStatus()) || characterDto.getStatus().equals(CharacterStatusEnum.APPROVE.getStatus())) {
                    examinePassCharacters.add(characterDto.getCharacterName());
                }
                if (characterDto.getStatus().equals(CharacterStatusEnum.ITERATING.getStatus())) {
                    examineIterationCharacters.add(characterDto.getCharacterName());
                }
                if (characterDto.getStatus().equals(CharacterStatusEnum.DECOMPOSED.getStatus())) {
                    examineDecomposeCharacters.add(characterDto.getCharacterName());
                }
            }
        }
        if (characterList.size() == characterListByJobitemIdAndStatusList.size()) {
            //如果有状态为 Approve 的记录 要将状态改为 approved
            {
                List<CharacterStatusEnum> statusList = new ArrayList<>();
                statusList.add(CharacterStatusEnum.APPROVE);
                //查看原工单下的所有Character, 如果所有的Character都已经处于approve或者iterating状态
                List<RdmsCharacterDto> approveCharacterList = rdmsCharacterService.getCharacterListByJobitemIdAndStatusList(jobitemId, statusList);
                if (!CollectionUtils.isEmpty(approveCharacterList)) {
                    for (RdmsCharacterDto characterDto : approveCharacterList) {
                        characterDto.setStatus(CharacterStatusEnum.APPROVED.getStatus());
                        rdmsCharacterService.update(CopyUtil.copy(characterDto, RdmsCharacter.class));
                    }
                }
            }

            //创建被迭代的Character对应的jobItem为评价转态
            RdmsJobItem jobItems = this.selectByPrimaryKey(jobitemId);
            if (!ObjectUtils.isEmpty(jobItems)) {
                jobItems.setStatus(JobItemStatusEnum.EVALUATE.getStatus());
                jobItems.setNextNode(jobItems.getProductManagerId());
                String jobItemOrigin = this.update(jobItems);

                //创建对应工单的process状态
                RdmsJobItemProcess jobItemProcessOrigin = new RdmsJobItemProcess();
                jobItemProcessOrigin.setJobItemId(jobItemOrigin);
                jobItemProcessOrigin.setExecutorId(jobItems.getExecutorId());
                long processCountOrigin = rdmsJobItemProcessService.getJobItemProcessCount(jobItemOrigin);
                jobItemProcessOrigin.setDeep((int) processCountOrigin);

                StringBuilder passCharacterStr = new StringBuilder("审批通过：");
                for (String characterName : examinePassCharacters) {
                    passCharacterStr.append("《").append(characterName).append("》");
                }
                StringBuilder iterationCharacterStr = new StringBuilder("被修订：");
                for (String characterName : examineIterationCharacters) {
                    iterationCharacterStr.append("《").append(characterName).append("》");
                }
                StringBuilder decomposeCharacterStr = new StringBuilder("功能分解：");
                for (String characterName : examineDecomposeCharacters) {
                    decomposeCharacterStr.append("《").append(characterName).append("》");
                }
                StringBuilder reStr = new StringBuilder("提交工作质量评价。");
                if (!CollectionUtils.isEmpty(examinePassCharacters)) {
                    reStr.append(passCharacterStr);
                }
                if (!CollectionUtils.isEmpty(examinePassCharacters) && !CollectionUtils.isEmpty(examineIterationCharacters)) {
                    reStr.append("; ").append(iterationCharacterStr);
                } else if (CollectionUtils.isEmpty(examinePassCharacters) && !CollectionUtils.isEmpty(examineIterationCharacters)) {
                    reStr.append(iterationCharacterStr);
                }
                if ((!CollectionUtils.isEmpty(examinePassCharacters) || !CollectionUtils.isEmpty(examineIterationCharacters)) && !CollectionUtils.isEmpty(examineDecomposeCharacters)) {
                    reStr.append("; ").append(decomposeCharacterStr);
                } else if ((CollectionUtils.isEmpty(examinePassCharacters) && CollectionUtils.isEmpty(examineIterationCharacters)) && !CollectionUtils.isEmpty(examineDecomposeCharacters)) {
                    reStr.append(decomposeCharacterStr);
                }

                jobItemProcessOrigin.setJobDescription(reStr.toString());
                jobItemProcessOrigin.setProcessStatus(JobItemProcessStatusEnum.EVALUATE.getStatus());
                jobItemProcessOrigin.setNextNode(jobItems.getExecutorId());
                jobItemProcessOrigin.setActualSubmissionTime(null);
                rdmsJobItemProcessService.save(jobItemProcessOrigin);
            }
        }
    }

    public boolean hasTestJobInSubproject(String subprojectId) {
        RdmsJobItemExample jobItemExample = new RdmsJobItemExample();
        jobItemExample.createCriteria().andSubprojectIdEqualTo(subprojectId).andStatusEqualTo(JobItemStatusEnum.TESTING.getStatus()).andDeletedEqualTo(0);
        return rdmsJobItemsMapper.countByExample(jobItemExample) > 0;
    }

    /**
     * 获得一个工单"记录"直接相关的所有文字及资料信息
     * 1. 本条工单信息
     * 1.1 任务描述及基本信息
     * 1.2 工单附件
     * 1.3 资产
     * 1.4 测试
     * 1.5 流程
     * 1.6 评审
     *
     * @param jobItemId
     * @return
     */

    @Transactional
    public RdmsJobItemDto getJobitemRecordDetailInfo(String jobItemId, String loginUserId) {
        RdmsJobItem jobItem = this.selectByPrimaryKey(jobItemId);
        RdmsJobItemDto recordDetailInfo = CopyUtil.copy(jobItem, RdmsJobItemDto.class);
        if (!ObjectUtils.isEmpty(jobItem)) {
            //显示工时和标准工时进行转换
            if (jobItem.getType().equals(JobItemTypeEnum.TEST.getType())) {
                Double workerManhour = rdmsManhourService.transformToWorkerManhour(recordDetailInfo.getManhour(), jobItem.getExecutorId(), jobItem.getCustomerId(), OperateTypeEnum.TEST);
                recordDetailInfo.setManhour(workerManhour);
            } else {
                Double workerManhour = rdmsManhourService.transformToWorkerManhour(recordDetailInfo.getManhour(), jobItem.getExecutorId(), jobItem.getCustomerId(), OperateTypeEnum.DEVELOP);
                recordDetailInfo.setManhour(workerManhour);
            }
            //1. 获取本条工单的记录信息
            if (!ObjectUtils.isEmpty(recordDetailInfo.getCustomerId())) {
                RdmsCustomer customer = rdmsCustomerService.selectByPrimaryKey(recordDetailInfo.getCustomerId());
                recordDetailInfo.setCustomerName(customer.getCustomerName());
            }
            if (!ObjectUtils.isEmpty(recordDetailInfo.getPreProjectId())) {
                RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(recordDetailInfo.getPreProjectId());
                if (!ObjectUtils.isEmpty(rdmsPreProject)) {
                    recordDetailInfo.setPreProjectName(rdmsPreProject.getPreProjectName());
                }
            }
            if (!ObjectUtils.isEmpty(recordDetailInfo.getProjectId())) {
                RdmsProject projectInfo = rdmsProjectService.selectByPrimaryKey(recordDetailInfo.getProjectId());
                if (!ObjectUtils.isEmpty(projectInfo)) {
                    recordDetailInfo.setProjectName(projectInfo.getProjectName());
                    recordDetailInfo.setRdType(projectInfo.getRdType());
                }
            }
            if (!ObjectUtils.isEmpty(recordDetailInfo.getSubprojectId())) {
                RdmsProjectSubproject subProjectInfo = rdmsSubprojectService.getSubProjectInfo(recordDetailInfo.getSubprojectId());
                if(!ObjectUtils.isEmpty(subProjectInfo)){
                    recordDetailInfo.setSubprojectName(subProjectInfo.getLabel());
                }

            }
            if (!ObjectUtils.isEmpty(recordDetailInfo.getCharacterId())) {
                RdmsCharacter character = rdmsCharacterService.selectByPrimaryKey(recordDetailInfo.getCharacterId());
                if(!ObjectUtils.isEmpty(character)){
                    recordDetailInfo.setCharacterName(character.getCharacterName());
                    RdmsCharacterDto copy = CopyUtil.copy(character, RdmsCharacterDto.class);
                    recordDetailInfo.setCharacterDto(copy);
                }
            }
            //..testedJobitem
            //..parentJobitem
            if (!ObjectUtils.isEmpty(recordDetailInfo.getProjectManagerId())) {
                RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(recordDetailInfo.getProjectManagerId());
                recordDetailInfo.setProjectManagerName(customerUser.getTrueName());
            }
            if (!ObjectUtils.isEmpty(recordDetailInfo.getProductManagerId())) {
                RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(recordDetailInfo.getProductManagerId());
                recordDetailInfo.setProductManagerName(customerUser.getTrueName());
            }
            //..taskDescription
            //..manhour
            if (!ObjectUtils.isEmpty(recordDetailInfo.getDemandIdStr()) && recordDetailInfo.getDemandIdStr().length() > 6) {
                List<String> stringList = JSON.parseArray(recordDetailInfo.getDemandIdStr(), String.class);
                List<RdmsDemand> demandList = rdmsDemandService.getDemandListByIdList(stringList);
                List<String> idList = demandList.stream().map(RdmsDemand::getId).collect(Collectors.toList());
                recordDetailInfo.setDemandIdList(idList);
                List<String> nameList = demandList.stream().map(RdmsDemand::getDemandName).collect(Collectors.toList());
                recordDetailInfo.setDemandNameList(nameList);

                List<RdmsDemandDto> rdmsDemandDtos = CopyUtil.copyList(demandList, RdmsDemandDto.class);
                for (RdmsDemandDto demandDto : rdmsDemandDtos) {
                    List<String> attachmentList = JSON.parseArray(demandDto.getFileListStr(), String.class);
                    if (!CollectionUtils.isEmpty(attachmentList)) {
                        List<RdmsFileDto> fileDtos = rdmsFileService.getFileListByIdList(attachmentList);
                        for (RdmsFileDto fileDto : fileDtos) {
                            if (!ObjectUtils.isEmpty(fileDto)) {
                                RdmsFileAuth byFileId = rdmsFileAuthService.getByFileId(fileDto.getId());
                                if (!(ObjectUtils.isEmpty(byFileId) || ObjectUtils.isEmpty(byFileId.getAuthIdsStr()))) {
                                    List<String> strings = JSON.parseArray(byFileId.getAuthIdsStr(), String.class);
                                    if (strings.contains(loginUserId)) {
                                        fileDto.setAuthStatus(true);
                                    } else {
                                        fileDto.setAuthStatus(false);
                                    }
                                } else {
                                    fileDto.setAuthStatus(true);
                                }
                            }
                        }
                        demandDto.setResFiles(fileDtos);
                    }
                    RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(demandDto.getPreProjectId());
                    demandDto.setPreProjectName(rdmsPreProject.getPreProjectName());

                    RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(demandDto.getProductManagerId());
                    demandDto.setProductManagerName(customerUser.getTrueName());

                    RdmsCustomerUser customerUser1 = rdmsCustomerUserService.selectByPrimaryKey(demandDto.getWriterId());
                    demandDto.setWriterName(customerUser1.getTrueName());
                }

                recordDetailInfo.setDemandDtoList(rdmsDemandDtos);
            }
            if (!ObjectUtils.isEmpty(recordDetailInfo.getFileListStr())) {
                List<String> fileIdList = JSON.parseArray(recordDetailInfo.getFileListStr(), String.class);
                List<RdmsFileDto> fileDtos = new ArrayList<>();
                for (String id : fileIdList) {
                    RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(id);
                    if (!ObjectUtils.isEmpty(fileDto)) {
                        RdmsFileAuth byFileId = rdmsFileAuthService.getByFileId(fileDto.getId());
                        if (!(ObjectUtils.isEmpty(byFileId) || ObjectUtils.isEmpty(byFileId.getAuthIdsStr()))) {
                            List<String> strings = JSON.parseArray(byFileId.getAuthIdsStr(), String.class);
                            if (strings.contains(loginUserId)) {
                                fileDto.setAuthStatus(true);
                            } else {
                                fileDto.setAuthStatus(false);
                            }
                        } else {
                            fileDto.setAuthStatus(true);
                        }
                    }
                    fileDtos.add(fileDto);
                }
                recordDetailInfo.setFileList(fileDtos);
            }
            //..createTime
            //..planSubmissionTime
            //..actualSubmissionTime
            if (!ObjectUtils.isEmpty(recordDetailInfo.getExecutorId())) {
                RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(recordDetailInfo.getExecutorId());
                recordDetailInfo.setExecutorName(customerUser.getTrueName());
            }
            //..nextNode
            //..status
            //..type
            //..projectType
            if (!ObjectUtils.isEmpty(recordDetailInfo.getPropertyId())) {
                RdmsJobItemProperty rdmsJobItemProperty = rdmsJobItemPropertyService.selectByPrimaryKey(recordDetailInfo.getPropertyId());
                RdmsJobItemPropertyDto propertyDto = CopyUtil.copy(rdmsJobItemProperty, RdmsJobItemPropertyDto.class);
                if (!ObjectUtils.isEmpty(propertyDto)) {
                    if (!ObjectUtils.isEmpty(propertyDto.getFileListStr())) {
                        List<RdmsFileDto> fileDtos = new ArrayList<>();
                        List<String> stringList = JSON.parseArray(propertyDto.getFileListStr(), String.class);
                        if (!CollectionUtils.isEmpty(stringList)) {
                            for (String fileId : stringList) {
                                RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(fileId);
                                if (!ObjectUtils.isEmpty(fileDto)) {
                                    RdmsFileAuth byFileId = rdmsFileAuthService.getByFileId(fileDto.getId());
                                    if (!(ObjectUtils.isEmpty(byFileId) || ObjectUtils.isEmpty(byFileId.getAuthIdsStr()))) {
                                        List<String> strings = JSON.parseArray(byFileId.getAuthIdsStr(), String.class);
                                        if (strings.contains(loginUserId)) {
                                            fileDto.setAuthStatus(true);
                                        } else {
                                            fileDto.setAuthStatus(false);
                                        }
                                    } else {
                                        fileDto.setAuthStatus(true);
                                    }
                                }
                                fileDtos.add(fileDto);
                            }
                        }
                        propertyDto.setFileItems(fileDtos);
                    }
                    if (!ObjectUtils.isEmpty(propertyDto.getReferenceFileListStr())) {
                        List<RdmsFileDto> fileDtos = new ArrayList<>();
                        List<String> stringList = JSON.parseArray(propertyDto.getReferenceFileListStr(), String.class);
                        if (!CollectionUtils.isEmpty(stringList)) {
                            for (String fileId : stringList) {
                                RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(fileId);
                                if (!ObjectUtils.isEmpty(fileDto)) {
                                    RdmsFileAuth byFileId = rdmsFileAuthService.getByFileId(fileDto.getId());
                                    if (!(ObjectUtils.isEmpty(byFileId) || ObjectUtils.isEmpty(byFileId.getAuthIdsStr()))) {
                                        List<String> strings = JSON.parseArray(byFileId.getAuthIdsStr(), String.class);
                                        if (strings.contains(loginUserId)) {
                                            fileDto.setAuthStatus(true);
                                        } else {
                                            fileDto.setAuthStatus(false);
                                        }
                                    } else {
                                        fileDto.setAuthStatus(true);
                                    }
                                }
                                fileDtos.add(fileDto);
                            }
                        }
                        propertyDto.setReferenceFileList(fileDtos);
                    }
                }
                recordDetailInfo.setPropertyDto(propertyDto);
            }
            //..reviewWorkerIdStr
            //..reviewResult
            //..evaluateDesc
            //..evaluateScore
            String processStatus = this.getProcessStatus(jobItemId);
            recordDetailInfo.setProcessStatus(processStatus);
            //end

            List<RdmsJobItemDto> jobitemListByParent = this.getJobitemByParentJobitemId(jobItemId, null);
            long count = jobitemListByParent.stream().filter(item -> !item.getStatus().equals(JobItemStatusEnum.COMPLETED.getStatus())).count();
            recordDetailInfo.setChildrenJobComplateFlag(count == 0);
        }

        return recordDetailInfo;
    }

    @Transactional
    public RdmsJobItemDto getJobitemSimpleInfo(String jobItemId, String loginUserId) {
        RdmsJobItem jobItem = this.selectByPrimaryKey(jobItemId);
        if (ObjectUtils.isEmpty(jobItem)) {
            LOG.error("getJobitemSimpleInfo jobItemId is not exist");
            throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
        }
        RdmsJobItemDto simpleInfoDto = CopyUtil.copy(jobItem, RdmsJobItemDto.class);
        if (!ObjectUtils.isEmpty(jobItem.getPreProjectId())) {
            RdmsSysgmDto sysgmByCustomerId = rdmsSysgmService.getSysgmByCustomerId(jobItem.getCustomerId());
            simpleInfoDto.setSysgmId(sysgmByCustomerId.getSysgmId());
        }

        if (!ObjectUtils.isEmpty(jobItem.getProjectId())) {
            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(jobItem.getProjectId());
            simpleInfoDto.setRdType(rdmsProject.getRdType());
        }

        //demandIdList
        if (!ObjectUtils.isEmpty(simpleInfoDto.getDemandIdStr()) && simpleInfoDto.getDemandIdStr().length() > 6) {
            List<String> stringList = JSON.parseArray(simpleInfoDto.getDemandIdStr(), String.class);
            simpleInfoDto.setDemandIdList(stringList);
        }
        //fileList
        if (!ObjectUtils.isEmpty(simpleInfoDto.getFileListStr())) {
            List<String> fileIdList = JSON.parseArray(simpleInfoDto.getFileListStr(), String.class);
            List<RdmsFileDto> fileDtos = new ArrayList<>();
            for (String id : fileIdList) {
                RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(id);
                fileDtos.add(fileDto);
            }
            simpleInfoDto.setFileList(fileDtos);
        }
        //assistIdList
        //1.4 协作工单信息
        List<RdmsJobItemDto> assistJobitemList = this.getJobitemListByParentJobitemId(jobItemId, JobItemStatusEnum.COMPLETED.getStatus(), JobItemTypeEnum.ASSIST.getType());
        if (!CollectionUtils.isEmpty(assistJobitemList)) {
            //测试工单ID list
            List<String> stringList = assistJobitemList.stream().map(RdmsJobItemDto::getId).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(stringList)) {
                simpleInfoDto.setAssistJobitemIdList(stringList);
            } else {
                simpleInfoDto.setAssistJobitemIdList(null);
            }
        }
        //testJobitemIdList
        //1.2 测试工单信息
        List<RdmsJobItemDto> testJobitemList = this.getJobitemByTestedJobitemId(jobItemId, loginUserId, JobItemTypeEnum.TEST);
        if (!CollectionUtils.isEmpty(testJobitemList)) {
            //测试工单ID list
            List<String> stringList = testJobitemList.stream().map(RdmsJobItemDto::getId).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(stringList)) {
                simpleInfoDto.setTestJobitemIdList(stringList);
            } else {
                simpleInfoDto.setTestJobitemIdList(null);
            }
        }
        //tested工单的类型
        if (simpleInfoDto.getTestedJobitemId() != null) {
            RdmsJobItem testedJobitem = this.selectByPrimaryKey(simpleInfoDto.getTestedJobitemId());
            simpleInfoDto.setTestedJobType(testedJobitem.getType());
        }

        //parent工单的类型
        if (simpleInfoDto.getParentJobitemId() != null) {
            RdmsJobItem parentJobitem = this.selectByPrimaryKey(simpleInfoDto.getParentJobitemId());
            simpleInfoDto.setParentJobType(parentJobitem.getType());
        }
        RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(simpleInfoDto.getExecutorId());
        simpleInfoDto.setExecutorName(rdmsCustomerUser.getTrueName());

        //将标准工时转换成个人工时
        if(!ObjectUtils.isEmpty(simpleInfoDto.getManhour())){
            OperateTypeEnum opeType = OperateTypeEnum.DEVELOP;
            if (!ObjectUtils.isEmpty(simpleInfoDto.getType()) && simpleInfoDto.getType().equals(JobItemTypeEnum.TEST.getType())) {
                opeType = OperateTypeEnum.TEST;
            }
            Double staffManhour = rdmsManhourService.transformToWorkerManhour(simpleInfoDto.getManhour(), simpleInfoDto.getExecutorId(), simpleInfoDto.getCustomerId(), opeType);
            simpleInfoDto.setManhour(staffManhour);
        }
        //查看是否有申报工时记录
        RdmsManhourDeclare rdmsManhourDeclare = rdmsManhourDeclareService.selectByPrimaryKey(jobItemId);
        if (!ObjectUtils.isEmpty(rdmsManhourDeclare)) {
            simpleInfoDto.setDeclareManhour(rdmsManhourDeclare.getManhour());
            simpleInfoDto.setDeclareReason(rdmsManhourDeclare.getReason());
        }

        return simpleInfoDto;
    }

    @Transactional
    public void appendRecordSimpleInfo(RdmsJobItemDto simpleInfoDto, String loginUserId) {
        //demandIdList
        if (!ObjectUtils.isEmpty(simpleInfoDto.getDemandIdStr()) && simpleInfoDto.getDemandIdStr().length() > 6) {
            List<String> stringList = JSON.parseArray(simpleInfoDto.getDemandIdStr(), String.class);
            simpleInfoDto.setDemandIdList(stringList);
        }
        //fileList
        if (!ObjectUtils.isEmpty(simpleInfoDto.getFileListStr())) {
            List<String> fileIdList = JSON.parseArray(simpleInfoDto.getFileListStr(), String.class);
            List<RdmsFileDto> fileDtos = new ArrayList<>();
            for (String id : fileIdList) {
                RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(id);
                //读取访问权限
                if (!ObjectUtils.isEmpty(fileDto)) {
                    //补充填写文件权限信息
                    RdmsFileAuth byFileId = rdmsFileAuthService.getByFileId(fileDto.getId());
                    if (ObjectUtils.isEmpty(byFileId)) {
                        fileDto.setAuthStatus(true);
                    } else {
                        String authIdsStr = byFileId.getAuthIdsStr();
                        if (ObjectUtils.isEmpty(authIdsStr)) {
                            fileDto.setAuthStatus(true);
                        } else {
                            List<String> strings = JSON.parseArray(authIdsStr, String.class);
                            if (strings.contains(loginUserId)) {
                                fileDto.setAuthStatus(true);
                            } else {
                                fileDto.setAuthStatus(false);
                            }
                        }
                    }
                }
                fileDtos.add(fileDto);
            }
            simpleInfoDto.setFileList(fileDtos);
        }
        //assistIdList
        //1.4 协作工单信息
        List<RdmsJobItemDto> assistJobitemList = this.getJobitemListByParentJobitemId(simpleInfoDto.getId(), JobItemStatusEnum.COMPLETED.getStatus(), JobItemTypeEnum.ASSIST.getType());
        if (!CollectionUtils.isEmpty(assistJobitemList)) {
            //测试工单ID list
            List<String> stringList = assistJobitemList.stream().map(RdmsJobItemDto::getId).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(stringList)) {
                simpleInfoDto.setAssistJobitemIdList(stringList);
            } else {
                simpleInfoDto.setAssistJobitemIdList(null);
            }
        }
        //testJobitemIdList
        //1.2 测试工单信息
        List<RdmsJobItemDto> testJobitemList = this.getJobitemByTestedJobitemId(simpleInfoDto.getId(), loginUserId, JobItemTypeEnum.TEST);
        if (!CollectionUtils.isEmpty(testJobitemList)) {
            //测试工单ID list
            List<String> stringList = testJobitemList.stream().map(RdmsJobItemDto::getId).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(stringList)) {
                simpleInfoDto.setTestJobitemIdList(stringList);
            } else {
                simpleInfoDto.setTestJobitemIdList(null);
            }
        }
        //tested工单的类型
        if (simpleInfoDto.getTestedJobitemId() != null) {
            RdmsJobItem testedJobitem = this.selectByPrimaryKey(simpleInfoDto.getTestedJobitemId());
            simpleInfoDto.setTestedJobType(testedJobitem.getType());
        }

        //parent工单的类型
        if (!ObjectUtils.isEmpty(simpleInfoDto.getParentJobitemId())) {
            RdmsJobItem parentJobitem = this.selectByPrimaryKey(simpleInfoDto.getParentJobitemId());
            if (!ObjectUtils.isEmpty(parentJobitem)) {
                simpleInfoDto.setParentJobType(parentJobitem.getType());
            }
        }
        RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(simpleInfoDto.getExecutorId());
        simpleInfoDto.setExecutorName(rdmsCustomerUser.getTrueName());

        RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(simpleInfoDto.getCharacterId());
        simpleInfoDto.setCharacterName(rdmsCharacter.getCharacterName());
        RdmsCustomerUser rdmsCustomerUser2 = rdmsCustomerUserService.selectByPrimaryKey(simpleInfoDto.getProjectManagerId());
        simpleInfoDto.setProjectManagerName(rdmsCustomerUser2.getTrueName());
        RdmsCustomerUser rdmsCustomerUser1 = rdmsCustomerUserService.selectByPrimaryKey(simpleInfoDto.getProductManagerId());
        simpleInfoDto.setProductManagerName(rdmsCustomerUser1.getTrueName());
    }

    @Transactional
    public void completeReCheckJobitem(String jobItemId) {
        RdmsJobItem jobItem = this.selectByPrimaryKey(jobItemId);
        if (ObjectUtils.isEmpty(jobItem)) {
            throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
        }
        jobItem.setTaskDescription("系统完成标记");
        jobItem.setStatus(JobItemStatusEnum.COMPLETED.getStatus());
        jobItem.setCompleteTime(new Date());
        jobItem.setNextNode(null);
        rdmsJobItemsMapper.updateByPrimaryKey(jobItem);

        //写一条Process记录
        RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
        jobItemProcess.setJobItemId(jobItem.getId());
        jobItemProcess.setExecutorId(null);
        jobItemProcess.setNextNode(jobItem.getExecutorId());
        long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobItemId);
        jobItemProcess.setDeep((int) jobItemProcessCount);
        jobItemProcess.setJobDescription("系统完成标记");
        jobItemProcess.setFileListStr(null);
        jobItemProcess.setActualSubmissionTime(null);
        jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.COMPLETE.getStatus());
        rdmsJobItemProcessService.save(jobItemProcess);
    }

    /**
     * 获得一个工单直接相关的所有文字及资料信息
     * 1. 本条工单信息
     * 1.1 任务描述及基本信息 含: 工单附件 资产信息
     * 1.2 测试
     * 1.3 评审  包括: 补充提交的材料 有条件通过工单材料 协同评审材料
     * 1.4 协作
     * 1.5 流程
     * 1.6 协作
     *
     * @param jobItemId
     * @return
     */
    @Transactional
    public RdmsJobItemDto getJobitemDetailInfo(String jobItemId, String loginUserId) {
        //1.1 任务描述及基本信息
        RdmsJobItemDto jobitemDetailInfo = this.getJobitemRecordDetailInfo(jobItemId, loginUserId);

        //添加评审组长姓名
        if (jobitemDetailInfo.getType().equals(JobItemTypeEnum.REVIEW.getType())
                || jobitemDetailInfo.getType().equals(JobItemTypeEnum.REVIEW_SUBP.getType())
                || jobitemDetailInfo.getType().equals(JobItemTypeEnum.REVIEW_MILESTONE.getType())) {
            if (!ObjectUtils.isEmpty(jobitemDetailInfo.getNextNode())) {
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(jobitemDetailInfo.getNextNode());
                jobitemDetailInfo.setReviewGroupLeaderName(rdmsCustomerUser.getTrueName());
            }
        } else if (jobitemDetailInfo.getType().equals(JobItemTypeEnum.REVIEW_COO.getType())) {
            RdmsJobItem rdmsJobItem = this.selectByPrimaryKey(jobitemDetailInfo.getTestedJobitemId());
            if (!ObjectUtils.isEmpty(rdmsJobItem.getNextNode())) {
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(rdmsJobItem.getNextNode());
                jobitemDetailInfo.setReviewGroupLeaderName(rdmsCustomerUser.getTrueName());
            }
        }

        //1.2 测试工单信息
        List<RdmsJobItemDto> testJobitemList = this.getJobitemByTestedJobitemId(jobItemId, loginUserId, JobItemTypeEnum.TEST);
        if (!CollectionUtils.isEmpty(testJobitemList)) {
            for (RdmsJobItemDto jobItemDto : testJobitemList) {
                RdmsJobItemDto testJobitemRecordDetailInfo = this.getJobitemRecordDetailInfo(jobItemDto.getId(), loginUserId);
                if (!ObjectUtils.isEmpty(testJobitemRecordDetailInfo)) {
                    if (CollectionUtils.isEmpty(jobitemDetailInfo.getTestJobitemList())) {
                        List<RdmsJobItemDto> jobitemList = new ArrayList<>();
                        jobitemList.add(testJobitemRecordDetailInfo);
                        jobitemDetailInfo.setTestJobitemList(jobitemList);
                    } else {
                        jobitemDetailInfo.getTestJobitemList().add(testJobitemRecordDetailInfo);
                    }
                }
            }
            //测试工单ID list
            List<String> stringList = testJobitemList.stream().map(RdmsJobItemDto::getId).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(stringList)) {
                jobitemDetailInfo.setTestJobitemIdList(stringList);
            } else {
                jobitemDetailInfo.setTestJobitemIdList(null);
            }
        }

        //1.3 评审工单信息
        List<RdmsJobItemDto> reviewJobitemList = this.getJobitemByTestedJobitemId(jobItemId, loginUserId, JobItemTypeEnum.REVIEW);
        if (!CollectionUtils.isEmpty(reviewJobitemList)) {
            for (RdmsJobItemDto reviewJobItemDto : reviewJobitemList) {
                RdmsJobItemDto reviewJobitemRecordDetailInfo = this.getJobitemRecordDetailInfo(reviewJobItemDto.getId(), loginUserId);
                if (!ObjectUtils.isEmpty(reviewJobitemRecordDetailInfo)) {
                    //添加 补充材料工单内容
                    List<RdmsJobItemDto> supplementDtos = this.getReviewJobitemListByParentJobitemIdAndResultEnum(reviewJobItemDto.getId(), ReviewResultEnum.SUPPLEMENT);
                    List<RdmsJobItemDto> tempSupplementDto = new ArrayList<>();
                    if (!CollectionUtils.isEmpty(supplementDtos)) {
                        for (RdmsJobItemDto jobItemDto : supplementDtos) {
                            tempSupplementDto.add(this.getJobitemRecordDetailInfo(jobItemDto.getId(), loginUserId));
                        }
                    }
                    reviewJobitemRecordDetailInfo.setReviewSupplementJobitemList(tempSupplementDto);

                    //添加 有条件通过工单内容
                    List<RdmsJobItemDto> conditionalDtos = this.getReviewJobitemListByParentJobitemIdAndResultEnum(reviewJobItemDto.getId(), ReviewResultEnum.CONDITIONAL);
                    List<RdmsJobItemDto> tempDto = new ArrayList<>();
                    if (!CollectionUtils.isEmpty(conditionalDtos)) {
                        for (RdmsJobItemDto jobItemDto : conditionalDtos) {
                            tempDto.add(this.getJobitemRecordDetailInfo(jobItemDto.getId(), loginUserId));
                        }
                    }
                    reviewJobitemRecordDetailInfo.setReviewConditionalJobitemList(tempDto);

                    //添加 联合评审工单信息
                    List<RdmsJobItemDto> reviewCooJobitemList = this.getJobitemByTestedJobitemId(reviewJobItemDto.getId(), loginUserId, JobItemTypeEnum.REVIEW_COO);
                    List<RdmsJobItemDto> tempReviewCooList = new ArrayList<>();
                    if (!CollectionUtils.isEmpty(reviewCooJobitemList)) {
                        for (RdmsJobItemDto jobItemDto : reviewCooJobitemList) {
                            tempReviewCooList.add(this.getJobitemRecordDetailInfo(jobItemDto.getId(), loginUserId));
                        }
                    }
                    reviewJobitemRecordDetailInfo.setReviewCooJobitemList(tempReviewCooList);

                    //添加 包含 协同评审 补充材料 有条件通过 工单信息的 评审工单信息
                    if (CollectionUtils.isEmpty(jobitemDetailInfo.getReviewJobitemList())) {
                        List<RdmsJobItemDto> jobitemList = new ArrayList<>();
                        jobitemList.add(reviewJobitemRecordDetailInfo);
                        jobitemDetailInfo.setReviewJobitemList(jobitemList);
                    } else {
                        jobitemDetailInfo.getReviewJobitemList().add(reviewJobitemRecordDetailInfo);
                    }
                }
            }
        }
        //1.4 协作工单信息
        List<RdmsJobItemDto> assistJobitemList = this.getJobitemListByParentJobitemId(jobItemId, null, JobItemTypeEnum.ASSIST.getType());
        if (!CollectionUtils.isEmpty(assistJobitemList)) {
            for (RdmsJobItemDto jobItemDto : assistJobitemList) {
                RdmsJobItemDto assistJobitemRecordDetailInfo = this.getJobitemRecordDetailInfo(jobItemDto.getId(), loginUserId);
                if (!ObjectUtils.isEmpty(assistJobitemRecordDetailInfo)) {
                    if (CollectionUtils.isEmpty(jobitemDetailInfo.getAssistJobitemList())) {
                        List<RdmsJobItemDto> jobitemList = new ArrayList<>();
                        jobitemList.add(assistJobitemRecordDetailInfo);
                        jobitemDetailInfo.setAssistJobitemList(jobitemList);
                    } else {
                        jobitemDetailInfo.getAssistJobitemList().add(assistJobitemRecordDetailInfo);
                    }
                }
            }
        }
        //1.5 流程工单信息
        List<RdmsJobItemProcess> jobProcessList = rdmsJobItemProcessService.getJobProcessListByJobId(jobItemId);
        List<RdmsJobItemProcessDto> processDtos = CopyUtil.copyList(jobProcessList, RdmsJobItemProcessDto.class);
        if (!CollectionUtils.isEmpty(processDtos)) {
            List<RdmsFileDto> fileDtos = new ArrayList<>();
            for (RdmsJobItemProcessDto processDto : processDtos) {
                List<String> stringList = JSON.parseArray(processDto.getFileListStr(), String.class);
                if (!CollectionUtils.isEmpty(stringList)) {
                    for (String fileId : stringList) {
                        RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(fileId);
                        fileDtos.add(fileDto);
                    }
                }
                processDto.setFileList(fileDtos);
            }
            jobitemDetailInfo.setJobitemProcessList(processDtos);
        }

        //为tab列表页提供信息
        List<RdmsCharacterDto> characterList = rdmsCharacterService.getCharacterListByJobItemIdAndStatus(jobItemId, CharacterStatusEnum.SUBMIT.getStatus());
        if (!CollectionUtils.isEmpty(characterList)) {
            jobitemDetailInfo.setChildren(characterList);
        }
        List<RdmsHmiCharacterPlainDto> characterPlainDtos = characterList.stream().map(e -> new RdmsHmiCharacterPlainDto(e.getId(), e.getCharacterName())).collect(Collectors.toList());
        jobitemDetailInfo.setCharacterPlainDtos(characterPlainDtos);

        String processStatus = this.getProcessStatus(jobitemDetailInfo.getId());
        jobitemDetailInfo.setProcessStatus(processStatus);

        List<RdmsJobItemDto> jobitemListByParent = this.getJobitemByParentJobitemId(jobItemId, null);
        long count = jobitemListByParent.stream().filter(item -> !item.getStatus().equals(JobItemStatusEnum.COMPLETED.getStatus())).count();
        jobitemDetailInfo.setChildrenJobComplateFlag(count == 0);

        return jobitemDetailInfo;
    }

    /**
     * 获得一个测试工单相关信息用于工作辅助
     * 1. 被测工单信息
     * 1.1 任务描述  工作描述  工单交付件
     * 2. Bug工单列表
     * 2.1 任务描述  工作描述  工单交付件
     *
     * @param jobItemId
     * @return
     */
    @Transactional
    public RdmsHmiTestJobitemAuxPlainDto getTestJobitemAuxPlainInfo(String jobItemId, String loginUserId) {
        RdmsHmiTestJobitemAuxPlainDto auxPlainDto = new RdmsHmiTestJobitemAuxPlainDto();

        //1. 被测工单信息
        RdmsJobItem testJobitem = this.selectByPrimaryKey(jobItemId);
        //被测工单的记录信息
        RdmsJobItemDto testedJobitemRecordInfo = this.getJobitemRecordDetailInfo(testJobitem.getTestedJobitemId(), loginUserId);

        auxPlainDto.setId(jobItemId);
        auxPlainDto.setTestedId(testedJobitemRecordInfo.getId());
        auxPlainDto.setTestedJobSerial(testedJobitemRecordInfo.getJobSerial());
        auxPlainDto.setTestedJobName(testedJobitemRecordInfo.getJobName());
        auxPlainDto.setDemandDtoList(testedJobitemRecordInfo.getDemandDtoList());
        auxPlainDto.setCharacterDto(testedJobitemRecordInfo.getCharacterDto());
        auxPlainDto.setTestedTaskDescription(testedJobitemRecordInfo.getTaskDescription());
        auxPlainDto.setTestedFileList(testedJobitemRecordInfo.getFileList());
        auxPlainDto.setTestedPropertyDto(testedJobitemRecordInfo.getPropertyDto());

        //Bug工单信息
        List<RdmsJobItemDto> bugJobitemList = this.getJobitemListByParentJobitemId(jobItemId, JobItemStatusEnum.COMPLETED.getStatus(), JobItemTypeEnum.BUG.getType());
        List<RdmsJobItemDto> bugJobitemDtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(bugJobitemList)) {
            for (RdmsJobItemDto jobItemDto : bugJobitemList) {
                RdmsJobItemDto jobitemRecordDetailInfo = this.getJobitemRecordDetailInfo(jobItemDto.getId(), loginUserId);
                bugJobitemDtoList.add(jobitemRecordDetailInfo);
            }
            auxPlainDto.setBugJobitemList(bugJobitemDtoList);
        }
        return auxPlainDto;
    }

    @Transactional
    public RdmsJobItemDto getReviewJobitemDetailInfo(String jobItemId, String loginUserId) {
        //1.1 任务描述及基本信息
        RdmsJobItemDto jobitemDetailInfo = this.getJobitemRecordDetailInfo(jobItemId, loginUserId);

        if (!ObjectUtils.isEmpty(jobitemDetailInfo)) {
            //添加 补充材料工单内容
            List<RdmsJobItemDto> supplementDtos = this.getReviewJobitemListByParentJobitemIdAndResultEnum(jobItemId, ReviewResultEnum.SUPPLEMENT);
            List<RdmsJobItemDto> tempSupplementDto = new ArrayList<>();
            if (!CollectionUtils.isEmpty(supplementDtos)) {
                for (RdmsJobItemDto jobItemDto : supplementDtos) {
                    tempSupplementDto.add(this.getJobitemRecordDetailInfo(jobItemDto.getId(), loginUserId));
                }
            }
            jobitemDetailInfo.setReviewSupplementJobitemList(tempSupplementDto);

            //添加 有条件通过工单内容
            List<RdmsJobItemDto> conditionalDtos = this.getReviewJobitemListByParentJobitemIdAndResultEnum(jobItemId, ReviewResultEnum.CONDITIONAL);
            List<RdmsJobItemDto> tempDto = new ArrayList<>();
            if (!CollectionUtils.isEmpty(conditionalDtos)) {
                for (RdmsJobItemDto jobItemDto : conditionalDtos) {
                    tempDto.add(this.getJobitemRecordDetailInfo(jobItemDto.getId(), loginUserId));
                }
            }
            jobitemDetailInfo.setReviewConditionalJobitemList(tempDto);

            //添加 联合评审工单信息
            List<RdmsJobItemDto> reviewCooJobitemList = this.getJobitemByTestedJobitemId(jobItemId, loginUserId, JobItemTypeEnum.REVIEW_COO);
            List<RdmsJobItemDto> tempReviewCooList = new ArrayList<>();
            if (!CollectionUtils.isEmpty(reviewCooJobitemList)) {
                for (RdmsJobItemDto jobItemDto : reviewCooJobitemList) {
                    tempReviewCooList.add(this.getJobitemRecordDetailInfo(jobItemDto.getId(), loginUserId));
                }
            }
            jobitemDetailInfo.setReviewCooJobitemList(tempReviewCooList);
        }

        return jobitemDetailInfo;
    }

    @Transactional
    public List<RdmsJobItemDto> getOtherReviewJobitems(String jobItemId, String loginUserId) {
        List<RdmsJobItemDto> jobItemDtos = new ArrayList<>();
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();

        rdmsJobItemExample.createCriteria().andTypeEqualTo(JobItemTypeEnum.REVIEW_COO.getType()).andTestedJobitemIdEqualTo(jobItemId);
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        if (!CollectionUtils.isEmpty(rdmsJobItems)) {
            for (RdmsJobItem jobitem : rdmsJobItems) {
                RdmsJobItemDto jobitemDetailInfo = this.getJobitemDetailInfo(jobitem.getId(), loginUserId);
                if (!ObjectUtils.isEmpty(jobitemDetailInfo)) {
                    jobItemDtos.add(jobitemDetailInfo);
                }
            }
        }
        return jobItemDtos.stream().sorted(Comparator.comparing(RdmsJobItemDto::getCreateTime).reversed()).collect(Collectors.toList());
    }

    @Transactional
    public List<RdmsJobItemDto> getSupplementJobitems(String jobItemId, String loginUserId) {
        List<RdmsJobItemDto> jobItemDtos = new ArrayList<>();
        RdmsJobItemExample rdmsJobItemExample = new RdmsJobItemExample();

        rdmsJobItemExample.createCriteria().andReviewResultEqualTo(ReviewResultEnum.SUPPLEMENT.getStatus()).andParentJobitemIdEqualTo(jobItemId);
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(rdmsJobItemExample);
        if (!CollectionUtils.isEmpty(rdmsJobItems)) {
            for (RdmsJobItem jobitem : rdmsJobItems) {
                RdmsJobItemDto jobitemDetailInfo = this.getJobitemDetailInfo(jobitem.getId(), loginUserId);
                if (!ObjectUtils.isEmpty(jobitemDetailInfo)) {
                    jobItemDtos.add(jobitemDetailInfo);
                }
            }
        }
        return jobItemDtos.stream().sorted(Comparator.comparing(RdmsJobItemDto::getCreateTime).reversed()).collect(Collectors.toList());
    }

    /**
     * 保存
     */
    @Transactional
    public String saveEvaluate(RdmsJobItemDto jobItemDto) {
        RdmsBudgetResearchExe budgetResearchExe = new RdmsBudgetResearchExe();
        if (!ObjectUtils.isEmpty(jobItemDto) && !ObjectUtils.isEmpty(jobItemDto.getId())) {
            RdmsJobItem rdmsJobItem = this.selectByPrimaryKey(jobItemDto.getId());
            if (ObjectUtils.isEmpty(rdmsJobItem)) {
                throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
            }

            if (ObjectUtils.isEmpty(jobItemDto.getProjectType())) {
                throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
            }
            ProjectTypeEnum projectTypeEnumByType = ProjectTypeEnum.getProjectTypeEnumByType(jobItemDto.getProjectType());
            if (ObjectUtils.isEmpty(projectTypeEnumByType)) {
                throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
            }
            //如果存在身边工时,则对工时进行调整
            if (!ObjectUtils.isEmpty(jobItemDto.getDeclareManhour()) && jobItemDto.getDeclareManhour() > 0) {
                Double standManhour = rdmsManhourService.transformToStandManhour(jobItemDto.getDeclareManhour(), jobItemDto.getExecutorId(), jobItemDto.getCustomerId(), OperateTypeEnum.DEVELOP);
                rdmsJobItem.setManhour(standManhour);
                this.update(rdmsJobItem); //更新工单工时, 避免在做一级任务绩效计算的时候使用原来的值导致错误
            }


            switch (projectTypeEnumByType) {
                case PRE_PROJECT: {
                    RdmsJobItem jobItem = CopyUtil.copy(rdmsJobItem, RdmsJobItem.class);
                    jobItem.setEvaluateDesc(jobItemDto.getEvaluateDesc());
                    jobItem.setEvaluateScore(jobItemDto.getEvaluateScore());
                    jobItem.setNextNode(null);
                    jobItem.setStatus(JobItemStatusEnum.COMPLETED.getStatus());
                    jobItem.setCompleteTime(new Date());
                    jobItem.setPerformanceManhour(jobItem.getManhour());
                    rdmsJobItemsMapper.updateByPrimaryKey(jobItem);

                    //写一条Process记录
                    RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                    jobItemProcess.setJobItemId(jobItem.getId());
                    jobItemProcess.setExecutorId(jobItemDto.getLoginUserId());
                    jobItemProcess.setNextNode(jobItem.getExecutorId());
                    long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobItem.getId());
                    jobItemProcess.setDeep((int) jobItemProcessCount);
                    jobItemProcess.setJobDescription("对工单质量进行了评价");
                    jobItemProcess.setFileListStr(null);
                    jobItemProcess.setActualSubmissionTime(null);
                    jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.COMPLETE.getStatus());
                    rdmsJobItemProcessService.save(jobItemProcess);

                    //向预算执行数据表中写入一条人工成本记录
                    budgetResearchExe.setId(null);
                    budgetResearchExe.setCustomerId(rdmsJobItem.getCustomerId());
                    budgetResearchExe.setPreprojectId(rdmsJobItem.getPreProjectId());
                    budgetResearchExe.setProjectId(null);
                    budgetResearchExe.setParentId(null);
                    budgetResearchExe.setSubprojectId(null);
                    budgetResearchExe.setSerialNo(rdmsJobItem.getJobSerial());
                    budgetResearchExe.setName(rdmsJobItem.getJobName());
                    budgetResearchExe.setExecutorId(rdmsJobItem.getExecutorId());
                    budgetResearchExe.setClassify(BudgetClassifyStdEnum.STAFF.getClassify());
                    budgetResearchExe.setCapitalSource(CapitalSourceEnum.SELF.getSource());  //人工费 暂时 全部走自筹经费
                    //成本分类
                    if(rdmsJobItem.getType().equals(JobItemTypeEnum.TASK_FUNCTION.getType())){
                        budgetResearchExe.setPayClassify(PayClassifyEnum.TASK_FUNCTION.getClassify());
                        budgetResearchExe.setPayId(jobItem.getSubprojectId());
                    }else{
                        budgetResearchExe.setPayClassify(PayClassifyEnum.CHARACTER.getClassify());
                        budgetResearchExe.setPayId(jobItem.getSubprojectId());
                    }

                    //计算当前工单的绩效工时
                    double performanceManhour = rdmsManhourService.calJobItemPerformanceManhour(rdmsJobItem.getId());
                    budgetResearchExe.setAmount(rdmsJobItem.getStandManhourFee().multiply(BigDecimal.valueOf(performanceManhour)));
                    budgetResearchExe.setPaymentStatue(PaymentStatusEnum.UNPAID.getStatus());
                    budgetResearchExe.setPaymentTime(null);
                    budgetResearchExe.setCreateTime(new Date());
                    String save = rdmsBudgetResearchExeService.save(budgetResearchExe);

                    return jobItem.getId();
                }
                case DEV_PROJECT: {
                    RdmsJobItem jobItem = CopyUtil.copy(rdmsJobItem, RdmsJobItem.class);
                    jobItem.setEvaluateDesc(jobItemDto.getEvaluateDesc());
                    jobItem.setEvaluateScore(jobItemDto.getEvaluateScore());
                    jobItem.setNextNode(null);
                    jobItem.setStatus(JobItemStatusEnum.COMPLETED.getStatus());
                    jobItem.setCompleteTime(new Date());
                    jobItem.setPerformanceManhour(jobItem.getManhour());
                    rdmsJobItemsMapper.updateByPrimaryKey(jobItem);

                    //写一条Process记录
                    RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                    jobItemProcess.setJobItemId(jobItem.getId());
                    jobItemProcess.setExecutorId(jobItemDto.getLoginUserId());
                    jobItemProcess.setNextNode(jobItem.getExecutorId());
                    long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobItem.getId());
                    jobItemProcess.setDeep((int) jobItemProcessCount);
                    jobItemProcess.setJobDescription("对工单质量进行了评价");
                    jobItemProcess.setFileListStr(null);
                    jobItemProcess.setActualSubmissionTime(null);
                    jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.COMPLETE.getStatus());
                    rdmsJobItemProcessService.save(jobItemProcess);

                    //向预算执行数据表中写入一条人工成本记录
                    budgetResearchExe.setId(null);
                    budgetResearchExe.setCustomerId(jobItem.getCustomerId());
                    budgetResearchExe.setProjectId(jobItem.getProjectId());
                    RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(jobItem.getSubprojectId());
                    budgetResearchExe.setParentId(subproject.getParent());
                    budgetResearchExe.setSubprojectId(jobItem.getSubprojectId());
                    //成本分类
                    budgetResearchExe.setPayClassify(PayClassifyEnum.TASK_SUBP.getClassify());  //所有DEV_PROJECT 阶段的工单都是 子项目任务
                    budgetResearchExe.setPayId(jobItem.getSubprojectId());

                    budgetResearchExe.setSerialNo(jobItem.getJobSerial());
                    budgetResearchExe.setName(jobItem.getJobName());
                    budgetResearchExe.setExecutorId(jobItem.getExecutorId());
                    budgetResearchExe.setClassify(BudgetClassifyStdEnum.STAFF.getClassify());
                    budgetResearchExe.setCapitalSource(CapitalSourceEnum.SELF.getSource());  //人工费 暂时 全部走自筹经费

                    budgetResearchExe.setAmount(jobItem.getStandManhourFee().multiply(BigDecimal.valueOf(jobItem.getPerformanceManhour())));
                    budgetResearchExe.setPaymentStatue(PaymentStatusEnum.UNPAID.getStatus());
                    budgetResearchExe.setPaymentTime(null);
                    budgetResearchExe.setCreateTime(new Date());
                    String save = rdmsBudgetResearchExeService.save(budgetResearchExe);

                    return jobItem.getId();
                }
                case PROJECT:
                case SUB_PROJECT: {
                    JobItemTypeEnum jobItemTypeEnumByType = JobItemTypeEnum.getJobItemTypeEnumByType(rdmsJobItem.getType());
                    switch (Objects.requireNonNull(jobItemTypeEnumByType)) {
                        case DEVELOP: {
                            RdmsJobItem jobItem = CopyUtil.copy(rdmsJobItem, RdmsJobItem.class);
                            jobItem.setEvaluateDesc(jobItemDto.getEvaluateDesc());
                            jobItem.setEvaluateScore(jobItemDto.getEvaluateScore());
                            jobItem.setNextNode(null);
                            jobItem.setStatus(JobItemStatusEnum.COMPLETED.getStatus());
                            jobItem.setCompleteTime(new Date());
                            double performanceManhour = rdmsManhourService.calJobItemPerformanceManhour(jobItem.getId());
                            jobItem.setPerformanceManhour(performanceManhour);
                            rdmsJobItemsMapper.updateByPrimaryKey(jobItem);

                            //写一条Process记录
                            RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                            jobItemProcess.setJobItemId(jobItem.getId());
                            jobItemProcess.setExecutorId(jobItemDto.getLoginUserId());
                            jobItemProcess.setNextNode(jobItem.getExecutorId());
                            long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobItem.getId());
                            jobItemProcess.setDeep((int) jobItemProcessCount);
                            jobItemProcess.setJobDescription("对工单质量进行了评价");
                            jobItemProcess.setFileListStr(null);
                            jobItemProcess.setActualSubmissionTime(null);
                            jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.COMPLETE.getStatus());
                            rdmsJobItemProcessService.save(jobItemProcess);

                            //成本分类
                            budgetResearchExe.setPayClassify(PayClassifyEnum.CHARACTER.getClassify());
                            budgetResearchExe.setPayId(jobItem.getCharacterId());

                            break;
                        }
                        case CHARACTER_INT: {
                            RdmsJobItem jobItem = CopyUtil.copy(rdmsJobItem, RdmsJobItem.class);
                            jobItem.setEvaluateDesc(jobItemDto.getEvaluateDesc());
                            jobItem.setEvaluateScore(jobItemDto.getEvaluateScore());
                            jobItem.setNextNode(null);
                            jobItem.setStatus(JobItemStatusEnum.COMPLETED.getStatus());
                            jobItem.setCompleteTime(new Date());
                            double performanceManhour = rdmsManhourService.calJobItemPerformanceManhour(jobItem.getId());
                            jobItem.setPerformanceManhour(performanceManhour);
                            rdmsJobItemsMapper.updateByPrimaryKey(jobItem);

                            //写一条Process记录
                            RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                            jobItemProcess.setJobItemId(jobItem.getId());
                            jobItemProcess.setExecutorId(jobItemDto.getLoginUserId());
                            jobItemProcess.setNextNode(jobItem.getExecutorId());
                            long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobItem.getId());
                            jobItemProcess.setDeep((int) jobItemProcessCount);
                            jobItemProcess.setJobDescription("对工单质量进行了评价");
                            jobItemProcess.setFileListStr(null);
                            jobItemProcess.setActualSubmissionTime(null);
                            jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.COMPLETE.getStatus());
                            rdmsJobItemProcessService.save(jobItemProcess);

                            //如果工单类型为 Character_int 还要将Character记录状态修改为 dev_complete
                            if (rdmsJobItem.getType().equals(JobItemTypeEnum.CHARACTER_INT.getType())) {
                                RdmsCharacter character_int = rdmsCharacterService.selectByPrimaryKey(jobItem.getCharacterId());
                                character_int.setStatus(CharacterStatusEnum.DEV_COMPLETE.getStatus());
                                rdmsCharacterService.update(character_int);
                            }

                            //成本分类
                            budgetResearchExe.setPayClassify(PayClassifyEnum.CHARACTER.getClassify());
                            budgetResearchExe.setPayId(jobItem.getCharacterId());

                            break;
                        }
                        case BUG: {
                            RdmsJobItem jobItem = CopyUtil.copy(rdmsJobItem, RdmsJobItem.class);
                            jobItem.setEvaluateDesc(jobItemDto.getEvaluateDesc());
                            jobItem.setEvaluateScore(jobItemDto.getEvaluateScore());
                            jobItem.setNextNode(null);
                            jobItem.setStatus(JobItemStatusEnum.COMPLETED.getStatus());
                            jobItem.setCompleteTime(new Date());
                            double performanceManhour = rdmsManhourService.calJobItemPerformanceManhour(jobItem.getId());
                            jobItem.setPerformanceManhour(performanceManhour);
                            rdmsJobItemsMapper.updateByPrimaryKey(jobItem);

                            //写一条Process记录
                            RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                            jobItemProcess.setJobItemId(jobItem.getId());
                            jobItemProcess.setExecutorId(jobItemDto.getLoginUserId());
                            jobItemProcess.setNextNode(jobItem.getExecutorId());
                            long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobItem.getId());
                            jobItemProcess.setDeep((int) jobItemProcessCount);
                            jobItemProcess.setJobDescription("对工单质量进行了评价");
                            jobItemProcess.setFileListStr(null);
                            jobItemProcess.setActualSubmissionTime(null);
                            jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.COMPLETE.getStatus());
                            rdmsJobItemProcessService.save(jobItemProcess);

                            RdmsJobItem testJob = this.selectByPrimaryKey(jobItem.getParentJobitemId());
                            if (!ObjectUtils.isEmpty(testJob) && !ObjectUtils.isEmpty(testJob.getTestedJobitemId())) {
                                RdmsJobItem testedJob = this.selectByPrimaryKey(testJob.getTestedJobitemId());
                                if (!ObjectUtils.isEmpty(testedJob) && !ObjectUtils.isEmpty(testedJob.getType())) {
                                    if (testedJob.getType().equals(JobItemTypeEnum.ASSIST.getType())) {
                                        RdmsJobItem parentJob = this.selectByPrimaryKey(testedJob.getParentJobitemId());
                                        if (!ObjectUtils.isEmpty(parentJob) && !ObjectUtils.isEmpty(parentJob.getType())) {
                                            if (parentJob.getType().equals(JobItemTypeEnum.CHARACTER_INT.getType())) {
                                                //成本分类
                                                budgetResearchExe.setPayClassify(PayClassifyEnum.CHARACTER.getClassify());
                                                budgetResearchExe.setPayId(jobItem.getCharacterId());
                                            } else {
                                                if(jobItemDto.getProjectType().equals(ProjectTypeEnum.PROJECT.getType())){
                                                    //成本分类
                                                    budgetResearchExe.setPayClassify(PayClassifyEnum.PROJECT.getClassify());
                                                    budgetResearchExe.setPayId(jobItem.getSubprojectId());
                                                }else {
                                                    //成本分类
                                                    budgetResearchExe.setPayClassify(PayClassifyEnum.TASK_SUBP.getClassify());
                                                    budgetResearchExe.setPayId(jobItem.getSubprojectId());
                                                }
                                            }
                                        }
                                    } else {
                                        if (testedJob.getType().equals(JobItemTypeEnum.DEVELOP.getType())
                                                || testedJob.getType().equals(JobItemTypeEnum.CHARACTER_INT.getType())) {
                                            //成本分类
                                            budgetResearchExe.setPayClassify(PayClassifyEnum.CHARACTER.getClassify());
                                            budgetResearchExe.setPayId(jobItem.getCharacterId());
                                        } else {
                                            //成本分类
                                            budgetResearchExe.setPayClassify(PayClassifyEnum.TASK_SUBP.getClassify());
                                            budgetResearchExe.setPayId(jobItem.getSubprojectId());
                                        }
                                    }
                                }
                            }

                            break;
                        }
                        case ASSIST: {
                            RdmsJobItem jobItem = CopyUtil.copy(rdmsJobItem, RdmsJobItem.class);
                            jobItem.setEvaluateDesc(jobItemDto.getEvaluateDesc());
                            jobItem.setEvaluateScore(jobItemDto.getEvaluateScore());
                            jobItem.setNextNode(null);
                            jobItem.setStatus(JobItemStatusEnum.COMPLETED.getStatus());
                            jobItem.setCompleteTime(new Date());
                            double performanceManhour = rdmsManhourService.calJobItemPerformanceManhour(jobItem.getId());
                            jobItem.setPerformanceManhour(performanceManhour);
                            rdmsJobItemsMapper.updateByPrimaryKey(jobItem);

                            //写一条Process记录
                            RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                            jobItemProcess.setJobItemId(jobItem.getId());
                            jobItemProcess.setExecutorId(jobItemDto.getLoginUserId());
                            jobItemProcess.setNextNode(jobItem.getExecutorId());
                            long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobItem.getId());
                            jobItemProcess.setDeep((int) jobItemProcessCount);
                            jobItemProcess.setJobDescription("对工单质量进行了评价");
                            jobItemProcess.setFileListStr(null);
                            jobItemProcess.setActualSubmissionTime(null);
                            jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.COMPLETE.getStatus());
                            rdmsJobItemProcessService.save(jobItemProcess);

                            RdmsJobItem parentJob = this.selectByPrimaryKey(jobItem.getParentJobitemId());
                            if (!ObjectUtils.isEmpty(parentJob) && !ObjectUtils.isEmpty(parentJob.getType())) {
                                if (parentJob.getType().equals(JobItemTypeEnum.CHARACTER_INT.getType())) {
                                    //成本分类
                                    budgetResearchExe.setPayClassify(PayClassifyEnum.CHARACTER.getClassify());
                                    budgetResearchExe.setPayId(jobItem.getCharacterId());
                                } else {
                                    if(jobItemDto.getProjectType().equals(ProjectTypeEnum.PROJECT.getType())){
                                        //成本分类
                                        budgetResearchExe.setPayClassify(PayClassifyEnum.PROJECT.getClassify());
                                        budgetResearchExe.setPayId(jobItem.getSubprojectId());
                                    }else {
                                        //成本分类
                                        budgetResearchExe.setPayClassify(PayClassifyEnum.TASK_SUBP.getClassify());
                                        budgetResearchExe.setPayId(jobItem.getSubprojectId());
                                    }
                                }
                            }

                            break;
                        }
                        case TASK_SUBP:
                        case TASK_PRODUCT:
                        case TASK_MILESTONE:
                        case TASK_TEST:
                        case TASK_BUG:
                        case QUALITY: {
                            RdmsJobItem jobItem = CopyUtil.copy(rdmsJobItem, RdmsJobItem.class);
                            jobItem.setEvaluateDesc(jobItemDto.getEvaluateDesc());
                            jobItem.setEvaluateScore(jobItemDto.getEvaluateScore());
                            jobItem.setNextNode(null);
                            jobItem.setStatus(JobItemStatusEnum.COMPLETED.getStatus());
                            jobItem.setCompleteTime(new Date());
                            double performanceManhour = rdmsManhourService.calJobItemPerformanceManhour(jobItem.getId());
                            jobItem.setPerformanceManhour(performanceManhour);
                            rdmsJobItemsMapper.updateByPrimaryKey(jobItem);

                            //写一条Process记录
                            RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                            jobItemProcess.setJobItemId(jobItem.getId());
                            jobItemProcess.setExecutorId(jobItemDto.getLoginUserId());
                            jobItemProcess.setNextNode(jobItem.getExecutorId());
                            long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobItem.getId());
                            jobItemProcess.setDeep((int) jobItemProcessCount);
                            jobItemProcess.setJobDescription("对工单质量进行了评价");
                            jobItemProcess.setFileListStr(null);
                            jobItemProcess.setActualSubmissionTime(null);
                            jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.COMPLETE.getStatus());
                            rdmsJobItemProcessService.save(jobItemProcess);

                            //如果是增补材料工单
                            if (!ObjectUtils.isEmpty(jobItem.getReviewResult())) {
                                if (jobItem.getReviewResult().equals(ReviewResultEnum.SUPPLEMENT.getStatus())) {
                                    {
                                        RdmsJobItem reviewJobitem = this.selectByPrimaryKey(jobItem.getParentJobitemId());
                                        reviewJobitem.setStatus(JobItemStatusEnum.SUBMIT.getStatus());
                                        String updateId = this.update(reviewJobitem);
                                        //写一条Process记录
                                        RdmsJobItemProcess parentJobItemProcess = new RdmsJobItemProcess();
                                        parentJobItemProcess.setJobItemId(updateId);
                                        parentJobItemProcess.setExecutorId(jobItemDto.getLoginUserId());
                                        parentJobItemProcess.setNextNode(reviewJobitem.getExecutorId());
                                        long jobItemProcessCount_parent = rdmsJobItemProcessService.getJobItemProcessCount(updateId);
                                        parentJobItemProcess.setDeep((int) jobItemProcessCount_parent);
                                        parentJobItemProcess.setJobDescription("恢复工单评审状态");
                                        parentJobItemProcess.setFileListStr(null);
                                        parentJobItemProcess.setActualSubmissionTime(null);
                                        parentJobItemProcess.setProcessStatus(JobItemProcessStatusEnum.SUBMIT.getStatus());
                                        rdmsJobItemProcessService.save(parentJobItemProcess);

                                    }
                                    if (!ObjectUtils.isEmpty(jobItem.getCharacterId())) {
                                        //将character状态重新设置为评审
                                        RdmsCharacter rdmsCharacter = rdmsCharacterService.selectByPrimaryKey(jobItem.getCharacterId());
                                        rdmsCharacter.setStatus(CharacterStatusEnum.REVIEW.getStatus());
                                        String characterId = rdmsCharacterService.update(rdmsCharacter);

                                        RdmsCharacterProcess characterProcess = new RdmsCharacterProcess();
                                        characterProcess.setCharacterId(characterId);
                                        characterProcess.setExecutorId(jobItemDto.getExecutorId());
                                        characterProcess.setNextNode(null);
                                        long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(characterId);
                                        characterProcess.setDeep((int) characterProcessCount);
                                        characterProcess.setJobDescription("提交评审");
                                        characterProcess.setProcessStatus(CharacterProcessStatusEnum.REVIEW.getStatus());
                                        rdmsCharacterProcessService.save(characterProcess);
                                    }
                                }
                            }
                            if(jobItemDto.getProjectType().equals(ProjectTypeEnum.PROJECT.getType())){
                                //成本分类
                                if(jobItem.getType().equals(JobItemTypeEnum.TASK_TEST.getType())){
                                    budgetResearchExe.setPayClassify(PayClassifyEnum.TASK_TEST.getClassify());
                                    budgetResearchExe.setPayId(jobItem.getSubprojectId());
                                }else{
                                    budgetResearchExe.setPayClassify(PayClassifyEnum.PROJECT.getClassify());
                                    budgetResearchExe.setPayId(jobItem.getSubprojectId());
                                }

                            }else {
                                //成本分类
                                budgetResearchExe.setPayClassify(PayClassifyEnum.TASK_SUBP.getClassify());
                                budgetResearchExe.setPayId(jobItem.getSubprojectId());
                            }

                            break;
                        }
                        case TEST: {
                            RdmsJobItem jobItem = CopyUtil.copy(rdmsJobItem, RdmsJobItem.class);
                            {
                                jobItem.setEvaluateDesc(jobItemDto.getEvaluateDesc());
                                jobItem.setEvaluateScore(jobItemDto.getEvaluateScore());
                                jobItem.setNextNode(null);
                                jobItem.setStatus(JobItemStatusEnum.COMPLETED.getStatus());
                                jobItem.setCompleteTime(new Date());
                                double performanceManhour = rdmsManhourService.calJobItemPerformanceManhour(jobItem.getId());
                                jobItem.setPerformanceManhour(performanceManhour);
                                rdmsJobItemsMapper.updateByPrimaryKey(jobItem);

                                //写一条Process记录
                                RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                                jobItemProcess.setJobItemId(jobItem.getId());
                                jobItemProcess.setExecutorId(jobItemDto.getLoginUserId());
                                jobItemProcess.setNextNode(jobItem.getExecutorId());
                                long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobItem.getId());
                                jobItemProcess.setDeep((int) jobItemProcessCount);
                                jobItemProcess.setJobDescription("对工单质量进行了评价");
                                jobItemProcess.setFileListStr(null);
                                jobItemProcess.setActualSubmissionTime(null);
                                jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.COMPLETE.getStatus());
                                rdmsJobItemProcessService.save(jobItemProcess);
                            }
                            //处理被测工单的状态
                            {
                                RdmsJobItem testJobitem = this.selectByPrimaryKey(jobItem.getTestedJobitemId());
                                testJobitem.setNextNode(testJobitem.getProjectManagerId());
                                testJobitem.setStatus(JobItemStatusEnum.SUBMIT.getStatus());
                                rdmsJobItemsMapper.updateByPrimaryKey(testJobitem);

                                //写一条Process记录
                                RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                                jobItemProcess.setJobItemId(testJobitem.getId());
                                jobItemProcess.setExecutorId(jobItemDto.getLoginUserId());
                                long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(testJobitem.getId());
                                jobItemProcess.setDeep((int) jobItemProcessCount);
                                jobItemProcess.setJobDescription("对工单交付件经过了测试");
                                jobItemProcess.setFileListStr(null);
                                jobItemProcess.setNextNode(testJobitem.getExecutorId());
                                jobItemProcess.setActualSubmissionTime(null);
                                jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.SUBMIT.getStatus());
                                rdmsJobItemProcessService.save(jobItemProcess);
                            }

                            RdmsJobItem testedJob = this.selectByPrimaryKey(jobItem.getTestedJobitemId());
                            if (!ObjectUtils.isEmpty(testedJob) && !ObjectUtils.isEmpty(testedJob.getType())) {

                                if (testedJob.getType().equals(JobItemTypeEnum.ASSIST.getType())) {
                                    RdmsJobItem parentJob = this.selectByPrimaryKey(testedJob.getParentJobitemId());
                                    if (!ObjectUtils.isEmpty(parentJob) && !ObjectUtils.isEmpty(parentJob.getType())) {
                                        if (parentJob.getType().equals(JobItemTypeEnum.CHARACTER_INT.getType())) {
                                            //成本分类
                                            budgetResearchExe.setPayClassify(PayClassifyEnum.CHARACTER.getClassify());
                                            budgetResearchExe.setPayId(jobItem.getCharacterId());
                                        } else {
                                            if(jobItemDto.getProjectType().equals(ProjectTypeEnum.PROJECT.getType())){
                                                //成本分类
                                                budgetResearchExe.setPayClassify(PayClassifyEnum.PROJECT.getClassify());
                                                budgetResearchExe.setPayId(jobItem.getSubprojectId());
                                            }else {
                                                //成本分类
                                                budgetResearchExe.setPayClassify(PayClassifyEnum.TASK_SUBP.getClassify());
                                                budgetResearchExe.setPayId(jobItem.getSubprojectId());
                                            }
                                        }
                                    }
                                } else {
                                    if (testedJob.getType().equals(JobItemTypeEnum.DEVELOP.getType())
                                            || testedJob.getType().equals(JobItemTypeEnum.CHARACTER_INT.getType())) {
                                        //成本分类
                                        budgetResearchExe.setPayClassify(PayClassifyEnum.CHARACTER.getClassify());
                                        budgetResearchExe.setPayId(jobItem.getCharacterId());
                                    } else {
                                        if(jobItemDto.getProjectType().equals(ProjectTypeEnum.PROJECT.getType())){
                                            //成本分类
                                            budgetResearchExe.setPayClassify(PayClassifyEnum.PROJECT.getClassify());
                                            budgetResearchExe.setPayId(jobItem.getSubprojectId());
                                        }else {
                                            //成本分类
                                            budgetResearchExe.setPayClassify(PayClassifyEnum.TASK_SUBP.getClassify());
                                            budgetResearchExe.setPayId(jobItem.getSubprojectId());
                                        }
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
                //向预算执行数据表中写入一条人工成本记录
                budgetResearchExe.setId(null);
                budgetResearchExe.setCustomerId(rdmsJobItem.getCustomerId());
                budgetResearchExe.setProjectId(rdmsJobItem.getProjectId());
                RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(rdmsJobItem.getSubprojectId());
                budgetResearchExe.setParentId(subproject.getParent());
                budgetResearchExe.setSubprojectId(rdmsJobItem.getSubprojectId());
                budgetResearchExe.setSerialNo(rdmsJobItem.getJobSerial());
                budgetResearchExe.setName(rdmsJobItem.getJobName());
                budgetResearchExe.setExecutorId(rdmsJobItem.getExecutorId());
                budgetResearchExe.setClassify(BudgetClassifyStdEnum.STAFF.getClassify());
                budgetResearchExe.setCapitalSource(CapitalSourceEnum.SELF.getSource());  //人工费 暂时 全部走自筹经费
                //计算当前工单的绩效工时
                double performanceManhour = rdmsManhourService.calJobItemPerformanceManhour(rdmsJobItem.getId());
                budgetResearchExe.setAmount(rdmsJobItem.getStandManhourFee().multiply(BigDecimal.valueOf(performanceManhour)));
                budgetResearchExe.setPaymentStatue(PaymentStatusEnum.UNPAID.getStatus());
                budgetResearchExe.setPaymentTime(null);
                budgetResearchExe.setCreateTime(new Date());
                String save = rdmsBudgetResearchExeService.save(budgetResearchExe);
            }
        }
        return null;
    }

    /**
     * 新增
     */
    private String insert(RdmsJobItem jobItem) {
        if (ObjectUtils.isEmpty(jobItem.getId())) {
            jobItem.setId(UuidUtil.getShortUuid());
        }
        jobItem.setJobSerial(CodeUtil.randomJobNum());
        jobItem.setCreateTime(new Date());
        jobItem.setDeleted(0);
        jobItem.setSettleAccounts(0);
        if (jobItem.getManhour() == null) {
            jobItem.setManhour(0.0);
        }
        if (jobItem.getStandManhourFee() == null) {
            jobItem.setStandManhourFee(BigDecimal.ZERO);
        }
        RdmsJobItem rdmsJobItems = rdmsJobItemsMapper.selectByPrimaryKey(jobItem.getId());
        if (!ObjectUtils.isEmpty(rdmsJobItems)) {
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        } else {
            jobItem.setUpdateTime(new Date());
            if (ObjectUtils.isEmpty(jobItem.getAuxType())) {
                jobItem.setAuxType(JobItemTypeEnum.NOTSET.getType());
            }
            rdmsJobItemsMapper.insert(jobItem);
            return jobItem.getId();
        }
    }

    /**
     * 更新
     */
    @Transactional
    public String update(RdmsJobItem jobItem) {
        RdmsJobItem rdmsJobItems = rdmsJobItemsMapper.selectByPrimaryKey(jobItem.getId());
        if (!ObjectUtils.isEmpty(rdmsJobItems)) {
            jobItem.setUpdateTime(new Date());
            jobItem.setDeleted(0);
            jobItem.setCreateTime(rdmsJobItems.getCreateTime());
            rdmsJobItemsMapper.updateByPrimaryKey(jobItem);
            return jobItem.getId();
        } else {
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

    @Transactional
    public String updateByPrimaryKeySelective(RdmsJobItem jobItem) {
        if (!ObjectUtils.isEmpty(jobItem) && !ObjectUtils.isEmpty(jobItem.getId())) {
            rdmsJobItemsMapper.updateByPrimaryKeySelective(jobItem);
            return jobItem.getId();
        } else {
            return null;
        }

    }

    /**
     * 更新
     */
    @Transactional
    public RdmsJobItem selectByPrimaryKey(String jobItemId) {
        return rdmsJobItemsMapper.selectByPrimaryKey(jobItemId);
    }

    /**
     * 更新
     */
    @Transactional
    public RdmsJobItemDto getOriginJobitemByCharacterId(String characterId) {

        RdmsCharacter character = rdmsCharacterService.selectByPrimaryKey(characterId);
        if (character.getStatus().equals(CharacterStatusEnum.ITERATING.getStatus())) {
            RdmsCharacter savedCharacter = rdmsCharacterService.getSavedCharacterByIteratingCharacterId(characterId);
            RdmsJobItem rdmsJobItem = this.selectByPrimaryKey(savedCharacter.getJobitemId());
            return CopyUtil.copy(rdmsJobItem, RdmsJobItemDto.class);
        } else {
            RdmsJobItem rdmsJobItem = this.selectByPrimaryKey(character.getJobitemId());
            return CopyUtil.copy(rdmsJobItem, RdmsJobItemDto.class);
        }
    }

    @Transactional
    public List<RdmsJobItemDto> getJobitemListByPjm(String projectManagerId) {
        RdmsJobItemExample jobItemExample = new RdmsJobItemExample();
        RdmsJobItemExample.Criteria criteria = jobItemExample.createCriteria().andProjectManagerIdEqualTo(projectManagerId).andDeletedEqualTo(0);
        List<String> jobTypeList = new ArrayList<>();
        jobTypeList.add(JobItemTypeEnum.DEVELOP.getType());
        jobTypeList.add(JobItemTypeEnum.ASSIST.getType());
        jobTypeList.add(JobItemTypeEnum.TEST.getType());
        jobTypeList.add(JobItemTypeEnum.CHARACTER_INT.getType());
        jobTypeList.add(JobItemTypeEnum.TASK_SUBP.getType());
        jobTypeList.add(JobItemTypeEnum.TASK_MILESTONE.getType());
        jobTypeList.add(JobItemTypeEnum.TASK.getType());
        criteria.andTypeIn(jobTypeList);
        List<RdmsJobItem> rdmsJobItems = rdmsJobItemsMapper.selectByExample(jobItemExample);
        return CopyUtil.copyList(rdmsJobItems, RdmsJobItemDto.class);
    }

    @Transactional
    public void archive(RdmsHmiIdsDto idsDto) {
        RdmsJobItem jobItem = this.selectByPrimaryKey(idsDto.getJobitemId());
        {
            jobItem.setNextNode(null);
            jobItem.setStatus(JobItemStatusEnum.ARCHIVED.getStatus());
            rdmsJobItemsMapper.updateByPrimaryKey(jobItem);

            //写一条Process记录
            RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
            jobItemProcess.setJobItemId(jobItem.getId());
            jobItemProcess.setExecutorId(jobItem.getExecutorId());
            jobItemProcess.setNextNode(null);
            long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobItem.getId());
            jobItemProcess.setDeep((int) jobItemProcessCount);
            jobItemProcess.setJobDescription("评审完成, 归档");
            jobItemProcess.setFileListStr(null);
            jobItemProcess.setActualSubmissionTime(null);
            jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.COMPLETE.getStatus());
            rdmsJobItemProcessService.save(jobItemProcess);
        }

        if (jobItem.getType().equals(JobItemTypeEnum.REVIEW_SUBP.getType())) {
            //当子项目发集成工单后, 子项的状态 应该为 integration 状态
            RdmsProjectSubprojectDto subprojectDto = rdmsProjectService.getSubprojectBySubprojectId(jobItem.getSubprojectId());
            RdmsProjectSubproject subproject = CopyUtil.copy(subprojectDto, RdmsProjectSubproject.class);
            subproject.setStatus(ProjectStatusEnum.ARCHIVED.getStatus());
            rdmsSubprojectService.updateByPrimaryKeySelective(subproject);
        }
        //判断被评审的项目是否为主项目, 如果是主项目通过评审, 需要处理project表中的项目状态
        if (jobItem.getProjectId().equals(jobItem.getSubprojectId()) && jobItem.getType().equals(JobItemTypeEnum.REVIEW_SUBP.getType())) {
            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(jobItem.getProjectId());
            rdmsProject.setStatus(ProjectStatusEnum.ARCHIVED.getStatus());
            rdmsProjectService.update(rdmsProject);
        }

    }

    @Transactional
    public void closeJobitem(String jobId) {
        RdmsJobItem jobItem = this.selectByPrimaryKey(jobId);
        jobItem.setStatus(JobItemStatusEnum.CANCEL.getStatus());
        String jobitemId = this.update(jobItem);

        //写一条Process记录
        RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
        jobItemProcess.setJobItemId(jobitemId);
        jobItemProcess.setExecutorId(jobItem.getProjectManagerId());
        jobItemProcess.setNextNode(null);
        long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobitemId);
        jobItemProcess.setDeep((int) jobItemProcessCount);
        jobItemProcess.setJobDescription("取消工作任务");
        jobItemProcess.setFileListStr(null);
        jobItemProcess.setActualSubmissionTime(null);
        jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.COMPLETE.getStatus());
        rdmsJobItemProcessService.save(jobItemProcess);

        if (jobItem.getType().equals(JobItemTypeEnum.SUGGEST.getType())) {
            RdmsCharacter character = rdmsCharacterService.selectByPrimaryKey(jobItem.getCharacterId());
            character.setStatus(CharacterStatusEnum.DISCARD.getStatus());
            String characterId = rdmsCharacterService.update(character);

            RdmsCharacterProcess characterProcess = new RdmsCharacterProcess();
            characterProcess.setCharacterId(characterId);
            characterProcess.setExecutorId(jobItem.getProjectManagerId());
            characterProcess.setNextNode(null);
            long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(characterId);
            characterProcess.setDeep((int) characterProcessCount);
            characterProcess.setJobDescription("功能废弃");
            characterProcess.setProcessStatus(CharacterProcessStatusEnum.COMPLETE.getStatus());
            rdmsCharacterProcessService.save(characterProcess);
        }
        if (jobItem.getType().equals(JobItemTypeEnum.SUGGEST_UPDATE.getType())) {
            RdmsCharacter character = rdmsCharacterService.selectByPrimaryKey(jobItem.getCharacterId());
            character.setStatus(CharacterStatusEnum.REFUSE.getStatus());
            character.setAuxStatus(CharacterStatusEnum.NOTSET.getStatus());
            String characterId = rdmsCharacterService.update(character);

            RdmsCharacterProcess characterProcess = new RdmsCharacterProcess();
            characterProcess.setCharacterId(characterId);
            characterProcess.setExecutorId(jobItem.getProjectManagerId());
            characterProcess.setNextNode(null);
            long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(characterId);
            characterProcess.setDeep((int) characterProcessCount);
            characterProcess.setJobDescription("拒绝功能迭代建议");
            characterProcess.setProcessStatus(CharacterProcessStatusEnum.COMPLETE.getStatus());
            rdmsCharacterProcessService.save(characterProcess);

            //处理CBB状态
            List<RdmsCbbDto> cbbByCbbCode = rdmsCbbService.getCbbByCbbCode(characterId);
            if(!CollectionUtils.isEmpty(cbbByCbbCode)){
                cbbByCbbCode.get(0).setStatus(CharacterStatusEnum.REFUSE.getStatus());
                rdmsCbbService.update(cbbByCbbCode.get(0));
            }
        }


    }

    /**
     * 删除操作----设置删除标志位
     */
    @Transactional
    public void delete(String id) {
        RdmsJobItem rdmsJobItems = rdmsJobItemsMapper.selectByPrimaryKey(id);
        if (!ObjectUtils.isEmpty(rdmsJobItems)) {
            rdmsJobItems.setDeleted(1);
            rdmsJobItemsMapper.updateByPrimaryKey(rdmsJobItems);
        } else {
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

}
