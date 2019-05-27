<template></template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      code: "",
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
              localStorage.setItem("user",res.data.data.userName);
              localStorage.setItem("menuList", JSON.stringify(res.data.data.menuList));
            }
            this.$router.push({
              path: "home"
            });
          }else if(res.data.code == "40029"){
            
            window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=ww41c7c10a677de598&redirect_uri=https%3A%2F%2Frisk-approval.weshareholdings.com%2FWechat&response_type=code&scope=snsapi_base&agentid=1000026&state=STATE#wechat_redirect"
          }
           else {
            window.serviceError(res.data.message);
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

