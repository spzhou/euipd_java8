/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.util.ftp;

import org.apache.commons.net.ftp.FTPClient;

/**
 * 获取 ftp 客户端对象的接口
 */
public interface FTPPoolService {
    /**
     * 获取ftpClient
     * @return
     */
    FTPClient borrowObject();

    /**
     * 归还ftpClient
     * @param ftpClient
     * @return
     */
    void returnObject(FTPClient ftpClient);

    /**
     * 获取 ftp 配置信息
     * @return
     */
    FTPConfig getFtpPoolConfig();
}
