/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper.rdms;

import com.course.server.domain.RdmsDepartment;
import com.course.server.domain.RdmsUser;
import com.course.server.domain.rdms.MyCharacter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyCharacterMapper {
    List<MyCharacter> getCharacterUnionJobitemList(
            @Param("subprojectId") String subprojectId,
            @Param("characterStatus") String characterStatus,
            @Param("jobitemStatus") String jobitemStatus,
            @Param("jobitemType") String jobitemType);

    List<MyCharacter> getCharacterUnionJobitemListByTypeList(
            @Param("subprojectId") String subprojectId,
            @Param("characterStatusList") List<String> characterStatusList,
            @Param("jobitemStatus") String jobitemStatus,
            @Param("jobitemTypeList") List<String> jobitemTypeList);

    List<MyCharacter> getCharacterListByIdList(
            @Param("customerId") String customerId,
            @Param("characterIdList") List<String> characterIdList);

}
