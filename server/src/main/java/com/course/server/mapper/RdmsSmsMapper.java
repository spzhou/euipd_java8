/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsSms;
import com.course.server.domain.RdmsSmsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsSmsMapper {
    long countByExample(RdmsSmsExample example);

    int deleteByExample(RdmsSmsExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsSms record);

    int insertSelective(RdmsSms record);

    List<RdmsSms> selectByExample(RdmsSmsExample example);

    RdmsSms selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsSms record, @Param("example") RdmsSmsExample example);

    int updateByExample(@Param("record") RdmsSms record, @Param("example") RdmsSmsExample example);

    int updateByPrimaryKeySelective(RdmsSms record);

    int updateByPrimaryKey(RdmsSms record);
}