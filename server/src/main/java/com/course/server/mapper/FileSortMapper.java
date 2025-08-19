/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.FileSort;
import com.course.server.domain.FileSortExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FileSortMapper {
    long countByExample(FileSortExample example);

    int deleteByExample(FileSortExample example);

    int deleteByPrimaryKey(String id);

    int insert(FileSort record);

    int insertSelective(FileSort record);

    List<FileSort> selectByExample(FileSortExample example);

    FileSort selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") FileSort record, @Param("example") FileSortExample example);

    int updateByExample(@Param("record") FileSort record, @Param("example") FileSortExample example);

    int updateByPrimaryKeySelective(FileSort record);

    int updateByPrimaryKey(FileSort record);
}