<style lang="less" scoped>
@border_color: rgba(204, 204, 204, 0.5);
.product_layout {
  padding: 40px;
  color: #212121;
  width: 1041px;
  margin: 0 auto;
  button {
    height: 40px;
    font-size: 14px;
  }
}
.product_name {
  font-size: 24px;
  font-weight: 500;
  line-height: 40px;
}
.product_team {
  margin-top: 30px;
  color: #626262;
  font-size: 16px;
  line-height: 40px;
}
.tab_name {
  font-size: 16px;
  font-weight: 500;
  line-height: 23px;
  margin-top: 25px;
}
.product_info {
  width: 100%;
  border: 1px solid @border_color;
  border-collapse: collapse;
  border-spacing: 0;
  font-size: 14px;
  tr {
    border-bottom: 1px solid @border_color;
    td {
      height: 50px;
      text-align: center;
      border-right: 1px solid #ccc;
      color: #626262;
    }
    .pro {
      padding: 20px 0;
    }
    .info_key {
      width: 174px;
      // background: rgba(240, 242, 245, 0.4);
      padding: 10px 0px;
    }
    input {
      width: 100%;
      line-height: 50px;
      border: 0;
      padding: 0px 7px;
      &:focus {
        // border-color: #57a3f3;
        outline: #57a3f3;
      }
    }
    .txt {
      padding: 0 7px;
      color: #212121;
    }
    textarea {
      width: 100%;
      height: 95%;
      border: 0;
      padding: 5px;
      &:focus {
        outline: 0;
      }
    }
  }
  .ivu-radio-wrapper {
    margin-right: 20px;
  }
}
.upload_tips {
  color: red;
  display: inline-block;
  margin-left: 20px;
  position: absolute;
  top: 11px;
  left: 80px;
}
.file_item {
  margin-top: 10px;
  span,
  a {
    margin: 0 10px;
    vertical-align: middle;
  }
  .iconfont {
    vertical-align: middle;
    font-size: 22px;
  }
  .icon-file_pdf {
    color: #ff545c;
  }
  .icon-shanchu_o {
    color: #72808c;
    cursor: pointer;
    &:hover {
      color: red;
    }
  }
}
.history_list {
  border: 1px solid #ccc;
  margin-top: 20px;
  .history_item {
    padding: 20px 0 20px 30px;
    font-size: 16px;
    color: #818181;
    border-bottom: 1px solid #ccc;
    &:last-child {
      border: 0;
    }
  }
  .disagree {
    color: #f92c2c;
  }
}
textarea {
  resize: none;
}
.margin-right-20 {
  border-radius: 5px;
}
.btnselect{
  height: 50px;
  
}
.btnselect ivu-select ivu-select-single ivu-select-default{
  .ivu-select-selection{
    border: none;
  }
}
.product_info tr textarea{
  height: 55px;
}
.addImg{
  width:20px;
  height:20px;
  position: absolute;
  right: -20px;
  margin-top: -35px;
  img{
    display:block;
    width: 100%;
    height: 100%;

  }
}
</style>
<!-- 项目详情
@props
 isEdit 是否为可编辑状态
 id 编辑id
 fundInfo 预览or编辑数据
 -->
