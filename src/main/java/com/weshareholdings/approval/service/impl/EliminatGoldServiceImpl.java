package com.weshareholdings.approval.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weshareholdings.approval.config.BaseConstant;
import com.weshareholdings.approval.po.*;
import com.weshareholdings.approval.repository.EliminatGoldMapper;
import com.weshareholdings.approval.repository.ProjectMapper;
import com.weshareholdings.approval.repository.VoteMeetMapper;
import com.weshareholdings.approval.service.EliminatGoldService;
import com.weshareholdings.approval.service.VoteMeetService;
import com.weshareholdings.approval.vo.GoldProInfo;
import lombok.extern.slf4j.Slf4j;
import org.snaker.engine.access.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EliminatGoldServiceImpl implements EliminatGoldService {

    @Autowired
    private EliminatGoldMapper eliminatGoldMapper;

    @Autowired
    private ProjectMapper projectMapper;

    public Page<ProInfo> queryEliminatGoldList(String operator,Page<ProInfo> page, String proName){
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        List<ProInfo> list = eliminatGoldMapper.queryEliminatGoldList(operator,proName,BaseConstant.PROTYPE.ABS_PROJECT.getCode(),BaseConstant.PROTYPE.CONSUMER_PROJECT.getCode());
        PageInfo<ProInfo> pageInfo = new PageInfo<>(list);
        page.setResult(list);
        page.setTotalCount(pageInfo.getTotal());
        return page;
    }

    public GoldProInfo queryProByOrderId(String orderId){
        //根据流程实例id查询项目详情
        GoldProInfo goldProInfo = eliminatGoldMapper.queryProByOtderId(orderId);
        if (goldProInfo == null) {
            return null;
        }
        List<Attach> attaches = projectMapper.queryAttactByProId(goldProInfo.getId() + "",BaseConstant.PRO_ATTACH_TYPE.PRO_SUPPLEMENT_IMPLEMENT.getCode());
        if (attaches != null && attaches.size() > 0) {
            goldProInfo.setFileList(attaches);
        }
        List<Team> teams = projectMapper.queryTeamByProId(goldProInfo.getId() + "");
        if (teams != null && teams.size() > 0) {
            goldProInfo.setTeamList(teams);
        }
        //根据项目id查询项目业务模式详情
        List<ProModel> proModelList = projectMapper.queryProModelDetailByProId(goldProInfo.getId() + "");
        if (proModelList != null && proModelList.size() > 0) {
            goldProInfo.setProModelList(proModelList);
        }
        return goldProInfo;
    }
}
