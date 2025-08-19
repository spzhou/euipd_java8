/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsCharacter2;
import com.course.server.domain.RdmsCharacter2Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsCharacter2Mapper {
    long countByExample(RdmsCharacter2Example example);

    int deleteByExample(RdmsCharacter2Example example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsCharacter2 record);

    int insertSelective(RdmsCharacter2 record);

    List<RdmsCharacter2> selectByExample(RdmsCharacter2Example example);

    RdmsCharacter2 selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsCharacter2 record, @Param("example") RdmsCharacter2Example example);

    int updateByExample(@Param("record") RdmsCharacter2 record, @Param("example") RdmsCharacter2Example example);

    int updateByPrimaryKeySelective(RdmsCharacter2 record);

    int updateByPrimaryKey(RdmsCharacter2 record);
}