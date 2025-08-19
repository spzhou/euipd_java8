/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.ShareCategory;
import com.course.server.domain.ShareCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShareCategoryMapper {
    long countByExample(ShareCategoryExample example);

    int deleteByExample(ShareCategoryExample example);

    int deleteByPrimaryKey(String id);

    int insert(ShareCategory record);

    int insertSelective(ShareCategory record);

    List<ShareCategory> selectByExample(ShareCategoryExample example);

    ShareCategory selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ShareCategory record, @Param("example") ShareCategoryExample example);

    int updateByExample(@Param("record") ShareCategory record, @Param("example") ShareCategoryExample example);

    int updateByPrimaryKeySelective(ShareCategory record);

    int updateByPrimaryKey(ShareCategory record);
}