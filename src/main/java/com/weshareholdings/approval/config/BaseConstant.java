package com.weshareholdings.approval.config;


import java.util.HashMap;
import java.util.Map;

public class BaseConstant {

    /**
     * 新增项目参数校验表达式(ABS)
     */
    public final static String[] ENABLEPROCESS_EXPRESSION_ABS = {
            "proName-1-项目名称不能为空！",
            "date-1-日期不能为空！",
            "category-1-行业分类不能为空！",
            "assetSerAgency-1-资产服务机构不能为空！",
            "inferiorInvestor-1-劣后投资人不能为空！",
            "diffAgency-1-差额补足机构不能为空！",
            "coopScale-1-12个月内预计合作规模(亿)不能为空！",
            "coopState-1-是否同意框架合作及系统对接不能为空！",
            "totalAmount-1-发行金额不能为空！",
            "cost-1-综合成本不能为空！",
            "term-1-期限不能为空！",
            "channel-1-分销渠道不能为空！",
            "frontIncome-1-项目前端收入不能为空！",
            "afterIncome-1-项目后端收入不能为空！",
            "externalEnhancementAgencies-1-外部增信机构不能为空！",
            "ratingSituation-1-评级情况不能为空！",
            "projectTeamOpinion-1-项目团队意见不能为空！",
            "proType-1-项目类型不能为空！",
            "systemAbutmentState-1-是否同意系统对接不能为空！",
            "scaleCooperation-1-已合作规模不能为空！",
            "stockBalance-1-存量余额不能为空！",
            "requirements-1-系统需求不能为空！",
            "layerList-3-LAYER_LIST_EXPRESSION",
            "teamList-3-TEAM_LIST_EXPRESSION",
            "proModelList-3-PROMODEL_LIST_EXPRESSION",
            "fileList-3-FILE_LIST_EXPRESSION"
    };

    /**
     * 数据使用申请校验表达式
     */
    public final static String[] DATA_USE_APPLY = {
            "systemName-1-应用系统名称不能为空！",
            "applyDepartment-1-申请部门不能为空！",
            "suggestCompleteTime-1-建议完成时间不能为空！",
            "applyType-1-验收类型不能为空！",
            "applyCategory-1-申请类别不能为空！",
            "optReason-1-数据查看/提取/修改/删除原因不能为空！",
            "optContent-1-数据查看/提取/修改/删除内容不能为空！",
            "feasibilityAnalysis-1-可行性分析和影响不能为空！"
    };

    /**
     * 新增项目参数校验表达式(消金)
     */
    public final static String[] ENABLEPROCESS_EXPRESSION_ELIMINATING = {
            "proName-1-项目名称不能为空！",
            "partners-1-合作方不能为空！",
            "proDetails-1-项目介绍不能为空！",
            "proCost-1-项目成本不能为空！",
            "proExpectation-1-项目预期不能为空！",
            "proStarTime-1-计划项目启动时间不能为空！",
            "proCompleteTime-1-项目完成时间不能为空！",
            "proType-1-项目类型不能为空！",
            "fileList-3-FILE_LIST_EXPRESSION",
            "proModelList-3-PROMODEL_LIST_EXPRESSION"
    };

    /**
     * 新增项目参数校验表达式(投委)
     */
    public final static String[] ENABLEPROCESS_EXPRESSION_VOTE= {
            "proName-1-项目名称不能为空！"
            ,"date-1-日期不能为空！"
            ,"assetSerAgency-1-资产服务机构不能为空！"
            ,"inferiorInvestor-1-劣后投资人不能为空！"
            ,"diffAgency-1-差额补足机构不能为空！"
            ,"coopScale-1-12个月内预计合作规模(亿)不能为空！"
            ,"coopState-1-是否同意框架合作及系统对接不能为空！"
            ,"totalAmount-1-发行金额不能为空！"
            ,"cost-1-综合成本不能为空！"
            ,"term-1-产品期限不能为空！"
            ,"channel-1-分销渠道不能为空！"
            ,"frontIncome-1-项目前端收入不能为空！"
            ,"afterIncome-1-项目后端收入不能为空！"
            ,"externalEnhancementAgencies-1-外部增信机构不能为空！"
            ,"ratingSituation-1-评级情况不能为空！"
//            ,"projectTeamOpinion-1-项目团队意见不能为空！"
            ,"proType-1-项目类型不能为空！"
            ,"systemAbutmentState-1-是否同意系统对接不能为空！"
            ,"scaleCooperation-1-已合作规模不能为空！"
            ,"stockBalance-1-存量余额不能为空！"
            ,"investmentTarget-1-投资标的不能为空！"
            ,"investmentPurpose-1-投资目的不能为空！"
            ,"leverage-1-杠杆倍数不能为空！"
            ,"investmentCharacteristics-1-投资特点不能为空！"
            ,"exitMode-1-退出方式不能为空！"
            ,"requirements-1-系统需求不能为空！"
            ,"layerList-3-LAYER_LIST_EXPRESSION"
            ,"teamList-3-TEAM_LIST_EXPRESSION"
            ,"proModelList-3-PROMODEL_LIST_EXPRESSION"
            ,"fileList-3-FILE_LIST_EXPRESSION"
            ,"proInfoTradeStructure-8-交易结构不能为空！"
    };

