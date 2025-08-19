/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.enums;

public enum MemberActionEnum {

    WATCH("WATCH", "浏览"),
    COLLECT("COLLECT", "收藏"),
    LIKE("LIKE", "点赞"),
    PROFESSOR("PROFESSOR", "专家"),
    INSTITUTION("INSTITUTION", "机构");

    private String code;

    private String desc;

    MemberActionEnum(String code, String desc) {
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
