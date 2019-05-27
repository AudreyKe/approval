<style lang="less">
@base_font_color: #212121;
.base_nav {
  list-style: none;
  overflow: hidden;
  height: 50px;
  li {
    float: left;
    line-height: 47px;
    margin-right: 47px;
    font-size: 16px;
    cursor: pointer;
    font-weight: bold;
    color: #8a8a8a;
    &:hover {
      color: @base_font_color;
      border-bottom: 3px solid #0061ff;
    }
  }
  li.active {
    color: @base_font_color;
    border-bottom: 3px solid #0061ff;
  }
}
</style>
<!--
@desc 页面nav
@navArr {arr} nav数组
['nav1','nav2']
@navActive {String} 选中的nav序号
@return
  changeNav 当点击改变时回调的函数
e.g：
<base-nav :navArr="navArr" :navActive="navActive" @changeNav="changeNav"></base-nav>
-->
<template>
    <div>
        <ul class="base_nav border_bm">
            <li  v-for="(item,index) in navArr" :class="{'active':navActive == (index + 1)}" @click="changeNav(index)">{{item}}</li>
        </ul>
    </div>
</template>

<script>
export default {
  props: {
    navArr: Array,
    navActive: {
      type: String,
      default: "1"
    }
  },
  methods: {
    changeNav(index) {
      this.$emit("changeNav", index + 1 + "");
    }
  }
};
</script>
