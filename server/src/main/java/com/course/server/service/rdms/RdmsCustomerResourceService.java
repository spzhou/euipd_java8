/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.RdmsCustomerResource;
import com.course.server.domain.RdmsCustomerResourceExample;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResourceDto;
import com.course.server.mapper.RdmsCustomerResourceMapper;
import com.course.server.util.CopyUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class RdmsCustomerResourceService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsCustomerResourceService.class);

    @javax.annotation.Resource
    private RdmsCustomerResourceMapper rdmsCustomerResourceMapper;

    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsCustomerResourceExample resourceExample = new RdmsCustomerResourceExample();
        List<RdmsCustomerResource> resourceList = rdmsCustomerResourceMapper.selectByExample(resourceExample);
        PageInfo<RdmsCustomerResource> pageInfo = new PageInfo<>(resourceList);
        pageDto.setTotal(pageInfo.getTotal());
        List<ResourceDto> resourceDtoList = CopyUtil.copyList(resourceList, ResourceDto.class);
        pageDto.setList(resourceDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(ResourceDto resourceDto) {
        RdmsCustomerResource resource = CopyUtil.copy(resourceDto, RdmsCustomerResource.class);
        if (ObjectUtils.isEmpty(resourceDto.getId())) {
            this.insert(resource);
        } else {
            this.update(resource);
        }
    }

    /**
     * 新增，ID是自定义好的，不是自动生成的
     */
    private void insert(RdmsCustomerResource resource) {
        rdmsCustomerResourceMapper.insert(resource);
    }

    /**
     * 更新
     */
    private void update(RdmsCustomerResource resource) {
        rdmsCustomerResourceMapper.updateByPrimaryKey(resource);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        rdmsCustomerResourceMapper.deleteByPrimaryKey(id);
    }



    /**
     * 保存资源树
     * @param json
     */
    @Transactional
    public void saveJson(String json) {
        List<ResourceDto> jsonList = JSON.parseArray(json, ResourceDto.class);
        List<ResourceDto> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jsonList)) {
            for (ResourceDto d: jsonList) {
                d.setParent("");
                add(list, d);
            }
        }
//        LOG.info("共{}条", list.size());

        rdmsCustomerResourceMapper.deleteByExample(null);
        for (int i = 0; i < list.size(); i++) {
            this.insert(CopyUtil.copy(list.get(i), RdmsCustomerResource.class));
        }
    }

    /**
     * 递归，将树型结构的节点全部取出来，放到list
     * @param list
     * @param dto
     */
    public void add(List<ResourceDto> list, ResourceDto dto) {
        list.add(dto);
        if (!CollectionUtils.isEmpty(dto.getChildren())) {
            for (ResourceDto d: dto.getChildren()) {
                d.setParent(dto.getId());
                add(list, d);
            }
        }
    }

    /**
     * 按约定将列表转成树
     * 要求：ID要正序排列
     * @return
     */
    public List<ResourceDto> loadTree() {
        RdmsCustomerResourceExample example = new RdmsCustomerResourceExample();
        example.setOrderByClause("id asc");
        List<RdmsCustomerResource> resourceList = rdmsCustomerResourceMapper.selectByExample(example);
        //从resourceList中排除以"51"开头的资源
        List<RdmsCustomerResource> resourceListOut = new ArrayList<>();
        for(RdmsCustomerResource resource : resourceList){
            if(! resource.getId().substring(0,2).equals("51")){
                resourceListOut.add(resource);
            }
        }

        List<ResourceDto> resourceDtoList = CopyUtil.copyList(resourceListOut, ResourceDto.class);
        for (int i = resourceDtoList.size() - 1; i >= 0; i--) {
            // 当前要移动的记录
            ResourceDto child = resourceDtoList.get(i);
//            child.setLabel(child.getName());  //设置Label

            // 如果当前节点没有父节点，则不用往下了
            if (ObjectUtils.isEmpty(child.getParent())) {
                continue;
            }
            // 查找父节点
            for (int j = i - 1; j >= 0; j--) {
                ResourceDto parent = resourceDtoList.get(j);
                if (child.getParent().equals(parent.getId())) {
                    if (CollectionUtils.isEmpty(parent.getChildren())) {
                        parent.setChildren(new ArrayList<>());
                    }
                    // 添加到最前面，否则会变成倒序，因为循环是从后往前循环的
                    parent.getChildren().add(0, child);

                    // 子节点找到父节点后，删除列表中的子节点
                    resourceDtoList.remove(child);
                }
            }
        }
        return resourceDtoList;
    }
}
