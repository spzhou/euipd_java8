/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.ecology.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class OaConfig {

    private static final Properties properties = new Properties();

    static {
        try (InputStream inputStream = OaConfig.class.getClassLoader().getResourceAsStream("config/ecology.properties")) {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new IOException("Properties file not found");
            }
        } catch (IOException e) {
//            e.printStackTrace();
            throw new RuntimeException("Error loading properties file", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getHost() {
        String key = "oa.host";
        return properties.getProperty(key);
    }

    public static String getSpk() {
        String key = "oa.spk";
        return properties.getProperty(key);
    }
    public static String getAppId() {
        String key = "oa.appid";
        return properties.getProperty(key);
    }
    public static String getSecret() {
        String key = "oa.secret";
        return properties.getProperty(key);
    }

}
