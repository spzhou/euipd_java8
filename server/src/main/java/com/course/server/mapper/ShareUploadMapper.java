/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.ShareUpload;
import com.course.server.domain.ShareUploadExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShareUploadMapper {
    long countByExample(ShareUploadExample example);

    int deleteByExample(ShareUploadExample example);

    int deleteByPrimaryKey(String id);

    int insert(ShareUpload record);

    int insertSelective(ShareUpload record);

    List<ShareUpload> selectByExample(ShareUploadExample example);

    ShareUpload selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ShareUpload record, @Param("example") ShareUploadExample example);

    int updateByExample(@Param("record") ShareUpload record, @Param("example") ShareUploadExample example);

    int updateByPrimaryKeySelective(ShareUpload record);

    int updateByPrimaryKey(ShareUpload record);
}