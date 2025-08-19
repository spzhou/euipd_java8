/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package apitest;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.RdmsCustomer;
import com.course.server.dto.ResponseDto;
import lombok.Data;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

@Data
class Resp {
    private String success;
    private String code;
    private String message;
    private String content;
}

public class EuipdAuthTest {

    private static final String API_URL = "http://127.0.0.1:9000/system/auth2/getToken";

    /**
     * 获取认证令牌
     * 通过AccessKey和AccessSecret向认证服务请求获取访问令牌
     * 
     * @param accessKey 访问密钥
     * @param accessSecret 访问密钥密文
     * @return 返回认证令牌字符串
     * @throws Exception 请求异常
     */
    public String getToken(String accessKey, String accessSecret) throws Exception {
        // 创建HttpClient对象
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // 创建HttpPost对象
            HttpPost httpPost = new HttpPost(API_URL);

            // 设置请求头
            httpPost.setHeader("Content-Type", "application/json");

            // 构建请求体
            String json = String.format("{\"accessKey\": \"%s\", \"accessSecret\": \"%s\"}", accessKey, accessSecret);
            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);

            // 执行请求
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                // 检查响应状态
                if (response.getStatusLine().getStatusCode() == 200) {
                    // 解析响应体
                    HttpEntity responseEntity = response.getEntity();
                    String result = EntityUtils.toString(responseEntity);
                    String token = parseTokenFromResponse(result);
                    return token;
                } else {
                    throw new RuntimeException("Failed to get token: " + response.getStatusLine());
                }
            }
        }
    }

    /**
     * 获取客户信息
     * 使用认证令牌向API服务请求获取客户详细信息
     * 
     * @param token 认证令牌
     * @return 返回客户信息对象
     * @throws Exception 请求异常
     */
    public RdmsCustomer getCustomerInfo(String token) throws Exception {
        // 创建HttpClient对象
        String API_PATH = "http://127.0.0.1:9000/system/auth2/api/getCustomerInfo/P93BKcxQ";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // 创建HttpPost对象
            HttpPost httpPost = new HttpPost(API_PATH);

            // 设置请求头
            httpPost.setHeader("Content-Type", "application/json");
            // 注意, 添加token到请求头中
            httpPost.setHeader("token", token);

            // 构建请求体
            //String json = String.format("{\"accessKey\": \"%s\", \"accessSecret\": \"%s\"}", accessKey, accessSecret);
            //StringEntity entity = new StringEntity(json);
            //httpPost.setEntity(entity);

            // 执行请求
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                // 检查响应状态
                if (response.getStatusLine().getStatusCode() == 200) {
                    // 解析响应体
                    HttpEntity responseEntity = response.getEntity();
                    String result = EntityUtils.toString(responseEntity);
                    ResponseDto responseDto = JSON.parseObject(result, ResponseDto.class);
                    Object content = responseDto.getContent();
                    String jsonString = JSON.toJSONString(content);
                    RdmsCustomer rdmsCustomer = JSON.parseObject(jsonString, RdmsCustomer.class);
                    return rdmsCustomer;
                } else {
                    throw new RuntimeException("Failed to get token: " + response.getStatusLine());
                }
            }
        }
    }

    /**
     * 解析响应中的令牌
     * 从HTTP响应中解析出认证令牌
     * 
     * @param response HTTP响应字符串
     * @return 返回解析出的令牌字符串
     */
    private String parseTokenFromResponse(String response) {
        // 实际应用中应该解析JSON响应，提取出token字段
        Resp resp = JSON.parseObject(response, Resp.class);
        return resp.getContent();
    }

    public static void main(String[] args) {
        try {
            EuipdAuthTest service = new EuipdAuthTest();
            String token = service.getToken("Vrov7Oqqcepmdm4s6E5f0L0VsSuQFYUN", "733f9dd70e0f4f37bc97d945f8522e51");
            System.out.println("Token: " + token);
            RdmsCustomer customerInfo = service.getCustomerInfo(token);
            System.out.println("customerInfo: " + customerInfo.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
