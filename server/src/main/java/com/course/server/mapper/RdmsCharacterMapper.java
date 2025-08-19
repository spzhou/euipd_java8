/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsCharacter;
import com.course.server.domain.RdmsCharacterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsCharacterMapper {
    long countByExample(RdmsCharacterExample example);

    int deleteByExample(RdmsCharacterExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsCharacter record);

    int insertSelective(RdmsCharacter record);

    List<RdmsCharacter> selectByExample(RdmsCharacterExample example);

    RdmsCharacter selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsCharacter record, @Param("example") RdmsCharacterExample example);

    int updateByExample(@Param("record") RdmsCharacter record, @Param("example") RdmsCharacterExample example);

    int updateByPrimaryKeySelective(RdmsCharacter record);

    int updateByPrimaryKey(RdmsCharacter record);
}