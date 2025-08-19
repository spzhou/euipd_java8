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
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.*;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RdmsUserRoleService {

    @Resource
    private RdmsUserRoleMapper rdmsUserRoleMapper;

    @Resource
    private RdmsUserRoleResourceMapper rdmsUserRoleResourceMapper;

    @Resource
    private RdmsUserRoleUserMapper rdmsUserRoleUserMapper;
    @Resource
    private RdmsCustomerService rdmsCustomerService;

    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsUserRoleExample roleExample = new RdmsUserRoleExample();
        roleExample.setOrderByClause("id asc");
        roleExample.createCriteria().andCustomerIdEqualTo(pageDto.getKeyWord());
        List<RdmsUserRole> roleList = rdmsUserRoleMapper.selectByExample(roleExample);
        PageInfo<RdmsUserRole> pageInfo = new PageInfo<>(roleList);
        pageDto.setTotal(pageInfo.getTotal());
        List<RdmsRoleDto> roleDtoList = CopyUtil.copyList(roleList, RdmsRoleDto.class);
        if(!CollectionUtils.isEmpty(roleDtoList)){
            for(RdmsRoleDto roleDto: roleDtoList){
                RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(roleDto.getCustomerId());
                if(!ObjectUtils.isEmpty(rdmsCustomer.getEnAuthEdit())){
                    roleDto.setEnAuthEdit(rdmsCustomer.getEnAuthEdit());
                }else{
                    roleDto.setEnAuthEdit(0);
                }
            }
        }
        pageDto.setList(roleDtoList);
    }

    public RdmsUserRole getUserRoleById(String id) {
        RdmsUserRoleExample roleExample = new RdmsUserRoleExample();
        roleExample.createCriteria().andIdEqualTo(id);
        return rdmsUserRoleMapper.selectByExample(roleExample).get(0);
    }

    public RdmsUserRole selectByPrimaryKey(Integer index){
        return rdmsUserRoleMapper.selectByPrimaryKey(index);
    }

    public String save(RdmsRoleDto roleDto) {
        RdmsUserRole role = CopyUtil.copy(roleDto, RdmsUserRole.class);
        if(ObjectUtils.isEmpty(role.getIndex())){
            return this.insert(role);
        }else{
            RdmsUserRole rdmsUserRole = this.selectByPrimaryKey(role.getIndex());
            if(ObjectUtils.isEmpty(rdmsUserRole)){
                return this.insert(role);
            }else{
                return this.update(role);
            }
        }
    }

    private String insert(RdmsUserRole role) {
        if(ObjectUtils.isEmpty(role.getId())){
            role.setId(UuidUtil.getShortUuid());
        }
        rdmsUserRoleMapper.insert(role);
        return role.getId();
    }

    public String update(RdmsUserRole role) {
        if(ObjectUtils.isEmpty(role.getIndex())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsUserRole rdmsUserRole = this.selectByPrimaryKey(role.getIndex());
        if(ObjectUtils.isEmpty(rdmsUserRole)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            rdmsUserRoleMapper.updateByPrimaryKey(role);
            return role.getId();
        }
    }

    @Transactional
    public void delete(String id) {
        //首先要删除role-user表中所有对应id的权限记录
        RdmsUserRoleUserExample roleUserExample = new RdmsUserRoleUserExample();
        RdmsUserRoleUserExample.Criteria criteria = roleUserExample.createCriteria();
        criteria.andRoleIdEqualTo(id);
        List<RdmsUserRoleUser> roleUserList = rdmsUserRoleUserMapper.selectByExample(roleUserExample);
        for(RdmsUserRoleUser roleUser: roleUserList){
            rdmsUserRoleUserMapper.deleteByPrimaryKey(roleUser.getId());
        }
        RdmsUserRoleExample roleExample = new RdmsUserRoleExample();
        roleExample.createCriteria().andIdEqualTo(id);
        rdmsUserRoleMapper.deleteByExample(roleExample);
    }

    /**
     * 按角色保存资源
     */
    public void saveResource(RdmsRoleDto roleDto) {
        String roleId = roleDto.getId();
        List<String> resourceIds = roleDto.getResourceIds();
        // 清空库中所有的当前角色下的记录
        RdmsUserRoleResourceExample example = new RdmsUserRoleResourceExample();
        example.createCriteria().andRoleIdEqualTo(roleId).andCustomerIdEqualTo(roleDto.getCustomerId());
        rdmsUserRoleResourceMapper.deleteByExample(example);

        // 保存角色资源
        for (int i = 0; i < resourceIds.size(); i++) {
            RdmsUserRoleResource roleResource = new RdmsUserRoleResource();
            roleResource.setId(UuidUtil.getShortUuid());
            roleResource.setRoleId(roleId);
            roleResource.setResourceId(resourceIds.get(i));
            roleResource.setCustomerId(roleDto.getCustomerId());
            rdmsUserRoleResourceMapper.insert(roleResource);
        }
    }

    /**
     * 保存授权信息
     */
    public void saveAuth(RdmsRoleDto roleDto) {
        // 清空当前user的所有权限
        RdmsUserRoleUserExample example = new RdmsUserRoleUserExample();
        example.createCriteria()
                .andUserIdEqualTo(roleDto.getUserId())
                .andCustomerIdEqualTo(roleDto.getCustomerId());
        rdmsUserRoleUserMapper.deleteByExample(example);

        // 保存角色资源
        for (String roleId : roleDto.getRoleIds()) {
            RdmsUserRoleUser roleUser = new RdmsUserRoleUser();
            roleUser.setId(UuidUtil.getShortUuid());
            roleUser.setRoleId(roleId);
            roleUser.setUserId(roleDto.getUserId());
            roleUser.setCustomerId(roleDto.getCustomerId());
            rdmsUserRoleUserMapper.insert(roleUser);
        }
    }

    /**
     * 按角色加载资源
     * @param roleId
     */
    public List<String> listResource(String roleId, String customerId) {
        RdmsUserRoleResourceExample example = new RdmsUserRoleResourceExample();
        example.createCriteria().andRoleIdEqualTo(roleId).andCustomerIdEqualTo(customerId);
        List<RdmsUserRoleResource> roleResourceList = rdmsUserRoleResourceMapper.selectByExample(example);
        List<String> resourceIdList = new ArrayList<>();
        for (int i = 0, l = roleResourceList.size(); i < l; i++) {
            resourceIdList.add(roleResourceList.get(i).getResourceId());
        }
        return resourceIdList;
    }

    /**
     * 按角色加载资源
     * @param customerId
     */
    public List<RdmsUserRole> getRoleByCustomer(String customerId) {
        RdmsUserRoleExample userRoleExample = new RdmsUserRoleExample();
        userRoleExample.createCriteria().andCustomerIdEqualTo(customerId);
        return rdmsUserRoleMapper.selectByExample(userRoleExample);
    }

    /**
     * 按角色加载资源
     * @param customerId
     */
    public List<RdmsUserRole> getRoleByCustomerAndId(String customerId, String id) {
        RdmsUserRoleExample userRoleExample = new RdmsUserRoleExample();
        userRoleExample.createCriteria().andCustomerIdEqualTo(customerId).andIdEqualTo(id);
        return rdmsUserRoleMapper.selectByExample(userRoleExample);
    }

    /**
     * 按角色加载资源
     * @param userId
     */
    public List<RdmsUserRoleUser> listUserAuthedRole(String customerId, String userId) {
        RdmsUserRoleUserExample userRoleUserExample = new RdmsUserRoleUserExample();
        userRoleUserExample.createCriteria().andCustomerIdEqualTo(customerId).andUserIdEqualTo(userId);
        return rdmsUserRoleUserMapper.selectByExample(userRoleUserExample);
    }

    /**
     * 按角色保存用户
     */
    public void saveUser(RdmsRoleDto roleDto) {
        String roleId = roleDto.getId();
        List<String> userIdList = roleDto.getUserIds();
        // 清空库中所有的当前角色下的记录
        RdmsUserRoleUserExample example = new RdmsUserRoleUserExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        rdmsUserRoleUserMapper.deleteByExample(example);

        // 保存角色用户
        for (int i = 0; i < userIdList.size(); i++) {
            RdmsUserRoleUser roleUser = new RdmsUserRoleUser();
            roleUser.setId(UuidUtil.getShortUuid());
            roleUser.setRoleId(roleId);
            roleUser.setUserId(userIdList.get(i));
            rdmsUserRoleUserMapper.insert(roleUser);
        }
    }

    /**
     * 按角色加载用户
     * @param roleId
     */
    public List<String> listUser(String roleId) {
        RdmsUserRoleUserExample example = new RdmsUserRoleUserExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        List<RdmsUserRoleUser> roleUserList = rdmsUserRoleUserMapper.selectByExample(example);
        List<String> userIdList = new ArrayList<>();
        for (int i = 0, l = roleUserList.size(); i < l; i++) {
            userIdList.add(roleUserList.get(i).getUserId());
        }
        return userIdList;
    }

    public List<RdmsUserRoleUser> getUserRole(String userId) {
        RdmsUserRoleUserExample example = new RdmsUserRoleUserExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<RdmsUserRoleUser> roleUserList = rdmsUserRoleUserMapper.selectByExample(example);
        return roleUserList;
    }

}
