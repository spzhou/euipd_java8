/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsCustomer;
import com.course.server.domain.RdmsCustomerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsCustomerMapper {
    long countByExample(RdmsCustomerExample example);

    int deleteByExample(RdmsCustomerExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsCustomer record);

    int insertSelective(RdmsCustomer record);

    List<RdmsCustomer> selectByExample(RdmsCustomerExample example);

    RdmsCustomer selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsCustomer record, @Param("example") RdmsCustomerExample example);

    int updateByExample(@Param("record") RdmsCustomer record, @Param("example") RdmsCustomerExample example);

    int updateByPrimaryKeySelective(RdmsCustomer record);

    int updateByPrimaryKey(RdmsCustomer record);
}