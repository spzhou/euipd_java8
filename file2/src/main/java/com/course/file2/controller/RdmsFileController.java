/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.file2.controller;

import com.alibaba.fastjson.JSON;
import com.course.server.dto.PageDto;
import com.course.server.dto.rdms.*;
import com.course.server.domain.*;
import com.course.server.dto.ResponseDto;
import com.course.server.enums.FileGroupingEnum;
import com.course.server.enums.rdms.CustomerConfigStatusEnum;
import com.course.server.mapper.rdms.MyFileMapper;
import com.course.server.service.rdms.*;
import com.course.server.util.CopyUtil;
import com.course.server.util.ValidatorUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rdms")
public class RdmsFileController {
    private static final Logger LOG = LoggerFactory.getLogger(RdmsFileController.class);
    public static final String BUSINESS_NAME = "文件管理";

    @Resource
    private RdmsFileService rdmsFileService;
    @Resource
    private RdmsFileDownloadService rdmsFileDownloadService;
    @Resource
    private RdmsCustomerConfigService rdmsCustomerConfigService;
    @Resource
    private RdmsCharacterService rdmsCharacterService;
    @Resource
    private RdmsJobItemService rdmsJobItemService;
    @Resource
    private RdmsJobItemProcessService rdmsJobItemProcessService;
    @Resource
    private MyFileMapper myFileMapper;
    @Resource
    private RdmsDemandService rdmsDemandService;
    @Resource
    private RdmsProjectService rdmsProjectService;
    @Resource
    private RdmsFileApplyService rdmsFileApplyService;
    @Resource
    private RdmsCustomerUserService rdmsCustomerUserService;
    @Resource
    private RdmsFileAuthService rdmsFileAuthService;

    @Value("${oss.domain}")
    private String ossDomain;


