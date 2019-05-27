package com.weshareholdings.approval.vo;

import lombok.Data;

import java.util.List;

@Data
public class DataUseApplyProInfo {
    /**
     * id
     */
    private int id;

    /**
     * id
     */
    private String orderId;

    /**
     * 申请状态
     */
    private int orderState;

    /**
     * 应用系统名称
     */
    private String systemName;

    /**
     * 申请部门
     */
    private String applyDepartment;

    /**
     * 建议完成时间
     */
    private String suggestCompleteTime;

    /**
     * 验收类型(数据查看分析、数据提取应用、数据修改/删除 )
     */
    private String applyType;

    /**
     * 申请类别(
     *  查看与分析明细级不脱敏数据,
     *  查看与分析明细级脱敏数据大于10万条(含本数),
     *  查看与分析统计级数据(包含非数据人员查看数据),
     *  提取明细级不脱敏数据,
     *  提取明细级脱敏数据-公司内部流转,
     *  提取明细级脱敏数据-公司外部流转,
     *  提取明统计级数据-公司内部流转,
     *  提取统计级数据-公司外部流转,
     *  修改及删除明细级不脱敏数据,
     *  修改及删除明细级脱敏数据)
     */
    private String applyCategory;

    /**
     * 数据查看/提取/修改/删除原因
     */
    private String optReason;

    /**
     * 数据查看/提取/修改/删除内容
     */
    private String optContent;

    /**
     * 可行性分析和影响
     */
    private String feasibilityAnalysis;

    /**
     * 验收结论
     */
    private String acceptanceConclusion;

    /**
     * 申请人id
     */
    private String createUserId;

    /**
     * 申请人名称
     */
    private String createUserName;

    /**
     * 申请日期
     */
    private String createTime;

    /**
     * 项目类型
     */
    private String proType;

    /**
     * 节点详情
     */
    private List<NodesDetail> nodesDetailList;

    /**
     * 当前节点
     */
    private NodesDetail nowNode;
}
