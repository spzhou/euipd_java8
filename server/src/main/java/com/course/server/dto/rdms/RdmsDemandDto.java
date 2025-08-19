/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RdmsDemandDto {
    private String id;
    private String demandName;

    private String customerId;
    private String customerName;

    private String preProjectId;
    private String preProjectName;

    private String productManagerId;
    private String productManagerName;

    private String demandCustomerName;

    private String confirmPersonName;

    private String confirmContactTel;

    private String demandDescription;

    private String workCondition;

    private String fileListStr;
    private List<String> fileList;
    private List<RdmsFileDto> resFiles;

    private String writerId;
    private String writerName;

    private String nextNode;

    private String status;

    private Date createTime;
    private String createTimeStr;

    private Date submitTime;
    private String submitTimeStr;

    private Integer deleted;

    private String jobitemId;
}
