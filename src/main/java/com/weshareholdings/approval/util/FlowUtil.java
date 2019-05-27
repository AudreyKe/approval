package com.weshareholdings.approval.util;

import com.alibaba.fastjson.JSONObject;
import com.weshareholdings.approval.config.BaseConstant;
import com.weshareholdings.approval.po.*;
import com.weshareholdings.approval.repository.*;
import com.weshareholdings.approval.service.impl.FlowServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snaker.engine.IQueryService;
import org.snaker.engine.ITaskService;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.entity.Order;
import org.snaker.engine.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author yiyh
 * @Date 2019/02/19
 **/
@Service
public class FlowUtil {
    private static Logger logger = LoggerFactory.getLogger(FlowUtil.class);

    @Autowired
    private ITaskService taskService;
    @Autowired
    private DataUseApplyMapper dataUseApplyMapper;
    @Autowired
    private SnakerEngineFacets facets;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IQueryService iQueryService;
    @Autowired
    private ITaskService iTaskService;
    @Autowired
    private WfTaskResultMapper wfTaskResultMapper;
    @Autowired
    private ProOptMapper proOptMapper;
    @Autowired
    private SendEmailUtil sendEmailUtil;
    @Autowired
    private ProjectMapper projectMapper;
    @Value("${stmp.account}")
    private String fromEmail;
    @Value("${stmp.subject}")
    private String subject;

    /**
     * 根据流程id添加任务参与者
     * @param orderId 流程id
     * @param applyCategory 申请类别
     * @param applyDepartment 申请部门
     * @return
     */
    public boolean addTaskActor(String orderId,String applyCategory,String applyDepartment){
        //根据流程id查询任务id和任务名称和申请部门
        Map task = dataUseApplyMapper.queryTaskIdByOrderIdNoPro(orderId);
        if(task==null){
            return false;
        }
        String taskId = task.get("id")+"";
        String taskName = task.get("display_Name")+"";
        logger.info("下一流程节点为："+taskName);
        List<String> actorList = dataUseApplyMapper.queryActorByApplyDepartmentAndDisplayName(applyDepartment,taskName,applyCategory);
        if("申请验收".equals(taskName)){
            String username = dataUseApplyMapper.queryApplyUserByOrderId(orderId);
            actorList.add(username);
        }
        if(actorList==null||actorList.size()<=0){
            return true;
        }
        for(String actor:actorList){
            taskService.addTaskActor(taskId,actor);
        }
        return false;
    }


    /**
     * 根据流程id添加任务参与者
     * @param orderId 流程id
     * @return
     */
    public boolean addTaskActor(String orderId){
        //根据流程id查询任务id和任务名称和申请部门
        Map task = dataUseApplyMapper.queryTaskIdByOrderId(orderId);
        if(task==null){
            return false;
        }
        String taskId = task.get("id")+"";
        String taskName = task.get("display_Name")+"";
        String applyCategory = task.get("applyCategory")+"";
        String applyDepartment = task.get("applyDepartment")+"";
        if(applyCategory==null){
            return false;
        }
        applyCategory = arrayToJsonStrSingle(applyCategory.split(","));
        logger.info("下一流程节点为："+taskName);
        List<String> actorList = dataUseApplyMapper.queryActorByApplyDepartmentAndDisplayName(applyDepartment,taskName,applyCategory);
        if("申请验收".equals(taskName)){
            String username = dataUseApplyMapper.queryApplyUserByOrderId(orderId);
            actorList.add(username);
        }
        if(actorList==null||actorList.size()<=0){
            return true;
        }
        for(String actor:actorList){
            taskService.addTaskActor(taskId,actor);
        }
        sendEmailUtil.sendEmail(projectMapper.queryProById(orderId),dataUseApplyMapper.queryEmailByUserName(listToJsonStrSingle(actorList)));
        return false;
    }

    /**
     * 数据使用申请执行申请验收节点时修改项目信息和项目历史信息
     * @param taskId 任务id
     * @return
     */
    public boolean dataApplyUpdateProInfo(String taskId,String acceptanceConclusion){
        //根据流程id查询任务id和任务名称和申请部门
        Map task = dataUseApplyMapper.queryTaskHisIdByOrderId(taskId);
        if(task==null){
            return false;
        }
        String proId = task.get("id")+"";
        String taskName = task.get("display_Name")+"";
        if("申请验收".equals(taskName)){
            Map param = new HashMap();
            param.put("proId",proId);
            param.put("acceptanceConclusion",acceptanceConclusion);
            dataUseApplyMapper.updateProByProId(param);
            dataUseApplyMapper.updateProCopyByProId(param);
        }
        return false;
    }

    /**
     * 验证用户是否存在并新增流程
     * @param proInfo 项目详情
     * @param processId 流程id
     * @param userId 用户id
     * @return
     */
    public String insertOrder(Map proInfo,String processId,String userId){
        Map<String, Object> args = new HashMap<>();
        args.put("proName", proInfo.get("proName"));
        //新建流程
        Order order = facets.startInstanceById(processId, userId, args);
        //根据用户名查询用户信息
        List<User> users = userMapper.getUserByUserName(userId);
        if (users == null || users.size() == 0) {
            logger.error("用户名：" + userId + "的用户信息不存在");
            return null;
        }
        //根据filter查询活动任务
        List<Task> tasks = iQueryService.getActiveTasks(new QueryFilter().setOrderId(order.getId()).setName("apply"));
        for (Task task : tasks) {
            iTaskService.addTaskActor(task.getId(), userId);
            facets.execute(task.getId(), userId, null);
            this.saveTaskResult(order.getId(), task, users.get(0).getFullName()); // 保存任务结果
        }
        if (order == null) {
            return null;
        }
        proInfo.put("orderId", order.getId());

        if (proInfo.get("proName") == null&&!BaseConstant.PROTYPE.DATA_USE_APPLY.getCode().equals(proInfo.get("proType"))) {
            return null;
        }
        return order.getId();
    }

