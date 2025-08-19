/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsBudgetResearchExe;
import com.course.server.domain.RdmsBudgetResearchExeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsBudgetResearchExeMapper {
    long countByExample(RdmsBudgetResearchExeExample example);

    int deleteByExample(RdmsBudgetResearchExeExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsBudgetResearchExe record);

    int insertSelective(RdmsBudgetResearchExe record);

    List<RdmsBudgetResearchExe> selectByExample(RdmsBudgetResearchExeExample example);

    RdmsBudgetResearchExe selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsBudgetResearchExe record, @Param("example") RdmsBudgetResearchExeExample example);

    int updateByExample(@Param("record") RdmsBudgetResearchExe record, @Param("example") RdmsBudgetResearchExeExample example);

    int updateByPrimaryKeySelective(RdmsBudgetResearchExe record);

    int updateByPrimaryKey(RdmsBudgetResearchExe record);
}