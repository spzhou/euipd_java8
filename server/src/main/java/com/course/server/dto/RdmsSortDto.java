/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto;

public class RdmsSortDto {
    /**
     * ID
     */
    private String id;

    /**
     * 当前排序
     */
    private int oldSort;

    /**
     * 新排序
     */
    private int newSort;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNewSort() {
        return newSort;
    }

    public void setNewSort(int newSort) {
        this.newSort = newSort;
    }

    public int getOldSort() {
        return oldSort;
    }

    public void setOldSort(int oldSort) {
        this.oldSort = oldSort;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SortDto{");
        sb.append("id='").append(id).append('\'');
        sb.append(", oldSort=").append(oldSort);
        sb.append(", newSort=").append(newSort);
        sb.append('}');
        return sb.toString();
    }
}
