package com.weshareholdings.approval.service;

import com.weshareholdings.approval.po.ProInfo;
import com.weshareholdings.approval.vo.GoldProInfo;
import org.snaker.engine.access.Page;

public interface EliminatGoldService {


    Page<ProInfo> queryEliminatGoldList(String operator, Page<ProInfo> page, String proName);


    GoldProInfo queryProByOrderId(String orderId);
}
