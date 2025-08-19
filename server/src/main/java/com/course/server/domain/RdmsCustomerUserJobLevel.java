/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

import java.math.BigDecimal;

public class RdmsCustomerUserJobLevel {
    private String id;

    private String customerId;

    private String levelCode;

    private String leveName;

    private BigDecimal manHourFee;

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

    public String getLevelCode() {
        return levelCode;
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }

    public String getLeveName() {
        return leveName;
    }

    public void setLeveName(String leveName) {
        this.leveName = leveName;
    }

    public BigDecimal getManHourFee() {
        return manHourFee;
    }

    public void setManHourFee(BigDecimal manHourFee) {
        this.manHourFee = manHourFee;
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
        sb.append(", levelCode=").append(levelCode);
        sb.append(", leveName=").append(leveName);
        sb.append(", manHourFee=").append(manHourFee);
        sb.append(", deleted=").append(deleted);
        sb.append("]");
        return sb.toString();
    }
}