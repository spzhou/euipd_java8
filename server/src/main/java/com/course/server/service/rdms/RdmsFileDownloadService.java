/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.rdms;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.*;
import com.course.server.dto.PageDto;
import com.course.server.dto.rdms.RdmsAttachmentDto;
import com.course.server.dto.rdms.RdmsFileDownloadDto;
import com.course.server.dto.rdms.RdmsFileQueryDto;
import com.course.server.mapper.RdmsFileDownloadMapper;
import com.course.server.mapper.RdmsFileMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class RdmsFileDownloadService {

    @Resource
    private RdmsFileDownloadMapper rdmsFileDownloadMapper;
    @Resource
    private RdmsCustomerService rdmsCustomerService;
    @Resource
    private RdmsSubprojectService rdmsSubprojectService;
    @Resource
    private RdmsPreProjectService rdmsPreProjectService;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;

    /**
     * 列表查询
     */
    @Transactional
    public void list(PageDto<RdmsFileDownloadDto> pageDto) {
        //确认公司和管理员是否对应
        if(!ObjectUtils.isEmpty(pageDto.getCustomerId()) && !ObjectUtils.isEmpty(pageDto.getActor())){
            //一般用户
            PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
            RdmsFileDownloadExample fileDownloadExample = new RdmsFileDownloadExample();
            fileDownloadExample.setOrderByClause("download_time desc");
            fileDownloadExample.createCriteria()
                    .andCustomerIdEqualTo(pageDto.getCustomerId())
                    .andDownloadorIdEqualTo(pageDto.getActor())
                    .andDeletedEqualTo(0);
            List<RdmsFileDownload> rdmsFileDownloads = rdmsFileDownloadMapper.selectByExample(fileDownloadExample);
            PageInfo<RdmsFileDownload> pageInfo = new PageInfo<>(rdmsFileDownloads);
            pageDto.setTotal(pageInfo.getTotal());
            List<RdmsFileDownloadDto> fileDownloadDtoList = CopyUtil.copyList(rdmsFileDownloads, RdmsFileDownloadDto.class);
            if(! CollectionUtils.isEmpty(fileDownloadDtoList)){
                for(RdmsFileDownloadDto fileDto: fileDownloadDtoList){
                    RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(fileDto.getOperatorId());
                    fileDto.setOperatorName(rdmsCustomerUser.getTrueName());

                    RdmsCustomerUser rdmsCustomerUser1 = rdmsCustomerUserService.selectByPrimaryKey(fileDto.getDownloadorId());
                    fileDto.setDownloadorName(rdmsCustomerUser1.getTrueName());

                    if(!ObjectUtils.isEmpty(fileDto.getSubprojectId())){
                        RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(fileDto.getSubprojectId());
                        fileDto.setProjectName(subproject.getLabel());
                    }else if(!ObjectUtils.isEmpty(fileDto.getPreProjectId())){
                        RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(fileDto.getPreProjectId());
                        fileDto.setProjectName(rdmsPreProject.getPreProjectName());
                    }
                }
            }
            pageDto.setList(fileDownloadDtoList);

            /*RdmsCustomer rdmsCustomer = rdmsCustomerService.selectByPrimaryKey(pageDto.getCustomerId());
            List<RdmsCustomerUser> adminUser = rdmsCustomerUserService.findCustomerUserByLoginName(rdmsCustomer.getContactPhone());
            if(!CollectionUtils.isEmpty(adminUser)){
                if(adminUser.get(0).getId().equals(pageDto.getActor())){
                    //超级管理员
                    PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
                    RdmsFileDownloadExample fileDownloadExample = new RdmsFileDownloadExample();
                    fileDownloadExample.setOrderByClause("download_time desc");
                    fileDownloadExample.createCriteria().andCustomerIdEqualTo(pageDto.getCustomerId()).andDeletedEqualTo(0);
                    List<RdmsFileDownload> rdmsFileDownloads = rdmsFileDownloadMapper.selectByExample(fileDownloadExample);
                    PageInfo<RdmsFileDownload> pageInfo = new PageInfo<>(rdmsFileDownloads);
                    pageDto.setTotal(pageInfo.getTotal());
                    List<RdmsFileDownloadDto> fileDownloadDtoList = CopyUtil.copyList(rdmsFileDownloads, RdmsFileDownloadDto.class);
                    if(! CollectionUtils.isEmpty(fileDownloadDtoList)){
                        for(RdmsFileDownloadDto fileDto: fileDownloadDtoList){
                            RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(fileDto.getOperatorId());
                            fileDto.setOperatorName(rdmsCustomerUser.getTrueName());

                            RdmsCustomerUser rdmsCustomerUser1 = rdmsCustomerUserService.selectByPrimaryKey(fileDto.getDownloadorId());
                            fileDto.setDownloadorName(rdmsCustomerUser1.getTrueName());

                            if(!ObjectUtils.isEmpty(fileDto.getSubprojectId())){
                                RdmsProjectSubproject subproject = rdmsSubprojectService.selectByPrimaryKey(fileDto.getSubprojectId());
                                fileDto.setProjectName(subproject.getLabel());
                            }else if(!ObjectUtils.isEmpty(fileDto.getPreProjectId())){
                                RdmsPreProject rdmsPreProject = rdmsPreProjectService.selectByPrimaryKey(fileDto.getPreProjectId());
                                if(! ObjectUtils.isEmpty(rdmsPreProject)){
                                    fileDto.setProjectName(rdmsPreProject.getPreProjectName());
                                }
                            }
                        }
                    }
                    pageDto.setList(fileDownloadDtoList);
                }else{

                }
            }*/
        }
    }

    @Transactional
    public void searchByKeyWord(PageDto<RdmsFileDownloadDto> pageDto) {
        if(!ObjectUtils.isEmpty(pageDto.getCustomerId())){
            PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
            RdmsFileDownloadExample fileDownloadExample = new RdmsFileDownloadExample();
            fileDownloadExample.setOrderByClause("download_time desc");
            fileDownloadExample.createCriteria()
                    .andCustomerIdEqualTo(pageDto.getCustomerId())
                    .andDownloadorIdEqualTo(pageDto.getActor())
                    .andNameLike("%"+ pageDto.getKeyWord() +"%")
                    .andDeletedEqualTo(0);
            List<RdmsFileDownload> rdmsFileDownloads = rdmsFileDownloadMapper.selectByExample(fileDownloadExample);
            PageInfo<RdmsFileDownload> pageInfo = new PageInfo<>(rdmsFileDownloads);
            pageDto.setTotal(pageInfo.getTotal());
            List<RdmsFileDownloadDto> fileDownloadDtoList = CopyUtil.copyList(rdmsFileDownloads, RdmsFileDownloadDto.class);
            if(! CollectionUtils.isEmpty(fileDownloadDtoList)){
                for(RdmsFileDownloadDto fileDto: fileDownloadDtoList){
                    RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(fileDto.getOperatorId());
                    fileDto.setOperatorName(rdmsCustomerUser.getTrueName());
                }
            }
            pageDto.setList(fileDownloadDtoList);
        }
    }

    /**
     * 使用idList进行查询
     */
    public RdmsFileDownloadDto getDownloadFileByFileId(String fileId) {
        if(!ObjectUtils.isEmpty(fileId)){
            RdmsFileDownloadExample fileDownloadExample = new RdmsFileDownloadExample();
            RdmsFileDownloadExample.Criteria criteria = fileDownloadExample.createCriteria();
            criteria
                    .andDeletedEqualTo(0)
                    .andFileIdEqualTo(fileId);
            List<RdmsFileDownload> rdmsFileDownloads = rdmsFileDownloadMapper.selectByExample(fileDownloadExample);
            if(!CollectionUtils.isEmpty(rdmsFileDownloads)){
                List<RdmsFileDownloadDto> fileDtos = CopyUtil.copyList(rdmsFileDownloads, RdmsFileDownloadDto.class);
                return fileDtos.get(0);
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    public List<RdmsFileDownloadDto> getDownloadFileByDownloadorId(String customerId,String downloadorId) {
        if(!ObjectUtils.isEmpty(downloadorId)){
            RdmsFileDownloadExample fileDownloadExample = new RdmsFileDownloadExample();
            RdmsFileDownloadExample.Criteria criteria = fileDownloadExample.createCriteria();
            criteria
                    .andDeletedEqualTo(0)
                    .andCustomerIdEqualTo(customerId)
                    .andDownloadorIdEqualTo(downloadorId);
            List<RdmsFileDownload> rdmsFileDownloads = rdmsFileDownloadMapper.selectByExample(fileDownloadExample);
            if(!CollectionUtils.isEmpty(rdmsFileDownloads)){
                List<RdmsFileDownloadDto> fileDtos = CopyUtil.copyList(rdmsFileDownloads, RdmsFileDownloadDto.class);
                return fileDtos;
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    public String save(RdmsFileDownload rdmsFileDownload) {
        if(rdmsFileDownload.getDownloadTime()==null){
            rdmsFileDownload.setDownloadTime(new Date());
        }
        rdmsFileDownload.setDeleted(0);

        if(rdmsFileDownload.getId() == null || rdmsFileDownload.getId().equals("")){
            return this.insert(rdmsFileDownload);
        }else{
            RdmsFileDownloadExample fileDownloadExample = new RdmsFileDownloadExample();
            fileDownloadExample.createCriteria().andIdEqualTo(rdmsFileDownload.getId());
            List<RdmsFileDownload> rdmsFileDownloads = rdmsFileDownloadMapper.selectByExample(fileDownloadExample);
            if(!CollectionUtils.isEmpty(rdmsFileDownloads)){
                return this.update(rdmsFileDownload);
            }else{
                return this.insert(rdmsFileDownload);
            }
        }
    }

    /**
     * 新增
     */
    private String insert(RdmsFileDownload file) {
        if(ObjectUtils.isEmpty(file.getId())){  //当前端页面给出projectID时,将不为空
            file.setId(UuidUtil.getShortUuid());
        }
        Date now = new Date();
        file.setDownloadTime(now);
        rdmsFileDownloadMapper.insert(file);
        return file.getId();
    }

    /**
     * 更新
     */
    public String update(RdmsFileDownload file) {
        file.setUpdateTime(new Date());
        rdmsFileDownloadMapper.updateByPrimaryKey(file);
        return file.getId();
    }

    /**
     * 删除
     */
    public String delete(String id) {
        RdmsFileDownload rdmsFileDownload = rdmsFileDownloadMapper.selectByPrimaryKey(id);
        if(!ObjectUtils.isEmpty(rdmsFileDownload)){
            rdmsFileDownload.setDeleted(1);
            return this.update(rdmsFileDownload);
        }else{
            return null;
        }
    }

    /**
     *
     */
    public RdmsFileDownload selectByPrimaryKey(String fileId) {
        return rdmsFileDownloadMapper.selectByPrimaryKey(fileId);
    }

    public RdmsFileDownloadDto getFileSimpleRecordInfo(String fileId) {
        RdmsFileDownload rdmsFileDownload = this.selectByPrimaryKey(fileId);
        if(!ObjectUtils.isEmpty(rdmsFileDownload)){
            RdmsFileDownloadDto fileDto = CopyUtil.copy(rdmsFileDownload, RdmsFileDownloadDto.class);
            return fileDto;
        }else{
            return null;
        }

    }

}
