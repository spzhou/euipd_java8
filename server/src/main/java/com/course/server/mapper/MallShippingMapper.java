/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.MallShipping;
import com.course.server.domain.MallShippingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MallShippingMapper {
    long countByExample(MallShippingExample example);

    int deleteByExample(MallShippingExample example);

    int deleteByPrimaryKey(String id);

    int insert(MallShipping record);

    int insertSelective(MallShipping record);

    List<MallShipping> selectByExample(MallShippingExample example);

    MallShipping selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") MallShipping record, @Param("example") MallShippingExample example);

    int updateByExample(@Param("record") MallShipping record, @Param("example") MallShippingExample example);

    int updateByPrimaryKeySelective(MallShipping record);

    int updateByPrimaryKey(MallShipping record);
}