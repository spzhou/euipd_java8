/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsTgm;
import com.course.server.domain.RdmsTgmExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsTgmMapper {
    long countByExample(RdmsTgmExample example);

    int deleteByExample(RdmsTgmExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsTgm record);

    int insertSelective(RdmsTgm record);

    List<RdmsTgm> selectByExample(RdmsTgmExample example);

    RdmsTgm selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsTgm record, @Param("example") RdmsTgmExample example);

    int updateByExample(@Param("record") RdmsTgm record, @Param("example") RdmsTgmExample example);

    int updateByPrimaryKeySelective(RdmsTgm record);

    int updateByPrimaryKey(RdmsTgm record);
}