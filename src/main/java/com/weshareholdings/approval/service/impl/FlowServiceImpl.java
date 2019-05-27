package com.weshareholdings.approval.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weshareholdings.approval.config.BaseConstant;
import com.weshareholdings.approval.config.Props;
import com.weshareholdings.approval.po.*;
import com.weshareholdings.approval.repository.*;
import com.weshareholdings.approval.service.EliminatGoldService;
import com.weshareholdings.approval.service.FlowService;
import com.weshareholdings.approval.service.TaskService;
import com.weshareholdings.approval.service.VoteMeetService;
import com.weshareholdings.approval.util.*;
import com.weshareholdings.approval.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snaker.engine.IProcessService;
import org.snaker.engine.IQueryService;
import org.snaker.engine.access.Page;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.core.Execution;
import org.snaker.engine.core.SnakerEngineImpl;
import org.snaker.engine.entity.Order;
import org.snaker.engine.entity.Process;
import org.snaker.engine.entity.Task;
import org.snaker.engine.helper.JsonHelper;
import org.snaker.engine.model.NodeModel;
import org.snaker.engine.model.ProcessModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.snaker.engine.core.AccessService.STATE_FINISH;
import static org.snaker.engine.core.AccessService.STATE_TERMINATION;
import static org.snaker.engine.helper.ReflectHelper.getFieldValue;

@Service
@Slf4j
public class FlowServiceImpl implements FlowService {

    @Autowired
    private SnakerEngineFacets facets;

    @Autowired
    private Props props;

    @Autowired
    private ProjectMapper projectMapper;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FlowUtil flowUtil;
    @Autowired
    private ProInfoCopyMapper proInfoCopyMapper;
    @Autowired
    private IQueryService iQueryService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SeqUtil seqUtil;
    @Autowired
    private WfTaskResultMapper wfTaskResultMapper;
    @Autowired
    private ProAttachTaskMapper attachTaskMapper;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ProInfoDraftMapper proInfoDraftMapper;
    @Autowired
    private VoteMeetService voteMeetService;
    @Autowired
    private EliminatGoldService eliminatGoldService;
    @Autowired
    private IProcessService iProcessService;
    @Autowired
    private SnakerEngineImpl snakerEngine;

    /**
     * 根据流程实例id查询项目详情
     *
     * @param id
     * @return
     */
    private ProInfo queryProByIdPro(String id) {
        //根据流程实例id查询项目详情
        ProInfo proInfo = projectMapper.queryProById(id);
        if (proInfo == null) {
            return null;
        }
        //根据项目id查询项目分层详情
        List<ProLayer> proLayers = projectMapper.queryProLayerByProId(proInfo.getId() + "");
        if (proLayers != null && proLayers.size() > 0) {
            proInfo.setLayerList(proLayers);
        }
        //根据项目id查询项目附件详情
        List<Attach> attaches = projectMapper.queryAttactByProId(proInfo.getId() + "",BaseConstant.PRO_ATTACH_TYPE.PRO_SUPPLEMENT_IMPLEMENT.getCode());
        if (attaches != null && attaches.size() > 0) {
            proInfo.setFileList(attaches);
        }
        //根据项目id查询项目团队详情
        List<Team> teams = projectMapper.queryTeamByProId(proInfo.getId() + "");
        if (teams != null && teams.size() > 0) {
            proInfo.setTeamList(teams);
        }
        //根据项目id查询项目业务模式详情
        List<ProModel> proModelList = projectMapper.queryProModelDetailByProId(proInfo.getId() + "");
        if (proModelList != null && proModelList.size() > 0) {
            proInfo.setProModelList(proModelList);
        }
        return proInfo;
    }

    /**
     * 根据流程实例id查询项目详情
     *
     * @param id 流程实例id
     * @return
     */
    @Override
    public ProInfoVo queryProById(String id) {
        ProInfoVo result = new ProInfoVo();
        ProInfo proInfo = queryProByIdPro(id);
        BeanUtils.copyProperties(proInfo, result);
        if (StringUtils.isNotBlank(result.getBeforeOrderId())) {
            result.setResultList(getApprovalFlowResult(proInfo.getBeforeOrderId()));
            // 评审类型时加改变内容
            ProInfo beforePro = queryProByIdPro(result.getBeforeOrderId());
            JSONObject changeData = compareChangeData(proInfo,beforePro);
            result.setChangeData(changeData);
        }
        return result;
    }

    /**
     * 获取改变内容
     * @param source 当前值
     * @param target 改变之前的值
     * @return
     */
    private JSONObject compareChangeData(ProInfo source, ProInfo target) {
        JSONObject result = new JSONObject();
        if(null == source || null == target) {
            return null;
        }
        //取出source类
        Class<?> sourceClass = source.getClass();

        Field[] sourceFields = sourceClass.getDeclaredFields();
        for(Field srcField : sourceFields) {
            String srcName = srcField.getName();
            if(srcName.equals("id") || srcName.equals("proType") || srcName.equals("team")){
                continue;
            }
            //获取srcField值
            String srcValue = getFieldValue(source, srcName) != null ? getFieldValue(source, srcName).toString() : "";
            //获取对应的targetField值
            String targetValue = getFieldValue(target, srcName) == null ? "" : getFieldValue(target, srcName).toString();
            if(org.springframework.util.StringUtils.isEmpty(srcValue) && org.springframework.util.StringUtils.isEmpty(targetValue)) {
                continue;
            }
            if(!srcValue.equals(targetValue)) {

                if(srcName.equals("layerList")){
                    result.put("layerList", target.getLayerList());
                }else if(srcName.equals("teamList")) {
                    result.put("teamList", target.getTeamList());
                }else{
                    result.put(srcName, targetValue);
                }
            }
        }
        return  result;
    }


    /**
     * 新增项目
     *
     * @param proInfo 项目详情
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 6000, rollbackFor = Exception.class)
    public boolean addProInfo(Map proInfo, String[] user, String processId) {
        try {
            if(!draftSubmit(proInfo)){
                return false;
            }
            String userId = user[1];
            //验证用户是否存在并新增流程
            String orderId = flowUtil.insertOrder(proInfo,processId,user[0]);
            if(orderId == null){
                return false;
            }
            //设置创建人
            proInfo.put("createUserId",userId);
            Map proInfoCopy = new HashMap(proInfo);
            //新增项目
            projectMapper.insertProInfo(proInfo);
            Integer id = (Integer) proInfo.get("id");
            proInfoCopy.put("proId", id);
            //新增项目操作历史
            proInfoCopyMapper.insertProInfoVo(proInfoCopy);
            Integer idCopy = (Integer) proInfoCopy.get("id");
            if(!"01".equals(proInfo.get("proType"))){ //如果项目类型不为消金项目就添加项目分层
                //新增项目分层
                insertProLayers(proInfo,id,idCopy);
            }
            //插入附件
            if(!inertAttach(proInfo,id,processId,idCopy,user)){
                return false;
            }
            //插入项目团队
            insertProTeam(proInfo,id,idCopy);
            //插入业务模式
            insertProModel(proInfo,id,idCopy,userId);
            //插入项目交易结构
            saveProTradeStructure(proInfo,id+"",idCopy+"");
            // 插入操作记录
            flowUtil.saveProOpt("新增", idCopy, id, Integer.valueOf(userId), BaseConstant.PRO_COPY_STATUS.NORMAL.getCode());
            //给下一节点的人发送提醒邮件
            flowUtil.processNextNodeSendEmail(orderId, null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    /**
     * 草稿提交处理
     * @param proInfo 项目详情信息
     * @return
     */
    private boolean draftSubmit(Map proInfo){
        if(proInfo.get("status") != null && BaseConstant.PRO_DRAFT_STATUS.DRAFT.getCode().equals(proInfo.get("status"))){
            // 若为草稿项目提交则修改草稿状态为已提交
            if(null == proInfo.get("id")){
                return false;
            }
            Integer proInfoDraftId = (Integer) proInfo.get("id");
            ProInfoDraft proInfoDraft = new ProInfoDraft();
            proInfoDraft.setId(proInfoDraftId);
            proInfoDraft.setStatus(BaseConstant.PRO_DRAFT_STATUS.SUBMIT.getCode());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            proInfoDraft.setUpdateTime(format.format(new Date()));
            proInfoDraftMapper.updateByPrimaryKeySelective(proInfoDraft);
        }
        return true;
    }


