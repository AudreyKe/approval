
<template>
  <div class="header-nav">
    <mt-navbar v-model="selected">
      <mt-tab-item id="todoList">
        <router-link to="/todoList">待办</router-link>
      </mt-tab-item>
      <mt-tab-item id="doneList">
        <router-link to="/doneList">已办</router-link>
      </mt-tab-item>
      <mt-tab-item id="detList">
        <router-link to="/detList">项目列表</router-link>
      </mt-tab-item>
      <mt-tab-item id="authorizeList">
        <router-link to="/authorizeList">委托授权</router-link>
      </mt-tab-item>
    <span class="checkOut" @click="checkOut"><img src="@/assets/images/checkout.png"></span>
    <!-- <span class="checkOut" @click="checkOut">退出</span> -->
    </mt-navbar>
  </div>
</template>

<script>
import { Navbar, TabItem } from "mint-ui";
export default {
  props: {},
  components: { Navbar, TabItem },
  data() {
    return {
      selected: this.$route.path.substr(1)
    };
  },
  computed: {},
  methods: {
    checkOut(){
      localStorage.removeItem('userName');
      this.$router.push("/login");
    }
  }
};
</script>

<style lang="less" scoped>
.header-nav {
  height: 52px;
  color: rgba(102, 102, 102, 1);
  font-weight: 500;
  overflow: hidden;
  .mint-navbar .mint-tab-item.is-selected {
    color: rgba(23, 101, 240, 1);
    border: none;
  }
  a.mint-tab-item.is-selected:after {
    content: "";
    display: inline-block;
    width: 28px;
    height: 3px;
    background: rgba(23, 101, 240, 1);
    border-radius: 2px;
  }
  .checkOut{
    display: block;
    width: 40px;
    height: 25px;
    position: absolute;
    right: 15px;
    top: 15px;
    font-size: 12px;
    line-height: 25px;
    color: red;
    img{
      display: block;
      width: 25px;
      margin: 0 auto;
    }
  }
}
</style>
