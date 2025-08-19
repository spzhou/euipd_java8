/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsQcgm;
import com.course.server.domain.RdmsQcgmExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsQcgmMapper {
    long countByExample(RdmsQcgmExample example);

    int deleteByExample(RdmsQcgmExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsQcgm record);

    int insertSelective(RdmsQcgm record);

    List<RdmsQcgm> selectByExample(RdmsQcgmExample example);

    RdmsQcgm selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsQcgm record, @Param("example") RdmsQcgmExample example);

    int updateByExample(@Param("record") RdmsQcgm record, @Param("example") RdmsQcgmExample example);

    int updateByPrimaryKeySelective(RdmsQcgm record);

    int updateByPrimaryKey(RdmsQcgm record);
}