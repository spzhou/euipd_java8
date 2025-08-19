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
public class RdmsHmiMergeCharactersDto {
    private String customerId;
    private String preProjectId;
    private String productManagerId;
    private String productManagerName;
    private List<String> characterIdList;
    private List<String> characterNameList;

}
