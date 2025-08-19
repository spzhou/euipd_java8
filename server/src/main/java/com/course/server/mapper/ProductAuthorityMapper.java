/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.ProductAuthority;
import com.course.server.domain.ProductAuthorityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductAuthorityMapper {
    long countByExample(ProductAuthorityExample example);

    int deleteByExample(ProductAuthorityExample example);

    int deleteByPrimaryKey(String id);

    int insert(ProductAuthority record);

    int insertSelective(ProductAuthority record);

    List<ProductAuthority> selectByExample(ProductAuthorityExample example);

    ProductAuthority selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ProductAuthority record, @Param("example") ProductAuthorityExample example);

    int updateByExample(@Param("record") ProductAuthority record, @Param("example") ProductAuthorityExample example);

    int updateByPrimaryKeySelective(ProductAuthority record);

    int updateByPrimaryKey(ProductAuthority record);
}