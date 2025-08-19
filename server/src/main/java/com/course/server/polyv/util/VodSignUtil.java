/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.polyv.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * polyv 点播签名工具类
 * @author: thomas
 **/

public class VodSignUtil {
    private static final Logger log = LoggerFactory.getLogger(VodSignUtil.class);
    private static final String UTF8 = "UTF-8";
 
    
    
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    
    /**
     * 点播签名方法
     * @param params 签名参数
     * @param secretKey 签名密钥
     * @return 签名
     * @throws NoSuchAlgorithmException 异常异常
     */
    public static String getSign(Map<String, String> params, String secretKey)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        log.debug("参与签名参数：{}",  params );
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String plain = "";
        for (String key : keys) {
            if (null != params.get(key) && params.get(key).length() > 0) {
                plain += key + "=" + params.get(key) + "&";
            }
        }
        if (plain != null && !"".equals(plain.trim())) {
            plain = plain.substring(0, plain.length() - 1);
        }
        plain += secretKey;
        log.debug("签名原始字符串：{}", plain);
        String sign = getSha1(plain).toUpperCase();
        log.debug("签名结果：{}", sign);
        return sign;
    }
    
 
    
    /**
     * sha1算法签名
     * @param input 签名原始字符串
     * @return 签名
     * @throws NoSuchAlgorithmException 签名异常
     */
    public static String getSha1(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes( UTF8));
        StringBuilder sb = new StringBuilder();
        for (int b : result) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
        
    }


    /**
     * 获取直播加密字符串，并且假如到参数params中
     * @param params 加密参数
     * @param appSecret 保利威用户签名密钥
     * @return  MD5签名字符串
     * @throws NoSuchAlgorithmException 签名异常
     *  @throws UnsupportedEncodingException 编码异常
     */
    public static String getSignMd5(Map<String, String> params, String appSecret)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // 处理参数，计算MD5哈希值
        log.debug("参与签名参数：{}" ,  params );
        String concatStr = concatParams(params);
        String plain = appSecret + concatStr + appSecret;
        log.debug("签名原始字符串：{}" , plain);
        String encrypted = md5Hex(plain).toUpperCase();
        log.debug("签名结果： {}" , encrypted);
        // 32位大写MD5值
        return encrypted;
    }

    /**
     * 把数组所有元素排序，并按照“参数参数值”的模式成字符串，用于计算MD5哈希值
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String concatParams(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            String value = params.get(key);
            if (value == null || "".equals(value.trim())) {
                continue;
            }
            sb.append(key).append(value);
        }
        return sb.toString();
    }

    /**
     * 对字符串做MD5加密，返回加密后的字符串。
     * @param text 待加密的字符串。
     * @return 加密后的字符串。
     * @throws NoSuchAlgorithmException 签名异常
     *  @throws UnsupportedEncodingException 编码异常
     */
    public static String md5Hex(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] inputByteArray = text.getBytes(UTF8);
        messageDigest.update(inputByteArray);
        byte[] resultByteArray = messageDigest.digest();
        return byteArrayToHex(resultByteArray).toLowerCase();

    }

    /**
     * 将字节数组换成成16进制的字符串
     * @param byteArray 字节
     * @return 字符串
     */
    public static String byteArrayToHex(byte[] byteArray) {
        // 初始化一个字符数组用来存放每个16进制字符
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        // new一个字符数组，这个就是用来组成结果字符串的（一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] resultCharArray = new char[byteArray.length * 2];
        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        // 字符数组组合成字符串返回
        return new String(resultCharArray);
    }
    
}