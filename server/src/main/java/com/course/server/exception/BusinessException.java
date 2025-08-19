/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.exception;

public class BusinessException extends RuntimeException{

    private BusinessExceptionCode code;

    /**
     * 构造业务异常
     * 使用业务异常代码创建业务异常实例
     * 
     * @param code 业务异常代码对象
     */
    public BusinessException (BusinessExceptionCode code) {
        super(code.getDesc());
        this.code = code;
    }

    /**
     * 获取业务异常代码
     * 
     * @return 返回业务异常代码对象
     */
    public BusinessExceptionCode getCode() {
        return code;
    }

    /**
     * 设置业务异常代码
     * 
     * @param code 业务异常代码对象
     */
    public void setCode(BusinessExceptionCode code) {
        this.code = code;
    }

    /**
     * 不写入堆栈信息，提高性能
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}