package com.weshareholdings.approval.repository;

import com.weshareholdings.approval.vo.TaskActorVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.snaker.engine.entity.HistoryTask;
import org.snaker.engine.entity.Task;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface TaskMapper {

    /**
     * 批量删除当前活动任务的执行者列表
     */
    void batchDeleteTaskActor(@Param("taskIds") List<String> taskIds);

    /**
     * 根据orderId查询当前活动任务的执行者列表
     *
     * @param orderId
     * @return
     */
    List<String> queryTaskActor(@Param("orderId") String orderId);

    List<HistoryTask> selectAll(HistoryTask historyTask);

    Task selectTaskById(@Param("taskId") String taskId);

    List<Task> selectTaskByOrderIdAndDisplayName(@Param("orderId") String orderId,@Param("displayName") String displayName);

    Integer deleteTaskHisById(@Param("taskId") String taskId);

    Integer deleteTaskActorHisByTaskId(@Param("taskId") String taskId);

    void  closeDeleteForeignMatch();
    void  openDeleteForeignMatch();

    /**
     * 查询任务参与者
     * @param proId 项目id
     * @param taskName 任务名称
     * @return
     */
    List<String> selectTaskActorEmail(@Param("proId") String proId, @Param("taskName") String taskName);

    List<TaskActorVo> queryTaskActorByTaskId(@Param("taskId") String taskId);

    String queryTaskActorHisByTaskId(@Param("taskId") String taskId,@Param("actorId") String actorId);

    Integer updateTaskActorHisById(@Param("taskActorHisId") String taskActorHisId,@Param("type") String type,@Param("surrogateId") String surrogateId);
}