    /**
     * 新增项目分层
     * @param proInfo 项目内容
     * @param id 项目id
     * @param idCopy 项目操作历史id
     */
    private void insertProLayers(Map proInfo,Integer id,Integer idCopy){
        //获取项目分层list
        List<Map> proLayers = (List<Map>) proInfo.get("layerList");
        if (proLayers != null && proLayers.size() > 0) {
            for (int i = 0, len = proLayers.size(); i < len; i++) {
                Map proLayer = proLayers.get(i);
                proLayer.put("proId", id);
                //往项目分层表中插入项目分层记录
                projectMapper.insertProLayer(proLayer);
                proLayer.put("proId", idCopy);
                projectMapper.insertProLayerCopy(proLayer);
            }
        }
    }

    /**
     * 新增项目团队
     * @param proInfo 项目内容
     * @param id 项目id
     * @param idCopy 项目操作历史id
     */
    private void insertProTeam(Map proInfo,Integer id,Integer idCopy){
        //获取项目团队
        List<Map> proTeams = (List<Map>) proInfo.get("teamList");
        if (proTeams != null && proTeams.size() > 0) {
            for(Map team:proTeams){
                team.put("proId", id);
                //往项目团队表中插入项目分层记录
                projectMapper.insertProTeam(team);
                team.put("hisProId", idCopy);
                projectMapper.insertProTeamHis(team);
            }
        }
    }

    /**
     * 新增项目业务模式
     * @param proInfo 项目内容
     * @param id 项目id
     * @param idCopy 项目操作历史id
     */
    private void insertProModel(Map proInfo,Integer id,Integer idCopy,String userId){
        //获取项目团队
        List<Map> proModelList = (List<Map>) proInfo.get("proModelList");
        if (proModelList != null && proModelList.size() > 0) {
            for(Map proModel:proModelList){
                if(proModel.get("modelId")==null||"".equals(proModel.get("modelId"))){
                    proModel.put("userId",userId);
                    projectMapper.insertProModel(proModel);
                    proModel.put("modelId",proModel.get("id"));
                }
                proModel.put("proId", id);
                //往项目分层表中插入项目分层记录
                projectMapper.insertProModelRelation(proModel);
                proModel.put("proHisId", idCopy);
                projectMapper.insertProModelRelationHis(proModel);
            }
        }
    }

    /**
     * 新增附件
     * @param proInfo 项目内容
     * @param id 项目id
     * @param processId 流程实例id
     * @param idCopy 项目操作历史id
     * @return
     */
    private boolean inertAttach(Map proInfo,Integer id,String processId,Integer idCopy,String[] user){
        List<Map> attachMap = putFileList(proInfo,processId);
        // 当前项目为评审或投决时
        if (props.getReviewProcessId().equals(processId)||props.getVetoProcessId().equals(processId)) {
            if (attachMap != null && attachMap.size() > 0) {
                for (Map map:attachMap) {
                    Attach attach = setAttach(id,map,user[0]);
                    // 新增附件，更新
                    if (map.get("proId")!=null&&(Integer) map.get("proId") == 0 && !BaseConstant.PRO_DRAFT_STATUS.DRAFT.getCode().equals(proInfo.get("status"))) {
                        projectMapper.updateAttachByAttachId(attach);
                    } else { // 已有附件，插入
                        projectMapper.insertAttachClass(attach);
                    }
                    // 插入附件任务表
                    this.insertAttachTask(attach);
                }
            }
            // 当前项目为立项或消金
        } else if (props.getProjectProcessId().equals(processId) || props.getGoldProcessId().equals(processId)) {
            if (!updateAttach(attachMap, id, String.valueOf(proInfo.get("status")),user)) {
                return false;
            }
        }
        //新增历史附件
        this.batchInsertAttachCopy(attachMap, idCopy);
        return true;
    }


    /**
     * 设置附件list
     * @param proInfo 项目详情
     * @param processId 流程定义id
     * @return
     */
    private List<Map> putFileList(Map proInfo,String processId){
        // 要插入的最终附件
        List<Map> attachMap = new ArrayList<>();
        List<Map> fileList = (List) proInfo.get("fileList");
        //设置项目立项材料
        attachMap = addList(attachMap,fileList);
        //设置投决附件
        if (props.getVetoProcessId().equals(processId)) {
            Map proInfoTradeStructure = (Map) proInfo.get("proInfoTradeStructure");
            if(proInfoTradeStructure!=null){
                for(String fileName:BaseConstant.FILE_LIST_NAMES){
                    if(proInfoTradeStructure.get(fileName)!=null&&!"".equals(proInfoTradeStructure.get(fileName))){
                        List<Map> list = (List) proInfoTradeStructure.get(fileName);
                        //设置交易结构材料
                        attachMap = addList(attachMap,list);
                    }
                }
            }
        }
        return attachMap;
    }

    /**
     * 把一个非空数组添加到另一个数组中
     * @param attachMap 被添加的数组
     * @param fileList 添加数组
     * @return
     */
    private List<Map> addList(List<Map> attachMap,List<Map> fileList){
        if(fileList != null && fileList.size() > 0){//设置项目立项材料
            attachMap.addAll(fileList);
        }
        return attachMap;
    }

    /**
     * 设置附件插入对象
     * @param id 项目id
     * @param map 附件信息
     * @param userId 用户id
     * @return
     */
    private Attach setAttach(Integer id,Map map,String userId){
        Attach attach = new Attach();
        attach.setProId(id);
        attach.setId(Integer.valueOf(map.get("id") + ""));
        attach.setFilePath((String) map.get("filePath"));
        attach.setFileName((String) map.get("fileName"));
        attach.setRealFileName((String) map.get("realFileName"));
        attach.setUploadPersonId((String)map.get("uploadPersonId"));
        List<User> users = userMapper.getUserByUserName(userId);
        attach.setUploadPersonName(users.get(0).getFullName());
        attach.setProAttachType((String)map.get("proAttachType"));
        return attach;
    }

