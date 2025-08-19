/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import com.course.server.domain.RdmsDepartment;
import lombok.Data;

import java.util.List;

@Data
public class RdmsDepartmentDto extends RdmsDepartment {
    private String customerName;
    private String managerName;
    private String assistantName;
    private String secretaryName;
    private String name;  //数据库中存储的departName
    private List<String> memberIdList;
}
