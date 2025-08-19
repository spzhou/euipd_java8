/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.enums;

public enum ShowClassEnum {

    PLAN(1, "计划"),
    LIVE(2, "正在直播"),
    REPEATE(0, "回放");

    private Integer code;

    private String desc;

    ShowClassEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public static ShowClassEnum getShowClassEnumByCode(Integer code) {
        for (ShowClassEnum enumEntity : ShowClassEnum.values()) {
            if (enumEntity.getCode() == code) {
                return enumEntity;
            }
        }
        return REPEATE;
    }

    public static ShowClassEnum getShowClassEnumByDesc(String desc) {
        for (ShowClassEnum enumEntity : ShowClassEnum.values()) {
            if (enumEntity.getDesc().equals(desc)) {
                return enumEntity;
            }
        }
        return REPEATE;
    }

}
