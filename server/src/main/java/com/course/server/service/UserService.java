/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.*;
import com.course.server.dto.*;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.ResourceMapper;
import com.course.server.mapper.RoleResourceMapper;
import com.course.server.mapper.RoleUserMapper;
import com.course.server.mapper.UserMapper;
import com.course.server.mapper.my.MyUserMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Resource
    private UserMapper userMapper;

    @Resource
    private MyUserMapper myUserMapper;

    @Resource
    private RoleUserMapper roleUserMapper;

    @Resource
    private RoleResourceMapper roleResourceMapper;

    @Resource
    private ResourceMapper resourceMapper;

    @Resource
    private ChannelService channelService;


    /**
     * 列表查询
     * 分页查询用户列表，包含频道信息
     * 
     * @param pageDto 分页查询对象
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        UserExample userExample = new UserExample();
        List<User> userList = userMapper.selectByExample(userExample);
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        pageDto.setTotal(pageInfo.getTotal());
        List<UserDto> userDtoList = CopyUtil.copyList(userList, UserDto.class);
        for(UserDto userDto : userDtoList){
            Channel channel = channelService.getChannelByLoginName(userDto.getLoginName());
            if(channel == null){
                continue;
            }
            userDto.setChannelPassword(channel.getChannelPassword());
            userDto.setChannelId(channel.getChannelId());
            userDto.setTeacherUrl(channel.getTeacherUrl());
            userDto.setShowUrl(channel.getShowUrl());
        }

        pageDto.setList(userDtoList);
    }

    /**
     * 列表查询
     * 查询所有用户列表
     * 
     * @return 返回用户列表
     */
    public List<User> listUser() {
        UserExample userExample = new UserExample();
        return userMapper.selectByExample(userExample);
    }

    /**
     * 保存，id有值时更新，无值时新增
     * 保存或更新用户信息
     * 
     * @param userDto 用户数据传输对象
     */
    public void save(UserDto userDto) {
        User user = CopyUtil.copy(userDto, User.class);
        User userDb = this.selectByLoginName(user.getLoginName());
        if(userDb != null){
            this.update(user);
        }else{
            this.insert(user);
        }
    }

    /**
     * 新增
     * 创建新用户记录
     * 
     * @param user 用户对象
     * @throws BusinessException 登录名已存在异常
     */
    private void insert(User user) {
        if(user.getId() == null){
            user.setId(UuidUtil.getShortUuid());
        }
        User userDb = this.selectByLoginName(user.getLoginName());
        if (userDb != null) {
            throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
        }
        userMapper.insert(user);
    }

    /**
     * 更新
     * 更新用户信息
     * 
     * @param user 用户对象
     */
    private void update(User user) {
        if(user.getPassword() == null){
            user.setPassword((DigestUtils.md5DigestAsHex(user.getId().getBytes())));
        }
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andLoginNameEqualTo(user.getLoginName());
        userMapper.updateByExampleSelective(user,userExample);
    }
    /**
     * 更新
     * 更新用户基本信息
     * 
     * @param user 用户对象
     */
    public void saveEdit(User user) {
        User tempUser = new User();
        tempUser.setId(user.getId());
        tempUser.setName(user.getName());

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andIdEqualTo(user.getId());
        userMapper.updateByExampleSelective(tempUser,userExample);
    }

    /**
     * 删除
     * 根据ID删除用户记录
     * 
     * @param id 用户ID
     */
    public void delete(String id) {
        //userMapper.deleteByPrimaryKey(id);

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andIdEqualTo(id);
        userMapper.deleteByExample(userExample);
    }

    /**
     * 根据登录名查询用户信息
     * 通过登录名查询用户详细信息
     * 
     * @param loginName 登录名
     * @return 返回用户对象，如果不存在则返回null
     */
    public User selectByLoginName(String loginName) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andLoginNameEqualTo(loginName);
        List<User> userList = userMapper.selectByExample(userExample);
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        } else {
            return userList.get(0);
        }
    }
     /**
     * 根据主键查询用户信息
     * 通过用户ID查询用户详细信息
     * 
     * @param id 用户ID
     * @return 返回用户对象，如果不存在则返回null
     */
     public User selectByPrimaryKey(String id) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(id);
        List<User> userList = userMapper.selectByExample(userExample);
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        } else {
            return userList.get(0);
        }
    }


    /**
     * 保存密码
     * 更新指定用户的密码
     * 
     * @param userDto 用户数据传输对象
     */
    public void savePassword(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setPassword(userDto.getPassword());
        //userMapper.updateByPrimaryKeySelective(user);

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andIdEqualTo(userDto.getId());
        userMapper.updateByExampleSelective(user,userExample);
    }

    /**
     * 登录
     * 用户登录验证，成功后设置用户权限
     * 
     * @param userDto 用户数据传输对象
     * @return 返回登录用户数据传输对象
     */
    public LoginUserDto login(UserDto userDto) {
        User user = selectByLoginName(userDto.getLoginName());
        if (user == null) {
            LOG.info("用户名不存在, {}", userDto.getLoginName());
            throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
        } else {
            if (user.getPassword().equals(userDto.getPassword())) {
                // 登录成功
                LoginUserDto loginUserDto = CopyUtil.copy(user, LoginUserDto.class);
                // 为登录用户读取权限
                setAuth(loginUserDto);
                return loginUserDto;
            } else {
                LOG.info("密码不对, 输入密码：{}, 数据库密码：{}", userDto.getPassword(), user.getPassword());
                throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
            }
        }
    }



    /**
     * 为登录用户读取权限
     * 设置用户的资源权限和请求权限
     * 
     * @param loginUserDto 登录用户数据传输对象
     */
    private void setAuth(LoginUserDto loginUserDto) {
        //List<ResourceDto> resourceDtoList = myUserMapper.findResources(loginUserDto.getId());
        //1. 通过user的id，查user_role表，得到userRoleIdList
        RoleUserExample roleUserExample = new RoleUserExample();
        RoleUserExample.Criteria criteria = roleUserExample.createCriteria();
        criteria.andUserIdEqualTo(loginUserDto.getId());
        List<RoleUser> roleUserList = roleUserMapper.selectByExample(roleUserExample);

        //2. 通过userRoleIdList，查role_resource表，得到userResourceIdlist
        List<RoleResource> roleResourceList = new ArrayList<>();
        for(RoleUser roleUser: roleUserList){
            RoleResourceExample roleResourceExample = new RoleResourceExample();
            RoleResourceExample.Criteria resCriteria = roleResourceExample.createCriteria();
            resCriteria.andRoleIdEqualTo(roleUser.getRoleId());
            List<RoleResource> roleResources = roleResourceMapper.selectByExample(roleResourceExample);
            for(RoleResource roleResource: roleResources){
                roleResourceList.add(roleResource);
            }
        }
        List<RoleResource> roleResourceList1 = roleResourceList.stream().distinct().collect(Collectors.toList());

        //3. 通过userResourceIdlist，查resource表，得到用户的resourceList
        List<ResourceAuth> resourceList = new ArrayList<>();
        for(RoleResource roleResource: roleResourceList1){
            ResourceExample resourceExample = new ResourceExample();
            ResourceExample.Criteria resCriteria = resourceExample.createCriteria();
            resCriteria.andIdEqualTo(roleResource.getResourceId());
            List<ResourceAuth> resourceAuthList = resourceMapper.selectByExample(resourceExample);
            for(ResourceAuth resourceAuth: resourceAuthList){
                resourceList.add(resourceAuth);
            }
        }
        List<ResourceAuth> resourceList1 = resourceList.stream().distinct().collect(Collectors.toList());
        //4. 组装数据
        List<ResourceDto> resourceDtoList = new ArrayList<>();
        for(ResourceAuth resourceAuth : resourceList1){
            ResourceDto resourceDto = new ResourceDto();
            BeanUtils.copyProperties(resourceAuth, resourceDto);
            resourceDtoList.add(resourceDto);
        }

        loginUserDto.setResources(resourceDtoList);

        // 整理所有有权限的请求，用于接口拦截
        HashSet<String> requestSet = new HashSet<>();
        if (!CollectionUtils.isEmpty(resourceDtoList)) {
            for (int i = 0, l = resourceDtoList.size(); i < l; i++) {
                ResourceDto resourceDto = resourceDtoList.get(i);
                String arrayString = resourceDto.getRequest();
                List<String> requestList = JSON.parseArray(arrayString, String.class);
                if (!CollectionUtils.isEmpty(requestList)) {
                    requestSet.addAll(requestList);
                }
            }
        }
        LOG.info("有权限的请求：{}", requestSet);
        loginUserDto.setRequests(requestSet);
    }

    /**
     * 按手机号查找
     * 根据登录名查找用户并转换为登录用户数据传输对象
     * 
     * @param loginName 登录名
     * @return 返回登录用户数据传输对象
     */
    public LoginUserDto findByLoginName(String loginName) {
        User user = this.selectByLoginName(loginName);
        return CopyUtil.copy(user, LoginUserDto.class);
    }

    /**
     * 重置密码
     * 重置指定用户的密码
     * 
     * @param userDto 用户数据传输对象
     * @throws BusinessException 用户不存在异常
     */
    public void resetPassword(UserDto userDto) throws BusinessException {
        User userDb = this.selectByLoginName(userDto.getLoginName());
        if (userDb == null) {
            throw new BusinessException(BusinessExceptionCode.MEMBER_NOT_EXIST);
        } else {
            User user = new User();
            user.setPassword(userDto.getPassword());
            UserExample userExample = new UserExample();
            UserExample.Criteria criteria = userExample.createCriteria();
            criteria.andLoginNameEqualTo(userDto.getLoginName());
            userMapper.updateByExampleSelective(user,userExample);
        }
    }

}
