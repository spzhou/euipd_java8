/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.MemberCourse;
import com.course.server.domain.MemberCourseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MemberCourseMapper {
    long countByExample(MemberCourseExample example);

    int deleteByExample(MemberCourseExample example);

    int deleteByPrimaryKey(String id);

    int insert(MemberCourse record);

    int insertSelective(MemberCourse record);

    List<MemberCourse> selectByExample(MemberCourseExample example);

    MemberCourse selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") MemberCourse record, @Param("example") MemberCourseExample example);

    int updateByExample(@Param("record") MemberCourse record, @Param("example") MemberCourseExample example);

    int updateByPrimaryKeySelective(MemberCourse record);

    int updateByPrimaryKey(MemberCourse record);
}