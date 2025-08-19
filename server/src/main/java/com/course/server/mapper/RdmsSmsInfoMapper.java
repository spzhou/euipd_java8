/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsSmsInfo;
import com.course.server.domain.RdmsSmsInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsSmsInfoMapper {
    long countByExample(RdmsSmsInfoExample example);

    int deleteByExample(RdmsSmsInfoExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsSmsInfo record);

    int insertSelective(RdmsSmsInfo record);

    List<RdmsSmsInfo> selectByExample(RdmsSmsInfoExample example);

    RdmsSmsInfo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsSmsInfo record, @Param("example") RdmsSmsInfoExample example);

    int updateByExample(@Param("record") RdmsSmsInfo record, @Param("example") RdmsSmsInfoExample example);

    int updateByPrimaryKeySelective(RdmsSmsInfo record);

    int updateByPrimaryKey(RdmsSmsInfo record);
}