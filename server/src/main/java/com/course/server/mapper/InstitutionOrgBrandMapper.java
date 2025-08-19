/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.InstitutionOrgBrand;
import com.course.server.domain.InstitutionOrgBrandExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InstitutionOrgBrandMapper {
    long countByExample(InstitutionOrgBrandExample example);

    int deleteByExample(InstitutionOrgBrandExample example);

    int deleteByPrimaryKey(String id);

    int insert(InstitutionOrgBrand record);

    int insertSelective(InstitutionOrgBrand record);

    List<InstitutionOrgBrand> selectByExample(InstitutionOrgBrandExample example);

    InstitutionOrgBrand selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") InstitutionOrgBrand record, @Param("example") InstitutionOrgBrandExample example);

    int updateByExample(@Param("record") InstitutionOrgBrand record, @Param("example") InstitutionOrgBrandExample example);

    int updateByPrimaryKeySelective(InstitutionOrgBrand record);

    int updateByPrimaryKey(InstitutionOrgBrand record);
}