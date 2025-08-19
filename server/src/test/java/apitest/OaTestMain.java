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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@Data
class Request {
    private Params params;
}

@Data
class Params {
    private String departmentid;
    private Integer pagesize;
    private Integer curpage;
}


@Data
public class OaTestMain {

    public static void main(String[] args) throws Exception {

        final String DB_URL="jdbc:mysql://localhost:3306/mycourse?useUnicode=true&serverTimezone=Asia/Shanghai&characterEncoding=utf8&autoReconnect=true&useSSL=false&allowMultiQueries=true";
        final String USER="root";
        final String PASS="YOUR PASSWORD";

        String userId = "442";
        String title = "物料申请流程-(开发测试)";
        Integer workflowId = 1222;

        String path = OaConfig.getHost() + "/api/hrm/resful/getHrmdepartmentWithPage";
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
        params.setDepartmentid("14");
        params.setPagesize(100);
        params.setCurpage(1);
        req.setParams(params);

        String jsonString = JSON.toJSONString(req);
        StringEntity entity = new StringEntity(jsonString, "UTF-8");
        httpPost.setEntity(entity);

        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            // 检查响应状态
            if (response.getStatusLine().getStatusCode() == 200) {
                // 解析响应体
                String responseBody = EntityUtils.toString(response.getEntity());
//                HrmResp<HrmDepartment> resp = JSON.parseObject(responseBody, HrmResp.class);
                // 使用 TypeReference 指定泛型类型
                HrmResp<HrmDepartment> resp = JSON.parseObject(responseBody, new TypeReference<HrmResp<HrmDepartment>>() {});
                List<HrmDepartment> dataList = resp.getData().getDataList();
                List<String> departmentIdList = new ArrayList<>();
                for (HrmDepartment department : dataList) {
                    String id = department.getId();
                    departmentIdList.add(id);
                }
                for (String departmentId : departmentIdList) {
                    String path_1 = OaConfig.getHost() + "/api/hrm/resful/getHrmUserInfoWithPage";
                    CloseableHttpClient httpClient_1 = HttpClients.createDefault();
                    // 创建 HttpPost 实例
                    HttpPost httpPost_1 = new HttpPost(path_1);

                    // 设置请求头
                    httpPost_1.setHeader("Content-Type", "application/json");
                    httpPost_1.setHeader("token", OaTokenUtils.getToken());
                    httpPost_1.setHeader("appid", OaConfig.getAppId());

                    if (!ObjectUtils.isEmpty(userId)) {
                        String u = RsaEncrypt.encrypt(userId, OaConfig.getSpk());
                        httpPost_1.setHeader("userid", u);
                    }

                    Request req_1 = new Request();
                    Params params_1 = new Params();
                    params_1.setDepartmentid(departmentId);  //部门Id
                    params_1.setPagesize(100);
                    params_1.setCurpage(1);
                    req_1.setParams(params_1);

                    String jsonString_1 = JSON.toJSONString(req_1);
                    StringEntity entity_1 = new StringEntity(jsonString_1, "UTF-8");
                    httpPost_1.setEntity(entity_1);

                    try (CloseableHttpResponse response_1 = httpClient_1.execute(httpPost_1)) {
                        // 检查响应状态
                        if (response_1.getStatusLine().getStatusCode() == 200) {
                            // 解析响应体
                            String responseBody_1 = EntityUtils.toString(response_1.getEntity());
//                            HrmResp resp_1 = JSON.parseObject(responseBody_1, HrmResp.class);
                            // 使用 TypeReference 指定泛型类型
                            HrmResp<HrmUser> resp_1 = JSON.parseObject(responseBody_1, new TypeReference<HrmResp<HrmUser>>() {
                            });
                            List<HrmUser> dataList_1 = resp_1.getData().getDataList();
                            {
                                String sql = "INSERT INTO rdms_oa_hrm_user (companystartdate, tempresidentnumber, createdate, language, workstartdate, " +
                                        "subcompanyid1, subcompanyname, joblevel, startdate, password, belongtoid, subcompanycode, " +
                                        "jobactivitydesc, bememberdate, modified, id, mobilecall, nativeplace, certificatenum, height, " +
                                        "loginid, created, degree, bepartydate, weight, telephone, classification, residentplace, " +
                                        "lastname, healthinfo, enddate, maritalstatus, departmentname, folk, status, birthday, isadaccount, " +
                                        "accounttype, textfield1, textfield2, jobcall, managerid, assistantid, departmentcode, belongto, " +
                                        "email, seclevel, policy, jobtitle, workcode, sex, departmentid, homeaddress, mobile, lastmoddate, " +
                                        "educationlevel, islabouunion, locationid, regresidentplace, dsporder) " +
                                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                                try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                                    for (HrmUser user : dataList_1) {
                                        int index = 1;
                                        pstmt.setString(index++, user.getCompanystartdate());
                                        pstmt.setString(index++, user.getTempresidentnumber());
                                        pstmt.setString(index++, user.getCreatedate());
                                        pstmt.setString(index++, user.getLanguage());
                                        pstmt.setString(index++, user.getWorkstartdate());
                                        pstmt.setString(index++, user.getSubcompanyid1());
                                        pstmt.setString(index++, user.getSubcompanyname());
                                        pstmt.setString(index++, user.getJoblevel());
                                        pstmt.setString(index++, user.getStartdate());
                                        pstmt.setString(index++, user.getPassword());
                                        pstmt.setString(index++, user.getBelongtoid());
                                        pstmt.setString(index++, user.getSubcompanycode());
                                        pstmt.setString(index++, user.getJobactivitydesc());
                                        pstmt.setString(index++, user.getBememberdate());
                                        pstmt.setString(index++, user.getModified());
                                        pstmt.setString(index++, user.getId());
                                        pstmt.setString(index++, user.getMobilecall());
                                        pstmt.setString(index++, user.getNativeplace());
                                        pstmt.setString(index++, user.getCertificatenum());
                                        pstmt.setString(index++, user.getHeight());
                                        pstmt.setString(index++, user.getLoginid());
                                        pstmt.setString(index++, user.getCreated());
                                        pstmt.setString(index++, user.getDegree());
                                        pstmt.setString(index++, user.getBepartydate());
                                        pstmt.setString(index++, user.getWeight());
                                        pstmt.setString(index++, user.getTelephone());
                                        pstmt.setString(index++, user.getClassification());
                                        pstmt.setString(index++, user.getResidentplace());
                                        pstmt.setString(index++, user.getLastname());
                                        pstmt.setString(index++, user.getHealthinfo());
                                        pstmt.setString(index++, user.getEnddate());
                                        pstmt.setString(index++, user.getMaritalstatus());
                                        pstmt.setString(index++, user.getDepartmentname());
                                        pstmt.setString(index++, user.getFolk());
                                        pstmt.setString(index++, user.getStatus());
                                        pstmt.setString(index++, user.getBirthday());
                                        pstmt.setString(index++, user.getIsadaccount());
                                        pstmt.setString(index++, user.getAccounttype());
                                        pstmt.setString(index++, user.getTextfield1());
                                        pstmt.setString(index++, user.getTextfield2());
                                        pstmt.setString(index++, user.getJobcall());
                                        pstmt.setString(index++, user.getManagerid());
                                        pstmt.setString(index++, user.getAssistantid());
                                        pstmt.setString(index++, user.getDepartmentcode());
                                        pstmt.setString(index++, user.getBelongto());
                                        pstmt.setString(index++, user.getEmail());
                                        pstmt.setString(index++, user.getSeclevel());
                                        pstmt.setString(index++, user.getPolicy());
                                        pstmt.setString(index++, user.getJobtitle());
                                        pstmt.setString(index++, user.getWorkcode());
                                        pstmt.setString(index++, user.getSex());
                                        pstmt.setString(index++, user.getDepartmentid());
                                        pstmt.setString(index++, user.getHomeaddress());
                                        pstmt.setString(index++, user.getMobile());
                                        pstmt.setString(index++, user.getLastmoddate());
                                        pstmt.setString(index++, user.getEducationlevel());
                                        pstmt.setString(index++, user.getIslabouunion());
                                        pstmt.setString(index++, user.getLocationid());
                                        pstmt.setString(index++, user.getRegresidentplace());
                                        pstmt.setString(index, user.getDsporder());

                                        pstmt.addBatch();
                                    }

                                    pstmt.executeBatch();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            System.out.println("Response Body: " + responseBody_1);
                        } else {
                            System.out.println("Request failed with status code: " + response.getStatusLine().getStatusCode());
                        }
                    }
                }
            } else {
                System.out.println("Request failed with status code: " + response.getStatusLine().getStatusCode());
            }
        }
    }
}
