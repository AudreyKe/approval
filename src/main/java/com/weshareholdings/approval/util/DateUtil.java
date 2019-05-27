package com.weshareholdings.approval.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.weshareholdings.approval.config.BaseConstant;
import com.weshareholdings.approval.po.SurrogateInfo;
import org.snaker.engine.helper.StringHelper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DateUtil {


    /**
     * 根据校验表达式验证传入参数并转换成Map给出
     *
     * @param param      参数
     * @param expression 校验表达式不能为空
     * @return
     */
    @SuppressWarnings({"serial", "rawtypes"})
    public static Map<String, String> matchParam(JSONObject param, String[] expression) {
        Map<String, String> result = new HashMap<String, String>() {{//定义一个返回结果,并初始返回成功
            put("status", "S");
            put("errorMessage", "");
        }};
        if (expression == null || param == null) {//如果校验参数或者校验表达式为空就直接返回错误
            result.put("status", "E");
            result.put("errorMessage", "校验表达式或校验参数不能为空！");
            return result;
        } else {
            for (int i = 0, length = expression.length; i < length; i++) {
                String str = expression[i];
                String[] strs = str.split("-");
                if (strs.length < 3) {
                    result.put("status", "E");
                    result.put("errorMessage", "校验表达式格式错误！");
                    return result;
                }
                if (strs[1] != null && "1".equals(strs[1])) {//如果传入规则为1就校验字段是否为空
                    if (param.get(strs[0]) == null || "".equals(param.get(strs[0])) || "null".equals(param.get(strs[0]).toString())) {
                        result.put("status", "E");
                        result.put("errorMessage", "" + (strs[2] == null ? "" : strs[2]));
                        return result;
                    }
                } else if (strs[1] != null && "2".equals(strs[1])) {//如果传入规则为2就校验字段是否为空并且校验字段是否小于表达式中的值
                    Integer mathLength = Integer.valueOf(strs[3]);
                    if ("null".equals(param.get(strs[0]).toString()) || param.get(strs[0]) == null || "".equals(param.get(strs[0])) || Integer.valueOf((String) param.get(strs[0])) < mathLength) {
                        result.put("status", "E");
                        result.put("errorMessage", "" + (strs[2] == null ? "" : strs[2]));
                        return result;
                    }
                } else if (strs[1] != null && "3".equals(strs[1])) {//如果传入规则为3就校验的是List类型里面的字段
                    List details = (List) param.get(strs[0]);
                    if (details != null && details.size() > 0) {
                        for (int j = 0, lenj = details.size(); j < lenj; j++) {
                            Map rm = matchParam(new JSONObject((Map) details.get(j)), getExprission(strs[2]));
                            if ("E".equals(rm.get("status"))) {
                                result.put("status", "E");
                                result.put("errorMessage", "" + (rm.get("errorMessage") == null ? "" : rm.get("errorMessage")));
                                return result;
                            }

                        }
                    }
                } else if (strs[1] != null && "4".equals(strs[1])) {//如果传入规则为4就校验字段是否为空并且校验字段长度是否小于表达式中的值
                    Integer mathLength = Integer.valueOf(strs[3]);
                    if ("null".equals(param.get(strs[0]).toString()) || param.get(strs[0]) == null || "".equals(param.get(strs[0])) || param.get(strs[0]).toString().length() > mathLength) {
                        result.put("status", "E");
                        result.put("errorMessage", "" + (strs[2] == null ? "" : strs[2]));
                        return result;
                    }
                } else if (strs[1] != null && "5".equals(strs[1])) {//如果传入规则为5就校验字段是否为指定值
                    boolean flag = true;
                    int index = 3;
                    while (index <= strs.length - 1) {
                        if (strs[index] != null && strs[index].equals(param.get(strs[0]))) {
                            flag = false;
                        }
                        index++;
                    }
                    if ("null".equals(param.get(strs[0]).toString()) || param.get(strs[0]) == null || "".equals(param.get(strs[0])) || flag) {
                        result.put("status", "E");
                        result.put("errorMessage", "" + (strs[2] == null ? "" : strs[2]));
                        return result;
                    }
                } else if (strs[1] != null && "6".equals(strs[1])) {//如果传入规则为6就校验字段是否为空并返回指定值
                    if (param.get(strs[0]) == null || "".equals(param.get(strs[0]))) {
                        result.put("status", "E");
                        result.put("errorMessage", "" + (strs[3] == null ? "" : strs[2]));
                        result.put("resultCode", "" + (strs[3] == null ? "" : strs[3]));
                        return result;
                    }
                }else if (strs[1] != null && "7".equals(strs[1])) {//如果传入规则为6就校验字段是否为空并返回指定值
                    String[] keys = strs[0].split(",");
                    if(keys.length==2){
                        if(param.get(keys[0])==null&&param.get(keys[1])==null){
                            result.put("status", "E");
                            result.put("errorMessage", "" + (strs[2] == null ? "" : strs[2]));
                            return result;
                        }
                    }else{
                        result.put("status", "E");
                        result.put("errorMessage", "校验表达式错误！" );
                        return result;
                    }
                } else if (strs[1] != null && "8".equals(strs[1])) {//规则8：投决项目新增字段校验
                    if ((param.get(strs[0]) == null || "".equals(param.get(strs[0])))&&!"4".equals(param.get("investmentTarget"))) {
                        result.put("status", "E");
                        result.put("errorMessage", "" + (strs[2] == null ? "" : strs[2]));
                        return result;
                    }else if(param.get(strs[0]) != null && !"".equals(param.get(strs[0]))){
                        JSONObject tradeObj = JSONObject.parseObject(JSON.toJSONString(param.get(strs[0])));
                        if("1".equals(param.get("investmentTarget"))){
                            return matchParam(tradeObj,getExprission("TRADE_ONE"));
                        }else if("2".equals(param.get("investmentTarget"))){
                            return matchParam(tradeObj,getExprission("TRADE_TWO"));
                        }else if("3".equals(param.get("investmentTarget"))){
                            return matchParam(tradeObj,getExprission("TRADE_THREE"));
                        }
                    }
                }
            }
        }
        return result;
    }


    /**
     * 根据传入字符获取指定参数校验表达式
     */
    public static final String[] getExprission(String str) {
        final String[] layerListExpression = {
                "level-1-分级不能为空！",
                "amount-1-发行金额（万元）不能为空！",
                "proportion-1-占比不能为空！",
                "issueRate-1-发行利率不能为空！",
                "proTerm-1-产品期限（月）不能为空！",
                "averageTerm-1-加权平均期限不能为空！"
        };

        final String[] fileListExpression = {
                "id-1-文件id不能为空！"
                , "fileName-1-文件名不能为空！"
                , "filePath-1-文件路径不能为空！"
                , "realFileName-1-真实文件名不能为空！"
                , "uploadPersonName-1-上传者姓名不能为空！"
                , "uploadPersonId-1-上传者id不能为空！"
                , "proAttachType-1-项目附件类型不能为空！"
        };

        final String[] proTradeStructureOneExpression = {
                "underlyingAssetsType-1-底层资产类型不能为空！"
                , "underlyingAssetsStandard,underlyingAssetsStandardFileList-7-底层资产投放标准或底层资产投放标准附件不能同时为空！"
                , "ratioAmount,ratioAmountFileList-7-各方出资金额及占比或各方出资金额及占比附件不能同时为空！"
                , "fundArrangements,fundArrangementsFileList-7-各方出资安排或各方出资安排附件不能同时为空！"
                , "termInvestment,termInvestmentFileList-7-投资期限或投资期限附件不能同时为空！"
                , "rateInvestment,rateInvestmentFileList-7-投资利率或投资利率附件不能同时为空！"
        };

        final String[] proTradeStructureTwoExpression = {
                "ratingResult,ratingResultFileList-7-ABS发行金额、分层占比、及分层评级结果或ABS发行金额、分层占比、及分层评级结果附件不能同时为空！"
                , "isOriginalList,isOriginalListFileList-7-原始权益人出表与否或原始权益人出表与否附件不能同时为空！"
                , "isOriginalBuyback,isOriginalBuybackFileList-7-原始权益人是否履行回购义务或原始权益人是否履行回购义务附件不能同时为空！"
                , "breachContent,breachContentFileList-7-违约事件阈值及内容设定或违约事件阈值及内容设定附件不能同时为空！"
                , "breachCompare,breachCompareFileList-7-违约事件发生前和发生后分配顺序的比较或违约事件发生前和发生后分配顺序的比较附件不能同时为空！"
                , "termInvestment,termInvestmentFileList-7-投资期限或投资期限附件不能同时为空！"
                , "rateInvestment,rateInvestmentFileList-7-投资利率或投资利率附件不能同时为空！"
        };

        final String[] proTradeStructureThreeExpression = {
                "totalFundAmount,totalFundAmountFileList-7-基金总金额或基金总金额附件不能同时为空！"
                , "seedFundProportion,seedFundProportionFileList-7-种子基金占比或种子基金占比附件不能同时为空！"
                , "estimatedManagementFee,estimatedManagementFeeFileList-7-预计收取的基金管理费或预计收取的基金管理费附件不能同时为空！"
                , "scopeInvestment,scopeInvestmentFileList-7-种子基金投资范围或产品或种子基金投资范围或产品附件不能同时为空！"
                , "riskArrangement,riskArrangementFileList-7-基金风控和投资风险安排或基金风控和投资风险安排附件不能同时为空！"
        };

        final String[]teamListExpression = {
                "userId-1-项目团队成员id不能为空！"
        };

        final String[] proModelListExpression = {
                "name-1-业务模式名称不能为空！"
        };
        if ("LAYER_LIST_EXPRESSION".equals(str)) {
            return layerListExpression;
        } else if ("FILE_LIST_EXPRESSION".equals(str)) {
            return fileListExpression;
        }else if ("TEAM_LIST_EXPRESSION".equals(str)) {
            return teamListExpression;
        }else if ("PROMODEL_LIST_EXPRESSION".equals(str)) {
            return proModelListExpression;
        }else if ("TRADE_ONE".equals(str)) {
            return proTradeStructureOneExpression;
        }else if ("TRADE_TWO".equals(str)) {
            return proTradeStructureTwoExpression;
        }else if ("TRADE_THREE".equals(str)) {
            return proTradeStructureThreeExpression;
        }
        return null;
    }

    ;

    /**
     * 根据转换表达式把JSon对象转换成指定Map
     *
     * @param param   参数
     * @param mapKeys 转换表达式
     * @return
     */
    public static Map<String, String> jsonToAppointMap(JSONObject param, String[] mapKeys) {
        Map<String, String> result = new HashMap<String, String>();//定义一个返回结果,并初始返回成功
        result.put("status", "S");
        result.put("errorMessage", "");
        if (mapKeys == null || param == null) {//如果校验参数或者校验表达式为空就直接返回错误
            result.put("status", "E");
            result.put("errorMessage", "转换表达式或校验参数不能为空！");
            return result;
        } else {
            for (int i = 0, length = mapKeys.length; i < length; i++) {
                String str = mapKeys[i];
                String[] strs = str.split("-");
                if (strs.length < 2) {
                    result.put("status", "E");
                    result.put("errorMessage", "转换表达式格式错误！");
                    return result;
                } else {
                    String val = "";
                    if (param.get(strs[0]) != null) {
                        val = param.get(strs[0]).toString();
                        if ("null".equals(val)) {
                            val = null;
                        }
                    }
                    result.put(strs[1], val);
                }
            }
        }
        return result;
    }

    public static String getCookieByKey(HttpServletRequest request, String key) {
        String userInfo = null;
        if (request != null) {
            Cookie[] cookies = request.getCookies();
            if (cookies == null || cookies.length <= 0) {
                return userInfo;
            }
            for (int i = 0, len = cookies.length; i < len; i++) {
                if (key.equals(cookies[i].getName())) {
                    userInfo = cookies[i].getValue();
                }
            }
        }
        return userInfo;
    }

    public static String getRedisValue(HttpServletRequest request, RedisUtil redisUtil) {
        String[] userInfo = getRedisUser( request,redisUtil);
        if(userInfo!=null&&userInfo.length>0){
            return userInfo[0];
        }
        return null;
    }

    public static String[] getRedisUser(HttpServletRequest request, RedisUtil redisUtil){
        String[] userInfo = null;
        if (request != null) {
            String sessionId = DateUtil.getCookieByKey(request, "sessionId");
            if (sessionId == null) {
                sessionId = DateUtil.getCookieByKey(request, "JSESSIONID");
            }
            if (sessionId == null || "".equals(sessionId)) {
                return userInfo;
            }
            String strs = redisUtil.get(sessionId);
            if (strs != null) {
                userInfo = strs.split("-");
            }
        }
        return userInfo;
    }

    public static SurrogateInfo setSurrogateInfo(SurrogateInfo surrogateInfo, String batchId, String type, String orderId){
        String surrogateId = StringHelper.getPrimaryKey();
        surrogateInfo.setOrderId(orderId);//设置流程id
        surrogateInfo.setBatchId(batchId);//设置批次id
        surrogateInfo.setId(surrogateId);//设置surrogateId
        surrogateInfo.setType(type);//设置授权操作类型为0：新增
        surrogateInfo.setSurrogateId(surrogateId);//设置授权id
        return surrogateInfo;
    }


//	public static String getRedisValueColony(HttpServletRequest request,XyyRedisTemplate xyyRedisTemplate){
//		String userInfo = null;
//		if(request!=null){
//			String sessionId = DateUtil.getCookieByKey(request,"sessionId");
//			if(sessionId==null||"".equals(sessionId)){
//				return userInfo;
//			}
//			String strs = xyyRedisTemplate.get(sessionId);
//			if(strs!=null){
//				userInfo = strs.split(",")[0];
//			}
//		}
//		return userInfo;
//	}

    /**
     * 判断当前节点驳回的时候是否需要回到初始节点
     * @param processCodes 驳回之后需要回到初始节点的节点code列表
     * @param taskName 当前节点的code
     * @return
     */
    public static boolean getRebackProcess(String[] processCodes,String taskName){
       for(String processCode:processCodes){
           if(processCode.equals(taskName)){
               return true;
           }
       }
       return false;
    }
}
