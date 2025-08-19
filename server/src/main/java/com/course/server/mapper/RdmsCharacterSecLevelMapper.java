/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsCharacterSecLevel;
import com.course.server.domain.RdmsCharacterSecLevelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsCharacterSecLevelMapper {
    long countByExample(RdmsCharacterSecLevelExample example);

    int deleteByExample(RdmsCharacterSecLevelExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsCharacterSecLevel record);

    int insertSelective(RdmsCharacterSecLevel record);

    List<RdmsCharacterSecLevel> selectByExample(RdmsCharacterSecLevelExample example);

    RdmsCharacterSecLevel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsCharacterSecLevel record, @Param("example") RdmsCharacterSecLevelExample example);

    int updateByExample(@Param("record") RdmsCharacterSecLevel record, @Param("example") RdmsCharacterSecLevelExample example);

    int updateByPrimaryKeySelective(RdmsCharacterSecLevel record);

    int updateByPrimaryKey(RdmsCharacterSecLevel record);
}