/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsManhourStandard;
import com.course.server.domain.RdmsManhourStandardExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsManhourStandardMapper {
    long countByExample(RdmsManhourStandardExample example);

    int deleteByExample(RdmsManhourStandardExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsManhourStandard record);

    int insertSelective(RdmsManhourStandard record);

    List<RdmsManhourStandard> selectByExample(RdmsManhourStandardExample example);

    RdmsManhourStandard selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsManhourStandard record, @Param("example") RdmsManhourStandardExample example);

    int updateByExample(@Param("record") RdmsManhourStandard record, @Param("example") RdmsManhourStandardExample example);

    int updateByPrimaryKeySelective(RdmsManhourStandard record);

    int updateByPrimaryKey(RdmsManhourStandard record);
}