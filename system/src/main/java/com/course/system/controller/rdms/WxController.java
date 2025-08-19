/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.system.controller.rdms;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.RdmsUser;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.RdmsUserDto;
import com.course.server.service.rdms.RdmsUserService;
import com.course.server.util.AuthUtil;
import com.course.server.util.CopyUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

@RestController
//@CrossOrigin
@RequestMapping("/WxAuth")
public class WxController {
    private static final Logger LOG = LoggerFactory.getLogger(WxController.class);
    public static final String BUSINESS_NAME = "微信登录";

    @Value("${web.wx.login.pc.callback.url}")
    private String WX_LOGIN_PC_CALL_BACK_URL;

    @Value("${web.wx.binding.callback.url}")
    private String WX_BINDING_CALL_BACK_URL;

    @Value("${web.root.page}")
    private String WEB_SITE_URL;

    @Resource
    private RdmsUserService rdmsUserService;

    @Resource
    private RedisTemplate redisTemplate;
    /*
    第一步,请求Code
    参数1:openid
    参数2:回调url   ---用户请求的第三方服务器+功能入口
    重定向到微信的接口地址,请求Code
     */
    @GetMapping("/wxRegister") //微信开放平台注册入口
    public ResponseDto wxRegister(HttpServletResponse resp) throws IOException {
        //TODO 回调地址要改
        String backUrl = WX_LOGIN_PC_CALL_BACK_URL;

        String url = "https://open.weixin.qq.com/connect/qrconnect?appid=" + AuthUtil.APPID +
                "&redirect_uri=" + URLEncoder.encode(backUrl) +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=STATE" +
                "#wechat_redirect";
//        resp.sendRedirect(url);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(url);
        return responseDto;
    }

    /*
    第二步,通过code换取网页授权access_token
    参数1:Code
    参数2:openid
    参数3:APPSecret
    调用微信提供的接口,获得access_token
    * */
    //处理微信开放平台回调
    @GetMapping("/pcCallBack")
    public void pcCallBack(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String code = req.getParameter("code");
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + AuthUtil.APPID +
                "&secret=" + AuthUtil.APPSECRET +
                "&code=" + code +
                "&grant_type=authorization_code";
        //调用工具类方法
        JSONObject jsonObject = AuthUtil.doGetJson(url);
        String openid = jsonObject.getString("openid");
        String token = jsonObject.getString("access_token");

        /*
        第三步,通过access_token和openid拉取用户信息
        参数1:access_toke
        参数2:openid
        调用微信提供的接口获得微信用户的数据.
        {
          "openid": "OPENID",
          "nickname": NICKNAME,
          "sex": 1,
          "province":"PROVINCE",
          "city":"CITY",
          "country":"COUNTRY",
          "headimgurl":"https://thirdwx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/46",
          "privilege":[ "PRIVILEGE1" "PRIVILEGE2"     ],
          "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
        }
        * */
        String infoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + token +
                "&openid=" + openid +
                "&lang=zh_CN";
        //获取用户的信息
        JSONObject userInfo = AuthUtil.doGetJson(infoUrl);
        String unionid = userInfo.getString("unionid");

        //1. 查看数据库中是否有对应的unionid
        if(!ObjectUtils.isEmpty(unionid)){
            RdmsUser rdmsUser = rdmsUserService.selectByUnionId(unionid);
            if(ObjectUtils.isEmpty(rdmsUser)){
                //设置token
                String wxLoginToken = unionid.substring(18);
                redisTemplate.opsForValue().set(wxLoginToken, JSON.toJSONString(userInfo), 900, TimeUnit.SECONDS);

                String redirectUrl = WEB_SITE_URL +"register";
                resp.sendRedirect(redirectUrl + "?token=" + wxLoginToken);

            }else{
                //3. 直接登录
                String redirectUrl = WEB_SITE_URL +"login";
                resp.sendRedirect(redirectUrl);
            }
        }
    }

    @PostMapping("/getWxUserInfo/{wxLoginToken}")
    public ResponseDto getWxUserInfo(@PathVariable String wxLoginToken) {
        Object object = redisTemplate.opsForValue().get(wxLoginToken);
        com.alibaba.fastjson.JSONObject wxUser = JSON.parseObject(String.valueOf(object));
        //JSONObject转JavaObject
        RdmsUserDto rdmsUserDto = JSON.toJavaObject(wxUser, RdmsUserDto.class);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(rdmsUserDto);
        return responseDto;
    }


    @GetMapping("/wxBinding") //微信开放平台注册入口
    public ResponseDto wxBinding() throws IOException {
        //TODO 回调地址要改
        String backUrl = WX_BINDING_CALL_BACK_URL;

        String url = "https://open.weixin.qq.com/connect/qrconnect?appid=" + AuthUtil.APPID +
                "&redirect_uri=" + URLEncoder.encode(backUrl) +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=STATE" +
                "#wechat_redirect";
//        resp.sendRedirect(url);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(url);
        return responseDto;
    }

    @GetMapping("/wxBindingCallBack")
    public void wxBindingCallBack(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String code = req.getParameter("code");
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + AuthUtil.APPID +
                "&secret=" + AuthUtil.APPSECRET +
                "&code=" + code +
                "&grant_type=authorization_code";
        //调用工具类方法
        JSONObject jsonObject = AuthUtil.doGetJson(url);
        String openid = jsonObject.getString("openid");
        String token = jsonObject.getString("access_token");

        String infoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + token +
                "&openid=" + openid +
                "&lang=zh_CN";
        //获取用户的信息
        JSONObject userInfo = AuthUtil.doGetJson(infoUrl);
        String unionid = userInfo.getString("unionid");

        //1. 查看数据库中是否有对应的unionid
        if(!ObjectUtils.isEmpty(unionid)){
            RdmsUser rdmsUser = rdmsUserService.selectByUnionId(unionid);
            if(ObjectUtils.isEmpty(rdmsUser)){
                //设置token
                String wxLoginToken = unionid.substring(18);
                redisTemplate.opsForValue().set(wxLoginToken, JSON.toJSONString(userInfo), 900, TimeUnit.SECONDS);

                String redirectUrl = WEB_SITE_URL +"wx-binding";
                resp.sendRedirect(redirectUrl + "?token=" + wxLoginToken);

            }else{
                //3. 直接登录
                String redirectUrl = WEB_SITE_URL +"welcome";
                resp.sendRedirect(redirectUrl);
            }
        }
    }

