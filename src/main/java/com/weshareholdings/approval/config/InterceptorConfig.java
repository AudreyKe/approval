package com.weshareholdings.approval.config;

import com.alibaba.fastjson.JSON;
import com.weshareholdings.approval.util.DateUtil;
import com.weshareholdings.approval.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;

@Component
public class InterceptorConfig implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(InterceptorConfig.class);

    RedisUtil redisUtil;

    Props props;


    /**
     * 进入controller层之前拦截请求
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        log.info("进入用户身份授权拦截器，用户访问地址：" + httpServletRequest.getRequestURL());
        httpServletResponse.setCharacterEncoding("utf-8");
        // 获取用户访问地址
        String url = httpServletRequest.getRequestURI().replace("/snaker/", "");
        String sessionId = DateUtil.getCookieByKey(httpServletRequest, "sessionId");
        if (sessionId == null) {
            sessionId = DateUtil.getCookieByKey(httpServletRequest, "JSESSIONID");
        }
        if (redisUtil == null) { // 解决redisUtil为null无法注入问题
            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(httpServletRequest.getServletContext());
            redisUtil = (RedisUtil) factory.getBean("redisUtil");
            props = (Props) factory.getBean("props");
        }
        if (sessionId == null || "".equals(sessionId)) {
            PrintWriter printWriter = httpServletResponse.getWriter();
            printWriter.write("{\"code\":400,\"message\":\"session is invalid,please login again!\"}");
            return false;
        }
        String[] strs = DateUtil.getRedisUser(httpServletRequest, redisUtil);
        log.info("用户信息：" + JSON.toJSONString(strs));
        if (strs == null || "".equals(strs)) {
            redisUtil.remove(sessionId);
            PrintWriter printWriter = httpServletResponse.getWriter();
            printWriter.write("{\"code\":400,\"message\":\"session is invalid,please login again!\"}");
            return false;
        }
        String time = strs[2];
        if (System.currentTimeMillis() - Long.valueOf(time) < Integer.parseInt(props.getSessionTimeOut())) {
            String user = strs[0] + "-" + strs[1] + "-" + System.currentTimeMillis()+"-"+strs[3];
            redisUtil.set(sessionId, user);
            return true;
            // 校验当前url是否在可访问列表中
//            Object usable = httpServletRequest.getSession().getAttribute("action");
//            if (usable != null) {
//                String actions = usable.toString();
//                if (actions.contains(url)) {
//                    log.info("退出用户授权拦截器，权限验证成功！");
//                    return true;
//                }
//            }
//            PrintWriter printWriter = httpServletResponse.getWriter();
//            printWriter.write("{\"code\":10014,\"message\":\"对不起!你没有该功能的操作权限!\"}");
//            printWriter.close();
//            log.info("退出用户授权拦截器，用户无权访问！");
//            return false;
        } else {
            redisUtil.remove(sessionId);
            PrintWriter printWriter = httpServletResponse.getWriter();
            printWriter.write("{\"code\":400,\"message\":\"session is invalid,please login again!\"}");
            return false;
        }

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

}
