package com.weshareholdings.approval.service;


import com.weshareholdings.approval.po.SurrogateInfo;
import com.weshareholdings.approval.vo.OrderSurrogate;
import com.weshareholdings.approval.vo.Result;
import com.weshareholdings.approval.vo.SurrogateVo;
import org.snaker.engine.access.Page;

import java.util.List;

/**
 * @Author liling
 * @Date 2018/9/10
 **/
public interface SurrogateService {

    /**
     * 获取委托授权列表
     */
    Page<SurrogateVo> querySurrogateList(Page<SurrogateVo> page, String operator);

    /**
     * 获取参数信息
     *
     * @param page
     * @param value
     * @return
     */
    Page<String> queryProParamter(Page<String> page, String value);

    /**
     * 新增授权
     *
     * @param surrogateInfo
     * @return
     */
    Result addSurrogate(SurrogateInfo surrogateInfo);

    /**
     * 编辑授权
     *
     * @param surrogateInfo
     * @return
     */
    Result editSurrogate(SurrogateInfo surrogateInfo);

    /**
     * 删除授权
     *
     * @param id
     * @return
     */
    Result deleteSurrogate(String id);

    /**
     * 查询可授权的流程
     *
     * @param operator
     * @return
     */
    List<OrderSurrogate> querySurrogateOrderList(String operator);
}