    @PostMapping("/wxUserBinding")
    public ResponseDto wxUserBinding(@RequestBody RdmsUserDto rdmsUserDto){
        RdmsUser rdmsUser1 = rdmsUserService.selectByPrimaryKey(rdmsUserDto.getId());
        RdmsUserDto rdmsUserDto1 = CopyUtil.copy(rdmsUser1, RdmsUserDto.class);

        //获取redis信息
        Object object = redisTemplate.opsForValue().get(rdmsUserDto.getToken());
        com.alibaba.fastjson.JSONObject wxUser = JSON.parseObject(String.valueOf(object));
        //JSONObject转JavaObject
        RdmsUserDto rdmsUserDto2 = JSON.toJavaObject(wxUser, RdmsUserDto.class);

        rdmsUserDto1.setNickname(rdmsUserDto2.getNickname());
        rdmsUserDto1.setSex(rdmsUserDto2.getSex());
        rdmsUserDto1.setHeadimgurl(rdmsUserDto2.getHeadimgurl());
        rdmsUserDto1.setUnionid(rdmsUserDto2.getUnionid());
        rdmsUserDto1.setOpenid(rdmsUserDto2.getOpenid());

        rdmsUserService.save(rdmsUserDto1);

        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }




/*    @PostMapping("/login-wx-proxy")
    public ResponseDto loginWxProxy() {
        String wxLoginToken = "member-token";
        Object object = redisTemplate.opsForValue().get(wxLoginToken);
        com.alibaba.fastjson.JSONObject loginUserDto = JSON.parseObject(String.valueOf(object));
        String password = loginUserDto.getString("password");
        String mobile = loginUserDto.getString("mobile");

        MemberDto memberDto = new MemberDto();
        memberDto.setMobile(mobile);
        memberDto.setPassword(password);

        LoginMemberDto loginMemberDto = memberService.login(memberDto);
        String token = UuidUtil.getShortUuid();
        loginMemberDto.setToken(token);
        redisTemplate.opsForValue().set(token, JSON.toJSONString(loginMemberDto), 3600, TimeUnit.SECONDS);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(loginMemberDto);
        return responseDto;
    }*/

    //绑定微信和第三方系统账号
/*    @PostMapping("/wxBinding")
    public ResponseDto wxBinding(@RequestBody UserWxLogin member ) {

        // 将从前端拿到的加密字符串再次进行加密
        member.setPassword(DigestUtils.md5DigestAsHex(member.getPassword().getBytes()));

        ResponseDto responseDto = new ResponseDto();
        String wxBindingToken = "login-wx-binding";
        Object object = redisTemplate.opsForValue().get(wxBindingToken);
        if(object == null){
            responseDto.setContent("/login"); //要求前端跳转到登录页面
            return responseDto;
        }
        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(String.valueOf(object));

        member.setUnionid(jsonObject.getString("unionid"));
        member.setOpenid(jsonObject.getString("openid"));
        member.setNickname(jsonObject.getString("nickname"));
        member.setProvince(jsonObject.getString("province"));
        member.setCity(jsonObject.getString("city"));
        member.setCountry(jsonObject.getString("country"));
        member.setHeadimgurl(jsonObject.getString("headimgurl"));
        member.setPrivilege(jsonObject.getString("privilege"));

        Member dbMmeber = memberService.selectByMobile(member.getLoginName());
        if(null != dbMmeber && member.getPassword().equals(dbMmeber.getPassword())){
            UserWxLogin userByLoginName = userWxLoginService.getUserByLoginName(member.getLoginName());
            if(userByLoginName != null){
                userWxLoginService.updateUserInfoByLoginName(member);
            }else {
                userWxLoginService.insertUserInfo(member);
            }
            UserWxLogin userWxLogin = userWxLoginService.getUserByUnionId(member.getUnionid());
            if(null != userWxLogin){
                //绑定成功
                Member memberDb = memberService.selectByMobile(member.getLoginName());
                String wxLoginToken = "member-token";
                //使用redis将member记住,给login-wx-proxy用,然后删除
                redisTemplate.opsForValue().set(wxLoginToken, JSON.toJSONString(memberDb), 20, TimeUnit.SECONDS);
                //通过代理页面实现登录操作
                responseDto.setContent("/login-wx-proxy");
                return responseDto;

            }else{
                responseDto.setContent("/login"); //要求前端跳转到登录页面
                return responseDto;
            }
        }else{
            responseDto.setContent("/login"); //要求前端跳转到登录页面
            return responseDto;
        }
    }*/

    //处理微信开放平台回调
/*    @GetMapping("/profile-img/{loginName}")
    public ResponseDto getProfileImg(@PathVariable String loginName) {
        ResponseDto responseDto = new ResponseDto();
        UserWxLogin userByLogin = userWxLoginService.getUserByLoginName(loginName);
        if(userByLogin==null){
            responseDto.setContent(null);
            return responseDto;
        }
        responseDto.setContent(userByLogin.getHeadimgurl()); //要求前端跳转到登录页面
        return responseDto;
    }*/
}


