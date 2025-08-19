/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.InstitutionMember;
import com.course.server.domain.InstitutionMemberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InstitutionMemberMapper {
    long countByExample(InstitutionMemberExample example);

    int deleteByExample(InstitutionMemberExample example);

    int deleteByPrimaryKey(String id);

    int insert(InstitutionMember record);

    int insertSelective(InstitutionMember record);

    List<InstitutionMember> selectByExample(InstitutionMemberExample example);

    InstitutionMember selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") InstitutionMember record, @Param("example") InstitutionMemberExample example);

    int updateByExample(@Param("record") InstitutionMember record, @Param("example") InstitutionMemberExample example);

    int updateByPrimaryKeySelective(InstitutionMember record);

    int updateByPrimaryKey(InstitutionMember record);
}