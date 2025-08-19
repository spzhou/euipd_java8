/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsPerformance;
import com.course.server.domain.RdmsPerformanceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsPerformanceMapper {
    long countByExample(RdmsPerformanceExample example);

    int deleteByExample(RdmsPerformanceExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsPerformance record);

    int insertSelective(RdmsPerformance record);

    List<RdmsPerformance> selectByExample(RdmsPerformanceExample example);

    RdmsPerformance selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsPerformance record, @Param("example") RdmsPerformanceExample example);

    int updateByExample(@Param("record") RdmsPerformance record, @Param("example") RdmsPerformanceExample example);

    int updateByPrimaryKeySelective(RdmsPerformance record);

    int updateByPrimaryKey(RdmsPerformance record);
}