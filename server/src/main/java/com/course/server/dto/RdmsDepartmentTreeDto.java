/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto;

import lombok.Data;
import com.course.server.dto.rdms.RdmsCustomerUserDto;
import java.util.List;

@Data
public class RdmsDepartmentTreeDto {
    private String id;
    private String parent;
    private String label;
    private String name;
    private String managerId;
    private String description;
    private int fixed;
    private List<RdmsDepartmentTreeDto> children;
    private List<String> memberIdList;
    private RdmsCustomerUserDto managerInfo;

}
