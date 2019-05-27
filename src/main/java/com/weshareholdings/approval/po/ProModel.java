package com.weshareholdings.approval.po;

import lombok.Data;

@Data
public class ProModel {
    /**
     * id
     */
    private String modelId;

    /**
     * 创建者id
     */
    private String userId;

    /**
     * code
     */
    private String code;

    /**
     * 名称
     */
    private String name;
}
