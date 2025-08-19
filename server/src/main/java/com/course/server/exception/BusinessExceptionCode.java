/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.exception;

public enum BusinessExceptionCode {

    SYSGM_NOT_SET("系统工程总监未设置"),
    MEMBER_NOT_EXIST("会员不存在"),
    USER_LOGIN_NAME_EXIST("用户已存在"),
    CUSTOMER_LOGIN_NAME_EXIST("客户已存在"),
    DEPARTMENT_NAME_EXIST("部门已存在"),
    CONTACT_PHONE_EXIST("一部手机只能注册一个管理员账号"),
    CUSTOMER_NOT_EXIST("客户不存在,请添加客户后操作"),
    CUSTOMER_REGISTER_RECHECK("注册信息审核中,您会很快收到短信通知, 请耐心等候!"),
    CUSTOMER_FROZEN("账号被冻结,请联系运营方处理"),
    CUSTOMER_STOP("机构已停业,数据已删除"),
    CUSTOMER_CONTACT_PHONE_EXIST("电话号码已存在"),
    LOGIN_USER_ERROR("用户名不存在或密码错误"),
    SYSTEM_NOT_AUTHORIZED("系统没有获得厂商授权"),
    USER_NOT_EXIST_ERROR("用户不存在"),
    OA_INTERFACE_ERROR("OA接口错误,请联系OA管理员"),
    OVERDUE_ERROR("账号过期,请联系供应商"),
    SELECTED_CUSTOMER_OVERDUE_ERROR("登录机构账号过期"),
    LOGIN_MEMBER_ERROR("手机号不存在或密码错误"),
    LOGIN_PHONE_NOT_INPUT("手机号未提供"),
    MOBILE_CODE_TOO_FREQUENT("短信请求过于频繁"),
    MOBILE_CODE_ERROR("短信验证码不正确"),
    MOBILE_CODE_EXPIRED("短信验证码不存在或已过期，请重新发送短信"),
    INPUT_INFORMATION_ERROR("输入信息错误"),
    DB_CONNECT_ERROR("数据库连接错误"),
    DB_RECORD_EXIST("数据库记录已存在"),
    DB_RECORD_NOT_EXIST("数据库记录不存在"),
    DB_RECORD_NOT_EXIST_OR_ERROR("数据库记录不存在或者错误"),
    DB_RECORD_ERROR("数据库记录错误"),
    DATA_RECORD_ERROR("数据记录错误"),
    PROJECT_CODE_EXIST_ERROR("项目代码重复"),
    USER_NUM_LIMIT_ERROR("用户数量超过限制数量"),
    FILE_SPACE_LIMIT_ERROR("文件存储空间不足"),
    DB_RECORD_RESOURCE_NOT_EXIST("权限资源不存在"),
    RELEASE_TIME_CANNOT_BEFORE_OTHER_MILESTONE("发布里程碑时间不能早于其他里程碑"),
    NORMAL_MILESTONE_TIME_CANNOT_AFTER_RELEASE("普通里程碑时间不能晚于发布里程碑"),
    HAVE_NOT_RELEASE_MILESTONE("没有设定发布里程碑"),
    INPUT_ERROR("输入错误"),
    REFRESH_PAGE("请刷新页面后重试"),
    ;

    private String desc;

    BusinessExceptionCode(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
