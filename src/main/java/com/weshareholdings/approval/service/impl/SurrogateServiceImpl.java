package com.weshareholdings.approval.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weshareholdings.approval.config.BaseConstant;
import com.weshareholdings.approval.po.SurrogateInfo;
import com.weshareholdings.approval.po.User;
import com.weshareholdings.approval.repository.ProParamterMapper;
import com.weshareholdings.approval.repository.SurrogateMapper;
import com.weshareholdings.approval.repository.UserMapper;
import com.weshareholdings.approval.service.SurrogateService;
import com.weshareholdings.approval.util.DateUtil;
import com.weshareholdings.approval.util.ResultUtil;
import com.weshareholdings.approval.vo.OrderSurrogate;
import com.weshareholdings.approval.vo.Result;
import com.weshareholdings.approval.vo.SurrogateVo;
import com.weshareholdings.approval.vo.TaskActorVo;
import org.snaker.engine.access.Page;
import org.snaker.engine.helper.StringHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author liling
 * @Date 2018/9/10
 **/
@Service
public class SurrogateServiceImpl implements SurrogateService {

    @Autowired
    private SurrogateMapper surrogateMapper;
    @Autowired
    private ProParamterMapper proParamterMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Page<SurrogateVo> querySurrogateList(Page<SurrogateVo> page, String operator) {
//        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        PageHelper.startPage(page.getPageNo(), page.getPageSize(),"ws.create_time desc");
        List<SurrogateVo> list = surrogateMapper.querySurrogateList(operator);
        PageInfo<SurrogateVo> pageInfo = new PageInfo<>(list);
        page.setResult(list);
        page.setTotalCount(pageInfo.getTotal());
        return page;
    }

    @Override
    public Page<String> queryProParamter(Page<String> page, String value) {
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        List<String> list = proParamterMapper.selectProParamter(BaseConstant.PARAMTYPE.ORIGINAL_RIGHTS_HOLDER.getCode(), value);
        PageInfo<String> pageInfo = new PageInfo<>(list);
        page.setResult(list);
        page.setTotalCount(pageInfo.getTotal());
        return page;
    }

    @Override
    public Result addSurrogate(SurrogateInfo surrogateInfo) {
        List<User> list = userMapper.getUserByUserName(surrogateInfo.getSurrogate());
        if (list == null || list.size() == 0) {
            return ResultUtil.error("代理人信息不存在！");
        }
        String batchId = StringHelper.getPrimaryKey();
        addSurrogateInfo(surrogateInfo,batchId,BaseConstant.SURROGATE_OPT_TYPE.ADD.getCode());
        return ResultUtil.success("执行成功");
    }

    public void addSurrogateInfo(SurrogateInfo surrogateInfo,String batchId,String type){
        List<String> orders = surrogateMapper.queryAllSurrogateByProcessId(surrogateInfo.getOperator(),surrogateInfo.getProcessId());
        List<SurrogateInfo> surrogateInfos = new ArrayList<SurrogateInfo>();
        for(String str:orders){
            SurrogateInfo surrogateInfo1 = new SurrogateInfo();
            BeanUtils.copyProperties(surrogateInfo, surrogateInfo1);
            surrogateInfo1 = DateUtil.setSurrogateInfo(surrogateInfo1,batchId,type,str);
            surrogateInfos.add(surrogateInfo1);
        }
        if(surrogateInfos==null||surrogateInfos.size()==0){
            surrogateInfo = DateUtil.setSurrogateInfo(surrogateInfo,batchId,type,null);
            surrogateInfos.add(surrogateInfo);
        }
        //新增授权
        surrogateMapper.batchInsertSurrogate(surrogateInfos);
        surrogateMapper.batchInsertSurrogateHis(surrogateInfos);//新增授权操作历史纪录
    }



    @Override
    public Result editSurrogate(SurrogateInfo surrogateInfo) {
        List<User> list = userMapper.getUserByUserName(surrogateInfo.getSurrogate());
        if (list == null || list.size() == 0) {
            return ResultUtil.error("代理人信息不存在！");
        }
        if(surrogateInfo.getBatchId()!=null&&surrogateInfo.getProcessId()!=null){
            String processId = surrogateMapper.queryProcessIdByBatchId(surrogateInfo.getBatchId());
            if(surrogateInfo.getProcessId().equals(processId)){//判断有没有修改项目授权类型
                //修改授权
                surrogateMapper.updateSurrogate(surrogateInfo);
                surrogateInfo.setSurrogateId(surrogateInfo.getId());//设置授权id
                List<SurrogateInfo> surrogateInfos= surrogateMapper.querySurrogateListByBatchId(surrogateInfo.getBatchId(),BaseConstant.SURROGATE_OPT_TYPE.UPDATE.getCode());
                surrogateMapper.batchInsertSurrogateHis(surrogateInfos);
                //删除原有的参与者记录
                deleteTaskActor(surrogateInfos);
            }else{//如果有修改
                deleteSurrogate(surrogateInfo.getBatchId(),BaseConstant.SURROGATE_OPT_TYPE.UPDATE_DELETE.getCode());
                addSurrogateInfo(surrogateInfo,surrogateInfo.getBatchId(),BaseConstant.SURROGATE_OPT_TYPE.UPDATE_ADD.getCode());
            }
        }
        return ResultUtil.success("执行成功");
    }

    /**
     * 删除原有的参与者记录
     * @param surrogateInfos
     */
    private void deleteTaskActor(List<SurrogateInfo> surrogateInfos){
//        //根据id查询修改前的授权
//        List<SurrogateInfo> surrogateInfos = surrogateMapper.querySurrogateByBatchId(surrogateInfo.getBatchId());
        for(SurrogateInfo surrogateInfo1:surrogateInfos){
            //根据用户id和流程id查询任务参与者记录(授权)
            List<TaskActorVo> taskActorSurrogates = surrogateMapper.queryTaskActorByTaskIdAndUserName(surrogateInfo1.getSurrogate(),surrogateInfo1.getOrderId(),"01",null,surrogateInfo1.getProcessId(),surrogateInfo1.getBatchId());
            for(TaskActorVo taskActor:taskActorSurrogates){//删除原有的参与者记录
                surrogateMapper.deleteTaskActor(taskActor.getId());
            }
        }
    }

    @Override
    public Result deleteSurrogate(String id) {
        if(id!=null){
            deleteSurrogate(id,BaseConstant.SURROGATE_OPT_TYPE.DELETE.getCode());
        }else{
            return ResultUtil.error("授权id不能为空！");
        }
        return ResultUtil.success("执行成功");
    }

    /**
     * 删除授权操作
     * @param batchId 批次id
     * @param type 删除类型(1修改删除、2删除)
     */
    private void deleteSurrogate(String batchId,String type){
        //根据批次id查询授权详情
        List<SurrogateInfo> surrogateInfos = surrogateMapper.querySurrogateByBatchId(batchId);
        if(surrogateInfos!=null&&surrogateInfos.size()>0){
            for(SurrogateInfo surrogateInfo:surrogateInfos){
                surrogateInfo.setType(type);//设置授权操作类型为0：删除
                surrogateInfo.setSurrogateId(surrogateInfo.getId());//设置授权id
            }
            surrogateMapper.batchInsertSurrogateHis(surrogateInfos);//新增授权操作历史纪录
            //删除原有的参与者记录
            deleteTaskActor(surrogateInfos);
        }
        //修改授权为删除
        surrogateMapper.deleteSurrogate(batchId);
    }

    @Override
    public List<OrderSurrogate> querySurrogateOrderList(String operator) {
        return surrogateMapper.querySurrogateOrderList(operator);
    }
}
