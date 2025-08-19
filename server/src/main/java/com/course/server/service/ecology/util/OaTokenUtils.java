/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.ecology.util;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

public class OaTokenUtils {
    /**
     * 获取OA系统认证令牌
     * 通过HTTP请求向OA系统申请认证令牌，使用RSA加密密钥
     * 
     * @return 返回OA系统认证令牌
     * @throws Exception 请求异常或加密异常
     */
    public static String getToken() throws Exception {
        // 创建HttpClient对象
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // 创建HttpPost对象
            HttpPost httpPost = new HttpPost(OaConfig.getHost()+"/api/ec/dev/auth/applytoken");

            // 设置请求头
            httpPost.setHeader("appid", OaConfig.getAppId());
            httpPost.setHeader("Content-Type", "application/json");
            //对secret使用公钥加密后的字符串
            String encryptedText = RsaEncrypt.encrypt(OaConfig.getSecret(), OaConfig.getSpk());
            httpPost.setHeader("secret", encryptedText);

            // 执行请求
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                // 检查响应状态
                if (response.getStatusLine().getStatusCode() == 200) {
                    // 解析响应体
                    HttpEntity responseEntity = response.getEntity();
                    String result = EntityUtils.toString(responseEntity);
                    OAResp resp = JSON.parseObject(result, OAResp.class);
                    return resp.getToken();
                } else {
                    throw new RuntimeException("Failed to get token: " + response.getStatusLine());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
