/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service;

import com.course.server.domain.UserWxLogin;
import com.course.server.domain.UserWxLoginExample;
import com.course.server.mapper.UserWxLoginMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserWxLoginService {

    private static final Logger LOG = LoggerFactory.getLogger(UserWxLoginService.class);

    @Resource
    private UserWxLoginMapper userWxLoginMapper;

    /**
     * 根据OpenID获取微信用户信息
     * 通过微信OpenID查询对应的用户登录信息
     * 
     * @param openId 微信OpenID
     * @return 返回用户微信登录信息，如果不存在则返回null
     */
    public UserWxLogin getUserByOpenId(String openId){
        UserWxLoginExample userWxLoginExample = new UserWxLoginExample();
        UserWxLoginExample.Criteria criteria = userWxLoginExample.createCriteria();
        criteria.andOpenidEqualTo(openId);
        List<UserWxLogin> userWxLogins = userWxLoginMapper.selectByExample(userWxLoginExample);
        if(userWxLogins.size()>0){
            return userWxLogins.get(0);
        }
        return null;
    }

    /**
     * 根据UnionID获取微信用户信息
     * 通过微信UnionID查询对应的用户登录信息
     * 
     * @param unionId 微信UnionID
     * @return 返回用户微信登录信息，如果不存在则返回null
     */
    public UserWxLogin getUserByUnionId(String unionId){
        UserWxLoginExample userWxLoginExample = new UserWxLoginExample();
        UserWxLoginExample.Criteria criteria = userWxLoginExample.createCriteria();
        criteria.andUnionidEqualTo(unionId);
        List<UserWxLogin> userWxLogins = userWxLoginMapper.selectByExample(userWxLoginExample);
        if(userWxLogins.size()>0){
            return userWxLogins.get(0);
        }
        return null;
    }

    /**
     * 根据登录名获取微信用户信息
     * 通过登录名查询对应的用户微信登录信息
     * 
     * @param loginName 登录名
     * @return 返回用户微信登录信息，如果不存在则返回null
     */
    public UserWxLogin getUserByLoginName(String loginName){
        UserWxLoginExample userWxLoginExample = new UserWxLoginExample();
        UserWxLoginExample.Criteria criteria = userWxLoginExample.createCriteria();
        criteria.andLoginNameEqualTo(loginName);
        List<UserWxLogin> userWxLogins = userWxLoginMapper.selectByExample(userWxLoginExample);
        if(userWxLogins.size()>0){
            return userWxLogins.get(0);
        }
        return null;
    }

    /**
     * 根据登录名更新用户微信信息
     * 通过登录名更新对应用户的微信登录信息
     * 
     * @param user 用户微信登录信息对象
     * @return 返回更新影响的记录数
     */
    public int updateUserInfoByLoginName(UserWxLogin user){
        UserWxLoginExample userWxLoginExample = new UserWxLoginExample();
        UserWxLoginExample.Criteria criteria = userWxLoginExample.createCriteria();
        criteria.andLoginNameEqualTo(user.getLoginName());

        int num = userWxLoginMapper.updateByExample(user, userWxLoginExample);
        if(num>0){
            return num;
        }else {
            return 0;
        }
    }

    /**
     * 插入用户微信信息
     * 新增用户微信登录信息记录
     * 
     * @param user 用户微信登录信息对象
     * @return 返回插入影响的记录数
     */
    public int insertUserInfo(UserWxLogin user){
        int num = userWxLoginMapper.insert(user);
        if(num>0){
            return num;
        }else {
            return 0;
        }
    }


}
