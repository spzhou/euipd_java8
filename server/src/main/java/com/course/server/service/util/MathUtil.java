/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.util;

import java.math.BigDecimal;

public class MathUtil {
    /**
     * 格式化小数位数
     * 将double类型数值格式化为指定小数位数的字符串，使用四舍五入规则
     * 
     * @param d 需要格式化的double数值
     * @param decimal 保留的小数位数
     * @return 返回格式化后的字符串
     */
    public static String decimalFormat(double d, int decimal) {
        //double d=111231.5585;
        BigDecimal b=new BigDecimal(d);
        Double f1 = b.setScale(decimal,BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1.toString();
    }
}
