<template>
  <div class="white-bg">
    <div class="logo-container">
      <img class="logo" :src="require('@/assets/images/login_logo.png')">
    </div>
    <div class="login-form">
      <mt-field class="login-input" placeholder="请输入用户名" v-model="userName"></mt-field>
      <mt-field class="login-input" placeholder="请输入密码" type="password" v-model="password"></mt-field>
      <mt-button @click="login" class="button login-btn">登录</mt-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "login",
  components: {},
  props: {},
  data() {
    return {
      userName: "",
      password: ""
    };
  },

  watch: {},
  created() {},
  methods: {
    login() {
      this.$api
        .login({
          userName: this.userName,
          password: this.password
        })
        .then(data => {
          localStorage.setItem('userName',this.userName)
          this.$router.push("/todoList");
        })
        .catch(err => {});
    }
  }
};
</script>

<style lang="less" scoped>
.logo-container {
  width: 100%;
  .logo {
    display: block;
    width: 143px;
    margin: 70px auto 40px auto;
  }
}
.login-form {
  width: 310px;
  padding: 20px;
  margin: 0 auto;
  .form-title {
    font-size: 24px;
    color: rgba(51, 51, 51, 1);
    margin-bottom: 24px;
  }
  .login-input {
    margin-bottom: 20px;
  }
  .login-btn {
    margin-top: 50px;
  }
}
</style>
