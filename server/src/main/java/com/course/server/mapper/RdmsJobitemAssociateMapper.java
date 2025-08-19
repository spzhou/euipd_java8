/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsJobitemAssociate;
import com.course.server.domain.RdmsJobitemAssociateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsJobitemAssociateMapper {
    long countByExample(RdmsJobitemAssociateExample example);

    int deleteByExample(RdmsJobitemAssociateExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsJobitemAssociate record);

    int insertSelective(RdmsJobitemAssociate record);

    List<RdmsJobitemAssociate> selectByExample(RdmsJobitemAssociateExample example);

    RdmsJobitemAssociate selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsJobitemAssociate record, @Param("example") RdmsJobitemAssociateExample example);

    int updateByExample(@Param("record") RdmsJobitemAssociate record, @Param("example") RdmsJobitemAssociateExample example);

    int updateByPrimaryKeySelective(RdmsJobitemAssociate record);

    int updateByPrimaryKey(RdmsJobitemAssociate record);
}