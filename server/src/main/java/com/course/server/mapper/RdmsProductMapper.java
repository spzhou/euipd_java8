/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsProduct;
import com.course.server.domain.RdmsProductExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsProductMapper {
    long countByExample(RdmsProductExample example);

    int deleteByExample(RdmsProductExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsProduct record);

    int insertSelective(RdmsProduct record);

    List<RdmsProduct> selectByExample(RdmsProductExample example);

    RdmsProduct selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsProduct record, @Param("example") RdmsProductExample example);

    int updateByExample(@Param("record") RdmsProduct record, @Param("example") RdmsProductExample example);

    int updateByPrimaryKeySelective(RdmsProduct record);

    int updateByPrimaryKey(RdmsProduct record);
}