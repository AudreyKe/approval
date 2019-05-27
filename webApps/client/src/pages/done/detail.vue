<template>
  <div class="white-bg">
    <mt-header class="header" title="已办-查看详情">
      <router-link to="/doneList" slot="left">
        <mt-button icon="back"></mt-button>
      </router-link>
    </mt-header>
    <scroll ref="scroll" class="scroll-wrapper">
      <div>
        <div class="title-area">
          <div class="title">{{data.proName}}简介表</div>
          <div class="title-info">
            <span>日期：</span>
            <span>{{data.date}}</span>
          </div>
          <div class="title-info">
            <span>项目团队：</span>
            <span>{{data.team}}</span>
          </div>
        </div>
        <div class="info-table-area">
          <div class="area-title">项目基本信息</div>
          <table class="info-table">
            <tr>
              <th class="th">项目名称</th>
              <td>{{data.proName}}</td>
            </tr>
            <tr>
              <th class="th">业务模式</th>
              <td>{{data.proModel}}</td>
            </tr>
            <tr>
              <th class="th">行业分类</th>
              <td>{{data.category}}</td>
            </tr>
            <tr>
              <th class="th">基础资产类型</th>
              <td>{{data.assertType}}</td>
            </tr>
            <tr>
              <th class="th">基础资产情况</th>
              <td>{{data.assertSituation}}</td>
            </tr>
            <!-- <tr>
              <th class="th">资产服务机构、劣后投资人、差额补足机构</th>
              <td>{{data.assetSerAgency}}</td>
            </tr> -->
            <!-- <tr> -->
            <tr>
              <th class="th">资产服务机构</th>
              <td>{{data.assetSerAgency}}</td>
            </tr>
            <tr>
              <th class="th">劣后投资人</th>
              <td>{{data.inferiorInvestor}}</td>
            </tr>
            <tr>
              <th class="th">差额补足机构</th>
              <td>{{data.diffAgency}}</td>
            </tr>
            <tr>
              <th class="th">12个月内预计合作规模(亿)</th>
              <td>{{data.coopScale}}</td>
            </tr>
            <tr>
              <th class="th">已合作规模</th>
              <td>{{data.scaleCooperation}}</td>
            </tr>
            <tr>
              <th class="th">存量余额</th>
              <td>{{data.stockBalance}}</td>
            </tr>
            <tr>
              <th class="th">是否同意框架合作</th>
              <td>{{data.coopState}}</td>
            </tr>
            <tr>
              <th class="th">是否同意系统对接</th>
              <td>{{data.systemAbutmentState}}</td>
            </tr>
            <tr>
              <th class="th">是否需要新分享投资</th>
              <td>{{data.investComment}}</td>
            </tr>
            <tr v-show="data.investComment == '是'">
              <th class="th">投资内容、利率、期限</th>
              <td>{{data.whetherWeshareInvest}}</td>
            </tr>
            <tr>
              <th class="th">外部增信机构</th>
              <td>{{data.externalEnhancementAgencies}}</td>
            </tr>
            <tr>
              <th class="th">评级情况</th>
              <td>{{data.ratingSituation}}</td>
            </tr>
            <tr>
              <th class="th">原始权益人</th>
              <td>{{data.originalRightsHolder}}</td>
            </tr>
            <tr>
              <th class="th">评级情况</th>
              <td>{{data.ratingSituation}}</td>
            </tr>
          </table>
          <div v-if="open" class="mid-title" style="border-top: 1px solid #eee;">分层结构</div>
          <div v-else class="mid-title">
            <span @click="showDetail" class="open-text">点击展开更多 <img class="open-icon" :src="require('@/assets/images/icon_dropdown.png')" /></span>
          </div>
          <table v-show="open" v-for="item in data.layerList" :key="item.$index" class="info-table">
            <tr>
              <th rowspan="5" class="top-th">{{item.level}}</th>
              <th class="second-th">发行金额(万元)</th>
              <td>{{item.amount}}</td>
            </tr>
            <tr>
              <th class="second-th">占比(%)</th>
              <td>{{item.proportion}}</td>
            </tr>
            <tr>
              <th class="second-th">发行利率(%)</th>
              <td>{{item.issueRate}}</td>
            </tr>
            <tr>
              <th class="second-th">产品期限(月)</th>
              <td>{{item.proTerm}}</td>
            </tr>
            <tr>
              <th class="second-th">加权平均期限(年)</th>
              <td>{{item.averageTerm}}</td>
            </tr>
          </table>
          <table v-show="open" class="info-table">
           <tr>
              <th colspan="2">综合成本</th>
              <td colspan="2">{{data.cost}}</td>
            </tr>
            <tr>
              <th style="width:200px">发行金额(含次级)亿元</th>
              <td colspan="3">{{data.totalAmount}}</td>
            </tr>
            <tr>
              <th style="width:200px">期限</th>
              <td colspan="3">{{data.term}}</td>
            </tr>
            <tr>
              <th colspan="2">分销渠道</th>
              <td colspan="2">{{data.channel}}</td>
            </tr>
             <tr>
              <td colspan="6">{{data.requirements}}</td>
            </tr>
            <tr v-show="data.requirements=='循环结构'">
               <td colspan="6"> 循环期【<span>{{data.cyclePeriod}}</span>】+ 摊还期【 <span>{{data.amortizationPeriod}}</span> 】个月</td>
            </tr>
            <tr>
            <tr v-show="data.channel=='理财通'">
               <td colspan="6">理财通分【<span>{{data.packageNum}}</span>】包发行，单包规模【 <span>{{data.packageScale}}</span> 】万元</td>
            </tr>
            <!-- <tr>
              <th colspan="2">项目前端收入</th>
              <td colspan="2">{{data.frontIncome}}</td>
            </tr>
            <tr>
              <th colspan="2">项目后端收入</th>
              <td colspan="2">{{data.afterIncome}}</td>
            </tr>
            <tr>
              <th colspan="2">项目团队意见</th>
              <td colspan="2">{{data.projectTeamOpinion}}</td>
            </tr>
           <tr v-for="ad in data.resultList" :key="ad.$index">
              <th colspan="2">{{ad.resultType}}</th>
              <td>{{ad.resultState}}</td>
              <td>{{ad.result}}</td>
            </tr> -->
          </table>
          <template v-if="open">
          <th class="th" style="width: 145px;color: #626262;background: #f9fafb;font-weight: normal;padding: 20px 0;border-right: 1px solid #eee;">项目前端收入</th>
          <td style="width: 294px;color: #626262;font-weight: normal;padding: 20px 0;text-align: center;word-wrap: break-word;border: 1px solid rgba(151, 151, 151, 0.1);">{{data.frontIncome}}</td><br>
          <th class="th" style="width: 145px;color: #626262;background: #f9fafb;font-weight: normal;padding: 20px 0;border-right: 1px solid #eee;">项目后端收入</th>
          <td style="width: 294px;color: #626262;font-weight: normal;padding: 20px 0;text-align: center;word-wrap: break-word;border: 1px solid rgba(151, 151, 151, 0.1);">{{data.afterIncome}}</td><br>
          <th class="th" style="width: 135px;color: #626262;background: #f9fafb;font-weight: normal;padding: 20px 0;border-right: 1px solid #eee;">项目团队意见</th>
          <td style="width: 294px;color: #626262;font-weight: normal;padding: 20px 0;text-align: center;word-wrap: break-word;border: 1px solid rgba(151, 151, 151, 0.1);">{{data.projectTeamOpinion}}</td>
          </template >
          <table v-show="open" class="info-table">
            <tr v-for="ad in data.resultList" :key="ad.$index">
              <th colspan="2">{{ad.resultType}}</th>
              <td>{{ad.resultState}}</td>
              <td>{{ad.result}}</td>
            </tr>
          </table>
        </div>
        <div v-show="open" class="attachment-area">
          <div class="area-title">附件</div>
          <a v-for="file in data.fileList" :key="file.$index" class="file-container" @click="goPdf(file)">
            <i class="file-icon iconfont icon-pdf" />
            <div class="file-info">
              <div class="file-name">{{file.fileName}}</div>
            </div>
          </a>
        </div>
        <div v-show="open" class="progress-area">
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
    </scroll>
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
      open: false,
      data: {},
      progressHistory: [],
    };
  },
  watch: {},
  created() {
    this.$api.getAlreadyDetail({ id: this.$route.query.orderId }).then(res => {
      this.data = res.data;
      let brr =[]
    let arr = res.data.teamList

    if (arr) {
    for (let i = 0; i < arr.length; i++) {
     brr.push(arr[i].fullName)
    }
    }
    let proModel = res.data.proModelList;
     if (proModel != null) {
      let proModelArr = [];
      for (let i = 0; i < proModel.length; i++) {
        proModelArr.push(proModel[i].name)
      }
      this.data.proModel = proModelArr.toString()
    }

    this.data.team = brr.toString();

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
     goPdf(pages){
       let url = '/snaker/flow/attach/'+pages.id+ "/" + "-1";
       console.log(url);
       window.open('/static/pdf/web/viewer.html?file=' + url)
      
    }
  }
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
