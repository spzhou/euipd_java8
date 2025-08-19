/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import com.course.server.domain.RdmsFileApply;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RdmsFileApplyDto extends RdmsFileApply {
    private String fileName;

    private String operatorName;  //文件提供者姓名
    private String proposerName;  //申请人姓名
    private String approverName; //审批人姓名
    private String projectName;  //所属项目名称
    private String useClass;
    private Date fileCreateTime;

    private List<String> fileIdList;

}
