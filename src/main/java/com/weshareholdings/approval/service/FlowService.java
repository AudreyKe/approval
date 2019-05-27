package com.weshareholdings.approval.service;

import com.weshareholdings.approval.po.Attach;
import com.weshareholdings.approval.po.ProInfo;
import com.weshareholdings.approval.po.ProInfoDraft;
import com.weshareholdings.approval.po.User;
import com.weshareholdings.approval.vo.ApprovalFlowResult;
import com.weshareholdings.approval.vo.ProDraftVo;
import com.weshareholdings.approval.vo.ProInfoVo;
import com.weshareholdings.approval.vo.Result;
import org.snaker.engine.access.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface FlowService {

    /**
     * 查询项目
     *
     * @param id
     */
    ProInfoVo queryProById(String id);

    /**
     * 添加项目
     *
     * @param proInfo
     * @param user
     * @return
     */
    boolean addProInfo(Map proInfo, String[] user, String processId);

    /**
     * 根据流程ID 查询审批流
     *
     * @param orderId
     * @return
     */
    List<String> getOrderFlowById(String orderId);

    /**
     * 根据附件ID 查询附件信息
     *
     * @param fileId
     * @return
     */
    Attach queryPathByFileId(String fileId, String status);

    /**
     * 根据用户账号查询用户信息
     *
     * @param userName 用户账号
     * @return
     */
    User queryUserByUserName(String userName);

    /**
     * 根据部门查询上会人员
     * @param department 部门code
     * @return
     */
    List<User> queryMeetPersonByProType(String department);


    /**
     * 项目审批详情
     *
     * @param orderId
     * @return
     */
    ProInfoVo queryApprovalDetail(String orderId);

    /**
     * 附件上传
     * @param file 文件流
     * @param user 当前用户信息
     * @param fileType 文件类型
     * @return
     */
    Attach uploadAttach(MultipartFile file,String[] user,String fileType);

    /**
     * 查询审批流
     *
     * @param orderId
     * @return
     */
    List<ApprovalFlowResult> getApprovalFlowResult(String orderId);

    /**
     * 修改立项内容
     *
     * @param proInfo 项目信息
     * @return
     */
    Map<String, String> submitModify(Map<String, Object> proInfo, String[] user,String processId);

    /**
     * 执行审批
     * @return
     */
    Result process(String acceptanceConclusion,String processId, String orderId, String taskId, String operator, String resultState, String result, String nextOperator, String ccOperator, String[] nodes, String meetMembers);

    /**
     * 保存项目
     * @param proInfo
     * @param user
     * @return
     */
    Result saveProInfoDraft(Map<String, Object> proInfo, String[] user ,String processId);

    /**
     * 查询项目草稿
     * @param page
     * @param proName
     * @param proType
     * @param users
     * @return
     */
    Page<ProDraftVo> getProDrafts(Page<ProDraftVo> page, String proName, String proType, String[] users);

    /**
     * 删除草稿
     * @param id
     * @param users
     */
    void deleteProDraft(int id, String[] users);

    /**
     * 根据项目id项目草稿详细信息
     * @param id
     * @return
     */
    ProInfoDraft getProDraftDetailById(String id);
}
