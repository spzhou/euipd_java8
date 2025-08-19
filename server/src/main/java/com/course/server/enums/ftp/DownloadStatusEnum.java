/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.enums.ftp;

public enum DownloadStatusEnum {

    RemoteFileNotExist("RemoteFileNotExist", "服务器文件不存在"),
    LocalFileBiggerThanRemoteFile("LocalFileBiggerThanRemoteFile", "本地文件比服务器文件大"),
    DownloadFromBreakSuccess("DownloadFromBreakSuccess", "中断后下载成功"),
    DownloadFromBreakFailed("DownloadFromBreakFailed", "中断后下载失败"),
    DownloadNewSuccess("DownloadNewSuccess", "新文件下载成功"),
    DownloadNewFailed("DownloadNewFailed", "新文件下载失败");

    private String code;

    private String desc;

    DownloadStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public static DownloadStatusEnum getEnterStatusEnumByCode(String code) {
        for (DownloadStatusEnum enumEntity : DownloadStatusEnum.values()) {
            if (enumEntity.getCode() == code) {
                return enumEntity;
            }
        }
        return null;
    }

    public static DownloadStatusEnum getEnterStatusEnumByDesc(String desc) {
        for (DownloadStatusEnum enumEntity : DownloadStatusEnum.values()) {
            if (enumEntity.getDesc().equals(desc)) {
                return enumEntity;
            }
        }
        return null;
    }

}
