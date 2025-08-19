/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsCharacterProcess;
import com.course.server.domain.RdmsCharacterProcessExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsCharacterProcessMapper {
    long countByExample(RdmsCharacterProcessExample example);

    int deleteByExample(RdmsCharacterProcessExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsCharacterProcess record);

    int insertSelective(RdmsCharacterProcess record);

    List<RdmsCharacterProcess> selectByExample(RdmsCharacterProcessExample example);

    RdmsCharacterProcess selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsCharacterProcess record, @Param("example") RdmsCharacterProcessExample example);

    int updateByExample(@Param("record") RdmsCharacterProcess record, @Param("example") RdmsCharacterProcessExample example);

    int updateByPrimaryKeySelective(RdmsCharacterProcess record);

    int updateByPrimaryKey(RdmsCharacterProcess record);
}