/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import com.course.server.gitLab.GitLabService;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gitlab")
public class GitLabController {

    private final GitLabService gitLabService;

    @Autowired
    public GitLabController(GitLabService gitLabService) {
        this.gitLabService = gitLabService;
    }

    @PostMapping("/projects")
    public ResponseEntity<?> createProject(@RequestParam String name) {
        try {
            Project project = gitLabService.createProject(name);
            return ResponseEntity.ok(project);
        } catch (GitLabApiException e) {
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        }
    }

    @GetMapping("/projects")
    public ResponseEntity<?> getProjects() {
        try {
            List<Project> projects = gitLabService.getAllProjects();
            return ResponseEntity.ok(projects);
        } catch (GitLabApiException e) {
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        }
    }
}
