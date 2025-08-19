/**
 * Author：周朔鹏
 * 编制时间：2025-08-09

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.system.controller.rdms;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.RdmsCustomer;
import com.course.server.domain.RdmsCustomerUser;
import com.course.server.domain.RdmsUser;
import com.course.server.dto.LoginUserDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.*;
import com.course.server.enums.SmsUseEnum;
import com.course.server.enums.rdms.CustomerStatusEnum;
import com.course.server.enums.rdms.LoginUserGroupEnum;
import com.course.server.enums.rdms.WorkerStatusEnum;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.service.rdms.*;
import com.course.server.service.util.IpUtils;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.course.server.constants.Constants.SUPER_ADMIN_LOGIN_NAME;

@RestController
@RequestMapping("/rdms/user")
public class RdmsUserController {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsUserController.class);
    public static final String BUSINESS_NAME = "开发者";

    @Resource
    private RdmsUserService rdmsUserService;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private RdmsSmsService rdmsSmsService;
    @Resource
    private RdmsCustomerService rdmsCustomerService;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private IpUtils ipUtils;

    /**
     * 保存客户信息
     */
    @PostMapping("/save")
    @Transactional
    public ResponseDto save(@RequestBody RdmsUserDto userDto) throws Exception {
        ResponseDto responseDto = new ResponseDto();

        ValidatorUtil.require(userDto.getLoginName(), "登录名称");
        ValidatorUtil.require(userDto.getTrueName(), "真实姓名");
        ValidatorUtil.require(userDto.getDescription(), "能力描述");

        ValidatorUtil.length(userDto.getLoginName(), "登录名称", 11, 11);
        ValidatorUtil.length(userDto.getTrueName(), "真实姓名", 2, 4);

        ValidatorUtil.length(userDto.getDescription(), "能力描述", 10, 500);

        //判断用户是否存在
        RdmsUser rdmsUser = rdmsUserService.selectByLoginName(userDto.getLoginName());
        if(rdmsUser==null){
            userDto.setId(UuidUtil.getShortUuid());
            rdmsUserService.save(userDto);
            return responseDto;
        }else{
            throw new BusinessException(BusinessExceptionCode.CUSTOMER_LOGIN_NAME_EXIST);
        }

    }

    /**
     * 用户信息修改
     */
    @PostMapping("/modify")
    @Transactional
    public ResponseDto modify(@RequestBody RdmsUserDto userDto) throws Exception {
        ResponseDto responseDto = new ResponseDto();
        ValidatorUtil.require(userDto.getLoginName(), "登录名称");
        ValidatorUtil.require(userDto.getTrueName(), "真实姓名");
        ValidatorUtil.require(userDto.getDescription(), "能力描述");

        ValidatorUtil.length(userDto.getLoginName(), "登录名称", 11, 11);
        ValidatorUtil.length(userDto.getTrueName(), "真实姓名", 2, 4);
        ValidatorUtil.length(userDto.getDescription(), "能力描述", 10, 500);

        //判断用户是否存在
        RdmsUser rdmsUser = rdmsUserService.selectByPrimaryKey(userDto.getId());
        if(rdmsUser!=null){
            rdmsUserService.modify(userDto);
            return responseDto;
        }else{
            throw new BusinessException(BusinessExceptionCode.MEMBER_NOT_EXIST);
        }

    }


    /**
     * 根据登录名称进行查询
     */
    @PostMapping("/login-search")
    @Transactional
    public ResponseDto loginSearch(@RequestBody RdmsUserDto userDto) {
        ValidatorUtil.require(userDto.getLoginName(), "登录名称");
        ResponseDto responseDto = new ResponseDto();

        //判断用户是否存在
        List<RdmsUser> users = rdmsUserService.searchUserByloginName(userDto.getLoginName());
        List<RdmsUserDto> userDtos = new ArrayList<>();
        if (users.size()>0) {
            userDtos = CopyUtil.copyList(users, RdmsUserDto.class);
            responseDto.setContent(userDtos);
            return responseDto;
        }
        userDtos.add(userDto);
        responseDto.setContent(userDtos);
        responseDto.setSuccess(false);
        responseDto.setMessage("记录不存在!");
        return responseDto;
    }

    /**
     * 根据公司名称进行查询
     */
    @PostMapping("/name-search")
    @Transactional
    public ResponseDto nameSearch(@RequestBody RdmsUserDto userDto) {
        ValidatorUtil.require(userDto.getTrueName(), "真实姓名");
        ResponseDto responseDto = new ResponseDto();

        //判断用户是否存在
        List<RdmsUser> users = rdmsUserService.searchUserByTrueName(userDto.getTrueName());
        List<RdmsUserDto> userDtos = new ArrayList<>();
        if (users.size()>0) {
            userDtos = CopyUtil.copyList(users, RdmsUserDto.class);
            responseDto.setContent(userDtos);
            return responseDto;
        }
        userDtos.add(userDto);
        responseDto.setContent(userDtos);
        responseDto.setSuccess(false);
        responseDto.setMessage("记录不存在!");
        return responseDto;
    }

    /**
     * 列表查询
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        rdmsUserService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 列表查询
     */
    @PostMapping("/searchList")
    public ResponseDto<PageDto<RdmsUserDto>> searchList(@RequestBody PageDto<RdmsUserDto> pageDto) {
        ResponseDto<PageDto<RdmsUserDto>> responseDto = new ResponseDto<>();
        rdmsUserService.searchList(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

/**
     * 列表查询
     */
    @PostMapping("/searchUserByLoginName/{loginName}")
    public ResponseDto searchUserByLoginName(@PathVariable String loginName) {
        ResponseDto responseDto = new ResponseDto();
        LoginUserDto byLoginName = rdmsUserService.findByLoginName(loginName);
        responseDto.setContent(byLoginName);
        return responseDto;
    }

    /**
     * 删除
     */
    @DeleteMapping("/del/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        rdmsUserService.delete(id);
        return responseDto;
    }

    /**
     * 重置密码
     */
    @PostMapping("/save-password")
    public ResponseDto savePassword(@RequestBody RdmsUserDto userDto) {
        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
        ResponseDto responseDto = new ResponseDto();
        rdmsUserService.savePassword(userDto);
        responseDto.setContent(userDto);
        return responseDto;
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public ResponseDto login(@RequestBody RdmsUserDto userDto, HttpServletRequest request) {
//        LOG.info("用户登录开始");
        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
        ResponseDto responseDto = new ResponseDto();
        LoginUserDto loginUserDto = new LoginUserDto();

        // 根据验证码token去获取缓存中的验证码，和用户输入的验证码是否一致
        // String imageCode = (String) request.getSession().getAttribute(userDto.getImageCodeToken());
        String imageCode = (String) redisTemplate.opsForValue().get(userDto.getImageCodeToken());
//        LOG.info("从redis中获取到的验证码：{}", imageCode);
        if (ObjectUtils.isEmpty(imageCode)) {
            responseDto.setSuccess(false);
            responseDto.setMessage("验证码已过期");
//            LOG.info("用户登录失败，验证码已过期");
            return responseDto;
        }
        //判断用户是否已经离职
        List<RdmsCustomerUser> customerUserByLoginName = rdmsCustomerUserService.findCustomerUserByCustomerIdAndLoginName(userDto.getSelectedCustomerId(), userDto.getLoginName());
        if(!CollectionUtils.isEmpty(customerUserByLoginName)){
            if(customerUserByLoginName.get(0).getStatus().equals(WorkerStatusEnum.WORKER_DISMISS.getStatus())
                    || customerUserByLoginName.get(0).getStatus().equals(WorkerStatusEnum.WORKER_VACATE.getStatus())){
                responseDto.setSuccess(false);
                responseDto.setMessage("已离职,无权限!");
                return responseDto;
            }
        }

        if (!imageCode.toLowerCase().equals(userDto.getImageCode().toLowerCase())) {
            responseDto.setSuccess(false);
            responseDto.setMessage("验证码不对");
//            LOG.info("用户登录失败，验证码不对");
            return responseDto;
        } else {
            // 验证通过后，移除验证码
//            request.getSession().removeAttribute(userDto.getImageCodeToken());
            redisTemplate.delete(userDto.getImageCodeToken());
        }

        //读出旧的token, 如果不为空, 则说明这个用户已经在某个浏览器登录了, 需要将已经登录的用户踢出去
        if(!ObjectUtils.isEmpty(loginUserDto.getLoginToken())){
            String loginUserJSONStr = (String) redisTemplate.opsForValue().get(loginUserDto.getLoginToken());
            if(!ObjectUtils.isEmpty(loginUserJSONStr)){
//                LoginUserDto loginUserDto1 = JSON.parseObject(loginUserJSONStr, LoginUserDto.class);
                redisTemplate.delete(loginUserDto.getLoginToken());
            }
        }

        //判断是否是管理员,却以用户的身份登录
        RdmsCustomer selectedCustomer = rdmsCustomerService.selectByPrimaryKey(userDto.getSelectedCustomerId());
        if(ObjectUtils.isEmpty(selectedCustomer)){
            responseDto.setSuccess(false);
            responseDto.setMessage("没有可登录的授权机构!");
            return responseDto;
        }
        if(selectedCustomer.getContactPhone().equals(userDto.getLoginName())){
            //管理员以用户身份登录, 说明是登录原来的企业管理员账号
            RdmsCustomerDto customerDto = CopyUtil.copy(userDto, RdmsCustomerDto.class);
            loginUserDto = rdmsCustomerService.login(customerDto);
            loginUserDto.setUserGroup(LoginUserGroupEnum.CUSTOMER.getStatus());

            if(!loginUserDto.getContactPhone().equals(SUPER_ADMIN_LOGIN_NAME)){
                //机构用户加载admin用户信息  一个电话号码只对应一个customer的 admin账号
                RdmsCustomerUser customerUser = rdmsCustomerUserService.findCustomerUserByLoginName(loginUserDto.getContactPhone()).get(0); //只会有一个结果
                if(!ObjectUtils.isEmpty(customerUser)){
                    RdmsCustomerUserDto customerUserDto = CopyUtil.copy(customerUser, RdmsCustomerUserDto.class);
                    customerUserDto.setCustomerUserId(customerUser.getId()); //填写customerUserID
                    loginUserDto.setAdminUser(customerUserDto);

                    List<String> roleList = rdmsUserService.getUserRole(customerUser.getCustomerId(), customerUserDto.getId());
                    loginUserDto.setRoleList(roleList);
                }else{
                    loginUserDto.setAdminUser(null);
                }
            }

            //生成新的token
            String token = UuidUtil.getShortUuid();
            //查看token的登录状态,如果已经登录, 则删除相应的token, 然后完成登录___避免多窗口重复登录
            RdmsCustomer rdmsCustomer = new RdmsCustomer();
            rdmsCustomer.setId(loginUserDto.getId());
            rdmsCustomer.setLoginToken(token);
            String ipAddress = ipUtils.getIpAddress(request);
            rdmsCustomer.setLoginIp(ipAddress);
            rdmsCustomerService.updateByPrimaryKeySelective(rdmsCustomer);

            loginUserDto.setToken(token);
//        request.getSession().setAttribute(Constants.LOGIN_USER, loginUserDto);
            redisTemplate.opsForValue().set(token, JSON.toJSONString(loginUserDto), 7200, TimeUnit.SECONDS);
            responseDto.setContent(loginUserDto);

        }else{
            loginUserDto = rdmsUserService.login(userDto);
            loginUserDto.setUserGroup(LoginUserGroupEnum.DEVELOPER.getStatus());
            //开发者用户加载customer信息
            RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(userDto.getSelectedCustomerId());
            loginUserDto.setCustomer(rdmsCustomer);

            //设置用户角色
            RdmsCustomerUser customerUser = rdmsCustomerUserService.getCustomerUserByCustomerIdAndLoginName(rdmsCustomer.getId(), userDto.getLoginName());
            List<String> roleList = rdmsUserService.getUserRole(userDto.getSelectedCustomerId(), customerUser.getId());
            loginUserDto.setRoleList(roleList);

            String token = UuidUtil.getShortUuid();

            //查看token的登录状态,如果已经登录, 则删除相应的token, 然后完成登录___避免多窗口重复登录
            RdmsUser rdmsUser = new RdmsUser();
            rdmsUser.setId(loginUserDto.getId());
            rdmsUser.setLoginToken(token);
            String ipAddress = ipUtils.getIpAddress(request);
            rdmsUser.setLoginIp(ipAddress);
            rdmsUserService.updateByPrimaryKeySelective(rdmsUser);

            loginUserDto.setToken(token);
//        request.getSession().setAttribute(Constants.LOGIN_USER, loginUserDto);
            if(loginUserDto.getLoginName().equals("13866668888")){  //大屏幕登录永不过期
                redisTemplate.opsForValue().set(token, JSON.toJSONString(loginUserDto));
            }else{
                redisTemplate.opsForValue().set(token, JSON.toJSONString(loginUserDto), 7200, TimeUnit.SECONDS);
            }

            responseDto.setContent(loginUserDto);
        }

        return responseDto;
    }

    /**
     * 前端跨项目登录时, 创建临时的token将用户信息存到redis中, 返回临时的token
     * @param userInfo
     * @return
     */
    @PostMapping("/create-temp-token")
    public ResponseDto createTempToken(@RequestBody LoginUserDto userInfo) {
        ResponseDto responseDto = new ResponseDto();
        //生成临时的token
        String tempToken = UuidUtil.getShortUuid();
        //通过临时token将用户信息保存到Redis中
        redisTemplate.opsForValue().set(tempToken, userInfo, 300, TimeUnit.SECONDS);
        responseDto.setContent(tempToken);
        return responseDto;
    }

    /**
     * 前端跨项目登录时, 通过临时token获取用户信息
     * @param tempToken
     * @return
     */
    @PostMapping("/get-userinfo/{tempToken}")
    public ResponseDto getUserInfoByTempToken(@PathVariable String tempToken) {
        ResponseDto responseDto = new ResponseDto();
        LoginUserDto loginUserDto = (LoginUserDto) redisTemplate.opsForValue().get(tempToken);
        if(!ObjectUtils.isEmpty(loginUserDto)){
            redisTemplate.delete(tempToken);
            responseDto.setCode("200");
            responseDto.setContent(loginUserDto);
            return responseDto;
        }else{
            throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
        }
    }

    /**
     * 退出登录
     */
    @GetMapping("/logout/{token}")
    public ResponseDto logout(@PathVariable String token) {
        ResponseDto responseDto = new ResponseDto();
//        request.getSession().removeAttribute(Constants.LOGIN_USER);
        redisTemplate.delete(token);
//        LOG.info("从redis中删除token:{}", token);
        return responseDto;
    }

    /**
     * 校验登录名是否存在
     * 存在则success=true，不存在则success=false
     */
    @GetMapping(value = "/is-mobile-exist/{loginName}")
    public ResponseDto isMobileExist(@PathVariable(value = "loginName") String loginName) throws BusinessException {
//        LOG.info("查询手机号是否存在开始");
        ResponseDto responseDto = new ResponseDto();
        LoginUserDto loginUserDto = rdmsUserService.findByLoginName(loginName);
        LoginUserDto loginCustomerDto = rdmsCustomerService.findByContactPhone(loginName);
        if (loginUserDto == null && loginCustomerDto == null) {
            responseDto.setSuccess(false);
        } else {
            responseDto.setSuccess(true);
        }
        return responseDto;
    }

    @PostMapping("/reset-password")
    public ResponseDto resetPassword(@RequestBody RdmsUserDto userDto) throws BusinessException {
        ResponseDto<RdmsUserDto> responseDto = new ResponseDto();
        // 当用户重置密码时, 如果即是机构管理员,又是开发者,则两种身份的密码都会被重置
        boolean flag = false;
        String password = userDto.getPassword();
        // 1. 如果是开发者身份
        RdmsUser rdmsUser = rdmsUserService.selectByLoginName(userDto.getLoginName());
        // 如果用户存在
        if(rdmsUser != null){
            userDto.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
            // 校验短信验证码
            RdmsSmsDto rdmsSmsDto = new RdmsSmsDto();
            rdmsSmsDto.setMobile(userDto.getLoginName());
            rdmsSmsDto.setCode(userDto.getSmsCode());
            rdmsSmsDto.setUse(SmsUseEnum.FORGET.getCode());
            rdmsSmsService.validCode(rdmsSmsDto);
            flag = true;
            // 重置密码
            rdmsUserService.resetPassword(userDto);
        }

        // 2. 如果是机构管理员身份
        RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByContactPhone(userDto.getLoginName());
        // 如果机构管理员身份存在
        if(rdmsCustomer != null){
            //注意:此时传递过来的loginName是contactPhone(联系电话),不是customer表中的客户登录名称
            //根据管理员电话查找相应的记录
            RdmsCustomerDto customerDto = new RdmsCustomerDto();
            customerDto.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));

            customerDto.setLoginName(rdmsCustomer.getLoginName());
            customerDto.setContactPhone(rdmsCustomer.getContactPhone());

            // 校验短信验证码
            if(!flag){
                RdmsSmsDto rdmsSmsDto = new RdmsSmsDto();
                rdmsSmsDto.setMobile(customerDto.getContactPhone());
                rdmsSmsDto.setCode(customerDto.getSmsCode());
                rdmsSmsDto.setUse(SmsUseEnum.FORGET.getCode());
                rdmsSmsService.validCode(rdmsSmsDto);
            }
            // 重置密码
            rdmsCustomerService.resetPassword(customerDto);
        }

        return responseDto;
    }

    @GetMapping("/list-customer/{loginName}")
    public ResponseDto<List<RdmsCustomerDto>> listCustomer(@PathVariable String loginName){
        ResponseDto<List<RdmsCustomerDto>> responseDto = new ResponseDto();
        List<RdmsCustomerUser> customerUserByLoginName = rdmsCustomerUserService.findCustomerUserByLoginName(loginName);
        List<RdmsCustomerUserDto> rdmsCustomerUserDtos = CopyUtil.copyList(customerUserByLoginName, RdmsCustomerUserDto.class);
        List<RdmsCustomerDto> customerDtos = new ArrayList<>();
        for(RdmsCustomerUserDto customerUserDto : rdmsCustomerUserDtos){
            RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(customerUserDto.getCustomerId());
            if((!ObjectUtils.isEmpty(rdmsCustomer) && rdmsCustomer.getStatus().equals(CustomerStatusEnum.CUSTOMER_NORMAL.getStatus()))){
                RdmsCustomerDto customerDto = CopyUtil.copy(rdmsCustomer, RdmsCustomerDto.class);
                customerDtos.add(customerDto);
            }
        }
        responseDto.setContent(customerDtos);
        return responseDto;
    }



}
