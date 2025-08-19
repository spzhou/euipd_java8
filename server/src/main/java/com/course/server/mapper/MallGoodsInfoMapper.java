/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.MallGoodsInfo;
import com.course.server.domain.MallGoodsInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MallGoodsInfoMapper {
    long countByExample(MallGoodsInfoExample example);

    int deleteByExample(MallGoodsInfoExample example);

    int deleteByPrimaryKey(Long goodsId);

    int insert(MallGoodsInfo record);

    int insertSelective(MallGoodsInfo record);

    List<MallGoodsInfo> selectByExample(MallGoodsInfoExample example);

    MallGoodsInfo selectByPrimaryKey(Long goodsId);

    int updateByExampleSelective(@Param("record") MallGoodsInfo record, @Param("example") MallGoodsInfoExample example);

    int updateByExample(@Param("record") MallGoodsInfo record, @Param("example") MallGoodsInfoExample example);

    int updateByPrimaryKeySelective(MallGoodsInfo record);

    int updateByPrimaryKey(MallGoodsInfo record);
}