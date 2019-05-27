package com.weshareholdings.approval.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author liling
 * @Date 2018/12/19
 **/
@Data
public class FileApprovalVo implements Serializable {

    private String proId;

    /**
     *  项目名称
     */
    private String proName;

    /**
     * 项目类型
     */
    private String proType;

    /**
     * 启动时间
     */
    private String createTime;

    /**
     * 项目审批状态
     */
    private String orderState;

    /**
     * 附件审批状态
     */
    private String attachTaskState;

    /**
     * 流程实例ID
     */
    private String orderId;
}
