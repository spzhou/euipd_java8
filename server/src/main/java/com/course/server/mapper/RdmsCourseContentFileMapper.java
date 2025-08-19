/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsCourseContentFile;
import com.course.server.domain.RdmsCourseContentFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsCourseContentFileMapper {
    long countByExample(RdmsCourseContentFileExample example);

    int deleteByExample(RdmsCourseContentFileExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsCourseContentFile record);

    int insertSelective(RdmsCourseContentFile record);

    List<RdmsCourseContentFile> selectByExample(RdmsCourseContentFileExample example);

    RdmsCourseContentFile selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsCourseContentFile record, @Param("example") RdmsCourseContentFileExample example);

    int updateByExample(@Param("record") RdmsCourseContentFile record, @Param("example") RdmsCourseContentFileExample example);

    int updateByPrimaryKeySelective(RdmsCourseContentFile record);

    int updateByPrimaryKey(RdmsCourseContentFile record);
}