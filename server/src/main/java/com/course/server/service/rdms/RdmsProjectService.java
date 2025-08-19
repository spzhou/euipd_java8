/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.*;
import com.course.server.domain.RdmsProject;
import com.course.server.dto.PageDto;
import com.course.server.dto.rdms.*;
import com.course.server.enums.rdms.*;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsBomConfigMapper;
import com.course.server.mapper.RdmsProjectMapper;
import com.course.server.mapper.RdmsProjectSubprojectMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.course.server.constants.Constants.SUPER_CUSTOMER_ID;

@Service
public class RdmsProjectService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsProjectService.class);

    @Resource
    private RdmsProjectMapper rdmsProjectMapper;
    @Resource
    private RdmsProjectSubprojectMapper rdmsProjectSubprojectMapper;
    @Resource
    private RdmsMilestoneService rdmsMilestoneService;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private RdmsSubprojectService rdmsSubprojectService;
    @Resource
    private RdmsCharacterService rdmsCharacterService;
    @Resource
    private RdmsJobItemService rdmsJobItemService;
    @Resource
    private RdmsPreProjectService rdmsPreProjectService;
    @Resource
    private RdmsFileService rdmsFileService;
    @Resource
    private RdmsBomConfigMapper rdmsBomConfigMapper;
    @Resource
    private RdmsProjectSecLevelService rdmsProjectSecLevelService;
    @Autowired
    private RdmsCustomerService rdmsCustomerService;
    @Autowired
    private RdmsUserService rdmsUserService;
    @Autowired
    private RdmsMaterialManageService rdmsMaterialManageService;
    @Autowired
    private RdmsFeeManageService rdmsFeeManageService;
    @Autowired
    private RdmsPdgmService rdmsPdgmService;
    @Autowired
    private RdmsTgmService rdmsTgmService;
    @Autowired
    private RdmsDepartmentService rdmsDepartmentService;


    @Transactional
    public void listProject(PageDto<ProjectSummaryDto> pageDto){
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        List<String> rdTypeList = new ArrayList<>();
        rdTypeList.add(RdTypeEnum.MODULE.getType());
        RdmsProjectExample projectExample = new RdmsProjectExample();
        projectExample.createCriteria()
                .andCustomerIdEqualTo(pageDto.getCustomerId())
                .andRdTypeNotIn(rdTypeList)
                .andDeletedEqualTo(0);
        List<RdmsProject> rdmsProjects = rdmsProjectMapper.selectByExample(projectExample);
        PageInfo<RdmsProject> pageInfo = new PageInfo<>(rdmsProjects);
        pageDto.setTotal(pageInfo.getTotal());
        List<ProjectSummaryDto> projectSummaryDtoList = CopyUtil.copyList(rdmsProjects, ProjectSummaryDto.class);
        pageDto.setList(projectSummaryDtoList);
    }

    @Transactional
    public Integer getMaterialAndFeeApplicationNum(String productManagerId) {
        RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(productManagerId);
        if (!ObjectUtils.isEmpty(rdmsCustomerUser)) {
            List<RdmsProjectDto> archivedProjects = this.getArchivedProjectsByCustomerAndPdmId(rdmsCustomerUser.getCustomerId(), productManagerId);
            Integer sumApplicationNum = 0;
            if (!CollectionUtils.isEmpty(archivedProjects)) {
                for (RdmsProjectDto rdmsProjectDto : archivedProjects) {
                    Integer materialApplicationNum = rdmsMaterialManageService.getApplicationNumByProjectId(rdmsProjectDto.getId());
                    sumApplicationNum += materialApplicationNum;
                    Integer feeApplicationNum = rdmsFeeManageService.getApplicationNumByProjectId(rdmsProjectDto.getId());
                    sumApplicationNum += feeApplicationNum;
                }
            }
            return sumApplicationNum;

        } else {
            return 0;
        }
    }

    public List<RdmsProjectSubproject> getSubProjectListByMilestone(String milestoneId){
        RdmsProjectSubprojectExample subProjectExample = new RdmsProjectSubprojectExample();
        subProjectExample.createCriteria().andTargetMilestoneIdEqualTo(milestoneId).andDeletedEqualTo(0);
        List<RdmsProjectSubproject> rdmsProjectSubprojects = rdmsProjectSubprojectMapper.selectByExample(subProjectExample);
        return rdmsProjectSubprojects;

    }

    public List<RdmsProject> getSetupedProjectListByTm(String customerId, String loginUserId){
        RdmsProjectExample projectExample = new RdmsProjectExample();
        projectExample.setOrderByClause("setuped_time asc");
        projectExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andTestManagerIdEqualTo(loginUserId)
//                .andPreProjectIdIsNotNull()   //应该是有功能单元定义才能有测试
                .andStatusEqualTo(ProjectStatusEnum.SETUPED.getStatus())
                .andDeletedEqualTo(0);
        List<RdmsProject> rdmsProjects = rdmsProjectMapper.selectByExample(projectExample);
        return rdmsProjects;

    }

    public List<RdmsProjectSubprojectDto> getTestedSubprojectListByTgm(String customerId, String loginUserId){
        //判断师傅是测试负责人
        RdmsTgmDto tgmByCustomerId = rdmsTgmService.getTgmByCustomerId(customerId);
        if(!tgmByCustomerId.getTgmId().equals(loginUserId)){
            return new ArrayList<>();
        }
        List<RdmsProjectSubprojectDto> hasTestJobSubprojectList = new ArrayList<>();
        List<RdmsProjectSubproject> subprojectList = rdmsSubprojectService.getSubprojectListByCustomerId(customerId);
        if(! CollectionUtils.isEmpty(subprojectList)){
            for(RdmsProjectSubproject subproject : subprojectList){
                if(rdmsJobItemService.hasTestJobInSubproject(subproject.getId())){
                    hasTestJobSubprojectList.add(CopyUtil.copy(subproject, RdmsProjectSubprojectDto.class));
                }
            }

            if(!CollectionUtils.isEmpty(hasTestJobSubprojectList)) {
                //对日期时间进行格式化
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                for (RdmsProjectSubprojectDto subprojectDto : hasTestJobSubprojectList) {
                    if(!ObjectUtils.isEmpty(subprojectDto.getReleaseTime())){
                        subprojectDto.setReleaseTimeStr(sdf.format(subprojectDto.getReleaseTime()));
                    }
                    RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(subprojectDto.getProjectManagerId());
                    subprojectDto.setProjectManagerName(rdmsCustomerUser.getTrueName());

                    if (!ObjectUtils.isEmpty(subprojectDto.getTestManagerId())) {
                        RdmsCustomerUser customerUser_test = rdmsCustomerUserService.selectByPrimaryKey(subprojectDto.getTestManagerId());
                        subprojectDto.setTestManagerName(customerUser_test.getTrueName());
                    }

                    RdmsProject projectById = this.findProjectById(subprojectDto.getProjectId());
                    if (ObjectUtils.isEmpty(projectById)) {
                        subprojectDto.setCharacters(null);
                    } else {
                        subprojectDto.setProjectName(projectById.getProjectName());
                        List<RdmsCharacterDto> characterDtos = rdmsCharacterService.listBySubProjectId(subprojectDto.getId());
                        subprojectDto.setCharacters(characterDtos);
                    }

                    List<JobItemStatusEnum> jobItemStatusList = new ArrayList<>();
                    jobItemStatusList.add(JobItemStatusEnum.HANDLING);
                    List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
                    jobitemTypeList.add(JobItemTypeEnum.TEST);
                    int handlingNum = rdmsJobItemService.getJobitemListBySubprojectIdAndStatusListTypeList(subprojectDto.getId(), jobItemStatusList, jobitemTypeList).size();
                    subprojectDto.setHandlingJobNum(handlingNum);
                    jobItemStatusList.clear();
                    jobItemStatusList.add(JobItemStatusEnum.SUBMIT);
                    int submitNum = rdmsJobItemService.getJobitemListBySubprojectIdAndStatusListTypeList(subprojectDto.getId(), jobItemStatusList, jobitemTypeList).size();
                    subprojectDto.setSubmitJobNum(submitNum);
                }
            }
            return hasTestJobSubprojectList;
        }else {
            return new ArrayList<>();
        }
    }
    public void getTestedSubprojectListByTestManagerId(PageDto<RdmsProjectSubprojectDto> pageDto){
        List<ProjectStatusEnum> statusEnumList = new ArrayList<>();
        statusEnumList.add(ProjectStatusEnum.SETUPED);
        statusEnumList.add(ProjectStatusEnum.ONGOING);
        statusEnumList.add(ProjectStatusEnum.INTEGRATION);
        statusEnumList.add(ProjectStatusEnum.DEV_COMPLETE);
        statusEnumList.add(ProjectStatusEnum.EN_REVIEW);
        statusEnumList.add(ProjectStatusEnum.REVIEW_SUBP);
        statusEnumList.add(ProjectStatusEnum.REVIEW_PRO);
        statusEnumList.add(ProjectStatusEnum.COMPLETE);
        statusEnumList.add(ProjectStatusEnum.ARCHIVED);

        RdmsTgmDto tgmByCustomerId = rdmsTgmService.getTgmByCustomerId(pageDto.getCustomerId());
        if(tgmByCustomerId.getTgmId().equals(pageDto.getLoginUserId())){
            //如果是测试负责人, 列出所有项目
            rdmsSubprojectService.getSubprojectListCustomerIdAndStatusList(pageDto, statusEnumList);
        }else{
            //如果是测试主管, 列出自己的项目
            rdmsSubprojectService.getSubprojectListCustomerIdAndStatusListByTestManager(pageDto, statusEnumList);
        }

    }
    /**
     * 通过customerID查询所有项目列表
     * @param pageDto
     */
    @Transactional
    public void list(PageDto<RdmsProjectDto> pageDto, List<ProjectStatusEnum> statusEnumList, String actorType) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsProjectExample rdmsProjectInfoExample = new RdmsProjectExample();
        rdmsProjectInfoExample.setOrderByClause("release_time desc, create_time desc");
        RdmsProjectExample.Criteria criteria = rdmsProjectInfoExample.createCriteria();
        criteria.andCustomerIdEqualTo(pageDto.getKeyWord()).andDeletedEqualTo(0);

        if(pageDto.getActor() != null && pageDto.getActor().length()==8 && actorType.equals(ActorTypeEnum.SUPERVISE.getStatus())){
            criteria.andSuperviseEqualTo(pageDto.getActor()); //监管委员
        }
        if(pageDto.getActor() != null && pageDto.getActor().length()==8 && actorType.equals(ActorTypeEnum.PRODUCT_MANAGER.getStatus())){
            criteria.andProductManagerIdEqualTo(pageDto.getActor()); //产品经理
        }

        if(pageDto.getActor() != null && pageDto.getActor().length()==8 && actorType.equals(ActorTypeEnum.CREATER.getStatus())){
            criteria.andCreaterIdEqualTo(pageDto.getActor()); //创建者
        }

        if(! CollectionUtils.isEmpty(statusEnumList)){
            List<String> statusList = new ArrayList<>();
            for(ProjectStatusEnum statusEnum: statusEnumList){
                statusList.add(statusEnum.getStatus());
            }
            criteria.andStatusIn(statusList);
        }
        List<RdmsProject> rdmsProjectInfos = rdmsProjectMapper.selectByExample(rdmsProjectInfoExample);

        PageInfo<RdmsProject> pageInfo = new PageInfo<>(rdmsProjectInfos);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsProjectDto> rdmsProjectInfoDtos = CopyUtil.copyList(rdmsProjectInfos, RdmsProjectDto.class);
        for(RdmsProjectDto projectInfoDto : rdmsProjectInfoDtos){
            List<RdmsCharacterDto> characterDtos = rdmsCharacterService.listAllCharactersByProjectId(projectInfoDto.getId(), null);
            for(RdmsCharacterDto characterDto : characterDtos){
                if(! ObjectUtils.isEmpty(characterDto.getSubprojectId())){
                    RdmsProjectSubproject subProjectInfo = rdmsSubprojectService.getSubProjectInfo(characterDto.getSubprojectId());
                    if(! ObjectUtils.isEmpty(subProjectInfo)){
                        characterDto.setSubprojectName(subProjectInfo.getLabel());
                    }
                }
                if(characterDto.getProjectId() != null){
                    //添加项目名称
                    RdmsProject projectById = this.findProjectById(characterDto.getProjectId());
                    characterDto.setProjectName(projectById.getProjectName());
                }
                /*CharacterStatusEnum characterEnumByStatus = CharacterStatusEnum.getCharacterEnumByStatus(characterDto.getStatus());
                characterDto.setStatus(characterEnumByStatus.getStatusName());*/
            }
            projectInfoDto.setCharacters(characterDtos);
        }

        //对日期时间进行格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(RdmsProjectDto projectInfoDto : rdmsProjectInfoDtos){
            projectInfoDto.setCreateTimeStr(sdf.format(projectInfoDto.getCreateTime()));
            if(projectInfoDto.getSetupedTime() != null){
                projectInfoDto.setSetupedTimeStr(sdf.format(projectInfoDto.getSetupedTime()));
            }
            if(projectInfoDto.getReleaseTime() != null){
                projectInfoDto.setReleaseTimeStr(sdf.format(projectInfoDto.getReleaseTime()));
            }
            List<RdmsMilestoneDto> rdmsMilestoneDtos = rdmsMilestoneService.listByProjectId(projectInfoDto.getId());
            projectInfoDto.setMilestoneList(rdmsMilestoneDtos);

            String superviseId = projectInfoDto.getSupervise();
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(superviseId);
            if(rdmsCustomerUser != null){
                projectInfoDto.setSuperviseName(rdmsCustomerUser.getTrueName());
            }

            String productManagerId = projectInfoDto.getProductManagerId();
            RdmsCustomerUser productManager = rdmsCustomerUserService.selectByPrimaryKey(productManagerId);
            if(productManager != null){
                projectInfoDto.setProductManagerName(productManager.getTrueName());
            }

            String projectManagerId = projectInfoDto.getProjectManagerId();
            RdmsCustomerUser projectManager = rdmsCustomerUserService.selectByPrimaryKey(projectManagerId);
            if(! ObjectUtils.isEmpty(projectManager)){
                projectInfoDto.setProjectManagerName(projectManager.getTrueName());
            }

            //添加关键成员名单
            StringBuilder keyMembers = new StringBuilder();
            List<String> keyMemberList = JSON.parseArray(projectInfoDto.getKeyMemberListStr(), String.class);
            if(!CollectionUtils.isEmpty(keyMemberList)){
                for(String customerUserId : keyMemberList){
                    RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(customerUserId);
                    if(!ObjectUtils.isEmpty(customerUser)){
                        keyMembers.append(customerUser.getTrueName()).append(" ");
                    }
                }
            }
            projectInfoDto.setKeyMemberListStr(keyMembers.toString());

            RdmsProjectSubprojectDto treeListStr = rdmsSubprojectService.getProjectTreeListStr(pageDto.getKeyWord(), projectInfoDto.getId());
            projectInfoDto.setProjectTree(treeListStr);

        }

        //是否已经有组件开发项目
        List<RdmsProject> moduleProjectList = this.getModuleProjectList(pageDto.getKeyWord());
        if(!CollectionUtils.isEmpty(moduleProjectList)){
            pageDto.setStatus("hasModuleProject");

            List<RdmsProject> collect = moduleProjectList.stream().filter(item -> item.getStatus().equals(ProjectStatusEnum.SETUPED.getStatus())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(collect)){
                pageDto.setFlag("hasSetupedModuleProject");
            }
        }else{
            pageDto.setStatus(null);
        }
        pageDto.setList(rdmsProjectInfoDtos);
    }

    @Transactional
    public void getSetupedProjectList(@NotNull PageDto<RdmsProjectDto> pageDto, List<ProjectStatusEnum> statusEnumList) {
        //判断是否是管理员
        String role = "";
        RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(pageDto.getActor());
        if(!ObjectUtils.isEmpty(rdmsCustomerUser)){
            RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(pageDto.getCustomerId());
            if(rdmsCustomer != null){
                role = RoleEnum.STAFF.getRole();
                List<String> userRole = rdmsUserService.getUserRole(rdmsCustomer.getId(), rdmsCustomerUser.getId());
                if(userRole.contains(RoleEnum.PDM.getRole()))
                    role = RoleEnum.PDM.getRole();
                if(userRole.contains(RoleEnum.IPMT.getRole()))
                    role = RoleEnum.IPMT.getRole();
                if(userRole.contains(RoleEnum.BOSS.getRole())
                        || userRole.contains(RoleEnum.ADMIN.getRole())
                        || userRole.contains(RoleEnum.SYSGM.getRole())
                        || userRole.contains(RoleEnum.PDGM.getRole())
                ){
                    role = RoleEnum.BOSS.getRole();
                }

                if(role.equals(RoleEnum.STAFF.getRole())){
                    PageInfo<RdmsProject> pageInfo = new PageInfo<>();
                    pageDto.setTotal(pageInfo.getTotal());
                    pageDto.setList(null);
                    return;
                }
            }
        }

        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsProjectExample rdmsProjectInfoExample = new RdmsProjectExample();
        rdmsProjectInfoExample.setOrderByClause("release_time desc, create_time desc");
        RdmsProjectExample.Criteria criteria = rdmsProjectInfoExample.createCriteria();
        criteria.andCustomerIdEqualTo(pageDto.getCustomerId())
                .andStatusEqualTo(ProjectStatusEnum.SETUPED.getStatus())
                .andDeletedEqualTo(0);

        if(! CollectionUtils.isEmpty(statusEnumList)){
            List<String> statusList = new ArrayList<>();
            for(ProjectStatusEnum statusEnum: statusEnumList){
                statusList.add(statusEnum.getStatus());
            }
            criteria.andStatusIn(statusList);
        }

        if(! ObjectUtils.isEmpty(pageDto.getKeyWord())){
            criteria.andProjectNameLike("%" +pageDto.getKeyWord()+ "%");
        }

        if(role.equals(RoleEnum.PDM.getRole())){
            criteria.andProductManagerIdEqualTo(pageDto.getActor());
        }else{
            if(role.equals(RoleEnum.IPMT.getRole())){
                criteria.andSuperviseEqualTo(pageDto.getActor());
            }
            //如果上述情况都不是, 就只能是 boss 或 Admin了
            //不必加任何其他过滤条件
        }
        if(!(ObjectUtils.isEmpty(pageDto.getFlag()) || pageDto.getFlag().equals(pageDto.getCustomerId()))){
            criteria.andCategoryIdListStrLike("%"+pageDto.getFlag()+"%");
        }

        List<RdmsProject> rdmsProjectInfos = rdmsProjectMapper.selectByExample(rdmsProjectInfoExample);
        PageInfo<RdmsProject> pageInfo = new PageInfo<>(rdmsProjectInfos);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsProjectDto> rdmsProjectInfoDtos = CopyUtil.copyList(rdmsProjectInfos, RdmsProjectDto.class);
        for(RdmsProjectDto projectInfoDto : rdmsProjectInfoDtos){
            List<RdmsCharacterDto> characterDtos = rdmsCharacterService.listAllCharactersByProjectId(projectInfoDto.getId(), null);
            for(RdmsCharacterDto characterDto : characterDtos){
                if(! ObjectUtils.isEmpty(characterDto.getSubprojectId())){
                    RdmsProjectSubproject subProjectInfo = rdmsSubprojectService.getSubProjectInfo(characterDto.getSubprojectId());
                    if(! ObjectUtils.isEmpty(subProjectInfo)){
                        characterDto.setSubprojectName(subProjectInfo.getLabel());
                    }
                }
                if(characterDto.getProjectId() != null){
                    //添加项目名称
                    RdmsProject projectById = this.findProjectById(characterDto.getProjectId());
                    characterDto.setProjectName(projectById.getProjectName());
                }
                /*CharacterStatusEnum characterEnumByStatus = CharacterStatusEnum.getCharacterEnumByStatus(characterDto.getStatus());
                characterDto.setStatus(characterEnumByStatus.getStatusName());*/
            }
            projectInfoDto.setCharacters(characterDtos);
        }

        //对日期时间进行格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(RdmsProjectDto projectInfoDto : rdmsProjectInfoDtos){
            projectInfoDto.setCreateTimeStr(sdf.format(projectInfoDto.getCreateTime()));
            if(projectInfoDto.getSetupedTime() != null){
                projectInfoDto.setSetupedTimeStr(sdf.format(projectInfoDto.getSetupedTime()));
            }
            if(projectInfoDto.getReleaseTime() != null){
                projectInfoDto.setReleaseTimeStr(sdf.format(projectInfoDto.getReleaseTime()));
            }
            List<RdmsMilestoneDto> rdmsMilestoneDtos = rdmsMilestoneService.listByProjectId(projectInfoDto.getId());
            projectInfoDto.setMilestoneList(rdmsMilestoneDtos);

            String superviseId = projectInfoDto.getSupervise();
            RdmsCustomerUser supervise = rdmsCustomerUserService.selectByPrimaryKey(superviseId);
            if(supervise != null){
                projectInfoDto.setSuperviseName(supervise.getTrueName());
            }

            String productManagerId = projectInfoDto.getProductManagerId();
            RdmsCustomerUser productManager = rdmsCustomerUserService.selectByPrimaryKey(productManagerId);
            if(productManager != null){
                projectInfoDto.setProductManagerName(productManager.getTrueName());
            }

            String projectManagerId = projectInfoDto.getProjectManagerId();
            RdmsCustomerUser projectManager = rdmsCustomerUserService.selectByPrimaryKey(projectManagerId);
            if(! ObjectUtils.isEmpty(projectManager)){
                projectInfoDto.setProjectManagerName(projectManager.getTrueName());
            }

            String testManagerId = projectInfoDto.getTestManagerId();
            if(testManagerId != null){
                RdmsCustomerUser testManager = rdmsCustomerUserService.selectByPrimaryKey(testManagerId);
                if(! ObjectUtils.isEmpty(testManager)){
                    projectInfoDto.setTestManagerName(testManager.getTrueName());
                }
            }

            //添加关键成员名单
            StringBuilder keyMembers = new StringBuilder();
            List<String> keyMemberList = JSON.parseArray(projectInfoDto.getKeyMemberListStr(), String.class);
            if(!CollectionUtils.isEmpty(keyMemberList)){
                for(String customerUserId : keyMemberList){
                    RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(customerUserId);
                    if(!ObjectUtils.isEmpty(customerUser)){
                        keyMembers.append(customerUser.getTrueName()).append(" ");
                    }
                }
            }
            projectInfoDto.setKeyMemberListStr(keyMembers.toString());

            RdmsProjectSubprojectDto treeListStr = rdmsSubprojectService.getProjectTreeListStr(pageDto.getCustomerId(), projectInfoDto.getId());
            projectInfoDto.setProjectTree(treeListStr);

        }

        //是否已经有组件开发项目
        List<RdmsProject> moduleProjectList = this.getModuleProjectList(pageDto.getCustomerId());
        if(!CollectionUtils.isEmpty(moduleProjectList)){
            pageDto.setStatus("hasModuleProject");

            List<RdmsProject> collect = moduleProjectList.stream().filter(item -> item.getStatus().equals(ProjectStatusEnum.SETUPED.getStatus())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(collect)){
                pageDto.setFlag("hasSetupedModuleProject");
            }
        }else{
            pageDto.setStatus(null);
        }
        pageDto.setList(rdmsProjectInfoDtos);
    }

    @Transactional
    public List<RdmsProjectDto> getSetupedProjectList(String customerId, String loginUserId ) {
        RdmsProjectExample rdmsProjectInfoExample = new RdmsProjectExample();
        rdmsProjectInfoExample.setOrderByClause("release_time desc, create_time desc");
        RdmsProjectExample.Criteria criteria = rdmsProjectInfoExample.createCriteria();
        criteria.andCustomerIdEqualTo(customerId)
                .andStatusEqualTo(ProjectStatusEnum.SETUPED.getStatus())
                .andDeletedEqualTo(0);

        //判断是否是管理员
        RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(loginUserId);
        if(!ObjectUtils.isEmpty(rdmsCustomerUser)){
            RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(customerId);
            if(rdmsCustomer != null){
                if(!rdmsCustomer.getContactPhone().equals(rdmsCustomerUser.getLoginName())){
                    //如果不是管理员,就必须是ipmt, 如果都不是,则返回为空
                    criteria.andSuperviseEqualTo(loginUserId);
                }
            }
        }
        List<RdmsProject> rdmsProjectInfos = rdmsProjectMapper.selectByExample(rdmsProjectInfoExample);
        List<RdmsProjectDto> rdmsProjectInfoDtos = appendProjectInfo(customerId, rdmsProjectInfos);
        return rdmsProjectInfoDtos;
    }

    @Transactional
    public List<RdmsProjectDto> getSetupProjectList(String customerId) {
        RdmsProjectExample rdmsProjectInfoExample = new RdmsProjectExample();
        rdmsProjectInfoExample.setOrderByClause("release_time desc, create_time desc");
        RdmsProjectExample.Criteria criteria = rdmsProjectInfoExample.createCriteria();
        criteria.andCustomerIdEqualTo(customerId)
                .andStatusEqualTo(ProjectStatusEnum.SETUP.getStatus())
                .andDeletedEqualTo(0);
        List<RdmsProject> rdmsProjectInfos = rdmsProjectMapper.selectByExample(rdmsProjectInfoExample);

        List<RdmsProjectDto> rdmsProjectInfoDtos = appendProjectInfo(customerId, rdmsProjectInfos);
        return rdmsProjectInfoDtos;
    }

    @Transactional
    public List<RdmsProjectDto> getSetupProjectListByIpmt(String customerId, String ipmtId) {
        RdmsProjectExample rdmsProjectInfoExample = new RdmsProjectExample();
        rdmsProjectInfoExample.setOrderByClause("release_time desc, create_time desc");
        RdmsProjectExample.Criteria criteria = rdmsProjectInfoExample.createCriteria();
        criteria.andCustomerIdEqualTo(customerId)
                .andStatusEqualTo(ProjectStatusEnum.SETUP.getStatus())
                .andSuperviseEqualTo(ipmtId)
                .andDeletedEqualTo(0);
        List<RdmsProject> rdmsProjectInfos = rdmsProjectMapper.selectByExample(rdmsProjectInfoExample);

        List<RdmsProjectDto> rdmsProjectInfoDtos = appendProjectInfo(customerId, rdmsProjectInfos);
        return rdmsProjectInfoDtos;
    }

    @Transactional
    public List<RdmsProjectDto> getSetupEditProjectListByCreater(String customerId, String createrId) {
        RdmsProjectExample rdmsProjectInfoExample = new RdmsProjectExample();
        rdmsProjectInfoExample.setOrderByClause("release_time desc, create_time desc");
        RdmsProjectExample.Criteria criteria = rdmsProjectInfoExample.createCriteria();
        criteria.andCustomerIdEqualTo(customerId)
                .andStatusEqualTo(ProjectStatusEnum.SETUP_EDIT.getStatus())
                .andCreaterIdEqualTo(createrId)
                .andDeletedEqualTo(0);
        List<RdmsProject> rdmsProjectInfos = rdmsProjectMapper.selectByExample(rdmsProjectInfoExample);

        List<RdmsProjectDto> rdmsProjectInfoDtos = appendProjectInfo(customerId, rdmsProjectInfos);
        return rdmsProjectInfoDtos;
    }

    private @NotNull List<RdmsProjectDto> appendProjectInfo(String customerId, List<RdmsProject> rdmsProjectInfos) {
        List<RdmsProjectDto> rdmsProjectInfoDtos = CopyUtil.copyList(rdmsProjectInfos, RdmsProjectDto.class);
        for(RdmsProjectDto projectInfoDto : rdmsProjectInfoDtos){
            List<RdmsCharacterDto> characterDtos = rdmsCharacterService.listAllCharactersByProjectId(projectInfoDto.getId(), null);
            for(RdmsCharacterDto characterDto : characterDtos){
                if(! ObjectUtils.isEmpty(characterDto.getSubprojectId())){
                    RdmsProjectSubproject subProjectInfo = rdmsSubprojectService.getSubProjectInfo(characterDto.getSubprojectId());
                    if(! ObjectUtils.isEmpty(subProjectInfo)){
                        characterDto.setSubprojectName(subProjectInfo.getLabel());
                    }
                }
                if(characterDto.getProjectId() != null){
                    //添加项目名称
                    RdmsProject projectById = this.findProjectById(characterDto.getProjectId());
                    characterDto.setProjectName(projectById.getProjectName());
                }
            }
            projectInfoDto.setCharacters(characterDtos);
        }

        //对日期时间进行格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(RdmsProjectDto projectInfoDto : rdmsProjectInfoDtos){
            projectInfoDto.setCreateTimeStr(sdf.format(projectInfoDto.getCreateTime()));
            if(projectInfoDto.getSetupedTime() != null){
                projectInfoDto.setSetupedTimeStr(sdf.format(projectInfoDto.getSetupedTime()));
            }
            if(projectInfoDto.getReleaseTime() != null){
                projectInfoDto.setReleaseTimeStr(sdf.format(projectInfoDto.getReleaseTime()));
            }
            List<RdmsMilestoneDto> rdmsMilestoneDtos = rdmsMilestoneService.listByProjectId(projectInfoDto.getId());
            projectInfoDto.setMilestoneList(rdmsMilestoneDtos);

            String superviseId = projectInfoDto.getSupervise();
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(superviseId);
            if(rdmsCustomerUser != null){
                projectInfoDto.setSuperviseName(rdmsCustomerUser.getTrueName());
            }

            String productManagerId = projectInfoDto.getProductManagerId();
            RdmsCustomerUser productManager = rdmsCustomerUserService.selectByPrimaryKey(productManagerId);
            if(productManager != null){
                projectInfoDto.setProductManagerName(productManager.getTrueName());
            }

            String projectManagerId = projectInfoDto.getProjectManagerId();
            RdmsCustomerUser projectManager = rdmsCustomerUserService.selectByPrimaryKey(projectManagerId);
            if(! ObjectUtils.isEmpty(projectManager)){
                projectInfoDto.setProjectManagerName(projectManager.getTrueName());
            }

            String testManagerId = projectInfoDto.getTestManagerId();
            if(! ObjectUtils.isEmpty(testManagerId)){
                RdmsCustomerUser testManager = rdmsCustomerUserService.selectByPrimaryKey(testManagerId);
                if(! ObjectUtils.isEmpty(testManager)){
                    projectInfoDto.setTestManagerName(testManager.getTrueName());
                }
            }

            String systemManagerId = projectInfoDto.getSystemManagerId();
            if(! ObjectUtils.isEmpty(systemManagerId)){
                RdmsCustomerUser systemManager = rdmsCustomerUserService.selectByPrimaryKey(systemManagerId);
                if(! ObjectUtils.isEmpty(systemManager)){
                    projectInfoDto.setSystemManagerName(systemManager.getTrueName());
                }
            }

            //添加关键成员名单
            StringBuilder keyMembers = new StringBuilder();
            List<String> keyMemberList = JSON.parseArray(projectInfoDto.getKeyMemberListStr(), String.class);
            if(!CollectionUtils.isEmpty(keyMemberList)){
                for(String customerUserId : keyMemberList){
                    RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(customerUserId);
                    if(!ObjectUtils.isEmpty(customerUser)){
                        keyMembers.append(customerUser.getTrueName()).append(" ");
                    }
                }
            }
            projectInfoDto.setKeyMemberListStr(keyMembers.toString());

            RdmsProjectSubprojectDto treeListStr = rdmsSubprojectService.getProjectTreeListStr(customerId, projectInfoDto.getId());
            projectInfoDto.setProjectTree(treeListStr);

        }
        return rdmsProjectInfoDtos;
    }

    public List<RdmsProject> getModuleProjectList(String customerId) {
        RdmsProjectExample rdmsProjectExample = new RdmsProjectExample();
        RdmsProjectExample.Criteria criteria = rdmsProjectExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andRdTypeEqualTo(RdTypeEnum.MODULE.getType())
                .andDeletedEqualTo(0);
        List<RdmsProject> rdmsProjectInfos = rdmsProjectMapper.selectByExample(rdmsProjectExample);
        return rdmsProjectInfos;

    }

    public RdmsProjectDto getProjectRecordInfo(String projectId) {
        RdmsProject rdmsProjectInfo = this.selectByPrimaryKey(projectId);
        RdmsProjectDto projectInfoDto = CopyUtil.copy(rdmsProjectInfo, RdmsProjectDto.class);
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

        if(projectInfoDto.getTestManagerId() != null){
            RdmsCustomerUser testManager = rdmsCustomerUserService.selectByPrimaryKey(projectInfoDto.getTestManagerId());
            projectInfoDto.setTestManagerName(testManager.getTrueName());
        }

        if(projectInfoDto.getSystemManagerId() != null){
            RdmsCustomerUser systemManager = rdmsCustomerUserService.selectByPrimaryKey(projectInfoDto.getSystemManagerId());
            projectInfoDto.setSystemManagerName(systemManager.getTrueName());
        }

        String keyMemberListStr = rdmsProjectInfo.getKeyMemberListStr();
        List<String> keyMemberList = JSON.parseArray(keyMemberListStr, String.class);
        projectInfoDto.setKeyMemberList(keyMemberList);

        if(projectInfoDto.getReleaseTime() != null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            projectInfoDto.setReleaseTimeStr(sdf.format(projectInfoDto.getReleaseTime()));
        }

        List<RdmsMilestoneDto> rdmsMilestoneDtos = rdmsMilestoneService.listByProjectId(projectId);
        if(!CollectionUtils.isEmpty(rdmsMilestoneDtos)){
            projectInfoDto.setMilestoneList(rdmsMilestoneDtos);
        }

        Integer characterNum = rdmsCharacterService.getCharacterNumByProjectId(projectId);
        projectInfoDto.setCharacterNum(characterNum);

        List<String> strings = JSON.parseArray(rdmsProjectInfo.getKeyMemberListStr(), String.class);
        StringBuilder memberStr = new StringBuilder();
        if(! CollectionUtils.isEmpty(strings)){
            for(String id : strings){
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(id);
                memberStr.append(rdmsCustomerUser.getTrueName()).append(" ");
            }
            projectInfoDto.setKeyMemberListStr(memberStr.toString());
        }

        List<RdmsFileDto> fileDtos = new ArrayList<>();
        if(! ObjectUtils.isEmpty(projectInfoDto.getFileListStr()) && projectInfoDto.getFileListStr().length()>6){
            List<String> fileIdList = JSON.parseArray(projectInfoDto.getFileListStr(), String.class);
            for(String id: fileIdList){
                RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(id);
                fileDtos.add(fileDto);
            }
        }
        projectInfoDto.setFileList(fileDtos);

        //是否存在评审工单  用于要求补充材料的评审场景
        List<RdmsJobItem> jobitemList = rdmsJobItemService.getJobitemListByProjectIdAndStatusAndJobType(projectId, JobItemStatusEnum.SUBMIT.getStatus(), JobItemTypeEnum.REVIEW_PRO.getType());
        if(!CollectionUtils.isEmpty(jobitemList)){
            projectInfoDto.setHasReviewJobitem(true);
        }else{
            projectInfoDto.setHasReviewJobitem(false);
        }
        return projectInfoDto;
    }

    public RdmsProject selectByPrimaryKey(String projectId){
        //如果项目没有设定保密等级,则将保密等级设置为 普通项目_0
        if(!ObjectUtils.isEmpty(projectId)){
            List<RdmsProjectSecLevel> rdmsProjectSecLevels = rdmsProjectSecLevelService.getByProjectId(projectId);
            if(CollectionUtils.isEmpty(rdmsProjectSecLevels)){
                RdmsProjectSecLevel projectSecLevel = new RdmsProjectSecLevel();
                projectSecLevel.setProjectId(projectId);
                projectSecLevel.setSubprojectId(projectId);
                projectSecLevel.setLevel(0);
                rdmsProjectSecLevelService.saveBySubprojectId(projectSecLevel);
            }
        }
        return rdmsProjectMapper.selectByPrimaryKey(projectId);
    }
    /**
     * 分页查询里程碑列表
     * @Param pageDto.KeyWord is ProjectId
     * @return
     */
    @Transactional
    public List<RdmsProjectDto> getCompleteProjectListByCustomerId(String customerId){
        RdmsProjectExample rdmsProjectExample = new RdmsProjectExample();
        List<String> statusList = new ArrayList<>();
        statusList.add(ProjectStatusEnum.COMPLETE.getStatus());
        statusList.add(ProjectStatusEnum.ARCHIVED.getStatus());
        RdmsProjectExample.Criteria criteria = rdmsProjectExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andDeletedEqualTo(0);
        criteria.andStatusIn(statusList);
        List<RdmsProject> rdmsProjectInfos = rdmsProjectMapper.selectByExample(rdmsProjectExample);

        List<RdmsProjectDto> rdmsProjectInfoDtos = CopyUtil.copyList(rdmsProjectInfos, RdmsProjectDto.class);
        //对日期时间进行格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(RdmsProjectDto rdmsProjectInfoDto : rdmsProjectInfoDtos){
            rdmsProjectInfoDto.setReleaseTimeStr(sdf.format(rdmsProjectInfoDto.getReleaseTime()));
            rdmsProjectInfoDto.setCreateTimeStr(sdf.format(rdmsProjectInfoDto.getCreateTime()));
        }
        return rdmsProjectInfoDtos;
    }

    @Transactional
    public List<RdmsProjectDto> getOnGoingProjectListByCustomerId(String customerId){
        RdmsProjectExample rdmsProjectExample = new RdmsProjectExample();
        List<String> statusList = new ArrayList<>();
        statusList.add(ProjectStatusEnum.SETUPED.getStatus());
        statusList.add(ProjectStatusEnum.ONGOING.getStatus());
        statusList.add(ProjectStatusEnum.INTEGRATION.getStatus());
        RdmsProjectExample.Criteria criteria = rdmsProjectExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andDeletedEqualTo(0);
        criteria.andStatusIn(statusList);
        List<RdmsProject> rdmsProjectInfos = rdmsProjectMapper.selectByExample(rdmsProjectExample);

        List<RdmsProjectDto> rdmsProjectInfoDtos = CopyUtil.copyList(rdmsProjectInfos, RdmsProjectDto.class);
        //对日期时间进行格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(RdmsProjectDto rdmsProjectInfoDto : rdmsProjectInfoDtos){
            if(! ObjectUtils.isEmpty(rdmsProjectInfoDto.getReleaseTime())){
                rdmsProjectInfoDto.setReleaseTimeStr(sdf.format(rdmsProjectInfoDto.getReleaseTime()));  //MODULE开发项目没有具体指定发布时间
            }
            rdmsProjectInfoDto.setCreateTimeStr(sdf.format(rdmsProjectInfoDto.getCreateTime()));
        }
        return rdmsProjectInfoDtos;
    }

    @Transactional
    public List<RdmsProjectDto> getProjectListByCustomerIdAndStatusList(String customerId, List<ProjectStatusEnum> statusEnumList){
        RdmsProjectExample rdmsProjectExample = new RdmsProjectExample();
        RdmsProjectExample.Criteria criteria = rdmsProjectExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andDeletedEqualTo(0);

        if(! CollectionUtils.isEmpty(statusEnumList)){
            List<String> statusList = new ArrayList<>();
            for(ProjectStatusEnum statusEnum: statusEnumList){
                statusList.add(statusEnum.getStatus());
            }
            criteria.andStatusIn(statusList);
        }
        List<RdmsProject> rdmsProjectInfos = rdmsProjectMapper.selectByExample(rdmsProjectExample);

        List<RdmsProjectDto> rdmsProjectInfoDtos = CopyUtil.copyList(rdmsProjectInfos, RdmsProjectDto.class);
        return rdmsProjectInfoDtos;
    }

    @Transactional
    public List<RdmsProjectDto> getProjectListByCustomerId_allStatus(String customerId){
        RdmsProjectExample rdmsProjectExample = new RdmsProjectExample();
        List<String> statusList = new ArrayList<>();
        statusList.add(ProjectStatusEnum.SETUPED.getStatus());
        statusList.add(ProjectStatusEnum.ONGOING.getStatus());
        statusList.add(ProjectStatusEnum.COMPLETE.getStatus());
        rdmsProjectExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andStatusIn(statusList)
                .andDeletedEqualTo(0);
        List<RdmsProject> rdmsProjectInfos = rdmsProjectMapper.selectByExample(rdmsProjectExample);
        List<RdmsProjectDto> rdmsProjectInfoDtos = CopyUtil.copyList(rdmsProjectInfos, RdmsProjectDto.class);
        if(!CollectionUtils.isEmpty(rdmsProjectInfoDtos)){
            for(RdmsProjectDto projectDto: rdmsProjectInfoDtos){
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(projectDto.getProjectManagerId());
                projectDto.setProjectManagerName(rdmsCustomerUser.getTrueName());
                RdmsCustomerUser rdmsCustomerUser1 = rdmsCustomerUserService.selectByPrimaryKey(projectDto.getProductManagerId());
                projectDto.setProductManagerName(rdmsCustomerUser1.getTrueName());
            }
        }
        return rdmsProjectInfoDtos;
    }

    @Transactional
    public List<RdmsProjectDto> getProjectListByCustomerId_setuped(String customerId){
        RdmsProjectExample rdmsProjectExample = new RdmsProjectExample();
        List<String> statusList = new ArrayList<>();
        statusList.add(ProjectStatusEnum.SETUPED.getStatus());
        rdmsProjectExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andStatusIn(statusList)
                .andDeletedEqualTo(0);
        List<RdmsProject> rdmsProjectInfos = rdmsProjectMapper.selectByExample(rdmsProjectExample);
        List<RdmsProjectDto> rdmsProjectInfoDtos = CopyUtil.copyList(rdmsProjectInfos, RdmsProjectDto.class);
        if(!CollectionUtils.isEmpty(rdmsProjectInfoDtos)){
            for(RdmsProjectDto projectDto: rdmsProjectInfoDtos){
                if(!ObjectUtils.isEmpty(projectDto.getProjectManagerId())){
                    RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(projectDto.getProjectManagerId());
                    projectDto.setProjectManagerName(rdmsCustomerUser.getTrueName());
                }
                if(!ObjectUtils.isEmpty(projectDto.getProductManagerId())){
                    RdmsCustomerUser rdmsCustomerUser1 = rdmsCustomerUserService.selectByPrimaryKey(projectDto.getProductManagerId());
                    projectDto.setProductManagerName(rdmsCustomerUser1.getTrueName());
                }
            }
        }
        return rdmsProjectInfoDtos;
    }

    @Transactional
    public List<RdmsProject> getProjectListByProjectName(String customerId, String projectNameKeyWord){
        RdmsProjectExample rdmsProjectExample = new RdmsProjectExample();
        List<String> statusList = new ArrayList<>();
        statusList.add(ProjectStatusEnum.SETUPED.getStatus());
        RdmsProjectExample.Criteria criteria = rdmsProjectExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andStatusIn(statusList)
                .andDeletedEqualTo(0);
        if(!ObjectUtils.isEmpty(projectNameKeyWord)){
            criteria.andProjectNameLike("%"+projectNameKeyWord+"%");
        }
        List<RdmsProject> rdmsProjectInfos = rdmsProjectMapper.selectByExample(rdmsProjectExample);
        return rdmsProjectInfos;
    }

    @Transactional
    public List<RdmsProjectDto> getMainProjectBudgetExecutionSummaryListByIPMT(String customerUserId){
        RdmsProjectExample rdmsProjectExample = new RdmsProjectExample();
        rdmsProjectExample.createCriteria()
                .andSuperviseEqualTo(customerUserId)
                .andStatusEqualTo(ProjectStatusEnum.SETUPED.getStatus())
                .andDeletedEqualTo(0);
        List<RdmsProject> rdmsProjectInfos = rdmsProjectMapper.selectByExample(rdmsProjectExample);
        List<RdmsProjectDto> rdmsProjectInfoDtos = CopyUtil.copyList(rdmsProjectInfos, RdmsProjectDto.class);
        if(!CollectionUtils.isEmpty(rdmsProjectInfoDtos)){
            for(RdmsProjectDto projectDto: rdmsProjectInfoDtos){
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(projectDto.getProjectManagerId());
                projectDto.setProjectManagerName(rdmsCustomerUser.getTrueName());
                RdmsCustomerUser rdmsCustomerUser1 = rdmsCustomerUserService.selectByPrimaryKey(projectDto.getProductManagerId());
                projectDto.setProductManagerName(rdmsCustomerUser1.getTrueName());
            }
        }
        return rdmsProjectInfoDtos;
    }

    /**
     * 获取当前用户管辖的所有产品经理\Underline等人员所管辖的项目经理Id列表
     *
     * @param customerUserId
     * @return
     */
    @Transactional
    public List<String> getUnderlineMemberList(String customerUserId) {
        //Underline
        //1. 如果自己是产品经理,或者为自己设定的underline是产品经理,需要显示的项目包括: 自己作为产品经理对应的项目
        List<String> underlineIdList = new ArrayList<>();
        underlineIdList.add(customerUserId);

        //得到部门所有下属人员清单
        List<RdmsDepartmentDto> rdmsDepartmentDtos = rdmsDepartmentService.listDepartmentByManagerId(customerUserId);
        if(!CollectionUtils.isEmpty(rdmsDepartmentDtos)){
            List<String> departmentIdList = rdmsDepartmentDtos.stream().map(RdmsDepartmentDto::getId).collect(Collectors.toList());
            List<RdmsCustomerUser> memberList = rdmsCustomerUserService.getMemberListByDepartmentIdList(departmentIdList);
            if(!CollectionUtils.isEmpty(memberList)){
                List<String> memberIdList = memberList.stream().map(RdmsCustomerUser::getId).collect(Collectors.toList());
                underlineIdList.addAll(memberIdList);
            }
        }

        //如果underlineIdList是产品经理,对应的项目经理都是哪些人
        List<String> projectManagerIds = new ArrayList<>();
        RdmsProjectExample rdmsProjectExample = new RdmsProjectExample();
        rdmsProjectExample.createCriteria()
                .andProductManagerIdIn(underlineIdList)
                .andStatusEqualTo(ProjectStatusEnum.SETUPED.getStatus())
                .andDeletedEqualTo(0);
        List<RdmsProject> rdmsProjectInfos = rdmsProjectMapper.selectByExample(rdmsProjectExample);
        if (!CollectionUtils.isEmpty(rdmsProjectInfos)) {
            List<String> projectManagerIdList = rdmsProjectInfos.stream().map(RdmsProject::getProjectManagerId).collect(Collectors.toList());
            projectManagerIds.addAll(projectManagerIdList);
        }

        //如果这些人本身就是项目经理,则加进列表,并去重
        projectManagerIds.addAll(underlineIdList);
        List<String> allUnderlineIdList = projectManagerIds.stream().distinct().collect(Collectors.toList());
        return allUnderlineIdList;
    }

    /**
     * 获取当前用户在资产/进度菜单中,所能够看到的项目列表
     * 只包含主项目,子项目在主项目页面中列出
     * @param customerUserId
     * @return
     */
    @Transactional
    public List<RdmsProjectDto> getUnderlineProjectList(String customerUserId){
        //1. 如果自己是产品经理,或者为自己设定的underline是产品经理,需要显示的项目包括: 自己作为产品经理对应的项目
        List<String> underlineIdList = new ArrayList<>();
        underlineIdList.add(customerUserId);

        //得到部门所有下属人员清单
        List<RdmsDepartmentDto> rdmsDepartmentDtos = rdmsDepartmentService.listDepartmentByManagerId(customerUserId);
        if(!CollectionUtils.isEmpty(rdmsDepartmentDtos)){
            List<String> departmentIdList = rdmsDepartmentDtos.stream().map(RdmsDepartmentDto::getId).collect(Collectors.toList());
            List<RdmsCustomerUser> memberList = rdmsCustomerUserService.getMemberListByDepartmentIdList(departmentIdList);
            if(!CollectionUtils.isEmpty(memberList)){
                List<String> memberIdList = memberList.stream().map(RdmsCustomerUser::getId).collect(Collectors.toList());
                underlineIdList.addAll(memberIdList);
            }
        }

        //如果这些人是产品经理,对应的项目经理都是哪些人
        List<String> projectManagerIds = new ArrayList<>();
        RdmsProjectExample rdmsProjectExample = new RdmsProjectExample();
        rdmsProjectExample.createCriteria()
                .andProductManagerIdIn(underlineIdList)
                .andStatusEqualTo(ProjectStatusEnum.SETUPED.getStatus())
                .andDeletedEqualTo(0);
        List<RdmsProject> rdmsProjectInfos = rdmsProjectMapper.selectByExample(rdmsProjectExample);
        if (!CollectionUtils.isEmpty(rdmsProjectInfos)) {
            List<String> projectManagerIdList = rdmsProjectInfos.stream().map(RdmsProject::getProjectManagerId).collect(Collectors.toList());
            projectManagerIds.addAll(projectManagerIdList);
        }

        //如果这些人本身就是项目经理,则加进列表,并去重
        projectManagerIds.addAll(underlineIdList);
        List<String> allPjmList = projectManagerIds.stream().distinct().collect(Collectors.toList());

        //项目经理角色
        RdmsProjectExample rdmsProjectExample2 = new RdmsProjectExample();
        rdmsProjectExample2.createCriteria()
                .andProjectManagerIdIn(allPjmList)
                .andStatusEqualTo(ProjectStatusEnum.SETUPED.getStatus())
                .andDeletedEqualTo(0);
        List<RdmsProject> rdmsProjectInfos2 = rdmsProjectMapper.selectByExample(rdmsProjectExample2);
        List<RdmsProjectDto> allProjectInfoDtos = CopyUtil.copyList(rdmsProjectInfos2, RdmsProjectDto.class);

        //补充信息
        if (!CollectionUtils.isEmpty(allProjectInfoDtos)) {
            for (RdmsProjectDto projectDto : allProjectInfoDtos) {
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(projectDto.getProjectManagerId());
                projectDto.setProjectManagerName(rdmsCustomerUser.getTrueName());
                RdmsCustomerUser rdmsCustomerUser1 = rdmsCustomerUserService.selectByPrimaryKey(projectDto.getProductManagerId());
                projectDto.setProductManagerName(rdmsCustomerUser1.getTrueName());
            }
        }
        return allProjectInfoDtos;
    }

    @Transactional
    public List<RdmsProjectDto> getProjectListByCode(String projectCode){
        RdmsProjectExample rdmsProjectExample = new RdmsProjectExample();
        rdmsProjectExample.createCriteria().andProjectCodeEqualTo(projectCode).andDeletedEqualTo(0);
        List<RdmsProject> rdmsProjectInfos = rdmsProjectMapper.selectByExample(rdmsProjectExample);
        List<RdmsProjectDto> rdmsProjectInfoDtos = CopyUtil.copyList(rdmsProjectInfos, RdmsProjectDto.class);
        return rdmsProjectInfoDtos;
    }

    @Transactional
    public Integer countSetupProjectBySupervise(String superviseId){
        RdmsProjectExample rdmsProjectExample = new RdmsProjectExample();
        rdmsProjectExample.createCriteria().andSuperviseEqualTo(superviseId).andDeletedEqualTo(0).andStatusEqualTo(ProjectStatusEnum.SETUP.getStatus());
        return (Integer) (int)rdmsProjectMapper.countByExample(rdmsProjectExample);
    }

    @Transactional
    public Integer countSetupProjectByProductManager(String productManagerId){
        RdmsProjectExample rdmsProjectExample = new RdmsProjectExample();
        rdmsProjectExample.createCriteria().andProductManagerIdEqualTo(productManagerId).andDeletedEqualTo(0).andStatusEqualTo(ProjectStatusEnum.SETUP_EDIT.getStatus());
        return (Integer) (int)rdmsProjectMapper.countByExample(rdmsProjectExample);
    }

    @Transactional
    public Integer countSetupProjectByCustomerId(String customerId){
        RdmsProjectExample rdmsProjectExample = new RdmsProjectExample();
        rdmsProjectExample.createCriteria().andCustomerIdEqualTo(customerId).andDeletedEqualTo(0).andStatusEqualTo(ProjectStatusEnum.SETUP.getStatus());
        return (Integer) (int)rdmsProjectMapper.countByExample(rdmsProjectExample);
    }

    /**
     *
     * @Param pageDto.KeyWord is ProjectId
     * @return
     */
    @Transactional
    public List<RdmsProjectDto> listProjectsByPreProjectId(String preProjectId){
        RdmsProjectExample rdmsProjectExample = new RdmsProjectExample();
        rdmsProjectExample.createCriteria().andPreProjectIdEqualTo(preProjectId).andDeletedEqualTo(0);
        List<RdmsProject> rdmsProjectInfos = rdmsProjectMapper.selectByExample(rdmsProjectExample);
        List<RdmsProjectDto> rdmsProjectInfoDtos = CopyUtil.copyList(rdmsProjectInfos, RdmsProjectDto.class);
        return rdmsProjectInfoDtos;
    }

    @Transactional
    public List<RdmsCharacterDto> getCbbList(String projectId){
        List<RdmsCharacterDto> characterListByIdList = new ArrayList<>();
        RdmsProject rdmsProject = this.selectByPrimaryKey(projectId);
        if(!ObjectUtils.isEmpty(rdmsProject) && !ObjectUtils.isEmpty(rdmsProject.getModuleIdListStr())){
            List<String> cbbIds = JSON.parseArray(rdmsProject.getModuleIdListStr(), String.class);
            if(!CollectionUtils.isEmpty(cbbIds)){
                characterListByIdList = rdmsCharacterService.getCharacterListByIdList(rdmsProject.getCustomerId(), cbbIds);
            }
        }
        return characterListByIdList;
    }

    @Transactional
    public RdmsProjectSubprojectDto getSubprojectBySubprojectId(String subprojectId){
         RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(subprojectId);
         RdmsProjectSubprojectDto projectSubprojectDto = CopyUtil.copy(subproject, RdmsProjectSubprojectDto.class);
         return projectSubprojectDto;
    }

    @Transactional
    public List<RdmsProjectDto> listSetupedProjects(String customerId){
        RdmsProjectExample rdmsProjectExample = new RdmsProjectExample();
        rdmsProjectExample.createCriteria().andCustomerIdEqualTo(customerId).andDeletedEqualTo(0).andStatusEqualTo(ProjectStatusEnum.SETUPED.getStatus());
        List<RdmsProject> rdmsProjectInfos = rdmsProjectMapper.selectByExample(rdmsProjectExample);
        List<RdmsProjectDto> rdmsProjectInfoDtos = CopyUtil.copyList(rdmsProjectInfos, RdmsProjectDto.class);
        return rdmsProjectInfoDtos;
    }

   @Transactional
    public List<RdmsProjectSubprojectDto> getSubprojectListByProjectId(String projectId){
        RdmsProjectSubprojectExample subProjectExample = new RdmsProjectSubprojectExample();
       subProjectExample.createCriteria().andProjectIdEqualTo(projectId).andDeletedEqualTo(0);
       List<RdmsProjectSubproject> rdmsProjectSubprojects = rdmsProjectSubprojectMapper.selectByExample(subProjectExample);
       List<RdmsProjectSubprojectDto> rdmsProjectSubprojectDtos = CopyUtil.copyList(rdmsProjectSubprojects, RdmsProjectSubprojectDto.class);
       return rdmsProjectSubprojectDtos;
    }

    @Transactional
    public RdmsProjectDto getProjectAndCharacterList(String projectId, List<CharacterStatusEnum> statusEnumList){
        RdmsProject rdmsProject = this.selectByPrimaryKey(projectId);
        RdmsProjectDto projectDto = CopyUtil.copy(rdmsProject, RdmsProjectDto.class);
        List<RdmsCharacterDto> characterList = rdmsCharacterService.getCharacterListByProjectIdAndStatusList_notUpdated(projectId, statusEnumList);
        if(!CollectionUtils.isEmpty(characterList)){
            for(RdmsCharacterDto characterDto: characterList){
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(characterDto.getWriterId());
                characterDto.setWriterName(rdmsCustomerUser.getTrueName());
            }
        }
        projectDto.setCharacters(characterList);
        return projectDto;
    }

    @Transactional
    public List<RdmsBomConfigDto> getBomConfigListByProjectId(String projectId){
        RdmsBomConfigExample bomConfigExample = new RdmsBomConfigExample();
        bomConfigExample.createCriteria().andProjectIdEqualTo(projectId).andDeletedEqualTo(0);
        List<RdmsBomConfig> rdmsBomConfigs = rdmsBomConfigMapper.selectByExample(bomConfigExample);

        return CopyUtil.copyList(rdmsBomConfigs, RdmsBomConfigDto.class);
    }

    @Transactional
    public RdmsProject getProjectBySubprojectId(String subprojectId){
        RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(subprojectId);
        RdmsProject rdmsProject = new RdmsProject();
        if(! ObjectUtils.isEmpty(subproject)){
            rdmsProject = this.selectByPrimaryKey(subproject.getProjectId());
        }
       return rdmsProject;
    }


    @Transactional
    public List<RdmsHmiSubprojectAndJobItemMixDto> getSubprojectListAndAllTaskJobitems(String projectId){
        List<RdmsHmiSubprojectAndJobItemMixDto> mixDtoList = new ArrayList<>();

        List<RdmsProjectSubprojectDto> subprojectList = this.getSubprojectListByProjectId(projectId);
        if(! ObjectUtils.isEmpty(subprojectList)){
            List<RdmsProjectSubproject> rdmsProjects = CopyUtil.copyList(subprojectList, RdmsProjectSubproject.class);
            for(RdmsProjectSubproject subproject: rdmsProjects){
                //subproject
                RdmsHmiSubprojectAndJobItemMixDto subprojectMix = new RdmsHmiSubprojectAndJobItemMixDto();
                {
                    subprojectMix.setId(subproject.getId());
                    subprojectMix.setItemName(subproject.getLabel());
                    subprojectMix.setProjectId(subproject.getProjectId());
                    RdmsProject rdmsProject = this.selectByPrimaryKey(subproject.getProjectId());
                    subprojectMix.setProjectName(rdmsProject.getProjectName());
                    subprojectMix.setParent(subproject.getParent());
                    subprojectMix.setProjectManagerId(subproject.getProjectManagerId());
                    RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(subproject.getProjectManagerId());
                    subprojectMix.setProjectManagerName(customerUser.getTrueName());
                    RdmsProject rdmsProject1 = this.selectByPrimaryKey(projectId);
                    subprojectMix.setProductManagerId(rdmsProject1.getProductManagerId());
                    RdmsCustomerUser customerUser1 = rdmsCustomerUserService.selectByPrimaryKey(rdmsProject1.getProductManagerId());
                    subprojectMix.setProductManagerName(customerUser1.getTrueName());
                    subprojectMix.setDescription(subproject.getIntroduce());
                    subprojectMix.setSubmitTime(subproject.getReleaseTime());
                    subprojectMix.setExecutorId(subproject.getProjectManagerId());
                    subprojectMix.setExecutorName(customerUser.getTrueName());
                    subprojectMix.setCreaterTime(subproject.getCreateTime());
                    subprojectMix.setParentOrChildren(ParentOrChildrenEnum.PARENT.getKey());
                }
                //subproject's task jobitems
                {
                    List<JobItemTypeEnum> typeEnumList = new ArrayList<>();
                    typeEnumList.add(JobItemTypeEnum.TASK_SUBP);

                    List<RdmsJobItemDto> jobitemList = rdmsJobItemService.getJobitemListBySubprojectId(subproject.getId(), null, typeEnumList);
                    List<RdmsHmiSubprojectAndJobItemMixDto> jobItemMixList = new ArrayList<>();
                    if(! CollectionUtils.isEmpty(jobitemList)){
                        for(RdmsJobItemDto jobItemDto: jobitemList){
                            //Dto assemble
                            RdmsHmiSubprojectAndJobItemMixDto jobItemMix = new RdmsHmiSubprojectAndJobItemMixDto();
                            jobItemMix.setId(jobItemDto.getId());
                            jobItemMix.setItemName(jobItemDto.getJobName());
                            jobItemMix.setParent(jobItemDto.getParentJobitemId());
                            jobItemMix.setDescription(jobItemDto.getJobDescription());
                            jobItemMix.setSubmitTime(jobItemDto.getPlanSubmissionTime());
                            jobItemMix.setExecutorId(jobItemDto.getExecutorId());
                            RdmsCustomerUser customerUser2 = rdmsCustomerUserService.selectByPrimaryKey(jobItemDto.getExecutorId());
                            jobItemMix.setExecutorName(customerUser2.getTrueName());
                            jobItemMix.setParentOrChildren(ParentOrChildrenEnum.CHILDREN.getKey());
                            //集成属性
                            jobItemMix.setProjectId(subproject.getProjectId());
                            RdmsProject rdmsProject = this.selectByPrimaryKey(subproject.getProjectId());
                            jobItemMix.setProjectName(rdmsProject.getProjectName());
                            jobItemMix.setProjectManagerId(subproject.getProjectManagerId());
                            RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(subproject.getProjectManagerId());
                            jobItemMix.setProjectManagerName(customerUser.getTrueName());
                            RdmsProject rdmsProject1 = this.selectByPrimaryKey(projectId);
                            jobItemMix.setProductManagerId(rdmsProject1.getProductManagerId());
                            RdmsCustomerUser customerUser1 = rdmsCustomerUserService.selectByPrimaryKey(rdmsProject1.getProductManagerId());
                            jobItemMix.setProductManagerName(customerUser1.getTrueName());
                            jobItemMix.setCreaterTime(jobItemDto.getCreateTime());

                            jobItemMixList.add(jobItemMix);
                        }
                    }
                    subprojectMix.setChildren(jobItemMixList);
                }
                mixDtoList.add(subprojectMix);
            }
        }
        return mixDtoList;
    }

    @Transactional
    public List<RdmsHmiSubprojectAndCharacterMixDto> getSubprojectsAndCharacterList(String projectManagerId){
        List<RdmsHmiSubprojectAndCharacterMixDto> mixDtoList = new ArrayList<>();

        List<RdmsProjectSubproject> subprojectList = this.getSubprojectByProjectManagerId(projectManagerId);
        if(! ObjectUtils.isEmpty(subprojectList)){
            for(RdmsProjectSubproject subproject: subprojectList){
                //subproject
                RdmsHmiSubprojectAndCharacterMixDto subprojectMix = new RdmsHmiSubprojectAndCharacterMixDto();
                RdmsProject rdmsSubProject = this.selectByPrimaryKey(subproject.getProjectId());
                {
                    subprojectMix.setId(subproject.getId());
                    subprojectMix.setItemName(subproject.getLabel());
                    subprojectMix.setProjectId(subproject.getProjectId());
                    subprojectMix.setProjectName(rdmsSubProject.getProjectName());
                    subprojectMix.setParent(subproject.getParent());
                    subprojectMix.setProjectManagerId(subproject.getProjectManagerId());
                    RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(subproject.getProjectManagerId());
                    subprojectMix.setProjectManagerName(customerUser.getTrueName());
                    subprojectMix.setProductManagerId(rdmsSubProject.getProductManagerId());
                    RdmsCustomerUser customerUser1 = rdmsCustomerUserService.selectByPrimaryKey(rdmsSubProject.getProductManagerId());
                    subprojectMix.setProductManagerName(customerUser1.getTrueName());
                    subprojectMix.setDescription(subproject.getIntroduce());
                    subprojectMix.setCreaterTime(subproject.getCreateTime());
                    subprojectMix.setParentOrChildren(ParentOrChildrenEnum.PARENT.getKey());
                    subprojectMix.setStatus(rdmsSubProject.getStatus());
                }
                {
                    List<RdmsCharacterDto> characterList = rdmsCharacterService.getCharacterListBySubprojectId(rdmsSubProject.getId(), null);
                    List<RdmsHmiSubprojectAndCharacterMixDto> characterMixList = new ArrayList<>();
                    if(! CollectionUtils.isEmpty(characterList)){
                        for(RdmsCharacterDto characterDto: characterList){

                            RdmsHmiSubprojectAndCharacterMixDto characterMix = new RdmsHmiSubprojectAndCharacterMixDto();
                            characterMix.setId(characterDto.getId());
                            characterMix.setItemName(characterDto.getCharacterName());
                            characterMix.setParent(characterDto.getParent());
                            characterMix.setDescription(characterDto.getFunctionDescription());
                            characterMix.setWriterId(characterDto.getWriterId());
                            RdmsCustomerUser customerUser2 = rdmsCustomerUserService.selectByPrimaryKey(characterDto.getWriterId());
                            characterMix.setWriterName(customerUser2.getTrueName());
                            characterMix.setParentOrChildren(ParentOrChildrenEnum.CHILDREN.getKey());
                            characterMix.setStatus(characterDto.getStatus());

                            characterMix.setProjectId(subproject.getProjectId());
                            RdmsProject rdmsProject = this.selectByPrimaryKey(subproject.getProjectId());
                            characterMix.setProjectName(rdmsProject.getProjectName());
                            characterMix.setProjectManagerId(subproject.getProjectManagerId());
                            RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(subproject.getProjectManagerId());
                            characterMix.setProjectManagerName(customerUser.getTrueName());
                            characterMix.setProductManagerId(rdmsProject.getProductManagerId());
                            RdmsCustomerUser customerUser1 = rdmsCustomerUserService.selectByPrimaryKey(rdmsProject.getProductManagerId());
                            characterMix.setProductManagerName(customerUser1.getTrueName());
                            characterMix.setCreaterTime(characterDto.getCreateTime());

                            characterMixList.add(characterMix);
                        }
                    }
                    subprojectMix.setChildren(characterMixList);
                }
                mixDtoList.add(subprojectMix);
            }
        }
        return mixDtoList;
    }

    @Transactional
    public void listSubprojectsAndCharacterList(PageDto<RdmsHmiSubprojectAndCharacterMixDto> pageDto){
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsProjectSubprojectExample rdmsProjectSubprojectExample = new RdmsProjectSubprojectExample();
        rdmsProjectSubprojectExample.createCriteria()
                .andCustomerIdEqualTo(pageDto.getKeyWord())
                .andProjectManagerIdEqualTo(pageDto.getActor())
                .andDeletedEqualTo(0)
                .andTypeIsNull();
        List<RdmsProjectSubproject> subprojectList = rdmsProjectSubprojectMapper.selectByExample(rdmsProjectSubprojectExample);
        PageInfo<RdmsProjectSubproject> pageInfo = new PageInfo<>(subprojectList);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsHmiSubprojectAndCharacterMixDto> mixDtoList = new ArrayList<>();
        Integer i = 1;
        if(! ObjectUtils.isEmpty(subprojectList)){
            for(RdmsProjectSubproject subproject: subprojectList){
                Integer j = 1;

                RdmsHmiSubprojectAndCharacterMixDto subprojectMix = new RdmsHmiSubprojectAndCharacterMixDto();
                RdmsProject rdmsProject = this.selectByPrimaryKey(subproject.getProjectId());
                {
                    subprojectMix.setId(subproject.getId());
                    subprojectMix.setItemName(subproject.getLabel());
                    subprojectMix.setProjectId(subproject.getProjectId());
                    subprojectMix.setProjectName(rdmsProject.getProjectName());
                    subprojectMix.setParent(subproject.getParent());
                    subprojectMix.setProjectManagerId(rdmsProject.getProjectManagerId());
                    RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(rdmsProject.getProjectManagerId());
                    subprojectMix.setProjectManagerName(customerUser.getTrueName());
                    subprojectMix.setProductManagerId(rdmsProject.getProductManagerId());
                    RdmsCustomerUser customerUser1 = rdmsCustomerUserService.selectByPrimaryKey(rdmsProject.getProductManagerId());
                    subprojectMix.setProductManagerName(customerUser1.getTrueName());
                    subprojectMix.setDescription(subproject.getIntroduce());
                    subprojectMix.setCreaterTime(subproject.getCreateTime());
                    subprojectMix.setParentOrChildren(ParentOrChildrenEnum.PARENT.getKey());
                    subprojectMix.setStatus(subproject.getStatus());
                    subprojectMix.setBudget(subproject.getBudget());
                    subprojectMix.setVersion(rdmsProject.getDevVersion());
                    subprojectMix.setComplateTime(subproject.getReleaseTime());
                    subprojectMix.setDocType(DocTypeEnum.SUB_PROJECT.getType());
                    subprojectMix.setIndex(i.toString());
                    i++;
                }
                {
                    List<RdmsCharacterDto> characterList = rdmsCharacterService.getCharacterListBySubprojectId(subproject.getId(), null);
                    List<RdmsHmiSubprojectAndCharacterMixDto> characterMixList = new ArrayList<>();
                    if(! CollectionUtils.isEmpty(characterList)){
                        for(RdmsCharacterDto characterDto: characterList){

                            RdmsHmiSubprojectAndCharacterMixDto characterMix = new RdmsHmiSubprojectAndCharacterMixDto();
                            characterMix.setId(characterDto.getId());
                            characterMix.setItemName(characterDto.getCharacterName());
                            characterMix.setParent(characterDto.getParent());
                            characterMix.setDescription(characterDto.getFunctionDescription());
                            characterMix.setWriterId(characterDto.getWriterId());
                            RdmsCustomerUser customerUser2 = rdmsCustomerUserService.selectByPrimaryKey(characterDto.getWriterId());
                            characterMix.setWriterName(customerUser2.getTrueName());
                            characterMix.setParentOrChildren(ParentOrChildrenEnum.CHILDREN.getKey());
                            characterMix.setStatus(characterDto.getStatus());

                            characterMix.setProjectId(subproject.getProjectId());
                            characterMix.setProjectName(rdmsProject.getProjectName());
                            characterMix.setProjectManagerId(subproject.getProjectManagerId());
                            RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(subproject.getProjectManagerId());
                            characterMix.setProjectManagerName(customerUser.getTrueName());
                            characterMix.setProductManagerId(rdmsProject.getProductManagerId());
                            RdmsCustomerUser customerUser1 = rdmsCustomerUserService.selectByPrimaryKey(rdmsProject.getProductManagerId());
                            characterMix.setProductManagerName(customerUser1.getTrueName());
                            characterMix.setCreaterTime(characterDto.getCreateTime());

                            characterMix.setBudget(characterDto.getBudget());
                            characterMix.setVersion(BigDecimal.valueOf( characterDto.getIterationVersion()));
                            RdmsMilestoneDto rdmsMilestoneDto = rdmsMilestoneService.selectByPrimaryKey(characterDto.getMilestoneId());
                            characterMix.setComplateTime(rdmsMilestoneDto.getReviewTime());
                            characterMix.setDocType(DocTypeEnum.CHARACTER.getType());
                            characterMix.setIndex((i-1) + "." + j);
                            j++;

                            characterMixList.add(characterMix);
                        }
                    }
                    subprojectMix.setChildren(characterMixList);
                }
                mixDtoList.add(subprojectMix);
            }
        }
        pageDto.setList(mixDtoList);
    }

    private List<RdmsProjectSubproject> getSubprojectByProjectManagerId(String projectManagerId) {
        RdmsProjectSubprojectExample subprojectExample = new RdmsProjectSubprojectExample();
        subprojectExample.createCriteria().andProjectManagerIdEqualTo(projectManagerId).andDeletedEqualTo(0);
        List<RdmsProjectSubproject> rdmsProjectSubprojects = rdmsProjectSubprojectMapper.selectByExample(subprojectExample);
        return rdmsProjectSubprojects;
    }

    /**
     *
     * @param projectId
     * @return
     */
    @Transactional
    public RdmsHmiProjectAndJobItemMixDto getProjectAndTaskJobitems(String projectId) {

        RdmsHmiProjectAndJobItemMixDto projectAndJobItemMixDto = new RdmsHmiProjectAndJobItemMixDto();
        RdmsProject project = this.selectByPrimaryKey(projectId);
        projectAndJobItemMixDto.setId(project.getId());
        projectAndJobItemMixDto.setItemName(project.getProjectName());
        projectAndJobItemMixDto.setStatus(project.getStatus());
        projectAndJobItemMixDto.setType("主项目");
        projectAndJobItemMixDto.setParentOrChildren(ParentOrChildrenEnum.PARENT.getKey());


        List<JobItemTypeEnum> typeEnumList = new ArrayList<>();
        typeEnumList.add(JobItemTypeEnum.TASK_PRODUCT);

        List<JobItemStatusEnum> statusEnumList = new ArrayList<>();
        statusEnumList.add(JobItemStatusEnum.HANDLING);
        statusEnumList.add(JobItemStatusEnum.SUBMIT);
        statusEnumList.add(JobItemStatusEnum.TESTING);

        List<RdmsJobItemDto> jobitemList = rdmsJobItemService.getJobitemListByProjectId(project.getId(), statusEnumList, typeEnumList);

        List<RdmsHmiProjectAndJobItemMixDto> jobItemMixList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jobitemList)) {
            for (RdmsJobItemDto jobItemDto : jobitemList) {
                //Dto assemble
                RdmsHmiProjectAndJobItemMixDto jobItemMix = new RdmsHmiProjectAndJobItemMixDto();
                jobItemMix.setId(jobItemDto.getId());
                jobItemMix.setItemName(jobItemDto.getJobName());
                jobItemMix.setStatus(jobItemDto.getStatus());
                jobItemMix.setType(jobItemDto.getType());
                jobItemMix.setParentOrChildren(ParentOrChildrenEnum.CHILDREN.getKey());
                jobItemMixList.add(jobItemMix);
            }
            projectAndJobItemMixDto.setChildren(jobItemMixList);
        }

        return projectAndJobItemMixDto;
    }


    /**
     *
     * @param subProjectId
     * @return
     */
    @Transactional
    public RdmsHmiProjectAndJobItemMixDto getSubProjectAndTaskJobitems(String subProjectId) {

        RdmsHmiProjectAndJobItemMixDto projectAndJobItemMixDto = new RdmsHmiProjectAndJobItemMixDto();
        RdmsProjectSubprojectDto subproject = this.getSubprojectBySubprojectId(subProjectId);
        projectAndJobItemMixDto.setId(subproject.getId());
        projectAndJobItemMixDto.setItemName(subproject.getLabel());
        projectAndJobItemMixDto.setStatus(subproject.getStatus());
        projectAndJobItemMixDto.setType("项目");
        projectAndJobItemMixDto.setParentOrChildren(ParentOrChildrenEnum.PARENT.getKey());

        if(subproject.getStatus().equals(ProjectStatusEnum.REVIEW_SUBP.getStatus())){
            projectAndJobItemMixDto.setIsReviewing(true);
        }else {
            projectAndJobItemMixDto.setIsReviewing(false);
        }

        if(subproject.getStatus().equals(ProjectStatusEnum.CLOSE.getStatus())
                || subproject.getStatus().equals(ProjectStatusEnum.COMPLETE.getStatus())
                || subproject.getStatus().equals(ProjectStatusEnum.ARCHIVED.getStatus())
        ){
            projectAndJobItemMixDto.setIsCompleted(true);
        }else{
            projectAndJobItemMixDto.setIsCompleted(false);
        }

        if(subproject.getStatus().equals(ProjectStatusEnum.DEV_COMPLETE.getStatus())){
            projectAndJobItemMixDto.setIsDevCompleted(true);
        }else{
            projectAndJobItemMixDto.setIsDevCompleted(false);
        }

        List<JobItemTypeEnum> typeEnumList = new ArrayList<>();
        typeEnumList.add(JobItemTypeEnum.TASK_SUBP);
        typeEnumList.add(JobItemTypeEnum.SUBPROJECT_INT);
        typeEnumList.add(JobItemTypeEnum.REVIEW_SUBP);

        List<JobItemStatusEnum> statusEnumList = new ArrayList<>();
        statusEnumList.add(JobItemStatusEnum.HANDLING);
        statusEnumList.add(JobItemStatusEnum.SUBMIT);
        statusEnumList.add(JobItemStatusEnum.TESTING);

        List<RdmsJobItemDto> jobitemList = rdmsJobItemService.getJobitemListBySubProjectId(subproject.getId(), statusEnumList, typeEnumList);

        if(subproject.getId().equals(subproject.getProjectId())){
            List<JobItemTypeEnum> typeEnumList_milestone = new ArrayList<>();
            typeEnumList_milestone.add(JobItemTypeEnum.TASK_MILESTONE);
            List<RdmsJobItemDto> jobitemList_milestone = rdmsJobItemService.getJobitemListByProjectId_mainPjm(subproject.getProjectId(), statusEnumList, typeEnumList_milestone, subproject.getProjectManagerId());
            jobitemList.addAll(jobitemList_milestone);
        }

        List<RdmsHmiProjectAndJobItemMixDto> jobItemMixList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jobitemList)) {
            for (RdmsJobItemDto jobItemDto : jobitemList) {
                //Dto assemble
                RdmsHmiProjectAndJobItemMixDto jobItemMix = new RdmsHmiProjectAndJobItemMixDto();
                jobItemMix.setId(jobItemDto.getId());
                jobItemMix.setItemName(jobItemDto.getJobName());
                jobItemMix.setStatus(jobItemDto.getStatus());
                jobItemMix.setType(jobItemDto.getType());
                jobItemMix.setParentOrChildren(ParentOrChildrenEnum.CHILDREN.getKey());
                jobItemMixList.add(jobItemMix);
            }
            projectAndJobItemMixDto.setChildren(jobItemMixList);
        }

        return projectAndJobItemMixDto;
    }


    /**
     *
     * @param subprojectId
     * @return
     */
    @Transactional
    public RdmsHmiProjectAndJobItemMixDto getSubProjectAndTestJobitems(String subprojectId) {

        RdmsHmiProjectAndJobItemMixDto projectAndJobItemMixDto = new RdmsHmiProjectAndJobItemMixDto();
        RdmsProjectSubprojectDto subproject = this.getSubprojectBySubprojectId(subprojectId);
        projectAndJobItemMixDto.setId(subproject.getId());
        projectAndJobItemMixDto.setItemName(subproject.getLabel());
        projectAndJobItemMixDto.setStatus(subproject.getStatus());
        projectAndJobItemMixDto.setParentOrChildren(ParentOrChildrenEnum.PARENT.getKey());

        List<JobItemTypeEnum> typeEnumList = new ArrayList<>();
        typeEnumList.add(JobItemTypeEnum.TEST);
        List<RdmsJobItemDto> jobitemList = rdmsJobItemService.getJobitemListBySubProjectId(subprojectId, null, typeEnumList);

        List<RdmsHmiProjectAndJobItemMixDto> jobItemMixList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jobitemList)) {
            for (RdmsJobItemDto jobItemDto : jobitemList) {
                //Dto assemble
                RdmsHmiProjectAndJobItemMixDto jobItemMix = new RdmsHmiProjectAndJobItemMixDto();
                jobItemMix.setId(jobItemDto.getId());
                jobItemMix.setItemName(jobItemDto.getJobName());
                jobItemMix.setStatus(jobItemDto.getStatus());
                jobItemMix.setParentOrChildren(ParentOrChildrenEnum.CHILDREN.getKey());
                jobItemMixList.add(jobItemMix);
            }
            projectAndJobItemMixDto.setChildren(jobItemMixList);
        }
        return projectAndJobItemMixDto;
    }

    /**
     *
     */
    @Transactional
    public RdmsHmiSubProjectReviewDetailDto getSubProjectReviewDetailInfo(String subprojectId, String loginUserId) {
        RdmsHmiSubProjectReviewDetailDto subProjectReviewDetailDto = new RdmsHmiSubProjectReviewDetailDto();

        subProjectReviewDetailDto.setSubprojectId(subprojectId);

        RdmsProjectSubprojectDto subprojectDto = this.getSubprojectBySubprojectId(subprojectId);
        subProjectReviewDetailDto.setSubProjectDto(subprojectDto);

        //子项目自身的评审信息
        {
            List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
            jobitemTypeList.add(JobItemTypeEnum.REVIEW_SUBP);
            List<RdmsJobItemDto> reviewJobItemDtos = rdmsJobItemService.getJobitemListBySubprojectIdAndStatus(subprojectId, JobItemStatusEnum.COMPLETED.getStatus(), jobitemTypeList);
            if(! CollectionUtils.isEmpty(reviewJobItemDtos)){
                for(RdmsJobItemDto jobItemDto: reviewJobItemDtos){
                    RdmsJobItemDto jobitemRecordDetailInfo = rdmsJobItemService.getJobitemDetailInfo(jobItemDto.getId(), loginUserId);
                    if(CollectionUtils.isEmpty(subProjectReviewDetailDto.getReviewJobitemList())){
                        List<RdmsJobItemDto> jobitemList = new ArrayList<>();
                        jobitemList.add(jobitemRecordDetailInfo);
                        subProjectReviewDetailDto.setReviewJobitemList(jobitemList);
                    }else{
                        subProjectReviewDetailDto.getReviewJobitemList().add(jobitemRecordDetailInfo);
                    }
                }
            }
        }

        //子项目项下所有Character的评审信息
        List<RdmsCharacterDto> characterList = rdmsCharacterService.getCharacterListBySubprojectId(subprojectId, CharacterStatusEnum.ARCHIVED);
        List<RdmsHmiCharacterReviewDetailDto> characterReviewDetailDtos = new ArrayList<>();
        if(!CollectionUtils.isEmpty(characterList)){
            for(RdmsCharacterDto characterDto: characterList){
                RdmsHmiCharacterReviewDetailDto characterReviewDetailInfo = rdmsCharacterService.getCharacterReviewDetailInfo(characterDto.getId(), loginUserId);
                characterReviewDetailDtos.add(characterReviewDetailInfo);
            }
        }
        subProjectReviewDetailDto.setCharacterReviewDetailList(characterReviewDetailDtos);

        //子项目项下所有子项目的评审信息
        List<RdmsProjectSubprojectDto> subProjectList = this.getSubProjectListByParentId(subprojectId, ProjectStatusEnum.COMPLETE);
        List<RdmsHmiSubProjectReviewDetailDto> subProjectReviewDetailDtos = new ArrayList<>();
        if(!CollectionUtils.isEmpty(subProjectList)){
            for(RdmsProjectSubprojectDto subproject: subProjectList){
                RdmsHmiSubProjectReviewDetailDto subProjectReviewDetailInfo = this.getSubProjectReviewDetailInfo(subproject.getId(), loginUserId);
                subProjectReviewDetailDtos.add(subProjectReviewDetailInfo);
            }
        }
        subProjectReviewDetailDto.setSubProjectReviewDetailList(subProjectReviewDetailDtos);

        return subProjectReviewDetailDto;
    }

    /**
     *
     */
    @Transactional
    public RdmsHmiProjectReviewDetailDto getProjectReviewDetailInfo(String projectId, String loginUserId) {
        RdmsHmiProjectReviewDetailDto projectReviewDetailDto = new RdmsHmiProjectReviewDetailDto();

        projectReviewDetailDto.setProjectId(projectId);

        RdmsProject rdmsProject = this.selectByPrimaryKey(projectId);
        RdmsProjectDto projectDto = CopyUtil.copy(rdmsProject, RdmsProjectDto.class);
        this.addDetailInfoForProjectDto(projectDto);
        projectReviewDetailDto.setProjectDto(projectDto);

        //项目自身的评审信息
        {
            List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
            jobitemTypeList.add(JobItemTypeEnum.REVIEW_PRO);
            List<RdmsJobItemDto> reviewJobItemDtos = rdmsJobItemService.getJobitemListByProjectIdAndStatus(projectId, JobItemStatusEnum.COMPLETED.getStatus(), jobitemTypeList);
            if(! CollectionUtils.isEmpty(reviewJobItemDtos)){
                for(RdmsJobItemDto jobItemDto: reviewJobItemDtos){
                    RdmsJobItemDto jobitemRecordDetailInfo = rdmsJobItemService.getJobitemDetailInfo(jobItemDto.getId(), loginUserId);
                    if(CollectionUtils.isEmpty(projectReviewDetailDto.getReviewJobitemList())){
                        List<RdmsJobItemDto> jobitemList = new ArrayList<>();
                        jobitemList.add(jobitemRecordDetailInfo);
                        projectReviewDetailDto.setReviewJobitemList(jobitemList);
                    }else{
                        projectReviewDetailDto.getReviewJobitemList().add(jobitemRecordDetailInfo);
                    }
                }
            }
        }

        //子项目项下所有子项目的评审信息
        List<RdmsProjectSubprojectDto> subProjectList = this.getSubProjectListByParentId(projectId, ProjectStatusEnum.COMPLETE);
        List<RdmsHmiSubProjectReviewDetailDto> subProjectReviewDetailDtos = new ArrayList<>();
        if(!CollectionUtils.isEmpty(subProjectList)){
            for(RdmsProjectSubprojectDto subproject: subProjectList){
                RdmsHmiSubProjectReviewDetailDto subProjectReviewDetailInfo = this.getSubProjectReviewDetailInfo(subproject.getId(), loginUserId);
                subProjectReviewDetailDtos.add(subProjectReviewDetailInfo);
            }
        }
        projectReviewDetailDto.setSubProjectReviewDetailList(subProjectReviewDetailDtos);

        return projectReviewDetailDto;
    }

    private void addDetailInfoForProjectDto(RdmsProjectDto projectDto) {
        if(ObjectUtils.isEmpty(projectDto)){
            throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
        }
        if(!ObjectUtils.isEmpty(projectDto.getProductManagerId())){
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(projectDto.getProductManagerId());
            if(!ObjectUtils.isEmpty(rdmsCustomerUser)){
                projectDto.setProductManagerName(rdmsCustomerUser.getTrueName());
            }
        }
        if(!ObjectUtils.isEmpty(projectDto.getProjectManagerId())){
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(projectDto.getProjectManagerId());
            if(!ObjectUtils.isEmpty(rdmsCustomerUser)){
                projectDto.setProjectManagerName(rdmsCustomerUser.getTrueName());
            }
        }
        if(!ObjectUtils.isEmpty(projectDto.getSupervise())){
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(projectDto.getSupervise());
            if(!ObjectUtils.isEmpty(rdmsCustomerUser)){
                projectDto.setSuperviseName(rdmsCustomerUser.getTrueName());
            }
        }
        if(!ObjectUtils.isEmpty(projectDto.getTestManagerId())){
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(projectDto.getTestManagerId());
            if(!ObjectUtils.isEmpty(rdmsCustomerUser)){
                projectDto.setTestManagerName(rdmsCustomerUser.getTrueName());
            }
        }
        if(!ObjectUtils.isEmpty(projectDto.getKeyMemberListStr())){
            List<String> strings = JSON.parseArray(projectDto.getKeyMemberListStr(), String.class);
            List<RdmsCustomerUser> customerUsers = new ArrayList<>();
            if(!CollectionUtils.isEmpty(strings)){
                for(String id: strings){
                    RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(id);
                    if(!ObjectUtils.isEmpty(rdmsCustomerUser)){
                        customerUsers.add(rdmsCustomerUser);
                    }
                }
            }
            if(!CollectionUtils.isEmpty(customerUsers)){
                List<String> stringList = customerUsers.stream().map(RdmsCustomerUser::getTrueName).collect(Collectors.toList());
                projectDto.setKeyMemberList(stringList);
            }
        }
    }

    @Transactional
    public List<RdmsProjectSubprojectDto> getSubProjectListByParentId(String parentId, ProjectStatusEnum statusEnum){
        RdmsProjectSubprojectExample subprojectExample = new RdmsProjectSubprojectExample();
        RdmsProjectSubprojectExample.Criteria criteria = subprojectExample.createCriteria().andParentEqualTo(parentId).andDeletedEqualTo(0);
        if(!ObjectUtils.isEmpty(statusEnum)){
            criteria.andStatusEqualTo(statusEnum.getStatus());
        }
        List<RdmsProjectSubproject> rdmsSubprojects = rdmsProjectSubprojectMapper.selectByExample(subprojectExample);
        return CopyUtil.copyList(rdmsSubprojects, RdmsProjectSubprojectDto.class);
    }
    /**
     *
     * @param subprojectId
     * @return
     */
    @Transactional
    public RdmsHmiProjectAndJobItemMixDto getSubProjectAndTestJobitems_submit(String subprojectId) {

        RdmsHmiProjectAndJobItemMixDto projectAndJobItemMixDto = new RdmsHmiProjectAndJobItemMixDto();
        RdmsProjectSubprojectDto subproject = this.getSubprojectBySubprojectId(subprojectId);
        projectAndJobItemMixDto.setId(subproject.getId());
        projectAndJobItemMixDto.setItemName(subproject.getLabel());
        projectAndJobItemMixDto.setStatus(subproject.getStatus());
        projectAndJobItemMixDto.setParentOrChildren(ParentOrChildrenEnum.PARENT.getKey());

        List<JobItemTypeEnum> typeEnumList = new ArrayList<>();
        typeEnumList.add(JobItemTypeEnum.TEST);

        List<JobItemStatusEnum> statusEnumList = new ArrayList<>();
        statusEnumList.add(JobItemStatusEnum.SUBMIT);

        List<RdmsJobItemDto> jobitemList = rdmsJobItemService.getJobitemListBySubProjectId(subprojectId, statusEnumList, typeEnumList);

        List<RdmsHmiProjectAndJobItemMixDto> jobItemMixList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jobitemList)) {
            for (RdmsJobItemDto jobItemDto : jobitemList) {
                //Dto assemble
                RdmsHmiProjectAndJobItemMixDto jobItemMix = new RdmsHmiProjectAndJobItemMixDto();
                jobItemMix.setId(jobItemDto.getId());
                jobItemMix.setItemName(jobItemDto.getJobName());
                jobItemMix.setStatus(jobItemDto.getStatus());
                jobItemMix.setParentOrChildren(ParentOrChildrenEnum.CHILDREN.getKey());
                jobItemMixList.add(jobItemMix);
            }
            projectAndJobItemMixDto.setChildren(jobItemMixList);
        }
        return projectAndJobItemMixDto;
    }

    @Transactional
    public Integer getCountOfSetupedProjectByPjm(String pjmId) {
        RdmsProjectSubprojectExample rdmsProjectSubprojectExample = new RdmsProjectSubprojectExample();
        rdmsProjectSubprojectExample.createCriteria()
                .andStatusEqualTo(ProjectStatusEnum.SETUPED.getStatus())
                .andProjectManagerIdEqualTo(pjmId)
                .andDeletedEqualTo(0);
        return (Integer) (int)rdmsProjectSubprojectMapper.countByExample(rdmsProjectSubprojectExample);
    }

    @Transactional
    public Integer getCountOfArchivedProjectByPjm(String pjmId) {
        RdmsProjectSubprojectExample rdmsProjectSubprojectExample = new RdmsProjectSubprojectExample();
        rdmsProjectSubprojectExample.createCriteria()
                .andStatusEqualTo(ProjectStatusEnum.ARCHIVED.getStatus())
                .andProjectManagerIdEqualTo(pjmId)
                .andDeletedEqualTo(0);
        return (Integer) (int)rdmsProjectSubprojectMapper.countByExample(rdmsProjectSubprojectExample);
    }

    @Transactional
    public Integer getCountOfArchivedProjectByPdm(String pdmId) {
        RdmsProjectExample rdmsProjectExample = new RdmsProjectExample();
        rdmsProjectExample.createCriteria()
                .andStatusEqualTo(ProjectStatusEnum.ARCHIVED.getStatus())
                .andProductManagerIdEqualTo(pdmId)
                .andDeletedEqualTo(0);
        return (Integer) (int)rdmsProjectMapper.countByExample(rdmsProjectExample);
    }

    @Transactional
    public Integer getCountOfSetupProjectByPdm(String pdmId) {
        RdmsProjectExample rdmsProjectExample = new RdmsProjectExample();
        rdmsProjectExample.createCriteria()
                .andStatusEqualTo(ProjectStatusEnum.SETUP.getStatus())
                .andProductManagerIdEqualTo(pdmId)
                .andDeletedEqualTo(0);
        return (Integer) (int)rdmsProjectMapper.countByExample(rdmsProjectExample);
    }

    /**
     *
     * @param projectId
     * @return
     */
    @Transactional
    public RdmsHmiProjectAndJobItemMixDto getProjectAndTaskJobitems_submit(String projectId) {

        RdmsHmiProjectAndJobItemMixDto projectAndJobItemMixDto = new RdmsHmiProjectAndJobItemMixDto();
        RdmsProject project = this.selectByPrimaryKey(projectId);
        if(! ObjectUtils.isEmpty(project)){
            projectAndJobItemMixDto.setId(project.getId());
            projectAndJobItemMixDto.setItemName(project.getProjectName());
            projectAndJobItemMixDto.setStatus(project.getStatus());
            projectAndJobItemMixDto.setParentOrChildren(ParentOrChildrenEnum.PARENT.getKey());
            projectAndJobItemMixDto.setDocType(DocTypeEnum.PROJECT.getType());
            projectAndJobItemMixDto.setHasIntJobitem(false);
            int index = 1;

            List<JobItemTypeEnum> typeEnumList = new ArrayList<>();
            typeEnumList.add(JobItemTypeEnum.TASK_PRODUCT);

            List<JobItemStatusEnum> statusEnumList = new ArrayList<>();
            statusEnumList.add(JobItemStatusEnum.SUBMIT);

            List<RdmsJobItemDto> jobitemList = rdmsJobItemService.getJobitemListByProjectId(project.getId(), statusEnumList, typeEnumList);
            List<RdmsHmiProjectAndJobItemMixDto> jobItemMixList = new ArrayList<>();
            if (!CollectionUtils.isEmpty(jobitemList)) {
                for (RdmsJobItemDto jobItemDto : jobitemList) {
                    RdmsHmiProjectAndJobItemMixDto jobItemMix = new RdmsHmiProjectAndJobItemMixDto();
                    jobItemMix.setId(jobItemDto.getId());
                    jobItemMix.setItemName(jobItemDto.getJobName());
                    jobItemMix.setStatus(jobItemDto.getStatus());
                    jobItemMix.setType(jobItemDto.getType());
                    jobItemMix.setProjectType(jobItemDto.getProjectType());
                    jobItemMix.setParentOrChildren(ParentOrChildrenEnum.CHILDREN.getKey());
                    jobItemMix.setDocType(DocTypeEnum.JOBITEM.getType());
                    /*if (jobItemDto.getType().equals(JobItemTypeEnum.PROJECT_INT.getType())) {
                        projectAndJobItemMixDto.setHasIntJobitem(true); //已经发集成工单了
                    }*/
                    jobItemMix.setIndex(index++);

                    jobItemMixList.add(jobItemMix);
                }
                projectAndJobItemMixDto.setChildren(jobItemMixList);
            }
        }

        return projectAndJobItemMixDto;
    }

    /**
     *
     * @param subProjectId
     * @return
     */
    @Transactional
    public RdmsHmiProjectAndJobItemMixDto getSubProjectAndTaskJobitems_submit(String subProjectId) {

        RdmsHmiProjectAndJobItemMixDto projectAndJobItemMixDto = new RdmsHmiProjectAndJobItemMixDto();
        RdmsProjectSubprojectDto subproject = this.getSubprojectBySubprojectId(subProjectId);
        if(! ObjectUtils.isEmpty(subproject)){
            projectAndJobItemMixDto.setId(subproject.getId());
            projectAndJobItemMixDto.setItemName(subproject.getLabel());
            projectAndJobItemMixDto.setStatus(subproject.getStatus());
            projectAndJobItemMixDto.setParentOrChildren(ParentOrChildrenEnum.PARENT.getKey());
            projectAndJobItemMixDto.setDocType(DocTypeEnum.SUB_PROJECT.getType());
            projectAndJobItemMixDto.setHasIntJobitem(false);
            int index = 1;

            List<JobItemTypeEnum> typeEnumList = new ArrayList<>();
            typeEnumList.add(JobItemTypeEnum.TASK_SUBP);
            typeEnumList.add(JobItemTypeEnum.SUBPROJECT_INT);

            List<JobItemStatusEnum> statusEnumList = new ArrayList<>();
            statusEnumList.add(JobItemStatusEnum.SUBMIT);

            List<RdmsJobItemDto> jobitemList = rdmsJobItemService.getJobitemListBySubProjectId(subproject.getId(), statusEnumList, typeEnumList);

            if(subproject.getId().equals(subproject.getProjectId())){
                List<JobItemTypeEnum> typeEnumList_milestone = new ArrayList<>();
                typeEnumList_milestone.add(JobItemTypeEnum.TASK_MILESTONE);
                List<RdmsJobItemDto> jobitemList_milestone = rdmsJobItemService.getJobitemListByProjectId_mainPjm(subproject.getProjectId(), statusEnumList, typeEnumList_milestone, subproject.getProjectManagerId());
                jobitemList.addAll(jobitemList_milestone);
            }

            List<RdmsHmiProjectAndJobItemMixDto> jobItemMixList = new ArrayList<>();
            if (!CollectionUtils.isEmpty(jobitemList)) {
                for (RdmsJobItemDto jobItemDto : jobitemList) {
                    RdmsHmiProjectAndJobItemMixDto jobItemMix = new RdmsHmiProjectAndJobItemMixDto();
                    jobItemMix.setId(jobItemDto.getId());
                    jobItemMix.setItemName(jobItemDto.getJobName());
                    jobItemMix.setStatus(jobItemDto.getStatus());
                    jobItemMix.setType(jobItemDto.getType());
                    jobItemMix.setProjectType(jobItemDto.getProjectType());
                    jobItemMix.setParentOrChildren(ParentOrChildrenEnum.CHILDREN.getKey());
                    jobItemMix.setDocType(DocTypeEnum.JOBITEM.getType());
                    if (jobItemDto.getType().equals(JobItemTypeEnum.SUBPROJECT_INT.getType())) {
                        projectAndJobItemMixDto.setHasIntJobitem(true); //已经发集成工单了
                    }
                    jobItemMix.setIndex(index++);

                    jobItemMixList.add(jobItemMix);
                }
                projectAndJobItemMixDto.setChildren(jobItemMixList);
            }
        }

        return projectAndJobItemMixDto;
    }


    public RdmsProject findProjectById(String projectId){
        RdmsProjectExample rdmsProjectExample = new RdmsProjectExample();
        rdmsProjectExample.createCriteria().andIdEqualTo(projectId);
        List<RdmsProject> rdmsProjectInfos = rdmsProjectMapper.selectByExample(rdmsProjectExample);
        if(!rdmsProjectInfos.isEmpty()){
            return rdmsProjectInfos.get(0);
        }
        return null;
    }

    @Transactional
    public RdmsHmiIdsDto getIdsDtoByProjectIdAndType(String projectType, String anyProjectId){
        RdmsHmiIdsDto idsDto = new RdmsHmiIdsDto();
        ProjectTypeEnum projectTypeEnumByType = ProjectTypeEnum.getProjectTypeEnumByType(projectType);
        if(! ObjectUtils.isEmpty(projectTypeEnumByType)){
            switch (projectTypeEnumByType){
                case PROJECT:{
                    RdmsProject rdmsProject = this.selectByPrimaryKey(anyProjectId);
                    idsDto = CopyUtil.copy(rdmsProject, RdmsHmiIdsDto.class);
                    idsDto.setProjectId(rdmsProject.getId());
                    idsDto.setRdType(rdmsProject.getRdType());
                    idsDto.setPlanCompleteTime(rdmsProject.getReleaseTime());
                    return idsDto;
                }
                case SUB_PROJECT:{
                    RdmsProjectSubprojectDto subproject = this.getSubprojectBySubprojectId(anyProjectId);
                    idsDto = CopyUtil.copy(subproject, RdmsHmiIdsDto.class);
                    idsDto.setSubprojectId(subproject.getId());
                    idsDto.setRdType(subproject.getRdType());
                    idsDto.setPlanCompleteTime(subproject.getReleaseTime());
                    return idsDto;
                }
                case PRE_PROJECT:{
                    RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(anyProjectId);
                    idsDto = CopyUtil.copy(rdmsPreProject, RdmsHmiIdsDto.class);
                    idsDto.setPreProjectId(rdmsPreProject.getId());
                    idsDto.setRdType(null);
                    return idsDto;
                }
            }
        }
        return null;
    }

    @Transactional
    public List<RdmsProjectDto> getArchivedProjectsByCustomerAndPdmId(String customerId, String pdmId){
        RdmsProjectExample projectExample = new RdmsProjectExample();
        projectExample.setOrderByClause("create_time desc");
        RdmsProjectExample.Criteria criteria = projectExample.createCriteria()
                .andCustomerIdEqualTo(customerId);

        List<String> statusList = new ArrayList<>();
        statusList.add(ProjectStatusEnum.COMPLETE.getStatus());
        statusList.add(ProjectStatusEnum.ARCHIVED.getStatus());
        criteria.andStatusIn(statusList);

        Boolean isAdmin = rdmsCustomerUserService.isAdmin(customerId, pdmId);
        Boolean isBoss = rdmsCustomerUserService.isBoss(customerId, pdmId);
        if(!isAdmin && !isBoss){
            RdmsPdgmDto pdgmByCustomerId = rdmsPdgmService.getPdgmByCustomerId(customerId);
            if(!ObjectUtils.isEmpty(pdgmByCustomerId)){
                if(! pdgmByCustomerId.getPdgmId().equals(pdmId)){
                    criteria.andProductManagerIdEqualTo(pdmId);
                }
            }else{
                criteria.andProductManagerIdEqualTo(pdmId);
            }
        }

        List<RdmsProject> rdmsProjects = rdmsProjectMapper.selectByExample(projectExample);
        List<RdmsProjectDto> rdmsProjectDtos = CopyUtil.copyList(rdmsProjects, RdmsProjectDto.class);

        rdmsProjectDtos.forEach(project -> {
            List<JobItemStatusEnum> statusList1 = new ArrayList<>();
            statusList1.add(JobItemStatusEnum.HANDLING);
            List<RdmsJobItemDto> handlingJobList = rdmsJobItemService.getJobitemListByProjectIdPdmIdAndStatus(project.getId(), project.getProjectManagerId(), statusList1, ProjectTypeEnum.PROJECT);
            project.setHandlingJobNum(handlingJobList.size());

            List<JobItemStatusEnum> statusList_submit = new ArrayList<>();
            statusList_submit.add(JobItemStatusEnum.SUBMIT);
            List<RdmsJobItemDto> submitJobList = rdmsJobItemService.getJobitemListByProjectIdPdmIdAndStatus(project.getId(), project.getProductManagerId(), statusList_submit, ProjectTypeEnum.PROJECT);
            if(!CollectionUtils.isEmpty(submitJobList)){
                List<RdmsJobItemDto> collect = submitJobList.stream().filter(item -> item.getType().equals(JobItemTypeEnum.TASK_PRODUCT.getType())).collect(Collectors.toList());
                project.setSubmitJobNum(collect.size());
            }

            Integer materialApplicationNum = rdmsMaterialManageService.getApplicationNumByProjectId(project.getId());
            project.setMaterialAppNum(materialApplicationNum);
            Integer feeApplicationNum = rdmsFeeManageService.getApplicationNumByProjectId(project.getId());
            project.setFeeAppNum(feeApplicationNum);
        });

        if(! CollectionUtils.isEmpty(rdmsProjectDtos)){
            for(RdmsProjectDto projectDto: rdmsProjectDtos){
                this.addDetailInfoForProjectDto(projectDto);
            }
        }
        return rdmsProjectDtos;
    }

    @Transactional
    public List<RdmsProjectDto> getAllProjectArchivedList(String loginCustomerId){
        RdmsProjectExample projectExample = new RdmsProjectExample();
        if(loginCustomerId.equals(SUPER_CUSTOMER_ID)){
            RdmsProjectExample.Criteria criteria = projectExample.createCriteria();
            List<String> statusList = new ArrayList<>();
            statusList.add(ProjectStatusEnum.COMPLETE.getStatus());
            statusList.add(ProjectStatusEnum.ARCHIVED.getStatus());
            criteria.andStatusIn(statusList);
            criteria.andCustomerIdEqualTo(loginCustomerId);
            List<RdmsProject> rdmsProjects = rdmsProjectMapper.selectByExample(projectExample);
            List<RdmsProjectDto> rdmsProjectDtos = CopyUtil.copyList(rdmsProjects, RdmsProjectDto.class);
            if(! CollectionUtils.isEmpty(rdmsProjectDtos)){
                for(RdmsProjectDto projectDto: rdmsProjectDtos){
                    this.addDetailInfoForProjectDto(projectDto);
                }
            }
            return rdmsProjectDtos;
        }else{
            return null;
        }
    }

    @Transactional
    public List<RdmsProjectDto> getAllProjectArchivedListByStaff(String loginCustomerId){
        RdmsProjectExample projectExample = new RdmsProjectExample();
        RdmsProjectExample.Criteria criteria = projectExample.createCriteria();
        List<String> statusList = new ArrayList<>();
        statusList.add(ProjectStatusEnum.COMPLETE.getStatus());
        statusList.add(ProjectStatusEnum.ARCHIVED.getStatus());
        criteria.andStatusIn(statusList);
        criteria.andCustomerIdEqualTo(loginCustomerId);
        List<RdmsProject> rdmsProjects = rdmsProjectMapper.selectByExample(projectExample);
        List<RdmsProjectDto> rdmsProjectDtos = CopyUtil.copyList(rdmsProjects, RdmsProjectDto.class);
        if(! CollectionUtils.isEmpty(rdmsProjectDtos)){
            for(RdmsProjectDto projectDto: rdmsProjectDtos){
                this.addDetailInfoForProjectDto(projectDto);
            }
        }
        return rdmsProjectDtos;
    }

    @Transactional
    public List<RdmsProjectDto> getDevelopingProjectsByCustomerAndPdmId(String customerId, String pdmId){
        RdmsProjectExample projectExample = new RdmsProjectExample();
        projectExample.setOrderByClause("create_time desc");
        RdmsProjectExample.Criteria criteria = projectExample.createCriteria()
                .andCustomerIdEqualTo(customerId);

        List<String> statusList = new ArrayList<>();
        statusList.add(ProjectStatusEnum.SETUPED.getStatus());
        statusList.add(ProjectStatusEnum.ONGOING.getStatus());
        statusList.add(ProjectStatusEnum.SUSPEND.getStatus());
        statusList.add(ProjectStatusEnum.INTEGRATION.getStatus());
        statusList.add(ProjectStatusEnum.DEV_COMPLETE.getStatus());
        statusList.add(ProjectStatusEnum.EN_REVIEW.getStatus());
        statusList.add(ProjectStatusEnum.REVIEW_SUBP.getStatus());
        statusList.add(ProjectStatusEnum.REVIEW_PRO.getStatus());
        criteria.andStatusIn(statusList);

        Boolean isAdmin = rdmsCustomerUserService.isAdmin(customerId, pdmId);
        if(!isAdmin){
            RdmsPdgmDto pdgmByCustomerId = rdmsPdgmService.getPdgmByCustomerId(customerId);
            if(!ObjectUtils.isEmpty(pdgmByCustomerId)){
                if(! pdgmByCustomerId.getPdgmId().equals(pdmId)){
                    criteria.andProductManagerIdEqualTo(pdmId);
                }
            }else{
                criteria.andProductManagerIdEqualTo(pdmId);
            }
        }

        List<RdmsProject> rdmsProjects = rdmsProjectMapper.selectByExample(projectExample);
        List<RdmsProjectDto> rdmsProjectDtos = CopyUtil.copyList(rdmsProjects, RdmsProjectDto.class);

        if(! CollectionUtils.isEmpty(rdmsProjectDtos)){
            for(RdmsProjectDto projectDto: rdmsProjectDtos){
                this.addDetailInfoForProjectDto(projectDto);
            }
        }
        return rdmsProjectDtos;
    }

    @Transactional
    public List<RdmsProjectSubprojectDto> getSubprojectListByProjectIdAndStatus(String projectId, List<ProjectStatusEnum> statusEnumList){
        RdmsProjectSubprojectExample subprojectExample = new RdmsProjectSubprojectExample();
        RdmsProjectSubprojectExample.Criteria criteria = subprojectExample.createCriteria().andProjectIdEqualTo(projectId).andDeletedEqualTo(0);
        if(! CollectionUtils.isEmpty(statusEnumList)){
            List<String> statusList = new ArrayList<>();
            for(ProjectStatusEnum statusEnum: statusEnumList){
                statusList.add(statusEnum.getStatus());
            }
            criteria.andStatusIn(statusList);
        }
        List<RdmsProjectSubproject> rdmsProjectSubprojects = rdmsProjectSubprojectMapper.selectByExample(subprojectExample);
        return CopyUtil.copyList(rdmsProjectSubprojects, RdmsProjectSubprojectDto.class);
    }

    @Transactional
    public List<RdmsProjectDto> getSetupedProjectListByPDM(String pdmId){
        RdmsProjectExample rdmsProjectExample = new RdmsProjectExample();
        rdmsProjectExample.createCriteria()
                .andProductManagerIdEqualTo(pdmId)
                .andStatusEqualTo(ProjectStatusEnum.SETUPED.getStatus())
                .andDeletedEqualTo(0);
        List<RdmsProject> rdmsProjects = rdmsProjectMapper.selectByExample(rdmsProjectExample);
        return CopyUtil.copyList(rdmsProjects, RdmsProjectDto.class);
    }

    public List<String> findKeyMemberIdListByProjectId(String projectId){
        RdmsProjectExample rdmsProjectExample = new RdmsProjectExample();
        rdmsProjectExample.createCriteria().andIdEqualTo(projectId);
        List<RdmsProject> rdmsProjectInfos = rdmsProjectMapper.selectByExample(rdmsProjectExample);
        if(rdmsProjectInfos.size()>0){
            String memberIdListStr = rdmsProjectInfos.get(0).getKeyMemberListStr();
            List<String> stringList = JSON.parseArray(memberIdListStr, String.class);
            return stringList;
        }
        return null;
    }

    public long projectNameIsUsed(String customerId, String projectName){
        RdmsProjectExample projectInfoExample = new RdmsProjectExample();
        projectInfoExample.createCriteria().andProjectNameEqualTo(projectName).andDeletedEqualTo(0).andCustomerIdEqualTo(customerId);
        return rdmsProjectMapper.countByExample(projectInfoExample);
    }

    /**
     * 保存
     */
    public String save(RdmsProject projectInfo) {
        if(projectInfo.getCreateTime()==null){
            projectInfo.setCreateTime(new Date());
        }
        projectInfo.setDeleted(0);
        if(projectInfo.getId() == null || projectInfo.getId().equals("")){
            return this.insert(projectInfo);
        }else{
            RdmsProject rdmsProject = rdmsProjectMapper.selectByPrimaryKey(projectInfo.getId());
            if(! ObjectUtils.isEmpty(rdmsProject)){
                return this.update(projectInfo);
            }else{
                return this.insert(projectInfo); // 用户前端页面给出projectID的情况
            }
        }
    }

    /**
     * 新增
     */
    private String insert(RdmsProject projectInfo) {
        if(ObjectUtils.isEmpty(projectInfo.getId())){  //当前端页面给出projectID时,将不为空
            projectInfo.setId(UuidUtil.getShortUuid());
        }
        projectInfo.setCreateTime(new Date());
        RdmsProject rdmsProject = rdmsProjectMapper.selectByPrimaryKey(projectInfo.getId());
        if(! ObjectUtils.isEmpty(rdmsProject)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            rdmsProjectMapper.insert(projectInfo);
            return projectInfo.getId();
        }
    }

    /**
     * 更新
     */
    public String update(RdmsProject projectInfo) {
        RdmsProject rdmsProject = rdmsProjectMapper.selectByPrimaryKey(projectInfo.getId());
        if(! ObjectUtils.isEmpty(rdmsProject)){
            projectInfo.setDeleted(0);
            rdmsProjectMapper.updateByPrimaryKey(projectInfo);
            return projectInfo.getId();
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

    /**
     * 更新
     */
    public String updateByPrimaryKeySelective(RdmsProject projectInfo) {
        rdmsProjectMapper.updateByPrimaryKeySelective(projectInfo);
        return projectInfo.getId();
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsProject rdmsProject = rdmsProjectMapper.selectByPrimaryKey(id);
        if(! ObjectUtils.isEmpty(rdmsProject)){
            rdmsProject.setDeleted(1);
            rdmsProjectMapper.updateByPrimaryKey(rdmsProject);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

}
