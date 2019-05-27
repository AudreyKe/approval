package com.weshareholdings.approval.vo;

import com.weshareholdings.approval.po.Attach;
import com.weshareholdings.approval.po.ProLayer;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class VoteProInfo {
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

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 状态
     */
    private String orderState;

    /**
     * 流程实例id
     */
    private String orderId;
}
