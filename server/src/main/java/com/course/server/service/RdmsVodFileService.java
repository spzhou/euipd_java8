/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service;

import com.course.server.domain.*;
import com.course.server.domain.RdmsVodFile;
import com.course.server.dto.PageDto;
import com.course.server.dto.rdms.RdmsVodFileDto;
import com.course.server.mapper.RdmsVodFileMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

@Service
public class RdmsVodFileService {

    @Resource
    private RdmsVodFileMapper rdmsVodFileMapper;

    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RdmsVodFileExample fileExample = new RdmsVodFileExample();
        List<RdmsVodFile> rdmsVodFiles = rdmsVodFileMapper.selectByExample(fileExample);
        PageInfo<RdmsVodFile> pageInfo = new PageInfo<>(rdmsVodFiles);
        pageDto.setTotal(pageInfo.getTotal());
        List<RdmsVodFileDto> fileDtoList = CopyUtil.copyList(rdmsVodFiles, RdmsVodFileDto.class);
        pageDto.setList(fileDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(RdmsVodFileDto fileDto) {
        RdmsVodFile file = CopyUtil.copy(fileDto, RdmsVodFile.class);
        RdmsVodFile fileDb = selectByKey(fileDto.getKey());
        if (fileDb == null) {
            this.insert(file);
        } else {
            fileDb.setShardIndex(fileDto.getShardIndex());

            this.update(fileDb);
        }
    }

    /**
     * 新增
     */
    private void insert(RdmsVodFile file) {
        Date now = new Date();
        file.setCreatedAt(now);
        file.setUpdatedAt(now);
        file.setId(UuidUtil.getShortUuid());
        rdmsVodFileMapper.insert(file);
    }

    /**
     * 更新
     */
    public void update(RdmsVodFile file) {
        file.setUpdatedAt(new Date());
        rdmsVodFileMapper.updateByPrimaryKey(file);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        rdmsVodFileMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据文件标识查询VOD文件
     * 通过文件标识查询对应的VOD文件记录
     * 
     * @param key 文件标识
     * @return 返回VOD文件对象，如果不存在则返回null
     */
    public RdmsVodFile selectByKey(String key) {
        RdmsVodFileExample example = new RdmsVodFileExample();
        example.createCriteria().andKeyEqualTo(key);
        List<RdmsVodFile> fileList = rdmsVodFileMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(fileList)) {
            return null;
        } else {
            return fileList.get(0);
        }
    }

    /**
     * 根据文件标识查询数据库记录
     * 通过文件标识查询VOD文件并转换为DTO对象
     * 
     * @param key 文件标识
     * @return 返回VOD文件DTO对象
     */
    public RdmsVodFileDto findByKey(String key) {
        return CopyUtil.copy(selectByKey(key), RdmsVodFileDto.class);
    }

    /**
     * 下载视频
     * 从指定URL下载视频文件到本地路径
     * 
     * @param videoUrl 视频网络地址
     * @param downloadPath 视频保存地址
     * @return 返回下载是否成功
     */
    public boolean downVideo(String videoUrl, String downloadPath) {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        RandomAccessFile randomAccessFile = null;
        boolean re;
        try {

            URL url = new URL(videoUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Range", "bytes=0-");
            connection.connect();
            if (connection.getResponseCode() / 100 != 2) {
                System.out.println("连接失败...");
                return false;
            }
            inputStream = connection.getInputStream();
            int downloaded = 0;
            int fileSize = connection.getContentLength();
            randomAccessFile = new RandomAccessFile(downloadPath, "rw");
            while (downloaded < fileSize) {
                byte[] buffer = null;
                if (fileSize - downloaded >= 1000000) {
                    buffer = new byte[1000000];
                } else {
                    buffer = new byte[fileSize - downloaded];
                }
                int read = -1;
                int currentDownload = 0;
                long startTime = System.currentTimeMillis();
                while (currentDownload < buffer.length) {
                    read = inputStream.read();
                    buffer[currentDownload++] = (byte) read;
                }
                long endTime = System.currentTimeMillis();
                double speed = 0.0;
                if (endTime - startTime > 0) {
                    speed = currentDownload / 1024.0 / ((double) (endTime - startTime) / 1000);
                }
                randomAccessFile.write(buffer);
                downloaded += currentDownload;
                randomAccessFile.seek(downloaded);
                System.out.printf(downloadPath+"下载了进度:%.2f%%,下载速度：%.1fkb/s(%.1fM/s)%n", downloaded * 1.0 / fileSize * 10000 / 100,
                        speed, speed / 1000);
            }
            re = true;
            return re;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            re = false;
            return re;
        } catch (IOException e) {
            e.printStackTrace();
            re = false;
            return re;
        } finally {
            try {
                connection.disconnect();
                inputStream.close();
                randomAccessFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 下载临时素材接口
     * 从指定URL下载文件并保存到本地路径
     * 
     * @param filePath 文件将要保存的目录
     * @param method 请求方法，包括POST和GET
     * @param url 请求的路径
     * @return 返回保存的文件对象
     */
    public java.io.File saveUrlAs(String url,String filePath,String method){
        //System.out.println("fileName---->"+filePath);
        //创建不同的文件夹目录
        java.io.File file=new java.io.File(filePath);
        //判断文件夹是否存在
        if (!file.exists())
        {
            //如果文件夹不存在，则创建新的的文件夹
            file.mkdirs();
        }
        FileOutputStream fileOut = null;
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        try
        {
            // 建立链接
            URL httpUrl=new URL(url);
            conn=(HttpURLConnection) httpUrl.openConnection();
            //以Post方式提交表单，默认get方式
            conn.setRequestMethod(method);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            // post方式不能使用缓存
            conn.setUseCaches(false);
            //连接指定的资源
            conn.connect();
            //获取网络输入流
            inputStream=conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            //判断文件的保存路径后面是否以/结尾
            if (!filePath.endsWith("/")) {

                filePath += "/";

            }
            //写入到文件（注意文件保存路径的后面一定要加上文件的名称）
            fileOut = new FileOutputStream(filePath+"123.png");
            BufferedOutputStream bos = new BufferedOutputStream(fileOut);

            byte[] buf = new byte[4096];
            int length = bis.read(buf);
            //保存文件
            while(length != -1)
            {
                bos.write(buf, 0, length);
                length = bis.read(buf);
            }
            bos.close();
            bis.close();
            conn.disconnect();
        } catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("抛出异常！！");
        }

        return file;
    }

    /**
     * 根据Vod查询是否存在相应记录
     * 通过VOD标识查询对应的文件记录
     * 
     * @param vod VOD标识
     * @return 返回VOD文件DTO对象，如果不存在则返回null
     */
    public RdmsVodFileDto findRecordByVod(String vod) {
        if(vod==null){
            return null;
        }
        RdmsVodFileExample example = new RdmsVodFileExample();
        example.createCriteria().andVodEqualTo(vod);
        List<RdmsVodFile> fileList = rdmsVodFileMapper.selectByExample(example);
        if(fileList.size()>0){
            return CopyUtil.copy(fileList.get(0), RdmsVodFileDto.class);
        }
        return null;
    }

}
