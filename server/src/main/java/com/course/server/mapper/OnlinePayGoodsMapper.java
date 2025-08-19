/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.OnlinePayGoods;
import com.course.server.domain.OnlinePayGoodsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OnlinePayGoodsMapper {
    long countByExample(OnlinePayGoodsExample example);

    int deleteByExample(OnlinePayGoodsExample example);

    int insert(OnlinePayGoods record);

    int insertSelective(OnlinePayGoods record);

    List<OnlinePayGoods> selectByExample(OnlinePayGoodsExample example);

    int updateByExampleSelective(@Param("record") OnlinePayGoods record, @Param("example") OnlinePayGoodsExample example);

    int updateByExample(@Param("record") OnlinePayGoods record, @Param("example") OnlinePayGoodsExample example);
}