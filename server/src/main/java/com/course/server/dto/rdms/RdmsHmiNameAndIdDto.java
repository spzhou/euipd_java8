/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import lombok.Data;

@Data
public class RdmsHmiNameAndIdDto {
    //获得的数据
    private String id;
    private String name;

    public RdmsHmiNameAndIdDto(String id, String name) {
        this.id = id;
        this.name= name;
    }
}
