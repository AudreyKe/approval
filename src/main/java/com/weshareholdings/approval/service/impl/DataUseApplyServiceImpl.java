package com.weshareholdings.approval.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weshareholdings.approval.config.BaseConstant;
import com.weshareholdings.approval.repository.DataUseApplyMapper;
import com.weshareholdings.approval.service.DataUseApplyService;
import com.weshareholdings.approval.util.FlowUtil;
import com.weshareholdings.approval.util.ResultUtil;
import com.weshareholdings.approval.vo.DataUseApplyProInfo;
import com.weshareholdings.approval.vo.NodesDetail;
import com.weshareholdings.approval.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.snaker.engine.access.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
public class DataUseApplyServiceImpl implements DataUseApplyService {

    @Autowired
    private DataUseApplyMapper dataUseApplyMapper;



    @Autowired
    private FlowUtil flowUtil;


    /**
     * 查询数据使用申请列表
     * @param operator 申请人
     * @param page 分页对象
     * @return
     */
    public Page<DataUseApplyProInfo> queryDataUseApplyList(String operator, Page<DataUseApplyProInfo> page){
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        List<DataUseApplyProInfo> list = dataUseApplyMapper.queryDataUseApplyList(operator,BaseConstant.PROTYPE.DATA_USE_APPLY.getCode());
        for(DataUseApplyProInfo dataUseApplyProInfo:list){
            setNowNode(dataUseApplyProInfo);
        }
        PageInfo<DataUseApplyProInfo> pageInfo = new PageInfo<>(list);
        page.setResult(list);
        page.setTotalCount(pageInfo.getTotal());
        return page;
    }

    /**
     * 查询所有应用系统名称
     * @return
     */
    public Result queryAppName(){
        List<Map> allApp = dataUseApplyMapper.queryAppName();
        List<Map> resultMap = new ArrayList<>();
        for(Map map:allApp){
            Map app = new HashMap();
            app.put("departmentCode",map.get("value"));
            app.put("departmentName",map.get("name"));
            app.put("appName",map.get("appName")==null?"":map.get("appName").toString().split(","));
            resultMap.add(app);
        }
        return ResultUtil.success(null,resultMap);
    }

    /**
     * 根据流程id查询数据使用申请详情
     * @param orderId 流程id
     * @return
     */
    public DataUseApplyProInfo queryProByOrderId(String orderId){
        DataUseApplyProInfo dataUseApplyProInfo = dataUseApplyMapper.queryProByOrderId(orderId);
        //根据流程id设置流程详情
        setProcessDetails(dataUseApplyProInfo);
        //设置当前节点
        setNowNode(dataUseApplyProInfo);
        //设置节点完成装
        setNodeCompleteStatus(dataUseApplyProInfo);
        return dataUseApplyProInfo;
    }

    /**
     * 设置节点完成装
     * @param dataUseApplyProInfo 节点详情
     * @return 返回结果
     */
    private DataUseApplyProInfo setNodeCompleteStatus(DataUseApplyProInfo dataUseApplyProInfo){
        int index = -1;
        for(int i=0,len=dataUseApplyProInfo.getNodesDetailList().size();i<len;i++){
            if(dataUseApplyProInfo.getNowNode()!=null&&dataUseApplyProInfo.getNowNode().getNodeName()==null){
                dataUseApplyProInfo.getNodesDetailList().get(i).setIsComplete("2");
            }else if(dataUseApplyProInfo.getNowNode()!=null&&index==-1&&!dataUseApplyProInfo.getNodesDetailList().get(i).getNodeName().equals(dataUseApplyProInfo.getNowNode().getNodeName())){
                dataUseApplyProInfo.getNodesDetailList().get(i).setIsComplete("2");
            }else if(dataUseApplyProInfo.getNowNode()!=null&&index==-1&&dataUseApplyProInfo.getNodesDetailList().get(i).getNodeName().equals(dataUseApplyProInfo.getNowNode().getNodeName())){
                dataUseApplyProInfo.getNodesDetailList().get(i).setIsComplete("1");
                index = i;
            }else if(dataUseApplyProInfo.getNowNode()!=null){
                dataUseApplyProInfo.getNodesDetailList().get(i).setIsComplete("0");
            }else {
                dataUseApplyProInfo.getNodesDetailList().get(i).setIsComplete("2");
            }
        }
        return dataUseApplyProInfo;
    }

