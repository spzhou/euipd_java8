/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsFileDownload;
import com.course.server.domain.RdmsFileDownloadExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsFileDownloadMapper {
    long countByExample(RdmsFileDownloadExample example);

    int deleteByExample(RdmsFileDownloadExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsFileDownload record);

    int insertSelective(RdmsFileDownload record);

    List<RdmsFileDownload> selectByExample(RdmsFileDownloadExample example);

    RdmsFileDownload selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsFileDownload record, @Param("example") RdmsFileDownloadExample example);

    int updateByExample(@Param("record") RdmsFileDownload record, @Param("example") RdmsFileDownloadExample example);

    int updateByPrimaryKeySelective(RdmsFileDownload record);

    int updateByPrimaryKey(RdmsFileDownload record);
}