/* Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.weshareholdings.approval.controller;

import com.weshareholdings.approval.config.BaseConstant;
import com.weshareholdings.approval.po.ProParamter;
import com.weshareholdings.approval.po.SurrogateInfo;
import com.weshareholdings.approval.po.User;
import com.weshareholdings.approval.repository.ProParamterMapper;
import com.weshareholdings.approval.repository.SurrogateMapper;
import com.weshareholdings.approval.repository.UserMapper;
import com.weshareholdings.approval.service.SurrogateService;
import com.weshareholdings.approval.util.DateUtil;
import com.weshareholdings.approval.util.RedisUtil;
import com.weshareholdings.approval.util.SnakerEngineFacets;
import com.weshareholdings.approval.util.ResultUtil;
import com.weshareholdings.approval.vo.Result;
import com.weshareholdings.approval.vo.SurrogateVo;
import org.snaker.engine.access.Page;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.entity.Surrogate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * 委托授权
 *
 * @author yuqs
 * @since 0.1
 */
@RestController
@RequestMapping(value = "/snaker/surrogate")
public class SurrogateController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SurrogateService surrogateService;
    @Autowired
    private ProParamterMapper proParamterMapper;
    @Autowired
    @Qualifier("redisUtil")
    private RedisUtil redisUtil;

    /**
     * 委托授权列表查询
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Result list(Page<SurrogateVo> page, HttpServletRequest request) {
        String operator = DateUtil.getRedisValue(request, redisUtil);
        // String operator = request.getRemoteUser();
            return ResultUtil.success("执行成功", surrogateService.querySurrogateList(page, operator));
    }

    /**
     * 新增委托代理
     *
     * @param surrogateInfo
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Result update(SurrogateInfo surrogateInfo) {
        if (surrogateInfo.getSurrogate() == null) {
            return ResultUtil.error("代理人信息不能为空！");
        }
        return surrogateService.addSurrogate(surrogateInfo);
    }

    /**
     * 编辑委托代理
     *
     * @param surrogateInfo
     * @return
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public Result edit(SurrogateInfo surrogateInfo) {
        if (surrogateInfo.getSurrogate() == null) {
            return ResultUtil.error("代理人信息不能为空！");
        }
        return surrogateService.editSurrogate(surrogateInfo);
    }

    /**
     * 删除委托代理
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "delete/{id}")
    public Result delete(@PathVariable("id") String id) {
        // facets.deleteSurrogate(id);
//        facets.updateSurrogateState(id);
        return surrogateService.deleteSurrogate(id);
    }

    /**
     * 查询可用代理人
     *
     * @param userName
     * @return
     */
    @RequestMapping(value = "surrogate")
    public Result querySurrogate(String userName, String fullName,String proType) {
        return ResultUtil.success("执行成功", userMapper.getUserLikeUserName(userName, fullName,proType));
    }

    /**
     * 查询可授权的流程
     * @return
     */
    @RequestMapping(value = "querySurrogateOrder")
    public Result querySurrogateOrder(HttpServletRequest request) {
        String operator = DateUtil.getRedisValue(request, redisUtil);
        return ResultUtil.success("执行成功", surrogateService.querySurrogateOrderList(operator));
    }

    /**
     * 查询权益人
     *
     * @return
     */
    @GetMapping(value = "originalRightsHolderList")
    public Result queryOriginalRightsHolderList(Page<String> page, String value) {
        // return ResultUtil.success("执行成功",surrogateService.queryProParamter(page, value));  //暂不分页
        return ResultUtil.success("执行成功", proParamterMapper.selectProParamter(BaseConstant.PARAMTYPE.ORIGINAL_RIGHTS_HOLDER.getCode(), value));
    }

    /**
     * 新增权益人
     *
     * @return
     */
    @RequestMapping(value = "addOriginalRightsHolder")
    public Result addOriginalRightsHolder(String value) {
        ProParamter proParamter = new ProParamter();
        proParamter.setType(BaseConstant.PARAMTYPE.ORIGINAL_RIGHTS_HOLDER.getCode());
        proParamter.setValue(value);
        proParamterMapper.insertSelective(proParamter);
        return ResultUtil.success("执行成功");
    }

}
