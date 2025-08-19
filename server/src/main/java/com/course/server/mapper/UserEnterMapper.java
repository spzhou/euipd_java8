/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.UserEnter;
import com.course.server.domain.UserEnterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserEnterMapper {
    long countByExample(UserEnterExample example);

    int deleteByExample(UserEnterExample example);

    int deleteByPrimaryKey(String id);

    int insert(UserEnter record);

    int insertSelective(UserEnter record);

    List<UserEnter> selectByExample(UserEnterExample example);

    UserEnter selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") UserEnter record, @Param("example") UserEnterExample example);

    int updateByExample(@Param("record") UserEnter record, @Param("example") UserEnterExample example);

    int updateByPrimaryKeySelective(UserEnter record);

    int updateByPrimaryKey(UserEnter record);
}