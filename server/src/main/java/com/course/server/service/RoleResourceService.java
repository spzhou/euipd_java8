/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service;

import com.course.server.domain.RoleResource;
import com.course.server.domain.RoleResourceExample;
import com.course.server.dto.RdmsRoleResourceDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.RoleResourceMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleResourceService {

    @Resource
    private RoleResourceMapper roleResourceMapper;

    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RoleResourceExample roleResourceExample = new RoleResourceExample();
        List<RoleResource> roleResourceList = roleResourceMapper.selectByExample(roleResourceExample);
        PageInfo<RoleResource> pageInfo = new PageInfo<>(roleResourceList);
        pageDto.setTotal(pageInfo.getTotal());
        List<RdmsRoleResourceDto> roleResourceDtoList = CopyUtil.copyList(roleResourceList, RdmsRoleResourceDto.class);
        pageDto.setList(roleResourceDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(RdmsRoleResourceDto roleResourceDto) {
        RoleResource roleResource = CopyUtil.copy(roleResourceDto, RoleResource.class);
        if (ObjectUtils.isEmpty(roleResourceDto.getId())) {
            this.insert(roleResource);
        } else {
            this.update(roleResource);
        }
    }

    /**
     * 新增
     */
    private void insert(RoleResource roleResource) {
        roleResource.setId(UuidUtil.getShortUuid());
        roleResourceMapper.insert(roleResource);
    }

    /**
     * 更新
     */
    private void update(RoleResource roleResource) {
        roleResourceMapper.updateByPrimaryKey(roleResource);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        roleResourceMapper.deleteByPrimaryKey(id);
    }
}
