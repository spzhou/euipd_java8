/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsCustomer2;
import com.course.server.domain.RdmsCustomer2Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsCustomer2Mapper {
    long countByExample(RdmsCustomer2Example example);

    int deleteByExample(RdmsCustomer2Example example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsCustomer2 record);

    int insertSelective(RdmsCustomer2 record);

    List<RdmsCustomer2> selectByExample(RdmsCustomer2Example example);

    RdmsCustomer2 selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsCustomer2 record, @Param("example") RdmsCustomer2Example example);

    int updateByExample(@Param("record") RdmsCustomer2 record, @Param("example") RdmsCustomer2Example example);

    int updateByPrimaryKeySelective(RdmsCustomer2 record);

    int updateByPrimaryKey(RdmsCustomer2 record);
}