package com.weshareholdings.approval.vo;

import lombok.Data;

@Data
public class OrderSurrogate {

    /**
     * 流程id
     */
    private String orderId;

    /**
     * 流程实例名
     */
    private String orderName;


}
