/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.util;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class AuthUtil {
    //察贤微信服务号,微信开放平台的安全配置信息
    public static final String APPID = "wx143faaa82d591523";
    public static final String APPSECRET = "5c6411af2a6272ea6354af9b87dc4fe5";

    //下面是调试用的 讲科学的 key
//    public static final String APPID = "wx18358e8e422c3033";
//    public static final String APPSECRET = "e3ca9b85121e3c5f3cb371d9368a4a60";

    public static JSONObject doGetJson(String url) throws IOException {
        JSONObject jsonObject = null;
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if(null != entity){
            String result = EntityUtils.toString(entity,"UTF-8");
            jsonObject = JSONObject.fromObject(result);
        }
        httpGet.releaseConnection();
        return jsonObject;
    }

    /**
     * 调用浏览器打开一个web页面
     * @param url
     */
    public static void openWebPage(String url) {
        // 判断当前系统是否支持Desktop类
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url)); // 打开指定的URL
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Desktop is not supported.");
        }
    }
}
