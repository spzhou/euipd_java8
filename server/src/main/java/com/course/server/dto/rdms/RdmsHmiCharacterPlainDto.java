/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import lombok.Data;

@Data
public class RdmsHmiCharacterPlainDto {
    private String id;
    private String characterName;

    public RdmsHmiCharacterPlainDto(String id, String characterName){
        this.id = id;
        this.characterName = characterName;
    }

}
