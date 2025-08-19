/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service;

import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.aliyun.vod20170321.models.GetMezzanineInfoRequest;
import com.aliyun.vod20170321.models.GetMezzanineInfoResponse;
import com.course.server.constants.Constants;
import com.course.server.domain.*;
import com.course.server.dto.ActionAllInfoDto;
import com.course.server.dto.MemberActionDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.WxMiniShowListDto;
import com.course.server.enums.*;
import com.course.server.mapper.*;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileSortService {

    private static final Logger LOG = LoggerFactory.getLogger(FileSortService.class);

    @Resource
    private ShareUploadMapper shareUploadMapper;
    @Resource
    private MemberActionMapper memberActionMapper;
    @Resource
    private InstitutionService institutionService;
    @Resource
    private FileSortMapper fileSortMapper;
    @Resource
    private FileMapper fileMapper;
    @Resource
    private SectionMapper sectionMapper;
    @Resource
    private CourseMapper courseMapper;
    @Resource
    private MemberService memberService;

    /**
     * 创建阿里云VOD客户端
     * 使用AccessKey ID和Secret创建VOD服务客户端实例
     *
     * @param accessKeyId 访问密钥ID
     * @param accessKeySecret 访问密钥密文
     * @return 返回VOD客户端实例
     * @throws Exception 创建客户端异常
     */
    private static com.aliyun.vod20170321.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // 您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "vod.cn-shanghai.aliyuncs.com";
        return new com.aliyun.vod20170321.Client(config);
    }

    /**
     * 获取视频原始信息
     * 通过视频ID获取视频的原始文件信息，包括文件URL
     *
     * @param videoId 视频ID
     * @return 返回视频原始文件的URL地址
     * @throws Exception 获取视频信息异常
     */
    private String getVideoOrigInfo(String videoId) throws Exception {
        GetMezzanineInfoResponse mezzanineInfoWithOptions =new GetMezzanineInfoResponse();
        com.aliyun.vod20170321.Client client = FileSortService.createClient(Constants.VOD_ACCESS_KEY_ID, Constants.VOD_ACCESS_KEY_SECRET);
        GetMezzanineInfoRequest getMezzanineInfoRequest = new GetMezzanineInfoRequest()
                .setVideoId(videoId)
                .setOutputType("cdn")
                .setAdditionType("video");
        RuntimeOptions runtime = new RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            mezzanineInfoWithOptions = client.getMezzanineInfoWithOptions(getMezzanineInfoRequest, runtime);
        } catch (TeaException error) {
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        }
        return mezzanineInfoWithOptions.body.getMezzanine().getFileURL();
    }

    /**
     * 在分享视频新建或更新的时候,将file_sort表中创建或更新数据
     * 根据分享视频信息创建或更新文件排序记录
     * 
     * @param randLevel 生成随机数的第一个数字
     * @param shareUpload 分享视频对象
     * @throws Exception 处理异常
     */
    @Transactional
    public void saveShareSortItem(Integer randLevel,ShareUpload shareUpload) throws Exception {
        FileSort fileSort = new FileSort();
        //private String id;
        fileSort.setId(UuidUtil.getShortUuid());
        //private String title;
        fileSort.setTitle(shareUpload.getName());
        //private String videoCover;
        fileSort.setVideoCover(shareUpload.getImage());
        //private String content;
        fileSort.setContent(shareUpload.getSummary());
        //private String createTime;
//            String timeStr = new SimpleDateFormat("yyyy-MM-dd").format(shareUpload.getCreatedAt());
        fileSort.setCreateTime(shareUpload.getCreatedAt());
        //private String videoUrl;
        /*String videoUrl = getVideoOrigInfo(shareUpload.getVod());
        fileSort.setVideoUrl(videoUrl);*/
        fileSort.setVod(shareUpload.getVod());

        Institution institutionByLoginName = institutionService.getInstitutionByLoginName(shareUpload.getCreatorLoginname());
        //private String institution;
        fileSort.setInstitution(institutionByLoginName.getName());
        //private String logoUrl;
        fileSort.setLogoUrl(institutionByLoginName.getLogo());

        //更新排序
        if(randLevel==null || randLevel==0){
            randLevel = 2;
        }
        Double sortDub = Math.random()*10000 + randLevel * 100000;
        fileSort.setSort(sortDub.intValue());

        FileSortExample fileSortExample = new FileSortExample();
        FileSortExample.Criteria criteria2 = fileSortExample.createCriteria();
        criteria2.andVodEqualTo(shareUpload.getVod());
        List<FileSort> fileSorts = fileSortMapper.selectByExample(fileSortExample);
        if(fileSorts.size()>0){
            fileSortMapper.updateByExample(fileSort, fileSortExample);
        } else{
            fileSortMapper.insert(fileSort);
        }
    }

    /**
     * 更新size指定条数的随机排序,以避免每次加载都是同样的视频
     * 为指定数量的文件记录更新随机排序值
     * 
     * @param randLevel 随机数级别
     * @param size 更新的记录数量
     * @throws Exception 处理异常
     */
    @Transactional
    public void updateFileSortRand(Integer randLevel, Integer size) throws Exception {
        //PageHelper.startPage(1, size);
        FileSortExample fileSortExample = new FileSortExample();
        fileSortExample.setOrderByClause("sort asc");
        FileSortExample.Criteria criteria = fileSortExample.createCriteria();
        criteria.andTitleIsNotNull()
                .andContentIsNotNull()
                .andLogoUrlIsNotNull()
                .andVodIsNotNull()
                .andVideoCoverIsNotNull()
                .andInstitutionIsNotNull();

        Calendar calendar = Calendar.getInstance(); //获得当前时间
        calendar.add(Calendar.DATE, (-1) * Constants.NEW_VIDEO_RAND_SORT_DAYS);
        Date startData = calendar.getTime();
        criteria.andCreateTimeGreaterThan(startData);

        List<FileSort> fileSortList = fileSortMapper.selectByExample(fileSortExample);

        for (FileSort fileSort : fileSortList) {

            if(randLevel==null || randLevel==0){
                randLevel = 1;
            }
            Double sortDub = Math.random()*10000 + randLevel * 100000;
            fileSort.setSort(sortDub.intValue());
            fileSortMapper.updateByPrimaryKey(fileSort);
        }
    }

    /**
     * 保存章节排序项
     * 根据章节信息创建或更新文件排序记录
     * 
     * @param randLevel 随机数级别
     * @param section 章节对象
     * @throws Exception 处理异常
     */
    @Transactional
    public void saveSectionSortItem(Integer randLevel,Section section) throws Exception {
        FileSort fileSort = new FileSort();
        //private String id;
        fileSort.setId(UuidUtil.getShortUuid());
        //private String title;
        fileSort.setTitle(section.getTitle());
        //private String createTime;
        fileSort.setCreateTime(section.getCreatedAt());
        //private String videoUrl;
        /*String videoUrl = getVideoOrigInfo(section.getVod());
        fileSort.setVideoUrl(videoUrl);*/
        fileSort.setVod(section.getVod());

        Course course = courseMapper.selectByPrimaryKey(section.getCourseId());
        if(course!=null && course.getImage()!=null && course.getSummary()!=null){
            //private String videoCover;
            fileSort.setVideoCover(course.getImage());
            //private String content;
            fileSort.setContent(course.getSummary());

            Institution institutionByLoginName = institutionService.getInstitutionByLoginName(course.getCreatorLoginname());
            //private String institution;
            fileSort.setInstitution(institutionByLoginName.getName());
            //private String logoUrl;
            fileSort.setLogoUrl(institutionByLoginName.getLogo());
        }

        //写入随机数
        if(randLevel==null || randLevel==0){
            randLevel = 2;
        }
        Double sortDub = Math.random()*10000 + randLevel * 100000;
        fileSort.setSort(sortDub.intValue());

        FileSortExample fileSortExample = new FileSortExample();
        FileSortExample.Criteria criteria2 = fileSortExample.createCriteria();
        criteria2.andVodEqualTo(section.getVod());
        List<FileSort> fileSorts = fileSortMapper.selectByExample(fileSortExample);
        if(fileSorts.size()>0){
            fileSortMapper.updateByExample(fileSort, fileSortExample);
        } else{
            fileSortMapper.insert(fileSort);
        }
    }

