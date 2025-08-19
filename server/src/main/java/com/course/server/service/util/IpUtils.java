/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.util;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

@Component
public class IpUtils {

    private static final Logger logger = LoggerFactory.getLogger(IpUtils.class);
    private static final String UNKNOWN_IP = "unknown";
    private static final int MAX_IP_LENGTH = 255;
    private static final int IPV4_MAX_LENGTH = 15;
    private static final String[] IP_HEADERS = {
        "X-Forwarded-For",
        "Proxy-Client-IP",
        "WL-Proxy-Client-IP"
    };

    /**
     * 获取客户端IP地址
     * 从请求中获取客户端真实IP地址，支持代理服务器场景
     * 
     * @param request HTTP请求对象
     * @return 返回客户端IP地址
     */
    public String getIpAddress(HttpServletRequest request) {
        String ipAddress = null;
        try {
            for (String header : IP_HEADERS) {
                ipAddress = extractIpFromHeader(request, header);
                if (!isUnknownIp(ipAddress)) {
                    break;
                }
            }
            if (isUnknownIp(ipAddress)) {
                ipAddress = request.getRemoteAddr();
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > IPV4_MAX_LENGTH) {
                ipAddress = extractFirstIp(ipAddress);
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            logger.error("Error retrieving IP address from headers: {}", e.getMessage(), e);
            ipAddress = UNKNOWN_IP;
        } catch (Exception e) {
            logger.error("Unexpected error retrieving IP address: {}", e.getMessage(), e);
            ipAddress = "";
        }

        if (ipAddress != null && ipAddress.length() > MAX_IP_LENGTH) {
            ipAddress = ipAddress.substring(0, MAX_IP_LENGTH);
        }

        return ipAddress == null ? UNKNOWN_IP : ipAddress;
    }

    /**
     * 从请求头中提取IP地址
     * 从指定的HTTP请求头中获取IP地址
     * 
     * @param request HTTP请求对象
     * @param header 请求头名称
     * @return 返回IP地址，如果不存在则返回"unknown"
     */
    private String extractIpFromHeader(HttpServletRequest request, String header) {
        String ipAddress = request.getHeader(header);
        return ipAddress == null || ipAddress.isEmpty() ? UNKNOWN_IP : ipAddress;
    }

    /**
     * 判断IP地址是否未知
     * 检查IP地址是否为空或者"unknown"
     * 
     * @param ipAddress IP地址
     * @return 如果IP未知返回true，否则返回false
     */
    private boolean isUnknownIp(String ipAddress) {
        return ipAddress == null || ipAddress.isEmpty() || UNKNOWN_IP.equalsIgnoreCase(ipAddress);
    }

    /**
     * 提取第一个IP地址
     * 从逗号分隔的IP地址列表中提取第一个（真实IP）
     * 
     * @param ipAddress 包含多个IP的字符串
     * @return 返回第一个IP地址
     */
    private String extractFirstIp(String ipAddress) {
        if (ipAddress == null) {
            return UNKNOWN_IP;
        }
        int commaIndex = ipAddress.indexOf(",");
        return commaIndex > 0 ? ipAddress.substring(0, commaIndex).trim() : ipAddress.trim();
    }
}
