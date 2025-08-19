/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsBudgetExe;
import com.course.server.domain.RdmsBudgetExeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsBudgetExeMapper {
    long countByExample(RdmsBudgetExeExample example);

    int deleteByExample(RdmsBudgetExeExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsBudgetExe record);

    int insertSelective(RdmsBudgetExe record);

    List<RdmsBudgetExe> selectByExample(RdmsBudgetExeExample example);

    RdmsBudgetExe selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsBudgetExe record, @Param("example") RdmsBudgetExeExample example);

    int updateByExample(@Param("record") RdmsBudgetExe record, @Param("example") RdmsBudgetExeExample example);

    int updateByPrimaryKeySelective(RdmsBudgetExe record);

    int updateByPrimaryKey(RdmsBudgetExe record);
}