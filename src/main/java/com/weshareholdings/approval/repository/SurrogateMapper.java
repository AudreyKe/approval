package com.weshareholdings.approval.repository;

import com.weshareholdings.approval.po.SurrogateInfo;
import com.weshareholdings.approval.vo.OrderSurrogate;
import com.weshareholdings.approval.vo.SurrogateVo;
import com.weshareholdings.approval.vo.TaskActorVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface SurrogateMapper {

    /**
     * 查询授权列表
     *
     * @return
     */
    List<SurrogateVo> querySurrogateList(@Param("operator") String operator);

    /**
     * 新增授权
     *
     * @return
     */
    Integer insertSurrogate(SurrogateInfo surrogateInfo);

    /**
     * 修改授权
     *
     * @return
     */
    Integer updateSurrogate(SurrogateInfo surrogateInfo);

    /**
     * 删除授权
     *
     * @return
     */
    Integer deleteSurrogate(String id);

    /**
     * 新增授权历史
     *
     * @return
     */
    Integer insertSurrogateHis(SurrogateInfo surrogateInfo);

    /**
     * 根据用户id和流程id查询任务参与者记录
     *
     * @return
     */
    List<TaskActorVo> queryTaskActorByTaskIdAndUserName(@Param("surrogate") String surrogate, @Param("orderId") String orderId, @Param("type") String type, @Param("surrogateId") String surrogateId,@Param("processId") String processId, @Param("batchId") String batchId);

    /**
     * 根据id删除任务参与者记录
     *
     * @return
     */
    Integer deleteTaskActor(String id);

    /**
     * 新增参与者记录
     *
     * @return
     */
    Integer insertTaskActor(TaskActorVo taskActor);

    /**
     * 新增参与者记录
     *
     * @return
     */
    Integer batchInsertTaskActor(@Param("taskActorVos")List<TaskActorVo> taskActorVos);

    /**
     * 根据批次号id查询授权详情
     *
     * @return
     */
    List<SurrogateInfo> querySurrogateByBatchId(@Param("batchId") String batchId);

    /**
     * 根据用户查询授权详情
     *
     * @return
     */
    List<SurrogateInfo> querySurrogateByUser(@Param("operator") String operator,@Param("type") String type);

    /**
     * 根据用户查询授权详情(空授权流程)
     *
     * @return
     */
    List<SurrogateInfo> querySurrogateChangeOrderIdByUser(@Param("operator") String operator,@Param("type") String type);

    /**
     * 修改授权任务为结束状态(授权时间已到或者流程所有节点都已经操作完毕)
     *
     * @return
     */
    Integer deleteSurrogateEnd(@Param("id") String id);

    /**
     * 查询可授权的流程
     * @param operator 用户名
     * @return
     */
    List<OrderSurrogate> querySurrogateOrderList(@Param("operator") String operator);

    /**
     * 根据操作人和流程定义id查询可授权的流程
     * @param operator 操作人
     * @param processId 流程定义id
     * @return
     */
    List<String> queryAllSurrogateByProcessId(@Param("operator") String operator,@Param("processId") String processId);

    /**
     * 根据批次号查询授权详情
     *
     * @return
     */
    List<SurrogateInfo> querySurrogateListByBatchId(@Param("batchId") String batchId,@Param("type") String type);

    /**
     * 批量插入授权历史
     *
     * @return
     */
    Integer batchInsertSurrogateHis(@Param("surrogateInfos") List<SurrogateInfo> surrogateInfos);

    /**
     * 批量插入授权
     *
     * @return
     */
    Integer batchInsertSurrogate(@Param("surrogateInfos") List<SurrogateInfo> surrogateInfos);

    /**
     * 根据批次号查询流程定义id
     * @return
     */
    String queryProcessIdByBatchId(@Param("batchId") String batchId);
}
