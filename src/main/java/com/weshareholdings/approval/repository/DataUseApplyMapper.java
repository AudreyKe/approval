package com.weshareholdings.approval.repository;


import com.weshareholdings.approval.vo.DataUseApplyProInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface DataUseApplyMapper {

    /**
     * 查询数据使用申请列表
     * @param operator 操作人
     * @return 返回结果
     */
    List<DataUseApplyProInfo> queryDataUseApplyList(@Param("operator") String operator, @Param("proType") String proType);

    /**
     * 根据流程id查询数据使用申请详情
     * @param orderId 流程id
     * @return 返回结果
     */
    DataUseApplyProInfo queryProByOrderId(@Param("orderId") String orderId);

    /**
     * 根据草稿id查询数据使用申请草稿详情
     * @param id 流程id
     * @return 返回结果
     */
    DataUseApplyProInfo proDraftDetail(@Param("id") String id);

    /**
     * 根据数据申请类型查询执行流程类型
     * @param applyCategorys 申请类别
     * @return 返回结果
     */
    List<String> queryTypeByApplyCategory(@Param("applyCategorys") String applyCategorys);

    /**
     * 根据执行流程类型查询流程id
     * @param type 执行流程类型
     * @return 返回结果
     */
    String queryProcessIdByType(@Param("type") String type);

    /**
     * 根据流程id查询任务id和任务名称和申请部门
     * @param orderId 流程id
     * @return 返回结果
     */
    Map queryTaskIdByOrderId(@Param("orderId") String orderId);

    /**
     * 根据流程id查询任务id和任务名称和申请部门
     * @param orderId 流程id
     * @return 返回结果
     */
    Map queryTaskIdByOrderIdNoPro(@Param("orderId") String orderId);

    /**
     * 根据任务id查询项目id和任务名称
     * @param taskId 任务id
     * @return 返回结果
     */
    Map queryTaskHisIdByOrderId(@Param("taskId") String taskId);

    /**
     * 查询所有应用系统名称
     * @return 返回结果
     */
    List<Map> queryAppName();

    /**
     * 往项目表中记录一条数据
     * @param proInfo 项目详情
     * @return 返回结果
     */
    Integer insertPro(Map proInfo);

    /**
     * 往项目草稿表中记录一条数据
     * @param proInfo 项目详情
     * @return 返回结果
     */
    Integer insertProDraft(Map proInfo);

    /**
     * 根据附件id修改项目草稿
     * @param param 修改参数map
     * @return 返回结果
     */
    Integer updateProDraftByProId(Map param);

    /**
     * 根据附件id修改项目验收结论
     * @param param 修改参数map
     * @return 返回结果
     */
    Integer updateProByProId(Map param);

    /**
     * 根据附件id修改项目历史验收结论
     * @param param 修改参数map
     * @return 返回结果
     */
    Integer updateProCopyByProId(Map param);

    /**
     * 往项目历史表中记录一条数据
     * @param proInfoCopy 项目历史详情
     * @return 返回结果
     */
    Integer insertProCopy(Map proInfoCopy);

    /**
     * 根据申请部门和申请数据类别判断流程中是否有审核冲突人员
     * @param applyDepartment 申请部门
     * @param applyCategory 申请数据类别
     * @return 返回结果
     */
    String queryCountByApplyDepartmentAndApplyCategory(@Param("applyDepartment") String applyDepartment,@Param("applyCategory") String applyCategory);

    /**
     * 根据申请部门和审批任务名称、申请类别查询任务参与者
     * @param applyDepartment 申请部门
     * @param displayName 审批任务名称
     * @param applyCategory 申请类别
     * @return 返回结果
     */
    List<String> queryActorByApplyDepartmentAndDisplayName(@Param("applyDepartment") String applyDepartment,@Param("displayName") String displayName,@Param("applyCategory") String applyCategory);

    /**
     * 根据申请人查询申请人邮箱
     * @param usernames 申请部门
     * @return 返回结果
     */
    List<String> queryEmailByUserName(@Param("usernames") String usernames);

    /**
     * 根据流程id查询项目申请者
     * @param orderId 流程id
     * @return 返回结果
     */
    String queryApplyUserByOrderId(@Param("orderId") String orderId);

    /**
     * 根据流程id查询流程详情
     * @param orderId 流程id
     * @return 返回结果
     */
    Map<String,String> queryProcessDetailByOrderId(@Param("orderId") String orderId);

    /**
     * 根据流程id查询当前节点详情
     * @param orderId 流程id
     * @return 返回结果
     */
    Map<String,String> queryNowNodeByOrderId(@Param("orderId") String orderId);

    /**
     * 根据流程id查询申请者
     * @param orderId 流程id
     * @return 返回结果
     */
    List<String> queryApplyName(@Param("orderId") String orderId);

    /**
     * 根据申请部门和审批任务名称、申请类别查询任务参与者
     * @param applyDepartment 申请部门
     * @param displayName 审批任务名称
     * @param applyCategory 申请类别
     * @return 返回结果
     */
    List<String> queryActorNameByApplyDepartmentAndDisplayName(@Param("applyDepartment") String applyDepartment,@Param("displayName") String displayName,@Param("applyCategory") String applyCategory);

    /**
     * 根据任务名称和流程id查询任务参与者
     * @param displayName 审批任务名称
     * @param orderId 申请类别
     * @return 返回结果
     */
    List<String> queryActorNameNoCofig(@Param("displayName") String displayName,@Param("orderId") String orderId);

}