/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.*;
import com.course.server.dto.rdms.RdmsFileDto;
import com.course.server.dto.rdms.RdmsJobItemDto;
import com.course.server.dto.rdms.RdmsJobItemGanttDto;
import com.course.server.enums.rdms.JobItemStatusEnum;
import com.course.server.enums.rdms.JobItemTypeEnum;
import com.course.server.enums.rdms.OperateTypeEnum;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsJobItemGanttMapper;
import com.course.server.service.util.CodeUtil;
import com.course.server.service.util.DateUtil;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RdmsJobItemGanttService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsJobItemGanttService.class);

    @Resource
    private RdmsJobItemGanttMapper rdmsJobItemGanttMapper;
    @Autowired
    private RdmsSubprojectService rdmsSubprojectService;
    @Autowired
    private RdmsManhourService rdmsManhourService;
    @Autowired
    private RdmsJobItemService rdmsJobItemService;
    @Autowired
    private RdmsGanttService rdmsGanttService;


    @Transactional
    public void issuePlanTask(String customerId) throws ParseException {
        //根据当天日期查找是否有符合条件的工单
        RdmsJobItemGanttExample ganttExample = new RdmsJobItemGanttExample();
        ganttExample.createCriteria()
                .andPlanStartTimeLessThan(new Date())
                .andCustomerIdEqualTo(customerId)
                .andStatusEqualTo(JobItemStatusEnum.PLAN.getStatus())
                .andDeletedEqualTo(0);
        List<RdmsJobItemGantt> rdmsJobItemGantts = rdmsJobItemGanttMapper.selectByExample(ganttExample);
        if(!CollectionUtils.isEmpty(rdmsJobItemGantts)){
            for(RdmsJobItemGantt rdmsJobItemGantt : rdmsJobItemGantts){
                //下发项目任务
                RdmsJobItemDto jobItemDto = CopyUtil.copy(rdmsJobItemGantt, RdmsJobItemDto.class);
                //下发工作任务
                //将manhour转换为 原始输入 数据
                jobItemDto.setManhour(rdmsManhourService.transformToWorkerManhour(rdmsJobItemGantt.getManhour(), rdmsJobItemGantt.getExecutorId(), rdmsJobItemGantt.getCustomerId(), OperateTypeEnum.DEVELOP));
                jobItemDto.setType(JobItemTypeEnum.TASK_SUBP.getType());
                jobItemDto.setLoginUserId(rdmsJobItemGantt.getCreaterId());
                jobItemDto.setAuxType(JobItemTypeEnum.NOTSET.getType());
                jobItemDto.setPlanSubmissionTime(DateUtil.subtractOneDay(jobItemDto.getPlanSubmissionTime()));  //保存的时候会加将近一天,所以这里需要减去一天
                jobItemDto.setSettleAccounts(0);
                rdmsJobItemService.saveJobitem(jobItemDto);
                //将jobitemGantt表中的相应条目设置为 COMPLETED 状态
                rdmsJobItemGantt.setStatus(JobItemStatusEnum.COMPLETED.getStatus());
                this.update(rdmsJobItemGantt);
                //将下发的任务信息保存到gantt表中
                {
                    RdmsGantt gantt = new RdmsGantt();
                    gantt.setId(rdmsJobItemGantt.getId());  //gantt的Id是任务Id
                    gantt.setParent(rdmsJobItemGantt.getSubprojectId());
                    gantt.setJobitemId(rdmsJobItemGantt.getId());
                    gantt.setCharacterId(rdmsJobItemGantt.getCharacterId());
                    gantt.setSubprojectId(rdmsJobItemGantt.getSubprojectId());
                    gantt.setProjectId(rdmsJobItemGantt.getProjectId());
                    gantt.setPreprojectId(rdmsJobItemGantt.getPreProjectId());
                    gantt.setCustomerId(rdmsJobItemGantt.getCustomerId());
                    gantt.setCreaterId(rdmsJobItemGantt.getCreaterId());
                    gantt.setExecutorId(rdmsJobItemGantt.getExecutorId());
                    gantt.setText(rdmsJobItemGantt.getJobName());
                    gantt.setStartDate(rdmsJobItemGantt.getPlanStartTime());
                    //用计划提交的日期减去创建的日期
                    gantt.setDuration((int) rdmsGanttService.calculateDuration(rdmsJobItemGantt.getPlanStartTime(), rdmsJobItemGantt.getPlanSubmissionTime()));
                    gantt.setManhour(rdmsJobItemGantt.getManhour());
                    gantt.setProgress(0.0);
                    gantt.setStatus("going");
                    gantt.setType("task");
                    gantt.setOpen(0);
                    gantt.setDeleted(0);

                    if (gantt.getDuration().doubleValue() > 0) {
                        rdmsGanttService.save(gantt);
                    }

                }
            }
        }
    }

    public List<RdmsJobItemGantt> getJobitemListBySubprojectId(String subprojectId){
        RdmsJobItemGanttExample jobItemGanttExample = new RdmsJobItemGanttExample();
        jobItemGanttExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andStatusEqualTo(JobItemStatusEnum.PLAN.getStatus())  //计划任务下发后,就标记为discard
                .andDeletedEqualTo(0);
        return rdmsJobItemGanttMapper.selectByExample(jobItemGanttExample);
    }

    public RdmsJobItemGantt selectByPrimaryKey(String id) {
        return rdmsJobItemGanttMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public String saveJobitemToPlan(RdmsJobItemGanttDto jobItemGanttDto){
        if(ObjectUtils.isEmpty(jobItemGanttDto)){
            return null;
        }
        ValidatorUtil.require(jobItemGanttDto.getId(),"工单ID");
        ValidatorUtil.require(jobItemGanttDto.getProjectId(),"项目ID");
        ValidatorUtil.require(jobItemGanttDto.getSubprojectId(),"子项目ID");
        ValidatorUtil.require(jobItemGanttDto.getProjectManagerId(),"项目经理ID");
        ValidatorUtil.require(jobItemGanttDto.getJobName(),"工单名称");
        ValidatorUtil.require(jobItemGanttDto.getTaskDescription(),"任务描述");
        ValidatorUtil.require(jobItemGanttDto.getExecutorId(),"执行者ID");
        ValidatorUtil.require(jobItemGanttDto.getManhour(),"工单工时");
        ValidatorUtil.require(jobItemGanttDto.getPlanStartTime(),"计划开始时间");
        ValidatorUtil.require(jobItemGanttDto.getPlanSubmissionTime(),"计划提交时间");
        ValidatorUtil.require(jobItemGanttDto.getCustomerId(),"客户ID");
        ValidatorUtil.require(jobItemGanttDto.getLoginUserId(),"创建者ID");
        ValidatorUtil.require(jobItemGanttDto.getType(),"任务类型");
        ValidatorUtil.require(jobItemGanttDto.getProjectType(),"项目类型");

        //加1天
        jobItemGanttDto.setPlanSubmissionTime(DateUtil.addOneDay(jobItemGanttDto.getPlanSubmissionTime()));
        RdmsJobItemGantt rdmsJobItemGantt = CopyUtil.copy(jobItemGanttDto, RdmsJobItemGantt.class);

//        ValidatorUtil.require(jobItemGanttDto.getJobSerial(),"工单编号");
        rdmsJobItemGantt.setJobSerial(CodeUtil.randomJobNum());

//        ValidatorUtil.require(jobItemGanttDto.getPreProjectId(),"前置项目ID");
        RdmsProjectSubproject rdmsProjectSubproject = rdmsSubprojectService.selectByPrimaryKey(jobItemGanttDto.getSubprojectId());
        if(!ObjectUtils.isEmpty(rdmsProjectSubproject)){
            rdmsJobItemGantt.setPreProjectId(rdmsProjectSubproject.getPreProjectId());
        }

//        ValidatorUtil.require(jobItemGanttDto.getCharacterId(),"角色ID");
        rdmsJobItemGantt.setCharacterId(null);
//        ValidatorUtil.require(jobItemGanttDto.getCreaterId(),"创建者ID");
        rdmsJobItemGantt.setCreaterId(jobItemGanttDto.getLoginUserId());
//        ValidatorUtil.require(jobItemGanttDto.getParentJobitemId(),"父任务ID");
        rdmsJobItemGantt.setParentJobitemId(null);
//        ValidatorUtil.require(jobItemGanttDto.getProductManagerId(),"产品经理ID");
        rdmsJobItemGantt.setProductManagerId(rdmsProjectSubproject.getProductManagerId());
//        ValidatorUtil.require(jobItemGanttDto.getTestManagerId(),"测试经理ID");
        rdmsJobItemGantt.setTestManagerId(rdmsProjectSubproject.getTestManagerId());
//        ValidatorUtil.require(jobItemGanttDto.getNextNode(),"下一节点");
        rdmsJobItemGantt.setNextNode(rdmsJobItemGantt.getCreaterId());
//        ValidatorUtil.require(jobItemGanttDto.getStatus(),"状态");
        rdmsJobItemGantt.setStatus(JobItemStatusEnum.PLAN.getStatus());
//        ValidatorUtil.require(jobItemGanttDto.getFileListStr(),"附件");
        if(!CollectionUtils.isEmpty(jobItemGanttDto.getFileList())){
            List<String> fileIdList = jobItemGanttDto.getFileList().stream().map(RdmsFileDto::getId).collect(Collectors.toList());
            String jsonString = JSON.toJSONString(fileIdList);
            rdmsJobItemGantt.setFileListStr(jsonString);
        }
//        ValidatorUtil.require(jobItemGanttDto.getStandManhourFee(),"标准工时费");
        Double v = rdmsManhourService.transformToStandManhour(rdmsJobItemGantt.getManhour(), rdmsJobItemGantt.getExecutorId(), rdmsJobItemGantt.getCustomerId(), OperateTypeEnum.DEVELOP);
        rdmsJobItemGantt.setManhour(v);
        RdmsManhourStandard standardManhourByCustomerId = rdmsManhourService.getStandardManhourByCustomerId(rdmsJobItemGantt.getCustomerId());
        rdmsJobItemGantt.setStandManhourFee(standardManhourByCustomerId.getDevManhourFee());

        return this.save(rdmsJobItemGantt);
    }

    /**
     * 保存
     */
    public String save(RdmsJobItemGantt rdmsJobItemGantt) {
        rdmsJobItemGantt.setDeleted(0);
        if (!ObjectUtils.isEmpty(rdmsJobItemGantt) && !ObjectUtils.isEmpty(rdmsJobItemGantt.getId())) {
            RdmsJobItemGantt jobItemGantt = this.selectByPrimaryKey(rdmsJobItemGantt.getId());
            if (!ObjectUtils.isEmpty(jobItemGantt)) {
                return this.update(rdmsJobItemGantt);
            } else {
                return this.insert(rdmsJobItemGantt);
            }
        } else {
            return this.insert(rdmsJobItemGantt);
        }

    }

    /**
     * 新增
     */
    private String insert(RdmsJobItemGantt rdmsJobItemGantt) {
        if(ObjectUtils.isEmpty(rdmsJobItemGantt.getId())){  //当前端页面给出projectID时,将不为空
            rdmsJobItemGantt.setId(UuidUtil.getShortUuid());
        }
        RdmsJobItemGantt jobItemGantt = this.selectByPrimaryKey(rdmsJobItemGantt.getId());
        if(!ObjectUtils.isEmpty(jobItemGantt)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            rdmsJobItemGantt.setCreateTime(new Date());
            rdmsJobItemGantt.setUpdateTime(new Date());
            rdmsJobItemGanttMapper.insert(rdmsJobItemGantt);
            return rdmsJobItemGantt.getId();
        }
    }

    /**
     * 更新
     */
    private String update(RdmsJobItemGantt rdmsJobItemGantt) {
        if(ObjectUtils.isEmpty(rdmsJobItemGantt) || ObjectUtils.isEmpty(rdmsJobItemGantt.getId())){
            throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
        }
        RdmsJobItemGantt jobItemGantt = this.selectByPrimaryKey(rdmsJobItemGantt.getId());
        if(!ObjectUtils.isEmpty(jobItemGantt)){
            rdmsJobItemGantt.setId(jobItemGantt.getId());
            rdmsJobItemGanttMapper.updateByPrimaryKey(rdmsJobItemGantt);
            return rdmsJobItemGantt.getId();
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsJobItemGantt jobItemGantt = rdmsJobItemGanttMapper.selectByPrimaryKey(id);
        if(!ObjectUtils.isEmpty(jobItemGantt)){
            jobItemGantt.setDeleted(1);
            this.update(jobItemGantt);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

}
