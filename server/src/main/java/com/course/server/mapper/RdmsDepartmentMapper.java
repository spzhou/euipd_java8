/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsDepartment;
import com.course.server.domain.RdmsDepartmentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsDepartmentMapper {
    long countByExample(RdmsDepartmentExample example);

    int deleteByExample(RdmsDepartmentExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsDepartment record);

    int insertSelective(RdmsDepartment record);

    List<RdmsDepartment> selectByExample(RdmsDepartmentExample example);

    RdmsDepartment selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsDepartment record, @Param("example") RdmsDepartmentExample example);

    int updateByExample(@Param("record") RdmsDepartment record, @Param("example") RdmsDepartmentExample example);

    int updateByPrimaryKeySelective(RdmsDepartment record);

    int updateByPrimaryKey(RdmsDepartment record);
}