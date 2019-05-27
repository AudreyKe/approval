import 'normalize.css'
import Vue from 'vue';
import App from './App.vue';
import router from './router'
import api from './libs/api';
import './libs/rem';
import './assets/fonts/iconfont.css';
import util from './libs/util'
import MintUI from 'mint-ui'
import 'mint-ui/lib/style.css'
import moment from 'moment'
import Scroll from '@/components/Scroll.vue'

Vue.use(MintUI)
Vue.prototype.$api = api;
Vue.prototype.$util = util;
Object.defineProperty(Vue.prototype, '$moment', { value: moment })
Vue.component('scroll',Scroll)

// window.baseURL = '/api'


var u = navigator.userAgent, app = navigator.appVersion;
if(u.indexOf('Android') > -1 || u.indexOf('Linux') > -1){
  window.navType = 'android';
}else if(!!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/)){
  window.navType = 'ios';
}
window.baseURL = (window.location.port == '8081' ? '/api' : '');
new Vue({
  el: '#app',
  router: router,
  render: h => h(App)
});
