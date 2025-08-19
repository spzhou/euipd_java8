/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.enums;

public enum ImgClassEnum {

    MAIN("MAIN", "产品主图"),
    PRODUCT("PRODUCT", "产品图"),
    MARQUEE("MARQUEE", "底部跑马灯");

    private String code;

    private String desc;

    ImgClassEnum(String code, String desc) {
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

    public static ImgClassEnum getImgClassEnumByCode(String code) {
        for (ImgClassEnum imgClassEnum : ImgClassEnum.values()) {
            if (imgClassEnum.getCode() == code) {
                return imgClassEnum;
            }
        }
        return PRODUCT;
    }

    public static ImgClassEnum getImgClassEnumByDesc(String desc) {
        for (ImgClassEnum imgClassEnum : ImgClassEnum.values()) {
            if (imgClassEnum.getDesc().equals(desc)) {
                return imgClassEnum;
            }
        }
        return PRODUCT;
    }

}
