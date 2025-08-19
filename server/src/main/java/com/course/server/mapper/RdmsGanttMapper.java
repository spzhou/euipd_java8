/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsGantt;
import com.course.server.domain.RdmsGanttExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsGanttMapper {
    long countByExample(RdmsGanttExample example);

    int deleteByExample(RdmsGanttExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsGantt record);

    int insertSelective(RdmsGantt record);

    List<RdmsGantt> selectByExample(RdmsGanttExample example);

    RdmsGantt selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsGantt record, @Param("example") RdmsGanttExample example);

    int updateByExample(@Param("record") RdmsGantt record, @Param("example") RdmsGanttExample example);

    int updateByPrimaryKeySelective(RdmsGantt record);

    int updateByPrimaryKey(RdmsGantt record);
}