/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import com.course.server.domain.RdmsFile;
import lombok.Data;

import java.util.Date;

@Data
public class RdmsFileDto extends RdmsFile {
    private String operatorName;
    private String absPath;
    private String projectName;

    private String applyStatus;

    private Boolean authStatus; //当为true是,用户可以下载
}
