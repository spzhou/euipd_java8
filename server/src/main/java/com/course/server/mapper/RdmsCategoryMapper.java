/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsCategory;
import com.course.server.domain.RdmsCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsCategoryMapper {
    long countByExample(RdmsCategoryExample example);

    int deleteByExample(RdmsCategoryExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsCategory record);

    int insertSelective(RdmsCategory record);

    List<RdmsCategory> selectByExample(RdmsCategoryExample example);

    RdmsCategory selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsCategory record, @Param("example") RdmsCategoryExample example);

    int updateByExample(@Param("record") RdmsCategory record, @Param("example") RdmsCategoryExample example);

    int updateByPrimaryKeySelective(RdmsCategory record);

    int updateByPrimaryKey(RdmsCategory record);
}