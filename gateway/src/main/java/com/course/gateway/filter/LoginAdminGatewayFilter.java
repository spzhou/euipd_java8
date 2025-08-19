/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class LoginAdminGatewayFilter implements GatewayFilter, Ordered {

    private static final Logger LOG = LoggerFactory.getLogger(LoginAdminGatewayFilter.class);

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 网关过滤器核心方法
     * 对请求进行登录验证和权限校验，拦截需要认证的接口
     * 
     * @param exchange 服务器Web交换对象，包含请求和响应信息
     * @param chain 网关过滤器链
     * @return 返回Mono<Void>，表示异步处理结果
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        // 请求地址中不包含/admin/的，不是控台请求，不需要拦截
        if (!path.contains("/admin/")
                && !path.contains("/rdms/")
                && !path.contains("/customer/")
                && !path.contains("/user/")
                && !path.contains("/system/auth2/")
        ) {
            return chain.filter(exchange);
        }

        if (path.contains("/system/rdms/user/login")
                || path.contains("/system/rdms/customer/login")
                || path.contains("/system/rdms/user/list-customer")
                || path.contains("/system/rdms/user/logout")
                || path.contains("/system/rdms/customer/logout")
                || path.contains("/system/rdms/user/is-mobile-exist")
                || path.contains("/system/rdms/customer/is-mobile-exist")
                || path.contains("/system/rdms/sms/send")
                || path.contains("/system/rdms/user/reset-password")
                || path.contains("/system/rdms/customer/reset-password")
                || path.contains("/system/rdms/customer/register")
                || path.contains("/system/rdms/customer/get-customer-by-loginname/")
                || path.contains("/system/rdms/kaptcha")
                || path.contains("/system/admin/user-enter/register")
                || path.contains("/system/admin/user/reset-password")
                || path.contains("/business/admin/course/find-outline")
                || path.contains("/business/admin/course/save")
                || path.contains("/business/admin/live/find-outline")
                || path.contains("/business/admin/user-config")
                || path.contains("/file/admin/aliyun-callback-info")
                || path.contains("/system/admin/kaptcha")
                || path.contains("/payproject/pay/create")
                || path.contains("/payproject/pay/notify")
                || path.contains("/system/WxAuth/")
                || path.contains("/system/vod/")
                || path.contains("/file/rdms/getFileAuth")
                || path.contains("/customer/web")
                || path.contains("/system/rdms/user/get-userinfo/")
        ) {
//            LOG.info("不需要控台登录验证：{}", path);
            return chain.filter(exchange);
        }

        //对Auth进行拦截设置
        if (path.contains("/system/auth2/getToken")
        ) {
            return chain.filter(exchange);
        }

        //访问路径包含/api时,是拿到token后,进行授权接口请求
        if (path.contains("/system/auth2/api")
        ) {
            //获取header的token参数
            String token = exchange.getRequest().getHeaders().getFirst("token");
            //auth2接口的token参数是32位的. 为了避免和常规访问的token混淆.
            if (token == null || token.length() != 32) {
                LOG.info("token为空，请求被拦截");
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);  //403 禁止  请求不带token 是被禁止的

                return exchange.getResponse().setComplete();
            }

            Object object = redisTemplate.opsForValue().get(token);
            //如果失败重试一次
            if (!ObjectUtils.isEmpty(object)) {
                Object object_retry = redisTemplate.opsForValue().get(token);
                if (!ObjectUtils.isEmpty(object_retry)) {
                    object = object_retry;
                }
            }

            if (object == null) {
                LOG.warn("token无效，请求被拦截");
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN); //403 禁止  请求不带token 是被禁止的

                return exchange.getResponse().setComplete();
            } else {
                //Token验证通过, 续期 5分钟
                JSONObject jsonObject = JSON.parseObject(String.valueOf(object));
                redisTemplate.opsForValue().set(token, JSON.toJSONString(jsonObject), 300, TimeUnit.SECONDS);

                return chain.filter(exchange);
            }
        }

        //上述各种情况都没有拦截,则执行下面的程序
        //获取header的token参数
        String token = exchange.getRequest().getHeaders().getFirst("token");
//        LOG.info("控台登录验证开始，token：{}", token);
        if (token == null || token.isEmpty()) {
//            LOG.info( "token为空，请求被拦截" );
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);  //403 禁止  请求不带token 是被禁止的
            return exchange.getResponse().setComplete();
        }
        Object object = redisTemplate.opsForValue().get(token);
        //如果失败重试一次
        if(!ObjectUtils.isEmpty(object)){
            Object object_retry = redisTemplate.opsForValue().get(token);
            if(!ObjectUtils.isEmpty(object_retry)){
                object = object_retry;
            }
        }

        if (object == null) {
//            LOG.warn( "token无效，请求被拦截" );
            exchange.getResponse().setStatusCode(HttpStatus.PRECONDITION_FAILED); //412 错误是redis token 过期了 是 前提条件错误
            return exchange.getResponse().setComplete();
        } else {
//            LOG.info("已登录：{}", object);

            // 增加权限校验，gateway里没有LoginUserDto，所以全部用JSON操作
//            LOG.info("接口权限校验，请求地址：{}", path);
            boolean exist = false;
            JSONObject loginUserDto = JSON.parseObject(String.valueOf(object));
            redisTemplate.opsForValue().set(token, JSON.toJSONString(loginUserDto), 7200, TimeUnit.SECONDS);
            JSONArray requests = loginUserDto.getJSONArray("requests");
            // 遍历所有【权限请求】，判断当前请求的地址是否在【权限请求】里
            for (int i = 0, l = requests.size(); i < l; i++) {
                String request = (String) requests.get(i);
                if (path.contains(request)) {
                    exist = true;
                    break;
                }
            }
            if (exist) {
//                LOG.info("权限校验通过");
            } else {
//                LOG.warn("权限校验未通过");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);  //401 没有权限
                return exchange.getResponse().setComplete();
            }

            return chain.filter(exchange);
        }
    }

    /**
     * 获取过滤器执行顺序
     * 数字越小优先级越高
     * 
     * @return 返回过滤器执行顺序，当前设置为1
     */
    @Override
    public int getOrder()
    {
        return 1;
    }
}
