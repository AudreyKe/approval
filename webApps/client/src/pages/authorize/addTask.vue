<template>
  <div class="white-bg">
    <div class="close-icon" @click="goback"><img :src="require('@/assets/images/icon_close.png')"></div>
    <div class="item item-select">
      <span class="item-key">流程名称</span>
      <span v-if="confirmProcessName" class="item-value" @click="openProcessPicker">{{confirmProcessName}}</span>
      <span v-else class="item-placeholder" @click="openProcessPicker">请选择<i class="iconfont icon-right"></i></span>
    </div>
    <div class="item item-select">
      <span class="item-key">授权人</span>
      <span class="item-value">{{authorizeName}}</span>
    </div>
    <div class="item item-select">
      <span class="item-key">代理人</span>
      <span v-if="confirmAgent" class="item-value" @click="openAgentPicker">{{confirmAgent}}</span>
      <span v-else class="item-placeholder" @click="openAgentPicker">请选择<i class="iconfont icon-right"></i></span>
    </div>
    <div class="item item-select">
      <span class="item-key">开始时间</span>
      <span v-if="confirmStartTime" class="item-value" @click="openStartPicker">{{confirmStartTime}}</span>
      <span v-else class="item-placeholder" @click="openStartPicker">请选择<i class="iconfont icon-right"></i></span>
    </div>
    <div class="item item-select">
      <span class="item-key">结束时间</span>
      <span v-if="confirmEndTime" class="item-value" @click="openEndPicker">{{confirmEndTime}}</span>
      <span v-else class="item-placeholder" @click="openEndPicker">请选择<i class="iconfont icon-right"></i></span>
    </div>

    <mt-popup class="popup" v-model="popupVisible" position="bottom">
      <div class="action-container">
        <span class="action" @click="popupVisible=false">取消</span>
        <span class="action" @click="agentConfirm">确定</span>
      </div>
      <mt-picker :slots="agentSlots" @change="onValuesChange"></mt-picker>
    </mt-popup>

    <mt-popup class="popup" v-model="processPopVisible" position="bottom">
      <div class="action-container">
        <span class="action" @click="processPopVisible=false">取消</span>
        <span class="action" @click="processConfirm">确定</span>
      </div>
      <mt-picker :slots="processSlots" @change="onProcessChange"></mt-picker>
    </mt-popup>

    <mt-datetime-picker ref="startPicker" type="datetime" @confirm="startTimeConfirm" v-model="startTime">
    </mt-datetime-picker>
    <mt-datetime-picker ref="endPicker" type="datetime" @confirm="endTimeConfirm" v-model="endTime">
    </mt-datetime-picker>
    <mt-button class="button submit-btn" @click="addTaskSubmit">提交</mt-button>
  </div>
</template>

<script>
export default {
  name: "addTask",
  components: {},
  props: {},
  data() {
    return {
      processName: "",
      processId: "",
      startTime: this.$moment(new Date()).format("YYYY-MM-DD HH:mm:ss"),
      endTime: this.$moment(new Date()).format("YYYY-MM-DD HH:mm:ss"),
      agent: "",
      processName: "",
      confirmStartTime: "",
      confirmEndTime: "",
      confirmAgent: "",
      confirmProcessName: "",
      agentSlots: [
        {
          values: [],
          textAlign: "center"
        }
      ],
      processSlots: [
        {
          values: [],
          textAlign: "center"
        }
      ],
      popupVisible: false,
      processPopVisible: false,
      processArray: []
    };
  },
  computed:{
    authorizeName(){
      return localStorage.getItem('userName');
    }
  },
  watch: {},
  created() {
    this.$api.getSurrogate().then(res => {
      this.agentSlots[0].values = res.data.map(element => {
        return element.userName;
      });
    });
    this.$api.getProcessAll().then(res => {
      this.processArray = Object.assign([], res.data);
      this.processSlots[0].values = res.data.map(element => {
        return element.proDisplayName;
      });
    });
  },
  methods: {
    goback() {
      this.$router.go(-1);
    },
    openStartPicker() {
      this.$refs.startPicker.open();
    },
    openEndPicker() {
      this.$refs.endPicker.open();
    },
    startTimeConfirm(value) {
      this.confirmStartTime = this.$moment(value).format("YYYY-MM-DD HH:mm:ss");
    },
    endTimeConfirm(value) {
      this.confirmEndTime = this.$moment(value).format("YYYY-MM-DD HH:mm:ss");
    },
    onValuesChange(event) {
      this.agent = event.getSlotValue(0);
    },
    onProcessChange(event) {
      this.processName = event.getSlotValue(0);
    },
    openAgentPicker() {
      this.popupVisible = true;
    },
    openProcessPicker() {
      this.processPopVisible = true;
    },
    agentConfirm() {
      this.confirmAgent = this.agent;
      this.popupVisible = false;
    },
    processConfirm() {
      this.confirmProcessName = this.processName;
      this.confirmProcessId = this.processArray.find(element => {
        return element.proDisplayName === this.confirmProcessName;
      }).processName;
      this.processPopVisible = false;
    },
    addTaskSubmit() {
      this.$api
        .addAuth({
          processName: this.confirmProcessName,
          odate: this.$moment(new Date()).format("YYYY-MM-DD HH:mm:ss"),
          sdate: this.confirmStartTime,
          edate: this.confirmEndTime,
          operator: this.authorizeName,
          surrogate: this.confirmAgent
        })
        .then(res => {
          this.goback();
        });
    }
  }
};
</script>

<style lang="less" scoped>
.item {
  display: flex;
  font-size: 16px;
  border-bottom: 1px solid rgba(151, 151, 151, 0.1);
  padding: 10px 12px;
  justify-content: space-between;

  .item-key {
    color: rgba(51, 51, 51, 1);
  }
  .item-value {
    color: rgba(51, 51, 51, 1);
    font-weight: 500;
  }
  .item-placeholder {
    color: rgba(155, 155, 155, 1);
  }
  &.item-select {
    padding: 20px 12px;
  }
}
.close-icon {
  padding: 22px 0 9px 12px;
  border-bottom: 1px solid rgba(151, 151, 151, 0.1);
  img {
    width: 22px;
  }
}
.popup {
  width: 100%;
  .action-container {
    display: flex;
  }
  .action {
    display: inline-block;
    width: 50%;
    text-align: center;
    line-height: 40px;
    font-size: 16px;
    color: #26a2ff;
  }
}
.submit-btn {
  display: block;
  margin: 36px auto 44px auto;
  width: 310px;
}
</style>
