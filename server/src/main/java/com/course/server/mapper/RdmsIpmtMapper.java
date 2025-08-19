/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsIpmt;
import com.course.server.domain.RdmsIpmtExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsIpmtMapper {
    long countByExample(RdmsIpmtExample example);

    int deleteByExample(RdmsIpmtExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsIpmt record);

    int insertSelective(RdmsIpmt record);

    List<RdmsIpmt> selectByExample(RdmsIpmtExample example);

    RdmsIpmt selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsIpmt record, @Param("example") RdmsIpmtExample example);

    int updateByExample(@Param("record") RdmsIpmt record, @Param("example") RdmsIpmtExample example);

    int updateByPrimaryKeySelective(RdmsIpmt record);

    int updateByPrimaryKey(RdmsIpmt record);
}