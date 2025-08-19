/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper;

import com.course.server.domain.InstitutionBlackList;
import com.course.server.domain.InstitutionBlackListExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InstitutionBlackListMapper {
    long countByExample(InstitutionBlackListExample example);

    int deleteByExample(InstitutionBlackListExample example);

    int insert(InstitutionBlackList record);

    int insertSelective(InstitutionBlackList record);

    List<InstitutionBlackList> selectByExample(InstitutionBlackListExample example);

    int updateByExampleSelective(@Param("record") InstitutionBlackList record, @Param("example") InstitutionBlackListExample example);

    int updateByExample(@Param("record") InstitutionBlackList record, @Param("example") InstitutionBlackListExample example);
}