/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.gitLab;

import org.gitlab4j.api.GitLabApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GitLabConfig {

    @Value("${gitlab.url}")
    private String gitlabUrl;

    @Value("${gitlab.token}")
    private String gitlabToken;

    @Bean
    public GitLabApi gitLabApi() {
        return new GitLabApi(gitlabUrl, gitlabToken);
    }
}
