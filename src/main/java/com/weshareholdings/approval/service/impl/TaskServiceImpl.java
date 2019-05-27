package com.weshareholdings.approval.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weshareholdings.approval.config.BaseConstant;
import com.weshareholdings.approval.config.Props;
import com.weshareholdings.approval.po.*;
import com.weshareholdings.approval.repository.*;
import com.weshareholdings.approval.service.FlowService;
import com.weshareholdings.approval.service.TaskService;
import com.weshareholdings.approval.util.DateUtil;
import com.weshareholdings.approval.util.FlowUtil;
import com.weshareholdings.approval.util.SendEmailUtil;
import com.weshareholdings.approval.vo.OrderResult;
import com.weshareholdings.approval.vo.TaskActorVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snaker.engine.*;
import org.snaker.engine.access.Page;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.core.AccessService;
import org.snaker.engine.core.Execution;
import org.snaker.engine.core.ServiceContext;
import org.snaker.engine.core.SnakerEngineImpl;
import org.snaker.engine.entity.*;
import org.snaker.engine.entity.Process;
import org.snaker.engine.helper.DateHelper;
import org.snaker.engine.helper.JsonHelper;
import org.snaker.engine.model.NodeModel;
import org.snaker.engine.model.ProcessModel;
import org.snaker.engine.model.TransitionModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.*;

