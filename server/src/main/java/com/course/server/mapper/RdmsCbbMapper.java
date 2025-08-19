/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsCbb;
import com.course.server.domain.RdmsCbbExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsCbbMapper {
    long countByExample(RdmsCbbExample example);

    int deleteByExample(RdmsCbbExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsCbb record);

    int insertSelective(RdmsCbb record);

    List<RdmsCbb> selectByExample(RdmsCbbExample example);

    RdmsCbb selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsCbb record, @Param("example") RdmsCbbExample example);

    int updateByExample(@Param("record") RdmsCbb record, @Param("example") RdmsCbbExample example);

    int updateByPrimaryKeySelective(RdmsCbb record);

    int updateByPrimaryKey(RdmsCbb record);
}