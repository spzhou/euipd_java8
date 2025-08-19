/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsBom;
import com.course.server.domain.RdmsBomExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsBomMapper {
    long countByExample(RdmsBomExample example);

    int deleteByExample(RdmsBomExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsBom record);

    int insertSelective(RdmsBom record);

    List<RdmsBom> selectByExample(RdmsBomExample example);

    RdmsBom selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsBom record, @Param("example") RdmsBomExample example);

    int updateByExample(@Param("record") RdmsBom record, @Param("example") RdmsBomExample example);

    int updateByPrimaryKeySelective(RdmsBom record);

    int updateByPrimaryKey(RdmsBom record);
}