    /**
     * 分页查询文件列表
     * 获取分页后的文件信息列表
     * 
     * @param pageDto 分页查询参数
     * @return 返回分页后的文件列表数据
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        rdmsFileService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 分页查询申请文件列表
     * 获取分页后的文件申请信息列表
     * 
     * @param pageDto 分页查询参数
     * @return 返回分页后的文件申请列表数据
     */
    @PostMapping("/listApplyFiles")
    public ResponseDto listApplyFiles(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        rdmsFileService.listApplyFiles(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 分页查询文件下载列表
     * 获取分页后的文件下载记录列表
     * 
     * @param pageDto 分页查询参数
     * @return 返回分页后的文件下载列表数据
     */
    @PostMapping("/list-download")
    public ResponseDto listDownload(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        rdmsFileDownloadService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 申请文件
     * 提交文件申请，设置审批人并保存申请信息
     * 
     * @param fileApplyDto 文件申请数据传输对象
     * @return 返回申请结果
     */
    @PostMapping("/applyFile")
    public ResponseDto applyFile(@RequestBody RdmsFileApplyDto fileApplyDto) {
        ResponseDto responseDto = new ResponseDto();
        RdmsFile rdmsFile = rdmsFileService.selectByPrimaryKey(fileApplyDto.getFileId());
        String approverId = rdmsFileService.getApproverId(rdmsFile.getId());
        fileApplyDto.setApproverId(approverId);
        rdmsFileApplyService.save(fileApplyDto);
        responseDto.setContent(fileApplyDto);
        return responseDto;
    }

    /**
     * 批量申请文件
     * 支持批量提交文件申请，自动排除已申请或已有权限的文件
     * 
     * @param fileApplyDto 包含文件ID列表的申请对象
     * @return 返回批量申请结果
     */
    @PostMapping("/applyFileList")
    @Transactional
    public ResponseDto applyFileList(@RequestBody RdmsFileApplyDto fileApplyDto) {
        ResponseDto responseDto = new ResponseDto();
        if(!CollectionUtils.isEmpty(fileApplyDto.getFileIdList())){
            //排除已经在申请中的相同的文件
            List<String> myFileIdList = new ArrayList<>();
            List<RdmsFileApplyDto> fileApplicationingList = rdmsFileApplyService.getFileApplicationingList(fileApplyDto.getCustomerId(), fileApplyDto.getProposerId());
            if(!CollectionUtils.isEmpty(fileApplicationingList)){
                List<String> applicationFileIdList = fileApplicationingList.stream().map(RdmsFileApplyDto::getFileId).collect(Collectors.toList());
                if(!CollectionUtils.isEmpty(applicationFileIdList)){
                    myFileIdList.addAll(applicationFileIdList);
                }
            }

            List<RdmsFileDto> fileListByOperatorId = rdmsFileService.getFileListByOperatorId(fileApplyDto.getCustomerId(), fileApplyDto.getProposerId());
            if(!CollectionUtils.isEmpty(fileListByOperatorId)){
                List<String> myDocFileIdList = fileListByOperatorId.stream().map(RdmsFileDto::getId).collect(Collectors.toList());
                if(!CollectionUtils.isEmpty(myDocFileIdList)){
                    myFileIdList.addAll(myDocFileIdList);
                }
            }

            List<RdmsFileDownloadDto> downloadFileByDownloadorId = rdmsFileDownloadService.getDownloadFileByDownloadorId(fileApplyDto.getCustomerId(), fileApplyDto.getProposerId());
            if(!CollectionUtils.isEmpty(downloadFileByDownloadorId)){
                List<String> downloadFileIdList = downloadFileByDownloadorId.stream().map(RdmsFileDownloadDto::getFileId).collect(Collectors.toList());
                if(!CollectionUtils.isEmpty(downloadFileIdList)){
                    myFileIdList.addAll(downloadFileIdList);
                }
            }

            if(!CollectionUtils.isEmpty(myFileIdList)){
                List<String> applyIdList = fileApplyDto.getFileIdList().stream().filter(item -> !myFileIdList.contains(item)).collect(Collectors.toList());
                for(String fileId: applyIdList){
                    fileApplyDto.setId(null);
                    fileApplyDto.setFileId(fileId);
                    rdmsFileApplyService.save(fileApplyDto);
                }
            }else{
                for(String fileId: fileApplyDto.getFileIdList()){
                    fileApplyDto.setId(null);
                    fileApplyDto.setFileId(fileId);
                    rdmsFileApplyService.save(fileApplyDto);
                }
            }
        }
        responseDto.setContent(fileApplyDto.getFileIdList());
        return responseDto;
    }

    /**
     * 关闭文件申请
     * 根据文件ID和申请人ID删除相关的文件申请记录
     * 
     * @param fileApplyDto 包含文件ID和申请人ID的申请对象
     * @return 返回关闭申请的结果
     */
    @PostMapping("/applyClose")
    public ResponseDto applyClose(@RequestBody RdmsFileApplyDto fileApplyDto) {
        ResponseDto responseDto = new ResponseDto();
        List<RdmsFileApply> fileApplyList = rdmsFileApplyService.getFileApplyByFileIdAndCustomerUserId(fileApplyDto.getFileId(), fileApplyDto.getProposerId());
        for(RdmsFileApply fileApply: fileApplyList){
            rdmsFileApplyService.delete(fileApply.getId());
        }
        responseDto.setContent(fileApplyDto);
        return responseDto;
    }

    /**
     * 根据关键词搜索文件
     * 支持分页的文件关键词搜索功能
     * 
     * @param pageDto 包含搜索关键词的分页查询参数
     * @return 返回分页后的搜索结果
     */
    @PostMapping("/searchByKeyWord")
    public ResponseDto<PageDto<RdmsFileDto>> searchByKeyWord(@RequestBody PageDto<RdmsFileDto> pageDto) {
        ResponseDto<PageDto<RdmsFileDto>> responseDto = new ResponseDto<>();
        rdmsFileService.searchByKeyWord(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/searchDownloadFileByKeyWord")
    public ResponseDto<PageDto<RdmsFileDownloadDto>> searchDownloadFileByKeyWord(@RequestBody PageDto<RdmsFileDownloadDto> pageDto) {
        ResponseDto<PageDto<RdmsFileDownloadDto>> responseDto = new ResponseDto<>();
        rdmsFileDownloadService.searchByKeyWord(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 检索不是自己的文件
     * @param pageDto
     * @return
     */
    @PostMapping("/searchMoreFileByKeyWord")
    public ResponseDto<PageDto<RdmsFileDto>> searchMoreFileByKeyWord(@RequestBody PageDto<RdmsFileDto> pageDto) {
        ResponseDto<PageDto<RdmsFileDto>> responseDto = new ResponseDto<>();
        rdmsFileService.searchMoreFileByKeyWord(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/getApplyNum")
    public ResponseDto<Long> getApplyNum(@RequestBody RdmsHmiIdsDto idsDto) {
        ResponseDto<Long> responseDto = new ResponseDto<>();
        long applyNum = rdmsFileApplyService.getApplyNum(idsDto.getCustomerId(), idsDto.getExecutorId());
        responseDto.setContent(applyNum);
        return responseDto;
    }

    /**
     * 保存客户信息
     */
    @PostMapping("/save")
    @Transactional
    public ResponseDto<RdmsFileDto> save(@RequestBody RdmsFileDto fileDto) {
        ResponseDto<RdmsFileDto> responseDto = new ResponseDto<>();

        //处理客户配置
        String accessKeyId_t;
        String accessKeySecret_t;
        String endpoint_t;
        String bucket_t;
        String ossDomain_t;

        RdmsCustomerConfig customerConfig = rdmsCustomerConfigService.getCustomerConfig(fileDto.getCustomerId());
        if(! ObjectUtils.isEmpty(customerConfig)){
            if(customerConfig.getStatus().equals(CustomerConfigStatusEnum.USED.getStatus())){
                accessKeyId_t = customerConfig.getOssAccessKey();
                accessKeySecret_t = customerConfig.getOssAccessKeySecret();
                endpoint_t = customerConfig.getOssEndpoint();
                bucket_t = customerConfig.getOssBucket();
                ossDomain_t = customerConfig.getOssDomain();
            }else{
                /*accessKeyId_t = accessKeyId;
                accessKeySecret_t = accessKeySecret;
                endpoint_t = endpoint;
                bucket_t = bucket;*/
                ossDomain_t = ossDomain;
            }
        }else{
            /*accessKeyId_t = accessKeyId;
            accessKeySecret_t = accessKeySecret;
            endpoint_t = endpoint;
            bucket_t = bucket;*/
            ossDomain_t = ossDomain;
        }


        ValidatorUtil.require(fileDto.getCustomerId(), "客户ID");
        ValidatorUtil.require(fileDto.getKey(), "文件Key");
        ValidatorUtil.require(fileDto.getPath(), "文件路径");
        ValidatorUtil.require(fileDto.getName(), "文件名");
        ValidatorUtil.require(fileDto.getSize(), "文件大小");
        ValidatorUtil.require(fileDto.getSuffix(), "扩展名");
        ValidatorUtil.require(fileDto.getUse(), "文件分类");

        RdmsFile rdmsFile = CopyUtil.copy(fileDto, RdmsFile.class);
        //如果是用户文件模板,则先将旧文件标记为删除
        if(fileDto.getUse().equals(FileGroupingEnum.USER_INFO_TEMPLATE.getCode())){
            RdmsFileQueryDto fileQueryDto = new RdmsFileQueryDto();
            fileQueryDto.setUse(fileDto.getUse());
            fileQueryDto.setItemId(fileDto.getCustomerId());
            List<RdmsFileDto> fileList = rdmsFileService.getFileListByUseAndItemId(fileQueryDto);
            if(!fileList.isEmpty()){
                for(RdmsFileDto rdmsFileDto: fileList){
                    rdmsFileService.delete(rdmsFileDto.getId());
                }
            }
        }
        rdmsFileService.save(rdmsFile);
        RdmsFileDto rdmsFileDto = CopyUtil.copy(rdmsFile, RdmsFileDto.class);
//        char c = ossDomain_t.charAt(ossDomain_t.length() - 1);
        if(!ossDomain_t.isEmpty() && ossDomain_t.charAt(ossDomain_t.length() - 1)=='/'){
            rdmsFileDto.setAbsPath(ossDomain_t + rdmsFile.getPath());
        }else{
            rdmsFileDto.setAbsPath(ossDomain_t + '/' + rdmsFile.getPath());
        }
        responseDto.setContent(rdmsFileDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("保存成功");
        return responseDto;
    }

    /**
     *根据RdmsAttachmentDto对文件进行查询
     */
    @PostMapping("/list-file-info")
    public ResponseDto<List<RdmsFileDto>> listFileInfo(@RequestBody RdmsAttachmentDto file) {
        //处理客户配置
        String accessKeyId_t;
        String accessKeySecret_t;
        String endpoint_t;
        String bucket_t;
        String ossDomain_t;

        RdmsCustomerConfig customerConfig = rdmsCustomerConfigService.getCustomerConfig(file.getCustomerId());
        if(!ObjectUtils.isEmpty(customerConfig)){
            if(customerConfig.getStatus().equals(CustomerConfigStatusEnum.USED.getStatus())){
                accessKeyId_t = customerConfig.getOssAccessKey();
                accessKeySecret_t = customerConfig.getOssAccessKeySecret();
                endpoint_t = customerConfig.getOssEndpoint();
                bucket_t = customerConfig.getOssBucket();
                ossDomain_t = customerConfig.getOssDomain();
            }else{
                /*accessKeyId_t = accessKeyId;
                accessKeySecret_t = accessKeySecret;
                endpoint_t = endpoint;
                bucket_t = bucket;*/
                ossDomain_t = ossDomain;
            }
        }else{
            /*accessKeyId_t = accessKeyId;
            accessKeySecret_t = accessKeySecret;
            endpoint_t = endpoint;
            bucket_t = bucket;*/
            ossDomain_t = ossDomain;
        }


        ResponseDto<List<RdmsFileDto>> responseDto = new ResponseDto<>();
        List<RdmsFileDto> fileDtos = rdmsFileService.listFileInfo(file);
        for(RdmsFileDto fileDto : fileDtos){
            if(!ossDomain_t.isEmpty() && ossDomain_t.charAt(ossDomain_t.length() - 1)=='/'){
                String absPath = ossDomain_t + fileDto.getPath();
                fileDto.setAbsPath(absPath);
            }else{
                String absPath = ossDomain_t + '/' + fileDto.getPath();
                fileDto.setAbsPath(absPath);
            }
        }
        responseDto.setContent(fileDtos);
        responseDto.setSuccess(true);
        responseDto.setMessage("查询成功");
        return responseDto;
    }

    /**
     * 保存客户信息
     */
    @PostMapping("/getFileAbsPath")
    public ResponseDto<RdmsFileDto> getFileAbsPath(@RequestBody RdmsFileDto file) {
        //处理客户配置
        String accessKeyId_t;
        String accessKeySecret_t;
        String endpoint_t;
        String bucket_t;
        String ossDomain_t;

        RdmsCustomerConfig customerConfig = rdmsCustomerConfigService.getCustomerConfig(file.getCustomerId());
        if(!ObjectUtils.isEmpty(customerConfig)){
            if(customerConfig.getStatus().equals(CustomerConfigStatusEnum.USED.getStatus())){
                accessKeyId_t = customerConfig.getOssAccessKey();
                accessKeySecret_t = customerConfig.getOssAccessKeySecret();
                endpoint_t = customerConfig.getOssEndpoint();
                bucket_t = customerConfig.getOssBucket();
                ossDomain_t = customerConfig.getOssDomain();
            }else{
                /*accessKeyId_t = accessKeyId;
                accessKeySecret_t = accessKeySecret;
                endpoint_t = endpoint;
                bucket_t = bucket;*/
                ossDomain_t = ossDomain;
            }
        }else{
            /*accessKeyId_t = accessKeyId;
            accessKeySecret_t = accessKeySecret;
            endpoint_t = endpoint;
            bucket_t = bucket;*/
            ossDomain_t = ossDomain;
        }

        ResponseDto<RdmsFileDto> responseDto = new ResponseDto<>();
        RdmsFile rdmsFile = rdmsFileService.selectByPrimaryKey(file.getId());
        RdmsFileDto fileDto = CopyUtil.copy(rdmsFile, RdmsFileDto.class);
        if(!ossDomain_t.isEmpty() && ossDomain_t.charAt(ossDomain_t.length() - 1)=='/'){
            String absPath = ossDomain_t + rdmsFile.getPath();
            fileDto.setAbsPath(absPath);
        }else{
            String absPath = ossDomain_t + '/' + rdmsFile.getPath();
            fileDto.setAbsPath(absPath);
        }
        responseDto.setContent(fileDto);
        responseDto.setSuccess(true);
        responseDto.setMessage("查询成功");
        return responseDto;
    }


    /**
     * 通过文件idList查询文件列表
     */
    @PostMapping("/getFileListByCharacterId")
    public ResponseDto<List<RdmsFileDto>> getFileListByIdList(@RequestParam String characterId, String loginUserId) {
        ResponseDto<List<RdmsFileDto>> responseDto = new ResponseDto<>();
        RdmsCharacter character = rdmsCharacterService.selectByPrimaryKey(characterId);
        if(! ObjectUtils.isEmpty(character) && !ObjectUtils.isEmpty(character.getFileListStr())){
            String fileIdsStr = character.getFileListStr();
            List<String> stringList = JSON.parseArray(fileIdsStr, String.class);
            if(!CollectionUtils.isEmpty(stringList)){
                List<RdmsFile> fileListByIdList = myFileMapper.getFileListByIdList(stringList);
                List<RdmsFileDto> fileDtos = new ArrayList<>();
                if(!CollectionUtils.isEmpty(fileListByIdList)){
                    for(RdmsFile file: fileListByIdList){
                        RdmsFileDto fileDto = CopyUtil.copy(file, RdmsFileDto.class);
                        //补充填写文件权限信息
                        RdmsFileAuth byFileId = rdmsFileAuthService.getByFileId(fileDto.getId());
                        if(ObjectUtils.isEmpty(byFileId)){
                            fileDto.setAuthStatus(true);
                        }else{
                            String authIdsStr = byFileId.getAuthIdsStr();
                            if(ObjectUtils.isEmpty(authIdsStr)){
                                fileDto.setAuthStatus(true);
                            }else{
                                List<String> strings = JSON.parseArray(authIdsStr, String.class);
                                if(strings.contains(loginUserId)){
                                    fileDto.setAuthStatus(true);
                                }else{
                                    fileDto.setAuthStatus(false);
                                }
                            }
                        }

                        if(!ossDomain.isEmpty() && ossDomain.charAt(ossDomain.length() - 1)=='/'){
                            String absPath = ossDomain + file.getPath();
                            fileDto.setAbsPath(absPath);
                        }else{
                            String absPath = ossDomain + '/' + file.getPath();
                            fileDto.setAbsPath(absPath);
                        }
                        fileDtos.add(fileDto);
                    }
                }
                responseDto.setContent(fileDtos);
            }else{
                responseDto.setContent(null);
            }
            responseDto.setMessage("查询成功");
        }else{
            responseDto.setMessage("结果为空");
        }
        responseDto.setSuccess(true);
        return responseDto;
    }

    @PostMapping("/getMyArchiveTreeByWorkerId/{workerId}")
    public ResponseDto<List<RdmsHmiTree<RdmsFileDto>>> getMyArchiveTreeByWorkerId(@PathVariable String workerId) {
        ResponseDto<List<RdmsHmiTree<RdmsFileDto>>> responseDto = new ResponseDto<>();
        List<RdmsHmiTree<RdmsFileDto>> myArchiveTreeByWorkerId = rdmsFileService.getMyArchiveTreeByWorkerId(workerId);
        responseDto.setContent(myArchiveTreeByWorkerId);
        return responseDto;
    }

    @PostMapping("/getReferenceArchiveTreeByCharacter")
    public ResponseDto<List<RdmsHmiTree<RdmsFileDto>>> getReferenceArchiveTreeByCharacterId(@RequestParam String characterId, String loginUserId) {
        ResponseDto<List<RdmsHmiTree<RdmsFileDto>>> responseDto = new ResponseDto<>();
        List<RdmsHmiTree<RdmsFileDto>> myArchiveTreeByWorkerId = rdmsFileService.getReferenceArchiveTreeByCharacterId(characterId, loginUserId);
        responseDto.setContent(myArchiveTreeByWorkerId);
        return responseDto;
    }

    @PostMapping("/getArchiveTreeBySubprojectId")
    public ResponseDto<List<RdmsHmiTree<RdmsFileDto>>> getArchiveTreeBySubprojectId(@RequestParam String subprojectId, String loginUserId) {
        ResponseDto<List<RdmsHmiTree<RdmsFileDto>>> responseDto = new ResponseDto<>();
        List<RdmsHmiTree<RdmsFileDto>> ArchiveTreeByProjectId = rdmsFileService.getArchiveTreeBySubprojectId(subprojectId, loginUserId);
        responseDto.setContent(ArchiveTreeByProjectId);
        return responseDto;
    }

    @PostMapping("/getArchiveTreeByPreProjectId")
    public ResponseDto<List<RdmsHmiTree<RdmsFileDto>>> getArchiveTreeByPreProjectId(@RequestParam String preprojectId, String loginUserId) {
        ResponseDto<List<RdmsHmiTree<RdmsFileDto>>> responseDto = new ResponseDto<>();
        List<RdmsHmiTree<RdmsFileDto>> ArchiveTreeByProjectId = rdmsFileService.getArchiveTreeByPreProjectId(preprojectId, loginUserId);
        responseDto.setContent(ArchiveTreeByProjectId);
        return responseDto;
    }

    @PostMapping("/getTestArchiveTreeByProjectId")
    public ResponseDto<List<RdmsHmiTree<RdmsFileDto>>> getTestArchiveTreeByProjectId(@RequestParam String projectId, String loginUserId) {
        ResponseDto<List<RdmsHmiTree<RdmsFileDto>>> responseDto = new ResponseDto<>();
        List<RdmsHmiTree<RdmsFileDto>> ArchiveTreeByProjectId = rdmsFileService.getTestArchiveTreeByProjectId(projectId, loginUserId);
        responseDto.setContent(ArchiveTreeByProjectId);
        return responseDto;
    }

    /**
     * 通过文件idList查询文件列表
     */
    @PostMapping("/getFileListByDemandId")
    public ResponseDto<List<RdmsFileDto>> getFileListByDemandId(@RequestParam String demandId, String loginUserId) {
        ResponseDto<List<RdmsFileDto>> responseDto = new ResponseDto<>();
        RdmsDemand rdmsDemand = rdmsDemandService.selectByPrimaryKey(demandId);
        if(! ObjectUtils.isEmpty(rdmsDemand) && !ObjectUtils.isEmpty(rdmsDemand.getFileListStr())){
            String fileIdsStr = rdmsDemand.getFileListStr();
            List<String> stringList = JSON.parseArray(fileIdsStr, String.class);
            if(!CollectionUtils.isEmpty(stringList)){
                List<RdmsFile> fileListByIdList = myFileMapper.getFileListByIdList(stringList);
                List<RdmsFileDto> fileDtos = new ArrayList<>();
                if(!CollectionUtils.isEmpty(fileListByIdList)){
                    for(RdmsFile file: fileListByIdList){
                        RdmsFileDto fileDto = CopyUtil.copy(file, RdmsFileDto.class);
                        //补充填写文件权限信息
                        RdmsFileAuth byFileId = rdmsFileAuthService.getByFileId(fileDto.getId());
                        if(ObjectUtils.isEmpty(byFileId)){
                            fileDto.setAuthStatus(true);
                        }else{
                            String authIdsStr = byFileId.getAuthIdsStr();
                            if(ObjectUtils.isEmpty(authIdsStr)){
                                fileDto.setAuthStatus(true);
                            }else{
                                List<String> strings = JSON.parseArray(authIdsStr, String.class);
                                if(strings.contains(loginUserId)){
                                    fileDto.setAuthStatus(true);
                                }else{
                                    fileDto.setAuthStatus(false);
                                }
                            }
                        }

                        if(!ossDomain.isEmpty() && ossDomain.charAt(ossDomain.length() - 1)=='/'){
                            String absPath = ossDomain + file.getPath();
                            fileDto.setAbsPath(absPath);
                        }else{
                            String absPath = ossDomain + '/' + file.getPath();
                            fileDto.setAbsPath(absPath);
                        }
                        fileDtos.add(fileDto);
                    }
                }
                responseDto.setContent(fileDtos);
            }else{
                responseDto.setContent(null);
            }
            responseDto.setMessage("查询成功");
        }else{
            responseDto.setMessage("结果为空");
        }
        responseDto.setSuccess(true);
        return responseDto;
    }

    @PostMapping("/getFileSimpleInfo/{fileId}")
    public ResponseDto<RdmsFileDto> getFileSimpleInfo(@PathVariable String fileId) {
        ResponseDto<RdmsFileDto> responseDto = new ResponseDto<>();
        RdmsFile rdmsFile = rdmsFileService.getFileSimpleRecordInfo(fileId);
        RdmsFileDto copy = CopyUtil.copy(rdmsFile, RdmsFileDto.class);
        responseDto.setContent(copy);
        responseDto.setSuccess(true);
        return responseDto;
    }

    @PostMapping("/getFileAuth")
    public ResponseDto<Boolean> getFileAuth(@RequestParam String fileId, String loginUserId) {
        ResponseDto<Boolean> responseDto = new ResponseDto<>();
        RdmsFileAuth byFileId = rdmsFileAuthService.getByFileId(fileId);
        if(ObjectUtils.isEmpty(byFileId)){
            responseDto.setContent(true);
            return responseDto;
        }else{
            String authIdsStr = byFileId.getAuthIdsStr();
            if(ObjectUtils.isEmpty(authIdsStr)){
                responseDto.setContent(true);
                return responseDto;
            }else{
                List<String> strings = JSON.parseArray(authIdsStr, String.class);
                if(strings.contains(loginUserId)){
                    responseDto.setContent(true);
                    return responseDto;
                }else{
                    responseDto.setContent(false);
                    return responseDto;
                }
            }
        }
    }

    /**
     * 通过文件idList查询文件列表
     */
    @PostMapping("/getAllProcessFileListByJobitemId/{jobItemId}")
    public ResponseDto<List<RdmsFileDto>> getFileListByJobitemId(@PathVariable String jobItemId) {
        ResponseDto<List<RdmsFileDto>> responseDto = new ResponseDto<>();
        RdmsJobItem jobItem = rdmsJobItemService.selectByPrimaryKey(jobItemId);
        if(! ObjectUtils.isEmpty(jobItem)){
            List<RdmsJobItemProcess> jobProcessListByJobId = rdmsJobItemProcessService.getJobProcessListByJobId(jobItem.getId());
            List<String> fileIdList = new ArrayList<>();
            if(! CollectionUtils.isEmpty(jobProcessListByJobId)){
                for(RdmsJobItemProcess process: jobProcessListByJobId){
                    List<String> stringList = JSON.parseArray(process.getFileListStr(), String.class);
                    if(! CollectionUtils.isEmpty(stringList)){
                        fileIdList.addAll(stringList);
                    }
                }
            }
            List<RdmsFileDto> fileDtos = new ArrayList<>();
            for(String id: fileIdList){
                RdmsFile rdmsFile = rdmsFileService.selectByPrimaryKey(id);
                RdmsFileDto fileDto = CopyUtil.copy(rdmsFile, RdmsFileDto.class);
                if(!ossDomain.isEmpty() && ossDomain.charAt(ossDomain.length() - 1)=='/'){
                    fileDto.setAbsPath(ossDomain + fileDto.getPath());
                    fileDtos.add(fileDto);
                }else{
                    fileDto.setAbsPath(ossDomain + '/' + fileDto.getPath());
                    fileDtos.add(fileDto);
                }
            }
            responseDto.setContent(fileDtos);
            responseDto.setMessage("查询成功");
        }else{
            responseDto.setMessage("结果为空");
        }
        responseDto.setSuccess(true);
        return responseDto;
    }


    @PostMapping("/getProjectRecordFileList/{projectId}")
    public ResponseDto<List<RdmsFileDto>> getProjectRecordFileList(@PathVariable String projectId) {
        ResponseDto<List<RdmsFileDto>> responseDto = new ResponseDto<>();
        RdmsProject rdmsProject = rdmsProjectService.selectByPrimaryKey(projectId);

        List<RdmsFileDto> fileDtos = new ArrayList<>();
        if(! ObjectUtils.isEmpty(rdmsProject.getFileListStr()) && rdmsProject.getFileListStr().length()>6){
            List<String> fileIdList = JSON.parseArray(rdmsProject.getFileListStr(), String.class);
            for(String id: fileIdList){
                RdmsFile rdmsFile = rdmsFileService.selectByPrimaryKey(id);
                RdmsFileDto fileDto = CopyUtil.copy(rdmsFile, RdmsFileDto.class);
                if(!ossDomain.isEmpty() && ossDomain.charAt(ossDomain.length() - 1)=='/'){
                    fileDto.setAbsPath(ossDomain + fileDto.getPath());
                    fileDtos.add(fileDto);
                }else{
                    fileDto.setAbsPath(ossDomain + '/' + fileDto.getPath());
                    fileDtos.add(fileDto);
                }
            }
        }
        responseDto.setContent(fileDtos);
        return responseDto;
    }

    @PostMapping("/getApproverInfo/{fileId}")
    public ResponseDto<RdmsCustomerUserDto> getApproverInfo(@PathVariable String fileId) {
        ResponseDto<RdmsCustomerUserDto> responseDto = new ResponseDto<>();
        String approverId = rdmsFileService.getApproverId(fileId);
        RdmsCustomerUser rdmsCustomerUser = rdmsCustomerUserService.selectByPrimaryKey(approverId);
        RdmsCustomerUserDto copy = CopyUtil.copy(rdmsCustomerUser, RdmsCustomerUserDto.class);
        responseDto.setContent(copy);
        return responseDto;
    }

    /**
     * 通过文件idList查询文件列表
     */
    @PostMapping("/getFileListByUseAndItemId")
    public ResponseDto<List<RdmsFileDto>> getFileListByUseAndItemId(@RequestBody RdmsFileQueryDto queryDto) {
        ResponseDto<List<RdmsFileDto>> responseDto = new ResponseDto<>();
        List<RdmsFileDto> fileList = rdmsFileService.getFileListByUseAndItemId(queryDto);
        if(!CollectionUtils.isEmpty(fileList)){
            List<RdmsFileDto> rdmsFileDtos = CopyUtil.copyList(fileList, RdmsFileDto.class);

            List<RdmsFileDto> fileDtos = new ArrayList<>();
            for(RdmsFileDto fileDto: rdmsFileDtos){
                //补充填写文件权限信息
                RdmsFileAuth byFileId = rdmsFileAuthService.getByFileId(fileDto.getId());
                if(ObjectUtils.isEmpty(byFileId)){
                    fileDto.setAuthStatus(true);
                }else{
                    String authIdsStr = byFileId.getAuthIdsStr();
                    if(ObjectUtils.isEmpty(authIdsStr)){
                        fileDto.setAuthStatus(true);
                    }else{
                        List<String> strings = JSON.parseArray(authIdsStr, String.class);
                        if(strings.contains(queryDto.getOperatorId())){
                            fileDto.setAuthStatus(true);
                        }else{
                            fileDto.setAuthStatus(false);
                        }
                    }
                }


                if(!ossDomain.isEmpty() && ossDomain.charAt(ossDomain.length() - 1)=='/'){
                    fileDto.setAbsPath(ossDomain + fileDto.getPath());
                    fileDtos.add(fileDto);
                }else{
                    fileDto.setAbsPath(ossDomain + '/' + fileDto.getPath());
                    fileDtos.add(fileDto);
                }
            }
            responseDto.setContent(fileDtos);
        }
        return responseDto;
    }

    @PostMapping("/getFileListByJobitemId")
    public ResponseDto<List<RdmsFileDto>> getFileListByJobitemId(@RequestParam String jobitemId, String loginUserId) {
        ResponseDto<List<RdmsFileDto>> responseDto = new ResponseDto<>();
        List<RdmsFileDto> fileList = rdmsFileService.getFileListByJobitemId(jobitemId);
        if(!CollectionUtils.isEmpty(fileList)){
            List<RdmsFileDto> rdmsFileDtos = CopyUtil.copyList(fileList, RdmsFileDto.class);

            List<RdmsFileDto> fileDtos = new ArrayList<>();
            for(RdmsFileDto fileDto: rdmsFileDtos){
                //补充填写文件权限信息
                RdmsFileAuth byFileId = rdmsFileAuthService.getByFileId(fileDto.getId());
                if(ObjectUtils.isEmpty(byFileId)){
                    fileDto.setAuthStatus(true);
                }else{
                    String authIdsStr = byFileId.getAuthIdsStr();
                    if(ObjectUtils.isEmpty(authIdsStr)){
                        fileDto.setAuthStatus(true);
                    }else{
                        List<String> strings = JSON.parseArray(authIdsStr, String.class);
                        if(strings.contains(loginUserId)){
                            fileDto.setAuthStatus(true);
                        }else{
                            fileDto.setAuthStatus(false);
                        }
                    }
                }
                fileDtos.add(fileDto);
            }
            responseDto.setContent(fileDtos);
        }
        return responseDto;
    }

    @PostMapping("/getApplyList/{approverId}")
    public ResponseDto<List<RdmsFileApplyDto>> getApplyList(@PathVariable String approverId) {
        ResponseDto<List<RdmsFileApplyDto>> responseDto = new ResponseDto<>();
        List<RdmsFileApplyDto> applyList = rdmsFileApplyService.getApplyList(approverId);
        responseDto.setContent(applyList);
        return responseDto;
    }

    @PostMapping("/getApplyInfo/{applyId}")
    public ResponseDto<RdmsFileApplyDto> getApplyInfo(@PathVariable String applyId) {
        ResponseDto<RdmsFileApplyDto> responseDto = new ResponseDto<>();
        RdmsFileApplyDto applyInfo = rdmsFileApplyService.getApplyInfo(applyId);
        responseDto.setContent(applyInfo);
        return responseDto;
    }

    @PostMapping("/applyFile-agree/{applyId}")
    public ResponseDto<String> applyApprove(@PathVariable String applyId) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        rdmsFileApplyService.applyApprove(applyId);
        responseDto.setContent(applyId);
        return responseDto;
    }

    @PostMapping("/applyFile-reject/{applyId}")
    public ResponseDto<String> applyReject(@PathVariable String applyId) {
        ResponseDto<String> responseDto = new ResponseDto<>();
        rdmsFileApplyService.applyReject(applyId);
        responseDto.setContent(applyId);
        return responseDto;
    }

    /**
     * 删除
     */
    @DeleteMapping("/del/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        rdmsFileService.delete(id);
        return responseDto;
    }

}
