package com.weshareholdings.approval.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weshareholdings.approval.config.BaseConstant;
import com.weshareholdings.approval.po.*;
import com.weshareholdings.approval.repository.ProjectMapper;
import com.weshareholdings.approval.repository.VoteMeetMapper;
import com.weshareholdings.approval.vo.VoteProInfo;
import com.weshareholdings.approval.vo.VoteProInfoVo;
import org.snaker.engine.access.Page;
import com.weshareholdings.approval.service.VoteMeetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class VoteMeetServiceImpl implements VoteMeetService {

    @Autowired
    private VoteMeetMapper voteMeetMapper;

    @Autowired
    private ProjectMapper projectMapper;

    public Page<VoteProInfo> queryVoteMeetList(String operator,Page<VoteProInfo> page, String proName,String proType){
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        List<VoteProInfo> list = voteMeetMapper.queryVoteMeetList(operator,proName,BaseConstant.PROTYPE.ABS_PROJECT.getCode(),BaseConstant.PROTYPE.CAST_PROJECT.getCode(),proType);
        PageInfo<VoteProInfo> pageInfo = new PageInfo<>(list);
        page.setResult(list);
        page.setTotalCount(pageInfo.getTotal());
        return page;
    }

    public VoteProInfoVo queryProById(String orderId){
        //根据流程实例id查询项目详情
        VoteProInfoVo voteProInfoVo = voteMeetMapper.queryProByOtderId(orderId);
        if (voteProInfoVo == null) {
            return null;
        }
        //根据项目id查询项目分层详情
        List<ProLayer> proLayers = projectMapper.queryProLayerByProId(voteProInfoVo.getId() + "");
        if (proLayers != null && proLayers.size() > 0) {
            voteProInfoVo.setLayerList(proLayers);
        }
        //根据项目id查询项目团队信息
        List<Team> teams = projectMapper.queryTeamByProId(voteProInfoVo.getId() + "");
        if (teams != null && teams.size() > 0) {
            voteProInfoVo.setTeamList(teams);
        }
        //根据项目id查询项目业务模式详情
        List<ProModel> proModelList = projectMapper.queryProModelDetailByProId(voteProInfoVo.getId() + "");
        if (proModelList != null && proModelList.size() > 0) {
            voteProInfoVo.setProModelList(proModelList);
        }
        //设置项目附件信息
        voteProInfoVo = setVoteProInfoVoAttach(voteProInfoVo);
        //设置项目交易结构
        voteProInfoVo = setVoteProInfoTradeStructure(voteProInfoVo);
        return voteProInfoVo;
    }

    /**
     * 设置项目附件信息
     * @param voteProInfoVo
     * @return
     */
    private VoteProInfoVo setVoteProInfoVoAttach(VoteProInfoVo voteProInfoVo){
        //查询项目立项、补充、落实材料
        List<Attach> attaches = projectMapper.queryAttactByProId(voteProInfoVo.getId() + "",BaseConstant.PRO_ATTACH_TYPE.PRO_SUPPLEMENT_IMPLEMENT.getCode());
        if (attaches != null && attaches.size() > 0) {
            voteProInfoVo.setFileList(attaches);
        }
        return voteProInfoVo;
    }

    /**
     * 设置项目交易结构
     * @param voteProInfoVo
     * @return
     */
    private VoteProInfoVo setVoteProInfoTradeStructure(VoteProInfoVo voteProInfoVo){
        String proId = voteProInfoVo.getId()+"";
        ProInfoTradeStructure proInfoTradeStructure = voteMeetMapper.queryProInfoTradeStructureByProId(proId);
        if(proInfoTradeStructure!=null){
            //设置违约事件发生前和发生后分配顺序的比较附件list
            proInfoTradeStructure.setBreachCompareFileList(projectMapper.queryAttactByProId(proId,BaseConstant.PRO_ATTACH_TYPE.BREACH_COMPARE_FILE_LIST.getCode()));
            //设置违约事件阈值及内容设定附件list
            proInfoTradeStructure.setBreachContentFileList(projectMapper.queryAttactByProId(proId,BaseConstant.PRO_ATTACH_TYPE.BREACH_CONTENT_FILE_LIST.getCode()));
            //设置预计收取的基金管理费附件list
            proInfoTradeStructure.setEstimatedManagementFeeFileList(projectMapper.queryAttactByProId(proId,BaseConstant.PRO_ATTACH_TYPE.ESTIMATED_MANAGEMENT_FEE_FILE_LIST.getCode()));
            //设置各方出资安排附件list
            proInfoTradeStructure.setFundArrangementsFileList(projectMapper.queryAttactByProId(proId,BaseConstant.PRO_ATTACH_TYPE.FUND_ARRANGEMENTS_FILE_LIST.getCode()));
            //设置投资利率附件list
            proInfoTradeStructure.setRateInvestmentFileList(projectMapper.queryAttactByProId(proId,BaseConstant.PRO_ATTACH_TYPE.RATE_INVESTMENT_FILE_LIST.getCode()));
            //设置ABS发行金额、分层占比、及分层评级结果附件list
            proInfoTradeStructure.setRatingResultFileList(projectMapper.queryAttactByProId(proId,BaseConstant.PRO_ATTACH_TYPE.RATING_RESULT_FILE_LIST.getCode()));
            //设置各方出资金额及占比附件list
            proInfoTradeStructure.setRatioAmountFileList(projectMapper.queryAttactByProId(proId,BaseConstant.PRO_ATTACH_TYPE.RATIO_AMOUNT_FILE_LIST.getCode()));
            //设置种子基金投资范围或产品附件list
            proInfoTradeStructure.setScopeInvestmentFileList(projectMapper.queryAttactByProId(proId,BaseConstant.PRO_ATTACH_TYPE.SCOPE_INVESTMENT_FILE_LIST.getCode()));
            //设置种子基金占比附件list
            proInfoTradeStructure.setSeedFundProportionFileList(projectMapper.queryAttactByProId(proId,BaseConstant.PRO_ATTACH_TYPE.SEED_FUND_PROPORTION_FILE_LIST.getCode()));
            //设置投资期限附件list
            proInfoTradeStructure.setTermInvestmentFileList(projectMapper.queryAttactByProId(proId,BaseConstant.PRO_ATTACH_TYPE.TERM_INVESTMENT_FILE_LIST.getCode()));
            //设置基金总金额附件list
            proInfoTradeStructure.setTotalFundAmountFileList(projectMapper.queryAttactByProId(proId,BaseConstant.PRO_ATTACH_TYPE.TOTAL_FUND_AMOUNT_FILE_LIST.getCode()));
            //设置底层资产投放标准附件list
            proInfoTradeStructure.setUnderlyingAssetsStandardFileList(projectMapper.queryAttactByProId(proId,BaseConstant.PRO_ATTACH_TYPE.UNDERLYING_ASSETS_STANDARD_FILE_LIST.getCode()));
            //设置基金风控和投资风险安排附件list
            proInfoTradeStructure.setRiskArrangementFileList(projectMapper.queryAttactByProId(proId,BaseConstant.PRO_ATTACH_TYPE.RISK_ARRANGEMENT_FILE_LIST.getCode()));
            //设置原始权益人是否履行回购义务(Y/N)附件list
            proInfoTradeStructure.setIsOriginalBuybackFileList(projectMapper.queryAttactByProId(proId,BaseConstant.PRO_ATTACH_TYPE.IS_ORIGINAL_BUYBACK_FILE_LIST.getCode()));
            //设置原始权益人出表与否(Y/N)附件list
            proInfoTradeStructure.setIsOriginalListFileList(projectMapper.queryAttactByProId(proId,BaseConstant.PRO_ATTACH_TYPE.IS_ORIGINAL_LIST_FILE_LIST.getCode()));
        }
        voteProInfoVo.setProInfoTradeStructure(proInfoTradeStructure);
        return voteProInfoVo;
    }
}
