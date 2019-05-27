<template>
  <div class="white-bg">
    <mt-header class="header" title="已办-查看详情">
      <router-link to="/doneList" slot="left">
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
          <div class="title-info">
            <span>项目团队：</span>
            <span>{{data.team}}</span>
          </div>
        </div>
        <div class="info-table-area">
          <div class="area-title">项目基本信息</div>
          <table class="info-table" style="word-wrap:break-word; word-break:break-all;">
            <tr>
              <th class="th">项目名称</th>
              <td>{{data.proName}}</td>
            </tr>
            <tr>
              <th class="th">业务模式</th>
              <td>{{data.proModelReader}}</td>
            </tr>
            <tr>
              <th class="th">合作方</th>
              <td>{{data.partners}}</td>
            </tr>
            
            <tr>
              <th class="th">项目介绍</th>
              <td>{{data.proDetails}}</td>
            </tr>
            <tr>
              <th class="th">项目成本</th>
              <td>{{data.proCost}}</td>
            </tr>
            <tr>
              <th class="th">项目预期</th>
              <td>{{data.proExpectation}}</td>
            </tr>
            <tr>
              <th class="th">计划项目启动时间</th>
              <td>{{data.proStarTime}}</td>
            </tr>
            <tr>
              <th class="th">项目完成时间</th>
              <td>{{data.proCompleteTime}}</td>
            </tr>

          </table>
        </div>
        <div class="attachment-area">
          <div class="area-title">附件</div>
          <a v-for="file in data.fileList" :key="file.$index" class="file-container" @click="goPdf(file)">
            <i class="file-icon iconfont icon-pdf" />
            <div class="file-info">
              <div class="file-name">{{file.fileName}}</div>
            </div>
          </a>
        </div>
        <div class="progress-area">
          <div class="area-title">审批历史</div>
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
      </div>
    <!-- </scroll> -->
  </div>
</template>

<script>
import mdContract from './contract.md.vue'
export default {
  name: "todoDetail",
  components: {},
  props: {},
  data() {
    return {
      agree: "同意",
      advice: "",
      open: false,
      data: {},
      progressHistory: [],
      flage: false,
      showtoHide:'showdoneDetail',
      vision:true,
      WidthArr: []

    };
  },
  watch: {
    
  },
  created() {
    this.$api.proDetail({ id: this.$route.query.orderId }).then(res => {
      this.data = res.data;
      console.log(this.data);
      let arr = []
      let brr=[]
    let crr = res.data.teamList;
    console.log(arr);
    for (let i = 0; i < crr.length; i++) {
      brr.push(crr[i].fullName)
    }
      this.data.team = brr.toString()
      let proModel = res.data.proModelList;
     if (proModel != null) {
      let proModelArr = [];
      for (let i = 0; i < proModel.length; i++) {
        proModelArr.push(proModel[i].name)
      }
      this.data.proModelReader = proModelArr.toString()
      this.data.proStarTime = new Date(res.data.proStarTime).format("yyyy-MM-dd hh:mm:ss")
      this.data.proCompleteTime = new Date(res.data.proCompleteTime).format("yyyy-MM-dd hh:mm:ss")
    }
    });
    this.$api.getHistoryAudit({ id: this.$route.query.orderId }).then(res => {
      this.progressHistory = res.data;
      console.log(this.progressHistory);
      
    })
  },
  methods: {
    showDetail() {
      this.open = !this.open;
      //this.$refs.scroll.forceUpdate();
    },
    showdoneDetail(){
      this.showOn()
      this.vision = false;
    },
    goPdf(pages){      
       let url = '/snaker/flow/attach/'+pages.id+ "/" + "-1";
       console.log(url);
       window.open('/static/pdf/web/viewer.html?file=' + url)
    }
  },
  name: 'page-one',
  components: {
      mdContract
  },
};
</script>

<style lang="less" scoped>
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
    margin-bottom: 20px;
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
      width: 100%;
      .title {
        width: 200px;
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
 word-wrap: break-word;
 overflow-y: hidden;
//  line-height: 20px;
margin-top: -5px;
margin-bottom: 15px;
 .open-text{
   display: block;
   width: 107px;
   color: #1765f0;
 }
}
.time{
  width:auto;
  height: 20px;
  line-height: 20px;
  text-align: right;
  position: absolute;
  right: 0px;
}
.showdoneDetail{
  width: 330px;
  height: 50px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.open_down{
  color:  #1765f0;
  position: absolute;
  top: 40px;
  display: block;
  width: 90px;
  left: 50%;
  margin-left: -45px;
  .bg_icon{
    width: 14px;
  }
}
.teatxt{
  width: 100%;
  word-break:break-all;
  overflow: hidden;
  height: auto;
}
.bgtext{
  width: 100%;
  height: 100%;
}
.approval{
  color: #000;
}
</style>
