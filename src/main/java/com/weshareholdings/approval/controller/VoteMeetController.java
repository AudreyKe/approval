package com.weshareholdings.approval.controller;


import com.weshareholdings.approval.config.BaseConstant;
import com.weshareholdings.approval.po.Attach;
import com.weshareholdings.approval.po.ProInfo;

import com.weshareholdings.approval.po.ProParamter;
import com.weshareholdings.approval.repository.ProParamterMapper;
import com.weshareholdings.approval.service.VoteMeetService;
import com.weshareholdings.approval.util.DateUtil;
import com.weshareholdings.approval.util.RedisUtil;
import com.weshareholdings.approval.util.ResultUtil;
import com.weshareholdings.approval.vo.Result;
import com.weshareholdings.approval.vo.VoteProInfo;
import org.snaker.engine.access.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


@RequestMapping(value = "/snaker/voteMeet/")
@RestController
public class VoteMeetController {

    @Autowired
    private VoteMeetService voteMeetService;

    @Autowired
    @Qualifier("redisUtil")
    private RedisUtil redisUtil;

    @Autowired
    private ProParamterMapper proParamterMapper;


    /**
     * 查询已办审批列表
     *
     * @param page
     * @param request
     * @return
     */
    @RequestMapping(value = "queryVoteMeetList")
    public Result queryVoteMeetList(HttpServletRequest request, Page<VoteProInfo> page, String proName, String proType) {
        String[] user = DateUtil.getRedisUser(request, redisUtil);
        if(user==null||user.length<=0){
            return ResultUtil.error("执行失败！请重新登陆！");
        }
        return ResultUtil.success("执行成功", voteMeetService.queryVoteMeetList(user[1] ,page , proName,proType));
    }

    /**
     * 流程实例查询（详情）
     */
    @GetMapping(value = "proDetail/{orderId}")
    public Result orderDetail(@PathVariable("orderId") String proId) {
        return ResultUtil.success("执行成功", voteMeetService.queryProById(proId));
    }

    /**
     * 新增合作伙伴
     *
     * @return
     */
    @RequestMapping(value = "addCollaborator")
    public Result addCollaborator(String value) {
        ProParamter proParamter = new ProParamter();
        proParamter.setType(BaseConstant.PARAMTYPE.COLLABORATOR.getCode());
        proParamter.setValue(value);
        proParamterMapper.insertSelective(proParamter);
        return ResultUtil.success("执行成功");
    }

    /**
     * 查询合作伙伴
     *
     * @return
     */
    @GetMapping(value = "collaboratorList")
    public Result queryCollaboratorList(Page<String> page, String value) {
        return ResultUtil.success("执行成功", proParamterMapper.selectProParamter(BaseConstant.PARAMTYPE.COLLABORATOR.getCode(), value));
    }
}
