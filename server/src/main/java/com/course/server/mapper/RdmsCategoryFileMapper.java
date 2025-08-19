/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsCategoryFile;
import com.course.server.domain.RdmsCategoryFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsCategoryFileMapper {
    long countByExample(RdmsCategoryFileExample example);

    int deleteByExample(RdmsCategoryFileExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsCategoryFile record);

    int insertSelective(RdmsCategoryFile record);

    List<RdmsCategoryFile> selectByExample(RdmsCategoryFileExample example);

    RdmsCategoryFile selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsCategoryFile record, @Param("example") RdmsCategoryFileExample example);

    int updateByExample(@Param("record") RdmsCategoryFile record, @Param("example") RdmsCategoryFileExample example);

    int updateByPrimaryKeySelective(RdmsCategoryFile record);

    int updateByPrimaryKey(RdmsCategoryFile record);
}