    /**
     * 新增项目分层
     * @param proInfo 项目内容
     * @param id 项目id
     * @param idCopy 项目操作历史id
     */
    private void insertProLayerDrafts(Map proInfo,Integer id,Integer idCopy){
        //获取项目分层list
        List<Map> proLayers = (List<Map>) proInfo.get("layerList");
        if (proLayers != null && proLayers.size() > 0) {
            for (int i = 0, len = proLayers.size(); i < len; i++) {
                Map proLayer = proLayers.get(i);
                if (proLayer != null ){
                    Object amount = proLayer.get("amount");
                    Object proportion = proLayer.get("proportion");
                    if (amount == null || (amount != null && StringUtils.isBlank(String.valueOf(amount)))){
                        proLayer.put("amount",0);
                    }
                    if (proportion == null || (proportion != null && StringUtils.isBlank(String.valueOf(proportion)))){
                        proLayer.put("proportion",0);
                    }
                }
                proLayer.put("proId", id);
                //往项目分层表中插入项目分层记录
                proInfoDraftMapper.insertLayerDraftSelective(proLayer);
                proLayer.put("proId", idCopy);
                proLayer.put("optType", proInfo.get("optType"));//设置操作类型为0:草稿
                projectMapper.insertProLayerCopy(proLayer);
            }
        }
    }

    /**
     * 新增项目团队
     * @param proInfo 项目内容
     * @param id 项目id
     * @param idCopy 项目操作历史id
     */
    private void insertProModelDrafts(Map proInfo,Integer id,Integer idCopy, String userId){
        List<Map> proModelList = (List<Map>) proInfo.get("proModelList");
        if (proModelList != null && proModelList.size() > 0) {
            for (Map proModel:proModelList) {
                if(proModel.get("modelId")==null||"".equals(proModel.get("modelId"))){
                    proModel.put("userId",userId);
                    projectMapper.insertProModel(proModel);
                    proModel.put("modelId",proModel.get("id"));
                }
                proModel.put("proId", id);
                proInfoDraftMapper.insertProModelDraftSelective(proModel);
                proModel.put("proHisId", idCopy);
                proModel.put("optType", proInfo.get("optType"));//设置操作类型
                projectMapper.insertProModelRelationHis(proModel);
            }
        }

    }

    /**
     * 新增项目团队
     * @param proInfo 项目内容
     * @param id 项目id
     * @param idCopy 项目操作历史id
     */
    private void insertProTeamDrafts(Map proInfo,Integer id,Integer idCopy){
        List<Map> proTeams = (List<Map>) proInfo.get("teamList");
        if (proTeams != null && proTeams.size() > 0) {
            for (Map team:proTeams) {
                team.put("proId", idCopy);
                team.put("draftProId", id);
                proInfoDraftMapper.insertTeamDraftSelective(team);
                team.put("hisProId", idCopy);
                team.put("optType", proInfo.get("optType"));//设置操作类型
                projectMapper.insertProTeamHis(team);
            }
        }
    }

    /**
     * 新增附件
     * @param proInfo 项目内容
     * @param id 项目id
     * @param idCopy 项目操作历史id
     * @return
     */
    private boolean inertAttachDrafts(Map proInfo,Integer id,Integer idCopy,String processId){
        List<Map> attachMap = putFileList(proInfo,processId);
        if (attachMap != null && attachMap.size() > 0) {
            for (Map map:attachMap) {
                if (map.get("proId") != null && (Integer) map.get("proId") == 0) {
                    projectMapper.deleteAttachByPrimaryKey(String.valueOf(map.get("id")));
                }
                map.put("id", null);
                map.put("proId", id);
                map.put("optType", proInfo.get("optType"));//设置操作类型为0:草稿
                proInfoDraftMapper.insertAttachDraftSelective(map);
            }
        }
        //新增历史附件
        this.batchInsertAttachCopy(attachMap, idCopy);
        return true;
    }



    @Override
    public Attach uploadAttach(MultipartFile file,String[] user,String fileType) {
        String fileName = file.getOriginalFilename();
        String realFileName = fileName.substring(0, fileName.lastIndexOf("."));
        String fileSuffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());

