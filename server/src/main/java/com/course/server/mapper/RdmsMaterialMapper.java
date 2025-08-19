/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsMaterial;
import com.course.server.domain.RdmsMaterialExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsMaterialMapper {
    long countByExample(RdmsMaterialExample example);

    int deleteByExample(RdmsMaterialExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsMaterial record);

    int insertSelective(RdmsMaterial record);

    List<RdmsMaterial> selectByExample(RdmsMaterialExample example);

    RdmsMaterial selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsMaterial record, @Param("example") RdmsMaterialExample example);

    int updateByExample(@Param("record") RdmsMaterial record, @Param("example") RdmsMaterialExample example);

    int updateByPrimaryKeySelective(RdmsMaterial record);

    int updateByPrimaryKey(RdmsMaterial record);
}