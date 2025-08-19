/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsProjectTask;
import com.course.server.domain.RdmsProjectTaskExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsProjectTaskMapper {
    long countByExample(RdmsProjectTaskExample example);

    int deleteByExample(RdmsProjectTaskExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsProjectTask record);

    int insertSelective(RdmsProjectTask record);

    List<RdmsProjectTask> selectByExample(RdmsProjectTaskExample example);

    RdmsProjectTask selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsProjectTask record, @Param("example") RdmsProjectTaskExample example);

    int updateByExample(@Param("record") RdmsProjectTask record, @Param("example") RdmsProjectTaskExample example);

    int updateByPrimaryKeySelective(RdmsProjectTask record);

    int updateByPrimaryKey(RdmsProjectTask record);
}