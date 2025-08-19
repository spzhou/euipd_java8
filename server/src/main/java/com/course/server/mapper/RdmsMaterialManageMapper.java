/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsMaterialManage;
import com.course.server.domain.RdmsMaterialManageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsMaterialManageMapper {
    long countByExample(RdmsMaterialManageExample example);

    int deleteByExample(RdmsMaterialManageExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsMaterialManage record);

    int insertSelective(RdmsMaterialManage record);

    List<RdmsMaterialManage> selectByExample(RdmsMaterialManageExample example);

    RdmsMaterialManage selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsMaterialManage record, @Param("example") RdmsMaterialManageExample example);

    int updateByExample(@Param("record") RdmsMaterialManage record, @Param("example") RdmsMaterialManageExample example);

    int updateByPrimaryKeySelective(RdmsMaterialManage record);

    int updateByPrimaryKey(RdmsMaterialManage record);
}