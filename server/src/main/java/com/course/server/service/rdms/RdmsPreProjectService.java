/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.course.server.domain.*;
import com.course.server.dto.PageDto;
import com.course.server.dto.rdms.*;
import com.course.server.enums.rdms.*;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsPreProjectMapper;
import com.course.server.service.util.CodeUtil;
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

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RdmsPreProjectService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsPreProjectService.class);

    @Resource
    private RdmsPreProjectMapper rdmsProjectPrepareMapper;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private RdmsCharacterService rdmsCharacterService;
    @Resource
    private RdmsJobItemService rdmsJobItemService;
    @Resource
    private RdmsDemandService rdmsDemandService;
    @Autowired
    private RdmsMaterialManageService rdmsMaterialManageService;
    @Autowired
    private RdmsFeeManageService rdmsFeeManageService;
    @Autowired
    private RdmsSysgmService rdmsSysgmService;

    public RdmsPreProject selectByPrimaryKey(String id){
        return rdmsProjectPrepareMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据customerId和actor 进行列表查询
     * @param pageDto 参数: customerId, actor
     */
    public void list(PageDto<RdmsPreProjectDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsPreProjectExample rdmsPreProjectExample = new RdmsPreProjectExample();
        rdmsPreProjectExample.setOrderByClause("create_time desc");
        rdmsPreProjectExample.createCriteria()
                .andCustomerIdEqualTo(pageDto.getKeyWord())
                .andProductManagerIdEqualTo(pageDto.getActor()) //当前用户是产品经理
                .andDeletedEqualTo(0);
        List<RdmsPreProject> rdmsProjectPrepares = rdmsProjectPrepareMapper.selectByExample(rdmsPreProjectExample);

        PageInfo<RdmsPreProject> pageInfo = new PageInfo<>(rdmsProjectPrepares);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsPreProjectDto> rdmsProjectPrepareDtos = CopyUtil.copyList(rdmsProjectPrepares, RdmsPreProjectDto.class);
        //检查是否选择了系统工程师, 如果没有则将产品经理Id写入系统工程师Id
        for(RdmsPreProjectDto projectPrepareDto : rdmsProjectPrepareDtos){
            String systemManagerId = projectPrepareDto.getSystemManagerId();
            if(systemManagerId == null){
                projectPrepareDto.setSystemManagerId(projectPrepareDto.getProductManagerId());
                this.update(projectPrepareDto);
            }
        }

        //对日期时间进行格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(RdmsPreProjectDto projectPrepareDto : rdmsProjectPrepareDtos){
            projectPrepareDto.setCreateTimeStr(sdf.format(projectPrepareDto.getCreateTime()));

            String productManagerId = projectPrepareDto.getProductManagerId();
            if(productManagerId != null){
                RdmsCustomerUser productManager = rdmsCustomerUserService.selectByPrimaryKey(productManagerId);
                projectPrepareDto.setProductManagerName(productManager.getTrueName());
            }

            String systemManagerId = projectPrepareDto.getSystemManagerId();
            if(systemManagerId != null){
                RdmsCustomerUser systemManager = rdmsCustomerUserService.selectByPrimaryKey(systemManagerId);
                projectPrepareDto.setSystemManagerName(systemManager.getTrueName());
            }

            //编辑中和待签审状态的组件定义是否有
            long examineItemsNum = rdmsJobItemService.getExamineItemsNum(projectPrepareDto.getCustomerId(), projectPrepareDto.getId(), pageDto.getActor());
            long evaluateItemsNum = rdmsJobItemService.getEvaluateItemsNum(projectPrepareDto.getCustomerId(), projectPrepareDto.getId(), pageDto.getActor());
            long refuseItemsNum = rdmsJobItemService.getRefuseItemsNum(projectPrepareDto.getCustomerId(), projectPrepareDto.getId(), pageDto.getActor());

            if(examineItemsNum >0 || evaluateItemsNum >0 || refuseItemsNum>0 ){
                projectPrepareDto.setHandlingItems(examineItemsNum + evaluateItemsNum + refuseItemsNum);
            }else{
                projectPrepareDto.setHandlingItems((long)0);
            }

            long demandHandlingNum = rdmsDemandService.getDemandHandlingNum(projectPrepareDto.getCustomerId(), projectPrepareDto.getId(), pageDto.getActor());
            if(demandHandlingNum > 0){
                projectPrepareDto.setDemandHandlingItems(demandHandlingNum);
            }else {
                projectPrepareDto.setDemandHandlingItems((long)0);
            }
            List<CharacterStatusEnum> characterStatusEnumList = new ArrayList<>();
            characterStatusEnumList.add(CharacterStatusEnum.UPDATE);

            List<JobItemTypeEnum> jobItemTypeEnumList = new ArrayList<>();
            jobItemTypeEnumList.add(JobItemTypeEnum.UPDATE);
            List<RdmsCharacterDto> characterList = rdmsCharacterService.getCharacterListByPreProjectIdAndStatusTypeList(projectPrepareDto.getId(), characterStatusEnumList, jobItemTypeEnumList);
            projectPrepareDto.setUpdateItems((long)characterList.size());
        }
        //如果projectCode空白,则添加
        if(!CollectionUtils.isEmpty(rdmsProjectPrepareDtos)){
            for(RdmsPreProject rdmsPreProject : rdmsProjectPrepareDtos){
                if(rdmsPreProject.getProjectCode() == null){
                    rdmsPreProject.setProjectCode(CodeUtil.randomProjectCode());
                }
            }
        }

        pageDto.setList(rdmsProjectPrepareDtos);
    }

    @Transactional
    public RdmsHmiProjectAndJobItemMixDto getPreProjectAndTaskJobitems_submit(String preProjectId) {
        RdmsHmiProjectAndJobItemMixDto projectAndJobItemMixDto = new RdmsHmiProjectAndJobItemMixDto();
        RdmsPreProject rdmsPreProject = this.selectByPrimaryKey(preProjectId);
        if(! ObjectUtils.isEmpty(rdmsPreProject)){
            projectAndJobItemMixDto.setId(rdmsPreProject.getId());
            projectAndJobItemMixDto.setItemName(rdmsPreProject.getPreProjectName());
            projectAndJobItemMixDto.setStatus(null);
            projectAndJobItemMixDto.setParentOrChildren(ParentOrChildrenEnum.PARENT.getKey());
            projectAndJobItemMixDto.setDocType(DocTypeEnum.PRE_PROJECT.getType());
            projectAndJobItemMixDto.setHasIntJobitem(false);
            int index = 1;

            List<JobItemTypeEnum> typeEnumList = new ArrayList<>();
            typeEnumList.add(JobItemTypeEnum.TASK_FUNCTION);

            List<JobItemStatusEnum> statusEnumList = new ArrayList<>();
            statusEnumList.add(JobItemStatusEnum.SUBMIT);

            List<RdmsJobItemDto> jobitemList = rdmsJobItemService.getJobitemListByPreProjectId(preProjectId, statusEnumList, typeEnumList);

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

                    jobItemMix.setIndex(index++);
                    jobItemMixList.add(jobItemMix);
                }
                projectAndJobItemMixDto.setChildren(jobItemMixList);
            }
        }

        return projectAndJobItemMixDto;
    }


    public Integer getCountOfPreProjectByPdm(String pdmId) {
        RdmsPreProjectExample rdmsPreProjectExample = new RdmsPreProjectExample();
        rdmsPreProjectExample.createCriteria()
                .andProductManagerIdEqualTo(pdmId)
                .andDeletedEqualTo(0);
        return (Integer) (int)rdmsProjectPrepareMapper.countByExample(rdmsPreProjectExample);
    }

    public List<RdmsPreProjectDto> getPreProjectListBySysgm(String customerId) {
        RdmsPreProjectExample rdmsPreProjectExample = new RdmsPreProjectExample();
        rdmsPreProjectExample.setOrderByClause("create_time desc");
        rdmsPreProjectExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andDeletedEqualTo(0);
        List<RdmsPreProject> rdmsPreProjects = rdmsProjectPrepareMapper.selectByExample(rdmsPreProjectExample);
        //如果projectCode空白,则添加
        if(!CollectionUtils.isEmpty(rdmsPreProjects)){
            for(RdmsPreProject rdmsPreProject : rdmsPreProjects){
                if(rdmsPreProject.getProjectCode() == null){
                    rdmsPreProject.setProjectCode(CodeUtil.randomProjectCode());
                    rdmsProjectPrepareMapper.updateByPrimaryKey(rdmsPreProject);
                }
            }
        }

        List<RdmsPreProjectDto> rdmsPreProjectDtos = CopyUtil.copyList(rdmsPreProjects, RdmsPreProjectDto.class);
        if(!CollectionUtils.isEmpty(rdmsPreProjectDtos)) {
            //对日期时间进行格式化
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (RdmsPreProjectDto preProject : rdmsPreProjectDtos) {
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(preProject.getProductManagerId());
                preProject.setProductManagerName(rdmsCustomerUser.getTrueName());

                List<JobItemStatusEnum> jobItemStatusList = new ArrayList<>();
                jobItemStatusList.add(JobItemStatusEnum.HANDLING);
                List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
                jobitemTypeList.add(JobItemTypeEnum.DEMAND);
                int handlingNum = rdmsJobItemService.getJobitemListByPreProjectIdAndStatusListTypeList(preProject.getId(), jobItemStatusList, jobitemTypeList).size();
                preProject.setHandlingJobNum(handlingNum);

                jobItemStatusList.clear();
                jobItemStatusList.add(JobItemStatusEnum.SUBMIT);
                List<JobItemTypeEnum> jobitemTypeList1 = new ArrayList<>();
                jobitemTypeList1.add(JobItemTypeEnum.DEMAND);
                jobitemTypeList1.add(JobItemTypeEnum.ITERATION);
                jobitemTypeList1.add(JobItemTypeEnum.DECOMPOSE);
                jobitemTypeList1.add(JobItemTypeEnum.MERGE);
                int submitNum = rdmsJobItemService.getJobitemListByPreProjectIdAndStatusListTypeList(preProject.getId(), jobItemStatusList, jobitemTypeList1).size();
                preProject.setSubmitJobNum(submitNum);
            }
        }

        return rdmsPreProjectDtos;

    }

    public List<RdmsPreProjectDto> getPreProjectListByPdm(String pdmId, String customerId) {
        RdmsPreProjectExample rdmsPreProjectExample = new RdmsPreProjectExample();
        rdmsPreProjectExample.setOrderByClause("create_time desc");
        rdmsPreProjectExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andProductManagerIdEqualTo(pdmId)
                .andDeletedEqualTo(0);
        List<RdmsPreProject> rdmsPreProjects = rdmsProjectPrepareMapper.selectByExample(rdmsPreProjectExample);
        //如果projectCode空白,则添加
        if(!CollectionUtils.isEmpty(rdmsPreProjects)){
            for(RdmsPreProject rdmsPreProject : rdmsPreProjects){
                if(rdmsPreProject.getProjectCode() == null){
                    rdmsPreProject.setProjectCode(CodeUtil.randomProjectCode());
                    rdmsProjectPrepareMapper.updateByPrimaryKey(rdmsPreProject);
                }
            }
        }
        List<RdmsPreProjectDto> rdmsPreProjectDtos = CopyUtil.copyList(rdmsPreProjects, RdmsPreProjectDto.class);
        return rdmsPreProjectDtos;

    }

    public Integer getMaterialAndFeeApplicationNum(String userId) {
        RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(userId);
        RdmsSysgmDto sysgmByCustomerId = rdmsSysgmService.getSysgmByCustomerId(rdmsCustomerUser.getCustomerId());
        if(!ObjectUtils.isEmpty(sysgmByCustomerId)){
            if(sysgmByCustomerId.getSysgmId().equals(userId)){
                List<RdmsPreProjectDto> preProjectListBySysgm = this.getPreProjectListBySysgm(rdmsCustomerUser.getCustomerId());
                Integer sumApplicationNum = 0;
                if(!CollectionUtils.isEmpty(preProjectListBySysgm)){
                    for(RdmsPreProjectDto rdmsPreProjectDto : preProjectListBySysgm){
                        Integer materialApplicationNum = rdmsMaterialManageService.getApplicationNumByPreProjectId(rdmsPreProjectDto.getId());
                        sumApplicationNum += materialApplicationNum;
                        Integer feeApplicationNum = rdmsFeeManageService.getApplicationNumByPreProjectId(rdmsPreProjectDto.getId());
                        sumApplicationNum += feeApplicationNum;
                    }
                }
                return sumApplicationNum;
            }else{
                return 0;
            }
        }else {
            return 0;
        }
    }


    public List<RdmsPreProjectDto> getPreProjectListBySm(String customerId, String smId) {
        RdmsPreProjectExample rdmsPreProjectExample = new RdmsPreProjectExample();
        rdmsPreProjectExample.setOrderByClause("create_time desc");
        rdmsPreProjectExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andSystemManagerIdEqualTo(smId)
                .andDeletedEqualTo(0);
        List<RdmsPreProject> rdmsPreProjects = rdmsProjectPrepareMapper.selectByExample(rdmsPreProjectExample);
        //如果projectCode空白,则添加
        if(!CollectionUtils.isEmpty(rdmsPreProjects)){
            for(RdmsPreProject rdmsPreProject : rdmsPreProjects){
                if(rdmsPreProject.getProjectCode() == null){
                    rdmsPreProject.setProjectCode(CodeUtil.randomProjectCode());
                    rdmsProjectPrepareMapper.updateByPrimaryKey(rdmsPreProject);
                }
            }
        }

        List<RdmsPreProjectDto> rdmsPreProjectDtos = CopyUtil.copyList(rdmsPreProjects, RdmsPreProjectDto.class);
        if(!CollectionUtils.isEmpty(rdmsPreProjectDtos)) {
            //对日期时间进行格式化
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (RdmsPreProjectDto preProject : rdmsPreProjectDtos) {
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(preProject.getProductManagerId());
                preProject.setProductManagerName(rdmsCustomerUser.getTrueName());

                List<JobItemStatusEnum> jobItemStatusList = new ArrayList<>();
                jobItemStatusList.add(JobItemStatusEnum.HANDLING);
                List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
                jobitemTypeList.add(JobItemTypeEnum.DEMAND);
                int handlingNum = rdmsJobItemService.getJobitemListByPreProjectIdAndStatusListTypeList(preProject.getId(), jobItemStatusList, jobitemTypeList).size();
                preProject.setHandlingJobNum(handlingNum);

                jobItemStatusList.clear();
                jobItemStatusList.add(JobItemStatusEnum.SUBMIT);
                List<JobItemTypeEnum> jobitemTypeList1 = new ArrayList<>();
                jobitemTypeList1.add(JobItemTypeEnum.DEMAND);
                jobitemTypeList1.add(JobItemTypeEnum.ITERATION);
                jobitemTypeList1.add(JobItemTypeEnum.DECOMPOSE);
                jobitemTypeList1.add(JobItemTypeEnum.MERGE);
                jobitemTypeList1.add(JobItemTypeEnum.UPDATE);  //从产品管理触发的迭代任务
                int submitNum = rdmsJobItemService.getJobitemListByPreProjectIdAndStatusListTypeList(preProject.getId(), jobItemStatusList, jobitemTypeList1).size();
                preProject.setSubmitJobNum(submitNum);
            }
        }

        return rdmsPreProjectDtos;

    }

    public List<RdmsPreProject> getModulePreProject(String customerId){
        RdmsPreProjectExample rdmsPreProjectExample = new RdmsPreProjectExample();
        rdmsPreProjectExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andRdTypeEqualTo(RdTypeEnum.MODULE.getType())
                .andDeletedEqualTo(0);
        List<RdmsPreProject> rdmsPreProjects = rdmsProjectPrepareMapper.selectByExample(rdmsPreProjectExample);
        return rdmsPreProjects;
    }


    public List<RdmsPreProjectDto> listByCustomerId(String customerId) {
        RdmsPreProjectExample rdmsPreProjectExample = new RdmsPreProjectExample();
        rdmsPreProjectExample.setOrderByClause("create_time desc");
        rdmsPreProjectExample.createCriteria().andCustomerIdEqualTo(customerId).andDeletedEqualTo(0);
        List<RdmsPreProject> rdmsProjectPrepares = rdmsProjectPrepareMapper.selectByExample(rdmsPreProjectExample);

        List<RdmsPreProjectDto> rdmsProjectPrepareDtos = CopyUtil.copyList(rdmsProjectPrepares, RdmsPreProjectDto.class);
        //对日期时间进行格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(RdmsPreProjectDto projectPrepareDto : rdmsProjectPrepareDtos){
            projectPrepareDto.setCreateTimeStr(sdf.format(projectPrepareDto.getCreateTime()));

            String productManagerId = projectPrepareDto.getProductManagerId();
            if(productManagerId != null){
                RdmsCustomerUser productManager = rdmsCustomerUserService.selectByPrimaryKey(productManagerId);
                projectPrepareDto.setProductManagerName(productManager.getTrueName());
            }

        }
        return rdmsProjectPrepareDtos;
    }

    public List<RdmsPreProjectDto> getPreProjectListByPdmId(String pdmId) {
        RdmsPreProjectExample rdmsPreProjectExample = new RdmsPreProjectExample();
        rdmsPreProjectExample.setOrderByClause("create_time desc");
        rdmsPreProjectExample.createCriteria().andProductManagerIdEqualTo(pdmId).andDeletedEqualTo(0);
        List<RdmsPreProject> rdmsProjectPrepares = rdmsProjectPrepareMapper.selectByExample(rdmsPreProjectExample);

        List<RdmsPreProjectDto> rdmsProjectPrepareDtos = CopyUtil.copyList(rdmsProjectPrepares, RdmsPreProjectDto.class);
        //对日期时间进行格式化
        for(RdmsPreProjectDto projectPrepareDto : rdmsProjectPrepareDtos){
            String productManagerId = projectPrepareDto.getProductManagerId();
            if(productManagerId != null){
                RdmsCustomerUser productManager = rdmsCustomerUserService.selectByPrimaryKey(productManagerId);
                projectPrepareDto.setProductManagerName(productManager.getTrueName());
            }
        }
        return rdmsProjectPrepareDtos;
    }

    public RdmsPreProjectDto getPreProjectSimpleInfo(String preProjectId) {
        RdmsPreProject rdmsProjectPrepare = rdmsProjectPrepareMapper.selectByPrimaryKey(preProjectId);

        RdmsPreProjectDto rdmsProjectPrepareDto = CopyUtil.copy(rdmsProjectPrepare, RdmsPreProjectDto.class);
        //对日期时间进行格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        rdmsProjectPrepareDto.setCreateTimeStr(sdf.format(rdmsProjectPrepareDto.getCreateTime()));

        String productManagerId = rdmsProjectPrepareDto.getProductManagerId();
        if(productManagerId != null){
            RdmsCustomerUser productManager = rdmsCustomerUserService.selectByPrimaryKey(productManagerId);
            rdmsProjectPrepareDto.setProductManagerName(productManager.getTrueName());
        }
        return rdmsProjectPrepareDto;
    }

    @Transactional
    public RdmsHmiProjectAndJobItemMixDto getPreProjectAndTaskJobitems(String preProjectId) {

        RdmsHmiProjectAndJobItemMixDto projectAndJobItemMixDto = new RdmsHmiProjectAndJobItemMixDto();
        RdmsPreProject rdmsPreProject = this.selectByPrimaryKey(preProjectId);
        projectAndJobItemMixDto.setId(rdmsPreProject.getId());
        projectAndJobItemMixDto.setItemName(rdmsPreProject.getPreProjectName());
        projectAndJobItemMixDto.setType("项目");
        projectAndJobItemMixDto.setParentOrChildren(ParentOrChildrenEnum.PARENT.getKey());

        List<JobItemTypeEnum> typeEnumList = new ArrayList<>();
        typeEnumList.add(JobItemTypeEnum.TASK_FUNCTION);

        List<JobItemStatusEnum> statusEnumList = new ArrayList<>();
        statusEnumList.add(JobItemStatusEnum.HANDLING);
        statusEnumList.add(JobItemStatusEnum.SUBMIT);

        List<RdmsJobItemDto> jobitemList = rdmsJobItemService.getJobitemListByPreProjectId(rdmsPreProject.getId(), statusEnumList, typeEnumList);

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
     * 保存
     */
    public String save(RdmsPreProject projectPrepare) {
        if(projectPrepare.getCreateTime()==null){
            projectPrepare.setCreateTime(new Date());
        }
        projectPrepare.setDeleted(0);

        if(projectPrepare.getId() == null || projectPrepare.getId().equals("")){
            return this.insert(projectPrepare);
        }else{
            RdmsPreProjectExample projectPrepareExample = new RdmsPreProjectExample();
            projectPrepareExample.createCriteria().andIdEqualTo(projectPrepare.getId());
            List<RdmsPreProject> rdmsProjectPrepares = rdmsProjectPrepareMapper.selectByExample(projectPrepareExample);
            if(!CollectionUtils.isEmpty(rdmsProjectPrepares)){
                return this.update(projectPrepare);
            }else{
                return this.insert(projectPrepare);
            }
        }
    }

    /**
     * 新增
     */
    private String insert(RdmsPreProject projectPrepare) {
        if(org.springframework.util.ObjectUtils.isEmpty(projectPrepare.getId())){  //当前端页面给出projectID时,将不为空
            projectPrepare.setId(UuidUtil.getShortUuid());
        }
        projectPrepare.setCreateTime(new Date());
        RdmsPreProjectExample projectPrepareExample = new RdmsPreProjectExample();
        projectPrepareExample.createCriteria().andIdEqualTo(projectPrepare.getId());
        List<RdmsPreProject> rdmsProjectPrepares = rdmsProjectPrepareMapper.selectByExample(projectPrepareExample);
        if(! CollectionUtils.isEmpty(rdmsProjectPrepares)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            rdmsProjectPrepareMapper.insert(projectPrepare);
            return projectPrepare.getId();
        }
    }

    /**
     * 更新
     */
    private String update(RdmsPreProject projectPrepare) {
        RdmsPreProjectExample projectPrepareExample = new RdmsPreProjectExample();
        projectPrepareExample.createCriteria().andIdEqualTo(projectPrepare.getId());
        List<RdmsPreProject> rdmsProjectPrepares = rdmsProjectPrepareMapper.selectByExample(projectPrepareExample);
        if(! CollectionUtils.isEmpty(rdmsProjectPrepares)){
            rdmsProjectPrepareMapper.updateByPrimaryKey(projectPrepare);
            return projectPrepare.getId();
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsPreProjectExample projectPrepareExample = new RdmsPreProjectExample();
        projectPrepareExample.createCriteria().andIdEqualTo(id);
        List<RdmsPreProject> rdmsProjectPrepares = rdmsProjectPrepareMapper.selectByExample(projectPrepareExample);
        if(rdmsProjectPrepares.size()>0){
            RdmsPreProject projectPrepare = rdmsProjectPrepares.get(0);
            projectPrepare.setDeleted(1);
            rdmsProjectPrepareMapper.updateByPrimaryKey(projectPrepare);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

}
