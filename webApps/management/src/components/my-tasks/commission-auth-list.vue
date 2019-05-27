<style scoped lang="less">
.surrogate{
  width: 500px;
}
</style>

<template>
    <div>
        <Row>
            <Col span="24" class="main_base_layout">
                <Row>
                    <div class="single_title base_nav border_bm">委托授权</div>
                    <div class="action">
                        <Button :size="'large'" icon="md-add" type="primary" @click="shwoModel">新增授权</Button>
                    </div>
                </Row>
                <Row>
                    <div class="main_table_layout">
                        <Table :columns="columns" :data="tableData" stripe :loading="tableLoading" :no-data-text="'暂无数据'" :no-filtered-data-text="'暂无数据'"></Table>
                        <Page :total="total" :page-size="10" size="small" @on-change="getList"/>
                    </div>
                </Row>
            </Col>
        </Row>
        <Modal v-model="showModal" scrollable footer-hide :width="'800'" :mask-closable="false">
            <div class="modal_layout">
                <div class="modal_title common_modal_title border_bm">
                    设置授权
                </div>
                <div class="modal_form common_form">
                    <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="165">
                        <Row>
                            <FormItem label="流程名称" prop="processName" >
                                <Select v-model="formValidate.processName" >
                                    <Option v-for="item in processList" :value="item.proDisplayName" :key="item.proDisplayName">{{item.proDisplayName}}</Option>
                                </Select>
                            </FormItem>
                        </Row>
                        <Row>
                            <FormItem label="授权人" prop="operator">
                                <Input v-model="operator" class="small_input" disabled></Input>
                            </FormItem>
                        </Row>
                        <Row>
                            <FormItem label="代理人" prop="surrogate" class="surrogate">
                                <Select v-model="formValidate.surrogate" filterable>
                                    <Option v-for="item in surrogateList" :value="item.userName" :key="item.id" :label="item.fullName">
                                      <span>{{item.fullName}}</span>
                                      <span style="float:right;color:#ccc">{{item.userName}}</span>
                                    </Option>
                                </Select>
                            </FormItem>
                        </Row>
                        <Row>
                            <Col span="12" class="input_175">
                                <FormItem label="开始时间" prop="sdate">
                                    <DatePicker v-model="formValidate.sdate" type="datetime" placeholder="请选择开始时间"></DatePicker>
                                </FormItem>
                            </Col>
                            <Col span="12" class="input_175">
                                <FormItem label="结束时间" prop="edate" :label-width="144">
                                    <DatePicker v-model="formValidate.edate" type="datetime" placeholder="请选择结束时间"></DatePicker>
                                </FormItem>
                            </Col>
                        </Row>
                    </Form>
                </div>
                <Row class="margin-top-20 t-r">
                    <div class="submit_btn">
                        <Button type="primary" class="margin-right-20" @click="addAuth">确定</Button>
                        <Button @click="showModal = false">取消</Button>
                    </div>
                </Row>
            </div>
        </Modal>
    </div>
</template>

