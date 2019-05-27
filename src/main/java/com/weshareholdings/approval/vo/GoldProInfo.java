package com.weshareholdings.approval.vo;

import com.weshareholdings.approval.po.Attach;
import com.weshareholdings.approval.po.ProModel;
import com.weshareholdings.approval.po.Team;
import lombok.Data;

import java.util.List;

@Data
public class GoldProInfo {
    /**
     * id
     */
    private int id;

    /**
     * 项目名称
     */
    private String proName;

    /**
     * 项目类型(0：ABS项目立项、1：消金项目、2：投委会项目、3：ABS评审)
     */
    private String proType;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 业务模式(0存量债转、1新增助贷、2投资业务、3数据采购、4新产品上线、5外包采购、6其他，可多种逗号隔开)
     */
    private String proModel;

    /**
     * 业务模式(0存量债转、1新增助贷、2投资业务、3数据采购、4新产品上线、5外包采购、6其他，可多种逗号隔开)
     */
    private List<ProModel> proModelList;

    /**
     * 合作方
     */
    private String partners;

    /**
     * 项目介绍
     */
    private String proDetails;

    /**
     * 项目成本(***元人民币)
     */
    private String proCost;

    /**
     * 项目预期
     */
    private String proExpectation;

    /**
     * 计划项目启动时间
     */
    private String proStarTime;

    /**
     * 项目完成时间
     */
    private String proCompleteTime;

    /**
     * 前置流程实例ID
     */
    private String beforeOrderId;

    /**
     * 附件list
     */
    private List<Attach> fileList;

    /**
     * 项目团队list
     */
    private List<Team> teamList;

    /**
     * 流程id
     */
    private String orderId;

}
