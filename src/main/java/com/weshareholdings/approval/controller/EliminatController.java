package com.weshareholdings.approval.controller;


import com.weshareholdings.approval.config.BaseConstant;
import com.weshareholdings.approval.po.ProInfo;
import com.weshareholdings.approval.po.ProParamter;
import com.weshareholdings.approval.repository.ProParamterMapper;
import com.weshareholdings.approval.service.EliminatGoldService;
import com.weshareholdings.approval.service.VoteMeetService;
import com.weshareholdings.approval.util.DateUtil;
import com.weshareholdings.approval.util.RedisUtil;
import com.weshareholdings.approval.util.ResultUtil;
import com.weshareholdings.approval.vo.Result;
import org.snaker.engine.access.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RequestMapping(value = "/snaker/eliminat/")
@RestController
public class EliminatController {

    @Autowired
    private EliminatGoldService eliminatGoldService;

    @Autowired
    @Qualifier("redisUtil")
    private RedisUtil redisUtil;

    @Autowired
    private ProParamterMapper proParamterMapper;

    /**
     * 查询消金项目列表
     *
     * @param page
     * @param request
     * @return
     */
    @RequestMapping(value = "queryEliminatGoldList")
    public Result queryEliminatGoldList(HttpServletRequest request,Page<ProInfo> page, String proName) {
        String[] user = DateUtil.getRedisUser(request, redisUtil);
        if(user==null||user.length<=0){
            return ResultUtil.error("执行失败！请重新登陆！");
        }
        return ResultUtil.success("执行成功", eliminatGoldService.queryEliminatGoldList(user[1],page , proName));
    }

    /**
     * 流程实例查询（详情）
     */
    @GetMapping(value = "proDetail/{orderId}")
    public Result orderDetail(@PathVariable("orderId") String proId) {
        return ResultUtil.success("执行成功", eliminatGoldService.queryProByOrderId(proId));
    }

    /**
     * 新增合作方
     *
     * @return
     */
    @RequestMapping(value = "addPartners")
    public Result addPartners(String value) {
        ProParamter proParamter = new ProParamter();
        proParamter.setType(BaseConstant.PARAMTYPE.PARTNERS.getCode());
        proParamter.setValue(value);
        proParamterMapper.insertSelective(proParamter);
        return ResultUtil.success("执行成功");
    }

    /**
     * 查询合作方
     *
     * @return
     */
    @GetMapping(value = "partnersList")
    public Result queryPartnersList(Page<String> page, String value) {
        return ResultUtil.success("执行成功", proParamterMapper.selectProParamter(BaseConstant.PARAMTYPE.PARTNERS.getCode(), value));
    }
}
