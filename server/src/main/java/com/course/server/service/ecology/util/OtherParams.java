/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.ecology.util;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.Data;


@Data
public class OtherParams {

    /**
     * (api未描述)
     */
    private String messageType;

    /**
     * 新建流程是否默认提交到第二节点，可选值为[0 ：不流转 1：流转 (默认)]
     */
    private String isnextflow;

    /**
     * "流程密级， 开启密级后生效， 默认公开"
     */
    private String requestSecLevel;

    /**
     * ”保密期限，流程密级为秘密或机密时，通过改参数上传保密期限，不上传时取分级保护出的保密期限“
     */
    private String requestSecValidity;


    /**
     * 新建流程失败是否默认删除流程，可选值为[0 ：不删除 1：删除 (默认)]
     */
    private String delReqFlowFaild;

    /**
     * "是否验证用户创建流程权限，可选值为[0 ：不验证 1：验证 (默认)]"
     */
    private String isVerifyPer;

}
