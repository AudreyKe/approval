package com.weshareholdings.approval.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class WsSysUser implements Serializable {

    private int id;

    private String name;

    private Integer orgId;

    private Integer gender;

    private Integer status;

    private String email;

    private String tel;

    private String code;

    private String password;

    private String salt;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

}