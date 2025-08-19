/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.IndexConfig;
import com.course.server.domain.IndexConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IndexConfigMapper {
    long countByExample(IndexConfigExample example);

    int deleteByExample(IndexConfigExample example);

    int deleteByPrimaryKey(String id);

    int insert(IndexConfig record);

    int insertSelective(IndexConfig record);

    List<IndexConfig> selectByExample(IndexConfigExample example);

    IndexConfig selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") IndexConfig record, @Param("example") IndexConfigExample example);

    int updateByExample(@Param("record") IndexConfig record, @Param("example") IndexConfigExample example);

    int updateByPrimaryKeySelective(IndexConfig record);

    int updateByPrimaryKey(IndexConfig record);
}