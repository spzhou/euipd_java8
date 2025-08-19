/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto;

import lombok.Data;

//类字段对应FileSort实体类
@Data
public class WxMiniShowListDto {
    private String id;
    private String videoClass;
    private String paramId;
    private String title;
    private String institution;
    private String logoUrl;
    private String videoCover;
    private String videoUrl;
    private String content;
    private String likeNum;
    private String watchNum;
    private String createTime;
    private Integer sort;  //随机排序
    private Integer score;  //根据watch\like\collect计算的得分
}