<script>
import axios from 'axios';
export default {
  data() {
    return {
      showModal: false,
      Interface:"0", //编辑接口
      datalist:"",
      tableLoading: true,
      assetModal: false,
      modalActive: "1",
      pageSize: "10",
      columns: [
        {
          title: "流程名称",
          key: "proDisplayName"
        },
        {
          title: "授权人",
          key: "operatorId"
        },
        {
          title: "代理人",
          key: "surrogateId"
        },
        {
          title: "开始时间",
          key: "sdate"
        },
        {
          title: "结束时间",
          key: "edate"
        },
        {
          title: "操作",
          key: "",
          width: 150,
          render: (h, params) => {
            return h("div", [
              h(
                "a",
                {
                  style: {
                    marginRight: "20px"
                  },
                  on: {
                    click: () => {
                      // console.log(params.row);
                      // console.log(this.surrogateList);
                      // for (let i = 0; i < this.surrogateList.length; i++) {
                      //   if (this.surrogateList[i].userName==params.row.surrogateId) {
                      //     // this.datalist = this.surrogateList[i].userName
                      //     // this.surrogateList[i].userName   = selected 
                      //   }
                      // }
                      this.getDetail(params.row);
                      this.Interface = "2"//修改接口调用
                    }
                  }
                },
                "编辑"
              ),
              h(
                "a",
                {
                  on: {
                    click: () => {
                      var _self = this;
                      this.$Modal.confirm({
                        title: "提示",
                        content: `确定删除 <span style="color:red">${
                          params.row.proDisplayName
                        } ？<span>`,
                        onOk: function() {
                          _self.removeAuth(params.row.batchId);
                        },
                        onCancel: function() {
                          console.log("已经删除！");
                        }
                      });
                    }
                  }
                },
                "删除"
              )
            ]);
          }
        }
      ],
      tableData: [],
      total: 0,
      formValidate: {
        processName: "",
        operator: "",
        surrogate: "",//代理人
        sdate: "",
        edate: "",
        id: ""
      },
      ruleValidate: {
        processName: [{ required: true, message: "请输入", trigger: "change" }],
        // operator: [{ required: true, message: "请输入", trigger: "change" }],
        surrogate: [{ required: true, message: "请选择", trigger: "change" }],
        sdate: [
          { required: true, type: "date", message: "请选择", trigger: "change" }
        ],
        edate: [
          { required: true, type: "date", message: "请选择", trigger: "change" }
        ]
      },
      surrogateList: [],
      processList: []
    };
  },
  computed: {
    operator() {
      return localStorage.getItem("user");
    }
  },
  mounted() {
    this.getList();
    this.getProcess();
    this.getSurrogate();
  },
  methods:{
  //   remoteMethod2(query) {
  //   let self = this;
  //     if (query) {
  //       self.loading2 = true;
  //        setTimeout(() => {
  //       let params = {
  //         name: query
  //       }
  //        axios.get('/api/snaker/surrogate/surrogate?fullName='+query)
  //       .then((res)=>{
  //         let Num = res.data.data
  //         for (var i = 0; i < Num.length; i++){
  //           this.surrogateList.push({
  //             "userName":Num[i].userName,
  //             "fullName":Num[i].fullName
  //           })
  //         }
  //         console.log(res.data.data);
  //       })
  //       .catch(function (error) {
  //         console.log(error);
  //       });
  //       this.loading2 = false;
  //       }, 300);
  //     }else{
  //       this.surrogateList =[];
  //       query =""
  //     }
  // },
    shwoModel() {
      this.showModal = true;
      this.Interface = "1"//新增授权接口
      this.$nextTick(() => {
        this.$refs["formValidate"].resetFields();
        this.formValidate = this.util.setObjNull(this.formValidate);
        this.formValidate.operator = localStorage.getItem("user");
      });
    },
    getList(page = "1") {
      this.api
        .getAuthList({
          pageNo: page,
          pageSize: this.pageSize,
          processName: ""
        })
        .then(data => {
          this.tableLoading = false;
          if (data.code === "000") {
            this.tableData = data.data.result;
            this.total = data.data.totalCount;
          }
        });
    },
    getDetail(row) {
      this.showModal = true;
      this.$nextTick(() => {
        this.$refs["formValidate"].resetFields();
        this.formValidate = { ...row };
        this.formValidate.sdate = new Date(row.sdate);
        this.formValidate.edate = new Date(row.edate);
        this.formValidate.surrogate = row.surrogateId;
        this.formValidate.processName = row.proDisplayName;
        console.log(this.formValidate.processName);
        
      });
    },
    //查询流程名称
    getProcess() {
      this.api.getProcessAll().then(data => {
        if (data.code === "000") {
          this.processList = data.data;
        }
      });
    },
    //查询代理人下拉
    getSurrogate() {
      this.api.getSurrogate().then(data => {
        if (data.code === "000") {
          this.surrogateList = data.data;
        }
      });
    },
    addAuth() {
      this.$refs["formValidate"].validate(valid => {
        if (valid) {
          let date = new Date();
          if(this.formValidate.sdate < date){
            this.$Message.error("开始时间不能小于当前时间");
            return;
          }else if(this.formValidate.edate < date){
            this.$Message.error("结束时间不能小于当前时间");
            return;
          }
          if (this.formValidate.edate < this.formValidate.sdate) {
            this.$Message.error("结束时间不能小于开始时间");
            return;
          }
          let Str = ""
          let params = { ...this.formValidate };
          params.sdate = params.sdate.format("yyyy-MM-dd hh:mm:ss");
          params.edate = params.edate.format("yyyy-MM-dd hh:mm:ss");
          params.operator = localStorage.getItem("user");
          for (let i = 0; i < this.processList.length; i++) {
            if (this.processList[i].proDisplayName == params.processName) {
              params.processId = this.processList[i].processId
            }
          }
          console.log(params);
          loading();

          if (this.Interface === "1") {
            this.api.addAuth(params).then(data => {
              loadingHide();
              if (data.code === "000") {
                this.getList();
                successNotice("新增成功");
                this.showModal = false;
              } else {
                serviceError(data.message);
              }
            });

          }else{
            this.api.Edit(params).then(data => {
              loadingHide();
              if (data.code === "000") {
                this.getList();
                successNotice("修改成功");
                this.showModal = false;
              } else {
                serviceError(data.message);
              }
            });
          }
        }
      });
    },
    //删除授权
    removeAuth(id) {
      loading();
      this.api.delAuth({
          id: id
        })
        .then(data => {
          if (data.code === "000") {
            loadingHide();
            this.getList();
            successNotice("操作成功");
          } else {
            serviceError(data.message);
          }
        });
    }
  }
};
</script>


