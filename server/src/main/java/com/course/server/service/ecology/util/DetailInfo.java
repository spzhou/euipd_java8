/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.ecology.util;

import lombok.Data;

import java.util.List;

@Data
public class DetailInfo<B> {
    // 明细表名称
    String detailTableName;
    // 明细数据
    List<B> detailDatas;
}
