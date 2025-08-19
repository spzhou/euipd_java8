/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.file2.dto;

import lombok.Data;

@Data
public class AliyunCallBackDto {
    private String EventTime;  //事件时间
    private String EventType;  //事件类型
    private String VideoId;   //vod
    private String Status;   //success 或 fail
    private Float Duration;   //时长(秒)
    private String ErrorCode;  //错误代码
    private String FileUrl;  //视频流地址
}
