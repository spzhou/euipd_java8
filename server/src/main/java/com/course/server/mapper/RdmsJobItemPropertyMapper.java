/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsJobItemProperty;
import com.course.server.domain.RdmsJobItemPropertyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsJobItemPropertyMapper {
    long countByExample(RdmsJobItemPropertyExample example);

    int deleteByExample(RdmsJobItemPropertyExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsJobItemProperty record);

    int insertSelective(RdmsJobItemProperty record);

    List<RdmsJobItemProperty> selectByExample(RdmsJobItemPropertyExample example);

    RdmsJobItemProperty selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsJobItemProperty record, @Param("example") RdmsJobItemPropertyExample example);

    int updateByExample(@Param("record") RdmsJobItemProperty record, @Param("example") RdmsJobItemPropertyExample example);

    int updateByPrimaryKeySelective(RdmsJobItemProperty record);

    int updateByPrimaryKey(RdmsJobItemProperty record);
}