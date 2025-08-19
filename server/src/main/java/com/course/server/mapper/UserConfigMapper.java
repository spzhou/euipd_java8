/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.UserConfig;
import com.course.server.domain.UserConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserConfigMapper {
    long countByExample(UserConfigExample example);

    int deleteByExample(UserConfigExample example);

    int deleteByPrimaryKey(String id);

    int insert(UserConfig record);

    int insertSelective(UserConfig record);

    List<UserConfig> selectByExample(UserConfigExample example);

    UserConfig selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") UserConfig record, @Param("example") UserConfigExample example);

    int updateByExample(@Param("record") UserConfig record, @Param("example") UserConfigExample example);

    int updateByPrimaryKeySelective(UserConfig record);

    int updateByPrimaryKey(UserConfig record);
}