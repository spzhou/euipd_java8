/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto;

import com.course.server.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class InstitutionDto {

    private String id;

    private String name;

    private String slogan;

    private String image;

    private String logo;

    private String contact;

    private String phone;

    private String address;

    private String video;

    private String vod;

    private String level;

    private String status;

    private Integer sort;

    private Date createdAt;

    private Date updatedAt;

    private String creatorLoginname;

    private Integer like;

    private Integer collect;

    private Integer watch;

    private Integer power;

    private Integer promotion;

    private List<CategoryDto> categorys;

    private String mainImgUrl;

    private List<String> instInfoImgUrls;

    private String contactInfoUrl;

    private String channelId;

    private String liveStatus;  //live end

    private String roleEnum;

    private String contactAvatar;

    private String contactQrCode;

    private String mainCoverImage;

    private Number watchNum; //展示量
    private Number likeNum; //点赞量
    private Number collectNum; //收藏量

    private String orgLink;

}
