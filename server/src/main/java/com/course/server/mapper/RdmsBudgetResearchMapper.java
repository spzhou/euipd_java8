/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsBudgetResearch;
import com.course.server.domain.RdmsBudgetResearchExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsBudgetResearchMapper {
    long countByExample(RdmsBudgetResearchExample example);

    int deleteByExample(RdmsBudgetResearchExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsBudgetResearch record);

    int insertSelective(RdmsBudgetResearch record);

    List<RdmsBudgetResearch> selectByExample(RdmsBudgetResearchExample example);

    RdmsBudgetResearch selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsBudgetResearch record, @Param("example") RdmsBudgetResearchExample example);

    int updateByExample(@Param("record") RdmsBudgetResearch record, @Param("example") RdmsBudgetResearchExample example);

    int updateByPrimaryKeySelective(RdmsBudgetResearch record);

    int updateByPrimaryKey(RdmsBudgetResearch record);
}