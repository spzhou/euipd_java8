/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class FileDto {

    /**
     * id
     */
    private String id;

    /**
     * 相对路径
     */
    private String path;
    /**
     * 绝对路径
     */
    private String absPath;

    /**
     * 文件名
     */
    private String name;

    /**
     * 后缀
     */
    private String suffix;

    /**
     * 大小|字节B
     */
    private Integer size;

    /**
     * 用途|枚举[FileUseEnum]：COURSE("C", "讲师"), TEACHER("T", "课程")
     */
    private String use;

    private String customer;  //机构客户

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdAt;

    /**
     * 修改时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updatedAt;

    private Integer shardIndex;

    private Integer shardSize;

    private Integer shardTotal;

    private String key;

    /**
     * base64
     */
    private String shard;

    private String vod;

    private Integer time;

}
