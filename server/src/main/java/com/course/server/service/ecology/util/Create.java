/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.ecology.util;

import lombok.Data;

@Data
public class Create {

    /**
     * 必填 主表数据 org.dromara.mes.common.domain.flow.MainData
     */
    private String mainData;

    /**
     * 必填 流程标题
     */
    private String requestName;

    /**
     * 必填 流程Id
     */
    private Integer workflowId;

    /**
     * 非必填 明细表数据 org.dromara.mes.common.domain.flow.DetailData
     */
    private String detailData;

    /**
     * 非必填 其他参数 org.dromara.mes.common.domain.flow.OtherParams
     */
//    private JSONObject otherParams;

    /**
     * 非必填 签字意见，默认值流程默认意见若未设置则为空
     */
//    private String remark;

    /**
     * 非必填 紧急程度
     */
//    private String requestLevel;

}
