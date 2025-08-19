/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsCbb2;
import com.course.server.domain.RdmsCbb2Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsCbb2Mapper {
    long countByExample(RdmsCbb2Example example);

    int deleteByExample(RdmsCbb2Example example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsCbb2 record);

    int insertSelective(RdmsCbb2 record);

    List<RdmsCbb2> selectByExample(RdmsCbb2Example example);

    RdmsCbb2 selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsCbb2 record, @Param("example") RdmsCbb2Example example);

    int updateByExample(@Param("record") RdmsCbb2 record, @Param("example") RdmsCbb2Example example);

    int updateByPrimaryKeySelective(RdmsCbb2 record);

    int updateByPrimaryKey(RdmsCbb2 record);
}