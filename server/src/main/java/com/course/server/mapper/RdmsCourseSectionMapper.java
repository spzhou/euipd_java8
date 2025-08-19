/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsCourseSection;
import com.course.server.domain.RdmsCourseSectionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsCourseSectionMapper {
    long countByExample(RdmsCourseSectionExample example);

    int deleteByExample(RdmsCourseSectionExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsCourseSection record);

    int insertSelective(RdmsCourseSection record);

    List<RdmsCourseSection> selectByExample(RdmsCourseSectionExample example);

    RdmsCourseSection selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsCourseSection record, @Param("example") RdmsCourseSectionExample example);

    int updateByExample(@Param("record") RdmsCourseSection record, @Param("example") RdmsCourseSectionExample example);

    int updateByPrimaryKeySelective(RdmsCourseSection record);

    int updateByPrimaryKey(RdmsCourseSection record);
}