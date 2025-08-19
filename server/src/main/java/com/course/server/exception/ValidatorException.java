/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.exception;

public class ValidatorException extends RuntimeException{

    public ValidatorException(String message) {
        super(message);
    }
}
