import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)

const login = resolve => require(['@/pages/login.vue'], resolve)
const todoList = resolve => require(['@/pages/todo/list.vue'], resolve)
const todoDetail = resolve => require(['@/pages/todo/detail.vue'], resolve)
const todomoney = resolve => require(['@/pages/todo/money.vue'], resolve)
const tododataApplication = resolve => require(['@/pages/todo/dataApplication.vue'], resolve)
const todoCast = resolve => require(['@/pages/todo/Cast.vue'], resolve)
const doneList = resolve => require(['@/pages/done/list.vue'], resolve)
const doneDetail = resolve => require(['@/pages/done/detail.vue'], resolve)
const donemoney = resolve => require(['@/pages/done/money.vue'], resolve)
const donedataApplication = resolve => require(['@/pages/done/dataApplication.vue'], resolve)
const doneCast = resolve => require(['@/pages/done/Cast.vue'], resolve)
const detList = resolve => require(['@/pages/detailsList/list.vue'], resolve)
const detDetail = resolve => require(['@/pages/detailsList/detail.vue'], resolve)
const detmoney = resolve => require(['@/pages/detailsList/money.vue'], resolve)
const detdataApplication = resolve => require(['@/pages/detailsList/dataApplication.vue'], resolve)
const detCast = resolve => require(['@/pages/detailsList/Cast.vue'], resolve)
const authorizeList = resolve => require(['@/pages/authorize/list.vue'], resolve)
const addTask = resolve => require(['@/pages/authorize/addTask.vue'], resolve)
const wechat = resolve => require(['@/pages/wechat.vue'], resolve)

export default new Router({
  mode: 'history',
  routes: [
    // {
    //   path: '/',
    //   redirect:'/login'
    // },
    {
      path:'/wechat',
      component:wechat
    },
    {
      mode: 'history',
      path:'/login',
      component:login
    },
    {
      path:'/todoList',
      component:todoList
    },
    {
      path:'/todoDetail',
      component:todoDetail
    },
    {
      path:'/todomoney',
      component:todomoney
    },
    {
      path:'/tododataApplication',
      component:tododataApplication
    },
    {
      path:'/todoCast',
      component:todoCast
    },
    {
      path:'/doneList',
      component:doneList
    },
    {
      path:'/doneDetail',
      component:doneDetail
    },
    {
      path:'/donemoney',
      component:donemoney
    },
    {
      path:'/donedataApplication',
      component:donedataApplication
    },
    {
      path:'/doneCast',
      component:doneCast
    },
    {
      path:'/detList',
      component:detList
    },
    {
      path:'/detDetail',
      component:detDetail
    },
    {
      path:'/detmoney',
      component:detmoney
    },
    {
      path:'/detdataApplication',
      component:detdataApplication
    },
    {
      path:'/detCast',
      component:detCast
    },
    {
      path:'/authorizeList',
      component:authorizeList
    },
    {
      path:'/addTask',
      component:addTask
    },
    {
      path:'/wechat',
      component:wechat
    },
    {
      path:'/',
      redirect:'/login'
    },
  ]
})
