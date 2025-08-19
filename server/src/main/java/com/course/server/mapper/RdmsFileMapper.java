/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsFile;
import com.course.server.domain.RdmsFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsFileMapper {
    long countByExample(RdmsFileExample example);

    int deleteByExample(RdmsFileExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsFile record);

    int insertSelective(RdmsFile record);

    List<RdmsFile> selectByExample(RdmsFileExample example);

    RdmsFile selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsFile record, @Param("example") RdmsFileExample example);

    int updateByExample(@Param("record") RdmsFile record, @Param("example") RdmsFileExample example);

    int updateByPrimaryKeySelective(RdmsFile record);

    int updateByPrimaryKey(RdmsFile record);
}