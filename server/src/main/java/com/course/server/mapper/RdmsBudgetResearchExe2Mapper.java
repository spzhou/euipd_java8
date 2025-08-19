/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsBudgetResearchExe2;
import com.course.server.domain.RdmsBudgetResearchExe2Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsBudgetResearchExe2Mapper {
    long countByExample(RdmsBudgetResearchExe2Example example);

    int deleteByExample(RdmsBudgetResearchExe2Example example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsBudgetResearchExe2 record);

    int insertSelective(RdmsBudgetResearchExe2 record);

    List<RdmsBudgetResearchExe2> selectByExample(RdmsBudgetResearchExe2Example example);

    RdmsBudgetResearchExe2 selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsBudgetResearchExe2 record, @Param("example") RdmsBudgetResearchExe2Example example);

    int updateByExample(@Param("record") RdmsBudgetResearchExe2 record, @Param("example") RdmsBudgetResearchExe2Example example);

    int updateByPrimaryKeySelective(RdmsBudgetResearchExe2 record);

    int updateByPrimaryKey(RdmsBudgetResearchExe2 record);
}