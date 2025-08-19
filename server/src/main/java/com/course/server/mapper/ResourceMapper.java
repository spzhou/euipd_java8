/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.ResourceAuth;
import com.course.server.domain.ResourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ResourceMapper {
    long countByExample(ResourceExample example);

    int deleteByExample(ResourceExample example);

    int deleteByPrimaryKey(String id);

    int insert(ResourceAuth record);

    int insertSelective(ResourceAuth record);

    List<ResourceAuth> selectByExample(ResourceExample example);

    ResourceAuth selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ResourceAuth record, @Param("example") ResourceExample example);

    int updateByExample(@Param("record") ResourceAuth record, @Param("example") ResourceExample example);

    int updateByPrimaryKeySelective(ResourceAuth record);

    int updateByPrimaryKey(ResourceAuth record);
}