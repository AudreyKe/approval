// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
// import Vue from 'vue'
import Vue from 'vue'

import 'babel-polyfill'
import store from './store'
import App from './App'
import router from './router/index'
import util from './lib/util'
import './lib/rem'
import iView from 'iview'
import 'iview/dist/styles/iview.css'
import './assets/fonts/iconfont.css'
import './theme/index.less'
import api from './lib/api'
import 'jquery'
//进入页面 让页面默认在顶部
router.afterEach((to,from,next) => {
  window.scrollTo(0,0);
})

Vue.use(iView);
Vue.prototype.$modal = iView.modal;
Vue.prototype.api = api;
Vue.prototype.util = util;

window.baseURL = "/api";
window.loading = function (msg = '处理中,请稍后...') {
  iView.Spin.show({
    render: h => {
      return h("div", [
        h("Icon", {
          class: "spin-icon-load",
          props: {
            type: "ios-loading",
            size: 18
          }
        }),
        h("div", msg)
      ]);
    }
  });
}
window.loadingHide = function () {
  iView.Spin.hide();
}
window.serviceError = function (msg = '调用服务异常') {
  iView.Modal.error({
    title: "提示",
    content: msg
  });
}
window.successNotice = function (msg) {
  iView.Notice.success({
    title: msg
  });
}



/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App),
})
