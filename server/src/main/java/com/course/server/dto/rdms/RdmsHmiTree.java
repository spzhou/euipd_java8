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
public class RdmsHmiTree<T> {
    private String id;

    private String label;

    private String value;

    private String parent;

    private Integer deep;

    private String status;
    private String auxStatus;
    private String versionUpdate;

    private T obj;

    private List<RdmsHmiTree<T>> children;

    private int createTimeStamp;

}
