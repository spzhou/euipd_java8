/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.*;
import com.course.server.dto.PageDto;
import com.course.server.dto.rdms.*;
import com.course.server.enums.rdms.*;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsProjectSubprojectMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RdmsSubprojectService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsSubprojectService.class);

    @Resource
    private RdmsProjectService rdmsProjectService;
    @Resource
    private RdmsProjectSubprojectMapper rdmsProjectSubprojectMapper;
    @Resource
    private RdmsProjectManagerService rdmsProjectManagerService;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private RdmsCharacterService rdmsCharacterService;
    @Resource
    private RdmsTreeService rdmsTreeService;
    @Resource
    private RdmsJobItemService rdmsJobItemService;
    @Resource
    private RdmsMilestoneService rdmsMilestoneService;
    @Resource
    private RdmsFileService rdmsFileService;
    @Resource
    private RdmsManhourService rdmsManhourService;
    @Resource
    private RdmsJobItemPropertyService rdmsJobItemPropertyService;
    @Resource
    private RdmsBudgetResearchExeService rdmsBudgetResearchExeService;
    @Resource
    private RdmsFileAuthService rdmsFileAuthService;
    @Resource
    private RdmsBossService rdmsBossService;
    @Autowired
    private RdmsCbbService rdmsCbbService;

    public List<RdmsProjectSubproject> getSubprojectListByIpmt(String customerUserId){
        RdmsProjectSubprojectExample subprojectExample = new RdmsProjectSubprojectExample();
        subprojectExample.createCriteria()
                .andSuperviseEqualTo(customerUserId)
                .andStatusNotEqualTo(ProjectStatusEnum.COMPLETE.getStatus())
                .andStatusNotEqualTo(ProjectStatusEnum.ARCHIVED.getStatus())
                .andStatusNotEqualTo(ProjectStatusEnum.CLOSE.getStatus())
                .andDeletedEqualTo(0);
        List<RdmsProjectSubproject> rdmsProjectSubprojects = rdmsProjectSubprojectMapper.selectByExample(subprojectExample);
        return rdmsProjectSubprojects;
    }

    public List<RdmsProjectSubproject> getSubprojectListByPdm(String customerUserId){
        RdmsProjectSubprojectExample subprojectExample = new RdmsProjectSubprojectExample();
        subprojectExample.createCriteria()
                .andProductManagerIdEqualTo(customerUserId)
                .andStatusNotEqualTo(ProjectStatusEnum.COMPLETE.getStatus())
                .andStatusNotEqualTo(ProjectStatusEnum.ARCHIVED.getStatus())
                .andStatusNotEqualTo(ProjectStatusEnum.CLOSE.getStatus())
                .andDeletedEqualTo(0);
        List<RdmsProjectSubproject> rdmsProjectSubprojects = rdmsProjectSubprojectMapper.selectByExample(subprojectExample);
        return rdmsProjectSubprojects;
    }

    public List<RdmsProjectSubproject> getSubprojectListByPjm(String customerUserId){
        RdmsProjectSubprojectExample subprojectExample = new RdmsProjectSubprojectExample();
        subprojectExample.createCriteria()
                .andProjectManagerIdEqualTo(customerUserId)
                .andStatusNotEqualTo(ProjectStatusEnum.COMPLETE.getStatus())
                .andStatusNotEqualTo(ProjectStatusEnum.ARCHIVED.getStatus())
                .andStatusNotEqualTo(ProjectStatusEnum.CLOSE.getStatus())
                .andDeletedEqualTo(0);
        List<RdmsProjectSubproject> rdmsProjectSubprojects = rdmsProjectSubprojectMapper.selectByExample(subprojectExample);
        return rdmsProjectSubprojects;
    }

    public List<RdmsProjectSubproject> getSubprojectListByPjmIdList(List<String> pjmIdList){
        RdmsProjectSubprojectExample subprojectExample = new RdmsProjectSubprojectExample();
        subprojectExample.createCriteria()
                .andProjectManagerIdIn(pjmIdList)
                .andStatusNotEqualTo(ProjectStatusEnum.COMPLETE.getStatus())
                .andStatusNotEqualTo(ProjectStatusEnum.ARCHIVED.getStatus())
                .andStatusNotEqualTo(ProjectStatusEnum.CLOSE.getStatus())
                .andDeletedEqualTo(0);
        List<RdmsProjectSubproject> rdmsProjectSubprojects = rdmsProjectSubprojectMapper.selectByExample(subprojectExample);
        return rdmsProjectSubprojects;
    }

    public List<RdmsProjectSubproject> getSubprojectListByTargetMilestone(String targetMilestoneId){
        RdmsProjectSubprojectExample subprojectExample = new RdmsProjectSubprojectExample();
        subprojectExample.createCriteria()
                .andTargetMilestoneIdEqualTo(targetMilestoneId)
                .andStatusNotEqualTo(ProjectStatusEnum.COMPLETE.getStatus())
                .andStatusNotEqualTo(ProjectStatusEnum.ARCHIVED.getStatus())
                .andStatusNotEqualTo(ProjectStatusEnum.CLOSE.getStatus())
                .andStatusNotEqualTo(ProjectStatusEnum.OUT_SOURCE.getStatus())
                .andDeletedEqualTo(0);
        List<RdmsProjectSubproject> rdmsProjectSubprojects = rdmsProjectSubprojectMapper.selectByExample(subprojectExample);
        return rdmsProjectSubprojects;
    }

    public List<RdmsProjectSubproject> getSubprojectListByProjectIdList(List<String> projectList){
        RdmsProjectSubprojectExample subprojectExample = new RdmsProjectSubprojectExample();
        subprojectExample.createCriteria()
                .andProjectIdIn(projectList)
                .andDeletedEqualTo(0);
        List<RdmsProjectSubproject> rdmsProjectSubprojects = rdmsProjectSubprojectMapper.selectByExample(subprojectExample);
        return rdmsProjectSubprojects;
    }

    public Boolean getCanBeOutsourcingFlag(String subprojectId){
        // 1. 子项目必须有功能; 2. 所有功能没有进入开发状态; 3. 不是主项目
        // 是否为主项目
        RdmsProjectSubproject subproject = this.selectByPrimaryKey(subprojectId);
        if(subproject.getId().equals(subproject.getProjectId())){
            return false;
        }
        //查看是否有组件定义, 并且所有功能都处于未开发状态
        List<RdmsCharacter> setupedCharacterListBySubprojectId = rdmsCharacterService.getSetupedCharacterListBySubprojectId(subprojectId);
        if(CollectionUtils.isEmpty(setupedCharacterListBySubprojectId)  && setupedCharacterListBySubprojectId.isEmpty()){
            return false;
        }

        //查看是否有组件定义, 并且所有功能都处于未开发状态
        List<RdmsCharacter> afterDevelopingCharacterListBySubprojectId = rdmsCharacterService.getAfterDevelopingCharacterListBySubprojectId(subprojectId);
        if(!CollectionUtils.isEmpty(afterDevelopingCharacterListBySubprojectId)){
            return false;
        }
        return true;
    }


    public List<RdmsProjectSubprojectDto> getSubProjectListByPjm(String pjmId){
        RdmsProjectSubprojectExample subprojectExample = new RdmsProjectSubprojectExample();
        subprojectExample.createCriteria()
                .andProjectManagerIdEqualTo(pjmId)
                .andDeletedEqualTo(0);
        List<RdmsProjectSubproject> subprojectList = rdmsProjectSubprojectMapper.selectByExample(subprojectExample);
        if(! CollectionUtils.isEmpty(subprojectList)){
            return CopyUtil.copyList(subprojectList, RdmsProjectSubprojectDto.class);
        }
        return null;
    }

    /**
    * 获得一个用JSON string组成的list.每个JSON string是一个项目的树
    */
  /*  public RdmsProjectSubprojectDto getProjectTreeListStr(String customerId){
        //构建树根对象,也就是customer根对象
        RdmsProjectSubprojectDto treeRoot = new RdmsProjectSubprojectDto();
        treeRoot.setId(customerId);
        treeRoot.setLabel("根对象");
        treeRoot.setParent(customerId);
        treeRoot.setChildren(null);
        treeRoot.setDeep(null);

        //查出项目的列表
        List<RdmsProjectDto> rdmsProjectInfoDtos = rdmsProjectService.getCompleteProjectListByCustomerId(customerId);
        //第一层循环,遍历公司所有项目
        for(RdmsProjectDto rdmsProject : rdmsProjectInfoDtos){
            //取出第一个项目的子项目列表
            RdmsProjectSubprojectExample subprojectExample = new RdmsProjectSubprojectExample();
            subprojectExample.createCriteria().andProjectIdEqualTo(rdmsProject.getId()).andDeletedEqualTo(0).andTypeIsNull();
            List<RdmsProjectSubproject> subprojectList = rdmsProjectSubprojectMapper.selectByExample(subprojectExample);
            List<RdmsProjectSubproject> rdmsProjectSubprojects = new ArrayList<>();
            for(RdmsProjectSubproject subproject : subprojectList){
                if(subproject.getStatus() == null || (! subproject.getStatus().equals(ProjectStatusEnum.CLOSE.getStatus()))){
                    rdmsProjectSubprojects.add(subproject);
                }
            }
            //创建项目的根节点
            RdmsProjectSubprojectDto projectRoot = new RdmsProjectSubprojectDto();
            projectRoot.setId(rdmsProject.getId());
            projectRoot.setLabel(rdmsProject.getProjectName());
            projectRoot.setChildren(null);
            projectRoot.setDeep(0);
            projectRoot.setParent(rdmsProject.getId()); //根节点的parentTemp是项目自身
            projectRoot.setProjectId(rdmsProject.getId());

            //1. 判断项目是否有子项目
            if(rdmsProjectSubprojects.size()>0){
                //1.1 如果有子项目, 则对子项目进行逆序排序,开始进行分支组装, 然后,将组装好的分支作为子项目,添加到根项目的children中
                //根据deep进行反向排序
                List<RdmsProjectSubproject> sortedProjectSubprojects = rdmsProjectSubprojects.stream().sorted(Comparator.comparingInt(RdmsProjectSubproject::getDeep).reversed()).collect(Collectors.toList());
                List<RdmsProjectSubprojectDto> sortedProjectSubprojectDtos = CopyUtil.copyList(sortedProjectSubprojects, RdmsProjectSubprojectDto.class);

                //创建parentTemp对象
                RdmsProjectSubprojectDto subParent = new RdmsProjectSubprojectDto();
                //循环处理每一个子项目
                for(RdmsProjectSubprojectDto subChildren : sortedProjectSubprojectDtos){
                    //取出一个子项目
                    if(subChildren.getParent().equals(projectRoot.getId())) {
                        //只有一级子项目
                        if(projectRoot.getChildren() == null){
                            List<RdmsProjectSubprojectDto> childrenTemp = new ArrayList<>();
                            childrenTemp.add(subChildren);
                            projectRoot.setChildren(childrenTemp);
                        }else{
                            if(!projectRoot.getChildren().contains(subChildren)){
                                List<RdmsProjectSubprojectDto> childrenTemp = projectRoot.getChildren();
                                childrenTemp.add(subChildren);
                                projectRoot.setChildren(childrenTemp);
                            }
                        }
                    }else {
                        //子节点的父节点不是跟项目,也就是说,存在多级关系
                        do{
                            for(RdmsProjectSubprojectDto parentTemp : sortedProjectSubprojectDtos){
                                if(subChildren.getParent().equals(parentTemp.getId())){
                                    //取出这个父节点的子节点list
                                    List<RdmsProjectSubprojectDto> childrenTemp = parentTemp.getChildren();
                                    //将子节点添加到父节点的childrenTemp中去
                                    if(null == childrenTemp){
                                        List<RdmsProjectSubprojectDto> tempChildren = new ArrayList<>();
                                        tempChildren.add(subChildren);
                                        parentTemp.setChildren(tempChildren);
                                    }else{
                                        if(! childrenTemp.contains(subChildren)){
                                            childrenTemp.add(subChildren);
                                            parentTemp.setChildren(childrenTemp);
                                        }
                                    }
                                    subParent =parentTemp;
                                    break;
                                }
                            }

                            //将父节点作为上一级的子节点,也就是赋值给了子节点变量,然后,将这个父节点作为子节点,进一步查找他的父节点,直到到达根节点
                            subChildren = subParent;
                            //判断是否到达根节点
                        }while ((! subParent.getParent().equals(projectRoot.getId())));
                        //将一个children分支,添加到根节点
                        List<RdmsProjectSubprojectDto> children = projectRoot.getChildren();
                        if(null == children){
                            List<RdmsProjectSubprojectDto> tempChildren = new ArrayList<>();
                            tempChildren.add(subParent);
                            projectRoot.setChildren(tempChildren);
                        }else{
                            if(! children.contains(subParent)){
                                children.add(subParent);
                                projectRoot.setChildren(children);
                            }
                        }
                    }
                }
            }

            //组装树跟节点
            List<RdmsProjectSubprojectDto> children = treeRoot.getChildren();
            if(children == null || children.isEmpty()){
                List<RdmsProjectSubprojectDto> childrenTemp = new ArrayList<>();
                childrenTemp.add(projectRoot);
                treeRoot.setChildren(childrenTemp);
            }else {
                if(!children.contains(projectRoot)){
                    children.add(projectRoot);
                    treeRoot.setChildren(children);
                }
            }
        }
        return treeRoot;
    }
*/
    /**
     * 通过customerID和projectID组合来查询特定project的子项目树
    * 获得一个用JSON string组成的list.每个JSON string是一个项目的树
    */
    public RdmsProjectSubprojectDto getProjectTreeListStr(String customerId, String projectId){
        //构建树根对象,也就是customer根对象
        RdmsProjectSubprojectDto treeRoot = new RdmsProjectSubprojectDto();
        treeRoot.setId(customerId);
        treeRoot.setLabel("根对象");
        treeRoot.setParent(customerId);
        treeRoot.setChildren(null);
        treeRoot.setDeep(null);

        //查出项目
        RdmsProject rdmsProject = rdmsProjectService.findProjectById(projectId);
        {
            //取出第一个项目的子项目列表
            RdmsProjectSubprojectExample subprojectExample = new RdmsProjectSubprojectExample();
            subprojectExample.createCriteria().andProjectIdEqualTo(rdmsProject.getId()).andDeletedEqualTo(0).andTypeIsNull();
            List<RdmsProjectSubproject> subprojectList = rdmsProjectSubprojectMapper.selectByExample(subprojectExample);
            List<RdmsProjectSubproject> rdmsProjectSubprojects = new ArrayList<>();
            for(RdmsProjectSubproject subproject : subprojectList){
                if(subproject.getStatus() == null || (! subproject.getStatus().equals(ProjectStatusEnum.CLOSE.getStatus()))){
                    rdmsProjectSubprojects.add(subproject);
                }
            }
            //创建项目的根节点
            RdmsProjectSubprojectDto projectRoot = new RdmsProjectSubprojectDto();
            projectRoot.setId(rdmsProject.getId());
            projectRoot.setLabel(rdmsProject.getProjectName());
            projectRoot.setChildren(null);
            projectRoot.setDeep(0);
            projectRoot.setParent(rdmsProject.getId()); //根节点的parentTemp是项目自身
            projectRoot.setProjectId(rdmsProject.getId());

            //1. 判断项目是否有子项目
            if(!rdmsProjectSubprojects.isEmpty()){
                //1.1 如果有子项目, 则对子项目进行逆序排序,开始进行分支组装, 然后,将组装好的分支作为子项目,添加到根项目的children中
                //根据deep进行反向排序
                List<RdmsProjectSubproject> sortedProjectSubprojects = rdmsProjectSubprojects.stream().sorted(Comparator.comparingInt(RdmsProjectSubproject::getDeep).reversed()).collect(Collectors.toList());
                List<RdmsProjectSubprojectDto> sortedProjectSubprojectDtos = CopyUtil.copyList(sortedProjectSubprojects, RdmsProjectSubprojectDto.class);

                //创建parentTemp对象
                RdmsProjectSubprojectDto subParent = new RdmsProjectSubprojectDto();
                //循环处理每一个子项目
                for(RdmsProjectSubprojectDto subChildren : sortedProjectSubprojectDtos){
                    //取出一个子项目
                    if(subChildren.getParent().equals(projectRoot.getId())) {
                        //只有一级子项目
                        if(projectRoot.getChildren() == null){
                            List<RdmsProjectSubprojectDto> childrenTemp = new ArrayList<>();
                            childrenTemp.add(subChildren);
                            projectRoot.setChildren(childrenTemp);
                        }else{
                            if(!projectRoot.getChildren().contains(subChildren)){
                                List<RdmsProjectSubprojectDto> childrenTemp = projectRoot.getChildren();
                                childrenTemp.add(subChildren);
                                projectRoot.setChildren(childrenTemp);
                            }
                        }
                    }else {
                        //子节点的父节点不是跟项目,也就是说,存在多级关系
                        do{
                            for(RdmsProjectSubprojectDto parentTemp : sortedProjectSubprojectDtos){
                                if(subChildren.getParent().equals(parentTemp.getId())){
                                    //取出这个父节点的子节点list
                                    List<RdmsProjectSubprojectDto> childrenTemp = parentTemp.getChildren();
                                    //将子节点添加到父节点的childrenTemp中去
                                    if(null == childrenTemp){
                                        List<RdmsProjectSubprojectDto> tempChildren = new ArrayList<>();
                                        tempChildren.add(subChildren);
                                        parentTemp.setChildren(tempChildren);
                                    }else{
                                        if(! childrenTemp.contains(subChildren)){
                                            childrenTemp.add(subChildren);
                                            parentTemp.setChildren(childrenTemp);
                                        }
                                    }
                                    subParent =parentTemp;
                                    break;
                                }
                            }

                            //将父节点作为上一级的子节点,也就是赋值给了子节点变量,然后,将这个父节点作为子节点,进一步查找他的父节点,直到到达根节点
                            subChildren = subParent;
                            //判断是否到达根节点
                        }while ((! subParent.getParent().equals(projectRoot.getId())));
                        //将一个children分支,添加到根节点
                        List<RdmsProjectSubprojectDto> children = projectRoot.getChildren();
                        if(null == children){
                            List<RdmsProjectSubprojectDto> tempChildren = new ArrayList<>();
                            tempChildren.add(subParent);
                            projectRoot.setChildren(tempChildren);
                        }else{
                            if(! children.contains(subParent)){
                                children.add(subParent);
                                projectRoot.setChildren(children);
                            }
                        }
                    }
                }
            }

            //组装树跟节点
            List<RdmsProjectSubprojectDto> children = treeRoot.getChildren();
            if(children == null || children.isEmpty()){
                List<RdmsProjectSubprojectDto> childrenTemp = new ArrayList<>();
                childrenTemp.add(projectRoot);
                treeRoot.setChildren(childrenTemp);
            }else {
                if(!children.contains(projectRoot)){
                    children.add(projectRoot);
                    treeRoot.setChildren(children);
                }
            }
        }
        return treeRoot;
    }

    @Transactional
    public List<RdmsProjectSubprojectDto> getTakePartInSubprojectList(String customerUserId){
        List<RdmsProjectSubprojectDto> subprojectDtos = new ArrayList<>();
        List<RdmsJobItemDto> jobitemList = rdmsJobItemService.getJobitemListByExecutorId(customerUserId);
        if(!CollectionUtils.isEmpty(jobitemList)){
            List<RdmsJobItemDto> jobitemList_notNull = jobitemList.stream().filter(item -> !ObjectUtils.isEmpty(item.getSubprojectId())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(jobitemList_notNull)){
                List<String> stringList = jobitemList_notNull.stream().map(RdmsJobItemDto::getSubprojectId).distinct().collect(Collectors.toList());
                if(!ObjectUtils.isEmpty(stringList)){
                    List<RdmsProjectSubproject> subprojectByIdList = this.getSubprojectByIdList(stringList);
                    if(!CollectionUtils.isEmpty(subprojectByIdList)){
                        List<RdmsProjectSubproject> collect = subprojectByIdList.stream().filter(item -> item.getStatus().equals(ProjectStatusEnum.ONGOING.getStatus())).collect(Collectors.toList());
                        subprojectDtos = CopyUtil.copyList(collect, RdmsProjectSubprojectDto.class);
                        //判断是否包含组件开发项目,如果不包含, 则增加进去
                        if(!CollectionUtils.isEmpty(subprojectDtos)){
                            List<RdmsProjectSubprojectDto> moduleProject = subprojectDtos.stream().filter(item -> item.getRdType().equals(RdTypeEnum.MODULE.getType())).collect(Collectors.toList());
                            if(CollectionUtils.isEmpty(moduleProject)){
                                //添加组件开发项目
                                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(customerUserId);
                                RdmsProjectSubproject moduleSubProject = this.getModuleSubProject(rdmsCustomerUser.getCustomerId());
                                if(!ObjectUtils.isEmpty(moduleSubProject)){
                                    RdmsProjectSubprojectDto projectSubprojectDto = CopyUtil.copy(moduleSubProject, RdmsProjectSubprojectDto.class);
                                    subprojectDtos.add(projectSubprojectDto);
                                }
                            }
                        }
//                        return subprojectDtos;
                    }
                }
            }
        }
        RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(customerUserId);
        List<RdmsProjectSubproject> subprojectListByCustomerIdAndRdType = this.getSubprojectListByCustomerIdAndRdType(rdmsCustomerUser.getCustomerId(), RdTypeEnum.MODULE.getType());
        List<RdmsProjectSubprojectDto> subprojectDtos_modules = CopyUtil.copyList(subprojectListByCustomerIdAndRdType, RdmsProjectSubprojectDto.class);

        List<String> subprojectIdList = subprojectDtos.stream().map(RdmsProjectSubprojectDto::getId).collect(Collectors.toList());
        List<RdmsProjectSubprojectDto> collect = subprojectDtos_modules.stream().filter(item -> !subprojectIdList.contains(item.getId())).collect(Collectors.toList());

        subprojectDtos.addAll(collect);

        return subprojectDtos;
    }
    public List<RdmsProjectSubproject> getSubprojectByIdList(List<String> subprojectIdList){
        RdmsProjectSubprojectExample subprojectExample = new RdmsProjectSubprojectExample();
        subprojectExample.createCriteria()
                .andIdIn(subprojectIdList)
                .andDeletedEqualTo(0);
        List<RdmsProjectSubproject> rdmsProjectSubprojects = rdmsProjectSubprojectMapper.selectByExample(subprojectExample);
        return rdmsProjectSubprojects;
    }



    public List<RdmsProjectSubprojectDto> getSubprojectListByTM(String customerUserId){
        RdmsProjectSubprojectExample subprojectExample = new RdmsProjectSubprojectExample();
        subprojectExample.createCriteria().andTestManagerIdEqualTo(customerUserId).andDeletedEqualTo(0);
        List<RdmsProjectSubproject> rdmsProjectSubprojects = rdmsProjectSubprojectMapper.selectByExample(subprojectExample);
        List<RdmsProjectSubprojectDto> subprojectDtos = CopyUtil.copyList(rdmsProjectSubprojects, RdmsProjectSubprojectDto.class);
        return subprojectDtos;
    }

    public List<RdmsHmiTree<RdmsHmiTreeInfoDto>> getCompleteProjectTreeListByCustomerId(String customerId){
        List<RdmsProjectDto> projectList = rdmsProjectService.getCompleteProjectListByCustomerId(customerId);

        List<RdmsHmiTree<RdmsHmiTreeInfoDto>> projectTreeList = new ArrayList<>();
        if(! CollectionUtils.isEmpty(projectList)){
            for(RdmsProjectDto projectDto: projectList){
                List<RdmsHmiTree<RdmsHmiTreeInfoDto>> projectTree = this.getProjectTree(projectDto.getId());
                if(!CollectionUtils.isEmpty(projectTree) && !ObjectUtils.isEmpty(projectTree.get(0))){
                    projectTreeList.add(projectTree.get(0));
                }
            }
        }
        return projectTreeList;
    }

    public List<RdmsHmiTree<RdmsHmiTreeInfoDto>> getOnGoingProjectTreeListByCustomerId(String customerId){
        List<RdmsProjectDto> projectList = rdmsProjectService.getOnGoingProjectListByCustomerId(customerId);

        List<RdmsHmiTree<RdmsHmiTreeInfoDto>> projectTreeList = new ArrayList<>();
        if(! CollectionUtils.isEmpty(projectList)){
            for(RdmsProjectDto projectDto: projectList){
                List<RdmsHmiTree<RdmsHmiTreeInfoDto>> projectTree = this.getProjectTree(projectDto.getId());
                if(!CollectionUtils.isEmpty(projectTree) && !ObjectUtils.isEmpty(projectTree.get(0))){
                    projectTreeList.add(projectTree.get(0));
                }
            }
        }
        return projectTreeList;
    }

    public List<RdmsHmiTree<RdmsHmiTreeInfoDto>> getProjectTree(String projectId){
        //查出项目
        RdmsProject rdmsProject = rdmsProjectService.findProjectById(projectId);
        //取出第一个项目的子项目列表


        RdmsProjectSubprojectExample subprojectExample = new RdmsProjectSubprojectExample();
        subprojectExample.createCriteria()
                .andProjectIdEqualTo(rdmsProject.getId())
                .andDeletedEqualTo(0)
                .andTypeEqualTo(ProjectTypeEnum.SUB_PROJECT.getType());
        List<RdmsProjectSubproject> subprojectList = rdmsProjectSubprojectMapper.selectByExample(subprojectExample);
        List<RdmsHmiTree<RdmsHmiTreeInfoDto>> rdmsHmiTrees = new ArrayList<>();
        for(RdmsProjectSubproject subproject : subprojectList){
            RdmsHmiTreeInfoDto treeInfoDto = new RdmsHmiTreeInfoDto();
            treeInfoDto.setCreatorId(subproject.getCreatorId());
            treeInfoDto.setStatus(subproject.getStatus());
            treeInfoDto.setCode(subproject.getProjectCode());

            if(subproject.getStatus() == null || (! subproject.getStatus().equals(ProjectStatusEnum.CLOSE.getStatus()))){
                RdmsHmiTree<RdmsHmiTreeInfoDto> hmiTree = new RdmsHmiTree<>();
                hmiTree.setObj(treeInfoDto);
                hmiTree.setParent(subproject.getParent());
                hmiTree.setValue(subproject.getId());
                hmiTree.setLabel(subproject.getLabel());
                hmiTree.setDeep(subproject.getDeep());
                hmiTree.setId(subproject.getId());
                rdmsHmiTrees.add(hmiTree);
            }
        }

        //完善TreeRoot信息, 这里是项目信息.  如果是子项目, 就是子项信息
        RdmsHmiTree<RdmsHmiTreeInfoDto> tree = rdmsTreeService.getTree(rdmsHmiTrees);
        tree.setId(rdmsProject.getId());
        tree.setLabel(rdmsProject.getProjectName());
        tree.setDeep(0);
        tree.setParent(rdmsProject.getId()); //根节点的parentTemp是项目自身

        List<RdmsHmiTree<RdmsHmiTreeInfoDto>> treeList = new ArrayList<>();
        treeList.add(tree);
        return treeList;
    }

    public void appendSubProjectInfo(RdmsProjectSubproject subProjectInfo, RdmsProjectSubprojectDto projectSubprojectDto, String loginUserId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(subProjectInfo.getReleaseTime() != null){
            projectSubprojectDto.setReleaseTimeStr(sdf.format(subProjectInfo.getReleaseTime()));
        }
        RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(subProjectInfo.getProjectManagerId());
        projectSubprojectDto.setProjectManagerName(rdmsCustomerUser.getTrueName());

        RdmsCustomerUser productManager = rdmsCustomerUserService.selectByPrimaryKey(subProjectInfo.getProductManagerId());
        projectSubprojectDto.setProductManagerName(productManager.getTrueName());

        RdmsCustomerUser supervise = rdmsCustomerUserService.selectByPrimaryKey(subProjectInfo.getSupervise());
        projectSubprojectDto.setSuperviseName(supervise.getTrueName());

        RdmsBossDto bossByCustomerId = rdmsBossService.getBossByCustomerId(subProjectInfo.getCustomerId());
        if(!ObjectUtils.isEmpty(bossByCustomerId)){
            projectSubprojectDto.setBossId(bossByCustomerId.getBossId());
            RdmsCustomerUser rdmsCustomerUser1 = rdmsCustomerUserService.selectByPrimaryKey(bossByCustomerId.getBossId());
            projectSubprojectDto.setBossName(rdmsCustomerUser1.getTrueName());
        }

        if(!ObjectUtils.isEmpty(subProjectInfo.getTestManagerId())){
            RdmsCustomerUser customerUser_test = rdmsCustomerUserService.selectByPrimaryKey(subProjectInfo.getTestManagerId());
            projectSubprojectDto.setTestManagerName(customerUser_test.getTrueName());
        }

        if(!subProjectInfo.getRdType().equals(RdTypeEnum.MODULE.getType())){
            List<RdmsMilestoneDto> milestoneList = this.getSubprojectMilestoneList(subProjectInfo.getId());
            if(!CollectionUtils.isEmpty(milestoneList)){
                projectSubprojectDto.setMilestoneList(milestoneList);
            }
        }

        List<RdmsHmiTree<RdmsHmiTreeInfoDto>> projectTreeList = this.getSubProjectTreeList(subProjectInfo.getId());
        if(! CollectionUtils.isEmpty(projectTreeList)){
            projectSubprojectDto.setProjectTreeList(projectTreeList);
        }

        List<RdmsCustomerUser> customerUsers = rdmsCustomerUserService.listAllWorkersBySubProjectId(subProjectInfo.getId());
        List<RdmsUserOutlineInfoDto> rdmsUserOutlineList = CopyUtil.copyList(customerUsers, RdmsUserOutlineInfoDto.class);
        if(!CollectionUtils.isEmpty(rdmsUserOutlineList)){
            projectSubprojectDto.setWorkerList(rdmsUserOutlineList);
        }

        List<RdmsFileDto> fileDtos = new ArrayList<>();
        if(! ObjectUtils.isEmpty(projectSubprojectDto.getFileListStr()) && projectSubprojectDto.getFileListStr().length()>6){
            List<String> fileIdList = JSON.parseArray(projectSubprojectDto.getFileListStr(), String.class);
            for(String id: fileIdList){
                RdmsFileDto fileDto = rdmsFileService.getFileSimpleRecordInfo(id);
                //读取访问权限
                if(!ObjectUtils.isEmpty(fileDto)){
                    //补充填写文件权限信息
                    RdmsFileAuth byFileId = rdmsFileAuthService.getByFileId(fileDto.getId());
                    if(ObjectUtils.isEmpty(byFileId)){
                        fileDto.setAuthStatus(true);
                    }else{
                        String authIdsStr = byFileId.getAuthIdsStr();
                        if(ObjectUtils.isEmpty(authIdsStr)){
                            fileDto.setAuthStatus(true);
                        }else{
                            List<String> strings = JSON.parseArray(authIdsStr, String.class);
                            if(!ObjectUtils.isEmpty(loginUserId) && strings.contains(loginUserId)){
                                fileDto.setAuthStatus(true);
                            }else{
                                fileDto.setAuthStatus(false);
                            }
                        }
                    }
                }

                fileDtos.add(fileDto);
            }
        }
        projectSubprojectDto.setFileList(fileDtos);

        String moduleIdListStr = subProjectInfo.getModuleIdListStr();
        if(!ObjectUtils.isEmpty(moduleIdListStr)){
            List<String> moduleIdList = JSON.parseArray(moduleIdListStr, String.class);
            if(!CollectionUtils.isEmpty(moduleIdList)){
                List<RdmsCbbDto> cbbListByIdList = rdmsCbbService.getCbbListByIdList(subProjectInfo.getCustomerId(), moduleIdList);
                projectSubprojectDto.setCbbList(cbbListByIdList);
            }
        }
    }

    public List<RdmsHmiTree<RdmsHmiTreeInfoDto>> getSubProjectTreeList(String subprojectId){
        //查出项目
        RdmsProjectSubproject rdmsSubproject = this.selectByPrimaryKey(subprojectId);
        RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(rdmsSubproject.getProjectId());
        //取出第一个项目的子项目列表
        RdmsProjectSubprojectExample subprojectExample = new RdmsProjectSubprojectExample();
        subprojectExample.createCriteria()
                .andProjectIdEqualTo(rdmsProject.getId())
                .andDeletedEqualTo(0);
        List<RdmsProjectSubproject> subprojectList = rdmsProjectSubprojectMapper.selectByExample(subprojectExample);
        List<RdmsHmiTree<RdmsHmiTreeInfoDto>> rdmsHmiTrees = new ArrayList<>();
        for(RdmsProjectSubproject subproject : subprojectList){
            RdmsHmiTreeInfoDto treeInfoDto = new RdmsHmiTreeInfoDto();
            treeInfoDto.setCreatorId(subproject.getCreatorId());
            treeInfoDto.setStatus(subproject.getStatus());
            treeInfoDto.setCode(subproject.getProjectCode());

            if(subproject.getStatus() == null || (! subproject.getStatus().equals(ProjectStatusEnum.CLOSE.getStatus()))){
                RdmsHmiTree<RdmsHmiTreeInfoDto> hmiTree = new RdmsHmiTree<>();
                hmiTree.setObj(treeInfoDto);
                hmiTree.setParent(subproject.getParent());
                hmiTree.setLabel(subproject.getLabel());
                hmiTree.setDeep(subproject.getDeep());
                hmiTree.setId(subproject.getId());
                hmiTree.setCreateTimeStamp((int)subproject.getCreateTime().getTime());
                rdmsHmiTrees.add(hmiTree);
            }
        }

        //子树算法
        RdmsHmiTree<RdmsHmiTreeInfoDto> tree = rdmsTreeService.getSubTree(rdmsHmiTrees, rdmsSubproject.getId(), rdmsSubproject.getDeep());
        List<RdmsHmiTree<RdmsHmiTreeInfoDto>> treeList = new ArrayList<>();
        treeList.add(tree);
        return treeList;
    }

    public List<RdmsProjectSubprojectDto> getNextLevelSubproject(String subprojectId){
        List<RdmsProjectSubproject> subprojectListByParentId = this.getSubprojectListByParentId(subprojectId);
        return CopyUtil.copyList(subprojectListByParentId, RdmsProjectSubprojectDto.class);
    }

    public List<String> getSubProjectIdListIncludeNextLevel(String subprojectId){
        List<String> subprojectIdCollector = new ArrayList<>();
        subprojectIdCollector.add(subprojectId);

        List<String> subprojectIdList = new ArrayList<>();
        subprojectIdList.add(subprojectId);

        List<String> subprojectIds = new ArrayList<>();
        do{
            subprojectIds.clear();
            for(String subpId: subprojectIdList){
                List<RdmsProjectSubproject> subprojectListByParentId = this.getSubprojectListByParentId(subpId);
                List<String> idList = subprojectListByParentId.stream().map(RdmsProjectSubproject::getId).collect(Collectors.toList());
                subprojectIds.addAll(idList);
            }
            subprojectIdCollector.addAll(subprojectIds);
            subprojectIdList.clear();
            subprojectIdList.addAll(subprojectIds);

        }while(! CollectionUtils.isEmpty(subprojectIds));

        List<String> stringList = subprojectIdCollector.stream().distinct().collect(Collectors.toList());

        return stringList;
    }

    public RdmsProjectSubproject selectByPrimaryKey(String id){
        return rdmsProjectSubprojectMapper.selectByPrimaryKey(id);
    }

    public void closeAllSubProject(List<RdmsProjectSubproject> subprojectListByParentId) {
        if(!CollectionUtils.isEmpty(subprojectListByParentId)){
            for(RdmsProjectSubproject projectSubproject: subprojectListByParentId){
                projectSubproject.setStatus(ProjectStatusEnum.CLOSE.getStatus());
                this.updateByPrimaryKeySelective(projectSubproject);

                List<RdmsProjectSubproject> subprojectList = this.getSubprojectListByParentId(projectSubproject.getId());
                closeAllSubProject(subprojectList);
            }
        }
    }

    public List<RdmsProjectSubproject> getSubprojectListByParentId(String parent){
        RdmsProjectSubprojectExample subprojectExample = new RdmsProjectSubprojectExample();
        subprojectExample.createCriteria()
                .andParentEqualTo(parent)
                .andIdNotEqualTo(parent)
                .andDeletedEqualTo(0);
        List<RdmsProjectSubproject> rdmsProjectSubprojects = rdmsProjectSubprojectMapper.selectByExample(subprojectExample);
        return rdmsProjectSubprojects;
    }

    public List<RdmsProjectSubproject> getModuleSubproject(String customerId){
        RdmsProjectSubprojectExample subprojectExample = new RdmsProjectSubprojectExample();
        subprojectExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andRdTypeEqualTo(RdTypeEnum.MODULE.getType())
                .andDeletedEqualTo(0);
        List<RdmsProjectSubproject> rdmsProjectSubprojects = rdmsProjectSubprojectMapper.selectByExample(subprojectExample);
        return rdmsProjectSubprojects;
    }

    public List<RdmsMilestoneDto> getSubprojectMilestoneList(String subprojectId){
        List<RdmsMilestoneDto> milestoneList = new ArrayList<>();
        RdmsProjectSubproject subproject = this.selectByPrimaryKey(subprojectId);
        String targetMilestoneId = subproject.getTargetMilestoneId();
        if(targetMilestoneId != null){
            RdmsMilestoneDto targetMilestoneDto = rdmsMilestoneService.selectByPrimaryKey(targetMilestoneId);
            if(targetMilestoneDto != null && targetMilestoneDto.getReviewTime() != null){
                milestoneList = rdmsMilestoneService.getMilestoneListBetweenStartEndTime(subproject.getProjectId(), new Date(), targetMilestoneDto.getReviewTime());
                if(CollectionUtils.isEmpty(milestoneList)){
                    milestoneList.add(targetMilestoneDto);
                }
            }
        }
        return milestoneList;
    }

    public RdmsProjectSubproject save(RdmsProjectSubproject subproject) {
        if(subproject.getCreateTime()==null){
            subproject.setCreateTime(new Date());
        }
        subproject.setDeleted(0);

        if(ObjectUtils.isEmpty(subproject.getId())){
            return this.insert(subproject);
        }else{
            RdmsProjectSubproject subprojectDb = rdmsProjectSubprojectMapper.selectByPrimaryKey(subproject.getId());
            if(! ObjectUtils.isEmpty(subprojectDb)){
                return this.update(subproject);
            }else{
                return this.insert(subproject);
            }
        }
    }

    public int updateByPrimaryKeySelective(RdmsProjectSubproject subproject) {
        RdmsProjectSubproject subprojectDb = rdmsProjectSubprojectMapper.selectByPrimaryKey(subproject.getId());
        if(ObjectUtils.isEmpty(subprojectDb)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
        return rdmsProjectSubprojectMapper.updateByPrimaryKeySelective(subproject);
    }

    @Transactional
    public RdmsHmiJobItemDocPageDto getLastReviewInfo(String subprojectId, String loginUserId) {
        RdmsProjectSubproject subproject = this.selectByPrimaryKey(subprojectId);
        boolean isProject = false;
        if(subproject.getId().equals(subproject.getParent())){
            isProject = true;
        }
        RdmsHmiJobItemDocPageDto reviewJobInfo = new RdmsHmiJobItemDocPageDto();
        List<JobItemStatusEnum> jobItemStatusList = new ArrayList<>();
        jobItemStatusList.add(JobItemStatusEnum.ARCHIVED);
        List<JobItemTypeEnum> jobitemTypeList =new ArrayList<>();
        jobitemTypeList.add(JobItemTypeEnum.REVIEW_SUBP);
        List<RdmsJobItemDto> jobitemList = rdmsJobItemService.getJobitemListBySubprojectIdAndStatusListTypeList(subprojectId, jobItemStatusList, jobitemTypeList);
        if(!CollectionUtils.isEmpty(jobitemList)){
            RdmsJobItemDto reviewInfo = jobitemList.get(0);
            //private String id;
            reviewJobInfo.setId(reviewInfo.getId());
            //private String jobSerial;
            reviewJobInfo.setJobSerial(reviewInfo.getJobSerial());
            //private String jobName;
            reviewJobInfo.setJobName(reviewInfo.getJobName());
            //private String executorId;
            reviewJobInfo.setExecutorId(reviewInfo.getExecutorId());
            //private String executorName;
            reviewJobInfo.setExecutorName(rdmsCustomerUserService.selectByPrimaryKey(reviewInfo.getExecutorId()).getTrueName());
            //private String nextNodeId;
            if(isProject){
                reviewJobInfo.setNextNodeId(reviewInfo.getProductManagerId());
            }else{
                reviewJobInfo.setNextNodeId(reviewInfo.getProjectManagerId());
            }
            //private String nextNodeName;
            reviewJobInfo.setNextNodeName(rdmsCustomerUserService.selectByPrimaryKey(reviewJobInfo.getNextNodeId()).getTrueName());
            //private String status;
            reviewJobInfo.setStatus(reviewInfo.getStatus());
            //private String type;
            reviewJobInfo.setType(reviewInfo.getType());
            //private String taskDescription;
            reviewJobInfo.setTaskDescription(reviewInfo.getTaskDescription());
            //private Double manhour;
            reviewJobInfo.setManhour(0.0);
            //private List<RdmsFileDto> taskFileList;  //任务附件
            if(! ObjectUtils.isEmpty(reviewInfo.getFileListStr()) && reviewInfo.getFileListStr().length()>6){
                List<RdmsFileDto> fileList = rdmsFileService.getFileListByIdListStr(reviewInfo.getFileListStr(), loginUserId);
                reviewJobInfo.setTaskFileList(fileList);
            }
            //private String evaluateDesc;
            reviewJobInfo.setEvaluateDesc(reviewInfo.getEvaluateDesc());
            //private Integer evaluateScore;
            reviewJobInfo.setEvaluateScore(reviewInfo.getEvaluateScore());
            //private String submitDescription;
            List<RdmsJobItemProperty> jobItemProperty = rdmsJobItemPropertyService.getJobItemPropertyByJobItemId(reviewInfo.getId());
            if(! CollectionUtils.isEmpty(jobItemProperty)){
                reviewJobInfo.setSubmitDescription(jobItemProperty.get(0).getJobDescription());
                //private List<RdmsFileDto> submitFileList;  //提交附件
                if(! ObjectUtils.isEmpty(jobItemProperty.get(0).getFileListStr()) && jobItemProperty.get(0).getFileListStr().length()>6){
                    List<RdmsFileDto> fileList = rdmsFileService.getFileListByIdListStr(jobItemProperty.get(0).getFileListStr(), loginUserId);
                    reviewJobInfo.setSubmitFileList(fileList);
                }
            }
        }
        return reviewJobInfo;
    }

    /**
     * tree搜索, 获得所有树叶 leaf
     * @param subprojectId
     * @return
     */
    @Transactional
    public RdmsHmiJobItemDocPageDto getReviewJobInfo(String subprojectId, String loginUserId) {
        RdmsHmiJobItemDocPageDto reviewJobInfo = new RdmsHmiJobItemDocPageDto();
        RdmsProjectSubproject subproject = this.selectByPrimaryKey(subprojectId);
        if(ObjectUtils.isEmpty(subproject)){
            return reviewJobInfo;
        }
        boolean isProject = false;
        if(subproject.getId().equals(subproject.getParent())){
            isProject = true;
        }
        List<JobItemStatusEnum> jobItemStatusList = new ArrayList<>();
        jobItemStatusList.add(JobItemStatusEnum.ARCHIVED);
        jobItemStatusList.add(JobItemStatusEnum.SUBMIT);
        List<JobItemTypeEnum> jobitemTypeList =new ArrayList<>();
        jobitemTypeList.add(JobItemTypeEnum.REVIEW_SUBP);
        List<RdmsJobItemDto> jobitemList = rdmsJobItemService.getJobitemListBySubprojectIdAndStatusListTypeList(subprojectId, jobItemStatusList, jobitemTypeList);
        if(!CollectionUtils.isEmpty(jobitemList)){
            RdmsJobItemDto reviewInfo = jobitemList.get(0);
            //private String id;
            reviewJobInfo.setId(reviewInfo.getId());
            //private String jobSerial;
            reviewJobInfo.setJobSerial(reviewInfo.getJobSerial());
            //private String jobName;
            reviewJobInfo.setJobName(reviewInfo.getJobName());
            //private String executorId;
            reviewJobInfo.setExecutorId(reviewInfo.getExecutorId());
            //private String executorName;
            reviewJobInfo.setExecutorName(rdmsCustomerUserService.selectByPrimaryKey(reviewInfo.getExecutorId()).getTrueName());
            //private String nextNodeId;
            if(isProject){
                reviewJobInfo.setNextNodeId(reviewInfo.getProductManagerId());
            }else{
                reviewJobInfo.setNextNodeId(reviewInfo.getProjectManagerId());
            }
            //private String nextNodeName;
            reviewJobInfo.setNextNodeName(rdmsCustomerUserService.selectByPrimaryKey(reviewJobInfo.getNextNodeId()).getTrueName());
            //private String status;
            reviewJobInfo.setStatus(reviewInfo.getStatus());
            //private String type;
            reviewJobInfo.setType(reviewInfo.getType());
            //private String taskDescription;
            reviewJobInfo.setTaskDescription(reviewInfo.getTaskDescription());
            //private Double manhour;
            reviewJobInfo.setManhour(0.0);
            //private List<RdmsFileDto> taskFileList;  //任务附件
            if(! ObjectUtils.isEmpty(reviewInfo.getFileListStr()) && reviewInfo.getFileListStr().length()>6){
                List<RdmsFileDto> fileList = rdmsFileService.getFileListByIdListStr(reviewInfo.getFileListStr(), loginUserId);
                reviewJobInfo.setTaskFileList(fileList);
            }
            //private String evaluateDesc;
            reviewJobInfo.setEvaluateDesc(reviewInfo.getEvaluateDesc());
            //private Integer evaluateScore;
            reviewJobInfo.setEvaluateScore(reviewInfo.getEvaluateScore());
            //private String submitDescription;
            List<RdmsJobItemProperty> jobItemProperty = rdmsJobItemPropertyService.getJobItemPropertyByJobItemId(reviewInfo.getId());
            if(! CollectionUtils.isEmpty(jobItemProperty)){
                reviewJobInfo.setSubmitDescription(jobItemProperty.get(0).getJobDescription());
                //private List<RdmsFileDto> submitFileList;  //提交附件
                if(! ObjectUtils.isEmpty(jobItemProperty.get(0).getFileListStr()) && jobItemProperty.get(0).getFileListStr().length()>6){
                    List<RdmsFileDto> fileList = rdmsFileService.getFileListByIdListStr(jobItemProperty.get(0).getFileListStr(), loginUserId);
                    reviewJobInfo.setSubmitFileList(fileList);
                }
            }
        }
        return reviewJobInfo;
    }

    @Transactional
    public List<String> getAllChildrenSubprojectId(String subprojectId) {
        List<String> iterationIdList = new ArrayList<>();
        iterationIdList.add(subprojectId);
        List<String> subprojectIdCollect = new ArrayList<>(iterationIdList);
        do {
            List<String> tempIdList = new ArrayList<>();
            for(String itemId: iterationIdList){
                List<RdmsProjectSubproject> subprojectList = this.getSubprojectListByParentId(itemId);
                List<String> stringList = subprojectList.stream().map(RdmsProjectSubproject::getId).collect(Collectors.toList());
                List<String> remainStringList = new ArrayList<>();
                for(String id : stringList){
                    if(! iterationIdList.contains(id)){
                        remainStringList.add(id);
                    }
                }
                tempIdList.addAll(remainStringList);
            }
            if(CollectionUtils.isEmpty(tempIdList)){
                break;
            }else{
                iterationIdList.clear();
                subprojectIdCollect.addAll(tempIdList);
                iterationIdList = tempIdList;
            }
        }while (true);
        return subprojectIdCollect;
    }

    @Data
    public static class OutsourceParameter{
        private String subprojectId;
        private String outCustomerId;
        private String superviseId;
    }

    //TODO 外包管理模块尚未完成
    @Transactional
    public void saveOutSourcing(OutsourceParameter parameter) {
        //1. 标记当前项目为外包状态
        RdmsProjectSubproject originSubproject = this.selectByPrimaryKey(parameter.getSubprojectId());
        originSubproject.setStatus(ProjectStatusEnum.OUT_SOURCE.getStatus());
        originSubproject.setOutSourceStatus(ProjectStatusEnum.OUT_SOURCE.getStatus());
        this.update(originSubproject);


        //2. 在对方团队, 创建一个申请立项项目
        RdmsProject projectInfo = CopyUtil.copy(originSubproject, RdmsProject.class);
        projectInfo.setId(null);
        projectInfo.setProjectName(originSubproject.getLabel());
        projectInfo.setDevVersion(originSubproject.getDevVersion());
        projectInfo.setSupervise(parameter.getSuperviseId());
        projectInfo.setCustomerId(parameter.getOutCustomerId());
        projectInfo.setStatus(ProjectStatusEnum.SETUP.getStatus()); //申请立项状态
        projectInfo.setRdType(RdTypeEnum.DIRECT.getType());
        projectInfo.setPreProjectId(null);
        projectInfo.setProductManagerId(null);
        projectInfo.setProjectManagerId(null);
        projectInfo.setTestManagerId(null);
        projectInfo.setKeyMemberListStr(null);
        projectInfo.setModuleIdListStr(null);

        String projectId = rdmsProjectService.save(projectInfo);

        //3. 创建功能副本, 放到对方的character表中
        List<RdmsCharacter> setupedCharacterList = rdmsCharacterService.getSetupedCharacterListBySubprojectId(originSubproject.getId());
        if(!CollectionUtils.isEmpty(setupedCharacterList)){
            for(RdmsCharacter originCharacter: setupedCharacterList ){
                //将character保存到外包项目下
                RdmsCharacter copyCharacter = CopyUtil.copy(originCharacter, RdmsCharacter.class);
                copyCharacter.setId(null);
                copyCharacter.setCharacterSerial(null);
                copyCharacter.setDeep(0);
                copyCharacter.setIterationVersion(0);
                copyCharacter.setCustomerId(parameter.getOutCustomerId());
                copyCharacter.setProjectId(projectId);
                copyCharacter.setStatus(CharacterStatusEnum.SETUP.getStatus());
                copyCharacter.setStatus(CharacterStatusEnum.SETUP.getStatus());

                copyCharacter.setPreProjectId(null);
                copyCharacter.setMilestoneId(null);
                copyCharacter.setProductManagerId(null);
                copyCharacter.setNextNode(null);
                copyCharacter.setProjectType(ProjectTypeEnum.PRE_PROJECT.getType());

                String copyCharacterId = rdmsCharacterService.save(copyCharacter);

                //4. 将本项目相应的功能, 状态标记为外包 并 填写外包 outCharacterId
                originCharacter.setStatus(CharacterStatusEnum.OUT_SOURCE.getStatus());
                originCharacter.setOutCharacterId(copyCharacterId);
                rdmsCharacterService.update(originCharacter);
            }
        }
    }

    @Transactional
    public RdmsBoardSubprojectDto getSubprojectBoardInfo_direct(String subprojectId) {
        RdmsBoardSubprojectDto subprojectBoardInfo = new RdmsBoardSubprojectDto();

        RdmsHmiBudgeStatisticsDto budgetExecutionSummary = new RdmsHmiBudgeStatisticsDto();
//        RdmsProjectSubproject subproject = this.selectByPrimaryKey(subprojectId);
        /*if(subproject.getId().equals(subproject.getProjectId())){
            budgetExecutionSummary = rdmsBudgetResearchExeService.getBudgetExecutionSummary_mainProject(subprojectId);
        }else {
            budgetExecutionSummary = rdmsBudgetResearchExeService.getBudgetExecutionSummary(subprojectId);
        }*/
        budgetExecutionSummary = rdmsBudgetResearchExeService.getBudgetExecutionSummary(subprojectId);

        subprojectBoardInfo.setSumBudget(budgetExecutionSummary.getSumBudget());
        subprojectBoardInfo.setActiveBudget(budgetExecutionSummary.getSumActiveBudget());

        subprojectBoardInfo.setSumManhourBudget(budgetExecutionSummary.getSumManhourBudget());
        subprojectBoardInfo.setSumManhourFee(budgetExecutionSummary.getSumActiveManhourBudget());

        subprojectBoardInfo.setSumMaterialBudget(budgetExecutionSummary.getSumMaterialBudget());
        subprojectBoardInfo.setSumMaterialFee(budgetExecutionSummary.getSumActiveMaterialBudget());

        subprojectBoardInfo.setSumEquipmentBudget(budgetExecutionSummary.getSumEquipmentBudget());
        subprojectBoardInfo.setSumEquipmentFee(budgetExecutionSummary.getSumActiveEquipmentBudget());

        subprojectBoardInfo.setSumFeeBudget(budgetExecutionSummary.getSumOtherBudget());
        subprojectBoardInfo.setSumFee(budgetExecutionSummary.getSumActiveOtherBudget());

        //子项目项下工单的总数
        List<JobItemStatusEnum> statusEnumList2 = new ArrayList<>();
        statusEnumList2.add(JobItemStatusEnum.HANDLING);
        statusEnumList2.add(JobItemStatusEnum.SUBMIT);
        statusEnumList2.add(JobItemStatusEnum.TESTING);
        statusEnumList2.add(JobItemStatusEnum.CHA_RECHECK);
        statusEnumList2.add(JobItemStatusEnum.QUA_RECHECK);
        statusEnumList2.add(JobItemStatusEnum.SUB_RECHECK);
        statusEnumList2.add(JobItemStatusEnum.EVALUATE);
        statusEnumList2.add(JobItemStatusEnum.APPROVED);
        statusEnumList2.add(JobItemStatusEnum.COMPLETED);
        statusEnumList2.add(JobItemStatusEnum.ARCHIVED);
        List<RdmsJobItemDto> jobitemListBySubProjectId = rdmsJobItemService.getJobitemListBySubProjectId(subprojectId, statusEnumList2, null);

        subprojectBoardInfo.setSumJobitemNum((long)jobitemListBySubProjectId.size());
        //提交文档列表
        List<String> docIdList = new ArrayList<>();
        List<RdmsCharacterDto> characterList2 = rdmsCharacterService.getCharacterListBySubprojectId(subprojectId, null);
        for(RdmsCharacterDto characterDto: characterList2){
            List<String> strings = JSON.parseArray(characterDto.getFileListStr(), String.class);
            if(!CollectionUtils.isEmpty(strings)){
                docIdList.addAll(strings);
            }
        }
        for(RdmsJobItemDto jobItemDto: jobitemListBySubProjectId){
            List<String> strings = JSON.parseArray(jobItemDto.getFileListStr(), String.class);
            if(!CollectionUtils.isEmpty(strings)){
                docIdList.addAll(strings);
            }
        }
        List<String> jobIdList = jobitemListBySubProjectId.stream().map(RdmsJobItemDto::getId).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(jobIdList)){
            List<RdmsJobItemProperty> propertyList = rdmsJobItemPropertyService.getListByJobitemIdList(jobIdList);
            for(RdmsJobItemProperty property: propertyList){
                List<String> strings = JSON.parseArray(property.getFileListStr(), String.class);
                if(!CollectionUtils.isEmpty(strings)){
                    docIdList.addAll(strings);
                }
            }
        }

        subprojectBoardInfo.setDocIdList(docIdList);
        subprojectBoardInfo.setSubmitDocNum((long)docIdList.size());

        //子项目项下已完工工单数
        List<JobItemStatusEnum> statusEnumList3 = new ArrayList<>();
        statusEnumList3.add(JobItemStatusEnum.COMPLETED);
        statusEnumList3.add(JobItemStatusEnum.ARCHIVED);
        Long jobitemCount3 = rdmsJobItemService.getJobitemCountBySubprojectId(subprojectId, statusEnumList3);
        subprojectBoardInfo.setComplateJobNum(jobitemCount3);

        //子项目项下执行中工单数
        subprojectBoardInfo.setExeJobNum((long)jobitemListBySubProjectId.size() - jobitemCount3);

        //子项目项下逾期工单数
        List<JobItemStatusEnum> statusEnumList4 = new ArrayList<>();
        statusEnumList4.add(JobItemStatusEnum.HANDLING);
        statusEnumList4.add(JobItemStatusEnum.SUBMIT);
        statusEnumList4.add(JobItemStatusEnum.TESTING);
        statusEnumList4.add(JobItemStatusEnum.CHA_RECHECK);
        statusEnumList4.add(JobItemStatusEnum.QUA_RECHECK);
        statusEnumList4.add(JobItemStatusEnum.SUB_RECHECK);
        statusEnumList4.add(JobItemStatusEnum.EVALUATE);
        statusEnumList4.add(JobItemStatusEnum.APPROVED);
        List<RdmsJobItemDto> jobitemList4 = rdmsJobItemService.getJobitemListBySubProjectId(subprojectId, statusEnumList4, null);
        if(!CollectionUtils.isEmpty(jobitemList4)){
            long count = jobitemList4.stream().filter(record -> record.getPlanSubmissionTime() != null && new Date().getTime() > record.getPlanSubmissionTime().getTime()).count();
            subprojectBoardInfo.setOverdueJobNum(count);
        }else {
            subprojectBoardInfo.setOverdueJobNum(0L);
        }

        return subprojectBoardInfo;
    }

    /**
     * 查：
     * 	1. 项目的工单总数
     * 	2. 已经完工的工单数
     * 	3. 功能总数
     * 	4. 已经开发完成功能数
     * 	5. 总预算
     * 	6. 预算消耗 就是项下所有工单的工时
     */
    @Transactional
    public RdmsSubprojectExeSimpleInfoDto getSubprojectExeSimpleInfo(String subprojectId, String loginUserId){
        RdmsSubprojectExeSimpleInfoDto subprojectExeSimpleInfoDto = new RdmsSubprojectExeSimpleInfoDto();
        RdmsProjectSubproject subproject = this.selectByPrimaryKey(subprojectId);
        List<RdmsJobItemDto> allDevJobitemList = new ArrayList<>();
        List<RdmsJobItemDto> alltestJobitemList = new ArrayList<>();
        //private String id;
        subprojectExeSimpleInfoDto.setId(subprojectId);
        //private String projectCode;
        subprojectExeSimpleInfoDto.setProjectCode(subproject.getProjectCode());
        //private String projectName;
        subprojectExeSimpleInfoDto.setProjectName(subproject.getLabel());
        //private String status;
        subprojectExeSimpleInfoDto.setStatus(subproject.getStatus());
        //private Integer jobSumNum;
        {
            List<JobItemStatusEnum> statusEnumList = new ArrayList<>();
            statusEnumList.add(JobItemStatusEnum.HANDLING);
            statusEnumList.add(JobItemStatusEnum.SUBMIT);
            statusEnumList.add(JobItemStatusEnum.TESTING);
            statusEnumList.add(JobItemStatusEnum.CHA_RECHECK);
            statusEnumList.add(JobItemStatusEnum.QUA_RECHECK);
            statusEnumList.add(JobItemStatusEnum.SUB_RECHECK);
            statusEnumList.add(JobItemStatusEnum.EVALUATE);
            statusEnumList.add(JobItemStatusEnum.APPROVED);
            statusEnumList.add(JobItemStatusEnum.COMPLETED);
            statusEnumList.add(JobItemStatusEnum.ARCHIVED);

            List<JobItemTypeEnum> typeEnumList = new ArrayList<>();
            typeEnumList.add(JobItemTypeEnum.DEVELOP);
            typeEnumList.add(JobItemTypeEnum.CHARACTER_INT);
            typeEnumList.add(JobItemTypeEnum.TASK_SUBP);

            allDevJobitemList = rdmsJobItemService.getJobitemListBySubprojectId(subprojectId, statusEnumList, typeEnumList);

            List<JobItemTypeEnum> testTypeEnumList = new ArrayList<>();
            testTypeEnumList.add(JobItemTypeEnum.TEST);

            List<JobItemTypeEnum> notInAuxTypeList = new ArrayList<>();
            notInAuxTypeList.add(JobItemTypeEnum.CHARACTER_INT);
            notInAuxTypeList.add(JobItemTypeEnum.TASK_SUBP);
            alltestJobitemList = rdmsJobItemService.getJobitemListBySubprojectIdAndStatusListTypeListNotInAuxTypeList(subprojectId, statusEnumList, testTypeEnumList, notInAuxTypeList);

            subprojectExeSimpleInfoDto.setJobSumNum(allDevJobitemList.size() + alltestJobitemList.size());
        }
        //private Integer jobComplateNum;
        {
            List<JobItemStatusEnum> statusEnumList = new ArrayList<>();
            statusEnumList.add(JobItemStatusEnum.COMPLETED);
            statusEnumList.add(JobItemStatusEnum.ARCHIVED);

            List<JobItemTypeEnum> typeEnumList = new ArrayList<>();
            typeEnumList.add(JobItemTypeEnum.DEVELOP);
            typeEnumList.add(JobItemTypeEnum.CHARACTER_INT);
            typeEnumList.add(JobItemTypeEnum.TASK_SUBP);
            typeEnumList.add(JobItemTypeEnum.TEST);

            List<JobItemTypeEnum> notInAuxTypeList = new ArrayList<>();
            notInAuxTypeList.add(JobItemTypeEnum.CHARACTER_INT);
            notInAuxTypeList.add(JobItemTypeEnum.TASK_SUBP);
            List<RdmsJobItemDto> jobitemListBySubprojectId = rdmsJobItemService.getJobitemListBySubprojectIdAndStatusListTypeListNotInAuxTypeList(subprojectId, statusEnumList, typeEnumList, notInAuxTypeList);
            subprojectExeSimpleInfoDto.setJobComplateNum(jobitemListBySubprojectId.size());
        }
        //private Integer characterSumNum;
        {
            List<CharacterStatusEnum> statusEnumList = new ArrayList<>();
            statusEnumList.add(CharacterStatusEnum.SETUPED);
            statusEnumList.add(CharacterStatusEnum.DEVELOPING);
            statusEnumList.add(CharacterStatusEnum.INTEGRATION);
            statusEnumList.add(CharacterStatusEnum.DEV_COMPLETE);
            statusEnumList.add(CharacterStatusEnum.REVIEW);
            statusEnumList.add(CharacterStatusEnum.ARCHIVED);
            statusEnumList.add(CharacterStatusEnum.UPDATED);
            statusEnumList.add(CharacterStatusEnum.UPDATED_HISTORY);
            List<RdmsCharacterDto> characterListBySubprojectId = rdmsCharacterService.getCharacterListBySubprojectIdAndStatusList(subprojectId, statusEnumList);
            subprojectExeSimpleInfoDto.setCharacterSumNum(characterListBySubprojectId.size());
        }

        //private Integer characterComplateNum;
        {
            List<CharacterStatusEnum> statusEnumList = new ArrayList<>();
            statusEnumList.add(CharacterStatusEnum.ARCHIVED);
            statusEnumList.add(CharacterStatusEnum.UPDATED);
            statusEnumList.add(CharacterStatusEnum.UPDATED_HISTORY);
            List<RdmsCharacterDto> characterListBySubprojectId = rdmsCharacterService.getCharacterListBySubprojectIdAndStatusList(subprojectId, statusEnumList);
            subprojectExeSimpleInfoDto.setCharacterComplateNum(characterListBySubprojectId.size());
        }

        //private BigDecimal budgetApproved;
        subprojectExeSimpleInfoDto.setBudgetApproved(subproject.getBudget().add(subproject.getAddCharge()));
        //private BigDecimal budgetActive;  //按下发工单计算
        //查询子项目所有功能
        List<RdmsCharacterDto> characterDtos = rdmsCharacterService.listAllCharactersBySubProjectId(subprojectId, loginUserId);
        BigDecimal characterHaveUsedCharge = BigDecimal.ZERO;
        if(!CollectionUtils.isEmpty(characterDtos)){
            characterHaveUsedCharge = characterDtos.stream().map(RdmsCharacterDto::getHaveUsedBudget).reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        List<JobItemStatusEnum> statusEnumList = new ArrayList<>();
        statusEnumList.add(JobItemStatusEnum.COMPLETED);
        statusEnumList.add(JobItemStatusEnum.ARCHIVED);
        statusEnumList.add(JobItemStatusEnum.HANDLING);
        statusEnumList.add(JobItemStatusEnum.SUBMIT);
        statusEnumList.add(JobItemStatusEnum.TESTING);
        statusEnumList.add(JobItemStatusEnum.CHA_RECHECK);
        statusEnumList.add(JobItemStatusEnum.QUA_RECHECK);
        statusEnumList.add(JobItemStatusEnum.EVALUATE);
        statusEnumList.add(JobItemStatusEnum.APPROVED);
        List<JobItemTypeEnum> jobitemTypeList = new ArrayList<>();
        jobitemTypeList.add(JobItemTypeEnum.TASK_SUBP);
        List<RdmsJobItemDto> taskJobitemList = rdmsJobItemService.getJobitemListBySubprojectIdAndStatusListTypeList(subprojectId, statusEnumList, jobitemTypeList);
        //物料费用
        BigDecimal SumTaskJobitemCharge = BigDecimal.ZERO;
        BigDecimal haveUsedFee = BigDecimal.ZERO;
        if(! CollectionUtils.isEmpty(taskJobitemList)){
            for(RdmsJobItemDto jobItemDto: taskJobitemList){
                SumTaskJobitemCharge = SumTaskJobitemCharge.add(rdmsManhourService.calJobitemAllAccountCharge(jobItemDto.getId()));
            }

        }

        subprojectExeSimpleInfoDto.setBudgetActive(characterHaveUsedCharge.add(SumTaskJobitemCharge));
        //private BigDecimal budgetRate;  //预算执行率
//        if(Objects.equals(subprojectExeSimpleInfoDto.getBudgetApproved(), BigDecimal.ZERO)){
        if(subprojectExeSimpleInfoDto.getBudgetApproved().signum() == 0){
            subprojectExeSimpleInfoDto.setBudgetRate(BigDecimal.ZERO);
        }else{
            BigDecimal budgetRate = subprojectExeSimpleInfoDto.getBudgetActive().divide(subprojectExeSimpleInfoDto.getBudgetApproved(), 6, RoundingMode.HALF_UP).setScale(2,RoundingMode.HALF_UP);
            subprojectExeSimpleInfoDto.setBudgetRate(budgetRate);
        }
        return subprojectExeSimpleInfoDto;
    }

    /**
     * 新增
     */
    private RdmsProjectSubproject insert(RdmsProjectSubproject subproject) {
        if(ObjectUtils.isEmpty(subproject.getId())){  //当前端页面给出projectID时,将不为空
            subproject.setId(UuidUtil.getShortUuid());
        }
        RdmsProjectSubproject subprojectDb = rdmsProjectSubprojectMapper.selectByPrimaryKey(subproject.getId());
        if(! ObjectUtils.isEmpty(subprojectDb)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            rdmsProjectSubprojectMapper.insert(subproject);
            return subproject;
        }
    }

    /**
     * 更新
     */
    public RdmsProjectSubproject update(RdmsProjectSubproject subproject) {
        RdmsProjectSubproject subprojectDb = rdmsProjectSubprojectMapper.selectByPrimaryKey(subproject.getId());
        if(! ObjectUtils.isEmpty(subprojectDb)){
            rdmsProjectSubprojectMapper.updateByPrimaryKey(subproject);
            return subproject;
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

    public List<RdmsCustomerUserDto> getProjectManagerList(String customerId){
        List<RdmsCustomerUser> projectManagers = rdmsProjectManagerService.getProjectManagerListByCustomerId(customerId);
        return CopyUtil.copyList(projectManagers, RdmsCustomerUserDto.class);
    }

    public RdmsProjectSubproject getSubProjectInfo(String subProjectId){
        RdmsProjectSubproject subproject = rdmsProjectSubprojectMapper.selectByPrimaryKey(subProjectId);
        if(! ObjectUtils.isEmpty(subproject)){
            return subproject;
        }else{
            return null;
        }
    }

    @Transactional
    public long getCountOfCompleteSubProjectByParent(String subprojectId){
        RdmsProjectSubprojectExample subprojectExample = new RdmsProjectSubprojectExample();
        List<String> statusList = new ArrayList<>();
        statusList.add(ProjectStatusEnum.COMPLETE.getStatus());
        statusList.add(ProjectStatusEnum.CLOSE.getStatus());
        statusList.add(ProjectStatusEnum.ARCHIVED.getStatus());
        subprojectExample.createCriteria()
                .andParentEqualTo(subprojectId)
                .andStatusIn(statusList)
                .andDeletedEqualTo(0);
        return rdmsProjectSubprojectMapper.countByExample(subprojectExample);
    }

    @Transactional
    public long getCountOfSubProjectByParent(String subprojectId){
        RdmsProjectSubprojectExample subprojectExample = new RdmsProjectSubprojectExample();
        subprojectExample.createCriteria()
                .andParentEqualTo(subprojectId)
                .andIdNotEqualTo(subprojectId)  //排除id和parent相同的情况, 这是主项目的特征
                .andDeletedEqualTo(0);
        return rdmsProjectSubprojectMapper.countByExample(subprojectExample);
    }

    public RdmsProjectSubproject getModuleSubProject(String customerId) {
        RdmsProjectSubprojectExample rdmsProjectExample = new RdmsProjectSubprojectExample();
        RdmsProjectSubprojectExample.Criteria criteria = rdmsProjectExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andRdTypeEqualTo(RdTypeEnum.MODULE.getType())
                .andDeletedEqualTo(0);
        List<RdmsProjectSubproject> rdmsProjectSubprojects = rdmsProjectSubprojectMapper.selectByExample(rdmsProjectExample);
        if (!CollectionUtils.isEmpty(rdmsProjectSubprojects)) {
            return rdmsProjectSubprojects.get(0);
        }else{
            return null;
        }
    }

    @Transactional
    public boolean getDevComplateStatusFlag(String subprojectId) {
        boolean jobComplete = false;
        boolean characterComplete = false;
        boolean subProjectComplete = false;
        //项下所有的工单全部完成
        long completeJobNum = rdmsJobItemService.getCountOfCompleteJobitemBySubproject(subprojectId);
        long allJobNum = rdmsJobItemService.getCountOfJobitemBySubproject(subprojectId);
        if(allJobNum == completeJobNum){
            jobComplete = true;
        }

        //项下所有的功能开发全部完成
        long completeCharacter = rdmsCharacterService.getCountOfCompleteCharacterBySubproject(subprojectId);
        long allCharacter = rdmsCharacterService.getCountOfCharacterBySubproject(subprojectId);
        if(allCharacter == completeCharacter){
            characterComplete = true;
        }

        //项下所有的"直接"子项目全部通过评审
        long completeSubproject = this.getCountOfCompleteSubProjectByParent(subprojectId);
        long allSubproject = this.getCountOfSubProjectByParent(subprojectId);
        if(allSubproject == completeSubproject){
            subProjectComplete = true;
        }
        boolean devComplateFlag = jobComplete && characterComplete && subProjectComplete;
        return devComplateFlag;
    }

    @Transactional
    public Integer getNumOfSubProjectByNextNodeAndStatus(String nextNode, List<ProjectStatusEnum> statusEnumList){
        long count1 = 0L;
        long count2 = 0L;
        {
            RdmsProjectSubprojectExample subprojectExample = new RdmsProjectSubprojectExample();
            RdmsProjectSubprojectExample.Criteria criteria = subprojectExample.createCriteria().andProductManagerIdEqualTo(nextNode).andDeletedEqualTo(0);
            if(! CollectionUtils.isEmpty(statusEnumList)){
                List<String> statusList = new ArrayList<>();
                for(ProjectStatusEnum statusEnum: statusEnumList){
                    statusList.add(statusEnum.getStatus());
                }
                criteria.andStatusIn(statusList);
            }
            List<RdmsProjectSubproject> rdmsProjectSubprojects = rdmsProjectSubprojectMapper.selectByExample(subprojectExample);
            count1 = rdmsProjectSubprojects.stream().filter(item -> !item.getId().equals(item.getParent())).count();
        }
        {
            RdmsProjectSubprojectExample subprojectExample = new RdmsProjectSubprojectExample();
            RdmsProjectSubprojectExample.Criteria criteria = subprojectExample.createCriteria().andSuperviseEqualTo(nextNode).andDeletedEqualTo(0);
            if(! CollectionUtils.isEmpty(statusEnumList)){
                List<String> statusList = new ArrayList<>();
                for(ProjectStatusEnum statusEnum: statusEnumList){
                    statusList.add(statusEnum.getStatus());
                }
                criteria.andStatusIn(statusList);
            }
            List<RdmsProjectSubproject> rdmsProjectSubprojects = rdmsProjectSubprojectMapper.selectByExample(subprojectExample);
            count2 = rdmsProjectSubprojects.stream().filter(item -> item.getId().equals(item.getParent())).count();
        }
        return (int)(count1+count2);
    }

    public RdmsProjectSubproject getMainTestSubproject(String projectId){
        RdmsProjectSubprojectExample subprojectExample = new RdmsProjectSubprojectExample();
        subprojectExample.createCriteria().andProjectIdEqualTo(projectId).andTypeEqualTo(ProjectTypeEnum.PROJECT.getType()).andDeletedEqualTo(0);
        List<RdmsProjectSubproject> rdmsProjectSubprojects = rdmsProjectSubprojectMapper.selectByExample(subprojectExample);
        if(rdmsProjectSubprojects.size() == 1){
            return rdmsProjectSubprojects.get(0);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_ERROR);
        }
    }

    public void customerSubProjectList(PageDto<RdmsProjectSubprojectDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsProjectSubprojectExample subprojectExample = new RdmsProjectSubprojectExample();
        subprojectExample.createCriteria().andCustomerIdEqualTo(pageDto.getKeyWord()).andDeletedEqualTo(0);
        List<RdmsProjectSubproject> rdmsProjectSubprojects = rdmsProjectSubprojectMapper.selectByExample(subprojectExample);

        PageInfo<RdmsProjectSubproject> pageInfo = new PageInfo<>(rdmsProjectSubprojects);
        pageDto.setTotal(pageInfo.getTotal());

        if(CollectionUtils.isEmpty(rdmsProjectSubprojects)){
            pageDto.setList(null);
        }else{
            List<RdmsProjectSubprojectDto> rdmsProjectSubprojectDtos = CopyUtil.copyList(rdmsProjectSubprojects, RdmsProjectSubprojectDto.class);
            //对日期时间进行格式化
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for(RdmsProjectSubprojectDto subprojectDto : rdmsProjectSubprojectDtos){
                subprojectDto.setReleaseTimeStr(sdf.format(subprojectDto.getReleaseTime()));
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(subprojectDto.getProjectManagerId());
                subprojectDto.setProjectManagerName(rdmsCustomerUser.getTrueName());

                if(!ObjectUtils.isEmpty(subprojectDto.getTestManagerId())){
                    RdmsCustomerUser customerUser_test = rdmsCustomerUserService.selectByPrimaryKey(subprojectDto.getTestManagerId());
                    subprojectDto.setTestManagerName(customerUser_test.getTrueName());
                }

                RdmsProject projectById = rdmsProjectService.findProjectById(subprojectDto.getProjectId());
                if(ObjectUtils.isEmpty(projectById)){
                    subprojectDto.setCharacters(null);
                }else{
                    subprojectDto.setProjectName(projectById.getProjectName());
                    List<RdmsCharacterDto> characterDtos = rdmsCharacterService.listBySubProjectId(subprojectDto.getId());
                    subprojectDto.setCharacters(characterDtos);
                }
            }
            pageDto.setList(rdmsProjectSubprojectDtos);
        }
    }

    public void listSubProjectByPJM(PageDto<RdmsProjectSubprojectDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsProjectSubprojectExample subprojectExample = new RdmsProjectSubprojectExample();
        subprojectExample.createCriteria()
                .andCustomerIdEqualTo(pageDto.getKeyWord())
                .andProjectManagerIdEqualTo(pageDto.getActor())
                .andDeletedEqualTo(0)
                .andTypeIsNull();
        List<RdmsProjectSubproject> rdmsProjectSubprojects = rdmsProjectSubprojectMapper.selectByExample(subprojectExample);

        PageInfo<RdmsProjectSubproject> pageInfo = new PageInfo<>(rdmsProjectSubprojects);
        pageDto.setTotal(pageInfo.getTotal());

        if(CollectionUtils.isEmpty(rdmsProjectSubprojects)){
            pageDto.setList(null);
        }else{
            List<RdmsProjectSubprojectDto> rdmsProjectSubprojectDtos = CopyUtil.copyList(rdmsProjectSubprojects, RdmsProjectSubprojectDto.class);
            //对日期时间进行格式化
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for(RdmsProjectSubprojectDto subprojectDto : rdmsProjectSubprojectDtos){
                subprojectDto.setReleaseTimeStr(sdf.format(subprojectDto.getReleaseTime()));
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(subprojectDto.getProjectManagerId());
                subprojectDto.setProjectManagerName(rdmsCustomerUser.getTrueName());

                if(!ObjectUtils.isEmpty(subprojectDto.getTestManagerId())){
                    RdmsCustomerUser customerUser_test = rdmsCustomerUserService.selectByPrimaryKey(subprojectDto.getTestManagerId());
                    subprojectDto.setTestManagerName(customerUser_test.getTrueName());
                }

                RdmsProject projectById = rdmsProjectService.findProjectById(subprojectDto.getProjectId());
                if(ObjectUtils.isEmpty(projectById)){
                    subprojectDto.setCharacters(null);
                }else{
                    subprojectDto.setProjectName(projectById.getProjectName());
                    List<RdmsCharacterDto> characterDtos = rdmsCharacterService.listBySubProjectId(subprojectDto.getId());
                    subprojectDto.setCharacters(characterDtos);
                }
            }
            pageDto.setList(rdmsProjectSubprojectDtos);
        }
    }

    public void listSubProjectByTM(PageDto<RdmsProjectSubprojectDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsProjectSubprojectExample subprojectExample = new RdmsProjectSubprojectExample();
        subprojectExample.createCriteria()
                .andCustomerIdEqualTo(pageDto.getKeyWord())
                .andTestManagerIdEqualTo(pageDto.getActor())
                .andStatusNotEqualTo(ProjectStatusEnum.ARCHIVED.getStatus())
                .andDeletedEqualTo(0);
        List<RdmsProjectSubproject> rdmsProjectSubprojects = rdmsProjectSubprojectMapper.selectByExample(subprojectExample);

        PageInfo<RdmsProjectSubproject> pageInfo = new PageInfo<>(rdmsProjectSubprojects);
        pageDto.setTotal(pageInfo.getTotal());

        if(CollectionUtils.isEmpty(rdmsProjectSubprojects)){
            pageDto.setList(null);
        }else{
            List<RdmsProjectSubprojectDto> rdmsProjectSubprojectDtos = CopyUtil.copyList(rdmsProjectSubprojects, RdmsProjectSubprojectDto.class);
            //对日期时间进行格式化
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for(RdmsProjectSubprojectDto subprojectDto : rdmsProjectSubprojectDtos){
                subprojectDto.setReleaseTimeStr(sdf.format(subprojectDto.getReleaseTime()));
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(subprojectDto.getProjectManagerId());
                subprojectDto.setProjectManagerName(rdmsCustomerUser.getTrueName());

                if(!ObjectUtils.isEmpty(subprojectDto.getTestManagerId())){
                    RdmsCustomerUser customerUser_test = rdmsCustomerUserService.selectByPrimaryKey(subprojectDto.getTestManagerId());
                    subprojectDto.setTestManagerName(customerUser_test.getTrueName());
                }

                RdmsProject projectById = rdmsProjectService.findProjectById(subprojectDto.getProjectId());
                if(ObjectUtils.isEmpty(projectById)){
                    subprojectDto.setCharacters(null);
                }else{
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
            pageDto.setList(rdmsProjectSubprojectDtos);
        }
    }

    @Transactional
    public List<RdmsProjectSubprojectDto> getTestSubprojectListByTM(String testManagerId) {
        List<RdmsProjectSubprojectDto> hasTestJobSubprojectList = new ArrayList<>();
        RdmsCustomerUser rdmsCustomerUser1 = rdmsCustomerUserService.selectByPrimaryKey(testManagerId);
        List<RdmsProjectSubproject> subprojectList = this.getSubprojectListByCustomerIdAndTestManagerId(rdmsCustomerUser1.getCustomerId(), testManagerId);
        if(! CollectionUtils.isEmpty(subprojectList)){
            for(RdmsProjectSubproject subproject : subprojectList){
                if(rdmsJobItemService.hasTestJobInSubproject(subproject.getId())){
                    hasTestJobSubprojectList.add(CopyUtil.copy(subproject, RdmsProjectSubprojectDto.class));
                }
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

                RdmsProject projectById = rdmsProjectService.findProjectById(subprojectDto.getProjectId());
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
    }

    @Transactional
    public List<RdmsJobItemDto> getSubprojectTaskList(String subprojectId) {
        List<JobItemStatusEnum> jobItemStatusList = new ArrayList<>();
        jobItemStatusList.add(JobItemStatusEnum.HANDLING);
        jobItemStatusList.add(JobItemStatusEnum.SUBMIT);
        jobItemStatusList.add(JobItemStatusEnum.TESTING);
        jobItemStatusList.add(JobItemStatusEnum.EVALUATE);
        jobItemStatusList.add(JobItemStatusEnum.APPROVED);
        jobItemStatusList.add(JobItemStatusEnum.COMPLETED);
        return rdmsJobItemService.getJobitemListBySubprojectIdAndStatusListTypeList(subprojectId, jobItemStatusList, null);
    }

    @Transactional
    public List<RdmsProjectSubproject> getSubprojectListByCustomerId(String customerId){
        RdmsProjectSubprojectExample subprojectExample = new RdmsProjectSubprojectExample();
        subprojectExample.createCriteria().andCustomerIdEqualTo(customerId).andDeletedEqualTo(0);
        return rdmsProjectSubprojectMapper.selectByExample(subprojectExample);
    }

    @Transactional
    public List<RdmsProjectSubproject> getSubprojectListByCustomerIdAndTestManagerId(String customerId, String testManagerId){
        RdmsProjectSubprojectExample subprojectExample = new RdmsProjectSubprojectExample();
        subprojectExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andTestManagerIdEqualTo(testManagerId)
                .andDeletedEqualTo(0);
        return rdmsProjectSubprojectMapper.selectByExample(subprojectExample);
    }

    @Transactional
    public List<RdmsProjectSubproject> getSubprojectListByCustomerIdAndRdType(String customerId, String rdType){
        RdmsProjectSubprojectExample subprojectExample = new RdmsProjectSubprojectExample();
        subprojectExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andRdTypeEqualTo(rdType)
                .andDeletedEqualTo(0);
        return rdmsProjectSubprojectMapper.selectByExample(subprojectExample);
    }

    @Transactional
    public List<RdmsProjectSubprojectDto> getMixProjectListByPjmAndStatusList(String customerId, String projectManagerId, List<ProjectStatusEnum> statusEnumList){
        RdmsProjectSubprojectExample subprojectExample = new RdmsProjectSubprojectExample();
        subprojectExample.setOrderByClause("create_time desc");
        RdmsProjectSubprojectExample.Criteria criteria = subprojectExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andProjectManagerIdEqualTo(projectManagerId)
                .andDeletedEqualTo(0);
        if(! CollectionUtils.isEmpty(statusEnumList)){
            List<String> statusList = new ArrayList<>();
            for(ProjectStatusEnum statusEnum: statusEnumList){
                statusList.add(statusEnum.getStatus());
            }
            criteria.andStatusIn(statusList);
        }
        List<RdmsProjectSubproject> subprojectList = rdmsProjectSubprojectMapper.selectByExample(subprojectExample);
        List<RdmsProjectSubprojectDto> subprojectDtos = CopyUtil.copyList(subprojectList, RdmsProjectSubprojectDto.class);
        subprojectDtos.forEach(subproject -> {
            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(subproject.getProjectId());
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(rdmsProject.getProductManagerId());
            subproject.setProductManagerName(rdmsCustomerUser.getTrueName());

            if(!ObjectUtils.isEmpty(subproject.getTestManagerId())){
                RdmsCustomerUser customerUser_test = rdmsCustomerUserService.selectByPrimaryKey(subproject.getTestManagerId());
                if(!ObjectUtils.isEmpty(customerUser_test)){
                    subproject.setTestManagerName(customerUser_test.getTrueName());
                }
            }

            if (!ObjectUtils.isEmpty(subproject.getProjectId())) {
                RdmsProject rdmsProject1 = rdmsProjectService.selectByPrimaryKey(subproject.getProjectId());
                if (!ObjectUtils.isEmpty(rdmsProject1) && !ObjectUtils.isEmpty(rdmsProject1.getSystemManagerId())) {
                    RdmsCustomerUser customerUser_system = rdmsCustomerUserService.selectByPrimaryKey(rdmsProject1.getSystemManagerId());
                    if (!ObjectUtils.isEmpty(customerUser_system)) {
                        subproject.setSystemManagerName(customerUser_system.getTrueName());
                    }
                }
            }

            RdmsCustomerUser rdmsCustomerUser1 = rdmsCustomerUserService.selectByPrimaryKey(subproject.getProjectManagerId());
            subproject.setProjectManagerName(rdmsCustomerUser1.getTrueName());
            RdmsCustomerUser rdmsCustomerUser2 = rdmsCustomerUserService.selectByPrimaryKey(rdmsProject.getSupervise());
            subproject.setSuperviseName(rdmsCustomerUser2.getTrueName());

            List<JobItemStatusEnum> statusList = new ArrayList<>();
            statusList.add(JobItemStatusEnum.HANDLING);
            List<RdmsJobItemDto> handlingJobList = rdmsJobItemService.getJobitemListBySubProjectIdPjmIdAndStatus(subproject.getId(), projectManagerId, statusList, ProjectTypeEnum.DEV_PROJECT);
            subproject.setHandlingJobNum(handlingJobList.size());

            List<JobItemStatusEnum> statusList_submit = new ArrayList<>();
            statusList_submit.add(JobItemStatusEnum.SUBMIT);
            List<RdmsJobItemDto> submitJobList = rdmsJobItemService.getJobitemListBySubProjectIdPjmIdAndStatus(subproject.getId(), projectManagerId, statusList_submit, ProjectTypeEnum.DEV_PROJECT);
            subproject.setSubmitJobNum(submitJobList.size());

            List<RdmsJobItemDto> submitJobList_subproject = rdmsJobItemService.getTaskJobitemListBySubProjectIdPjmIdAndStatus(subproject.getId(), projectManagerId, statusList_submit, ProjectTypeEnum.SUB_PROJECT);
            subproject.setSubmitJobNum_subProject(submitJobList_subproject.size());
            //对里程碑任务进行单独处理
            if(subproject.getId().equals(subproject.getProjectId())){
                List<JobItemTypeEnum> typeEnumList_milestone = new ArrayList<>();
                typeEnumList_milestone.add(JobItemTypeEnum.TASK_MILESTONE);
                List<RdmsJobItemDto> jobitemList_milestone = rdmsJobItemService.getJobitemListByProjectId_mainPjm(subproject.getProjectId(), statusList_submit, typeEnumList_milestone, subproject.getProjectManagerId());
                subproject.setSubmitJobNum_subProject(submitJobList_subproject.size() + jobitemList_milestone.size());
            }

            List<JobItemStatusEnum> statusList_evaluate = new ArrayList<>();
            statusList_evaluate.add(JobItemStatusEnum.EVALUATE);
            List<RdmsJobItemDto> evaluateJobList = rdmsJobItemService.getJobitemListBySubProjectIdPjmIdAndStatus(subproject.getId(), projectManagerId, statusList_evaluate, ProjectTypeEnum.DEV_PROJECT);
            subproject.setEvaluateJobNum(evaluateJobList.size());

            if(subproject.getStatus().equals(ProjectStatusEnum.REVIEW_SUBP.getStatus())){
                subproject.setIsReviewing(true);
            }else{
                subproject.setIsReviewing(false);
            }

            if(subproject.getStatus().equals(ProjectStatusEnum.CLOSE.getStatus())
                    || subproject.getStatus().equals(ProjectStatusEnum.COMPLETE.getStatus())
                    || subproject.getStatus().equals(ProjectStatusEnum.ARCHIVED.getStatus())
            ){
                subproject.setIsCompleted(true);
            }else{
                subproject.setIsCompleted(false);
            }

            if(subproject.getStatus().equals(ProjectStatusEnum.DEV_COMPLETE.getStatus())){
                subproject.setIsDevCompleted(true);
            }else{
                subproject.setIsDevCompleted(false);
            }
        });

        return subprojectDtos;
    }

    @Transactional
    public List<RdmsProjectSubprojectDto> getMixProjectListByPdmAndStatusList(String customerId, String productManagerId, List<ProjectStatusEnum> statusEnumList){
        RdmsProjectSubprojectExample subprojectExample = new RdmsProjectSubprojectExample();
        subprojectExample.setOrderByClause("create_time desc");
        RdmsProjectSubprojectExample.Criteria criteria = subprojectExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andProductManagerIdEqualTo(productManagerId)
                .andDeletedEqualTo(0);
        if(! CollectionUtils.isEmpty(statusEnumList)){
            List<String> statusList = new ArrayList<>();
            for(ProjectStatusEnum statusEnum: statusEnumList){
                statusList.add(statusEnum.getStatus());
            }
            criteria.andStatusIn(statusList);
        }
        List<RdmsProjectSubproject> subprojectList = rdmsProjectSubprojectMapper.selectByExample(subprojectExample);
        List<RdmsProjectSubprojectDto> subprojectDtos = CopyUtil.copyList(subprojectList, RdmsProjectSubprojectDto.class);
        subprojectDtos.forEach(subproject -> {
            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(subproject.getProjectId());
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(rdmsProject.getProductManagerId());
            subproject.setProductManagerName(rdmsCustomerUser.getTrueName());

            if(!ObjectUtils.isEmpty(subproject.getTestManagerId())){
                RdmsCustomerUser customerUser_test = rdmsCustomerUserService.selectByPrimaryKey(subproject.getTestManagerId());
                if(!ObjectUtils.isEmpty(customerUser_test)){
                    subproject.setTestManagerName(customerUser_test.getTrueName());
                }
            }

            if (!ObjectUtils.isEmpty(subproject.getProjectId())) {
                RdmsProject rdmsProject1 = rdmsProjectService.selectByPrimaryKey(subproject.getProjectId());
                if (!ObjectUtils.isEmpty(rdmsProject1) && !ObjectUtils.isEmpty(rdmsProject1.getSystemManagerId())) {
                    RdmsCustomerUser customerUser_system = rdmsCustomerUserService.selectByPrimaryKey(rdmsProject1.getSystemManagerId());
                    if (!ObjectUtils.isEmpty(customerUser_system)) {
                        subproject.setSystemManagerName(customerUser_system.getTrueName());
                    }
                }
            }

            RdmsCustomerUser rdmsCustomerUser1 = rdmsCustomerUserService.selectByPrimaryKey(subproject.getProjectManagerId());
            subproject.setProjectManagerName(rdmsCustomerUser1.getTrueName());
            RdmsCustomerUser rdmsCustomerUser2 = rdmsCustomerUserService.selectByPrimaryKey(rdmsProject.getSupervise());
            subproject.setSuperviseName(rdmsCustomerUser2.getTrueName());

        });

        return subprojectDtos;
    }

    @Transactional
    public void getSubprojectListCustomerIdAndStatusList(PageDto<RdmsProjectSubprojectDto> pageDto, List<ProjectStatusEnum> statusEnumList){

        List<String> statusList = new ArrayList<>();
        for(ProjectStatusEnum statusEnum: statusEnumList){
            statusList.add(statusEnum.getStatus());
        }

        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsProjectSubprojectExample subprojectExample = new RdmsProjectSubprojectExample();
        subprojectExample.setOrderByClause("create_time desc");
        RdmsProjectSubprojectExample.Criteria criteria = subprojectExample.createCriteria()
                .andCustomerIdEqualTo(pageDto.getCustomerId())
                .andDeletedEqualTo(0);
        criteria.andStatusIn(statusList);

        if(!ObjectUtils.isEmpty(pageDto.getKeyWord())){
            criteria.andLabelLike("%"+ pageDto.getKeyWord() +"%");
        }
        List<RdmsProjectSubproject> subprojectList = rdmsProjectSubprojectMapper.selectByExample(subprojectExample);
        PageInfo<RdmsProjectSubproject> pageInfo = new PageInfo<>(subprojectList);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsProjectSubprojectDto> subprojectDtos = CopyUtil.copyList(subprojectList, RdmsProjectSubprojectDto.class);
        subprojectDtos.forEach(subproject -> {
            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(subproject.getProjectId());
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(rdmsProject.getProductManagerId());
            subproject.setProductManagerName(rdmsCustomerUser.getTrueName());
            subproject.setProjectName(rdmsProject.getProjectName());

            if(!ObjectUtils.isEmpty(subproject.getTestManagerId())){
                RdmsCustomerUser customerUser_test = rdmsCustomerUserService.selectByPrimaryKey(subproject.getTestManagerId());
                if(!ObjectUtils.isEmpty(customerUser_test)){
                    subproject.setTestManagerName(customerUser_test.getTrueName());
                }
            }

            if (!ObjectUtils.isEmpty(subproject.getProjectId())) {
                RdmsProject rdmsProject1 = rdmsProjectService.selectByPrimaryKey(subproject.getProjectId());
                if (!ObjectUtils.isEmpty(rdmsProject1) && !ObjectUtils.isEmpty(rdmsProject1.getSystemManagerId())) {
                    RdmsCustomerUser customerUser_system = rdmsCustomerUserService.selectByPrimaryKey(rdmsProject1.getSystemManagerId());
                    if (!ObjectUtils.isEmpty(customerUser_system)) {
                        subproject.setSystemManagerName(customerUser_system.getTrueName());
                    }
                }
            }

            RdmsCustomerUser rdmsCustomerUser1 = rdmsCustomerUserService.selectByPrimaryKey(subproject.getProjectManagerId());
            subproject.setProjectManagerName(rdmsCustomerUser1.getTrueName());
            RdmsCustomerUser rdmsCustomerUser2 = rdmsCustomerUserService.selectByPrimaryKey(rdmsProject.getSupervise());
            subproject.setSuperviseName(rdmsCustomerUser2.getTrueName());
        });
        pageDto.setList(subprojectDtos);
    }

    @Transactional
    public void getSubprojectListCustomerIdAndStatusListByTestManager(PageDto<RdmsProjectSubprojectDto> pageDto, List<ProjectStatusEnum> statusEnumList){

        List<String> statusList = new ArrayList<>();
        for(ProjectStatusEnum statusEnum: statusEnumList){
            statusList.add(statusEnum.getStatus());
        }

        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsProjectSubprojectExample subprojectExample = new RdmsProjectSubprojectExample();
        subprojectExample.setOrderByClause("create_time desc");
        RdmsProjectSubprojectExample.Criteria criteria = subprojectExample.createCriteria()
                .andCustomerIdEqualTo(pageDto.getCustomerId())
                .andTestManagerIdEqualTo(pageDto.getLoginUserId())
                .andDeletedEqualTo(0);
        criteria.andStatusIn(statusList);

        if(!ObjectUtils.isEmpty(pageDto.getKeyWord())){
            criteria.andLabelLike("%"+ pageDto.getKeyWord() +"%");
        }
        List<RdmsProjectSubproject> subprojectList = rdmsProjectSubprojectMapper.selectByExample(subprojectExample);
        PageInfo<RdmsProjectSubproject> pageInfo = new PageInfo<>(subprojectList);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsProjectSubprojectDto> subprojectDtos = CopyUtil.copyList(subprojectList, RdmsProjectSubprojectDto.class);
        subprojectDtos.forEach(subproject -> {
            RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(subproject.getProjectId());
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(rdmsProject.getProductManagerId());
            subproject.setProductManagerName(rdmsCustomerUser.getTrueName());
            subproject.setProjectName(rdmsProject.getProjectName());

            if(!ObjectUtils.isEmpty(subproject.getTestManagerId())){
                RdmsCustomerUser customerUser_test = rdmsCustomerUserService.selectByPrimaryKey(subproject.getTestManagerId());
                if(!ObjectUtils.isEmpty(customerUser_test)){
                    subproject.setTestManagerName(customerUser_test.getTrueName());
                }
            }

            if (!ObjectUtils.isEmpty(subproject.getProjectId())) {
                RdmsProject rdmsProject1 = rdmsProjectService.selectByPrimaryKey(subproject.getProjectId());
                if (!ObjectUtils.isEmpty(rdmsProject1) && !ObjectUtils.isEmpty(rdmsProject1.getSystemManagerId())) {
                    RdmsCustomerUser customerUser_system = rdmsCustomerUserService.selectByPrimaryKey(rdmsProject1.getSystemManagerId());
                    if (!ObjectUtils.isEmpty(customerUser_system)) {
                        subproject.setSystemManagerName(customerUser_system.getTrueName());
                    }
                }
            }

            RdmsCustomerUser rdmsCustomerUser1 = rdmsCustomerUserService.selectByPrimaryKey(subproject.getProjectManagerId());
            subproject.setProjectManagerName(rdmsCustomerUser1.getTrueName());
            RdmsCustomerUser rdmsCustomerUser2 = rdmsCustomerUserService.selectByPrimaryKey(rdmsProject.getSupervise());
            subproject.setSuperviseName(rdmsCustomerUser2.getTrueName());
        });
        pageDto.setList(subprojectDtos);
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsProjectSubproject subproject = rdmsProjectSubprojectMapper.selectByPrimaryKey(id);
        if(! ObjectUtils.isEmpty(subproject)){
            subproject.setDeleted(1);
            rdmsProjectSubprojectMapper.updateByPrimaryKey(subproject);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

    public List<RdmsProjectSubprojectDto> ListSubprojectByIds(RdmsHmiIdsDto idsDto){
        RdmsProjectSubprojectExample subprojectExample = new RdmsProjectSubprojectExample();
        RdmsProjectSubprojectExample.Criteria criteria = subprojectExample.createCriteria()
                .andDeletedEqualTo(0)
                .andStatusNotEqualTo(ProjectStatusEnum.OUT_SOURCE.getStatus())
                .andTypeEqualTo(ProjectTypeEnum.SUB_PROJECT.getType());
        if(! ObjectUtils.isEmpty(idsDto.getCreatorId())){
            criteria.andCreatorIdEqualTo(idsDto.getCreatorId());
        }
        if(! ObjectUtils.isEmpty(idsDto.getParent())){
            criteria.andParentEqualTo(idsDto.getParent());
        }

        List<RdmsProjectSubproject> rdmsProjectSubprojects = rdmsProjectSubprojectMapper.selectByExample(subprojectExample);
        return CopyUtil.copyList(rdmsProjectSubprojects, RdmsProjectSubprojectDto.class);
    }


}
