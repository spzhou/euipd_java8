/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service;

import com.course.server.domain.*;
import com.course.server.dto.PageDto;
import com.course.server.dto.RoleDto;
import com.course.server.mapper.RoleMapper;
import com.course.server.mapper.RoleResourceMapper;
import com.course.server.mapper.RoleUserMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleResourceMapper roleResourceMapper;

    @Resource
    private RoleUserMapper roleUserMapper;

    /**
     * 列表查询
     * 分页查询角色列表
     * 
     * @param pageDto 分页查询对象
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RoleExample roleExample = new RoleExample();
        List<Role> roleList = roleMapper.selectByExample(roleExample);
        PageInfo<Role> pageInfo = new PageInfo<>(roleList);
        pageDto.setTotal(pageInfo.getTotal());
        List<RoleDto> roleDtoList = CopyUtil.copyList(roleList, RoleDto.class);
        pageDto.setList(roleDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     * 保存或更新角色信息
     * 
     * @param roleDto 角色数据传输对象
     */
    public void save(RoleDto roleDto) {
        Role role = CopyUtil.copy(roleDto, Role.class);
        if (ObjectUtils.isEmpty(roleDto.getId())) {
            this.insert(role);
        } else {
            this.update(role);
        }
    }

    /**
     * 新增
     * 创建新的角色记录
     * 
     * @param role 角色对象
     */
    private void insert(Role role) {
        role.setId(UuidUtil.getShortUuid());
        roleMapper.insert(role);
    }

    /**
     * 更新
     * 更新角色信息
     * 
     * @param role 角色对象
     */
    private void update(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }

    /**
     * 删除
     * 根据ID删除角色及相关权限记录
     * 
     * @param id 角色ID
     */
    @Transactional
    public void delete(String id) {
        //首先要删除role-user表中所有对应id的权限记录
        RoleUserExample roleUserExample = new RoleUserExample();
        RoleUserExample.Criteria criteria = roleUserExample.createCriteria();
        criteria.andRoleIdEqualTo(id);
        List<RoleUser> roleUserList = roleUserMapper.selectByExample(roleUserExample);
        for(RoleUser roleUser: roleUserList){
            roleUserMapper.deleteByPrimaryKey(roleUser.getId());
        }
        roleMapper.deleteByPrimaryKey(id);
    }



    /**
     * 按角色保存资源
     * 为角色配置资源权限，先清空再重新配置
     * 
     * @param roleDto 角色数据传输对象，包含资源ID列表
     */
    public void saveResource(RoleDto roleDto) {
        String roleId = roleDto.getId();
        List<String> resourceIds = roleDto.getResourceIds();
        // 清空库中所有的当前角色下的记录
        RoleResourceExample example = new RoleResourceExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        roleResourceMapper.deleteByExample(example);

        // 保存角色资源
        for (int i = 0; i < resourceIds.size(); i++) {
            RoleResource roleResource = new RoleResource();
            roleResource.setId(UuidUtil.getShortUuid());
            roleResource.setRoleId(roleId);
            roleResource.setResourceId(resourceIds.get(i));
            roleResourceMapper.insert(roleResource);
        }
    }

    /**
     * 按角色加载资源
     * 根据角色ID查询该角色下的所有资源ID列表
     * 
     * @param roleId 角色ID
     * @return 返回资源ID列表
     */
    public List<String> listResource(String roleId) {
        RoleResourceExample example = new RoleResourceExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        List<RoleResource> roleResourceList = roleResourceMapper.selectByExample(example);
        List<String> resourceIdList = new ArrayList<>();
        for (int i = 0, l = roleResourceList.size(); i < l; i++) {
            resourceIdList.add(roleResourceList.get(i).getResourceId());
        }
        return resourceIdList;
    }

    /**
     * 按角色保存用户
     * 为角色分配用户，先清空再重新分配
     * 
     * @param roleDto 角色数据传输对象，包含用户ID列表
     */
    public void saveUser(RoleDto roleDto) {
        String roleId = roleDto.getId();
        List<String> userIdList = roleDto.getUserIds();
        // 清空库中所有的当前角色下的记录
        RoleUserExample example = new RoleUserExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        roleUserMapper.deleteByExample(example);

        // 保存角色用户
        for (int i = 0; i < userIdList.size(); i++) {
            RoleUser roleUser = new RoleUser();
            roleUser.setId(UuidUtil.getShortUuid());
            roleUser.setRoleId(roleId);
            roleUser.setUserId(userIdList.get(i));
            roleUserMapper.insert(roleUser);
        }
    }

    /**
     * 按角色加载用户
     * 根据角色ID查询该角色下的所有用户ID列表
     * 
     * @param roleId 角色ID
     * @return 返回用户ID列表
     */
    public List<String> listUser(String roleId) {
        RoleUserExample example = new RoleUserExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        List<RoleUser> roleUserList = roleUserMapper.selectByExample(example);
        List<String> userIdList = new ArrayList<>();
        for (int i = 0, l = roleUserList.size(); i < l; i++) {
            userIdList.add(roleUserList.get(i).getUserId());
        }
        return userIdList;
    }
}