    /**
     * 设置当前节点
     * @param dataUseApplyProInfo 使用申请详情
     * @return 返回结果
     */
    private DataUseApplyProInfo setNowNode(DataUseApplyProInfo dataUseApplyProInfo){
        Map<String,String> nodeMap = dataUseApplyMapper.queryNowNodeByOrderId(dataUseApplyProInfo.getOrderId());
        if(nodeMap!=null){
            String applyCategory = nodeMap.get("apply_category")+"";
            String applyDepartment = nodeMap.get("apply_department")+"";
            String display_Name = nodeMap.get("display_Name")+"";
            NodesDetail detail = new NodesDetail();
            detail.setActors(dataUseApplyMapper.queryActorNameByApplyDepartmentAndDisplayName(applyDepartment,display_Name,FlowUtil.arrayToJsonStrSingle(applyCategory.split(","))));
            detail.setNodeName(display_Name);
            if("申请验收".equals(display_Name)||"提交申请".equals(display_Name)){
                detail.setActors(dataUseApplyMapper.queryApplyName(dataUseApplyProInfo.getOrderId()));
            }
            dataUseApplyProInfo.setNowNode(detail);
            detail.setIsComplete("1");
        }
        return dataUseApplyProInfo;
    }

    /**
     * 根据流程id设置流程详情
     * @param dataUseApplyProInfo 使用申请详情
     * @return 返回结果
     */
    private DataUseApplyProInfo setProcessDetails(DataUseApplyProInfo dataUseApplyProInfo){
        Map<String,String> nodeMap = dataUseApplyMapper.queryProcessDetailByOrderId(dataUseApplyProInfo.getOrderId());
        if(nodeMap!=null){
            String[] nodeList = nodeMap.get("value").split(",");
            String applyCategory = nodeMap.get("apply_category")+"";
            String applyDepartment = nodeMap.get("apply_department")+"";
            List<NodesDetail> nodesDetailList = new ArrayList<>();
            for(int i=0,len=nodeList.length;i<len;i++){
                NodesDetail detail = new NodesDetail();
                detail.setNodeName(nodeList[i]);
                List<String> actors = dataUseApplyMapper.queryActorNameNoCofig(nodeList[i],dataUseApplyProInfo.getOrderId());
                if(actors!=null&&actors.size()>0){
                    detail.setActors(actors);
                }else{
                    detail.setActors(dataUseApplyMapper.queryActorNameByApplyDepartmentAndDisplayName(applyDepartment,nodeList[i],FlowUtil.arrayToJsonStrSingle(applyCategory.split(","))));
                }
                if(i!=0){
                    detail.setPreviousNode(nodeList[i-1]);
                }
                if(i!=len-1){
                    detail.setNextNode(nodeList[i+1]);
                }
                if(i==0||i==len-1){
                    detail.setActors(dataUseApplyMapper.queryApplyName(dataUseApplyProInfo.getOrderId()));
                }
                nodesDetailList.add(detail);
            }
            dataUseApplyProInfo.setNodesDetailList(nodesDetailList);
        }
        return dataUseApplyProInfo;
    }

    /**
     * 根据草稿id查询数据使用申请草稿详情
     * @param id 流程id
     * @return
     */
    public DataUseApplyProInfo proDraftDetail(String id){
        DataUseApplyProInfo dataUseApplyProInfo = dataUseApplyMapper.proDraftDetail(id);
        return dataUseApplyProInfo;
    }



