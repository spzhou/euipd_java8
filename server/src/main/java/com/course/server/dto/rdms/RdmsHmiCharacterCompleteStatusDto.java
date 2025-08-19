/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import lombok.Data;

@Data
public class RdmsHmiCharacterCompleteStatusDto {
    private Boolean completeStatus;  // 是否完成
    private Boolean intComplete;  //集成任务是否完成
    private Integer materialApplicationNum;
    private Long materialCompleteNum;
    private Integer feeApplicationNum;
    private Long feeCompleteNum;
}
