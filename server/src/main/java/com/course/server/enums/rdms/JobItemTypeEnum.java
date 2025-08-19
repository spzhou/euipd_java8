/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.enums.rdms;

import java.util.Objects;

public enum JobItemTypeEnum {
    //工单类型
    NOTSET("NOTSET", "未设定"),
    DEMAND("DEMAND", "需求任务"),
    DECOMPOSE("DECOMPOSE", "分解任务"),
    ITERATION("ITERATION", "修订任务"),
    MERGE("MERGE", "合并任务"),
    JOIN_TO("JOIN_TO", "归并任务"),
    CHA_RECHECK("CHA_RECHECK", "复核任务"),
    DEVELOP("DEVELOP", "开发任务"),
    QUALITY("QUALITY", "质量任务"),  //√
    ASSIST("ASSIST", "协作任务"),
    BUG("BUG", "BUG任务"),
    TEST("TEST","测试任务"),
    CHARACTER_INT("CHARACTER_INT","集成任务"),  //√
    REVIEW("REVIEW","功能评审"),  //项目经理提交的工单
    REVIEW_PRO("REVIEW_PRO","项目评审"),  //项目评审
    REVIEW_SUBP("REVIEW_SUBP","项目评审"),  //项目评审
    REVIEW_MILESTONE_PRE("REVIEW_MILESTONE_PRE","发起里程碑评审"),  //发起里程碑评审
    REVIEW_MILESTONE("REVIEW_MILESTONE","里程碑评审"),  //里程碑评审
    REVIEW_COO("REVIEW_COO","联合评审"),  //评审委员审批的工单
    REVIEW_NOTIFY("REVIEW_NOTIFY","评审通知"),  //通知评审结果
    MILESTONE_NOTIFY("MILESTONE_NOTIFY","里程碑通知"),  //里程碑通知
    REFUSE_NOTIFY("REFUSE_NOTIFY", "拒收通知"),
    BUDGET_ADJUST_NOTIFY("BUDGET_ADJUST_NOTIFY", "预算审批通知"),
    TASK("TASK","功能任务"),  //研发任务成本 在功能预算中
    TASK_SUBP("TASK_SUBP","项目任务"),  //子项目任务成本 在子项目预算中,在子项目经理的预算盘子里
    TASK_MILESTONE("TASK_MILESTONE","里程碑任务"),  //子项目任务成本 在子项目预算中,在子项目经理的预算盘子里
    TASK_PRO("TASK_PRO","项目任务"),  //项目任务成本 子项目预算中, 在项目经理的预算盘子里
    TASK_PRODUCT("TASK_PRODUCT","产品任务"),
    TASK_FUNCTION("TASK_FUNCTION","系统工程任务"),
    TASK_TEST("TASK_TEST","测试任务"),
    TASK_BUG("TASK_BUG","缺陷处理"),
    SUBPROJECT_INT("SUBPROJECT_INT","集成任务"),
    PROJECT_INT("PROJECT_INT","项目集成"),
    SUGGEST("SUGGEST","增补功能建议"),
    SUGGEST_UPDATE("SUGGEST_UPDATE","功能升级建议"),
    FUNCTION("FUNCTION","系统工程"),
    CBB_DEFINE("CBB_DEFINE","CBB定义"),
    UPDATE("UPDATE","功能迭代"); //√

    private String type;
    private String typeName;

    JobItemTypeEnum(String type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    public static JobItemTypeEnum getJobItemTypeEnumByType(String type) {
        for (JobItemTypeEnum statusEnum : JobItemTypeEnum.values()) {
            if (Objects.equals(statusEnum.getType(), type)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static JobItemTypeEnum getJobItemTypeEnumByName(String typeName) {
        for (JobItemTypeEnum statusEnum : JobItemTypeEnum.values()) {
            if (statusEnum.getTypeName().equals(typeName)) {
                return statusEnum;
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
