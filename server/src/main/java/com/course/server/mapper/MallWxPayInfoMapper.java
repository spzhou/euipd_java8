/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.MallWxPayInfo;
import com.course.server.domain.MallWxPayInfoExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface MallWxPayInfoMapper {
    long countByExample(MallWxPayInfoExample example);

    int deleteByExample(MallWxPayInfoExample example);

    int deleteByPrimaryKey(String id);

    int insert(MallWxPayInfo record);

    int insertSelective(MallWxPayInfo record);

    List<MallWxPayInfo> selectByExample(MallWxPayInfoExample example);

    MallWxPayInfo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") MallWxPayInfo record, @Param("example") MallWxPayInfoExample example);

    int updateByExample(@Param("record") MallWxPayInfo record, @Param("example") MallWxPayInfoExample example);

    int updateByPrimaryKeySelective(MallWxPayInfo record);

    int updateByPrimaryKey(MallWxPayInfo record);
}