/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.enums;

public enum InstitutionStatusEnum {

    OPENING("OPENING", "营业"),
    CLOSED("CLOSED", "打烊"),
    STOP("STOP", "停业"),
    HOLIDAY("HOLIDAY", "放假"),
    CANCEL("CANCEL", "注销");

    private String code;

    private String desc;

    InstitutionStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
