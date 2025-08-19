/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsOaHrmUser;
import com.course.server.domain.RdmsOaHrmUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsOaHrmUserMapper {
    long countByExample(RdmsOaHrmUserExample example);

    int deleteByExample(RdmsOaHrmUserExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsOaHrmUser record);

    int insertSelective(RdmsOaHrmUser record);

    List<RdmsOaHrmUser> selectByExample(RdmsOaHrmUserExample example);

    RdmsOaHrmUser selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsOaHrmUser record, @Param("example") RdmsOaHrmUserExample example);

    int updateByExample(@Param("record") RdmsOaHrmUser record, @Param("example") RdmsOaHrmUserExample example);

    int updateByPrimaryKeySelective(RdmsOaHrmUser record);

    int updateByPrimaryKey(RdmsOaHrmUser record);
}