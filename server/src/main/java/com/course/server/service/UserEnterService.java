/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service;

import com.course.server.domain.*;
import com.course.server.dto.*;
import com.course.server.enums.EnterStatusEnum;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.*;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserEnterService {

    private static final Logger LOG = LoggerFactory.getLogger(UserEnterService.class);

    @Resource
    private UserEnterMapper userEnterMapper;

    /**
     * 列表查询
     * 分页查询待审批的用户入驻申请列表
     * 
     * @param pageDto 分页查询对象
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        UserEnterExample userEnterExample = new UserEnterExample();
        UserEnterExample.Criteria criteria = userEnterExample.createCriteria();
        criteria.andStatusEqualTo(EnterStatusEnum.WAITING.getCode());//只列出没有经过审批的
        List<UserEnter> userEnterList = userEnterMapper.selectByExample(userEnterExample);
        PageInfo<UserEnter> pageInfo = new PageInfo<>(userEnterList);
        pageDto.setTotal(pageInfo.getTotal());
        List<UserEnterDto> userEnterDtoList = CopyUtil.copyList(userEnterList, UserEnterDto.class);

        pageDto.setList(userEnterDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     * 保存或更新用户入驻申请信息
     * 
     * @param userEnterDto 用户入驻数据传输对象
     */
    public void save(UserEnterDto userEnterDto) {
        UserEnter user = CopyUtil.copy(userEnterDto, UserEnter.class);
        if (ObjectUtils.isEmpty(userEnterDto.getId())) {
            this.insert(user);
        } else {
            this.update(user);
        }
    }
    /**
     * 审批用户入驻申请
     * 根据登录名更新用户入驻申请的状态
     * 
     * @param enterDto 入驻审批数据传输对象
     */
    public void approve(EnterDto enterDto) {
        UserEnterExample userEnterExample = new UserEnterExample();
        UserEnterExample.Criteria criteria = userEnterExample.createCriteria();
        criteria.andLoginNameEqualTo(enterDto.getLoginName());
        List<UserEnter> userEnters = userEnterMapper.selectByExample(userEnterExample);
        if(userEnters.size()>0){
            userEnters.get(0).setStatus(enterDto.getCode());
        }
        this.update(userEnters.get(0));
    }

    /**
     * 新增
     * 创建新的用户入驻申请记录
     * 
     * @param user 用户入驻对象
     * @throws BusinessException 登录名已存在异常
     */
    private void insert(UserEnter user) {
        user.setId(UuidUtil.getShortUuid());
        UserEnter userDb = this.selectByLoginName(user.getLoginName());
        if (userDb != null) {
            throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
        }
        userEnterMapper.insert(user);
    }

    /**
     * 更新
     * 更新用户入驻申请信息
     * 
     * @param user 用户入驻对象
     */
    private void update(UserEnter user) {
        UserEnterExample userEnterExample = new UserEnterExample();
        UserEnterExample.Criteria criteria = userEnterExample.createCriteria();
        criteria.andIdEqualTo(user.getId());
        userEnterMapper.updateByExampleSelective(user,userEnterExample);
    }
    /**
     * 编辑用户入驻信息
     * 更新用户入驻申请的名称信息
     * 
     * @param user 用户入驻对象
     */
    public void saveEdit(UserEnter user) {
        UserEnter tempUser = new UserEnter();
        tempUser.setId(user.getId());
        tempUser.setName(user.getName());

        UserEnterExample userEnterExample = new UserEnterExample();
        UserEnterExample.Criteria criteria = userEnterExample.createCriteria();
        criteria.andIdEqualTo(user.getId());
        userEnterMapper.updateByExampleSelective(tempUser,userEnterExample);
    }

    /**
     * 删除
     * 根据ID删除用户入驻申请记录
     * 
     * @param id 用户入驻申请ID
     */
    public void delete(String id) {

        UserEnterExample userEnterExample = new UserEnterExample();
        UserEnterExample.Criteria criteria = userEnterExample.createCriteria();
        criteria.andIdEqualTo(id);
        userEnterMapper.deleteByExample(userEnterExample);
    }

    /**
     * 根据登录名查询用户信息
     * 通过登录名查询用户入驻申请信息
     * 
     * @param loginName 登录名
     * @return 返回用户入驻对象，如果不存在则返回null
     */
    public UserEnter selectByLoginName(String loginName) {
        UserEnterExample userEnterExample = new UserEnterExample();
        userEnterExample.createCriteria().andLoginNameEqualTo(loginName);
        List<UserEnter> userEnterList = userEnterMapper.selectByExample(userEnterExample);
        if (CollectionUtils.isEmpty(userEnterList)) {
            return null;
        } else {
            return userEnterList.get(0);
        }
    }

}
