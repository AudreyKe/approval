package com.weshareholdings.approval.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weshareholdings.approval.config.BaseConstant;
import com.weshareholdings.approval.po.Attach;
import com.weshareholdings.approval.po.ProAttachTask;
import com.weshareholdings.approval.po.ProInfo;
import com.weshareholdings.approval.repository.*;
import com.weshareholdings.approval.service.SecretoryService;
import com.weshareholdings.approval.util.ResultUtil;
import com.weshareholdings.approval.util.SendEmailUtil;
import com.weshareholdings.approval.vo.AttachVo;
import com.weshareholdings.approval.vo.FileApprovalVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.snaker.engine.access.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author liling
 * @Date 2018/12/18
 **/
@Service
@Slf4j
public class SecretoryServiceImpl implements SecretoryService {
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private ProAttachTaskMapper attachTaskMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SendEmailUtil sendEmailUtil;
    @Value("${stmp.account}")
    private String fromEmail;
    @Value("${stmp.subject}")
    private String subject;
    @Autowired
    private AuthorityMapper authorityMapper;
    @Autowired
    private TaskMapper taskMapper;

    @Override
    public String submitFile(String attachs, int proId) {
        String result = "提交成功";
        List<AttachVo> attachList = JSON.parseArray(attachs, AttachVo.class);
        Set<Integer> addSet = new HashSet();
        Set<Integer> deleteSet = new HashSet();
        for(AttachVo attachVo : attachList){
            // 分离删除附件及新增附件
            if(BaseConstant.ATTACH_OPT_TYPE.ADD.getCode().equals(String.valueOf(attachVo.getOptType()))){
                addSet.add(attachVo.getId());
            }else if (BaseConstant.ATTACH_OPT_TYPE.DELETE.getCode().equals(String.valueOf(attachVo.getOptType()))){
                deleteSet.add(attachVo.getId());
            }
        }
        Set<Integer> repeatSet = new HashSet<>();
        repeatSet.addAll(addSet);
        repeatSet.retainAll(deleteSet);
        log.info("无效数据：" + JSON.toJSONString(repeatSet));
        List<AttachVo> attachVos = new ArrayList<>();
        List<String> errorFileNames = new ArrayList<>();
        List<ProAttachTask> proAttachTasks;
        for(AttachVo attachVo : attachList){
            if(repeatSet.contains(attachVo.getId())){
                continue;
            }
            proAttachTasks = attachTaskMapper.selectAttachTask(null, String.valueOf(attachVo.getId()), BaseConstant.ATTACH_TASK_STATUS.UN_APPROVAL.getCode());
            if (proAttachTasks != null && proAttachTasks.size()>0){
                attachVos.add(attachVo);
                errorFileNames.add(attachVo.getFileName());
            }else{
                attachVo.setProId(proId);
                projectMapper.updateAttachByAttachId(attachVo);
                this.insertAttachTask(attachVo);
            }
        }
        if(attachVos.size() > 0){
            log.error("提交失败，提交内容正在审批中。。" + JSON.toJSONString(attachVos));
            result =  "文件：" + JSON.toJSONString(errorFileNames) + "正在审核中，请勿重复提交！";
        }
        return result;
    }

    /**
     * 待优化
      */
    @Override
    public Page<FileApprovalVo> getProsForFileApproval(Page<FileApprovalVo> page, String[] user, String proName, String proType) {
        List<FileApprovalVo> approvalVoList = new ArrayList<>();
        // 根据当前登录用户判断显示哪种类型的项目
        if(user != null && user.length > 3 && StringUtils.isNotBlank(user[3])){
            List<String> baseProType = new ArrayList<>(Arrays.asList(user[3].split(",")));
            // 判断当前用户是否是秘书角色或管理员角色
            int count = authorityMapper.getCountOfRoleByUserId(user[1],"Secretary","Admin");
            PageHelper.startPage(page.getPageNo(), page.getPageSize());
            if(count > 0){
                approvalVoList = attachTaskMapper.selectProsForFileApproval(proName, baseProType, proType);
            }else{
                approvalVoList = attachTaskMapper.selectProsForFileApprovalByUser(proName, baseProType, proType, user[1]);
            }
        }
        PageInfo<FileApprovalVo> pageInfo = new PageInfo<>(approvalVoList);
        page.setResult(approvalVoList);
        page.setTotalCount(pageInfo.getTotal());
        return page;
    }

    @Override
    public List<AttachVo> getFileForFileApproval(String proId) {
        return attachTaskMapper.selectFileWithTask(proId);
    }

