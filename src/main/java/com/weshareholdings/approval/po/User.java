package com.weshareholdings.approval.po;

import lombok.Data;

@Data
public class User {
    /**
     * id
     */
    private String id;

    /**
     * 地址
     */
    private String address;

    /**
     * 年龄
     */
    private String age;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 是否禁用
     */
    private String enabled;

    /**
     * 全名
     */
    private String fullName;

    /**
     * 性别
     */
    private String sex;

    /**
     * 用户账号
     */
    private String userName;

    /**
     *  组织机构
     */
    private String org;

    /**
     *  用户类型
     */
    private String type;
}
