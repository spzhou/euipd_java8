/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.dto;


import com.course.server.domain.RdmsCustomer;
import com.course.server.domain.RdmsCustomerUser;
import com.course.server.dto.rdms.RdmsCustomerDto;
import com.course.server.dto.rdms.RdmsCustomerUserDto;
import com.course.server.enums.rdms.LoginUserGroupEnum;
import lombok.Data;

import java.util.HashSet;
import java.util.List;

@Data
public class LoginUserDto implements java.io.Serializable {

    /**
     * id
     */
    private String id;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 昵称
     */
    private String name;
    private String trueName;
    private String profile;

    /**
     * 登录凭证
     */
    private String token;

    /**
     * 所有资源，用于前端界面控制
     */
    private List<ResourceDto> resources;

    /**
     * 如果是企业用户登录,则加载Admin用户信息
     */
    private RdmsCustomerUserDto adminUser;

    /**
     * 如果是开发者用户登录,则加载所在机构信息信息
     */
    private RdmsCustomer customer;
    /**
     * 如果是开发者用户登录,获取其所在机构的customerUserId
     */
    private String customerUserId;

    /**
     * 登录用户组别 LoginUserGroupEnum 类型
     */
    private String userGroup;

    /**
     * 机构联系人电话号码
     */
    private String contactPhone;

    /**
     * 所有资源中的请求，用于后端接口拦截
     */
    private HashSet<String> requests;

    //数据库中记录的登录状态
    private String loginToken;  //数据库记录的登录token
    private String loginIp;

    private String unionid;

    private List<String> roleList;  //用户的角色  产品经理  项目经理 IPMT等
}
