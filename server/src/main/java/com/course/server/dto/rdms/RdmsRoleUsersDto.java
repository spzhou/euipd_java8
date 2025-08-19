/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto.rdms;

import lombok.Data;

import java.util.List;

@Data
public class RdmsRoleUsersDto {
    private String bossId;
    private String superId;  //超级用户
    private String ipmtId;
    private String pdmId;
    private String pjmId;
    private String mainPjmId;  //主项目经理
    private String receiverId;  //下一步接收人
    private String loginUserId;
    private String executorId;
    private String tmId;  //测试主管
    private String smId;  //系统工程师
    private String directLeader;  //直属领导
    private String sysgmId;
    private String pdgmId;
    private String tgmId;
    private List<String> reviewWorkerIds;  //测试主管
}
