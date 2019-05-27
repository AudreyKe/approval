package com.weshareholdings.approval.repository;

import com.weshareholdings.approval.vo.OrderResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.snaker.engine.entity.Task;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface OrderMapper {

    /**
     * 查询待办审批
     *
     * @param operator
     * @return
     */
    List<OrderResult> queryOrderResult(@Param("operator") String operator, @Param("orderName") String orderName,@Param("proTypes") String... proTypes);

    /**
     * 查询已办审批
     *
     * @param operator
     * @return
     */
    List<OrderResult> queryHistoryTask(@Param("operator") String operator, @Param("orderName") String orderName, @Param("proTypes") String... proTypes);

    /**
     * 查询
     *
     * @param operator
     * @param orderState
     * @param processId
     * @return
     */
    List<OrderResult> queryOrder(@Param("operator") String operator, @Param("orderState") String orderState, @Param("processId") String processId, @Param("orderName") String orderName, @Param("baseProTypes") List<String> baseProTypes,@Param("proType") String proType);

    /**
     * 查询审批流
     *
     * @param orderId
     * @return
     */
    List<String> queryOrderFlowById(String orderId);

    /**
     * 查询任务名及审批结果
     *
     * @param orderId
     * @return
     */
    @Select("SELECT display_Name as displayName, variable FROM wf_hist_task\n" +
            "WHERE  order_Id = #{orderId} ORDER BY finish_Time ASC ")
    List<Task> queryTaskById(String orderId);

    /**
     * 根据立项流程ID查询可发起评审流程
     *
     * @param beforeOrderId
     * @return
     */
    List<String> queryApprovalByBeforeOrderId(@Param("beforeOrderId") String beforeOrderId);

    /**
     * 查询可发起评审的流程
     * @param userId
     * @return
     */
    List<OrderResult> queryApproval(@Param("userId") int userId, @Param("orderName") String orderName, @Param("proType") String proType);
//    List<OrderResult> queryApproval(@Param("operator") String operator, @Param("orderState") String orderState, @Param("processId") String processId, @Param("orderName") String orderName);


}
