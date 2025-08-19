/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.ecology.util;

import org.jetbrains.annotations.NotNull;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RsaEncrypt {
    /**
     * RSA公钥加密
     * 使用RSA公钥对明文进行加密
     * 
     * @param plainText 需要加密的明文
     * @param publicKeyStr RSA公钥字符串
     * @return 返回Base64编码的加密结果
     * @throws Exception 加密异常
     */
    public static String encrypt(@NotNull String plainText, String publicKeyStr) throws Exception {
        // 去除公钥字符串中的换行符和开头结尾的标记
        publicKeyStr = publicKeyStr.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", "");

        // 将公钥字符串转换为字节数组
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyStr);

        // 使用 X509EncodedKeySpec 从字节数组中加载公钥
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);

        // 创建 Cipher 实例并初始化为加密模式
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        // 加密明文
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));

        // 将加密后的字节数组转换为 Base64 编码的字符串
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
}
