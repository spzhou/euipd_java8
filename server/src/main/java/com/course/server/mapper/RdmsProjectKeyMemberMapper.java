/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsProjectKeyMember;
import com.course.server.domain.RdmsProjectKeyMemberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsProjectKeyMemberMapper {
    long countByExample(RdmsProjectKeyMemberExample example);

    int deleteByExample(RdmsProjectKeyMemberExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsProjectKeyMember record);

    int insertSelective(RdmsProjectKeyMember record);

    List<RdmsProjectKeyMember> selectByExample(RdmsProjectKeyMemberExample example);

    RdmsProjectKeyMember selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsProjectKeyMember record, @Param("example") RdmsProjectKeyMemberExample example);

    int updateByExample(@Param("record") RdmsProjectKeyMember record, @Param("example") RdmsProjectKeyMemberExample example);

    int updateByPrimaryKeySelective(RdmsProjectKeyMember record);

    int updateByPrimaryKey(RdmsProjectKeyMember record);
}