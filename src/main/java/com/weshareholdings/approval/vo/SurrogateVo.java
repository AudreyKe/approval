package com.weshareholdings.approval.vo;

import lombok.Data;
import org.snaker.engine.entity.Surrogate;

/**
 * @Author liling
 * @Date 2018/9/10
 **/
@Data
public class SurrogateVo extends Surrogate {

    /**
     * id
     */
    private String id;

    /**
     * 流程显示名称
     */
    private String proDisplayName;

    /**
     * 授权人账号
     */
    private String operatorId;

    /**
     * 代理人账号
     */
    private String surrogateId;

    /**
     * 代理人账号
     */
    private String batchId;

    /**
     * 流程定义id
     */
    private String processId;

    /**
     * 操作时间
     */
    private String odate;


}
