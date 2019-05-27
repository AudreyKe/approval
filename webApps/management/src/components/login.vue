<style lang="less">
@import "./login.less";
.login {
  position: relative;
  .login_bg {
    width: 100%;
    height: 100%;
  }
  .login_info {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    vertical-align: middle;
    box-sizing: border-box;
    box-shadow: 0 0px 30px rgba(0, 0, 0, 0.1);
    background: #fff;
  }
  .login_form {
    text-align: center;
    .login_form_input {
      border: 1px solid #bdc3cb;
      &:focus {
        border: 1px solid #0061ff;
        outline: 0;
      }
    }
    .login_button {
      background: #0061ff;
      border-radius: 2px;
      color: #fff;
    }
    .login_form_icon {
      position: absolute;
      padding-top: 1px;
    }
  }
}
.ivu-form-item {
  margin-bottom: 20px;
}
@media (max-width: 1440px) {
  .login_info {
    width: 450px;
    height: 330px;
    margin: auto;
  }
  .login_form {
    padding: 0 113px;
    .login_logo {
      padding: 40px 0 30px 0;
    }
    .login_form_input {
      width: 225px;
      height: 30px;
      padding: 10px 0 10px 30px;
      font-size: 11px;
    }
    .login_button {
      width: 225px;
      height: 30px;
      margin: 10px 0 50px 0;
      font-size: 11px;
    }
    .login_form_icon {
      font-size: 14px;
      width: 30px;
      line-height: 30px;
    }
  }
}
@media (min-width: 1441px) {
  .login_info {
    width: 600px;
    height: 400px;
    margin: 220px auto;
  }
  .login_form {
    padding: 0 150px;
    text-align: center;
    .login_logo {
      padding: 53px 0 40px 0;
    }
    .login_form_input {
      width: 300px;
      height: 40px;
      padding: 13px 0 13px 40px;
      font-size: 12px;
    }
    .login_button {
      width: 300px;
      height: 40px;
      margin: 13px 0 66px 0;
      font-size: 14px;
    }
    .login_form_icon {
      position: absolute;
      font-size: 16.8px;
      width: 40px;
      line-height: 40px;
      padding-top: 1px;
    }
  }
}
</style>

<template>
    <div class="login" @keydown.enter="handleSubmit">
        <img src="../assets/images/login_bg.png" alt="" class="login_bg">
        <div class="login_info">
            <div class="login_form">
              <div class="login_logo">
                <img src="../assets/images/login_logo.png" alt="" style="width:122px">
              </div>
                <!-- <Form action="http://10.10.18.58:8080/snaker/auth/login" ref="loginForm" :model="form" :rules="rules"> -->
                <Form ref="loginForm" :model="form" :rules="rules">
                    <FormItem prop="userName">
                      <i class="iconfont icon-user login_form_icon"/>
                      <input type="text" placeholder="请输入用户名" class="login_form_input" v-model="form.userName"> 
                    </FormItem>
                    <FormItem prop="password">
                      <i class="iconfont icon-Thelock login_form_icon"/>
                      <input type="password" placeholder="请输入密码" class="login_form_input" v-model="form.password">
                    </FormItem>
                    <FormItem >
                      <!-- <button type="submit" class="login_button" :loading="loading" style="border:0;cursor: pointer;">登录</button> -->
                        <Button @click="handleSubmit" type="default" long  class="login_button">登录</Button>
                    </FormItem>
                </Form>
            </div>
        </div>
    </div>
</template>

<script>
import Cookies from "js-cookie";
import axios from "axios";
export default {
  data() {
    return {
      loading: false,
      form: {
        userName: "",
        password: ""
      },
      rules: {
        userName: [
          { required: true, message: "用户名不能为空", trigger: "blur" }
        ],
        password: [{ required: true, message: "密码不能为空", trigger: "blur" }]
      }
    };
  },
  mounted(){
    sessionStorage.setItem('isok','1')
  },
  methods: {
    handleSubmit() {
      let _self = this;
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          let params = {
            userName: this.form.userName,
            password: this.form.password
          };
          this.loading = true;
          this.api
            .login(params)
            .then(data => {
              this.loading = false;
              if (data.code == "000") {
                localStorage.setItem("menuList", JSON.stringify(data.data.menuList));
                localStorage.setItem("user", this.form.userName);
                _self.$router.push({
                  name: "otherRouter"
                });
              } else {
                window.serviceError(data.message);
              }
            })
            .catch(err => {
              this.loading = false;
              window.serviceError(err.message);
            });
        }
      });
    }
  }
};
</script>