@Service
@Slf4j
public class TaskServiceImpl extends AccessService implements TaskService {
    private Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);
    @Autowired
    protected DBAccess access;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private SurrogateMapper surrogateMapper;

    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private Props props;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SendEmailUtil sendEmailUtil;
    @Value("${stmp.account}")
    private String fromEmail;
    @Value("${stmp.subject}")
    private String subject;
    @Autowired
    private WfTaskResultMapper taskResultMapper;
    @Autowired
    private FlowUtil flowUtil;


    @Override
    public Page<OrderResult> queryActiveTask(Page<OrderResult> page, String operator, String orderName, String proType) {
        handSurrogateTask(operator);
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        List<OrderResult> list;
        if(StringUtils.isNotBlank(proType)){
            list = orderMapper.queryOrderResult(operator, orderName, proType);
        }else{
            list = orderMapper.queryOrderResult(operator, orderName);
        }
        PageInfo<OrderResult> pageInfo = new PageInfo<>(list);
        for (OrderResult orderResult : list) {
            //默认不可编辑
            orderResult.setEditFlag(0);
//            orderResult.setPerformType(orderResult.getPerformType() + "-" + orderResult.getTaskType());
            orderResult.setPerformType(BaseConstant.PROTYPE.getMessageByCode(orderResult.getPerformType()) + "-" + orderResult.getTaskType());
            orderResult.setOrderState(BaseConstant.OrderState.get(orderResult.getOrderState()));
            List<Task> taskList = iQueryService.getActiveTasks(new QueryFilter().setOrderId(orderResult.getOrderId()).setNames(new String[]{"apply"}));
            // 判断当前待办是否有编辑权限
            if (taskList != null && taskList.size() > 0) {
                List<Team> teams = projectMapper.queryTeamByOrderId(orderResult.getOrderId());
                if (teams != null && teams.size() > 0) {
                    // 判断当前用户是否可编辑
                    for (Team team : teams) {
                        if (team.getUsername().equals(operator))    {
                            orderResult.setEditFlag(1);
                            break;
                        }
                    }
                }
            }
        }
        page.setResult(list);
        page.setTotalCount(pageInfo.getTotal());
        return page;
    }

    /**
     * 查询代办任务前处理授权委托
     * @param operator
     */
    private void handSurrogateTask(String operator){
        handSurrogateEmptyOrderId(operator);
        //根据用户查询授权详情(当前时间在授权时间内并且状态是正常的)
        List<SurrogateInfo> surrogateInfoActives = surrogateMapper.querySurrogateByUser(operator,"active");
        for(SurrogateInfo surrogateInfo:surrogateInfoActives){
            //根据代理人和流程实例id查询委托代理记录
            List<TaskActorVo> actors = surrogateMapper.queryTaskActorByTaskIdAndUserName(surrogateInfo.getSurrogate(),surrogateInfo.getOrderId(),"01",null,surrogateInfo.getProcessId(),surrogateInfo.getBatchId());
            if(actors==null||actors.size()==0){//如果不存在代理记录则新增代理记录
                insertTaskActor(surrogateInfo);//新增参与者记录
            }
        }
        //根据用户查询授权详情(当前时间不在授权时间内并且状态是正常的)
        List<SurrogateInfo> surrogateInfoEnds = surrogateMapper.querySurrogateByUser(operator,null);
        for(SurrogateInfo surrogateInfo:surrogateInfoEnds){
            //根据代理人和流程实例id查询委托代理记录
            List<TaskActorVo> actors = surrogateMapper.queryTaskActorByTaskIdAndUserName(surrogateInfo.getSurrogate(),surrogateInfo.getOrderId(),"01",null,surrogateInfo.getProcessId(),surrogateInfo.getBatchId());
            for(TaskActorVo taskActorVo:actors){//如果存在代理记录则删除代理记录
                surrogateMapper.deleteTaskActor(taskActorVo.getId());
            }
            //修改授权任务为结束状态(授权时间已到)
            surrogateMapper.deleteSurrogateEnd(surrogateInfo.getBatchId());
        }
    }

    /**
     * 查询代办任务前处理空流程id的代办任务
     * @param operator
     */
    private void handSurrogateEmptyOrderId(String operator){
        //根据用户查询授权详情(当前时间在授权时间内并且状态是正常的并且order_id为空)
        List<SurrogateInfo> surrogateInfos = surrogateMapper.querySurrogateChangeOrderIdByUser(operator,"active");
        List<SurrogateInfo> surrogateInfoHisList = new ArrayList<>();
        for(SurrogateInfo surrogateInfo:surrogateInfos){
            String batchId = surrogateInfo.getBatchId();
            List<String> orders = surrogateMapper.queryAllSurrogateByProcessId(surrogateInfo.getOperator(),surrogateInfo.getProcessId());
            if(orders!=null&&orders.size()>0){
                surrogateInfoHisList = addDeleteSurrogateInfoHis(surrogateInfoHisList,batchId);
                //修改授权为删除
                surrogateMapper.deleteSurrogate(batchId);
                List<SurrogateInfo> surrogateInfoAdds = new ArrayList<SurrogateInfo>();
                for(String str:orders){
                    SurrogateInfo surrogateInfo1 = new SurrogateInfo();
                    BeanUtils.copyProperties(surrogateInfo, surrogateInfo1);
                    surrogateInfo1 = DateUtil.setSurrogateInfo(surrogateInfo1,batchId,BaseConstant.SURROGATE_OPT_TYPE.ACTIVE_ADD.getCode(),str);
                    surrogateInfoAdds.add(surrogateInfo1);
                    surrogateInfoHisList.add(surrogateInfo1);
                }
                //新增授权
                surrogateMapper.batchInsertSurrogate(surrogateInfoAdds);

            }
        }
        if(surrogateInfoHisList!=null&&surrogateInfoHisList.size()>0){
            surrogateMapper.batchInsertSurrogateHis(surrogateInfoHisList);//新增授权操作历史纪录
        }
    }

    private List<SurrogateInfo> addDeleteSurrogateInfoHis(List<SurrogateInfo> surrogateInfoList,String batchId){
        List<SurrogateInfo> surrogateInfos = surrogateMapper.querySurrogateByBatchId(batchId);
        for(SurrogateInfo surrogateInfo:surrogateInfos){
            SurrogateInfo surrogateInfoHis = new SurrogateInfo();
            BeanUtils.copyProperties(surrogateInfo, surrogateInfoHis);
            surrogateInfoHis.setType(BaseConstant.SURROGATE_OPT_TYPE.ACTIVE_DELETE.getCode());//设置授权操作类型为代办删除
            surrogateInfoHis.setSurrogateId(surrogateInfo.getId());//设置授权id
            surrogateInfoList.add(surrogateInfoHis);
        }
        return surrogateInfoList;
    }

    /**
     * 新增参与者记录
     * @param surrogateInfo
     */
    private void insertTaskActor(SurrogateInfo surrogateInfo){
        //根据用户id和流程id查询任务参与者记录
        List<TaskActorVo> taskActors = surrogateMapper.queryTaskActorByTaskIdAndUserName(surrogateInfo.getOperator(),surrogateInfo.getOrderId(),"00",null,surrogateInfo.getProcessId(),null);
        for(TaskActorVo taskActor:taskActors){//新增参与者记录
            taskActor.setActorId(surrogateInfo.getSurrogate());
            taskActor.setType("01");
            taskActor.setSurrogateId(surrogateInfo.getId());
        }
        if(taskActors!=null&&taskActors.size()>0){
            surrogateMapper.batchInsertTaskActor(taskActors);
        }
    }

    @Override
    public Page<OrderResult> queryHistoryTask(Page<OrderResult> page, String operator, String orderName,String proType) {
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        List<OrderResult> list;
        if(StringUtils.isNotBlank(proType)){
            list = orderMapper.queryHistoryTask(operator, orderName, proType);
        }else{
            list = orderMapper.queryHistoryTask(operator, orderName);
        }
        PageInfo<OrderResult> pageInfo = new PageInfo<>(list);
        for (OrderResult orderResult : list) {
//            orderResult.setPerformType(BaseConstant.PROTYPE.orderResult.getPerformType() + "-" + orderResult.getTaskType());
            orderResult.setPerformType(BaseConstant.PROTYPE.getMessageByCode(orderResult.getPerformType()) + "-" + orderResult.getTaskType());
        }
        page.setResult(list);
        page.setTotalCount(pageInfo.getTotal());
        return page;
    }

    @Override
    public Page<OrderResult> queryOrder(Page<OrderResult> page, String[] user, String orderState, String processId, String orderName, String proType) {
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        List<OrderResult> list = new ArrayList<>();
        // 根据当前登录用户判断显示哪种类型的项目
        if(StringUtils.isNotBlank(user[3])){
            List<String> baseProType = new ArrayList<>(Arrays.asList(user[3].split(",")));
            list = orderMapper.queryOrder(user[0], orderState, processId, orderName, baseProType, proType);
        }
        PageInfo<OrderResult> pageInfo = new PageInfo<>(list);
        for (OrderResult orderResult : list) {
            orderResult.setPerformType(BaseConstant.PROTYPE.getMessageByCode(orderResult.getPerformType()));
            orderResult.setOrderState(BaseConstant.OrderState.get(orderResult.getOrderState()));
        }
        page.setResult(list);
        page.setTotalCount(pageInfo.getTotal());
        return page;
    }

    @Override
    public Page<OrderResult> queryAllPro(Page<OrderResult> page, String[] user, String orderState, String processId, String orderName, String proType) {
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        List<OrderResult> list = new ArrayList<>();
        // 根据当前登录用户判断显示哪种类型的项目
        if(StringUtils.isNotBlank(user[3])){
            List<String> baseProType = new ArrayList<>(Arrays.asList(user[3].split(",")));
            list = orderMapper.queryOrder(null, orderState, processId, orderName, baseProType, proType);
        }
        PageInfo<OrderResult> pageInfo = new PageInfo<>(list);
        for (OrderResult orderResult : list) {
            orderResult.setOrderState(BaseConstant.OrderState.get(orderResult.getOrderState()));
        }
        page.setResult(list);
        page.setTotalCount(pageInfo.getTotal());
        return page;
    }

    @Override
    @Transactional
    public void terminateOrder(String orderId, Map<String, Object> params, String operator, Task task) {
        // 获取当前活动任务
        SnakerEngine engine = ServiceContext.getEngine();
        List<Task> tasks = engine
                .query()
                .getActiveTasks(new QueryFilter().setOrderId(orderId));
        // 2.获取事务定义
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        // 3.设置事务隔离级别，开启新事务
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        // 4.获得事务状态
        TransactionStatus transactionStatus = transactionManager.getTransaction(def);
        try {
            String variable = JsonHelper.toJson(params);
            //当前任务为主办任务
            if (0 == task.getTaskType()) {
                for (Task task1 : tasks) {
                    this.endTask(task1, variable, operator, task.getId(),STATE_TERMINATION);
                }
                this.endOrder(orderId, STATE_TERMINATION);
                sendEmailToTeam(orderId, BaseConstant.EMAIL_TYPE.END_CONTENT.getCode());
            } else {  // 协办任务
                this.endTask(task, variable, operator, task.getId(),STATE_TERMINATION);
            }
            transactionManager.commit(transactionStatus);
        } catch (Exception e) {
            logger.error("执行审批异常！");
            e.printStackTrace();
            transactionManager.rollback(transactionStatus);
            throw e;
        }

    }

    @Override
    public void endOrder(String orderId, int orderState) {
        Order order = access.getOrder(orderId);
        HistoryOrder history = new HistoryOrder(order);
        history.setOrderState(orderState);
        history.setEndTime(DateHelper.getTime());

        access.updateHistory(history);
        access.deleteOrder(order);
        logger.info("The order[{}] has completed", order.getId());
    }

    @Override
    public void endTask(Task task, String variable, String operator, String taskId, Integer taskState) {
        HistoryTask history = new HistoryTask(task);
        history.setFinishTime(DateHelper.getTime());
        history.setTaskState(taskState);
        // history.setOperator(operator);
        if (task.getId().equals(taskId)) {
            history.setVariable(variable);
            history.setOperator(operator);
        }
        if (history.getActorIds() == null) {
            List<TaskActor> actors = access.getTaskActorsByTaskId(task.getId());
            String[] actorIds = new String[actors.size()];
            for (int i = 0; i < actors.size(); i++) {
                actorIds[i] = actors.get(i).getActorId();
            }
            history.setActorIds(actorIds);
        }
        access.saveHistory(history);
        access.deleteTask(task);
        logger.info("The task[{}] has been user[{}] has completed", task.getId(), task.getOperator());
    }

    @Override
    public Page<OrderResult> queryApprovalByCreator(Page<OrderResult> page, String userId, String orderState, String processId, String orderName, String proType) {
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        List<OrderResult> list = orderMapper.queryApproval(Integer.parseInt(userId), orderName, proType);
        PageInfo<OrderResult> pageInfo = new PageInfo<>(list);

        page.setResult(list);
        page.setTotalCount(pageInfo.getTotal());
        return page;
    }

    @Autowired
    private IQueryService iQueryService;
    @Autowired
    private IProcessService iProcessService;
    @Autowired
    private SnakerEngineImpl snakerEngine;
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private ITaskService iTaskService;

    /**
     * 投票机制
     *
     * @param orderId
     * @param userName
     * @param task
     * @return
     */
    @Override
    public Map<String, String> vote(String orderId, String userName, Task task) {
        Map<String, String> resultMap = new HashMap();
        QueryFilter queryFilter = new QueryFilter();
        List<Task> tasks = iQueryService.getActiveTasks(queryFilter.setOrderId(orderId));
        List<HistoryTask> operatorTasks = iQueryService.getHistoryTasks(queryFilter.setOperator(userName).setName(BaseConstant.TASKNAME.MEETAPPROVAL.getCode()));
        // 判断当前节点待办任务是否已完成
        if (null == tasks || tasks.size() == 0) {
            if (StringUtils.isNotBlank(userName)) {
                // 判断领导是否否决
                if (null == operatorTasks || operatorTasks.size() == 0 || 0 == operatorTasks.get(0).getTaskState() || -2 == operatorTasks.get(0).getTaskState()) {
                    //判断通过率是否占半数及以上
                    this.checkPassRate(orderId, userName, task, tasks);
                } else {
                    for (Task task1 : tasks) {
                        this.endTask(task1, null, userName, null, STATE_TERMINATION);
                    }
                    this.endOrder(orderId, STATE_TERMINATION);
                    sendEmailToTeam(orderId, BaseConstant.EMAIL_TYPE.END_CONTENT.getCode());
                }
            } else {
                this.checkPassRate(orderId, userName, task, tasks);
            }

        }
        resultMap.put("code", "200");
        resultMap.put("message", "执行成功");
        return resultMap;
    }

    @Override
    public Page<OrderResult> queryOrderList(Page<OrderResult> page, String[] user, String processId, String orderName, String[] taskNames, String proType) {
        Page<OrderResult> result = queryAllPro(page, user, null, processId, orderName, proType);
        List<OrderResult> orderResultList = result.getResult();
        for (OrderResult orderResult : orderResultList) {
            //默认不可编辑
            orderResult.setEditFlag(0);
            // 移动至待办任务中
            /*List<Task> taskList = iQueryService.getActiveTasks(new QueryFilter().setOrderId(orderResult.getOrderId()).setNames(taskNames));
            // 判断当前待办是否有编辑权限
            if (taskList != null && taskList.size() > 0) {
                String team = projectMapper.queryProTeamByOrderId(orderResult.getOrderId());
                JSONArray teamArr = JSONObject.parseArray(team);
                if (teamArr != null && teamArr.size() > 0) {
                    // 判断当前用户是否可编辑
                    for (int i = 0; i < teamArr.size(); i++) {
                        JSONObject jsonObject = teamArr.getJSONObject(i);
                        String userName = (String) jsonObject.get("userName");
                        if (userName.equals(user[0])) {
                            orderResult.setEditFlag(1);
                            break;
                        }
                    }
                }
            }*/
        }
        return result;
    }

    @Override
    public void terminateActiveTaskAndJump(String orderId, String operator, Task task, String taskName, String variable) {
        // 处理当前活动任务
        List<Task> tasks = iQueryService.getActiveTasks(new QueryFilter().setOrderId(orderId));
        for (Task task1 : tasks) {
            this.endTask(task1, variable, operator, task.getId(), STATE_TERMINATION);
        }
        tasks = this.jump(orderId, task, taskName);
        /*List<HistoryTask> hisApplyTask = iQueryService.getHistoryTasks(new QueryFilter().setOrderId(orderId).setName("apply"));
        for (Task task1 : tasks){
            iTaskService.addTaskActor(task1.getId(), hisApplyTask.get(0).getOperator());
        }*/
        List<Team> teams = projectMapper.queryTeamByOrderId(orderId);
        if (teams != null && teams.size() > 0){
            for (Task task1 :tasks){
                for (Team team : teams){
                    iTaskService.addTaskActor(task1.getId(), team.getUsername());
                }
                if ("apply".equals(task1.getTaskName())) {
                    sendEmailToTeam(orderId, BaseConstant.EMAIL_TYPE.TASK_CONTENT.getCode());
                }
            }
        }
    }

    /**
     * 跳转到指定任务节点
     *
     * @param orderId
     * @param task
     * @param taskName
     * @return
     */
    @Override
    public List<Task> jump(String orderId, Task task, String taskName) {
        Order order = iQueryService.getOrder(orderId);
        Process process = iProcessService.getProcessById(order.getProcessId());
        Execution execution = new Execution(snakerEngine, process, order, new HashMap<>(1));
        execution.setOperator(task.getOperator());
        execution.setTask(task);
        ProcessModel model = execution.getProcess().getModel();
        NodeModel nodeModel = model.getNode(taskName);
        //动态创建转移对象，由转移对象执行execution实例
        TransitionModel tm = new TransitionModel();
        tm.setTarget(nodeModel);
        tm.setEnabled(true);
        tm.execute(execution);
        return execution.getTasks();
    }

    /**
     * 判断通过率是否占半数及以上
     *
     * @param orderId  流程ID
     * @param userName 拥有一票否决权的用户名
     * @param task     当前任务
     * @param tasks    当前流程的活动任务列表
     */
    private void checkPassRate(String orderId, String userName, Task task, List<Task> tasks) {
        QueryFilter queryFilter = new QueryFilter();
        WfTaskResult wfTaskResult = new WfTaskResult();
        wfTaskResult.setOrderid(orderId);
        wfTaskResult.setResulttype(BaseConstant.TASKNAME.MEETAPPROVAL.getCode());
        wfTaskResult.setResultstate("0");
        List<WfTaskResult>  passTasks = taskResultMapper.selectResults(wfTaskResult);
        int passNum = 0;
        if (passTasks != null && passTasks.size() > 0){
            passNum = passTasks.size();
        }
        // 结果为有条件同意的任务集合
        wfTaskResult.setResultstate("-2");
        List<WfTaskResult>  passWithConditionTasks = taskResultMapper.selectResults(wfTaskResult);
        if (passWithConditionTasks != null && passWithConditionTasks.size() > 0){
            passNum = passNum + passWithConditionTasks.size();
        }

        queryFilter = new QueryFilter();
        List<HistoryTask> historyTasks = iQueryService.getHistoryTasks(queryFilter.setOrderId(orderId).setName(BaseConstant.TASKNAME.MEETAPPROVAL.getCode()));
        int num = 0;
        if (0 == historyTasks.size() % 2 && historyTasks != null && historyTasks.size() > 0) {
            num = historyTasks.size() / 2;
        } else if (historyTasks != null && historyTasks.size() > 0) {
            num = historyTasks.size() / 2 + 1;
        }
        // 判断通过率是否占半数及以上
        if (passNum >= num) {
            // 存在有条件同意的任务
//            if (passWithConditionTasks != null && passWithConditionTasks.size() > 0){
//                Order order = iQueryService.getOrder(orderId);
//                Process process = iProcessService.getProcessById(order.getProcessId());
//                Execution execution = new Execution(snakerEngine, process, order, new HashMap<>(1));
//                execution.setOperator(task.getOperator());
//                execution.setTask(task);
//                ProcessModel model = execution.getProcess().getModel();
//                NodeModel nodeModel = model.getNode(task.getTaskName());
//                //将执行对象交给该任务对应的节点模型执行
//                nodeModel.execute(execution);
//                flowUtil.processNextNodeSendEmail(orderId, task.getParentTaskId());
//            }else{
                this.endOrder(orderId, STATE_FINISH);
                sendEmailToTeam(orderId, BaseConstant.EMAIL_TYPE.END_CONTENT.getCode());
//            }
        } else {
            for (Task task1 : tasks) {
                this.endTask(task1, null, userName, null, STATE_TERMINATION);
            }
            this.endOrder(orderId, STATE_TERMINATION);
            sendEmailToTeam(orderId, BaseConstant.EMAIL_TYPE.END_CONTENT.getCode());
        }
    }

    /**
     * 流程结束时通知项目团队
     *
     * @param orderId
     */
    private void sendEmailToTeam(String orderId, String contentType) {
//        ProInfo proInfo = projectMapper.queryProById(orderId);
//        String team = proInfo.getTeam();
//        JSONArray teamArr = JSONObject.parseArray(team);
//        if (teamArr != null && teamArr.size() > 0) {
//            // 判断当前用户是否可编辑
//            StringBuilder sb = new StringBuilder();
//            for (int i = 0; i < teamArr.size(); i++) {
//                JSONObject jsonObject = teamArr.getJSONObject(i);
//                String userName = (String) jsonObject.get("userName");
//                List<User> user = userMapper.getUserByUserName(userName);
//                sb.append(user.get(0).getEmail() + ",");
//            }
//            sendEmailUtil.sendEmail(fromEmail, sb.substring(0, sb.length() - 1), "审批提醒", context);
//        }
        List<String> emails = userMapper.queryTeamEmailByOrderId(orderId);
        ProInfo proInfo = projectMapper.queryProById(orderId);
        if (emails != null && emails.size() > 0){
            String toEmail = emails.toString().substring(1, emails.toString().length() - 1);
            Map<String, Object> params = new HashMap<>();
            params.put("proName", proInfo.getProName());
            params.put("contentType", contentType);
            sendEmailUtil.sendEmail(fromEmail, toEmail, subject, params);
        }
    }
}
