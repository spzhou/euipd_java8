/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsVodFile;
import com.course.server.domain.RdmsVodFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsVodFileMapper {
    long countByExample(RdmsVodFileExample example);

    int deleteByExample(RdmsVodFileExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsVodFile record);

    int insertSelective(RdmsVodFile record);

    List<RdmsVodFile> selectByExample(RdmsVodFileExample example);

    RdmsVodFile selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsVodFile record, @Param("example") RdmsVodFileExample example);

    int updateByExample(@Param("record") RdmsVodFile record, @Param("example") RdmsVodFileExample example);

    int updateByPrimaryKeySelective(RdmsVodFile record);

    int updateByPrimaryKey(RdmsVodFile record);
}