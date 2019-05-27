<template>
  <div class="product_layout">
    <Row class="t-c product_name">{{DataAll.proName}}项目简介表</Row>
    <Row class="product_team">
      <p class="f-l">
        项目团队：
        <span v-if="isDetail" style="color:#313131" v-cloak>{{team}}</span>
        <Select
          v-model="DataAll.teamList"
          filterable
          multiple
          remote
          :remote-method="remoteMethod2"
          style="width:285px"
          v-else
        >
          <Option
            v-for="item in label"
            :value="item.userName"
            :key="item.value"
            :label="item.fullName"
          >
            <span>{{item.fullName}}</span>
            <span style="float:right;color:#ccc">{{item.userName}}</span>
          </Option>
        </Select>
      </p>
      <p class="f-r">
        日期：
        <span>{{baseInfo.date}}</span>
      </p>
    </Row>
    <Row class="tab_name">项目基本信息</Row>
    <Row style="margin-top:15px">
      <table class="product_info">
        <tbody>
          <tr>
            <td class="info_key">项目名称</td>
            <td colspan="6">
              <div class="t-l txt" v-if="isDetail" style="color:#313131;">{{DataAll.proName}}</div>
              <input type="text" v-model="DataAll.proName" v-else>
            </td>
          </tr>
          <tr>
            <td class="info_key">业务模式</td>
            <td colspan="6">
              <template v-if="isDetail">
                <div class="t-l txt">{{DataAll.proModelReader}}</div>
              </template>
              <CheckboxGroup
                style="text-align: left;margin-left: 25px;"
                v-model="DataAll.proModel"
                v-else
              >
                <Checkbox label="数据采购">数据采购</Checkbox>
                <Checkbox label="新产品上线">新产品上线</Checkbox>
                <Checkbox label="外包采购">外包采购</Checkbox>
                <Checkbox  v-model="falg">其他</Checkbox>:
                <input
                  type="text"
                  style="width: 150px;height: 25px;border-bottom: 1px solid #DCDEE2;"
                  v-model="others"
                >
              </CheckboxGroup>
            </td>
          </tr>
          <tr>
            <td class="info_key">合作方</td>
            <td colspan="6">
              <template v-if="isDetail">
                <div class="t-l txt">{{DataAll.partners}}</div>
              </template>
              <Select filterable class="btnselect" v-model="DataAll.partners" v-else>
                <Option value=" ">
                  <div @click="addPepole">新增合作方</div>
                </Option>
                <Option
                  v-for="(item,index) in PartnersArr"
                  :value="item.usename"
                  :key="index"
                  :label="item.usename"
                >
                  <span>{{item.usename}}</span>
                </Option>
              </Select>
            </td>
          </tr>
          <tr>
            <td class="info_key">项目介绍</td>
            <td colspan="6">
              <div class="t-l txt" v-if="isDetail">{{DataAll.proDetails}}</div>
              <textarea rows="5" style="height: 100px;" v-model="DataAll.proDetails" maxlength="1000" v-else placeholder="最大输入1000个字符" @input="proDetailsInput"></textarea>
              <span class="numberV" v-show="!isDetail">{{txtVal}}/1000</span>
            </td>
          </tr>
          <tr>
            <td class="info_key">项目成本</td>
            <td colspan="6">
              <div class="t-l txt" v-if="isDetail">{{DataAll.proCost}}</div>
              <textarea rows="5" style="height: 100px;" v-model="DataAll.proCost" maxlength="64" v-else placeholder="最大输入64个字符" @input="proCostInput"></textarea>
              <span class="numberV" v-show="!isDetail">{{txtValproCost}}/64</span>
            </td>
          </tr>
          <tr>
            <td class="info_key">项目预期</td>
            <td colspan="6">
              <div class="t-l txt" v-if="isDetail">{{DataAll.proExpectation}}</div>
              <textarea rows="5" style="height: 100px;" v-model="DataAll.proExpectation" maxlength="1000" v-else placeholder="最大输入1000个字符" @input="proExpectationInput"></textarea>
              <span class="numberV" v-show="!isDetail">{{txtValproExpectation}}/1000</span>
            </td>
          </tr>
          <tr>
            <td class="info_key">计划项目启动时间</td>
            <td colspan="3">
              <div class="t-l txt" v-if="isDetail">{{DataAll.proStarTime}}</div>
              <Col span="12" v-else class="proStarTime">
                <DatePicker
                  type="datetime"
                  placeholder="请选择计划项目启动时间"
                  style="width: 250px;"
                  v-model="DataAll.proStarTime"
                ></DatePicker>
              </Col>
            </td>
            <td class="info_key">项目完成时间</td>
            <td colspan="3">
              <div class="t-l txt" v-if="isDetail">{{DataAll.proCompleteTime}}</div>
              <Col span="12" v-else class="proCompleteTime">
                <DatePicker
                  type="datetime"
                  placeholder="请选择计划项目启动时间"
                  style="width: 250px;"
                  v-model="DataAll.proCompleteTime"
                ></DatePicker>
              </Col>
            </td>
          </tr>
        </tbody>
      </table>
      <Row class="tab_name">{{isDetail ? '项目附件' : '上传附件'}} :</Row>
     <div v-show="!pdfshow">
        <div class="file_item" v-for="(item,index) in DataAll.fileList" :key="index">
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
          <Upload :before-upload="handleUpload" action :format="['pdf']">
            <Button icon="ios-cloud-upload-outline">上传</Button>
          </Upload>
          <span class="upload_tips">*仅支持PDF格式文件</span>
        </template>
      </Row>

      <Row style="margin:30px 0 0 0;text-align:right">
        <Button type="primary" class="margin-right-20;" style="border-radius: 5px;" @click="addAudit(value='modify')">提交修改</Button>
      </Row>
    </Row>
      <div v-show="Approval">
        <Row class="tab_name">审批历史</Row>
        <div class="history_list">
            <template v-if="historyAudit.length > 0">
                <Row class="history_item" v-for="(item,index) in historyAudit" :key="index" :class="item.result== '不同意' ? 'disagree' : ''">
                    <Col span="4" style="margin-right: 20px;">审批人：<span style="color:#212121;display: block;position: relative;top: -25px;left: 60px;">{{item.operator}}</span></Col>
                    <Col span="4">审批结果：<span style="color:#212121">{{item.resultState}}</span></Col><br>
                    <Col span="7" style="position: relative;left: -175px;top: 5px;" v-if="item.result.length !=0">审批意见：<span style="color:#212121">{{item.result}}</span></Col>
                    <Col span="8" offset="1">审批日期：<span style="color:#212121">{{item.date}}</span></Col>
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
        <Row style="margin:-50px 0 0 0;text-align:right;">
          <Button type="primary" class="margin-right-20 btntijiao" @click="auditFund">提交</Button>
        </Row>
      </Row>
      
      <Row class="margin-top-20" v-show="App">
        <table class="product_info">
          <tbody>
            <tr>
              <td class="info_key">选择审批节点</td>
              <td colspan="6">
                <CheckboxGroup v-model="examination">
                <Checkbox label="技术审批"></Checkbox>
                <Checkbox label="合规审批"></Checkbox>
                <Checkbox label="风控审批"></Checkbox>
                </CheckboxGroup>
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
      DataAll: {
        teamList: [], //项目团队
        proName: "", //项目名称
        proModel: [], //业务模式
        proModelReader: "", //业务模式只读
        partners: "", //合作方
        proDetails: "", //项目介绍
        proCost: "", //项目成本
        proExpectation: "", //项目预期
        proStarTime: "", //计划项目启动时间
        proCompleteTime: "", //项目完成时间
        fileList: [], //附件列表
        back:[],
        queryid:[]
      },
      falg:false,
      App:false,
      txtVal:0,//项目介绍的字数
      txtValproCost:0,//项目成本的字数
      txtValproExpectation:0,//项目预期的字数
      Agreed:false,
      status:"",
      pdfshow:false,
      detailshow:false,
      force:false,
      Examination:false,
      EnclosureShow:false,
      examination:[],
      // Exhibition:true,
      SubId: [], //附件列表的ID
      Approval:false,
      dataTableListshow: [],
      processId:"",
      taskId:"",
      others: "", //其他的选项
      historyAudit: [], //历史审批
      otherAll: "",
      PartnersArr: [], //合作方列表
      BusinessMode:["数据采购","新产品上线","外包采购"],
      examinationList:["技术审批","合规审批","风控审批"],
      proId:"",
      isDetail: false,
      auditStatus: "",
      auditInfo:"",
      team: "",
      label: [],
      checkTips: {
        teamList: "项目团队不能为空",
        proName: "项目名称不能为空",
        proModel: "业务模式不能为空",
        partners: "合作方不能为空",
        proDetails: "项目介绍不能为空",
        proCost: "项目成本不能为空",
        proExpectation: "项目预期不能为空",
        proStarTime: "计划项目启动时间不能为空",
        proCompleteTime: "项目完成时间不能为空"
      }
    };
  },
  mounted() {
    //我发起的页面点击重新发起的时候请求数据,并且做相对应的赋值操作
    if (this.$route.query.orderId) {
      this.orderId = this.$route.query.orderId;
    }
    if (!this.$route.query.isDetail) {
      this.isDetail = this.$route.query.isDetail;
    }
    if (this.$route.query.back) {
      this.back = this.$route.query.back;
    }
    if (this.$route.query.id) {
      this.queryid = this.$route.query.id;
    }
    if (this.$route.query.detailshow) {
      this.detailshow = this.$route.query.detailshow;
    }
     if (this.$route.query.Examination) {
      this.Examination = this.$route.query.Examination
    }
     if (this.$route.query.processId) {
      this.processId = this.$route.query.processId
    }
     if (this.$route.query.taskId) {
      this.taskId = this.$route.query.taskId
    }
      if (this.$route.query.Approval) {
      this.Approval = this.$route.query.Approval
    }
    if (this.$route.query.proId) {
      this.proId = this.$route.query.proId;
    }
    if (this.$route.query.App) {
      this.App = this.$route.query.App;
    }
    if (this.$route.query.EnclosureShow) {
      this.EnclosureShow = this.$route.query.EnclosureShow;
    }
    if (this.$route.query.proId) {
      this.proId = this.$route.query.proId;
    }
    if (this.$route.query.force) {
      this.force = this.$route.query.force;
    }
    if (this.$route.query.pdfshow) {
      this.pdfshow = this.$route.query.pdfshow;
    }
    if (this.$route.query.Agreed) {
      this.Agreed = this.$route.query.Agreed;
    }
    //项目团队默认赋值
    let UseId = localStorage.getItem("user")
    if (!this.isDetail) {
      this.DataAll.teamList.push(UseId)
    }
    if (this.detailshow) {
        axios
        .get("/api/snaker/eliminat/proDetail/" + this.orderId)
        .then(res => {
          let arr = [];
          let proModel = []
          let proModelArr = []
          let Num = res.data.data;
          if (res.data.code == "000") {
            this.DataAll = res.data.data;
            let proModel = res.data.data.proModelList
            for (let i = 0; i < proModel.length; i++) {
            if (proModel[i].code != "Other") {
              proModelArr.push(proModel[i].name)
            }else if(proModel[i].code = "Other"){
              this.falg = true
              this.others = proModel[i].name
            }
          }
          this.DataAll.proModel = proModelArr
            console.log(this.DataAll);
            for (let i = 0; i < this.DataAll.teamList.length; i++) {
              arr.push(this.DataAll.teamList[i].username);
            }
            this.DataAll.teamList = arr;
            for (let i = 0; i < res.data.data.proModelList.length; i++) {
              proModel.push(res.data.data.proModelList[i].name)
            }
            console.log(proModel);

            this.DataAll.proModelReader = proModel.toString();
            this.DataAll = res.data.data;
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    }
    axios
      .get("/api/snaker/surrogate/surrogate?proType="+"ELIMINATING")
      .then(res => {
        let Num = res.data.data;
        for (var i = 0; i < Num.length; i++) {
          this.label.push({
            userName: Num[i].userName,
            fullName: Num[i].fullName,
            userId: Num[i].id
          });
        }
      })
      .catch(function(error) {
        console.log(error);
      });

    //合作列表数据
    axios
      .get("/api/snaker/eliminat/partnersList")
      .then(res => {
        if (res.data.code == "000") {
          let arr = res.data.data;
          for (let i = 0; i < arr.length; i++) {
            this.PartnersArr.push({
              usename: arr[i]
            });
          }
        }
      })
      .catch(function(error) {
        console.log(error);
      });

    //调用历史审批
    if (this.Approval) {
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
    }
  },

  methods: {
    remoteMethod2(query) {
      let self = this;
      if (query) {
        setTimeout(() => {
          let params = {
            name: query
          };
          axios
            .get("/api/snaker/surrogate/surrogate?fullName=" + query)
            .then(res => {
              let Num = res.data.data;
              this.label = [];
              for (var i = 0; i < Num.length; i++) {
                this.label.push({
                  userName: Num[i].userName,
                  fullName: Num[i].fullName,
                  userId: Num[i].id
                });
              }
            })
            .catch(function(error) {
              console.log(error);
            });
        }, 100);
      } else {
        this.label = [];
      }
    },
    //新增合作方
    addPepole() {
      this.$Modal.confirm({
        render: h => {
          return h("Input", {
            props: {
              value: this.value,
              autofocus: true,
              placeholder: "请输入新增合作方"
            },
            on: {
              input: val => {
                this.value = val;
              }
            }
          });
        },
        onOk: () => {
          if (this.value.length) {
            axios
              .post("/api/snaker/eliminat/addPartners?value=" + this.value)
              .then(res => {
                if (res.status == "200") {
                  this.PartnersArr.push({
                    usename: this.value
                  });
                }
              })
              .catch(function(error) {
                console.log(error);
              });
          }
        }
      });
    },
    AllData() {
      const proType = "3"; //表示是消金
      //单独处理业务模式问题处理
      let arrLen = [];
      let proModelArr = []
      let proModel = this.DataAll.proModel
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
      if (this.falg) {
        proModelArr.push({
          "modelId":null,
          "code":null,
          "name":this.others,
        })
      }
      console.log(proModelArr);
      //处理时间问题
      if (
        this.DataAll.proStarTime != "" &&
        this.DataAll.proCompleteTime != ""
      ) {
        let date = new Date();
        if (this.DataAll.proStarTime < date) {
          this.$Message.error("计划项目启动时间不能小于当前时间");
          return;
        } else if (this.DataAll.proCompleteTime < date) {
          this.$Message.error("项目完成时间不能小于当前时间");
          return;
        } else if (this.DataAll.proStarTime > this.DataAll.proCompleteTime) {
          this.$Message.error("项目完成时间不能小于计划项目启动时间");
          return;
        }
      } else {
        if (this.DataAll.proStarTime == "") {
          this.$Message.error("计划项目启动时间不能为空");
          return;
        } else if (this.DataAll.proCompleteTime == "") {
          this.$Message.error("项目完成时间不能为空");
          return;
        } else {
          this.DataAll.proStarTime = this.DataAll.proStarTime.format(
            "yyyy-MM-dd"
          );
          console.log(this.DataAll.proStarTime.format("yyyy-MM-dd"));
          console.log(this.DataAll.proCompleteTime.format("yyyy-MM-dd"));

          this.DataAll.proCompleteTime = this.DataAll.proCompleteTime.format(
            "yyyy-MM-dd"
          );
        }
      }
      let params = JSON.parse(JSON.stringify(this.DataAll));

      if (!this.checkParams(params)) {
        return;
      }
      if (params.teamList.length == 0) {
        this.$Message.error("项目团队不能为空");
        return;
      }
      if (params.proModel.length == 0) {
        this.$Message.error("业务模式不能为空");
        return;
      }
      //处理项目团队
      let arr = [];
      for (let i = 0; i < this.DataAll.teamList.length; i++) {
        for (let j = 0; j < this.label.length; j++) {
          if (this.label[j].userName == this.DataAll.teamList[i]) {
            arr.push(this.label[j]);
          }
        }
      }
      params.proModel = null;
      params.teamList = arr;
      params.proStarTime = params.proStarTime.split("T")[0];
      params.proCompleteTime = params.proCompleteTime.split("T")[0];
      params.proModelList = proModelArr
      params.proType = proType;
      console.log(params);
      
      // 提交
      loading();
      this.api.addProduct(params).then(data => {
        loadingHide();
        if (data.code == "000") {
          window.successNotice("提交成功");
          this.$router.push({ name: "money" });
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
          this.DataAll.fileList.push({
            id: data.data.id,
            fileName: data.data.fileName,
            filePath: data.data.filePath,
            realFileName: data.data.realFileName,
            proId: data.data.proId,
            status: data.data.status,
            proAttachType: data.data.proAttachType,
            uploadPersonId: data.data.uploadPersonId,
            uploadPersonName: data.data.uploadPersonName
          });
        } else {
          serviceError(data.message);
        }
      });

      return false;
    },
    //删除上传附件的pdf文件3
    removeFile(index) {
      this.DataAll.fileList.splice(index, 1);
    },
    displayPdf(item) {
      if (this.status =="") {
           this.status = "-1"
         }
      let url = "/api/snaker/flow/attach/" + item.id + "/" + this.status;
      window.open("/static/pdf/web/viewer.html?file=" + url);
    },
    //保存
    Preservation() {
      //单独处理业务模式问题处理
      let params=""
      let arrLen = [];
      let proModelArr = []
      let proModel = this.DataAll.proModel
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
          if (this.falg) {
        proModelArr.push({
          "modelId":null,
          "code":null,
          "name":this.others,
        })
      }
      }
      // if (this.falg) {
      //   if (this.others.length ==0) {
      //     this.$Message.error({
      //     content: "请您填写其他部分的值",
      //     duration: 3
      //   });
      //   return 
      //   }
      //   let StrLen =((this.others+","+this.DataAll.proModel.toString()).split("false")[0])
      //   let Str = StrLen.split(",")
      //   for (let i = 0; i < Str.length; i++) {
      //     if (Str[i]) {
      //       arrLen.push(Str[i])
      //     }
      //   }
      // }else{
      //   arrLen = this.DataAll.proModel
      // }

       if (this.DataAll.teamList.length == 0) {
        this.$Message.error("项目团队不能为空");
        return;
      }
      let arr = [];
        for (let i = 0; i < this.DataAll.teamList.length; i++) {
          for (let j = 0; j < this.label.length; j++) {
            if (this.label[j].userName == this.DataAll.teamList[i]) {
              arr.push({
                "userId":this.label[j].userId
              });
            }
          }
        }
        for (let i = 0; i < this.DataAll.teamList.length; i++) {
          this.DataAll.teamList[i] = arr[i]
        }

      this.DataAll.proType = "3";
      this.DataAll.proModel = this.DataAll.proModel.toString()
      this.DataAll.proModelList = proModelArr
      this.DataAll.date = this.baseInfo.date
      console.log(this.DataAll);
      
      loading();
      this.api.savePro(this.DataAll).then(data => {
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
    let Arr = []
  if (this.Examination == true) {
        if (this.auditStatus == "") {
        serviceError("请选择审批结果！");
        return;
        if (this.auditStatus == "-1" && !this.auditInfo) {
          serviceError("审批不同意时，请填写审核意见");
          return;
        }
      }

  }else{
    let difference = this.examinationList.filter(x => this.examination.indexOf(x) == -1).concat(this.examination.filter(x => this.examinationList.indexOf(x) == -1));
    this.historyAudit.resultState = "0"
  }
        let params = {
        processId: this.processId,
        orderId: this.orderId,
        taskId: this.taskId,
        resultState: this.historyAudit.resultState,
        result: this.auditInfo,
        proType:"2",
        nodes:Arr
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
    //附件审核
    Material() {
      let Arrlist = []
      if (this.AttachmentList.length == 0) {
          this.$Message.error({
          content: "您提交的数据为空,请您检查以后再进行操作",
          duration: 3
        });
        return 
      }
      for (let i = 0; i < this.AttachmentList.length; i++) {
        if (this.AttachmentList[i].attachTaskState != "00") {
          Arrlist.push(this.AttachmentList[i])
        }
      }

      //修改attachTaskState的值如果不修改后台报错，修改了以后会导致之前选中的值全部都没有了
      // for (let i = 0; i < Arrlist.length; i++) {
      //  if (Arrlist[i].attachTaskState) {
      //    Arrlist[i].backReason = "true"
      //  }else{
      //    Arrlist[i].backReason = "false"
      //  }
      // }
      let fileResult = { fileResult: Arrlist};
      console.log(fileResult);
        if (fileResult.fileResult.length >0) {
        for (let i = 0; i < fileResult.fileResult.length; i++) {
        if (fileResult.fileResult[i].attachTaskState == "true") {
        fileResult.fileResult[i].attachTaskState = "01"
         }else if(fileResult.fileResult[i].attachTaskState == "false"){
           fileResult.fileResult[i].attachTaskState = "02"
         }
        }
      }

      let title = ""
        for (let i = 0; i < Arrlist.length; i++) {
        if (Arrlist[i].Comments =="01") {
          if (Arrlist[i].backReason == true || Arrlist[i].attachTaskState =="01") {
          title +=Arrlist[i].fileName+" -"+"通过"+"<br>"
        }else if(Arrlist[i].backReason == false || Arrlist[i].attachTaskState =="02"){
          title +=Arrlist[i].fileName+" -"+"驳回"+"<br>"
          }
        }
      }
        this.$Modal.confirm({
        title: '请确认你的操作',
        content: title,
        style:{
          fontSize:"12px"
        },
          onOk: () => {
        loading();
        this.api.submitFileApprovalResult(fileResult).then(data => {
        loadingHide();
        if (data.code == "000") {
          window.successNotice(data.message);
          this.$router.push({name:'Nucleus'})
        } else {
          serviceError(data.message);
        }
      });
          },
          onCancel: () => {
              
          }
      });
    },
    //字数限制
    proDetailsInput(){
      this.txtVal = this.DataAll.proDetails.length;
      if (this.DataAll.proDetails.length>1000) {
          serviceError("您输入的字数超出上限请您检查一下您输入的字数！！");
      }
    },
     proCostInput(){
      this.txtValproCost = this.DataAll.proCost.length;
      if (this.DataAll.proCost.length>64) {
          serviceError("您输入的字数超出上限请您检查一下您输入的字数！！");
      }
    },
     proExpectationInput(){
      this.txtValproExpectation = this.DataAll.proExpectation.length;
      if (this.DataAll.proExpectation.length>1000) {
          serviceError("您输入的字数超出上限请您检查一下您输入的字数！！");
          // alert("您输入的字数超出上限请您检查一下您输入的字数！！")
      }
    },
      addAudit(value) {
      let valuecon = value
     const proType = "3"; //表示是消金
     console.log(this.DataAll.teamList);
     if (this.DataAll.teamList.length ==0) {
       this.$Message.error({
          content: "项目团队不能为空！",
          duration: 3
        });
        return 
     }
      //单独处理业务模式问题处理
      let arrLen = [];
      let proModelArr = []
      let proModel = this.DataAll.proModel
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
      if (this.falg) {
        proModelArr.push({
          "modelId":null,
          "code":null,
          "name":this.others,
        })
      }
      console.log(proModelArr);
      //处理时间问题
      if (
        this.DataAll.proStarTime != "" &&
        this.DataAll.proCompleteTime != ""
      ) {
        let date = new Date();
        if (this.DataAll.proStarTime < date) {
          this.$Message.error("计划项目启动时间不能小于当前时间");
          return;
        } else if (this.DataAll.proCompleteTime < date) {
          this.$Message.error("项目完成时间不能小于当前时间");
          return;
        } else if (this.DataAll.proStarTime > this.DataAll.proCompleteTime) {
          this.$Message.error("项目完成时间不能小于计划项目启动时间");
          return;
        }
      } else {
        if (this.DataAll.proStarTime == "") {
          this.$Message.error("计划项目启动时间不能为空");
          return;
        } else if (this.DataAll.proCompleteTime == "") {
          this.$Message.error("项目完成时间不能为空");
          return;
        } else {
          this.DataAll.proStarTime = this.DataAll.proStarTime.format(
            "yyyy-MM-dd"
          );
        }
      }
      let params = JSON.parse(JSON.stringify(this.DataAll));

      if (!this.checkParams(params)) {
        return;
      }
      if (params.teamList.length == 0) {
        this.$Message.error("项目团队不能为空");
        return;
      }
      if (params.proModel.length == 0) {
        this.$Message.error("业务模式不能为空");
        return;
      }
      //处理项目团队
      let arr = [];
      for (let i = 0; i < this.DataAll.teamList.length; i++) {
        for (let j = 0; j < this.label.length; j++) {
          if (this.label[j].userName == this.DataAll.teamList[i]) {
            arr.push(this.label[j]);
          }
        }
      }
      params.proModel = null;
      params.orderId = this.orderId;
      params.teamList = arr;
      params.proModelList = proModelArr
      params.proType = proType;
      params.partners = params.partners.toString()
      console.log(params);

      // 接口的判断
      loading();
      if (valuecon === 'modify') {
        this.api.RejectaddProduct(params).then(data => {
          loadingHide();
          if (data.code == "000") {
            this.$router.push("/task/upcoming-list");
            const title = "修改成功！";
            const content = "<p>请到待办审批中提交申请！</p>";
            this.$Modal.success({
              title: title,
              content: content
            });
          } else {
            serviceError(data.message);
          }
        });
      } else {
        this.api.addProduct(params).then(data => {
          loadingHide();
          if (data.code == "000") {
            window.successNotice("提交成功");
            this.$router.push("/task/upcoming-list");
          } else {
            serviceError(data.message);
          }
        });
      }
    },
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
  // right: -140px;
}
.btnselect {
  height: 50px;
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
</style>
