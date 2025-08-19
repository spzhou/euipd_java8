/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsCourseContent;
import com.course.server.domain.RdmsCourseContentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsCourseContentMapper {
    long countByExample(RdmsCourseContentExample example);

    int deleteByExample(RdmsCourseContentExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsCourseContent record);

    int insertSelective(RdmsCourseContent record);

    List<RdmsCourseContent> selectByExampleWithBLOBs(RdmsCourseContentExample example);

    List<RdmsCourseContent> selectByExample(RdmsCourseContentExample example);

    RdmsCourseContent selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsCourseContent record, @Param("example") RdmsCourseContentExample example);

    int updateByExampleWithBLOBs(@Param("record") RdmsCourseContent record, @Param("example") RdmsCourseContentExample example);

    int updateByExample(@Param("record") RdmsCourseContent record, @Param("example") RdmsCourseContentExample example);

    int updateByPrimaryKeySelective(RdmsCourseContent record);

    int updateByPrimaryKeyWithBLOBs(RdmsCourseContent record);
}