/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.Chapter;
import com.course.server.domain.ChapterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChapterMapper {
    long countByExample(ChapterExample example);

    int deleteByExample(ChapterExample example);

    int deleteByPrimaryKey(String id);

    int insert(Chapter record);

    int insertSelective(Chapter record);

    List<Chapter> selectByExample(ChapterExample example);

    Chapter selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Chapter record, @Param("example") ChapterExample example);

    int updateByExample(@Param("record") Chapter record, @Param("example") ChapterExample example);

    int updateByPrimaryKeySelective(Chapter record);

    int updateByPrimaryKey(Chapter record);
}