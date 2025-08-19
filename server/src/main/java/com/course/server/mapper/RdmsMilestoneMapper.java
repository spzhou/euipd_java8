/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsMilestone;
import com.course.server.domain.RdmsMilestoneExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsMilestoneMapper {
    long countByExample(RdmsMilestoneExample example);

    int deleteByExample(RdmsMilestoneExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsMilestone record);

    int insertSelective(RdmsMilestone record);

    List<RdmsMilestone> selectByExample(RdmsMilestoneExample example);

    RdmsMilestone selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsMilestone record, @Param("example") RdmsMilestoneExample example);

    int updateByExample(@Param("record") RdmsMilestone record, @Param("example") RdmsMilestoneExample example);

    int updateByPrimaryKeySelective(RdmsMilestone record);

    int updateByPrimaryKey(RdmsMilestone record);
}