/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsCustomerRoleResource;
import com.course.server.domain.RdmsCustomerRoleResourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsCustomerRoleResourceMapper {
    long countByExample(RdmsCustomerRoleResourceExample example);

    int deleteByExample(RdmsCustomerRoleResourceExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsCustomerRoleResource record);

    int insertSelective(RdmsCustomerRoleResource record);

    List<RdmsCustomerRoleResource> selectByExample(RdmsCustomerRoleResourceExample example);

    RdmsCustomerRoleResource selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsCustomerRoleResource record, @Param("example") RdmsCustomerRoleResourceExample example);

    int updateByExample(@Param("record") RdmsCustomerRoleResource record, @Param("example") RdmsCustomerRoleResourceExample example);

    int updateByPrimaryKeySelective(RdmsCustomerRoleResource record);

    int updateByPrimaryKey(RdmsCustomerRoleResource record);
}