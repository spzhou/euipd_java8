/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.TeacherCategory;
import com.course.server.domain.TeacherCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TeacherCategoryMapper {
    long countByExample(TeacherCategoryExample example);

    int deleteByExample(TeacherCategoryExample example);

    int deleteByPrimaryKey(String id);

    int insert(TeacherCategory record);

    int insertSelective(TeacherCategory record);

    List<TeacherCategory> selectByExample(TeacherCategoryExample example);

    TeacherCategory selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TeacherCategory record, @Param("example") TeacherCategoryExample example);

    int updateByExample(@Param("record") TeacherCategory record, @Param("example") TeacherCategoryExample example);

    int updateByPrimaryKeySelective(TeacherCategory record);

    int updateByPrimaryKey(TeacherCategory record);
}