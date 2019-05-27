package com.weshareholdings.approval.vo;

import lombok.Data;

/**
 * 返回结果封装类
 */
@Data
public class Result {

    // 响应码
    private String code;

    // 返回信息
    private String message;

    //返回结果
    private Object data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
