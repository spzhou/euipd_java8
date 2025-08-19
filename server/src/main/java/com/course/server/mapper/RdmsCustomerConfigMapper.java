/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsCustomerConfig;
import com.course.server.domain.RdmsCustomerConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsCustomerConfigMapper {
    long countByExample(RdmsCustomerConfigExample example);

    int deleteByExample(RdmsCustomerConfigExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsCustomerConfig record);

    int insertSelective(RdmsCustomerConfig record);

    List<RdmsCustomerConfig> selectByExample(RdmsCustomerConfigExample example);

    RdmsCustomerConfig selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsCustomerConfig record, @Param("example") RdmsCustomerConfigExample example);

    int updateByExample(@Param("record") RdmsCustomerConfig record, @Param("example") RdmsCustomerConfigExample example);

    int updateByPrimaryKeySelective(RdmsCustomerConfig record);

    int updateByPrimaryKey(RdmsCustomerConfig record);
}