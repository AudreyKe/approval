package com.weshareholdings.approval.util;

import com.weshareholdings.approval.vo.Result;

public class ResultUtil {

    public static final String SUCCESS_CODE = "000";
    public static final String FAIL_CODE = "500";

    public static Result success(String message, Object object) {
        Result result = new Result();
        result.setCode(SUCCESS_CODE);
        result.setMessage(message);
        result.setData(object);
        return result;
    }

    public static Result error(String code, String message) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static Result error(String message) {
        Result result = new Result();
        result.setCode(FAIL_CODE);
        result.setMessage(message);
        return result;
    }

    public static Result success(String message) {
        Result result = new Result();
        result.setCode(SUCCESS_CODE);
        result.setMessage(message);
        return result;
    }

    public static Result success() {
        Result result = new Result();
        result.setCode(SUCCESS_CODE);
        return result;
    }
}
