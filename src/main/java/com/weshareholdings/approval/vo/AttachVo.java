package com.weshareholdings.approval.vo;

import com.weshareholdings.approval.po.Attach;
import lombok.Data;

/**
 * @Author liling
 * @Date 2018/12/18
 **/
@Data
public class AttachVo extends Attach {
    /**
     * 操作类型
     */
//    private Integer optType;

    /**
     * 附件审批状态
     */
    private  String attachTaskState;

    /**
     * 驳回原因
     */
    private String backReason;

    /**
     * 附件任务ID
     */
    private Integer attachTaskId;

    /**
     * 附件状态标志（用于前端判断是否显示操作内容）
     */
    private String flag;

}
