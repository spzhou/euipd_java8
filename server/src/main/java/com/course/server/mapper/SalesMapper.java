/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.Sales;
import com.course.server.domain.SalesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SalesMapper {
    long countByExample(SalesExample example);

    int deleteByExample(SalesExample example);

    int deleteByPrimaryKey(String id);

    int insert(Sales record);

    int insertSelective(Sales record);

    List<Sales> selectByExample(SalesExample example);

    Sales selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Sales record, @Param("example") SalesExample example);

    int updateByExample(@Param("record") Sales record, @Param("example") SalesExample example);

    int updateByPrimaryKeySelective(Sales record);

    int updateByPrimaryKey(Sales record);
}