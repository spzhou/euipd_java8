/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class RdmsSmsDto {

    /**
     * id
     */
    private String id;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 验证码
     */
    private String code;

    /**
     * 用途|枚举[SmsUseEnum]：REGISTER("R", "注册"), FORGET("F", "忘记密码")
     */
    private String use;

    /**
     * 生成时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date at;

    /**
     * 用途|枚举[SmsStatusEnum]：USED("U", "已使用"), NOT_USED("N", "未使用")
     */
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public Date getAt() {
        return at;
    }

    public void setAt(Date at) {
        this.at = at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", mobile=").append(mobile);
        sb.append(", code=").append(code);
        sb.append(", use=").append(use);
        sb.append(", at=").append(at);
        sb.append(", status=").append(status);
        sb.append("]");
        return sb.toString();
    }

}
