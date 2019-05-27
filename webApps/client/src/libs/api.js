import axios from 'axios';
import { Toast } from 'mint-ui';
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
  setTimeout(function () {
    Toast({
      message: `调用服务异常，${err}`,
      position: 'top',
      duration: 2000
    })
  }, 500);
}
var tokenErr = function () {
  Toast({
    message: `登录态已失效，请重新登录`,
    position: 'top',
    duration: 2000
  })
  setTimeout(function () {
    document.location.href = '/login'
  }, 2000);
}
export default {
  //登录
  login(params) {
    return this.reqUrl('post', '/snaker/auth/login', params, true)
  },
  //登出
  logout(params) {
    return this.reqUrl('get', 'otherRouter', params)
  },
  //获取字典
  getDict(dictId) {
    return this.reqUrl('get', dict[dictId])
  },
  //查询待办任务列表
  getUpcomingList(params) {
    return this.reqUrl('post', '/snaker/task/activeTasks', params, true)
  },
  //查询项目列表
  SearchList(params) {
    return this.reqUrl('post', '/snaker/flow/orderList', params, true)
  },
   queryAllPro(params) {
    return this.reqUrl('post', '/snaker/flow/order/queryAllPro', params, true)
  },
  //查询待办任务信息
  getUpcomingDetail(params) {
    return this.reqUrl('get', `/snaker/flow/order/${params.id}`, params, true)
  },
    proDetail(params) {
    return this.reqUrl('get', `/snaker/eliminat/proDetail/${params.id}`, params, true)
  },
   proDetailList(params) {
    return this.reqUrl('get', `/snaker/voteMeet/proDetail/${params.id}`, params, true)
  },
  proDateList(params) {
    return this.reqUrl('get', `/snaker/dataUseApply/proDetail/${params.id}`, params, true)
  },
  //查询我发起的列表
  getInitiateList(params) {
    return this.reqUrl('post', '/snaker/flow/order/user', params, true)
  },
  //查询已办列表
  getAlreadyList(params) {
    return this.reqUrl('post', '/snaker/flow/order', params, true)
  },
  //查询已办详情
  getAlreadyDetail(params) {
      return this.reqUrl('get', `/snaker/flow/order/${params.id}`, params, true)
  },
  //查询授权列表
  getAuthList(params) {
    return this.reqUrl('get', `/snaker/surrogate/list?pageNo=${params.pageNo}&pageSize=${params.pageSize}&processName=${params.processName}`)
  },
  //查询历史审批
  getHistoryAudit(params) {
    return this.reqUrl('get', `/snaker/task/histTasks/${params.id}`)
  },
  //查询流程名称
  getProcessAll() {
    return this.reqUrl('get', '/snaker/process/processNames')
  },
  //查询代理人
  getSurrogate() {
    return this.reqUrl('get', `/snaker/surrogate/surrogate`)
  },
  //执行审批
  process(params) {
    return this.reqUrl('post', `/snaker/flow/process`, params, true)
  },
  auditFund(params){
    return this.reqUrl('post','/snaker/flow/process',params,true)
  },
  //新建委托授权
  addSurrogate(params) {
    return this.reqUrl('post', `/snaker/surrogate/create`, params, true)
  },
  //设置授权
  addAuth(params) {
    return this.reqUrl('post', '/snaker/surrogate/add', params, true)
  },
  //删除授权
  delAuth(params) {
    return this.reqUrl('get', `/snaker/surrogate/delete/${params.id}`)
  },
  //立项
  addProduct(params) {
    return this.reqUrl('post', '/snaker/flow/enable', params)
  },
  //上传附件
  uploadFile(params) {
    return this.reqUrl('post', '/snaker/flow/uploadFile', params)
  },
  
    dataUseApply(params) {
    return this.reqUrl('get', `/snaker/dataUseApply/proDetail/${params.orderId}`)
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
          } else if (res.data.code == "500") {
            errFunc(res.data.message);
          } else {
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
              } else if (res.data.code == "500") {
                errFunc(res.data.message);
              } else {
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
            } else if (res.data.code == "500") {
              errFunc(res.data.message);
            } else {
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