/*
    @Transactional
    public void updateSectionSortRand(Integer randLevel, Integer size) throws Exception {
        //PageHelper.startPage(1, size);
        SectionExample sectionExample = new SectionExample();
        sectionExample.setOrderByClause("sort desc");
        SectionExample.Criteria criteria = sectionExample.createCriteria();
        criteria.andTitleIsNotNull()
                .andVodIsNotNull()
                .andChargeNotEqualTo(CourseChargeEnum.CHARGE.getCode())
                .andCreatedAtIsNotNull();

        Calendar calendar = Calendar.getInstance(); //获得当前时间
        calendar.add(Calendar.DATE, (-1) * Constants.NEW_VIDEO_RAND_SORT_DAYS);
        Date startData = calendar.getTime();
        criteria.andCreatedAtGreaterThan(startData);

        List<Section> sectionList = sectionMapper.selectByExample(sectionExample);

        for (Section section : sectionList) {
            FileSort fileSort = new FileSort();
            //查询file_sort表,看记录是否存在,如果存在,更新排序值即可
            FileSortExample fileSortExample = new FileSortExample();
            FileSortExample.Criteria criteria2 = fileSortExample.createCriteria();
            criteria2.andVodEqualTo(section.getVod());
            List<FileSort> fileSorts = fileSortMapper.selectByExample(fileSortExample);
            if (fileSorts.size() > 0) {
                fileSort = CopyUtil.copy(fileSorts.get(0), FileSort.class);
                //更新排序
                if (randLevel == null || randLevel == 0) {
                    randLevel = 2;
                }
                Double sortDub = Math.random() * 10000 + randLevel * 100000;
                fileSort.setSort(sortDub.intValue());
                fileSortMapper.updateByExample(fileSort, fileSortExample);
            }
        }
    }
*/

    /**
     * 用于刷新数据库,生产file_sort表原始数据
     * 生成文件排序表的初始数据，包括分享视频和章节的排序记录
     * 
     * @param shareBeforeData 分享视频的天数限制
     * @param sectionBeforeData 章节的天数限制
     * @throws Exception 处理异常
     */
    @Transactional
    public void generateSortList(Integer shareBeforeData, Integer sectionBeforeData) throws Exception {
        if(shareBeforeData == null || shareBeforeData == 0){
            shareBeforeData = 7;
        }
        if(sectionBeforeData == null || sectionBeforeData == 0){
            sectionBeforeData = 30;
        }

        List<FileSort> fileSortList = new ArrayList<>();
        //准备一张表格,这张表格是周期性生成的,排序已经排好了,前端读取的时候,分页读取就好了
        {
            // 1. 读取share表格,将7天以内的视频读出来,根据sort值进行排序
            ShareUploadExample shareUploadExample = new ShareUploadExample();
            ShareUploadExample.Criteria criteria = shareUploadExample.createCriteria();
            Calendar calendar = Calendar.getInstance(); //获得当前时间
            calendar.add(Calendar.DATE, (-1) * shareBeforeData);
            Date startData = calendar.getTime();
            criteria.andCreatedAtGreaterThan(startData)
                    .andSummaryIsNotNull()
                    .andNameIsNotNull()
                    .andImageIsNotNull();

            List<ShareUpload> shareUploadList1 = shareUploadMapper.selectByExample(shareUploadExample);
            for (ShareUpload shareUpload : shareUploadList1) {
                FileSort fileSort = new FileSort();
                //private String id;
                fileSort.setId(UuidUtil.getShortUuid());
                //private String videoClass;
                fileSort.setVideoClass(VideoClassEnum.SHARE.getCode());
                //private String paramId;
                fileSort.setParamId(shareUpload.getId());
                //private String title;
                fileSort.setTitle(shareUpload.getName());
                //private String vod;
                fileSort.setVod(shareUpload.getVod());
                //private String content;
                if(shareUpload.getSummary().length()>=200){
                    shareUpload.setSummary(shareUpload.getSummary().substring(0,199));
                }
                fileSort.setContent(shareUpload.getSummary());
                //private String videoCover;
                fileSort.setVideoCover(shareUpload.getImage());
                //private String institution;
                Institution institutionByLoginName = institutionService.getInstitutionByLoginName(shareUpload.getCreatorLoginname());
                fileSort.setInstitution(institutionByLoginName.getName());
                //private String logoUrl;
                fileSort.setLogoUrl(institutionByLoginName.getLogo());
                //private Integer sort;
                //计算课程的sort评分
                MemberActionDto memberActionDto = new MemberActionDto();
                memberActionDto.setPageCategory(PageCategoryEnum.SHARE.getCode());
                memberActionDto.setParamId(shareUpload.getId());
                ActionAllInfoDto actionAllInfoDto = memberService.actionAllInfo(memberActionDto);
                Integer sort = actionAllInfoDto.getWatchNum() * MemberActionWeightEnum.WATCH.getCode()
                        + actionAllInfoDto.getLikeNum() * MemberActionWeightEnum.LIKE.getCode()
                        + actionAllInfoDto.getCollectNum() * MemberActionWeightEnum.COLLECT.getCode();
                fileSort.setSort(sort);
                //private Integer rand;
                Double sortDub = Math.random() * 10000 + 100000;
                fileSort.setRand(sortDub.intValue());
                //private Date createTime;
                fileSort.setCreateTime(shareUpload.getCreatedAt());

                fileSortList.add(fileSort);
            }
        }
        {
            // 2. 读取section表格中的数据,将30天以内的数据读出来,根据对应课程的sort进行排序,每个课程选择一个section
            SectionExample sectionExample = new SectionExample();
            SectionExample.Criteria criteria_1 = sectionExample.createCriteria();
            Calendar calendar_1 = Calendar.getInstance(); //获得当前时间
            calendar_1.add(Calendar.DATE, (-1) * sectionBeforeData);
            Date startData_1 = calendar_1.getTime();
            criteria_1.andCreatedAtGreaterThan(startData_1);
            List<Section> sectionList = sectionMapper.selectByExample(sectionExample);
            List<String> courseIdList = sectionList.stream().map(Section::getCourseId).distinct().collect(Collectors.toList());

            for (String courseId : courseIdList) {
                FileSort fileSort = new FileSort();
                Course course = courseMapper.selectByPrimaryKey(courseId);
                if(course==null){
                    continue;
                }
                SectionExample sectionExample1 = new SectionExample();
                SectionExample.Criteria criteria1 = sectionExample1.createCriteria();
                criteria1.andCourseIdEqualTo(course.getId());
                Section section = sectionMapper.selectByExample(sectionExample1).get(0);

                //private String id;
                fileSort.setId(UuidUtil.getShortUuid());
                //private String videoClass;
                fileSort.setVideoClass(VideoClassEnum.COURSE.getCode());
                //private String paramId;   特别注意,这是的paramId是sectionId 不是courseId!!
                fileSort.setParamId(section.getId());
                //private String title;
                fileSort.setTitle(section.getTitle());
                //private String vod;
                fileSort.setVod(section.getVod());
                //private String content;
                fileSort.setContent(course.getSummary());
                //private String videoCover;
                fileSort.setVideoCover(course.getImage());
                //private String institution;
                Institution institutionByLoginName = institutionService.getInstitutionByLoginName(course.getCreatorLoginname());
                fileSort.setInstitution(institutionByLoginName.getName());
                //private String logoUrl;
                fileSort.setLogoUrl(institutionByLoginName.getLogo());
                //private Integer sort;
                //计算课程的sort评分
                MemberActionDto memberActionDto = new MemberActionDto();
                memberActionDto.setPageCategory(PageCategoryEnum.COURSE.getCode());
                memberActionDto.setParamId(course.getId());
                ActionAllInfoDto actionAllInfoDto = memberService.actionAllInfo(memberActionDto);
                Integer sort = actionAllInfoDto.getWatchNum() * MemberActionWeightEnum.WATCH.getCode()
                        + actionAllInfoDto.getLikeNum() * MemberActionWeightEnum.LIKE.getCode()
                        + actionAllInfoDto.getCollectNum() * MemberActionWeightEnum.COLLECT.getCode();
                fileSort.setSort(sort);
                //private Integer rand;
                Double sortDub = Math.random() * 10000 + 100000;
                fileSort.setRand(sortDub.intValue());
                //private Date createTime;
                fileSort.setCreateTime(section.getCreatedAt());

                fileSortList.add(fileSort);
            }
        }
        // 将上述两部分进行随机排序,放在最前面 随机数以1开头
        {
            // 3. 读取share表格7天以前的数据,根据sort评分进行读取
            ShareUploadExample shareUploadExample_3 = new ShareUploadExample();
            ShareUploadExample.Criteria criteria_3 = shareUploadExample_3.createCriteria();
            Calendar calendar_3 = Calendar.getInstance(); //获得当前时间
            calendar_3.add(Calendar.DATE, (-1) * shareBeforeData);
            Date endData3 = calendar_3.getTime();

            Calendar calendar3 = Calendar.getInstance(); //获得当前时间
            calendar3.add(Calendar.DATE, (-1) * sectionBeforeData);
            Date startData3 = calendar3.getTime();

            criteria_3.andCreatedAtGreaterThan(startData3)
                    .andCreatedAtLessThan(endData3)
                    .andSummaryIsNotNull()
                    .andNameIsNotNull()
                    .andImageIsNotNull();

            List<ShareUpload> shareUploadList_3 = shareUploadMapper.selectByExample(shareUploadExample_3);
            for (ShareUpload shareUpload : shareUploadList_3) {
                FileSort fileSort = new FileSort();
                //private String id;
                fileSort.setId(UuidUtil.getShortUuid());
                //private String videoClass;
                fileSort.setVideoClass(VideoClassEnum.SHARE.getCode());
                //private String paramId;
                fileSort.setParamId(shareUpload.getId());
                //private String title;
                fileSort.setTitle(shareUpload.getName());
                //private String vod;
                fileSort.setVod(shareUpload.getVod());
                //private String content;
                if(shareUpload.getSummary().length()>=200){
                    shareUpload.setSummary(shareUpload.getSummary().substring(0,199));
                }
                fileSort.setContent(shareUpload.getSummary());
                //private String videoCover;
                fileSort.setVideoCover(shareUpload.getImage());
                //private String institution;
                Institution institutionByLoginName = institutionService.getInstitutionByLoginName(shareUpload.getCreatorLoginname());
                fileSort.setInstitution(institutionByLoginName.getName());
                //private String logoUrl;
                fileSort.setLogoUrl(institutionByLoginName.getLogo());
                //private Integer sort;
                //计算课程的sort评分
                MemberActionDto memberActionDto = new MemberActionDto();
                memberActionDto.setPageCategory(PageCategoryEnum.SHARE.getCode());
                memberActionDto.setParamId(shareUpload.getId());
                ActionAllInfoDto actionAllInfoDto = memberService.actionAllInfo(memberActionDto);
                Integer sort = actionAllInfoDto.getWatchNum() * MemberActionWeightEnum.WATCH.getCode()
                        + actionAllInfoDto.getLikeNum() * MemberActionWeightEnum.LIKE.getCode()
                        + actionAllInfoDto.getCollectNum() * MemberActionWeightEnum.COLLECT.getCode();
                fileSort.setSort(sort);
                //private Integer rand;
                Double sortDub = Math.random() * 10000 + 200000;
                fileSort.setRand(sortDub.intValue());
                //private Date createTime;
                fileSort.setCreateTime(shareUpload.getCreatedAt());

                fileSortList.add(fileSort);
            }
        }
        // 4. 30天以前的课程每门课程读取一个小节
        {
            SectionExample sectionExample_4 = new SectionExample();
            SectionExample.Criteria criteria_4 = sectionExample_4.createCriteria();
            Calendar calendar_4 = Calendar.getInstance(); //获得当前时间
            calendar_4.add(Calendar.DATE, (-1) * sectionBeforeData);
            Date startData_4 = calendar_4.getTime();
            criteria_4.andCreatedAtLessThan(startData_4);

            List<Section> sectionList_4 = sectionMapper.selectByExample(sectionExample_4);
            List<String> courseIdList_4 = sectionList_4.stream().map(Section::getCourseId).distinct().collect(Collectors.toList());

            for (String courseId : courseIdList_4) {
                FileSort fileSort = new FileSort();
                Course course = courseMapper.selectByPrimaryKey(courseId);
                if(course==null){
                    continue;
                }
                SectionExample sectionExample1 = new SectionExample();
                SectionExample.Criteria criteria1 = sectionExample1.createCriteria();
                criteria1.andCourseIdEqualTo(course.getId());
                Section section = sectionMapper.selectByExample(sectionExample1).get(0);

                //private String id;
                fileSort.setId(UuidUtil.getShortUuid());
                //private String videoClass;
                fileSort.setVideoClass(VideoClassEnum.COURSE.getCode());
                //private String paramId;
                fileSort.setParamId(section.getId());
                //private String title;
                fileSort.setTitle(section.getTitle());
                //private String vod;
                fileSort.setVod(section.getVod());
                //private String content;
                fileSort.setContent(course.getSummary());
                //private String videoCover;
                fileSort.setVideoCover(course.getImage());
                //private String institution;
                Institution institutionByLoginName = institutionService.getInstitutionByLoginName(course.getCreatorLoginname());
                fileSort.setInstitution(institutionByLoginName.getName());
                //private String logoUrl;
                fileSort.setLogoUrl(institutionByLoginName.getLogo());
                //private Integer sort;
                //计算课程的sort评分
                MemberActionDto memberActionDto = new MemberActionDto();
                memberActionDto.setPageCategory(PageCategoryEnum.COURSE.getCode());
                memberActionDto.setParamId(course.getId());
                ActionAllInfoDto actionAllInfoDto = memberService.actionAllInfo(memberActionDto);
                Integer sort = actionAllInfoDto.getWatchNum() * MemberActionWeightEnum.WATCH.getCode()
                        + actionAllInfoDto.getLikeNum() * MemberActionWeightEnum.LIKE.getCode()
                        + actionAllInfoDto.getCollectNum() * MemberActionWeightEnum.COLLECT.getCode();
                fileSort.setSort(sort);
                //private Integer rand;
                Double sortDub = Math.random() * 10000 + 200000;
                fileSort.setRand(sortDub.intValue());
                //private Date createTime;
                fileSort.setCreateTime(section.getCreatedAt());

                fileSortList.add(fileSort);
            }
        }
        // 3.4 两项放在一起进行随机排序,放在1.2 后面 随机数以2开头

        //更新file_sort表
        for(FileSort fileSort : fileSortList){
            FileSortExample fileSortExample = new FileSortExample();
            FileSortExample.Criteria criteria2 = fileSortExample.createCriteria();
            criteria2.andVodEqualTo(fileSort.getVod());
            List<FileSort> fileSorts = fileSortMapper.selectByExample(fileSortExample);
            if(fileSorts.size()>0){
                //update
                fileSortMapper.updateByExample(fileSort, fileSortExample);
            }else{
                //insert
                fileSortMapper.insert(fileSort);
            }
        }
    }




}
