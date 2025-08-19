/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.course.server.domain.RdmsGanttLinks;
import com.course.server.domain.RdmsGanttLinksExample;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.RdmsGanttLinksMapper;
import com.course.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class RdmsGanttLinksService {

    private static final Logger LOG = LoggerFactory.getLogger(RdmsGanttLinksService.class);

    @Resource
    private RdmsGanttLinksMapper rdmsGanttLinksMapper;


    @Transactional
    public List<RdmsGanttLinks> getListBySourceIdList(List<String> sourceIdList){
        RdmsGanttLinksExample rdmsGanttLinksExample = new RdmsGanttLinksExample();
        rdmsGanttLinksExample.createCriteria()
                .andSourceIn(sourceIdList)
                .andDeletedEqualTo(0);
        List<RdmsGanttLinks> rdmsGanttLinkss = rdmsGanttLinksMapper.selectByExample(rdmsGanttLinksExample);
        if(CollectionUtils.isEmpty(rdmsGanttLinkss)){
            return null;
        }else{
            return rdmsGanttLinkss;
        }
    }

    public RdmsGanttLinks selectByPrimaryKey(String id) {
        return rdmsGanttLinksMapper.selectByPrimaryKey(id);
    }
    /**
     * 保存
     */
    @Transactional
    public String save(RdmsGanttLinks rdmsGanttLinks) {
        rdmsGanttLinks.setDeleted(0);
        if(!ObjectUtils.isEmpty(rdmsGanttLinks) && !ObjectUtils.isEmpty(rdmsGanttLinks.getId())){
            RdmsGanttLinks rdmsGanttLinks1 = this.selectByPrimaryKey(rdmsGanttLinks.getId());
            if(!ObjectUtils.isEmpty(rdmsGanttLinks1)){
                return this.update(rdmsGanttLinks);
            }else{
                return this.insert(rdmsGanttLinks);
            }
        }else{
            return this.insert(rdmsGanttLinks);
        }
    }

    /**
     * 新增
     */
    @Transactional
    public String insert(RdmsGanttLinks rdmsGanttLinks) {
        if (rdmsGanttLinks == null) {
            return null;  // 或根据业务需求抛出 IllegalArgumentException
        }
        if (ObjectUtils.isEmpty(rdmsGanttLinks.getId())) {
            rdmsGanttLinks.setId(UuidUtil.getShortUuid());
        }
        RdmsGanttLinks existing = this.selectByPrimaryKey(rdmsGanttLinks.getId());
        if (existing != null) {
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_EXIST);
        }
        rdmsGanttLinksMapper.insert(rdmsGanttLinks);
        return rdmsGanttLinks.getId();
    }


    /**
     * 更新
     */
    @Transactional
    public String update(RdmsGanttLinks rdmsGanttLinks) {
        if(ObjectUtils.isEmpty(rdmsGanttLinks) || ObjectUtils.isEmpty(rdmsGanttLinks.getId())){
            throw new BusinessException(BusinessExceptionCode.INPUT_ERROR);
        }
        RdmsGanttLinks rdmsGanttLinks1 = this.selectByPrimaryKey(rdmsGanttLinks.getId());
        if(!ObjectUtils.isEmpty(rdmsGanttLinks1)){
            rdmsGanttLinks.setId(rdmsGanttLinks1.getId());
            rdmsGanttLinksMapper.updateByPrimaryKey(rdmsGanttLinks);
            return rdmsGanttLinks.getId();
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

    /**
     * 删除操作----设置删除标志位
     */
    public void delete(String id) {
        RdmsGanttLinks ganttLink = rdmsGanttLinksMapper.selectByPrimaryKey(id);
        if(!ObjectUtils.isEmpty(ganttLink)){
            ganttLink.setDeleted(1);
            this.update(ganttLink);
        }else{
            throw new BusinessException(BusinessExceptionCode.DB_RECORD_NOT_EXIST);
        }
    }

}
