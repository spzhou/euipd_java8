/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.*;
import com.course.server.dto.LoginUserDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResourceDto;
import com.course.server.dto.rdms.*;
import com.course.server.enums.rdms.RoleEnum;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.*;
import com.course.server.mapper.rdms.MyRdmsUserMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.course.server.constants.Constants.KEY;
import static com.course.server.constants.Constants.SUPER_CUSTOMER_ID;

@Service
public class RdmsUserService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsUserService.class);

    @Resource
    private RdmsUserMapper rdmsUserMapper;
    @Resource
    private RdmsCustomerUserMapper rdmsCustomerUserMapper;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private MyRdmsUserMapper myRdmsUserMapper;
    @Resource
    private RdmsUserRoleUserMapper rdmsUserRoleUserMapper;
    @Resource
    private RdmsUserRoleResourceMapper rdmsUserRoleResourceMapper;
    @Resource
    private RdmsCustomerResourceMapper rdmsCustomerResourceMapper;
    @Resource
    private RdmsCustomerService rdmsCustomerService;
    @Resource
    private RdmsIpmtService rdmsIpmtService;
    @Resource
    private RdmsProductManagerService rdmsProductManagerService;
    @Resource
    private RdmsProjectManagerService rdmsProjectManagerService;
    @Resource
    private RdmsSubprojectService rdmsSubprojectService;
    @Autowired
    private RdmsBossService rdmsBossService;
    @Resource
    private RdmsPdgmService rdmsPdgmService;
    @Resource
    private RdmsSysgmService rdmsSysgmService;
    @Resource
    private RdmsQcgmService rdmsQcgmService;
    @Resource
    private RdmsSystemManagerService rdmsSystemManagerService;
    @Resource
    private RdmsFggmService rdmsFggmService;;
    @Autowired
    private RdmsTgmService rdmsTgmService;

    @Value("${spring.system.uuid}")
    String systemUuid;
    @Value("${spring.system.host}")
    String host;
    @Value("${spring.system.port}")
    Integer port;
    @Value("${spring.system.username}")
    String username;
    @Value("${spring.system.password}")
    String password;
    @Value("${spring.system.isvalid}")
    String isValid;   /*0 不进行Uuid验证; 1 进行Uuid验证*/



    public String updateByPrimaryKeySelective(RdmsUser user) {
        rdmsUserMapper.updateByPrimaryKeySelective(user);
        return user.getId();
    }

    public RdmsUser selectByToken(String token) {
        RdmsUserExample userExample = new RdmsUserExample();
        userExample.createCriteria().andLoginTokenEqualTo(token);
        List<RdmsUser> rdmsUserList = rdmsUserMapper.selectByExample(userExample);
        if (CollectionUtils.isEmpty(rdmsUserList)) {
            return null;
        } else {
            return rdmsUserList.get(0);
        }
    }

    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsUserExample userExample = new RdmsUserExample();
        List<RdmsUser> userList = rdmsUserMapper.selectByExample(userExample);
        //去掉有删除标记的记录
        List<RdmsUser> userListTemp = new ArrayList<>();
        for(RdmsUser rdmsUser : userList){
            if(rdmsUser.getDeleted() != 1){
                userListTemp.add(rdmsUser);
            }
        }

        PageInfo<RdmsUser> pageInfo = new PageInfo<>(userList);
        pageDto.setTotal(pageInfo.getTotal());
        List<RdmsUserDto> userDtoList = CopyUtil.copyList(userListTemp, RdmsUserDto.class);
        //对日期时间进行格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(RdmsUserDto userDto : userDtoList){
            userDto.setCreateTimeStr(sdf.format(userDto.getCreateTime()));
        }

        pageDto.setList(userDtoList);
    }

    /**
     * 根据keyWord查询用户
     * @param pageDto
     */
    public void searchList(PageDto<RdmsUserDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        List<RdmsUser> userList = myRdmsUserMapper.findUsers(pageDto.getKeyWord());
        //去掉有删除标记的记录
        List<RdmsUser> userListTemp = new ArrayList<>();
        for(RdmsUser rdmsUser : userList){
            if(rdmsUser.getDeleted() != 1){
                userListTemp.add(rdmsUser);
            }
        }

        PageInfo<RdmsUser> pageInfo = new PageInfo<>(userList);
        pageDto.setTotal(pageInfo.getTotal());
        List<RdmsUserDto> userDtoList = CopyUtil.copyList(userListTemp, RdmsUserDto.class);
        //对日期时间进行格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(RdmsUserDto userDto : userDtoList){
            userDto.setCreateTimeStr(sdf.format(userDto.getCreateTime()));
        }

        pageDto.setList(userDtoList);
    }

