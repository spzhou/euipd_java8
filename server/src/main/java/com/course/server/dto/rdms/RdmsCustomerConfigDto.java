/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import lombok.Data;

@Data
public class RdmsCustomerConfigDto {
    private String id;

    private String customerId;

    private String ossAccessKey;

    private String ossAccessKeySecret;

    private String ossEndpoint;

    private String ossBucket;

    private String ossDomain;

    private String vodAccessKey;

    private String vodAccessKeySecret;

    private String status;

    private Integer deleted;

}
