<template>
  <div class="white-bg">
    <mt-header class="header" title="待办-查看详情">
      <router-link to="/todoList" slot="left">
        <mt-button icon="back"></mt-button>
      </router-link>
    </mt-header>
    <!-- <scroll ref="scroll" class="scroll-wrapper"> -->
      <div>
        <div class="title-area">
          <div class="title">{{data.proName}}简介表</div>
          <div class="title-info">
            <span>日期：</span>
            <span>{{data.createTime}}</span>
          </div>
        </div>
        <div class="info-table-area">
          <div class="area-title">项目基本信息</div>
          <table class="info-table" style="text-align: left;">
            <tr>
              <th class="th">应用系统名称</th>
              <td>{{data.systemName}}</td>
            </tr>
            <tr>
              <th class="th">申请部门</th>
              <td>{{data.applyDepartment}}</td>
            </tr>
            <tr>
              <th class="th">申请人</th>
              <td>{{data.createUserName}}</td>
            </tr>
            <tr>
              <th class="th">申请提交时间</th>
              <td>{{data.createTime}}</td>
            </tr>
            <tr>
              <th class="th">建议完成时间</th>
              <td>{{data.suggestCompleteTime}}</td>
            </tr>
            <tr>
              <th class="th">任务类型</th>
              <td>{{data.applyType}}</td>
            </tr>
            <tr>
              <th class="th">任务类别</th>
              <td>{{data.applyCategory}}</td>
            </tr>
           <tr style="height: 50px;">
             <span style="display: block;position: relative;left: 150%;margin-left: -50px;width: 100px;height: 40px;line-height: 40px;color:#626262;">申请描述栏</span>
           </tr>
           <tr>
              <th class="th">数据查看/提取/修改/删除原因</th>
              <span style="width: 270px;word-wrap: break-word;display: block;padding-right: 5px;">{{data.optReason}}</span>
            </tr>
            <tr>
              <th class="th">数据查看/提取/修改/删除内容</th>
              <span style="width: 270px;word-wrap: break-word;display: block;padding-right: 5px;">{{data.optContent}}</span>
            </tr>
            <tr style="height: 50px;">
             <span style="display: block;position: relative;left: 150%;margin-left: -75px;width: 130px;height: 40px;line-height: 40px;color:#626262;">申请操作审批栏</span>
           </tr>
           <tr>
              <th class="th">可行性分析和影响</th>
              <span style="width: 270px;word-wrap: break-word;display: block;padding-right: 5px;">{{data.feasibilityAnalysis}}</span>
            </tr>
          </table>
          <template v-if="data.acceptanceConclusion != null">
          <span style="display: block;color:#626262;width: 215px;position: relative;left: 50%;margin-left: -105px;padding: 20px 0;">数据查看/提取/修改/删除验收栏</span>
          <th class="th" style="width: 164px;color: #626262;background: #f9fafb;font-weight: normal;padding: 20px 0;border-right: 1px solid #eee;">验收结论</th>
          <td style="width: 294px;color: #626262;background: #f9fafb;font-weight: normal;padding: 20px 0;text-align: center;">{{data.acceptanceConclusion}}</td>
          </template>
          <template v-if="DateShowlist">
            <span style="display: block;color:#626262;width: 215px;position: relative;left: 50%;margin-left: -105px;padding: 20px 0;">数据查看/提取/修改/删除验收栏</span>
             <table class="info-table">
               <th class="th" style="width: 164px;color: #626262;background: #f9fafb;font-weight: normal;border-right: 1px solid #eee;">验收结论</th>
                <textarea style="width: 80%;position: relative;top: 10px;padding: 0 15px;border: none;" v-model="acceptanceConclusion"></textarea>
             </table>
          </template>
        </div>


         <div class="progress-area">
          <div class="area-title">审批流</div>
          <div v-for="item in progressHistory" :key="item.$index" class="progress-item">
            <div class="progress-line"></div>
            <div class="progress-icon"><img :src="require('@/assets/images/icon_done.png')"></div>
            <div class="content">
              <div class="approval">{{item.resultType}}</div>
              <div class="title">{{item.operator}}·{{item.resultState}}</div>
              <div v-if="item.resultType != '提交申请'"  class="advice">审批意见：{{item.result}}</div>
              <div v-if="item.resultType == '提交申请'"  class="advice">备注：{{item.result}}</div>
            </div>
            <div class="time">{{item.date}}</div>
          </div>
        </div>

        <template v-if="!DateShowlist">
        <div class="divide-title">审批结果</div>
        <mt-radio class="radio" v-model="agree" :options="['同意', '不同意','有条件同意']" v-if="Conditionalshow"></mt-radio>
        <mt-radio class="radio" v-model="agree" :options="['同意', '不同意']" v-else></mt-radio>
        </mt-radio>
        <div class="divide-title">审批意见</div>
        <div style="padding-bottom: 25px;">
          <mt-field class="advice-input" placeholder="审批意见" type="textarea" rows="6" v-model.trim="advice"></mt-field>
          <mt-button @click="submit" class="button submit-btn">提交</mt-button>
        </div>
        </template>
        <template v-if="DateShowlist">
          <mt-button @click="DateSub" class="button submit-btn">提交</mt-button>
        </template>
      </div>
    <!-- </scroll> -->
    <div class="Loading" v-show="DisshowList"><img src="@/assets/images/loading.gif" alt=""></div>
  </div>
