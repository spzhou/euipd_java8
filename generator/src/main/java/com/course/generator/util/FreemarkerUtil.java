/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.generator.util;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class FreemarkerUtil {

    static String ftlPath = "generator\\src\\main\\java\\com\\course\\generator\\ftl\\";

    static Template temp;

    /**
     * 初始化Freemarker配置
     * 设置模板路径和对象包装器，加载指定的模板文件
     * 
     * @param ftlName 模板文件名
     * @throws IOException 模板加载异常
     */
    public static void initConfig(String ftlName) throws IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        cfg.setDirectoryForTemplateLoading(new File(ftlPath));
        cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_29));
        temp = cfg.getTemplate(ftlName);
    }

    /**
     * 生成文件
     * 使用Freemarker模板引擎根据数据模型生成目标文件
     * 
     * @param fileName 目标文件名
     * @param map 数据模型映射
     * @throws IOException 文件操作异常
     * @throws TemplateException 模板处理异常
     */
    public static void generator(String fileName, Map<String, Object> map) throws IOException, TemplateException {
        FileWriter fw = new FileWriter(fileName);
        BufferedWriter bw = new BufferedWriter(fw);
        temp.process(map, bw);
        bw.flush();
        fw.close();
    }
}
