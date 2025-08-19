/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsUnderline;
import com.course.server.domain.RdmsUnderlineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsUnderlineMapper {
    long countByExample(RdmsUnderlineExample example);

    int deleteByExample(RdmsUnderlineExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsUnderline record);

    int insertSelective(RdmsUnderline record);

    List<RdmsUnderline> selectByExample(RdmsUnderlineExample example);

    RdmsUnderline selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsUnderline record, @Param("example") RdmsUnderlineExample example);

    int updateByExample(@Param("record") RdmsUnderline record, @Param("example") RdmsUnderlineExample example);

    int updateByPrimaryKeySelective(RdmsUnderline record);

    int updateByPrimaryKey(RdmsUnderline record);
}