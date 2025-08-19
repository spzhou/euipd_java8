/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper.rdms;

import com.course.server.dto.ResourceDto;
import com.course.server.dto.rdms.RdmsCharacterBudgetDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyCharacterBudgetMapper {

    List<RdmsCharacterBudgetDto> listDecomposeCharacterBudget(@Param("parentCharacterId") String  parentCharacterId);
}
