/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.math.BigDecimal;
import java.util.Date;

public class RdmsManhourStandard {
    private String id;

    private String customerId;

    private BigDecimal devManhourFee;

    private BigDecimal testManhourFee;

    private Date createTime;

    private String deleted;

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

    public BigDecimal getDevManhourFee() {
        return devManhourFee;
    }

    public void setDevManhourFee(BigDecimal devManhourFee) {
        this.devManhourFee = devManhourFee;
    }

    public BigDecimal getTestManhourFee() {
        return testManhourFee;
    }

    public void setTestManhourFee(BigDecimal testManhourFee) {
        this.testManhourFee = testManhourFee;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
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
        sb.append(", devManhourFee=").append(devManhourFee);
        sb.append(", testManhourFee=").append(testManhourFee);
        sb.append(", createTime=").append(createTime);
        sb.append(", deleted=").append(deleted);
        sb.append("]");
        return sb.toString();
    }
}