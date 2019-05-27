package com.weshareholdings.approval.controller;

import com.weshareholdings.approval.po.MenuAction;
import com.weshareholdings.approval.service.impl.AuthorityService;
import com.weshareholdings.approval.util.ResultUtil;
import com.weshareholdings.approval.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/authorityAuthentication")
public class AuthorityAuthenticationController {

    private final AuthorityService authorityService;

    @Autowired
    public AuthorityAuthenticationController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @PostMapping("authorizate")
    public Result authorizate(@RequestBody String  permissionString, HttpServletRequest request){
       // authorityService.setMenuAndActionSession(request,1);
        Object actions = request.getSession().getAttribute("action");
        if(actions!=null){
            String actionss = actions.toString();
            if(actionss.contains(permissionString)){
                return ResultUtil.success("校验成功");
            }
        }else {
            return ResultUtil.error("对不起!用户登陆超时!");
        }
        return ResultUtil.error("对不起!你没有该功能的操作权限!");
    }

    @PostMapping("getAllMenu")
    public Result getAllMenu(HttpServletRequest request){
        Object menus = request.getSession().getAttribute("menu");
        if(menus!=null){
            return ResultUtil.success("成功",((List<MenuAction>)menus));
        }else {
            return ResultUtil.error("对不起!用户登陆超时!");
        }
    }

    @PostMapping("getAllActionByMenuId")
    public Result getAllActionByMenuCode(@RequestBody String  menuId, HttpServletRequest request){
        Object actions = request.getSession().getAttribute("action");
        if(actions!=null){
            List<MenuAction> allActions = (List<MenuAction>)actions;
            List<MenuAction> resultActions = new ArrayList<>();
            for (MenuAction menuAction:allActions) {
                if(menuId.equals(menuAction.getFid()+"")){
                    resultActions.add(menuAction);
                }
            }
            return  ResultUtil.success("成功",resultActions);
        }else {
            return ResultUtil.error("对不起!用户登陆超时!");
        }
    }

}
