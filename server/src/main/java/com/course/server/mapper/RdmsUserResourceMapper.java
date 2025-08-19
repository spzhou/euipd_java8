/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsUserResource;
import com.course.server.domain.RdmsUserResourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsUserResourceMapper {
    long countByExample(RdmsUserResourceExample example);

    int deleteByExample(RdmsUserResourceExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsUserResource record);

    int insertSelective(RdmsUserResource record);

    List<RdmsUserResource> selectByExample(RdmsUserResourceExample example);

    RdmsUserResource selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsUserResource record, @Param("example") RdmsUserResourceExample example);

    int updateByExample(@Param("record") RdmsUserResource record, @Param("example") RdmsUserResourceExample example);

    int updateByPrimaryKeySelective(RdmsUserResource record);

    int updateByPrimaryKey(RdmsUserResource record);
}