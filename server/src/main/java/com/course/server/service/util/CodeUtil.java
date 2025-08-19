/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class CodeUtil {
    /**
     * 生成随机工单号
     * 生成包含时间戳和随机数的工单号
     * 
     * @return 返回20位工单号
     */
    public static String randomJobNum() {
        SimpleDateFormat dmDate = new SimpleDateFormat("yyyyMMddHHmmss");
        String randata = getRandom(6);
        Date date = new Date();
        String dateran = dmDate.format(date);
        String Xsode = "JN" + dateran + randata;
        if (Xsode.length() < 22) {
            Xsode = Xsode + 0;
        }
        return Xsode.substring(0,20);
    }

    /**
     * 生成随机项目编码
     * 生成包含时间戳和随机数的项目编码
     * 
     * @return 返回20位项目编码
     */
    public static String randomProjectCode() {
        SimpleDateFormat dmDate = new SimpleDateFormat("yyyyMMddHHmmss");
        String randata = getRandom(6);
        Date date = new Date();
        String dateran = dmDate.format(date);
        String Xsode = "Pn" + dateran + randata;
        if (Xsode.length() < 22) {
            Xsode = Xsode + 0;
        }
        return Xsode.substring(0,20);
    }

    /**
     * 生成随机质量编号
     * 生成包含时间戳和随机数的质量编号
     * 
     * @return 返回20位质量编号
     */
    public static String randomQualityNum() {
        SimpleDateFormat dmDate = new SimpleDateFormat("yyyyMMddHHmmss");
        String randata = getRandom(6);
        Date date = new Date();
        String dateran = dmDate.format(date);
        String Xsode = "QN" + dateran + randata;
        if (Xsode.length() < 22) {
            Xsode = Xsode + 0;
        }
        return Xsode.substring(0,20);
    }

    /**
     * 生成指定长度的随机数字字符串
     * 生成指定长度的纯数字随机字符串
     * 
     * @param len 随机字符串长度
     * @return 返回指定长度的随机数字字符串
     */
    public static String getRandom(int len) {
        Random r = new Random();
        StringBuilder rs = new StringBuilder();
        for (int i = 0; i < len; i++) {
            rs.append(r.nextInt(10));
        }
        return rs.toString();
    }
}
