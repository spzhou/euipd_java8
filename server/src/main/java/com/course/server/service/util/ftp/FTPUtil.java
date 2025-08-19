/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.service.util.ftp;

import cn.hutool.core.util.CharsetUtil;
import com.course.server.enums.ftp.DownloadStatusEnum;
import com.course.server.enums.ftp.UploadStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * FTP工具类
 *
 * @author YJ2023085043
 */
@Component
@Slf4j
public class FTPUtil {

    /**
     * ftp 连接池
     */
    @Autowired
    private FTPPoolService ftpPoolService;

    @Autowired
    private FTPConfig ftpConfig;

    public static final String DIR_SPLIT = "/";

    public static final String HTTP_protocol = "http://";


    /**
     * 上传单个文件
     *
     * @param uploadPath 上传路径
     * @param fileName   文件名
     * @param input      文件输入流
     * @return 上传结果
     */
    public UploadStatusEnum upload(String uploadPath, String fileName, InputStream input) throws IOException {
        FTPClient ftpClient = ftpPoolService.borrowObject();

        this.createDirectory(uploadPath, ftpClient);
        String directory = uploadPath.substring(1, uploadPath.lastIndexOf("/"));  //相对路径
        try {
            // 文件写入
            // 切换到工作目录
            if (!ftpClient.changeWorkingDirectory(ftpConfig.getWorkingDirectory() + "/" +directory)) {   //相对于跟目录的绝对路径
                ftpClient.makeDirectory(directory);
                ftpClient.changeWorkingDirectory(directory);
            }
            // 文件写入
            boolean storeFile = ftpClient.storeFile(fileName, input);
            if (storeFile) {
                log.info("文件:{}上传成功", fileName);
                return UploadStatusEnum.UploadNewFileSuccess;
            } else {
                throw new RuntimeException("ftp文件写入异常");
            }
        } catch (IOException e) {
            log.error("文件:{}上传失败", fileName, e);
            return UploadStatusEnum.UploadNewFileFailed;
        } finally {
            IOUtils.closeQuietly(input);
            ftpPoolService.returnObject(ftpClient);
        }
    }

    /**
     * 从FTP服务器上 下载文件,支持断点续传，下载百分比汇报
     *
     * @param ftpPath 远程文件路径
     * @param fileName 远程文件名
     * @param local  本地文件完整绝对路径
     * @return 下载的状态
     * @throws IOException
     */
    public DownloadStatusEnum downloadFile(String ftpPath, String fileName, String local) throws IOException {
        FTPClient ftpClient = ftpPoolService.borrowObject();
        // 设置被动模式,由于Linux安全性考虑，端口没有全部放开，所有被动模式不能用
        ftpClient.enterLocalPassiveMode();
        // 设置以二进制方式传输
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        DownloadStatusEnum result;
        try {
            // 检查远程文件是否存在
            FTPFile[] files = ftpClient.listFiles(ftpPath,file -> file.getName().equals(fileName));
            if (files.length != 1) {
                log.info("远程文件不存在");
                return DownloadStatusEnum.RemoteFileNotExist;
            }
            long lRemoteSize = files[0].getSize();
            File f = new File(local+DIR_SPLIT+fileName);
            // 本地存在文件，进行断点下载
            if (f.exists()) {
                long localSize = f.length();
                // 判断本地文件大小是否大于远程文件大小
                if (localSize >= lRemoteSize) {
                    log.info("本地文件大于远程文件，下载中止");
                    return DownloadStatusEnum.LocalFileBiggerThanRemoteFile;
                }
                // 进行断点续传，并记录状态
                FileOutputStream out = new FileOutputStream(f, true);
                ftpClient.setRestartOffset(localSize);
                InputStream in = ftpClient.retrieveFileStream(ftpPath + DIR_SPLIT + fileName);
                byte[] bytes = new byte[1024];
                long step = lRemoteSize / 100;
                // 文件过小，step可能为0
                step = step == 0 ? 1 : step;
                long process = localSize / step;
                int c;
                while ((c = in.read(bytes)) != -1) {
                    out.write(bytes, 0, c);
                    localSize += c;
                    long nowProcess = localSize / step;
                    if (nowProcess > process) {
                        process = nowProcess;
                        if (process % 10 == 0) {
                            log.info("下载进度：" + process);
                        }
                    }
                }
                in.close();
                out.close();
                boolean isDo = ftpClient.completePendingCommand();
                if (isDo) {
                    result = DownloadStatusEnum.DownloadFromBreakSuccess;
                } else {
                    result = DownloadStatusEnum.DownloadFromBreakFailed;
                }
            } else {
                OutputStream out = new FileOutputStream(f);
                InputStream in = ftpClient.retrieveFileStream(ftpPath + DIR_SPLIT + fileName);
                byte[] bytes = new byte[1024];
                long step = lRemoteSize / 100;
                // 文件过小，step可能为0
                step = step == 0 ? 1 : step;
                long process = 0;
                long localSize = 0L;
                int c;
                while ((c = in.read(bytes)) != -1) {
                    out.write(bytes, 0, c);
                    localSize += c;
                    long nowProcess = localSize / step;
                    if (nowProcess > process) {
                        process = nowProcess;
                        if (process % 10 == 0) {
                            log.info("下载进度：" + process);
                        }
                    }
                }
                in.close();
                out.close();
                boolean upNewStatus = ftpClient.completePendingCommand();
                if (upNewStatus) {
                    result = DownloadStatusEnum.DownloadNewSuccess;
                } else {
                    result = DownloadStatusEnum.DownloadNewFailed;
                }
            }
        } catch (Exception e) {
            log.error("download error", e);
        } finally {
            ftpPoolService.returnObject(ftpClient);
        }
        return DownloadStatusEnum.DownloadNewFailed;
    }

