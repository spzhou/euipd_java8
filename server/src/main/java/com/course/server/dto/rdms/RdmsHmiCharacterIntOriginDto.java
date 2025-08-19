/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import lombok.Data;

import java.util.List;

@Data
public class RdmsHmiCharacterIntOriginDto {
    private RdmsCharacterDto character;
    private RdmsJobItemDto intJobitem;  //集成工单
    private List<RdmsJobItemDto> assistJobitemList;  //集成工单的协作工单列表

}
