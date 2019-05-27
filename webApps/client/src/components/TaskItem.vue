
<template>
  <mt-cell-swipe class="state-item" :right="[swipeConfig]">
    <div>
      <div class="item-title">{{data.proDisplayName}}</div>
      <div class="item-info">
        <span class="item-info-key">授权人：</span>
        <span class="item-info-value">{{data.operatorId}}</span>
      </div>
      <div class="item-info">
        <span class="item-info-key">代理人：</span>
        <span class="item-info-key">{{data.surrogateId}}</span>
      </div>
      <div class="item-info">
        <span class="item-info-key">开始时间：</span>
        <span class="item-info-key">{{data.sdate}}</span>
      </div>
      <div class="item-info">
        <span class="item-info-key">结束时间：</span>
        <span class="item-info-key">{{data.edate}}</span>
      </div>
    </div>
  </mt-cell-swipe>
</template>

<script>
import { CellSwipe } from "mint-ui";

export default {
  props: {
    data: {
      type: Object,
      default: () => {
        return {
          proDisplayName: "",
          operator: "",
          surrogate: "",
          sdate: "",
          edate: ""
        };
      }
    },
    deleteHandler: {
      type: Function
    }
  },
  components: { CellSwipe },
  data() {
    return {
      swipeConfig: {
        content: "删除",
        style: {
          background: "rgba(236, 83, 74, 1)",
          color: "#fff",
          fontSize: "18px",
          height: "145px",
          lineHeight: "145px"
        },
        handler: () => this.$messagebox("确认删除?").then(()=>this.deleteHandler(this.data.id))
      }
    };
  },
  created(){
    console.log("111");
    
  },
  computed: {},
  methods: {}
};
</script>

<style lang="less" scoped>
.state-item {
  position: relative;
  display: inline-block;
  width: 100%;
  padding: 20px;
  background: #fff;
  margin-top: 10px;
  box-sizing: border-box;
  .item-title {
    font-size: 20px;
    font-weight: 500;
    color: #000;
    margin-bottom: 12px;
  }
  .item-info {
    font-size: 14px;
    margin-bottom: 4px;
    .item-info-key {
      color: rgba(102, 102, 102, 1);
    }
    .item-info-value {
      color: rgba(0, 0, 0, 1);
    }
  }
}
</style>
