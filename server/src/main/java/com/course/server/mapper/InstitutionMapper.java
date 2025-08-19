/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.Institution;
import com.course.server.domain.InstitutionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InstitutionMapper {
    long countByExample(InstitutionExample example);

    int deleteByExample(InstitutionExample example);

    int deleteByPrimaryKey(String id);

    int insert(Institution record);

    int insertSelective(Institution record);

    List<Institution> selectByExample(InstitutionExample example);

    Institution selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Institution record, @Param("example") InstitutionExample example);

    int updateByExample(@Param("record") Institution record, @Param("example") InstitutionExample example);

    int updateByPrimaryKeySelective(Institution record);

    int updateByPrimaryKey(Institution record);
}