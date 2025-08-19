/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service;

import com.course.server.domain.Test;
import com.course.server.domain.TestExample;
import com.course.server.mapper.TestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestService {

    @Resource
    private TestMapper testMapper;

    /**
     * 获取测试列表
     * 查询ID为1的测试记录，按ID倒序排列
     * 
     * @return 返回测试记录列表
     */
    public List<Test> list() {
        TestExample testExample = new TestExample();
        testExample.createCriteria().andIdEqualTo("1");
        testExample.setOrderByClause("id desc");
        return testMapper.selectByExample(testExample);
    }
}
