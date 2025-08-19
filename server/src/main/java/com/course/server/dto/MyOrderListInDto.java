
/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto;
import lombok.Data;

@Data
public class MyOrderListInDto {
    private String memberId;
    private Integer pageSize; //每一页的条目数量
    private Integer currentPageNo;  //当前查询的页码
}