    /**
     * 驳回流程code
     */
    public final static String[] REBACK_PROCESS= {
            "Approval"//上级审批
            ,"compliApproval"//合规审批
            ,"riskApproval"//风控审批
            ,"techApproval"//技术审批
            ,"secreApproval"//秘书发起会议
            ,"leaderApproval"//上级审批
            // 大数据审批
            ,"dataApproval"
    };

    /**
     * 投决附件上传list名称
     */
    public final static String[] FILE_LIST_NAMES= {
            "underlyingAssetsStandardFileList"//底层资产投放标准附件list
            ,"ratioAmountFileList"//各方出资金额及占比附件list
            ,"fundArrangementsFileList"//各方出资安排附件list
            ,"termInvestmentFileList"//投资期限附件list
            ,"rateInvestmentFileList"//投资利率附件list
            ,"ratingResultFileList"//ABS发行金额、分层占比、及分层评级结果附件list
            ,"breachContentFileList"//违约事件阈值及内容设定附件list
            ,"breachCompareFileList"//违约事件发生前和发生后分配顺序的比较附件list
            ,"totalFundAmountFileList"//基金总金额附件list
            ,"seedFundProportionFileList"//种子基金占比附件list
            ,"estimatedManagementFeeFileList"//预计收取的基金管理费附件list
            ,"scopeInvestmentFileList"//种子基金投资范围或产品附件list
            ,"riskArrangementFileList"//基金风控和投资风险安排附件list
            ,"isOriginalListFileList"//原始权益人出表与否(Y/N)附件list
            ,"isOriginalBuybackFileList"//原始权益人是否履行回购义务(Y/N)附件list
    };

    /**
     * 投决附件上传code
     */
    public final static String[] FILE_LIST_CODES= {
            "underlyingAssetsStandardFileList-06"//underlyingAssetsStandardFileList 底层资产投放标准附件list
            ,"ratioAmountFileList-07"//各方出资金额及占比附件list
            ,"fundArrangementsFileList-08"//各方出资安排附件list
            ,"termInvestmentFileList-09"//投资期限附件list
            ,"rateInvestmentFileList-10"//投资利率附件list
            ,"ratingResultFileList-11"//ABS发行金额、分层占比、及分层评级结果附件list
            ,"breachContentFileList-12"//违约事件阈值及内容设定附件list
            ,"breachCompareFileList-13"//违约事件发生前和发生后分配顺序的比较附件list
            ,"totalFundAmountFileList-14"//基金总金额附件list
            ,"seedFundProportionFileList-15"//种子基金占比附件list
            ,"estimatedManagementFeeFileList-16"//预计收取的基金管理费附件list
            ,"scopeInvestmentFileList-17"//种子基金投资范围或产品附件list
            ,"riskArrangementFileList-18"//基金风控和投资风险安排附件list
            ,"isOriginalListFileList-19"//原始权益人出表与否(Y/N)附件list
            ,"isOriginalBuybackFileList-20"//原始权益人是否履行回购义务(Y/N)附件list
    };



    /**
     * 流程状态映射
     */
    public final static Map<String, String> OrderState = new HashMap<>();

    static {
        OrderState.put("0", "已通过");
        OrderState.put("1", "审批中");
        OrderState.put("2", "已终止");
    }

    /**
     * 审批状态映射
     */
    public final static Map<String, String> ApprovalState = new HashMap<>();

    static {
        ApprovalState.put("-1", "不同意");
        ApprovalState.put("0", "同意");
        ApprovalState.put("1", "转办");
        ApprovalState.put("2", "协办");
        ApprovalState.put("-2", "有条件同意");
    }

    /**
     * 项目类型-部门映射
     */
    public final static Map<String, String> ProjectTypeReflect = new HashMap<>();

    static {
        ProjectTypeReflect.put("0", "ABS");
        ProjectTypeReflect.put("1", "ABS");
        ProjectTypeReflect.put("2", "VOTE");
        ProjectTypeReflect.put("3", "ELIMINATING");
    }

