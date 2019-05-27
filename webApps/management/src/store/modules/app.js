import {otherRouter, appRouter} from '@/router/router';
import Util from '@/lib/util';
import Cookies from 'js-cookie';
import Vue from 'vue';

const app = {
    state: {
        accesstoken:"",//登录标识
        menuList:[],//菜单
        upcomingLength:0,
        orderId:""
    },
    mutations: {
        setUpcoming(state,len){
            state.upcomingLength = len;
        },
        setToken(state,token){
            state.accesstoken = token;
        },
        setMenuList (state, list) {
            state.menuList = list;
        },
        setorderId(state,orderId){
            state.orderId = orderId;
        }
    }
};

export default app;
