package com.weshareholdings.approval.vo;

import lombok.Data;

/**
 * @Author liling
 * @Date 2018/9/12
 **/
@Data
public class ProcessResult {

    /**
     * 流程id
     */
    private String processId;

    /**
     * 流程名称
     */
    private String processName;

    /**
     * 流程显示名称
     */
    private String proDisplayName;
}
