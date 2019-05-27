<template>
  <div class="product_layout">
    <Row class="t-c product_name">数据使用申请简介表</Row>
    <Row style="margin-top:15px">
      <table class="product_info">
        <tbody>
            <tr>
            <td class="info_key">应用系统名称</td>
            <td colspan="6">
              <template v-if="isDetail">
                <div class="t-l txt">{{DataAll.systemName}}</div>
              </template>
              <Select v-model="DataAll.systemName" class="btnselect btnselectlist" style="width:100%;height:50px;" v-else>
                <OptionGroup label="消费金融业务线">
                  <Option v-for="item in ConsumerFinance" :value="item.value" :key="item.index">{{item.label}}</Option>
                </OptionGroup>
                <OptionGroup label="模型中心">
                  <Option v-for="item in ModelCenter" :value="item.value" :key="item.index">{{item.label}}</Option>
                </OptionGroup>
                <OptionGroup label="结构化业务线">
                  <Option v-for="item in Structured" :value="item.value" :key="item.index">{{item.label}}</Option>
                </OptionGroup>
              </Select>
            </td>
          </tr>
           <tr>
            <td class="info_key">数据来源部门</td>
            <td colspan="3">
              <template v-if="isDetail">
                <div class="t-l txt">{{DataAll.applyDepartment}}</div>
              </template>
              <input type="text" :readonly = "true" v-model="applicationSystem" v-else>
            </td>
            <td class="info_key">申请人</td>
            <td colspan="3">
              <template v-if="isDetail">
                <div class="t-l txt"><span class="text_line">{{Apply}}<br></span></div>
              </template>
              <input type="text" :readonly = "true" v-model="Applicant" v-else>
            </td>
          </tr>

          <tr>
            <td class="info_key" v-if="isDetail">申请提交时间</td>
            <td colspan="3" v-if="isDetail">
              <div class="t-l txt text" v-if="isDetail">{{DataAll.createTime}}</div>
            </td>
            <td class="info_key">建议完成时间</td>
            <td colspan="6">
              <div class="t-l txt" v-if="isDetail">{{DataAll.suggestCompleteTime}}</div>
              <Col span="12" v-else class="proCompleteTime">
                <DatePicker
                  type="date"
                  placeholder="请选择建议完成时间"
                  style="width: 100%;"
                  v-model="DataAll.suggestCompleteTime"
                ></DatePicker>
              </Col>
              </td>
            </tr>
          <tr>
            <td class="info_key">任务类型</td>
            <td colspan="6">
              <template v-if="isDetail">
                <div class="t-l txt">{{DataAll.applyType}}</div>
              </template>
              <CheckboxGroup
                style="text-align: left;margin-left: 25px;"
                v-model="DataAll.applyType"
                v-else
              >
                <Checkbox label="数据查看分析">数据查看分析 </Checkbox>
                <Checkbox label="数据提取应用">数据提取应用</Checkbox>
                <Checkbox label="数据修改/删除">数据修改/删除</Checkbox>
              </CheckboxGroup>
            </td>
          </tr>
          <tr>
            <td class="info_key">任务类别</td>
            <td colspan="6">
              <template v-if="isDetail">
                <div class="t-l txt">{{DataAll.applyCategory}}</div>
              </template>
              <template v-else>
                <template v-if="category"><span>请先选择任务类型!!!</span></template>
                <template v-if="checkDatashow">
                  <span style="display: block;text-align: left;margin-left: 25px;font-size: 12px;color:#999">数据查看分析</span>
                <CheckboxGroup style="text-align: left;margin-left: 45px;padding: 10px 0;" v-model="checkDataArr">
                    <Checkbox label="查看与分析明细级不脱敏数据">查看与分析明细级不脱敏数据</Checkbox><br>
                    <Checkbox label="查看与分析统计级数据(包含非数据人员查看数据)">查看与分析统计级数据(包含非数据人员查看数据)</Checkbox>
                    <Checkbox label="查看与分析明细级脱敏数据大于10万条(含本数)">查看与分析明细级脱敏数据大于10万条(含本数)</Checkbox>
                </CheckboxGroup>
                </template>
                <template v-if="extraction">
                  <span style="display: block;text-align: left;margin-left: 25px;font-size: 12px;color:#999">数据提取应用</span>
                <CheckboxGroup style="text-align: left;margin-left: 45px;padding: 10px 0;" v-model="extractionArr">
                    <Checkbox label="提取明细级不脱敏数据">提取明细级不脱敏数据</Checkbox><br>
                    <Checkbox label="提取明细级脱敏数据-公司内部流转">提取明细级脱敏数据-公司内部流转</Checkbox>
                    <Checkbox label="提取明细级脱敏数据-公司外部流转">提取明细级脱敏数据-公司外部流转</Checkbox><br>
                    <Checkbox label="提取统计级数据-公司内部流转">提取统计级数据-公司内部流转</Checkbox>
                    <Checkbox label="提取统计级数据-公司外部流转">提取统计级数据-公司外部流转</Checkbox>
                </CheckboxGroup>
                </template>
               <template v-if="modification">
                  <span style="display: block;text-align: left;margin-left: 25px;font-size: 12px;color:#999">数据修改/删除</span>
                <CheckboxGroup style="text-align: left;margin-left: 45px;padding: 10px 0;" v-model="modificationArr">
                    <Checkbox label="修改及删除明细级不脱敏数据">修改及删除明细级不脱敏数据</Checkbox>
                    <Checkbox label="修改及删除明细级脱敏数据">修改及删除明细级脱敏数据</Checkbox>
                </CheckboxGroup>
               </template>
              </template>
            </td>
          </tr>
          <tr>
            <td class="info_key" colspan="6">申请描述栏</td>
          </tr>
          <tr>
            <td class="info_key">数据查看/提取/修改<br>/删除原因</td>
            <td colspan="6">
              <pre class="t-l txt newline" v-if="isDetail">{{DataAll.optReason}}</pre>
              <textarea rows="5" style="height: 100px;" v-model="DataAll.optReason" maxlength="1000" v-else placeholder="最大输入1000个字符" @input="proDetailsInput"></textarea>
              <span class="numberV" v-show="!isDetail">{{txtVal}}/1000</span>
            </td>
          </tr>
          <tr>
            <td class="info_key">数据查看/提取/修改<br>/删除内容</td>
            <td colspan="6">
              <pre class="t-l txt newline" v-if="isDetail">{{DataAll.optContent}}</pre>
              <textarea rows="5" style="height: 100px;" v-model="DataAll.optContent" maxlength="2000" v-else placeholder="最大输入2000个字符" @input="proCostInput"></textarea>
              <span class="numberV" v-show="!isDetail">{{txtValproCost}}/2000</span>
            </td>
          </tr>
          <tr>
            <td class="info_key" colspan="6">申请操作审批栏</td>
          </tr>
          <tr>
            <td class="info_key">可行性分析和影响</td>
            <td colspan="6">
              <pre class="t-l txt newline" v-if="isDetail">{{DataAll.feasibilityAnalysis}}</pre>
              <textarea rows="5" style="height: 100px;" v-model="DataAll.feasibilityAnalysis" maxlength="1000" v-else placeholder="最大输入2000个字符" @input="proExpectationInput"></textarea>
              <span class="numberV" v-show="!isDetail">{{txtValproExpectation}}/2000</span>
            </td>
          </tr>
         <template v-if="DataAll.acceptanceConclusion != null || check">
          <tr>
            <td class="info_key" colspan="6">数据查看/提取/修改/删除验收栏</td>
          </tr>
          <tr>
            <td class="info_key">验收结论</td>
            <td colspan="6">
              <pre class="t-l txt newline" v-if="isDetail && DataAll.acceptanceConclusion != 'null'">{{DataAll.acceptanceConclusion}}</pre>
              <textarea rows="5" style="height: 100px;" v-model="acceptanceConclusion" maxlength="1000" placeholder="最大输入1000个字符" @input="conclusionInput" v-if="check"></textarea>
              <span v-if="check" class="numberV">{{conclusion}}/1000</span>
            </td>
          </tr>
         </template>
          </tbody>
        </table>
        </Row>
      <Row style="margin:30px 0 0 0;text-align:right" v-show="!isDetail">
        <Button type="info" style="border-radius: 5px;position: relative;left: -80px;" @click="Preservation">暂时保存</Button>
        <Button type="primary" class="margin-right-20;" style="border-radius: 5px;" @click="AllData">提交申请</Button>
      </Row>
      <Row style="margin:30px 0 0 0;text-align:right" v-show="check">
        <Button type="primary" class="margin-right-20;" style="border-radius: 5px;margin-top: -50px;" @click="acceptance">提交</Button>
      </Row>
        
        <template v-if="isDetail">
          <Row class="tab_name">审批流程:</Row>
          <Steps :current="current" status="finish" style="margin-top: 20px;">
              <Step v-for="item in nodesDetailList" :title="item.isComplete" :content="'审批节点：'+item.nodeName +'\t'+',审批人:'+item.actors.toString()" :key="item.id"></Step>
          </Steps>
      </template>

      <div v-show="Approval">
        <Row class="tab_name">审批历史</Row>
        <div class="history_list">
            <template v-if="historyAudit.length > 0">
                <Row class="history_item" v-for="(item,index) in historyAudit" :key="index" :class="item.result== '不同意' ? 'disagree' : ''">
                    <Col span="4" style="margin-right: 20px;float: left;width: 165px;">审批人：<span style="color:#212121;display: block;float: right;width: 100px;">{{item.operator}}</span></Col>
                    <Col style="width: 185px;" span="4">审批结果：<span style="color:#212121;float: right;width: 100px;">{{item.resultState}}</span><br></Col>
                    <Col style="width: 185px;" span="4">审批节点：<span style="color:#212121;float: right;width: 100px;">{{item.approvalNode}}</span><br></Col>
                    <Col span="8" offset="1" style="float: left;">审批日期：<span style="color:#212121">{{item.date}}</span></Col>
                    <Col span="7" style="position: relative;width: 90%;top: 15px;" v-if="item.result.length !=0">审批意见：
                    <span style="color:#212121;color: red;" v-if="item.result == '不同意'">{{item.result}}</span>
                    <pre class="newlineresult" style="color:#212121" v-else>{{item.result}}</pre>
                    </Col>
                </Row>
            </template>
            <Row v-else class="t-c history_item">
                暂无历史审批
            </Row>
        </div>
    </div>
       <Row class="margin-top-20" v-show="Exhibition">
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
        <Row style="margin:-50px 0 0 0;text-align:right;">
          <Button type="primary" class="margin-right-20 btntijiao" @click="auditFund">提交</Button>
        </Row>
      </Row>
    </Row>
    
  </div>
