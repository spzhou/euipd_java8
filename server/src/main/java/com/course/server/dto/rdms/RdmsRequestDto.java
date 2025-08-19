/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import lombok.Data;

@Data
public class RdmsRequestDto<T> {
    private String loginId;

    private String userId;

    private String customerUserId;

    private String customerId;

    private String projectId;

    private String preProjectId;

    private String subprojectId;

    private String characterId;

    private String qualityId;

    private String status;

    private String type;

    private T data;
}
