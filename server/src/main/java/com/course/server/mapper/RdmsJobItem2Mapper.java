/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsJobItem2;
import com.course.server.domain.RdmsJobItem2Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsJobItem2Mapper {
    long countByExample(RdmsJobItem2Example example);

    int deleteByExample(RdmsJobItem2Example example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsJobItem2 record);

    int insertSelective(RdmsJobItem2 record);

    List<RdmsJobItem2> selectByExample(RdmsJobItem2Example example);

    RdmsJobItem2 selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsJobItem2 record, @Param("example") RdmsJobItem2Example example);

    int updateByExample(@Param("record") RdmsJobItem2 record, @Param("example") RdmsJobItem2Example example);

    int updateByPrimaryKeySelective(RdmsJobItem2 record);

    int updateByPrimaryKey(RdmsJobItem2 record);
}