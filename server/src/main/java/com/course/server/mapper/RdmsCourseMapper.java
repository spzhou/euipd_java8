/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsCourse;
import com.course.server.domain.RdmsCourseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsCourseMapper {
    long countByExample(RdmsCourseExample example);

    int deleteByExample(RdmsCourseExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsCourse record);

    int insertSelective(RdmsCourse record);

    List<RdmsCourse> selectByExample(RdmsCourseExample example);

    RdmsCourse selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsCourse record, @Param("example") RdmsCourseExample example);

    int updateByExample(@Param("record") RdmsCourse record, @Param("example") RdmsCourseExample example);

    int updateByPrimaryKeySelective(RdmsCourse record);

    int updateByPrimaryKey(RdmsCourse record);
}