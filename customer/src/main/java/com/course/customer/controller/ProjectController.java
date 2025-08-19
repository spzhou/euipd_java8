/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.*;
import com.course.server.dto.PageDto;
import com.course.server.dto.RdmsBudgetResearchDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.*;
import com.course.server.enums.FileGroupingEnum;
import com.course.server.enums.rdms.*;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsProjectSubprojectMapper;
import com.course.server.service.calculate.CalBudgetService;
import com.course.server.service.rdms.*;
import com.course.server.util.CopyUtil;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/project")
public class ProjectController {
    private static final Logger LOG = LoggerFactory.getLogger(ProjectController.class);
    public static final String BUSINESS_NAME = "项目管理";

    @Resource
    private RdmsProjectService rdmsProjectService;
    @Resource
    private RdmsIpmtService rdmsIpmtService;
    @Resource
    private RdmsProductManagerService rdmsProductManagerService;
    @Resource
    private RdmsProjectManagerService rdmsProjectManagerService;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private RdmsCharacterService rdmsCharacterService;
    @Resource
    private RdmsMilestoneService rdmsMilestoneService;
    @Resource
    private RdmsCharacterProcessService rdmsCharacterProcessService;
    @Resource
    private RdmsSubprojectService rdmsSubprojectService;
    @Resource
    private RdmsJobItemService rdmsJobItemService;
    @Resource
    private CalBudgetService calBudgetService;
    @Resource
    private RdmsFileService rdmsFileService;
    @Resource
    private RdmsBomConfigService rdmsBomConfigService;
    @Resource
    private RdmsBudgetResearchService rdmsBudgetResearchService;
    @Resource
    private RdmsManhourService rdmsManhourService;
    @Resource
    private RdmsPreProjectService rdmsPreProjectService;
    @Resource
    private RdmsCustomerService rdmsCustomerService;
    @Resource
    private RdmsProjectSecLevelService rdmsProjectSecLevelService;
    @Resource
    private RdmsCharacterSecLevelService rdmsCharacterSecLevelService;
    @Resource
    private RdmsFileAuthService rdmsFileAuthService;
    @Resource
    private RdmsBudgetService rdmsBudgetService;
    @Resource
    private RdmsBossService rdmsBossService;
    @Autowired
    private RdmsBudgetAdjustService rdmsBudgetAdjustService;
    @Autowired
    private RdmsCategoryProjectService rdmsCategoryProjectService;
    @Autowired
    private RdmsSystemManagerService rdmsSystemManagerService;
    @Autowired
    private RdmsGanttService rdmsGanttService;
    @Autowired
    private RdmsMaterialManageService rdmsMaterialManageService;
    @Autowired
    private RdmsFeeManageService rdmsFeeManageService;
    @Autowired
    private RdmsCbbService rdmsCbbService;

    /**
     * 列表查询已经立项的项目
     */
    @PostMapping("/list")
    public ResponseDto<PageDto<RdmsProjectDto>> list(@RequestBody PageDto<RdmsProjectDto> pageDto) {
        ResponseDto<PageDto<RdmsProjectDto>> responseDto = new ResponseDto<>();
        rdmsProjectService.list(pageDto, null, ActorTypeEnum.ALL.getStatus());
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 列表查询申请立项的项目
     */
    @PostMapping("/list-setup-project")
    public ResponseDto<List<RdmsProjectDto>> listSetupProject(@RequestParam String customerId, String loginUserId) {
        ResponseDto<List<RdmsProjectDto>> responseDto = new ResponseDto<>();

        RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(loginUserId);
        RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(customerId);

        List<RdmsProjectDto> projectList = new ArrayList<>();
        //判断是否是老板
        RdmsBossDto boss = rdmsBossService.getBossByCustomerId(rdmsCustomer.getId());
        if(boss.getBossId().equals(rdmsCustomerUser.getId())){
            List<RdmsProjectDto> setupProjectList = rdmsProjectService.getSetupProjectList(customerId);
            projectList.addAll(setupProjectList);
        }
        //如果是监管委员
        List<RdmsCustomerUser> ipmtList = rdmsIpmtService.listIpmtByCustomerId(rdmsCustomer.getId());
        if(!CollectionUtils.isEmpty(ipmtList)) {
            List<String> ipmtIdList = ipmtList.stream().map(RdmsCustomerUser::getId).collect(Collectors.toList());
            if(ipmtIdList.contains(loginUserId)){
                List<RdmsProjectDto> setupProjectListByIpmt = rdmsProjectService.getSetupProjectListByIpmt(customerId, loginUserId);
                projectList.addAll(setupProjectListByIpmt);
            }
        }

        //创建者
        if(!ObjectUtils.isEmpty(loginUserId)){
            List<RdmsProjectDto> setupEditProjectListByCreater = rdmsProjectService.getSetupEditProjectListByCreater(customerId, loginUserId);
            projectList.addAll(setupEditProjectListByCreater);
        }

        List<RdmsProjectDto> projectDtoList = new ArrayList<>();
        List<String> projectIdList = projectList.stream().map(RdmsProjectDto::getId).distinct().collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(projectIdList)){
            projectIdList.forEach(projectId -> {
                for(RdmsProjectDto projectDto: projectList){
                    if(projectId.equals(projectDto.getId())){
                        projectDtoList.add(projectDto);
                        break;
                    }
                }
            });
        }

        //附加处理: 检查项目分类中是否存在"全部项目"分类, 如果不存在, 则创建默认分类
        rdmsCategoryProjectService.initCategoryByCustomerId(customerId);

        List<RdmsProjectDto> projectListSorted = new ArrayList<>();
        if(!CollectionUtils.isEmpty(projectDtoList)){
            projectListSorted = projectDtoList.stream().sorted(Comparator.comparing(RdmsProjectDto::getCreateTime).reversed()).collect(Collectors.toList());
        }
        responseDto.setContent(projectListSorted);
        return responseDto;
    }

