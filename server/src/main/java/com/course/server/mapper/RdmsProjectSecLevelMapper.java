/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsProjectSecLevel;
import com.course.server.domain.RdmsProjectSecLevelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsProjectSecLevelMapper {
    long countByExample(RdmsProjectSecLevelExample example);

    int deleteByExample(RdmsProjectSecLevelExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsProjectSecLevel record);

    int insertSelective(RdmsProjectSecLevel record);

    List<RdmsProjectSecLevel> selectByExample(RdmsProjectSecLevelExample example);

    RdmsProjectSecLevel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsProjectSecLevel record, @Param("example") RdmsProjectSecLevelExample example);

    int updateByExample(@Param("record") RdmsProjectSecLevel record, @Param("example") RdmsProjectSecLevelExample example);

    int updateByPrimaryKeySelective(RdmsProjectSecLevel record);

    int updateByPrimaryKey(RdmsProjectSecLevel record);
}