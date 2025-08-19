/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller;

import com.course.server.dto.RdmsChapterDto;
import com.course.server.dto.RdmsChapterPageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.RdmsChapterService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/chapter")
public class ChapterController {

    private static final Logger LOG = LoggerFactory.getLogger(ChapterController.class);
    public static final String BUSINESS_NAME = "篇章";

    @Resource
    private RdmsChapterService rdmsChapterService;

    /**
     * 列表查询
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody RdmsChapterPageDto chapterPageDto) {
        ResponseDto responseDto = new ResponseDto();
        ValidatorUtil.require(chapterPageDto.getCourseId(), "课程ID");
        rdmsChapterService.list(chapterPageDto);
        responseDto.setContent(chapterPageDto);
        return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody RdmsChapterDto chapterDto) {
        // 保存校验
        ValidatorUtil.require(chapterDto.getName(), "名称");
        ValidatorUtil.require(chapterDto.getCourseId(), "课程ID");
        ValidatorUtil.length(chapterDto.getCourseId(), "课程ID", 1, 8);

        ResponseDto responseDto = new ResponseDto();
        rdmsChapterService.save(chapterDto);
        responseDto.setContent(chapterDto);
        return responseDto;
    }

    @PostMapping("/getChapterInfo/{chapterId}")
    public ResponseDto getChapterInfo(@PathVariable String chapterId) {
        // 保存校验
        ValidatorUtil.require(chapterId, "章节ID");

        ResponseDto responseDto = new ResponseDto();
        RdmsChapterDto chapterInfo = rdmsChapterService.getChapterInfo(chapterId);
        responseDto.setContent(chapterInfo);
        return responseDto;
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        rdmsChapterService.delete(id);
        return responseDto;
    }
}
