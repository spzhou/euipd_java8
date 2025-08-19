/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.generator.util;

public class Field {
    private String name; // 字段名：course_id
    private String nameHump; // 字段名小驼峰：courseId
    private String nameBigHump; // 字段名大驼峰：CourseId
    private String nameCn; // 中文名：课程
    private String type; // 字段类型：char(8)
    private String javaType; // java类型：String
    private String comment; // 注释：课程|ID
    private Boolean nullAble; // 是否可为空
    private Integer length; // 字符串长度
    private Boolean enums; // 是否是枚举
    private String enumsConst; // 枚举常量 COURSE_LEVEL

    /**
     * 获取字段名
     * 
     * @return 返回字段名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置字段名
     * 
     * @param name 要设置的字段名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取字段名小驼峰形式
     * 
     * @return 返回小驼峰形式的字段名
     */
    public String getNameHump() {
        return nameHump;
    }

    /**
     * 设置字段名小驼峰形式
     * 
     * @param nameHump 要设置的小驼峰形式字段名
     */
    public void setNameHump(String nameHump) {
        this.nameHump = nameHump;
    }

    /**
     * 获取字段名大驼峰形式
     * 
     * @return 返回大驼峰形式的字段名
     */
    public String getNameBigHump() {
        return nameBigHump;
    }

    /**
     * 设置字段名大驼峰形式
     * 
     * @param nameBigHump 要设置的大驼峰形式字段名
     */
    public void setNameBigHump(String nameBigHump) {
        this.nameBigHump = nameBigHump;
    }

    /**
     * 获取字段中文名
     * 
     * @return 返回字段中文名
     */
    public String getNameCn() {
        return nameCn;
    }

    /**
     * 设置字段中文名
     * 
     * @param nameCn 要设置的字段中文名
     */
    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    /**
     * 获取字段类型
     * 
     * @return 返回字段类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置字段类型
     * 
     * @param type 要设置的字段类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取字段注释
     * 
     * @return 返回字段注释
     */
    public String getComment() {
        return comment;
    }

    /**
     * 设置字段注释
     * 
     * @param comment 要设置的字段注释
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * 获取Java类型
     * 
     * @return 返回Java类型
     */
    public String getJavaType() {
        return javaType;
    }

    /**
     * 设置Java类型
     * 
     * @param javaType 要设置的Java类型
     */
    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    /**
     * 获取是否可为空
     * 
     * @return 返回是否可为空
     */
    public Boolean getNullAble() {
        return nullAble;
    }

    /**
     * 设置是否可为空
     * 
     * @param nullAble 要设置的是否可为空
     */
    public void setNullAble(Boolean nullAble) {
        this.nullAble = nullAble;
    }

    /**
     * 获取字符串长度
     * 
     * @return 返回字符串长度
     */
    public Integer getLength() {
        return length;
    }

    /**
     * 设置字符串长度
     * 
     * @param length 要设置的字符串长度
     */
    public void setLength(Integer length) {
        this.length = length;
    }

    /**
     * 获取是否是枚举
     * 
     * @return 返回是否是枚举
     */
    public Boolean getEnums() {
        return enums;
    }

    /**
     * 设置是否是枚举
     * 
     * @param enums 要设置的是否是枚举
     */
    public void setEnums(Boolean enums) {
        this.enums = enums;
    }

    /**
     * 获取枚举常量
     * 
     * @return 返回枚举常量
     */
    public String getEnumsConst() {
        return enumsConst;
    }

    /**
     * 设置枚举常量
     * 
     * @param enumsConst 要设置的枚举常量
     */
    public void setEnumsConst(String enumsConst) {
        this.enumsConst = enumsConst;
    }

    /**
     * 重写toString方法
     * 返回对象的字符串表示，包含所有字段信息
     * 
     * @return 返回对象的字符串表示
     */
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Field{");
        sb.append("name='").append(name).append('\'');
        sb.append(", nameHump='").append(nameHump).append('\'');
        sb.append(", nameBigHump='").append(nameBigHump).append('\'');
        sb.append(", nameCn='").append(nameCn).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", javaType='").append(javaType).append('\'');
        sb.append(", comment='").append(comment).append('\'');
        sb.append(", nullAble=").append(nullAble);
        sb.append(", length=").append(length);
        sb.append(", enums=").append(enums);
        sb.append(", enumsConst='").append(enumsConst).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
