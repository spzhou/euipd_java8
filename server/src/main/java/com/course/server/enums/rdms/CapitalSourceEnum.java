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
public enum CapitalSourceEnum {

    SUBSIDY("SUBSIDY", "国拨"),
    SELF("SELF", "自筹");

    private String source;
    private String sourceName;

    CapitalSourceEnum(String source, String sourceName) {
        this.source = source;
        this.sourceName = sourceName;
    }

    public static CapitalSourceEnum getCapitalSourceEnumBySource(String source) {
        for (CapitalSourceEnum sourceEnum : CapitalSourceEnum.values()) {
            if (Objects.equals(sourceEnum.getSource(), source)) {
                return sourceEnum;
            }
        }
        return null;
    }

    public static CapitalSourceEnum getCapitalSourceEnumByName(String sourceName) {
        for (CapitalSourceEnum sourceEnum : CapitalSourceEnum.values()) {
            if (sourceEnum.getSourceName().equals(sourceName)) {
                return sourceEnum;
            }
        }
        return null;
    }

}
