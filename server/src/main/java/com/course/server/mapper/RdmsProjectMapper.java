/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsProject;
import com.course.server.domain.RdmsProjectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsProjectMapper {
    long countByExample(RdmsProjectExample example);

    int deleteByExample(RdmsProjectExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsProject record);

    int insertSelective(RdmsProject record);

    List<RdmsProject> selectByExample(RdmsProjectExample example);

    RdmsProject selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsProject record, @Param("example") RdmsProjectExample example);

    int updateByExample(@Param("record") RdmsProject record, @Param("example") RdmsProjectExample example);

    int updateByPrimaryKeySelective(RdmsProject record);

    int updateByPrimaryKey(RdmsProject record);
}