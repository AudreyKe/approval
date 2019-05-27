package com.weshareholdings.approval.repository;


import com.weshareholdings.approval.po.WfTaskResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface WfTaskResultMapper {

    int deleteByPrimaryKey(String key);

    int insert(WfTaskResult record);

    int insertSelective(WfTaskResult record);

    WfTaskResult selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(WfTaskResult record);

    int updateByPrimaryKey(WfTaskResult record);

    List<WfTaskResult> selectByOrderId(@Param("orderId") String orderId);

    List<WfTaskResult> selectResults(WfTaskResult result);
}