/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.enums.rdms;

import lombok.Getter;

import java.util.Objects;

@Getter
public enum RoleEnum {
    STAFF("STAFF", "员工"),
    ADMIN("ADMIN", "管理员"),
    IPMT("IPMT", "IPMT"),
    BOSS("BOSS", "BOSS"),
    PDM("PDM", "产品经理"),
    PJM("PJM", "项目经理"),
    TM("TM", "测试主管"),
    SM("SM", "系统工程师"),
    SYSGM("SYSGM", "系统工程总监"),
    PDGM("PDGM", "产品总监"),
    QCGM("QCGM", "质量总监"),
    FGGM("FGGM", "法规总监"),
    TGM("TGM", "测试总监");

    private String role;
    private String name;

    RoleEnum(String role, String name) {
        this.role = role;
        this.name = name;
    }

    public static RoleEnum getRoleEnumByRole(String role) {
        for (RoleEnum roleEnum : RoleEnum.values()) {
            if (Objects.equals(roleEnum.getRole(), role)) {
                return roleEnum;
            }
        }
        return null;
    }

    public static RoleEnum getRoleEnumByName(String name) {
        for (RoleEnum roleEnum : RoleEnum.values()) {
            if (roleEnum.getName().equals(name)) {
                return roleEnum;
            }
        }
        return null;
    }
}
