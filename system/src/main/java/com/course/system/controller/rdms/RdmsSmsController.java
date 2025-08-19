/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.system.controller.rdms;

import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.RdmsSmsDto;
import com.course.server.service.rdms.RdmsSmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("RdmsSmsController")
@RequestMapping("/rdms/sms")
public class RdmsSmsController {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsSmsController.class);
    public static final String BUSINESS_NAME = "SMS短信";

    @Resource
    private RdmsSmsService rdmsSmsService;

    /**
     * 发送短信验证码
     * 接收短信发送请求并调用短信服务发送验证码
     * 
     * @param rdmsSmsDto 短信发送数据传输对象
     * @return 返回发送结果
     */
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public ResponseDto send(@RequestBody RdmsSmsDto rdmsSmsDto) {
        LOG.info("发送短信请求开始: {}", rdmsSmsDto);
        ResponseDto responseDto = new ResponseDto();
        rdmsSmsService.sendCode(rdmsSmsDto);
        LOG.info("发送短信请求结束");
        return responseDto;
    }

/*    @RequestMapping(value = "/send-job-notify", method = RequestMethod.POST)
    public ResponseDto sendJobNotify(@RequestBody RdmsSmsInfoDto rdmsSmsInfoDto) {
        LOG.info("发送短信请求开始: {}", rdmsSmsInfoDto);
        ResponseDto responseDto = new ResponseDto();
        rdmsSmsService.sendJobNotify(rdmsSmsInfoDto);
        LOG.info("发送短信请求结束");
        return responseDto;
    }*/


}