<template>
    <div class="product_layout">
        <Row class="t-c product_name">
            <!-- <input type="text"> -->
            {{baseInfo.proName}}项目简介表
        </Row>
        <Row class="product_team">
            <p class="f-l">
                项目团队：
                <span v-if="isEdit" style="color:#313131">{{baseInfo.team}}</span>
                <!-- <Input  style="width:290px;font-size:16px;" v-model="baseInfo.team" v-else></Input> -->
                 <Select v-model="model10" filterable multiple remote :remote-method="remoteMethod2" :loading="loading2" style="width:285px" v-else  >
                    <Option v-for="item in label" :value="item.userName" :key="item.value" :label="item.fullName">
                      <span>{{item.fullName}}</span>
                      <span style="float:right;color:#ccc">{{item.userName}}</span>
                    </Option>
                </Select>
            </p>
            <p class="f-r">
                日期：<span>{{baseInfo.date}}</span>
            </p>
        </Row>
        <Row class="tab_name">
            项目基本信息
        </Row>
        <Row style="margin-top:15px">
            <table class="product_info">
                <tbody>
                    <tr>
                        <td class="info_key">项目名称</td>
                        <td colspan="6">
                            <input type="text" v-model="baseInfo.proName" :readonly="isEdit">
                        </td>
                    </tr>
                    <tr>
                        <td class="info_key">业务模式</td>
                        <td colspan="3">
                            <template v-if="isEdit">
                                <div class="t-l txt" >{{baseInfo.proModel}}</div>
                            </template>
                            <CheckboxGroup v-model="baseInfo.proModel" v-else>
                                <Checkbox label="存量债转">存量债转</Checkbox>
                                <Checkbox label="新增助贷">新增助贷</Checkbox>
                            </CheckboxGroup>
                        </td>
                        <td class="info_key">
                            行业分类
                        </td>
                        <td colspan="2">
                            <input type="text" v-model="baseInfo.category" :readonly="isEdit">
                        </td>
                    </tr>
                     <tr>
                        <td class="info_key">基础资产类型</td>
                        <td colspan="3">
                            <input type="text" v-model="baseInfo.assertType" :readonly="isEdit">
                        </td>
                        <td class="info_key">基础资产情况</td>
                        <td colspan="3">
                            <input type="text" v-model="baseInfo.assertSituation" :readonly="isEdit">
                        </td>
                    </tr>
                    <tr>
            <td class="info_key">资产服务机构</td>
            <td colspan="6">
               <template v-if="isEdit">
                <div class="t-l txt"><span v-show="changeData.assetSerAgency" class="text_line">{{changeData.assetSerAgency}}<br></span>
                {{baseInfo.assetSerAgency}}</div>
              </template>
              <textarea v-model="baseInfo.assetSerAgency" v-else style="height:auto;"></textarea>
            </td>
          </tr>
          <tr>
            <td class="info_key">劣后投资人</td>
            <td colspan="6">
               <template v-if="isEdit">
                <div class="t-l txt"><span v-show="changeData.inferiorInvestor" class="text_line">{{changeData.inferiorInvestor}}<br></span>
                {{baseInfo.inferiorInvestor}}</div>
              </template>
              <textarea v-model="baseInfo.inferiorInvestor" v-else style="height:auto;"></textarea>
            </td>
          </tr>
          <tr>
            <td class="info_key">差额补足机构</td>
            <td colspan="6">
               <template v-if="isEdit">
                <div class="t-l txt"><span v-show="changeData.diffAgency" class="text_line">{{changeData.diffAgency}}<br></span>
                {{baseInfo.diffAgency}}</div>
              </template>
              <textarea v-model="baseInfo.diffAgency" v-else style="height:auto;"></textarea>
            </td>
          </tr>
                    <tr>
          </tr>
          
          <tr>
             <td class="info_key">12个月内预计
              <br>合作规模(亿)
            </td>
            <td colspan="2">
              <input type="tel" v-model="baseInfo.coopScale" :readonly="isEdit">
            </td>
            <td class="info_key">已合作规模</td>
            <td colspan="1">
              <input type="tel" v-model="baseInfo.scaleCooperation" :readonly="isEdit">
            </td>
            <td class="info_key">存量余额</td>
            <td colspan="1">
              <input type="tel" v-model="baseInfo.stockBalance" :readonly="isEdit">
            </td>
          </tr>
          <tr>
            <td class="info_key">是否同意框架合作</td>
            <td colspan="1">
              <template v-if="isEdit">
                <div class="t-l txt">{{baseInfo.coopState}}</div>
              </template>
              <RadioGroup v-model="baseInfo.coopState" v-else>
                <Radio label="是"></Radio>
                <Radio label="否"></Radio>
              </RadioGroup>
            </td>
            <td class="info_key">是否同意系统对接</td>
            <td colspan="1">
              <template v-if="isEdit">
                <div class="t-l txt">{{baseInfo.systemAbutmentState}}</div>
              </template>
              <RadioGroup v-model="baseInfo.systemAbutmentState" v-else>
                <Radio label="是"></Radio>
                <Radio label="否"></Radio>
              </RadioGroup>
            </td>
             <td class="info_key">是否需要新分享投资</td>
            <td colspan="2">
              <template v-if="isEdit">
                <div class="t-l txt">{{baseInfo.investComment}}</div>
              </template>
              <RadioGroup v-model="baseInfo.investComment" v-else>
                <Radio label="是"></Radio>
                <Radio label="否"></Radio>
              </RadioGroup>
            </td>
          </tr>
          <tr v-show="weshareShow">
            <td class="info_key">投资内容、<br>利率、期限</td>
            <td colspan="6">
              <textarea
                v-model="baseInfo.whetherWeshareInvest"
                :readonly="isEdit"
                style="height: auto;"
              ></textarea>
            </td>
          </tr>
                     <tr>
                        <td class="info_key">原始权益人</td>
                       <td colspan="3">
                            <!-- <input type="text" v-model="baseInfo.originalRightsHolder" :readonly="isEdit"> -->
                            <template v-if="isEdit">
                                <div class="t-l txt" >{{baseInfo.originalRightsHolder}}</div>
                            </template>
                            <Select v-model="baseInfo.originalRightsHolder" class="btnselect" v-else>
                              <Option value=" "><div  @click="addPepole">新增权益人</div></Option>
                              <Option v-for="(item,index) in model9" :value="item.usename" :key="index" :label="item.usename" >
                                  <span>{{item.usename}}</span>
                              </Option>
                          </Select>
                        </td>
                        <td class="info_key">评级情况</td>
                        <td colspan="3">
                            <input type="text" v-model="baseInfo.holderRatingSituation" :readonly="isEdit">
                        </td>
                    </tr>
                    <tr>
                        <td class="info_key">外部增信机构</td>
                        <td colspan="3">
                            <!-- <input type="text" v-model="baseInfo.externalEnhancementAgencies" :readonly="isEdit"> -->
                            <textarea v-model="baseInfo.externalEnhancementAgencies" :readonly="isEdit"></textarea>
                        </td>
                        <td class="info_key">评级情况</td>
                        <td colspan="3">
                            <input type="text" v-model="baseInfo.ratingSituation" :readonly="isEdit">
                        </td>
                    </tr>
                    <tr>
                        <td class="info_key pro" :rowspan="LengthRow">分层结构</td>
                        <td class="info_key">分级</td>
                        <td class="info_key">发行金额(万元)</td>
                        <td class="info_key">占比(%)</td>
                        <td class="info_key">发行利率(%)</td>
                        <td class="info_key">产品期限(月)</td>
                        <td class="info_key">加权平均期限(年)</td>
                    </tr>
                    <tr v-for="item in layerList">
                        <td>
                            <input type="text" v-model="item.level" :readonly="isEdit">
                        </td>
                        <td>
                            <input type="tel" v-model="item.amount" :readonly="isEdit">
                        </td>
                        <td>
                            <input type="tel" v-model="item.proportion" :readonly="isEdit">
                        </td>
                        <td>
                            <textarea class="text" v-model="item.issueRate" :readonly="isEdit"></textarea>
                        </td>
                        <td>
                            <input type="text" v-model="item.proTerm" :readonly="isEdit">
                        </td>
                        <td>
                            <input type="text" v-model="item.averageTerm" :readonly="isEdit">
                        </td>
                    </tr>
                    <tr>
                        <td class="info_key">发行金额(含次级)<br>亿元</td>
                        <td colspan="2">
                            <input type="text" v-model="baseInfo.totalAmount" :readonly="isEdit">
                        </td>
                        <td class="info_key">
                            综合成本(元)
                        </td>
                        <td colspan="1">
                            <input type="tel" v-model="baseInfo.cost" :readonly="isEdit">
                        </td>
                        <td class="info_key">
                            产品期限(年)
                        </td>
                        <td colspan="1">
                            <input type="tel" v-model="baseInfo.term" :readonly="isEdit">
                        </td>
                    </tr>
                    <tr>
                       <td colspan="7">
                          <span v-if="isEdit">{{baseInfo.requirements}}</span>
                            <RadioGroup v-model="baseInfo.requirements" v-else>
                                <Radio label="静态池"></Radio>
                                <Radio label="循环结构"></Radio>
                            </RadioGroup>
                            <span v-show="isshow">循环期【<input type="text" style="width:50px;text-align:center" v-model="baseInfo.cyclePeriod" :readonly="isEdit">】+ 摊还期【<input :readonly="isEdit" type="text" style="width:50px;text-align:center" v-model="baseInfo.amortizationPeriod">】个月</span>
                        </td>
                    </tr>
                    <tr>
                        <td class="info_key">分销渠道</td>
                        <td colspan="6">
                            <span v-if="isEdit">{{baseInfo.channel}}</span>
                            <Select v-model="baseInfo.channel" style="width:100%" :readonly="isEdit" v-else>
                                <Option v-for="item in model8" :value="item.channel" :key="item.channel">{{ item.channel }}</Option>
                            </Select>
                            <span v-show="channel">理财通分【<input type="text" style="width:50px;text-align:center" v-model="baseInfo.packageNum" :readonly="isEdit">】包发行，单包规模【<input :readonly="isEdit" type="text" style="width:100px;text-align:center;minWidth:50px;" v-model="baseInfo.packageScale" onkeyup="this.value=this.value.replace(/[^0-9-]+/,'');" >】万元</span>
                        </td>
                    </tr>
                     <!-- <tr>
                        <td colspan="7">
                            理财通分【<input type="tel" style="width:50px;text-align:center" v-model="baseInfo.packageNum" :readonly="isEdit">】包发行，单包规模【<input :readonly="isEdit" type="number" style="width:50px;text-align:center" v-model="baseInfo.packageScale">】万元
                        </td>
                    </tr> -->
                    <tr>
                        <td class="info_key">项目前端收入</td>
                        <td colspan="3">
                            <textarea name="" id="" rows="5" v-model="baseInfo.frontIncome" :readonly="isEdit"></textarea>
                        </td>
                        <td class="info_key">项目后端收入</td>
                        <td colspan="2">
                            <textarea name="" id="" rows="5" v-model="baseInfo.afterIncome" :readonly="isEdit"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td class="info_key" >项目团队意见</td>
                        <td colspan="6" >
                            <textarea style="" name="" rows="5" placeholder="项目亮点、项目调整、前期与腾讯业务及风控团队交流情况等" v-model="baseInfo.projectTeamOpinion" :readonly="isEdit"></textarea>
                        </td>
                    </tr>
                    <template v-if="reviewInfo">
                        <tr v-for="item in baseInfo.resultList">
                            <td class="info_key">{{item.resultType}}</td>
                            <td>{{item.resultState}}</td>
                            <td colspan="5" style="padding:5px;text-align:left">
                                {{item.result}}
                            </td>
                        </tr>
                    </template>
                </tbody>
            </table>
        </Row>
      <Row class="tab_name" v-show="EnclosureShow">{{"附件列表"}}</Row>
      <Row class="tab_name" v-show="!EnclosureShow">{{isEdit ? '项目附件' : '上传附件'}}</Row>
      <div class="file_item" v-for="(item,index) in baseInfo.fileList" v-show="!EnclosureShow" :key="index">
      <i class="iconfont icon-file_pdf"></i>
      <template v-if="!isEdit">
        <span style>{{item.fileName}}</span>
        <i class="iconfont icon-shanchu_o" title="删除" @click="removeFile(index)"></i>
      </template>
      <template v-else>
        <a @click="displayPdf(item)">{{item.fileName}}</a>
      </template>
    </div>
    </div>
