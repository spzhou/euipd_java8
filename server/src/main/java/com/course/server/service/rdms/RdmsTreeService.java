/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.course.server.dto.rdms.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RdmsTreeService<T> {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsTreeService.class);

    /**
     * 只要给出一个带有Tree信息的List, 本函数就能构建一棵树
     * @param treeList
     * @return
     */
    public RdmsHmiTree<T> getTree(List<RdmsHmiTree<T>> treeList){
        //构建树根对象
        RdmsHmiTree<T> treeRoot = new RdmsHmiTree<>();
        treeRoot.setId(null);
        treeRoot.setLabel("根对象");
        treeRoot.setValue(null);
        treeRoot.setParent(null);
        treeRoot.setChildren(null);
        treeRoot.setDeep(null);

        //根据deep逆序排列
        List<RdmsHmiTree<T>> sortedTreeList = treeList.stream().sorted(Comparator.comparingInt(RdmsHmiTree<T>::getDeep).reversed()).collect(Collectors.toList());

        //逐层deep向下装配
        if(! CollectionUtils.isEmpty(sortedTreeList)){
            for(int i = sortedTreeList.get(0).getDeep(); i > 0; i--){
                RdmsHmiTree<T> tempParent = new RdmsHmiTree<>();
                for(int j = 0 ; j < sortedTreeList.size(); ){
                    RdmsHmiTree<T> treeItem = sortedTreeList.get(j);
                    if(treeItem.getDeep() == i){
                        //找出ID为 这个 parentID的 列表项
                        List<RdmsHmiTree<T>> parentItems = sortedTreeList.stream().filter(record -> record.getId().equals(treeItem.getParent())).collect(Collectors.toList());
                        if(! CollectionUtils.isEmpty(parentItems) && ! parentItems.get(0).getId().equals(treeItem.getId())){  //子记录和父记录不能是同一条记录
                            tempParent = parentItems.get(0);
                            if(ObjectUtils.isEmpty(tempParent.getChildren())){
                                List<RdmsHmiTree<T>> rdmsHmiTrees = new ArrayList<>();
                                rdmsHmiTrees.add(treeItem);
                                tempParent.setChildren(rdmsHmiTrees);
                                sortedTreeList.remove(treeItem);
                                j = 0;
                            }else{
                                List<RdmsHmiTree<T>> rdmsHmiTrees = tempParent.getChildren();
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
     * 只要给出一个带有Tree信息的List, 和一个子树的跟对象, 本函数可以得到相应的子树
     * @param treeList 用于生产树的List
     * @param rootId 作为子树的跟对象的ID
     * @param rootDeep 是查询子树的deep值
     * @return 1. 可以通过返回值得到子树
     *         2. 可以通过传入的子树的根对象, 得到整颗子树.
     */
    public RdmsHmiTree<T> getSubTree(List<RdmsHmiTree<T>> treeList, String rootId, Integer rootDeep){

        //根据deep逆序排列
        List<RdmsHmiTree<T>> sortedTreeList = treeList.stream().sorted(Comparator.comparingInt(RdmsHmiTree<T>::getDeep).reversed()).collect(Collectors.toList());

        //逐层deep向下装配
        if(!CollectionUtils.isEmpty(sortedTreeList)){
            for(int i = sortedTreeList.get(0).getDeep(); i > rootDeep; i--){
                RdmsHmiTree<T> tempParent = new RdmsHmiTree<>();
                for(int j = 0 ; j < sortedTreeList.size(); ){
                    RdmsHmiTree<T> treeItem = sortedTreeList.get(j);
                    if(treeItem.getDeep() == i){
                        //找出ID为 这个 parentID的 列表项
                        List<RdmsHmiTree<T>> parentItems = sortedTreeList.stream().filter(record -> record.getId().equals(treeItem.getParent())).collect(Collectors.toList());
                        if(! CollectionUtils.isEmpty(parentItems)){
                            tempParent = parentItems.get(0);
                            if(ObjectUtils.isEmpty(tempParent.getChildren()) && ! parentItems.get(0).getId().equals(treeItem.getId())){
                                List<RdmsHmiTree<T>> rdmsHmiTrees = new ArrayList<>();
                                rdmsHmiTrees.add(treeItem);
                                tempParent.setChildren(rdmsHmiTrees);
                                sortedTreeList.remove(treeItem);
                                j = 0;
                            }else{
                                List<RdmsHmiTree<T>> rdmsHmiTrees = tempParent.getChildren();
                                rdmsHmiTrees.add(treeItem);
                                List<RdmsHmiTree<T>> collect = rdmsHmiTrees.stream().sorted(Comparator.comparingInt(RdmsHmiTree<T>::getCreateTimeStamp)).collect(Collectors.toList());
                                tempParent.setChildren(collect);
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
            List<RdmsHmiTree<T>> respTree = sortedTreeList.stream().filter(subTree -> subTree.getId().equals(rootId)).collect(Collectors.toList());
            return respTree.get(0);
        }

        return null;
    }

}
