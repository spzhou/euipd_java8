/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import com.course.server.domain.RdmsPerformance;
import lombok.Data;

import java.util.List;

@Data
public class RdmsPerformanceDto extends RdmsPerformance {
    private String jobNum;
    private String jobPosition;
    private String title;
    private String jobLevel;
    private String officeTel;
    private String customerUserName;
    private List<String> jobitemIdList;
    private String customerId;

}
