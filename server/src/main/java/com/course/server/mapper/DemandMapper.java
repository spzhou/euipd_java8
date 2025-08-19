/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.Demand;
import com.course.server.domain.DemandExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DemandMapper {
    long countByExample(DemandExample example);

    int deleteByExample(DemandExample example);

    int deleteByPrimaryKey(String id);

    int insert(Demand record);

    int insertSelective(Demand record);

    List<Demand> selectByExample(DemandExample example);

    Demand selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Demand record, @Param("example") DemandExample example);

    int updateByExample(@Param("record") Demand record, @Param("example") DemandExample example);

    int updateByPrimaryKeySelective(Demand record);

    int updateByPrimaryKey(Demand record);
}