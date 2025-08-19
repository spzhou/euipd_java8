/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsBoss;
import com.course.server.domain.RdmsBossExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsBossMapper {
    long countByExample(RdmsBossExample example);

    int deleteByExample(RdmsBossExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsBoss record);

    int insertSelective(RdmsBoss record);

    List<RdmsBoss> selectByExample(RdmsBossExample example);

    RdmsBoss selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsBoss record, @Param("example") RdmsBossExample example);

    int updateByExample(@Param("record") RdmsBoss record, @Param("example") RdmsBossExample example);

    int updateByPrimaryKeySelective(RdmsBoss record);

    int updateByPrimaryKey(RdmsBoss record);
}