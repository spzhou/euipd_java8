/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsCustomerUser;
import com.course.server.domain.RdmsCustomerUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsCustomerUserMapper {
    long countByExample(RdmsCustomerUserExample example);

    int deleteByExample(RdmsCustomerUserExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsCustomerUser record);

    int insertSelective(RdmsCustomerUser record);

    List<RdmsCustomerUser> selectByExample(RdmsCustomerUserExample example);

    RdmsCustomerUser selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsCustomerUser record, @Param("example") RdmsCustomerUserExample example);

    int updateByExample(@Param("record") RdmsCustomerUser record, @Param("example") RdmsCustomerUserExample example);

    int updateByPrimaryKeySelective(RdmsCustomerUser record);

    int updateByPrimaryKey(RdmsCustomerUser record);
}