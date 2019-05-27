package com.weshareholdings.approval.repository;

import com.weshareholdings.approval.po.Menu;
import com.weshareholdings.approval.po.MenuAction;
import com.weshareholdings.approval.po.MenuChild;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AuthorityMapper {

    /**
     * 根据用户id查询该用户配置的所有菜单
     * @param userId
     * @return
     */
    List<MenuAction> getAllMenuByUserId(int userId);

    /**
     * 根据用户id查询该用户配置的所有父菜单
     * @param userId
     * @return
     */
    List<Menu> getAllFatherMenuByUserId(int userId);

    /**
     * 根据用户id查询和父菜单id该用户配置的指定父菜单下的子菜单
     * @param userId
     * @return
     */
    List<MenuChild> getAllMenuByUserIdAndFid(@Param("userId")int userId, @Param("fid") int fid);

    /**
     * 根据用户id查询该用户配置的所有动作
     * @param userId
     * @return
     */
    List<MenuAction> getAllActionByUserId(int userId);

    /**
     * 查询用户是否拥有某一角色的授权
     * @param userId
     * @param userCodes
     * @return
     */
    int getCountOfRoleByUserId(@Param("userId") String userId, @Param("userCodes") String... userCodes);
}