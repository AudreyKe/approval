<template>
  <div class="main">
    <div class="main_header">
      <div class="logo_info">
        <img src="@/assets/images/index_logo.png" alt="" style="width:175px;cursor: pointer;" @click="Goback">
        <span class="split">|</span>
        <div class="logo_text">审批平台</div>
        <div class="header_operate">
          <a href="http://sso.weshareholdings.com/cas/login " class="pwd"><img src="@/assets/images/pwd.png" alt=""></a>
          <i class="iconfont icon-dengchu" title="退出" @click="logout"></i>
        </div>
      </div>
    </div>
    <div class="main_content">
      <div class="content_menu">
        <Menu ref="sideMenu" :theme="theme" @on-select="changeMenu" :active-name="$route.name" :open-names="openname">
          <template v-for="(item) in menuList" >
            <Submenu :name="item.name" :key="item.id" v-if="item.child && item.child.length > 0">
              <template slot="title">
                <i class="iconfont" :class="item.icon"></i>
                  {{item.name}}
              </template>
              <template v-for="(item2) in item.child">
                  <MenuItem :name="item2.url" :key="item2.id" class="second_menu">
                  {{item2.name}}
                  <template v-if="item2.name === '待办审批'">
                    <span class="menu_tips" v-if="$store.state.app.upcomingLength > 0">{{$store.state.app.upcomingLength}}</span>
                  </template>
                  </MenuItem>
              </template>
            </Submenu>
            <MenuItem :name="item.url" :key="item.id" v-else>{{item.name}}</MenuItem>
          </template>
      </Menu>
      </div>
      <!-- <div class="crumb">
        <span class="crumb_icon">
          <i class="iconfont icon-daibanrenwu"></i>
        </span>
        <div class="crumb_text">
          <div class="first_title">待办任务</div>
          <div class="child_title">列表</div>
        </div>
      </div> -->
      <div class="content_info" id="content_info">
        <router-view></router-view>
      </div>
    </div>
    
  </div>
</template>

<script>
export default {
  name: "HelloWorld",
  data() {
    return {
      theme: "light",
      menuList: [],
      openname: ["我的任务", "发起审批","秘书工作台"],
      // menuList: [
      //   {
      //     name: "我的任务",
      //     url: "data",
      //     icon: "icon-daibanrenwu",
      //     id: "0",
      //     child: [
      //       {
      //         name: "待办审批",
      //         url: "upcoming-list",
      //         id: "01"
      //       },
      //       {
      //         name: "已办审批",
      //         url: "already-list",
      //         id: "02"
      //       },
      //       {
      //         name: "我发起的",
      //         url: "initiate-list",
      //         id: "03"
      //       },
      //       {
      //         name: "待提交项目",
      //         url: "submitted",
      //         id: "04"
      //       },
      //       {
      //         name: "资料上传",
      //         url: "data-upload",
      //         id: "05"
      //       },
      //       {
      //         name: "委托授权",
      //         url: "commission-auth-list",
      //         id: "06"
      //       }
      //     ]
      //   },
      //   {
      //     name: "发起审批",
      //     url: "operation",
      //     icon: "icon-wenben",
      //     id: "10",
      //     child: [
      //       {
      //         name: "项目立项",
      //         url: "Useproject",
      //         id: "11"
      //       },
      //       {
      //         name: "项目评审",
      //         url: "product-review",
      //         id: "12"
      //       },
      //        {
      //         name: "投决会",
      //         url: "decision",
      //         id: "14"
      //       },
      //        {
      //         name: "消金会",
      //         url: "money",
      //         id: "15"
      //       },
      //       {
      //         name: "数据使用申请",
      //         url: "dataliatList",
      //         id: "16"
      //       }
      //     ]
      //   },{
      //     name: "秘书工作台",
      //     url: "operation",
      //     icon: "icon-xiaomishu",
      //     id: "2",
      //     child: [
      //       {
      //         name: "资料上传",
      //         url: "materials",
      //         id: "21"
      //       },
      //       {
      //         name: "材料审核",
      //         url: "Nucleus",
      //         id: "22"
      //       },
      //       {
      //         name: "审核提醒",
      //         url: "Remind",
      //         id: "23"
      //       }
      //     ]
      //   }
      // ]
    };
  },
  created() {
    if (localStorage.getItem("token")) {
      //展开所有一级菜单
      this.$nextTick(() => {
        this.$refs.sideMenu.updateOpened();
      });
      this.api.getUpcomingList({
        pageNo:"1",
        pageSize:"1",
      }).then(data => {
        this.tableLoading = false;
        if(data.code === "000"){
          this.$store.commit('setUpcoming',data.data.totalCount)
        }
      });
    }
    let menuList  = localStorage.getItem("menuList");
    this.menuList = JSON.parse(menuList)

  },
  mounted() {
    // $("#content_info").css(
    //   "height",
    //   window.document.documentElement.clientHeight - 190 + "px"
    // );
  },
  watch: {
    // $route(to) {
      // if (!localStorage.getItem("token")) {
      //   this.$router.push({
      //     name: "login"
      //   });
      // }
    // },
  },
  methods: {
    changeMenu(name) {
      this.$router.push({
        name: name
      });
    },
    logout() {
      this.api
        .logout()
        .then(data => {
          this.$store.commit("logout");
          this.$router.push({
            name: "login"
          });
        });
    },
    Goback(){
      this.$router.push({ path: '/home'})
    }
  }
};
</script>

<style lang="less">
@base_font_color: #212121;

