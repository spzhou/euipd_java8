/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsCourseCategory;
import com.course.server.domain.RdmsCourseCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsCourseCategoryMapper {
    long countByExample(RdmsCourseCategoryExample example);

    int deleteByExample(RdmsCourseCategoryExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsCourseCategory record);

    int insertSelective(RdmsCourseCategory record);

    List<RdmsCourseCategory> selectByExample(RdmsCourseCategoryExample example);

    RdmsCourseCategory selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsCourseCategory record, @Param("example") RdmsCourseCategoryExample example);

    int updateByExample(@Param("record") RdmsCourseCategory record, @Param("example") RdmsCourseCategoryExample example);

    int updateByPrimaryKeySelective(RdmsCourseCategory record);

    int updateByPrimaryKey(RdmsCourseCategory record);
}