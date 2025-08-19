/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

public class RdmsCustomerConfig {
    private String id;

    private String customerId;

    private String ossAccessKey;

    private String ossAccessKeySecret;

    private String ossEndpoint;

    private String ossEndpointSts;

    private String ossBucket;

    private String ossBucketWeb;

    private String ossDomain;

    private String ossDomainWeb;

    private String ossRoleArn;

    private String vodAccessKey;

    private String vodAccessKeySecret;

    private String status;

    private Integer deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOssAccessKey() {
        return ossAccessKey;
    }

    public void setOssAccessKey(String ossAccessKey) {
        this.ossAccessKey = ossAccessKey;
    }

    public String getOssAccessKeySecret() {
        return ossAccessKeySecret;
    }

    public void setOssAccessKeySecret(String ossAccessKeySecret) {
        this.ossAccessKeySecret = ossAccessKeySecret;
    }

    public String getOssEndpoint() {
        return ossEndpoint;
    }

    public void setOssEndpoint(String ossEndpoint) {
        this.ossEndpoint = ossEndpoint;
    }

    public String getOssEndpointSts() {
        return ossEndpointSts;
    }

    public void setOssEndpointSts(String ossEndpointSts) {
        this.ossEndpointSts = ossEndpointSts;
    }

    public String getOssBucket() {
        return ossBucket;
    }

    public void setOssBucket(String ossBucket) {
        this.ossBucket = ossBucket;
    }

    public String getOssBucketWeb() {
        return ossBucketWeb;
    }

    public void setOssBucketWeb(String ossBucketWeb) {
        this.ossBucketWeb = ossBucketWeb;
    }

    public String getOssDomain() {
        return ossDomain;
    }

    public void setOssDomain(String ossDomain) {
        this.ossDomain = ossDomain;
    }

    public String getOssDomainWeb() {
        return ossDomainWeb;
    }

    public void setOssDomainWeb(String ossDomainWeb) {
        this.ossDomainWeb = ossDomainWeb;
    }

    public String getOssRoleArn() {
        return ossRoleArn;
    }

    public void setOssRoleArn(String ossRoleArn) {
        this.ossRoleArn = ossRoleArn;
    }

    public String getVodAccessKey() {
        return vodAccessKey;
    }

    public void setVodAccessKey(String vodAccessKey) {
        this.vodAccessKey = vodAccessKey;
    }

    public String getVodAccessKeySecret() {
        return vodAccessKeySecret;
    }

    public void setVodAccessKeySecret(String vodAccessKeySecret) {
        this.vodAccessKeySecret = vodAccessKeySecret;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", customerId=").append(customerId);
        sb.append(", ossAccessKey=").append(ossAccessKey);
        sb.append(", ossAccessKeySecret=").append(ossAccessKeySecret);
        sb.append(", ossEndpoint=").append(ossEndpoint);
        sb.append(", ossEndpointSts=").append(ossEndpointSts);
        sb.append(", ossBucket=").append(ossBucket);
        sb.append(", ossBucketWeb=").append(ossBucketWeb);
        sb.append(", ossDomain=").append(ossDomain);
        sb.append(", ossDomainWeb=").append(ossDomainWeb);
        sb.append(", ossRoleArn=").append(ossRoleArn);
        sb.append(", vodAccessKey=").append(vodAccessKey);
        sb.append(", vodAccessKeySecret=").append(vodAccessKeySecret);
        sb.append(", status=").append(status);
        sb.append(", deleted=").append(deleted);
        sb.append("]");
        return sb.toString();
    }
}
