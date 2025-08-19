/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.course.server.domain.*;
import com.course.server.dto.PageDto;
import com.course.server.dto.rdms.RdmsDemandDto;
import com.course.server.dto.rdms.RdmsHmiNotifyDto;
import com.course.server.enums.rdms.CharacterStatusEnum;
import com.course.server.enums.rdms.DemandStatusEnum;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsDemandMapper;
import com.course.server.mapper.rdms.MyDemandMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class RdmsDemandService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsDemandService.class);

    @Resource
    private RdmsDemandMapper rdmsDemandMapper;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private RdmsPreProjectService rdmsProjectPrepareService;
    @Resource
    private MyDemandMapper myDemandMapper;


    public RdmsDemand selectByPrimaryKey(String demandId){
        return rdmsDemandMapper.selectByPrimaryKey(demandId);
    }

    @Transactional
    public Integer getCountOfDemandByUserID(String userId) {
        RdmsDemandExample rdmsDemandExample = new RdmsDemandExample();
        rdmsDemandExample.createCriteria()
                .andStatusEqualTo(DemandStatusEnum.SAVED.getStatus())
                .andWriterIdEqualTo(userId)
                .andDeletedEqualTo(0);
        return (Integer) (int)rdmsDemandMapper.countByExample(rdmsDemandExample);
    }

    public List<RdmsDemand> getDemandListByIdList(List<String> ids){
        return myDemandMapper.getDemandListByIdList(ids);
    }

    public String updateByPrimaryKeySelective(RdmsDemand demand){
        rdmsDemandMapper.updateByPrimaryKeySelective(demand);
        return demand.getId();
    }

    public long getDemandHandlingNum(String customerId, String preProjectId, String userId){
        RdmsDemandExample rdmsDemandExample = new RdmsDemandExample();
        rdmsDemandExample.createCriteria()
                .andCustomerIdEqualTo(customerId)
                .andPreProjectIdEqualTo(preProjectId)
                .andNextNodeEqualTo(userId)
                .andStatusEqualTo(DemandStatusEnum.SUBMIT.getStatus())
                .andDeletedEqualTo(0);
        return rdmsDemandMapper.countByExample(rdmsDemandExample);
    }

    public RdmsHmiNotifyDto getDemandJobNotify(RdmsHmiNotifyDto notifyDto) {
        RdmsDemandExample demandExample = new RdmsDemandExample();
        RdmsDemandExample.Criteria criteria = demandExample.createCriteria().andDeletedEqualTo(0);
        if(! ObjectUtils.isEmpty(notifyDto.getCustomerId())){
            criteria.andCustomerIdEqualTo(notifyDto.getCustomerId());
        }
        if(! ObjectUtils.isEmpty(notifyDto.getActorId())){
            criteria.andNextNodeEqualTo(notifyDto.getActorId());
        }
        if(! ObjectUtils.isEmpty(notifyDto.getStatus())){
            criteria.andStatusEqualTo(notifyDto.getStatus());
        }

        long l = rdmsDemandMapper.countByExample(demandExample);
        notifyDto.setCount((int)l );
        notifyDto.setFlag(l > 0);
        return notifyDto;
    }

    /**
     * 列表需求编辑者保存的尚未提交的需求草稿
     * @param pageDto: 机构 customerId,  需求编辑者 actor
     */
    public void listSavedDemandByCustomerId(PageDto<RdmsDemandDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsDemandExample rdmsDemandExample = new RdmsDemandExample();
        rdmsDemandExample.setOrderByClause("create_time desc");
        rdmsDemandExample.createCriteria()
                .andCustomerIdEqualTo(pageDto.getKeyWord())
                .andNextNodeEqualTo(pageDto.getActor())
                .andStatusEqualTo(DemandStatusEnum.SAVED.getStatus())
                .andDeletedEqualTo(0);
        List<RdmsDemand> rdmsDemandDiscerns = rdmsDemandMapper.selectByExample(rdmsDemandExample);

        PageInfo<RdmsDemand> pageInfo = new PageInfo<>(rdmsDemandDiscerns);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsDemandDto> rdmsDemandDiscernDtos = CopyUtil.copyList(rdmsDemandDiscerns, RdmsDemandDto.class);
        //对日期时间进行格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(RdmsDemandDto demandDiscernDto : rdmsDemandDiscernDtos){
            demandDiscernDto.setCreateTimeStr(sdf.format(demandDiscernDto.getCreateTime()));
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(demandDiscernDto.getWriterId());
            demandDiscernDto.setWriterName(rdmsCustomerUser.getTrueName());
            RdmsPreProject rdmsProjectPrepare = rdmsProjectPrepareService.selectByPrimaryKey(demandDiscernDto.getPreProjectId());
            demandDiscernDto.setPreProjectName(rdmsProjectPrepare.getPreProjectName());
        }
        pageDto.setList(rdmsDemandDiscernDtos);
    }

    public void listSubmitDemandByPreProjectId(PageDto<RdmsDemandDto> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsDemandExample rdmsDemandExample = new RdmsDemandExample();
        rdmsDemandExample.setOrderByClause("create_time desc");
        rdmsDemandExample.createCriteria()
                .andPreProjectIdEqualTo(pageDto.getKeyWord())
                .andNextNodeEqualTo(pageDto.getActor())  //产品经理是自己
                .andStatusEqualTo(DemandStatusEnum.SUBMIT.getStatus())
                .andDeletedEqualTo(0);
        List<RdmsDemand> rdmsDemandDiscerns = rdmsDemandMapper.selectByExample(rdmsDemandExample);

        PageInfo<RdmsDemand> pageInfo = new PageInfo<>(rdmsDemandDiscerns);
        pageDto.setTotal(pageInfo.getTotal());

        List<RdmsDemandDto> rdmsDemandDiscernDtos = CopyUtil.copyList(rdmsDemandDiscerns, RdmsDemandDto.class);
        //对日期时间进行格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(RdmsDemandDto demandDiscernDto : rdmsDemandDiscernDtos){
            demandDiscernDto.setCreateTimeStr(sdf.format(demandDiscernDto.getCreateTime()));
            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(demandDiscernDto.getWriterId());
            demandDiscernDto.setWriterName(rdmsCustomerUser.getTrueName());
        }
        pageDto.setList(rdmsDemandDiscernDtos);
    }

    /**
     * 保存
     */
    public String save(RdmsDemand demand) {
        if(ObjectUtils.isEmpty(demand.getNextNode())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        if(ObjectUtils.isEmpty(demand.getId())){
            return this.insert(demand);
        }else{
            RdmsDemand rdmsDemandDiscern = this.selectByPrimaryKey(demand.getId());
            if(ObjectUtils.isEmpty(rdmsDemandDiscern)){
                return this.insert(demand);
            }else{
                return this.update(demand);
            }
        }
    }

    private String insert(RdmsDemand demand) {
        if(org.springframework.util.ObjectUtils.isEmpty(demand.getId())){  //当前端页面给出projectID时,将不为空
            demand.setId(UuidUtil.getShortUuid());
        }
        RdmsDemand rdmsDemandDiscern = this.selectByPrimaryKey(demand.getId());
        if(ObjectUtils.isEmpty(rdmsDemandDiscern)){
            demand.setDeleted(0);
            demand.setCreateTime(new Date());
            rdmsDemandMapper.insert(demand);
            return demand.getId();
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }
    }

    public String update(RdmsDemand demand) {
        if(ObjectUtils.isEmpty(demand.getId())){
            throw new BusinessException(BusinessExceptionCode.DATA_RECORD_ERROR);
        }
        RdmsDemand rdmsDemandDiscern = this.selectByPrimaryKey(demand.getId());
        if(ObjectUtils.isEmpty(rdmsDemandDiscern)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            demand.setDeleted(0);
            demand.setCreateTime(rdmsDemandDiscern.getCreateTime());
            rdmsDemandMapper.updateByPrimaryKey(demand);
            return demand.getId();
        }
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsDemand rdmsDemandDiscern1 = this.selectByPrimaryKey(id);
        if(ObjectUtils.isEmpty(rdmsDemandDiscern1)){
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }else{
            rdmsDemandDiscern1.setDeleted(1);
            rdmsDemandMapper.updateByPrimaryKey(rdmsDemandDiscern1);
        }
    }

}
