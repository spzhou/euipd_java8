/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtil {

    private NumberUtil() {
    }


    /**
     * 判断是否为11位电话号码
     * 验证手机号码格式是否符合中国大陆手机号规则
     *
     * @param phone 手机号码字符串
     * @return 返回是否为有效的手机号码
     */
    public static boolean isPhone(String phone) {
        Pattern pattern = Pattern.compile("^((13[0-9])|(14[5,7])|(15[^4,\\D])|(17[0-8])|(18[0-9]))\\d{8}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    /**
     * 生成指定长度的随机数
     * 生成指定位数的随机整数
     *
     * @param length 随机数的位数
     * @return 返回指定位数的随机整数
     */
    public static int genRandomNum(int length) {
        int num = 1;
        double random = Math.random();
        if (random < 0.1) {
            random = random + 0.1;
        }
        for (int i = 0; i < length; i++) {
            num = num * 10;
        }
        return (int) ((random * num));
    }

    /**
     * 生成订单流水号
     * 生成包含时间戳和随机数的订单流水号
     *
     * @return 返回订单流水号字符串
     */
    public static String genOrderNo() {
        StringBuffer buffer = new StringBuffer(String.valueOf(System.currentTimeMillis()));
        int num = genRandomNum(4);
        buffer.append(num);
        return buffer.toString();
    }
}
