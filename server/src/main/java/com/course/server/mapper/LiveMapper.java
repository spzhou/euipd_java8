/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.Live;
import com.course.server.domain.LiveExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LiveMapper {
    long countByExample(LiveExample example);

    int deleteByExample(LiveExample example);

    int deleteByPrimaryKey(String id);

    int insert(Live record);

    int insertSelective(Live record);

    List<Live> selectByExample(LiveExample example);

    Live selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Live record, @Param("example") LiveExample example);

    int updateByExample(@Param("record") Live record, @Param("example") LiveExample example);

    int updateByPrimaryKeySelective(Live record);

    int updateByPrimaryKey(Live record);
}