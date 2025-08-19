/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsPreProject;
import com.course.server.domain.RdmsPreProjectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsPreProjectMapper {
    long countByExample(RdmsPreProjectExample example);

    int deleteByExample(RdmsPreProjectExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsPreProject record);

    int insertSelective(RdmsPreProject record);

    List<RdmsPreProject> selectByExample(RdmsPreProjectExample example);

    RdmsPreProject selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsPreProject record, @Param("example") RdmsPreProjectExample example);

    int updateByExample(@Param("record") RdmsPreProject record, @Param("example") RdmsPreProjectExample example);

    int updateByPrimaryKeySelective(RdmsPreProject record);

    int updateByPrimaryKey(RdmsPreProject record);
}