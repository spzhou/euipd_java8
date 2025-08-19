/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.InstitutionCategory;
import com.course.server.domain.InstitutionCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InstitutionCategoryMapper {
    long countByExample(InstitutionCategoryExample example);

    int deleteByExample(InstitutionCategoryExample example);

    int deleteByPrimaryKey(String id);

    int insert(InstitutionCategory record);

    int insertSelective(InstitutionCategory record);

    List<InstitutionCategory> selectByExample(InstitutionCategoryExample example);

    InstitutionCategory selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") InstitutionCategory record, @Param("example") InstitutionCategoryExample example);

    int updateByExample(@Param("record") InstitutionCategory record, @Param("example") InstitutionCategoryExample example);

    int updateByPrimaryKeySelective(InstitutionCategory record);

    int updateByPrimaryKey(InstitutionCategory record);
}