    public final static String Channel = "理财通,场内发行,机构私募,转租赁";

    /**
     * 节点名
     */
    public enum  TASKNAME {
        /**
         * 提交申请
         */
        APPLY("apply", "提交申请"),
        /**
         * 技术审批
         */
        TECHAPPROVAL("techApproval", "技术审批"),
        /**
         * 上级审批
         */
        LEADERAPPROVAL("leaderApproval", "上级审批"),
        /**
         * 合规审批
         */
        COMPLIAPPROVAL("compliApproval", "合规审批"),
        /**
         * 风控审批
         */
        RISKAPPROVAL("riskApproval", "风控审批"),
        /**
         * 秘书决策
         */
        SECREAPROVAL("secreApproval", "秘书发起会议"),
        /**
         * 委员决策
         */
        MEETAPPROVAL("meetApproval", "委员决策"),
        /**
         * 秘书选择
         */
        SECRECHOOSE("secreChoose", "秘书选择"),
        /**
         * 秘书落实跟进事项
         */
        SECRECONFIRM("secreConfirm", "秘书落实跟进事项"),
        /**
         * 前置审批
         */
        FRONTAPPROVAL("frontApproval","前置审批"),

        /**
         * 大数据审批
         */
        DATAAPROVAL("dataApproval","大数据审批");

        private String code;
        private String message;

        TASKNAME(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public static String getMessageByCode(String code){
            for (TASKNAME tasName : TASKNAME.values()){
                if (tasName.getCode().equals(code)){
                    return tasName.getMessage();
                }
            }
            return null;
        }

    }

    /**
     * 项目参数类型
     */
    public enum PARAMTYPE {

        /**
         * 原始权益人
         */
        ORIGINAL_RIGHTS_HOLDER("originalRightsHolder", "原始权益人"),

        /**
         * 合作方
         */
        PARTNERS("partners", "合作方"),

        /**
         * 合作人
         */
        COLLABORATOR("collaborator", "合作伙伴");

        private String code;

        private String message;

        PARAMTYPE(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

    }
    /**
     *  项目类型
     */
    public enum PROTYPE {

        /**
         * ABS立项项目
         */
        ABS_PROJECT("0", "立项"),
        /**
         * ABS评审项目
         */
        ABS_REVIEW("1", "评审"),
        /**
         * 投决会项目
         */
        CAST_PROJECT("2", "投决"),
        /**
         * 消金项目
         */
        CONSUMER_PROJECT("3", "消金"),
        /**
         * 数据使用申请
         */
        DATA_USE_APPLY("4", "数据使用申请");

        private String code;

        private String message;

        PROTYPE(String code, String message){
            this.code = code;
            this.message = message;
        }

        public String getCode(){
            return code;
        }

        public String getMessage(){
            return message;
        }

        public static String getMessageByCode(String code){
            for (PROTYPE protype : PROTYPE.values()){
                if (protype.getCode().equals(code)){
                    return protype.getMessage();
                }
            }
            return null;
        }
    }

    public enum PRO_ATTACH_TYPE{
        /**
         *项目附件类型
         */
        PROJECT("00", "项目立项材料")
        ,SUPPLEMENT("01", "项目补充材料")
        ,CONFIRM("02", "项目落实材料")
        ,PRO_SUPPLEMENT_IMPLEMENT("00,01,02", "立项、补充、落实材料")
        ,PASSDEFAULTRATE("03", "通过率及累计违约率假设材料")
        ,HISTORYDATACONTRAST("04", "历史数据表现对比材料")
        ,OTHERCONDITION("05", "其他限制性条件材料")
        ,UNDERLYING_ASSETS_STANDARD_FILE_LIST("06", "底层资产投放标准附件list")
        ,RATIO_AMOUNT_FILE_LIST("07", "各方出资金额及占比附件list")
        ,FUND_ARRANGEMENTS_FILE_LIST("08", "各方出资安排附件list")
        ,TERM_INVESTMENT_FILE_LIST("09", "投资期限附件list")
        ,RATE_INVESTMENT_FILE_LIST("10", "投资利率附件list")
        ,RATING_RESULT_FILE_LIST("11", "ABS发行金额、分层占比、及分层评级结果附件list")
        ,BREACH_CONTENT_FILE_LIST("12", "违约事件阈值及内容设定附件list")
        ,BREACH_COMPARE_FILE_LIST("13", "违约事件发生前和发生后分配顺序的比较附件list")
        ,TOTAL_FUND_AMOUNT_FILE_LIST("14", "基金总金额附件list")
        ,SEED_FUND_PROPORTION_FILE_LIST("15", "种子基金占比附件list")
        ,ESTIMATED_MANAGEMENT_FEE_FILE_LIST("16", "预计收取的基金管理费附件list")
        ,SCOPE_INVESTMENT_FILE_LIST("17", "种子基金投资范围或产品附件list")
        ,RISK_ARRANGEMENT_FILE_LIST("18", "基金风控和投资风险安排附件list")
        ,IS_ORIGINAL_LIST_FILE_LIST("19", "原始权益人出表与否(Y/N)附件list")
        ,IS_ORIGINAL_BUYBACK_FILE_LIST("20", "原始权益人是否履行回购义务(Y/N)附件list");

