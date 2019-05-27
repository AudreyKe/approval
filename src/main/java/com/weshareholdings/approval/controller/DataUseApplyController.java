package com.weshareholdings.approval.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.weshareholdings.approval.util.FlowUtil;
import org.snaker.engine.access.Page;
import com.weshareholdings.approval.service.DataUseApplyService;
import com.weshareholdings.approval.util.DateUtil;
import com.weshareholdings.approval.util.RedisUtil;
import com.weshareholdings.approval.util.ResultUtil;
import com.weshareholdings.approval.vo.DataUseApplyProInfo;
import com.weshareholdings.approval.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/snaker/dataUseApply/")
@RestController
@Slf4j
public class DataUseApplyController {

    @Autowired
    @Qualifier("redisUtil")
    private RedisUtil redisUtil;

    @Autowired
    private DataUseApplyService dataUseApplyService;

    @Autowired
    private FlowUtil flowUtil;
    /**
     * 查询数据使用申请列表
     * @param page
     * @param request
     * @return
     */
    @RequestMapping(value = "queryDataUseApplyList")
    public Result queryDataUseApplyList(HttpServletRequest request, Page<DataUseApplyProInfo> page) {
        String[] user = DateUtil.getRedisUser(request, redisUtil);
        if(user==null||user.length<=0){
            return ResultUtil.error("执行失败！请重新登陆！");
        }
        return ResultUtil.success("执行成功", dataUseApplyService.queryDataUseApplyList(user[1],page));
    }

    /**
     * 根据流程id查询数据使用申请详情
     * @param orderId 流程id
     * @return 返回结果
     */
    @GetMapping(value = "proDetail/{orderId}")
    public Result orderDetail(@PathVariable("orderId") String orderId) {
        return ResultUtil.success("执行成功", dataUseApplyService.queryProByOrderId(orderId));
    }

    /**
     * 根据草稿id查询数据使用申请草稿详情
     * @param id 流程id
     * @return 返回结果
     */
    @GetMapping(value = "proDraftDetail/{id}")
    public Result proDraftDetail(@PathVariable("id") String id) {
        return ResultUtil.success("执行成功", dataUseApplyService.proDraftDetail(id));
    }


    /**
     * 查询所有应用系统名称
     * @return 返回结果
     */
    @GetMapping(value = "queryAllAppName")
    public Result queryAllAppName() {
        return dataUseApplyService.queryAppName();
    }

    /**
     * 数据使用申请草稿保存
     * @param proInfo 项目草稿信息
     * @param request 请求对象
     * @return  返回结果
     */
    @RequestMapping(value = "addProDraft", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Result addProDraft(@RequestBody JSONObject proInfo, HttpServletRequest request) {
        log.info("进入添加数据申请草稿处理。。" + JSON.toJSONString(proInfo));
        String[] user = DateUtil.getRedisUser(request, redisUtil);
        if (user == null) {
            return ResultUtil.error("500", "执行失败");
        }
        return dataUseApplyService.addProDraft(handProInfoArray(proInfo), user);
    }

    /**
     * 处理项目中申请类别和申请类型数组
     * @param proInfo 项目信息
     * @return 处理后项目信息
     */
    private Map handProInfoArray(Map proInfo){
        if(proInfo.get("applyType")!=null){
            proInfo.put("applyType",flowUtil.arrayToJsonStr((List<String>) proInfo.get("applyType")));
        }
        if(proInfo.get("applyCategory")!=null){
            List<String> applyCategoryList = (List<String>) proInfo.get("applyCategory");
            proInfo.put("applyCategory",flowUtil.arrayToJsonStr(applyCategoryList));
            proInfo.put("applyCategoryParam",flowUtil.arrayToJsonStrSingle(applyCategoryList));
        }
        return  proInfo;
    }

    /**
     * 数据使用申请提交
     */
    @RequestMapping(value = "add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Result enableProcess(@RequestBody JSONObject proInfo, HttpServletRequest request) {
        log.info("进入添加数据申请处理。。" + JSON.toJSONString(proInfo));
        Map mResult =flowUtil.matchParam(proInfo);
        if ("E".equals(mResult.get("status"))) {
            return ResultUtil.error("500", "执行失败，" + mResult.get("errorMessage"));
        }
        String[] user = DateUtil.getRedisUser(request, redisUtil);
        if (user == null) {
            return ResultUtil.error("500", "执行失败");
        }
        return dataUseApplyService.addProInfo(handProInfoArray(proInfo), user);
    }

}
