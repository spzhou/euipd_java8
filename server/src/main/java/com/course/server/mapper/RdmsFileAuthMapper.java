/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsFileAuth;
import com.course.server.domain.RdmsFileAuthExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsFileAuthMapper {
    long countByExample(RdmsFileAuthExample example);

    int deleteByExample(RdmsFileAuthExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsFileAuth record);

    int insertSelective(RdmsFileAuth record);

    List<RdmsFileAuth> selectByExample(RdmsFileAuthExample example);

    RdmsFileAuth selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsFileAuth record, @Param("example") RdmsFileAuthExample example);

    int updateByExample(@Param("record") RdmsFileAuth record, @Param("example") RdmsFileAuthExample example);

    int updateByPrimaryKeySelective(RdmsFileAuth record);

    int updateByPrimaryKey(RdmsFileAuth record);
}