/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.course.server.domain.*;
import com.course.server.dto.rdms.RdmsCharacterDto;
import com.course.server.dto.rdms.RdmsFunctionTreeDto;
import com.course.server.dto.rdms.RdmsJobItemDto;
import com.course.server.enums.rdms.CharacterStatusEnum;
import com.course.server.enums.rdms.JobItemStatusEnum;
import com.course.server.enums.rdms.JobItemTypeEnum;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsFunctionTreeMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class RdmsFunctionTreeService {
    private static final Logger LOG = LoggerFactory.getLogger(RdmsFunctionTreeService.class);

    @Resource
    private RdmsFunctionTreeMapper rdmsFunctionTreeMapper;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private RdmsPreProjectService rdmsPreProjectService;
    @Autowired
    private RdmsJobItemService rdmsJobItemService;
    @Autowired
    private RdmsCharacterService rdmsCharacterService;
    @Autowired
    private RdmsProjectService rdmsProjectService;

    /**
     * 只要给出一个带有Tree信息的List, 本函数就能构建一棵树
     * 根据功能树列表构建树形结构，支持多层级嵌套
     * 
     * @param treeList 功能树列表
     * @return 返回构建好的功能树根节点
     */
    public RdmsFunctionTreeDto getFunctionTree(@NotNull List<RdmsFunctionTreeDto> treeList){
        //构建树根对象
        RdmsFunctionTreeDto treeRoot = new RdmsFunctionTreeDto();
        treeRoot.setId(null);
        treeRoot.setParent(null);
        treeRoot.setDeep(null);
        treeRoot.setCode(null);
        treeRoot.setName("根对象");
        treeRoot.setWriterId(null);
        treeRoot.setWriterName(null);
        treeRoot.setPreprojectId(null);
        treeRoot.setPreprojectName(null);
        treeRoot.setProjectType(null);
        treeRoot.setJobType(null);
        treeRoot.setStatus(null);
        treeRoot.setAuxStatus(null);
        treeRoot.setChildren(null);

        //组装label
        if(!CollectionUtils.isEmpty(treeList)){
            for(RdmsFunctionTreeDto treeItem : treeList){
                String statusName = "";
                if(!ObjectUtils.isEmpty(treeItem.getStatus())){
                    CharacterStatusEnum characterEnumByStatus = CharacterStatusEnum.getCharacterEnumByStatus(treeItem.getStatus());
                    if(!ObjectUtils.isEmpty(characterEnumByStatus)){
                        statusName = characterEnumByStatus.getStatusName();
                    }
                }
                treeItem.setLabel(treeItem.getName() + "( 项目: " + treeItem.getPreprojectName() + ", 编制: " + treeItem.getWriterName() + ", 状态: " + statusName +")" );
            }
        }

        //根据deep逆序排列
        List<RdmsFunctionTreeDto> sortedTreeList = treeList.stream().sorted(Comparator.comparingInt(RdmsFunctionTreeDto::getDeep).reversed()).collect(Collectors.toList());

        //逐层deep向下装配
        if(! CollectionUtils.isEmpty(sortedTreeList)){
            for(int i = sortedTreeList.get(0).getDeep(); i > 0; i--){
                RdmsFunctionTreeDto tempParent = new RdmsFunctionTreeDto();
                for(int j = 0 ; j < sortedTreeList.size(); ){
                    RdmsFunctionTreeDto treeItem = sortedTreeList.get(j);
                    if(treeItem.getDeep() == i){
                        //找出ID为 这个 parentID的 列表项
                        List<RdmsFunctionTreeDto> parentItems = sortedTreeList.stream().filter(record -> record.getId().equals(treeItem.getParent())).collect(Collectors.toList());
                        if(! CollectionUtils.isEmpty(parentItems) && ! parentItems.get(0).getId().equals(treeItem.getId())){  //子记录和父记录不能是同一条记录
                            tempParent = parentItems.get(0);
                            if(ObjectUtils.isEmpty(tempParent.getChildren())){
                                List<RdmsFunctionTreeDto> rdmsHmiTrees = new ArrayList<>();
                                rdmsHmiTrees.add(treeItem);
                                tempParent.setChildren(rdmsHmiTrees);
                                sortedTreeList.remove(treeItem);
                                j = 0;
                            }else{
                                List<RdmsFunctionTreeDto> rdmsHmiTrees = tempParent.getChildren();
                                rdmsHmiTrees.add(treeItem);
                                tempParent.setChildren(rdmsHmiTrees);
                                sortedTreeList.remove(treeItem);
                                j = 0;
                            }
                        }else{
                            j ++;
                        }
                    } else{
                        j++;
                    }
                }
            }
            treeRoot.setChildren(sortedTreeList);
        }
        return treeRoot;
    }

    /**
     * 保存功能树
     * 将功能树DTO列表转换为实体对象并保存到数据库
     * 
     * @param functionTreeDtoList 功能树DTO列表
     */
    @Transactional
    public void saveFunctionTree(List<RdmsFunctionTreeDto> functionTreeDtoList){
        if(CollectionUtils.isEmpty(functionTreeDtoList)){
            return;
        }
        List<RdmsFunctionTree> functionTreeCollect = new ArrayList<>();

        iterationCollectFunctionItem(functionTreeDtoList, functionTreeCollect, 0, null);

        for(RdmsFunctionTree functionTreeItem : functionTreeCollect){
            this.save(functionTreeItem);
        }
    }

    /**
     * 递归收集功能项
     * 递归遍历功能树，将DTO对象转换为实体对象并收集到列表中
     * 
     * @param functionTreeList 功能树DTO列表
     * @param functionTreeCollect 收集功能树实体的列表
     * @param deep 当前层级深度
     * @param parentId 父节点ID
     */
    private static void iterationCollectFunctionItem(List<RdmsFunctionTreeDto> functionTreeList, List<RdmsFunctionTree> functionTreeCollect, int deep, String parentId) {
        for(RdmsFunctionTreeDto functionTreeItem : functionTreeList){
            if(ObjectUtils.isEmpty(functionTreeItem)){
                return;
            }
            RdmsFunctionTree rdmsFunctionTree = new RdmsFunctionTree();
            rdmsFunctionTree.setId(functionTreeItem.getId());
            rdmsFunctionTree.setParent(parentId);
            rdmsFunctionTree.setDeep(deep);
            rdmsFunctionTree.setCode(functionTreeItem.getCode());
            rdmsFunctionTree.setName(functionTreeItem.getName());
            rdmsFunctionTree.setWriterId(functionTreeItem.getWriterId());
            rdmsFunctionTree.setPreprojectId(functionTreeItem.getPreprojectId());
            rdmsFunctionTree.setProjectType(functionTreeItem.getProjectType());
            rdmsFunctionTree.setJobType(functionTreeItem.getJobType());
            rdmsFunctionTree.setStatus(functionTreeItem.getStatus());

            functionTreeCollect.add(rdmsFunctionTree);
            if(!CollectionUtils.isEmpty(functionTreeItem.getChildren())){
                iterationCollectFunctionItem(functionTreeItem.getChildren(), functionTreeCollect, deep+1, functionTreeItem.getId());
            }
        }
    }

    /**
     * 根据预项目ID获取功能树
     * 获取指定预项目的功能树结构，并填充相关的用户和项目信息
     * 
     * @param preprojectId 预项目ID
     * @return 返回功能树DTO对象
     */
    public RdmsFunctionTreeDto getFunctionTree(String preprojectId) {
        List<RdmsFunctionTree> functionTreeList = this.getFunctionTreeListByPreprojectId(preprojectId);
        List<RdmsFunctionTreeDto> rdmsFunctionTreeDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(functionTreeList)) {
            for (RdmsFunctionTree functionTree : functionTreeList) {
                RdmsFunctionTreeDto functionTreeDto = CopyUtil.copy(functionTree, RdmsFunctionTreeDto.class);
                if (!ObjectUtils.isEmpty(functionTree.getWriterId())) {
                    RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(functionTree.getWriterId());
                    if (!ObjectUtils.isEmpty(rdmsCustomerUser)) {
                        functionTreeDto.setWriterName(rdmsCustomerUser.getTrueName());
                    }
                }
                if (!ObjectUtils.isEmpty(functionTree.getPreprojectId())) {
                    RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(functionTree.getPreprojectId());
                    if (!ObjectUtils.isEmpty(rdmsPreProject)) {
                        functionTreeDto.setPreprojectName(rdmsPreProject.getPreProjectName());
                    }
                }
                //判断item是否已经存在function处理记录
                if(!ObjectUtils.isEmpty(functionTree.getId())){
                    //用id作为parent去查看是否存在jobitemType为function的记录
                    List<RdmsCharacterDto> characterByParentIdAndStatus = rdmsCharacterService.getCharacterByParentIdAndStatus(functionTree.getId(), JobItemTypeEnum.FUNCTION);
                    if(!CollectionUtils.isEmpty(characterByParentIdAndStatus)){
                        functionTreeDto.setHaveUpdated(true);
                    }else{
                        functionTreeDto.setHaveUpdated(false);
                    }
                }
                rdmsFunctionTreeDtos.add(functionTreeDto);
            }
        }
        RdmsFunctionTreeDto functionTree = this.getFunctionTree(rdmsFunctionTreeDtos);
        return functionTree;
    }

    /**
     * 根据项目ID获取功能树
     * 获取指定项目的功能树结构，并填充相关的用户和项目信息
     * 
     * @param projectId 项目ID
     * @return 返回功能树DTO对象
     */
    public RdmsFunctionTreeDto getFunctionTreeByProjectId(String projectId) {
        List<RdmsFunctionTree> functionTreeList = this.getFunctionTreeListByProjectId(projectId);
        List<RdmsFunctionTreeDto> rdmsFunctionTreeDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(functionTreeList)) {
            for (RdmsFunctionTree functionTree : functionTreeList) {
                RdmsFunctionTreeDto functionTreeDto = CopyUtil.copy(functionTree, RdmsFunctionTreeDto.class);
                if (!ObjectUtils.isEmpty(functionTree.getWriterId())) {
                    RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(functionTree.getWriterId());
                    if (!ObjectUtils.isEmpty(rdmsCustomerUser)) {
                        functionTreeDto.setWriterName(rdmsCustomerUser.getTrueName());
                    }
                }
                if (!ObjectUtils.isEmpty(functionTree.getPreprojectId())) {
                    RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(functionTree.getPreprojectId());
                    if (!ObjectUtils.isEmpty(rdmsPreProject)) {
                        functionTreeDto.setPreprojectName(rdmsPreProject.getPreProjectName());
                    }
                }
                //判断item是否已经存在function处理记录
                if(!ObjectUtils.isEmpty(functionTree.getId())){
                    //用id作为parent去查看是否存在jobitemType为function的记录
                    List<RdmsCharacterDto> characterByParentIdAndStatus = rdmsCharacterService.getCharacterByParentIdAndStatus(functionTree.getId(), JobItemTypeEnum.FUNCTION);
                    if(!CollectionUtils.isEmpty(characterByParentIdAndStatus)){
                        functionTreeDto.setHaveUpdated(true);
                    }else{
                        functionTreeDto.setHaveUpdated(false);
                    }
                }
                rdmsFunctionTreeDtos.add(functionTreeDto);
            }
        }
        RdmsFunctionTreeDto functionTree = this.getFunctionTree(rdmsFunctionTreeDtos);
        return functionTree;
    }

    /**
     * 根据预项目ID获取功能树列表
     * 查询指定预项目下的所有功能树记录
     * 
     * @param preprojectId 预项目ID
     * @return 返回功能树列表
     */
    public List<RdmsFunctionTree> getFunctionTreeListByPreprojectId(String preprojectId){
        RdmsFunctionTreeExample functionTreeExample = new RdmsFunctionTreeExample();
        functionTreeExample.setOrderByClause("create_time desc");
        functionTreeExample.createCriteria().andPreprojectIdEqualTo(preprojectId).andDeletedEqualTo(0);
        List<RdmsFunctionTree> rdmsFunctionTrees = rdmsFunctionTreeMapper.selectByExample(functionTreeExample);
        return rdmsFunctionTrees;
    }

    /**
     * 根据项目ID获取功能树列表
     * 通过项目ID查找对应的预项目，然后获取功能树列表
     * 
     * @param projectId 项目ID
     * @return 返回功能树列表，如果项目不存在则返回null
     */
    public List<RdmsFunctionTree> getFunctionTreeListByProjectId(String projectId){
        RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(projectId);
        if(!ObjectUtils.isEmpty(rdmsProject) && !ObjectUtils.isEmpty(rdmsProject.getPreProjectId())){
            RdmsFunctionTreeExample functionTreeExample = new RdmsFunctionTreeExample();
            functionTreeExample.setOrderByClause("create_time asc");
            functionTreeExample.createCriteria().andPreprojectIdEqualTo(rdmsProject.getPreProjectId()).andDeletedEqualTo(0);
            List<RdmsFunctionTree> rdmsFunctionTrees = rdmsFunctionTreeMapper.selectByExample(functionTreeExample);
            return rdmsFunctionTrees;
        }else{
            return null;
        }
    }

    /**
     * 根据主键查询功能树
     * 
     * @param id 功能树ID
     * @return 返回功能树对象
     */
    public RdmsFunctionTree selectByPrimaryKey(String id){
        return rdmsFunctionTreeMapper.selectByPrimaryKey(id);
    }

    /**
     * 保存功能树
     * 根据ID判断是新增还是更新操作
     * 
     * @param functionTree 功能树对象
     * @return 返回保存后的ID
     */
    public String save(RdmsFunctionTree functionTree) {
        if(ObjectUtils.isEmpty(functionTree.getId())){
            return this.insert(functionTree);
        }else{
            RdmsFunctionTree rdmsBom = this.selectByPrimaryKey(functionTree.getId());
            if(ObjectUtils.isEmpty(rdmsBom)){
                return this.insert(functionTree);
            }else{
                return this.update(functionTree);
            }
        }
    }

    /**
     * 保存功能树（不更新已存在的记录）
     * 只进行新增操作，如果记录已存在则返回null
     * 
     * @param functionTree 功能树对象
     * @return 返回保存后的ID，如果记录已存在则返回null
     */
    public String save_noUpdate(RdmsFunctionTree functionTree) {
        if(ObjectUtils.isEmpty(functionTree.getId())){
            return this.insert(functionTree);
        }else{
            RdmsFunctionTree rdmsFunctionTree = this.selectByPrimaryKey(functionTree.getId());
            if(ObjectUtils.isEmpty(rdmsFunctionTree)){
                return this.insert(functionTree);
            }else{
                return null;
            }
        }
    }

    /**
     * 新增功能树
     * 插入新的功能树记录到数据库
     * 
     * @param functionTree 功能树对象
     * @return 返回新增记录的ID
     */
    private String insert(RdmsFunctionTree functionTree) {
        if(ObjectUtils.isEmpty(functionTree.getId())){
            functionTree.setId(UuidUtil.getShortUuid());
        }
        RdmsFunctionTree rdmsBom = rdmsFunctionTreeMapper.selectByPrimaryKey(functionTree.getId());
        if(! ObjectUtils.isEmpty(rdmsBom)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            functionTree.setDeleted(0);
            functionTree.setCreateTime(new Date());
            rdmsFunctionTreeMapper.insert(functionTree);
            return functionTree.getId();
        }
    }

    /**
     * 更新功能树
     * 更新已存在的功能树记录
     * 
     * @param functionTree 功能树对象
     * @return 返回更新记录的ID
     */
    public String update(RdmsFunctionTree functionTree) {
        if(ObjectUtils.isEmpty(functionTree.getId())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsFunctionTree rdmsBom = this.selectByPrimaryKey(functionTree.getId());
        if(ObjectUtils.isEmpty(rdmsBom)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            functionTree.setDeleted(0);
            rdmsFunctionTreeMapper.updateByPrimaryKey(functionTree);
            return functionTree.getId();
        }
    }

    /**
     * 选择性更新功能树
     * 只更新非空字段
     * 
     * @param functionTree 功能树对象
     * @return 返回更新记录的ID
     */
    public String updateByPrimaryKeySelective(RdmsFunctionTree functionTree){
        rdmsFunctionTreeMapper.updateByPrimaryKeySelective(functionTree);
        return functionTree.getId();
    }

    /**
     * 删除操作----设置删除标志位
     * 将指定功能树标记为已删除状态
     * 
     * @param id 功能树ID
     */
    public void delete(String id) {
        RdmsFunctionTree functionTree = rdmsFunctionTreeMapper.selectByPrimaryKey(id);
        if(!ObjectUtils.isEmpty(functionTree)){
            functionTree.setDeleted(1);
            this.update(functionTree);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

}
