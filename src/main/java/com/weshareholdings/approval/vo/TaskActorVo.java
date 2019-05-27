package com.weshareholdings.approval.vo;

import lombok.Data;
import org.snaker.engine.entity.Surrogate;

/**
 * @Author yiyh
 * @Date 2018/12/19
 **/
@Data
public class TaskActorVo {

    /**
     * ID
     */
    private String id;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 参与者ID
     */
    private String actorId;

    /**
     * 参与者类型(00：正常、01：委托代理)
     */
    private String type;

    /**
     * 授权委托id
     */
    private String surrogateId;

}
