/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.GoodsVideo;
import com.course.server.domain.GoodsVideoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GoodsVideoMapper {
    long countByExample(GoodsVideoExample example);

    int deleteByExample(GoodsVideoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(GoodsVideo record);

    int insertSelective(GoodsVideo record);

    List<GoodsVideo> selectByExample(GoodsVideoExample example);

    GoodsVideo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") GoodsVideo record, @Param("example") GoodsVideoExample example);

    int updateByExample(@Param("record") GoodsVideo record, @Param("example") GoodsVideoExample example);

    int updateByPrimaryKeySelective(GoodsVideo record);

    int updateByPrimaryKey(GoodsVideo record);
}