/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsBomTreeMain;
import com.course.server.domain.RdmsBomTreeMainExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsBomTreeMainMapper {
    long countByExample(RdmsBomTreeMainExample example);

    int deleteByExample(RdmsBomTreeMainExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsBomTreeMain record);

    int insertSelective(RdmsBomTreeMain record);

    List<RdmsBomTreeMain> selectByExample(RdmsBomTreeMainExample example);

    RdmsBomTreeMain selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsBomTreeMain record, @Param("example") RdmsBomTreeMainExample example);

    int updateByExample(@Param("record") RdmsBomTreeMain record, @Param("example") RdmsBomTreeMainExample example);

    int updateByPrimaryKeySelective(RdmsBomTreeMain record);

    int updateByPrimaryKey(RdmsBomTreeMain record);
}