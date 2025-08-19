/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.util;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class CopyUtil {

    /**
     * 复制对象列表
     * 将源对象列表中的每个对象复制为目标类型的对象
     * 
     * @param source 源对象列表
     * @param clazz 目标类型
     * @param <T> 目标类型泛型
     * @return 返回复制后的目标类型对象列表
     */
    public static <T> List<T> copyList(List source, Class<T> clazz) {
        List<T> target = new ArrayList<>();
        if (!CollectionUtils.isEmpty(source)){
            if (!CollectionUtils.isEmpty(source)){
                for (Object c: source) {
                    T obj = copy(c, clazz);
                    target.add(obj);
                }
            }
        }
        return target;
    }

    /**
     * 复制单个对象
     * 将源对象复制为目标类型的对象
     * 
     * @param source 源对象
     * @param clazz 目标类型
     * @param <T> 目标类型泛型
     * @return 返回复制后的目标类型对象，如果源对象为空则返回null
     */
    public static <T> T copy(Object source, Class<T> clazz) {
        if (source == null) {
            return null;
        }
        T obj = null;
        try {
            obj = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        BeanUtils.copyProperties(source, obj);
        return obj;
    }
}
