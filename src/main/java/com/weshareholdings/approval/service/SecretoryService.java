package com.weshareholdings.approval.service;


import com.weshareholdings.approval.vo.AttachVo;
import com.weshareholdings.approval.vo.FileApprovalVo;
import org.snaker.engine.access.Page;

import java.util.List;
import java.util.Map;

/**
 * @Author liling
 * @Date 2018/12/18
 **/
public interface SecretoryService {

    /**
     * 提交上传附件
     * @param attachs
     * @param proId
     */
    String submitFile(String attachs,int proId);

    /**
     * 查询提交审核材料的项目列表
     * @param proName
     * @param proType
     * @return
     */
    Page<FileApprovalVo> getProsForFileApproval(Page<FileApprovalVo> page, String[] user, String proName, String proType);

    /**
     *根据项目ID查询附件审批信息
     * @param proId
     * @return
     */
    List<AttachVo> getFileForFileApproval(String proId);

    /**
     *提交附件审批结果
     * @param user
     * @param result
     */
    Map<String, Object> submitFileApprovalResult(String[] user, String result);

    /**
     *查询审批中的项目
     * @param page
     * @param user
     * @param proType
     * @param proName
     * @return
     */
    Page<FileApprovalVo> getProsInApproval(Page<FileApprovalVo> page, String[] user, String proType, String proName);

    /**
     * 发送提醒
     * @param user
     * @param orderIdList
     */
    void sendRemind(String[] user, String orderIdList);
}
