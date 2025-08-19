/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.domain;

public class Test {
    private String id;

    private String name;

    /**
     * 获取ID
     * 
     * @return 返回ID值
     */
    public String getId() {
        return id;
    }

    /**
     * 设置ID
     * 
     * @param id 要设置的ID值
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取名称
     * 
     * @return 返回名称值
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     * 
     * @param name 要设置的名称值
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 重写toString方法
     * 返回对象的字符串表示，包含所有字段信息
     * 
     * @return 返回对象的字符串表示
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append("]");
        return sb.toString();
    }
}