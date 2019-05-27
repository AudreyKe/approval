package com.weshareholdings.approval.po;

import lombok.Data;

@Data
public class Team {
    /**
     * id
     */
    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户姓名
     */
    private String fullName;
}
