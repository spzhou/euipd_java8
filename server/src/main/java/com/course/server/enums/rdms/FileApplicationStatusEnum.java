/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.enums.rdms;

import lombok.Getter;

import java.util.Objects;

@Getter
public enum FileApplicationStatusEnum {
    NOTSET("NOTSET", "未设置"),
    APPLICATION("APPLICATION", "申请"),
    REJECT("REJECT", "拒绝"),
    APPROVED("APPROVED", "已批准"),
    COMPLETE("COMPLETE", "结束"),
    CANCEL("CANCEL", "取消"),
    DOWNLOADED("DOWNLOADED", "已下载");

    private final String status;
    private final String name;

    FileApplicationStatusEnum(String status, String name) {
        this.status = status;
        this.name = name;
    }

    public static FileApplicationStatusEnum getFileApplicationStatusByStatus(String status) {
        for (FileApplicationStatusEnum fileApplicationStatusEnum : FileApplicationStatusEnum.values()) {
            if (Objects.equals(fileApplicationStatusEnum.getStatus(), status)) {
                return fileApplicationStatusEnum;
            }
        }
        return null;
    }

    public static FileApplicationStatusEnum getFileApplicationStatusByName(String name) {
        for (FileApplicationStatusEnum fileApplicationStatusEnum : FileApplicationStatusEnum.values()) {
            if (fileApplicationStatusEnum.getName().equals(name)) {
                return fileApplicationStatusEnum;
            }
        }
        return null;
    }
}