.main {
  background: #f0f2f5;
  position: absolute;
  width: 100%;
  height: 100%;
}
.main_header {
  width: 100%;
  height: 60px;
  background: #fff;
  box-sizing: border-box;
  position: fixed;
    z-index: 10;
    border-bottom: 1px solid rgba(0, 0, 0, 0.1);
  .logo_info {
    padding: 0 23px;
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    position: relative;
    .split {
      color: rgba(0, 33, 81, 0.2);
      margin: -3px 15px 0 15px;
      width: 1px;
      height: 16px;
    }
    .logo_text {
      font-size: 14px;
      color: @base_font_color;
      font-weight: 600;
      letter-spacing: 1px;
    }
    .header_operate {
      position: absolute;
      right: 30px;
      color: #72808c;
      i {
        cursor: pointer;
        font-size: 24px;
        &:hover {
          color: #1765f0;
        }
      }
    }
  }
}
.main_content {
  width: 100%;
  height: 100%;
  position: relative;
  .content_menu {
    width: 240px;
    background: #fff;
    height: 100%;
    // overflow: hidden;
    position: absolute;
    top: 60px;
    left: 0;
    padding-top: 20px;
    z-index: 1;
    .second_menu {
      padding-left: 54px !important;
    }
    .second_menu_title {
      padding-left: 6px !important;
    }
    .menu_tips {
      display: inline-block;
      margin-left: 10px;
      background: #ff5050;
      min-width: 30px;
      color: #fff;
      line-height: 15px;
      border-radius: 14px;
      font-weight: normal;
      padding: 2px 7px;
      text-align: center;
    }
  }
  .content_info {
    position: absolute;
    left: 240px;
    top: 70px;
    right: 0;
    overflow: hidden;
    margin: 20px 10px;
    background: #fff;
    min-width: 1020px;
  }
  .crumb {
    position: absolute;
    left: 240px;
    margin: 80px 40px 0px 40px;
    display: flex;
    width: 100%;
    height: 50px;
    color: #212121;
    .crumb_icon {
      display: flex;
      width: 50px;
      height: 50px;
      background: #1765f0;
      border-radius: 2px;
      box-shadow: 5px 6px 20px #1765f05c;
      i {
        font-size: 35px;
        width: 100%;
        color: #fff;
        text-align: center;
      }
    }
    .crumb_text {
      margin-left: 15px;
      .first_title {
        font-size: 18px;
        font-weight: bold;
      }
      .child_title {
        color: #8a8a8a;
        font-size: 12px;
      }
    }
  }
}
.main_base_layout {
  padding: 10px 30px 20px 30px;
  color: #212121;
  .main_base_title {
    font-size: 14px;
    color: @base_font_color;
    margin-top: 20px;
    font-weight: bold;
    .realtime {
      display: inline-block;
      margin-left: 20px;
      font-weight: normal;
    }
  }
  .single_title {
    height: 50px;
    line-height: 50px;
    font-size: 16px;
    color: #212121;
    font-weight: bold;
  }
  .action {
    position: absolute;
    top: 5px;
    right: 0;
  }
}
.modal_layout {
  padding: 0px 4px 14px;
  color: #212121;
  font-size: 14px;
  .modal_title {
    height: 54px;
    line-height: 54px;
    text-align: center;
    padding-top: 4px;
  }
  .common_modal_title {
    font-size: 16px;
    color: #212121;
    font-weight: bold;
    padding-bottom: 20px;
    height: 47px;
    line-height: normal;
  }
  .modal_form {
    padding-top: 20px;
    box-sizing: border-box;
    input {
      height: 40px;
      font-size: 14px;
    }
  }
  .submit_btn {
    button {
      width: 80px;
      height: 40px;
      font-size: 14px;
    }
  }
  .common_form {
    padding-right: 68px;
    input {
      width: 500px;
    }
    .input_175 {
      input {
        width: 175px;
      }
    }
    .ivu-select {
      width: 500px;
      height: 40px;
      line-height: 40px;
    }
  }
}
//表单label必填处理
.rule_text {
  margin-right: 20px;
  display: inline-block;
  &::before {
    content: "*";
    display: inline-block;
    margin-right: 4px;
    line-height: 1;
    font-family: SimSun;
    font-size: 12px;
    color: #ed4014;
    
  }
}
.border_bm {
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
}
.main_table_layout {
  margin-top: 20px;
  text-align: center;
  .ivu-page {
    margin-top: 20px;
  }
  .ivu-table-wrapper a {
    color: #006bffdb;
    &:hover {
      text-decoration: underline;
    }
  }
}
.table_page {
  text-align: center;
  margin-top: 20px;
}
@keyframes ani-demo-spin {
  from {
    transform: rotate(0deg);
  }
  50% {
    transform: rotate(180deg);
  }
  to {
    transform: rotate(360deg);
  }
}
.spin-icon-load {
  animation: ani-demo-spin 1s linear infinite;
}


//滚动条样式处理
*::-webkit-scrollbar {
  width: 5px;
  height: 7px;
}

*::-webkit-scrollbar-thumb {
  border-radius: 5px;
  background-color: hsla(220, 4%, 58%, 0.3);
}

*::-webkit-scrollbar-track {
  background: transparent;
  border-radius: 5px;
  
}
.pwd{
  display: inline-block;
    width: 20px;
    height: 20px;
    position: relative;
    top: 0px;
    right: 5px;
    img{
      display: block;
      width: 100%;
      height: 100%;
    }
}
</style>
