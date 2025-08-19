/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsMaterialManage3;
import com.course.server.domain.RdmsMaterialManage3Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsMaterialManage3Mapper {
    long countByExample(RdmsMaterialManage3Example example);

    int deleteByExample(RdmsMaterialManage3Example example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsMaterialManage3 record);

    int insertSelective(RdmsMaterialManage3 record);

    List<RdmsMaterialManage3> selectByExample(RdmsMaterialManage3Example example);

    RdmsMaterialManage3 selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsMaterialManage3 record, @Param("example") RdmsMaterialManage3Example example);

    int updateByExample(@Param("record") RdmsMaterialManage3 record, @Param("example") RdmsMaterialManage3Example example);

    int updateByPrimaryKeySelective(RdmsMaterialManage3 record);

    int updateByPrimaryKey(RdmsMaterialManage3 record);
}