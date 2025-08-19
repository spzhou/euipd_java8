/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.ecology.util;

import lombok.Data;

@Data
public class Field<T> {
    private String fieldName;
    private T fieldValue;

    /**
     * 默认构造函数
     * 创建空的Field对象
     */
    public Field() {
    }

    /**
     * 带参数构造函数
     * 创建包含字段名和字段值的Field对象
     * 
     * @param fieldName 字段名
     * @param fieldValue 字段值
     */
    public Field(String fieldName, T fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
