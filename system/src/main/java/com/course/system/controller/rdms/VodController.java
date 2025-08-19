/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.system.controller.rdms;

import com.aliyun.vod20170321.models.GetVideoPlayAuthResponse;
import com.course.server.dto.ResponseDto;
import com.course.server.service.rdms.RdmsAliyunVodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
//@CrossOrigin
@RequestMapping("/vod")
public class VodController {

    @Resource
    RdmsAliyunVodService rdmsAliyunVodService;
    private static final Logger LOG = LoggerFactory.getLogger(VodController.class);
    public static final String BUSINESS_NAME = "Vod控制";

    @GetMapping("/getVideoPlayAuth/{videoId}")
    public ResponseDto getVideoPlayAuth(@PathVariable String videoId) throws Exception {
        ResponseDto responseDto = new ResponseDto();
        GetVideoPlayAuthResponse getVideoPlayAuthResponse = rdmsAliyunVodService.GetVideoPlayAuth(videoId);
        responseDto.setContent(getVideoPlayAuthResponse);
        return responseDto;
    }

}


