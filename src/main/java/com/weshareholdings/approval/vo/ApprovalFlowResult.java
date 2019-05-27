package com.weshareholdings.approval.vo;

import lombok.Data;

/**
 * @Author liling
 * @Date 2018/9/10
 **/
@Data
public class ApprovalFlowResult {

    /**
     * 审批意见
     */
    private String result;

    /**
     * 审批结果
     */
    private String resultState;

    /**
     * 日期
     */
    private String date;

    /**
     * 操作者
     */
    private String operator;

    /**
     * 意见类型
     */
    private String resultType;

    /**
     * 审批节点
     */
    private String approvalNode;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultState() {
        return resultState;
    }

    public void setResultState(String resultState) {
        this.resultState = resultState;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }
}
