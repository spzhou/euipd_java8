/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsFggm;
import com.course.server.domain.RdmsFggmExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsFggmMapper {
    long countByExample(RdmsFggmExample example);

    int deleteByExample(RdmsFggmExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsFggm record);

    int insertSelective(RdmsFggm record);

    List<RdmsFggm> selectByExample(RdmsFggmExample example);

    RdmsFggm selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsFggm record, @Param("example") RdmsFggmExample example);

    int updateByExample(@Param("record") RdmsFggm record, @Param("example") RdmsFggmExample example);

    int updateByPrimaryKeySelective(RdmsFggm record);

    int updateByPrimaryKey(RdmsFggm record);
}