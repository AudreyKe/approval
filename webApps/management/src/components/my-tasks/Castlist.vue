<template>
  <div class="product_layout">
    <Row class="t-c product_name">
            {{datainfo.proName}}项目简介表
    </Row>
    <Row class="product_team">
      <p class="f-l">
                项目团队：
                <span v-if="isDetail" style="color:#313131" v-cloak>{{team}}</span>
                <Select v-model="datainfo.teamList" filterable multiple remote :remote-method="remoteMethod2" style="width:285px" v-else>
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
                    <div class="t-l txt" v-if="isDetail" style="color:#313131">{{datainfo.proName}}</div>
                    <input type="text" v-model="datainfo.proName" v-else>
                </td>
            </tr>
            <tr>
                    <td class="info_key">项目总规模</td>
                    <td colspan="3">
                        <div class="t-l txt" v-if="isDetail" style="color:#313131">{{datainfo.proTotal}}</div>
                        <textarea style="height: auto;" v-model="datainfo.proTotal" v-else></textarea>
                    </td>
                    <td class="info_key">投资金额</td>
                    <td colspan="3">
                        <div class="t-l txt" v-if="isDetail" style="color:#313131">{{datainfo.investmentAmount}}</div>
                        <input type="text" v-model="datainfo.investmentAmount" v-else>
                    </td>
              </tr>
            <tr>
                  <td class="info_key">合作方</td>
                  <td colspan="6">
                      <div class="t-l txt"  v-if="isDetail">{{datainfo.partners}}</div>
                      <Select  filterable  class="btnselect" v-model="datainfo.partners" v-else>
                        <Option value=" "><div @click="addPepole">新增合作方</div></Option>
                        <Option v-for="(item,index) in PartnersArr" :value="item.usename" :key="index" :label="item.usename" >
                            <span>{{item.usename}}</span>
                        </Option>
                    </Select>
                  </td>
              </tr>
               <tr>
                    <td class="info_key" :rowspan="LengthRow" >分层结构</td>
                    <td class="info_key">分级</td>
                    <td class="info_key">发行金额(万元)</td>
                    <td class="info_key">占比(%)</td>
                    <td class="info_key">发行利率(%)</td>
                    <td class="info_key">产品期限(月)</td>
                    <td class="info_key">加权平均期限(年)</td>
                </tr>
                  <template v-if="isDetail">
                  <tr v-for="(item,index) in datainfo.layerList" :key="index">
                      <td>
                          <input type="text" v-model="item.level" :readonly="isDetail">
                      </td>
                      <td>
                          <input type="text" v-model="item.amount" :readonly="isDetail">
                      </td>
                      <td>
                          <input type="tel" v-model="item.proportion" :readonly="isDetail">
                      </td>
                      <td>
                          <input type="text" v-model="item.issueRate" :readonly="isDetail">
                      </td>
                      <td>
                          <input type="text" v-model="item.proTerm" :readonly="isDetail">
                      </td>
                      <td>
                          <input type="text" v-model="item.averageTerm" :readonly="isDetail">
                      </td>
                  </tr>
              </template>
                 <template v-else>
                  <tr v-for="item in layerList">
                      <td>
                          <input type="text" v-model="item.level" :readonly="isDetail">
                      </td>
                      <td>
                          <input type="tel" v-model="item.amount" :readonly="isDetail">
                      </td>
                      <td>
                          <input type="tel" v-model="item.proportion" :readonly="isDetail">
                      </td>
                      <td>
                          <input type="tel" v-model="item.issueRate" :readonly="isDetail">
                      </td>
                      <td>
                          <input type="text" v-model="item.proTerm" :readonly="isDetail">
                      </td>
                      <td>
                          <input type="tel" v-model="item.averageTerm" :readonly="isDetail">
                      </td>
                  </tr>
                </template>
                 <tr>
                    <td colspan="6" v-show="!isDetail">
                    <div class="addImg" ref="add" @click="AddDom"><img src="@/assets/images/round_add.png" alt></div>
                    <div class="reduce" ref="reduce" @click="reduce"><img src="@/assets/images/round_reduce.png" alt></div>
                    </td>
                  </tr>
                <tr>
                    <td class="info_key">预计投资时间</td>
                    <td colspan="3">
                        <div class="t-l txt"  v-if="isDetail">{{datainfo.expectTime}}</div>
                        <textarea style="height: auto;" v-model="datainfo.expectTime" v-else></textarea>
                    </td>
                    <td class="info_key">预期回报率</td>
                    <td colspan="3">
                        <div class="t-l txt"  v-if="isDetail">{{datainfo.expectRate}}</div>
                        <input type="text" v-model="datainfo.expectRate" v-else>
                    </td>
              </tr>
                <tr>
                    <td class="info_key">平均期限</td>
                    <td colspan="3">
                        <div class="t-l txt"  v-if="isDetail">{{datainfo.avgTerm}}</div>
                        <textarea style="height: auto;" v-model="datainfo.avgTerm" v-else></textarea>
                    </td>
                    <td class="info_key">最长期限</td>
                    <td colspan="3">
                        <div class="t-l txt"  v-if="isDetail">{{datainfo.largeTerm}}</div>
                        <input type="text" v-model="datainfo.largeTerm" v-else>
                    </td>
              </tr>
              <tr>
                  <td class="info_key" >通过率及累计<br>违约率假设</td>
                  <td colspan="6" >
                      <!-- <div class="t-l txt"  v-if="isDetail">{{datainfo.passDefaultRate}}</div>
                      <textarea rows="5" style="height: 50px;" v-model="datainfo.passDefaultRate" v-else></textarea> -->
                     <div style="position: relative;">
                        <div style="float: right;margin-right: 35px;margin-top: 10px;" v-show="!isDetail">
                        <template>
                            <Upload :before-upload="handleUploadPassing" action="" :format="['pdf']">
                                <Button icon="ios-cloud-upload-outline">上传</Button>
                            </Upload>
                        </template>
                            <span style="color:red;">*仅支持PDF格式文件</span> 
                      </div>
                      <div style="float:left;text-align: left;margin-left: 15px;">
                         <div class="file_item" style="margin-left:0px;" v-for="(item,index) in datainfo.passDefaultRateFileList" :key="index">
                            <i class="iconfont icon-file_pdf"></i>
                            <template v-if="!isDetail">
                                <span style>{{item.fileName}}</span>
                                <i class="iconfont icon-shanchu_o" title="删除" @click="removeFilePassing(index)"></i>
                            </template>
                            <template v-else>
                                <a @click="displayPdf(item)">{{item.fileName}}</a>
                            </template>
                        </div>
                        </div>
                     </div>
                  </td>
              </tr>
               <tr>
                  <td class="info_key" style="padding: 5px 5px;">历史数据表现 对比（累计违 约率、通过率）</td>
                  <td colspan="6" >
                      <!-- <div class="t-l txt"  v-if="isDetail">{{datainfo.historyDataContrast}}</div>
                      <textarea rows="5" style="height: 100px;" v-model="datainfo.historyDataContrast" v-else></textarea> -->
                        <div style="position: relative;">
                        <div style="float: right;margin-right: 35px;margin-top: 10px;" v-show="!isDetail">
                        <template>
                            <Upload :before-upload="handleUploadHistory" action="" :format="['pdf']">
                                <Button icon="ios-cloud-upload-outline">上传</Button>
                            </Upload>
                        </template>
                            <span style="color:red;">*仅支持PDF格式文件</span> 
                      </div>
                      <div style="float:left;text-align: left;margin-left: 15px;">
                         <div class="file_item" style="margin-left:0px;" v-for="(item,index) in datainfo.historyDataContrastFileList" :key="index">
                            <i class="iconfont icon-file_pdf"></i>
                            <template v-if="!isDetail">
                                <span style>{{item.fileName}}</span>
                                <i class="iconfont icon-shanchu_o" title="删除" @click="removeFileHistory(index)"></i>
                            </template>
                            <template v-else>
                                <a @click="displayPdf(item)">{{item.fileName}}</a>
                            </template>
                        </div>
                        </div>
                     </div>
                  </td>
              </tr>
              <tr>
                  <td class="info_key" >风险评估</td>
                  <td colspan="6" >
                      <div class="t-l txt"  v-if="isDetail">{{datainfo.riskAssessment}}</div>
                      <textarea rows="5" style="height: 50px;" v-model="datainfo.riskAssessment" v-else></textarea>
                  </td>
              </tr>
              <tr>
                  <td class="info_key" >信托计划形成<br>的时长</td>
                  <td colspan="6" >
                      <div class="t-l txt"  v-if="isDetail">{{datainfo.trustPlanTime}}</div>
                      <textarea rows="5" style="height: 50px;" v-model="datainfo.trustPlanTime" v-else></textarea>
                  </td>
              </tr>
               <tr>
                  <td class="info_key" >法律文本及系<br>统准备情况</td>
                  <td colspan="6" >
                      <div class="t-l txt"  v-if="isDetail">{{datainfo.legalText}}</div>
                      <textarea rows="5" style="height: 50px;" v-model="datainfo.legalText" v-else></textarea>
                  </td>
              </tr>
              <tr>
                  <td class="info_key" >其他限制性条<br>件</td>
                  <td colspan="6" >
                      <!-- <div class="t-l txt"  v-if="isDetail">{{datainfo.otherCondition}}</div>
                      <textarea rows="5" style="height: 50px;" v-model="datainfo.otherCondition" v-else></textarea> -->
                      <div style="position: relative;">
                        <div style="float: right;margin-right: 35px;margin-top: 10px;" v-show="!isDetail">
                        <template>
                            <Upload :before-upload="handleUploadother" action="" :format="['pdf']">
                                <Button icon="ios-cloud-upload-outline">上传</Button>
                            </Upload>
                        </template>
                            <span style="color:red;">*仅支持PDF格式文件</span> 
                      </div>
                      <div style="float:left;text-align: left;margin-left: 15px;">
                         <div class="file_item" style="margin-left:0px;" v-for="(item,index) in datainfo.otherConditionFileList" :key="index">
                            <i class="iconfont icon-file_pdf"></i>
                            <template v-if="!isDetail">
                                <span style>{{item.fileName}}</span>
                                <i class="iconfont icon-shanchu_o" title="删除" @click="removeFileother(index)"></i>
                            </template>
                            <template v-else>
                                <a @click="displayPdf(item)">{{item.fileName}}</a>
                            </template>
                        </div>
                        </div>
                     </div>
                  </td>
              </tr>
              <tr v-show="castshow">
                  <td class="info_key" >立项项目名称</td>
                  <td colspan="6" >
                      <!-- <div class="t-l txt"  v-if="isDetail">{{datainfo.beforeOrderId}}</div>
                      <textarea rows="5" style="height: 50px;" v-model="datainfo.beforeOrderId" v-else></textarea> -->
                      <a @click="Jump">{{datainfo.proName}}</a>
                  </td>
              </tr>
               
          </tbody>
      
        </table>
    <div >
     <Row class="tab_name">
            {{isDetail ? '项目附件' : '上传附件'}} :
      </Row>
          <div v-show="!pdfshow">
            <div class="file_item" v-for="(item,index) in datainfo.fileList" :key="index">
            <i class="iconfont icon-file_pdf"></i>
            <template v-if="!isDetail">
                <span style>{{item.fileName}}</span>
                <i class="iconfont icon-shanchu_o" title="删除" @click="removeFile(index)"></i>
            </template>
            <template v-else>
                <a @click="displayPdf(item)">{{item.fileName}}</a>
            </template>
          </div>
        </div>
      <Row style="margin-top:15px" v-show="!isDetail">
         <template>
                <Upload :before-upload="handleUpload" action="" :format="['pdf']">
                    <Button icon="ios-cloud-upload-outline">上传</Button>
                </Upload>
                <span class="upload_tips">*仅支持PDF格式文件</span> 
            </template>
      </Row>

        <Row style="margin:30px 0 0 0;text-align:right">
                <Button type="primary" class="margin-right-20;" style="border-radius: 5px;" @click="addAudit">提交修改</Button>
        </Row>
    </div>
    </Row>
      <div v-show="Approval">
        <Row class="tab_name">审批历史</Row>
        <div class="history_list">
            <template v-if="historyAudit.length > 0">
                <Row class="history_item" v-for="(item,index) in historyAudit" :key="index" :class="item.result== '不同意' ? 'disagree' : ''">
                    <Col span="4" style="height: 24px;width: auto;">审批人：<span style="color:#212121">{{item.operator}}</span></Col>
                    <Col span="4" style="    position: absolute;left: 275px;height: 24px;width: auto;">审批结果：<span style="color:#212121">{{item.resultState}}</span></Col><br>
                    <Col span="7" style="width: auto;">审批意见：<span style="color:#212121">{{item.result}}</span></Col>
                    <Col span="8" offset="1" style="position: absolute;top: 20px;right: 0;">审批日期：<span style="color:#212121">{{item.date}}</span></Col>
                </Row>
            </template>
            <Row v-else class="t-c history_item" >
                暂无历史审批
            </Row>
        </div>
    </div>
      <Row class="margin-top-20" v-show="Examination">
        <table class="product_info">
          <tbody>
            <tr>
              <td class="info_key">审批结果</td>
              <td colspan="6">
                <RadioGroup v-model="auditStatus">
                  <Radio label="0">
                    <span>同意</span>
                  </Radio>
                  <Radio label="-1">
                    <span>不同意</span>
                  </Radio>
                  <Radio label="-2" v-show="Agreed">
                    <span>有条件同意</span>
                  </Radio>
                </RadioGroup>
              </td>
            </tr>
            <tr>
              <td class="info_key">审批意见</td>
              <td colspan="6">
                <textarea rows="5" v-model="auditInfo" style="height: auto;"></textarea>
              </td>
            </tr>
          </tbody>
        </table>
      </Row>
    </div>

    </Row>
  </div>
