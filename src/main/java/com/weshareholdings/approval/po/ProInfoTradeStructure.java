package com.weshareholdings.approval.po;

import lombok.Data;

import java.util.List;

@Data
public class ProInfoTradeStructure {
    /**
     * id
     */
    private String id;

    /**
     * 项目id
     */
    private String proId;

    /**
     * 底层资产类型(车分期、车抵贷、房抵贷、消费贷、个人经营贷、小微企业)
     */
    private String underlyingAssetsType;

    /**
     * 底层资产投放标准
     */
    private String underlyingAssetsStandard;

    /**
     * 底层资产投放标准附件list
     */
    private List<Attach> underlyingAssetsStandardFileList;

    /**
     * 各方出资金额及占比
     */
    private String ratioAmount;

    /**
     * 各方出资金额及占比附件list
     */
    private List<Attach> ratioAmountFileList;

    /**
     * 各方出资安排
     */
    private String fundArrangements;

    /**
     * 各方出资安排附件list
     */
    private List<Attach> fundArrangementsFileList;

    /**
     * 投资期限
     */
    private String termInvestment;

    /**
     * 投资期限附件list
     */
    private List<Attach> termInvestmentFileList;

    /**
     * 投资利率
     */
    private String rateInvestment;

    /**
     * 投资利率附件list
     */
    private List<Attach> rateInvestmentFileList;

    /**
     * ABS发行金额、分层占比、及分层评级结果
     */
    private String ratingResult;

    /**
     * ABS发行金额、分层占比、及分层评级结果附件list
     */
    private List<Attach> ratingResultFileList;

    /**
     * 原始权益人出表与否(Y/N)
     */
    private String isOriginalList;

    /**
     * 原始权益人出表与否(Y/N)附件list
     */
    private List<Attach> isOriginalListFileList;

    /**
     * 原始权益人是否履行回购义务(Y/N)
     */
    private String isOriginalBuyback;

    /**
     * 原始权益人是否履行回购义务(Y/N)附件list
     */
    private List<Attach> isOriginalBuybackFileList;

    /**
     * 违约事件阈值及内容设定
     */
    private String breachContent;

    /**
     * 违约事件阈值及内容设定附件list
     */
    private List<Attach> breachContentFileList;

    /**
     * 违约事件发生前和发生后分配顺序的比较
     */
    private String breachCompare;

    /**
     * 违约事件发生前和发生后分配顺序的比较附件list
     */
    private List<Attach> breachCompareFileList;

    /**
     * 基金总金额
     */
    private String totalFundAmount;

    /**
     * 基金总金额附件list
     */
    private List<Attach> totalFundAmountFileList;

    /**
     * 种子基金占比
     */
    private String seedFundProportion;

    /**
     * 种子基金占比附件list
     */
    private List<Attach> seedFundProportionFileList;

    /**
     * 预计收取的基金管理费
     */
    private String estimatedManagementFee;

    /**
     * 预计收取的基金管理费附件list
     */
    private List<Attach> estimatedManagementFeeFileList;

    /**
     * 种子基金投资范围或产品
     */
    private String scopeInvestment;

    /**
     * 种子基金投资范围或产品附件list
     */
    private List<Attach> scopeInvestmentFileList;

    /**
     * 基金风控和投资风险安排
     */
    private String riskArrangement;

    /**
     * 基金风控和投资风险安排附件list
     */
    private List<Attach> riskArrangementFileList;
}