    /**
     * 下载文件到本地 *
     *
     * @param ftpPath     FTP服务器文件目录 *
     * @param ftpFileName 文件名称 *
     * @param localPath   下载后的文件路径 *
     * @return
     */
    public boolean download(String ftpPath, String ftpFileName, String localPath) {
        FTPClient ftpClient = ftpPoolService.borrowObject();
        OutputStream outputStream = null;
        try {
            FTPFile[] ftpFiles = ftpClient.listFiles(ftpPath, file -> file.isFile() && file.getName().equals(ftpFileName));
            if (ftpFiles != null && ftpFiles.length > 0) {
                FTPFile ftpFile = ftpFiles[0];
                File localFile = new File(localPath + DIR_SPLIT + ftpFile.getName());
                // 判断本地路径目录是否存在，不存在则创建
                if (!localFile.getParentFile().exists()) {
                    localFile.getParentFile().mkdirs();
                }
                outputStream = Files.newOutputStream(localFile.toPath());
                ftpClient.retrieveFile(ftpFile.getName(), outputStream);

                log.info("fileName:{},size:{}", ftpFile.getName(), ftpFile.getSize());
                log.info("下载文件成功...");
                return true;
            } else {
                log.info("文件不存在，filePathname:{},", ftpPath + DIR_SPLIT + ftpFileName);
            }
        } catch (Exception e) {
            log.error("下载文件失败...",e);
        } finally {
            IOUtils.closeQuietly(outputStream);
            ftpPoolService.returnObject(ftpClient);
        }
        return false;
    }

