/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.enums;

public enum InstitutionImgClassEnum {

    MAIN("MAIN", "机构主图"),
    LOGO("LOGO", "LOGO"),
    INFO("INFO", "机构信息"),
    CONTACT("CONTACT", "联系信息");

    private String code;

    private String desc;

    InstitutionImgClassEnum(String code, String desc) {
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

    public static InstitutionImgClassEnum getImgClassEnumByCode(String code) {
        for (InstitutionImgClassEnum imgClassEnum : InstitutionImgClassEnum.values()) {
            if (imgClassEnum.getCode() == code) {
                return imgClassEnum;
            }
        }
        return MAIN;
    }

    public static InstitutionImgClassEnum getImgClassEnumByDesc(String desc) {
        for (InstitutionImgClassEnum imgClassEnum : InstitutionImgClassEnum.values()) {
            if (imgClassEnum.getDesc().equals(desc)) {
                return imgClassEnum;
            }
        }
        return MAIN;
    }

}
