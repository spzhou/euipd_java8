/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.course.server.domain.RdmsBomTree;
import com.course.server.domain.RdmsBomTreeExample;
import com.course.server.domain.RdmsBomTreeMain;
import com.course.server.domain.RdmsBomTreeMainExample;
import com.course.server.dto.rdms.RdmsBomTreeDto;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsBomTreeMainMapper;
import com.course.server.mapper.RdmsBomTreeMapper;
import com.course.server.util.UuidUtil;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class RdmsBomTreeService {
    private static final Logger LOG = LoggerFactory.getLogger(RdmsBomTreeService.class);

    @Resource
    private RdmsBomTreeMapper rdmsBomTreeMapper;
    @Resource
    private RdmsBomTreeMainMapper rdmsBomTreeMainMapper;

    /**
     * 只要给出一个带有Tree信息的List, 本函数就能构建一棵树
     * @param treeList
     * @return
     */
    public RdmsBomTreeDto getBomTree(@NotNull List<RdmsBomTreeDto> treeList){
        //构建树根对象
        RdmsBomTreeDto treeRoot = new RdmsBomTreeDto();
        treeRoot.setId(null);
        treeRoot.setParent(null);
        treeRoot.setDeep(null);
        treeRoot.setBomCode(null);
        treeRoot.setName("根对象");
        treeRoot.setModel(null);
        treeRoot.setUnit(null);
        treeRoot.setAmount(null);
        treeRoot.setSubprojectId(null);
        treeRoot.setProjectId(null);
        treeRoot.setChildren(null);

        //组装label
        if(!CollectionUtils.isEmpty(treeList)){
            for(RdmsBomTreeDto treeItem : treeList){
                treeItem.setLabel(treeItem.getName() + "( " + treeItem.getBomCode() + " : " + treeItem.getModel() + " : " + treeItem.getAmount() + " " + treeItem.getUnit() +")" );
            }
        }

        //根据deep逆序排列
        List<RdmsBomTreeDto> sortedTreeList = treeList.stream().sorted(Comparator.comparingInt(RdmsBomTreeDto::getDeep).reversed()).collect(Collectors.toList());

        //逐层deep向下装配
        if(! CollectionUtils.isEmpty(sortedTreeList)){
            for(int i = sortedTreeList.get(0).getDeep(); i > 0; i--){
                RdmsBomTreeDto tempParent = new RdmsBomTreeDto();
                for(int j = 0 ; j < sortedTreeList.size(); ){
                    RdmsBomTreeDto treeItem = sortedTreeList.get(j);
                    if(treeItem.getDeep() == i){
                        //找出ID为 这个 parentID的 列表项
                        List<RdmsBomTreeDto> parentItems = sortedTreeList.stream().filter(record -> record.getId().equals(treeItem.getParent())).collect(Collectors.toList());
                        if(! CollectionUtils.isEmpty(parentItems) && ! parentItems.get(0).getId().equals(treeItem.getId())){  //子记录和父记录不能是同一条记录
                            tempParent = parentItems.get(0);
                            if(ObjectUtils.isEmpty(tempParent.getChildren())){
                                List<RdmsBomTreeDto> rdmsHmiTrees = new ArrayList<>();
                                rdmsHmiTrees.add(treeItem);
                                tempParent.setChildren(rdmsHmiTrees);
                                sortedTreeList.remove(treeItem);
                                j = 0;
                            }else{
                                List<RdmsBomTreeDto> rdmsHmiTrees = tempParent.getChildren();
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

    @Transactional
    public void saveBomTree(List<RdmsBomTreeDto> bomTreeDtoList){
        if(CollectionUtils.isEmpty(bomTreeDtoList)){
            return;
        }
        List<RdmsBomTree> bomTreeCollect = new ArrayList<>();

        iterationCollectBomItem(bomTreeDtoList, bomTreeCollect, 0, null);

        for(RdmsBomTree bomTreeItem : bomTreeCollect){
            this.save(bomTreeItem);
        }
    }

    @Transactional
    public void saveBomTree_main(List<RdmsBomTreeDto> bomTreeDtoList){
        if(CollectionUtils.isEmpty(bomTreeDtoList)){
            return;
        }
        List<RdmsBomTreeMain> bomTreeCollect = new ArrayList<>();

        iterationCollectMainBomItem(bomTreeDtoList, bomTreeCollect, 0, null);

        for(RdmsBomTreeMain bomTreeItem : bomTreeCollect){
            this.save_main(bomTreeItem);
        }
    }

    private static void iterationCollectBomItem(List<RdmsBomTreeDto> bomTreeList, List<RdmsBomTree> bomTreeCollect, int deep, String parentId) {
        for(RdmsBomTreeDto bomTreeItem : bomTreeList){
            if(ObjectUtils.isEmpty(bomTreeItem)){
                return;
            }
            RdmsBomTree rdmsBomTree = new RdmsBomTree();
            rdmsBomTree.setId(bomTreeItem.getId());
            rdmsBomTree.setParent(parentId);
            rdmsBomTree.setDeep(deep);
            rdmsBomTree.setBomCode(bomTreeItem.getBomCode());
            rdmsBomTree.setName(bomTreeItem.getName());
            rdmsBomTree.setModel(bomTreeItem.getModel());
            rdmsBomTree.setUnit(bomTreeItem.getUnit());
            if(!ObjectUtils.isEmpty(bomTreeItem.getAmount())){
                rdmsBomTree.setAmount(Double.valueOf(bomTreeItem.getAmount()));
            }else {
                rdmsBomTree.setAmount(0.0);
            }
            rdmsBomTree.setSubprojectId(bomTreeItem.getSubprojectId());
            bomTreeCollect.add(rdmsBomTree);
            if(!CollectionUtils.isEmpty(bomTreeItem.getChildren())){
                iterationCollectBomItem(bomTreeItem.getChildren(), bomTreeCollect, deep+1, bomTreeItem.getId());
            }
        }
    }

    private static void iterationCollectMainBomItem(List<RdmsBomTreeDto> bomTreeList, List<RdmsBomTreeMain> bomTreeCollect, int deep, String parentId) {
        for(RdmsBomTreeDto bomTreeItem : bomTreeList){
            if(ObjectUtils.isEmpty(bomTreeItem)){
                return;
            }
            RdmsBomTreeMain rdmsBomTree = new RdmsBomTreeMain();
            rdmsBomTree.setId(bomTreeItem.getId());
            rdmsBomTree.setParent(parentId);
            rdmsBomTree.setDeep(deep);
            rdmsBomTree.setBomCode(bomTreeItem.getBomCode());
            rdmsBomTree.setName(bomTreeItem.getName());
            rdmsBomTree.setModel(bomTreeItem.getModel());
            rdmsBomTree.setUnit(bomTreeItem.getUnit());
            if(!ObjectUtils.isEmpty(bomTreeItem.getAmount())){
                rdmsBomTree.setAmount(Double.valueOf(bomTreeItem.getAmount()));
            }else {
                rdmsBomTree.setAmount(0.0);
            }
            rdmsBomTree.setSubprojectId(bomTreeItem.getSubprojectId());
            rdmsBomTree.setProjectId(bomTreeItem.getProjectId());
            bomTreeCollect.add(rdmsBomTree);
            if(!CollectionUtils.isEmpty(bomTreeItem.getChildren())){
                iterationCollectMainBomItem(bomTreeItem.getChildren(), bomTreeCollect, deep+1, bomTreeItem.getId());
            }
        }
    }

    public List<RdmsBomTree> getBomTreeListBySubprojectId(String subprojectId){
        RdmsBomTreeExample bomTreeExample = new RdmsBomTreeExample();
        bomTreeExample.setOrderByClause("create_time asc");
        bomTreeExample.createCriteria().andSubprojectIdEqualTo(subprojectId).andDeletedEqualTo(0);
        List<RdmsBomTree> rdmsBomTrees = rdmsBomTreeMapper.selectByExample(bomTreeExample);
        return rdmsBomTrees;
    }

    public List<RdmsBomTreeMain> getBomTreeMainListByProjectId(String projectId){
        RdmsBomTreeMainExample bomTreeExample = new RdmsBomTreeMainExample();
        bomTreeExample.setOrderByClause("create_time asc");
        bomTreeExample.createCriteria().andProjectIdEqualTo(projectId).andDeletedEqualTo(0);
        List<RdmsBomTreeMain> rdmsBomTreeMains = rdmsBomTreeMainMapper.selectByExample(bomTreeExample);
        return rdmsBomTreeMains;
    }

    public RdmsBomTree selectByPrimaryKey(String id){
        return rdmsBomTreeMapper.selectByPrimaryKey(id);
    }

    public RdmsBomTreeMain selectByPrimaryKey_main(String id){
        return rdmsBomTreeMainMapper.selectByPrimaryKey(id);
    }

    /**
     * 保存
     */
    public String save(RdmsBomTree bomTree) {
        if(ObjectUtils.isEmpty(bomTree.getId())){
            return this.insert(bomTree);
        }else{
            RdmsBomTree rdmsBom = this.selectByPrimaryKey(bomTree.getId());
            if(ObjectUtils.isEmpty(rdmsBom)){
                return this.insert(bomTree);
            }else{
                return this.update(bomTree);
            }
        }
    }

    public String save_main(RdmsBomTreeMain bomTree) {
        if(ObjectUtils.isEmpty(bomTree.getId())){
            return this.insert_main(bomTree);
        }else{
            RdmsBomTreeMain rdmsBomTreeMain = this.selectByPrimaryKey_main(bomTree.getId());
            if(ObjectUtils.isEmpty(rdmsBomTreeMain)){
                return this.insert_main(bomTree);
            }else{
                return this.update_main(bomTree);
            }
        }
    }

    public String save_main_noUpdate(RdmsBomTreeMain bomTree) {
        if(ObjectUtils.isEmpty(bomTree.getId())){
            return this.insert_main(bomTree);
        }else{
            RdmsBomTreeMain rdmsBomTreeMain = this.selectByPrimaryKey_main(bomTree.getId());
            if(ObjectUtils.isEmpty(rdmsBomTreeMain)){
                return this.insert_main(bomTree);
            }else{
                return null;
            }
        }
    }

    private String insert(RdmsBomTree bomTree) {
        if(ObjectUtils.isEmpty(bomTree.getId())){
            bomTree.setId(UuidUtil.getShortUuid());
        }
        RdmsBomTree rdmsBom = rdmsBomTreeMapper.selectByPrimaryKey(bomTree.getId());
        if(! ObjectUtils.isEmpty(rdmsBom)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            bomTree.setDeleted(0);
            bomTree.setCreateTime(new Date());
            rdmsBomTreeMapper.insert(bomTree);
            return bomTree.getId();
        }
    }

    private String insert_main(RdmsBomTreeMain bomTree) {
        if(ObjectUtils.isEmpty(bomTree.getId())){
            bomTree.setId(UuidUtil.getShortUuid());
        }
        RdmsBomTreeMain rdmsBomTreeMain = rdmsBomTreeMainMapper.selectByPrimaryKey(bomTree.getId());
        if(! ObjectUtils.isEmpty(rdmsBomTreeMain)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }else{
            bomTree.setDeleted(0);
            bomTree.setCreateTime(new Date());
            rdmsBomTreeMainMapper.insert(bomTree);
            return bomTree.getId();
        }
    }

    /**
     * 更新
     */
    public String update(RdmsBomTree bomTree) {
        if(ObjectUtils.isEmpty(bomTree.getId())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsBomTree rdmsBom = this.selectByPrimaryKey(bomTree.getId());
        if(ObjectUtils.isEmpty(rdmsBom)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            bomTree.setDeleted(0);
            rdmsBomTreeMapper.updateByPrimaryKey(bomTree);
            return bomTree.getId();
        }
    }

    public String update_main(RdmsBomTreeMain bomTree) {
        if(ObjectUtils.isEmpty(bomTree.getId())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsBomTreeMain rdmsBomTreeMain = this.selectByPrimaryKey_main(bomTree.getId());
        if(ObjectUtils.isEmpty(rdmsBomTreeMain)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            bomTree.setDeleted(0);
            rdmsBomTreeMainMapper.updateByPrimaryKey(bomTree);
            return bomTree.getId();
        }
    }

    public String updateByPrimaryKeySelective(RdmsBomTree bomTree){
        rdmsBomTreeMapper.updateByPrimaryKeySelective(bomTree);
        return bomTree.getId();
    }

    public String updateByPrimaryKeySelective_main(RdmsBomTreeMain bomTree){
        rdmsBomTreeMainMapper.updateByPrimaryKeySelective(bomTree);
        return bomTree.getId();
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsBomTree bomTree = rdmsBomTreeMapper.selectByPrimaryKey(id);
        if(!ObjectUtils.isEmpty(bomTree)){
            bomTree.setDeleted(1);
            this.update(bomTree);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

}