    /**
     * 下载文件到浏览器 *
     *
     * @param ftpPath     FTP服务器文件目录 *
     * @param ftpFileName 文件名称 *
     * @param response
     * @return
     */
    public void download(HttpServletResponse response, String ftpPath, String ftpFileName)  {
        FTPClient ftpClient = ftpPoolService.borrowObject();
        OutputStream outputStream = null;
        try {
            FTPFile[] ftpFiles = ftpClient.listFiles(ftpPath, file -> file.isFile() && file.getName().equals(ftpFileName));
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("utf8");
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(ftpFileName,"UTF-8") );
            outputStream = response.getOutputStream();
            if (ftpFiles != null && ftpFiles.length > 0) {
                FTPFile ftpFile = ftpFiles[0];
                ftpClient.retrieveFile(ftpPath+DIR_SPLIT+ftpFile.getName(), outputStream);

                log.info("fileName:{},size:{}", ftpFile.getName(), ftpFile.getSize());
                log.info("下载文件成功...");
            } else {
                log.info("文件不存在，filePathname:{},", ftpPath + DIR_SPLIT + ftpFileName);
            }
        } catch (Exception e) {
            log.error("下载文件失败...",e);
        } finally {
            IOUtils.closeQuietly(outputStream);
            ftpPoolService.returnObject(ftpClient);
        }
    }

    public void ftpZipFileDownload (HttpServletResponse response,String ftpPath) {
        //从FTP上下载文件并打成ZIP包给用户下载
        ZipOutputStream zipOut = null;
        try {
            //文件名称
            String zipFileName = "导出数据.zip";
            response.reset();
            // 设置导出文件头
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(zipFileName,"UTF-8") );
            // 定义Zip输出流
            zipOut = new ZipOutputStream(response.getOutputStream());
            zipFTPFile(ftpPath,zipOut,"");
        } catch (IOException e) {
            log.error("当前："+ftpPath+"下载FTP文件--->下载文件失败："+e.getMessage());
        } finally {
            // 关闭zip文件输出流
            if (null != zipOut) {
                try {
                    zipOut.closeEntry();
                    zipOut.close();
                } catch (IOException e) {
                    log.error("当前："+ftpPath+"下载FTP文件--->关闭zip文件输出流出错："+e.getMessage());
                }
            }
        }
    }

    public void zipFTPFile(String ftpPath, ZipOutputStream zipOut,String foldPath){
        FTPClient ftpClient = ftpPoolService.borrowObject();
        try {
            // 切换到指定目录中，如果切换失败说明目录不存在
            if(!ftpClient.changeWorkingDirectory(ftpPath)){
                log.error("切换目录失败");
                throw new RuntimeException("切换目录失败");
            }
            // 每次数据连接之前,ftp client告诉ftp server开通一个端口来传输数据
            ftpClient.enterLocalPassiveMode();
            // 遍历路径下的所有文件
            FTPFile[] fileList = ftpClient.listFiles();
            byte[] byteReader = new byte[1024];
            ByteArrayOutputStream os = null;
            for (FTPFile tempFile : fileList) {
                if (tempFile.isFile()) {
                    os = new ByteArrayOutputStream();
                    // 从FTP上下载downFileName该文件把该文件转化为字节数组的输出流
                    ftpClient.retrieveFile(tempFile.getName(), os);
                    byte[] bytes = os.toByteArray();
                    InputStream ins = new ByteArrayInputStream(bytes);

                    int len;
                    zipOut.putNextEntry(new ZipEntry(foldPath + tempFile.getName()));
                    // 读入需要下载的文件的内容，打包到zip文件
                    while ((len = ins.read(byteReader)) > 0) {
                        zipOut.write(byteReader, 0, len);
                    }
                }else{
                    //如果是文件夹，则递归调用该方法
                    zipOut.putNextEntry(new ZipEntry(tempFile.getName() + DIR_SPLIT));
                    zipFTPFile(ftpPath + DIR_SPLIT + tempFile.getName(), zipOut, tempFile.getName() + DIR_SPLIT);
                }
            }
            zipOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            ftpPoolService.returnObject(ftpClient);
        }
    }

    /**
     * 得到某个目录下的文件名列表
     *
     * @param ftpDirPath FTP上的目标文件路径
     * @return
     * @throws IOException
     */
    public List<String> getFileList(String ftpDirPath) {
        FTPClient ftpClient = ftpPoolService.borrowObject();
        try {
            FTPFile[] ftpFiles = ftpClient.listFiles(ftpDirPath);
            if (ftpFiles != null && ftpFiles.length > 0) {
                return Arrays.stream(ftpFiles).map(FTPFile::getName).collect(Collectors.toList());
            }
            log.error(String.format("路径有误，或目录【%s】为空", ftpDirPath));
        } catch (Exception e) {
            log.error("获取目录下文件列表失败", e);
        } finally {
            ftpPoolService.returnObject(ftpClient);
        }
        return null;
    }

    /**
     * 删除文件
     *
     * @param ftpPath 服务器文件存储路径
     * @param fileName 文件名
     * @return
     * @throws IOException
     */
    public boolean deleteFile(String ftpPath, String fileName) {
        FTPClient ftpClient = ftpPoolService.borrowObject();
        try {
            // 在 ftp 目录下获取文件名与 fileName 匹配的文件信息
            FTPFile[] ftpFiles = ftpClient.listFiles(ftpPath, file -> file.getName().equals(fileName));
            // 删除文件
            if (ftpFiles != null && ftpFiles.length > 0) {
                boolean del;
                String deleteFilePath = ftpPath + DIR_SPLIT + fileName;
                FTPFile ftpFile = ftpFiles[0];
                if (ftpFile.isDirectory()) {
                    //递归删除该目录下的所有文件后删除目录
                    FTPFile[] files = ftpClient.listFiles(ftpPath + DIR_SPLIT + fileName);
                    for (FTPFile file : files) {
                        if(file.isDirectory()){
                            deleteFile(ftpPath + DIR_SPLIT + fileName,file.getName());
                        }else{
                            del = ftpClient.deleteFile(deleteFilePath + DIR_SPLIT + file.getName());
                            log.info(del ? "文件:{}删除成功" : "文件:{}删除失败", file.getName());
                        }
                    }
                    del = ftpClient.removeDirectory(deleteFilePath);
                } else {
                    del = ftpClient.deleteFile(deleteFilePath);
                }
                log.info(del ? "文件:{}删除成功" : "文件:{}删除失败", fileName);
                return del;
            } else {
                log.warn("文件:{}未找到", fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            ftpPoolService.returnObject(ftpClient);
        }
        return false;
    }

    /**
     * 上传文件到FTP服务器，支持断点续传
     * @param uploadPath 远程文件存放路径
     * @param fileName 上传文件名
     * @param input 文件输入流
     * @return 上传结果
     * @throws IOException
     */
    public UploadStatusEnum uploadFile(String uploadPath, String fileName, InputStream input) {
        FTPClient ftpClient = ftpPoolService.borrowObject();
        UploadStatusEnum result = UploadStatusEnum.UploadNewFileFailed;
        try {
            // 设置PassiveMode传输
            ftpClient.enterLocalPassiveMode();
            // 设置以二进制流的方式传输
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.setControlEncoding(CharsetUtil.UTF_8);
            //切换到工作目录
            if(!ftpClient.changeWorkingDirectory(uploadPath)){
                ftpClient.makeDirectory(uploadPath);
                ftpClient.changeWorkingDirectory(uploadPath);
            }
            // 检查远程是否存在文件
            FTPFile[] files = ftpClient.listFiles(uploadPath,file -> file.getName().equals(fileName));
            if (files.length == 1) {
                long remoteSize = files[0].getSize();
                //根据文件输入流获取文件对象
                File f = getFileFromInputStream(input);
                long localSize = f.length();
                // 文件存在
                if (remoteSize == localSize) {
                    return UploadStatusEnum.FileExits;
                } else if (remoteSize > localSize) {
                    return UploadStatusEnum.RemoteFileBiggerThanLocalFile;
                }
                // 尝试移动文件内读取指针,实现断点续传
                result = uploadFile(fileName, f, ftpClient, remoteSize);
                // 如果断点续传没有成功，则删除服务器上文件，重新上传
                if (result == UploadStatusEnum.UploadFromBreakFailed) {
                    if (!ftpClient.deleteFile(fileName)) {
                        return UploadStatusEnum.DeleteRemoteFaild;
                    }
                    result = uploadFile(fileName, f, ftpClient, 0);
                }
            } else {
                result = uploadFile(fileName, getFileFromInputStream(input), ftpClient, 0);
            }
        } catch (Exception e) {
            log.error("上传文件失败", e);
        } finally {
            ftpPoolService.returnObject(ftpClient);
        }
        return result;
    }

    /**
     * 从输入流中获取文件对象
     * @param inputStream
     * @return
     */
    public static File getFileFromInputStream(InputStream inputStream) {
        File file = null;
        try {
            // 创建临时文件
            file = File.createTempFile("temp", null);

            // 将输入流写入临时文件
            byte[] buffer = new byte[1024];
            int bytesRead;
            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 递归创建远程服务器目录
     *
     * @param remote    远程服务器文件绝对路径
     * @param ftpClient FTPClient对象
     * @return 目录创建是否成功
     * @throws IOException
     */

    public UploadStatusEnum createDirectory(String remote, FTPClient ftpClient) throws IOException {
        UploadStatusEnum status = UploadStatusEnum.CreateDirectorySuccess;
        String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
        if (!directory.equalsIgnoreCase("/") && !ftpClient.changeWorkingDirectory(new String(directory.getBytes(CharsetUtil.UTF_8), CharsetUtil.ISO_8859_1))) {
            // 如果远程目录不存在，则递归创建远程服务器目录
            int start = 0;
            int end = 0;
            if (directory.startsWith("/")) {
                start = 1;
            } else {
                start = 0;
            }
            end = directory.indexOf("/", start);
            while (true) {
                String subDirectory = new String(remote.substring(start, end).getBytes(CharsetUtil.UTF_8), CharsetUtil.ISO_8859_1);
                if (!ftpClient.changeWorkingDirectory(subDirectory)) {
                    if (ftpClient.makeDirectory(subDirectory)) {
                        ftpClient.changeWorkingDirectory(subDirectory);
                    } else {
                        log.info("创建目录失败");
                        return UploadStatusEnum.CreateDirectoryFail;
                    }
                }
                start = end + 1;
                end = directory.indexOf("/", start);
                // 检查所有目录是否创建完毕
                if (end <= start) {
                    break;
                }
            }
        }
        return status;
    }

    /**
     * 上传文件到服务器,新上传和断点续传
     *
     * @param remoteFileName 远程文件名，在上传之前已经将服务器工作目录做了改变，一定要注意这里的 remoteFile 已经别被编码 ISO-8859-1
     * @param localFile  本地文件File句柄，绝对路径
     * @param ftpClient  FTPClient引用
     * @return
     * @throws IOException
     */
    public UploadStatusEnum uploadFile(String remoteFileName, File localFile, FTPClient ftpClient, long remoteSize) {
        if (null == ftpClient) {
            ftpClient = ftpPoolService.borrowObject();
        }
        if (null == ftpClient) {
            return null;
        }
        UploadStatusEnum status = UploadStatusEnum.UploadNewFileFailed;

        try (RandomAccessFile raf = new RandomAccessFile(localFile, "r");
             OutputStream out = ftpClient.appendFileStream(remoteFileName);) {
            // 显示进度的上传
            log.info("localFile.length():" + localFile.length());
            long step = localFile.length() / 100;
            // 文件过小，step可能为0
            step = step == 0 ? 1 : step;
            long process = 0;
            long localreadbytes = 0L;

            // 断点续传
            if (remoteSize > 0) {
                ftpClient.setRestartOffset(remoteSize);
                process = remoteSize / step;
                raf.seek(remoteSize);
                localreadbytes = remoteSize;
            }
            byte[] bytes = new byte[1024];
            int c;
            while ((c = raf.read(bytes)) != -1) {
                out.write(bytes, 0, c);
                localreadbytes += c;
                if (localreadbytes / step != process) {
                    process = localreadbytes / step;
                    if (process % 10 == 0) {
                        log.info("上传进度：" + process);
                    }
                }
            }
            out.flush();
            raf.close();
            out.close();
            // FTPUtil的upload方法在执行ftpClient.completePendingCommand()之前应该先关闭OutputStream，否则主线程会在这里卡死执行不下去。
            // 原因是completePendingCommand()会一直在等FTP Server返回226 Transfer complete，但是FTP Server只有在接受到OutputStream执行close方法时，才会返回。
            boolean result = ftpClient.completePendingCommand();
            if (remoteSize > 0) {
                status = result ? UploadStatusEnum.UploadFromBreakSuccess : UploadStatusEnum.UploadFromBreakFailed;
            } else {
                status = result ? UploadStatusEnum.UploadNewFileSuccess : UploadStatusEnum.UploadNewFileFailed;
            }
        } catch (Exception e) {
            log.error("uploadFile error ", e);
        }
        return status;
    }

    /**
     * 获取FTP某一特定目录下的文件数量
     *
     * @param ftpDirPath FTP上的目标文件路径
     */
    public Integer getFileNum(String ftpDirPath) {
        FTPClient ftpClient = ftpPoolService.borrowObject();
        try {
            FTPFile[] ftpFiles = ftpClient.listFiles(ftpDirPath);
            if (ftpFiles != null && ftpFiles.length > 0) {
                return Arrays.stream(ftpFiles).map(FTPFile::getName).collect(Collectors.toList()).size();
            }
            log.error(String.format("路径有误，或目录【%s】为空", ftpDirPath));
        } catch (IOException e) {
            log.error("文件获取异常:", e);
        } finally {
            ftpPoolService.returnObject(ftpClient);
        }
        return null;
    }

    /**
     * 获取文件夹下文件数量
     * @param ftpPath
     * @return
     */
    public Map<String,String> getDirFileNum(String ftpPath) {
        FTPClient ftpClient = ftpPoolService.borrowObject();
        try {
            Integer sum  = 0;
            Map<String,String> map = new HashMap<>();
            FTPFile[] ftpFiles = ftpClient.listFiles(ftpPath);
            if (ftpFiles != null && ftpFiles.length > 0) {
                for (FTPFile file : ftpFiles) {
                    if (file.isDirectory()) {
                        sum += getFileNum(ftpPath + DIR_SPLIT + file.getName());
                        map.put(file.getName(), String.valueOf(getFileNum(ftpPath + DIR_SPLIT + file.getName())));
                    }
                }
            }else {
                log.error(String.format("路径有误，或目录【%s】为空", ftpPath));
            }
            map.put("sum", String.valueOf(sum));
            return map;
        } catch (IOException e) {
            log.error("文件获取异常:", e);
        } finally {
            ftpPoolService.returnObject(ftpClient);
        }
        return null;
    }


    /**
     * 下载指定文件夹到本地
     * @param ftpPath FTP服务器文件目录
     * @param localPath 下载后的文件路径
     * @param dirName 文件夹名称
     * @return
     */
    public void downloadDir(String ftpPath, String localPath, String dirName){
        FTPClient ftpClient = ftpPoolService.borrowObject();
        OutputStream outputStream = null;
        try {
            //判断是否存在该文件夹
            FTPFile[] ftpFiles = ftpClient.listFiles(ftpPath, file -> file.getName().equals(dirName));
            if (ftpFiles != null && ftpFiles.length > 0) {
                if(ftpFiles[0].isDirectory()) {
                    // 判断本地路径目录是否存在，不存在则创建
                    File localFile = new File(localPath + DIR_SPLIT + dirName);
                    if (!localFile.exists()) {
                        localFile.mkdirs();
                    }
                    for (FTPFile file : ftpClient.listFiles(ftpPath + DIR_SPLIT + dirName)) {
                        if (file.isDirectory()) {
                            downloadDir(ftpPath + DIR_SPLIT + dirName, localPath + dirName + DIR_SPLIT, file.getName());
                        } else {
                            outputStream = Files.newOutputStream(new File(localPath + DIR_SPLIT + dirName + DIR_SPLIT + file.getName()).toPath());
                            ftpClient.retrieveFile(ftpPath + DIR_SPLIT + dirName + DIR_SPLIT + file.getName(), outputStream);
                            log.info("fileName:{},size:{}", file.getName(), file.getSize());
                            outputStream.close();
                        }
                    }
                }
            }
        }catch (Exception e){
            log.error("下载文件夹失败，filePathname:{},", ftpPath + DIR_SPLIT + dirName, e);
        }finally {
            IOUtils.closeQuietly(outputStream);
            ftpPoolService.returnObject(ftpClient);
        }
    }

    /**
     * 从本地上传文件夹
     * @param uploadPath ftp服务器地址
     * @param localPath 本地文件夹地址
     * @param dirName  文件夹名称
     * @return
     */
    public boolean uploadDir(String uploadPath, String localPath, String dirName){
        FTPClient ftpClient = ftpPoolService.borrowObject();
        try{
            // 切换到工作目录
            if (!ftpClient.changeWorkingDirectory(uploadPath)) {
                ftpClient.makeDirectory(uploadPath);
                ftpClient.changeWorkingDirectory(uploadPath);
            }
            //创建文件夹
            ftpClient.makeDirectory(uploadPath + DIR_SPLIT + dirName);
            File src = new File(localPath);
            //获取该目录下的所有文件
            File[] files = src.listFiles();
            FileInputStream input = null;
            for(File file : files) {
                if (file.isDirectory()) {
                    uploadDir(uploadPath + DIR_SPLIT + dirName, file.getAbsolutePath(), file.getName());
                } else {
                    input = new FileInputStream(file);
                    boolean storeFile = ftpClient.storeFile(uploadPath + DIR_SPLIT + dirName + DIR_SPLIT + file.getName(), input);
                    if (storeFile) {
                        log.info("文件:{}上传成功", file.getName());
                    } else {
                        throw new RuntimeException("ftp文件写入异常");
                    }
                }
            }
            if(input != null){
                input.close();
            }
        }catch (Exception e){
            log.error("文件夹上传失败",e);
        }finally {
            ftpPoolService.returnObject(ftpClient);
        }
        return false;
    }
}
