/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RdmsHmiProjectAndJobItemMixDto {
    private String id;

    private String itemName; // jobName  projectName

    private String status;

    private String type;  //jobitem parameter

    private String docType;

    private String projectType;  //jobitem parameter

    private String parentOrChildren;  // value:  parent  children

    private List<RdmsHmiProjectAndJobItemMixDto> children;

    private boolean hasIntJobitem;

    private int index;

    private Boolean isReviewing;  //评审中
    private Boolean isCompleted;  //完成状态
    private Boolean isDevCompleted;  //"伪"完成状态
}
