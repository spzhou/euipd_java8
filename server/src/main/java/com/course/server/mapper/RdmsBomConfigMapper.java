/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsBomConfig;
import com.course.server.domain.RdmsBomConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsBomConfigMapper {
    long countByExample(RdmsBomConfigExample example);

    int deleteByExample(RdmsBomConfigExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsBomConfig record);

    int insertSelective(RdmsBomConfig record);

    List<RdmsBomConfig> selectByExample(RdmsBomConfigExample example);

    RdmsBomConfig selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsBomConfig record, @Param("example") RdmsBomConfigExample example);

    int updateByExample(@Param("record") RdmsBomConfig record, @Param("example") RdmsBomConfigExample example);

    int updateByPrimaryKeySelective(RdmsBomConfig record);

    int updateByPrimaryKey(RdmsBomConfig record);
}