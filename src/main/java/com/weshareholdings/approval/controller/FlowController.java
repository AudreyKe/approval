package com.weshareholdings.approval.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.weshareholdings.approval.config.BaseConstant;
import com.weshareholdings.approval.config.Props;
import com.weshareholdings.approval.po.Attach;
import com.weshareholdings.approval.po.ProInfoDraft;
import com.weshareholdings.approval.repository.ProjectMapper;
import com.weshareholdings.approval.service.FlowService;
import com.weshareholdings.approval.service.TaskService;
import com.weshareholdings.approval.util.*;
import com.weshareholdings.approval.vo.OrderResult;
import com.weshareholdings.approval.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.snaker.engine.access.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

@RequestMapping(value = "/snaker/flow/")
@RestController
@Slf4j
public class FlowController {

    @Autowired
    private FlowService flowService;
    @Autowired
    private SnakerEngineFacets facets;
    @Autowired
    private Props props;
    @Autowired
    @Qualifier("redisUtil")
    private RedisUtil redisUtil;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private FlowUtil flowUtil;
    /**
     * 执行审批
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "process")
    public Result process(HttpServletRequest request) {
        String processId = request.getParameter("processId");
        String orderId = request.getParameter("orderId");
        String taskId = request.getParameter("taskId");
        String operator = DateUtil.getRedisValue(request, redisUtil);
        // 获取当前用户输入内容
        String resultState = request.getParameter("resultState");
        String result = request.getParameter("result");
        String nextOperator = request.getParameter("");
        String ccOperator = request.getParameter("ccoperator");
        String acceptanceConclusion = request.getParameter("acceptanceConclusion");
        //消金秘书选择的节点
        String nodes = request.getParameter("nodes");
        if(nodes==null){
            nodes = "";
        }
        String[] nodesResult = nodes.split(",");
        String assignee = request.getParameter("assignee");
        if (StringUtils.isBlank(resultState)) {
            return ResultUtil.error("审批结果不能为空！");
        }
        if ((!"0".equals(resultState)) && StringUtils.isBlank(result)) {
            return ResultUtil.error("审批意见不能为空!");
        }
        return flowService.process(acceptanceConclusion,processId,orderId,taskId,operator,resultState,result,nextOperator,ccOperator,nodesResult,assignee);
    }


    /**
     * 流程实例查询(当前用户发起)
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "order/user")
    public Result selectOrder(Page<OrderResult> page, HttpServletRequest request, String orderName, String proType) {
        String[] user = DateUtil.getRedisUser(request, redisUtil);
        return ResultUtil.success("执行成功", taskService.queryOrder(page, user, null, null, orderName, proType));
    }

    /**
     * 查询全部项目
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "order/queryAllPro")
    public Result queryAllPro(Page<OrderResult> page, HttpServletRequest request, String orderName, String proType) {
        String[] user = DateUtil.getRedisUser(request, redisUtil);
        return ResultUtil.success("执行成功", taskService.queryAllPro(page, user, null, null, orderName, proType));
    }

    /**
     * 流程实例查询（详情）
     */
    @GetMapping(value = "order/{orderId}")
    public Result orderDetail(@PathVariable("orderId") String orderId) {
        return ResultUtil.success("执行成功", flowService.queryProById(orderId));
    }

    /**
     * 查询已办审批列表
     *
     * @param page
     * @param request
     * @return
     */
    @RequestMapping(value = "order")
    public Result historyTaskList(Page<OrderResult> page, HttpServletRequest request, String orderName, String proType) {
        String operator = DateUtil.getRedisValue(request, redisUtil);
        return ResultUtil.success("执行成功", taskService.queryHistoryTask(page, operator, orderName, proType));
    }

