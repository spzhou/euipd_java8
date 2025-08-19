/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsCourseOwnCategory;
import com.course.server.domain.RdmsCourseOwnCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsCourseOwnCategoryMapper {
    long countByExample(RdmsCourseOwnCategoryExample example);

    int deleteByExample(RdmsCourseOwnCategoryExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsCourseOwnCategory record);

    int insertSelective(RdmsCourseOwnCategory record);

    List<RdmsCourseOwnCategory> selectByExample(RdmsCourseOwnCategoryExample example);

    RdmsCourseOwnCategory selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsCourseOwnCategory record, @Param("example") RdmsCourseOwnCategoryExample example);

    int updateByExample(@Param("record") RdmsCourseOwnCategory record, @Param("example") RdmsCourseOwnCategoryExample example);

    int updateByPrimaryKeySelective(RdmsCourseOwnCategory record);

    int updateByPrimaryKey(RdmsCourseOwnCategory record);
}