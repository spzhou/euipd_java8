/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsProductManager;
import com.course.server.domain.RdmsProductManagerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsProductManagerMapper {
    long countByExample(RdmsProductManagerExample example);

    int deleteByExample(RdmsProductManagerExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsProductManager record);

    int insertSelective(RdmsProductManager record);

    List<RdmsProductManager> selectByExample(RdmsProductManagerExample example);

    RdmsProductManager selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsProductManager record, @Param("example") RdmsProductManagerExample example);

    int updateByExample(@Param("record") RdmsProductManager record, @Param("example") RdmsProductManagerExample example);

    int updateByPrimaryKeySelective(RdmsProductManager record);

    int updateByPrimaryKey(RdmsProductManager record);
}