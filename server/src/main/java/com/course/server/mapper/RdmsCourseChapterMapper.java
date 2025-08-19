/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsCourseChapter;
import com.course.server.domain.RdmsCourseChapterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsCourseChapterMapper {
    long countByExample(RdmsCourseChapterExample example);

    int deleteByExample(RdmsCourseChapterExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsCourseChapter record);

    int insertSelective(RdmsCourseChapter record);

    List<RdmsCourseChapter> selectByExample(RdmsCourseChapterExample example);

    RdmsCourseChapter selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsCourseChapter record, @Param("example") RdmsCourseChapterExample example);

    int updateByExample(@Param("record") RdmsCourseChapter record, @Param("example") RdmsCourseChapterExample example);

    int updateByPrimaryKeySelective(RdmsCourseChapter record);

    int updateByPrimaryKey(RdmsCourseChapter record);
}