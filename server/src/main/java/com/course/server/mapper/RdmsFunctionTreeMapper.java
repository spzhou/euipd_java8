/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsFunctionTree;
import com.course.server.domain.RdmsFunctionTreeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsFunctionTreeMapper {
    long countByExample(RdmsFunctionTreeExample example);

    int deleteByExample(RdmsFunctionTreeExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsFunctionTree record);

    int insertSelective(RdmsFunctionTree record);

    List<RdmsFunctionTree> selectByExample(RdmsFunctionTreeExample example);

    RdmsFunctionTree selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsFunctionTree record, @Param("example") RdmsFunctionTreeExample example);

    int updateByExample(@Param("record") RdmsFunctionTree record, @Param("example") RdmsFunctionTreeExample example);

    int updateByPrimaryKeySelective(RdmsFunctionTree record);

    int updateByPrimaryKey(RdmsFunctionTree record);
}