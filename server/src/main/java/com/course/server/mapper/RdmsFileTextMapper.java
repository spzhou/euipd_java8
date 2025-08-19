/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsFileText;
import com.course.server.domain.RdmsFileTextExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsFileTextMapper {
    long countByExample(RdmsFileTextExample example);

    int deleteByExample(RdmsFileTextExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsFileText record);

    int insertSelective(RdmsFileText record);

    List<RdmsFileText> selectByExampleWithBLOBs(RdmsFileTextExample example);

    List<RdmsFileText> selectByExample(RdmsFileTextExample example);

    RdmsFileText selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsFileText record, @Param("example") RdmsFileTextExample example);

    int updateByExampleWithBLOBs(@Param("record") RdmsFileText record, @Param("example") RdmsFileTextExample example);

    int updateByExample(@Param("record") RdmsFileText record, @Param("example") RdmsFileTextExample example);

    int updateByPrimaryKeySelective(RdmsFileText record);

    int updateByPrimaryKeyWithBLOBs(RdmsFileText record);

    int updateByPrimaryKey(RdmsFileText record);
}