/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsSystem;
import com.course.server.domain.RdmsSystemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsSystemMapper {
    long countByExample(RdmsSystemExample example);

    int deleteByExample(RdmsSystemExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsSystem record);

    int insertSelective(RdmsSystem record);

    List<RdmsSystem> selectByExample(RdmsSystemExample example);

    RdmsSystem selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsSystem record, @Param("example") RdmsSystemExample example);

    int updateByExample(@Param("record") RdmsSystem record, @Param("example") RdmsSystemExample example);

    int updateByPrimaryKeySelective(RdmsSystem record);

    int updateByPrimaryKey(RdmsSystem record);
}