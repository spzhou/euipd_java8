/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.enums.rdms;

import java.util.Objects;

public enum ProjectStageEnum {
    PRE_PROJECT("PRE_PROJECT", "预立项阶段"),
    PROJECT("PROJECT", "产品开发阶段"),
    PRODUCT("PRODUCT", "PLM阶段");

    private String stage;
    private String stageName;

    ProjectStageEnum(String stage, String stageName) {
        this.stage = stage;
        this.stageName = stageName;
    }

    public static ProjectStageEnum getProjectStageEnumByStage(String stage) {
        for (ProjectStageEnum stageEnum : ProjectStageEnum.values()) {
            if (Objects.equals(stageEnum.getStage(), stage)) {
                return stageEnum;
            }
        }
        return null;
    }

    public static ProjectStageEnum getProjectStageEnumByName(String stageName) {
        for (ProjectStageEnum stageEnum : ProjectStageEnum.values()) {
            if (stageEnum.getStageName().equals(stageName)) {
                return stageEnum;
            }
        }
        return null;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

}
