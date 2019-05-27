package com.weshareholdings.approval.po;

import lombok.Data;

import java.io.Serializable;

/**
 * 委托代理实体类
 * @author yiyh
 */
@Data
public class SurrogateInfo implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -7359321877096338448L;
    /**
     * 主键ID
     */
    private String id;
    /**
     * 委托代理ID
     */
    private String surrogateId;
    /**
     * 流程name
     */
    private String processName;
    /**
     * 流程id
     */
    private String processId;
    /**
     * 授权人
     */
    private String operator;
    /**
     * 代理人
     */
    private String surrogate;
    /**
     * 操作时间
     */
    private String odate;
    /**
     * 开始时间
     */
    private String sdate;
    /**
     * 结束时间
     */
    private String edate;
    /**
     * 状态
     */
    private Integer state;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 类型(0：新增、1：修改、2删除)
     */
    private String type;

    /**
     * 流程实例ID
     */
    private String orderId;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 批次id
     */
    private String batchId;
}
