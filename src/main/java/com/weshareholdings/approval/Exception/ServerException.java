package com.weshareholdings.approval.Exception;

import lombok.Data;

/**
 * @Author liling
 * @Date 2018/11/1
 **/
@Data
public class ServerException extends RuntimeException {
    private static final long serialVersionUID = -9089143409689179150L;

    /**
     * 异常信息描述
     */
    private String message;

    /**
     * 异常编码
     */
    private String code;

    public ServerException(Throwable e) {
        super(e);
    }

    public ServerException(String message) {
        super(message);
    }

    public ServerException(String message, Throwable e) {
        super(message, e);
    }

    public ServerException(String message, String code) {
        this.message = message;
        this.code = code;
    }
}
