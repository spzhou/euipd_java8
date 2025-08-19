/**
 * Author：周朔鹏
 * 编制时间：2025-08-05

 * 电子邮件：391902958@qq.com
 * 项目官网：https://www.euipd.com
 */

package com.course.server.enums.ftp;

public enum UploadStatusEnum {

    UploadNewFileSuccess("UploadNewFileSuccess", "上传新文件成功"),
    UploadNewFileFailed("UploadNewFileFailed", "上传新文件失败"),
    FileExits("FileExits", "文件存在"),
    RemoteFileBiggerThanLocalFile("RemoteFileBiggerThanLocalFile", "服务器文件比本地文件大"),
    UploadFromBreakFailed("UploadFromBreakFailed", "上传中断后重新上传"),
    DeleteRemoteFaild("DeleteRemoteFaild", "删除上传失败的文件"),
    CreateDirectorySuccess("CreateDirectorySuccess", "创建目录成功"),
    CreateDirectoryFail("CreateDirectoryFail", "创建目录失败"),
    UploadFromBreakSuccess("UploadFromBreakSuccess", "中断后上传成功");

    private String code;

    private String desc;

    UploadStatusEnum(String code, String desc) {
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

    public static UploadStatusEnum getEnterStatusEnumByCode(String code) {
        for (UploadStatusEnum enumEntity : UploadStatusEnum.values()) {
            if (enumEntity.getCode() == code) {
                return enumEntity;
            }
        }
        return null;
    }

    public static UploadStatusEnum getEnterStatusEnumByDesc(String desc) {
        for (UploadStatusEnum enumEntity : UploadStatusEnum.values()) {
            if (enumEntity.getDesc().equals(desc)) {
                return enumEntity;
            }
        }
        return null;
    }

}
