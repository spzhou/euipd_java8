/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsCustomerRole;
import com.course.server.domain.RdmsCustomerRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsCustomerRoleMapper {
    long countByExample(RdmsCustomerRoleExample example);

    int deleteByExample(RdmsCustomerRoleExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsCustomerRole record);

    int insertSelective(RdmsCustomerRole record);

    List<RdmsCustomerRole> selectByExample(RdmsCustomerRoleExample example);

    RdmsCustomerRole selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsCustomerRole record, @Param("example") RdmsCustomerRoleExample example);

    int updateByExample(@Param("record") RdmsCustomerRole record, @Param("example") RdmsCustomerRoleExample example);

    int updateByPrimaryKeySelective(RdmsCustomerRole record);

    int updateByPrimaryKey(RdmsCustomerRole record);
}