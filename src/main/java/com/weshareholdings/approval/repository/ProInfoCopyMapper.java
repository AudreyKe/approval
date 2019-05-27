package com.weshareholdings.approval.repository;

import com.weshareholdings.approval.po.ProInfoCopy;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Mapper
public interface ProInfoCopyMapper {
    int deleteByPrimaryKey(String key);

    int insert(ProInfoCopy record);

    int insertSelective(ProInfoCopy record);

    ProInfoCopy selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ProInfoCopy record);

    int updateByPrimaryKey(ProInfoCopy record);

    /**
     * 添加项目
     *
     * @param proInfoCopy 项目详情
     * @return
     */
    int insertProInfoVo(Map proInfoCopy);
}