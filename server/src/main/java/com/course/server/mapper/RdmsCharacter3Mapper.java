/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsCharacter3;
import com.course.server.domain.RdmsCharacter3Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsCharacter3Mapper {
    long countByExample(RdmsCharacter3Example example);

    int deleteByExample(RdmsCharacter3Example example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsCharacter3 record);

    int insertSelective(RdmsCharacter3 record);

    List<RdmsCharacter3> selectByExample(RdmsCharacter3Example example);

    RdmsCharacter3 selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsCharacter3 record, @Param("example") RdmsCharacter3Example example);

    int updateByExample(@Param("record") RdmsCharacter3 record, @Param("example") RdmsCharacter3Example example);

    int updateByPrimaryKeySelective(RdmsCharacter3 record);

    int updateByPrimaryKey(RdmsCharacter3 record);
}