</template>

<script>
import { Indicator } from 'mint-ui';
import { Toast } from 'mint-ui';
export default {
  name: "todoDetail",
  components: {},
  props: {},
  data() {
    return {
      agree: "同意",
      options:"",
      advice: "",
      acceptanceConclusion:'',//数据使用申请
      open: false,
      data: {},
      progressHistory: [],
      DisshowList:false,
      Conditionalshow:false,
      Tips:false,
      DateShowlist:false
    };
  },
  watch: {},
  created() {
    this.Conditionalshow = this.$route.query.Conditional === 'true' ? true : false,
    this.Tips = this.$route.query.Tips === 'true' ? true : false,
    this.DateShowlist = this.$route.query.DateShowlist === 'true' ? true : false,
    this.$api.proDateList({ id: this.$route.query.orderId }).then(res => {
      this.data = res.data;
      console.log(this.data);
    });
    this.$api.getHistoryAudit({ id: this.$route.query.orderId }).then(res => {
      this.progressHistory = res.data;
      console.log(this.progressHistory);
      
      
    });
  },
  methods: {
    showDetail(){
      this.open = !this.open
      this.$refs.scroll.forceUpdate();
    },
    submit() {
      if (this.agree == "同意") {
        this.resData = "0"
      }else if(this.agree == "不同意"){
        this.resData = "-1"
      }else if(this.agree == "有条件同意"){
        this.resData = "-2"
      }
      if (this.Tips) {
      Toast({
        message: "移动端无法选择上会人员请用电脑操作",
        position: 'middle',
        duration: 5000
      });
      return false;
      }
      let title = `审批结果${this.agree}<br>审批意见：${this.advice}`
      console.log(this.acceptanceConclusion);
      this.$messagebox({
          title: '温馨提示',
          message: title,
          showCancelButton: true,
          confirmButtonText:"继续提交",
          cancelButtonText:"取消提交"
        }).then(action => {
       if(action == 'confirm'){

          if (this.resData == '-1' || this.resData == '-2') {
            if (this.advice == '') {
              Toast({
              message: "请填写审批意见",
              position: "middle",
              duration: 5000
            });
            return false;
            }
      }

         this.DisshowList = true;
          this.$api
          .process({
            orderId: this.$route.query.orderId,
            taskId: this.$route.query.taskId,
            processId: this.$route.query.processId,
            resultState: this.resData,
            result: this.advice,
            proType:"4",
          })
          .then(res => {
              this.DisshowList = false;
              window.location.href="/doneList"
          });
            }else{
              console.log('取消')
            }

      })

    },
    DateSub(){
      if (this.DateShowlist) {
        if (this.acceptanceConclusion == '') {
        Toast({
        message: "验收结论不能为空！",
        position: 'middle',
        duration: 5000
      });
      return false
      }
      }
      var params = {
        processId: this.$route.query.processId,
        orderId: this.$route.query.orderId,
        taskId: this.$route.query.taskId,
        resultState: '0',
        result: '',
        acceptanceConclusion:this.acceptanceConclusion,
        proType:"4",
      };
    console.log(params);
    this.DisshowList = true;
    this.$api.auditFund(params).then(data => {
      if (data.code == "000") {
        this.DisshowList = false;
        this.$router.push("/doneList");
      } else {
        Toast({
        message: data.message,
        position: 'middle',
        duration: 5000
      });
      }
    });
    }
  }
};
</script>

