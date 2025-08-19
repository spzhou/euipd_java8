/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsGanttLinks;
import com.course.server.domain.RdmsGanttLinksExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsGanttLinksMapper {
    long countByExample(RdmsGanttLinksExample example);

    int deleteByExample(RdmsGanttLinksExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsGanttLinks record);

    int insertSelective(RdmsGanttLinks record);

    List<RdmsGanttLinks> selectByExample(RdmsGanttLinksExample example);

    RdmsGanttLinks selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsGanttLinks record, @Param("example") RdmsGanttLinksExample example);

    int updateByExample(@Param("record") RdmsGanttLinks record, @Param("example") RdmsGanttLinksExample example);

    int updateByPrimaryKeySelective(RdmsGanttLinks record);

    int updateByPrimaryKey(RdmsGanttLinks record);
}