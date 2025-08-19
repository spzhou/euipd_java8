/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.ecology.util;

import lombok.Data;

@Data
public class OaRes {

    /**
     * 返回数据状态 SUCCESS：成功，PARAM_ERROR：参数错误，NO_PERMISSION：无权限，SYSTEM_INNER_ERROR：程序异常，USER_EXCEPTION：用户异常
     */
    private String code;

    /**
     * 接口状态为SUCCESS,则data中包含生成的requestid
     */
    private Data data;

    /**
     * 接口异常信息：例如状态为PARAM_ERROR 则返回错误参数信息
     */
    private String errMsg;

    @lombok.Data
    public static class Data {
        private Integer requestid;
    }
}