        realFileName = realFileName + "_" + seqUtil.getSeqValue(SeqUtil.FILE);
        Attach attach = new Attach();
        realFileName = realFileName + fileSuffix;
        try {
            FileUtil.byte2File(file.getBytes(), props.getUploadFilePathProp(), realFileName);
            attach.setFilePath(props.getUploadFilePathProp());
            attach.setFileName(fileName);
            attach.setRealFileName(realFileName);
            attach.setProAttachType(fileType);
            attach.setStatus(BaseConstant.ATTACH_STATUS.NORMAL.getCode());
            List<User> users = userMapper.getUserByUserName(user[0]);
            attach.setUploadPersonName(users.get(0).getFullName());
            attach.setUploadPersonId(user[1]);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            attach.setCreateTime(df.format(new Date()));
            attach.setUpdateTime(df.format(new Date()));
            projectMapper.insertAttachClass(attach);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return null;
        }
        return attach;
    }

    private void insertAttachTask(Attach attach) {
        ProAttachTask proAttachTask = new ProAttachTask();
        proAttachTask.setAttachId(attach.getId());
        proAttachTask.setOptType(Integer.valueOf(BaseConstant.ATTACH_OPT_TYPE.ADD.getCode()));
        proAttachTask.setProId(attach.getProId());
        proAttachTask.setStatus(BaseConstant.ATTACH_TASK_STATUS.APPROVAL_PASS.getCode());
        proAttachTask.setUserId(Integer.parseInt(attach.getUploadPersonId()));
        proAttachTask.setProAttachType(attach.getProAttachType());
        attachTaskMapper.insertSelective(proAttachTask);
    }


    /**
     * 更新附件
     *
     * @param attaches 附件列表
     * @param proId    项目id
     * @param status 项目草稿状态
     * @return
     */
    private boolean updateAttach(List attaches, int proId, String status, String[] user) {
        if (attaches != null && attaches.size() > 0) {
            for (int i = 0, len = attaches.size(); i < len; i++) {
                Attach attach = new Attach();
                Map map = (Map) attaches.get(i);
                try {
                    attach.setProId(proId);
                    attach.setUploadPersonId((String)map.get("uploadPersonId"));
                    if (map.get("id") != null && !"".equals(map.get("id")) && !BaseConstant.PRO_DRAFT_STATUS.DRAFT.getCode().equals(status)) {
                        attach.setId(Integer.valueOf(map.get("id") + ""));
                        attach.setUploadPersonId((String) map.get("uploadPersonId"));
                        projectMapper.updateAttachByAttachId(attach);
                    }else{
                        attach.setFilePath((String) map.get("filePath"));
                        attach.setFileName((String) map.get("fileName"));
                        attach.setRealFileName((String) map.get("realFileName"));
//                        attach.setUploadPersonName(user[0]);
                        List<User> users = userMapper.getUserByUserName(user[0]);
                        attach.setUploadPersonName(users.get(0).getFullName());
                        attach.setProAttachType((String)map.get("proAttachType"));
                        projectMapper.insertAttachClass(attach);
                    }
                    // 插入附件任务
                    this.insertAttachTask(attach);
                } catch (Exception e) {
                    logger.info("附件更新失败。。。");
                    logger.info(e.getMessage());
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 新增历史附件
     *
     * @param attaches  附件列表
     * @param proCopyId 历史项目id
     * @return
     */
    private boolean batchInsertAttachCopy(List<Map> attaches, int proCopyId) {
        if (attaches != null && attaches.size() > 0) {
            for (Map map:attaches) {
                if (map.get("id") != null && !"".equals(map.get("id"))) {
                    map.put("proId", proCopyId);
                }
                if(map.get("optType") == null || "".equals(map.get("optType"))){
                    map.put("optType","1");
                }
            }
            projectMapper.batchInsertAttachCopy(attaches);
        }
        return true;
    }

    @Override
    public List<String> getOrderFlowById(String orderId) {
        return projectMapper.queryOrderFlowById(orderId);
    }

    /**
     * 根据附件id查询附件信息
     *
     * @param fileId 文件id
     * @return
     */
    @Override
    public Attach queryPathByFileId(String fileId, String status) {
        Attach attach ;
        if("0".equals(status)){
            attach = proInfoDraftMapper.queryAttachByFileId(fileId);
        }else {
            attach = projectMapper.queryPathByFileId(fileId);
        }
        return attach;
    }

    ;

    /**
     * 根据用户账号查询用户信息
     *
     * @param userName 用户账号
     * @return
     */
    @Override
    public User queryUserByUserName(String userName) {
        return projectMapper.queryUserByUserName(userName);
    }



    @Override
    public ProInfoVo queryApprovalDetail(String orderId) {
        ProInfoVo result = new ProInfoVo();
        ProInfo proInfo = queryProByIdPro(orderId);
        BeanUtils.copyProperties(proInfo, result);
        result.setResultList(getApprovalFlowResult(orderId));
        return result;
    }


    @Override
    public List<ApprovalFlowResult> getApprovalFlowResult(String orderId) {
        List<WfTaskResult> wfTaskResults = wfTaskResultMapper.selectByOrderId(orderId);
        List<ApprovalFlowResult> flowList = new ArrayList<>();
        if (wfTaskResults != null && wfTaskResults.size() > 0) {
            for (WfTaskResult task : wfTaskResults) {
                ApprovalFlowResult flowResult = new ApprovalFlowResult();
                flowResult.setResult(task.getResult());
                flowResult.setApprovalNode(task.getDisplayName());
                flowResult.setResultState(BaseConstant.ApprovalState.get(task.getResultstate()));
                flowResult.setResultType(BaseConstant.TASKNAME.getMessageByCode(task.getResulttype()));
                flowResult.setOperator(task.getOperator());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                flowResult.setDate(formatter.format(task.getCreatetime()));
                flowList.add(flowResult);
            }
        }
        return flowList;
    }

    @Transactional
    @Override
    public Map<String, String> submitModify(Map<String, Object> proInfo, String[] user,String processId) {
        logger.info("项目id:" + proInfo.get("id") + "提交修改开始。提交内容：" + JSON.toJSONString(proInfo));
        Map<String, String> result = new HashMap<>(2);
        // 1.更新项目数据
        //查询修改前项目内容
        //测试开始
        Map map = getBeforeProAndModifyContent(proInfo);
        int beforeProId = Integer.valueOf(String.valueOf(map.get("beforeProId")));
        Map content = (Map) map.get("content");
        //测试结束
        //对比修改内容
        if (content == null || content.size() == 0) {
            logger.info("未检测到修改内容，直接返回成功。");
            result.put("code", ResultUtil.SUCCESS_CODE);
            result.put("message", "执行成功");
            return result;
        }
        //1.1 更新修改后内容进项目表
        projectMapper.updateProInfo(proInfo);
        // 2.将修改后内容插入备份表(项目表、分层表、附件表)
        proInfo.put("proId", proInfo.get("id"));
        proInfo.put("id", null);
        proInfoCopyMapper.insertProInfoVo(proInfo);
        int proCopyId = (Integer) proInfo.get("id");
        //更新项目分层
        modifyProLayer(beforeProId,proInfo,proCopyId,content.get("layerList") != null);
        //更新附件
        modifyAttach(beforeProId,proInfo,proCopyId,processId);
        //更新项目团队
        modifyProTeam(beforeProId,proInfo,proCopyId,content.get("teamList") != null);
        //保存交易结构
        saveProTradeStructure(proInfo,proInfo.get("id")+"",proCopyId+"");
        //更新业务模式
        modifyProModel(beforeProId,proInfo,proCopyId,user[1], content.get("proModelList") != null);
        // 将修改内容记录进操作表
        flowUtil.saveProOpt(JSON.toJSONString(content), proCopyId, beforeProId, Integer.parseInt(user[1]), BaseConstant.PRO_COPY_STATUS.NORMAL.getCode());

        result.put("code", ResultUtil.SUCCESS_CODE);
        result.put("message", "执行成功");
        return result;
    }

    private Map getBeforeProAndModifyContent(Map<String, Object> proInfo) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> content = null;
        Object beforeProInfo = new Object();
        if (BaseConstant.PROTYPE.ABS_PROJECT.getCode().equals(proInfo.get("proType")) || BaseConstant.PROTYPE.ABS_REVIEW.getCode().equals(proInfo.get("proType"))){
             beforeProInfo = this.queryProById(String.valueOf(proInfo.get("orderId")));
             content = this.compareProInfoTest(beforeProInfo, proInfo, new ProInfo());
        }else if (BaseConstant.PROTYPE.CAST_PROJECT.getCode().equals(proInfo.get("proType"))){
             beforeProInfo = voteMeetService.queryProById(String.valueOf(proInfo.get("orderId")));
            content = this.compareProInfoTest(beforeProInfo, proInfo, new VoteProInfoVo());
        }else if (BaseConstant.PROTYPE.CONSUMER_PROJECT.getCode().equals(proInfo.get("proType"))){
             beforeProInfo = eliminatGoldService.queryProByOrderId(String.valueOf(proInfo.get("orderId")));
            content = this.compareProInfoTest(beforeProInfo, proInfo, new GoldProInfo());
        }
        result.put("content", content);
        result.put("beforeProId", ReflectUtils.getFieldValue(beforeProInfo, "id"));
        return result;
    }
    private Map<String, Object> compareProInfoTest(Object beforeProInfoVo, Map<String,Object> proInfo, Object afterProInfo) {
        Map proInfoTradeStructure=null;
        if ( proInfo.get("proInfoTradeStructure") != null){
            proInfoTradeStructure =(Map) proInfo.get("proInfoTradeStructure");
            ProInfoTradeStructure proInfoTradeStructureAfter = new ProInfoTradeStructure();
            proInfoTradeStructureAfter = (ProInfoTradeStructure)ReflectUtils.transMap2Bean2(proInfoTradeStructure, proInfoTradeStructureAfter);
            proInfo.put("proInfoTradeStructure",proInfoTradeStructureAfter);
        }
        afterProInfo = ReflectUtils.transMap2Bean2(proInfo, afterProInfo);
        Map<String, Object> modifyContent = ReflectUtils.packageModifyContent(afterProInfo, beforeProInfoVo);
        proInfo.put("proInfoTradeStructure",proInfoTradeStructure);
        log.info("检测到修改内容：" + JSON.toJSONString(modifyContent));
        return modifyContent;
    }

    /**
     * 保存交易结构
     * @param proInfo
     */
    private void saveProTradeStructure(Map<String, Object> proInfo,String id,String proCopyId){
        if(BaseConstant.PROTYPE.CAST_PROJECT.getCode().equals(proInfo.get("proType"))){ //如果项目类型为投决项目就添加项目交易结构
            //获取项目交易结构
            Map proInfoTradeStructure = (Map) proInfo.get("proInfoTradeStructure");
            if (proInfoTradeStructure != null){
                proInfoTradeStructure.put("proId", id);
                if(proInfoTradeStructure!=null&&proInfoTradeStructure.get("id")!=null&&!BaseConstant.PRO_DRAFT_STATUS.DRAFT.getCode().equals(proInfo.get("status"))){
                    //根据id修改交易记录
                    projectMapper.updateProTradeStructure(proInfoTradeStructure);
                }else{
                    //项目交易结构表中插入记录
                    projectMapper.insertProInfoTradeStructure(proInfoTradeStructure);
                }
                proInfoTradeStructure.put("proHisId",proCopyId);
                projectMapper.insertProInfoTradeStructureCopy(proInfoTradeStructure);
            }
        }


    }


    /**
     * 更新项目分层
     * @param beforeProId 更新前的项目
     * @param proInfo 待更新的项目
     * @param proCopyId 项目操作记录表id
     */
    private void modifyProLayer(int beforeProId,Map<String, Object> proInfo,int proCopyId, boolean isModify){
        //获取项目分层list
        List<Map> proLayers = (List<Map>) proInfo.get("layerList");
        if (isModify){
            // 1.2 更新修改前项目分层（// 先删除再插入）
            List<ProLayer> proLayersBefore = projectMapper.queryProLayerByProId(beforeProId + "");
            if (proLayersBefore != null && proLayersBefore.size() > 0) {
                for (ProLayer proLayer : proLayersBefore) {
                    projectMapper.deleteProLayerByPrimaryKey(proLayer.getId());
                }
            }
            if (proLayers != null && proLayers.size() > 0) {
                for (int i = 0, len = proLayers.size(); i < len; i++) {
                    Map proLayer = proLayers.get(i);
                    proLayer.put("proId", beforeProId);
                    //往项目分层表中插入项目分层记录
                    projectMapper.insertProLayer(proLayer);
                }
            }
        }
        if (proLayers != null && proLayers.size() > 0) {
            for (int i = 0, len = proLayers.size(); i < len; i++) {
                Map proLayer = proLayers.get(i);
                proLayer.put("proId", proCopyId);
                //往项目分层表中插入项目分层记录
                projectMapper.insertProLayerCopy(proLayer);
            }
        }
    }

    /**
     * 更新项目附件
     * @param beforeProId 更新前的项目
     * @param proInfo 待更新的项目
     * @param proCopyId 项目操作记录表id
     */
    private void modifyAttach(int beforeProId,Map<String, Object> proInfo,int proCopyId,String processId){
        log.info("进入附件更新，客户端提交附件：" + proInfo.get("fileList"));
        List<Attach> attachs = projectMapper.queryAttactByProId(String.valueOf(beforeProId),null);
        Set<Attach> oldAttachs = new HashSet<>(attachs);
        List<Map> attachMap =putFileList(proInfo,processId);

        if(attachMap != null && attachMap.size()>0){
            List<Attach> list = JSONArray.parseArray(JSON.toJSONString(attachMap), Attach.class);
            Set<Attach> newAttachs = new HashSet<>(list);
            Set<Attach> repeatAttachs = new HashSet<>();
            repeatAttachs.addAll(oldAttachs);
            repeatAttachs.retainAll(newAttachs);
            log.info("无效数据：" + JSON.toJSONString(repeatAttachs));

            oldAttachs.removeAll(repeatAttachs);
            log.info("删除数据：" + oldAttachs);

            newAttachs.removeAll(repeatAttachs);
            log.info("新增数据：" + newAttachs);

            this.handleModifyAttach(list, oldAttachs, newAttachs, beforeProId, proCopyId);
        }else{
            // 提交附件为空，删除全部附件
            log.info("提交附件为空，删除全部附件：" + JSON.toJSONString(attachs));
            for(Attach attach : attachs){
                attach.setStatus(BaseConstant.ATTACH_STATUS.DELETE.getCode());
                projectMapper.updateAttachByAttachId(attach);
            }
        }
    }

    /**
     * 处理修改的附件
     * @param attachs 原有附件
     * @param oldAttachs 删除附件
     * @param newAttachs 新增附件
     * @param id 原有项目ID
     * @param proCopyId 备份项目ID
     */
    private void handleModifyAttach(List<Attach> attachs, Set<Attach> oldAttachs, Set<Attach> newAttachs, int id, int proCopyId) {
        for(Attach attach : oldAttachs){
            attach.setStatus(BaseConstant.ATTACH_STATUS.DELETE.getCode());
            projectMapper.updateAttachByAttachId(attach);
        }
        for (Attach attach : newAttachs){
            attach.setProId(id);
            attach.setStatus(BaseConstant.ATTACH_STATUS.NORMAL.getCode());
            projectMapper.updateAttachByAttachId(attach);
            this.insertAttachTask(attach);
        }
        for (Attach attach : attachs){
            attach.setProId(proCopyId);
            attach.setOptType(BaseConstant.PRO_COPY_STATUS.DRAFT.getCode());
            projectMapper.insertAttachCopyClass(attach);
        }
    }

    /**
     * 更新项目团队
     * @param beforeProId 更新前的项目
     * @param proInfo 待更新的项目
     * @param proCopyId 项目操作记录表id
     */
    private void modifyProTeam(int beforeProId,Map<String, Object> proInfo,int proCopyId, boolean isModify){
        if (isModify){
            // 1.2 更新修改前项目团队（// 先删除再插入）
            List<Team> teamList = projectMapper.queryTeamByProId(beforeProId + "");
            if (teamList != null && teamList.size() > 0) {
                for (Team team : teamList) {
                    projectMapper.deleteProTeamByPrimaryKey(Integer.parseInt(team.getId()));
                }
            }
            insertProTeam(proInfo,beforeProId,proCopyId);
        }else {
            List<Map> proTeams = (List<Map>) proInfo.get("teamList");
            if (proTeams != null && proTeams.size() > 0) {
                for(Map team:proTeams){
                    team.put("proId",beforeProId);
                    team.put("hisProId", proCopyId);
                    projectMapper.insertProTeamHis(team);
                }
            }
        }

    }

    /**
     * 更新业务模式
     * @param beforeProId 更新前的项目
     * @param proInfo 待更新的项目
     * @param proCopyId 项目操作记录表id
     */
    private void modifyProModel(int beforeProId,Map<String, Object> proInfo,int proCopyId,String userId, boolean isModify){
        if (isModify){
            // 1.2 更新修改前项目业务模式关系（// 先删除再插入）
            List<String> proModelIds = projectMapper.queryProModelByProId(beforeProId + "");
            if (proModelIds != null && proModelIds.size() > 0) {
                for (String proModelId : proModelIds) {
                    projectMapper.deleteProModelByPrimaryKey(Integer.parseInt(proModelId));
                }
            }
            insertProModel(proInfo,beforeProId,proCopyId,userId);
        }else {
            List<Map> proModelList = (List<Map>) proInfo.get("proModelList");
            if (proModelList != null && proModelList.size() > 0) {
                for(Map proModel:proModelList){
                    proModel.put("proId",beforeProId);
                    proModel.put("proHisId", proCopyId);
                    projectMapper.insertProModelRelationHis(proModel);
                }
            }
        }
    }



    /**
     *  秘书选择流程节点处理
     * @param task
     * @param orderId
     * @param resultState
     * @param nodes
     */
    private boolean handEliminat(String processId,Task task ,String orderId,String resultState,String[] nodes){
        if(BaseConstant.TASKNAME.SECRECHOOSE.getMessage().equals(task.getDisplayName())&&"0".equals(resultState)){
            if(nodes!=null){
                if(nodes!=null&&nodes.length>0){
                    for(String node:nodes){
                        List<Task> tasks = taskMapper.selectTaskByOrderIdAndDisplayName(orderId,node);
                        taskMapper.closeDeleteForeignMatch();
                        for(Task taskObj:tasks){
                            taskObj.setTaskType(0);
                            excuteTask(processId,orderId,taskObj.getId(),getApprovalUser(node),resultState,taskObj,false,null);
                            taskMapper.deleteTaskHisById(taskObj.getId());
                            taskMapper.deleteTaskActorHisByTaskId(taskObj.getId());
                        }
                        taskMapper.openDeleteForeignMatch();
                    }
                }

            }
        }
        return true;
    }

    private String getApprovalUser(String node){
        String operator = null;
        if (BaseConstant.TASKNAME.COMPLIAPPROVAL.getCode().equals(node)){
            operator = props.getCompliApprovalUser();
        }else if(BaseConstant.TASKNAME.RISKAPPROVAL.getCode().equals(node)){
            operator = props.getRiskApprovalUser();
        }else if(BaseConstant.TASKNAME.TECHAPPROVAL.getCode().equals(node)){
            operator = props.getTechApprovalUser();
        }
        return operator;
    }

    @Override
    public Result process(String acceptanceConclusion,String processId, String orderId, String taskId, String operator, String resultState, String result, String nextOperator, String ccOperator, String[] nodes, String assignee){
        List<User> users = userMapper.getUserByUserName(operator);
        if (users == null || users.size() == 0) {
            return ResultUtil.error("用户名：" + operator + "的用户信息不存在");
        }
        String resultTye = saveTaskResult(orderId, taskId, resultState, result, users.get(0).getFullName());
        if ("0".equals(resultTye)) {
            return ResultUtil.error("该任务已处理，请勿重复提交");
        }
        Task task = facets.getTaskById(taskId);
        excuteTask(processId,orderId,taskId,operator,resultState,task,true,assignee);
        if (StringUtils.isNotEmpty(ccOperator)) {
            facets.getEngine().order().createCCOrder(orderId, ccOperator.split(","));
        }
        //如果是数据申请就根据流程id添加流程审批参与者
        flowUtil.addTaskActor(orderId);
        flowUtil.dataApplyUpdateProInfo(taskId,acceptanceConclusion);
        return ResultUtil.success("执行成功");
    }


    /**
     *  执行任务
     * @param processId 流程实例id
     * @param orderId 流程实例id
     * @param taskId 任务id
     * @param operator 操作人
     * @param resultState 审批结果
     * @param task
     * @param assignee 节点审批人
     */
    private void excuteTask(String processId,String orderId,String taskId,String operator,String resultState,Task task,boolean isSendEmail, String assignee){
        Map<String, Object> params = new HashMap(16);
        this.setAssignee(task, params, assignee);
        if (StringUtils.isEmpty(orderId) && StringUtils.isEmpty(taskId)) {
            facets.startAndExecute(processId, operator, params);
        } else {
            int method;
            try {
                method = Integer.parseInt(resultState);
            } catch (Exception e) {
                method = 0;
            }
            // 秘书落实跟进事项节点需进行投票判断，需特殊处理
//            if (BaseConstant.TASKNAME.MEETAPPROVAL.getCode().equals(task.getTaskName())){
//                handleSecreConfirm(orderId, task, resultState);
//            }
            switch (method) {
                //任务执行
                case 0:
                    // 执行任务前查询参与者记录信息
                    List<TaskActorVo> taskActorVos = taskMapper.queryTaskActorByTaskId(taskId);
                    facets.execute(taskId, operator, params);
                    // 任务执行完之后，处理代办任务历史
                    handSurrogateTask(taskActorVos);
                    if(!BaseConstant.TASKNAME.MEETAPPROVAL.getCode().equals(task.getTaskName())&&isSendEmail){
                        flowUtil.processNextNodeSendEmail(orderId, task.getParentTaskId());
                    }
                    break;
                //驳回、任意跳转
                case -1:
                    //判断当前任务是否是可驳回节点，是则驳回时回到申请人,否则结束任务
                    if (DateUtil.getRebackProcess(BaseConstant.REBACK_PROCESS,task.getTaskName())) {
                        taskService.terminateActiveTaskAndJump(orderId, operator, task, BaseConstant.TASKNAME.APPLY.getCode(), JsonHelper.toJson(params));
                    } else {
                        taskService.terminateOrder(orderId, params, operator, task);
                    }
                    break;
                default:
                    facets.execute(taskId, operator, params);
                    break;
            }
            // 委员决策节点任务类型为协办，需特殊处理
            if (1 == task.getTaskType() && BaseConstant.TASKNAME.MEETAPPROVAL.getCode().equals(task.getTaskName())){
//                handleMeetApproval(orderId, task);
                taskService.vote(orderId, props.getVetoUser(), task);
                //执行任务前查询参与者记录信息
                List<TaskActorVo> taskActorVos = taskMapper.queryTaskActorByTaskId(task.getId());
                //任务执行完之后，处理代办任务历史
                handSurrogateTask(taskActorVos);
            }
        }
    }

    private void handleSecreConfirm(String orderId, Task task, String resultState) {
        log.info("秘书落实跟进事项意见提交，进入投票判断，一票否决权拥有者：" + props.getVetoUser());
//        taskService.endTask(task, null, props.getVetoUser(), null, resultState.equals("0")? STATE_FINISH : STATE_TERMINATION);
        taskService.vote(orderId, props.getVetoUser(), task);
        //执行任务前查询参与者记录信息
        List<TaskActorVo> taskActorVos = taskMapper.queryTaskActorByTaskId(task.getId());
        //任务执行完之后，处理代办任务历史
        handSurrogateTask(taskActorVos);
    }

    private void handleMeetApproval(String orderId, Task task) {
        QueryFilter queryFilter = new QueryFilter();
        List<Task> tasks = iQueryService.getActiveTasks(queryFilter.setOrderId(orderId));
        if (tasks == null || tasks.size() == 0){
            Order order = iQueryService.getOrder(orderId);
            Process process = iProcessService.getProcessById(order.getProcessId());
            Execution execution = new Execution(snakerEngine, process, order, new HashMap<>(1));
            execution.setOperator(task.getOperator());
            execution.setTask(task);
            ProcessModel model = execution.getProcess().getModel();
            NodeModel nodeModel = model.getNode(task.getTaskName());
            //将执行对象交给该任务对应的节点模型执行
            nodeModel.execute(execution);
            flowUtil.processNextNodeSendEmail(orderId, task.getParentTaskId());
        }
        //执行任务前查询参与者记录信息
        List<TaskActorVo> taskActorVos = taskMapper.queryTaskActorByTaskId(task.getId());
        //任务执行完之后，处理代办任务历史
        handSurrogateTask(taskActorVos);
    }

    /**
     * 设置审批人
     * @param task
     * @param params
     * @param assignee
     */
    private void setAssignee(Task task, Map<String,Object> params, String assignee) {
        if (StringUtils.isNotBlank(assignee)){
            if(BaseConstant.TASKNAME.SECRECHOOSE.getCode().equals(task.getTaskName())){
                params.put(BaseConstant.TASKNAME.FRONTAPPROVAL.getCode(), assignee);
            }else if (BaseConstant.TASKNAME.SECREAPROVAL.getCode().equals(task.getTaskName())){
                params.put(BaseConstant.TASKNAME.MEETAPPROVAL.getCode(), assignee);
            }
            log.info("taskId：" + task.getId() + "选择的审批人员：" + assignee);
        }
    }

    private void handSurrogateTask(List<TaskActorVo> taskActorVos){
        for(TaskActorVo taskActorVo:taskActorVos){
            String taskActorHisId = taskMapper.queryTaskActorHisByTaskId(taskActorVo.getTaskId(),taskActorVo.getActorId());
            if(StringUtils.isNotEmpty(taskActorHisId)&&StringUtils.isNotEmpty(taskActorVo.getSurrogateId())){
                taskMapper.updateTaskActorHisById(taskActorHisId,taskActorVo.getType(),taskActorVo.getSurrogateId());
            }
        }
    }

    private String saveTaskResult(String orderId, String taskId, String resultState, String result, String fullName) {
        WfTaskResult taskResult = new WfTaskResult();
        taskResult.setOperator(fullName);
        taskResult.setOrderid(orderId);
        taskResult.setTaskid(taskId);
        taskResult.setResult(result);
        taskResult.setResultstate(resultState);
        Task task = iQueryService.getTask(taskId);
        if (null == task) {
            return "0";
        }
        taskResult.setResulttype(task.getTaskName());
        wfTaskResultMapper.insertSelective(taskResult);
        return "1";
    }

    /**
     * 保存项目草稿
     *
     * @param proInfo 项目详情
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 6000, rollbackFor = Exception.class)
    public Result saveProInfoDraft(Map<String, Object> proInfo, String[] user ,String processId) {
        try {
            proInfo.put("optType","0");//设置操作类型为0:草稿
            log.info("开始保存草稿，草稿内容：" + JSON.toJSONString(proInfo));
            //根据用户名查询用户信息
            List<User> users = userMapper.getUserByUserName(user[0]);
            if (users == null || users.size() == 0) {
                logger.error("用户名：" + user[0] + "的用户信息不存在");
                return ResultUtil.error("用户信息不存在");
            }
            //根据传入的id判断更新或新增项目
            Object id =  proInfo.get("id");
            proInfo.put("createUserId", user[1]);
            int proCopyId = 0;
            ProInfoDraft beforeProInfoDraft = null;
            ProInfoVo beforeProInfo = new ProInfoVo();
            if(id != null &&proInfo.get("status") != null && BaseConstant.PRO_DRAFT_STATUS.DRAFT.getCode().equals(proInfo.get("status"))){
                // 提交的是草稿内容
                beforeProInfoDraft = this.getProDraftDetailById(String.valueOf(id));
                BeanUtils.copyProperties(beforeProInfoDraft,beforeProInfo);

                Map map = this.compareProInfoTest(beforeProInfo, proInfo, new ProInfoDraft());
                if (map == null || map.size() == 0) {
                    logger.info("未检测到修改内容，直接返回成功。");
                    return ResultUtil.success("执行成功");
                }
                if(beforeProInfoDraft != null){
                    // 提交的是草稿表内容，pro_id取传入值
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    proInfo.put("updateTime", df.format(new Date()));
                    proInfoDraftMapper.updateProInfoDraftSelective(proInfo);
                    proInfo.put("proId", proInfo.get("id"));
                }else{
                    // 提交的是正式表内容
                    proInfo.put("proId", proInfo.get("id"));
                    proInfoDraftMapper.insertProInfoDraftSelective(proInfo);
                    id = proInfo.get("id");
                    proInfo.put("proId", id);
                }
                // 插入项目备份表
                proInfoCopyMapper.insertProInfoVo(proInfo);
                proCopyId = (Integer) proInfo.get("id");
                // 将修改内容记录进操作表
                flowUtil.saveProOpt(JSON.toJSONString(map), proCopyId, beforeProInfo.getId(),Integer.parseInt(user[1]),BaseConstant.PRO_COPY_STATUS.DRAFT.getCode());
            }else{
                proInfoDraftMapper.insertProInfoDraftSelective(proInfo);
                id = proInfo.get("id");
                proInfo.put("proId", id);
                proInfoCopyMapper.insertProInfoVo(proInfo);
                proCopyId = (Integer) proInfo.get("id");
                flowUtil.saveProOpt("新增", proCopyId, (Integer) id, Integer.parseInt(user[1]),BaseConstant.PRO_COPY_STATUS.DRAFT.getCode());
            }
            // 1.2更新项目分层 先删除再插入
            proInfoDraftMapper.deleteLayerDraftByProId((Integer)id);
            this.insertProLayerDrafts(proInfo, (Integer) id, proCopyId);

            //1.3 更新附件 先删除再插入
            proInfoDraftMapper.deleteAttachDraftByProId((Integer) id);
            this.inertAttachDrafts(proInfo, (Integer) id, proCopyId,processId);

            //1.4 更新项目团队 先删除再插入
            proInfoDraftMapper.deleteTeamDraftByProId((Integer) id);
            this.insertProTeamDrafts(proInfo, (Integer) id, proCopyId);

            //1.4 更新项目业务模式关系 先删除再插入
            proInfoDraftMapper.deleteProModelDraftByProId((Integer) id);
            this.insertProModelDrafts(proInfo, (Integer) id, proCopyId, user[1]);

            //保存交易结构草稿
            saveProTradeStructureDraft(proInfo,id+"",proCopyId+"");
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultUtil.error("执行失败");
        }
        return ResultUtil.success("执行成功");
    }

    /**
     * 保存交易结构草稿
     * @param proInfo
     */
    private void saveProTradeStructureDraft(Map<String, Object> proInfo,String id,String proCopyId){
        Map<String, Object> proInfoTradeStructure = null;
        if(proInfo.get("proInfoTradeStructure")!=null){
            proInfoTradeStructure =(Map<String, Object>)proInfo.get("proInfoTradeStructure");
            proInfoTradeStructure.put("proId",id);
        }
       if(proInfoTradeStructure!=null){
            if(proInfoTradeStructure.get("id")!=null){
                proInfoDraftMapper.updateProTradeStructureDraft(proInfoTradeStructure);
            }else {
                proInfoDraftMapper.insertProTradeStructureDraft(proInfoTradeStructure);
            }
           proInfoTradeStructure.put("optType",proInfo.get("optType"));
           proInfoTradeStructure.put("proHisId",proCopyId);
           projectMapper.insertProInfoTradeStructureCopy(proInfoTradeStructure);
       }
    }


    @Override
    public Page<ProDraftVo> getProDrafts(Page<ProDraftVo> page, String proName, String proType, String[] users) {
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        List<ProDraftVo> proDraftVoList;
        if(StringUtils.isNotBlank(proType)){
            proDraftVoList = proInfoDraftMapper.selectProDraft(users[1], proName, proType);
        }else{
            proDraftVoList = proInfoDraftMapper.selectProDraft(users[1], proName);
        }
        PageInfo<ProDraftVo> pageInfo = new PageInfo<>(proDraftVoList);
        page.setResult(proDraftVoList);
        page.setTotalCount(pageInfo.getTotal());
        return page;
    }

    @Override
    public void deleteProDraft(int id, String[] user) {
        ProInfoDraft proInfoDraft = new ProInfoDraft();
        proInfoDraft.setStatus(BaseConstant.PRO_DRAFT_STATUS.DELETE.getCode());
        proInfoDraft.setId(id);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        proInfoDraft.setUpdateTime(format.format(new Date()));
        proInfoDraftMapper.updateByPrimaryKeySelective(proInfoDraft);
        flowUtil.saveProOpt("删除", 0, id, Integer.parseInt(user[1]), BaseConstant.PRO_COPY_STATUS.DRAFT.getCode());
    }

    @Override
    public ProInfoDraft getProDraftDetailById(String id) {
        ProInfoDraft proInfo = proInfoDraftMapper.selectByPrimaryKey(id);
        if(proInfo == null){
            return null;
        }
        //根据项目id查询项目分层详情
        List<ProLayer> proLayers = proInfoDraftMapper.selectProLayerDraftByProId(proInfo.getId());
        if (proLayers != null && proLayers.size() > 0) {
            proInfo.setLayerList(proLayers);
        }
        List<Attach> attaches = proInfoDraftMapper.selectAttachDraftByProId(proInfo.getId(), BaseConstant.PRO_ATTACH_TYPE.PRO_SUPPLEMENT_IMPLEMENT.getCode());
        if (attaches != null && attaches.size() > 0) {
            proInfo.setFileList(attaches);
        }
        List<Team> teams = proInfoDraftMapper.selectTeamDraftByProId(proInfo.getId());
        if (teams != null && teams.size() > 0) {
            proInfo.setTeamList(teams);
        }
        //根据项目id查询项目业务模式详情
        List<ProModel> proModelList = proInfoDraftMapper.queryProModelDetailByProId(proInfo.getId());
        if (proModelList != null && proModelList.size() > 0) {
            proInfo.setProModelList(proModelList);
        }
        proInfo = setVoteProInfoTradeStructureDraft(proInfo);
        return  proInfo;
    }

    /**
     * 设置项目交易结构
     * @param proInfo
     * @return
     */
    private ProInfoDraft setVoteProInfoTradeStructureDraft(ProInfoDraft proInfo){
        String proId = proInfo.getId()+"";
        ProInfoTradeStructure proInfoTradeStructure = proInfoDraftMapper.queryProInfoTradeStructureDraftByProId(proId);
        if(proInfoTradeStructure!=null){
            //设置违约事件发生前和发生后分配顺序的比较附件list
            proInfoTradeStructure.setBreachCompareFileList(proInfoDraftMapper.queryAttactDraftByProId(proId,BaseConstant.PRO_ATTACH_TYPE.BREACH_COMPARE_FILE_LIST.getCode()));
            //设置违约事件阈值及内容设定附件list
            proInfoTradeStructure.setBreachContentFileList(proInfoDraftMapper.queryAttactDraftByProId(proId,BaseConstant.PRO_ATTACH_TYPE.BREACH_CONTENT_FILE_LIST.getCode()));
            //设置预计收取的基金管理费附件list
            proInfoTradeStructure.setEstimatedManagementFeeFileList(proInfoDraftMapper.queryAttactDraftByProId(proId,BaseConstant.PRO_ATTACH_TYPE.ESTIMATED_MANAGEMENT_FEE_FILE_LIST.getCode()));
            //设置各方出资安排附件list
            proInfoTradeStructure.setFundArrangementsFileList(proInfoDraftMapper.queryAttactDraftByProId(proId,BaseConstant.PRO_ATTACH_TYPE.FUND_ARRANGEMENTS_FILE_LIST.getCode()));
            //设置投资利率附件list
            proInfoTradeStructure.setRateInvestmentFileList(proInfoDraftMapper.queryAttactDraftByProId(proId,BaseConstant.PRO_ATTACH_TYPE.RATE_INVESTMENT_FILE_LIST.getCode()));
            //设置ABS发行金额、分层占比、及分层评级结果附件list
            proInfoTradeStructure.setRatingResultFileList(proInfoDraftMapper.queryAttactDraftByProId(proId,BaseConstant.PRO_ATTACH_TYPE.RATING_RESULT_FILE_LIST.getCode()));
            //设置各方出资金额及占比附件list
            proInfoTradeStructure.setRatioAmountFileList(proInfoDraftMapper.queryAttactDraftByProId(proId,BaseConstant.PRO_ATTACH_TYPE.RATIO_AMOUNT_FILE_LIST.getCode()));
            //设置种子基金投资范围或产品附件list
            proInfoTradeStructure.setScopeInvestmentFileList(proInfoDraftMapper.queryAttactDraftByProId(proId,BaseConstant.PRO_ATTACH_TYPE.SCOPE_INVESTMENT_FILE_LIST.getCode()));
            //设置种子基金占比附件list
            proInfoTradeStructure.setSeedFundProportionFileList(proInfoDraftMapper.queryAttactDraftByProId(proId,BaseConstant.PRO_ATTACH_TYPE.SEED_FUND_PROPORTION_FILE_LIST.getCode()));
            //设置投资期限附件list
            proInfoTradeStructure.setTermInvestmentFileList(proInfoDraftMapper.queryAttactDraftByProId(proId,BaseConstant.PRO_ATTACH_TYPE.TERM_INVESTMENT_FILE_LIST.getCode()));
            //设置基金总金额附件list
            proInfoTradeStructure.setTotalFundAmountFileList(proInfoDraftMapper.queryAttactDraftByProId(proId,BaseConstant.PRO_ATTACH_TYPE.TOTAL_FUND_AMOUNT_FILE_LIST.getCode()));
            //设置底层资产投放标准附件list
            proInfoTradeStructure.setUnderlyingAssetsStandardFileList(proInfoDraftMapper.queryAttactDraftByProId(proId,BaseConstant.PRO_ATTACH_TYPE.UNDERLYING_ASSETS_STANDARD_FILE_LIST.getCode()));
            //设置基金风控和投资风险安排附件list
            proInfoTradeStructure.setRiskArrangementFileList(proInfoDraftMapper.queryAttactDraftByProId(proId,BaseConstant.PRO_ATTACH_TYPE.RISK_ARRANGEMENT_FILE_LIST.getCode()));
            //设置原始权益人是否履行回购义务(Y/N)附件list
            proInfoTradeStructure.setIsOriginalBuybackFileList(proInfoDraftMapper.queryAttactDraftByProId(proId,BaseConstant.PRO_ATTACH_TYPE.IS_ORIGINAL_BUYBACK_FILE_LIST.getCode()));
            //设置原始权益人出表与否(Y/N)附件list
            proInfoTradeStructure.setIsOriginalListFileList(proInfoDraftMapper.queryAttactDraftByProId(proId,BaseConstant.PRO_ATTACH_TYPE.IS_ORIGINAL_LIST_FILE_LIST.getCode()));
        }
        proInfo.setProInfoTradeStructure(proInfoTradeStructure);
        return proInfo;
    }

    /**
     * 根据部门查询上会人员
     * @param department 部门code
     * @return
     */
    public List<User> queryMeetPersonByProType(String department){
        return projectMapper.queryMeetPersonByProType(department);
    }

}
