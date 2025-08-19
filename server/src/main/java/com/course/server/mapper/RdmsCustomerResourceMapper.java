/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsCustomerResource;
import com.course.server.domain.RdmsCustomerResourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsCustomerResourceMapper {
    long countByExample(RdmsCustomerResourceExample example);

    int deleteByExample(RdmsCustomerResourceExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsCustomerResource record);

    int insertSelective(RdmsCustomerResource record);

    List<RdmsCustomerResource> selectByExample(RdmsCustomerResourceExample example);

    RdmsCustomerResource selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsCustomerResource record, @Param("example") RdmsCustomerResourceExample example);

    int updateByExample(@Param("record") RdmsCustomerResource record, @Param("example") RdmsCustomerResourceExample example);

    int updateByPrimaryKeySelective(RdmsCustomerResource record);

    int updateByPrimaryKey(RdmsCustomerResource record);
}