package com.weshareholdings.approval.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class WfTaskResult implements Serializable {

    private int id;

    private String orderid;

    private String taskid;

    private String result;

    private String resultstate;

    private String resulttype;

    private String operator;

    private String displayName;

    private Date createtime;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", orderid=").append(orderid);
        sb.append(", taskid=").append(taskid);
        sb.append(", result=").append(result);
        sb.append(", resultstate=").append(resultstate);
        sb.append(", resulttype=").append(resulttype);
        sb.append(", operator=").append(operator);
        sb.append(", createtime=").append(createtime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}