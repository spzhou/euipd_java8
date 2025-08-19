/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto;

public class SectionPageDto extends PageDto {

    private String courseId;

    private String chapterId;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SectionPageDto{");
        sb.append("courseId='").append(courseId).append('\'');
        sb.append(", chapterId='").append(chapterId).append('\'');
        sb.append(", page=").append(page);
        sb.append(", size=").append(size);
        sb.append(", total=").append(total);
        sb.append(", list=").append(list);
        sb.append('}');
        return sb.toString();
    }
}
