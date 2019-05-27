package com.weshareholdings.approval.repository;

import com.weshareholdings.approval.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface UserMapper {

    /**
     * 根据用户名查询用户信息
     *
     * @param userName
     * @return
     */
    List<User> getUserByUserName(@Param("userName") String userName);

    /**
     * 根据用户名模糊查询用户信息(排除当前登录用户)
     *
     * @param userName
     * @return
     */
    List<User> getUserLikeUserName(@Param("userName") String userName, @Param("fullName") String fullName,@Param("proType")String proType);

    /**
     * 新增用户
     *
     * @param user
     */
    void insertUser(User user);

    /**
     * 根据流程id查询需要发送邮件提醒的用户的邮箱
     *
     * @param param
     * @return
     */
    List<String> queryUserEmailByOrderId(Map<String, String> param);

    /**
     * 根据流程id查询项目团队邮箱
     * @param orderId
     * @return
     */
    List<String> queryTeamEmailByOrderId(@Param("orderId") String orderId);

}
