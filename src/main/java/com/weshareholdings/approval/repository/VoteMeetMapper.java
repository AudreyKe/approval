package com.weshareholdings.approval.repository;


import com.weshareholdings.approval.po.ProInfoTradeStructure;
import com.weshareholdings.approval.vo.VoteProInfo;
import com.weshareholdings.approval.vo.VoteProInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface VoteMeetMapper {

    /**
     * 查询待办托委会项目
     *
     * @param operator
     * @return
     */
    List<VoteProInfo> queryVoteMeetList(@Param("operator") String operator, @Param("proName") String proName, @Param("projectProType") String projectProType, @Param("voteProType") String voteProType, @Param("proType") String proType);

    /**
     * 根据流程id查询投委项目详情
     * @param orderId
     * @return
     */
    VoteProInfoVo queryProByOtderId(@Param("orderId") String orderId);

    /**
     * 根据项目id查询项目交易结构
     * @param proId
     * @return
     */
    ProInfoTradeStructure queryProInfoTradeStructureByProId(@Param("proId") String proId);
}