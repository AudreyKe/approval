package com.weshareholdings.approval.vo;

import lombok.Data;

@Data
public class OrderResult {
    /**
     * 流程定义ID
     */
    private String processId;

    /**
     * 流程id
     */
    private String orderId;

    /**
     * 流程实例名
     */
    private String orderName;

    /**
     * 流程启动时间
     */
    private String createTime;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 流程实例编号
     */
    private String orderNo;

    /**
     * 实例状态
     */
    private String orderState;

    /**
     * 常量字符串
     */
    private String orderVariable;

    /**
     * 流程类型
     */
    private String performType;

    /**
     * 任务类型
     */
    private String taskType;

    /**
     * 是否可编辑 1-可编辑 0-不可编辑
     */
    private int editFlag;

    /**
     * 任务类型 0-立项申请，1-项目评审申请，2-项目投决申请，3-消金立项申请
     */
    private String proType;

}
