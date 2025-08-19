/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsBudget;
import com.course.server.domain.RdmsBudgetExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsBudgetMapper {
    long countByExample(RdmsBudgetExample example);

    int deleteByExample(RdmsBudgetExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsBudget record);

    int insertSelective(RdmsBudget record);

    List<RdmsBudget> selectByExample(RdmsBudgetExample example);

    RdmsBudget selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsBudget record, @Param("example") RdmsBudgetExample example);

    int updateByExample(@Param("record") RdmsBudget record, @Param("example") RdmsBudgetExample example);

    int updateByPrimaryKeySelective(RdmsBudget record);

    int updateByPrimaryKey(RdmsBudget record);
}