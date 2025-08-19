/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsCategoryProject;
import com.course.server.domain.RdmsCategoryProjectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsCategoryProjectMapper {
    long countByExample(RdmsCategoryProjectExample example);

    int deleteByExample(RdmsCategoryProjectExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsCategoryProject record);

    int insertSelective(RdmsCategoryProject record);

    List<RdmsCategoryProject> selectByExample(RdmsCategoryProjectExample example);

    RdmsCategoryProject selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsCategoryProject record, @Param("example") RdmsCategoryProjectExample example);

    int updateByExample(@Param("record") RdmsCategoryProject record, @Param("example") RdmsCategoryProjectExample example);

    int updateByPrimaryKeySelective(RdmsCategoryProject record);

    int updateByPrimaryKey(RdmsCategoryProject record);
}