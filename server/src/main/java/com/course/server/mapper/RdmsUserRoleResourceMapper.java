/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsUserRoleResource;
import com.course.server.domain.RdmsUserRoleResourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsUserRoleResourceMapper {
    long countByExample(RdmsUserRoleResourceExample example);

    int deleteByExample(RdmsUserRoleResourceExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsUserRoleResource record);

    int insertSelective(RdmsUserRoleResource record);

    List<RdmsUserRoleResource> selectByExample(RdmsUserRoleResourceExample example);

    RdmsUserRoleResource selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsUserRoleResource record, @Param("example") RdmsUserRoleResourceExample example);

    int updateByExample(@Param("record") RdmsUserRoleResource record, @Param("example") RdmsUserRoleResourceExample example);

    int updateByPrimaryKeySelective(RdmsUserRoleResource record);

    int updateByPrimaryKey(RdmsUserRoleResource record);
}