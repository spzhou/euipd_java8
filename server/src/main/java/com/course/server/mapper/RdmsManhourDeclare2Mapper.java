/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsManhourDeclare2;
import com.course.server.domain.RdmsManhourDeclare2Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsManhourDeclare2Mapper {
    long countByExample(RdmsManhourDeclare2Example example);

    int deleteByExample(RdmsManhourDeclare2Example example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsManhourDeclare2 record);

    int insertSelective(RdmsManhourDeclare2 record);

    List<RdmsManhourDeclare2> selectByExample(RdmsManhourDeclare2Example example);

    RdmsManhourDeclare2 selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsManhourDeclare2 record, @Param("example") RdmsManhourDeclare2Example example);

    int updateByExample(@Param("record") RdmsManhourDeclare2 record, @Param("example") RdmsManhourDeclare2Example example);

    int updateByPrimaryKeySelective(RdmsManhourDeclare2 record);

    int updateByPrimaryKey(RdmsManhourDeclare2 record);
}