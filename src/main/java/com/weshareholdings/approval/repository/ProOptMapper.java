package com.weshareholdings.approval.repository;


import com.weshareholdings.approval.po.ProOpt;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface ProOptMapper {

    int insert(ProOpt record);

    int insertSelective(ProOpt record);

    int updateByPrimaryKeySelective(ProOpt record);

    int updateByPrimaryKey(ProOpt record);

    /**
     * 根据项目id查询当前序列号
     *
     * @param proId
     * @return
     */
    String getStep(int proId);
}