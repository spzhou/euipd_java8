/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto;


import java.util.List;

public class EnterDto {

    /**
     * id
     */
    private String loginName;

    /**
     * 审批码
     */
    private String code;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "EnterDto{" +
                "loginName='" + loginName + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}