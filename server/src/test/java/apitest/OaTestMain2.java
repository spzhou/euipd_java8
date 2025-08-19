/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package apitest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.aliyuncs.utils.StringUtils;
import com.course.server.service.ecology.util.*;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

@Data
class Request2 {
    private Params2 params;
}

@Data
class Params2 {
    private String departmentid;
    private String loginid;
    private Integer pagesize;
    private Integer curpage;
}


@Data
public class OaTestMain2 {

    public static void main(String[] args) throws Exception {

        String userId = "4";  //设定为一个肯定不会删掉的账号
        String title = "物料申请流程-(开发测试)";
        Integer workflowId = 1222;

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

        Request2 req = new Request2();
        Params2 params = new Params2();
        params.setLoginid("13818105317");
        req.setParams(params);

        String jsonString = JSON.toJSONString(req);
        StringEntity entity = new StringEntity(jsonString, "UTF-8");
        httpPost.setEntity(entity);

        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            if (response.getStatusLine().getStatusCode() == 200) {
                // 解析响应体
                String responseBody = EntityUtils.toString(response.getEntity());
                HrmResp<HrmUser> hrmUserHrmResp = JSON.parseObject(responseBody, new TypeReference<HrmResp<HrmUser>>() {
                });
                System.out.println("hrmUserHrmResp: " + hrmUserHrmResp);

            } else {
                System.out.println("Request failed with status code: " + response.getStatusLine().getStatusCode());
            }
        }
    }
}
