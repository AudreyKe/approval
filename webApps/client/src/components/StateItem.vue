
<template>
  <router-link :to="next" class="state-item">
    <div class="item-title">{{data.orderName}}</div>
    <div class="item-info">
      <span class="item-info-key">任务类型：</span>
      <span class="item-info-value">{{data.performType}}</span>
    </div>
    <div class="item-info">
      <span class="item-info-key">任务启动时间：</span>
      <span class="item-info-key">{{data.createTime}}</span>
    </div>
    <div class="state state-progress" v-if="data.orderState === '审批中' || data.orderState === '1'">
      审批中
    </div>
    <div class="state state-end" v-else-if="data.orderState === '已结束' || data.orderState === '2'">
      已结束
    </div>
    <div class="state state-passed" v-else-if="data.orderState === '已通过' || data.orderState === '0'">
      已通过
    </div>
    <div class="state state-rejected" v-else>
      已拒绝
    </div>
  </router-link>
</template>

<script>
export default {
  props: {
    data: {
      type: Object,
      default: () => {
        return {
          orderState: "",
          performType: "",
          createTime: "",
          orderName: ""
        };
      }
    },
    next:{
      type:String
    }
  },
  components: {},
  data() {
    return {};
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
  text-decoration: none;
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

  .state {
    position: absolute;
    top: 50%;
    right: 24px;
    transform: translateY(-50%);
    width: 48px;
    height: 24px;
    font-size: 14px;
    border-radius: 2px;
    border: 1px solid;
    text-align: center;
    line-height: 24px;
    &.state-progress {
      color: rgba(23, 101, 240, 1);
      border-color: rgba(23, 101, 240, 1);
    }
    &.state-end {
      color: rgba(155, 155, 155, 1);
      border-color: rgba(155, 155, 155, 1);
    }
    &.state-passed {
      color: rgba(126, 211, 33, 1);
      border-color: rgba(126, 211, 33, 1);
    }
    &.state-rejected {
      color: rgba(236, 83, 74, 1);
      border-color: rgba(236, 83, 74, 1);
    }
  }
}
</style>
