/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsBomTree;
import com.course.server.domain.RdmsBomTreeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsBomTreeMapper {
    long countByExample(RdmsBomTreeExample example);

    int deleteByExample(RdmsBomTreeExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsBomTree record);

    int insertSelective(RdmsBomTree record);

    List<RdmsBomTree> selectByExample(RdmsBomTreeExample example);

    RdmsBomTree selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsBomTree record, @Param("example") RdmsBomTreeExample example);

    int updateByExample(@Param("record") RdmsBomTree record, @Param("example") RdmsBomTreeExample example);

    int updateByPrimaryKeySelective(RdmsBomTree record);

    int updateByPrimaryKey(RdmsBomTree record);
}