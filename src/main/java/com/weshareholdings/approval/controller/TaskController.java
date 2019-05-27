package com.weshareholdings.approval.controller;

import com.weshareholdings.approval.service.FlowService;
import com.weshareholdings.approval.service.TaskService;
import com.weshareholdings.approval.util.DateUtil;
import com.weshareholdings.approval.util.RedisUtil;
import com.weshareholdings.approval.util.ResultUtil;
import com.weshareholdings.approval.util.SnakerEngineFacets;
import com.weshareholdings.approval.vo.OrderResult;
import com.weshareholdings.approval.vo.Result;
import org.snaker.engine.access.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/snaker/task")
@RestController
public class TaskController {

    @Autowired
    private SnakerEngineFacets facets;
    @Autowired
    private FlowService flowService;
    @Autowired
    private TaskService taskService;
    @Autowired
    @Qualifier("redisUtil")
    private RedisUtil redisUtil;


    /**
     * 根据当前用户查询待办任务列表
     *
     * @return
     */
    @RequestMapping(value = "activeTasks")
    public Result userTaskList(Page<OrderResult> page, HttpServletRequest request, String orderName, String proType) {
        String operator = DateUtil.getRedisValue(request, redisUtil);
        return ResultUtil.success("执行成功", taskService.queryActiveTask(page, operator, orderName, proType));
    }

    /**
     * 活动任务的驳回
     *
     * @param taskId
     * @return
     */
    @RequestMapping(value = "reject", method = RequestMethod.GET)
    public Result activeTaskReject(String taskId, HttpServletRequest request) {
        try {
            String operator = DateUtil.getRedisValue(request, redisUtil);
            // String operator = request.getRemoteUser();
            // 驳回人需获取当前用户
            facets.executeAndJump(taskId, operator, null, null);
        } catch (Exception e) {
            return ResultUtil.error(ResultUtil.FAIL_CODE, "执行失败");
        }
        return ResultUtil.success("执行成功");
    }

    /**
     * 审批流查询接口
     *
     * @param orderId
     * @returnxua
     */
    @GetMapping(value = "histTasks/{orderId}")
    public Result getOrderFlowById(@PathVariable("orderId") String orderId) {
        return ResultUtil.success("执行成功", flowService.getApprovalFlowResult(orderId));
    }
}
