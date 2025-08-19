/**
 * Author：周朔鹏
 * 编制时间：2025-08-09

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.*;
import com.course.server.dto.LoginUserDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResourceDto;
import com.course.server.dto.RdmsRoleDto;
import com.course.server.dto.rdms.*;
import com.course.server.enums.rdms.BudgetModeEnum;
import com.course.server.enums.rdms.CustomerStatusEnum;
import com.course.server.enums.rdms.FixedDepartmentEnum;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.*;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.course.server.constants.Constants.*;
import static com.course.server.enums.rdms.CustomerStatusEnum.getCustomerEnumByStatus;

@Service
public class RdmsCustomerService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsCustomerService.class);

    @Resource
    private RdmsCustomerMapper customerMapper;
    @Resource
    private RdmsCustomerUserJobLevelService rdmsJobLevelService;
    @Resource
    private RdmsUserService rdmsUserService;
    @Resource
    private RdmsDepartmentService rdmsDepartmentService;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private RdmsCustomerRoleCustomerMapper rdmsCustomerRoleCustomerMapper;
    @Resource
    private RdmsCustomerRoleResourceMapper rdmsCustomerRoleResourceMapper;
    @Resource
    private RdmsCustomerResourceMapper rdmsCustomerResourceMapper;
    @Resource
    private RdmsUserRoleService rdmsUserRoleService;
    @Resource
    private RdmsUserRoleResourceMapper rdmsUserRoleResourceMapper;
    @Resource
    private RdmsManhourStandardMapper rdmsManhourStandardMapper;
    @Resource
    private RdmsProductManagerService rdmsProductManagerService;
    @Resource
    private RdmsProjectManagerService rdmsProjectManagerService;
    @Resource
    private RdmsIpmtService rdmsIpmtService;
    @Resource
    private RdmsSmsInfoService rdmsSmsInfoService;
    @Resource
    private RdmsCategoryService rdmsCategoryService;
    @Resource
    private RdmsBossService rdmsBossService;
    @Autowired
    private RdmsPdgmService rdmsPdgmService;
    @Autowired
    private RdmsSysgmService rdmsSysgmService;
    @Autowired
    private RdmsTgmService rdmsTgmService;
    @Autowired
    private RdmsFggmService rdmsFggmService;
    @Autowired
    private RdmsQcgmService rdmsQcgmService;

    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsCustomerExample customerExample = new RdmsCustomerExample();
        List<RdmsCustomer> customerList = customerMapper.selectByExample(customerExample);
        //去掉有删除标记的记录
        List<RdmsCustomer> customerListTemp = new ArrayList<>();
        for(RdmsCustomer rdmsCustomer : customerList){
            if(rdmsCustomer.getDeleted() != 1){
                customerListTemp.add(rdmsCustomer);
            }
        }

        PageInfo<RdmsCustomer> pageInfo = new PageInfo<>(customerList);
        pageDto.setTotal(pageInfo.getTotal());
        List<RdmsCustomerDto> customerDtoList = CopyUtil.copyList(customerListTemp, RdmsCustomerDto.class);
        //对日期时间进行格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(RdmsCustomerDto customerDto : customerDtoList){
            customerDto.setCreateTimeStr(sdf.format(customerDto.getCreateTime()));
            //将status的key转换为Name
            CustomerStatusEnum customerEnumByStatus = getCustomerEnumByStatus(customerDto.getStatus());
            customerDto.setStatusName(customerEnumByStatus.getStatusName());
        }

        pageDto.setList(customerDtoList);
    }

    /**
     * 根据keyWord查询客户列表
     * @param pageDto
     */
    public void customerList(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsCustomerExample customerExample = new RdmsCustomerExample();
        customerExample.setOrderByClause("create_time desc");
        RdmsCustomerExample.Criteria criteria = customerExample.createCriteria()
                .andDeletedEqualTo(0);
        if(! ObjectUtils.isEmpty(pageDto.getKeyWord())){
            criteria.andCustomerNameLike('%'+ pageDto.getKeyWord() +'%');
        }
        List<RdmsCustomer> customerList = customerMapper.selectByExample(customerExample);

//        List<RdmsCustomer> customerList = myCustomerMapper.findCustomers(pageDto.getKeyWord());
        PageInfo<RdmsCustomer> pageInfo = new PageInfo<>(customerList);
        pageDto.setTotal(pageInfo.getTotal());
        List<RdmsCustomerDto> customerDtoList = CopyUtil.copyList(customerList, RdmsCustomerDto.class);
        //对日期时间进行格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(RdmsCustomerDto customerDto : customerDtoList){
            customerDto.setCreateTimeStr(sdf.format(customerDto.getCreateTime()));
            CustomerStatusEnum customerEnumByStatus = getCustomerEnumByStatus(customerDto.getStatus());
            customerDto.setStatusName(customerEnumByStatus.getStatusName());

            RdmsBossDto bossByCustomerId = rdmsBossService.getBossByCustomerId(customerDto.getId());
            if(ObjectUtils.isEmpty(bossByCustomerId)){
                //将super设置为boss
                RdmsCustomerUser superviseUserInfo = rdmsCustomerUserService.getAdminUserInfo(customerDto.getId());
                RdmsBossDto bossDto = new RdmsBossDto();
                bossDto.setBossId(superviseUserInfo.getId());
                bossDto.setCustomerId(customerDto.getId());
                bossDto.setId(UuidUtil.getShortUuid());
                rdmsBossService.save(bossDto);

                customerDto.setBossId(bossDto.getBossId());
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(bossDto.getBossId());
                customerDto.setBossName(rdmsCustomerUser.getTrueName());
            }else{
                customerDto.setBossId(bossByCustomerId.getBossId());
                RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(bossByCustomerId.getBossId());
                if(!ObjectUtils.isEmpty(rdmsCustomerUser)){
                    customerDto.setBossName(rdmsCustomerUser.getTrueName());
                }
            }
        }

        pageDto.setList(customerDtoList);
    }

    public List<RdmsCustomerDto> getOutSourcingCustomerList(String customerId) {
        RdmsCustomerExample customerExample = new RdmsCustomerExample();
        customerExample.createCriteria()
                .andDeletedEqualTo(0)
                .andIdNotEqualTo(customerId)
                .andStatusEqualTo(CustomerStatusEnum.CUSTOMER_NORMAL.getStatus());
        List<RdmsCustomer> customerList = customerMapper.selectByExample(customerExample);
        if(!CollectionUtils.isEmpty(customerList)){
            List<RdmsCustomer> collect = customerList.stream().filter(item -> ObjectUtils.isEmpty(item.getExpirationTime()) || new Date().getTime() < item.getExpirationTime().getTime()).collect(Collectors.toList());
            return CopyUtil.copyList(collect, RdmsCustomerDto.class);
        }else{
            return null;
        }
    }

    /**
     * 当新创建系统时, 创建平台超级管理员
     */
    private void initSystemSuperAdmin() {
        //创建默认机构
        RdmsCustomer customer = new RdmsCustomer();
        customer.setId(SUPER_CUSTOMER_ID);
        customer.setPassword(UuidUtil.getShortUuid());  //随机生成一个密码
        customer.setCustomerName("根用户");
        customer.setAdminName("根用户");
        customer.setContactPhone(SUPER_ADMIN_LOGIN_NAME);
        customer.setLoginName(SUPER_ADMIN_LOGIN_NAME);
        customer.setStatus(CustomerStatusEnum.CUSTOMER_NORMAL.getStatus());
        this.insert(customer);
        //创建admin账号
        RdmsUserDto userDto = new RdmsUserDto();
        userDto.setDeleted(0);
        userDto.setLoginName(customer.getContactPhone());
        userDto.setTrueName(customer.getAdminName());
        userDto.setProfile("https://my-course-img.oss-cn-shanghai.aliyuncs.com/user/defaultProfile.jpeg");
        rdmsUserService.save(userDto);
        //创建代表公司的顶级部门
        RdmsDepartmentDto departmentDto = new RdmsDepartmentDto();
        departmentDto.setDepartName(customer.getLoginName());
        departmentDto.setCustomerId(customer.getId());
        RdmsUser rdmsUser = rdmsUserService.selectByLoginName(customer.getContactPhone());
        departmentDto.setManagerId(rdmsUser.getId());
        departmentDto.setPhone(rdmsUser.getLoginName());
        departmentDto.setDepartDescription("代表公司的顶级部门");
        if(departmentDto.getId() == null){
            departmentDto.setId(UuidUtil.getShortUuid());
        }
        rdmsDepartmentService.save(departmentDto);
        //在customer_user表中增加关系记录
        RdmsCustomerUserDto customerUserDto = new RdmsCustomerUserDto();
        customerUserDto.setCustomerId(customer.getId());
        customerUserDto.setUserId(rdmsUser.getId());
        customerUserDto.setJobNum("J0000000");
        customerUserDto.setDepartmentId(departmentDto.getId());
        customerUserDto.setStatus("NORMAL");
        rdmsCustomerUserService.save(customerUserDto);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @Transactional
    public String save(RdmsCustomerDto customerDto) {
        RdmsCustomer customer = CopyUtil.copy(customerDto, RdmsCustomer.class);
        if(customer.getLoginName() != null){
            RdmsCustomer customerDb = this.selectByLoginName(customer.getLoginName());  //机构登录名
            if(customerDb != null){
                //警告:客户登录名已存在, 机构的登录名称不能重复
                throw new BusinessException(BusinessExceptionCode.CUSTOMER_LOGIN_NAME_EXIST);
            }else{
                if(customer.getContactPhone() != null){
                    customerDb = this.selectByContactPhone(customer.getContactPhone());
                    if(customerDb != null){
                        //警告:联系电话已存在,也就是说电话号码已经存在, 系统只允许一个电话号码对应一个customer管理员账号
                        throw new BusinessException(BusinessExceptionCode.CUSTOMER_CONTACT_PHONE_EXIST);
                    }else{
                        //创建Customer
                        customer.setId("");
                        customer.setStatus(CustomerStatusEnum.CUSTOMER_NORMAL.getStatus());
                        customer.setUserNumLimit(200);  //用户数量限制
                        customer.setBudgetMode(BudgetModeEnum.FREE.getMode());
                        customer.setStorageSpace(2000);  //2000GB   //存储空间限制
                        customer.setEnAuthEdit(0);
                        String customerId = this.insert(customer);

                        //如果是第一次创建用户,需要同时创建一个用户和一个顶级部门,并在customer_user表中增加相应的记录
                        //创建管理员用户,
                        RdmsUserDto userDto = new RdmsUserDto();
                        userDto.setDeleted(0);
                        userDto.setLoginName(customer.getContactPhone());
                        userDto.setTrueName(customer.getAdminName());
                        userDto.setProfile("https://my-course-img.oss-cn-shanghai.aliyuncs.com/user/defaultProfile.jpeg");
                        userDto.setNickname(customerDto.getNickname());
                        userDto.setSex(customerDto.getSex());
                        userDto.setHeadimgurl(customerDto.getHeadimgurl());
                        userDto.setUnionid(customerDto.getUnionid());
                        userDto.setOpenid(customerDto.getOpenid());
                        if(!ObjectUtils.isEmpty(customerDto.getHeadimgurl())){
                            userDto.setProfile(customerDto.getHeadimgurl());
                        }
                        String adminUserId = rdmsUserService.save(userDto);

                        //在customer_user表中增加关联字段的值:  部门 管理员
                        RdmsCustomerUserDto customerUserDto = new RdmsCustomerUserDto();
                        customerUserDto.setCustomerId(customerId);
                        customerUserDto.setUserId(adminUserId);
                        customerUserDto.setLoginName(userDto.getLoginName());
                        customerUserDto.setTrueName(userDto.getTrueName());
                        customerUserDto.setJobNum("J0000000");
                        customerUserDto.setJobLevel("P4"); //默认职级
                        customerUserDto.setDepartmentId(null);
                        customerUserDto.setStatus("NORMAL");
                        customerUserDto.setProfile(userDto.getProfile());
                        String customerUserId = rdmsCustomerUserService.save(customerUserDto);

                        //创建顶级部门
                        RdmsDepartmentDto departmentDto = new RdmsDepartmentDto();
                        departmentDto.setDepartName(customer.getLoginName());
                        departmentDto.setCustomerId(customer.getId());
                        RdmsUser rdmsUser = rdmsUserService.selectByLoginName(customer.getContactPhone());
                        departmentDto.setManagerId(rdmsUser.getId());
                        departmentDto.setPhone(rdmsUser.getLoginName());
                        departmentDto.setDepartDescription("代表公司的顶级部门");
                        departmentDto.setFixed(1);
                        if(departmentDto.getId() == null){
                            departmentDto.setId(UuidUtil.getShortUuid());
                        }
                        String departmentId = rdmsDepartmentService.save(departmentDto);

                        //为管理员添加顶级部门
                        RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(customerUserId);
                        rdmsCustomerUser.setDepartmentId(departmentId);
                        rdmsCustomerUserService.update(rdmsCustomerUser);

                        //添加必须的一级部门
                        addDefaultDepartmentInfo(customerId, customerUserId, departmentId);

                        //添加默认职级
                        RdmsCustomerUserJobLevel customerUserJobLevel = new RdmsCustomerUserJobLevel();
                        customerUserJobLevel.setCustomerId(customer.getId());
                        customerUserJobLevel.setLevelCode("P4");
                        customerUserJobLevel.setManHourFee(BigDecimal.valueOf(100));
                        customerUserJobLevel.setLeveName("默认职级");
                        rdmsJobLevelService.save(customerUserJobLevel);

                        //将管理员添加为默认的Boss
                        RdmsBossDto bossUserDto = new RdmsBossDto();
                        bossUserDto.setCustomerId(customerId);
                        bossUserDto.setBossId(customerUserId);
                        rdmsBossService.save(bossUserDto);

                        //添加到默认权限组
                        RdmsCustomerRoleCustomer customerRoleCustomer =new RdmsCustomerRoleCustomer();
                        customerRoleCustomer.setId(UuidUtil.getShortUuid());
                        customerRoleCustomer.setRoleId("00000001");
                        customerRoleCustomer.setCustomerId(customerId);
                        rdmsCustomerRoleCustomerMapper.insert(customerRoleCustomer);

                        //为机构用户创建通用权限组
                        RdmsRoleDto roleDto = new RdmsRoleDto();
                        roleDto.setId("00000001");
                        roleDto.setName("通用权限");
                        roleDto.setDesc("添加用户时分配的最小权限");
                        roleDto.setCustomerId(customerId);
                        String userRoleId = rdmsUserRoleService.save(roleDto);

                        //为通用权限组分配权限
                        List<String> resourceIdList = new ArrayList<>();
                        resourceIdList.add("00");
                        resourceIdList.add("0001");
                        resourceIdList.add("01");
                        resourceIdList.add("0101");
                        resourceIdList.add("5200");
                        resourceIdList.add("520001");
                        resourceIdList.add("5205");
                        resourceIdList.add("520501");
                        resourceIdList.add("5201");
                        resourceIdList.add("520101");
                        resourceIdList.add("5204");
                        resourceIdList.add("520401");
                        resourceIdList.add("520402");
                        resourceIdList.add("5207");
                        resourceIdList.add("520701");
                        resourceIdList.add("520702");
                        resourceIdList.add("520703");
                        resourceIdList.add("520704");
                        resourceIdList.add("5209");
                        resourceIdList.add("520901");
                        resourceIdList.add("520902");
                        resourceIdList.add("5206");
                        resourceIdList.add("520601");
                        resourceIdList.add("5215");
                        resourceIdList.add("521501");
                        resourceIdList.add("5208");
                        resourceIdList.add("520801");
                        resourceIdList.add("5216");
                        resourceIdList.add("521601");
                        resourceIdList.add("5217");
                        resourceIdList.add("521701");
                        resourceIdList.add("5202");
                        resourceIdList.add("520201");
                        resourceIdList.add("520202");
                        resourceIdList.add("521101");
                        resourceIdList.add("5210");
                        resourceIdList.add("521001");
                        resourceIdList.add("5211");
                        resourceIdList.add("521101");
                        resourceIdList.add("521102");
                        resourceIdList.add("5213");
                        resourceIdList.add("521301");
                        resourceIdList.add("521302");
                        resourceIdList.add("5212");
                        resourceIdList.add("521201");
                        resourceIdList.add("521202");
                        resourceIdList.add("521203");
                        resourceIdList.add("521204");
                        resourceIdList.add("5218");
                        resourceIdList.add("521801");
                        resourceIdList.add("521802");
                        resourceIdList.add("5203");
                        resourceIdList.add("520301");
                        resourceIdList.add("5219");
                        resourceIdList.add("521901");
                        resourceIdList.add("5220");
                        resourceIdList.add("522001");
                        resourceIdList.add("5221");
                        resourceIdList.add("522101");
                        resourceIdList.add("5222");
                        resourceIdList.add("522201");
                        // 保存角色资源
                        for (int i = 0; i < resourceIdList.size(); i++) {
                            RdmsUserRoleResource roleResource = new RdmsUserRoleResource();
                            roleResource.setId(UuidUtil.getShortUuid());
                            roleResource.setRoleId(userRoleId);
                            roleResource.setResourceId(resourceIdList.get(i));
                            roleResource.setCustomerId(roleDto.getCustomerId());
                            rdmsUserRoleResourceMapper.insert(roleResource);
                        }

                        //将管理员添加到IPMT
                        RdmsIpmt rdmsIpmt = new RdmsIpmt();
                        rdmsIpmt.setCustomerId(customerId);
                        List<String> ipmtIdList = new ArrayList<>();
                        ipmtIdList.add(customerUserId);
                        String ipmtJsonString = JSON.toJSONString(ipmtIdList);
                        rdmsIpmt.setIpmtIdList(ipmtJsonString);
                        rdmsIpmtService.save(rdmsIpmt);

                        //将管理员添加为产品经理
                        RdmsProductManager rdmsProductManager = new RdmsProductManager();
                        rdmsProductManager.setCustomerId(customerId);
                        List<String> idList = new ArrayList<>();
                        idList.add(customerUserId);
                        String jsonString = JSON.toJSONString(idList);
                        rdmsProductManager.setProductManagerIdListStr(jsonString);
                        rdmsProductManagerService.save(rdmsProductManager);

                        //将管理员添加为项目经理
                        RdmsProjectManager rdmsProjectManager = new RdmsProjectManager();
                        rdmsProjectManager.setCustomerId(customerId);
                        List<String> projectIdList = new ArrayList<>();
                        projectIdList.add(customerUserId);
                        String projectIdJsonString = JSON.toJSONString(projectIdList);
                        rdmsProjectManager.setProjectManagerIdListStr(projectIdJsonString);
                        rdmsProjectManagerService.save(rdmsProjectManager);

                        //创建标准工时记录
                        RdmsManhourStandard manhourStandard = new RdmsManhourStandard();
                        manhourStandard.setId(UuidUtil.getShortUuid());
                        manhourStandard.setCustomerId(customerId);
                        manhourStandard.setDevManhourFee(BigDecimal.valueOf(60));
                        manhourStandard.setTestManhourFee(BigDecimal.valueOf(60));
                        manhourStandard.setCreateTime(new Date());
                        manhourStandard.setDeleted("0");
                        rdmsManhourStandardMapper.insert(manhourStandard);

                        //创建基础文件分类
                        rdmsCategoryService.initCategoryByCustomerId(customerId);

                        return customerId;
                    }
                }else{
                    throw new BusinessException(BusinessExceptionCode.LOGIN_PHONE_NOT_INPUT);
                }
            }
        }else{
            throw new BusinessException(BusinessExceptionCode.INPUT_INFORMATION_ERROR);
        }
    }

    public void addDefaultDepartmentInfo(String customerId, String bossId, String parentDepartmentId) {
        RdmsCustomer customer = this.selectByPrimaryKey(customerId);
        //1. 产品中心
        RdmsDepartment productCenter = new RdmsDepartment();
        productCenter.setId(null);
        productCenter.setDepartName(FixedDepartmentEnum.PRODUCT_CENTER.getCode());
        productCenter.setCustomerId(customerId);
        productCenter.setManagerId(bossId);
        productCenter.setPhone(customer.getContactPhone());
        productCenter.setDepartDescription(FixedDepartmentEnum.PRODUCT_CENTER.getName());
        productCenter.setAssistantId(null);
        productCenter.setSecretaryId(null);
        productCenter.setParent(parentDepartmentId);
        productCenter.setFixed(1);
        productCenter.setDeleted(0);
        RdmsDepartmentDto rdmsDepartmentDto = CopyUtil.copy(productCenter, RdmsDepartmentDto.class);
        rdmsDepartmentService.save(rdmsDepartmentDto);

        RdmsPdgmDto pdgmByCustomerId = rdmsPdgmService.getPdgmByCustomerId(customerId);
        if(!ObjectUtils.isEmpty(pdgmByCustomerId)){
            pdgmByCustomerId.setPdgmId(bossId);
            rdmsPdgmService.save(pdgmByCustomerId);
        }

        //3. 系统工程
        RdmsDepartment systemDepartment = new RdmsDepartment();
        systemDepartment.setId(null);
        systemDepartment.setDepartName(FixedDepartmentEnum.SYSTEM_ENGINEERING_DEPARTMENT.getCode());
        systemDepartment.setCustomerId(customerId);
        systemDepartment.setManagerId(bossId);
        systemDepartment.setPhone(customer.getContactPhone());
        systemDepartment.setDepartDescription(FixedDepartmentEnum.SYSTEM_ENGINEERING_DEPARTMENT.getName());
        systemDepartment.setAssistantId(null);
        systemDepartment.setSecretaryId(null);
        systemDepartment.setParent(parentDepartmentId);
        systemDepartment.setFixed(1);
        systemDepartment.setDeleted(0);
        RdmsDepartmentDto systemDepartmentDto = CopyUtil.copy(systemDepartment, RdmsDepartmentDto.class);
        rdmsDepartmentService.save(systemDepartmentDto);

        RdmsSysgmDto sysgmByCustomerId = rdmsSysgmService.getSysgmByCustomerId(customerId);
        if(!ObjectUtils.isEmpty(sysgmByCustomerId)){
            sysgmByCustomerId.setSysgmId(bossId);
            rdmsSysgmService.save(sysgmByCustomerId);
        }

        //4. 研发测试部
        RdmsDepartment testDepartment = new RdmsDepartment();
        testDepartment.setId(null);
        testDepartment.setDepartName(FixedDepartmentEnum.TEST_DEPARTMENT.getCode());
        testDepartment.setCustomerId(customerId);
        testDepartment.setManagerId(bossId);
        testDepartment.setPhone(customer.getContactPhone());
        testDepartment.setDepartDescription(FixedDepartmentEnum.TEST_DEPARTMENT.getName());
        testDepartment.setAssistantId(null);
        testDepartment.setSecretaryId(null);
        testDepartment.setParent(parentDepartmentId);
        testDepartment.setFixed(1);
        testDepartment.setDeleted(0);
        RdmsDepartmentDto testDepartmentDto = CopyUtil.copy(testDepartment, RdmsDepartmentDto.class);
        rdmsDepartmentService.save(testDepartmentDto);

        RdmsTgmDto tgmByCustomerId = rdmsTgmService.getTgmByCustomerId(customerId);
        if(!ObjectUtils.isEmpty(tgmByCustomerId)){
            tgmByCustomerId.setTgmId(bossId);
            rdmsTgmService.save(tgmByCustomerId);
        }

        //2. 研发中心
        RdmsDepartment developDepartment = new RdmsDepartment();
        developDepartment.setId(null);
        developDepartment.setDepartName(FixedDepartmentEnum.RESEARCH_AND_DEVELOPMENT_CENTER.getCode());
        developDepartment.setCustomerId(customerId);
        developDepartment.setManagerId(bossId);
        developDepartment.setPhone(customer.getContactPhone());
        developDepartment.setDepartDescription(FixedDepartmentEnum.RESEARCH_AND_DEVELOPMENT_CENTER.getName());
        developDepartment.setAssistantId(null);
        developDepartment.setSecretaryId(null);
        developDepartment.setParent(parentDepartmentId);
        developDepartment.setFixed(1);
        developDepartment.setDeleted(0);
        RdmsDepartmentDto developDepartmentDto = CopyUtil.copy(developDepartment, RdmsDepartmentDto.class);
        rdmsDepartmentService.save(developDepartmentDto);

        //5. 法规部
        RdmsDepartment regulationDepartment = new RdmsDepartment();
        regulationDepartment.setId(null);
        regulationDepartment.setDepartName(FixedDepartmentEnum.REGULATION_DEPARTMENT.getCode());
        regulationDepartment.setCustomerId(customerId);
        regulationDepartment.setManagerId(bossId);
        regulationDepartment.setPhone(customer.getContactPhone());
        regulationDepartment.setDepartDescription(FixedDepartmentEnum.REGULATION_DEPARTMENT.getName());
        regulationDepartment.setAssistantId(null);
        regulationDepartment.setSecretaryId(null);
        regulationDepartment.setParent(parentDepartmentId);
        regulationDepartment.setFixed(1);
        regulationDepartment.setDeleted(0);
        RdmsDepartmentDto regulationDepartmentDto = CopyUtil.copy(regulationDepartment, RdmsDepartmentDto.class);
        rdmsDepartmentService.save(regulationDepartmentDto);

        RdmsFggmDto fggmByCustomerId = rdmsFggmService.getFggmByCustomerId(customerId);
        if(!ObjectUtils.isEmpty(fggmByCustomerId)){
            fggmByCustomerId.setFggmId(bossId);
            rdmsFggmService.save(fggmByCustomerId);
        }

        //6. 质量部
        RdmsDepartment qualityDepartment = new RdmsDepartment();
        qualityDepartment.setId(null);
        qualityDepartment.setDepartName(FixedDepartmentEnum.QUALITY_DEPARTMENT.getCode());
        qualityDepartment.setCustomerId(customerId);
        qualityDepartment.setManagerId(bossId);
        qualityDepartment.setPhone(customer.getContactPhone());
        qualityDepartment.setDepartDescription(FixedDepartmentEnum.QUALITY_DEPARTMENT.getName());
        qualityDepartment.setAssistantId(null);
        qualityDepartment.setSecretaryId(null);
        qualityDepartment.setParent(parentDepartmentId);
        qualityDepartment.setFixed(1);
        qualityDepartment.setDeleted(0);
        RdmsDepartmentDto qualityDepartmentDto = CopyUtil.copy(qualityDepartment, RdmsDepartmentDto.class);
        rdmsDepartmentService.save(qualityDepartmentDto);

        RdmsQcgmDto qcgmByCustomerId = rdmsQcgmService.getQcgmByCustomerId(customerId);
        if(!ObjectUtils.isEmpty(qcgmByCustomerId)){
            qcgmByCustomerId.setQcgmId(bossId);
            rdmsQcgmService.save(qcgmByCustomerId);
        }
    }

    /**
     * 用户注册
     */
    @Transactional
    public String register(RdmsCustomerDto customerDto) {
        RdmsCustomer customerByLoginName = this.getCustomerByLoginName(customerDto.getLoginName());
        if(!ObjectUtils.isEmpty(customerByLoginName)){
            throw new BusinessException(BusinessExceptionCode.CUSTOMER_LOGIN_NAME_EXIST);
        }

        RdmsCustomer customerByContactPhone = this.getCustomerByContactPhone(customerDto.getContactPhone());
        if(!ObjectUtils.isEmpty(customerByContactPhone)){
            throw new BusinessException(BusinessExceptionCode.CONTACT_PHONE_EXIST);
        }

        String customerId = this.save(customerDto);
        RdmsCustomer rdmsCustomer = this.selectByPrimaryKey(customerId);
        if(!ObjectUtils.isEmpty(customerDto.getRegType()) && customerDto.getRegType().equals("aiimooc")){
            rdmsCustomer.setStatus(CustomerStatusEnum.CUSTOMER_NORMAL.getStatus());
            this.update(rdmsCustomer);
        }else{
            rdmsCustomer.setStatus(CustomerStatusEnum.CUSTOMER_REGISTER.getStatus());
            this.update(rdmsCustomer);
        }

        //短信发复核通知
        if(!ObjectUtils.isEmpty(rdmsCustomer)){
            RdmsSmsInfo rdmsSmsInfo = new RdmsSmsInfo();
            rdmsSmsInfo.setMobile(SUPER_ADMIN_LOGIN_NAME);
            if(rdmsCustomer.getLoginName().length()>20){
                rdmsSmsInfo.setName(rdmsCustomer.getLoginName().substring(0,20));
            }else{
                rdmsSmsInfo.setName(rdmsCustomer.getLoginName());
            }
            rdmsSmsInfo.setPhone(rdmsCustomer.getContactPhone());
            rdmsSmsInfo.setType("REGISTER_RECHECK");
            rdmsSmsInfoService.sendRegisterRecheckNotify(rdmsSmsInfo);
        }
        return rdmsCustomer.getId();
    }

    /**
     * 修改公司信息
     */
    @Transactional
    public String modify(RdmsCustomerDto customerDto) {
        RdmsCustomer customer = CopyUtil.copy(customerDto, RdmsCustomer.class);
        if(customer.getLoginName() != null){
            RdmsCustomer customerDb = this.selectByLoginName(customer.getLoginName());  //机构登录名
            if(customerDb != null){
                //如果新的联系电话和以前的电话号码相同
                if(customerDto.getContactPhone().equals(customerDb.getContactPhone())){
                    //对Customer信息进行直接更新
                    if(customer.getId().equals(customerDb.getId()) && (!customer.getAdminName().isEmpty())){
                        customer.setLoginName(customerDb.getLoginName()); //不允许修改机构登录名称
                        this.update(customer);

                        //判断是否是注册用户复核通过
                        if(customerDb.getStatus().equals(CustomerStatusEnum.CUSTOMER_REGISTER.getStatus())  && customerDto.getStatus().equals(CustomerStatusEnum.CUSTOMER_NORMAL.getStatus())){
                            //发复核通过通知
                            RdmsSmsInfo rdmsSmsInfo = new RdmsSmsInfo();
                            rdmsSmsInfo.setMobile(customerDb.getContactPhone());
                            if(customerDb.getLoginName().length()>20){
                                rdmsSmsInfo.setName(customerDb.getLoginName().substring(0,20));
                            }else{
                                rdmsSmsInfo.setName(customerDb.getLoginName());
                            }
                            rdmsSmsInfo.setType("RECHECK_PASS");
                            rdmsSmsInfoService.sendRegisterRecheckPassNotify(rdmsSmsInfo);
                        }
                    }else{
                        throw new BusinessException(BusinessExceptionCode.INPUT_INFORMATION_ERROR);
                    }
                }else{
                    //联系电话和之前的不同
                    //这是超级用户修改了用户的管理员的情况
                    RdmsCustomer rdmsCustomer = this.selectByContactPhone(customer.getContactPhone());
                    if(rdmsCustomer != null){
                        //警告:联系电话已存在,也就是说电话号码已经存在, 系统只允许一个电话号码对应一个customer管理员账号
                        throw new BusinessException(BusinessExceptionCode.CUSTOMER_CONTACT_PHONE_EXIST);
                    }else{
                        //判断这个电话号码对应的用户是否存在
                        List<RdmsUser> rdmsUsers = rdmsUserService.searchUserByloginName(customer.getContactPhone());
                        if(!CollectionUtils.isEmpty(rdmsUsers)){
                            //存在这个用户的情况
                            //判断这个人是否是对应公司的员工
                            List<RdmsCustomerUser> customerUserByLoginName = rdmsCustomerUserService.findCustomerUserByLoginName(customer.getContactPhone());
                            if(!CollectionUtils.isEmpty(customerUserByLoginName)){
                                List<String> customerIdList = customerUserByLoginName.stream().map(RdmsCustomerUser::getCustomerId).collect(Collectors.toList());
                                if(!customerIdList.contains(customerDb.getId())){
                                    //这个开发者不是这个公司的一员
                                    RdmsCustomerUserDto customerUserDto = new RdmsCustomerUserDto();
                                    customerUserDto.setCustomerId(customerDb.getId());
                                    customerUserDto.setUserId(rdmsUsers.get(0).getId());
                                    customerUserDto.setLoginName(rdmsUsers.get(0).getLoginName());
                                    customerUserDto.setTrueName(rdmsUsers.get(0).getTrueName());
                                    customerUserDto.setJobNum("J0000000");
                                    customerUserDto.setStatus("NORMAL");
                                    customerUserDto.setProfile(rdmsUsers.get(0).getProfile());
                                    rdmsCustomerUserService.save(customerUserDto);
                                }/*else{
                                    //如果已经是这个公司的员工则不需要处理
                                }*/
                            }else{
                                //user存在但是他不是任何公司的员工
                                RdmsCustomerUserDto customerUserDto = new RdmsCustomerUserDto();
                                customerUserDto.setCustomerId(customerDb.getId());
                                customerUserDto.setUserId(rdmsUsers.get(0).getId());
                                customerUserDto.setLoginName(rdmsUsers.get(0).getLoginName());
                                customerUserDto.setTrueName(rdmsUsers.get(0).getTrueName());
                                customerUserDto.setJobNum("J0000000");
                                customerUserDto.setStatus("NORMAL");
                                customerUserDto.setProfile(rdmsUsers.get(0).getProfile());
                                rdmsCustomerUserService.save(customerUserDto);
                            }
                        }else{
                            //不存在这个用户的情况
                            //创建管理员用户
                            RdmsUserDto userDto = new RdmsUserDto();
                            userDto.setDeleted(0);
                            userDto.setLoginName(customer.getContactPhone());
                            userDto.setTrueName(customer.getAdminName());
                            userDto.setProfile("https://my-course-img.oss-cn-shanghai.aliyuncs.com/user/defaultProfile.jpeg");
                            String userId = rdmsUserService.save(userDto);

                            RdmsCustomerUserDto customerUserDto = new RdmsCustomerUserDto();
                            customerUserDto.setCustomerId(customerDb.getId());
                            customerUserDto.setUserId(userId);
                            customerUserDto.setLoginName(customerDb.getLoginName());
                            customerUserDto.setTrueName(userDto.getTrueName());
                            customerUserDto.setJobNum("J0000000");
                            customerUserDto.setStatus("NORMAL");
                            customerUserDto.setProfile(userDto.getProfile());
                            rdmsCustomerUserService.save(customerUserDto);
                        }
                    }
                }
            }
        }else{
            throw new BusinessException(BusinessExceptionCode.INPUT_INFORMATION_ERROR);
        }
        return null;
    }

    /**
     * 修改管理员
     * @param adminDto
     * @return
     */
    @Transactional
    public String changeAdmin(RdmsHmiAdminDto adminDto) {
        RdmsCustomer customerDb = this.selectByPrimaryKey(adminDto.getCustomerId());
        if (customerDb != null) {
            RdmsCustomer rdmsCustomer = CopyUtil.copy(customerDb, RdmsCustomer.class);
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(adminDto.getAdminId());
            rdmsCustomer.setAdminName(rdmsCustomerUser.getTrueName());
            rdmsCustomer.setContactPhone(rdmsCustomerUser.getLoginName());
            this.update(rdmsCustomer);
        }
        return null;
    }

    @Transactional
    public RdmsBoss saveBoss(String customerId, String bossId) {
        RdmsBoss boss = new RdmsBoss();
        boss.setCustomerId(customerId);
        boss.setBossId(bossId);
        String id = rdmsBossService.save(boss);
        RdmsBoss boss1 = rdmsBossService.selectByPrimaryKey(id);
        return boss1;
    }

    /**
     * 新增
     */
    private String insert(RdmsCustomer customer) {
        if(org.springframework.util.ObjectUtils.isEmpty(customer.getId())){  //当前端页面给出projectID时,将不为空
            customer.setId(UuidUtil.getShortUuid());
        }
        customer.setDeleted(0);
        customer.setCreateTime(new Date());
        if(ObjectUtils.isEmpty(customer.getPassword())){
            String tempPass = customer.getId()+KEY;  //原始密码加盐
            customer.setPassword((DigestUtils.md5DigestAsHex(tempPass.getBytes()))); //第一次加盐加密
            customer.setPassword((DigestUtils.md5DigestAsHex(customer.getPassword().getBytes())));  //第二次加密
        }
        RdmsCustomer customerDb = this.selectByLoginName(customer.getLoginName());
        if (customerDb != null) {
            throw new BusinessException(BusinessExceptionCode.CUSTOMER_LOGIN_NAME_EXIST);
        }
        customerMapper.insert(customer);
        //添加默认权限组
        RdmsCustomerRoleCustomer record = new RdmsCustomerRoleCustomer();
        record.setId(UuidUtil.getShortUuid());
        record.setCustomerId(customer.getId());
        record.setRoleId("00000001");
        rdmsCustomerRoleCustomerMapper.insert(record);
        return customer.getId();
    }

    /**
     * 新增
     */
    public String savePassword(String customerId, String password) {
        RdmsCustomer customer = this.selectByPrimaryKey(customerId);
        if(!ObjectUtils.isEmpty(password)){
            String tempPass = password+KEY;  //原始密码加盐
            customer.setPassword((DigestUtils.md5DigestAsHex(tempPass.getBytes()))); //第一次加盐加密
            customer.setPassword((DigestUtils.md5DigestAsHex(customer.getPassword().getBytes())));  //第二次加密
            return this.update(customer);
        }
        return null;
    }

    /**
     * 更新
     */
    private String update(RdmsCustomer customer) {
        if(customer.getPassword() == null){
            //如果客户密码为空,则将客户的ID作为默认密码;
            String tempPass = customer.getId()+KEY;  //原始密码加盐
            customer.setPassword((DigestUtils.md5DigestAsHex(tempPass.getBytes()))); //第一次加盐加密
            customer.setPassword((DigestUtils.md5DigestAsHex(customer.getPassword().getBytes())));  //第二次加密
        }
        RdmsCustomerExample customerExample = new RdmsCustomerExample();
        RdmsCustomerExample.Criteria criteria = customerExample.createCriteria();
        criteria.andLoginNameEqualTo(customer.getLoginName());
        customerMapper.updateByExampleSelective(customer,customerExample);
        return customer.getId();
    }

    public String updateByPrimaryKeySelective(RdmsCustomer customer) {
        customerMapper.updateByPrimaryKeySelective(customer);
        return customer.getId();
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsCustomer customer = this.selectByPrimaryKey(id);
        if(customer != null){
            customer.setDeleted(1);  //设置删除标志位
            this.update(customer);
        }
    }

    /**
     * 根据登录名查询用户信息
     * @param loginName
     * @return
     */
    public RdmsCustomer selectByLoginName(String loginName) {
        RdmsCustomerExample customerExample = new RdmsCustomerExample();
        customerExample.createCriteria().andLoginNameEqualTo(loginName);
        List<RdmsCustomer> customerList = customerMapper.selectByExample(customerExample);
        if (CollectionUtils.isEmpty(customerList)) {
            return null;
        } else {
            return customerList.get(0);
        }
    }

    public RdmsCustomer selectByToken(String token) {
        RdmsCustomerExample customerExample = new RdmsCustomerExample();
        customerExample.createCriteria().andLoginTokenEqualTo(token);
        List<RdmsCustomer> customerList = customerMapper.selectByExample(customerExample);
        if (CollectionUtils.isEmpty(customerList)) {
            return null;
        } else {
            return customerList.get(0);
        }
    }

    public List<RdmsCustomer> getAllCustomerList() {
        RdmsCustomerExample customerExample = new RdmsCustomerExample();
        customerExample.createCriteria().andStatusEqualTo(CustomerStatusEnum.CUSTOMER_NORMAL.getStatus());
        List<RdmsCustomer> customerList = customerMapper.selectByExample(customerExample);
        return customerList;
    }

    /**
     * 根据联系电话查询用户信息
     * @param contactPhone
     * @return
     */
    public RdmsCustomer selectByContactPhone(String contactPhone) {
        RdmsCustomerExample customerExample = new RdmsCustomerExample();
        customerExample.createCriteria().andContactPhoneEqualTo(contactPhone);
        List<RdmsCustomer> customerList = customerMapper.selectByExample(customerExample);
        if (CollectionUtils.isEmpty(customerList)) {
            return null;
        } else {
            return customerList.get(0);
        }
    }

    public RdmsCustomer getCustomerByLoginName(String loginname) {
        RdmsCustomerExample customerExample = new RdmsCustomerExample();
        customerExample.createCriteria().andLoginNameEqualTo(loginname);
        List<RdmsCustomer> customerList = customerMapper.selectByExample(customerExample);
        if (CollectionUtils.isEmpty(customerList)) {
            return null;
        } else {
            return customerList.get(0);
        }
    }

    public RdmsCustomer getCustomerByContactPhone(String contactPhone) {
        RdmsCustomerExample customerExample = new RdmsCustomerExample();
        customerExample.createCriteria().andContactPhoneEqualTo(contactPhone).andDeletedEqualTo(0);
        List<RdmsCustomer> customerList = customerMapper.selectByExample(customerExample);
        if (CollectionUtils.isEmpty(customerList)) {
            return null;
        } else {
            return customerList.get(0);
        }
    }



    /**
     * 根据ID查询用户信息
     * @param id
     * @return
     */
     public RdmsCustomer selectByPrimaryKey(String id) {
         return customerMapper.selectByPrimaryKey(id);
    }


    /**
     * 重置密码
     * @param customerDto
     */
    public void savePassword(RdmsCustomerDto customerDto) {
        RdmsCustomer customer = new RdmsCustomer();
        customer.setId(customerDto.getId());
        customer.setPassword(customerDto.getPassword());

        RdmsCustomerExample customerExample = new RdmsCustomerExample();
        RdmsCustomerExample.Criteria criteria = customerExample.createCriteria();
        criteria.andIdEqualTo(customerDto.getId());
        customerMapper.updateByExampleSelective(customer,customerExample);
    }

    /**
     * 登录
     * 判断登录密码是否正确,如果密码正确,就给用户赋予相应的访问授权
     * @param customerDto
     */
    @Transactional
    public LoginUserDto login(RdmsCustomerDto customerDto) {
        //判断customerUser表中超级用户是否存在, 如果不存在, 则创建初始超级用户
        RdmsCustomer superAdmin = customerMapper.selectByPrimaryKey(SUPER_CUSTOMER_ID);
        if(superAdmin == null){
            //创建初始超级用户
            this.initSystemSuperAdmin();
        }

        RdmsCustomer customer = this.selectByContactPhone(customerDto.getLoginName().trim());
        if(ObjectUtils.isEmpty(customer)){
            throw new BusinessException(BusinessExceptionCode.CUSTOMER_NOT_EXIST);
        }

        //判断账户状态
        if(customer.getStatus().equals(CustomerStatusEnum.CUSTOMER_REGISTER.getStatus())){
            throw new BusinessException(BusinessExceptionCode.CUSTOMER_REGISTER_RECHECK);
        }else if(customer.getStatus().equals(CustomerStatusEnum.CUSTOMER_FROZEN.getStatus())){
            throw new BusinessException(BusinessExceptionCode.CUSTOMER_FROZEN);
        }else if(customer.getStatus().equals(CustomerStatusEnum.CUSTOMER_STOP.getStatus())){
            throw new BusinessException(BusinessExceptionCode.CUSTOMER_STOP);
        }

        //判断账号是否过期
        long currentTime = new Date().getTime();
        if(!ObjectUtils.isEmpty(customer.getExpirationTime())){
            if(!ObjectUtils.isEmpty(customer.getExpirationTime()) && !customer.getId().equals(SUPER_CUSTOMER_ID)){
                long overdueTime = customer.getExpirationTime().getTime();
                if(currentTime > overdueTime){
                    throw new BusinessException(BusinessExceptionCode.OVERDUE_ERROR);
                }
            }
        }

        //如果没有创建基础文件分类,则创建之
        rdmsCategoryService.initCategoryByCustomerId(customer.getId());

        if (ObjectUtils.isEmpty(customer)) {
//            LOG.info("用户名不存在, {}", customerDto.getLoginName());
            throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
        } else {
            if (customer.getPassword().equals(customerDto.getPassword())) {
                // 登录成功
                LoginUserDto loginUserDto = CopyUtil.copy(customer, LoginUserDto.class);
                loginUserDto.setName(loginUserDto.getLoginName());
                //判断是不是平台超级用户, 如果是平台超级用户, 给超级用户设定完全权限
                if(loginUserDto.getId().equals(SUPER_CUSTOMER_ID)){
                    // 读出role-resource表中所有权限, 全部授权给超级用户
                    this.setSuperAuth(loginUserDto);
                    return loginUserDto;
                }else{
                    // 为登录用户读取权限
                    this.setAuth(loginUserDto);
                    return loginUserDto;
                }

            } else {
//                LOG.info("密码不对, 输入密码：{}, 数据库密码：{}", customerDto.getPassword(), customer.getPassword());
                throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
            }
        }
    }

    /**
     * 为登录用户读取权限
     */
    private void setAuth(LoginUserDto loginUserDto) {
        //List<ResourceDto> resourceDtoList = myUserMapper.findResources(loginUserDto.getId());
        //1. 通过user的id，查user_role表，得到userRoleIdList
        RdmsCustomerRoleCustomerExample roleUserExample = new RdmsCustomerRoleCustomerExample();
        RdmsCustomerRoleCustomerExample.Criteria criteria = roleUserExample.createCriteria();
        criteria.andCustomerIdEqualTo(loginUserDto.getId());
        List<RdmsCustomerRoleCustomer> roleUserList = rdmsCustomerRoleCustomerMapper.selectByExample(roleUserExample);

        //2. 通过userRoleIdList，查role_resource表，得到userResourceIdlist
        List<RdmsCustomerRoleResource> roleResourceList = new ArrayList<>();
        for(RdmsCustomerRoleCustomer roleUser: roleUserList){
            RdmsCustomerRoleResourceExample roleResourceExample = new RdmsCustomerRoleResourceExample();
            RdmsCustomerRoleResourceExample.Criteria resCriteria = roleResourceExample.createCriteria();
            resCriteria.andRoleIdEqualTo(roleUser.getRoleId());
            List<RdmsCustomerRoleResource> roleResources = rdmsCustomerRoleResourceMapper.selectByExample(roleResourceExample);
            for(RdmsCustomerRoleResource roleResource: roleResources){
                roleResourceList.add(roleResource);
            }
        }
        List<RdmsCustomerRoleResource> roleResourceList1 = roleResourceList.stream().distinct().collect(Collectors.toList());

        //3. 通过userResourceIdlist，查resource表，得到用户的resourceList
        List<RdmsCustomerResource> resourceList = new ArrayList<>();
        for(RdmsCustomerRoleResource roleResource: roleResourceList1){
            RdmsCustomerResourceExample resourceExample = new RdmsCustomerResourceExample();
            RdmsCustomerResourceExample.Criteria resCriteria = resourceExample.createCriteria();
            resCriteria.andIdEqualTo(roleResource.getResourceId());
            List<RdmsCustomerResource> resourceAuthList = rdmsCustomerResourceMapper.selectByExample(resourceExample);
            for(RdmsCustomerResource resourceAuth: resourceAuthList){
                resourceList.add(resourceAuth);
            }
        }
        List<RdmsCustomerResource> resourceList1 = resourceList.stream().distinct().collect(Collectors.toList());
        //4. 组装数据
        List<ResourceDto> resourceDtoList = new ArrayList<>();
        for(RdmsCustomerResource resourceAuth : resourceList1){
            ResourceDto resourceDto = new ResourceDto();
            BeanUtils.copyProperties(resourceAuth, resourceDto);
            resourceDtoList.add(resourceDto);
        }

        loginUserDto.setResources(resourceDtoList);

        // 整理所有有权限的请求，用于接口拦截
        HashSet<String> requestSet = new HashSet<>();
        if (!CollectionUtils.isEmpty(resourceDtoList)) {
            for (int i = 0, l = resourceDtoList.size(); i < l; i++) {
                ResourceDto resourceDto = resourceDtoList.get(i);
                String arrayString = resourceDto.getRequest();
                List<String> requestList = JSON.parseArray(arrayString, String.class);
                if (!CollectionUtils.isEmpty(requestList)) {
                    requestSet.addAll(requestList);
                }
            }
        }
//        LOG.info("有权限的请求：{}", requestSet);
        loginUserDto.setRequests(requestSet);
    }

    /**
     * 为平台超级用户设置和读取权限
     */
    private void setSuperAuth(LoginUserDto loginUserDto) {
        //查询customer_resource表,得到resourceList
        RdmsCustomerResourceExample resourceExample = new RdmsCustomerResourceExample();
        List<RdmsCustomerResource> resourceList = rdmsCustomerResourceMapper.selectByExample(resourceExample);
        if(CollectionUtils.isEmpty(resourceList)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_RESOURCE_NOT_EXIST);
        }

        //4. 组装数据
        List<ResourceDto> resourceDtoList = new ArrayList<>();
        for(RdmsCustomerResource resourceAuth : resourceList){
            ResourceDto resourceDto = new ResourceDto();
            BeanUtils.copyProperties(resourceAuth, resourceDto);
            resourceDtoList.add(resourceDto);
        }

        loginUserDto.setResources(resourceDtoList);

        // 整理所有有权限的请求，用于接口拦截
        HashSet<String> requestSet = new HashSet<>();
        if (!CollectionUtils.isEmpty(resourceDtoList)) {
            for (int i = 0, l = resourceDtoList.size(); i < l; i++) {
                ResourceDto resourceDto = resourceDtoList.get(i);
                String arrayString = resourceDto.getRequest();
                List<String> requestList = JSON.parseArray(arrayString, String.class);
                if (!CollectionUtils.isEmpty(requestList)) {
                    requestSet.addAll(requestList);
                }
            }
        }
//        LOG.info("有权限的请求：{}", requestSet);
        loginUserDto.setRequests(requestSet);
    }

    /**
     * 按登录名查找
     */
    public LoginUserDto findByLoginName(String loginName) {
        RdmsCustomer customer = this.selectByLoginName(loginName);
        return CopyUtil.copy(customer, LoginUserDto.class);
    }

    /**
     * 按联系电话查找
     */
    public LoginUserDto findByContactPhone(String contactPhone) {
        RdmsCustomer customer = this.selectByContactPhone(contactPhone);
        return CopyUtil.copy(customer, LoginUserDto.class);
    }

    /**
     * 通过短信验证重置密码
     */
    public void resetPassword(RdmsCustomerDto customerDto) throws BusinessException {
        RdmsCustomer customerDb = this.selectByContactPhone(customerDto.getContactPhone());
        if (customerDb == null) {
            throw new BusinessException(BusinessExceptionCode.MEMBER_NOT_EXIST);
        } else {
            RdmsCustomer customer = new RdmsCustomer();
            customer.setPassword(customerDto.getPassword());
            RdmsCustomerExample customerExample = new RdmsCustomerExample();
            RdmsCustomerExample.Criteria criteria = customerExample.createCriteria();
            criteria.andContactPhoneEqualTo(customerDto.getContactPhone());
            customerMapper.updateByExampleSelective(customer,customerExample);
        }
    }

    /**
     * 根据客户的loginName查询用户信息
     * @param loginName
     * @return
     */
    public List<RdmsCustomer> searchCustomerByLoginName(String loginName){
        if(loginName.trim().equals("") || loginName.trim() == null){
            throw new BusinessException(BusinessExceptionCode.INPUT_INFORMATION_ERROR);
        };
        RdmsCustomerExample customerExample = new RdmsCustomerExample();
        customerExample.createCriteria().andLoginNameEqualTo(loginName);
        List<RdmsCustomer> customersFromLoginNames = customerMapper.selectByExample(customerExample);
        return customersFromLoginNames;
    }

    /**
     * 根据customerName查询客户信息
     * @param customerName
     * @return
     */
    public List<RdmsCustomer> searchCustomerByCustomerName(String customerName){
        if(customerName.trim().equals("") || customerName.trim() == null){
            throw new BusinessException(BusinessExceptionCode.INPUT_INFORMATION_ERROR);
        };
        RdmsCustomerExample customerExample = new RdmsCustomerExample();
        customerExample.createCriteria().andCustomerNameEqualTo(customerName);
        List<RdmsCustomer> customersFromLoginNames = customerMapper.selectByExample(customerExample);
        return customersFromLoginNames;
    }

    /**
     * 根据联系电话contactPhone查询客户信息
     * @param contactPhone
     * @return
     */
    public List<RdmsCustomer> searchCustomerByContactPhone(String contactPhone){
        if(contactPhone.trim().equals("") || contactPhone.trim() == null){
            throw new BusinessException(BusinessExceptionCode.INPUT_INFORMATION_ERROR);
        };
        RdmsCustomerExample customerExample = new RdmsCustomerExample();
        customerExample.createCriteria().andContactPhoneEqualTo(contactPhone);
        List<RdmsCustomer> customersFromLoginNames = customerMapper.selectByExample(customerExample);
        return customersFromLoginNames;
    }

    /**
     * 根据公司的信用代码CreditCode查询企业
     * @param creditCode
     * @return
     */
    public RdmsCustomer searchCustomerByCreditCode(String creditCode){
        RdmsCustomerExample customerExample = new RdmsCustomerExample();
        RdmsCustomerExample.Criteria criteria = customerExample.createCriteria();
        criteria.andCreditCodeEqualTo(creditCode);
        List<RdmsCustomer> rdmsCustomers = customerMapper.selectByExample(customerExample);
        if(rdmsCustomers.size()>0){
            return rdmsCustomers.get(0);
        }
        return null;
    }

}
