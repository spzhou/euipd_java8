/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import lombok.Data;

import java.util.Date;

@Data
public class RdmsHmiIdsDto {
    //获得的数据
    private String customerId;
    private String projectId;
    private String preProjectId;
    private String subprojectId;
    private String jobitemId;
    private String characterId;
    private String demandId;
    private String executorId;
    private String creatorId;
    private String sysgmId;
    private String pdgmId;
    private String parent;
    private String supervise;
    private String productManagerId;
    private String projectManagerId;
    private String parentJobitemId;
    private String testedJobitemId;
    private String propertyId;

    private String rdType;
    private Date planCompleteTime;

}
