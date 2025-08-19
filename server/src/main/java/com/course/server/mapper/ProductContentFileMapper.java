/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.ProductContentFile;
import com.course.server.domain.ProductContentFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductContentFileMapper {
    long countByExample(ProductContentFileExample example);

    int deleteByExample(ProductContentFileExample example);

    int deleteByPrimaryKey(String id);

    int insert(ProductContentFile record);

    int insertSelective(ProductContentFile record);

    List<ProductContentFile> selectByExample(ProductContentFileExample example);

    ProductContentFile selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ProductContentFile record, @Param("example") ProductContentFileExample example);

    int updateByExample(@Param("record") ProductContentFile record, @Param("example") ProductContentFileExample example);

    int updateByPrimaryKeySelective(ProductContentFile record);

    int updateByPrimaryKey(ProductContentFile record);
}