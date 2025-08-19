/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.MemberAction;
import com.course.server.domain.MemberActionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MemberActionMapper {
    long countByExample(MemberActionExample example);

    int deleteByExample(MemberActionExample example);

    int deleteByPrimaryKey(String id);

    int insert(MemberAction record);

    int insertSelective(MemberAction record);

    List<MemberAction> selectByExample(MemberActionExample example);

    MemberAction selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") MemberAction record, @Param("example") MemberActionExample example);

    int updateByExample(@Param("record") MemberAction record, @Param("example") MemberActionExample example);

    int updateByPrimaryKeySelective(MemberAction record);

    int updateByPrimaryKey(MemberAction record);
}