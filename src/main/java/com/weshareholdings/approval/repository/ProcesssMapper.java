package com.weshareholdings.approval.repository;

import com.weshareholdings.approval.vo.ProcessResult;

import java.util.List;

/**
 * @Author liling
 * @Date 2018/9/12
 **/
public interface ProcesssMapper {

    /**
     * 查询所有流程名称
     *
     * @return
     */
    List<ProcessResult> queryProcessName();
}
