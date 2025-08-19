/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.gitLab;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;

import org.gitlab4j.api.models.Issue;

import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GitLabService {

    private final GitLabApi gitLabApi;

    /**
     * 构造函数
     * 注入GitLabApi实例
     * 
     * @param gitLabApi GitLab API实例
     */
    @Autowired
    public GitLabService(GitLabApi gitLabApi) {
        this.gitLabApi = gitLabApi;
    }

    /**
     * 创建GitLab项目
     * 创建一个新的公开GitLab项目
     * 
     * @param projectName 项目名称
     * @return 返回创建的项目对象
     * @throws GitLabApiException GitLab API异常
     */
    public Project createProject(String projectName) throws GitLabApiException {
        Project projectConfig = new Project()
                .withName(projectName)
                .withVisibility(Visibility.PUBLIC);
        return gitLabApi.getProjectApi().createProject(projectConfig);
    }

    /**
     * 获取所有GitLab项目
     * 获取当前用户可访问的所有GitLab项目列表
     * 
     * @return 返回项目列表
     * @throws GitLabApiException GitLab API异常
     */
    public List<Project> getAllProjects() throws GitLabApiException {
        return gitLabApi.getProjectApi().getProjects();
    }

    // 创建Issue
/*    public Issue createIssue(Long projectId, String title, String description) throws GitLabApiException {
        // 创建 Issue 对象
        Issue issue = new Issue();
        issue.setTitle(title);
        issue.setDescription(description);

        // 调用 GitLab API 创建 Issue
        return gitLabApi.getIssuesApi().createIssue(projectId, issue);
    }*/
}
