import Main from '@/components/Main.vue';

//企业微信授权页面单独写
export const enterprisewechat = {
  path:"/wechat",
  name:"wechat",
  component: () =>
  import ('@/components/wechat.vue')
}

// 不作为Main组件的子页面展示的页面单独写，如下
export const loginRouter = {
  path: '/login',
  name: 'login',
  meta: {
    title: 'Login - 登录'
  },
  component: () =>
    import ('@/components/login.vue')
};

// 作为Main组件的子页面展示但是不在左侧菜单显示的路由写在otherRouter里
export const otherRouter = {
  path: '/',
  name: 'otherRouter',
  redirect: '/home',
  component: Main,
  meta:{requireAuth:true},
  children: [{
    path: 'home',
    title: {
      i18n: '主页'
    },
    name: 'home_index',
    component: () =>
      import ('@/components/home/home.vue')
  }]
};
// 作为Main组件的子页面展示并且在左侧菜单显示的路由写在appRouter里
const appRouter = [{
  path: '/task',
  name: 'task',
  title: '',
  component: Main,
  children: [{
    path: 'upcoming-list',
    title: '待办审批',
    name: 'upcoming-list',
    component: () =>
      import ('@/components/my-tasks/upcoming-list.vue')
  }, {
    path: 'already-list',
    title: '已办审批',
    name: 'already-list',
    component: () =>
      import ('@/components/my-tasks/already-list.vue')
  },{
    path: 'initiate-list',
    title: '我发起的',
    name: 'initiate-list',
    component: () =>
      import ('@/components/my-tasks/initiate-list.vue')
  },{
    path: 'submitted',
    title: '待提交项目',
    name: 'submitted',
    component: () =>
      import ('@/components/my-tasks/submitted.vue')
  },{
    path: 'data-upload',
    title: '资料上传',
    name: 'data-upload',
    component: () =>
      import ('@/components/my-tasks/data-upload.vue')
  },{
    path: 'commission-auth-list',
    title: '委托授权',
    name: 'commission-auth-list',
    component: () =>
      import ('@/components/my-tasks/commission-auth-list.vue')
  },{
    path: 'audit-task',
    title: '审核',
    name: 'audit-task',
    component: () =>
      import ('@/components/my-tasks/audit-task.vue')
  },
  {
    path: 'View-info',
    title: '审核',
    name: 'View-info',
    component: () =>
      import ('@/components/my-tasks/View-info.vue')
  }]
},{
  path: '/initiate',
  name: 'initiate',
  title: '',
  component: Main,
  children: [{
    path: 'add-product',
    title: '项目立项',
    name: 'add-product',
    component: () =>
      import ('@/components/initiate/add-product.vue')
  },{
    path: 'product-review',
    title: '项目评审',
    name: 'product-review',
    component: () =>
      import ('@/components/initiate/product-review.vue')
  },{
    path: 'review-info',
    title: '审核',
    name: 'review-info',
    component: () =>
      import ('@/components/common/review-info.vue')
    
  },{
    path: 'Investment',
    title: '投资业务',
    name: 'Investment',
    component: () =>
      import ('@/components/common/Investment.vue')
    
  },{
    path: 'Useproject',
    title: '项目立项',
    name: 'Useproject',
    component: Main,
    component: () =>
      import ('@/components/my-tasks/Useproject.vue')
  },{
    path: 'decision',
    title: '投决会',
    name: 'decision',
    component: Main,
    component: () =>
      import ('@/components/my-tasks/decision.vue')
  },{
    path: 'money',
    title: '消金会',
    name: 'money',
    component: Main,
    component: () =>
      import ('@/components/my-tasks/money.vue')
  },{
    path: 'consumption',
    title: '新建消金',
    name: 'consumption',
    component: Main,
    component: () =>
      import ('@/components/my-tasks/consumption.vue')
  },{
    path: 'consumptionList',
    title: '消金提交',
    name: 'consumptionList',
    component: Main,
    component: () =>
      import ('@/components/my-tasks/consumptionList.vue')
  },{
    path: 'Cast',
    title: '投决',
    name: 'Cast',
    component: Main,
    component: () =>
      import ('@/components/my-tasks/Cast.vue')
  },{
    path: 'Castlist',
    title: '投决提交',
    name: 'Castlist',
    component: Main,
    component: () =>
      import ('@/components/my-tasks/Castlist.vue')
  },{
    path: 'dataliatList',
    title: '数据使用申请列表',
    name: 'dataliatList',
    component: Main,
    component: () =>
      import ('@/components/my-tasks/dataliatList.vue')
  },{
    path: 'Dataliat',
    title: '数据使用申请',
    name: 'Dataliat',
    component: Main,
    component: () =>
      import ('@/components/my-tasks/Dataliat.vue')
  }]
},{
  path: '/Workbench',
  title: '',
  name: 'Workbench',
  component: Main,
  children: [{
    path: 'materials',
    title: '上传',
    name: 'materials',
    component: () =>
      import ('@/components/Workbench/materials.vue')
  },{
    path: 'Nucleus',
    title: '审核',
    name: 'Nucleus',
    component: () =>
      import ('@/components/Workbench/Nucleus.vue')
  },{
    path: 'Remind',
    title: '提醒',
    name: 'Remind',
    component: () =>
      import ('@/components/Workbench/Remind.vue')
  }]
}]
/**  Remind */

// 所有上面定义的路由都要写在下面的routers里
export const routers = [
  enterprisewechat,
  loginRouter,
  otherRouter,
  ...appRouter,
];
