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
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.*;
import com.course.server.enums.rdms.*;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.*;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RdmsMilestoneService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsMilestoneService.class);

    @Resource
    private RdmsMilestoneMapper rdmsMilestoneMapper;
    @Resource
    private RdmsProjectService rdmsProjectService;
    @Resource
    private RdmsJobItemService rdmsJobItemService;
    @Resource
    private RdmsCharacterService rdmsCharacterService;
    @Resource
    private RdmsJobItemProcessService rdmsJobItemProcessService;
    @Resource
    private RdmsSubprojectService rdmsSubprojectService;
    @Autowired
    private RdmsCustomerUserService rdmsCustomerUserService;


    @Transactional
    public void listMilestones(PageDto<RdmsMilestoneDto> pageDto) {
        //找出所有符合条件的项目
        List<RdmsProject> projectList = rdmsProjectService.getProjectListByProjectName(pageDto.getCustomerId(), pageDto.getKeyWord());
        if(!CollectionUtils.isEmpty(projectList)){
            List<String> projectIdList = projectList.stream().map(RdmsProject::getId).collect(Collectors.toList());

            List<RdmsMilestoneDto> rdmsMilestoneDtos = new ArrayList<>();
            if(pageDto.getPage() == 1){
                //显示超期的里程碑和未来的里程碑
                //1. 显示超期的里程碑
                //1.1 读出一年之内评审时间早于当前时间的里程碑
                // 获取当前日期
                Calendar calendar = Calendar.getInstance();
                // 减去半年
                calendar.add(Calendar.MONTH, -6);
                // 获取减去半年后的日期
                Date oneYearAgo = calendar.getTime();
                RdmsMilestoneExample rdmsMilestoneExample = new RdmsMilestoneExample();
                rdmsMilestoneExample.setOrderByClause("review_time asc");
                rdmsMilestoneExample.createCriteria()
                        .andProjectIdIn(projectIdList)
                        .andReviewTimeLessThan(new Date())
                        .andReviewTimeGreaterThan(oneYearAgo)
                        .andDeletedEqualTo("0");
                List<RdmsMilestone> rdmsMilestones = rdmsMilestoneMapper.selectByExample(rdmsMilestoneExample);
                for(RdmsMilestone rdmsMilestone: rdmsMilestones){
                    if(!ObjectUtils.isEmpty(rdmsMilestone)){
                        Boolean overTime = this.isOverTime(rdmsMilestone.getId());
                        if(overTime){
                            rdmsMilestoneDtos.add(CopyUtil.copy(rdmsMilestone, RdmsMilestoneDto.class));
                        }
                    }
                }
                for(RdmsMilestoneDto rdmsMilestoneDto: rdmsMilestoneDtos){
                    RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(rdmsMilestoneDto.getProjectId());
                    if(!ObjectUtils.isEmpty(rdmsProject)){
                        rdmsMilestoneDto.setProjectCode(rdmsProject.getProjectCode());
                        rdmsMilestoneDto.setProjectName(rdmsProject.getProjectName());
                        if(!ObjectUtils.isEmpty(rdmsProject.getProductManagerId())){
                            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(rdmsProject.getProductManagerId());
                            rdmsMilestoneDto.setProductManagerName(rdmsCustomerUser.getTrueName());
                        }
                    }
                }
            }

            //读出没有关闭的里程碑
            //注意: 没有被关联的里程碑, 不会超时, 也不会被关闭; 只有超时的里程碑才会被进行业绩考核
            PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
            RdmsMilestoneExample milestoneExample = new RdmsMilestoneExample();
            milestoneExample.setOrderByClause("review_time asc");
            RdmsMilestoneExample.Criteria criteria = milestoneExample.createCriteria()
                    .andProjectIdIn(projectIdList)
                    .andReviewTimeGreaterThan(new Date())
                    .andDeletedEqualTo("0");
            List<RdmsMilestone> rdmsMilestones2 = rdmsMilestoneMapper.selectByExample(milestoneExample);
            PageInfo<RdmsMilestone> pageInfo = new PageInfo<>(rdmsMilestones2);
            pageDto.setTotal(pageInfo.getTotal());

            List<RdmsMilestoneDto> rdmsMilestoneDtos2 = CopyUtil.copyList(rdmsMilestones2, RdmsMilestoneDto.class);
            for(RdmsMilestoneDto rdmsMilestoneDto: rdmsMilestoneDtos2){
                RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(rdmsMilestoneDto.getProjectId());
                if(!ObjectUtils.isEmpty(rdmsProject)){
                    rdmsMilestoneDto.setProjectCode(rdmsProject.getProjectCode());
                    rdmsMilestoneDto.setProjectName(rdmsProject.getProjectName());
                    if(!ObjectUtils.isEmpty(rdmsProject.getProductManagerId())){
                        RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(rdmsProject.getProductManagerId());
                        rdmsMilestoneDto.setProductManagerName(rdmsCustomerUser.getTrueName());
                    }
                }
            }
            if(pageDto.getPage() == 1){
                rdmsMilestoneDtos.addAll(rdmsMilestoneDtos2);
                pageDto.setList(rdmsMilestoneDtos);
            }else{
                pageDto.setList(rdmsMilestoneDtos2);
            }
        }else{
            pageDto.setList(null);
        }
    }

    /**
     * 检查里程碑是否未关闭
     * 通过检查关联的子项目和角色状态来判断里程碑是否处于未关闭状态
     * 
     * @param milestoneId 里程碑ID
     * @return 返回true表示里程碑未关闭，false表示已关闭
     */
    public Boolean isNotClosed(String milestoneId) {
        Boolean isNotClosed = false;
        Boolean isNotClosed_ch = false;
        List<RdmsProjectSubproject> subprojectList = rdmsSubprojectService.getSubprojectListByTargetMilestone(milestoneId);
        //如果subprojectList 为空,说明这个里程碑没有被子项目关联
        //没有被关联的里程碑, 不会超时, 也不会被关闭
        if(!ObjectUtils.isEmpty(subprojectList)){
            for(RdmsProjectSubproject subproject: subprojectList){
                if(!ObjectUtils.isEmpty(subproject) && !ObjectUtils.isEmpty(subproject.getTargetMilestoneId())){
                    if(!(subproject.getStatus().equals(ProjectStatusEnum.COMPLETE.getStatus())
                            || subproject.getStatus().equals(ProjectStatusEnum.CLOSE.getStatus())
                            || subproject.getStatus().equals(ProjectStatusEnum.OUT_SOURCE.getStatus())
                            || subproject.getStatus().equals(ProjectStatusEnum.ARCHIVED.getStatus())
                    )){
                        isNotClosed = true;
                        break;
                    }
                }
            }
        }else{
            isNotClosed = false;
        }


        List<RdmsCharacter> characterList = rdmsCharacterService.getCharacterListByMilestone(milestoneId);
        //如果subprojectList 为空,说明这个里程碑没有被子项目关联
        //没有被关联的里程碑, 不会超时, 也不会被关闭
        if(!ObjectUtils.isEmpty(characterList)){
            for(RdmsCharacter character: characterList){
                if(!ObjectUtils.isEmpty(character) && !ObjectUtils.isEmpty(character.getMilestoneId())){
                    if(!(character.getStatus().equals(CharacterStatusEnum.DISCARD.getStatus())
                            || character.getStatus().equals(CharacterStatusEnum.REFUSE.getStatus())
                            || character.getStatus().equals(CharacterStatusEnum.HISTORY.getStatus())
                            || character.getStatus().equals(CharacterStatusEnum.ARCHIVED.getStatus())
                            || character.getStatus().equals(CharacterStatusEnum.UPDATED.getStatus())
                            || character.getStatus().equals(CharacterStatusEnum.OUT_SOURCE.getStatus())
                            || character.getStatus().equals(CharacterStatusEnum.UPDATED_HISTORY.getStatus())
                    )){
                        isNotClosed_ch = true;
                        break;
                    }
                }
            }
        }else{
            isNotClosed_ch = false;
        }

        return isNotClosed || isNotClosed_ch;
    }

    /**
     * 检查里程碑是否超期
     * 判断里程碑是否处于未关闭状态且已超过评审时间
     * 
     * @param milestoneId 里程碑ID
     * @return 返回true表示里程碑已超期，false表示未超期
     */
    @Transactional
    public Boolean isOverTime(String milestoneId){
        //如果一个里程碑没有关闭, 并且处于超期状态,就是逾期了
        RdmsMilestoneDto rdmsMilestoneDto = this.selectByPrimaryKey(milestoneId);
        //判断里程碑是否关闭
        Boolean milestoneNotClosed = this.isNotClosed(milestoneId);
        if(!milestoneNotClosed){
            return false;
        }else{
            //看里程碑时间是不是比当前日期大
            Date now = new Date();
            long time = now.getTime();

            if(!ObjectUtils.isEmpty(rdmsMilestoneDto.getReviewTime())){
                long reviewTime = rdmsMilestoneDto.getReviewTime().getTime();
                if(time > reviewTime){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }
    }

    /**
     * 发送里程碑任务
     * 在里程碑到达时，给所有子项目经理下发工作任务，要求进行里程碑总结
     * 
     * @param jobItemsDto 工作任务数据传输对象
     * @return 返回发送的任务数量
     * @throws ParseException 解析异常
     */
    public Integer sendMilestoneTask(RdmsJobItemDto jobItemsDto) throws ParseException {
        //2.1. 里程碑到达任务
        //2.1.1 到达里程碑的时候, 给所有子项目经理发一个工作任务, 要求进行里程碑总结---下发的任务出现在 项目任务管理 的面板中----里程碑文件和数据快照
        // 1)知道了projectId; 2)查出这个project的所有子项目; 3)得到所有子项目经理的id;4)给所有子项目经理发一条工作任务
        String taskName = jobItemsDto.getJobName();
        List<RdmsFileDto> fileList = jobItemsDto.getFileList();
        if(!CollectionUtils.isEmpty(fileList)){
            List<String> fileIds = fileList.stream().map(RdmsFileDto::getId).collect(Collectors.toList());
            String fileIdsStr = JSONObject.toJSONString(fileIds);
            jobItemsDto.setFileListStr(fileIdsStr);
        }

        List<ProjectStatusEnum> statusEnumList = new ArrayList<>();
        statusEnumList.add(ProjectStatusEnum.SETUPED);
        statusEnumList.add(ProjectStatusEnum.ONGOING);
        List<RdmsProjectSubprojectDto> subProjectListByParentId = rdmsProjectService.getSubprojectListByProjectIdAndStatus(jobItemsDto.getProjectId(), statusEnumList);
        if(!CollectionUtils.isEmpty(subProjectListByParentId)){
            for(RdmsProjectSubprojectDto subprojectDto: subProjectListByParentId){
                //给项目经理发一条项目任务工单
                if(ObjectUtils.isEmpty(jobItemsDto.getId())){
                    jobItemsDto.setId(UuidUtil.getShortUuid());
                }
                jobItemsDto.setJobName("里程碑任务:" + taskName);
                jobItemsDto.setSubprojectId(subprojectDto.getId());
                RdmsProject rdmsProject_topLever = rdmsProjectService.selectByPrimaryKey(subprojectDto.getProjectId());
                jobItemsDto.setProjectManagerId(rdmsProject_topLever.getProjectManagerId());
                jobItemsDto.setProductManagerId(rdmsProject_topLever.getProductManagerId());

                jobItemsDto.setExecutorId(subprojectDto.getProjectManagerId()); //由项目经理执行
                jobItemsDto.setNextNode(jobItemsDto.getProjectManagerId());
                jobItemsDto.setLoginUserId(jobItemsDto.getProjectManagerId());
                jobItemsDto.setManhour(0.0);
                jobItemsDto.setCustomerId(subprojectDto.getCustomerId());
                jobItemsDto.setType(JobItemTypeEnum.TASK_MILESTONE.getType()); //里程碑任务
                jobItemsDto.setProjectType(ProjectTypeEnum.SUB_PROJECT.getType());
                jobItemsDto.setAuxType(JobItemTypeEnum.NOTSET.getType());
                jobItemsDto.setNote(jobItemsDto.getMilestoneId()); //note记录里程碑Id
                rdmsJobItemService.saveJobitem(jobItemsDto); //会审查process记录, 所以不需要另外添加process保存
                //保存完一条后,将id清空
                jobItemsDto.setId(null);
            }
        }
        return subProjectListByParentId.size();
    }
    /**
     * 周期性的检测是否有里程碑到达,如果里程碑到达了,则创建一系列里程碑项目任务
     */
    public void milestoneCycleTask_jobTask() throws ParseException {
//        LOG.info("milestoneCycleTask被调用!!!");
        //1. 查看milestone表,查出 当前时间大于里程碑时间,并且里程碑评审状态处于 0 的记录
        Calendar referenceCalendar = Calendar.getInstance();
        referenceCalendar.setTime(new Date());
        referenceCalendar.add(Calendar.DATE, 10); //提前10天
        Date time_10 = referenceCalendar.getTime();

        RdmsMilestoneExample rdmsMilestoneExample = new RdmsMilestoneExample();
        rdmsMilestoneExample.createCriteria()
                .andReviewTimeLessThan(time_10)
                .andIsReviewedEqualTo(0)
                .andDeletedEqualTo("0");
        List<RdmsMilestone> rdmsMilestones = rdmsMilestoneMapper.selectByExample(rdmsMilestoneExample);
        //2. 周期性里程碑到达处理
        if(!CollectionUtils.isEmpty(rdmsMilestones)){
            for(RdmsMilestone milestone: rdmsMilestones){

                //2.1. 里程碑到达任务
                //2.1.1 到达里程碑的时候, 给所有子项目经理发一个工作任务, 要求进行里程碑总结---下发的任务出现在 项目任务管理 的面板中----里程碑文件和数据快照
                // 1)知道了projectId; 2)查出这个project的所有子项目; 3)得到所有子项目经理的id;4)给所有子项目经理发一条工作任务
                List<ProjectStatusEnum> statusEnumList = new ArrayList<>();
                statusEnumList.add(ProjectStatusEnum.SETUPED);
                statusEnumList.add(ProjectStatusEnum.ONGOING);
                List<RdmsProjectSubprojectDto> subProjectListByParentId = rdmsProjectService.getSubprojectListByProjectIdAndStatus(milestone.getProjectId(), statusEnumList);
                if(!CollectionUtils.isEmpty(subProjectListByParentId)){
                    for(RdmsProjectSubprojectDto subprojectDto: subProjectListByParentId){
                        //给项目经理发一条项目任务工单
                        RdmsJobItemDto jobItemsDto = new RdmsJobItemDto();
                        jobItemsDto.setId(UuidUtil.getShortUuid());
                        jobItemsDto.setJobName("里程碑:" + subprojectDto.getLabel() +"-"+ milestone.getMilestoneName()+ "-工作汇报");
                        jobItemsDto.setProjectId(subprojectDto.getProjectId());
                        jobItemsDto.setSubprojectId(subprojectDto.getId());
                        RdmsProject rdmsProject_topLever = rdmsProjectService.selectByPrimaryKey(subprojectDto.getProjectId());
                        jobItemsDto.setProjectManagerId(rdmsProject_topLever.getProjectManagerId());
                        jobItemsDto.setProductManagerId(rdmsProject_topLever.getProductManagerId());
                        if(ObjectUtils.isEmpty(milestone.getCheckOut())){
                            jobItemsDto.setTaskDescription("请对相应的里程碑阶段的工作执行情况进行工作总结和汇报，详细陈述： 1）当前里程碑阶段行程的工作成果；2）工作的完成情况，如果有工作未能如期完成，请提供补救措施等；3）提供相关工作交付的证明材料等。");
                        }else{
                            jobItemsDto.setTaskDescription(milestone.getCheckOut());
                        }
                        jobItemsDto.setExecutorId(subprojectDto.getProjectManagerId()); //由项目经理执行
                        RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(milestone.getProjectId());
                        jobItemsDto.setNextNode(rdmsProject.getProjectManagerId());
                        jobItemsDto.setManhour(0.0);
                        jobItemsDto.setPlanSubmissionTime(milestone.getReviewTime());
                        jobItemsDto.setCustomerId(subprojectDto.getCustomerId());
                        jobItemsDto.setLoginUserId(rdmsProject.getProjectManagerId());  //主项目经理
                        jobItemsDto.setType(JobItemTypeEnum.TASK_MILESTONE.getType()); //里程碑任务
                        jobItemsDto.setProjectType(ProjectTypeEnum.SUB_PROJECT.getType());
                        jobItemsDto.setFileList(null);
                        jobItemsDto.setNote(milestone.getId());  //note 记录里程碑Id
                        //调用JobitemController的 save 函数创建工作任务
                        rdmsJobItemService.saveJobitem(jobItemsDto); //会审查process记录, 所以不需要另外添加process保存
                    }

                    //将当前milestone的 isReviewed 值赋值为 1
                    milestone.setIsReviewed(1);
                    this.updateByPrimaryKeySelective(milestone);
                }

                //2.1.2 提示主项目经理, 是否有功能评审节点到达; 是否有子项目评审节点到达---给主项目经理发评审提醒工单
                //1) 查看所有当前里程碑的功能
                List<RdmsCharacter> characterListByMilestone = rdmsCharacterService.getCharacterListByMilestone_valid(milestone.getId());
                //2) 查看所有当前里程碑的子项目
                List<RdmsProjectSubproject> subProjectListByMilestone = rdmsProjectService.getSubProjectListByMilestone(milestone.getId());
                List<RdmsProjectSubproject> subProjectList = subProjectListByMilestone.stream()
                        .filter(item -> item.getStatus().equals(ProjectStatusEnum.SETUPED.getStatus())
                                || item.getStatus().equals(ProjectStatusEnum.ONGOING.getStatus()))
                        .collect(Collectors.toList());

                if(!CollectionUtils.isEmpty(characterListByMilestone) || !CollectionUtils.isEmpty(subProjectList)){
                    //给项目经理发评审通知工单
                    //创建工单: 工单在创建的时候， 执行人是工单的接收人  nextNode 是处理完提交给谁
                    RdmsJobItem notifyJobitem = new RdmsJobItem();
                    notifyJobitem.setId(null);
                    notifyJobitem.setJobSerial(null);
                    notifyJobitem.setJobName("有评审任务需要处理");
                    StringBuilder descriptionStr = new StringBuilder("当前里程碑存在:");
                    if(!CollectionUtils.isEmpty(characterListByMilestone)){
                        descriptionStr
                                .append("<br/>")
                                .append(characterListByMilestone.size())
                                .append(" 条功能需要评审, ")
                                .append(" 功能标题为: ").append("<br/>");

                        for(RdmsCharacter character: characterListByMilestone){
                            RdmsProjectSubprojectDto subprojectBySubprojectId = rdmsProjectService.getSubprojectBySubprojectId(character.getSubprojectId());
                            descriptionStr
                                    .append(character.getCharacterName())
                                    .append("( ")
                                    .append(character.getId())
                                    .append(" )")
                                    .append(", ")
                                    .append("功能隶属于子项目: ")
                                    .append(subprojectBySubprojectId.getLabel())
                                    .append("( ")
                                    .append(subprojectBySubprojectId.getProjectCode())
                                    .append(" )")
                                    .append("; ").append("<br/>");
                        }
                    }

                    if(!CollectionUtils.isEmpty(subProjectListByMilestone)){
                        descriptionStr
                                .append("<br/>")
                                .append(subProjectListByMilestone.size())
                                .append(" 个子项目需要评审, 子项目标题为: ").append("<br/>");

                        for(RdmsProjectSubproject subproject: subProjectListByMilestone){
                            descriptionStr
                                    .append(subproject.getLabel())
                                    .append("( ")
                                    .append(subproject.getProjectCode())
                                    .append(" )")
                                    .append("; ").append("<br/>");
                        }
                    }
                    notifyJobitem.setTaskDescription(descriptionStr.toString());
                    RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(milestone.getProjectId());
                    notifyJobitem.setCustomerId(rdmsProject.getCustomerId());
                    notifyJobitem.setPreProjectId(rdmsProject.getPreProjectId());
                    notifyJobitem.setProjectId(rdmsProject.getId());
                    notifyJobitem.setSubprojectId(rdmsProject.getId());
                    notifyJobitem.setProjectManagerId(rdmsProject.getProjectManagerId());
                    notifyJobitem.setProductManagerId(rdmsProject.getProductManagerId());
                    notifyJobitem.setProjectType(ProjectTypeEnum.SUB_PROJECT.getType());
                    notifyJobitem.setFileListStr("[]");
                    notifyJobitem.setManhour(0.0);

                    notifyJobitem.setExecutorId(rdmsProject.getProductManagerId());  //产品经理
                    notifyJobitem.setNextNode(null); //通知到了之后, 没有下一步执行人
                    notifyJobitem.setStatus(JobItemStatusEnum.HANDLING.getStatus());
                    notifyJobitem.setReviewResult(null);
                    notifyJobitem.setType(JobItemTypeEnum.MILESTONE_NOTIFY.getType());
                    notifyJobitem.setParentJobitemId(null);
                    notifyJobitem.setPropertyId(null);
                    notifyJobitem.setReviewWorkerIdStr(null);
                    notifyJobitem.setPlanSubmissionTime(milestone.getReviewTime());
                    String jobitemId = rdmsJobItemService.save(notifyJobitem);

                    RdmsJobItemProcess jobItemProcess = new RdmsJobItemProcess();
                    jobItemProcess.setJobItemId(jobitemId);
                    jobItemProcess.setExecutorId(null);
                    jobItemProcess.setNextNode(notifyJobitem.getNextNode());
                    long jobItemProcessCount = rdmsJobItemProcessService.getJobItemProcessCount(jobitemId);
                    jobItemProcess.setDeep((int)jobItemProcessCount);
                    jobItemProcess.setJobDescription("评审任务通过");
                    jobItemProcess.setFileListStr(null);
                    jobItemProcess.setActualSubmissionTime(new Date());
                    jobItemProcess.setProcessStatus(JobItemProcessStatusEnum.ISSUE.getStatus());
                    rdmsJobItemProcessService.save(jobItemProcess);
                }

            }
        }
    }

    /**
     * 周期性的检测是否有里程碑到达,如果里程碑到达了,则创建一个评审处理工作任务
     */
    public void milestoneCycleTask_reviewTask() throws ParseException {
//        LOG.info("milestoneCycleTask被调用!!!");
        //1. 查看milestone表,查出 当前时间大于里程碑时间,并且里程碑评审状态处于 0 的记录
        Calendar referenceCalendar = Calendar.getInstance();
        referenceCalendar.setTime(new Date());
        referenceCalendar.add(Calendar.DATE, 1);  //提前1天
        Date time_1 = referenceCalendar.getTime();

        RdmsMilestoneExample rdmsMilestoneExample = new RdmsMilestoneExample();
        rdmsMilestoneExample.createCriteria()
                .andReviewTimeLessThan(time_1)
                .andIsReviewedEqualTo(1)
                .andDeletedEqualTo("0");
        List<RdmsMilestone> rdmsMilestones = rdmsMilestoneMapper.selectByExample(rdmsMilestoneExample);
        //2. 周期性里程碑到达处理
        if(!CollectionUtils.isEmpty(rdmsMilestones)){
            for(RdmsMilestone milestone: rdmsMilestones){
                // 根据里程碑对应的项目,查出项目经理, 给项目经理发一个里程碑评审发起任务
                RdmsProject mainProject = rdmsProjectService.selectByPrimaryKey(milestone.getProjectId());
                if(!ObjectUtils.isEmpty(mainProject)){
                    //给项目经理发一条评审发起任务工单
                    RdmsJobItemDto jobItemsDto = new RdmsJobItemDto();
                    jobItemsDto.setId(UuidUtil.getShortUuid());
                    jobItemsDto.setJobName("发起评审:" + milestone.getMilestoneName() + "-里程碑评审");
                    jobItemsDto.setProjectId(mainProject.getId());
                    jobItemsDto.setSubprojectId(mainProject.getId());
                    jobItemsDto.setProjectManagerId(mainProject.getProjectManagerId());
                    jobItemsDto.setProductManagerId(mainProject.getProductManagerId());
                    jobItemsDto.setTaskDescription("对" + milestone.getMilestoneName() +"的完成情况, 进行阶段评审.");

                    jobItemsDto.setExecutorId(mainProject.getProjectManagerId()); //由项目经理执行
                    jobItemsDto.setManhour(0.0);
                    jobItemsDto.setPlanSubmissionTime(milestone.getReviewTime());
                    jobItemsDto.setCustomerId(mainProject.getCustomerId());
                    RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(milestone.getProjectId());
                    jobItemsDto.setLoginUserId(rdmsProject.getProjectManagerId());  //主项目经理
                    jobItemsDto.setType(JobItemTypeEnum.REVIEW_MILESTONE_PRE.getType()); //里程碑发起评审
                    jobItemsDto.setProjectType(ProjectTypeEnum.SUB_PROJECT.getType());
                    jobItemsDto.setFileList(null);
                    jobItemsDto.setDocType(DocTypeEnum.MILESTONE.getType());
                    jobItemsDto.setNote(milestone.getId());
                    //调用JobitemController的 save 函数创建工作任务
                    rdmsJobItemService.saveJobitem(jobItemsDto);

                    //将当前milestone的 isReviewed 值赋值为 2 --- 发起评审任务
                    milestone.setIsReviewed(2);
                    this.updateByPrimaryKeySelective(milestone);
                }
            }
        }
    }

    @Transactional
    public List<RdmsMilestoneDto> listByProjectId(String projectId){
        RdmsMilestoneExample rdmsMilestoneExample = new RdmsMilestoneExample();
        rdmsMilestoneExample.setOrderByClause("review_time asc");
        rdmsMilestoneExample.createCriteria().andProjectIdEqualTo(projectId).andDeletedEqualTo("0");
        List<RdmsMilestone> rdmsMilestones = rdmsMilestoneMapper.selectByExample(rdmsMilestoneExample);

        List<RdmsMilestoneDto> rdmsMilestoneDtos = CopyUtil.copyList(rdmsMilestones, RdmsMilestoneDto.class);
        //对日期时间进行格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(RdmsMilestoneDto rdmsMilestoneDto : rdmsMilestoneDtos){
            rdmsMilestoneDto.setReviewTimeStr(sdf.format(rdmsMilestoneDto.getReviewTime()));
            rdmsMilestoneDto.setCreateTimeStr(sdf.format(rdmsMilestoneDto.getCreateTime()));

        }
        return rdmsMilestoneDtos;
    }

    public List<RdmsMilestoneDto> listSetupedProjectMilestonesByCustomerId(String customerId) {
        List<ProjectStatusEnum> statusEnumList = new ArrayList<>();
        statusEnumList.add(ProjectStatusEnum.SETUPED);
        List<RdmsProjectDto> projectListByCustomerId = rdmsProjectService.getProjectListByCustomerIdAndStatusList(customerId, statusEnumList);
        if (!CollectionUtils.isEmpty(projectListByCustomerId)) {
            List<String> projectIdList = projectListByCustomerId.stream().map(RdmsProjectDto::getId).collect(Collectors.toList());
            RdmsMilestoneExample milestoneExample = new RdmsMilestoneExample();
            milestoneExample.setOrderByClause("review_time asc");
            RdmsMilestoneExample.Criteria criteria = milestoneExample.createCriteria()
                    .andProjectIdIn(projectIdList)
                    .andReviewTimeGreaterThan(new Date())
                    .andDeletedEqualTo("0");
            List<RdmsMilestone> rdmsMilestones = rdmsMilestoneMapper.selectByExample(milestoneExample);
            List<RdmsMilestoneDto> rdmsMilestoneDtos = CopyUtil.copyList(rdmsMilestones, RdmsMilestoneDto.class);
            for (RdmsMilestoneDto rdmsMilestoneDto : rdmsMilestoneDtos) {
                RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(rdmsMilestoneDto.getProjectId());
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(rdmsProject.getProductManagerId());
                rdmsMilestoneDto.setProductManagerName(rdmsCustomerUser.getTrueName());
                rdmsMilestoneDto.setProjectName(rdmsProject.getProjectName());
                rdmsMilestoneDto.setProjectCode(rdmsProject.getProjectCode());
            }
            return rdmsMilestoneDtos;
        }
        return null;
    }

    public RdmsMilestoneDto selectByPrimaryKey(String id){
        RdmsMilestone rdmsMilestone = rdmsMilestoneMapper.selectByPrimaryKey(id);
        RdmsMilestoneDto rdmsMilestoneDto = CopyUtil.copy(rdmsMilestone, RdmsMilestoneDto.class);
        //对日期时间进行格式化
        if(!ObjectUtils.isEmpty(rdmsMilestoneDto)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if(rdmsMilestoneDto.getReviewTime() != null){
                rdmsMilestoneDto.setReviewTimeStr(sdf.format(rdmsMilestoneDto.getReviewTime()));
            }
            rdmsMilestoneDto.setCreateTimeStr(sdf.format(rdmsMilestoneDto.getCreateTime()));
        }
        return rdmsMilestoneDto;
    }

    public List<RdmsMilestoneDto> getMilestoneListBetweenStartEndTime(String projectId ,Date startTime, Date endTime){
        long l = endTime.getTime() + (long) 10;
        endTime.setTime(l);

        RdmsMilestoneExample milestoneExample = new RdmsMilestoneExample();
        RdmsMilestoneExample.Criteria criteria = milestoneExample.createCriteria().andProjectIdEqualTo(projectId).andDeletedEqualTo("0");
        criteria.andReviewTimeBetween(startTime, endTime);
        List<RdmsMilestone> rdmsMilestones = rdmsMilestoneMapper.selectByExample(milestoneExample);
        return CopyUtil.copyList(rdmsMilestones, RdmsMilestoneDto.class);
    }

    public RdmsMilestone selectReleaseMilestone(String projectId){
        RdmsMilestoneExample milestoneExample = new RdmsMilestoneExample();
        milestoneExample.createCriteria().andIsReleaseEqualTo("1").andProjectIdEqualTo(projectId).andDeletedEqualTo("0");
        List<RdmsMilestone> rdmsMilestones = rdmsMilestoneMapper.selectByExample(milestoneExample);
        if(! CollectionUtils.isEmpty(rdmsMilestones)){
            return rdmsMilestones.get(0);
        }else{
            return null;
        }
    }

    public String createReleaseMilestone(String projectId, Date releaseTime){
        RdmsMilestone milestone = new RdmsMilestone();
        milestone.setProjectId(projectId);
        milestone.setMilestoneName("发布里程碑");
        milestone.setCheckOut("通过型式试验");
        milestone.setReviewTime(releaseTime);
        milestone.setIsRelease("1");
        milestone.setCreateTime(new Date());
        milestone.setDeleted("0");
        return this.insert(milestone);
    }

    /**
     * 保存
     */
    @Transactional
    public String save(RdmsMilestoneDto milestoneDto) {
        RdmsMilestone milestone = CopyUtil.copy(milestoneDto, RdmsMilestone.class);
        if(milestone.getCreateTime()==null){
            milestone.setCreateTime(new Date());
        }
        milestone.setDeleted("0");

        if(ObjectUtils.isEmpty(milestone.getId())){
            if(milestone.getIsRelease() !=null && milestone.getIsRelease().equals("1")){
                //发布里程碑
                RdmsMilestone rdmsMilestone = this.selectReleaseMilestone(milestone.getProjectId());
                if(ObjectUtils.isEmpty(rdmsMilestone)){
                    //如果发布里程碑不存
                    //修改项目的发布时间
                    RdmsProject projectInfo = new RdmsProject();
                    projectInfo.setId(milestone.getProjectId());
                    projectInfo.setReleaseTime(milestone.getReviewTime());
                    rdmsProjectService.updateByPrimaryKeySelective(projectInfo);

                    return this.insert(milestone);

                }else{
                    //如果已经有发布里程碑
                    //修改项目的发布时间
                    RdmsProject projectInfo = new RdmsProject();
                    projectInfo.setId(milestone.getProjectId());
                    projectInfo.setReleaseTime(milestone.getReviewTime());
                    rdmsProjectService.updateByPrimaryKeySelective(projectInfo);

                    milestone.setId(rdmsMilestone.getId());
                    milestone.setCreateTime(rdmsMilestone.getCreateTime());
                    milestone.setIsRelease("1");
                    return this.update(milestone);
                }
            }else{
                //如果不是发布里程碑
                //查询是否有发布里程碑
                RdmsMilestone rdmsMilestone = this.selectReleaseMilestone(milestone.getProjectId());
                if(ObjectUtils.isEmpty(rdmsMilestone)){
                    this.createReleaseMilestone(milestone.getProjectId(), milestoneDto.getReleaseTime());
                }else{
                    //判断新传入的发布时间是否大于数据库发布里程碑的发布时间,如果大于,则更新发布时间
                    if(milestoneDto.getReleaseTime().getTime() > rdmsMilestone.getReviewTime().getTime()){
                        rdmsMilestone.setReviewTime(milestoneDto.getReleaseTime());
                        this.update(rdmsMilestone);
                    }
                }
                return this.insert(milestone);
            }
        }else{
            RdmsMilestoneExample milestoneExample = new RdmsMilestoneExample();
            milestoneExample.createCriteria().andIdEqualTo(milestone.getId());
            List<RdmsMilestone> rdmsMilestones = rdmsMilestoneMapper.selectByExample(milestoneExample);
            if(!rdmsMilestones.isEmpty()){
                if(milestone.getIsRelease().equals("1")){
                    //修改项目的发布时间
                    RdmsProject projectInfo = new RdmsProject();
                    projectInfo.setId(milestone.getProjectId());
                    projectInfo.setReleaseTime(milestone.getReviewTime());
                    rdmsProjectService.updateByPrimaryKeySelective(projectInfo);
                    //更新里程碑信息
                    this.updateByPrimaryKeySelective(milestone);
                    return milestone.getId();
                }else{
                    this.updateByPrimaryKeySelective(milestone);
                    return milestone.getId();
                }
            }else{
                throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
            }
        }
    }

    /**
     * 里程碑编辑
     */
    @Transactional
    public String edit(RdmsMilestoneDto milestoneDto) {
        RdmsMilestone milestone = CopyUtil.copy(milestoneDto, RdmsMilestone.class);
        //判断是不是发布里程碑
        RdmsMilestoneDto rdmsMilestoneDto = this.selectByPrimaryKey(milestone.getId());
        if(rdmsMilestoneDto.getIsRelease()!=null && rdmsMilestoneDto.getIsRelease().equals("1")){
            //发布里程碑
            //读取所有的里程碑时间,去除发布里程碑时间,然后排序,找到最后一个普通里程碑时间
            RdmsMilestoneExample milestoneExample = new RdmsMilestoneExample();
            milestoneExample.setOrderByClause("review_time desc");
            milestoneExample.createCriteria().andProjectIdEqualTo(milestone.getProjectId()).andDeletedEqualTo("0").andIsReleaseIsNull();
            List<RdmsMilestone> rdmsMilestoneList = rdmsMilestoneMapper.selectByExample(milestoneExample);
            Date refTime;
            if(! CollectionUtils.isEmpty(rdmsMilestoneList)){
                refTime = rdmsMilestoneList.get(0).getReviewTime();
            }else{
                refTime = new Date();
            }
            //发布里程碑的时间不能早于前一个里程碑
            if(milestone.getReviewTime().getTime() > refTime.getTime()){
                //修改项目的发布时间
                RdmsProject projectInfo = new RdmsProject();
                projectInfo.setId(milestone.getProjectId());
                projectInfo.setReleaseTime(milestone.getReviewTime());
                rdmsProjectService.updateByPrimaryKeySelective(projectInfo);

                {//如果有其他项目的目标里程碑是这个里程碑, 则对应修改releaseTime
                    List<RdmsProjectSubproject> subprojectListByTargetMilestone = rdmsSubprojectService.getSubprojectListByTargetMilestone(milestone.getId());
                    if(!CollectionUtils.isEmpty(subprojectListByTargetMilestone)){
                        for(RdmsProjectSubproject projectSubproject: subprojectListByTargetMilestone){
                            projectSubproject.setReleaseTime(milestone.getReviewTime());
                            rdmsSubprojectService.updateByPrimaryKeySelective(projectSubproject);
                        }
                    }
                }
                {//如果有功能开发的目标里程碑是这个里程碑, 则对应修改结束时间
                    List<RdmsCharacter> characterListByMilestone = rdmsCharacterService.getCharacterListByMilestone(milestone.getId());
                    if(!CollectionUtils.isEmpty(characterListByMilestone)){
                        for(RdmsCharacter character: characterListByMilestone){
                            character.setPlanCompleteTime(milestone.getReviewTime());
                            rdmsCharacterService.updateByPrimaryKeySelective(character);
                        }
                    }
                }

                return this.updateByPrimaryKeySelective(milestone);
            }else{
                throw new BusinessException(BusinessExceptionCode.RELEASE_TIME_CANNOT_BEFORE_OTHER_MILESTONE);
            }

        }else{
            //不是发布里程碑
            //不是发布里程碑的时间不能大于发布里程碑
            RdmsMilestoneExample milestoneExample = new RdmsMilestoneExample();
            milestoneExample.createCriteria().andIsReleaseEqualTo("1").andDeletedEqualTo("0").andProjectIdEqualTo(milestone.getProjectId());
            List<RdmsMilestone> rdmsMilestoneList = rdmsMilestoneMapper.selectByExample(milestoneExample);
            Date refTime;
            if(! CollectionUtils.isEmpty(rdmsMilestoneList)){
                refTime = rdmsMilestoneList.get(0).getReviewTime();
                //发布里程碑的时间不能早于前一个里程碑
                if(milestone.getReviewTime().getTime() > refTime.getTime()){
                    throw new BusinessException(BusinessExceptionCode.NORMAL_MILESTONE_TIME_CANNOT_AFTER_RELEASE);
                }else{
                    {//如果有其他项目的目标里程碑是这个里程碑, 则对应修改releaseTime
                        List<RdmsProjectSubproject> subprojectListByTargetMilestone = rdmsSubprojectService.getSubprojectListByTargetMilestone(milestone.getId());
                        if(!CollectionUtils.isEmpty(subprojectListByTargetMilestone)){
                            for(RdmsProjectSubproject projectSubproject: subprojectListByTargetMilestone){
                                projectSubproject.setReleaseTime(milestone.getReviewTime());
                                rdmsSubprojectService.updateByPrimaryKeySelective(projectSubproject);
                            }
                        }
                    }
                    {//如果有功能开发的目标里程碑是这个里程碑, 则对应修改结束时间
                        List<RdmsCharacter> characterListByMilestone = rdmsCharacterService.getCharacterListByMilestone(milestone.getId());
                        if(!CollectionUtils.isEmpty(characterListByMilestone)){
                            for(RdmsCharacter character: characterListByMilestone){
                                character.setPlanCompleteTime(milestone.getReviewTime());
                                rdmsCharacterService.updateByPrimaryKeySelective(character);
                            }
                        }
                    }
                    return this.updateByPrimaryKeySelective(milestone);
                }
            }else{
                throw new BusinessException(BusinessExceptionCode.HAVE_NOT_RELEASE_MILESTONE);
            }
        }
    }

    /**
     * 新增
     */
    private String insert(RdmsMilestone milestone) {
        if(ObjectUtils.isEmpty(milestone.getId())){  //当前端页面给出projectID时,将不为空
            milestone.setId(UuidUtil.getShortUuid());
        }
        milestone.setCreateTime(new Date());
        milestone.setIsReviewed(0);
        RdmsMilestoneExample milestoneExample = new RdmsMilestoneExample();
        milestoneExample.createCriteria().andIdEqualTo(milestone.getId());
        List<RdmsMilestone> rdmsMilestones = rdmsMilestoneMapper.selectByExample(milestoneExample);
        if(!rdmsMilestones.isEmpty()){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            rdmsMilestoneMapper.insert(milestone);
            return milestone.getId();
        }
    }

    /**
     * 更新
     */
    private String update(RdmsMilestone milestone) {
        RdmsMilestoneExample milestoneExample = new RdmsMilestoneExample();
        milestoneExample.createCriteria().andIdEqualTo(milestone.getId());
        List<RdmsMilestone> rdmsMilestones = rdmsMilestoneMapper.selectByExample(milestoneExample);
        if(!rdmsMilestones.isEmpty()){
            rdmsMilestoneMapper.updateByPrimaryKey(milestone);
            return milestone.getId();
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

    /**
     * 选择性更新
     */
    private String updateByPrimaryKeySelective(RdmsMilestone milestone) {
        RdmsMilestoneExample milestoneExample = new RdmsMilestoneExample();
        milestoneExample.createCriteria().andIdEqualTo(milestone.getId());
        List<RdmsMilestone> rdmsMilestones = rdmsMilestoneMapper.selectByExample(milestoneExample);
        if(!rdmsMilestones.isEmpty()){
            rdmsMilestoneMapper.updateByPrimaryKeySelective(milestone);
            return milestone.getId();
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsMilestoneExample milestoneExample = new RdmsMilestoneExample();
        milestoneExample.createCriteria().andIdEqualTo(id);
        List<RdmsMilestone> rdmsMilestones = rdmsMilestoneMapper.selectByExample(milestoneExample);
        if(!rdmsMilestones.isEmpty()){
            RdmsMilestone milestone = rdmsMilestones.get(0);
            milestone.setDeleted("1");
            rdmsMilestoneMapper.updateByPrimaryKey(milestone);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

}
