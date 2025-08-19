/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsManhourDeclare;
import com.course.server.domain.RdmsManhourDeclareExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsManhourDeclareMapper {
    long countByExample(RdmsManhourDeclareExample example);

    int deleteByExample(RdmsManhourDeclareExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsManhourDeclare record);

    int insertSelective(RdmsManhourDeclare record);

    List<RdmsManhourDeclare> selectByExample(RdmsManhourDeclareExample example);

    RdmsManhourDeclare selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsManhourDeclare record, @Param("example") RdmsManhourDeclareExample example);

    int updateByExample(@Param("record") RdmsManhourDeclare record, @Param("example") RdmsManhourDeclareExample example);

    int updateByPrimaryKeySelective(RdmsManhourDeclare record);

    int updateByPrimaryKey(RdmsManhourDeclare record);
}