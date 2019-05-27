<template>
  <div id="app">
    <!-- <transition :name="transitionname"> -->
    <router-view></router-view>
    <!-- </transition> -->
  </div>
</template>
<script>
export default {
  name: "App",
  data() {
    return {
      transitionname: "slide-left"
    };
  },
  watch:{
    $route(to, from) {
      if (to.meta.title) {
        document.title = to.meta.title;
      }
    }
  }
};
</script>
<style lang="less">
@import "./common/common.less";
body,
html {
  height: 100%;
  width: 100%;
  background: rgb(244, 244, 244);
  overflow: hidden;
}

#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  position: relative;
  box-sizing: border-box;
  height: 100%;
  overflow: hidden;
}
input::-webkit-input-placeholder {
  color: #e0e0e0;
}
</style>
