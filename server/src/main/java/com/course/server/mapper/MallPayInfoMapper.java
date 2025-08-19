/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.MallPayInfo;
import com.course.server.domain.MallPayInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MallPayInfoMapper {
    long countByExample(MallPayInfoExample example);

    int deleteByExample(MallPayInfoExample example);

    int deleteByPrimaryKey(String id);

    int insert(MallPayInfo record);

    int insertSelective(MallPayInfo record);

    List<MallPayInfo> selectByExample(MallPayInfoExample example);

    MallPayInfo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") MallPayInfo record, @Param("example") MallPayInfoExample example);

    int updateByExample(@Param("record") MallPayInfo record, @Param("example") MallPayInfoExample example);

    int updateByPrimaryKeySelective(MallPayInfo record);

    int updateByPrimaryKey(MallPayInfo record);
}