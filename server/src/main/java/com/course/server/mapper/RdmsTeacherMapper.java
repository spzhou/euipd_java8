/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsTeacher;
import com.course.server.domain.RdmsTeacherExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsTeacherMapper {
    long countByExample(RdmsTeacherExample example);

    int deleteByExample(RdmsTeacherExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsTeacher record);

    int insertSelective(RdmsTeacher record);

    List<RdmsTeacher> selectByExample(RdmsTeacherExample example);

    RdmsTeacher selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsTeacher record, @Param("example") RdmsTeacherExample example);

    int updateByExample(@Param("record") RdmsTeacher record, @Param("example") RdmsTeacherExample example);

    int updateByPrimaryKeySelective(RdmsTeacher record);

    int updateByPrimaryKey(RdmsTeacher record);
}