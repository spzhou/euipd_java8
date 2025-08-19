/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsFileReference;
import com.course.server.domain.RdmsFileReferenceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsFileReferenceMapper {
    long countByExample(RdmsFileReferenceExample example);

    int deleteByExample(RdmsFileReferenceExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsFileReference record);

    int insertSelective(RdmsFileReference record);

    List<RdmsFileReference> selectByExample(RdmsFileReferenceExample example);

    RdmsFileReference selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsFileReference record, @Param("example") RdmsFileReferenceExample example);

    int updateByExample(@Param("record") RdmsFileReference record, @Param("example") RdmsFileReferenceExample example);

    int updateByPrimaryKeySelective(RdmsFileReference record);

    int updateByPrimaryKey(RdmsFileReference record);
}