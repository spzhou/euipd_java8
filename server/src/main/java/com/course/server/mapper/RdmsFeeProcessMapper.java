/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsFeeProcess;
import com.course.server.domain.RdmsFeeProcessExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsFeeProcessMapper {
    long countByExample(RdmsFeeProcessExample example);

    int deleteByExample(RdmsFeeProcessExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsFeeProcess record);

    int insertSelective(RdmsFeeProcess record);

    List<RdmsFeeProcess> selectByExample(RdmsFeeProcessExample example);

    RdmsFeeProcess selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsFeeProcess record, @Param("example") RdmsFeeProcessExample example);

    int updateByExample(@Param("record") RdmsFeeProcess record, @Param("example") RdmsFeeProcessExample example);

    int updateByPrimaryKeySelective(RdmsFeeProcess record);

    int updateByPrimaryKey(RdmsFeeProcess record);
}