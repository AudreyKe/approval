package com.weshareholdings.approval.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ProParamter implements Serializable {

    private Integer id;

    private String type;

    private String value;

    private Date createTime;

    private static final long serialVersionUID = 1L;


}