/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsDepartment2;
import com.course.server.domain.RdmsDepartment2Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsDepartment2Mapper {
    long countByExample(RdmsDepartment2Example example);

    int deleteByExample(RdmsDepartment2Example example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsDepartment2 record);

    int insertSelective(RdmsDepartment2 record);

    List<RdmsDepartment2> selectByExample(RdmsDepartment2Example example);

    RdmsDepartment2 selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsDepartment2 record, @Param("example") RdmsDepartment2Example example);

    int updateByExample(@Param("record") RdmsDepartment2 record, @Param("example") RdmsDepartment2Example example);

    int updateByPrimaryKeySelective(RdmsDepartment2 record);

    int updateByPrimaryKey(RdmsDepartment2 record);
}