    /**
     * 保存任务结果
     *
     * @param orderId 流程ID
     * @param task    任务信息
     * @param userId  当前用户
     */
    private void saveTaskResult(String orderId, Task task, String userId) {
        WfTaskResult taskResult = new WfTaskResult();
        taskResult.setTaskid(task.getId());
        taskResult.setResulttype(task.getTaskName());
        taskResult.setResult("提交申请");
        taskResult.setResultstate("0");
        taskResult.setOperator(userId);
        taskResult.setOrderid(orderId);
        wfTaskResultMapper.insertSelective(taskResult);
    }

    /**
     * 保存操作记录
     *
     * @param content   修改内容
     * @param proCopyId 历史项目ID
     * @param proId     项目ID
     * @param status 备份项目类型00:正常项目、01:草稿
     */
    public void saveProOpt(String content, int proCopyId, int proId, int userId, int status) {
        ProOpt proOpt = new ProOpt();
        proOpt.setContent(content);
        proOpt.setProCopyId(proCopyId);
        proOpt.setProId(proId);
        proOpt.setStep(StringUtils.isBlank(proOptMapper.getStep(Integer.valueOf(proId))) ? 1 : Integer.valueOf(proOptMapper.getStep(Integer.valueOf(proId))) + 1);
        proOpt.setUserId(userId);
        proOpt.setStatus(status);
        proOptMapper.insertSelective(proOpt);
    }

    /**
     * 数组转json字符串
     * @param strs 数组
     * @return json字符串
     */
    public String arrayToJsonStrSingle(List<String> strs){
        String resultStr = "";
        for(int i=0,len=strs.size();i<len;i++){
            if(i==len-1){
                resultStr = resultStr + "'"+strs.get(i)+"'";
            }else{
                resultStr = resultStr + "'"+strs.get(i)+"',";
            }
        }
        return resultStr;
    }
    /**
     * 数组转json字符串
     * @param strs 数组
     * @return json字符串
     */
    public static String arrayToJsonStrSingle(String[] strs){
        String resultStr = "";
        for(int i=0,len=strs.length;i<len;i++){
            if(i==len-1){
                resultStr = resultStr + "'"+strs[i]+"'";
            }else{
                resultStr = resultStr + "'"+strs[i]+"',";
            }
        }
        return resultStr;
    }

    /**
     * list数组转json字符串
     * @param strs 数组
     * @return json字符串
     */
    public static String listToJsonStrSingle(List<String> strs){
        String resultStr = "";
        for(int i=0,len=strs.size();i<len;i++){
            if(i==len-1){
                resultStr = resultStr + "'"+strs.get(i)+"'";
            }else{
                resultStr = resultStr + "'"+strs.get(i)+"',";
            }
        }
        return resultStr;
    }

    /**
     * 数组转json字符串
     * @param strs 数组
     * @return json字符串
     */
    public String arrayToJsonStr(List<String> strs){
        String resultStr = "";
        for(int i=0,len=strs.size();i<len;i++){
            if(i==len-1){
                resultStr = resultStr + strs.get(i);
            }else{
                resultStr = resultStr + strs.get(i)+",";
            }
        }
        return resultStr;
    }

    /**
     * 给下一个节点发送邮件提醒
     * @param orderId 流程id
     * @param parentTaskId 父任务id
     */
    public void processNextNodeSendEmail(String orderId, String parentTaskId) {
        Map<String, String> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("parentTaskId", parentTaskId);
        List<String> emails = userMapper.queryUserEmailByOrderId(map);
        ProInfo proInfo = projectMapper.queryProById(orderId);
        sendEmailUtil.sendEmail(proInfo,emails);
    }


    /**
     * 验证项目参数
     * @param proInfo 项目信息
     * @return
     */
    public Map matchParam(JSONObject proInfo){
        Map mResult = new HashMap();
        if(BaseConstant.PROTYPE.ABS_PROJECT.getCode().equals(proInfo.get("proType"))||BaseConstant.PROTYPE.ABS_REVIEW.getCode().equals(proInfo.get("proType"))){
            mResult = DateUtil.matchParam(proInfo, BaseConstant.ENABLEPROCESS_EXPRESSION_ABS);
        }else if(BaseConstant.PROTYPE.CAST_PROJECT.getCode().equals(proInfo.get("proType"))){
            mResult = DateUtil.matchParam(proInfo, BaseConstant.ENABLEPROCESS_EXPRESSION_VOTE);
        }else if(BaseConstant.PROTYPE.CONSUMER_PROJECT.getCode().equals(proInfo.get("proType"))){
            mResult = DateUtil.matchParam(proInfo, BaseConstant.ENABLEPROCESS_EXPRESSION_ELIMINATING);
        }else if(BaseConstant.PROTYPE.DATA_USE_APPLY.getCode().equals(proInfo.get("proType"))){
            mResult = DateUtil.matchParam(proInfo, BaseConstant.DATA_USE_APPLY);
        }
        return mResult;
    }
}
