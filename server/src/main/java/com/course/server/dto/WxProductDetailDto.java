/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto;

import lombok.Data;

import java.util.List;

@Data
public class WxProductDetailDto {
    private String id;
    private String title;
    private String instName;
    private String createTime;
    private String videoUrl;
    private String coverImg;
    private String like;
    private String watch;
    private String logoImg;
    private String instIntroduce;
    private List<WxProductDto> productList;
}