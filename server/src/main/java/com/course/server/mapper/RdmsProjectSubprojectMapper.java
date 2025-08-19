/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsProjectSubproject;
import com.course.server.domain.RdmsProjectSubprojectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsProjectSubprojectMapper {
    long countByExample(RdmsProjectSubprojectExample example);

    int deleteByExample(RdmsProjectSubprojectExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsProjectSubproject record);

    int insertSelective(RdmsProjectSubproject record);

    List<RdmsProjectSubproject> selectByExample(RdmsProjectSubprojectExample example);

    RdmsProjectSubproject selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsProjectSubproject record, @Param("example") RdmsProjectSubprojectExample example);

    int updateByExample(@Param("record") RdmsProjectSubproject record, @Param("example") RdmsProjectSubprojectExample example);

    int updateByPrimaryKeySelective(RdmsProjectSubproject record);

    int updateByPrimaryKey(RdmsProjectSubproject record);
}