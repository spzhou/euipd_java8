/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.util;

import com.google.gson.Gson;

public class DeepCopyUtil {

    private static final Gson gson = new Gson();

    /**
     * 深度复制对象
     * 使用JSON序列化和反序列化实现对象的深度复制
     * 
     * @param source 源对象
     * @param clazz 目标类型
     * @param <T> 泛型类型
     * @return 返回深度复制后的新对象
     */
    public static <T> T deepCopy(T source, Class<T> clazz) {
        String json = gson.toJson(source);
        return gson.fromJson(json, clazz);
    }

}
