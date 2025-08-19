/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.Sms;
import com.course.server.domain.SmsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmsMapper {
    long countByExample(SmsExample example);

    int deleteByExample(SmsExample example);

    int deleteByPrimaryKey(String id);

    int insert(Sms record);

    int insertSelective(Sms record);

    List<Sms> selectByExample(SmsExample example);

    Sms selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Sms record, @Param("example") SmsExample example);

    int updateByExample(@Param("record") Sms record, @Param("example") SmsExample example);

    int updateByPrimaryKeySelective(Sms record);

    int updateByPrimaryKey(Sms record);
}