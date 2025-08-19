/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsCourse2;
import com.course.server.domain.RdmsCourse2Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsCourse2Mapper {
    long countByExample(RdmsCourse2Example example);

    int deleteByExample(RdmsCourse2Example example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsCourse2 record);

    int insertSelective(RdmsCourse2 record);

    List<RdmsCourse2> selectByExample(RdmsCourse2Example example);

    RdmsCourse2 selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsCourse2 record, @Param("example") RdmsCourse2Example example);

    int updateByExample(@Param("record") RdmsCourse2 record, @Param("example") RdmsCourse2Example example);

    int updateByPrimaryKeySelective(RdmsCourse2 record);

    int updateByPrimaryKey(RdmsCourse2 record);
}