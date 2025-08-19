
/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto;
import lombok.Data;

import java.util.List;

@Data
public class MyOrderListOutDto {
    private Integer totalCount;
    private Integer pageSize;
    private Integer totalPage;
    private Integer currPage;
    private Integer orderNum;
    private List<VueOrderDetail> vueOrderDetailList;
}
