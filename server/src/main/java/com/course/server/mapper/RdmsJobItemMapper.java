/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsJobItem;
import com.course.server.domain.RdmsJobItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsJobItemMapper {
    long countByExample(RdmsJobItemExample example);

    int deleteByExample(RdmsJobItemExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsJobItem record);

    int insertSelective(RdmsJobItem record);

    List<RdmsJobItem> selectByExample(RdmsJobItemExample example);

    RdmsJobItem selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsJobItem record, @Param("example") RdmsJobItemExample example);

    int updateByExample(@Param("record") RdmsJobItem record, @Param("example") RdmsJobItemExample example);

    int updateByPrimaryKeySelective(RdmsJobItem record);

    int updateByPrimaryKey(RdmsJobItem record);
}