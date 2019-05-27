
package com.weshareholdings.approval.controller;

import com.weshareholdings.approval.config.Props;
import com.weshareholdings.approval.po.User;
import com.weshareholdings.approval.service.FlowService;
import com.weshareholdings.approval.service.impl.AuthorityService;
import com.weshareholdings.approval.util.*;
import com.weshareholdings.approval.vo.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/snaker/auth/")
public class LoginController {

    @Autowired
    @Qualifier("redisUtil")
    private RedisUtil redisUtil;

//    @Autowired
//    private XyyRedisTemplate xyyRedisTemplate;

    @Autowired
    private FlowService flowService;
    @Autowired
    private Props props;
    @Autowired
    private SendEmailUtil sendEmailUtil;
    @Value("${stmp.account}")
    private String fromEmail;
    @Autowired
    private AuthorityService authorityService;
   /* @Autowired
    private CasConfigProperties casConfigProperties;*/


    @RequestMapping("login")
    public Result login(String userName, String password, HttpServletRequest request, HttpServletResponse response) {
        LDAPAuthentication ldap = new LDAPAuthentication();
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            return ResultUtil.error("500", "身份验证失败,用户名和密码不能为空！");
        }
        if (ldap.authenricate(userName, password, props)) {
            HttpSession session = request.getSession();
            User user = flowService.queryUserByUserName(userName);
            if (user == null) {
                return ResultUtil.error("500", "身份验证失败,该用户在本系统不存在，请先维护进系统！");
            }
            String userValue = userName + "-" + user.getId() + "-" + System.currentTimeMillis()+"-"+user.getOrg();
            redisUtil.set(session.getId(), userValue);//往redis设置用户信息 key：sessionId ；value：用户账号，用户Id，当前时间戳
//            xyyRedisTemplate.set(session.getId(),userValue);
//            Cookie userAccountCookie = new Cookie("sessionId", session.getId());
//            userAccountCookie.setPath("/snaker/");
//            response.addCookie(userAccountCookie);
            Map<String,Object> result = new HashMap<>();
            result.put("userName",userName);
            result.put("menuList",authorityService.setMenuAndActionSession(request, Integer.parseInt(user.getId())));
            return ResultUtil.success("登录成功", result);
        } else {
            return ResultUtil.error("500", "身份验证失败,用户名或密码错误！");
        }
    }

    @RequestMapping("logout")
    public Result logout(HttpServletRequest request) {
        String sessionId = DateUtil.getCookieByKey(request, "sessionId");
        if (sessionId == null) {
            sessionId = DateUtil.getCookieByKey(request, "JSESSIONID");
            if (sessionId != null) {
                redisUtil.remove(sessionId);
            }
        } else {
            redisUtil.remove(sessionId);
        }
        return ResultUtil.success("登出成功");
    }

    /**
     * 获取redis里用户信息
     */
    @GetMapping(value = "test")
    public Result test(HttpServletRequest request, HttpServletResponse response) {
        String sessionId = DateUtil.getCookieByKey(request, "sessionId");
        String map = redisUtil.get(sessionId);
//        String map =  xyyRedisTemplate.get(sessionId);
        return ResultUtil.success("执行成功", map);
    }

  /* @RequestMapping("login")
    public Result login(HttpServletRequest request){
        //获取cas给我们传递回来的对象，这个东西放到了session中
        //session的 key是 _const_cas_assertion_
        Assertion assertion = (Assertion) request.getSession().getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);

        //获取登录用户名
        String loginName =assertion.getPrincipal().getName();
        User user = flowService.queryUserByUserName(loginName);
        if(user == null){
            return ResultUtil.error("用户" + loginName + "在本系统不存在，请先维护进系统");
        }
        return ResultUtil.success("登录成功");

    }

    @RequestMapping("logout")
    public void logout(HttpSession session, HttpServletResponse response) throws Exception{
        session.invalidate();
        response.sendRedirect(casConfigProperties.getServerLoginUrl());
    }*/
}