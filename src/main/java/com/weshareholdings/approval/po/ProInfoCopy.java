package com.weshareholdings.approval.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ProInfoCopy implements Serializable {

    /**
     * id
     */
    private Integer id;

    /**
     * 项目id
     */
    private Integer proId;

    /**
     * 项目名称
     */
    private String proName;

    /**
     * 项目类型(0：ABS项目立项、1：消金项目、2：投委会项目、3：ABS评审)
     */
    private String proType;

    /**
     * 业务模式(0存量债转、1新增助贷、2投资业务、3数据采购、4新产品上线、5外包采购、6其他，可多种逗号隔开)
     */
    private String proModel;

    /**
     * 其他业务类型名称
     */
    private String otherModeNamel;

    /**
     * 合作方/合作伙伴
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
     * 项目总规模
     */
    private String proTotal;

    /**
     * 投资金额
     */
    private String investmentAmount;

    /**
     * 分层结构
     */
    private String layerStruct;

    /**
     * 预计投资时间
     */
    private String expectTime;

    /**
     * 预计回报率
     */
    private String expectRate;

    /**
     * 平均期限
     */
    private String avgTerm;

    /**
     * 最长期限
     */
    private String largeTerm;

    /**
     * 通过率及累计违约率假设
     */
    private String passDefaultRate;

    /**
     * 历史数据表现对比（累计违约率、通过率）
     */
    private String historyDataContrast;

    /**
     * 风险评估
     */
    private String riskAssessment;

    /**
     * 信托计划形成的时长
     */
    private String trustPlanTime;

    /**
     * 法律文本及系统准备情况
     */
    private String legalText;

    /**
     * 其他限制性条件
     */
    private String otherCondition;

    /**
     * 行业分类
     */
    private String category;

    /**
     * 资产服务机构、劣后投资人
     */
    private String assetSerAgency;

    /**
     * 12个月内预计合作规模(亿)
     */
    private String coopScale;

    /**
     * 是否同意框架合作(Y/N)
     */
    private String coopState;

    /**
     * 委托人、信托计划管理
     */
    private String planManagement;

    /**
     * 技术支持及咨询服务方
     */
    private String techSuppport;

    /**
     * 融资方、差额支付承诺人
     */
    private String promiser;

    /**
     * 融资方、差额支付承诺人评级情况
     */
    private String promiserLevel;

    /**
     * 差额补足、回购义务担保
     */
    private String guarantee;

    /**
     * 差额补足、回购义务担保评级情况
     */
    private String guaranteeLevel;

    /**
     * 担保机构
     */
    private String guaInstitution;

    /**
     * 担保机构评级情况
     */
    private String guaInstitutionLvel;

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
     * 系统需求
     */
    private String requirements;

    /**
     * 系统开发排期
     */
    private String schedule;

    /**
     * 系统需求是否已确认
     */
    private String requirementsState;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 流程实例id
     */
    private String orderId;

    /**
     * 日期
     */
    private Date date;

    /**
     * 团队
     */
    private String team;

    /**
     * 外部增信机构
     */
    private String externalEnhancementAgencies;

    /**
     * 评级情况
     */
    private String ratingSituation;

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
     * 基础资产属性
     */
    private String assertType;

    /**
     * 基础资产情况
     */
    private String assertSituation;

    /**
     * 原始权益人
     */
    private String originalRightsHolder;

    /**
     * 原始权益人评级情况
     */
    private String holderRatingSituation;

    /**
     * 理财通分包数
     */
    private String packageNum;

    /**
     * 理财通单包规模
     */
    private String packageScale;

    /**
     * 前置流程实例ID
     */
    private String beforeOrderId;

    /**
     * 创建人id
     */
    private String createUserId;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     *
     */
    private static final long serialVersionUID = 1L;

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
     * 发行金额
     */
    private String issueAmount;

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
}