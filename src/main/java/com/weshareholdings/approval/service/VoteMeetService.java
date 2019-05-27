package com.weshareholdings.approval.service;

import com.weshareholdings.approval.vo.VoteProInfo;
import com.weshareholdings.approval.vo.VoteProInfoVo;
import org.snaker.engine.access.Page;

public interface VoteMeetService {


    Page<VoteProInfo> queryVoteMeetList(String operator, Page<VoteProInfo> page, String proName,String proType);

    VoteProInfoVo queryProById(String orderId);
}
