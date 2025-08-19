/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.util;

import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.Base64;
import java.util.UUID;

public class Base64ToMultipartFile implements MultipartFile {

    private final byte[] imgContent;
    private final String header;

    public Base64ToMultipartFile(byte[] imgContent, String header) {
        this.imgContent = imgContent;
        String[] parts = header.split(";");
        this.header = parts.length > 0 ? parts[0] : "";
    }

    @Override
    public String getName() {
        return UUID.randomUUID().toString() + "." + getContentType().split("/")[1];
    }

    @Override
    public String getOriginalFilename() {
        return UUID.randomUUID().toString() + "." + getContentType().split("/")[1];
    }

    @Override
    public String getContentType() {
        String[] parts = header.split(":");
        return parts.length > 1 ? parts[1] : "";
    }

    @Override
    public boolean isEmpty() {
        return imgContent == null || imgContent.length == 0;
    }

    @Override
    public long getSize() {
        return imgContent.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return imgContent;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(imgContent);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        try (FileOutputStream fos = new FileOutputStream(dest)) {
            fos.write(imgContent);
        }
    }

    public static MultipartFile base64ToMultipart(String base64) {
        try {
            String[] baseStrs = base64.split(",", 2);
            if (baseStrs.length != 2) {
                throw new IllegalArgumentException("Invalid base64 string format");
            }

            byte[] imgContent = Base64.getDecoder().decode(baseStrs[1]);
            return new Base64ToMultipartFile(imgContent, baseStrs[0]);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Failed to convert base64 to multipart file", e);
        }
    }
}
