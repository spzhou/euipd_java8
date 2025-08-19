/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsAuth;
import com.course.server.domain.RdmsAuthExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsAuthMapper {
    long countByExample(RdmsAuthExample example);

    int deleteByExample(RdmsAuthExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsAuth record);

    int insertSelective(RdmsAuth record);

    List<RdmsAuth> selectByExample(RdmsAuthExample example);

    RdmsAuth selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsAuth record, @Param("example") RdmsAuthExample example);

    int updateByExample(@Param("record") RdmsAuth record, @Param("example") RdmsAuthExample example);

    int updateByPrimaryKeySelective(RdmsAuth record);

    int updateByPrimaryKey(RdmsAuth record);
}