/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service;

import com.course.server.domain.*;
import com.course.server.dto.*;
import com.course.server.enums.MemberActionEnum;
import com.course.server.enums.PageCategoryEnum;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class WxMemberService {

    private static final Logger LOG = LoggerFactory.getLogger(WxMemberService.class);

    @Resource
    private UserWxLoginMapper userWxLoginMapper;

    /**
     * 登录
     * 微信用户登录，如果用户已存在则更新信息，否则创建新用户
     * 
     * @param wxLoginDto 微信登录数据传输对象
     * @return 返回用户微信登录信息
     */
    @Transactional
    public UserWxLogin login(WxLoginDto wxLoginDto) {
        UserWxLogin userWxLogin = new UserWxLogin();
        UserWxLoginExample userWxLoginExample = new UserWxLoginExample();
        UserWxLoginExample.Criteria criteria = userWxLoginExample.createCriteria();
        criteria.andOpenidEqualTo(wxLoginDto.getOpenid());
        List<UserWxLogin> userWxLogins = userWxLoginMapper.selectByExample(userWxLoginExample);
        if (userWxLogins.size() > 0) {
            //更新最新登录时间和登录次数
            userWxLogin = userWxLogins.get(0);
            userWxLogin.setNickname(wxLoginDto.getNickName());
            userWxLogin.setHeadimgurl(wxLoginDto.getAvatarUrl());
            userWxLogin.setCountry(wxLoginDto.getCountry());
            userWxLogin.setProvince(wxLoginDto.getProvince());
            userWxLogin.setCity(wxLoginDto.getCity());
            userWxLogin.setUnionid(wxLoginDto.getUnionid());
            userWxLogin.setLoginTime(new Date());
            userWxLogin.setLoginTimes(userWxLogin.getLoginTimes() + 1);
            userWxLoginMapper.updateByPrimaryKey(userWxLogin);
        } else {
            userWxLogin.setNickname(wxLoginDto.getNickName());
            userWxLogin.setHeadimgurl(wxLoginDto.getAvatarUrl());
            userWxLogin.setCountry(wxLoginDto.getCountry());
            userWxLogin.setProvince(wxLoginDto.getProvince());
            userWxLogin.setCity(wxLoginDto.getCity());
            userWxLogin.setOpenid(wxLoginDto.getOpenid());
            userWxLogin.setUnionid(wxLoginDto.getUnionid());
            userWxLogin.setLoginTime(new Date());
            userWxLogin.setLoginTimes(1L);
            userWxLoginMapper.insert(userWxLogin);
        }
        return userWxLogin;
    }

}
