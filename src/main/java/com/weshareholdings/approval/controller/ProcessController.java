package com.weshareholdings.approval.controller;

import com.weshareholdings.approval.repository.ProcesssMapper;
import com.weshareholdings.approval.util.SnakerEngineFacets;
import com.weshareholdings.approval.util.ResultUtil;
import com.weshareholdings.approval.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 流程定义处理类
 */
@RequestMapping("/snaker/process/")
@RestController
public class ProcessController {

    @Autowired
    private SnakerEngineFacets facets;
    @Autowired
    private ProcesssMapper processsMapper;

    /**
     * 初始化流程
     */
    @RequestMapping(value = "init")
    public Result initProcess() {
        facets.initFlows(); //可改为读取配置文件
        return ResultUtil.success("执行成功");
    }

    /**
     * 获取所有流程名称
     */
    @GetMapping("/processNames")
    public Result processNames() {
        return ResultUtil.success("执行成功", processsMapper.queryProcessName());
    }


}
