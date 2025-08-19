/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.polyv.util;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
//import java.net.URLEncoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;
import com.course.server.polyv.common.constant.Constant;

/**
 * @author: sadboy
 **/

public class HttpUtil {
    private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);
    private static final String UTF8 = "UTF-8";
    private static final String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded; charset=UTF-8";
    private static final String APPLICATION_JSON = "application/json; charset=UTF-8";
    private static final String TEXT_PLAIN = "text/plain; charset=UTF-8";
    private static final String TEXT_HTML = "text/html; charset=UTF-8";
    private static final String APPLICATION_XML = "application/xml; charset=UTF-8";
    
    private HttpUtil() {
    }
    
    /**
     * 向url发送get请求,当无参数时，paramMap为NULL
     * @param url 请求url
     * @param paramMap 需要拼接的参数
     * @return 请求返回的数据
     * @throws IOException 读写异常
     */
    public static String get(String url, Map<String, String> paramMap) throws IOException {
        return get(url, paramMap, UTF8);
    }
    
    /**
     * 向url发送get请求
     * @param url 请求url
     * @param paramMap 需要拼接的参数
     * @param encoding 编码
     * @return 请求返回的数据
     * @throws IOException 读写异常
     */
    public static String get(String url, Map<String, String> paramMap, String encoding) throws IOException {
        encoding = encoding == null ? UTF8 : encoding;
        url = appendUrl(url, paramMap);
        return get(url, encoding, new DataParse<String>() {
            @Override
            public String parseData(HttpEntity httpEntity, String encoding) throws IOException {
                return EntityUtils.toString(httpEntity, encoding);
            }
        });
    }
    
    /**
     * 向url发送get请求
     * @param url 请求url
     * @param paramMap 需要拼接的参数
     * @param encoding 编码
     * @return 请求返回的字节数组，一般用于文件下载
     * @throws IOException 读写异常
     */
    public static byte[] getBinary(String url, Map<String, String> paramMap, String encoding) throws IOException {
        encoding = encoding == null ? UTF8 : encoding;
        url = appendUrl(url, paramMap);
        return get(url, encoding, new DataParse<byte[]>() {
            @Override
            public byte[] parseData(HttpEntity httpEntity, String encoding) throws IOException {
                return EntityUtils.toByteArray(httpEntity);
            }
        });
    }
    
    
    /**
     * HTTP GET 内部公共请求处理逻辑
     * @param url 请求地址
     * @param encoding 编码字符集， 默认为 utf-8
     * @param dataParse 返回数据反序列化逻辑实现类
     * @return HTTP 返回的内容
     * @throws IOException 客户端和服务器读写通讯异常
     */
    private static <T> T get(String url, String encoding, DataParse<T> dataParse) throws IOException {
        log.debug("http 请求 url: {}", url);
        T result = null;
        // 创建httpclient对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建get方式请求对象
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Content-type", APPLICATION_JSON);
        // 通过请求对象获取响应对象
        CloseableHttpResponse response = sendRequestAndGetResult(url, httpClient, httpGet);
        // 获取结果实体
        if (null != response) {
            result = dataParse.parseData(response.getEntity(), encoding);
            if (!(result instanceof byte[])) {
                log.debug("http 请求结果: {}", result);
            } else {
                Header[] headers = response.getHeaders("Content-Type");
                for (Header responseHead : headers) {
                    String headStr = responseHead.getValue();
                    if (headStr.startsWith("application/json")) {
                        String json = new String((byte[]) result);
                        response.close();
                        throw new RuntimeException(json);
                    }
                }
            }
        }
        try {
            if (null != response) {
                response.close();
            }
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
        return result;
    }
    
    
    /**
     * 向url发送post请求
     * @param url 请求url
     * @param paramMap 需要拼接的参数
     * @return 请求返回的数据
     * @throws IOException 读写异常
     */
    public static String postFormBody(String url, Map<String, String> paramMap) throws IOException {
        return postFormBody(url, paramMap, null);
    }
    
    /**
     * 向url发送post请求
     * @param url 请求url
     * @param paramMap 需要拼接的参数
     * @param encoding 编码
     * @return 请求返回的数据
     * @throws IOException 读写异常
     */
    public static String postFormBody(String url, Map<String, String> paramMap, String encoding) throws IOException {
        return post(url, paramMap, encoding);
    }
    
    
    /**
     * 向url发送post请求表单提交数据
     * @param url 请求url
     * @param paramMap 表单数据
     * @param encoding 编码
     * @return 请求返回的数据
     * @throws IOException 读写异常
     */
    private static String post(String url, Map<String, String> paramMap, String encoding) throws IOException {
        log.debug("http 请求 url: {} , 请求参数: {}", url, appendUrl("", paramMap).replace("?", ""));
        encoding = encoding == null ? UTF8 : encoding;
        // 创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);
        // 装填参数
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        if (paramMap != null) {
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                String value = entry.getValue();
                //去掉如下判断会造成String类型的value为null时
                if (value != null) {
                    nameValuePairs.add(new BasicNameValuePair(entry.getKey(), value));
                }
            }
        }
        // 设置参数到请求对象中
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, encoding));
        // 设置header信息
        // 指定报文头【Content-type】、【User-Agent】
        httpPost.setHeader("Content-type", APPLICATION_FORM_URLENCODED);
        return post(url, httpPost, encoding, new DataParse<String>() {
            @Override
            public String parseData(HttpEntity httpEntity, String encoding) throws IOException {
                return EntityUtils.toString(httpEntity, encoding);
            }
        });
    }
    
    /**
     * 向url发送post请求发送json
     * @param url 请求url
     * @param json json字符串
     * @param encoding 编码
     * @return 请求返回的数据
     * @throws IOException 读写异常
     */
    public static String postJsonBody(String url, String json, String encoding) throws IOException {
        log.debug("http 请求 url: {} , 请求参数: {}", url, json);
        encoding = encoding == null ? UTF8 : encoding;
        // 创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);
        // 设置参数到请求对象中
        StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
        //  Constant.UTF8
        stringEntity.setContentEncoding(encoding);
        httpPost.setEntity(stringEntity);
        String result = post(url, httpPost, encoding, new DataParse<String>() {
            @Override
            public String parseData(HttpEntity httpEntity, String encoding) throws IOException {
                return EntityUtils.toString(httpEntity, encoding);
            }
        });
        return result;
    }
    
    /**
     * 向url发送post请求
     * @param url 请求url
     * @param httpPost httpClient
     * @return 请求返回的数据
     * @throws IOException 读写异常
     */
    private static <T> T post(String url, HttpPost httpPost, String encoding, DataParse<T> dataParse)
            throws IOException {
        T result = null;
        CloseableHttpResponse response = null;
        // 创建httpclient对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        
        // 执行请求操作，并拿到结果（同步阻塞）
        response = sendRequestAndGetResult(url, httpClient, httpPost);
        // 获取结果实体
        // 判断网络连接状态码是否正常(0--200都数正常)
        if (null != response) {
            result = dataParse.parseData(response.getEntity(), encoding);
            log.debug("http 请求结果: {}", result);
        }
        try {
            if (null != response) {
                response.close();
            }
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
        return result;
    }
    
    /**
     * 设置http头，发送http请求，打印请求耗时
     * @param url 请求url
     * @param httpClient httpClient
     * @param httpUriRequest httpUriRequest
     * @return 请求返回的数据
     * @throws IOException 读写异常
     */
    private static CloseableHttpResponse sendRequestAndGetResult(String url, CloseableHttpClient httpClient,
            HttpUriRequest httpUriRequest) throws IOException {
        long startTime = System.currentTimeMillis();
        CloseableHttpResponse response = httpClient.execute(httpUriRequest);
        long endTime = System.currentTimeMillis();
        collectAPISpendTime(url, startTime, endTime);
        return response;
    }
    
    /**
     * 打印请求信息
     * @param url 请求url
     * @param startTime 请求开始时间
     * @param endTime 请求结束时间
     */
    private static void collectAPISpendTime(String url, long startTime, long endTime) {
        log.debug("HTTP请求耗时分析，请求URL: {} ， 耗时: {} ms", url, endTime - startTime);
    }
    
    /**
     * 向url发送post请求上传单文件
     * @param url 请求url
     * @param paramMap 需要表单提交的参数
     * @param fileMap 需要上传的文件
     * @param encoding 编码
     * @return 请求返回的数据
     * @throws IOException 读写异常
     */
    public static String postFile(String url, Map<String, String> paramMap, Map<String, File> fileMap, String encoding)
            throws IOException {
        if (fileMap != null) {
            Map<String, List<File>> fileListMap = new HashMap<String, List<File>>();
            for (Map.Entry<String, File> entry : fileMap.entrySet()) {
                File file = entry.getValue();
                List<File> fileList = new ArrayList<File>();
                fileList.add(file);
                fileListMap.put(entry.getKey(), fileList);
            }
            return postMultipleFile(url, paramMap, fileListMap, encoding);
        }
        return postMultipleFile(url, paramMap, null, encoding);
    }
    
    /**
     * 向url发送post请求上传多文件
     * 向url发送post请求上传单文件
     * @param url 请求url
     * @param paramMap 需要表单提交的参数
     * @param fileListMap 需要上传的文件
     * @param encoding 编码
     * @return 请求返回的数据
     * @throws IOException 读写异常
     */
    public static String postMultipleFile(String url, Map<String, String> paramMap, Map<String, List<File>> fileListMap,
            String encoding) throws IOException {
        return postFileBody(url, paramMap, fileListMap, encoding, new DataParse<String>() {
            @Override
            public String parseData(HttpEntity httpEntity, String encoding) throws IOException {
                return EntityUtils.toString(httpEntity, encoding);
            }
        });
    }
    
    /**
     * 向url发送post请求上传多文件
     * 向url发送post请求上传单文件
     * @param url 请求url
     * @param paramMap 需要表单提交的参数
     * @param fileListMap 需要上传的文件
     * @param encoding 编码
     * @return 请求返回的数据
     * @throws IOException 读写异常
     */
    private static <T> T postFileBody(String url, Map<String, String> paramMap, Map<String, List<File>> fileListMap,
            String encoding, DataParse<T> dataParse) throws IOException {
        log.debug("http 请求 url: {} , 请求参数: {}", url, appendUrl("", paramMap).replace("?", ""));
        encoding = encoding == null ? UTF8 : encoding;
        T result = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        
        ContentType contentType = ContentType.create("text/plain", Charset.forName(encoding));
        if (null != paramMap) {
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                entityBuilder.addTextBody(entry.getKey(), entry.getValue(), contentType);
            }
        }
        
        if (null != fileListMap) {
            for (Map.Entry<String, List<File>> entry : fileListMap.entrySet()) {
                String key = entry.getKey();
                List<File> fileList = entry.getValue();
                for (File file : fileList) {
                    FileBody fileBody = new FileBody(file);
                    entityBuilder.addPart(key, fileBody);
                }
            }
        }
        
        HttpEntity entity = entityBuilder.build();
        httpPost.setEntity(entity);
        CloseableHttpResponse response = sendRequestAndGetResult(url, httpClient, httpPost);
        if (null != response) {
            result = dataParse.parseData(response.getEntity(), encoding);
            log.debug("http 请求结果: {}", result);
        }
        try {
            if (null != response) {
                response.close();
            }
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
        return result;
    }
    
    
    /**
     * 公共数据解析接口
     * @param <T>
     */
    private interface DataParse<T> {
        /**
         * 解析返回数据
         * @param httpEntity 返回实体
         * @param encoding 编码
         * @return 实际解析返回内容
         * @throws IOException io异常
         */
        T parseData(HttpEntity httpEntity, String encoding) throws IOException;
        
    }
    
    /**
     * 将url与map拼接成HTTP查询字符串
     * @param url 请求url
     * @param paramMap 需要拼装的map
     * @return 拼装好的url
     */
    public static String appendUrl(String url, Map<String, String> paramMap) throws UnsupportedEncodingException {
        if (paramMap == null) {
            return url;
        }
        StringBuffer paramStringBuffer = new StringBuffer();
        Iterator<Map.Entry<String, String>> mapIterator = paramMap.entrySet().iterator();
        while (mapIterator.hasNext()) {
            Map.Entry<String, String> next = mapIterator.next();
            paramStringBuffer.append(next.getKey()).append("=").append(URLEncoder.encode(next.getValue(), Constant.UTF8)).append("&");
        }
        String paramStr = paramStringBuffer.toString();
//        String paramStr = mapJoinNotEncode(paramMap);
        if (paramStr != null && !"".equals(paramStr)) {
            if (url.indexOf("?") > 0) {
                if (url.endsWith("&")) {
                    url += paramStr.substring(0, paramStr.length() - 1);
                } else {
                    url += "&" + paramStr.substring(0, paramStr.length() - 1);
                }
            } else {
                url += "?" + paramStr.substring(0, paramStr.length() - 1);
            }
        }
        return url;
    }
    
    /**
     * 把二进制写入文件
     * @param bytes
     * @param path
     * @throws IOException
     */
    public static void writeFile(byte[] bytes, String path) throws IOException {
        OutputStream os = null;
        try {
            // 根据绝对路径初始化文件
            File localFile = new File(path);
            if (!localFile.exists()) {
                boolean newFile = localFile.createNewFile();
                if (!newFile) {
                    throw new RuntimeException("创建文件异常，路径：" + path);
                }
            }
            // 输出流
            os = new FileOutputStream(localFile);
            os.write(bytes);
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }
    
}