<style lang="less" scoped>
.white-bg {
  overflow-y: scroll;
  -webkit-overflow-scrolling: touch;
}
.title-area {
  padding: 0 18px;
  color: rgba(51, 51, 51, 1);
  border-bottom: 1px solid rgba(151, 151, 151, 0.1);
  .title {
    text-align: center;
    font-size: 20px;
    font-weight: 500;
    margin: 15px auto;
  }
  .title-info {
    font-size: 14px;
    margin-bottom: 8px;
  }
}
.area-title {
  font-size: 14px;
  font-weight: 500;
  color: rgba(51, 51, 51, 1);
  margin: 14px 18px;
  &:before {
    content: "";
    display: inline-block;
    width: 4px;
    height: 18px;
    background: rgba(49, 151, 251, 1);
    border-radius: 3px;
    margin-right: 8px;
    vertical-align: middle;
  }
}
.info-table-area {
  font-size: 14px;
  margin-bottom: 10px;
  .mid-title {
    text-align: center;
    padding: 14px;
    color: rgba(98, 98, 98, 1);
    .open-text {
      font-size: 14px;
      color: rgba(23, 101, 240, 1);
      margin-right: 5px;
    }
    .open-icon {
      width: 17px;
    }
  }
  .info-table {
    width: 100%;
    text-align: center;
    border-collapse: collapse;
    td,
    th {
      font-weight: 400;
      border: 1px solid rgba(151, 151, 151, 0.1);
      padding: 14px;
    }
    th {
      color: rgba(98, 98, 98, 1);
      background: rgba(249, 250, 251, 1);
    }
    .th {
      width: 120px;
      max-width: 120px;
    }
    td {
      color: rgba(51, 51, 51, 1);
    }
    .top-th {
      max-width: 40px;
      width: 40px;
      background: rgba(249, 250, 251, 1);
      color: rgba(51, 51, 51, 1);
    }
    .second-th {
      background-color: #fff;
    }
    .wd {
      max-width: 94px;
      white-space: nowrap;
    }
  }
}
.attachment-area {
  overflow: hidden;
  .file-container {
    display: flex;
    box-sizing: border-box;
    width: 355px;
    height: 80px;
    background: rgba(249, 250, 251, 1);
    padding: 20px;
    margin: 0 auto;
    justify-content: flex-start;
    align-items: center;
    margin-bottom: 10px;
    text-decoration: none;
    .file-icon {
      font-size: 34px;
      margin-right: 16px;
    }
    .file-info {
      float: left;
      .file-name {
        font-size: 14px;
        color: rgba(51, 51, 51, 1);
        margin-bottom: 2px;
      }
      .file-size {
        font-size: 14px;
        color: rgba(155, 155, 155, 1);
      }
    }
  }
}
.progress-area {
  .progress-item {
    position: relative;
    display: flex;
    justify-content: space-between;
    align-items: baseline;
    margin: 0 10px 0 18px;
    height: 100%;
    font-size: 12px;
    font-weight: 500;
    color: rgba(155, 155, 155, 1);
    .progress-line {
      position: absolute;
      display: inline-block;
      width: 3px;
      height: 100%;
      background: rgba(23, 101, 240, 0.5);
      border-radius: 2px;
    }
    .progress-icon {
      position: absolute;
      left: -7px;
      top: 0;
      img {
        width: 17px;
      }
    }
    &:last-child .progress-line {
      display: none;
    }
    .content {
      padding-left: 20px;
      width: 200px;
      .title {
        font-size: 14px;
        font-weight: 500;
        color: rgba(51, 51, 51, 1);
        margin-bottom: 6px;
      }
    }
  }
}
.divide-title {
  width: 100%;
  height: 48px;
  line-height: 48px;
  font-size: 14px;
  color: rgba(98, 98, 98, 1);
  margin: 10px 0;
  background: rgba(249, 250, 251, 1);
  text-align: center;
}
.advice-input {
  padding: 0 21px;
  border-bottom: 1px solid rgba(151, 151, 151, 0.1);

}
.submit-btn {
  display: block;
  margin: 36px auto 44px auto;
  width: 310px;
}
.advice{
 width: 330px !important;
 height: 100%;
//  margin-right: 20px;
 word-wrap: break-word;
}
.approval{
  margin-top: 10px;
}
.Loading{
  width: 50px;
  height: 50px;
  position: absolute;
  top: 50%;
  left: 50%;
  margin-top: -25px;
  margin-left: -25px;
  img{
    width: 100%;
    height: 100%;
  }
}
</style>
