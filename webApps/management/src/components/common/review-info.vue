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
.product_info tr textarea{
  height: 55px;
}
.addImg {
  width: 20px;
  height: 20px;
  margin-left: 320px;
  cursor: pointer;
  img {
    display: block;
    width: 100%;
    height: 100%;
  }
}
.reduce{
  width: 20px;
  height: 20px;
  position: absolute;
  right: 400px;
  margin-top: -20px;
  cursor: pointer;
    img {
    display: block;
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
                <span v-if="isEdit" style="color:#313131">{{baseInfo.teamList}}</span>
                <!-- <Input  style="width:290px;font-size:16px;" v-model="baseInfo.team" v-else></Input> -->
                 <Select v-model="model10" filterable multiple remote style="width:285px" v-else  >
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
                    <tr v-for="(item,index) in layerList" :key="index">
                        <td>
                            <input type="text" v-model="item.level" :readonly="isEdit">
                        </td>
                        <td>
                            <input type="Number" v-model="item.amount" @mousewheel.prevent :readonly="isEdit">
                        </td>
                        <td>
                            <input type="Number" v-model="item.proportion" @mousewheel.prevent :readonly="isEdit">
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
                      <td colspan="6" v-show="!isEdit">
                      <div class="addImg" ref="add" @click="AddDom"><img src="@/assets/images/round_add.png" alt></div>
                      <div class="reduce" ref="reduce" @click="reduce"><img src="@/assets/images/round_reduce.png" alt></div>
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
                          <span v-if="isEdit">{{requirements}}</span>
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
                            <Select v-model="baseInfo.channel" style="width:100%" :readonly="isEdit" v-else class="channel">
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
                            <textarea name="" id="" rows="5" placeholder="项目亮点、项目调整、前期与腾讯业务及风控团队交流情况等" v-model="baseInfo.projectTeamOpinion" :readonly="isEdit"></textarea>
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
        <Row class="tab_name">上传附件</Row>
        </Row>
           <div class="file_item" v-for="(item,index) in baseInfo.fileList">
                <i class="iconfont icon-file_pdf"></i>
                <template>
                    <a :href="'/api/snaker/flow/attach/'+item.id" target="_blank" download>{{item.fileName}}</a>
                     <i class="iconfont icon-shanchu_o" title="删除" @click="removeFile(index)"></i>
                </template>
            </div>
        <Row style="margin-top:15px">
            <template>
                <Upload :before-upload="handleUpload" action="" :format="['pdf']">
                    <Button icon="ios-cloud-upload-outline">上传</Button>
                </Upload>
                <span class="upload_tips">*仅支持PDF格式文件</span> 
            </template>
        </Row>
        <Row style="margin:30px 0 0 0;text-align:right" >
          <Button type="info" style="border-radius: 5px;position: relative;left: -80px;" @click="Preservation">保存数据</Button>
            <Button type="primary" class="margin-right-20" @click="addAudit">提交审核</Button>
        </Row>
    </div>
</template>

<script>
import axios from 'axios';
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
      LengthRow:7,
      model9:[],
      model8:[],
      loading2:"",
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
      isEdit: false,
      reviewFlase:false,
      wait:false,
      orderId: "",
      back:false,
      queryid:"",
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
        investComment:"",//是否需要新分享投资
        inferiorInvestor:"",//劣后投资人
        diffAgency:"",//差额补足机构

      },
      checkTips: {
        // date: "日期不能为空",
        // team: "项目团队不能为空",
        // proName: "项目名称不能为空",
        // proModel: "请选择业务模式",
        // category: "行业分类不能为空",
        // coopState: "请选择是否同意框架合作及系统对接",
        // externalEnhancementAgencies: "外部增信机构不能为空",
        // ratingSituation: "评级情况不能为空",
        // assetSerAgency: "资产服务机构劣后投资人不能为空",
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
        projectTeamOpinion: "项目团队意见不能为空",
        inferiorInvestor:'劣后投资人不能为空',
        diffAgency:'差额补足机构不能为空',
        assetSerAgency:"资产服务机构不能为空"
      }
    };
  },

  created() {
    if (this.$route.query.orderId) {
      this.orderId = this.$route.query.orderId;
    }
    if (this.$route.query.back) {
      this.back = this.$route.query.back;
    }
    if (this.$route.query.id) {
      this.queryid = this.$route.query.id;
    }
     if (!this.$route.query.reviewFlase) {
      this.reviewFlase = this.$route.query.reviewFlase;
    }
    if (this.$route.query.wait) {
      this.wait = this.$route.query.wait;
    }
  },
  mounted() {
    if (this.orderId && !this.back){
      loading();
      this.api
        .getReviewDetail({
          orderId: this.orderId
        })
        .then(data => {
          loadingHide();
          if (data.code === "000") {
            console.log(data);
            this.baseInfo = data.data;
            let proModel = data.data.proModelList;
            if (proModel != null) {
             let proModelArr = [];
             for (let i = 0; i < proModel.length; i++) {
               proModelArr.push(proModel[i].name)
             }
             this.baseInfo.proModel = proModelArr
           }
            data.data.channel
  
            this.model9.push({
              "usename":data.data.originalRightsHolder
            })
            if ((data.data.requirements == '静态池')) {
              this.baseInfo.cyclePeriod = ' '
              this.baseInfo.amortizationPeriod = ' '
            }
            if (!(data.data.channel == '理财通')) {
              this.baseInfo.packageNum = ''
              this.baseInfo.packageScale = ''
            }
            for (let i = 0; i < data.data.teamList.length; i++) {
              this.model10.push(data.data.teamList[i].username)
            }
            let num = data.data.layerList;
            for (let index = 0; index < this.layerList.length; index++) {
              if (num[index]) {
                this.layerList[index].level = num[index].level;
                this.layerList[index].amount = num[index].amount;
                this.layerList[index].proportion = num[index].proportion;
                this.layerList[index].issueRate = num[index].issueRate;
                this.layerList[index].proTerm = num[index].proTerm;
                this.layerList[index].averageTerm = num[index].averageTerm;
              }
            }
          } else {
            window.serviceError(data.message);
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    }
    if (this.wait) {
      console.log(this.reviewFlase);
      axios.get("/api/snaker/flow/getProDraftById?id="+this.queryid)
      .then(res=>{
        let arr = []
        if (res.data.code =="000") {
          this.baseInfo = res.data.data;
              let num = res.data.data.layerList;
            for (let index = 0; index < this.layerList.length; index++) {
              if (num[index]) {
                this.layerList[index].level = num[index].level;
                this.layerList[index].amount = num[index].amount;
                this.layerList[index].proportion = num[index].proportion;
                this.layerList[index].issueRate = num[index].issueRate;
                this.layerList[index].proTerm = num[index].proTerm;
                this.layerList[index].averageTerm = num[index].averageTerm;
              }
            }
        }else{
          serviceError(data.message);
        }

      })
      .catch(function(error) {
        console.log(error);
      });
    }

    //项目评审赋值
      if (this.back && !this.reviewFlase){
      axios.get("/api/snaker/flow/getProDraftById?id="+this.queryid)
      .then(res=>{
        let arr = []
        if (res.data.code =="000") {
          let Len = res.data.data.teamList
          this.baseInfo = res.data.data;
          let proModel = res.data.data.proModelList;
          let proModelArr = [];
          if (proModel !=null) {
            for (let i = 0; i < proModel.length; i++) {
            proModelArr.push(proModel[i].name)
            }
          }
          this.baseInfo.proModel = proModelArr
          // this.baseInfo.proModel = res.data.data.proModel.split(",");
          for (let i = 0; i < Len.length; i++) {
            this.model10.push(Len[i].username)
          }
          console.log(this.baseInfo);
          
          let num = res.data.data.layerList;
          for (let index = 0; index < this.layerList.length; index++) {
            if (num[index]) {
              this.layerList[index].level = num[index].level;
              this.layerList[index].amount = num[index].amount;
              this.layerList[index].proportion = num[index].proportion;
              this.layerList[index].issueRate = num[index].issueRate;
              this.layerList[index].proTerm = num[index].proTerm;
              this.layerList[index].averageTerm = num[index].averageTerm;
            }
          }
        }else{
          serviceError(data.message);
        }

      })
      .catch(function(error) {
        console.log(error);
      });
    }
    this.getHistoryAudit();
  axios
  .get("/api/snaker/surrogate/surrogate?proType="+"ABS")
  .then((res)=>{
    let Num = res.data.data
    for (var i = 0; i < Num.length; i++) {
      this.label.push({
        "userName":Num[i].userName,
        "fullName":Num[i].fullName,
        "id":Num[i].id
      })
    }
  })
  .catch(function (error) {
    console.log(error);
  });
  axios.get('/api/snaker/surrogate/originalRightsHolderList')
  .then((res)=>{
    let arr = [];
    arr= res.data.data
    for (let i = 0; i < arr.length; i++) {
      this.model9.push({
        'usename':arr[i]
      })
    }
  })
  .catch(function(error){
    console.log(error);
  })

  axios.get('/api/snaker/flow/channel')
  .then((res)=>{
    let arr = res.data.data.split(',');
    if (res.data.code == '000') {
      for (let i = 0; i < arr.length; i++) {
       this.model8.push({
        'channel':arr[i]
        })
      } 
    }
  })
  .catch(function(error){
  console.log(error);
})
  },
  methods: {
  remoteMethod2(query) {
    let self = this;
      if (query) {
        self.loading2 = true;
        let params = {
          name: query
        }
         axios.get('/api/snaker/surrogate/surrogate?fullName='+query)
        .then((res)=>{
          let Num = res.data.data
          for (var i = 0; i < Num.length; i++) {
            this.label.push({
              "userName":Num[i].userName,
              "fullName":Num[i].fullName,
              "id":Num[i].id
            })
          }
        })
        .catch(function (error) {
          console.log(error);
        });
        this.loading2 = false;
      }
    console.log(query);
    
  },

    changeInput(val) {
      // val = val.trim();

      let reg = /\S/;
      // if (!val) {
      //   return false;
      // } else {
      //   return true;
      // }
      if (reg.test(val)) {
        return true;
      } else {
        return false;
      }
    },
    handleUpload(file) {
      if (file.type != "application/pdf") {
        this.$Message.error("仅支持上传pdf文件");
        return false;
      }
      loading();
      let params = new FormData();
      params.append("file", file);
      params.append("proAttachType", "00");
      this.api.uploadFile(params).then(data => {
        loadingHide();
        if (data.code == "000") {
          if (this.baseInfo.fileList) {
            this.baseInfo.fileList.push({
              fileName: data.data.fileName,
              filePath: data.data.filePath,
              id: data.data.id,
              proAttachType:data.data.proAttachType,
              proId: data.data.proId,
              realFileName: data.data.realFileName,
              status: data.data.status,
              uploadPersonId: data.data.uploadPersonId,
              uploadPersonName: data.data.uploadPersonName,
            });
          } else {
            this.baseInfo.fileList = [
              { id: data.data.id.toString(),fileName: data.data.fileName,filePath:data.data.filePath,realFileName:data.data.realFileName,proId:data.data.proId}
            ];
          }
        } else {
          serviceError(data.message);
        }
      });
      return false;
    },
    removeFile(index) {
      this.baseInfo.fileList.splice(index, 1);
    },
    addAudit() {
        let params = this.getParams();
      //处理项目团队
      let arr = [];
      for (let i = 0; i < this.model10.length; i++) {
       for (let j = 0; j < this.label.length; j++) {
         if (this.label[j].userName ==this.model10[i]) {
           arr.push(this.label[j])
         }
       }
      }
      // params.teamList = JSON.stringify(arr)
      params.proType = "1";
     let ArrLen = [];
      for (let i = 0; i < this.layerList.length; i++) {
          if (this.layerList[i].level ||
              this.layerList[i].amount ||
              this.layerList[i].proportion ||
              this.layerList[i].issueRate ||
              this.layerList[i].proTerm ||
              this.layerList[i].averageTerm) {
              ArrLen.push(this.layerList[i])
          }
      }
       params.layerList = ArrLen
    if (params.requirements == '静态池') {
       params.cyclePeriod = "-";
       params.amortizationPeriod = '-'
     } else {
       params.cyclePeriod =this.baseInfo.cyclePeriod;
       params.amortizationPeriod = this.baseInfo.amortizationPeriod;
     }
     
     if (params.channel == '理财通') {
       params.packageNum = this.baseInfo.packageNum
       params.packageScale = this.baseInfo.packageScale
     } else {
       params.packageNum = '-'
       params.packageScale = '-'
     }


      //处理业务模式多选的问题
      // let proModelStr = "";
      // for (let i = 0; i < params.proModel.length; i++) {
      //   proModelStr += params.proModel[i] + " ";
      // }
      // console.log(proModelStr);
      params.proModel = null;

       //处理业务模式
      let proModelArr = []
      let proModel = this.baseInfo.proModel
      if (proModel.length !=0) {
      for (let i = 0; i < proModel.length; i++) {
          if (proModel[i] == "存量债转") {
            proModelArr.push({
              "modelId":"4",
              "code":"StockDebtTransfer",
              "name":"存量债转",
            })
          }else if(proModel[i] == "新增助贷"){
            proModelArr.push({
              "modelId":"5",
              "code":"NewLoan",
              "name":"新增助贷",
            })
          }else if(proModel[i] == "数据采购"){
            proModelArr.push({
              "modelId":"1",
              "code":"DataPurchase",
              "name":"数据采购",
            })
          }else if(proModel[i] == "新产品上线"){
            proModelArr.push({
              "modelId":"2",
              "code":"NewProductOnLine",
              "name":"新产品上线",
            })
          }else if(proModel[i] == "外包采购"){
            proModelArr.push({
              "modelId":"3",
              "code":"Outsourcing",
              "name":"外包采购",
            })
          }
        }
      }
      params.proModelList = proModelArr;
      if (!this.checkParams(params)) {
        return true;
      }
      console.log(params);
      loading();
      this.api.upReview(params).then(data => {
        console.log(data);
        loadingHide();
        if (data.code == "000") {
          window.successNotice("提交成功");
          this.$router.push("/task/initiate-list");
        } else {
          serviceError(data.message);
        }
      });
    },
    checkParams(params) {
      for (let i in params) {
        if (this.checkTips[i]) {
          let flag = this.changeInput(params[i]);

          if (!flag) {
            this.$Message.error({
              content: this.checkTips[i],
              duration: 3
            });
            return false;
          }
        }

        if (i === "layerList") {
          if (params.layerList.length === 0) {
            this.$Message.error({
              content: "分层结构至少需填写一条",
              duration: 3
            });
            return false;
          }
          let layerError = {
            level: "分级不能为空",
            amount: "发行金额应填写8位以内正整数",
            proportion: "占比应填写1-100以内的数",
            issueRate: "发行利率应填写1-100以内的数",
            proTerm: "产品期限应填写11位以内正整数",
            averageTerm: "加权平均期限不能为空"
          };
          let reg = /\S/;
          //单独判断发行金额(万元）是否是一个整数
          let reg1 = /^([0-9]{1,6})+(.[0-9]{1,3})?$/;
          for (var i = 0; i < this.layerList.length; i++) {
            if (reg.test(this.layerList[i].amount)) {
              if (!(this.layerList[i].amount >= 0.01 &&this.layerList[i].amount <= 99999999.99)){
                this.$Message.error({
                  content: `第${i + 1}条分层结构的${layerError.amount}`,
                  duration: 3
                });
                return false;
              }
            }
          }
          for (let j in params.layerList) {
            for (let k in params.layerList[j]) {
              if (!params.layerList[j][k]) {
                this.$Message.error({
                  content: `第${parseInt(j) + 1}条分层结构的${layerError[k]}`,
                  duration: 3
                });
                return false;
              } else if (!reg.test(params.layerList[j][k])) {
                this.$Message.error({
                  content: `第${j + 1}条分层结构的${layerError[k]}`,
                  duration: 3
                });
              }
            }
          }
           if (!(params.originalRightsHolder)) {
                this.$Message.error({
                content: "原始权益人不能为空，请选择一项！",
                duration: 3
              });
              return false;
            }
        }
      }
      return true;
    },
    getParams() {
      let param = JSON.parse(JSON.stringify(this.baseInfo));
      // param.fileList = this.fileList.slice(0);
      param.layerList = [];
      let arr = this.baseInfo.layerList.slice(0);
      for (let i in arr) {
        if (
          arr[i].level ||
          arr[i].amount ||
          arr[i].proportion ||
          arr[i].issueRate ||
          arr[i].proTerm ||
          arr[i].averageTerm
        ) {
          param.layerList.push(arr[i]);
        }
      }
      return param;
    },
    reset() {},
    //查询历史审批
    getHistoryAudit() {
      this.api
        .getHistoryAudit({
          id: this.orderId
        })
        .then(data => {
          if (data.code == "000") {
            this.historyAudit = data.data;
          } else {
            serviceError(data.message);
          }
        });
    },
     addPepole(){
      this.$Modal.confirm({
      render: (h) => {
          return h('Input', {
              props: {
                  title:'111',
                  value: this.value,
                  autofocus: true,
                  placeholder: '请输入新增的权益人'
              },
              on: {
                  input: (val) => {
                      this.value = val;
                  }
              }
              
          })
      },
      onOk: () => {
            if (this.value.length) {
                   axios.post('/api/snaker/surrogate/addOriginalRightsHolder?value='+this.value)
                  .then((res)=>{
                    if (res.status == '200') {
                      this.model9.push({
                        'usename':this.value
                      })
                    }
                  })
                  .catch(function(error){
                    console.log(error);
                  })
                }
              }
        });
      },
      AddDom(){
        this.layerList.push({})
        this.LengthRow = this.layerList.length+2
        console.log(this.layerList.length);
      },
      reduce() {
      console.log(this.layerList.length);
      if (this.layerList.length == 5) {
      this.$Message.error({
      content: "已经不能在减少了哦!!!",
      duration: 3
      });
      }else{
        this.layerList.splice(-1,1)
        this.LengthRow = this.layerList.length+2
      }
    },
    Preservation(){
      if (this.model10.length == 0) {
        this.$Message.error("项目团队不能为空");
        return;
      }
      let arr = [];
      let proModelArr = []
      let ArrLen = [];
      let proModel = this.baseInfo.proModel
      console.log(this.layerList);
            for (let i = 0; i < this.layerList.length; i++) {
          if (this.layerList[i].level ||
              this.layerList[i].amount ||
              this.layerList[i].proportion ||
              this.layerList[i].issueRate ||
              this.layerList[i].proTerm ||
              this.layerList[i].averageTerm) {
              ArrLen.push(this.layerList[i])
          }
      }
        console.log(ArrLen);
        if (proModel.length !=0) {
        for (let i = 0; i < proModel.length; i++) {
          if (proModel[i] == "存量债转") {
            proModelArr.push({
              "modelId":"4",
              "code":"StockDebtTransfer",
              "name":"存量债转",
            })
          }else if(proModel[i] == "新增助贷"){
            proModelArr.push({
              "modelId":"5",
              "code":"NewLoan",
              "name":"新增助贷",
            })
          }else if(proModel[i] == "数据采购"){
            proModelArr.push({
              "modelId":"1",
              "code":"DataPurchase",
              "name":"数据采购",
            })
          }else if(proModel[i] == "新产品上线"){
            proModelArr.push({
              "modelId":"2",
              "code":"NewProductOnLine",
              "name":"新产品上线",
            })
          }else if(proModel[i] == "外包采购"){
            proModelArr.push({
              "modelId":"3",
              "code":"Outsourcing",
              "name":"外包采购",
            })
          }
        }
      }
        for (let i = 0; i < this.model10.length; i++) {
          for (let j = 0; j < this.label.length; j++) {
            if (this.label[j].userName == this.model10[i]) {
              arr.push({
                "userId":this.label[j].id
              });
            }
          }
        }
      let params = this.getParams();
      console.log(params);
      
      params.teamList = arr;
      params.proType = "1";
      params.proModelList = proModelArr
      params.proModel = params.proModel.toString()
      params.date = this.baseInfo.date
      params.layerList = ArrLen

      console.log(params);
      
      loading();
      this.api.savePro(params).then(data => {
        loadingHide();
        if (data.code == "000") {
          console.log(data);
          window.successNotice("保存成功");
          this.$router.push({name:"submitted"});
        } else {
          serviceError(data.message);
        }
      });
    }
  }
   
};
</script>





