/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.mapper.calculate;

import com.course.server.domain.calculate.CalManhour;
import com.course.server.domain.rdms.MyCharacter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyManhourMapper {
    CalManhour getJobitemManhourInfo(@Param("jobitemId") String jobitemId);
}
