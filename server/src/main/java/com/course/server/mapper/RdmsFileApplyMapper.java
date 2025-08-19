/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsFileApply;
import com.course.server.domain.RdmsFileApplyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsFileApplyMapper {
    long countByExample(RdmsFileApplyExample example);

    int deleteByExample(RdmsFileApplyExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsFileApply record);

    int insertSelective(RdmsFileApply record);

    List<RdmsFileApply> selectByExample(RdmsFileApplyExample example);

    RdmsFileApply selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsFileApply record, @Param("example") RdmsFileApplyExample example);

    int updateByExample(@Param("record") RdmsFileApply record, @Param("example") RdmsFileApplyExample example);

    int updateByPrimaryKeySelective(RdmsFileApply record);

    int updateByPrimaryKey(RdmsFileApply record);
}