    /**
     * 根据附件id下载附件
     */
    @GetMapping(value = "attach/{fileId}/{status}")
    public Result download(@PathVariable String fileId, @PathVariable String status, HttpServletResponse response) throws Exception {
        Attach attach = flowService.queryPathByFileId(fileId, status);
        if (attach == null) {
            return ResultUtil.error("500", "执行失败,附件不存在");
        }
        String path = attach.getFilePath() + attach.getRealFileName();
        File file = new File(path);
        if (file.exists()) {
            // 设置强制下载不打开
            response.setContentType("application/force-download");
            // 设置文件名
            response.addHeader("Content-Disposition", "attachment;fileName=" + new String(attach.getFileName().getBytes(), "ISO-8859-1"));
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    /**
     * 项目立项
     */
    @RequestMapping(value = "enable", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Result enableProcess(@RequestBody JSONObject proInfo, HttpServletRequest request) {
        log.info("进入项目立项处理。。" + JSON.toJSONString(proInfo));
        Map mResult =flowUtil.matchParam(proInfo);
        if ("E".equals(mResult.get("status"))) {
            return ResultUtil.error("500", "执行失败，" + mResult.get("errorMessage"));
        }
        String[] user = DateUtil.getRedisUser(request, redisUtil);
        if (user == null) {
            return ResultUtil.error("500", "执行失败");
        }
        if("0".equals(proInfo.get("proType"))){
            // 当重新发起（项目评审申请）时，将前置流程id置为空
            proInfo.put("beforeOrderId", "");
        }
        boolean addResult = flowService.addProInfo(proInfo, user, getProcessId(proInfo.get("proType")+""));
        if (!addResult) {
            return ResultUtil.error("500", "执行失败");
        }
        log.info("结束项目处理。。。");
        return ResultUtil.success("执行成功", null);
    }

    /**
     * 根据项目类型获取processId
     * @param proType
     * @return
     */
    private String getProcessId(String proType){
        String processId = null;
        if(BaseConstant.PROTYPE.ABS_PROJECT.getCode().equals(proType)){
            processId = props.getProjectProcessId();
        }else if(BaseConstant.PROTYPE.ABS_REVIEW.getCode().equals(proType)){
            processId = props.getReviewProcessId();
        }else if(BaseConstant.PROTYPE.CAST_PROJECT.getCode().equals(proType)){
            processId = props.getVetoProcessId();
        }else{
            processId = props.getGoldProcessId();
        }
        return processId;
    }
    /**
     * 发起项目审批
     */
    @RequestMapping(value = "review", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Result review(@RequestBody JSONObject proInfo, HttpServletRequest request) {
        log.info("进入项目审批处理。。" + JSON.toJSONString(proInfo));
        Map mResult = DateUtil.matchParam(proInfo, BaseConstant.ENABLEPROCESS_EXPRESSION_ABS);
        if ("E".equals(mResult.get("status"))) {
            return ResultUtil.error("fail", "执行失败，" + mResult.get("errorMessage"));
        }
        if (proInfo.get("orderId") == null || "".equals(proInfo.get("orderId"))) {
            return ResultUtil.error("fail", "执行失败，项目立项流程ID不能为空！");
        }
        proInfo.put("beforeOrderId", proInfo.get("orderId"));
        String[] user = DateUtil.getRedisUser(request, redisUtil);
        if (user == null) {
            return ResultUtil.error("fail", "执行失败");
        }
        boolean addResult = flowService.addProInfo(proInfo, user, props.getReviewProcessId());
        if (!addResult) {
            return ResultUtil.error("fail", "执行失败");
        }
        log.info("结束项目处理。。。");
        return ResultUtil.success("执行成功", null);
    }


    /**
     * 项目审批查询（详情）
     */
    @GetMapping(value = "review/{orderId}")
    public Result approvalDetail(@PathVariable("orderId") String orderId) {
        return ResultUtil.success("执行成功", flowService.queryApprovalDetail(orderId));
    }

    /**
     * 处理文件上传
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public Result upload(@RequestParam("file") MultipartFile file ,@RequestParam("proAttachType") String proAttachType, HttpServletRequest request) {
        String[] user = DateUtil.getRedisUser(request, redisUtil);
        Attach attach = flowService.uploadAttach(file,user, proAttachType);
        if (attach == null||user==null||user.length<=0) {
            return ResultUtil.error("附件上传失败");
        }
        return ResultUtil.success("执行成功", attach);
    }

    /**
     * 查询待发起审批的项目
     * @param page
     * @param request
     * @return
     */
        @RequestMapping(value = "reviewList")
    public Result selectAprovalOrder(Page<OrderResult> page, HttpServletRequest request, String orderName, String proType) {
        String[] user = DateUtil.getRedisUser(request, redisUtil);
        return ResultUtil.success("执行成功", taskService.queryApprovalByCreator(page, user[1], "0", props.getProjectProcessId(), orderName, proType));
    }

    /**
     * 查询立项申请
     */
    @RequestMapping(value = "orderList")
    public Result selectOrderList(Page<OrderResult> page, HttpServletRequest request, String orderName, String proType) {
        String[] user = DateUtil.getRedisUser(request, redisUtil);
        Page<OrderResult> result = taskService.queryOrderList(page, user, null, orderName, new String[]{"apply"}, BaseConstant.PROTYPE.ABS_PROJECT.getCode());
        return ResultUtil.success("执行成功", result);
    }


    /**
     * 修改立项申请内容
     *
     * @param proInfo
     * @param request
     * @return
     */
    @RequestMapping(value = "modify", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Result submitModify(@RequestBody JSONObject proInfo, HttpServletRequest request) {
        String[] user = DateUtil.getRedisUser(request, redisUtil);
        Map mResult =flowUtil.matchParam(proInfo);
        if ("E".equals(mResult.get("status"))) {
            return ResultUtil.error("500", "执行失败，" + mResult.get("errorMessage"));
        }
        Map<String, String> resultMap = flowService.submitModify(proInfo,user,getProcessId(proInfo.get("proType")+""));
        if (ResultUtil.SUCCESS_CODE.equals(resultMap.get("code"))) {
            return ResultUtil.success("执行成功");
        } else {
            return ResultUtil.error(resultMap.get("code"), resultMap.get("message"));
        }

    }

    /**
     * 删除文件
     */
    @PostMapping(value = "attach/delete/{fileId}")
    public Result deleteFile(@PathVariable("fileId") String fileId) {
        projectMapper.deleteAttachByPrimaryKey(fileId);
        return ResultUtil.success("执行成功");
    }

    /**
     * 查询项目草稿列表
     * @param proName
     * @param proType
     * @param request
     * @return
     */
    @RequestMapping("getProDrafts")
    public Result getProDrafts(Page page, String proName, String proType, HttpServletRequest request){
        String[] users = DateUtil.getRedisUser(request, redisUtil);
        return ResultUtil.success("执行成功", flowService.getProDrafts(page, proName, proType, users));
    }

    /**
     * 根据id查询项目草稿详情
     * @param id
     * @return
     */
    @RequestMapping("getProDraftById")
    public Result getProDraftById(String id){
        ProInfoDraft proInfoDraft = flowService.getProDraftDetailById(id);
        if(null == proInfoDraft){
            return ResultUtil.error("未查询到对应的项目信息");
        }
       return ResultUtil.success("执行成功", proInfoDraft) ;
    }
    /**
     * 保存项目
     * @param proInfo
     * @param request
     * @return
     */
    @RequestMapping("savePro")
    public Result savePro(@RequestBody JSONObject proInfo, HttpServletRequest request){
        String[] user = DateUtil.getRedisUser(request, redisUtil);
        return  flowService.saveProInfoDraft(proInfo,user,getProcessId(proInfo.get("proType")+""));
    }

    @RequestMapping("unSubmitPro/delete")
    public Result deleteProDraft(int id, HttpServletRequest request){
        String[] users = DateUtil.getRedisUser(request, redisUtil);
        flowService.deleteProDraft(id, users);
        return ResultUtil.success("执行成功");
    }
    /**
     * 获取分销渠道名
     *
     * @return
     */
    @RequestMapping(value = "channel")
    public Result queryChannel() {
        return ResultUtil.success("执行成功", BaseConstant.Channel);
    }


    /**
     * 根据项目类型查询上会人员
     */
    @GetMapping(value = "getMeetPerson/{proType}")
    public Result getMeetPersonByProType(@PathVariable("proType") String proType) {
        return ResultUtil.success("执行成功", flowService.queryMeetPersonByProType(BaseConstant.ProjectTypeReflect.get(proType)));
    }

}
