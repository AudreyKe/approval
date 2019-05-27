package com.weshareholdings.approval.service;

import com.weshareholdings.approval.vo.DataUseApplyProInfo;
import com.weshareholdings.approval.vo.Result;
import org.snaker.engine.access.Page;

import java.util.Map;

public interface DataUseApplyService {


    /**
     * 查询数据使用申请列表
     * @param operator 申请人
     * @param page 分页对象
     * @return
     */
    Page<DataUseApplyProInfo> queryDataUseApplyList(String operator, Page<DataUseApplyProInfo> page);


    /**
     * 根据流程id查询数据使用申请详情
     * @param orderId 流程id
     * @return
     */
    DataUseApplyProInfo queryProByOrderId(String orderId);

    /**
     * 根据草稿id查询数据使用申请草稿详情
     * @param id 流程id
     * @return
     */
    DataUseApplyProInfo proDraftDetail(String id);

    /**
     * 添加项目
     * @param proInfo 数据申请对象
     * @param user 用户
     * @return
     */
    Result addProInfo(Map proInfo, String[] user);

    /**
     * 数据使用申请草稿保存
     * @param proInfo 数据申请对象
     * @param user 用户
     * @return
     */
    Result addProDraft(Map proInfo, String[] user);

    /**
     * 查询所有应用系统名称
     * @return
     */
    Result queryAppName();
}
