package com.weshareholdings.approval.po;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProLayer {
    /**
     * 分层id
     */
    private int id;

    /**
     * 项目id
     */
    private int proId;

    /**
     * 分级
     */
    private String level;

    /**
     * 发行金额
     */
    private BigDecimal amount;

    /**
     * 占比
     */
    private BigDecimal proportion;

    /**
     * 发行利率
     */
    private String issueRate;

    /**
     * 产品期限
     */
    private String proTerm;

    /**
     * 加权平均期限
     */
    private String averageTerm;

}
