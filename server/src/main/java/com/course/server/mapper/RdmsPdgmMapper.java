/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsPdgm;
import com.course.server.domain.RdmsPdgmExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsPdgmMapper {
    long countByExample(RdmsPdgmExample example);

    int deleteByExample(RdmsPdgmExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsPdgm record);

    int insertSelective(RdmsPdgm record);

    List<RdmsPdgm> selectByExample(RdmsPdgmExample example);

    RdmsPdgm selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsPdgm record, @Param("example") RdmsPdgmExample example);

    int updateByExample(@Param("record") RdmsPdgm record, @Param("example") RdmsPdgmExample example);

    int updateByPrimaryKeySelective(RdmsPdgm record);

    int updateByPrimaryKey(RdmsPdgm record);
}