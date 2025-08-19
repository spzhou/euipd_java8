/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsUser;
import com.course.server.domain.RdmsUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsUserMapper {
    long countByExample(RdmsUserExample example);

    int deleteByExample(RdmsUserExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsUser record);

    int insertSelective(RdmsUser record);

    List<RdmsUser> selectByExample(RdmsUserExample example);

    RdmsUser selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsUser record, @Param("example") RdmsUserExample example);

    int updateByExample(@Param("record") RdmsUser record, @Param("example") RdmsUserExample example);

    int updateByPrimaryKeySelective(RdmsUser record);

    int updateByPrimaryKey(RdmsUser record);
}