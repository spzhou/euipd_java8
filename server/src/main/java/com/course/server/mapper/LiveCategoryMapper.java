/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.LiveCategory;
import com.course.server.domain.LiveCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LiveCategoryMapper {
    long countByExample(LiveCategoryExample example);

    int deleteByExample(LiveCategoryExample example);

    int deleteByPrimaryKey(String id);

    int insert(LiveCategory record);

    int insertSelective(LiveCategory record);

    List<LiveCategory> selectByExample(LiveCategoryExample example);

    LiveCategory selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") LiveCategory record, @Param("example") LiveCategoryExample example);

    int updateByExample(@Param("record") LiveCategory record, @Param("example") LiveCategoryExample example);

    int updateByPrimaryKeySelective(LiveCategory record);

    int updateByPrimaryKey(LiveCategory record);
}