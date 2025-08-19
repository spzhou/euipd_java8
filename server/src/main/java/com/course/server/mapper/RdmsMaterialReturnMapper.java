/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsMaterialReturn;
import com.course.server.domain.RdmsMaterialReturnExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsMaterialReturnMapper {
    long countByExample(RdmsMaterialReturnExample example);

    int deleteByExample(RdmsMaterialReturnExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsMaterialReturn record);

    int insertSelective(RdmsMaterialReturn record);

    List<RdmsMaterialReturn> selectByExample(RdmsMaterialReturnExample example);

    RdmsMaterialReturn selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsMaterialReturn record, @Param("example") RdmsMaterialReturnExample example);

    int updateByExample(@Param("record") RdmsMaterialReturn record, @Param("example") RdmsMaterialReturnExample example);

    int updateByPrimaryKeySelective(RdmsMaterialReturn record);

    int updateByPrimaryKey(RdmsMaterialReturn record);
}