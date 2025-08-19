/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import com.course.server.domain.RdmsBomConfig;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RdmsBomConfigDto extends RdmsBomConfig {
    private String projectName;
    private List<String> characterIdList;
    private List<RdmsBomDto> materialList;

}
