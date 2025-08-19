/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.LoginNameProduct;
import com.course.server.domain.LoginNameProductExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LoginNameProductMapper {
    long countByExample(LoginNameProductExample example);

    int deleteByExample(LoginNameProductExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LoginNameProduct record);

    int insertSelective(LoginNameProduct record);

    List<LoginNameProduct> selectByExample(LoginNameProductExample example);

    LoginNameProduct selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LoginNameProduct record, @Param("example") LoginNameProductExample example);

    int updateByExample(@Param("record") LoginNameProduct record, @Param("example") LoginNameProductExample example);

    int updateByPrimaryKeySelective(LoginNameProduct record);

    int updateByPrimaryKey(LoginNameProduct record);
}