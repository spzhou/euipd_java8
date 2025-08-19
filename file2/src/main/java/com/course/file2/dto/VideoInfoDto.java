/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.file2.dto;

import lombok.Data;

@Data
public class VideoInfoDto {
    private String Status; //视频状态
    private Long CateId;  //分类ID
    private String VideoId;  //VOD
    private String Tags;  //标签
    private String RegionId;  //地域标识
    private String CateName;  //分类名称
    private Long Size;  //大小
    private String CoverURL;  //封面URL
    private String TemplateGroupId;  //模板ID
    private String Title;  //标题
    private Float Duration;  //时长(秒)

}
