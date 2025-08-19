/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.Channel;
import com.course.server.domain.ChannelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChannelMapper {
    long countByExample(ChannelExample example);

    int deleteByExample(ChannelExample example);

    int deleteByPrimaryKey(String id);

    int insert(Channel record);

    int insertSelective(Channel record);

    List<Channel> selectByExample(ChannelExample example);

    Channel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Channel record, @Param("example") ChannelExample example);

    int updateByExample(@Param("record") Channel record, @Param("example") ChannelExample example);

    int updateByPrimaryKeySelective(Channel record);

    int updateByPrimaryKey(Channel record);
}