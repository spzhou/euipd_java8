/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.RdmsSysgm;
import com.course.server.domain.RdmsSysgmExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RdmsSysgmMapper {
    long countByExample(RdmsSysgmExample example);

    int deleteByExample(RdmsSysgmExample example);

    int deleteByPrimaryKey(String id);

    int insert(RdmsSysgm record);

    int insertSelective(RdmsSysgm record);

    List<RdmsSysgm> selectByExample(RdmsSysgmExample example);

    RdmsSysgm selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RdmsSysgm record, @Param("example") RdmsSysgmExample example);

    int updateByExample(@Param("record") RdmsSysgm record, @Param("example") RdmsSysgmExample example);

    int updateByPrimaryKeySelective(RdmsSysgm record);

    int updateByPrimaryKey(RdmsSysgm record);
}