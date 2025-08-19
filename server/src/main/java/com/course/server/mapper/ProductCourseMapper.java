/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.ProductCourse;
import com.course.server.domain.ProductCourseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductCourseMapper {
    long countByExample(ProductCourseExample example);

    int deleteByExample(ProductCourseExample example);

    int deleteByPrimaryKey(String id);

    int insert(ProductCourse record);

    int insertSelective(ProductCourse record);

    List<ProductCourse> selectByExample(ProductCourseExample example);

    ProductCourse selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ProductCourse record, @Param("example") ProductCourseExample example);

    int updateByExample(@Param("record") ProductCourse record, @Param("example") ProductCourseExample example);

    int updateByPrimaryKeySelective(ProductCourse record);

    int updateByPrimaryKey(ProductCourse record);
}