/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsFeeManage;
import com.course.server.domain.RdmsFeeManageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsFeeManageMapper {
    long countByExample(RdmsFeeManageExample example);

    int deleteByExample(RdmsFeeManageExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsFeeManage record);

    int insertSelective(RdmsFeeManage record);

    List<RdmsFeeManage> selectByExample(RdmsFeeManageExample example);

    RdmsFeeManage selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsFeeManage record, @Param("example") RdmsFeeManageExample example);

    int updateByExample(@Param("record") RdmsFeeManage record, @Param("example") RdmsFeeManageExample example);

    int updateByPrimaryKeySelective(RdmsFeeManage record);

    int updateByPrimaryKey(RdmsFeeManage record);
}