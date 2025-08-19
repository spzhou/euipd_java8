/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsMaterialProcessReturn;
import com.course.server.domain.RdmsMaterialProcessReturnExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsMaterialProcessReturnMapper {
    long countByExample(RdmsMaterialProcessReturnExample example);

    int deleteByExample(RdmsMaterialProcessReturnExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsMaterialProcessReturn record);

    int insertSelective(RdmsMaterialProcessReturn record);

    List<RdmsMaterialProcessReturn> selectByExample(RdmsMaterialProcessReturnExample example);

    RdmsMaterialProcessReturn selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsMaterialProcessReturn record, @Param("example") RdmsMaterialProcessReturnExample example);

    int updateByExample(@Param("record") RdmsMaterialProcessReturn record, @Param("example") RdmsMaterialProcessReturnExample example);

    int updateByPrimaryKeySelective(RdmsMaterialProcessReturn record);

    int updateByPrimaryKey(RdmsMaterialProcessReturn record);
}