/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.InstitutionAuthority;
import com.course.server.domain.InstitutionAuthorityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InstitutionAuthorityMapper {
    long countByExample(InstitutionAuthorityExample example);

    int deleteByExample(InstitutionAuthorityExample example);

    int deleteByPrimaryKey(String id);

    int insert(InstitutionAuthority record);

    int insertSelective(InstitutionAuthority record);

    List<InstitutionAuthority> selectByExample(InstitutionAuthorityExample example);

    InstitutionAuthority selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") InstitutionAuthority record, @Param("example") InstitutionAuthorityExample example);

    int updateByExample(@Param("record") InstitutionAuthority record, @Param("example") InstitutionAuthorityExample example);

    int updateByPrimaryKeySelective(InstitutionAuthority record);

    int updateByPrimaryKey(InstitutionAuthority record);
}