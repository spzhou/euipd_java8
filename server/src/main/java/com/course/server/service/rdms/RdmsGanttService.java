/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.course.server.domain.*;
import com.course.server.dto.rdms.*;
import com.course.server.enums.rdms.CharacterStatusEnum;
import com.course.server.enums.rdms.JobItemStatusEnum;
import com.course.server.enums.rdms.ProjectStatusEnum;
import com.course.server.enums.rdms.ProjectTypeEnum;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsGanttMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RdmsGanttService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsGanttService.class);

    @Resource
    private RdmsGanttMapper rdmsGanttMapper;
    @Autowired
    private RdmsJobItemService rdmsJobItemService;
    @Autowired
    private RdmsSubprojectService rdmsSubprojectService;
    @Autowired
    private RdmsMilestoneService rdmsMilestoneService;
    @Autowired
    private RdmsProjectService rdmsProjectService;
    @Autowired
    private RdmsCharacterService rdmsCharacterService;
    @Autowired
    private RdmsJobItemGanttService rdmsJobItemGanttService;
    @Autowired
    private RdmsCustomerUserService rdmsCustomerUserService;


    @Transactional
    public List<RdmsGanttDto> getGanttListBySubprojectId(String subprojectId) {
        RdmsGanttExample ganttExample = new RdmsGanttExample();
        ganttExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andDeletedEqualTo(0);
        List<RdmsGantt> rdmsGantts = rdmsGanttMapper.selectByExample(ganttExample);
        RdmsProjectSubproject rdmsProjectSubproject = rdmsSubprojectService.selectByPrimaryKey(subprojectId);
        if (!ObjectUtils.isEmpty(rdmsProjectSubproject)) {
            //添加里程碑
            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(rdmsProjectSubproject.getProjectId());
            if (!ObjectUtils.isEmpty(rdmsProject)
                    && !ObjectUtils.isEmpty(rdmsProjectSubproject)
                    && !ObjectUtils.isEmpty(rdmsProjectSubproject.getReleaseTime())
                    && !ObjectUtils.isEmpty(rdmsProject.getSetupedTime())) {
                List<RdmsMilestoneDto> milestoneList = rdmsMilestoneService.getMilestoneListBetweenStartEndTime(rdmsProject.getId(), rdmsProject.getSetupedTime(), rdmsProjectSubproject.getReleaseTime());
                if (!CollectionUtils.isEmpty(milestoneList)) {
                    List<String> collect = milestoneList.stream().map(RdmsMilestoneDto::getId).collect(Collectors.toList());
                    RdmsGanttExample ganttExample1 = new RdmsGanttExample();
                    ganttExample1.createCriteria()
                            .andIdIn(collect)
                            .andDeletedEqualTo(0);
                    List<RdmsGantt> rdmsGantts1 = rdmsGanttMapper.selectByExample(ganttExample1);
                    rdmsGantts.addAll(rdmsGantts1);
                }
            }
            //添加功能单元开发
            //TODO 在子项目中将功能单元 投入 研发过程 时, 需要向gantt中写入一条数据, 这里用于读出这些数据
            List<RdmsCharacterDto> characterList = rdmsCharacterService.getCharacterListBySubprojectId(rdmsProjectSubproject.getId(), null);
            if (!ObjectUtils.isEmpty(characterList)) {
                List<String> characterIdList = characterList.stream().map(RdmsCharacterDto::getId).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(characterIdList)) {
                    RdmsGanttExample ganttExample1 = new RdmsGanttExample();
                    ganttExample1.createCriteria()
                            .andIdIn(characterIdList)
                            .andDeletedEqualTo(0);
                    List<RdmsGantt> rdmsGantts1 = rdmsGanttMapper.selectByExample(ganttExample1);
                    rdmsGantts.addAll(rdmsGantts1);
                }
            }
        }
        //读出尚处于计划表中的数据
        List<RdmsJobItemGantt> jobitemList = rdmsJobItemGanttService.getJobitemListBySubprojectId(subprojectId);
        List<RdmsGantt> rdmsGantts2 = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jobitemList)) {
            //构建任务的临时gantt记录  只有任务下发下去, 才将记录保存到gantt表中和正在执行的任务记录中去
            jobitemList.forEach(jobItem -> {
                RdmsGantt gantt = new RdmsGantt();
                gantt.setId(jobItem.getId());  //gantt的Id是任务Id
                gantt.setParent(jobItem.getSubprojectId());
                gantt.setJobitemId(jobItem.getId());
                gantt.setCharacterId(jobItem.getCharacterId());
                gantt.setSubprojectId(jobItem.getSubprojectId());
                gantt.setProjectId(jobItem.getProjectId());
                gantt.setPreprojectId(jobItem.getPreProjectId());
                gantt.setCustomerId(jobItem.getCustomerId());
                gantt.setCreaterId(jobItem.getCreaterId());
                gantt.setExecutorId(jobItem.getExecutorId());
                gantt.setText(jobItem.getJobName());
                gantt.setStartDate(jobItem.getPlanStartTime());
                //用计划提交的日期减去创建的日期
                gantt.setDuration((int) calculateDuration(jobItem.getPlanStartTime(), jobItem.getPlanSubmissionTime()));
                gantt.setManhour(jobItem.getManhour());
                gantt.setProgress(0.0);
                gantt.setStatus("plan");
                gantt.setType("task");
                gantt.setOpen(0);
                gantt.setDeleted(0);

                if (gantt.getDuration().doubleValue() > 0) {
                    rdmsGantts2.add(gantt);
                }
            });
            rdmsGantts.addAll(rdmsGantts2);
        }
        List<RdmsGantt> rdmsGantts_sorted = this.sortGanttList(rdmsGantts);
        List<RdmsGanttDto> rdmsGanttDtos = CopyUtil.copyList(rdmsGantts_sorted, RdmsGanttDto.class);
        if (!CollectionUtils.isEmpty(rdmsGanttDtos)) {
            rdmsGanttDtos.forEach(item -> {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String startDateString = sdf.format(item.getStartDate());
                item.setStart_date(startDateString);
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(item.getExecutorId());
                if(!ObjectUtils.isEmpty(rdmsCustomerUser)){
                    item.setExecutorName(rdmsCustomerUser.getTrueName());
                }
            });
        }
        return rdmsGanttDtos;
    }

    public List<RdmsGantt> sortGanttList(List<RdmsGantt> rdmsGantts) {
        if (rdmsGantts == null || rdmsGantts.isEmpty()) {
            return Collections.emptyList();
        }

        return rdmsGantts.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(RdmsGantt::getStartDate, Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toList());
    }
    /**
     * 用于初始化gantt表
     * 读取当前的项目\里程碑以及任务, 将子项目,里程碑,任务信息都插入到gantt中
     *
     * @param customerId
     */
    @Transactional
    public void getJobInfoAndSaveGanttData(String customerId) {
        //先读出所有的子项目
        List<RdmsProjectSubproject> subprojectList1 = rdmsSubprojectService.getSubprojectListByCustomerId(customerId);
        List<RdmsProjectSubproject> subprojectList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(subprojectList1)) {
            subprojectList = subprojectList1.stream().filter(item -> !ObjectUtils.isEmpty(item.getReleaseTime())
                    && !item.getRdType().equals(ProjectTypeEnum.PRODUCT.getType())).collect(Collectors.toList());
        }
        //然后,将所有子项目的任务插入到gantt中
        if (!CollectionUtils.isEmpty(subprojectList)) {
            for (RdmsProjectSubproject subproject : subprojectList) {
                this.createGanttRecordBySubproject(subproject);
            }
        }
    }

    /**
     * 针对一个子项目, 创建与其相关的gantt记录,包括子项目本身\功能\任务\里程碑
     * @param subproject
     */
    public void createGanttRecordBySubproject(RdmsProjectSubproject subproject) {
        RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(subproject.getProjectId());

        //创建子项目的Gantt记录
        this.createSubprojectGanttRecord(subproject);

        //保存功能/组件的gantt记录
        {
            //读出当前子项目的所有功能/组件定义
            List<RdmsCharacterDto> characterList = rdmsCharacterService.getCharacterListBySubprojectId(subproject.getId(), null);
            List<RdmsCharacterDto> characterListCollect = characterList.stream().filter(
                    item ->
                            !ObjectUtils.isEmpty(item.getPlanCompleteTime())
                                    && !ObjectUtils.isEmpty(item.getSubprojectId())
                                    && !item.getStatus().equals(CharacterStatusEnum.DISCARD.getStatus())
                                    && !item.getStatus().equals(CharacterStatusEnum.REFUSE.getStatus())
                                    && !item.getStatus().equals(CharacterStatusEnum.HISTORY.getStatus())
                                    && !item.getStatus().equals(CharacterStatusEnum.UPDATED_HISTORY.getStatus())
            ).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(characterListCollect)) {
                for (RdmsCharacterDto character : characterListCollect) {
                    RdmsGantt gantt = new RdmsGantt();
                    ;
                    gantt.setId(character.getId());  //gantt的Id是Character的Id
                    gantt.setParent(subproject.getId());
                    gantt.setJobitemId(null);
                    gantt.setCharacterId(character.getId());
                    gantt.setSubprojectId(subproject.getId());
                    gantt.setProjectId(subproject.getProjectId());
                    gantt.setPreprojectId(subproject.getPreProjectId());
                    gantt.setCustomerId(subproject.getCustomerId());
                    gantt.setCreaterId(character.getWriterId());
                    gantt.setExecutorId(subproject.getProjectManagerId());
                    gantt.setText(character.getCharacterName());
                    gantt.setStartDate(subproject.getSetupedTime());
                    //用计划提交的日期减去创建的日期
                    gantt.setDuration((int) calculateDuration(gantt.getStartDate(), character.getPlanCompleteTime()));
                    gantt.setManhour(0.0);
                    gantt.setProgress(0.0);
                    if (character.getStatus().equals(CharacterStatusEnum.ARCHIVED.getStatus()) || character.getStatus().equals(CharacterStatusEnum.UPDATED.getStatus())) {
                        gantt.setStatus("complete");
                    } else if (new Date().getTime() > character.getPlanCompleteTime().getTime()) {
                        gantt.setStatus("overdue");
                    } else {
                        gantt.setStatus("going");
                    }
                    gantt.setType("character");
                    gantt.setOpen(0);
                    gantt.setDeleted(0);

                    if (gantt.getDuration().doubleValue() > 0) {
                        this.save(gantt);
                    }
                }
            }
        }

        //添加里程碑gantt节点
        {
            //查看项目所有里程碑, 截取本子项目发布之前的所有里程碑列表
            if (!ObjectUtils.isEmpty(rdmsProject) && !ObjectUtils.isEmpty(subproject) && !ObjectUtils.isEmpty(subproject.getReleaseTime()) && !ObjectUtils.isEmpty(rdmsProject.getSetupedTime())) {
                List<RdmsMilestoneDto> milestoneList = rdmsMilestoneService.getMilestoneListBetweenStartEndTime(rdmsProject.getId(), rdmsProject.getSetupedTime(), subproject.getReleaseTime());
                if (!CollectionUtils.isEmpty(milestoneList)) {
                    for (RdmsMilestoneDto milestoneDto : milestoneList) {
                        //保存项目的gantt记录
                        RdmsGantt gantt = new RdmsGantt();
                        gantt.setId(milestoneDto.getId());  //gantt的Id是里程碑的Id
                        gantt.setParent(null);
                        gantt.setJobitemId(null);
                        gantt.setCharacterId(null);
                        gantt.setSubprojectId(null);
                        gantt.setProjectId(subproject.getProjectId());
                        gantt.setPreprojectId(subproject.getPreProjectId());
                        gantt.setCustomerId(subproject.getCustomerId());
                        gantt.setCreaterId(ObjectUtils.isEmpty(subproject.getProductManagerId()) ? subproject.getProjectManagerId() : subproject.getProductManagerId());
                        gantt.setExecutorId(ObjectUtils.isEmpty(subproject.getProductManagerId()) ? subproject.getProjectManagerId() : subproject.getProductManagerId());
                        gantt.setText(milestoneDto.getMilestoneName());
                        gantt.setStartDate(milestoneDto.getReviewTime());
                        //用计划提交的日期减去创建的日期
                        gantt.setDuration(1);
                        gantt.setManhour(0.0);
                        gantt.setProgress(0.0);
                        gantt.setStatus("milestone");  //只读状态
                        gantt.setType("milestone");
                        gantt.setOpen(0);
                        gantt.setDeleted(0);

                        if (gantt.getDuration().doubleValue() > 0) {
                            this.save(gantt);
                        }
                    }
                }
            }
        }

        //添加任务的gantt节点
        {
            List<RdmsJobItemDto> jobitemList1 = rdmsJobItemService.getJobitemListBySubprojectId(subproject.getId(), null, null);
            List<RdmsJobItemDto> jobitemList = jobitemList1.stream().filter(jobItemDto -> !ObjectUtils.isEmpty(jobItemDto.getPlanSubmissionTime())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(jobitemList)) {
                for (RdmsJobItemDto jobItemDto : jobitemList) {
                    RdmsGantt gantt = new RdmsGantt();
                    gantt.setId(jobItemDto.getId());  //gantt的Id是任务Id
                    gantt.setParent(subproject.getId());
                    gantt.setJobitemId(jobItemDto.getId());
                    gantt.setCharacterId(jobItemDto.getCharacterId());
                    gantt.setSubprojectId(jobItemDto.getSubprojectId());
                    gantt.setProjectId(jobItemDto.getProjectId());
                    gantt.setPreprojectId(jobItemDto.getPreProjectId());
                    gantt.setCustomerId(jobItemDto.getCustomerId());
                    gantt.setCreaterId(jobItemDto.getCreaterId());
                    gantt.setExecutorId(jobItemDto.getExecutorId());
                    gantt.setText(jobItemDto.getJobName());
                    gantt.setStartDate(jobItemDto.getCreateTime());
                    //用计划提交的日期减去创建的日期
                    gantt.setDuration((int) calculateDuration(jobItemDto.getCreateTime(), jobItemDto.getPlanSubmissionTime()));
                    gantt.setManhour(jobItemDto.getManhour());
                    gantt.setProgress(0.0);
                    if (jobItemDto.getStatus().equals(JobItemStatusEnum.ARCHIVED.getStatus()) || jobItemDto.getStatus().equals(JobItemStatusEnum.COMPLETED.getStatus())) {
                        gantt.setStatus("complete");
                    } else if (new Date().getTime() > jobItemDto.getPlanSubmissionTime().getTime()) {
                        gantt.setStatus("overdue");
                    } else {
                        gantt.setStatus("going");
                    }
                    gantt.setType("task");
                    gantt.setOpen(0);
                    gantt.setDeleted(0);

                    if (gantt.getDuration().doubleValue() > 0) {
                        this.save(gantt);
                    }
                }
            }
        }
    }

    /**
     * 创建子项目gantt记录
     * @param subproject
     */
    private void createSubprojectGanttRecord(RdmsProjectSubproject subproject) {
        //保存项目的gantt记录
        {
            RdmsGantt gantt = new RdmsGantt();
            gantt.setId(subproject.getId());  //gantt的Id是子项目的Id
            gantt.setParent(null);
            gantt.setJobitemId(subproject.getId());   //对于子项目, 任务Id和项目Id相同
            gantt.setCharacterId(null);
            gantt.setSubprojectId(subproject.getId());
            gantt.setProjectId(subproject.getProjectId());
            gantt.setPreprojectId(subproject.getPreProjectId());
            gantt.setCustomerId(subproject.getCustomerId());
            gantt.setCreaterId(subproject.getProjectManagerId());
            gantt.setExecutorId(subproject.getProjectManagerId());
            gantt.setText(subproject.getLabel());
            gantt.setStartDate(subproject.getSetupedTime());
            //用计划提交的日期减去创建的日期
            gantt.setDuration((int) calculateDuration(gantt.getStartDate(), subproject.getReleaseTime()));
            gantt.setManhour(0.0);
            gantt.setProgress(0.0);
            if (subproject.getStatus().equals(ProjectStatusEnum.ARCHIVED.getStatus()) || subproject.getStatus().equals(ProjectStatusEnum.COMPLETE.getStatus())) {
                gantt.setStatus("complete");
            } else if (new Date().getTime() > subproject.getReleaseTime().getTime()) {
                gantt.setStatus("overdue");
            } else {
                gantt.setStatus("going");
            }
            gantt.setType("project");
            gantt.setOpen(1);
            gantt.setDeleted(0);

            if (gantt.getDuration().doubleValue() > 0) {
                this.save(gantt);
            }
        }
    }

    public long calculateDuration(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(
                startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        );
    }

    @Transactional
    public List<RdmsGantt> getListBySubProjectId(String subprojectId) {
        RdmsGanttExample rdmsGanttExample = new RdmsGanttExample();
        rdmsGanttExample.createCriteria()
                .andSubprojectIdEqualTo(subprojectId)
                .andDeletedEqualTo(0);
        List<RdmsGantt> rdmsGantts = rdmsGanttMapper.selectByExample(rdmsGanttExample);
        if (CollectionUtils.isEmpty(rdmsGantts)) {
            return null;
        } else {
            return rdmsGantts;
        }
    }

    public RdmsGantt selectByPrimaryKey(String id) {
        return rdmsGanttMapper.selectByPrimaryKey(id);
    }

    /**
     * 保存
     */
    public String save(RdmsGantt rdmsGantt) {
        rdmsGantt.setDeleted(0);
        if (!ObjectUtils.isEmpty(rdmsGantt) && !ObjectUtils.isEmpty(rdmsGantt.getJobitemId())) {
            RdmsGanttExample rdmsGanttExample = new RdmsGanttExample();
            rdmsGanttExample.createCriteria()
                    .andJobitemIdEqualTo(rdmsGantt.getJobitemId())
                    .andDeletedEqualTo(0);
            List<RdmsGantt> rdmsGantts = rdmsGanttMapper.selectByExample(rdmsGanttExample);
            if (!CollectionUtils.isEmpty(rdmsGantts)) {
                rdmsGantt.setJobitemId(rdmsGantts.get(0).getJobitemId());
                return this.update(rdmsGantt);
            } else {
                return this.insert(rdmsGantt);
            }
        } else {
            if (!ObjectUtils.isEmpty(rdmsGantt) && !ObjectUtils.isEmpty(rdmsGantt.getId())) {
                RdmsGantt rdmsGantt1 = this.selectByPrimaryKey(rdmsGantt.getId());
                if (!ObjectUtils.isEmpty(rdmsGantt1)) {
                    return this.update(rdmsGantt);
                } else {
                    return this.insert(rdmsGantt);
                }
            } else {
                return this.insert(rdmsGantt);
            }
        }
    }

    /**
     * 新增
     */
    private String insert(RdmsGantt rdmsGantt) {
        if (org.springframework.util.ObjectUtils.isEmpty(rdmsGantt.getId())) {  //当前端页面给出projectID时,将不为空
            rdmsGantt.setId(UuidUtil.getShortUuid());
        }
        RdmsGantt rdmsGantt1 = this.selectByPrimaryKey(rdmsGantt.getId());
        if (!ObjectUtils.isEmpty(rdmsGantt1)) {
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        } else {
            rdmsGanttMapper.insert(rdmsGantt);
            return rdmsGantt.getId();
        }
    }

    /**
     * 更新
     */
    private String update(RdmsGantt rdmsGantt) {
        if (ObjectUtils.isEmpty(rdmsGantt) || ObjectUtils.isEmpty(rdmsGantt.getId())) {
            throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
        }
        RdmsGantt rdmsGantt1 = this.selectByPrimaryKey(rdmsGantt.getId());
        if (!ObjectUtils.isEmpty(rdmsGantt1)) {
            rdmsGantt.setId(rdmsGantt1.getId());
            rdmsGanttMapper.updateByPrimaryKey(rdmsGantt);
            return rdmsGantt.getId();
        } else {
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsGantt gantt = rdmsGanttMapper.selectByPrimaryKey(id);
        if (!ObjectUtils.isEmpty(gantt)) {
            gantt.setDeleted(1);
            this.update(gantt);
        } else {
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

}
