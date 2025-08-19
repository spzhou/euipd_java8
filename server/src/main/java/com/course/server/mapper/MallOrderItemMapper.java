/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.MallOrderItem;
import com.course.server.domain.MallOrderItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MallOrderItemMapper {
    long countByExample(MallOrderItemExample example);

    int deleteByExample(MallOrderItemExample example);

    int deleteByPrimaryKey(String orderItemId);

    int insert(MallOrderItem record);

    int insertSelective(MallOrderItem record);

    List<MallOrderItem> selectByExample(MallOrderItemExample example);

    MallOrderItem selectByPrimaryKey(String orderItemId);

    int updateByExampleSelective(@Param("record") MallOrderItem record, @Param("example") MallOrderItemExample example);

    int updateByExample(@Param("record") MallOrderItem record, @Param("example") MallOrderItemExample example);

    int updateByPrimaryKeySelective(MallOrderItem record);

    int updateByPrimaryKey(MallOrderItem record);
}