/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.enums;

public enum FileGroupingEnum {

    SYSTEM("system", "系统"),
    USER_INFO_TEMPLATE("user_info_template", "用户信息模板"),
    CUSTOMER("customer", "客户"),
    USER("user", "用户"),
    DEMAND("demand", "需求文件"),
    PRE_PROJECT("preproject", "预立项文件"),
    PROJECT("project", "项目文件"),
    CHARACTER("character", "特性定义"),
    JOBITEM("jobitem", "工单文件"),
    MATERIAL_APP("material_app", "物料申请"),
    MATERIAL_DEAL("material_deal", "物料处置"),
    MATERIAL_SUBMIT("material_submit", "处置完成"),
    FEE_APP("fee_app", "费用申请"),
    FEE_DEAL("fee_deal", "费用处理"),
    FEE_SUBMIT("fee_submit", "支付完成"),
    QUALITY("quality", "质量处理"),
    BUG("bug", "缺陷处理"),
    PROPERTY("property", "研发资产"),
    COURSE("course", "培训课程");

    private String code;

    private String desc;

    FileGroupingEnum(String code, String desc) {
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

    public static FileGroupingEnum getByCode(String code){
        for(FileGroupingEnum e: FileGroupingEnum.values()){
            if(code.equals(e.getCode())){
                return e;
            }
        }
        return  null;
    }
}
