/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsPerformance2;
import com.course.server.domain.RdmsPerformance2Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsPerformance2Mapper {
    long countByExample(RdmsPerformance2Example example);

    int deleteByExample(RdmsPerformance2Example example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsPerformance2 record);

    int insertSelective(RdmsPerformance2 record);

    List<RdmsPerformance2> selectByExample(RdmsPerformance2Example example);

    RdmsPerformance2 selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsPerformance2 record, @Param("example") RdmsPerformance2Example example);

    int updateByExample(@Param("record") RdmsPerformance2 record, @Param("example") RdmsPerformance2Example example);

    int updateByPrimaryKeySelective(RdmsPerformance2 record);

    int updateByPrimaryKey(RdmsPerformance2 record);
}