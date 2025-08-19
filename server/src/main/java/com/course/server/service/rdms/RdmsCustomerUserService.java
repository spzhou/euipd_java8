/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.*;
import com.course.server.dto.PageDto;
import com.course.server.dto.rdms.*;
import com.course.server.enums.rdms.*;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.*;
import com.course.server.mapper.rdms.MyCustomerUserMapper;
import com.course.server.service.calculate.CalManhourService;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RdmsCustomerUserService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsCustomerUserService.class);

    @Resource
    private RdmsCustomerUserMapper customerUserMapper;
    @Resource
    private MyCustomerUserMapper myCustomerUserMapper;
    @Resource
    private RdmsUserRoleService rdmsUserRoleService;
    @Resource
    private RdmsUserRoleUserMapper rdmsUserRoleUserMapper;
    @Resource
    private RdmsPreProjectService rdmsPreProjectService;
    @Resource
    private RdmsCharacterService rdmsCharacterService;
    @Resource
    private RdmsJobItemService rdmsJobItemService;
    @Resource
    private RdmsProjectService rdmsProjectService;
    @Resource
    private RdmsCustomerService rdmsCustomerService;
    @Resource
    private RdmsFileAuthService rdmsFileAuthService;
    @Resource
    private CalManhourService calManhourService;
    @Resource
    private RdmsManhourService rdmsManhourService;
    @Autowired
    private RdmsBossService rdmsBossService;
    @Autowired
    private RdmsDepartmentService rdmsDepartmentService;

    /**
     * 保存，id有值时更新，无值时新增
     */
    @Transactional
    public String save(RdmsCustomerUserDto customerUserDto) {
        RdmsCustomerUser customerUser = CopyUtil.copy(customerUserDto, RdmsCustomerUser.class);
        if(customerUser.getCreateTime()==null){
            customerUser.setCreateTime(new Date());
        }
        customerUser.setDeleted(0);

        RdmsCustomerUserExample customerUserExample = new RdmsCustomerUserExample();
        RdmsCustomerUserExample.Criteria criteria = customerUserExample.createCriteria();
        if(customerUserDto.getCustomerId() != null && customerUserDto.getLoginName() !=null){
            criteria.andCustomerIdEqualTo(customerUserDto.getCustomerId()).andLoginNameEqualTo(customerUserDto.getLoginName());
            //根据用户限制情况,判断是否需要保存
            /*RdmsCustomerUserExample customerUserExample1 = new RdmsCustomerUserExample();
            RdmsCustomerUserExample.Criteria criteria1 = customerUserExample1.createCriteria();
            criteria1.andCustomerIdEqualTo(customerUserDto.getCustomerId());
            long l = customerUserMapper.countByExample(customerUserExample1);*/

//            RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(customerUserDto.getCustomerId());
            //正常账号,不做用户数量限制
//            if(l <= rdmsCustomer.getUserNumLimit())
            {
                List<RdmsCustomerUser> rdmsCustomerUsers = customerUserMapper.selectByExample(customerUserExample);
                if(! CollectionUtils.isEmpty(rdmsCustomerUsers)){
                    return this.update(customerUser);
                }else{
                    return this.insert(customerUser);
                }
            }
            /*else {
                throw new BusinessException(BusinessExceptionCode.USER_NUM_LIMIT_ERROR);
            }*/
        }else {
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
    }

    /**
     * 新增
     */
    private String insert(RdmsCustomerUser customerUser) {
        if(org.springframework.util.ObjectUtils.isEmpty(customerUser.getId())){  //当前端页面给出projectID时,将不为空
            customerUser.setId(UuidUtil.getShortUuid());
        }
        customerUser.setJoinTime(new Date());
        RdmsCustomerUserExample customerUserExample = new RdmsCustomerUserExample();
        RdmsCustomerUserExample.Criteria criteria = customerUserExample.createCriteria();
        criteria.andCustomerIdEqualTo(customerUser.getCustomerId()).andLoginNameEqualTo(customerUser.getLoginName());
        List<RdmsCustomerUser> customerDbs = customerUserMapper.selectByExample(customerUserExample);
        if(! CollectionUtils.isEmpty(customerDbs)){
            LOG.error("客户用户名重复! 发生在 RdmsCustomerUserService 的 insert 方法中");
            throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
        }else{
            customerUserMapper.insert(customerUser);
            //添加默认权限组
            RdmsUserRoleUser record = new RdmsUserRoleUser();
            record.setId(UuidUtil.getShortUuid());
            record.setUserId(customerUser.getId());
            record.setRoleId("00000001"); //通用权限组
            record.setCustomerId(customerUser.getCustomerId());
            rdmsUserRoleUserMapper.insert(record);
            return customerUser.getId();
        }
    }

    /**
     * 更新
     */
    public String update(RdmsCustomerUser customerUser) {
        RdmsCustomerUser rdmsCustomerUser = this.selectByPrimaryKey(customerUser.getId());
        if(!ObjectUtils.isEmpty(rdmsCustomerUser)){
            customerUser.setId(rdmsCustomerUser.getId());
            customerUser.setCreateTime(rdmsCustomerUser.getCreateTime());
            customerUserMapper.updateByPrimaryKey(customerUser);
        }else{
            this.insert(customerUser);
        }
        return customerUser.getId();
    }

    /**
     * 根据ID查询用户信息
     * @param id
     * @return
     */
    public RdmsCustomerUser selectByPrimaryKey(String id) {
        RdmsCustomerUser rdmsCustomerUser = customerUserMapper.selectByPrimaryKey(id);
        if (rdmsCustomerUser != null) {
            return rdmsCustomerUser;
        }
            return null;
    }

    /**
     * 根据UserId查询user所有任职信息
     * @param userId
     * @return
     */
    public List<RdmsCustomerUser> selectByUserId(String userId) {
        RdmsCustomerUserExample customerUserExample = new RdmsCustomerUserExample();
        customerUserExample.createCriteria().andUserIdEqualTo(userId);
        List<RdmsCustomerUser> customerUserList = customerUserMapper.selectByExample(customerUserExample);
        if (CollectionUtils.isEmpty(customerUserList)) {
            return null;
        } else {
            return customerUserList;
        }
    }


    public List<RdmsCustomerUser> getMemberListByDepartmentId(String departmentId) {
        /**
         * 根据部门ID获取成员列表
         * 查询指定部门下状态正常且未删除的成员列表
         *
         * @param departmentId 部门ID
         * @return 成员列表，若不存在返回null
         */
        RdmsCustomerUserExample customerUserExample = new RdmsCustomerUserExample();
        customerUserExample.createCriteria()
                .andDepartmentIdEqualTo(departmentId)
                .andStatusEqualTo(WorkerStatusEnum.WORKER_NORMAL.getStatus())
                .andDeletedEqualTo(0);
        List<RdmsCustomerUser> customerUserList = customerUserMapper.selectByExample(customerUserExample);
        if (CollectionUtils.isEmpty(customerUserList)) {
            return null;
        } else {
            return customerUserList;
        }
    }

    public List<RdmsCustomerUser> getMemberListByDepartmentIdList(List<String> departmentIdList) {
        /**
         * 根据部门ID集合获取成员列表
         * 查询多个部门下状态正常且未删除的成员列表
         *
         * @param departmentIdList 部门ID集合
         * @return 成员列表，若不存在返回null
         */
        RdmsCustomerUserExample customerUserExample = new RdmsCustomerUserExample();
        customerUserExample.createCriteria()
                .andDepartmentIdIn(departmentIdList)
                .andStatusEqualTo(WorkerStatusEnum.WORKER_NORMAL.getStatus())
                .andDeletedEqualTo(0);
        List<RdmsCustomerUser> customerUserList = customerUserMapper.selectByExample(customerUserExample);
        if (CollectionUtils.isEmpty(customerUserList)) {
            return null;
        } else {
            return customerUserList;
        }
    }

    @Transactional
    public String turnOver(String customerId, String workerId, String newWorkerId) {
        /**
         * 人员交接
         * 将指定员工的未完成任务和交付文件授权转移给新员工
         *
         * @param customerId 客户ID
         * @param workerId 原员工ID
         * @param newWorkerId 新员工ID
         * @return 新员工ID
         */
        {//所有未完成的任务全部转交
            List<JobItemStatusEnum> statusList = new ArrayList<>();
            statusList.add(JobItemStatusEnum.COMPLETED);
            statusList.add(JobItemStatusEnum.ARCHIVED);
            statusList.add(JobItemStatusEnum.REFUSE);
            statusList.add(JobItemStatusEnum.CANCEL);
            List<RdmsJobItemDto> jobitemList = rdmsJobItemService.getJobitemListByCustomerIdExecutorIdAndNotInStatus(customerId, workerId, statusList);
            if (!CollectionUtils.isEmpty(jobitemList)) {
                for (RdmsJobItemDto jobItemDto : jobitemList) {
                    jobItemDto.setExecutorId(newWorkerId);
                    rdmsJobItemService.updateByPrimaryKeySelective(jobItemDto);
                }
            }
        }
        {//所有交付文件全部转交
            List<RdmsJobItemDto> jobitemListByExecutorId = rdmsJobItemService.getJobitemListByExecutorId(workerId);
            if (!CollectionUtils.isEmpty(jobitemListByExecutorId)) {
                for (RdmsJobItemDto jobItemDto : jobitemListByExecutorId) {
                    List<String> propertyFileIdList = rdmsJobItemService.getPropertyFileIdList(jobItemDto.getId());
                    if (!CollectionUtils.isEmpty(propertyFileIdList)) {
                        for (String fileId : propertyFileIdList) {
                            RdmsFileAuth fileAuth = rdmsFileAuthService.getByFileId(fileId);
                            if(!(ObjectUtils.isEmpty(fileAuth) || ObjectUtils.isEmpty(fileAuth.getAuthIdsStr()))){
                                List<String> authUserIds = JSON.parseArray(fileAuth.getAuthIdsStr(), String.class);
                                if (!CollectionUtils.isEmpty(authUserIds)) {
                                    authUserIds.add(newWorkerId);
                                    List<String> collect = authUserIds.stream().distinct().collect(Collectors.toList());
                                    String jsonString = JSON.toJSONString(collect);
                                    fileAuth.setAuthIdsStr(jsonString);
                                    rdmsFileAuthService.updateByPrimaryKeySelective(fileAuth);
                                } else {
                                    List<String> authUserIdList = new ArrayList<>();
                                    authUserIdList.add(newWorkerId);
                                    String jsonString = JSON.toJSONString(authUserIdList);
                                    fileAuth.setAuthIdsStr(jsonString);
                                    rdmsFileAuthService.updateByPrimaryKeySelective(fileAuth);
                                }
                            }else{
                                List<String> authUserIdList = new ArrayList<>();
                                authUserIdList.add(newWorkerId);
                                String jsonString = JSON.toJSONString(authUserIdList);
                                RdmsFileAuth fileAuth1 = new RdmsFileAuth();
                                fileAuth1.setAuthIdsStr(jsonString);
                                fileAuth1.setFileId(fileId);
                                rdmsFileAuthService.save(fileAuth1);
                            }

                        }
                    }
                }
            }
        }
        return newWorkerId;
    }

    public Boolean isAdmin(String customerId, String pdmId) {
        /**
         * 判断是否为客户管理员
         * 依据客户联系手机号与用户登录名是否一致判断
         *
         * @param customerId 客户ID
         * @param pdmId 用户ID
         * @return true为管理员，否则false
         */
        //判断是否是管理员
        RdmsCustomerUser rdmsCustomerUser = this.selectByPrimaryKey(pdmId);
        if(!ObjectUtils.isEmpty(rdmsCustomerUser)){
            RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(customerId);
            if(rdmsCustomer != null){
                if(rdmsCustomer.getContactPhone().equals(rdmsCustomerUser.getLoginName())){
                    //是管理员
                    return true;
                }else{
                    return false;
                }
            }
        }
        return false;
    }

    public Boolean isBoss(String customerId, String customerUserId) {
        /**
         * 判断是否为客户BOSS
         *
         * @param customerId 客户ID
         * @param customerUserId 用户ID
         * @return true表示是BOSS，否则false
         */
        //判断是否是管理员
        RdmsBossDto bossByCustomerId = rdmsBossService.getBossByCustomerId(customerId);
        if (!ObjectUtils.isEmpty(bossByCustomerId) && !ObjectUtils.isEmpty(customerUserId)) {
            if (bossByCustomerId.getBossId().equals(customerUserId)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 根据UserId和customerID获得用户的customerUserId
     * @param userId
     * @param customerId
     * @return
     */
    public RdmsCustomerUser getCustomerUser(String userId, String customerId) {
        RdmsCustomerUserExample customerUserExample = new RdmsCustomerUserExample();
        customerUserExample.createCriteria().andUserIdEqualTo(userId).andCustomerIdEqualTo(customerId);
        List<RdmsCustomerUser> customerUserList = customerUserMapper.selectByExample(customerUserExample);
        if (CollectionUtils.isEmpty(customerUserList)) {
            return null;
        } else {
            return customerUserList.get(0);
        }
    }

    @Transactional
    public RdmsCustomerUser getAdminUserInfo(String customerId) {
        /**
         * 获取客户管理员用户信息
         * 根据客户配置的联系人登录名获取管理员任职记录
         *
         * @param customerId 客户ID
         * @return 管理员任职记录，可能为null
         */
        //当前公司的管理员
        if(!ObjectUtils.isEmpty(customerId)){
            RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(customerId);
            if(!ObjectUtils.isEmpty(rdmsCustomer)){
                return this.getCustomerUserByCustomerIdAndLoginName(customerId, rdmsCustomer.getContactPhone());
            }else{
                return null;
            }
        }
        return null;
    }

    /**
     * 根据customerId查询公司所有员工列表UserList
     * @param customerId
     * @return
     */
    @Transactional
    public RdmsCustomerUser getCustomerUserByCustomerIdAndLoginName(String customerId, String loginName) {
        RdmsCustomerUserExample customerUserExample = new RdmsCustomerUserExample();
        customerUserExample.createCriteria().andCustomerIdEqualTo(customerId).andLoginNameEqualTo(loginName);
        List<RdmsCustomerUser> customerUserList = customerUserMapper.selectByExample(customerUserExample);
        if(!CollectionUtils.isEmpty(customerUserList)){
            return customerUserList.get(0);
        }else{
            return null;
        }
    }

    /**
     * 根据departmentId查询公司所有员工列表UserList
     * @param departmentId
     * @return
     */
    @Transactional
    public List<RdmsUser> findUsersByDepartmentId(String departmentId) {
        /**
         * 根据客户ID获取用户列表
         * 注意：当前实现按客户ID筛选，再按用户ID集合查询用户信息
         *
         * @param departmentId 客户ID
         * @return 用户列表，可能为null
         */
        RdmsCustomerUserExample customerUserExample = new RdmsCustomerUserExample();
        customerUserExample.createCriteria().andCustomerIdEqualTo(departmentId);
        List<RdmsCustomerUser> customerUserList = customerUserMapper.selectByExample(customerUserExample);

        List<String> userList = customerUserList.stream().map(RdmsCustomerUser::getUserId).collect(Collectors.toList());
        List<RdmsUser> users = myCustomerUserMapper.findUsers(userList);

        if (CollectionUtils.isEmpty(users)) {
            return null;
        } else {
            return users;
        }
    }

    /**
     * 根据customerId查询公司所有部门列表DepartmentList
     * @param customerId
     * @return
     */
    @Transactional
    public List<RdmsDepartment> findDepartments(String customerId) {
        /**
         * 根据客户ID获取部门列表
         * 通过任职信息中的部门ID集合查询部门信息
         *
         * @param customerId 客户ID
         * @return 部门列表，可能为null
         */
        RdmsCustomerUserExample customerUserExample = new RdmsCustomerUserExample();
        customerUserExample.createCriteria().andCustomerIdEqualTo(customerId);
        List<RdmsCustomerUser> customerUserList = customerUserMapper.selectByExample(customerUserExample);

        List<String> departmentList = customerUserList.stream().map(RdmsCustomerUser::getDepartmentId).collect(Collectors.toList());
        List<RdmsDepartment> departments = myCustomerUserMapper.findDepartments(departmentList);

        if (CollectionUtils.isEmpty(departments)) {
            return null;
        } else {
            return departments;
        }
    }

    /**
     * 分页查询某个公司的工作人员列表
     * @Param pageDto
     * @return
     */
    @Transactional
    public void list(RdmsWorkerPageDto pageDto){
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsCustomerUserExample customerUserExample = new RdmsCustomerUserExample();
        RdmsCustomerUserExample.Criteria criteria = customerUserExample.createCriteria();
        criteria.andDeletedEqualTo(0)
                .andCustomerIdEqualTo(pageDto.getCustomerId());
        List<RdmsCustomerUser> rdmsCustomerUsers = customerUserMapper.selectByExample(customerUserExample);
        List<RdmsCustomerUserDto> rdmsCustomerUserDtos = CopyUtil.copyList(rdmsCustomerUsers, RdmsCustomerUserDto.class);

        PageInfo<RdmsCustomerUser> pageInfo = new PageInfo<>(rdmsCustomerUsers);
        pageDto.setTotal(pageInfo.getTotal());
        this.addCustomerUserAuxInfo(rdmsCustomerUserDtos);
        pageDto.setList(rdmsCustomerUserDtos);
    }

    /**
     * 为员工DTO列表补充辅助信息
     * 包含日期格式化、部门名、角色组名称、管理员标识
     *
     * @param rdmsCustomerUserDtos 员工DTO列表
     */
    private void addCustomerUserAuxInfo(List<RdmsCustomerUserDto> rdmsCustomerUserDtos) {
        //对日期时间进行格式化
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(RdmsCustomerUserDto customerUserDto : rdmsCustomerUserDtos){
            customerUserDto.setCreateTimeStr(sdf.format(customerUserDto.getCreateTime()));
            if(!ObjectUtils.isEmpty(customerUserDto.getJoinTime())){
                customerUserDto.setJoinTimeStr(sdf.format(customerUserDto.getJoinTime()));
            }

            if(!ObjectUtils.isEmpty(customerUserDto.getDepartmentId())){
                RdmsDepartment department = rdmsDepartmentService.selectByPrimaryKey(customerUserDto.getDepartmentId());
                customerUserDto.setDepartmentName(department.getDepartName());
            }

            //添加角色组名称
            String userRoleListStr = "";
            List<RdmsUserRoleUser> userRoles = rdmsUserRoleService.getUserRole(customerUserDto.getId());
            //如果用户没有默认权限组, 则给用户分配默认权限组
            if(userRoles.isEmpty()){
                RdmsUserRoleUser record = new RdmsUserRoleUser();
                record.setId(UuidUtil.getShortUuid());
                record.setUserId(customerUserDto.getId());
                record.setRoleId("00000001");
                rdmsUserRoleUserMapper.insert(record);
                userRoles = rdmsUserRoleService.getUserRole(customerUserDto.getId());
            }
            for(RdmsUserRoleUser roleUser : userRoles){
                RdmsUserRole userRoleByPrimaryKey = rdmsUserRoleService.getUserRoleById(roleUser.getRoleId());
                if(userRoleListStr.isEmpty()){
                    userRoleListStr = userRoleByPrimaryKey.getName();
                }else{
                    userRoleListStr = userRoleListStr + ", " + userRoleByPrimaryKey.getName();
                }
            }
            customerUserDto.setUserRoleListStr(userRoleListStr);

            RdmsCustomerUser rdmsCustomerUser = this.selectByPrimaryKey(customerUserDto.getId());
            RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(rdmsCustomerUser.getCustomerId());
            if(rdmsCustomer.getContactPhone().equals(rdmsCustomerUser.getLoginName())){
                customerUserDto.setIsAdmin(true);
            }else{
                customerUserDto.setIsAdmin(false);
            }
        }
    }

    @Transactional
    public void searchByKeyWord(PageDto<RdmsCustomerUserDto> pageDto) {
        /**
         * 关键字搜索公司员工
         * 按姓名模糊匹配并分页返回
         *
         * @param pageDto 分页与搜索参数（需包含customerId与keyWord）
         */
        if(!ObjectUtils.isEmpty(pageDto.getCustomerId())){
            PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
            RdmsCustomerUserExample customerUserExample = new RdmsCustomerUserExample();
            customerUserExample.setOrderByClause("create_time desc");
            customerUserExample.createCriteria()
                    .andCustomerIdEqualTo(pageDto.getCustomerId())
                    .andTrueNameLike("%"+ pageDto.getKeyWord() +"%")
                    .andDeletedEqualTo(0);
            List<RdmsCustomerUser> customerUsers = customerUserMapper.selectByExample(customerUserExample);
            PageInfo<RdmsCustomerUser> pageInfo = new PageInfo<>(customerUsers);
            pageDto.setTotal(pageInfo.getTotal());

            List<RdmsCustomerUserDto> customerUserDtos = CopyUtil.copyList(customerUsers, RdmsCustomerUserDto.class);
            this.addCustomerUserAuxInfo(customerUserDtos);
            pageDto.setList(customerUserDtos);

        }
    }

    @Transactional
    public void listAllWorkers(PageDto<RdmsCustomerUser> pageDto){
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsCustomerUserExample customerUserExample = new RdmsCustomerUserExample();
        RdmsCustomerUserExample.Criteria criteria = customerUserExample.createCriteria();
        criteria.andDeletedEqualTo(0).andCustomerIdEqualTo(pageDto.getCustomerId());
        List<RdmsCustomerUser> rdmsCustomerUsers = customerUserMapper.selectByExample(customerUserExample);
        PageInfo<RdmsCustomerUser> pageInfo = new PageInfo<>(rdmsCustomerUsers);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(rdmsCustomerUsers);
    }

    @Transactional
    public void listUnderlineWorkers(PageDto<RdmsCustomerUser> pageDto){
        /**
         * 分页查询公司员工（含线下用户）
         *
         * @param pageDto 分页参数，需包含客户ID
         */
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsCustomerUserExample customerUserExample = new RdmsCustomerUserExample();
        RdmsCustomerUserExample.Criteria criteria = customerUserExample.createCriteria()
                .andDeletedEqualTo(0)
                .andCustomerIdEqualTo(pageDto.getCustomerId());
        List<RdmsCustomerUser> rdmsCustomerUsers = customerUserMapper.selectByExample(customerUserExample);
        PageInfo<RdmsCustomerUser> pageInfo = new PageInfo<>(rdmsCustomerUsers);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(rdmsCustomerUsers);
    }

    /**
     * 获取用户的工作符合数据
     * @param customerUserId
     * @return
     * @throws ParseException
     */
    public @NotNull List<RdmsHmiWorkLoadDto> getCustomerUserWorkLoadData(String customerUserId, RdmsJobItemDto currentJobItem) throws ParseException {
        List<JobItemStatusEnum> statusEnumList = new ArrayList<>();
        statusEnumList.add(JobItemStatusEnum.HANDLING);
        List<RdmsJobItemDto> jobitemList_origin = rdmsJobItemService.getJobitemListByExecutorId(customerUserId, statusEnumList);
        List<RdmsJobItemDto> jobitemList = jobitemList_origin.stream().filter(item -> !ObjectUtils.isEmpty(item.getManhour())).collect(Collectors.toList());
        if(!ObjectUtils.isEmpty(currentJobItem)){
            {
                //将工作人员工时,换算为标准工时
                Double standManhour = rdmsManhourService.transformToStandManhour(currentJobItem.getManhour(), currentJobItem.getExecutorId(), currentJobItem.getCustomerId(), OperateTypeEnum.DEVELOP);
//                currentJobItem.setStandManhour(standManhour);
                currentJobItem.setManhour(standManhour);
                BigDecimal standManhourFee = rdmsManhourService.getStandManhourFee(currentJobItem.getCustomerId(), OperateTypeEnum.DEVELOP);
                currentJobItem.setStandManhourFee(standManhourFee);  //标准工时
                currentJobItem.setCreateTime(new Date());
            }
            jobitemList.add(currentJobItem);  //将新的任务工时计入工作负荷计算中进行验证
        }
        List<RdmsJobItemDto> jobitemSortedList1 = jobitemList.stream().sorted(Comparator.comparing(RdmsJobItemDto::getCreateTime)).collect(Collectors.toList());
        Date jobStartTime = new Date();
        Date jobEndTime = new Date();
        if (!CollectionUtils.isEmpty(jobitemSortedList1)) {
            jobStartTime = jobitemSortedList1.get(0).getCreateTime();
        }

        List<RdmsJobItemDto> jobitemSortedList2 = jobitemList.stream().sorted(Comparator.comparing(RdmsJobItemDto::getPlanSubmissionTime).reversed()).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(jobitemSortedList2)) {
            jobEndTime = jobitemSortedList2.get(0).getPlanSubmissionTime();
        }

        //计算所有工单跨越的工作日数量
        //工时  计划区间  -> 得到区间所有工作日工时总和 -> 计算每小时负荷 -> 得到每天负荷
        String onDuty = "2023-11-27 09:00:00";
        String offDuty = "2023-11-27 17:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date onDutyTime = sdf.parse(onDuty);
        Date offDutyTime = sdf.parse(offDuty);

        CalManhourService.RspWorkHour rspWorkHour = calManhourService.calWorkHoursInLaw(jobStartTime, jobEndTime, onDutyTime, offDutyTime);
        //为每个工单进行工作量均布
        List<RdmsHmiWorkLoadDto> workLoadList = new ArrayList<>();
        for (RdmsJobItemDto jobItemDto : jobitemList) {
            //计算每个人的工时负荷是,应当转换成相应人员的工时进行 时间分布
            Double workerManhour = rdmsManhourService.transformToWorkerManhour(jobItemDto.getManhour(), jobItemDto.getExecutorId(), jobItemDto.getCustomerId(), OperateTypeEnum.DEVELOP);
            jobItemDto.setManhour(workerManhour);
            List<RdmsHmiWorkLoadCellDto> oneJobitemWorkloadList = calManhourService.getOneJobitemWorkloadList(jobItemDto, onDutyTime, offDutyTime);
            if (!CollectionUtils.isEmpty(oneJobitemWorkloadList)) {
                for (RdmsHmiWorkLoadCellDto cellDto : oneJobitemWorkloadList) {
                    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = f.parse(cellDto.getDateStr());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    cellDto.setDayStamp(date.getTime());

                    boolean flag = false;
                    //查看workLoadList中是否已经有某个日期的数据, 如果已经有了, 就将load数据相加
                    for (RdmsHmiWorkLoadDto workLoadDto : workLoadList) {
                        SimpleDateFormat wf = new SimpleDateFormat("yyyy-MM-dd");
                        Date wDate = wf.parse(workLoadDto.getDateStr());
                        Calendar wCalendar = Calendar.getInstance();
                        wCalendar.setTime(wDate);
                        int cellDay = calendar.get(Calendar.DAY_OF_YEAR);
                        int workDay = wCalendar.get(Calendar.DAY_OF_YEAR);
                        if (cellDay == workDay) {
                            workLoadDto.setDayLoad(workLoadDto.getDayLoad() + cellDto.getLoadCell());
                            flag = true;
                            break;
                        }
                    }
                    //如果没有相应日期的数据, 就创建一个新的数据放到workLoadList中去
                    if (!flag) {
                        RdmsHmiWorkLoadDto workLoadDto = new RdmsHmiWorkLoadDto();
                        workLoadDto.setWorkerId(cellDto.getWorkerId());
                        workLoadDto.setDateStr(cellDto.getDateStr().substring(0, 10));
                        workLoadDto.setDayLoad(cellDto.getLoadCell());
                        workLoadDto.setDayStamp(cellDto.getDayStamp());
                        workLoadList.add(workLoadDto);
                    }
                }
            }
        }

        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(6);
        nf.setRoundingMode(RoundingMode.HALF_UP);
        //计算平均工作负荷
        if (!CollectionUtils.isEmpty(workLoadList)) {
            Double sumLoad = workLoadList.stream().map(RdmsHmiWorkLoadDto::getDayLoad).reduce(0.0, Double::sum);
            double averageLoad = sumLoad / rspWorkHour.dayHourList.size();
            String format = nf.format(averageLoad);
            workLoadList.forEach(item -> item.setAverageDayLoad(Double.parseDouble(format)));
            workLoadList.forEach(item -> item.setDayLoad(Double.parseDouble(String.format("%.2f", item.getDayLoad()))));
        }

        //向前计算一周的平均工作负荷
        if (!CollectionUtils.isEmpty(workLoadList)) {
            //判断有工作内容的最远日期是否超过一周
            List<RdmsHmiWorkLoadDto> workLoadDtos = workLoadList.stream()
                    .sorted(Comparator.comparing(RdmsHmiWorkLoadDto::getDayStamp).reversed())
                    .collect(Collectors.toList());
            String dateStr = workLoadDtos.get(0).getDateStr();
            dateStr += " 17:00:00";
            Date lastDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
            long lastTime = lastDate.getTime();

            //当前时间
            Date current = new Date();

            Calendar referenceCalendar = Calendar.getInstance();
            referenceCalendar.setTime(new Date());
            referenceCalendar.add(Calendar.DATE, 7);
            Date reference = referenceCalendar.getTime();
            long referenceTime = reference.getTime();

            if (lastTime > referenceTime) {
                //超过一周
                List<RdmsHmiWorkLoadDto> inWeekWorkLoadList = workLoadList.stream()
                        .filter(item -> item.getDayStamp() < referenceTime && item.getDayStamp() > current.getTime())
                        .collect(Collectors.toList());
                Double inWeekSumLoad = inWeekWorkLoadList.stream().map(RdmsHmiWorkLoadDto::getDayLoad).reduce(0.0, Double::sum);
                double averageLoad = inWeekSumLoad / 7.0;
                String format = nf.format(averageLoad);
                for (RdmsHmiWorkLoadDto workLoadDto : workLoadList) {
                    workLoadDto.setAverageDayLoadOfWeek(Double.parseDouble(format));
                }
            } else {
                //不超过一周
                List<RdmsHmiWorkLoadDto> inWeekWorkLoadList = workLoadList.stream()
                        .filter(item -> item.getDayStamp() > current.getTime())
                        .collect(Collectors.toList());
                Double inWeekSumLoad = inWeekWorkLoadList.stream().map(RdmsHmiWorkLoadDto::getDayLoad).reduce(0.0, Double::sum);
                double averageLoad = inWeekSumLoad / 7.0;
                String format = nf.format(averageLoad);
                for (RdmsHmiWorkLoadDto workLoadDto : workLoadList) {
                    workLoadDto.setAverageDayLoadOfWeek(Double.parseDouble(format));
                }
            }
        }

        List<RdmsHmiWorkLoadDto> collectWorkLoadList = workLoadList.stream().sorted(Comparator.comparing(RdmsHmiWorkLoadDto::getDayStamp)).collect(Collectors.toList());
        return collectWorkLoadList;
    }

    /**
     * 列表查询公司所有员工
     * @Param customerId
     * @return
     */
    @Transactional
    public List<RdmsCustomerUser> listAllWorkers(String customerId){
        RdmsCustomerUserExample customerUserExample = new RdmsCustomerUserExample();
        RdmsCustomerUserExample.Criteria criteria = customerUserExample.createCriteria();
        criteria.andDeletedEqualTo(0)
                .andCustomerIdEqualTo(customerId);
        return customerUserMapper.selectByExample(customerUserExample);
    }

    @Transactional
    public List<RdmsCustomerUser> listAllNormalWorkers(String customerId){
        /**
         * 查询客户下所有状态正常的员工
         *
         * @param customerId 客户ID
         * @return 员工列表
         */
        RdmsCustomerUserExample customerUserExample = new RdmsCustomerUserExample();
        RdmsCustomerUserExample.Criteria criteria = customerUserExample.createCriteria();
        criteria.andDeletedEqualTo(0)
                .andStatusEqualTo(WorkerStatusEnum.WORKER_NORMAL.getStatus())
                .andCustomerIdEqualTo(customerId);
        return customerUserMapper.selectByExample(customerUserExample);
    }

    @Transactional
    public List<RdmsCustomerUser> listAllWorkersByPreProjectId(String preProjectId){
        /**
         * 根据预立项ID查询其客户下的所有员工
         *
         * @param preProjectId 预立项ID
         * @return 员工列表
         */
        RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(preProjectId);
        RdmsCustomerUserExample customerUserExample = new RdmsCustomerUserExample();
        RdmsCustomerUserExample.Criteria criteria = customerUserExample.createCriteria();
        criteria.andDeletedEqualTo(0).andCustomerIdEqualTo(rdmsPreProject.getCustomerId());
        return customerUserMapper.selectByExample(customerUserExample);
    }

    @Transactional
    public List<RdmsCustomerUser> listAllWorkersByJobitemId(String jobitemId){
        /**
         * 根据工单ID查询其客户下的所有员工
         *
         * @param jobitemId 工单ID
         * @return 员工列表
         */
        RdmsJobItem jobItem = rdmsJobItemService.selectByPrimaryKey(jobitemId);
        RdmsCustomerUserExample customerUserExample = new RdmsCustomerUserExample();
        RdmsCustomerUserExample.Criteria criteria = customerUserExample.createCriteria();
        criteria.andDeletedEqualTo(0).andCustomerIdEqualTo(jobItem.getCustomerId());
        return customerUserMapper.selectByExample(customerUserExample);
    }

    @Transactional
    public List<RdmsCustomerUser> listAllWorkersByCharacterId(String characterId){
        /**
         * 根据功能ID查询其客户下的所有员工
         *
         * @param characterId 功能ID
         * @return 员工列表
         */
        RdmsCharacter character = rdmsCharacterService.selectByPrimaryKey(characterId);
        RdmsCustomerUserExample customerUserExample = new RdmsCustomerUserExample();
        RdmsCustomerUserExample.Criteria criteria = customerUserExample.createCriteria();
        criteria.andDeletedEqualTo(0).andCustomerIdEqualTo(character.getCustomerId());
        return customerUserMapper.selectByExample(customerUserExample);
    }

    @Transactional
    public List<RdmsCustomerUser> listAllWorkersByProjectId(String projectId){
        /**
         * 根据项目ID查询其客户下的所有员工
         *
         * @param projectId 项目ID
         * @return 员工列表
         */
        RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(projectId);
        RdmsCustomerUserExample customerUserExample = new RdmsCustomerUserExample();
        RdmsCustomerUserExample.Criteria criteria = customerUserExample.createCriteria();
        criteria.andDeletedEqualTo(0).andCustomerIdEqualTo(rdmsProject.getCustomerId());
        return customerUserMapper.selectByExample(customerUserExample);
    }

    @Transactional
    public List<RdmsCustomerUser> listAllWorkersBySubProjectId(String subprojectId){
        /**
         * 根据子项目ID查询其所属项目的所有员工
         *
         * @param subprojectId 子项目ID
         * @return 员工列表，若未找到项目则返回null
         */
        RdmsProject project = rdmsProjectService.getProjectBySubprojectId(subprojectId);
        if(!ObjectUtils.isEmpty(project)){
            return this.listAllWorkersByProjectId(project.getId());
        }
        return null;
    }

    /**
     * 删除操作----设置删除标志位
     */
    @Transactional
    public void deleteByWorkerId(String workerId) {
        RdmsCustomerUser rdmsCustomerUser = this.selectByPrimaryKey(workerId);
        if(!ObjectUtils.isEmpty(rdmsCustomerUser)){
            rdmsCustomerUser.setDeleted(1);
            this.update(rdmsCustomerUser);
        }
    }

    public List<RdmsCustomerUser> findCustomerUserByLoginName(String loginName){
        /**
         * 根据登录名查询任职记录
         *
         * @param loginName 登录名
         * @return 任职记录列表，可能为null
         */
        RdmsCustomerUserExample customerUserExample = new RdmsCustomerUserExample();
        customerUserExample.createCriteria().andLoginNameEqualTo(loginName);
        List<RdmsCustomerUser> customerUserList = customerUserMapper.selectByExample(customerUserExample);
        if(CollectionUtils.isEmpty(customerUserList)){
            return null;
        }else{
            return customerUserList;
        }
    }

    public List<RdmsCustomerUser> findCustomerUserByCustomerIdAndLoginName(String customerId, String loginName){
        /**
         * 根据客户ID与登录名查询任职记录
         *
         * @param customerId 客户ID
         * @param loginName 登录名
         * @return 任职记录列表，可能为null
         */
        RdmsCustomerUserExample customerUserExample = new RdmsCustomerUserExample();
        customerUserExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andLoginNameEqualTo(loginName);
        List<RdmsCustomerUser> customerUserList = customerUserMapper.selectByExample(customerUserExample);
        if(CollectionUtils.isEmpty(customerUserList)){
            return null;
        }else{
            return customerUserList;
        }
    }

    public RdmsCustomerUser getCustomerUserInfo(String customerUserId){
        /**
         * 根据任职ID获取任职信息
         *
         * @param customerUserId 任职ID
         * @return 任职信息
         */
        RdmsCustomerUser customerUser = customerUserMapper.selectByPrimaryKey(customerUserId);
        return customerUser;

    }


}
