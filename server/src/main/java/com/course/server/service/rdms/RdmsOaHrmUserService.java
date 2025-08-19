/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.course.server.domain.RdmsOaHrmUser;
import com.course.server.domain.RdmsOaHrmUserExample;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsOaHrmUserMapper;
import com.course.server.service.ecology.util.*;
import com.course.server.util.UuidUtil;
import lombok.Data;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Data
class Request {
    private Params params;
}

@Data
class Params {
    private String departmentid;
    private String loginid;
    private Integer pagesize;
    private Integer curpage;
}


@Service
public class RdmsOaHrmUserService {
    private static final Logger LOG = LoggerFactory.getLogger(RdmsOaHrmUserService.class);

    @Resource
    private RdmsOaHrmUserMapper rdmsOaHrmUserMapper;

    @Value("${oa.material.form.creater}")
    private String formCreaterId;
    /**
     * 根据用户的loginID读取一条用户记录
     *
     * @param loginId
     * @return
     */
    public RdmsOaHrmUser selectByLoginId(String loginId) {
        RdmsOaHrmUserExample rdmsOaHrmUserExample = new RdmsOaHrmUserExample();
        rdmsOaHrmUserExample.createCriteria()
                .andLoginidEqualTo(loginId);
        List<RdmsOaHrmUser> rdmsOaHrmUsers = rdmsOaHrmUserMapper.selectByExample(rdmsOaHrmUserExample);
        if (CollectionUtils.isEmpty(rdmsOaHrmUsers)) {
            return null;
        }
        return rdmsOaHrmUsers.get(0);
    }

    /**
     * 如果根据loginId查询OA用户数据库,返回结果,并将查询到的记录保存到数据库中
     *
     * @param loginId
     * @return
     */
    public RdmsOaHrmUser selectByLoginIdAndSaveToDB(String loginId) throws Exception {
        RdmsOaHrmUser rdmsOaHrmUser = selectByLoginId(loginId);
        if (ObjectUtils.isEmpty(rdmsOaHrmUser)) {
            String userId = formCreaterId;  //刘亚如ID 393
            String path = OaConfig.getHost() + "/api/hrm/resful/getHrmUserInfoWithPage";
            CloseableHttpClient httpClient = HttpClients.createDefault();
            // 创建 HttpPost 实例
            HttpPost httpPost = new HttpPost(path);

            // 设置请求头
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("token", OaTokenUtils.getToken());
            httpPost.setHeader("appid", OaConfig.getAppId());

            if (!ObjectUtils.isEmpty(userId)) {
                String u = RsaEncrypt.encrypt(userId, OaConfig.getSpk());
                httpPost.setHeader("userid", u);
            }

            Request req = new Request();
            Params params = new Params();
            params.setLoginid(loginId);
            req.setParams(params);

            String jsonString = JSON.toJSONString(req);
            StringEntity entity = new StringEntity(jsonString, "UTF-8");
            httpPost.setEntity(entity);

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                if (response.getStatusLine().getStatusCode() == 200) {
                    // 解析响应体
                    String responseBody = EntityUtils.toString(response.getEntity());
                    HrmResp<RdmsOaHrmUser> hrmUserHrmResp = JSON.parseObject(responseBody, new TypeReference<HrmResp<RdmsOaHrmUser>>() {});
                    if(ObjectUtils.isEmpty(hrmUserHrmResp)){
                        return null;  // 未查询到数据
                    }else {
                        List<RdmsOaHrmUser> dataList = hrmUserHrmResp.getData().getDataList();
                        if (ObjectUtils.isEmpty(dataList)) {
                            return null;
                        } else {
                            RdmsOaHrmUser hrmUser = dataList.get(0);
                            //向数据库写入数据记录
                            if(!(ObjectUtils.isEmpty(hrmUser) || ObjectUtils.isEmpty(hrmUser.getId()))){
                                String save = this.save(hrmUser);
                            }
                            return hrmUser;
                        }
                    }
                } else {
                    throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST_OR_ERROR);
                }
            } catch (Exception e) {
                throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST_OR_ERROR);
            }
        } else {
            return rdmsOaHrmUser;
        }
    }


    public RdmsOaHrmUser selectByPrimaryKey(String id) {
        return rdmsOaHrmUserMapper.selectByPrimaryKey(id);
    }

    /**
     * 保存
     */
    public String save(@NotNull RdmsOaHrmUser hrmUser) {
        if (ObjectUtils.isEmpty(hrmUser.getId())) {
            return this.insert(hrmUser);
        } else {
            RdmsOaHrmUser rdmsHrmUser = this.selectByPrimaryKey(hrmUser.getId());
            if (ObjectUtils.isEmpty(rdmsHrmUser)) {
                return this.insert(hrmUser);
            } else {
                return this.update(hrmUser);
            }
        }
    }

    /**
     * 新增
     */
    private String insert(@NotNull RdmsOaHrmUser hrmUser) {
        if (ObjectUtils.isEmpty(hrmUser.getId())) {
            hrmUser.setId(UuidUtil.getShortUuid());
        }
        RdmsOaHrmUser rdmsHrmUser = rdmsOaHrmUserMapper.selectByPrimaryKey(hrmUser.getId());
        if (!ObjectUtils.isEmpty(rdmsHrmUser)) {
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        } else {
            rdmsOaHrmUserMapper.insert(hrmUser);
            return hrmUser.getId();
        }
    }

    /**
     * 更新
     */
    public String update(@NotNull RdmsOaHrmUser hrmUser) {
        if (ObjectUtils.isEmpty(hrmUser.getId())) {
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsOaHrmUser rdmsHrmUser = this.selectByPrimaryKey(hrmUser.getId());
        if (ObjectUtils.isEmpty(rdmsHrmUser)) {
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        } else {
            rdmsOaHrmUserMapper.updateByPrimaryKey(hrmUser);
            return hrmUser.getId();
        }
    }

    public String updateByPrimaryKeySelective(RdmsOaHrmUser hrmUser) {
        rdmsOaHrmUserMapper.updateByPrimaryKeySelective(hrmUser);
        return hrmUser.getId();
    }

}