/**
     * 根据keyWord查询用户
     * @param pageDto
     */
    public void searchUserByLoginName(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsUserExample rdmsUserExample = new RdmsUserExample();
        RdmsUserExample.Criteria criteria = rdmsUserExample.createCriteria();
        criteria.andLoginNameEqualTo(pageDto.getKeyWord())
                .andDeletedNotEqualTo(1);
        List<RdmsUser> rdmsUsers = rdmsUserMapper.selectByExample(rdmsUserExample);

        PageInfo<RdmsUser> pageInfo = new PageInfo<>(rdmsUsers);
        pageDto.setTotal(pageInfo.getTotal());
        List<RdmsUserDto> userDtoList = CopyUtil.copyList(rdmsUsers, RdmsUserDto.class);
        //对日期时间进行格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(RdmsUserDto userDto : userDtoList){
            userDto.setCreateTimeStr(sdf.format(userDto.getCreateTime()));
        }

        pageDto.setList(userDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public String save(RdmsUserDto userDto) {
        RdmsUser user = CopyUtil.copy(userDto, RdmsUser.class);
        RdmsUser userDb = this.selectByLoginName(user.getLoginName());
        if(userDb != null){
            user.setId(userDb.getId());
            user.setPassword(userDb.getPassword());
            return this.update(user);
        }else{
            return this.insert(user);
        }
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public String modify(RdmsUserDto userDto) {
        RdmsUser user = CopyUtil.copy(userDto, RdmsUser.class);
        RdmsUser userDb = this.selectByPrimaryKey(user.getId());
        if(userDb != null){
            return this.update(user);
        }else{
            throw new BusinessException(BusinessExceptionCode.MEMBER_NOT_EXIST);
        }
    }

    /**
     * 新增
     */
    private String insert(RdmsUser user) {
        if(org.springframework.util.ObjectUtils.isEmpty(user.getId())){  //当前端页面给出projectID时,将不为空
            user.setId(UuidUtil.getShortUuid());
        }
        user.setCreateTime(new Date());
        user.setDeleted(0);
        String tempPass = user.getId()+KEY;  //原始密码加盐
        user.setPassword((DigestUtils.md5DigestAsHex(tempPass.getBytes()))); //第一次加盐加密
        user.setPassword((DigestUtils.md5DigestAsHex(user.getPassword().getBytes())));  //第二次加密

        RdmsUser userDb = this.selectByLoginName(user.getLoginName());
        if (userDb != null) {
            throw new BusinessException(BusinessExceptionCode.CUSTOMER_LOGIN_NAME_EXIST);
        }
        rdmsUserMapper.insert(user);
        return user.getId();
    }

    /**
     * 更新
     */
    private String update(RdmsUser user) {
        if(user.getPassword() == null){
            //如果客户密码为空,则将客户的ID作为默认密码;
            String tempPass = user.getId()+KEY;  //原始密码加盐
            user.setPassword((DigestUtils.md5DigestAsHex(tempPass.getBytes()))); //第一次加盐加密
            user.setPassword((DigestUtils.md5DigestAsHex(user.getPassword().getBytes())));  //第二次加密
        }
        RdmsUserExample userExample = new RdmsUserExample();
        RdmsUserExample.Criteria criteria = userExample.createCriteria();
        criteria.andIdEqualTo(user.getId());
        rdmsUserMapper.updateByExampleSelective(user,userExample);
        return user.getId();
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {

        RdmsUser user = this.selectByPrimaryKey(id);
        if(user != null){
            user.setDeleted(1);  //设置删除标志位
            this.update(user);
        }
    }

    /**
     * 根据登录名查询用户信息
     * @param loginName
     * @return
     */
    public RdmsUser selectByLoginName(String loginName) {
        RdmsUserExample userExample = new RdmsUserExample();
        userExample.createCriteria().andLoginNameEqualTo(loginName);
        List<RdmsUser> userList = rdmsUserMapper.selectByExample(userExample);
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        } else {
            return userList.get(0);
        }
    }

    public RdmsUser selectByUnionId(String unionid) {
        RdmsUserExample userExample = new RdmsUserExample();
        userExample.createCriteria().andUnionidEqualTo(unionid);
        List<RdmsUser> userList = rdmsUserMapper.selectByExample(userExample);
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        } else {
            return userList.get(0);
        }
    }

    /**
     * 根据ID查询用户信息
     * @param id
     * @return
     */
     public RdmsUser selectByPrimaryKey(String id) {
        RdmsUserExample userExample = new RdmsUserExample();
        userExample.createCriteria().andIdEqualTo(id);
        List<RdmsUser> userList = rdmsUserMapper.selectByExample(userExample);
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        } else {
            return userList.get(0);
        }
    }


    /**
     * 重置密码
     * @param userDto
     */
    public void savePassword(RdmsUserDto userDto) {
        RdmsUser user = new RdmsUser();
        user.setId(userDto.getId());
        user.setPassword(userDto.getPassword());

        RdmsUserExample userExample = new RdmsUserExample();
        RdmsUserExample.Criteria criteria = userExample.createCriteria();
        criteria.andIdEqualTo(userDto.getId());
        rdmsUserMapper.updateByExampleSelective(user,userExample);
    }

    /**
     * 登录
     * @param userDto
     */
    public LoginUserDto login(RdmsUserDto userDto) {
        RdmsUser user = this.selectByLoginName(userDto.getLoginName());
        if(isValid.equals("1")){  //判断是否开启系统Uuid校验
            if(!this.getSystemUuidRight()){
                throw new BusinessException(BusinessExceptionCode.SYSTEM_NOT_AUTHORIZED);
            }
        }

        //判断账号是否过期
        long currentTime = new Date().getTime();

        RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(userDto.getSelectedCustomerId());
        if(!ObjectUtils.isEmpty(rdmsCustomer) && !ObjectUtils.isEmpty(rdmsCustomer.getExpirationTime()) && !rdmsCustomer.getId().equals(SUPER_CUSTOMER_ID)){
            long overdueTime = rdmsCustomer.getExpirationTime().getTime();
            if(currentTime > overdueTime){
                throw new BusinessException(BusinessExceptionCode.SELECTED_CUSTOMER_OVERDUE_ERROR);
            }
        }

        if (ObjectUtils.isEmpty(user)) {
//            LOG.info("用户名不存在, {}", userDto.getLoginName());
            throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
        } else {
            if (user.getPassword().equals(userDto.getPassword())) {
                // 登录成功
                LoginUserDto loginUserDto = CopyUtil.copy(user, LoginUserDto.class);
                loginUserDto.setName(user.getTrueName());
                // 为登录用户读取权限
                this.setAuth(loginUserDto, userDto.getSelectedCustomerId());
                // 获取user对应的customerUserId
                RdmsCustomerUser customerUser = rdmsCustomerUserService.getCustomerUser(user.getId(), userDto.getSelectedCustomerId());
                loginUserDto.setCustomerUserId(customerUser.getId());
                loginUserDto.setProfile(customerUser.getProfile());
                return loginUserDto;
            } else {
//                LOG.info("密码不对, 输入密码：{}, 数据库密码：{}", userDto.getPassword(), user.getPassword());
                throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
            }
        }
    }

    /**
     * 为登录用户读取权限
     */
    private void setAuth(@NotNull LoginUserDto loginUserDto, String selectedCustomerId) {
        //List<ResourceDto> resourceDtoList = myRdmsUserMapper.findResources(loginUserDto.getId());

        //查看这个user在哪几个公司中, 需要根据这个用户在各个公司中的权限进行登录授权
        RdmsCustomerUserExample customerUserExample = new RdmsCustomerUserExample();
        customerUserExample.createCriteria().andLoginNameEqualTo(loginUserDto.getLoginName()).andCustomerIdEqualTo(selectedCustomerId);
        List<RdmsCustomerUser> customerUserList = rdmsCustomerUserMapper.selectByExample(customerUserExample);

        RdmsCustomerUser customerUser = customerUserList.get(0);
        String customerId = customerUser.getCustomerId();
        String userId = customerUser.getId(); //加入到某公司后的 customerUserId

        //1. 通过user的id，查user_role表，得到userRoleIdList
        RdmsUserRoleUserExample roleUserExample = new RdmsUserRoleUserExample();
        RdmsUserRoleUserExample.Criteria criteria = roleUserExample.createCriteria();
        criteria.andUserIdEqualTo(userId).andCustomerIdEqualTo(customerId);
        List<RdmsUserRoleUser> roleUserList = rdmsUserRoleUserMapper.selectByExample(roleUserExample);

        //2. 通过userRoleIdList，查role_resource表，得到userResourceIdlist
        List<RdmsUserRoleResource> roleResourceList = new ArrayList<>();
        for(RdmsUserRoleUser roleUser: roleUserList){
            RdmsUserRoleResourceExample roleResourceExample = new RdmsUserRoleResourceExample();
            RdmsUserRoleResourceExample.Criteria resCriteria = roleResourceExample.createCriteria();
            resCriteria.andRoleIdEqualTo(roleUser.getRoleId()).andCustomerIdEqualTo(customerId);
            List<RdmsUserRoleResource> roleResources = rdmsUserRoleResourceMapper.selectByExample(roleResourceExample);
            for(RdmsUserRoleResource roleResource: roleResources){
                roleResourceList.add(roleResource);
            }
        }
        List<RdmsUserRoleResource> roleResourceList1 = roleResourceList.stream().distinct().collect(Collectors.toList());

        //3. 通过userResourceIdlist，查resource表，得到用户的resourceList
        List<RdmsCustomerResource> resourceList = new ArrayList<>();
        for(RdmsUserRoleResource roleResource: roleResourceList1){
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
     * 按登录名查找
     */
    public LoginUserDto findByLoginName(String loginName) {
        RdmsUser user = this.selectByLoginName(loginName);
        return CopyUtil.copy(user, LoginUserDto.class);
    }

    /**
     * 通过短信验证重置密码
     */
    public void resetPassword(RdmsUserDto userDto) throws BusinessException {
        RdmsUser userDb = this.selectByLoginName(userDto.getLoginName());
        if (userDb == null) {
            throw new BusinessException(BusinessExceptionCode.MEMBER_NOT_EXIST);
        } else {
            RdmsUser user = new RdmsUser();
            user.setPassword(userDto.getPassword());
            RdmsUserExample userExample = new RdmsUserExample();
            RdmsUserExample.Criteria criteria = userExample.createCriteria();
            criteria.andLoginNameEqualTo(userDto.getLoginName());
            rdmsUserMapper.updateByExampleSelective(user,userExample);
        }
    }

    /**
     * 根据用户的loginName搜索用户信息列表
     * @param loginName
     * @return
     */
    public List<RdmsUser> searchUserByloginName(String loginName){
        List<RdmsUser> usersFromLoginNames = myRdmsUserMapper.findUsersFromLoginName(loginName);
        return usersFromLoginNames;
    }

    /**
     * 根据用户的trueName搜索用户信息列表
     * @param trueName
     * @return
     */
    public List<RdmsUser> searchUserByTrueName(String trueName){
        List<RdmsUser> usersFromLoginNames = myRdmsUserMapper.findUsersFromTrueName(trueName);
        return usersFromLoginNames;
    }

    /**
     * 根据keyWord搜索用户信息列表
     * @param keyWord
     * @return
     */
     public List<RdmsUser> searchUser(String keyWord){
        List<RdmsUser> usersFromLoginNames = myRdmsUserMapper.findUsers(keyWord);
        return usersFromLoginNames;
    }

    /**
     * 列表查询
     */
    public void listWorker(RdmsResponseDto respDto) {
        RdmsCustomerUserExample rdmsCustomerUserExample = new RdmsCustomerUserExample();
        RdmsCustomerUserExample.Criteria criteria = rdmsCustomerUserExample.createCriteria();
        criteria.andCustomerIdEqualTo(respDto.getCustomerId());
        List<RdmsCustomerUser> rdmsCustomerUsers = rdmsCustomerUserMapper.selectByExample(rdmsCustomerUserExample);
        List<String> userIdList = rdmsCustomerUsers.stream().map(RdmsCustomerUser::getUserId).collect(Collectors.toList());
        List<RdmsUser> workerList = new ArrayList<>();
        for(String userId : userIdList){
            RdmsUser rdmsUser = rdmsUserMapper.selectByPrimaryKey(userId);
            if(rdmsUser != null){
                workerList.add(rdmsUser);
            }
        }

        //去掉有删除标记的记录
        List<RdmsUser> workerListTemp = new ArrayList<>();
        for(RdmsUser rdmsUser : workerList){
            if(rdmsUser.getDeleted() != 1){
                workerListTemp.add(rdmsUser);
            }
        }
        List<RdmsUserDto> userDtoList = CopyUtil.copyList(workerListTemp, RdmsUserDto.class);
        //对list内容进行排序
        userDtoList = userDtoList.stream().sorted(Comparator.comparing(RdmsUserDto::getTrueName)).collect(Collectors.toList());
        respDto.setList(userDtoList);
    }

    public @NotNull List<String> getUserRole(String customerId, String customerUserId) {
        //设置用户角色
        List<String> roleList = new ArrayList<>();
        //普通员工
        roleList.add(RoleEnum.STAFF.getRole());
        //Admin
        RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(customerUserId);
        RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(customerId);
        if(rdmsCustomer.getContactPhone().equals(rdmsCustomerUser.getLoginName())){
            roleList.add(RoleEnum.ADMIN.getRole());
        }
        //IPMT
        List<String> ipmtIdList = rdmsIpmtService.listByCustomerId(customerId);
        if (!CollectionUtils.isEmpty(ipmtIdList) && ipmtIdList.contains(customerUserId)) {
            roleList.add(RoleEnum.IPMT.getRole());
        }
        //BOSS
        RdmsBossDto bossByCustomerId = rdmsBossService.getBossByCustomerId(customerId);
        if (!ObjectUtils.isEmpty(bossByCustomerId) && customerUserId.equals(bossByCustomerId.getBossId())) {
            roleList.add(RoleEnum.BOSS.getRole());
        }
        //RDGM
        RdmsPdgmDto pdgmByCustomerId = rdmsPdgmService.getPdgmByCustomerId(customerId);
        if (!ObjectUtils.isEmpty(pdgmByCustomerId) && customerUserId.equals(pdgmByCustomerId.getPdgmId())) {
            roleList.add(RoleEnum.PDGM.getRole());
        }
        //SYSGM
        RdmsSysgmDto sysgmByCustomerId = rdmsSysgmService.getSysgmByCustomerId(customerId);
        if (!ObjectUtils.isEmpty(sysgmByCustomerId) && customerUserId.equals(sysgmByCustomerId.getSysgmId())) {
            roleList.add(RoleEnum.SYSGM.getRole());
        }
        //FGGM
        RdmsFggmDto fggmByCustomerId = rdmsFggmService.getFggmByCustomerId(customerId);
        if (!ObjectUtils.isEmpty(fggmByCustomerId) && customerUserId.equals(fggmByCustomerId.getFggmId())) {
            roleList.add(RoleEnum.FGGM.getRole());
        }
        //QCGM
        RdmsQcgmDto qcgmByCustomerId = rdmsQcgmService.getQcgmByCustomerId(customerId);
        if (!ObjectUtils.isEmpty(qcgmByCustomerId) && customerUserId.equals(qcgmByCustomerId.getQcgmId())) {
            roleList.add(RoleEnum.QCGM.getRole());
        }
        //TGM
        RdmsTgmDto tgmByCustomerId = rdmsTgmService.getTgmByCustomerId(customerId);
        if (!ObjectUtils.isEmpty(tgmByCustomerId) && customerUserId.equals(tgmByCustomerId.getTgmId())) {
            roleList.add(RoleEnum.TGM.getRole());
        }
        //产品经理
        List<String> pdmIdList = rdmsProductManagerService.getIdListByCustomerId(customerId);
        if (!CollectionUtils.isEmpty(pdmIdList) && pdmIdList.contains(customerUserId)) {
            roleList.add(RoleEnum.PDM.getRole());
        }
        //项目经理
        List<String> pjmIdList = rdmsProjectManagerService.getIdListByCustomerId(customerId);
        if (!CollectionUtils.isEmpty(pjmIdList) && pjmIdList.contains(customerUserId)) {
            roleList.add(RoleEnum.PJM.getRole());
        }
        //系统工程师
        List<String> smIdList = rdmsSystemManagerService.getIdListByCustomerId(customerId);
        if (!CollectionUtils.isEmpty(smIdList) && smIdList.contains(customerUserId)) {
            roleList.add(RoleEnum.SM.getRole());
        }
        //测试主管
        List<RdmsProjectSubproject> subprojectListByCustomerIdAndTestManagerId = rdmsSubprojectService.getSubprojectListByCustomerIdAndTestManagerId(customerId, customerUserId);
        if(!CollectionUtils.isEmpty(subprojectListByCustomerIdAndTestManagerId)){
            roleList.add(RoleEnum.TM.getRole());
        }
        return roleList;
    }

    private boolean getSystemUuidRight() {
        String command = "sudo dmidecode -s system-uuid"; // 要执行的命令

        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(username, host, port);
            session.setPassword(password);

            // 配置StrictHostKeyChecking为no，避免首次连接时的手动确认
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            com.jcraft.jsch.Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);

            // 获取标准输出流和错误流
            InputStream in = channel.getInputStream();
            ((ChannelExec) channel).setErrStream(System.err);

            channel.connect();

            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) break;
                    String uuid = new String(tmp, 0, i);
                    System.out.print("System-uuid:"+ uuid);
                    //去掉systemUuid前后的空格
                    uuid = uuid.trim();
                    if(systemUuid.equals(uuid)){
                        LOG.info("User has the System login authority!");
                        return true;
                    }else{
                        return false;
                    }
                }
                if (channel.isClosed()) {
                    if (in.available() > 0) continue;
                    LOG.info("Exit status: " + channel.getExitStatus());
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception ee) {
                }
            }

            channel.disconnect();
            session.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
