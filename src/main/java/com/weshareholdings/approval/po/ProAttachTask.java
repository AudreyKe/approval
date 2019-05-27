package com.weshareholdings.approval.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *附件任务表
 */
@Data
public class ProAttachTask implements Serializable {
    private Integer id;

    private Integer proId;

    private Integer attachId;

    private Integer userId;

    private String status;

    private Integer optType;

    private String backReason;

    private Date createTime;

    private Date updateTime;

    private String proAttachType;

    private static final long serialVersionUID = 1L;

}