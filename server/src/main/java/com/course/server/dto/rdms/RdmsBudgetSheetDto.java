/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RdmsBudgetSheetDto {
    private BigDecimal equipment;
    private BigDecimal test;
    private BigDecimal material;
    private BigDecimal power;
    private BigDecimal conference;
    private BigDecimal business;
    private BigDecimal cooperation;
    private BigDecimal property;
    private BigDecimal labor;
    private BigDecimal staff;
    private BigDecimal consulting;
    private BigDecimal management;
    private BigDecimal other;

    public RdmsBudgetSheetDto(){
        this.equipment = BigDecimal.ZERO;
        this.test = BigDecimal.ZERO;
        this.material = BigDecimal.ZERO;
        this.power = BigDecimal.ZERO;
        this.conference = BigDecimal.ZERO;
        this.business = BigDecimal.ZERO;
        this.cooperation = BigDecimal.ZERO;
        this.property = BigDecimal.ZERO;
        this.labor = BigDecimal.ZERO;
        this.staff = BigDecimal.ZERO;
        this.consulting = BigDecimal.ZERO;
        this.management = BigDecimal.ZERO;
        this.other = BigDecimal.ZERO;
    }
}
