/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsMaterial2;
import com.course.server.domain.RdmsMaterial2Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsMaterial2Mapper {
    long countByExample(RdmsMaterial2Example example);

    int deleteByExample(RdmsMaterial2Example example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsMaterial2 record);

    int insertSelective(RdmsMaterial2 record);

    List<RdmsMaterial2> selectByExample(RdmsMaterial2Example example);

    RdmsMaterial2 selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsMaterial2 record, @Param("example") RdmsMaterial2Example example);

    int updateByExample(@Param("record") RdmsMaterial2 record, @Param("example") RdmsMaterial2Example example);

    int updateByPrimaryKeySelective(RdmsMaterial2 record);

    int updateByPrimaryKey(RdmsMaterial2 record);
}