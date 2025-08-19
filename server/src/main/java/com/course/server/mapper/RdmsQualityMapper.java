/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsQuality;
import com.course.server.domain.RdmsQualityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsQualityMapper {
    long countByExample(RdmsQualityExample example);

    int deleteByExample(RdmsQualityExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsQuality record);

    int insertSelective(RdmsQuality record);

    List<RdmsQuality> selectByExample(RdmsQualityExample example);

    RdmsQuality selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsQuality record, @Param("example") RdmsQualityExample example);

    int updateByExample(@Param("record") RdmsQuality record, @Param("example") RdmsQualityExample example);

    int updateByPrimaryKeySelective(RdmsQuality record);

    int updateByPrimaryKey(RdmsQuality record);
}