        private String code;
        private String message;
        PRO_ATTACH_TYPE(String code, String message){
            this.code = code;
            this.message = message;
        }
        public String getCode(){
            return code;
        }

        public String getMessage(){
            return message;
        }
    }

    public enum ATTACH_STATUS{
        /**
         * 附件状态
         */
        NORMAL("00", "正常"),
        DELETE("01", "删除");

        private String code;
        private String message;
        ATTACH_STATUS(String code, String message){
            this.code = code;
            this.message = message;
        }
        public String getCode(){
            return code;
        }

        public String getMessage(){
            return message;
        }
    }

    public enum ATTACH_TASK_STATUS{
        /**
         * 附件任务状态
         */
        UN_APPROVAL("00", "未审核"),
        APPROVAL_PASS("01", "审核通过"),
        APPROVAL_REJECT("02", "审核拒绝");

        private String code;
        private String message;
        ATTACH_TASK_STATUS(String code, String message){
            this.code = code;
            this.message = message;
        }
        public String getCode(){
            return code;
        }

        public String getMessage(){
            return message;
        }
    }

    public enum ATTACH_OPT_TYPE{
        /**
         * 附件操作类型
         */
        ADD("0", "新增"),
        DELETE("1", "删除");

        private String code;
        private String message;
        ATTACH_OPT_TYPE(String code, String message){
            this.code = code;
            this.message = message;
        }
        public String getCode(){
            return code;
        }

        public String getMessage(){
            return message;
        }
    }

    public enum PRO_DRAFT_STATUS{
        /**
         * 项目草稿状态
         */
        DRAFT("0", "草稿"),
        SUBMIT("1", "已提交"),
        DELETE("2", "删除");

        private String code;
        private String message;

        PRO_DRAFT_STATUS(String code, String message){
            this.code = code;
            this.message = message;
        }
        public String getCode(){
            return code;
        }

        public String getMessage(){
            return message;
        }
    }


    /**
     *  授权委托操作类型
     */
    public enum SURROGATE_OPT_TYPE {

        /**
         * 新增
         */
        ADD("0", "新增"),
        /**
         * 修改
         */
        UPDATE("1", "修改"),
        /**
         * 删除
         */
        DELETE("2", "删除"),
        /**
         * 修改删除
         */
        UPDATE_DELETE("3", "修改删除"),
        /**
         * 修改新增
         */
        UPDATE_ADD("4", "修改新增"),
        /**
         * 代办新增
         */
        ACTIVE_ADD("5", "代办新增"),
        /**
         * 代办删除
         */
        ACTIVE_DELETE("6", "代办删除");


        private String code;

        private String message;

        SURROGATE_OPT_TYPE(String code, String message){
            this.code = code;
            this.message = message;
        }

        public String getCode(){
            return code;
        }

        public String getMessage(){
            return message;
        }
    }

    /**
     * 备份项目类型
     */
    public enum PRO_COPY_STATUS{
        /**
         * 草稿项目
         */
        DRAFT(0, "草稿项目"),
        /**
         * 正常项目
         */
        NORMAL(1, "正常项目");

        private int code;

        private String message;

        PRO_COPY_STATUS(int code, String message){
            this.code = code;
            this.message = message;
        }

        public int getCode(){
            return code;
        }

        public String getMessage(){
            return message;
        }
    }

    /**
     * 邮件发送类型
     */
    public enum EMAIL_TYPE{
        /**
         * 待办提醒
         */
        TASK_CONTENT("0", "待办提醒"),
        /**
         * 项目审批结束提醒
         */
        END_CONTENT("1", "项目审批结束提醒");

        private String code;

        private String message;

        EMAIL_TYPE(String code, String message){
            this.code = code;
            this.message = message;
        }

        public String getCode(){
            return code;
        }

        public String getMessage(){
            return message;
        }
    }
}
