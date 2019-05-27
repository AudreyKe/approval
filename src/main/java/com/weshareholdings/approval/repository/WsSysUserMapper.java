package com.weshareholdings.approval.repository;


import com.weshareholdings.approval.po.WsSysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface WsSysUserMapper {
    int deleteByPrimaryKey(String key);

    int insert(WsSysUser record);

    int insertSelective(WsSysUser record);

    WsSysUser selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(WsSysUser record);

    int updateByPrimaryKey(WsSysUser record);

    WsSysUser selectUserByUserName(@Param("userName") String userName);
}