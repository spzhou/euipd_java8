/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.course.server.domain.*;
import com.course.server.dto.PageDto;
import com.course.server.dto.RdmsRoleDto;
import com.course.server.mapper.RdmsCustomerRoleMapper;
import com.course.server.mapper.RdmsCustomerRoleResourceMapper;
import com.course.server.mapper.RdmsCustomerRoleCustomerMapper;
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
public class RdmsCustomerRoleService {

    @Resource
    private RdmsCustomerRoleMapper rdmsCustomerRoleMapper;

    @Resource
    private RdmsCustomerRoleResourceMapper rdmsCustomerRoleResourceMapper;

    @Resource
    private RdmsCustomerRoleCustomerMapper rdmsCustomerRoleCustomerMapper;

    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsCustomerRoleExample roleExample = new RdmsCustomerRoleExample();
        roleExample.setOrderByClause("id asc");
        List<RdmsCustomerRole> roleList = rdmsCustomerRoleMapper.selectByExample(roleExample);
        PageInfo<RdmsCustomerRole> pageInfo = new PageInfo<>(roleList);
        pageDto.setTotal(pageInfo.getTotal());
        List<RdmsRoleDto> roleDtoList = CopyUtil.copyList(roleList, RdmsRoleDto.class);
        pageDto.setList(roleDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(RdmsRoleDto roleDto) {
        RdmsCustomerRole role = CopyUtil.copy(roleDto, RdmsCustomerRole.class);
        if (ObjectUtils.isEmpty(roleDto.getId())) {
            this.insert(role);
        } else {
            this.update(role);
        }
    }

    /**
     * 新增
     */
    private void insert(RdmsCustomerRole role) {
        role.setId(UuidUtil.getShortUuid());
        rdmsCustomerRoleMapper.insert(role);
    }

    /**
     * 更新
     */
    private void update(RdmsCustomerRole role) {
        rdmsCustomerRoleMapper.updateByPrimaryKey(role);
    }

    /**
     * 删除
     */
    @Transactional
    public void delete(String id) {
        //首先要删除role-user表中所有对应id的权限记录
        RdmsCustomerRoleCustomerExample roleCustomerExample = new RdmsCustomerRoleCustomerExample();
        RdmsCustomerRoleCustomerExample.Criteria criteria = roleCustomerExample.createCriteria();
        criteria.andRoleIdEqualTo(id);
        List<RdmsCustomerRoleCustomer> roleCustomerList = rdmsCustomerRoleCustomerMapper.selectByExample(roleCustomerExample);
        for(RdmsCustomerRoleCustomer roleUser: roleCustomerList){
            rdmsCustomerRoleCustomerMapper.deleteByPrimaryKey(roleUser.getId());
        }
        rdmsCustomerRoleMapper.deleteByPrimaryKey(id);
    }



    /**
     * 按角色保存资源
     */
    public void saveResource(RdmsRoleDto roleDto) {
        String roleId = roleDto.getId();
        List<String> resourceIds = roleDto.getResourceIds();
        // 清空库中所有的当前角色下的记录
        RdmsCustomerRoleResourceExample example = new RdmsCustomerRoleResourceExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        rdmsCustomerRoleResourceMapper.deleteByExample(example);

        // 保存角色资源
        for (int i = 0; i < resourceIds.size(); i++) {
            RdmsCustomerRoleResource roleResource = new RdmsCustomerRoleResource();
            roleResource.setId(UuidUtil.getShortUuid());
            roleResource.setRoleId(roleId);
            roleResource.setResourceId(resourceIds.get(i));
            rdmsCustomerRoleResourceMapper.insert(roleResource);
        }
    }

    /**
     * 按角色加载资源
     * @param roleId
     */
    public List<String> listResource(String roleId) {
        RdmsCustomerRoleResourceExample example = new RdmsCustomerRoleResourceExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        List<RdmsCustomerRoleResource> roleResourceList = rdmsCustomerRoleResourceMapper.selectByExample(example);
        List<String> resourceIdList = new ArrayList<>();
        for (int i = 0, l = roleResourceList.size(); i < l; i++) {
            resourceIdList.add(roleResourceList.get(i).getResourceId());
        }
        return resourceIdList;
    }

    /**
     * 按角色保存用户
     */
    public void saveUser(RdmsRoleDto roleDto) {
        String roleId = roleDto.getId();
        List<String> userIdList = roleDto.getUserIds();
        // 清空库中所有的当前角色下的记录
        RdmsCustomerRoleCustomerExample example = new RdmsCustomerRoleCustomerExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        rdmsCustomerRoleCustomerMapper.deleteByExample(example);

        // 保存角色用户
        for (int i = 0; i < userIdList.size(); i++) {
            RdmsCustomerRoleCustomer roleUser = new RdmsCustomerRoleCustomer();
            roleUser.setId(UuidUtil.getShortUuid());
            roleUser.setRoleId(roleId);
            roleUser.setCustomerId(userIdList.get(i));
            rdmsCustomerRoleCustomerMapper.insert(roleUser);
        }
    }

    /**
     * 按角色加载用户
     * @param roleId
     */
    public List<String> listUser(String roleId) {
        RdmsCustomerRoleCustomerExample example = new RdmsCustomerRoleCustomerExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        List<RdmsCustomerRoleCustomer> roleCustomerList = rdmsCustomerRoleCustomerMapper.selectByExample(example);
        List<String> userIdList = new ArrayList<>();
        for (int i = 0, l = roleCustomerList.size(); i < l; i++) {
            userIdList.add(roleCustomerList.get(i).getCustomerId());
        }
        return userIdList;
    }

    /**
     * 根据用户的ID查询用户的角色列表
     * @param customerId
     * @return roleIdList
     */
    public List<String> getCustomerRole(String customerId) {
        RdmsCustomerRoleCustomerExample example = new RdmsCustomerRoleCustomerExample();
        example.createCriteria().andCustomerIdEqualTo(customerId);
        List<RdmsCustomerRoleCustomer> roleCustomerList = rdmsCustomerRoleCustomerMapper.selectByExample(example);
        List<String> roleIdList = new ArrayList<>();
        for (int i = 0, l = roleCustomerList.size(); i < l; i++) {
            roleIdList.add(roleCustomerList.get(i).getRoleId());
        }
        return roleIdList;
    }


}
