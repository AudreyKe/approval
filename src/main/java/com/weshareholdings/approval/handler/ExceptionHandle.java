package com.weshareholdings.approval.handler;

import com.weshareholdings.approval.util.ResultUtil;
import com.weshareholdings.approval.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author liling
 * @Date 2018/9/11
 **/
@ControllerAdvice
public class ExceptionHandle {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) {
        logger.info("拦截controller异常开始");
        logger.error(e.getMessage(), e);
        e.printStackTrace();
        return ResultUtil.error("500", e.getMessage());
    }

}
