package com.weshareholdings.approval.service;


import com.weshareholdings.approval.vo.OrderResult;
import org.snaker.engine.access.Page;
import org.snaker.engine.core.AccessService;
import org.snaker.engine.entity.Task;

import java.util.List;
import java.util.Map;

public interface TaskService {

    /**
     * 查询待办审批
     *
     * @param page
     * @param operator
     * @return
     */
    Page<OrderResult> queryActiveTask(Page<OrderResult> page, String operator, String orderName, String proType);

    /**
     * 查询已办审批
     *
     * @param page
     * @param operator
     * @return
     */
    Page<OrderResult> queryHistoryTask(Page<OrderResult> page, String operator, String orderName, String proType);

    /**
     * 根据用户查询流程实例信息
     *
     * @param page
     * @param user
     * @return
     */
    Page<OrderResult> queryOrder(Page<OrderResult> page, String[] user, String orderState, String processId, String orderName, String proType);

    /**
     * 根据用户查询流程实例信息
     *
     * @param page
     * @param user
     * @return
     */
    Page<OrderResult> queryAllPro(Page<OrderResult> page, String[] user, String orderState, String processId, String orderName, String proType);


    /**
     * 终止流程实例
     *
     * @param orderId
     * @param params
     * @param operator
     */
    void terminateOrder(String orderId, Map<String, Object> params, String operator, Task task);

    /**
     * 根据用户查询待发起审批流程实例信息
     *
     * @param page
     * @param operator
     * @return
     */
    Page<OrderResult> queryApprovalByCreator(Page<OrderResult> page, String operator, String orderState, String processId, String orderName, String proType);

    Map<String, String> vote(String orderId, String operator, Task task);

    /**
     * 结束当前任务
     * @param task 任务
     * @param variable
     * @param operator 操作者
     * @param taskId 任务Id
     * @param taskState 任务结果
     */
    void endTask(Task task, String variable, String operator, String taskId, Integer taskState);

    /**
     * 结束当前流程
     * @param orderId 流程id
     * @param orderState 流程状态
     */
    void endOrder(String orderId, int orderState);

    /**
     * 查询项目列表
     *
     * @param page        分页参数
     * @param user 当前用户
     * @param processId   流程定义ID
     * @param orderName   流程名
     * @param taskNames   可编辑任务节点（若当前任务处于该节点则判断为可编辑）
     * @param proType
     * @return
     */
    Page<OrderResult> queryOrderList(Page<OrderResult> page, String[] user, String processId, String orderName, String[] taskNames, String proType);

    /**
     * 终止当前活动任务并跳转到指定任务节点
     *
     * @param orderId  流程ID
     * @param operator 当前用户
     * @param task     当前任务
     * @param taskName 要跳转的任务名
     */
    void terminateActiveTaskAndJump(String orderId, String operator, Task task, String taskName, String variable);

    /**
     * 跳转到指定任务节点
     *
     * @param orderId  流程ID
     * @param task     当前任务
     * @param taskName 要跳转的任务名
     */
    List<Task> jump(String orderId, Task task, String taskName);
}
