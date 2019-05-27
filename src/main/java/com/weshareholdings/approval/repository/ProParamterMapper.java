package com.weshareholdings.approval.repository;


import com.weshareholdings.approval.po.ProParamter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface ProParamterMapper {
    int deleteByPrimaryKey(String key);

    int insert(ProParamter record);

    int insertSelective(ProParamter record);

    ProParamter selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ProParamter record);

    int updateByPrimaryKey(ProParamter record);

    /**
     * 根据类型查询参数值
     *
     * @param type
     * @return
     */
    List<String> selectProParamter(@Param("type") String type, @Param("value") String value);
}