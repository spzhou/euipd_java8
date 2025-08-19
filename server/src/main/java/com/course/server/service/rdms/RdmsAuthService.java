/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.RdmsAuth;
import com.course.server.domain.RdmsAuthExample;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.RdmsAuthDto;
import com.course.server.dto.rdms.RdmsTokenRequestDto;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsAuthMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RdmsAuthService {
    private static final Logger LOG = LoggerFactory.getLogger(RdmsAuthService.class);
    @Resource
    private RdmsAuthMapper rdmsAuthMapper;
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    public RdmsAuth selectByPrimaryKey(String id){
        return rdmsAuthMapper.selectByPrimaryKey(id);
    }

    public RdmsAuth getAuthByKey(String accessKey){
        RdmsAuthExample example = new RdmsAuthExample();
        example.createCriteria()
                .andAccessKeyEqualTo(accessKey)
                .andDeletedEqualTo(0);
        return rdmsAuthMapper.selectByExample(example).get(0);
    }

    public List<RdmsAuth> getAuthByCustomerId(String customerId){
        RdmsAuthExample example = new RdmsAuthExample();
        example.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andDeletedEqualTo(0);
        return rdmsAuthMapper.selectByExample(example);
    }

    public RdmsAuth save(@NotNull RdmsAuth auth) {
        return this.insert(auth);
    }

    private RdmsAuth insert(@NotNull RdmsAuth auth) {
        if(ObjectUtils.isEmpty(auth.getId())){  //当前端页面给出projectID时,将不为空
            auth.setId(UuidUtil.getShortUuid());
        }
        if(ObjectUtils.isEmpty(auth.getAccessKey())){  //当前端页面给出projectID时,将不为空
            auth.setAccessKey(UuidUtil.getUuid());
        }
        if(ObjectUtils.isEmpty(auth.getAccessSecret())){  //当前端页面给出projectID时,将不为空
            auth.setAccessSecret(UuidUtil.getUuid());
        }
        RdmsAuth accessBody = this.selectByPrimaryKey(auth.getId());
        if(ObjectUtils.isEmpty(accessBody)){
            auth.setDeleted(0);
            rdmsAuthMapper.insert(auth);
            return auth;
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsAuth accessBody = this.selectByPrimaryKey(id);
        if(ObjectUtils.isEmpty(accessBody)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            accessBody.setDeleted(1);
            rdmsAuthMapper.updateByPrimaryKey(accessBody);
        }
    }

    /**
     * 获得登录认证token
     * 根据用户提供的AccessKey和AccessSecret换取用户token
     * 用户获得token之后,在使用访问Api接口时,需要将token放到请求头中,例如:
     *
     *     private static final String API_URL = "http://127.0.0.1:9000/system/auth2/api/getCustomerInfo/P93BKcxQ";
     *     public String getCustomerInfo(String customerId) throws Exception {
     *         // 创建HttpClient对象
     *         try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
     *             // 创建HttpPost对象
     *             HttpPost httpPost = new HttpPost(API_URL);
     *
     *             // 设置请求头
     *             httpPost.setHeader("Content-Type", "application/json");
     *             httpPost.setHeader("token", "a805ace76cf84728adcb5607413c4614");  //注意这里
     *
     *             // 构建请求体
     *             //String json = JSON.toJSONString("请求对象放这里");
     *             //StringEntity entity = new StringEntity(json);
     *             //httpPost.setEntity(entity);
     *
     *             // 执行请求
     *             try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
     *                 // 检查响应状态
     *                 if (response.getStatusLine().getStatusCode() == 200) {
     *                     // 解析响应体
     *                     HttpEntity responseEntity = response.getEntity();
     *                     String result = EntityUtils.toString(responseEntity);
     *                     return parseTokenFromResponse(result); // 假设解析方法
     *                 } else {
     *                     throw new RuntimeException("Failed to get token: " + response.getStatusLine());
     *                 }
     *             }
     *         }
     *     }
     *
     * @param accessKey 包含AccessKey和AccessSecret以及必要的用户信息
     *
     * @return
     */
    public RdmsAuthDto getToken(String accessKey, String accessSecret) {
        String token = null;
        // 验证 AccessKey 和 AccessSecret
        if (ObjectUtils.isEmpty(accessKey) || ObjectUtils.isEmpty(accessSecret)) {
            return null;
        }
        // 验证 AccessKey 和 AccessSecret
        RdmsAuth authByKey = this.getAuthByKey(accessKey);
        if(ObjectUtils.isEmpty(authByKey)){
            return null;
        }else{
            if(!authByKey.getAccessSecret().equals(accessSecret)){
                return null;
            }else{
                // 生成新的 Token
                token = UuidUtil.getUuid();

                // 将 Token 存储到 Redis 中，设置过期时间为 5分钟
                authByKey.setAccessSecret(null); // 不返回 AccessSecret
                redisTemplate.opsForValue().set(token, JSON.toJSONString(authByKey), 300, TimeUnit.SECONDS);
            }
        }
        RdmsAuthDto authDto = CopyUtil.copy(authByKey, RdmsAuthDto.class);
        authDto.setToken(token);
        return authDto;
    }

}
