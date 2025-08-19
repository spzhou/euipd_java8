/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsBugFeedback;
import com.course.server.domain.RdmsBugFeedbackExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsBugFeedbackMapper {
    long countByExample(RdmsBugFeedbackExample example);

    int deleteByExample(RdmsBugFeedbackExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsBugFeedback record);

    int insertSelective(RdmsBugFeedback record);

    List<RdmsBugFeedback> selectByExample(RdmsBugFeedbackExample example);

    RdmsBugFeedback selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsBugFeedback record, @Param("example") RdmsBugFeedbackExample example);

    int updateByExample(@Param("record") RdmsBugFeedback record, @Param("example") RdmsBugFeedbackExample example);

    int updateByPrimaryKeySelective(RdmsBugFeedback record);

    int updateByPrimaryKey(RdmsBugFeedback record);
}