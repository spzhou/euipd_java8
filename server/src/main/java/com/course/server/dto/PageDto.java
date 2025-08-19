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
public class PageDto<T> {

    /**
     * 当前页码
     */
    protected int page;

    /**
     * 每页条数
     */
    protected int size;

    /**
     * 总条数
     */
    protected long total;

    /**
     * 返回体
     */
    protected List<T> list;

    /**
     * 附加参数:人员角色
     */
    protected String actor;

    /**
     * 附加参数:用于传递附加关键词参数
     */
    protected String keyWord;

    /**
     * 附加参数:用于传递附加状态参数
     */
    protected String status;

    /**
     * 附加参数:用于传递附加类型参数
     */
    protected String type;

    protected String mixType;

    protected String subprojectId;

    protected String customerId;

    protected String loginUserId;

    protected String flag;  //用于传递标记信息

}
