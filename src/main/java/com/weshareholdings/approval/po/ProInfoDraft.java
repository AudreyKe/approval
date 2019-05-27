package com.weshareholdings.approval.po;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProInfoDraft implements Serializable {
    private Integer id;

    private Integer proId;

    private String proName;

    private String proModel;

    private List<ProModel> proModelList;

    private String category;

    private String assetSerAgency;

    /**
     * 劣后投资人
     */
    private String inferiorInvestor;

    /**
     * 差额补足机构
     */
    private String diffAgency;

    private String coopScale;

    private String coopState;

    private String planManagement;

    private String techSuppport;

    private String promiser;

    private String promiserLevel;

    private String guarantee;

    private String guaranteeLevel;

    private String guaInstitution;

    private String guaInstitutionLvel;

    private String totalAmount;

    private String cost;

    private String term;

    private String channel;

    private String frontIncome;

    private String afterIncome;

    private String requirements;

    private String schedule;

    private String requirementsState;

    private String createTime;

    private String orderId;

    private String date;

    private String team;

    private String externalEnhancementAgencies;

    private String ratingSituation;

    private String cyclePeriod;

    private String amortizationPeriod;

    private String projectTeamOpinion;

    private String beforeOrderId;

    private String assertType;

    private String assertSituation;

    private String originalRightsHolder;

    private String holderRatingSituation;

    private String packageNum;

    private String packageScale;

    private String proType;

    private String otherModeNamel;

    private String partners;

    private String proCost;

    private String proExpectation;

    private String proStarTime;

    private String proCompleteTime;

    private String status;

    private String proTotal;

    private String investmentAmount;

    private String layerStruct;

    private String expectTime;

    private String expectRate;

    private String avgTerm;

    private String largeTerm;

    private String passDefaultRate;

    private String historyDataContrast;

    private String riskAssessment;

    private String trustPlanTime;

    private String legalText;

    private String otherCondition;

    private Integer createUserId;

    private String updateTime;

    private String proDetails;

    /**
     * 分级
     */
    private List<ProLayer> layerList;

    /**
     * 附件list
     */
    private List<Attach> fileList;

    /**
     * 项目团队
     */
    private List<Team> teamList;

    /**
     * 是否需要新分享投资
     */
    private String whetherWeshareInvest;

    /**
     * 投资内容、利率、期限
     */
    private String investComment;

    private String systemAbutmentState;

    private String scaleCooperation;

    private String stockBalance;

    /**
     * 通过率及累计违约率假设附件list
     */
    private List<Attach> passDefaultRateFileList;

    /**
     * 历史数据表现对比（累计违约率、通过率）附件list
     */
    private List<Attach> historyDataContrastFileList;

    /**
     * 其他限制性条件附件list
     */
    private List<Attach> otherConditionFileList;

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

    /**
     * 项目交易结构
     */
    private ProInfoTradeStructure proInfoTradeStructure;

}