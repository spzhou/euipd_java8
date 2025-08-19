/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsMaterialProcess;
import com.course.server.domain.RdmsMaterialProcessExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsMaterialProcessMapper {
    long countByExample(RdmsMaterialProcessExample example);

    int deleteByExample(RdmsMaterialProcessExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsMaterialProcess record);

    int insertSelective(RdmsMaterialProcess record);

    List<RdmsMaterialProcess> selectByExample(RdmsMaterialProcessExample example);

    RdmsMaterialProcess selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsMaterialProcess record, @Param("example") RdmsMaterialProcessExample example);

    int updateByExample(@Param("record") RdmsMaterialProcess record, @Param("example") RdmsMaterialProcessExample example);

    int updateByPrimaryKeySelective(RdmsMaterialProcess record);

    int updateByPrimaryKey(RdmsMaterialProcess record);
}