/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsJobItemProcess;
import com.course.server.domain.RdmsJobItemProcessExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsJobItemProcessMapper {
    long countByExample(RdmsJobItemProcessExample example);

    int deleteByExample(RdmsJobItemProcessExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsJobItemProcess record);

    int insertSelective(RdmsJobItemProcess record);

    List<RdmsJobItemProcess> selectByExample(RdmsJobItemProcessExample example);

    RdmsJobItemProcess selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsJobItemProcess record, @Param("example") RdmsJobItemProcessExample example);

    int updateByExample(@Param("record") RdmsJobItemProcess record, @Param("example") RdmsJobItemProcessExample example);

    int updateByPrimaryKeySelective(RdmsJobItemProcess record);

    int updateByPrimaryKey(RdmsJobItemProcess record);
}