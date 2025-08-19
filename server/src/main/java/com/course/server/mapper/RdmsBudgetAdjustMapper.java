/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsBudgetAdjust;
import com.course.server.domain.RdmsBudgetAdjustExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsBudgetAdjustMapper {
    long countByExample(RdmsBudgetAdjustExample example);

    int deleteByExample(RdmsBudgetAdjustExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsBudgetAdjust record);

    int insertSelective(RdmsBudgetAdjust record);

    List<RdmsBudgetAdjust> selectByExample(RdmsBudgetAdjustExample example);

    RdmsBudgetAdjust selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsBudgetAdjust record, @Param("example") RdmsBudgetAdjustExample example);

    int updateByExample(@Param("record") RdmsBudgetAdjust record, @Param("example") RdmsBudgetAdjustExample example);

    int updateByPrimaryKeySelective(RdmsBudgetAdjust record);

    int updateByPrimaryKey(RdmsBudgetAdjust record);
}