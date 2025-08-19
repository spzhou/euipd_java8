/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.customer.controller.calculate;

import com.course.server.domain.RdmsCustomerUser;
import com.course.server.domain.RdmsCustomerUserJobLevel;
import com.course.server.domain.RdmsCustomerUserJobLevelExample;
import com.course.server.domain.RdmsJobItem;
import com.course.server.domain.calculate.CalManhour;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.rdms.RdmsJobItemDto;
import com.course.server.mapper.RdmsCustomerUserJobLevelMapper;
import com.course.server.mapper.calculate.MyManhourMapper;
import com.course.server.service.rdms.RdmsCustomerUserJobLevelService;
import com.course.server.service.rdms.RdmsCustomerUserService;
import com.course.server.service.rdms.RdmsJobItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/cal-manhour")
public class CalManhourController {
    private static final Logger LOG = LoggerFactory.getLogger(CalManhourController.class);
    public static final String BUSINESS_NAME = "工时/工时费计算";

    @Resource
    private MyManhourMapper myManhourMapper;
    @Resource
    private RdmsJobItemService rdmsJobItemService;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private RdmsCustomerUserJobLevelService rdmsCustomerUserJobLevelService;

    /**
     * 获取工项工时信息
     * 根据父工项ID获取辅助工项的剩余工时信息
     * 
     * @param parentJobitemId 父工项ID
     * @return 返回工时计算结果
     */
    @PostMapping("/getJobitemManhourInfo/{parentJobitemId}")
    @Transactional
    public ResponseDto<CalManhour> assistJobitemRemainHour(@PathVariable String parentJobitemId) {
        ResponseDto<CalManhour> responseDto = new ResponseDto<>();

        CalManhour jobitemManhourInfo = myManhourMapper.getJobitemManhourInfo(parentJobitemId);

        responseDto.setContent(jobitemManhourInfo);
        responseDto.setSuccess(true);
        responseDto.setMessage("数据获取成功");
        return responseDto;
    }


    /**
     * 保存客户信息
     */
    @PostMapping("/getRemainJobitemManhourInfo/{parentJobitemId}")
    @Transactional
    public ResponseDto<Double> getRemainJobitemManhourInfo(@PathVariable String parentJobitemId) {
        ResponseDto<Double> responseDto = new ResponseDto<>();

        CalManhour jobitemManhourInfo = myManhourMapper.getJobitemManhourInfo(parentJobitemId);
        double remainFee = jobitemManhourInfo.getJobItemManhour() * jobitemManhourInfo.getUserManHourFee().floatValue();

        List<RdmsJobItemDto> rdmsJobItemDtos = rdmsJobItemService.getJobitemListByParentJobitemId(parentJobitemId, null, null);
        if(!CollectionUtils.isEmpty(rdmsJobItemDtos)){
            for(RdmsJobItemDto jobItemDto: rdmsJobItemDtos){
                RdmsCustomerUser customerUser = rdmsCustomerUserService.selectByPrimaryKey(jobItemDto.getExecutorId());
                RdmsCustomerUserJobLevel jobLevelByLevelCode = rdmsCustomerUserJobLevelService.getJobLevelByLevelCode(customerUser.getJobLevel(), customerUser.getCustomerId());
                double fee = jobItemDto.getManhour() * jobLevelByLevelCode.getManHourFee().doubleValue();
                remainFee -= fee;
            }
        }
        responseDto.setContent(remainFee);
        responseDto.setSuccess(true);
        responseDto.setMessage("数据获取成功");
        return responseDto;
    }



}
