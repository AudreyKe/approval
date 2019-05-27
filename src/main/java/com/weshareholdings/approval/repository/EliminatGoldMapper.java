package com.weshareholdings.approval.repository;


import com.weshareholdings.approval.po.ProInfo;
import com.weshareholdings.approval.vo.GoldProInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface EliminatGoldMapper {

    /**
     * 查询消金项目
     *
     * @param operator
     * @return
     */
    List<ProInfo> queryEliminatGoldList(@Param("operator") String operator, @Param("proName") String proName,@Param("projectProType") String projectProType, @Param("goldProType") String goldProType);

    /**
     * 根据流程id查询消金项目详情
     * @param orderId
     * @return
     */
    GoldProInfo queryProByOtderId(@Param("orderId") String orderId);
}