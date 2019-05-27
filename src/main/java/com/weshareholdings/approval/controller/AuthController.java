package com.weshareholdings.approval.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.weshareholdings.approval.po.User;
import com.weshareholdings.approval.service.FlowService;
import com.weshareholdings.approval.service.impl.AuthorityService;
import com.weshareholdings.approval.util.HttpsUtils;
import com.weshareholdings.approval.util.LDAPAuthentication;
import com.weshareholdings.approval.util.RedisUtil;
import com.weshareholdings.approval.util.ResultUtil;
import com.weshareholdings.approval.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 企业微信登录验证
 *
 * @Author liling
 * @Date 2018/11/27
 **/
@Slf4j
@RestController
@RequestMapping("/snaker/wechat/")
public class AuthController {
    @Autowired
    private FlowService flowService;
    @Autowired
    @Qualifier("redisUtil")
    private RedisUtil redisUtil;

    @Value("${wechat.appId}")
    private String appId;
    @Value("${wechat.corpsecret}")
    private String corpsecret;
    @Autowired
    private AuthorityService authorityService;

    @RequestMapping("login")
    public Result login(HttpServletRequest request, HttpServletResponse response) {
        log.info("进入企业微信登录验证。。。");
        // 获取accessToken
        String getTokenUrl = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + appId + "&corpsecret=" + corpsecret;
        log.info("请求企业微信获取access_token接口，请求参数为：url=" + getTokenUrl);
        String tokenResult = HttpsUtils.httpsRequest(getTokenUrl, "GET", null, "application/json", 2000);
        log.info("请求企业微信获取access_token结果，result=" + tokenResult);
        JSONObject jsonObject = JSONObject.parseObject(tokenResult);
        String errcode = String.valueOf(jsonObject.get("errcode"));
        String errmsg = (String) jsonObject.get("errmsg");
        if (!"0".equals(errcode)) {
            log.error("获取access_token失败。");
            return ResultUtil.error(errcode, errmsg);
        }
        String accessToken = (String) jsonObject.get("access_token");

        String code = request.getParameter("code");
        String url = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=" + accessToken + "&code=" + code;
        log.info("请求企业微信获取用户信息接口，请求参数为：url=" + url);
        String result = HttpsUtils.httpsRequest(url, "GET", null, "application/json", 2000);
        log.info("获取企业微信获取用户信息接口结果，result=" + result);
        JSONObject userJson = JSONObject.parseObject(result);
        String errcode1 = String.valueOf(userJson.get("errcode"));
        String errmsg1 = (String) userJson.get("errmsg");
        // 查询成功
        if (!"0".equals(errcode1)) {
            log.error("获取访问用户身份信息失败。。");
            return ResultUtil.error(errcode1, errmsg1);
        }
        String userId = (String) userJson.get("UserId");
        if (-1 != userId.indexOf("@")) {
            userId = userId.substring(0, userId.indexOf("@"));
        }

        HttpSession session = request.getSession();
        User user = flowService.queryUserByUserName(userId);
        if (user == null) {
            return ResultUtil.error("500", "身份验证失败,该用户在本系统不存在，请先维护进系统！");
        }
//        String userValue = userId + "," + user.getId() + "," + System.currentTimeMillis();
        String userValue = userId + "-" + user.getId() + "-" + System.currentTimeMillis()+"-"+user.getOrg();
        redisUtil.set(session.getId(), userValue);
        Map<String,Object> userInfo = new HashMap<>();
        userInfo.put("userName",userId);
        userInfo.put("menuList",authorityService.setMenuAndActionSession(request, Integer.parseInt(user.getId())));
        log.info("进入企业微信登录验证成功。。。");
        log.info("用户信息：" + JSON.toJSONString(userInfo));
        return ResultUtil.success("登录成功", userInfo);
    }

    public static String getUrl() {
        // 测试环境
//        String MAIN_URL = "http://risk-approval.weshareholdings.com.cn/Wechat";
//        String agentId = "1000003";
//        String appId = "ww050711af216fbb0c";

        // 生产环境
        String MAIN_URL = "https://risk-approval.weshareholdings.com/Wechat";
        String agentId = "1000026";
        String appId = "ww41c7c10a677de598";

        String redirect_uri = URLEncoder.encode(MAIN_URL);
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
                "appid=" + appId +   // 转换环境时需修改
                "&redirect_uri=" + redirect_uri +
                "&response_type=code" +
                "&scope=snsapi_base" +
                "&agentid=" + agentId +
                "&state=STATE" +
                "#wechat_redirect";
        System.out.println(url);
        return url;
    }

    public static void main(String[] args) {
        getUrl();
    }

}
