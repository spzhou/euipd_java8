/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsUserRoleUser;
import com.course.server.domain.RdmsUserRoleUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsUserRoleUserMapper {
    long countByExample(RdmsUserRoleUserExample example);

    int deleteByExample(RdmsUserRoleUserExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsUserRoleUser record);

    int insertSelective(RdmsUserRoleUser record);

    List<RdmsUserRoleUser> selectByExample(RdmsUserRoleUserExample example);

    RdmsUserRoleUser selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsUserRoleUser record, @Param("example") RdmsUserRoleUserExample example);

    int updateByExample(@Param("record") RdmsUserRoleUser record, @Param("example") RdmsUserRoleUserExample example);

    int updateByPrimaryKeySelective(RdmsUserRoleUser record);

    int updateByPrimaryKey(RdmsUserRoleUser record);
}