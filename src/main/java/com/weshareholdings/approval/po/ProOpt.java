package com.weshareholdings.approval.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProOpt implements Serializable {

    /**
     * 操作id
     */
    private Integer id;

    /**
     * 流程id
     */
    private Integer proId;

    /**
     * 历史流程id
     */
    private Integer proCopyId;

    /**
     * 修改内容
     */
    private String content;

    /**
     * 步骤
     */
    private Integer step;

    /**
     * 操作者ID
     */
    private Integer userId;

    /**
     * 创建时间
     */
    private String createtime;

    /**
     * 项目类型 0:正常项目、1:草稿
     */
    private int status;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", proId=").append(proId);
        sb.append(", proCopyId=").append(proCopyId);
        sb.append(", content=").append(content);
        sb.append(", step=").append(step);
        sb.append(", createtime=").append(createtime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}