/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.InstitutionContentFile;
import com.course.server.domain.InstitutionContentFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InstitutionContentFileMapper {
    long countByExample(InstitutionContentFileExample example);

    int deleteByExample(InstitutionContentFileExample example);

    int deleteByPrimaryKey(String id);

    int insert(InstitutionContentFile record);

    int insertSelective(InstitutionContentFile record);

    List<InstitutionContentFile> selectByExample(InstitutionContentFileExample example);

    InstitutionContentFile selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") InstitutionContentFile record, @Param("example") InstitutionContentFileExample example);

    int updateByExample(@Param("record") InstitutionContentFile record, @Param("example") InstitutionContentFileExample example);

    int updateByPrimaryKeySelective(InstitutionContentFile record);

    int updateByPrimaryKey(InstitutionContentFile record);
}