</template>

<script>
import axios from 'axios';
import mdContract from "./../common/contract.md.vue";
export default {
  computed: {
    layerListLength() {
      return this.baseInfo.layerList ? this.baseInfo.layerList.length + 1 : 1;
    },
    reviewInfo() {
      return this.baseInfo.resultList ? true : false;
    },
    channel(){
    return this.baseInfo.channel === '理财通' ? true :false;
    },
    isshow(){
      if (this.baseInfo.requirements === '循环结构') {
          return true;
      } else {
          return false;
      }
    },
    weshareShow(){
      return this.baseInfo.investComment === "是" ? true : false;
    }
  },
  data() {
    return {
      label:[],
      model10:[],
      LengthRow:6,
      changeData:"",
      EnclosureShow: false, //是否显示附件列表
      model9:[],
      model8:[],
      layerList: [
        {
          level: "",
          amount: "",
          proportion: "",
          issueRate: "",
          proTerm: "",
          averageTerm: ""
        },
        {
          level: "",
          amount: "",
          proportion: "",
          issueRate: "",
          proTerm: "",
          averageTerm: ""
        },
        {
          level: "",
          amount: "",
          proportion: "",
          issueRate: "",
          proTerm: "",
          averageTerm: ""
        },
        {
          level: "",
          amount: "",
          proportion: "",
          issueRate: "",
          proTerm: "",
          averageTerm: ""
        },
        {
          level: "",
          amount: "",
          proportion: "",
          issueRate: "",
          proTerm: "",
          averageTerm: ""
        }
      ], //分层结构
      fileList: [], //文件集合
      historyAudit: [], //历史审批
      status:"",
      isEdit: true,
      wsid:"",
      orderId: "",
      baseInfo: {
        date: new Date().format("yyyy/MM/dd"), //日期
        team: "", //团队
        proName: "", //项目名称
        proModel: [], //业务模式
        category: "", //行业分类
        assertSituation: "", //基础资产情况
        assertType: "", //基础资产类型
        assetSerAgency: "", //资产服务机构
        coopScale: "", //委托人预计全年合作规模
        coopState: "", //是否同意框架合作及系统对接
        totalAmount: "", //发行金额含次级
        cost: "", //综合成本
        term: "", //期限
        channel: "", //分销渠道
        packageNum: "", //理财通分包
        packageScale: "", //单包规模
        frontIncome: "", //项目前端收入
        afterIncome: "", //项目后端收入
        originalRightsHolder: "", //原始权益人
        holderRatingSituation: "", //评级情况
        externalEnhancementAgencies: "", //外部增信机构
        ratingSituation: "", //评级情况
        cyclePeriod: "", //循环期
        requirements:'静态池',//静态池，循环结构
        amortizationPeriod: "", //摊还期
        projectTeamOpinion: "", //项目团队意见
        fileList: [],
        layerList: [],
        systemAbutmentState:"",//是否同意系统对接
        scaleCooperation:"",//已合作规模
        stockBalance:"",//存量余额
        whetherWeshareInvest:"",//投资内容、利率、期限
        coopState:"",//是否同意框架合作
        investComment:""//是否需要新分享投资
        

      },
      checkTips: {
        coopScale: "12个月内预计合作规模应填写11位以内正整数",
        amount: "发行金额应填写11位以内正整数",
        proportion: "占比应填写11位以内正整数",
        issueRate: "发行利率应填写11位以内正整数",
        proTerm: "产品期限应填写11位以内正整数",
        averageTerm: "加权平均期限应填写11位以内正整数",
        totalAmount: "发行金额(含次级)亿元应填写11位以内正整数",
        cost: "综合成本应填写11位以内正整数",
        term: "产品年限应填写11位以内正整数",
        cyclePeriod: "循环期应填写11位以内正整数",
        amortizationPeriod: "摊还期应填写11位以内正整数",
        assertType: "基础资产类型不能为空",
        assertSituation: "基础资产情况不能为空",
        originalRightsHolder: "原始权益人不能为空",
        holderRatingSituation: "评级情况不能为空",
        packageNum: "理财通分包不能为空",
        packageScale: "单包规模不能为空",
        channel: "分销渠道不能为空",
        frontIncome: "项目前端收入不能为空",
        afterIncome: "项目后端收入不能为空",
        projectTeamOpinion: "项目团队意见不能为空"
      }
    };
  },

  created() {
    if (this.$route.query.wsid) {
      this.wsid = this.$route.query.wsid;
    }
  },
  mounted() {
    if (this.$route.query.wsid) {
        axios
        .get("/api/snaker/flow/getProDraftById?id=" + this.$route.query.wsid)
        .then(res => {
          let arr = [];
          let Num = res.data.data;
          if (res.data.code == "000") {
            this.baseInfo = res.data.data;
            this.status = res.data.data.status
            let proModel = res.data.data.proModelList;
            let proModelArr = [];
            console.log(this.baseInfo);
            
            let time = this.baseInfo.proStarTime
            for (let i = 0; i < this.baseInfo.teamList.length; i++) {
              arr.push(this.baseInfo.teamList[i].fullName);
            }
            this.baseInfo.team = arr.toString();
            for (let i = 0; i < proModel.length; i++) {
              proModelArr.push(proModel[i].name)
            }
            console.log(proModelArr);
            

            res.data.data.proModel = proModelArr.toString()
            this.baseInfo.proModelReader = res.data.data.proModel.toString();
            this.baseInfo = res.data.data;
            console.log(this.baseInfo.layerList);
        for (let index = 0; index < this.baseInfo.layerList.length; index++){
          this.layerList[index].level = this.baseInfo.layerList[index].level;
          this.layerList[index].amount = this.baseInfo.layerList[index].amount;
          this.layerList[index].proportion = this.baseInfo.layerList[index].proportion;
          this.layerList[index].issueRate = this.baseInfo.layerList[index].issueRate;
          this.layerList[index].proTerm = this.baseInfo.layerList[index].proTerm;
          this.layerList[index].averageTerm = this.baseInfo.layerList[index].averageTerm;
      }
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    }
  },
  methods: {
       displayPdf(item) {
         if (this.status =="") {
           this.status = "-1"
         }
      let url = "/api/snaker/flow/attach/" + item.id + "/" + this.status;
      window.open("/static/pdf/web/viewer.html?file=" + url);
    },
  }
   
};
</script>





