/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsJobItemGantt;
import com.course.server.domain.RdmsJobItemGanttExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsJobItemGanttMapper {
    long countByExample(RdmsJobItemGanttExample example);

    int deleteByExample(RdmsJobItemGanttExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsJobItemGantt record);

    int insertSelective(RdmsJobItemGantt record);

    List<RdmsJobItemGantt> selectByExample(RdmsJobItemGanttExample example);

    RdmsJobItemGantt selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsJobItemGantt record, @Param("example") RdmsJobItemGanttExample example);

    int updateByExample(@Param("record") RdmsJobItemGantt record, @Param("example") RdmsJobItemGanttExample example);

    int updateByPrimaryKeySelective(RdmsJobItemGantt record);

    int updateByPrimaryKey(RdmsJobItemGantt record);
}