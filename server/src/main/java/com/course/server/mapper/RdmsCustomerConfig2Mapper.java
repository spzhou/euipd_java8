/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsCustomerConfig2;
import com.course.server.domain.RdmsCustomerConfig2Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsCustomerConfig2Mapper {
    long countByExample(RdmsCustomerConfig2Example example);

    int deleteByExample(RdmsCustomerConfig2Example example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsCustomerConfig2 record);

    int insertSelective(RdmsCustomerConfig2 record);

    List<RdmsCustomerConfig2> selectByExample(RdmsCustomerConfig2Example example);

    RdmsCustomerConfig2 selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsCustomerConfig2 record, @Param("example") RdmsCustomerConfig2Example example);

    int updateByExample(@Param("record") RdmsCustomerConfig2 record, @Param("example") RdmsCustomerConfig2Example example);

    int updateByPrimaryKeySelective(RdmsCustomerConfig2 record);

    int updateByPrimaryKey(RdmsCustomerConfig2 record);
}