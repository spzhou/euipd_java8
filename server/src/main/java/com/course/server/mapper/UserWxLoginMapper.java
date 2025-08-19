/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.UserWxLogin;
import com.course.server.domain.UserWxLoginExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserWxLoginMapper {
    long countByExample(UserWxLoginExample example);

    int deleteByExample(UserWxLoginExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserWxLogin record);

    int insertSelective(UserWxLogin record);

    List<UserWxLogin> selectByExample(UserWxLoginExample example);

    UserWxLogin selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserWxLogin record, @Param("example") UserWxLoginExample example);

    int updateByExample(@Param("record") UserWxLogin record, @Param("example") UserWxLoginExample example);

    int updateByPrimaryKeySelective(UserWxLogin record);

    int updateByPrimaryKey(UserWxLogin record);
}