</template>

<script>
import axios from "axios";
export default {
  props: {
    baseInfo: {
      type: Object,
      default: function() {
        return {
          date: new Date().format("yyyy-MM-dd") //日期
        };
      }
    }
  },
  data() {
    return {
      isDetail: false,
      draft:false,
      datashow:false,
      Exhibition:false,
      Approval:false,
      Restart:false,
      check:false,
      isreader:false,
      orderId:'',
      wsid:'',
      nodesDetailList:[],//审批流程
      current:0,
      historyAudit: [], //历史审批
      auditInfo:"",//审批意见
      auditStatus:"",//审批结果
      acceptanceConclusion:"",//验收结论
      txtVal:0,//数据查看/提取/修改/删除原因
      txtValproCost:0,//数据查看/提取/修改/删除内容
      txtValproExpectation:0,//可行性分析和影响
      conclusion:0,//验收结论的字数
      Apply:"",//申请人
      processId:'',
      taskId:"",
      checkData: [],//数据查看分析
      extractionData: [],//数据提取应用
      modificationData: [],//数据修改/删除
      ConsumerFinance:[],//消费金融业务线
      ModelCenter:[],//模型中心
      Structured:[],//结构化业务线
      ConsumerFinanceArr:[],//消费金融业务线数组
      ModelCenterArr:[],//模型中心数组
      StructuredArr:[],//结构化业务线数组
      WatchDate:['查看与分析明细级不脱敏数据','查看与分析统计级数据(包含非数据人员查看数据)','查看与分析明细级脱敏数据大于10万条(含本数)'],//数据查看分析
      extractionDate:['提取明细级不脱敏数据','提取明细级脱敏数据-公司内部流转','提取统计级数据-公司内部流转','提取统计级数据-公司外部流转','提取明细级脱敏数据-公司外部流转'],//数据提取应用
      deletionDate:['修改及删除明细级不脱敏数据','修改及删除明细级脱敏数据'],//数据修改/删除
      checkDataArr:[],
      extractionArr:[],
      modificationArr:[],
      DataAll: {
        applyDepartment:"",//申请部门
        createTime:"",//申请提交时间
        systemName:"",//应用系统名称
        suggestCompleteTime:"",//建议完成时间
        applyType:[],//任务类型
        applyCategory:[],//任务类别
        optReason:"",//数据查看/提取/修改/删除原因
        optContent:"",//数据查看/提取/修改/删除内容
        feasibilityAnalysis:"",//可行性分析和影响
      },
      checkTips: {
        applyDepartment:"申请部门不能为空",
        systemName:"应用系统名称不能为空",
        suggestCompleteTime:"建议完成时间不能为空",
        applyType:"任务类型不能为空",
        applyCategory:"任务类别不能为空",
        optReason:"数据查看/提取/修改/删除原因不能为空",
        optContent:"数据查看/提取/修改/删除内容不能为空",
        feasibilityAnalysis:"可行性分析和影响不能为空",
      }
    };
  },
  computed:{
      checkDatashow(){
      if (this.DataAll.applyType.indexOf("数据查看分析") !='-1') {
        return true;
      }else{
        this.checkDataArr = []
        return false;
      }
      },
      extraction(){
      if (this.DataAll.applyType.indexOf("数据提取应用") !='-1') {
          return true;
        }else{
          this.extractionArr = []
          return false;
        }
      },
      modification(){
      if (this.DataAll.applyType.indexOf("数据修改/删除") !='-1') {
        return true;
      }else{
        this.modificationArr = []
        return false;
      }
      },
    Applicant(){
      return localStorage.getItem("user");
    },
    applicationSystem(){
      if (this.ConsumerFinanceArr.indexOf(this.DataAll.systemName) !='-1') {
        return "消费金融业务线"
      }
      if (this.ModelCenterArr.indexOf(this.DataAll.systemName) !='-1') {
        return "模型中心"
      }
      if (this.StructuredArr.indexOf(this.DataAll.systemName) !='-1') {
        return "结构化业务线"
      }
    },
    category(){
      if (this.checkDatashow || this.extraction || this.modification) {
        return false
      }else{
        return true
      }
    }
    },
  mounted() {
    if (this.$route.query.isDetail) {
      this.isDetail = this.$route.query.isDetail;
    }
    if (this.$route.query.orderId) {
      this.orderId = this.$route.query.orderId;
    }
    if (this.$route.query.check) {
      this.check = this.$route.query.check;
    }
    if (this.$route.query.processId) {
      this.processId = this.$route.query.processId;
    }
    if (this.$route.query.taskId) {
      this.taskId = this.$route.query.taskId;
    }
    if (this.$route.query.Exhibition) {
      this.Exhibition = this.$route.query.Exhibition;
    }
    if (this.$route.query.Approval) {
      this.Approval = this.$route.query.Approval;
    }
    if (this.$route.query.Restart) {
      this.Restart = this.$route.query.Restart;
    }
    if (this.$route.query.draft) {
      this.draft = this.$route.query.draft;
    }
    if (this.$route.query.wsid) {
      this.wsid = this.$route.query.wsid;
    }
    if (this.$route.query.isreader) {
      this.isreader = this.$route.query.isreader;
    }
     if (this.$route.query.checkshow) {
      this.checkshow = this.$route.query.checkshow;
    }
    //应用系统名称
    if (!this.isDetail) {
      axios.get("/api/snaker/dataUseApply/queryAllAppName")
      .then(res =>{
        
        if (res.data.code === "000") {
          let len = res.data.data;
          this.ConsumerFinanceArr = len[0].appName
          if (this.ConsumerFinanceArr.length !=0) {
            for (var i = 0; i < this.ConsumerFinanceArr.length; i++) {
              this.ConsumerFinance.push({
                'value':this.ConsumerFinanceArr[i],
                'label':this.ConsumerFinanceArr[i]
              })
            }
          }
          this.ModelCenterArr = len[1].appName
          if (this.ModelCenterArr.length !=0) {
            for (var i = 0; i < this.ModelCenterArr.length; i++) {
              this.ModelCenter.push({
                'value':this.ModelCenterArr[i],
                'label':this.ModelCenterArr[i]
              })
            }
          }

          this.StructuredArr = len[2].appName
          if (this.StructuredArr.length !=0) {
            for (var i = 0; i < this.StructuredArr.length; i++) {
              this.Structured.push({
                'value':this.StructuredArr[i],
                'label':this.StructuredArr[i]
              })
            }
          }
        }
        
      })
      .catch(function(error) {
      console.log(error);
      });
    }


    if (this.orderId) {
      axios.get("/api/snaker/dataUseApply/proDetail/" + this.orderId)
      .then(res => {
        if (res.data.code === '000') {
          this.DataAll = res.data.data;
          //处理审批流程
          this.nodesDetailList = this.DataAll.nodesDetailList
          if (this.nodesDetailList.length !=0) {
              for (let i = 0; i < this.nodesDetailList.length; i++) {
              if (this.nodesDetailList[i].isComplete == "0") {
                  this.nodesDetailList[i].isComplete = "未完成";
              }else if(this.nodesDetailList[i].isComplete == "1"){
                  this.nodesDetailList[i].isComplete = "进行中";
              }else if(this.nodesDetailList[i].isComplete == "2"){
                  this.nodesDetailList[i].isComplete = "已完成";
                  this.current = i+1;
              }
            }
          }

          if (this.Restart) {
            this.DataAll.applyType = this.DataAll.applyType.split(",");
          var apply = this.DataAll.applyCategory.split(",")
          console.log(apply);
          var checkDataArrList = []
          var extractionArrList = []
          var modificationArrList = []
          for (var i = 0; i < apply.length; i++) {
            if (this.WatchDate.indexOf(apply[i]) >=0){
              checkDataArrList.push(apply[i])
            }
          }
          this.checkDataArr = checkDataArrList
          for (var i = 0; i < apply.length; i++) {
            if (this.extractionDate.indexOf(apply[i]) >=0){
              extractionArrList.push(apply[i])
            }
          }
           this.extractionArr = extractionArrList
           
           for (var i = 0; i < apply.length; i++) {
            if (this.deletionDate.indexOf(apply[i]) >=0){
              modificationArrList.push(apply[i])
            }
          }
          this.modificationArr = modificationArrList
            // this.DataAll.applyCategory = apply;
          }
          this.Apply = this.DataAll.createUserName;
        }
        
      })
    .catch(function(error) {
      console.log(error);
    });
    }
    //调用历史审批
    if (this.Approval) {
        this.api
        .getHistoryAudit({
          id: this.orderId
        })
        .then(data => {
          if (data.code == "000") {
            this.historyAudit = data.data;
            console.log(this.historyAudit);
            
          } else {
            serviceError(data.message);
          }
        });
    }
    //草稿详情
    if (this.draft) {
      axios.get("/api/snaker/dataUseApply/proDraftDetail/" + this.$route.query.wsid)
        .then(res => {
          this.DataAll = res.data.data
          this.Apply = this.DataAll.createUserName;
          console.log(this.DataAll);
          
        })
        .catch(function(error) {
          console.log(error);
        });
    }
    if (this.isreader) {
      axios.get("/api/snaker/dataUseApply/proDraftDetail/" + this.$route.query.draftid)
        .then(res => {
          this.DataAll = res.data.data
          this.DataAll.applyType = this.DataAll.applyType.split(",");
          this.DataAll.applyCategory = this.DataAll.applyCategory.split(",");
          console.log(this.DataAll.applyCategory);
          var Len = this.DataAll.applyCategory
          var checkDataArrList = []
          var extractionArrList = []
          var modificationArrList = []
          for (var i = 0; i < Len.length; i++) {
            if (this.WatchDate.indexOf(Len[i]) >=0){
              checkDataArrList.push(Len[i])
            }
          }
          this.checkDataArr = checkDataArrList
          for (var i = 0; i < Len.length; i++) {
            if (this.extractionDate.indexOf(Len[i]) >=0){
              extractionArrList.push(Len[i])
            }
          }
           this.extractionArr = extractionArrList
           
           for (var i = 0; i < Len.length; i++) {
            if (this.deletionDate.indexOf(Len[i]) >=0){
              modificationArrList.push(Len[i])
            }
          }
          this.modificationArr = modificationArrList
        })
        .catch(function(error) {
          console.log(error);
        });
    }    
  },
  methods: {
    AllData(){
      let date = new Date();
        if (this.DataAll.suggestCompleteTime < date) {
          this.$Message.error("建议完成时间不能小于当前时间");
          return;
        }
      this.DataAll.applyCategory = this.checkDataArr.concat(this.extractionArr).concat(this.modificationArr);
      console.log(this.DataAll);
      
       const proType = "4"; //表示是数据使用申请
       this.DataAll.applyDepartment = this.applicationSystem;
      //单独处理任务类型与任务类别
      if (this.DataAll.applyCategory.length == 0) {
        this.$Message.error({
          content: '任务类别不能为空',
          duration: 3
        });
        return false
      }
       if (this.DataAll.applyType.length == 0) {
        this.$Message.error({
          content: '任务类型不能为空',
          duration: 3
        });
        return false
      }
      
      let params = JSON.parse(JSON.stringify(this.DataAll));
       if (!this.checkParams(params)) {
        return;
      }
      params.proType = proType;
      params.suggestCompleteTime = new Date(params.suggestCompleteTime).format("yyyy-MM-dd");
      console.log(params);
      // 提交
      loading();
      this.api.add(params).then(data => {
        loadingHide();
        if (data.code == "000") {
          window.successNotice("提交成功");
          this.$router.push({ name: "dataliatList" });
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
    acceptance(){
      if (this.acceptanceConclusion.length == 0) {
        this.$Message.error({
          content: '验收结论不能为空',
          duration: 3
        });
        return false
      }
      
      var params = {
        processId: this.processId,
        orderId: this.orderId,
        taskId: this.taskId,
        resultState: '0',
        result: '',
        acceptanceConclusion:this.acceptanceConclusion,
        proType:"4",
      };
      console.log(params);
      
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
    //暂时保存
    Preservation(){
      this.DataAll.applyCategory = this.checkDataArr.concat(this.extractionArr).concat(this.modificationArr);
      if (this.DataAll.systemName.length ==0 ) {
         this.$Message.error({
          content: '应用系统名称不能为空',
          duration: 3
        });
        return false
      }
      this.DataAll.suggestCompleteTime = new Date(this.DataAll.suggestCompleteTime).format("yyyy-MM-dd");
      this.DataAll.proType = "4";
      this.DataAll.applyDepartment = this.applicationSystem;
      console.log(this.DataAll);
      
      loading();
      this.api.addProDraft(this.DataAll).then(data => {
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
  auditFund(){
    if (this.auditStatus == "") {
      serviceError("请选择审批结果！");
      return;
    }else{
      var params = {
        processId: this.processId,
        orderId: this.orderId,
        taskId: this.taskId,
        resultState: this.auditStatus,
        result: this.auditInfo,
        proType:"4",
      };
    }
    console.log(params);
    if (params.resultState == '-1' && params.result == '') {
              this.$Message.error({
              content: "审批意见不能为空",
              duration: 3
                });
              return false;
          }

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

  //字数限制
    proDetailsInput(){
      this.txtVal = this.DataAll.optReason.length;
      if (this.DataAll.optReason.length>1000) {
          serviceError("您输入的字数超出上限请您检查一下您输入的字数！！");
      }
    },
     proCostInput(){
      this.txtValproCost = this.DataAll.optContent.length;
      if (this.DataAll.optContent.length>2000) {
          serviceError("您输入的字数超出上限请您检查一下您输入的字数！！");
      }
    },
     proExpectationInput(){
      this.txtValproExpectation = this.DataAll.feasibilityAnalysis.length;
      if (this.DataAll.feasibilityAnalysis.length>1000) {
          serviceError("您输入的字数超出上限请您检查一下您输入的字数！！");
      }
    },
    conclusionInput(){
      this.conclusion = this.acceptanceConclusion.length;
      if (this.acceptanceConclusion.length>1000) {
          serviceError("您输入的字数超出上限请您检查一下您输入的字数！！");
      }
    }
  }
};
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
    .text{
      width: 100px;
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
.btntijiao {
  border-radius: 5px;
  position: relative;
  top: 0px;
}
[v-cloak] {
  display: none;
}
.textErr {
  width: 330px;
  height: 40px;
}
.addImg {
  width: 20px;
  height: 20px;
  position: absolute;
  right: -20px;
  margin-top: -35px;
  img {
    display: block;
    width: 100%;
    height: 100%;
  }
}
.numberV{
  display: block;
  float: right;
  margin-right:15px;
}
.newline{
  width: 100%;
  white-space:pre-wrap;
}
.newlineresult{
  width: 100%;
  white-space:pre-wrap;
  position: relative;
  top: -40px;
  left: 75px;

}
</style>
