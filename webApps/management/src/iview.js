// 按需引入iview组件
import Vue from 'vue'
import { Button, Table,Icon,Spin,Modal,Card,Form,FormItem,Row,Col,Input,Menu,Submenu,MenuItem,Page } from 'iview';

Vue.config.productionTip = false
Vue.component('Button', Button);
Vue.component('Table', Table);
Vue.component('Spin', Spin);
Vue.component('Icon', Icon);
Vue.component('Modal',Modal);
Vue.component('Card',Card);
Vue.component('Form',Form);
Vue.component('FormItem',FormItem);
Vue.component('Row',Row);
Vue.component('Col',Col);
Vue.component('Input',Input);
Vue.component('Menu',Menu);
Vue.component('Submenu',Submenu);
Vue.component('MenuItem',MenuItem);
Vue.component('Page',Page);
Vue.prototype.$Modal = Modal;
Vue.prototype.$Spin = Spin;

export default Vue;