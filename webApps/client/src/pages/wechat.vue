<template></template>

<script>
import axios from "axios";
import { Toast } from 'mint-ui';
export default {
  data() {
    return {
      code: "", //企业微信重定向里面的code值，用户的唯一识别
      state: ""
    };
  },
  created() {
    this.code = this.$route.query.code;
    this.state = this.$route.query.state;
  },
  mounted() {
    if (this.code) {
      console.log(this.code);
      sessionStorage.setItem("login",1);
      sessionStorage.setItem("isok",1);
      axios.get("/api/snaker/wechat/login?code=" + this.code)
        .then(res => {
          if (res.data.code == "000") {
            if (res.data.data !=null) {
              localStorage.setItem("userName",res.data.data.userName);
            }
            this.$router.push({
              path: "todoList"
            });
          }else if(res.data.code == "40029"){
              window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=ww41c7c10a677de598&redirect_uri=https%3A%2F%2Frisk-approval.weshareholdings.com%2FWechat&response_type=code&scope=snsapi_base&agentid=1000026&state=STATE#wechat_redirect"
              // window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=ww050711af216fbb0c&redirect_uri=http%3A%2F%2Frisk-approval.weshareholdings.com.cn%2FWechat&response_type=code&scope=snsapi_base&agentid=1000003&state=STATE#wechat_redirect"
          } else {
            Toast({
                message: res.data.message,
                position: 'middle',
                duration: 5000
              });
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    }
  }
};
</script>

<style scoped>
</style>

