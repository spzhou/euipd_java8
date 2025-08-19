/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.generator.enums;

import com.course.server.enums.*;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnumGenerator {
//    static String path = "admin\\public\\static\\js\\enums.js";
    static String path = "web\\public\\static\\js\\enums.js";

    public static void main(String[] args) {
        StringBuffer bufferObject = new StringBuffer();
        StringBuffer bufferArray = new StringBuffer();
        long begin = System.currentTimeMillis();
        try {
            toJson(SectionChargeEnum.class, bufferObject, bufferArray);
            toJson(YesNoEnum.class, bufferObject, bufferArray);
            toJson(CourseLevelEnum.class, bufferObject, bufferArray);
            toJson(CourseChargeEnum.class, bufferObject, bufferArray);
            toJson(CourseStatusEnum.class, bufferObject, bufferArray);
            toJson(FileUseEnum.class, bufferObject, bufferArray);
            toJson(SmsUseEnum.class, bufferObject, bufferArray);
            toJson(SmsStatusEnum.class, bufferObject, bufferArray);

            StringBuffer buffer = bufferObject.append("\r\n").append(bufferArray);
            writeJs(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("执行耗时:" + (end - begin) + " 毫秒");
    }

    /**
     * 将枚举类转换为JSON格式
     * 生成枚举类的对象和数组表示，用于前端JavaScript使用
     * 
     * @param clazz 枚举类
     * @param bufferObject 对象缓冲区
     * @param bufferArray 数组缓冲区
     * @throws Exception 反射调用异常
     */
    private static void toJson(Class clazz, StringBuffer bufferObject, StringBuffer bufferArray) throws Exception {
        String key = toUnderline(clazz.getSimpleName());
        toJson(clazz, key, bufferObject, bufferArray);
    }

    /**
     * 将枚举类转换为JSON格式
     * 根据枚举类生成JavaScript对象和数组代码
     * 
     * @param clazz 枚举类
     * @param key 枚举键名
     * @param bufferObject 对象缓冲区
     * @param bufferArray 数组缓冲区
     * @throws Exception 反射调用异常
     */
    private static void toJson(Class clazz, String key, StringBuffer bufferObject, StringBuffer bufferArray) throws Exception {
        Object[] objects = clazz.getEnumConstants();
        Method name = clazz.getMethod("name");
        Method getDesc = clazz.getMethod("getDesc");
        Method getCode = clazz.getMethod("getCode");

        // 生成对象
        bufferObject.append(key).append("={");
        for (int i = 0; i < objects.length; i++) {
            Object obj = objects[i];
            if (getCode == null) {
                bufferObject.append(name.invoke(obj)).append(":{key:\"").append(name.invoke(obj)).append("\", value:\"").append(getDesc.invoke(obj)).append("\"}");
            } else {
                bufferObject.append(name.invoke(obj)).append(":{key:\"").append(getCode.invoke(obj)).append("\", value:\"").append(getDesc.invoke(obj)).append("\"}");
            }
            if (i < objects.length - 1) {
                bufferObject.append(",");
            }
        }
        bufferObject.append("};\r\n");

        // 生成数组
        bufferArray.append(key).append("_ARRAY=[");
        for (int i = 0; i < objects.length; i++) {
            Object obj = objects[i];
            if (getCode == null) {
                bufferArray.append("{key:\"").append(name.invoke(obj)).append("\", value:\"").append(getDesc.invoke(obj)).append("\"}");
            } else {
                bufferArray.append("{key:\"").append(getCode.invoke(obj)).append("\", value:\"").append(getDesc.invoke(obj)).append("\"}");
            }
            if (i < objects.length - 1) {
                bufferArray.append(",");
            }
        }
        bufferArray.append("];\r\n");
    }

    /**
     * 写文件
     * @param stringBuffer
     */
    public static void writeJs(StringBuffer stringBuffer) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(path);
            OutputStreamWriter osw = new OutputStreamWriter(out, "UTF-8");
            System.out.println(path);
            osw.write(stringBuffer.toString());
            osw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 功能：驼峰转大写下划线，并去掉_ENUM
     * 如：SectionChargeEnum 变成 SECTION_CHARGE
     * @param str
     * @return
     */
    public static String toUnderline(String str) {
        String result = underline(str).toString();
        return result.substring(1, result.length()).toUpperCase().replace("_ENUM", "");
    }

    /**
     * 驼峰转下划线，第一位是下划线
     * 如：SectionChargeEnum 变成 _section_charge_enum
     * 
     * @param str 需要转换的驼峰字符串
     * @return 返回转换后的下划线字符串
     */
    private static StringBuffer underline(String str) {
        Pattern pattern = Pattern.compile("[A-Z]");
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer(str);
        if(matcher.find()) {
            sb = new StringBuffer();
            matcher.appendReplacement(sb,"_"+matcher.group(0).toLowerCase());
            matcher.appendTail(sb);
        }else {
            return sb;
        }
        return underline(sb.toString());
    }
}
