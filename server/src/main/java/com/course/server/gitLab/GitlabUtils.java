/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.gitLab;

import com.course.server.service.util.HttpUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.util.HashMap;
import java.util.Map;


/**
 * gk
 * gitlab API工具类
 *
 */
public class GitlabUtils {

    /**
     * 将查询字符串转换为Map
     *
     * @param queryString 查询字符串
     * @return 查询参数的Map
     */
    public static Map<String, String> queryStringToMap(String queryString) {
        Map<String, String> queryPairs = new HashMap<>();
        String[] pairs = queryString.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            queryPairs.put(pair.substring(0, idx), pair.substring(idx + 1));
        }
        return queryPairs;
    }

    /**
     * 根据项目名称查找项目信息
     * 通过GitLab API搜索指定名称的项目
     *
     * @param projectName 项目名称
     * @param path 路径ip
     * @return 返回项目信息的JSON数组
     * @throws IOException IO异常
     */
    public static JSONArray getProjectId(String projectName, String path) throws IOException {
        Integer id = 0;
        String paths = "http://" + path + "/api/v4/search";
        String queryString = "search=" + projectName + "&scope=projects&all=true&access_token=令牌&per_page=50";
        Map<String, String> queryParams = queryStringToMap(queryString);

        // 使用 HttpUtils 类名直接调用静态方法
        String Project = HttpUtil.get(paths, queryParams); // 假设 HttpUtils.get 支持 Map 参数
        if (Project.isEmpty()) {
            return null;
        }
        JSONArray branch_json = JSONArray.fromObject(Project);
        return branch_json;
    }


}