    /**
     * 添加项目
     * @param proInfo 数据申请对象
     * @param user 用户
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 6000, rollbackFor = Exception.class)
    public Result addProInfo(Map proInfo, String[] user){
        //获取申请部门参数
        String applyDepartment = proInfo.get("applyDepartment")+"";
        //获取申请数据类别参数
        String applyCategoryParam = proInfo.get("applyCategoryParam")+"";
        //获取审核冲突节点
        String conflictNodeName = isSubmit(applyDepartment,applyCategoryParam);
        if(conflictNodeName!=null){
            return ResultUtil.error("提交的数据申请类型在"+conflictNodeName+"节点处有审核冲突,请分开提交!");
        }
        //根据类别获取流程定义id
        String processId = getProceessId(applyCategoryParam);
        log.info("获取流程定义id为："+processId);
        if(processId==null){
            return ResultUtil.error("获取流程定义失败,请检查数据库配置!");
        }
        //验证用户是否存在并新增流程
        String orderId = flowUtil.insertOrder(proInfo,processId,user[0]);
        if(orderId == null){
            return ResultUtil.error("新增流程失败!");
        }
        //根据流程id添加任务参与者
        if(flowUtil.addTaskActor(orderId,applyCategoryParam,applyDepartment)){
            return ResultUtil.error("未配置流程审批人,请配置!");
        }
        //设置创建人
        proInfo.put("createUserId",user[1]);
        //设置流程实例id
        proInfo.put("orderId",orderId);
        //新增项目
        dataUseApplyMapper.insertPro(proInfo);
        //设置项目id
        proInfo.put("proId", proInfo.get("id"));
        //设置操作类型
        proInfo.put("optType", BaseConstant.PRO_COPY_STATUS.NORMAL.getCode());
        //新增项目操作历史
        dataUseApplyMapper.insertProCopy(proInfo);
        // 插入操作记录
        flowUtil.saveProOpt("新增", Integer.parseInt(proInfo.get("id")+""), Integer.parseInt(proInfo.get("proId")+""), Integer.valueOf(user[1]), BaseConstant.PRO_COPY_STATUS.NORMAL.getCode());
        //给下一节点的人发送提醒邮件
        flowUtil.processNextNodeSendEmail(orderId, null);
        return ResultUtil.success();
    }


    /**
     * 数据使用申请草稿保存
     * @param proInfo 数据申请草稿对象
     * @param user 用户
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 6000, rollbackFor = Exception.class)
    public Result addProDraft(Map proInfo, String[] user){
        //获取申请部门
        String applyDepartment = proInfo.get("applyDepartment")+"";
        //获取申请数据类别
        String applyCategoryParam = proInfo.get("applyCategoryParam")+"";
        //获取审核冲突节点
        String conflictNodeName = isSubmit(applyDepartment,applyCategoryParam);
        if(conflictNodeName!=null){
            return ResultUtil.error("提交的数据申请类型在"+conflictNodeName+"节点处有审核冲突,请分开提交!");
        }
        //设置创建人
        proInfo.put("createUserId",user[1]);
        if(proInfo.get("id")==null||"".equals(proInfo.get("id"))){
            //新增项目
            dataUseApplyMapper.insertProDraft(proInfo);
        }else{
            //修改项目
            dataUseApplyMapper.updateProDraftByProId(proInfo);
        }
        //设置项目id
        proInfo.put("proId", proInfo.get("id"));
        //设置操作类型
        proInfo.put("optType", BaseConstant.PRO_COPY_STATUS.DRAFT.getCode());
        //新增项目操作历史
        dataUseApplyMapper.insertProCopy(proInfo);
        // 插入操作记录
        flowUtil.saveProOpt("新增", Integer.parseInt(proInfo.get("id")+""), Integer.parseInt(proInfo.get("proId")+""), Integer.valueOf(user[1]), BaseConstant.PRO_COPY_STATUS.DRAFT.getCode());
        return ResultUtil.success();
    }

    /**
     * 根据类别获取流程定义id
     * @param applyCategory 项目类别
     * @return
     */
    private String getProceessId(String applyCategory){
        //根据申请类别到参数表中查询类型值
        List<String> applyTypes = dataUseApplyMapper.queryTypeByApplyCategory(applyCategory);
        //处理类型值获取最终的类型值
        String type = getType(applyTypes);
        //根据类型查询流程定义id
        String processId = dataUseApplyMapper.queryProcessIdByType(type);
        return processId;
    }

    /**
     * 获取类别(先去重，后排序)
     * @param applyTypes 申请类型
     * @return
     */
    private String getType(List<String> applyTypes){
        List<String> typeList = new ArrayList<>();
        //对类型去重
        for(String type:applyTypes){
            String[] types = type.split("、");
            for(String str:types){
                if(!typeList.contains(str)){
                    typeList.add(str);
                }
            }
        }
        Collections.sort(typeList);
        String typeResult = "";
        //对类型进行排序
        for(int i=0,len=typeList.size();i<len;i++){
            if(i==0){
                typeResult = typeList.get(i);
            }else{
                typeResult = typeResult+"、"+typeList.get(i);
            }
        }
        return typeResult;
    }

    /**
     * 根据申请部门和申请数据类别判断流程中是否有审核冲突人员
     * @param applyDepartment 申请部门
     * @param applyCategory 申请数据类别
     * @return
     */
    private String isSubmit(String applyDepartment,String applyCategory){
        //根据申请部门和申请数据类别判断流程中是否有审核冲突人员
        String name = dataUseApplyMapper.queryCountByApplyDepartmentAndApplyCategory(applyDepartment,applyCategory);
        return name;
    }


}
