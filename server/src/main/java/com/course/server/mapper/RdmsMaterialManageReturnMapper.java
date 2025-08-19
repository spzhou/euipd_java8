/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsMaterialManageReturn;
import com.course.server.domain.RdmsMaterialManageReturnExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsMaterialManageReturnMapper {
    long countByExample(RdmsMaterialManageReturnExample example);

    int deleteByExample(RdmsMaterialManageReturnExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsMaterialManageReturn record);

    int insertSelective(RdmsMaterialManageReturn record);

    List<RdmsMaterialManageReturn> selectByExample(RdmsMaterialManageReturnExample example);

    RdmsMaterialManageReturn selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsMaterialManageReturn record, @Param("example") RdmsMaterialManageReturnExample example);

    int updateByExample(@Param("record") RdmsMaterialManageReturn record, @Param("example") RdmsMaterialManageReturnExample example);

    int updateByPrimaryKeySelective(RdmsMaterialManageReturn record);

    int updateByPrimaryKey(RdmsMaterialManageReturn record);
}