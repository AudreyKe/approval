package com.weshareholdings.approval.vo;

import com.weshareholdings.approval.po.*;
import lombok.Data;

import java.util.List;

@Data
public class VoteProInfoVo {
    /**
     * id
     */
    private int id;

    /**
     * 项目名称
     */
    private String proName;

    /**
     * 项目类型(00：ABS项目、01：消金项目、02：投委会项目)
     */
    private String proType;


    private List<ProModel> proModelList;


    /**
     * 投资金额
     */
    private String investmentAmount;

    /**
     * 资产服务机构
     */
    private String assetSerAgency;

    /**
     * 劣后投资人
     */
    private String inferiorInvestor;

    /**
     * 差额补足机构
     */
    private String diffAgency;

    /**
     * 12个月内预计合作规模(亿)
     */
    private String coopScale;

    /**
     * 外部增信机构
     */
    private String externalEnhancementAgencies;

    /**
     * 评级情况
     */
    private String ratingSituation;

    /**
     * 是否同意框架合作及系统对接
     */
    private String coopState;

    /**
     * 发行金额含次级（亿元）
     */
    private String totalAmount;

    /**
     * 综合成本
     */
    private String cost;

    /**
     * 产品期限（年）
     */
    private String term;

    /**
     * 循环期(月)
     */
    private String cyclePeriod;

    /**
     * 摊还期(月)
     */
    private String amortizationPeriod;

    /**
     * 项目团队意见
     */
    private String projectTeamOpinion;

    /**
     * 分销渠道
     */
    private String channel;

    /**
     * 项目前端收入
     */
    private String frontIncome;

    /**
     * 项目后端收入
     */
    private String afterIncome;

    /**
     * 项目团队
     */
    private List<Team> teamList;

    /**
     * 原始权益人
     */
    private String originalRightsHolder;

    /**
     * 原始权益人评级情况
     */
    private String holderRatingSituation;

    /**
     * 分级
     */
    private List<ProLayer> layerList;

    /**
     * 附件list
     */
    private List<Attach> fileList;


    /**
     * 是否同意系统对接(Y/N)
     */
    private String systemAbutmentState;

    /**
     * 已合作规模(亿)
     */
    private String scaleCooperation;

    /**
     * 存量余额(亿)
     */
    private String stockBalance;

    /**
     * 是否需要新分享投资
     */
    private String whetherWeshareInvest;

    /**
     * 投资内容、利率、期限
     */
    private String investComment;

    /**
     * 投资标的(1：Pre-ABS、2：ABS优先、夹层或劣后、3：RQFII基金种子、4：其他)
     */
    private String investmentTarget;

    /**
     * 投资目的(1：撬动ABS业务、2：撬动外部资金方、3：满足客户的具体需求、4：满足我方的需求)
     */
    private String investmentPurpose;

    /**
     * 杠杆倍数
     */
    private String leverage;

    /**
     * 投资特点(1：可循环性、2：可复制性)
     */
    private String investmentCharacteristics;

    /**
     * 退出方式(1：持有至到期退出、2：转让退出)
     */
    private String exitMode;

    /**
     * 项目交易结构
     */
    private ProInfoTradeStructure proInfoTradeStructure;

    /**
     * 系统需求(静态池/循环结构)
     */
    private String requirements;

    /**
     * 理财通分包数
     */
    private String packageNum;

    /**
     * 理财通单包规模
     */
    private String packageScale;

    /**
     * 日期
     */
    private String date;
}
