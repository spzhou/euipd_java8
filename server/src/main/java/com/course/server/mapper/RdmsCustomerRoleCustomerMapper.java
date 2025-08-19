/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsCustomerRoleCustomer;
import com.course.server.domain.RdmsCustomerRoleCustomerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsCustomerRoleCustomerMapper {
    long countByExample(RdmsCustomerRoleCustomerExample example);

    int deleteByExample(RdmsCustomerRoleCustomerExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsCustomerRoleCustomer record);

    int insertSelective(RdmsCustomerRoleCustomer record);

    List<RdmsCustomerRoleCustomer> selectByExample(RdmsCustomerRoleCustomerExample example);

    RdmsCustomerRoleCustomer selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsCustomerRoleCustomer record, @Param("example") RdmsCustomerRoleCustomerExample example);

    int updateByExample(@Param("record") RdmsCustomerRoleCustomer record, @Param("example") RdmsCustomerRoleCustomerExample example);

    int updateByPrimaryKeySelective(RdmsCustomerRoleCustomer record);

    int updateByPrimaryKey(RdmsCustomerRoleCustomer record);
}
