package com.weshareholdings.approval.controller;

import com.alibaba.fastjson.JSONObject;
import com.weshareholdings.approval.service.SecretoryService;
import com.weshareholdings.approval.util.DateUtil;
import com.weshareholdings.approval.util.RedisUtil;
import com.weshareholdings.approval.util.ResultUtil;
import com.weshareholdings.approval.vo.FileApprovalVo;
import com.weshareholdings.approval.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.snaker.engine.access.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author liling
 * @Date 2018/12/18
 **/
@RequestMapping("/snaker/secretary/")
@RestController
@Slf4j
public class SecretaryController {

    @Autowired
    private SecretoryService secretoryService;
    @Autowired
    @Qualifier("redisUtil")
    private RedisUtil redisUtil;

    /**
     * 提交上传材料
     * @return
     */
    @RequestMapping("submitFile")
    public Result submitFile(@RequestBody JSONObject attachs){
        log.info("开始提交材料：" + attachs);
        String message ;
        try {
            message =  secretoryService.submitFile(JSONObject.toJSONString(attachs.get("fileList")), attachs.getInteger("proId"));
        }catch (Exception e){
            e.printStackTrace();
            log.error("材料提交失败,失败原因：" + e.getMessage());
            return ResultUtil.error("材料提交失败");
        }
        return ResultUtil.success(message);
    }

    /**
     *查询提交审核材料的项目列表
     * @param page
     * @param proType
     * @param proName
     * @return
     */
    @RequestMapping("getProsForFileApproval")
    public Result getProsForFileApproval(Page<FileApprovalVo> page, String proType, String proName, HttpServletRequest request){
        String[] user = DateUtil.getRedisUser(request, redisUtil);
        Page<FileApprovalVo> result = secretoryService.getProsForFileApproval(page, user, proName, proType);
        return  ResultUtil.success("查询成功", result);
    }

    /**
     * 查询附件审批详情
     * @param proId
     * @return
     */
    @RequestMapping("getFileForFileApproval")
    public Result getFileForFileApproval(String proId){
        return ResultUtil.success("查询成功", secretoryService.getFileForFileApproval(proId));
    }

    /**
     * 提交附件审批结果
     * @param fileResult
     * @param request
     * @return
     */
    @RequestMapping("submitFileApprovalResult")
    public Result submitFileApprovalResult(@RequestBody JSONObject fileResult, HttpServletRequest request){
        String[] user = DateUtil.getRedisUser(request, redisUtil);
        Map<String, Object> result = secretoryService.submitFileApprovalResult(user, JSONObject.toJSONString(fileResult.get("fileResult")));
        if(ResultUtil.FAIL_CODE.equals(result.get("code"))){
            return ResultUtil.error("参数有误");
        }
        return ResultUtil.success("提交成功");
    }

    /**
     * 查询审批中的项目
     * @param proType
     * @param proName
     * @param request
     * @return
     */
    @RequestMapping("getProsInApproval")
    public Result getProsInApproval(Page<FileApprovalVo> page, String proType, String proName, HttpServletRequest request){
        String[] user = DateUtil.getRedisUser(request, redisUtil);
        return  ResultUtil.success("查询成功", secretoryService.getProsInApproval(page, user, proType, proName));
    }

    /**
     * 发送提醒
     * @param orderIds
     * @param request
     * @return
     */
    @RequestMapping("sendRemind")
    public Result sendRemind(@RequestBody JSONObject orderIds, HttpServletRequest request){
        String[] user = DateUtil.getRedisUser(request, redisUtil);
        secretoryService.sendRemind(user, JSONObject.toJSONString(orderIds.get("orderIds")));
        return ResultUtil.success("发送提醒成功");
    }
}