    @Override
    public Map<String, Object> submitFileApprovalResult(String[] user, String result) {
        log.info("开始处理附件审批结果。。。" + result);
        Map<String, Object> returnResult = new HashMap<>();
        JSONArray jsonArray = JSONObject.parseArray(result);
        for(int i = 0; i < jsonArray.size(); i++){
            JSONObject object = jsonArray.getJSONObject(i);
            if(object.get("attachTaskId") == null || object.get("attachTaskState") == null || object.get("optType") == null){
                returnResult.put("code", ResultUtil.FAIL_CODE);
                returnResult.put("message", "参数缺失");
                log.error("执行失败，参数缺失");
                return returnResult;
            }
            // 更新附件任务表
            ProAttachTask attachTask = new ProAttachTask();
            attachTask.setId(object.getInteger("attachTaskId"));
            attachTask.setStatus(object.getString("attachTaskState"));
            attachTask.setOptType(object.getInteger("optType"));
            attachTask.setUserId(Integer.parseInt(user[1]));
            attachTask.setBackReason(object.getString("backReason"));
            attachTaskMapper.updateByPrimaryKeySelective(attachTask);
            if(BaseConstant.ATTACH_OPT_TYPE.DELETE.getCode().equals(object.getString("optType")) && BaseConstant.ATTACH_TASK_STATUS.APPROVAL_PASS.getCode().equals(attachTask.getStatus())){
                //更新附件表状态
                Attach attach = new Attach();
                attach.setStatus(BaseConstant.ATTACH_STATUS.DELETE.getCode());
                attach.setId(object.getInteger("id"));
                projectMapper.updateAttachByAttachId(attach);
                //是否需要更新附件备份表 -todo
            }
        }
        // 附件更新后不通知委员
//        if(jsonArray.size()>0){
//            JSONObject object = jsonArray.getJSONObject(0);
//            String attachId = object.getString("id");
//            // 判断当前流程是否已上会，是则邮件通知已审批的委员 todo
//            ProInfo proInfo = projectMapper.selectProInfoByAttachId(attachId);
//            if(proInfo != null){
//                List<String> emails = taskMapper.selectTaskActorEmail(String.valueOf(proInfo.getId()), "meetApproval");
//                if (emails != null && emails.size() > 0) {
//                    String toEmail = emails.toString().substring(1, emails.toString().length() - 1);
//                    sendEmailUtil.sendEmail(fromEmail, toEmail, subject,  attachUpdateContext);
//                }
//            }
//        }


        log.info("附件审批结果处理结束。。。");
        return returnResult;
    }

    @Override
    public Page<FileApprovalVo> getProsInApproval(Page<FileApprovalVo> page, String[] user, String proType, String proName) {
        // 根据当前用户判断传入什么项目类型 --todo
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        List<FileApprovalVo> approvalVoList = new ArrayList<>();
        /*if(StringUtils.isNotBlank(proType)){
            approvalVoList = projectMapper.selectApprovalPros(proName, proType);
        }else{
            approvalVoList = projectMapper.selectApprovalPros(proName);
        }*/
        // 根据当前登录用户判断显示哪种类型的项目
        if(user != null && user.length > 3 && StringUtils.isNotBlank(user[3])){
            List<String> baseProType = new ArrayList<>(Arrays.asList(user[3].split(",")));
            approvalVoList = projectMapper.selectApprovalPros(proName, baseProType, proType);
        }
        PageInfo<FileApprovalVo> pageInfo = new PageInfo<>(approvalVoList);
        page.setResult(approvalVoList);
        page.setTotalCount(pageInfo.getTotal());
        return page;
    }

    @Override
    public void sendRemind(String[] user, String orderIds) {
        JSONArray orderIdList = JSONObject.parseArray(orderIds);
        Map<String, String> map = new HashMap<String, String>(16);
        for (int i = 0; i < orderIdList.size(); i++ ){
            String orderId = orderIdList.getJSONObject(i).getString("orderId");
            map.put("orderId", orderId);
            List<String> emails = userMapper.queryUserEmailByOrderId(map);
            ProInfo proInfo = projectMapper.queryProById(orderId);
            sendEmailUtil.sendEmail(proInfo,emails);
            map.clear();
        }
    }

    private void insertAttachTask(AttachVo attachVo) {
        ProAttachTask proAttachTask = new ProAttachTask();
        proAttachTask.setAttachId(attachVo.getId());
        proAttachTask.setOptType(attachVo.getOptType());
        proAttachTask.setProId(attachVo.getProId());
        proAttachTask.setStatus(BaseConstant.ATTACH_TASK_STATUS.UN_APPROVAL.getCode());
        proAttachTask.setUserId(Integer.parseInt(attachVo.getUploadPersonId()));
        attachTaskMapper.insertSelective(proAttachTask);
    }
}
