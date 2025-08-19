/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsProjectManager;
import com.course.server.domain.RdmsProjectManagerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsProjectManagerMapper {
    long countByExample(RdmsProjectManagerExample example);

    int deleteByExample(RdmsProjectManagerExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsProjectManager record);

    int insertSelective(RdmsProjectManager record);

    List<RdmsProjectManager> selectByExample(RdmsProjectManagerExample example);

    RdmsProjectManager selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsProjectManager record, @Param("example") RdmsProjectManagerExample example);

    int updateByExample(@Param("record") RdmsProjectManager record, @Param("example") RdmsProjectManagerExample example);

    int updateByPrimaryKeySelective(RdmsProjectManager record);

    int updateByPrimaryKey(RdmsProjectManager record);
}