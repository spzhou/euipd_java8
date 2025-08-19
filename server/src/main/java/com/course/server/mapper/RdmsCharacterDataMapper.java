/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsCharacterData;
import com.course.server.domain.RdmsCharacterDataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsCharacterDataMapper {
    long countByExample(RdmsCharacterDataExample example);

    int deleteByExample(RdmsCharacterDataExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsCharacterData record);

    int insertSelective(RdmsCharacterData record);

    List<RdmsCharacterData> selectByExample(RdmsCharacterDataExample example);

    RdmsCharacterData selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsCharacterData record, @Param("example") RdmsCharacterDataExample example);

    int updateByExample(@Param("record") RdmsCharacterData record, @Param("example") RdmsCharacterDataExample example);

    int updateByPrimaryKeySelective(RdmsCharacterData record);

    int updateByPrimaryKey(RdmsCharacterData record);
}