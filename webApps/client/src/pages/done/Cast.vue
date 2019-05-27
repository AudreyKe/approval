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
            <span>{{data.date}}</span>
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
              <th class="th">投资标的</th>
              <td>{{data.investmentTarget}}</td>
            </tr>
             <tr>
              <th class="th">投资目的</th>
              <td>{{data.investmentPurpose}}</td>
            </tr>
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
              <th class="th">投资金额(亿)</th>
              <td>{{data.investmentAmount}}</td>
            </tr>
            <tr>
              <th class="th">发行金额(亿)</th>
              <td>{{data.totalAmount}}</td>
            </tr>
            <tr>
              <th class="th">杠杆倍数</th>
              <td>{{data.leverage}}</td>
            </tr>
            <tr>
              <th class="th">12个月内预计合作规模(亿)</th>
              <td>{{data.coopScale}}</td>
            </tr>
            <tr>
              <th class="th">已合作规模(亿)</th>
              <td>{{data.scaleCooperation}}</td>
            </tr>
            <tr>
              <th class="th">存量余额(亿)</th>
              <td>{{data.stockBalance}}</td>
            </tr>
            <tr>
              <th class="th">投资特点</th>
              <td>{{data.investmentCharacteristics}}</td>
            </tr>
            <tr>
              <th class="th">退出方式</th>
              <td>{{data.exitMode}}</td>
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
              <th class="th">原始权益人</th>
              <td>{{data.originalRightsHolder}}</td>
            </tr>
            <tr>
              <th class="th">评级情况</th>
              <td>{{data.holderRatingSituation}}</td>
            </tr>
            <tr>
              <th class="th">外部增信机构</th>
              <td>{{data.externalEnhancementAgencies}}</td>
            </tr>
            <tr>
              <th class="th">评级情况</th>
              <td>{{data.ratingSituation}}</td>
            </tr>
          </table>
          <div v-if="open" class="mid-title">分层结构</div>
          <div v-else class="mid-title">
            <span @click="showDetail" class="open-text">点击展开更多 <img class="open-icon" :src="require('@/assets/images/icon_dropdown.png')" /></span>
          </div>
          <table v-show="open" v-for="item in data.layerList" :key="item.$index" class="info-table" style="word-wrap:break-word; word-break:break-all;">
            <tr>
              <th rowspan="5" class="top-th">{{item.level}}</th>
              <th class="second-th">发行金额(万元)</th>
              <td>{{item.amount}}</td>
            </tr>
            <tr>
              <th class="second-th">占比</th>
              <td>{{item.proportion}}</td>
            </tr>
            <tr>
              <th class="second-th">发行利率</th>
              <td>{{item.issueRate}}</td>
            </tr>
            <tr>
              <th class="second-th">各级期限(月)</th>
              <td>{{item.proTerm}}</td>
            </tr>
            <tr>
              <th class="second-th">加权平均期限(年)</th>
              <td>{{item.averageTerm}}</td>
            </tr>
          </table>

          <table v-show="open" class="info-table" style="word-wrap:break-word; word-break:break-all;" v-if="data.investmentTarget === 'Pre-ABS'">
               <tr>
              <th rowspan="12" class="top-th">交易结构（Pre-ABS项目）</th>
              <th style="width: 100px;">底层资产类型</th>
              <td colspan="4">{{data.proInfoTradeStructure.underlyingAssetsType}}</td>
            </tr>
            <tr>
              <th style="width: 100px;">底层资产投放标准</th>
              <td colspan="4">{{data.proInfoTradeStructure.underlyingAssetsStandard}}</td>
            </tr>
            <tr>
            <td colspan="6">
            <a v-for="file in data.proInfoTradeStructure.underlyingAssetsStandardFileList" :key="file.$index" class="file-container" @click="goPdf(file)">
            <i class="file-icon iconfont icon-pdf" />
            <div class="file-info" style="display: inline-block;">
              <div class="file-name">{{file.fileName}}</div>
            </div>
          </a>
              </td>
            </tr>

              <tr>
              <th>各方出资金额及占比</th>
              <td colspan="2">{{data.proInfoTradeStructure.ratioAmount}}</td>
            </tr>
            <tr>
            <td colspan="6">
            <a v-for="file in data.proInfoTradeStructure.ratioAmountFileList" :key="file.$index" class="file-container" @click="goPdf(file)">
            <i class="file-icon iconfont icon-pdf" />
            <div class="file-info" style="display: inline-block;">
              <div class="file-name">{{file.fileName}}</div>
            </div>
          </a>
              </td>
            </tr>
              
              <tr>
              <th>各方出资安排</th>
              <td colspan="2">{{data.proInfoTradeStructure.fundArrangements}}</td>
            </tr>
            <tr>
            <td colspan="6">
            <a v-for="file in data.proInfoTradeStructure.fundArrangementsFileList" :key="file.$index" class="file-container" @click="goPdf(file)">
            <i class="file-icon iconfont icon-pdf" />
            <div class="file-info" style="display: inline-block;">
              <div class="file-name">{{file.fileName}}</div>
            </div>
          </a>
              </td>
            </tr>

          <tr>
              <th>投资期限</th>
              <td colspan="2">{{data.proInfoTradeStructure.termInvestment}}</td>
            </tr>
            <tr>
            <td colspan="6">
            <a v-for="file in data.proInfoTradeStructure.termInvestmentFileList" :key="file.$index" class="file-container" @click="goPdf(file)">
            <i class="file-icon iconfont icon-pdf" />
            <div class="file-info" style="display: inline-block;">
              <div class="file-name">{{file.fileName}}</div>
            </div>
          </a>
              </td>
            </tr>

            <tr>
              <th>投资利率</th>
              <td colspan="2">{{data.proInfoTradeStructure.rateInvestment}}</td>
            </tr>
            <tr>
            <td colspan="6">
            <a v-for="file in data.proInfoTradeStructure.rateInvestmentFileList" :key="file.$index" class="file-container" @click="goPdf(file)">
            <i class="file-icon iconfont icon-pdf" />
            <div class="file-info" style="display: inline-block;">
              <div class="file-name">{{file.fileName}}</div>
            </div>
          </a>
              </td>
            </tr>
          </table>

          <table v-show="open" class="info-table" style="word-wrap:break-word; word-break:break-all;" v-if="data.investmentTarget == 'ABS优先、夹层或劣后'">
              <tr>
              <th rowspan="18" class="top-th">交易结构（ABS优先、夹层和劣后）</th>
            </tr>
            <tr>
              <th style="width: 100px;">ABS发行金额、分层占比、及分层评级结果</th>
              <td colspan="4">{{data.proInfoTradeStructure.ratingResult}}</td>
            </tr>
            <tr>
            <td colspan="6">
            <a v-for="file in data.proInfoTradeStructure.ratingResultFileList" :key="file.$index" class="file-container" @click="goPdf(file)">
            <i class="file-icon iconfont icon-pdf" />
            <div class="file-info" style="display: inline-block;">
              <div class="file-name">{{file.fileName}}</div>
            </div>
          </a>
              </td>
            </tr>
              <tr>
              <th>原始权益人出表与否</th>
              <td colspan="2">{{data.proInfoTradeStructure.isOriginalList}}</td>
            </tr>
            <tr>
            <td colspan="6">
            <a v-for="file in data.proInfoTradeStructure.isOriginalListFileList" :key="file.$index" class="file-container" @click="goPdf(file)">
            <i class="file-icon iconfont icon-pdf" />
            <div class="file-info" style="display: inline-block;">
              <div class="file-name">{{file.fileName}}</div>
            </div>
          </a>
              </td>
            </tr>
              
              <tr>
              <th>底层资产投放标准</th>
              <td colspan="2">{{data.proInfoTradeStructure.underlyingAssetsStandard}}</td>
            </tr>
            <tr>
            <td colspan="6">
            <a v-for="file in data.proInfoTradeStructure.underlyingAssetsStandardFileList" :key="file.$index" class="file-container" @click="goPdf(file)">
            <i class="file-icon iconfont icon-pdf" />
            <div class="file-info" style="display: inline-block;">
              <div class="file-name">{{file.fileName}}</div>
            </div>
          </a>
              </td>
            </tr>

          <tr>
              <th>投资期限</th>
              <td colspan="2">{{data.proInfoTradeStructure.termInvestment}}</td>
            </tr>
            <tr>
            <td colspan="6">
            <a v-for="file in data.proInfoTradeStructure.termInvestmentFileList" :key="file.$index" class="file-container" @click="goPdf(file)">
            <i class="file-icon iconfont icon-pdf" />
            <div class="file-info" style="display: inline-block;">
              <div class="file-name">{{file.fileName}}</div>
            </div>
          </a>
              </td>
            </tr>

            <tr>
              <th>投资利率</th>
              <td colspan="2">{{data.proInfoTradeStructure.rateInvestment}}</td>
            </tr>
            <tr>
            <td colspan="6">
            <a v-for="file in data.proInfoTradeStructure.rateInvestmentFileList" :key="file.$index" class="file-container" @click="goPdf(file)">
            <i class="file-icon iconfont icon-pdf" />
            <div class="file-info" style="display: inline-block;">
              <div class="file-name">{{file.fileName}}</div>
            </div>
          </a>
              </td>
            </tr>
            <tr>
              <th>原始权益人是否履行回购义务</th>
              <td colspan="2">{{data.proInfoTradeStructure.isOriginalBuyback}}</td>
            </tr>
            <tr>
            <td colspan="6">
            <a v-for="file in data.proInfoTradeStructure.isOriginalBuybackFileList" :key="file.$index" class="file-container" @click="goPdf(file)">
            <i class="file-icon iconfont icon-pdf" />
            <div class="file-info" style="display: inline-block;">
              <div class="file-name">{{file.fileName}}</div>
            </div>
          </a>
              </td>
            </tr>
            <tr>
              <th>违约事件阈值及内容设定</th>
              <td colspan="2">{{data.proInfoTradeStructure.breachContent}}</td>
            </tr>
            <tr>
            <td colspan="6">
            <a v-for="file in data.proInfoTradeStructure.breachContentFileList" :key="file.$index" class="file-container" @click="goPdf(file)">
            <i class="file-icon iconfont icon-pdf" />
            <div class="file-info" style="display: inline-block;">
              <div class="file-name">{{file.fileName}}</div>
            </div>
          </a>
              </td>
            </tr>
            <tr>
              <th>违约事件发生前和发生后分配顺序的比较</th>
              <td colspan="2">{{data.proInfoTradeStructure.breachCompare}}</td>
            </tr>
            <tr>
            <td colspan="6">
            <a v-for="file in data.proInfoTradeStructure.breachCompareFileList" :key="file.$index" class="file-container" @click="goPdf(file)">
            <i class="file-icon iconfont icon-pdf" />
            <div class="file-info" style="display: inline-block;">
              <div class="file-name">{{file.fileName}}</div>
            </div>
          </a>
              </td>
            </tr>
          </table>

          <table v-show="open" class="info-table" style="word-wrap:break-word; word-break:break-all;" v-if="data.investmentTarget == '满足客户的具体需求'">
              <tr>
              <th rowspan="10" class="top-th">交易结构（种子基金项目）</th>
            </tr>
            <tr>
              <th style="width: 100px;">基金总金额</th>
              <td colspan="4">{{data.proInfoTradeStructure.totalFundAmount}}</td>
            </tr>
            <tr>
            <td colspan="6">
            <a v-for="file in data.proInfoTradeStructure.totalFundAmountFileList" :key="file.$index" class="file-container" @click="goPdf(file)">
            <i class="file-icon iconfont icon-pdf" />
            <div class="file-info" style="display: inline-block;">
              <div class="file-name">{{file.fileName}}</div>
            </div>
          </a>
              </td>
            </tr>
              <tr>
              <th>种子基金占比</th>
              <td colspan="2">{{data.proInfoTradeStructure.seedFundProportion}}</td>
            </tr>
            <tr>
            <td colspan="6">
            <a v-for="file in data.proInfoTradeStructure.seedFundProportionFileList" :key="file.$index" class="file-container" @click="goPdf(file)">
            <i class="file-icon iconfont icon-pdf" />
            <div class="file-info" style="display: inline-block;">
              <div class="file-name">{{file.fileName}}</div>
            </div>
          </a>
              </td>
            </tr>
              
              <tr>
              <th>预计收取的基金管理费</th>
              <td colspan="2">{{data.proInfoTradeStructure.estimatedManagementFee}}</td>
            </tr>
            <tr>
            <td colspan="6">
            <a v-for="file in data.proInfoTradeStructure.estimatedManagementFeeFileList" :key="file.$index" class="file-container" @click="goPdf(file)">
            <i class="file-icon iconfont icon-pdf" />
            <div class="file-info" style="display: inline-block;">
              <div class="file-name">{{file.fileName}}</div>
            </div>
          </a>
              </td>
            </tr>

          <tr>
              <th>种子基金投资范围或产品</th>
              <td colspan="2">{{data.proInfoTradeStructure.scopeInvestment}}</td>
            </tr>
            <tr>
            <td colspan="6">
            <a v-for="file in data.proInfoTradeStructure.scopeInvestmentFileList" :key="file.$index" class="file-container" @click="goPdf(file)">
            <i class="file-icon iconfont icon-pdf" />
            <div class="file-info" style="display: inline-block;">
              <div class="file-name">{{file.fileName}}</div>
            </div>
          </a>
              </td>
            </tr>

            <tr>
              <th>基金风控和投资风险安排</th>
              <td colspan="2">{{data.proInfoTradeStructure.riskArrangement}}</td>
            </tr>
            <tr>
            <td colspan="6">
            <a v-for="file in data.proInfoTradeStructure.riskArrangementFileList" :key="file.$index" class="file-container" @click="goPdf(file)">
            <i class="file-icon iconfont icon-pdf" />
            <div class="file-info" style="display: inline-block;">
              <div class="file-name">{{file.fileName}}</div>
            </div>
          </a>
              </td>
            </tr>
          </table>

          <table v-show="open" class="info-table" style="word-wrap:break-word; word-break:break-all;">
              <tr>
              <th colspan="2">综合成本(元)</th>
              <td colspan="2">{{data.cost}}</td>
            </tr>
            <tr>
              <th style="width:200px">产品期限(年)</th>
              <td colspan="2">{{data.term}}</td>
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
            <tr>
              <th colspan="2">项目前端收入</th>
              <td colspan="2">{{data.frontIncome}}</td>
            </tr>
            <tr>
              <th colspan="2">项目后端收入</th>
              <td colspan="2">{{data.afterIncome}}</td>
            </tr>
          </table>
        </div>
        <div v-show="open" class="attachment-area">
          <div class="area-title">附件</div>
          <a v-for="file in data.fileList" :key="file.$index" class="file-container" @click="goPdf(file)">
           <!-- <a v-for="file in data.fileList" :key="file.$index" :href="'/api'+file.filePath" class="file-container"> -->
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
              <!-- <div class="advice showdoneDetail" @click="showdoneDetail">审批意见：{{item.result}}</div> -->
              <!-- <div class="advice">审批意见：<span>{{item.result}}</span></div> -->
              <div v-if="item.resultType != '提交申请'"  class="advice">审批意见：{{item.result}}</div>
              <div v-if="item.resultType == '提交申请'"  class="advice">备注：{{item.result}}</div>
               <!-- <span class="open_down" v-show="vision" @click="showdoneDetail">点击展开全部 <img class="bg_icon" :src="require('@/assets/images/icon_dropdown.png')" /></span> -->
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
    this.$api.proDetailList({ id: this.$route.query.orderId }).then(res => {
      this.data = res.data;
      console.log(this.data);
      let arr = []
      let brr=[]
      brr = res.data.teamList
      if (this.data.investmentTarget == 1) {
          this.data.investmentTarget = "Pre-ABS"
        }else if(this.data.investmentTarget ==2){
          this.data.investmentTarget = "ABS优先、夹层或劣后"
        }else if(this.data.investmentTarget == 3){
          this.data.investmentTarget = "RQFII基金种子"
        }else if(this.data.investmentTarget == 4){
          this.data.investmentTarget = '其他'
        }

        if (this.data.investmentPurpose == 1) {
        this.data.investmentPurpose = "撬动ABS业务"
      }else if(this.data.investmentPurpose ==2){
        this.data.investmentPurpose = "撬动外部资金方"
      }else if(this.data.investmentPurpose == 3){
        this.data.investmentPurpose = "满足客户的具体需求"
      }else if(this.data.investmentPurpose == 4){
        this.data.investmentPurpose = '满足我方的需求'
      }
     if (brr) {
        for (let i = 0; i < brr.length; i++) {
        arr.push(brr[i].fullName)
      }
     }
      this.data.team = arr.toString()
      
    });
    this.$api.getHistoryAudit({ id: this.$route.query.orderId }).then(res => {
      this.progressHistory = res.data;
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