</template>

<script>
import axios from 'axios';
import mdContract from './contract.md.vue'
export default {
props: {
  baseInfo: {
      type: Object,
      default: function() {
        return {
          date: new Date().format("yyyy-MM-dd"), //日期
        };
      },
    }
  },
      data(){
        return {
          label:[],
          LengthRow:7,
          castshow:false,
          pdfshow:false,
          orderId:"",
          team:"",
          SubId:[],
          isDetail:false,
          force:false,
          AttachmentList: [],
          back:false,
          detailshow:false,
          Examination:false,
          isshow:false,
          Agreed:false,
          queryid:"",
          auditStatus: "",
          status:"",
          auditInfo:"",
          processId:"",
          taskId:"",
          model9:[],
          PartnersArr:[],//合作方列表
          historyAudit: [], //历史审批
          Approval:false,
          proId:"",
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
      datainfo: {
        teamList:[],//项目团队
        proName:"",//项目名称
        proTotal:"",//项目总规模
        investmentAmount :"",//投资金额
        partners:"",//合作方
        expectTime:"",//预计投资时间
        expectRate:"",//预期回报率
        avgTerm:"",//平均期限
        largeTerm:"",//最长期限
        passDefaultRate:"",//通过率及累计违约率假设
        historyDataContrast:"",//历史数据表现 对比（累计违 约率、通过率)
        riskAssessment:"",//风险评估
        trustPlanTime:"",//信托计划形成的时长
        legalText:"",//法律文本及系统准备情况
        otherCondition:"",//其他限制性条件
        beforeOrderId:"",//立项项目名称
        layerList:[],
        fileList:[],//附件列表
        passDefaultRateFileList:[],//通过率及累计违约率假设附件list
        historyDataContrastFileList:[],//历史数据表现对比（累计违约率、通过率）附件list
        otherConditionFileList:[],//通过率及累计违约率假设附件list
      },
      checkTips: {
       proName:"项目名称不能为空",
       proTotal:"项目总规模不能为空",
       investmentAmount:"投资金额不能为空",
       partners:"合作方不能为空",
       expectTime:"预计投资时间不能为空",
       expectRate:"预期回报率不能为空",
       avgTerm:"平均期限不能为空",
       largeTerm:"最长期限不能为空",
      //  passDefaultRate:"通过率及累计违约率假设不能为空",
      //  historyDataContrast:"历史数据表现 对比（累计违 约率、通过率)不能为空",
       riskAssessment:"风险评估不能为空",
       trustPlanTime:"信托计划形成的时长不能为空",
       legalText:"法律文本及系统准备情况不能为空",
      //  otherCondition:"其他限制性条件不能为空",
       //beforeOrderId:"立项项目名称不能为空"//立项项目名称
      },
    }
      },
      components: {
          mdContract
      },
    mounted(){
    //我发起的页面点击重新发起的时候请求数据,并且做相对应的赋值操作
    if (this.$route.query.detailshow) {
      this.detailshow = this.$route.query.detailshow;
    }
    if (this.$route.query.orderId) {
      this.orderId = this.$route.query.orderId
    }
    //合作方
      axios.get('/api/snaker/voteMeet/collaboratorList')
     .then((res)=>{
       if (res.data.code =="000") {
         let arr = res.data.data;
         for (let i = 0; i < arr.length; i++) {
           this.PartnersArr.push({
             "usename":arr[i]
           })
         }
       }
     })
     .catch(function (error){
       console.log(error);
     });
    axios
    .get("/api/snaker/surrogate/surrogate?proType="+"VOTE")
    .then((res)=>{
      let Num = res.data.data
      if (res.data.code == "000") {
      for (var i = 0; i < Num.length; i++) {
        this.label.push({
          "userName":Num[i].userName,
          "fullName":Num[i].fullName,
          "userId":Num[i].id,
          })
        }
      }
    })
    .catch(function (error){
      console.log(error);
    });
    if(this.detailshow){
      axios.get('/api/snaker/voteMeet/proDetail/'+this.orderId)
     .then((res)=>{
        let arr = []
        console.log(res.data);
        if (res.data.code =="000") {
          let Len = res.data.data.teamList
          this.datainfo = res.data.data;
          console.log(res.data.data.fileList);
          for (let i = 0; i < Len.length; i++) {
            arr.push(Len[i].username)
          }
          this.LengthRow = 7
          this.datainfo.teamList = arr;
          //处理分层结构
          for (let index = 0; index < this.datainfo.layerList.length; index++) {
              this.layerList[index].level = this.datainfo.layerList[index].level;
              this.layerList[index].amount = this.datainfo.layerList[index].amount;
              this.layerList[index].proportion = this.datainfo.layerList[index].proportion;
              this.layerList[index].issueRate = this.datainfo.layerList[index].issueRate;
              this.layerList[index].proTerm = this.datainfo.layerList[index].proTerm;
              this.layerList[index].averageTerm = this.datainfo.layerList[index].averageTerm;
          }

        }else{
          serviceError(data.message);
        }
     })
     .catch(function (error){
       console.log(error);
     });
    }
    if (this.$route.query.wsid) {
    axios
    .get("/api/snaker/flow/getProDraftById?id=" + this.$route.query.wsid)
    .then((res)=>{
    let arr = []
    let Num = res.data.data
    if (res.data.code == "000") {
      this.datainfo = res.data.data
      this.status = res.data.data.status
      console.log(this.datainfo);
    for (let i = 0; i < this.datainfo.teamList.length; i++) {
      arr.push(this.datainfo.teamList[i].username)
    }
    this.DataAll.teamList = arr;
      let num =this.datainfo.layerList;
      this.LengthRow = this.datainfo.layerList.length+1;
      for (let index = 0; index < this.layerList.length; index++){
          if (num[index]) {
          this.datainfo.layerList[index].level = num[index].level;
          this.datainfo.layerList[index].amount = num[index].amount;
          this.datainfo.layerList[index].proportion = num[index].proportion;
          this.datainfo.layerList[index].issueRate = num[index].issueRate;
          this.datainfo.layerList[index].proTerm = num[index].proTerm;
          this.datainfo.layerList[index].averageTerm = num[index].averageTerm;
        }
      }
    }
  })
    .catch(function(error) {
      console.log(error);
    });
}
    if (this.isshow) {
    axios.get('/api/snaker/voteMeet/proDetail/'+this.orderId)
     .then((res)=>{
        let arr = []
        console.log(res.data);
        if (res.data.code =="000") {
          let Len = res.data.data.teamList
          this.datainfo = res.data.data;
          console.log(res.data.data.fileList);
          for (let i = 0; i < Len.length; i++) {
            arr.push(Len[i].username)
          }
          this.LengthRow = 6
          this.datainfo.teamList = arr;
          //处理分层结构
          for (let index = 0; index < this.datainfo.layerList.length; index++) {
              this.layerList[index].level = this.datainfo.layerList[index].level;
              this.layerList[index].amount = this.datainfo.layerList[index].amount;
              this.layerList[index].proportion = this.datainfo.layerList[index].proportion;
              this.layerList[index].issueRate = this.datainfo.layerList[index].issueRate;
              this.layerList[index].proTerm = this.datainfo.layerList[index].proTerm;
              this.layerList[index].averageTerm = this.datainfo.layerList[index].averageTerm;
          }

        }else{
          serviceError(data.message);
        }
     })
     .catch(function (error){
       console.log(error);
     });
    }
    },
  methods:{
    remoteMethod2(query) {
    let self = this;
      if (query) {
         setTimeout(() => {
        let params = {
          name: query
        }
         axios.get('/api/snaker/surrogate/surrogate?fullName='+query)
        .then((res)=>{
          let Num = res.data.data
           this.label=[]
          for (var i = 0; i < Num.length; i++) {
            this.label.push({
              "userName":Num[i].userName,
              "fullName":Num[i].fullName,
              "userId":Num[i].id,
            })
          }
        })
        .catch(function (error) {
          console.log(error);
        });
        }, 100);
      }else{
        this.label =[];
      }
  },
  //新增分层结构
  AddDom(){
    this.layerList.push({})
    this.LengthRow = this.layerList.length+2
  },
  reduce() {
  console.log(this.layerList.length);
  if (this.layerList.length == 5) {
  this.$Message.error({
  content: "已经不能在减少了哦!!!",
  duration: 3
  });
  }else{
    this.layerList.splice(1,1)
    this.LengthRow = this.layerList.length+2
  }
  },
  //新增合作方
    addPepole(){
      this.$Modal.confirm({
        "width":"345px",
      render: (h) => {
          return h('Input', {
              props: {
                  title:'111',
                  value: this.value,
                  autofocus: true,
                  placeholder: '请输入新增合作方'
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
                   axios.post('/api/snaker/voteMeet/addCollaborator?value='+this.value)
                  .then((res)=>{
                    if (res.status == '200') {
                      this.PartnersArr.push({
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
      addAudit(){
        //项目团队的非空判断
       if (this.datainfo.teamList.length == 0) {
        this.$Message.error("项目团队不能为空");
        return;
      }
      console.log(this.datainfo)
      const proType = "2";//表示是投决会
      let params = this.getParams();
      //拿到投委会表中除了分层结构部分的正则判断
      if (!this.checkParams(params)){
            return ;
      }
  //判断好了之后拿到表中所有的值
    //处理项目团队
      let arr = [];
      for (let i = 0; i < this.datainfo.teamList.length; i++) {
       for (let j = 0; j < this.label.length; j++) {
         if (this.label[j].userName ==this.datainfo.teamList[i]){
           arr.push(this.label[j])
         }
       }
      }
      params.teamList = arr;
      params.teamList = teamList;
      params.proType = proType;
      console.log(params);
      loading();
      //提交
        this.api.RejectaddProduct(params).then(data => {
          loadingHide();
          if (data.code == "000") {
            window.successNotice("提交成功");
            this.$router.push({name:"decision"})
          } else {
            serviceError(data.message);
          }
        });
      },
      getParams(){
        //拿到投委会表中所有的数据
          let param = JSON.parse(JSON.stringify(this.datainfo));
          param.layerList = [];
          for (let i = 0; i < this.layerList.length; i++) {
            if (
          this.layerList[i].level ||
          this.layerList[i].amount ||
          this.layerList[i].proportion ||
          this.layerList[i].issueRate ||
          this.layerList[i].proTerm ||
          this.layerList[i].averageTerm
            ) {
              param.layerList.push(this.layerList[i]);
            }
          }
          return param;
      },
       checkParams(params){
      for (let i in params){
        if (this.checkTips[i]){
          let flag = this.changeInput(params[i]);
          if (!flag){
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
            issueRate: "发行利率不能为空",
            proTerm: "产品期限应填写11位以内正整数",
            averageTerm: "加权平均期限不能为空"
          };
          let reg = /\S/;

          for (let j in params.layerList) {
            for (let k in params.layerList[j]) {
              if (!params.layerList[j][k]){
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
        }
      }
      return true;
    },
  changeInput(val) {
      let reg = /\S/;
      if (!val) {
        return false;
      } else {
        return true;
      }
      if (reg.test(val) && val > 0) {
        return true;
      } else {
        return false;
      }
    },
    //通过率pdf接口
    handleUploadPassing(file){
       if (file.type != "application/pdf") {
        this.$Message.error("仅支持上传pdf文件");
        return false;
      }
      loading();
      let params = new FormData();
      params.append("file", file);
      params.append("proAttachType", "03");
      this.api.uploadFile(params).then(data => {
        loadingHide();
        console.log(data);
        if (data.code == "000") {
          this.datainfo.passDefaultRateFileList.push({
            "id": data.data.id,
            "fileName": data.data.fileName,
            "filePath":data.data.filePath,
            "realFileName":data.data.realFileName,
            "proId":data.data.proId,
            "status":data.data.status,
            "proAttachType":data.data.proAttachType,
            "uploadPersonId":data.data.uploadPersonId,
            "uploadPersonName":data.data.uploadPersonName,
          });
        } else {
          serviceError(data.message);
        }
      });
      return false;
    },
    //历史数据pdf接口
    handleUploadHistory(file){
       if (file.type != "application/pdf") {
        this.$Message.error("仅支持上传pdf文件");
        return false;
      }
      loading();
      let params = new FormData();
      params.append("file", file);
      params.append("proAttachType", "04");
      this.api.uploadFile(params).then(data => {
        loadingHide();
        console.log(data);
        if (data.code == "000") {
          this.datainfo.historyDataContrastFileList.push({
            "id": data.data.id,
            "fileName": data.data.fileName,
            "filePath":data.data.filePath,
            "realFileName":data.data.realFileName,
            "proId":data.data.proId,
            "status":data.data.status,
            "proAttachType":data.data.proAttachType,
            "uploadPersonId":data.data.uploadPersonId,
            "uploadPersonName":data.data.uploadPersonName,
          });
        } else {
          serviceError(data.message);
        }
      });
      return false;
    },
      //其他限制性pdf接口
    handleUploadother(file){
       if (file.type != "application/pdf") {
        this.$Message.error("仅支持上传pdf文件");
        return false;
      }
      loading();
      let params = new FormData();
      params.append("file", file);
      params.append("proAttachType", "05");
      this.api.uploadFile(params).then(data => {
        loadingHide();
        console.log(data);
        if (data.code == "000") {
          this.datainfo.otherConditionFileList.push({
            "id": data.data.id,
            "fileName": data.data.fileName,
            "filePath":data.data.filePath,
            "realFileName":data.data.realFileName,
            "proId":data.data.proId,
            "status":data.data.status,
            "proAttachType":data.data.proAttachType,
            "uploadPersonId":data.data.uploadPersonId,
            "uploadPersonName":data.data.uploadPersonName,
          });
        } else {
          serviceError(data.message);
        }
      });
      return false;
    },
    //pdf上传接口
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
        console.log(data);
        if (data.code == "000") {
          this.datainfo.fileList.push({
            "id": data.data.id,
            "fileName": data.data.fileName,
            "filePath":data.data.filePath,
            "realFileName":data.data.realFileName,
            "proId":data.data.proId,
            "status":data.data.status,
            "proAttachType":data.data.proAttachType,
            "uploadPersonId":data.data.uploadPersonId,
            "uploadPersonName":data.data.uploadPersonName,
          });
        } else {
          serviceError(data.message);
        }
      });
      return false;
    },
    //删除上传附件的pdf文件
      removeFile(index) {
      this.datainfo.fileList.splice(index, 1);
    },
     //删除上传附件的pdf通过率文件
      removeFilePassing(index) {
      this.datainfo.passDefaultRateFileList.splice(index, 1);
    },
     removeFileHistory(index) {
      this.datainfo.historyDataContrastFileList.splice(index, 1);
    },
     removeFileother(index) {
      this.datainfo.historyDataContrastFileList.splice(index, 1);
    },
    displayPdf(item){
       if (this.status =="") {
           this.status = "-1"
         }
      let url = "/api/snaker/flow/attach/" + item.id + "/" + this.status;
      window.open("/static/pdf/web/viewer.html?file=" + url);
    },
    //保存
    Preservation() {
       if (this.datainfo.teamList.length == 0) {
        this.$Message.error("项目团队不能为空");
        return;
      }
      let arr = [];
        for (let i = 0; i < this.datainfo.teamList.length; i++) {
          for (let j = 0; j < this.label.length; j++) {
            if (this.label[j].userName == this.datainfo.teamList[i]) {
              arr.push({
                "userId":this.label[j].userId
              });
            }
          }
        }
        console.log(arr);
        
      let params = this.getParams();
      params.teamList = arr;
      params.proType = "2";
      params.date = this.baseInfo.date;
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
    },
    //提交审核
    auditFund() {
      if (this.auditStatus == "") {
        serviceError("请选择审批结果！");
        return;
        if (this.auditStatus == "-1" && !this.auditInfo) {
          serviceError("审批不同意时，请填写审核意见");
          return;
        }
      }
      let params = {
        processId: this.processId,
        orderId: this.orderId,
        taskId: this.taskId,
        resultState: this.auditStatus,
        result: this.auditInfo,
        proType:"2"
      };
      console.log(params);//proType
      loading();
      this.api.auditFund(params).then(data => {
        loadingHide();
        if (data.code == "000") {
          window.successNotice("审批成功");
          this.$router.push("/task/already-list");
        } else {
          serviceError(data.message);
        }
      });
    },
        remove(index) {
      this.dataTableListshow.splice(index, 1);
    },
    //项目附件列表提交
    Submit() {
      let title = ""
      if (this.SubId.length == 0) {
      this.$Message.error({
      content: "您提交的内容为空",
      duration: 3
      });
      return false;
      }
     console.log(this.SubId);
     
      let fileList = { proId: this.proId, fileList: this.SubId };
       for (let i = 0; i < fileList.fileList.length; i++) {
        if (fileList.fileList[i].optType == "0") {
          fileList.fileList[i].optType = "新增"
        }else {
          fileList.fileList[i].optType = "删除"
        }
        title +=fileList.fileList[i].fileName+" -"+fileList.fileList[i].optType+"<br>"
      }
      for (let i = 0; i < fileList.fileList.length; i++) {
        if (fileList.fileList[i].optType == "新增") {
          fileList.fileList[i].optType = "0"
        }else {
          fileList.fileList[i].optType = "1"
        }
      }
      console.log(title);
      this.$Modal.confirm({
          title: '请确认你的操作',
          content: title,
          onOk: () => {
            loading();
          this.api.addEnclosure(fileList).then(data => {
            loadingHide();
            if (data.code == "000") {
            this.$router.push({name:"materials"});
            } else {
              serviceError(data.message);
            }
              });
          },
          onCancel: () => {
              
          }
      });
    },
      //pdf上传落实材料
    handleUploadImplement(file) {
      if (file.type != "application/pdf") {
        this.$Message.error("仅支持上传pdf文件");
        return false;
      }
      loading();
      let params = new FormData();
      params.append("file", file);
      params.append("proAttachType", "02");
      this.api.uploadFile(params).then(data => {
        loadingHide();
        console.log(data);
        if (data.code == "000") {
          this.dataTableListshow.push({
            id: data.data.id,
            fileName: data.data.fileName,
            filePath: data.data.filePath,
            realFileName: data.data.realFileName,
            proId: data.data.proId,
            status: data.data.status,
            proAttachType: data.data.proAttachType,
            uploadPersonId: data.data.uploadPersonId,
            uploadPersonName: data.data.uploadPersonName,
            updateTime: data.data.updateTime.split("T")[0],
            optType: "0"
          });
          this.SubId.push({
            id: data.data.id,
            fileName: data.data.fileName,
            filePath: data.data.filePath,
            realFileName: data.data.realFileName,
            proId: data.data.proId,
            status: data.data.status,
            proAttachType: data.data.proAttachType,
            uploadPersonId: data.data.uploadPersonId,
            uploadPersonName: data.data.uploadPersonName,
            updateTime: data.data.updateTime.split("T")[0],
            optType: "0"
          });
        } else {
          serviceError(data.message);
        }
      });
      return false;
    },
    //pdf上传补充材料
    handleUploadsupplement(file) {
      if (file.type != "application/pdf") {
        this.$Message.error("仅支持上传pdf文件");
        return false;
      }
      loading();
      let params = new FormData();
      params.append("file", file);
      params.append("proAttachType", "01");
      this.api.uploadFile(params).then(data => {
        loadingHide();
        console.log(data);
        if (data.code == "000") {
          this.dataTableListshow.push({
            id: data.data.id,
            fileName: data.data.fileName,
            filePath: data.data.filePath,
            realFileName: data.data.realFileName,
            proId: data.data.proId,
            status: data.data.status,
            proAttachType: data.data.proAttachType,
            uploadPersonId: data.data.uploadPersonId,
            uploadPersonName: data.data.uploadPersonName,
            updateTime: data.data.updateTime.split("T")[0],
            optType: "0"
          });
          this.SubId.push({
            id: data.data.id,
            fileName: data.data.fileName,
            filePath: data.data.filePath,
            realFileName: data.data.realFileName,
            proId: data.data.proId,
            status: data.data.status,
            proAttachType: data.data.proAttachType,
            uploadPersonId: data.data.uploadPersonId,
            uploadPersonName: data.data.uploadPersonName,
            updateTime: data.data.updateTime.split("T")[0],
            optType: "0"
          });
        } else {
          serviceError(data.message);
        }
      });
      return false;
    },
    Jump(){
        this.$router.push({
        name:'audit-task',
        query:{
          orderId:this.orderId,
          isDetail:true
        }
      })
    }
  }
}
</script>

<style lang="less" scoped>
@border_color: rgba(204, 204, 204, 0.5);
@bg_color: rgba(255, 255, 255, 0.5);
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
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.product_team {
  margin-top: 30px;
  color: #626262;
  font-size: 16px;
  line-height: 40px;
  height: auto;
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
  margin-bottom: 100px;
  tr {
    border-bottom: 1px solid @border_color;
    td {
      height: 50px;
      text-align: center;
      border-right: 1px solid #ccc;
      color: #626262;
    }
    .info_key {
      width: 174px;
      background: rgba(255, 255, 255, 0.4);
      padding: 10px 0px;
    }
    input {
      width: 100%;
      line-height: 50px;
      border: 0;
      padding: 0px 7px;
      &:focus {
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
textarea{
  resize: none;
}
.btntijiao{
  border-radius:5px;
  position: relative;
  top: 0px;
}
.btnselect{
  height: 50px;
}
[v-cloak] {
  display: none;
}
.textErr{
  width: 330px;
  height: 40px;
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
