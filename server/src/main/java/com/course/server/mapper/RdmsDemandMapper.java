/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsDemand;
import com.course.server.domain.RdmsDemandExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsDemandMapper {
    long countByExample(RdmsDemandExample example);

    int deleteByExample(RdmsDemandExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsDemand record);

    int insertSelective(RdmsDemand record);

    List<RdmsDemand> selectByExample(RdmsDemandExample example);

    RdmsDemand selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsDemand record, @Param("example") RdmsDemandExample example);

    int updateByExample(@Param("record") RdmsDemand record, @Param("example") RdmsDemandExample example);

    int updateByPrimaryKeySelective(RdmsDemand record);

    int updateByPrimaryKey(RdmsDemand record);
}