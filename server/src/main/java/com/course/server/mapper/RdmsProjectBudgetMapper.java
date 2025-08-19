/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsProjectBudget;
import com.course.server.domain.RdmsProjectBudgetExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsProjectBudgetMapper {
    long countByExample(RdmsProjectBudgetExample example);

    int deleteByExample(RdmsProjectBudgetExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsProjectBudget record);

    int insertSelective(RdmsProjectBudget record);

    List<RdmsProjectBudget> selectByExample(RdmsProjectBudgetExample example);

    RdmsProjectBudget selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsProjectBudget record, @Param("example") RdmsProjectBudgetExample example);

    int updateByExample(@Param("record") RdmsProjectBudget record, @Param("example") RdmsProjectBudgetExample example);

    int updateByPrimaryKeySelective(RdmsProjectBudget record);

    int updateByPrimaryKey(RdmsProjectBudget record);
}