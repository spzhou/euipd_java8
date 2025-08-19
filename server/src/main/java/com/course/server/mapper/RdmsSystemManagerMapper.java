/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsSystemManager;
import com.course.server.domain.RdmsSystemManagerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsSystemManagerMapper {
    long countByExample(RdmsSystemManagerExample example);

    int deleteByExample(RdmsSystemManagerExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsSystemManager record);

    int insertSelective(RdmsSystemManager record);

    List<RdmsSystemManager> selectByExample(RdmsSystemManagerExample example);

    RdmsSystemManager selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsSystemManager record, @Param("example") RdmsSystemManagerExample example);

    int updateByExample(@Param("record") RdmsSystemManager record, @Param("example") RdmsSystemManagerExample example);

    int updateByPrimaryKeySelective(RdmsSystemManager record);

    int updateByPrimaryKey(RdmsSystemManager record);
}