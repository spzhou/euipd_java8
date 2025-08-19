/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;


public class RdmsCourseContentFileDto {

    /**
     * ID
     */
    private String id;

    /**
     * 课程ID
     */
    private String courseId;

    /**
     * 地址
     */
    private String url;

    /**
     * 文件名
     */
    private String name;

    /**
     * 大小|字节B
     */

    private String imgClass;

    private Integer orderNum;


    private Integer size;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getImgClass() {
        return imgClass;
    }

    public void setImgClass(String imgClass) {
        this.imgClass = imgClass;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }


    @Override
    public String toString() {
        return "CourseContentFileDto{" +
                "id='" + id + '\'' +
                ", courseId='" + courseId + '\'' +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", imgClass='" + imgClass + '\'' +
                ", orderNum=" + orderNum +
                ", size=" + size +
                '}';
    }
}