    /**
     * 列表查询申请立项的项目
     */
    @PostMapping("/list-setuped-project")
    public ResponseDto<PageDto<RdmsProjectDto>> listSetupedProject(@RequestBody PageDto<RdmsProjectDto> pageDto) {
        ResponseDto<PageDto<RdmsProjectDto>> responseDto = new ResponseDto<>();
        List<ProjectStatusEnum> statusEnumList = new ArrayList<>();
        statusEnumList.add(ProjectStatusEnum.SETUPED);
        statusEnumList.add(ProjectStatusEnum.ONGOING);
        statusEnumList.add(ProjectStatusEnum.SUSPEND);
        statusEnumList.add(ProjectStatusEnum.DEV_COMPLETE);
        statusEnumList.add(ProjectStatusEnum.INTEGRATION);
        statusEnumList.add(ProjectStatusEnum.REVIEW_SUBP);
        statusEnumList.add(ProjectStatusEnum.REVIEW_PRO);
        rdmsProjectService.getSetupedProjectList(pageDto, statusEnumList);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/getSetupedProjectListByTm")
    public ResponseDto<List<RdmsProjectDto>> getSetupedProjectListByTm(@RequestParam String customerId, String loginUserId) {
        ResponseDto<List<RdmsProjectDto>> responseDto = new ResponseDto<>();
        List<RdmsProject> setupedProjectListByTm = rdmsProjectService.getSetupedProjectListByTm(customerId, loginUserId);
        List<RdmsProjectDto> projectDtoList = CopyUtil.copyList(setupedProjectListByTm, RdmsProjectDto.class);
        int submitTestTaskNum = 0;
        int submitTestTaskNum_testTask = 0;
        if(!CollectionUtils.isEmpty(projectDtoList)){
            for(RdmsProjectDto rdmsProjectDto : projectDtoList){
                if(rdmsProjectDto.getSupervise() != null){
                    RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(rdmsProjectDto.getSupervise());
                    rdmsProjectDto.setSuperviseName(rdmsCustomerUser.getTrueName());
                }
                if(rdmsProjectDto.getProductManagerId() != null){
                    RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(rdmsProjectDto.getProductManagerId());
                    rdmsProjectDto.setProductManagerName(rdmsCustomerUser.getTrueName());
                }
                if(rdmsProjectDto.getProjectManagerId() != null){
                    RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(rdmsProjectDto.getProjectManagerId());
                    rdmsProjectDto.setProjectManagerName(rdmsCustomerUser.getTrueName());
                }
                if(rdmsProjectDto.getTestManagerId() != null){
                    RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(rdmsProjectDto.getTestManagerId());
                    rdmsProjectDto.setTestManagerName(rdmsCustomerUser.getTrueName());
                }

                //查询相应项目的功能单元
                List<RdmsCharacter> characterListByProjectId = rdmsCharacterService.getCharacterListByProjectId(rdmsProjectDto.getId());
                if(!CollectionUtils.isEmpty(characterListByProjectId)){
                    for(RdmsCharacter rdmsCharacter : characterListByProjectId){

                        List<JobItemStatusEnum> statusList = new ArrayList<>();
                        statusList.add(JobItemStatusEnum.SUBMIT);
                        Integer submitJobNun = rdmsJobItemService.getTestSchemeJobitemNumberByCharacterAndStatus_notAssist(rdmsCharacter.getId(), statusList);
                        if(submitJobNun > 0){
                            submitTestTaskNum += submitJobNun;
                        }
                        Integer submitJobNun_testTask = rdmsJobItemService.getTaskTestJobitemNumberByCharacterAndStatus(rdmsCharacter.getId(), statusList);
                        if(submitJobNun_testTask > 0){
                            submitTestTaskNum_testTask += submitJobNun_testTask;
                        }

                    }
                }
                rdmsProjectDto.setSubmitJobNum(submitTestTaskNum);
                submitTestTaskNum = 0;
                rdmsProjectDto.setSubmitJobNum_testTask(submitTestTaskNum_testTask);
                submitTestTaskNum_testTask = 0;
            }
        }
        responseDto.setContent(projectDtoList);
        return responseDto;
    }

    @PostMapping("/getTestedSubprojectListByTgm")
    public ResponseDto<List<RdmsProjectSubprojectDto>> getTestedSubprojectListByTgm(@RequestParam String customerId, String loginUserId) {
        ResponseDto<List<RdmsProjectSubprojectDto>> responseDto = new ResponseDto<>();
        List<RdmsProjectSubprojectDto> testedProjectListByTgm = rdmsProjectService.getTestedSubprojectListByTgm(customerId, loginUserId);
        responseDto.setContent(testedProjectListByTgm);
        return responseDto;
    }

    @PostMapping("/getTestedSubprojectListByTm")
    public ResponseDto<PageDto<RdmsProjectSubprojectDto>> getTestedSubprojectListByTm(@RequestBody PageDto<RdmsProjectSubprojectDto> pageDto) {
        ResponseDto<PageDto<RdmsProjectSubprojectDto>> responseDto = new ResponseDto<>();
        rdmsProjectService.getTestedSubprojectListByTestManagerId(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/getCompleteProjectListByCustomerId/{customerId}")
    public ResponseDto<List<RdmsProjectDto>> getCompleteProjectListByCustomerId(@PathVariable String customerId) {
        ResponseDto<List<RdmsProjectDto>> responseDto = new ResponseDto<>();
        List<RdmsProjectDto> rdmsProjectInfoDtos = rdmsProjectService.getCompleteProjectListByCustomerId(customerId);
        responseDto.setContent(rdmsProjectInfoDtos);
        return responseDto;
    }

    /**
     * 列表查询
     */
    @PostMapping("/countSetupProjectBySupervise/{userId}")
    public ResponseDto<Integer> countSetupProjectBySupervise(@PathVariable String userId) {
        ResponseDto<Integer> responseDto = new ResponseDto<>();
        Integer i = rdmsProjectService.countSetupProjectBySupervise(userId);
        Integer j = rdmsProjectService.countSetupProjectByProductManager(userId);
        responseDto.setContent(i + j);
        return responseDto;
    }

    @PostMapping("/getSubprojectListAndAllTaskJobitems/{projectId}")
    public ResponseDto<List<RdmsHmiSubprojectAndJobItemMixDto>> getSubprojectListAndAllTaskJobitems(@PathVariable String projectId) {
        ResponseDto<List<RdmsHmiSubprojectAndJobItemMixDto>> responseDto = new ResponseDto<>();
        List<RdmsHmiSubprojectAndJobItemMixDto> subprojectListAndAllTaskJobitems = rdmsProjectService.getSubprojectListAndAllTaskJobitems(projectId);
        responseDto.setContent(subprojectListAndAllTaskJobitems);
        return responseDto;
    }

    @PostMapping("/getProjectTeamIdsByProjectId/{projectId}")
    public ResponseDto<RdmProjectTeamInfoDto> getProjectTeamIdsByProjectId(@PathVariable String projectId) {
        ResponseDto<RdmProjectTeamInfoDto> responseDto = new ResponseDto<>();
        RdmProjectTeamInfoDto teamInfoDto = new RdmProjectTeamInfoDto();
        List<RdmsProjectSecLevel> rdmsProjectSecLevels = rdmsProjectSecLevelService.getByProjectId(projectId);
        if(!CollectionUtils.isEmpty(rdmsProjectSecLevels)){
            teamInfoDto.setProjectSecLevel(rdmsProjectSecLevels.get(0).getLevel());
        }else{
            teamInfoDto.setProjectSecLevel(0);
        }
        List<String> projectTeamIds = rdmsProjectSecLevelService.getProjectTeamIdsByProjectId(projectId);
        teamInfoDto.setTeamIdList(projectTeamIds);
        responseDto.setContent(teamInfoDto);
        return responseDto;
    }

    @PostMapping("/getSubProjectByPrimaryKey/{subprojectId}")
    public ResponseDto<RdmsProjectSubprojectDto> getSubProjectByPrimaryKey(@PathVariable String subprojectId) {
        ResponseDto<RdmsProjectSubprojectDto> responseDto = new ResponseDto<>();
        RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(subprojectId);
        responseDto.setContent(CopyUtil.copy(subproject, RdmsProjectSubprojectDto.class));
        return responseDto;
    }

    /**
     * 对jobitem表进行"简单"查询
     */
    @PostMapping("/getProjectAndTaskJobitems/{projectId}")
    public ResponseDto<RdmsHmiProjectAndJobItemMixDto> getProjectAndTaskJobitems(@PathVariable String projectId) {
        ResponseDto<RdmsHmiProjectAndJobItemMixDto> responseDto = new ResponseDto<>();
        RdmsHmiProjectAndJobItemMixDto projectAndTaskJobitems = rdmsProjectService.getProjectAndTaskJobitems(projectId);
        responseDto.setContent(projectAndTaskJobitems);
        return responseDto;
    }

    /**
     * 对jobitem表进行"简单"查询
     */
    @PostMapping("/getSubProjectAndTaskJobitems/{subProjectId}")
    public ResponseDto<RdmsHmiProjectAndJobItemMixDto> getSubProjectAndTaskJobitems(@PathVariable String subProjectId) {
        ResponseDto<RdmsHmiProjectAndJobItemMixDto> responseDto = new ResponseDto<>();
        RdmsHmiProjectAndJobItemMixDto projectAndTaskJobitems = rdmsProjectService.getSubProjectAndTaskJobitems(subProjectId);
        responseDto.setContent(projectAndTaskJobitems);
        return responseDto;
    }

    /**
     * 根据SubprojectId进行列表查询
     */
/*
    @PostMapping("/getSubProjectReviewDetailInfo/{subprojectId}")
    public ResponseDto<RdmsHmiSubProjectReviewDetailDto> getSubProjectReviewDetailInfo(@PathVariable String subprojectId) {
        ResponseDto<RdmsHmiSubProjectReviewDetailDto> responseDto = new ResponseDto<>();
        RdmsHmiSubProjectReviewDetailDto subProjectReviewDetailInfo = rdmsProjectService.getSubProjectReviewDetailInfo(subprojectId);
        responseDto.setContent(subProjectReviewDetailInfo);
        return responseDto;
    }
*/


    /**
     * 根据SubprojectId进行列表查询
     */
    @PostMapping("/getProjectReviewDetailInfo")
    public ResponseDto<RdmsHmiProjectReviewDetailDto> getProjectReviewDetailInfo(@RequestParam String projectId, String loginUserId) {
        ResponseDto<RdmsHmiProjectReviewDetailDto> responseDto = new ResponseDto<>();
        RdmsHmiProjectReviewDetailDto projectReviewDetailInfo = rdmsProjectService.getProjectReviewDetailInfo(projectId, loginUserId);
        responseDto.setContent(projectReviewDetailInfo);
        return responseDto;
    }


    /**
     * 对jobitem表进行"简单"查询
     */
    @PostMapping("/getSubProjectAndTestJobitems/{subprojectId}")
    public ResponseDto<RdmsHmiProjectAndJobItemMixDto> getSubProjectAndTestJobitems(@PathVariable String subprojectId) {
        ResponseDto<RdmsHmiProjectAndJobItemMixDto> responseDto = new ResponseDto<>();
        RdmsHmiProjectAndJobItemMixDto projectAndTaskJobitems = rdmsProjectService.getSubProjectAndTestJobitems(subprojectId);
        responseDto.setContent(projectAndTaskJobitems);
        return responseDto;
    }


    /**
     * 对jobitem表进行"简单"查询
     */
    @PostMapping("/getSubProjectAndTestJobitems_submit/{subprojectId}")
    public ResponseDto<RdmsHmiProjectAndJobItemMixDto> getSubProjectAndTestJobitems_submit(@PathVariable String subprojectId) {
        ResponseDto<RdmsHmiProjectAndJobItemMixDto> responseDto = new ResponseDto<>();
        RdmsHmiProjectAndJobItemMixDto projectAndTaskJobitems = rdmsProjectService.getSubProjectAndTestJobitems_submit(subprojectId);
        responseDto.setContent(projectAndTaskJobitems);
        return responseDto;
    }


    /**
     * 对jobitem表进行"简单"查询
     */
    @PostMapping("/getProjectAndTaskJobitems_submit/{projectId}")
    public ResponseDto<RdmsHmiProjectAndJobItemMixDto> getProjectAndTaskJobitems_submit(@PathVariable String projectId) {
        ResponseDto<RdmsHmiProjectAndJobItemMixDto> responseDto = new ResponseDto<>();
        RdmsHmiProjectAndJobItemMixDto projectAndTaskJobitems = rdmsProjectService.getProjectAndTaskJobitems_submit(projectId);
        responseDto.setContent(projectAndTaskJobitems);
        return responseDto;
    }

    @PostMapping("/getProjectHasListFlag/{userId}")
    public ResponseDto<Boolean> getProjectHasListFlag(@PathVariable String userId) {
        ResponseDto<Boolean> responseDto = new ResponseDto<>();
        Integer countOfSetupedProjectByPjm = rdmsProjectService.getCountOfSetupedProjectByPjm(userId);
        responseDto.setContent(countOfSetupedProjectByPjm >0);
        return responseDto;
    }

    @PostMapping("/getProductHasListFlag/{userId}")
    public ResponseDto<Boolean> getProductHasListFlag(@PathVariable String userId) {
        ResponseDto<Boolean> responseDto = new ResponseDto<>();
        Integer countOfSetupedProjectByPdm = rdmsProjectService.getCountOfArchivedProjectByPdm(userId);
        responseDto.setContent(countOfSetupedProjectByPdm >0);
        return responseDto;
    }

    @PostMapping("/getSetupProjectHasListFlag/{userId}")
    public ResponseDto<Boolean> getSetupProjectHasListFlag(@PathVariable String userId) {
        ResponseDto<Boolean> responseDto = new ResponseDto<>();
        RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(userId);
        RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(rdmsCustomerUser.getCustomerId());

        int countOfSetupProjectBySuper = 0;
        //判断是否为管理员
        if(rdmsCustomerUser.getLoginName().equals(rdmsCustomer.getContactPhone())){
            countOfSetupProjectBySuper = rdmsProjectService.countSetupProjectByCustomerId(rdmsCustomer.getId());
        }else{
            countOfSetupProjectBySuper = rdmsProjectService.countSetupProjectBySupervise(userId);
        }

        responseDto.setContent(countOfSetupProjectBySuper >0);
        return responseDto;
    }

    /**
     * 对jobitem表进行"简单"查询
     */
    @PostMapping("/getSubProjectAndTaskJobitems_submit/{subProjectId}")
    public ResponseDto<RdmsHmiProjectAndJobItemMixDto> getSubProjectAndTaskJobitems_submit(@PathVariable String subProjectId) {
        ResponseDto<RdmsHmiProjectAndJobItemMixDto> responseDto = new ResponseDto<>();
        RdmsHmiProjectAndJobItemMixDto projectAndTaskJobitems = rdmsProjectService.getSubProjectAndTaskJobitems_submit(subProjectId);
        responseDto.setContent(projectAndTaskJobitems);
        return responseDto;
    }

    @PostMapping("/getSubprojectListByProjectId/{projectId}")
    public ResponseDto<List<RdmsProjectSubprojectDto>> getSubprojectListByProjectId(@PathVariable String projectId) {
        ResponseDto<List<RdmsProjectSubprojectDto>> responseDto = new ResponseDto<>();
        List<RdmsProjectSubprojectDto> subprojectListByProjectId = rdmsProjectService.getSubprojectListByProjectId(projectId);
        responseDto.setContent(subprojectListByProjectId);
        return responseDto;
    }

    @Transactional
    @PostMapping("/getProjectDevelopCompleteStatus/{projectId}")
    public ResponseDto<Boolean> getProjectDevelopCompleteStatus(@PathVariable String projectId) {
        ResponseDto<Boolean> responseDto = new ResponseDto<>();
        Boolean flag = false;

        RdmsProject project = rdmsProjectService.selectByPrimaryKey(projectId);
        if( !(project.getStatus().equals(ProjectStatusEnum.ARCHIVED.getStatus()) || project.getStatus().equals(ProjectStatusEnum.COMPLETE.getStatus()))){
            //已经完成的工单和工单数
            List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
            jobitemTypeList.add(JobItemTypeEnum.TASK_SUBP);
            jobitemTypeList.add(JobItemTypeEnum.SUBPROJECT_INT);
            jobitemTypeList.add(JobItemTypeEnum.REVIEW_SUBP);

            List<JobItemStatusEnum> jobitemStatusList = new ArrayList<>();
            jobitemStatusList.add(JobItemStatusEnum.COMPLETED);
            jobitemStatusList.add(JobItemStatusEnum.ARCHIVED);

            List<RdmsJobItemDto> completeJobList = rdmsJobItemService.getJobitemListByProjectIdAndStatusListTypeList(projectId, jobitemStatusList, jobitemTypeList);
            List<RdmsJobItemDto> allJobList = rdmsJobItemService.getJobitemListByProjectIdAndStatusListTypeList(projectId, null, jobitemTypeList);
            if (!CollectionUtils.isEmpty(completeJobList) && !CollectionUtils.isEmpty(allJobList)) {
                if (completeJobList.size() == allJobList.size()) {
                    //判断项目项下所有子项目评审是否全部完成
                    List<RdmsProjectSubprojectDto> allSubprojects = rdmsProjectService.getSubprojectListByProjectId(projectId);
                    List<ProjectStatusEnum> statusEnumList =new ArrayList<>();
                    statusEnumList.add(ProjectStatusEnum.ARCHIVED);
                    statusEnumList.add(ProjectStatusEnum.COMPLETE);
                    List<RdmsProjectSubprojectDto> completeSubprojects = rdmsProjectService.getSubprojectListByProjectIdAndStatus(projectId, statusEnumList);

                    for (RdmsJobItemDto jobItemDto : completeJobList) {
                        if (jobItemDto.getType().equals(JobItemTypeEnum.PROJECT_INT.getType()) && jobItemDto.getStatus().equals(JobItemStatusEnum.COMPLETED.getStatus()) && allSubprojects.size() == completeSubprojects.size()) {
                            flag = true;
                        }
                    }
                }
            }
        }
        responseDto.setContent(flag);
        return responseDto;
    }

    /**
     * 这里的projectID是个通用的说法, 需要通过projectType确定到底是什么类型的项目id
     */
    @PostMapping("/getIdsDtoByProjectIdAndType")
    public ResponseDto<RdmsHmiIdsDto> getIdsDtoByProjectIdAndType(@RequestBody RdmsHmiProjectIdAndTypeDto projectIdAndType) {
        ResponseDto<RdmsHmiIdsDto> responseDto = new ResponseDto<>();
        RdmsHmiIdsDto idsDto = rdmsProjectService.getIdsDtoByProjectIdAndType(projectIdAndType.getProjectType(), projectIdAndType.getAnyProjectId());
        responseDto.setContent(idsDto);
        return responseDto;
    }

    @PostMapping("/joinToMainProject")
    public ResponseDto<String> joinToMainProject(@RequestBody RdmsProjectDto projectDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        List<String> joinProjectIdList = projectDto.getJoinProjectIdList();
        String jsonString = JSON.toJSONString(joinProjectIdList);
        RdmsProject project = new RdmsProject();
        project.setId(projectDto.getId());
        project.setJoinedProjectIdsStr(jsonString);
        rdmsProjectService.updateByPrimaryKeySelective(project);
        responseDto.setContent(jsonString);
        return responseDto;
    }

    @PostMapping("/getCharacterTreeByProjectIdWithLink/{projectId}")
    public ResponseDto<List<RdmsHmiTree<RdmsCharacterSimpleDto>>> getCharacterTreeByProjectIdWithLink(@PathVariable String projectId) {
        ResponseDto<List<RdmsHmiTree<RdmsCharacterSimpleDto>>> responseDto = new ResponseDto<>();
        List<RdmsHmiTree<RdmsCharacterSimpleDto>> treeList = new ArrayList<>();
        RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(projectId);

        //构建主项目树
        RdmsHmiTree<RdmsCharacterSimpleDto> characterTree = rdmsCharacterService.getCharacterTreeByProjectId(projectId);
        //添加树根节点信息
        characterTree.setId(rdmsProject.getId());
        characterTree.setDeep(-1);
        characterTree.setLabel("项目: "+rdmsProject.getProjectName());
        characterTree.setParent(rdmsProject.getId());
        characterTree.setStatus(rdmsProject.getStatus());
        treeList.add(characterTree);

        //添加link项目树
        if(! ObjectUtils.isEmpty(rdmsProject.getJoinedProjectIdsStr()) && rdmsProject.getJoinedProjectIdsStr().length()>6){
            List<String> linkProjectIds = JSON.parseArray(rdmsProject.getJoinedProjectIdsStr(), String.class);
            for(String id: linkProjectIds){
                RdmsProject linkProject = rdmsProjectService.selectByPrimaryKey(id);
                RdmsHmiTree<RdmsCharacterSimpleDto> linkProjectCharacterTree = rdmsCharacterService.getCharacterTreeByProjectId(id);
                //添加树根节点信息
                linkProjectCharacterTree.setId(linkProject.getId());
                linkProjectCharacterTree.setDeep(-1);
                linkProjectCharacterTree.setLabel("项目: "+linkProject.getProjectName());
                linkProjectCharacterTree.setParent(linkProject.getId());
                linkProjectCharacterTree.setStatus(linkProject.getStatus());
                treeList.add(linkProjectCharacterTree);
            }
        }

        responseDto.setContent(treeList);
        return responseDto;
    }

    @PostMapping("/getArchivedProjectList")
    public ResponseDto<List<RdmsProjectDto>> getArchivedProjectList(@RequestBody RdmsRequestDto requestDto) {
        ResponseDto<List<RdmsProjectDto>> responseDto = new ResponseDto<>();
        List<RdmsProjectDto> archivedProjects = rdmsProjectService.getArchivedProjectsByCustomerAndPdmId(requestDto.getCustomerId(), requestDto.getCustomerUserId());
        responseDto.setContent(archivedProjects);
        return responseDto;
    }

    @PostMapping("/getAllProjectArchivedList/{loginCustomerId}")
    public ResponseDto<List<RdmsProjectDto>> getAllProjectArchivedList(@PathVariable String loginCustomerId) {
        ResponseDto<List<RdmsProjectDto>> responseDto = new ResponseDto<>();
        List<RdmsProjectDto> archivedProjects = rdmsProjectService.getAllProjectArchivedList(loginCustomerId);
        responseDto.setContent(archivedProjects);
        return responseDto;
    }

    @PostMapping("/getAllProjectArchivedListByStaff/{loginCustomerId}")
    public ResponseDto<List<RdmsProjectDto>> getAllProjectArchivedListByStaff(@PathVariable String loginCustomerId) {
        ResponseDto<List<RdmsProjectDto>> responseDto = new ResponseDto<>();
        List<RdmsProjectDto> archivedProjects = rdmsProjectService.getAllProjectArchivedListByStaff(loginCustomerId);
        responseDto.setContent(archivedProjects);
        return responseDto;
    }

    @PostMapping("/getDevelopingProjectList")
    public ResponseDto<List<RdmsProjectDto>> getDevelopingProjectList(@RequestBody RdmsRequestDto requestDto) {
        ResponseDto<List<RdmsProjectDto>> responseDto = new ResponseDto<>();
        List<RdmsProjectDto> developingProjects = rdmsProjectService.getDevelopingProjectsByCustomerAndPdmId(requestDto.getCustomerId(), requestDto.getCustomerUserId());
        responseDto.setContent(developingProjects);
        return responseDto;
    }

    /**
     * 列表查询
     */
    @PostMapping("/getProjectInfo/{projectId}")
    public ResponseDto<RdmsProjectDto> getProjectInfo(@PathVariable String projectId) {
        ResponseDto<RdmsProjectDto> responseDto = new ResponseDto<>();
        RdmsProjectDto projectInfoDto = rdmsProjectService.getProjectRecordInfo(projectId);
        List<RdmsProjectSecLevel> rdmsProjectSecLevels = rdmsProjectSecLevelService.getBySubProjectId(projectId);
        if(!CollectionUtils.isEmpty(rdmsProjectSecLevels)){
            projectInfoDto.setSecretLevel(rdmsProjectSecLevels.get(0).getLevel());
        }
        responseDto.setContent(projectInfoDto);
        return responseDto;
    }

    @PostMapping("/getProjectSimpleInfo/{projectId}")
    public ResponseDto<RdmsProjectDto> getProjectSimpleInfo(@PathVariable String projectId) {
        ResponseDto<RdmsProjectDto> responseDto = new ResponseDto<>();
        RdmsProject rdmsProjectInfo = rdmsProjectService.selectByPrimaryKey(projectId);
        RdmsProjectDto projectInfoDto = CopyUtil.copy(rdmsProjectInfo, RdmsProjectDto.class);
        if(!ObjectUtils.isEmpty(projectInfoDto)){
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(projectInfoDto.getSupervise());
            projectInfoDto.setSuperviseName(rdmsCustomerUser.getTrueName());
            RdmsBossDto bossByCustomerId = rdmsBossService.getBossByCustomerId(projectInfoDto.getCustomerId());
            projectInfoDto.setBossId(bossByCustomerId.getBossId());
            RdmsCustomerUser rdmsCustomerUser1 = rdmsCustomerUserService.selectByPrimaryKey(bossByCustomerId.getBossId());
            projectInfoDto.setBossName(rdmsCustomerUser1.getTrueName());
        }

        List<RdmsProjectSecLevel> rdmsProjectSecLevels = rdmsProjectSecLevelService.getBySubProjectId(projectId);
        if(!CollectionUtils.isEmpty(rdmsProjectSecLevels)){
            projectInfoDto.setSecretLevel(rdmsProjectSecLevels.get(0).getLevel());
        }
        responseDto.setContent(projectInfoDto);
        return responseDto;
    }

    /**
     * 列表查询
     */
    @PostMapping("/getProjectDetailInfo")
    public ResponseDto<RdmsProjectDto> getProjectDetailInfo(@RequestParam String projectId, String loginUserId) {
        ResponseDto<RdmsProjectDto> responseDto = new ResponseDto<>();
        RdmsProject rdmsProjectInfo = rdmsProjectService.selectByPrimaryKey(projectId);
        RdmsProjectDto projectInfoDto = CopyUtil.copy(rdmsProjectInfo, RdmsProjectDto.class);
        List<RdmsProjectSecLevel> rdmsProjectSecLevels = rdmsProjectSecLevelService.getBySubProjectId(projectId);
        if(!CollectionUtils.isEmpty(rdmsProjectSecLevels)){
            projectInfoDto.setSecretLevel(rdmsProjectSecLevels.get(0).getLevel());
        }

        if(projectInfoDto.getProjectManagerId() != null){
            RdmsCustomerUser projectManager = rdmsCustomerUserService.selectByPrimaryKey(projectInfoDto.getProjectManagerId());
            projectInfoDto.setProjectManagerName(projectManager.getTrueName());
        }
        if(projectInfoDto.getProductManagerId() != null){
            RdmsCustomerUser productManager = rdmsCustomerUserService.selectByPrimaryKey(projectInfoDto.getProductManagerId());
            projectInfoDto.setProductManagerName(productManager.getTrueName());
        }
        if(projectInfoDto.getSupervise() != null){
            RdmsCustomerUser supervise = rdmsCustomerUserService.selectByPrimaryKey(projectInfoDto.getSupervise());
            projectInfoDto.setSuperviseName(supervise.getTrueName());
        }

        String keyMemberListStr = rdmsProjectInfo.getKeyMemberListStr();
        List<String> keyMemberList = JSON.parseArray(keyMemberListStr, String.class);
        projectInfoDto.setKeyMemberList(keyMemberList);
        String keyMemberNameStr = "";
        if(! CollectionUtils.isEmpty(keyMemberList)){
            for(String id: keyMemberList){
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(id);
                keyMemberNameStr += rdmsCustomerUser.getTrueName()+ " ";
            }
        }
        projectInfoDto.setKeyMemberListStr(keyMemberNameStr);

        String moduleIdListStr = rdmsProjectInfo.getModuleIdListStr();
        if(!ObjectUtils.isEmpty(moduleIdListStr) && moduleIdListStr.length() > 6){
            List<String> moduleIdList = JSON.parseArray(moduleIdListStr, String.class);
            List<RdmsCbbDto> cbbListByIdList = rdmsCbbService.getCbbListByIdList(projectInfoDto.getCustomerId(), moduleIdList);
            projectInfoDto.setCbbList(cbbListByIdList);
        }

        if(projectInfoDto.getReleaseTime() != null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            projectInfoDto.setReleaseTimeStr(sdf.format(projectInfoDto.getReleaseTime()));
        }

        List<RdmsMilestoneDto> rdmsMilestoneDtos = rdmsMilestoneService.listByProjectId(projectId);
        if(!CollectionUtils.isEmpty(rdmsMilestoneDtos)){
            projectInfoDto.setMilestoneList(rdmsMilestoneDtos);
        }
        List<RdmsHmiTree<RdmsHmiTreeInfoDto>> projectTreeList = rdmsSubprojectService.getProjectTree(projectId);
        if(! CollectionUtils.isEmpty(projectTreeList)){
            projectInfoDto.setProjectTreeList(projectTreeList);
        }

        List<RdmsCustomerUser> customerUsers = rdmsCustomerUserService.listAllWorkersByProjectId(projectId);
        List<RdmsUserOutlineInfoDto> rdmsUserOutlineList = CopyUtil.copyList(customerUsers, RdmsUserOutlineInfoDto.class);
        if(!CollectionUtils.isEmpty(rdmsUserOutlineList)){
            projectInfoDto.setWorkerList(rdmsUserOutlineList);
        }

        //是否存在评审工单  用于要求补充材料的评审场景
        List<RdmsJobItem> jobitemList = rdmsJobItemService.getJobitemListByProjectIdAndStatusAndJobType(projectId, JobItemStatusEnum.SUBMIT.getStatus(), JobItemTypeEnum.REVIEW_PRO.getType());
        if(!CollectionUtils.isEmpty(jobitemList)){
            projectInfoDto.setHasReviewJobitem(true);
        }else{
            projectInfoDto.setHasReviewJobitem(false);
        }

        //添加项目评审信息  主项目号等于子项目号的 Review_subp 类型工单
        List<JobItemStatusEnum> statusEnumList = new ArrayList<>();
        statusEnumList.add(JobItemStatusEnum.ARCHIVED);
        List<JobItemTypeEnum> typeEnumList = new ArrayList<>();
        typeEnumList.add(JobItemTypeEnum.REVIEW_SUBP);
        List<RdmsJobItemDto> reviewJobitems = rdmsJobItemService.getJobitemListBySubprojectIdAndStatusListTypeList(projectId, statusEnumList, typeEnumList);
        List<RdmsJobItemDto> collect = reviewJobitems.stream().sorted(Comparator.comparing(RdmsJobItemDto::getCreateTime).reversed()).collect(Collectors.toList());
        List<RdmsJobItemDto> jobItemDtoList = new ArrayList<>();
        if(! CollectionUtils.isEmpty(reviewJobitems)){
            for(RdmsJobItemDto jobItemDto: collect){
                RdmsJobItemDto jobitemDetailInfo = rdmsJobItemService.getJobitemDetailInfo(jobItemDto.getId(), loginUserId);
                if(! ObjectUtils.isEmpty(jobitemDetailInfo.getNextNode())){
                    RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(jobitemDetailInfo.getNextNode());
                    jobitemDetailInfo.setNextNodeName(rdmsCustomerUser.getTrueName());
                }

                List<String> workerIds = JSON.parseArray(jobitemDetailInfo.getReviewWorkerIdStr(), String.class);
                StringBuilder workerNameListStr = new StringBuilder();
                if(! CollectionUtils.isEmpty(workerIds)){
                    for(String id : workerIds){
                        RdmsCustomerUser rdmsCustomerUser1 = rdmsCustomerUserService.selectByPrimaryKey(id);
                        workerNameListStr.append(rdmsCustomerUser1.getTrueName()).append(" ");
                    }
                }
                jobitemDetailInfo.setReviewWorkerIdStr(workerNameListStr.toString());
                jobItemDtoList.add(jobitemDetailInfo);
            }
        }
        projectInfoDto.setReviewJobDetail(jobItemDtoList);

        responseDto.setContent(projectInfoDto);
        return responseDto;
    }

    /**
     * 列表查询
     */
    @PostMapping("/getProjectAndArchivedCharacters/{projectId}")
    public ResponseDto<RdmsProjectDto> getProjectAndArchivedCharacters(@PathVariable String projectId) {
        ResponseDto<RdmsProjectDto> responseDto = new ResponseDto<>();
        List<CharacterStatusEnum> statusEnumList = new ArrayList<>();
        statusEnumList.add(CharacterStatusEnum.ARCHIVED);
        RdmsProjectDto projectAndCharacterList = rdmsProjectService.getProjectAndCharacterList(projectId, statusEnumList);
        responseDto.setContent(projectAndCharacterList);
        return responseDto;
    }

    @PostMapping("/getBomConfigListByProjectId/{projectId}")
    public ResponseDto<List<RdmsBomConfigDto>> getBomConfigListByProjectId(@PathVariable String projectId) {
        ResponseDto<List<RdmsBomConfigDto>> responseDto = new ResponseDto<>();
        List<RdmsBomConfigDto> bomConfigListByProjectId = rdmsProjectService.getBomConfigListByProjectId(projectId);
        responseDto.setContent(bomConfigListByProjectId);
        return responseDto;
    }

    /**
     * 列表查询
     */
    @PostMapping("/getProjectInfoBySubprojectId/{subprojectId}")
    public ResponseDto<RdmsProjectDto> getProjectInfoBySubprojectId(@PathVariable String subprojectId) {
        ResponseDto<RdmsProjectDto> responseDto = new ResponseDto<>();
        RdmsProject project = rdmsProjectService.getProjectBySubprojectId(subprojectId);
        RdmsProjectDto projectInfoDto = CopyUtil.copy(project, RdmsProjectDto.class);
        if(projectInfoDto.getProjectManagerId() != null){
            RdmsCustomerUser projectManager = rdmsCustomerUserService.selectByPrimaryKey(projectInfoDto.getProjectManagerId());
            projectInfoDto.setProjectManagerName(projectManager.getTrueName());
        }
        if(projectInfoDto.getProductManagerId() != null){
            RdmsCustomerUser productManager = rdmsCustomerUserService.selectByPrimaryKey(projectInfoDto.getProductManagerId());
            projectInfoDto.setProductManagerName(productManager.getTrueName());
        }
        if(projectInfoDto.getSupervise() != null){
            RdmsCustomerUser supervise = rdmsCustomerUserService.selectByPrimaryKey(projectInfoDto.getSupervise());
            projectInfoDto.setSuperviseName(supervise.getTrueName());
        }

        String keyMemberListStr = project.getKeyMemberListStr();
        List<String> keyMemberList = JSON.parseArray(keyMemberListStr, String.class);
        projectInfoDto.setKeyMemberList(keyMemberList);

        if(projectInfoDto.getReleaseTime() != null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            projectInfoDto.setReleaseTimeStr(sdf.format(projectInfoDto.getReleaseTime()));
        }

        List<RdmsMilestoneDto> rdmsMilestoneDtos = rdmsMilestoneService.listByProjectId(project.getId());
        if(!CollectionUtils.isEmpty(rdmsMilestoneDtos)){
            projectInfoDto.setMilestoneList(rdmsMilestoneDtos);
        }

        responseDto.setContent(projectInfoDto);
        return responseDto;
    }


    /**
     * 列表查询
     */
    @PostMapping("/listIpmtByCustomerId/{customerId}")
    public ResponseDto<List<RdmsCustomerUser>> listIpmtByCustomerId(@PathVariable String customerId) {
        ResponseDto<List<RdmsCustomerUser>> responseDto = new ResponseDto<>();
        List<RdmsCustomerUser> rdmsCustomerUsers = rdmsIpmtService.listIpmtByCustomerId(customerId);
        responseDto.setContent(rdmsCustomerUsers);
        return responseDto;
    }

    /**
     * 列表查询
     */
    @PostMapping("/listProductManagerByCustomerId/{customerId}")
    public ResponseDto<List<RdmsCustomerUser>> listProductManagerByCustomerId(@PathVariable String customerId) {
        ResponseDto<List<RdmsCustomerUser>> responseDto = new ResponseDto<>();
        List<RdmsCustomerUser> rdmsCustomerUsers = rdmsProductManagerService.listProductManagerByCustomerId(customerId);
        responseDto.setContent(rdmsCustomerUsers);
        return responseDto;
    }

    /**
     * 列表查询
     */
    @PostMapping("/listProjectManagerByCustomerId/{customerId}")
    public ResponseDto<List<RdmsCustomerUser>> listProjectManagerByCustomerId(@PathVariable String customerId) {
        ResponseDto<List<RdmsCustomerUser>> responseDto = new ResponseDto<>();
        List<RdmsCustomerUser> rdmsCustomerUsers = rdmsProjectManagerService.getProjectManagerListByCustomerId(customerId);
        responseDto.setContent(rdmsCustomerUsers);
        return responseDto;
    }

    @PostMapping("/listSystemManagerByCustomerId/{customerId}")
    public ResponseDto<List<RdmsCustomerUser>> listSystemManagerByCustomerId(@PathVariable String customerId) {
        ResponseDto<List<RdmsCustomerUser>> responseDto = new ResponseDto<>();
        List<RdmsCustomerUser> rdmsCustomerUsers = rdmsSystemManagerService.listSystemManagerByCustomerId(customerId);
        responseDto.setContent(rdmsCustomerUsers);
        return responseDto;
    }

    /**
     * 列表查询
     */
    @PostMapping("/findKeyMemberIdListByProjectId/{projectId}")
    public ResponseDto<List<String>> findKeyMemberIdListByProjectId(@PathVariable String projectId) {
        ResponseDto<List<String>> responseDto = new ResponseDto<>();
        List<String> keyMemberIdListByProjectId = rdmsProjectService.findKeyMemberIdListByProjectId(projectId);
        responseDto.setContent(keyMemberIdListByProjectId);
        return responseDto;
    }

    /**
     * 保存项目信息
     */
    @PostMapping("/save")
    @Transactional
    public ResponseDto<RdmsProjectDto> save(@RequestBody RdmsProjectDto projectInfoDto) {
        ResponseDto<RdmsProjectDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(projectInfoDto.getCustomerId(), "客户ID");
        ValidatorUtil.require(projectInfoDto.getProjectName(), "项目名称");

        ValidatorUtil.length(projectInfoDto.getProjectName(), "项目名称", 2, 50);

        RdmsProject projectInfo = CopyUtil.copy(projectInfoDto, RdmsProject.class);
        String jsonString = JSON.toJSONString(projectInfoDto.getKeyMemberList());
        projectInfo.setKeyMemberListStr(jsonString);
        if(projectInfo.getStatus() == null){
            projectInfo.setStatus(ProjectStatusEnum.SETUP.getStatus());
        }
        rdmsProjectService.save(projectInfo);

        responseDto.setContent(projectInfoDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/saveBomConfig")
    @Transactional
    public ResponseDto<String> saveBomConfig(@RequestBody RdmsBomConfigDto bomConfigDto) {
        ResponseDto<String> responseDto = new ResponseDto<>();

        ValidatorUtil.require(bomConfigDto.getProjectId(), "项目ID");
        ValidatorUtil.require(bomConfigDto.getCharacterIdList(), "功能列表");


        RdmsBomConfig bomConfig = CopyUtil.copy(bomConfigDto, RdmsBomConfig.class);
        String jsonString = JSON.toJSONString(bomConfigDto.getCharacterIdList());
        bomConfig.setCharacterIdListStr(jsonString);
        if(!ObjectUtils.isEmpty(bomConfigDto.getIsActive()) && bomConfigDto.getIsActive() == 1){
            rdmsBomConfigService.resetActiveFlagByProjectId(bomConfigDto.getProjectId());
            bomConfig.setIsActive(1);
        }
        rdmsBomConfigService.save(bomConfig);

        responseDto.setContent(bomConfig.getId());
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

     /**
     * 立项
     */
    @PostMapping("/setup")
    @Transactional
    public ResponseDto<RdmsProjectSetupDto> setup(@RequestBody RdmsProjectSetupDto projectSetupDto) {
        ResponseDto<RdmsProjectSetupDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(projectSetupDto.getCustomerId(), "客户ID");
        ValidatorUtil.require(projectSetupDto.getProjectName(), "项目名称");
        ValidatorUtil.require(projectSetupDto.getSupervise(), "监管委员");
        ValidatorUtil.require(projectSetupDto.getProjectManagerId(), "项目经理");
        ValidatorUtil.require(projectSetupDto.getTestManagerId(), "测试主管");
        ValidatorUtil.require(projectSetupDto.getReleaseTime(), "发布时间");
//        ValidatorUtil.require(projectSetupDto.getAddCharge(), "附加费");
        ValidatorUtil.require(projectSetupDto.getProjectCode(), "项目代码");

        ValidatorUtil.length(projectSetupDto.getProjectName(), "项目名称", 2, 50);
        ValidatorUtil.length(projectSetupDto.getIncentivePolicy(), "激励政策", 2, 500);
        //查看项目编号是否重复
        List<RdmsProjectDto> projectListByCode = rdmsProjectService.getProjectListByCode(projectSetupDto.getProjectCode());
        for(RdmsProjectDto projectDto: projectListByCode){
            if(!projectDto.getStatus().equals(ProjectStatusEnum.SETUP.getStatus())){
                throw new BusinessException(BusinessExceptionCode.PROJECT_CODE_EXIST_ERROR);
            }
        }

        //处理项目信息
        RdmsProject projectInfo = CopyUtil.copy(projectSetupDto, RdmsProject.class);
        //从数据库中读出原有项目记录
        RdmsProject projectInfoDb = rdmsProjectService.selectByPrimaryKey(projectSetupDto.getId());
        projectInfo.setId(projectInfoDb.getId());
        if (CollectionUtils.isEmpty(projectSetupDto.getFileIdList())) {
            projectInfo.setFileListStr(projectInfoDb.getFileListStr());
        } else {
            String jsonString = JSON.toJSONString(projectSetupDto.getFileIdList());
            projectInfo.setFileListStr(jsonString);
        }

        String jsonString = JSON.toJSONString(projectSetupDto.getKeyMemberList());
        projectInfo.setKeyMemberListStr(jsonString);
        projectInfo.setStatus(ProjectStatusEnum.SETUPED.getStatus());
        projectInfo.setSetupedTime(new Date());

        String projectId = rdmsProjectService.save(projectInfo);
        //填写文件的访问权限人员
        if(!CollectionUtils.isEmpty(projectSetupDto.getFileIdList())){
            {
                //设置文件授权 权限
                RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                roleUsersDto.setLoginUserId(null);
                roleUsersDto.setReceiverId(null);
                RdmsProject project = rdmsProjectService.selectByPrimaryKey(projectId);
                RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(project.getCustomerId());
                roleUsersDto.setBossId(rdmsBossDto.getBossId());
                roleUsersDto.setSuperId(null);
                roleUsersDto.setIpmtId(null);
                roleUsersDto.setPjmId(projectSetupDto.getProjectManagerId());
                roleUsersDto.setPdmId(projectSetupDto.getProductManagerId());
                rdmsFileAuthService.setFileAuthUser(projectSetupDto.getFileIdList(), roleUsersDto);
            }
        }


        //保存项目保密等级
        RdmsProjectSecLevel projectSecLevel = new RdmsProjectSecLevel();
        projectSecLevel.setProjectId(projectId);
        projectSecLevel.setSubprojectId(projectId);
        projectSecLevel.setLevel(projectSetupDto.getSecretLevel());
        rdmsProjectSecLevelService.saveBySubprojectId(projectSecLevel);

        {
            //处理特性清单
            List<RdmsCharacterDto> characterDtoList = projectSetupDto.getCharacterList();
            List<RdmsCharacter> productCharacters = CopyUtil.copyList(characterDtoList, RdmsCharacter.class);
            for(RdmsCharacter rdmsCharacter : productCharacters){
                //update character表的数据
                rdmsCharacter.setProjectId(projectId);
                rdmsCharacter.setSubprojectId(projectId); //同时提供subProjectId
                rdmsCharacter.setStatus(CharacterStatusEnum.SETUPED.getStatus());
                rdmsCharacter.setNextNode(projectSetupDto.getProjectManagerId());
                rdmsCharacter.setProjectType(ProjectTypeEnum.DEV_PROJECT.getType());
                rdmsCharacter.setSetupedTime(new Date());
                //计算功能开发预算,并保存
                BigDecimal aFloat = calBudgetService.calcCharacterBudget(rdmsCharacter, projectSetupDto.getCustomerId());
                rdmsCharacter.setBudget(aFloat);

                String milestoneId = rdmsCharacter.getMilestoneId();
                RdmsMilestoneDto rdmsMilestoneDto = rdmsMilestoneService.selectByPrimaryKey(milestoneId);
                rdmsCharacter.setPlanCompleteTime(rdmsMilestoneDto.getReviewTime());

                String characterID = rdmsCharacterService.update(rdmsCharacter);
                //填写文件的访问权限人员
                if(!ObjectUtils.isEmpty(rdmsCharacter.getFileListStr())){
                    List<String> fileIdList = JSON.parseArray(rdmsCharacter.getFileListStr(), String.class);
                    if(!CollectionUtils.isEmpty(fileIdList)){
                        {
                            //设置文件授权 权限
                            RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                            roleUsersDto.setLoginUserId(null);
                            roleUsersDto.setReceiverId(null);
                            RdmsProject project = rdmsProjectService.selectByPrimaryKey(projectId);
                            RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(project.getCustomerId());
                            roleUsersDto.setBossId(rdmsBossDto.getBossId());
                            roleUsersDto.setSuperId(null);
                            roleUsersDto.setIpmtId(null);
                            roleUsersDto.setPjmId(projectSetupDto.getProjectManagerId());
                            roleUsersDto.setPdmId(null);
                            rdmsFileAuthService.setFileAuthUser(fileIdList, roleUsersDto);
                        }
                    }
                }

                //功能的保密等级
                RdmsCharacterSecLevel characterSecLevel = new RdmsCharacterSecLevel();
                characterSecLevel.setProjectId(projectId);
                characterSecLevel.setSubprojectId(projectId);
                characterSecLevel.setCharacterId(characterID);
                characterSecLevel.setLevel(projectSetupDto.getSecretLevel());
                rdmsCharacterSecLevelService.saveByCharacterId(characterSecLevel);

                //处理Character process
                RdmsCharacterProcess characterProcess = new RdmsCharacterProcess();
                characterProcess.setCharacterId(characterID);
                characterProcess.setExecutorId(projectSetupDto.getSupervise());
                characterProcess.setNextNode(rdmsCharacter.getNextNode());
                long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(characterID);
                characterProcess.setDeep((int)characterProcessCount);
                characterProcess.setJobDescription("功能已进入立项开发阶段");
                characterProcess.setProcessStatus(CharacterProcessStatusEnum.SETUPED.getStatus());
                rdmsCharacterProcessService.save(characterProcess);
            }

            //获取所有更新过的character,预算求和后更新项目预算
            List<RdmsCharacter> rdmsCharacters = rdmsCharacterService.listSetupedByProjectId(projectId);
            BigDecimal projectBudget = rdmsCharacters.stream().map(RdmsCharacter::getBudget).reduce(BigDecimal.valueOf(0.00), BigDecimal::add);
            RdmsProject rdmsProjectInfo = rdmsProjectService.selectByPrimaryKey(projectId);
            rdmsProjectInfo.setBudget(projectBudget);
            double directSumBudget = rdmsBudgetResearchService.getDirectSumBudgetByCustomerIdAndProjectId(rdmsProjectInfo.getCustomerId(), projectId);
            rdmsProjectInfo.setAddCharge(BigDecimal.valueOf(directSumBudget).subtract(projectBudget));
            rdmsProjectService.update(rdmsProjectInfo);
        }

        //创建一条默认对应主项目的子项目记录
        RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(projectId);
        RdmsProjectSubproject subproject = CopyUtil.copy(rdmsProject, RdmsProjectSubproject.class);
        subproject.setParent(projectId);
        subproject.setDeep(0);
        subproject.setProjectId(projectId);
        subproject.setLabel(rdmsProject.getProjectName());
        subproject.setIntroduce(rdmsProject.getProjectIntroduce());
        subproject.setCreatorId(rdmsProject.getSupervise());
        RdmsMilestone rdmsMilestone = rdmsMilestoneService.selectReleaseMilestone(rdmsProject.getId());
        subproject.setTargetMilestoneId(rdmsMilestone.getId());
        subproject.setCreateTime(new Date());
        subproject.setStatus(ProjectStatusEnum.SETUPED.getStatus());
        subproject.setType(ProjectTypeEnum.PROJECT.getType());
        rdmsSubprojectService.save(subproject);

        //创建子项目相关Gantt记录
        rdmsGanttService.createGanttRecordBySubproject(subproject);

        //向Budget表中写入一条项目预算记录
        RdmsBudget budget = new RdmsBudget();
        budget.setCustomerId(projectSetupDto.getCustomerId());
        budget.setProjectId(projectId);
        budget.setName(projectSetupDto.getProjectName());
        budget.setType(BudgetTypeEnum.PROJECT.getType());
        budget.setApplicantId(projectSetupDto.getProjectManagerId());  //预算的使用者
        budget.setApproverId(projectSetupDto.getSupervise());
        budget.setBudget(projectSetupDto.getBudget().add(projectSetupDto.getAddCharge()));
        budget.setStatus(BudgetStatusEnum.CREATED.getStatus());
        rdmsBudgetService.save(budget);

        responseDto.setContent(projectSetupDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }
    /**
     * 立项
     */
    @PostMapping("/save-setup-info")
    @Transactional
    public ResponseDto<RdmsProjectSetupDto> saveSetupInfo(@RequestBody RdmsProjectSetupDto projectSetupDto) {
        ResponseDto<RdmsProjectSetupDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(projectSetupDto.getCustomerId(), "客户ID");
        ValidatorUtil.require(projectSetupDto.getProjectName(), "项目名称");
        ValidatorUtil.require(projectSetupDto.getSupervise(), "监管委员");
        ValidatorUtil.require(projectSetupDto.getProjectManagerId(), "项目经理");
        ValidatorUtil.require(projectSetupDto.getTestManagerId(), "测试主管");
//        ValidatorUtil.require(projectSetupDto.getBudget(), "预算");
//        ValidatorUtil.require(projectSetupDto.getAddCharge(), "附加费");
        ValidatorUtil.require(projectSetupDto.getProjectCode(), "项目代码");
        ValidatorUtil.require(projectSetupDto.getSaveOrSubmitFlag(), "saveOrSubmit");
        ValidatorUtil.require(projectSetupDto.getCategoryId(), "项目分类");

        ValidatorUtil.length(projectSetupDto.getProjectName(), "项目名称", 2, 50);
        ValidatorUtil.length(projectSetupDto.getIncentivePolicy(), "激励政策", 2, 500);

        //查看项目编号是否重复
        /*List<RdmsProjectDto> projectListByCode = rdmsProjectService.getProjectListByCode(projectSetupDto.getProjectCode());
        for(RdmsProjectDto projectDto: projectListByCode){
            if(!projectDto.getStatus().equals(ProjectStatusEnum.SETUP.getStatus())){
                throw new BusinessException(BusinessExceptionCode.PROJECT_CODE_EXIST_ERROR);
            }
        }*/

        //处理项目信息
        RdmsProject projectInfo = CopyUtil.copy(projectSetupDto, RdmsProject.class);
        //从数据库中读出原有项目记录
        RdmsProject projectInfoDb = rdmsProjectService.selectByPrimaryKey(projectSetupDto.getId());
        projectInfo.setId(projectInfoDb.getId());
        if (CollectionUtils.isEmpty(projectSetupDto.getFileIdList())) {
            projectInfo.setFileListStr(projectInfoDb.getFileListStr());
        } else {
            String jsonString = JSON.toJSONString(projectSetupDto.getFileIdList());
            projectInfo.setFileListStr(jsonString);
        }

        String jsonString = JSON.toJSONString(projectSetupDto.getKeyMemberList());
        projectInfo.setKeyMemberListStr(jsonString);
        if(projectSetupDto.getSaveOrSubmitFlag().equals("save")){
            projectInfo.setStatus(ProjectStatusEnum.SETUP_EDIT.getStatus());
        }else if(projectSetupDto.getSaveOrSubmitFlag().equals("submit")){
            projectInfo.setStatus(ProjectStatusEnum.SETUP.getStatus());
        }
//        projectInfo.setSetupedTime(new Date());

        List<String> categoryIdList = new ArrayList<>();
        categoryIdList.add(projectSetupDto.getCustomerId());  //全部项目 分类标签
        categoryIdList.add(projectSetupDto.getCategoryId());
        projectInfo.setCategoryIdListStr(JSON.toJSONString(categoryIdList));

        String projectId = rdmsProjectService.save(projectInfo);
        //填写文件的访问权限人员
        if(!CollectionUtils.isEmpty(projectSetupDto.getFileIdList())){
            {
                //设置文件授权 权限
                RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                roleUsersDto.setLoginUserId(null);
                roleUsersDto.setReceiverId(null);
                RdmsProject project = rdmsProjectService.selectByPrimaryKey(projectId);
                RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(project.getCustomerId());
                roleUsersDto.setBossId(rdmsBossDto.getBossId());
                roleUsersDto.setSuperId(null);
                roleUsersDto.setIpmtId(null);
                roleUsersDto.setPjmId(projectSetupDto.getProjectManagerId());
                roleUsersDto.setPdmId(projectSetupDto.getProductManagerId());
                rdmsFileAuthService.setFileAuthUser(projectSetupDto.getFileIdList(), roleUsersDto);
            }
        }

        //保存项目保密等级
        RdmsProjectSecLevel projectSecLevel = new RdmsProjectSecLevel();
        projectSecLevel.setProjectId(projectId);
        projectSecLevel.setSubprojectId(projectId);
        projectSecLevel.setLevel(projectSetupDto.getSecretLevel());
        rdmsProjectSecLevelService.saveBySubprojectId(projectSecLevel);

        {
            //处理特性清单
            List<RdmsCharacterDto> characterDtoList = projectSetupDto.getCharacterList();
            List<RdmsCharacter> productCharacters = CopyUtil.copyList(characterDtoList, RdmsCharacter.class);
            for(RdmsCharacter rdmsCharacter : productCharacters){
                //update character表的数据
//                rdmsCharacter.setProjectId(projectId);
//                rdmsCharacter.setSubprojectId(projectId); //同时提供subProjectId
//                rdmsCharacter.setStatus(CharacterStatusEnum.SETUP.getStatus());  //和Setup不一样
//                rdmsCharacter.setNextNode(projectSetupDto.getProjectManagerId());
//                rdmsCharacter.setProjectType(ProjectTypeEnum.PRE_PROJECT.getType());  //
//                rdmsCharacter.setSetupedTime(new Date());  //
                //计算功能开发预算,并保存
                BigDecimal aFloat = calBudgetService.calcCharacterBudget(rdmsCharacter, projectSetupDto.getCustomerId());
                rdmsCharacter.setBudget(aFloat);

                /*String milestoneId = rdmsCharacter.getMilestoneId();
                RdmsMilestoneDto rdmsMilestoneDto = rdmsMilestoneService.selectByPrimaryKey(milestoneId);
                rdmsCharacter.setPlanCompleteTime(rdmsMilestoneDto.getReviewTime());*/

                String characterID = rdmsCharacterService.update(rdmsCharacter);
                RdmsCharacterSecLevel characterSecLevel = new RdmsCharacterSecLevel();
                characterSecLevel.setProjectId(projectId);
                characterSecLevel.setSubprojectId(projectId);
                characterSecLevel.setCharacterId(characterID);
                characterSecLevel.setLevel(projectSetupDto.getSecretLevel());
                rdmsCharacterSecLevelService.saveByCharacterId(characterSecLevel);

                //处理Character process
                RdmsCharacterProcess characterProcess = new RdmsCharacterProcess();
                characterProcess.setCharacterId(characterID);
                characterProcess.setExecutorId(projectSetupDto.getSupervise());  //??
                characterProcess.setNextNode(rdmsCharacter.getNextNode());
                long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(characterID);
                characterProcess.setDeep((int)characterProcessCount);
                characterProcess.setJobDescription("保存立项信息");
                characterProcess.setProcessStatus(CharacterProcessStatusEnum.SETUP.getStatus());
                rdmsCharacterProcessService.save(characterProcess);
            }

            //获取所有更新过的character,预算求和后更新项目预算
            List<RdmsCharacter> rdmsCharacters = rdmsCharacterService.listSetupedByProjectId(projectId);
            BigDecimal projectBudget = rdmsCharacters.stream().map(RdmsCharacter::getBudget).reduce(BigDecimal.valueOf(0.00), BigDecimal::add);
            RdmsProject rdmsProjectInfo = rdmsProjectService.selectByPrimaryKey(projectId);
            rdmsProjectInfo.setBudget(projectBudget);
            double directSumBudget = rdmsBudgetResearchService.getDirectSumBudgetByCustomerIdAndProjectId(rdmsProjectInfo.getCustomerId(), projectId);
            rdmsProjectInfo.setAddCharge(BigDecimal.valueOf(directSumBudget).subtract(projectBudget));
            rdmsProjectService.update(rdmsProjectInfo);
        }

/*        //创建一条默认对应主项目的子项目记录
        RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(projectId);
        RdmsProjectSubproject subproject = CopyUtil.copy(rdmsProject, RdmsProjectSubproject.class);
        subproject.setParent(projectId);
        subproject.setDeep(0);
        subproject.setProjectId(projectId);
        subproject.setLabel(rdmsProject.getProjectName());
        subproject.setIntroduce(rdmsProject.getProjectIntroduce());
        subproject.setCreatorId(rdmsProject.getSupervise());
        RdmsMilestone rdmsMilestone = rdmsMilestoneService.selectReleaseMilestone(rdmsProject.getId());
        subproject.setTargetMilestoneId(rdmsMilestone.getId());
        subproject.setCreateTime(new Date());
        subproject.setStatus(ProjectStatusEnum.SETUPED.getStatus());
        subproject.setType(ProjectTypeEnum.PROJECT.getType());
        rdmsSubprojectService.save(subproject);*/

        responseDto.setContent(projectSetupDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/save-product")
    @Transactional
    public ResponseDto<RdmsProjectSetupDto> saveProduct(@RequestBody RdmsProjectSetupDto projectSetupDto) {
        ResponseDto<RdmsProjectSetupDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(projectSetupDto.getCustomerId(), "客户ID");
        ValidatorUtil.require(projectSetupDto.getProjectName(), "项目名称");
        ValidatorUtil.require(projectSetupDto.getSupervise(), "监管委员");
        ValidatorUtil.require(projectSetupDto.getProjectManagerId(), "项目经理");
        ValidatorUtil.require(projectSetupDto.getProductManagerId(), "产品经理");
        ValidatorUtil.require(projectSetupDto.getTestManagerId(), "测试主管");
        ValidatorUtil.require(projectSetupDto.getProjectCode(), "项目代码");

        ValidatorUtil.length(projectSetupDto.getProjectName(), "项目名称", 2, 50);
        ValidatorUtil.length(projectSetupDto.getIncentivePolicy(), "激励政策", 2, 500);

        //处理项目信息
        RdmsProject projectInfo = CopyUtil.copy(projectSetupDto, RdmsProject.class);
        //从数据库中读
        RdmsProject projectInfoDb = rdmsProjectService.selectByPrimaryKey(projectSetupDto.getId());
        if(!ObjectUtils.isEmpty(projectInfoDb)){
            responseDto.setSuccess(false);
        }else {
            String jsonString = JSON.toJSONString(projectSetupDto.getKeyMemberList());
            projectInfo.setKeyMemberListStr(jsonString);
            projectInfo.setStatus(ProjectStatusEnum.ARCHIVED.getStatus());
            projectInfo.setRdType(RdTypeEnum.PRODUCT.getType());
            projectInfo.setBudget(BigDecimal.ZERO);
            projectInfo.setAddCharge(BigDecimal.ZERO);
            projectInfo.setChargeRate(BigDecimal.ONE);
            String projectId = rdmsProjectService.save(projectInfo);

            //创建对应的子项目
            RdmsProjectSubproject projectSubproject = CopyUtil.copy(projectInfo, RdmsProjectSubproject.class);
            projectSubproject.setParent(projectId);
            projectSubproject.setDeep(0);
            projectSubproject.setProjectId(projectId);
            projectSubproject.setLabel(projectInfo.getProjectName());
            projectSubproject.setIntroduce(projectInfo.getProjectIntroduce());
            projectSubproject.setCreatorId(projectInfo.getCreaterId());
            projectSubproject.setSetupedTime(projectInfo.getCreateTime());
            projectSubproject.setType(ProjectTypeEnum.PRODUCT.getType());
            rdmsSubprojectService.save(projectSubproject);

            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(projectId);
            //初始化budget_research表
            rdmsBudgetService.initProductBudgetSheetItem(projectSetupDto.getProductManagerId(), rdmsProject);
            responseDto.setContent(projectSetupDto);
        }
        responseDto.setSuccess(true);
        return responseDto;
    }



     @PostMapping("/setup-module-project")
    @Transactional
    public ResponseDto<RdmsProjectSetupDto> setupModuleProject(@RequestBody RdmsProjectSetupDto projectSetupDto) {
        ResponseDto<RdmsProjectSetupDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(projectSetupDto.getCustomerId(), "客户ID");
        ValidatorUtil.require(projectSetupDto.getProjectName(), "项目名称");
        ValidatorUtil.require(projectSetupDto.getSupervise(), "监管委员");
        ValidatorUtil.require(projectSetupDto.getProjectManagerId(), "项目经理");
        ValidatorUtil.require(projectSetupDto.getTestManagerId(), "测试主管");
        ValidatorUtil.require(projectSetupDto.getProjectCode(), "项目代码");

        ValidatorUtil.length(projectSetupDto.getProjectName(), "项目名称", 2, 50);
        //查看项目编号是否重复
        List<RdmsProjectDto> projectListByCode = rdmsProjectService.getProjectListByCode(projectSetupDto.getProjectCode());
        if(!CollectionUtils.isEmpty(projectListByCode)){
            throw new BusinessException(BusinessExceptionCode.PROJECT_CODE_EXIST_ERROR);
        }

        //处理项目信息
        RdmsProject projectInfo = CopyUtil.copy(projectSetupDto, RdmsProject.class);
        projectInfo.setStatus(ProjectStatusEnum.SETUPED.getStatus());
        projectInfo.setSetupedTime(new Date());
        projectInfo.setBudget(BigDecimal.ZERO);
        projectInfo.setAddCharge(BigDecimal.ZERO);
//        projectInfo.setChargeRate(BigDecimal.ZERO);
        String projectId = rdmsProjectService.save(projectInfo);

         //保存主项目预算
         RdmsBudgetResearchDto rdmsBudgetResearch = new RdmsBudgetResearchDto();
         rdmsBudgetResearch.setId(null);
         rdmsBudgetResearch.setCustomerId(projectSetupDto.getCustomerId());
         rdmsBudgetResearch.setProjectId(projectId);
         rdmsBudgetResearch.setParentId(null);
         rdmsBudgetResearch.setSubprojectId(null);  //子项目ID
         rdmsBudgetResearch.setIsRoot(1);
         rdmsBudgetResearchService.save(rdmsBudgetResearch);

         //保存作为子项目的主项目的预算
         rdmsBudgetResearch.setId(null);
         rdmsBudgetResearch.setCustomerId(projectSetupDto.getCustomerId());
         rdmsBudgetResearch.setProjectId(projectId);
         rdmsBudgetResearch.setParentId(projectId);
         rdmsBudgetResearch.setSubprojectId(projectId);  //子项目ID
         rdmsBudgetResearch.setIsRoot(0);
         rdmsBudgetResearchService.save(rdmsBudgetResearch);

        //创建一条默认对应主项目的子项目记录
        RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(projectId);
        RdmsProjectSubproject subproject = CopyUtil.copy(rdmsProject, RdmsProjectSubproject.class);
        subproject.setParent(projectId);
        subproject.setDeep(0);
        subproject.setProjectId(projectId);
        subproject.setLabel(rdmsProject.getProjectName());
        subproject.setIntroduce(rdmsProject.getProjectIntroduce());
        subproject.setCreatorId(rdmsProject.getSupervise());
        subproject.setCreateTime(new Date());
        subproject.setStatus(ProjectStatusEnum.SETUPED.getStatus());
        subproject.setType(ProjectTypeEnum.PROJECT.getType());
        rdmsSubprojectService.save(subproject);

        //为产品经理创建一个预立项项目
         RdmsPreProject projectPrepare = new RdmsPreProject();
         projectPrepare.setId(null);
         projectPrepare.setCustomerId(projectSetupDto.getCustomerId());
         projectPrepare.setPreProjectName("预立项:"+projectSetupDto.getProjectName());
         projectPrepare.setDevVersion(projectSetupDto.getDevVersion());
         projectPrepare.setPreProjectIntroduce("对功能/组件迭代项目进行处理.");
         projectPrepare.setProductManagerId(projectSetupDto.getProductManagerId());
         projectPrepare.setRdType(RdTypeEnum.MODULE.getType());
         rdmsPreProjectService.save(projectPrepare);

        responseDto.setContent(projectSetupDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

     /**
     * 立项
     */
    @PostMapping("/edit")
    @Transactional
    public ResponseDto<RdmsProjectDto> edit(@RequestBody RdmsProjectDto projectInfoDto) {
        ResponseDto<RdmsProjectDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(projectInfoDto.getCustomerId(), "客户ID");
        ValidatorUtil.require(projectInfoDto.getProjectName(), "项目名称");

        ValidatorUtil.length(projectInfoDto.getProjectName(), "项目名称", 2, 50);

        //处理项目信息
        RdmsProject projectInfo = CopyUtil.copy(projectInfoDto, RdmsProject.class);
        String jsonString = JSON.toJSONString(projectInfoDto.getKeyMemberList());
        projectInfo.setKeyMemberListStr(jsonString);
        rdmsProjectService.updateByPrimaryKeySelective(projectInfo);

        //修改作为子项目的主项目
        RdmsProjectSubproject rdmsProjectSubproject = new RdmsProjectSubproject();
        rdmsProjectSubproject.setId(projectInfo.getId());
        rdmsProjectSubproject.setLabel(projectInfo.getProjectName());
        rdmsProjectSubproject.setSupervise(projectInfo.getSupervise());
        rdmsProjectSubproject.setProductManagerId(projectInfo.getProductManagerId());
        rdmsProjectSubproject.setProjectManagerId(projectInfo.getProjectManagerId());
//        rdmsProjectSubproject.setSystemManagerId(projectInfo.getSystemManagerId());  //子项目中不保存系统工程信息, 统一到项目记录中读取
        rdmsProjectSubproject.setTestManagerId(projectInfo.getTestManagerId());
        rdmsProjectSubproject.setKeyMemberListStr(projectInfo.getKeyMemberListStr());
        rdmsSubprojectService.updateByPrimaryKeySelective(rdmsProjectSubproject);

        {//如果项目经理发生了变化
            if (!projectInfoDto.getProjectManagerId().equals(projectInfoDto.getOriginProjectManagerId())) {
                //修改直接下级子项目的creatorId
                List<RdmsProjectSubproject> subprojectListByParentId = rdmsSubprojectService.getSubprojectListByParentId(projectInfo.getId());
                if(!CollectionUtils.isEmpty(subprojectListByParentId)){
                    for(RdmsProjectSubproject subproject: subprojectListByParentId){
                        subproject.setCreatorId(projectInfoDto.getProjectManagerId());
                        rdmsSubprojectService.updateByPrimaryKeySelective(subproject);
                    }
                }

                //将该项目项下所有任务的项目经理更新为新的项目经理;
                List<RdmsJobItemDto> jobitemList = rdmsJobItemService.getJobitemListBySubprojectId(projectInfo.getId(), null, null);
                if (!CollectionUtils.isEmpty(jobitemList)) {
                    for (RdmsJobItemDto jobItemDto : jobitemList) {
                        //修改项目经理
                        if (jobItemDto.getProjectManagerId().equals(projectInfoDto.getOriginProjectManagerId())) {
                            jobItemDto.setProjectManagerId(projectInfoDto.getProjectManagerId());
                            rdmsJobItemService.updateByPrimaryKeySelective(jobItemDto);
                        }
                        //获取Job的附件和property的附件
                        List<String> fileIds = new ArrayList<>();
                        List<String> fileIdList = JSON.parseArray(jobItemDto.getFileListStr(), String.class);
                        if(!CollectionUtils.isEmpty(fileIdList)){
                            fileIds.addAll(fileIdList);
                        }
                        List<String> propertyFileIdList = rdmsJobItemService.getPropertyFileIdList(jobItemDto.getId());
                        if(!CollectionUtils.isEmpty(fileIdList)){
                            fileIds.addAll(propertyFileIdList);
                        }
                        //查看文件的授权, 授权给原来项目经理的,改为授权给新项目经理
                        if(!CollectionUtils.isEmpty(fileIds)){
                            for(String fileId: fileIds){
                                RdmsFileAuth byFileId = rdmsFileAuthService.getByFileId(fileId);
                                if(!(ObjectUtils.isEmpty(byFileId) || ObjectUtils.isEmpty(byFileId.getAuthIdsStr()))){
                                    String replace = byFileId.getAuthIdsStr().replace(projectInfoDto.getOriginProjectManagerId(), projectInfoDto.getProjectManagerId());
                                    byFileId.setAuthIdsStr(replace);
                                    rdmsFileAuthService.updateByPrimaryKeySelective(byFileId);
                                }
                            }
                        }

                    }
                }
                //处理未完成的任务
                List<JobItemStatusEnum> statusEnumList = new ArrayList<>();
                statusEnumList.add(JobItemStatusEnum.PLAN);
                statusEnumList.add(JobItemStatusEnum.HANDLING);
                statusEnumList.add(JobItemStatusEnum.SUBMIT);
                statusEnumList.add(JobItemStatusEnum.TESTING);
                statusEnumList.add(JobItemStatusEnum.CHA_RECHECK);
                statusEnumList.add(JobItemStatusEnum.QUA_RECHECK);
                statusEnumList.add(JobItemStatusEnum.SUB_RECHECK);
                statusEnumList.add(JobItemStatusEnum.EVALUATE);
                statusEnumList.add(JobItemStatusEnum.APPROVED);
                List<RdmsJobItem> jobitemListByNextNodeId = rdmsJobItemService.getJobitemListByNextNodeId(projectInfoDto.getOriginProjectManagerId(), statusEnumList);
                if (!CollectionUtils.isEmpty(jobitemListByNextNodeId)) {
                    for (RdmsJobItem jobItem : jobitemListByNextNodeId) {
                        jobItem.setNextNode(projectInfoDto.getProjectManagerId());
                        rdmsJobItemService.updateByPrimaryKeySelective(jobItem);
                    }
                }

                //处理未完成的物料申请
                {
                    List<RdmsMaterialManageDto> listByNextNodeId = rdmsMaterialManageService.getListByNextNodeIdForApprover(projectInfoDto.getOriginProjectManagerId());
                    if(!CollectionUtils.isEmpty(listByNextNodeId)){
                        for(RdmsMaterialManageDto materialManageDto: listByNextNodeId){
                            RdmsMaterialManage materialManage = CopyUtil.copy(materialManageDto, RdmsMaterialManage.class);
                            materialManage.setNextNode(projectInfoDto.getProjectManagerId());
                            rdmsMaterialManageService.updateByPrimaryKeySelective(materialManage);
                        }
                    }
                }
                //处理未完成的费用申请
                {
                    List<RdmsFeeManage> listByNextNodeId = rdmsFeeManageService.getListByNextNodeId(projectInfoDto.getOriginProjectManagerId());
                    if(!CollectionUtils.isEmpty(listByNextNodeId)){
                        for(RdmsFeeManage feeManageDto: listByNextNodeId){
                            feeManageDto.setNextNode(projectInfoDto.getProjectManagerId());
                            rdmsFeeManageService.updateByPrimaryKeySelective(feeManageDto);
                        }
                    }
                }
            }
        }

        {//如果系统工程师发生了变化
            if (!projectInfoDto.getSystemManagerId().equals(projectInfoDto.getOriginSystemManagerId())) {
                //将该项目项下所有任务的项目经理更新为新的项目经理;
                List<RdmsJobItemDto> jobitemList = rdmsJobItemService.getJobitemListBySubprojectId(projectInfo.getId(), null, null);
                if (!CollectionUtils.isEmpty(jobitemList)) {
                    for (RdmsJobItemDto jobItemDto : jobitemList) {
                        //修改执行人
                        if (jobItemDto.getExecutorId().equals(projectInfoDto.getOriginSystemManagerId())) {
                            jobItemDto.setExecutorId(projectInfoDto.getSystemManagerId());
                            rdmsJobItemService.updateByPrimaryKeySelective(jobItemDto);
                        }
                        //获取Job的附件和property的附件
                        List<String> fileIds = new ArrayList<>();
                        List<String> fileIdList = JSON.parseArray(jobItemDto.getFileListStr(), String.class);
                        if(!CollectionUtils.isEmpty(fileIdList)){
                            fileIds.addAll(fileIdList);
                        }
                        List<String> propertyFileIdList = rdmsJobItemService.getPropertyFileIdList(jobItemDto.getId());
                        if(!CollectionUtils.isEmpty(fileIdList)){
                            fileIds.addAll(propertyFileIdList);
                        }
                        //查看文件的授权, 授权给原来项目经理的,改为授权给新项目经理
                        if(!CollectionUtils.isEmpty(fileIds)){
                            for(String fileId: fileIds){
                                RdmsFileAuth byFileId = rdmsFileAuthService.getByFileId(fileId);
                                if(!(ObjectUtils.isEmpty(byFileId) || ObjectUtils.isEmpty(byFileId.getAuthIdsStr()))){
                                    String replace = byFileId.getAuthIdsStr().replace(projectInfoDto.getOriginSystemManagerId(), projectInfoDto.getSystemManagerId());
                                    byFileId.setAuthIdsStr(replace);
                                    rdmsFileAuthService.updateByPrimaryKeySelective(byFileId);
                                }
                            }
                        }

                    }
                }
                //处理未完成的任务
                List<JobItemStatusEnum> statusEnumList = new ArrayList<>();
                statusEnumList.add(JobItemStatusEnum.PLAN);
                statusEnumList.add(JobItemStatusEnum.HANDLING);
                statusEnumList.add(JobItemStatusEnum.SUBMIT);
                statusEnumList.add(JobItemStatusEnum.TESTING);
                statusEnumList.add(JobItemStatusEnum.CHA_RECHECK);
                statusEnumList.add(JobItemStatusEnum.QUA_RECHECK);
                statusEnumList.add(JobItemStatusEnum.SUB_RECHECK);
                statusEnumList.add(JobItemStatusEnum.EVALUATE);
                statusEnumList.add(JobItemStatusEnum.APPROVED);
                List<RdmsJobItem> jobitemListByNextNodeId = rdmsJobItemService.getJobitemListByNextNodeId(projectInfoDto.getOriginSystemManagerId(), statusEnumList);
                if (!CollectionUtils.isEmpty(jobitemListByNextNodeId)) {
                    for (RdmsJobItem jobItem : jobitemListByNextNodeId) {
                        jobItem.setNextNode(projectInfoDto.getSystemManagerId());
                        rdmsJobItemService.updateByPrimaryKeySelective(jobItem);
                    }
                }
                //处理未完成的物料申请
                {
                    List<RdmsMaterialManageDto> listByNextNodeId = rdmsMaterialManageService.getListByNextNodeIdForApprover(projectInfoDto.getOriginSystemManagerId());
                    if(!CollectionUtils.isEmpty(listByNextNodeId)){
                        for(RdmsMaterialManageDto materialManageDto: listByNextNodeId){
                            RdmsMaterialManage materialManage = CopyUtil.copy(materialManageDto, RdmsMaterialManage.class);
                            materialManage.setNextNode(projectInfoDto.getSystemManagerId());
                            rdmsMaterialManageService.updateByPrimaryKeySelective(materialManage);
                        }
                    }
                }
                //处理未完成的费用申请
                {
                    List<RdmsFeeManage> listByNextNodeId = rdmsFeeManageService.getListByNextNodeId(projectInfoDto.getOriginSystemManagerId());
                    if(!CollectionUtils.isEmpty(listByNextNodeId)){
                        for(RdmsFeeManage feeManageDto: listByNextNodeId){
                            feeManageDto.setNextNode(projectInfoDto.getSystemManagerId());
                            rdmsFeeManageService.updateByPrimaryKeySelective(feeManageDto);
                        }
                    }
                }
            }
        }

        {//如果产品经理发生了变化
            if (!projectInfoDto.getProductManagerId().equals(projectInfoDto.getOriginProductManagerId())) {
                //该项目的子项目的的上一级产品经理需要改成新的产品经理
                List<RdmsProjectSubprojectDto> subprojectListByProjectId = rdmsProjectService.getSubprojectListByProjectId(projectInfo.getId());
                for (RdmsProjectSubproject subproject : subprojectListByProjectId) {
                    subproject.setProductManagerId(projectInfoDto.getProductManagerId());
                    rdmsSubprojectService.updateByPrimaryKeySelective(subproject);
                }

                //将该项目项下所有任务的产品经理更新为新的产品经理;
                List<RdmsJobItemDto> jobitemList = rdmsJobItemService.getJobitemListByProjectId(projectInfo.getId(), null, null);
                if (!CollectionUtils.isEmpty(jobitemList)) {
                    for (RdmsJobItemDto jobItemDto : jobitemList) {
                        //修改产品经理
                        if (jobItemDto.getProductManagerId().equals(projectInfoDto.getOriginProductManagerId())) {
                            jobItemDto.setProductManagerId(projectInfoDto.getProductManagerId());
                            rdmsJobItemService.updateByPrimaryKeySelective(jobItemDto);
                        }
                    }
                }
                //将公司所有功能定义的产品经理改为新的产品经理;
                List<RdmsCharacter> characterList = rdmsCharacterService.getCharacterListByProjectId(projectInfo.getId());
                if (!CollectionUtils.isEmpty(characterList)) {
                    for (RdmsCharacter character : characterList) {
                        //修改产品经理
                        if (character.getProductManagerId().equals(projectInfoDto.getOriginProductManagerId())) {
                            character.setProductManagerId(projectInfoDto.getProductManagerId());
                            rdmsCharacterService.updateByPrimaryKeySelective(character);
                        }
                    }
                }
                //处理未完成的任务
                List<JobItemStatusEnum> statusEnumList = new ArrayList<>();
                statusEnumList.add(JobItemStatusEnum.PLAN);
                statusEnumList.add(JobItemStatusEnum.HANDLING);
                statusEnumList.add(JobItemStatusEnum.SUBMIT);
                statusEnumList.add(JobItemStatusEnum.TESTING);
                statusEnumList.add(JobItemStatusEnum.CHA_RECHECK);
                statusEnumList.add(JobItemStatusEnum.QUA_RECHECK);
                statusEnumList.add(JobItemStatusEnum.SUB_RECHECK);
                statusEnumList.add(JobItemStatusEnum.EVALUATE);
                statusEnumList.add(JobItemStatusEnum.APPROVED);
                List<RdmsJobItem> jobitemListByNextNodeId = rdmsJobItemService.getJobitemListByNextNodeId(projectInfoDto.getOriginProductManagerId(), statusEnumList);
                if (!CollectionUtils.isEmpty(jobitemListByNextNodeId)) {
                    for (RdmsJobItem jobItem : jobitemListByNextNodeId) {
                        jobItem.setNextNode(projectInfoDto.getProductManagerId());
                        rdmsJobItemService.updateByPrimaryKeySelective(jobItem);
                    }
                }
                //处理未完成的物料申请
                {
                    List<RdmsMaterialManageDto> listByNextNodeId = rdmsMaterialManageService.getListByNextNodeIdForApprover(projectInfoDto.getOriginProductManagerId());
                    if(!CollectionUtils.isEmpty(listByNextNodeId)){
                        for(RdmsMaterialManageDto materialManageDto: listByNextNodeId){
                            RdmsMaterialManage materialManage = CopyUtil.copy(materialManageDto, RdmsMaterialManage.class);
                            materialManage.setNextNode(projectInfoDto.getProductManagerId());
                            rdmsMaterialManageService.updateByPrimaryKeySelective(materialManage);
                        }
                    }
                }
                //处理未完成的费用申请
                {
                    List<RdmsFeeManage> listByNextNodeId = rdmsFeeManageService.getListByNextNodeId(projectInfoDto.getOriginProductManagerId());
                    if(!CollectionUtils.isEmpty(listByNextNodeId)){
                        for(RdmsFeeManage feeManageDto: listByNextNodeId){
                            feeManageDto.setNextNode(projectInfoDto.getProductManagerId());
                            rdmsFeeManageService.updateByPrimaryKeySelective(feeManageDto);
                        }
                    }
                }

            }
        }

        {//如果测试主管发生了变化
            if (!projectInfoDto.getTestManagerId().equals(projectInfoDto.getOriginTestManagerId())) {
                //将该项目项下所有任务的测试主管更新为新的测试主管;
                List<RdmsJobItemDto> jobitemList = rdmsJobItemService.getJobitemListBySubprojectId(projectInfo.getId(), null, null);
                if (!CollectionUtils.isEmpty(jobitemList)) {
                    for (RdmsJobItemDto jobItemDto : jobitemList) {
                        //修改测试主管
                        if (jobItemDto.getTestManagerId().equals(projectInfoDto.getOriginTestManagerId())) {
                            jobItemDto.setTestManagerId(projectInfoDto.getTestManagerId());
                            rdmsJobItemService.updateByPrimaryKeySelective(jobItemDto);
                        }
                    }
                }
                //处理未完成的任务
                List<JobItemStatusEnum> statusEnumList = new ArrayList<>();
                statusEnumList.add(JobItemStatusEnum.PLAN);
                statusEnumList.add(JobItemStatusEnum.HANDLING);
                statusEnumList.add(JobItemStatusEnum.SUBMIT);
                statusEnumList.add(JobItemStatusEnum.TESTING);
                statusEnumList.add(JobItemStatusEnum.CHA_RECHECK);
                statusEnumList.add(JobItemStatusEnum.QUA_RECHECK);
                statusEnumList.add(JobItemStatusEnum.SUB_RECHECK);
                statusEnumList.add(JobItemStatusEnum.EVALUATE);
                statusEnumList.add(JobItemStatusEnum.APPROVED);
                List<RdmsJobItem> jobitemListByNextNodeId = rdmsJobItemService.getJobitemListByNextNodeId(projectInfoDto.getOriginTestManagerId(), statusEnumList);
                if (!CollectionUtils.isEmpty(jobitemListByNextNodeId)) {
                    for (RdmsJobItem jobItem : jobitemListByNextNodeId) {
                        jobItem.setNextNode(projectInfoDto.getTestManagerId());
                        rdmsJobItemService.updateByPrimaryKeySelective(jobItem);
                    }
                }
                //处理未完成的物料申请
                {
                    List<RdmsMaterialManageDto> listByNextNodeId = rdmsMaterialManageService.getListByNextNodeIdForApprover(projectInfoDto.getOriginTestManagerId());
                    if(!CollectionUtils.isEmpty(listByNextNodeId)){
                        for(RdmsMaterialManageDto materialManageDto: listByNextNodeId){
                            RdmsMaterialManage materialManage = CopyUtil.copy(materialManageDto, RdmsMaterialManage.class);
                            materialManage.setNextNode(projectInfoDto.getTestManagerId());
                            rdmsMaterialManageService.updateByPrimaryKeySelective(materialManage);
                        }
                    }
                }
                //处理未完成的费用申请
                {
                    List<RdmsFeeManage> listByNextNodeId = rdmsFeeManageService.getListByNextNodeId(projectInfoDto.getOriginTestManagerId());
                    if(!CollectionUtils.isEmpty(listByNextNodeId)){
                        for(RdmsFeeManage feeManageDto: listByNextNodeId){
                            feeManageDto.setNextNode(projectInfoDto.getTestManagerId());
                            rdmsFeeManageService.updateByPrimaryKeySelective(feeManageDto);
                        }
                    }
                }
            }
        }

        {//如果IPMT发生了变化
            if (!projectInfoDto.getSupervise().equals(projectInfoDto.getOriginIpmtId())) {
                //该项目的子项目的的上一级IPMT需要改成新的IPMT
                List<RdmsProjectSubproject> subprojectListByParentId = rdmsSubprojectService.getSubprojectListByParentId(projectInfo.getId());
                for (RdmsProjectSubproject subproject : subprojectListByParentId) {
                    subproject.setSupervise(projectInfoDto.getSupervise());
                    rdmsSubprojectService.updateByPrimaryKeySelective(subproject);
                }
            }
        }

        responseDto.setContent(null);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

     /**
     * 追加特性或功能
     */
    @PostMapping("/append")
    @Transactional
    public ResponseDto<RdmsProjectSetupDto> append(@RequestBody RdmsProjectSetupDto projectSetupDto) {
        ResponseDto<RdmsProjectSetupDto> responseDto = new ResponseDto<>();
        //处理特性清单
        List<RdmsCharacterDto> characterDtoList = projectSetupDto.getCharacterList();
        List<RdmsCharacter> productCharacters = CopyUtil.copyList(characterDtoList, RdmsCharacter.class);
        BigDecimal childrenSumBudget = new BigDecimal("0");

        RdmsBudgetResearchDto budgetData = new RdmsBudgetResearchDto();

        for(RdmsCharacter rdmsCharacter : productCharacters){
            //update character表的数据
            rdmsCharacter.setProjectId(projectSetupDto.getTargetProjectId());
            rdmsCharacter.setSubprojectId(projectSetupDto.getTargetProjectId());
            rdmsCharacter.setStatus(CharacterStatusEnum.SETUPED.getStatus());
            rdmsCharacter.setSetupedTime(new Date());
            //计算功能开发预算,并保存
            BigDecimal aFloat = calBudgetService.calcCharacterBudget(rdmsCharacter, projectSetupDto.getCustomerId());
            rdmsCharacter.setBudget(aFloat);
            childrenSumBudget = childrenSumBudget.add(rdmsCharacter.getBudget());

            budgetData.setTestFeeZc(budgetData.getTestFeeZc().add(rdmsCharacter.getTestFee()));
            budgetData.setMaterialFeeZc(budgetData.getMaterialFeeZc().add(rdmsCharacter.getMaterialFee()));
            budgetData.setPowerFeeZc(budgetData.getPowerFeeZc().add(rdmsCharacter.getPowerFee()));
            budgetData.setConferenceFeeZc(budgetData.getConferenceFeeZc().add(rdmsCharacter.getConferenceFee()));
            budgetData.setBusinessFeeZc(budgetData.getBusinessFeeZc().add(rdmsCharacter.getBusinessFee()));
            budgetData.setCooperationFeeZc(budgetData.getCooperationFeeZc().add(rdmsCharacter.getCooperationFee()));
            budgetData.setPropertyFeeZc(budgetData.getPropertyFeeZc().add(rdmsCharacter.getPropertyFee()));
            budgetData.setLaborFeeZc(budgetData.getLaborFeeZc().add(rdmsCharacter.getLaborFee()));

            RdmsManhourStandard standManhour = rdmsManhourService.getStandardManhourByCustomerId(projectSetupDto.getCustomerId());
            BigDecimal devFee = BigDecimal.valueOf(rdmsCharacter.getDevManhourApproved()).multiply(standManhour.getDevManhourFee());
            BigDecimal testFee = BigDecimal.valueOf(rdmsCharacter.getTestManhourApproved()).multiply(standManhour.getTestManhourFee());
            budgetData.setStaffFeeZc(budgetData.getStaffFeeZc().add(devFee.add(testFee)));
            budgetData.setConsultingFeeZc(budgetData.getConsultingFeeZc().add(rdmsCharacter.getConsultingFee()));
            budgetData.setManagementFeeZc(budgetData.getManagementFeeZc().add(rdmsCharacter.getManagementFee()));

            if(!projectSetupDto.getRdType().equals(RdTypeEnum.MODULE.getType())){
                String milestoneId = rdmsCharacter.getMilestoneId();
                RdmsMilestoneDto rdmsMilestoneDto = rdmsMilestoneService.selectByPrimaryKey(milestoneId);
                rdmsCharacter.setPlanCompleteTime(rdmsMilestoneDto.getReviewTime());
            }
            rdmsCharacterService.update(rdmsCharacter);
        }

        //更新项目预算
        RdmsProject rdmsProjectInfo = rdmsProjectService.selectByPrimaryKey(projectSetupDto.getTargetProjectId());
/*        rdmsProjectInfo.setBudget(rdmsProjectInfo.getBudget().add(childrenSumBudget));
        rdmsProjectInfo.setChargeRate(rdmsProjectInfo.getAddCharge().divide(rdmsProjectInfo.getBudget().add(rdmsProjectInfo.getAddCharge()), 4, RoundingMode.HALF_UP));
        rdmsProjectService.update(rdmsProjectInfo);*/

        //同步修改, 已立项项目的子项信息
        RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(rdmsProjectInfo.getId());
/*        subproject.setBudget(subproject.getBudget().add(childrenSumBudget));  //将从功能追加过程中获得的预算, 直接加在子项目预算上
        subproject.setChargeRate(subproject.getAddCharge().divide(subproject.getBudget().add(subproject.getAddCharge()), 4, RoundingMode.HALF_UP));
        rdmsSubprojectService.save(subproject);*/

        subproject.setModuleIdListStr(projectSetupDto.getModuleIdListStr());
        rdmsSubprojectService.save(subproject);

        //调整预算总表, 包括主项和作为子项目的主项目
        rdmsBudgetResearchService.incrementSubprojectBudget(subproject.getCustomerId(), subproject.getId(), projectSetupDto.getLoginUserId(), budgetData);

        //如果预立项项目中所有功能都已经追加出去了, 从数据库删除功能特性已经被追加到其他项目的项目
        //查询预立项项目下,处于setup状态的记录
        if(!projectSetupDto.getRdType().equals(RdTypeEnum.MODULE.getType())){
            List<CharacterStatusEnum> statusEnumList = new ArrayList<>();
            statusEnumList.add(CharacterStatusEnum.SETUP);
            if(!ObjectUtils.isEmpty(projectSetupDto.getId())){
                List<RdmsCharacterDto> characterByPreProjectIdAndStatus = rdmsCharacterService.getCharacterByPreProjectIdAndStatus(projectSetupDto.getId(), statusEnumList);
                if(characterByPreProjectIdAndStatus.isEmpty()){
                /*RdmsProject projectInfoDb = rdmsProjectService.selectByPrimaryKey(projectSetupDto.getId());
                projectInfoDb.setDeleted(1);*/
                    rdmsProjectService.delete(projectSetupDto.getId());
                }
            }
        }else{
            List<CharacterStatusEnum> statusEnumList = new ArrayList<>();
            statusEnumList.add(CharacterStatusEnum.SETUP);
            if(!ObjectUtils.isEmpty(projectSetupDto.getModuleSetupProjectId())){
                List<RdmsCharacterDto> characterByPreProjectIdAndStatus = rdmsCharacterService.getCharacterByPreProjectIdAndStatus(projectSetupDto.getModuleSetupProjectId(), statusEnumList);
                if(characterByPreProjectIdAndStatus.isEmpty()){
                /*RdmsProject projectInfoDb = rdmsProjectService.selectByPrimaryKey(projectSetupDto.getId());
                projectInfoDb.setDeleted(1);*/
                    rdmsProjectService.delete(projectSetupDto.getModuleSetupProjectId());
                }
            }
        }

        responseDto.setContent(projectSetupDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

     /**
     * 申请立项
     */
    @PostMapping("/setup-application")
    @Transactional
    public ResponseDto<RdmsProjectSetupDto> setupApplication(@RequestBody RdmsProjectSetupDto projectSetupDto) {
        ResponseDto<RdmsProjectSetupDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(projectSetupDto.getCustomerId(), "客户ID");
        ValidatorUtil.require(projectSetupDto.getProjectName(), "项目名称");
        ValidatorUtil.require(projectSetupDto.getSupervise(),"IPMT/ITMT委员");
        ValidatorUtil.require(projectSetupDto.getProductManagerId(),"产品经理");
        ValidatorUtil.require(projectSetupDto.getCharacterList(),"功能/特性清单");
        ValidatorUtil.require(projectSetupDto.getLoginUserId(),"创建者");

        ValidatorUtil.length(projectSetupDto.getProjectName(), "项目名称", 2, 50);

        //处理项目信息
        RdmsProject projectInfo = CopyUtil.copy(projectSetupDto, RdmsProject.class);
        //查询是否有重名的项目
        long l = rdmsProjectService.projectNameIsUsed(projectSetupDto.getCustomerId(), projectSetupDto.getProjectName());
        if(l>0){
            projectInfo.setProjectName(projectInfo.getProjectName() + "-" +  String.valueOf(new Date().getTime()).substring(8));
        }

        projectInfo.setStatus(ProjectStatusEnum.SETUP_EDIT.getStatus()); //setup_edit仅仅是创建了一个编辑条目; 从编辑条目中提交之后, 才是setup状态; IPMT审批之后,才是setuped状态; setup_edit状态是可以"撤回"的, setup状态就只能ipmt驳回了
        projectInfo.setCreaterId(projectSetupDto.getLoginUserId());

        if(!CollectionUtils.isEmpty(projectSetupDto.getFileIdList())){
            String jsonString = JSON.toJSONString(projectSetupDto.getFileIdList());
            projectInfo.setFileListStr(jsonString);
        }
        if(!ObjectUtils.isEmpty(projectSetupDto.getPreProjectId())){
            RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(projectSetupDto.getPreProjectId());
            if(!ObjectUtils.isEmpty(rdmsPreProject) && !ObjectUtils.isEmpty(rdmsPreProject.getSystemManagerId())){
                projectInfo.setSystemManagerId(rdmsPreProject.getSystemManagerId());
            }
        }

        String projectId = rdmsProjectService.save(projectInfo);
        //填写文件的访问权限人员
        if(!ObjectUtils.isEmpty(projectInfo.getFileListStr())){
            List<String> fileIdList = JSON.parseArray(projectInfo.getFileListStr(), String.class);
            if(!CollectionUtils.isEmpty(fileIdList)){
                {
                    //设置文件授权 权限
                    RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                    roleUsersDto.setLoginUserId(null);
                    roleUsersDto.setReceiverId(null);
                    RdmsProject project = rdmsProjectService.selectByPrimaryKey(projectId);
                    RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(project.getCustomerId());
                    roleUsersDto.setBossId(rdmsBossDto.getBossId());
                    roleUsersDto.setSuperId(null);
                    roleUsersDto.setIpmtId(null);
                    roleUsersDto.setPjmId(null);
                    roleUsersDto.setPdmId(null);
                    rdmsFileAuthService.setFileAuthUser(fileIdList, roleUsersDto);
                }
            }
        }

        //处理特性清单
        List<RdmsCharacterDto> characterDtoList = projectSetupDto.getCharacterList();
        List<RdmsCharacter> productCharacters = CopyUtil.copyList(characterDtoList, RdmsCharacter.class);
        for(RdmsCharacter rdmsCharacter : productCharacters){
            //update character表的数据
            rdmsCharacter.setProjectId(projectId);
            rdmsCharacter.setStatus(CharacterStatusEnum.SETUP.getStatus());
            String characterId = rdmsCharacterService.update(rdmsCharacter);
            //填写文件的访问权限人员
            if(!ObjectUtils.isEmpty(rdmsCharacter.getFileListStr())){
                List<String> fileIdList = JSON.parseArray(rdmsCharacter.getFileListStr(), String.class);
                if(!CollectionUtils.isEmpty(fileIdList)){
                    {
                        //设置文件授权 权限
                        RdmsRoleUsersDto roleUsersDto = new RdmsRoleUsersDto();
                        roleUsersDto.setLoginUserId(null);
                        roleUsersDto.setReceiverId(null);
                        RdmsProject project = rdmsProjectService.selectByPrimaryKey(projectId);
                        RdmsBossDto rdmsBossDto = rdmsBossService.getBossByCustomerId(project.getCustomerId());
                        roleUsersDto.setBossId(rdmsBossDto.getBossId());
                        roleUsersDto.setSuperId(null);
                        roleUsersDto.setIpmtId(null);
                        roleUsersDto.setPjmId(null);
                        roleUsersDto.setPdmId(null);
                        rdmsFileAuthService.setFileAuthUser(fileIdList, roleUsersDto);
                    }
                }
            }

            //Character Process
            RdmsCharacterProcess characterProcess = new RdmsCharacterProcess();
            characterProcess.setCharacterId(characterId);
            characterProcess.setExecutorId(projectSetupDto.getProductManagerId()); //当前执行动作的人
            long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(characterId);
            characterProcess.setDeep((int)characterProcessCount);
            characterProcess.setJobDescription("提交立项申请");
            characterProcess.setProcessStatus(CharacterProcessStatusEnum.SETUP.getStatus());
            characterProcess.setNextNode(projectSetupDto.getSupervise());
            String characterProcessId = rdmsCharacterProcessService.save(characterProcess);
        }

        responseDto.setContent(projectSetupDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/direct-setup")
    @Transactional
    public ResponseDto<RdmsProjectSetupDto> directSetup(@RequestBody RdmsProjectSetupDto projectSetupDto) {
        ResponseDto<RdmsProjectSetupDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(projectSetupDto.getCustomerId(), "客户ID");
        ValidatorUtil.require(projectSetupDto.getProjectName(), "项目名称");
        ValidatorUtil.require(projectSetupDto.getSupervise(),"IPMT/ITMT委员");
        ValidatorUtil.require(projectSetupDto.getDevVersion(),"开发版本");
        ValidatorUtil.require(projectSetupDto.getLoginUserId(),"创建者");

        ValidatorUtil.length(projectSetupDto.getProjectName(), "项目名称", 2, 50);

        //处理项目信息
        RdmsProject projectInfo = CopyUtil.copy(projectSetupDto, RdmsProject.class);
        //查询是否有重名的项目
        long l = rdmsProjectService.projectNameIsUsed(projectSetupDto.getCustomerId(), projectSetupDto.getProjectName());
        if(l>0){
            projectInfo.setProjectName(projectInfo.getProjectName() + "-" +  String.valueOf(new Date().getTime()).substring(8));
        }
        projectInfo.setStatus(ProjectStatusEnum.SETUP_EDIT.getStatus()); //立项编辑状态
        projectInfo.setCreaterId(projectSetupDto.getLoginUserId());
        projectInfo.setRdType(RdTypeEnum.DIRECT.getType());
        String projectId = rdmsProjectService.save(projectInfo);

        responseDto.setContent(projectSetupDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    @PostMapping("/module-setup")
    @Transactional
    public ResponseDto<RdmsProjectSetupDto> moduleSetup(@RequestBody RdmsProjectSetupDto projectSetupDto) {
        ResponseDto<RdmsProjectSetupDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(projectSetupDto.getCustomerId(), "客户ID");
        ValidatorUtil.require(projectSetupDto.getProjectName(), "项目名称");
        ValidatorUtil.require(projectSetupDto.getSupervise(),"IPMT/ITMT委员");
        ValidatorUtil.require(projectSetupDto.getDevVersion(),"开发版本");

        ValidatorUtil.length(projectSetupDto.getProjectName(), "项目名称", 2, 50);

        //处理项目信息
        RdmsProject projectInfo = CopyUtil.copy(projectSetupDto, RdmsProject.class);
        //查询是否有重名的项目
        long l = rdmsProjectService.projectNameIsUsed(projectSetupDto.getCustomerId(), projectSetupDto.getProjectName());
        if(l>0){
            projectInfo.setProjectName(projectInfo.getProjectName() + "-" +  String.valueOf(new Date().getTime()).substring(8));
        }
        projectInfo.setStatus(ProjectStatusEnum.SETUP.getStatus()); //申请立项状态
        projectInfo.setRdType(RdTypeEnum.MODULE.getType());
        String projectId = rdmsProjectService.save(projectInfo);

        responseDto.setContent(projectSetupDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    /**
     * 申请立项
     */
    @PostMapping("/reject-application")
    @Transactional
    public ResponseDto<RdmsProjectSetupDto> rejectApplication(@RequestBody RdmsProjectSetupDto projectSetupDto) {
        ResponseDto<RdmsProjectSetupDto> responseDto = new ResponseDto<>();

        ValidatorUtil.require(projectSetupDto.getId(), "项目ID");

        //将项目状态标记为关闭
        RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(projectSetupDto.getId());
        rdmsProject.setStatus(ProjectStatusEnum.CLOSE.getStatus());
        rdmsProjectService.update(rdmsProject);

        //恢复上传的文件状态
        String fileListStr = rdmsProject.getFileListStr();
        List<String> fileIdList = JSON.parseArray(fileListStr, String.class);
        if(!CollectionUtils.isEmpty(fileIdList)){
            for(String id : fileIdList){
                RdmsFile rdmsFile = rdmsFileService.selectByPrimaryKey(id);
                rdmsFile.setUse(FileGroupingEnum.PRE_PROJECT.getCode().toLowerCase());
                rdmsFile.setItemId(rdmsFile.getPreProjectId());
                rdmsFileService.update(rdmsFile);
            }
        }

        //处理特性清单
        List<CharacterStatusEnum> statusEnumList = new ArrayList<>();
        statusEnumList.add(CharacterStatusEnum.SETUP);

        List<RdmsCharacterDto> characterListByProjectIdAndStatusList = rdmsCharacterService.getCharacterListByProjectIdAndStatusList(projectSetupDto.getId(), statusEnumList);
        if(!CollectionUtils.isEmpty(characterListByProjectIdAndStatusList)){
            for(RdmsCharacterDto characterDto: characterListByProjectIdAndStatusList){
                characterDto.setProjectId(null);
                characterDto.setStatus(CharacterStatusEnum.APPROVED.getStatus());
                characterDto.setMilestoneId(null);
                characterDto.setModuleIdListStr(null);
                String characterId = rdmsCharacterService.update(characterDto);

                //Character Process
                RdmsCharacterProcess characterProcess = new RdmsCharacterProcess();
                characterProcess.setCharacterId(characterId);
                characterProcess.setExecutorId(projectSetupDto.getSupervise()); //当前执行动作的人
                long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(characterId);
                characterProcess.setDeep((int)characterProcessCount);
                if(ObjectUtils.isEmpty(projectSetupDto.getRejectReason())){
                    characterProcess.setJobDescription("驳回立项申请");
                }else{
                    characterProcess.setJobDescription(projectSetupDto.getRejectReason());
                }
                characterProcess.setProcessStatus(CharacterProcessStatusEnum.COMPLETE.getStatus());
                characterProcess.setNextNode(projectSetupDto.getSupervise());
                String characterProcessId = rdmsCharacterProcessService.save(characterProcess);
            }
        }
        responseDto.setContent(projectSetupDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }



    @PostMapping("/withdrawProject/{projectId}")
    @Transactional
    public ResponseDto<String> withdrawProject(@PathVariable String projectId) {
        ResponseDto<String> responseDto = new ResponseDto<>();

        ValidatorUtil.require(projectId, "项目ID");

        //将项目状态标记为关闭
        RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(projectId);
        rdmsProject.setStatus(ProjectStatusEnum.CLOSE.getStatus());
        rdmsProjectService.update(rdmsProject);

        //恢复上传的文件状态
        String fileListStr = rdmsProject.getFileListStr();
        List<String> fileIdList = JSON.parseArray(fileListStr, String.class);
        if(!CollectionUtils.isEmpty(fileIdList)){
            for(String id : fileIdList){
                RdmsFile rdmsFile = rdmsFileService.selectByPrimaryKey(id);
                rdmsFile.setUse(FileGroupingEnum.PRE_PROJECT.getCode().toLowerCase());
                rdmsFile.setItemId(rdmsFile.getPreProjectId());
                rdmsFileService.update(rdmsFile);
            }
        }

        //处理特性清单
        List<CharacterStatusEnum> statusEnumList = new ArrayList<>();
        statusEnumList.add(CharacterStatusEnum.SETUP);

        List<RdmsCharacterDto> characterListByProjectIdAndStatusList = rdmsCharacterService.getCharacterListByProjectIdAndStatusList(projectId, statusEnumList);
        if(!CollectionUtils.isEmpty(characterListByProjectIdAndStatusList)){
            for(RdmsCharacterDto characterDto: characterListByProjectIdAndStatusList){
                characterDto.setProjectId(null);
                characterDto.setStatus(CharacterStatusEnum.APPROVED.getStatus());
                characterDto.setMilestoneId(null);
                characterDto.setModuleIdListStr(null);
                String characterId = rdmsCharacterService.update(characterDto);

                //Character Process
                RdmsCharacterProcess characterProcess = new RdmsCharacterProcess();
                characterProcess.setCharacterId(characterId);
                characterProcess.setExecutorId(rdmsProject.getProductManagerId());
                long characterProcessCount = rdmsCharacterProcessService.getCharacterProcessCount(characterId);
                characterProcess.setDeep((int)characterProcessCount);
                characterProcess.setJobDescription("撤回立项申请");

                characterProcess.setProcessStatus(CharacterProcessStatusEnum.COMPLETE.getStatus());
                characterProcess.setNextNode(rdmsProject.getProductManagerId());
                String characterProcessId = rdmsCharacterProcessService.save(characterProcess);
            }
        }
        responseDto.setContent(projectId);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    /**
     * 保存客户信息
     */
    @PostMapping("/append-characters")
    @Transactional
    public ResponseDto<RdmsProjectSetupDto> appendCharacters(@RequestBody RdmsProjectSetupDto projectSetupDto) {
        ResponseDto<RdmsProjectSetupDto> responseDto = new ResponseDto<>();

        //处理特性清单
        List<RdmsCharacterDto> characterDtoList = projectSetupDto.getCharacterList();
        List<RdmsCharacter> productCharacters = CopyUtil.copyList(characterDtoList, RdmsCharacter.class);
        for(RdmsCharacter rdmsCharacter : productCharacters){
            //update character表的数据
            rdmsCharacter.setProjectId(projectSetupDto.getId());
            rdmsCharacter.setStatus(CharacterStatusEnum.SETUPED.getStatus());
            rdmsCharacterService.update(rdmsCharacter);
        }

        responseDto.setContent(projectSetupDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    /**
     * 列表查询所有预立项项目项下经过审批的特性功能清单
     */
    @PostMapping("/listProjectsByPreProjectId/{preProjectId}")
    public ResponseDto<List<RdmsProjectDto>> listProjectsByPreProjectId(@PathVariable String preProjectId) {
        ResponseDto<List<RdmsProjectDto>> responseDto = new ResponseDto<>();
        List<RdmsProjectDto> projectDtos = rdmsProjectService.listProjectsByPreProjectId(preProjectId);
        responseDto.setContent(projectDtos);
        return responseDto;
    }

    @PostMapping("/getCbbList/{projectId}")
    public ResponseDto<List<RdmsCharacterDto>> getCbbList(@PathVariable String projectId) {
        ResponseDto<List<RdmsCharacterDto>> responseDto = new ResponseDto<>();
        List<RdmsCharacterDto> cbbList = rdmsProjectService.getCbbList(projectId);
        responseDto.setContent(cbbList);
        return responseDto;
    }

    /**
     * 列表查询所有预立项项目项下经过审批的特性功能清单
     */
    @PostMapping("/list-setuped-projects/{customerId}")
    public ResponseDto<List<RdmsProjectDto>> listSetupedProjects(@PathVariable String customerId) {
        ResponseDto<List<RdmsProjectDto>> responseDto = new ResponseDto<>();
        List<RdmsProjectDto> projectDtos = rdmsProjectService.listSetupedProjects(customerId);
        responseDto.setContent(projectDtos);
        return responseDto;
    }

    @PostMapping("/abandonProject/{projectId}")
    public ResponseDto<String> abandonProject(@PathVariable String projectId) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(projectId);
        rdmsProject.setStatus(ProjectStatusEnum.CLOSE.getStatus());
        rdmsProjectService.updateByPrimaryKeySelective(rdmsProject);

        //关闭作为主项目的子项目
        RdmsProjectSubproject rdmsProjectSubproject = rdmsSubprojectService.selectByPrimaryKey(projectId);
        rdmsProjectSubproject.setStatus(ProjectStatusEnum.CLOSE.getStatus());
        rdmsSubprojectService.updateByPrimaryKeySelective(rdmsProjectSubproject);
        //关闭所有下级子项目
        List<RdmsProjectSubproject> subprojectListByParentId = rdmsSubprojectService.getSubprojectListByParentId(projectId);
        rdmsSubprojectService.closeAllSubProject(subprojectListByParentId);
        //关闭所有功能
        List<RdmsCharacter> characterListByProjectId = rdmsCharacterService.getCharacterListByProjectId(projectId);
        if(!CollectionUtils.isEmpty(characterListByProjectId)){
            for(RdmsCharacter rdmsCharacter : characterListByProjectId){
                rdmsCharacter.setStatus(CharacterStatusEnum.DISCARD.getStatus());
                rdmsCharacterService.updateByPrimaryKeySelective(rdmsCharacter);
            }
        }
        //关闭所有任务
        List<RdmsJobItemDto> jobitemListByProjectId = rdmsJobItemService.getJobitemListByProjectId(projectId, null, null);
        if(!CollectionUtils.isEmpty(jobitemListByProjectId)){
            for(RdmsJobItemDto jobItemDto: jobitemListByProjectId){
                jobItemDto.setStatus(JobItemStatusEnum.CANCEL.getStatus());
                rdmsJobItemService.update(jobItemDto);
            }
        }
        //关闭所预算申请
        List<RdmsBudgetAdjust> budgetAdjustListByProjectId = rdmsBudgetAdjustService.getBudgetAdjustListByProjectId(projectId);
        if(!CollectionUtils.isEmpty(budgetAdjustListByProjectId)){
            for (RdmsBudgetAdjust budgetAdjust: budgetAdjustListByProjectId){
                if(!budgetAdjust.getStatus().equals(BudgetApplicantStatusEnum.COMPLETE.getStatus())){
                    budgetAdjust.setStatus(BudgetApplicantStatusEnum.REFUSED.getStatus());
                    rdmsBudgetAdjustService.update(budgetAdjust);
                }
            }
        }
        responseDto.setContent(projectId);
        return responseDto;
    }

    /**
     * 删除
     */
    @DeleteMapping("/del/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        rdmsProjectService.delete(id);
        return responseDto;
    }

}
