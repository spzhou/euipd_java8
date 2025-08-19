/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto;

import lombok.Data;

@Data
public class MainShowMenuDto {
    private String id;
    private Integer sort;
    private String videoClass;
    private String videoId;
    private String title;
    private String image;
    private String url;
    private String vod;
    private String institutionId;
    private String subjectTitle;
    private String videoFromInstId; //视频来源方的机构Id
    private Boolean usePlatformPlayList;
    private Integer platFlag;
}