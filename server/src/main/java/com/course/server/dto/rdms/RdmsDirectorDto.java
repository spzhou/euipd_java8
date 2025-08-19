/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import lombok.Data;

@Data
public class RdmsDirectorDto {
    private String customerId;

    private String pdgmId;
    private String pdgmName;

    private String sysgmId;
    private String sysgmName;

    private String fggmId;
    private String fggmName;

    private String qcgmId;
    private String qcgmName;

    private String tgmId;
    private String tgmName;


}
