package com.weshareholdings.approval.service.impl;

import com.weshareholdings.approval.po.Menu;
import com.weshareholdings.approval.po.MenuAction;
import com.weshareholdings.approval.po.MenuChild;
import com.weshareholdings.approval.repository.AuthorityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author xiaobin.huang
 */
@Component
public class AuthorityService {

    private final AuthorityMapper authorityMapper;

    @Autowired
    public AuthorityService(AuthorityMapper authorityMapper) {
        this.authorityMapper = authorityMapper;
    }

    public List<Menu> setMenuAndActionSession(HttpServletRequest request, int userId){
        List<Menu> menus = authorityMapper.getAllFatherMenuByUserId(userId);//根据用户id查询该用户配置的所有父菜单
        for(Menu menu:menus){//循环设置父菜单下的子菜单
            List<MenuChild> menuChildren = authorityMapper.getAllMenuByUserIdAndFid(userId,menu.getId());
            menu.setChild(menuChildren);
        }
        List<MenuAction> actions = authorityMapper.getAllActionByUserId(userId);
        request.getSession().setAttribute("menu",menus);
        request.getSession().setAttribute("action",actions);
        return menus;
    }
}
