package com.weshareholdings.approval.vo;

import com.alibaba.fastjson.JSONObject;
import com.weshareholdings.approval.po.ProInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author liling
 * @Date 2018/9/10
 **/
@Data
public class ProInfoVo extends ProInfo implements Serializable {

    /**
     * 意见集合
     */
    private List<ApprovalFlowResult> resultList;

    /**
     * 修改内容
     */
    private JSONObject changeData;

    public List<ApprovalFlowResult> getResultList() {
        return resultList;
    }

    public void setResultList(List<ApprovalFlowResult> resultList) {
        this.resultList = resultList;
    }
}
