/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsCustomerUserJobLevel;
import com.course.server.domain.RdmsCustomerUserJobLevelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsCustomerUserJobLevelMapper {
    long countByExample(RdmsCustomerUserJobLevelExample example);

    int deleteByExample(RdmsCustomerUserJobLevelExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsCustomerUserJobLevel record);

    int insertSelective(RdmsCustomerUserJobLevel record);

    List<RdmsCustomerUserJobLevel> selectByExample(RdmsCustomerUserJobLevelExample example);

    RdmsCustomerUserJobLevel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsCustomerUserJobLevel record, @Param("example") RdmsCustomerUserJobLevelExample example);

    int updateByExample(@Param("record") RdmsCustomerUserJobLevel record, @Param("example") RdmsCustomerUserJobLevelExample example);

    int updateByPrimaryKeySelective(RdmsCustomerUserJobLevel record);

    int updateByPrimaryKey(RdmsCustomerUserJobLevel record);
}