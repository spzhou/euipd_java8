/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.MallShoppingCartItem;
import com.course.server.domain.MallShoppingCartItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MallShoppingCartItemMapper {
    long countByExample(MallShoppingCartItemExample example);

    int deleteByExample(MallShoppingCartItemExample example);

    int deleteByPrimaryKey(String cartItemId);

    int insert(MallShoppingCartItem record);

    int insertSelective(MallShoppingCartItem record);

    List<MallShoppingCartItem> selectByExample(MallShoppingCartItemExample example);

    MallShoppingCartItem selectByPrimaryKey(String cartItemId);

    int updateByExampleSelective(@Param("record") MallShoppingCartItem record, @Param("example") MallShoppingCartItemExample example);

    int updateByExample(@Param("record") MallShoppingCartItem record, @Param("example") MallShoppingCartItemExample example);

    int updateByPrimaryKeySelective(MallShoppingCartItem record);

    int updateByPrimaryKey(MallShoppingCartItem record);
}