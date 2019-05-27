<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
@import "./components/common/common.less";
html,body{
    width: 100%;
    height: 100%;
    background: #f0f0f0;
    min-width: 1030px;
    /* overflow-x: hidden; */
}
#app{
  width: 100%;
  height: 100%;
  /* min-width: 1330px; */
}
</style>
