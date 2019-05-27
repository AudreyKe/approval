import axios from 'axios';
import Cookies from "js-cookie";
import {
  Modal
} from 'iview';
//字典管理
let dict = {
  CA: '/plugin/ca/query', //CA
  risk: '/plugin/model/query', //风控
  rules: '/queryDictionary/rule', //匹配规则
  roles: '/system/role/getRoleList', //角色
  product: '/asset/product/query', //产品编号
  rateFlow: '/source/query', //流量方编号
  strategy: '/strategy/standard/query', //策略编号
}
var errFunc = function (err) {
  loadingHide();
  setTimeout(function (){
    Modal.error({
      title: "提示",
      content: `调用服务异常，${err.data.code}`
    })
  }, 500);
}
var tokenErr = function () {
  loadingHide();
  Modal.error({
    title: "提示",
    content: `登录态已失效，请重新登录`,
    onOk: function () {
      document.location.href = '/login'
    }
  })
}
var JurErr = function () {
  loadingHide();
  Modal.error({
    title: "提示",
    content: `对不起!你没有该功能的操作权限!`,
    onOk: function () {
      document.location.href = '/home'
    }
  })
}

export default {
  //登录
  login(params) {
    return this.reqUrl('post', '/snaker/auth/login', params,true)
  },
  //登出
  logout() {
    return this.reqUrl('get', '/snaker/auth/logout')
  },
  //获取字典
  getDict(dictId) {
    return this.reqUrl('get', dict[dictId])
  },
  //查询待办任务列表
  getUpcomingList(params){
    return this.reqUrl('post','/snaker/task/activeTasks',params,true)
  },
  //查询待办任务信息
  getUpcomingDetail(params){
    return this.reqUrl('get',`/snaker/flow/order/${params.id}`,params,true)
  },
  //查询我发起的列表
  getInitiateList(params){
    return this.reqUrl('post','/snaker/flow/order/user',params,true)
  },
    //查询待提交项目
  getProDrafts(params){
    return this.reqUrl('post','/snaker/flow/getProDrafts',params,true)
  },
  //查询材料审核的列表
  getProsForFileApproval(params){
    return this.reqUrl('post','/snaker/secretary/getProsForFileApproval',params,true)
  },
  
   //查询投决会的列表
  queryVoteMeetList(params){
    return this.reqUrl('post','/snaker/voteMeet/queryVoteMeetList',params,true)
  },
    //查询审核提醒的列表
  getProsInApproval(params){
    return this.reqUrl('post','/snaker/secretary/getProsInApproval',params,true)
  },
   //查询项目的列表
  getProjectList(params){
    return this.reqUrl('post','/snaker/flow/orderList',params,true)
  },
  //查询消金项目的列表
  queryEliminatGoldList(params){
    return this.reqUrl('post','/snaker/eliminat/queryEliminatGoldList',params,true)
  },
  //查询数据使用申请列表
  queryDataUseApplyList(params){
    return this.reqUrl('post','/snaker/dataUseApply/queryDataUseApplyList',params,true)
  },
  //查询已办列表
  getAlreadyList(params){
    return this.reqUrl('post','/snaker/flow/order',params,true)
  },
  //查询授权列表
  getAuthList(params){
    return this.reqUrl('get',`/snaker/surrogate/list?pageNo=${params.pageNo}&pageSize=${params.pageSize}&processName=${params.processName}`)
  },
  //查询历史审批
  getHistoryAudit(params){
    return this.reqUrl('get',`/snaker/task/histTasks/${params.id}`)
  },
  //审批
  auditFund(params){
    return this.reqUrl('post','/snaker/flow/process',params,true)
  },
  //暂存
  savePro(params){
    return this.reqUrl('post','/snaker/flow/savePro',params)
  },
  //数据使用申请暂存
  addProDraft(params){
    return this.reqUrl('post','/snaker/dataUseApply/addProDraft',params)
  },
  //查询流程名称
  getProcessAll(){
    return this.reqUrl('get','/snaker/process/processNames')
  },
  //查询代理人
  getSurrogate(){
    return this.reqUrl('get',`/snaker/surrogate/surrogate`)
  },
  //设置授权
  addAuth(params){
    return this.reqUrl('post','/snaker/surrogate/add',params,true)
  },
  //编辑授权
  Edit(params){
    return this.reqUrl('post','/snaker/surrogate/edit',params,true)
  },
  //删除授权
  delAuth(params){
    return this.reqUrl('get',`/snaker/surrogate/delete/${params.id}`)
  },
  //立项
  addProduct(params){
    return this.reqUrl('post','/snaker/flow/enable',params)
  },
  //数据使用申请提交
  add(params){
    return this.reqUrl('post','/snaker/dataUseApply/add',params)
  },
    //附件列表提交
  addEnclosure(params){
    return this.reqUrl('post','/snaker/secretary/submitFile',params)
  },
  //资料审核附件列表提交
  submitFileApprovalResult(params){
    return this.reqUrl('post','/snaker/secretary/submitFileApprovalResult',params)
  },
   //审核提醒
  sendRemind(params){
    return this.reqUrl('post','/snaker/secretary/sendRemind',params)
  },
   //驳回以后的项目立项
  RejectaddProduct(params){
    return this.reqUrl('post','/snaker/flow/modify',params)
  },
  //上传附件
  uploadFile(params){
    return this.reqUrl('post','/snaker/flow/uploadFile',params)
  },
  //评审列表查询
  reviewList(params){
    return this.reqUrl('get',`/snaker/flow/reviewList?pageNo=${params.pageNo}&pageSize=${params.pageSize}`)
  },
  //发起评审
  upReview(params){
    return this.reqUrl('post',`/snaker/flow/review`,params)
  },
  //评审查询
  getReviewDetail(params){
    return this.reqUrl('get',`/snaker/flow/review/${params.orderId}`)
  },
  /**
   * 
   * @param {*} type 请求类型 
   * @param {*} url  请求路径
   * @param {*} params 请求参数
   * @param {*} isForm 请求参数是否为formdata形式
   */
  reqUrl(type, url, params, isForm) {
    // if (url.indexOf("?") > -1) {
    //   url = `${url}&accesstoken=${localStorage.token}`;
    // } else {
    //   url = `${url}?accesstoken=${localStorage.token}`;
    // }
    return new Promise((resolve, reject) => {
      if (type == 'get') {
        axios.get(baseURL + url).then(res => {
          if (res.data.code == "400") {
            tokenErr();
          }else if(res.data.code == "10014"){
			  JurErr();
		  }else {
            resolve(res.data);
          }
        }).catch(err => {
          errFunc(err);
          reject('调用服务异常');
        })
      } else {
        if (isForm) {
          axios({
              url: baseURL + url,
              method: "post",
              data: params,
              transformRequest: [
                function (data) {
                  let ret = "";
                  for (let it in data) {
                    ret +=
                      encodeURIComponent(it) +
                      "=" +
                      encodeURIComponent(data[it]) +
                      "&";
                  }
                  return ret;
                }
              ],
              headers: {
                "Content-Type": "application/x-www-form-urlencoded"
              }
            })
            .then(res => {
              if (res.data.code == "400") {
                tokenErr();
              } else if(res.data.code == "10014"){
				  JurErr();
			  }else{
                resolve(res.data);
              }
            })
            .catch(err => {
              errFunc(err);
              reject('调用服务异常');
            });
        } else {
          axios.post(baseURL + url, params).then(res => {
            if (res.data.code == "400") {
              tokenErr();
            }else if(res.data.code == "10014"){
				JurErr();
			}else {
              resolve(res.data);
            }
          }).catch(err => {
            errFunc(err);
            reject('调用服务异常');
          })
        }
      }
    })
  }
}
