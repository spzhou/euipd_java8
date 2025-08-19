/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.MallOrder;
import com.course.server.domain.MallOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MallOrderMapper {
    long countByExample(MallOrderExample example);

    int deleteByExample(MallOrderExample example);

    int insert(MallOrder record);

    int insertSelective(MallOrder record);

    List<MallOrder> selectByExample(MallOrderExample example);

    int updateByExampleSelective(@Param("record") MallOrder record, @Param("example") MallOrderExample example);

    int updateByExample(@Param("record") MallOrder record, @Param("example") MallOrderExample example);
}