package com.weshareholdings.approval.po;

import lombok.Data;

import java.util.Date;

@Data
public class Attach {
    /**
     * id
     */
    private int id;

    /**
     * 项目id
     */
    private int proId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 存放路径
     */
    private String filePath;

    /**
     * 实际文件名
     */
    private String realFileName;

    /**
     * 项目附件类型(00：项目立项材料、01：项目补充材料、02：项目落实材料)
     */
    private String proAttachType;

    /**
     * 状态(00：正常、01：删除)
     */
    private String status;

    /**
     * 上传者姓名
     */
    private String uploadPersonName;

    /**
     * 上传者id
     */
    private String uploadPersonId;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String  updateTime;

    /**
     * 操作类型(0:草稿、1:正常)
     */
    private Integer optType;

}
