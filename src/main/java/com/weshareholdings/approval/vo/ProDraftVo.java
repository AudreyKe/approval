package com.weshareholdings.approval.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Author liling
 * @Date 2018/12/21
 **/
@Data
public class ProDraftVo {

    /**
     * 草稿ID
     */
    private int id;
    /**
     * 项目ID
     */
    private String proId;
    /**
     * 流程实例ID
     */
    private String orderId;

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
     * 项目草稿状态（0：草稿、1：已提交、2：删除）
     */
    private String proState;

}
