/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.util;

import com.course.server.exception.ValidatorException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

public class ValidatorUtil {

    /**
     * 空校验（null or ""）
     */
    public static void require(Object str, String fieldName) {
        if (ObjectUtils.isEmpty(str)) {
            throw new ValidatorException(fieldName + "不能为空");
        }
    }

    /**
     * 长度校验
     */
    public static void length(String str, String fieldName, int min, int max) {
        if (ObjectUtils.isEmpty(str)) {
            return;
        }
        int length = 0;
        if (!ObjectUtils.isEmpty(str)) {
            length = str.length();
        }
        if (length < min || length > max) {
            throw new ValidatorException(fieldName + "长度" + min + "~" + max + "位");
        }
    }

    /**
     * 判断数组非空
     */
    public static void listNotEmpty(List<?> array, String fieldName) {
        if (CollectionUtils.isEmpty(array)) {
            throw new ValidatorException(fieldName + "不能为空");
        }
    }

    /**
     * 数字校验_判断是否是数字
     */
    public static void isNumeric(String str, String fieldName) {
        if (! org.apache.commons.lang3.StringUtils.isNumeric(str)) {
            throw new ValidatorException(fieldName + "应为数字");
        }
    }


}
