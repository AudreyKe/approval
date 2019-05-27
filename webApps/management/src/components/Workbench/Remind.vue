<template>
    <div>
        <Row>
            <Col span="24" class="main_base_layout">
                 <Row>
                    <div class="single_title base_nav border_bm" @keydown.enter="serach">审核提醒
                        <Select v-model="materials_sel" placeholder="请选择任务类型" style="width:285px;height:40px;position: absolute;right: 370px;">
                            <Option v-for="item in materials_res" :value="item.value" :key="item.value" style="font-weight: normal;">{{ item.label }}</Option>
                        </Select>
                    <input type="text" class="serach" placeholder="请您输入任务名称" v-model="datalist">
                    <i class="bgserach" @click="serach"><img src="../../assets/images/serach.png" alt=""></i>
                    </div>
                </Row>
                <Row>
                    <div class="main_table_layout">
                       <Table :columns="remind" :data="remindList" @on-select="delData" @on-select-all="delDataList" @on-select-cancel="List"></Table>
                       <Button class="Send" @click="Sub">发送提醒</Button>
                       <Page :total="total" :page-size="10" size="small" @on-change="getList"/>
                    </div>
                </Row>
            </Col>
        </Row>
    </div>
</template>

<script>
import axios from 'axios';
export default {
  data() {
    return {
      materials_sel:"",
      handleSelect:false,
      materials_res:[
          {
            value: '',
            label: '全部'
          },
         {
            value: '0',
            label: '立项'
          },
          {
            value: '1',
            label: '评审'
          },
          {
            value: '2',
            label: '投决'
          },
          {
            value: '3',
            label: '消金'
          },
          {
            value: '4',
            label: '数据使用申请'
          },
      ],
      remind: [
        {
            type: 'selection',
            width: 60,
            align: 'center'
        },
        {
            title: '任务名称',
            key: 'proName',
            render:(h,params) =>{
            return h('div',[
              h('a',{
              on:{
                click:()=>{
                  if (params.row.proType == "0") {
                    this.$router.push({
                    name:'audit-task',
                    query:{
                      orderId:params.row.orderId,
                      processId:params.row.processId,
                      taskId:params.row.taskId,
                      isDetail:true
                    }
                  })
                  }else if(params.row.proType == "1"){
                    this.$router.push({
                    name:'audit-task',
                    query:{
                      orderId:params.row.orderId,
                      processId:params.row.processId,
                      taskId:params.row.taskId,
                      isDetail:true,
                      showDataList:true
                    }
                  })
                  }else if(params.row.proType == "2"){
                    this.$router.push({
                    name:'Cast',
                    query:{
                      orderId:params.row.orderId,
                      isDetail:true,
                      detailshow:true,
                      Approval:true
                    }
                  })
                  }else if(params.row.proType == "3"){
                    this.$router.push({
                    name:'consumption',
                    query:{
                      orderId:params.row.orderId,
                      isDetail:true,
                      detailshow:true,
                      Approval:true
                    }
                  })
                  }else if(params.row.proType == "4"){
                    this.$router.push({
                    name:'Dataliat',
                    query:{
                      orderId:params.row.orderId,
                      isDetail:true,
                      Approval:true
                    }
                  })
                  }
                }
              }
            },params.row.proName)])
          }
        },
        {
            title: '任务类型',
            key: 'proType',
            render:(h, params) => {
            if (params.row.proType == '0') {
              return h('div', [
                h('span', {
                    style: {
                        display: 'block'
                    }
                },"立项")
            ]);
            } else if(params.row.proType == '1') {
            return h('div', [
                h('span', {
                    style: {
                        display: 'block'
                    }
                },"评审")
            ]);
            }else if(params.row.proType == '2'){
              return h('div', [
                h('span', {
                    style: {
                        display: 'block'
                    }
                },"投决")
            ]);
            }else if(params.row.proType == '3'){
                return h('div', [
                h('span', {
                    style: {
                        display: 'block'
                    }
                },"消金")
            ]);
            }else if(params.row.proType == '4'){
                return h('div', [
                h('span', {
                    style: {
                        display: 'block'
                    }
                },"数据使用申请")
            ]);
            }
          }
        },
        {
            title: '任务启动时间',
            key: 'createTime'
        },
        {
            title: '状态',
            key: 'orderState',
            width: 150,
            render:(h, params) => {
            if (params.row.orderState == '0') {
              return h('div', [
                h('span', {
                    style: {
                        display: 'block'
                    }
                },"已通过")
            ]);
            } else if(params.row.orderState == '1') {
            return h('div', [
                h('span', {
                    style: {
                        display: 'block'
                    }
                },"审批中")
            ]);
            }else if(params.row.orderState == '2'){
              return h('div', [
                h('span', {
                    style: {
                        display: 'block'
                    }
                },"已终止")
            ]);
            }
          }
        }
    ],
      remindList: [],
      total:0,
      pageSize:10,
      pageNo:1,
      tableLoading: true,
      assetModal: false,
      orderId: "",
      modalActive: "1",
      datalist:'',
      dataTable:[],//提交的数据
    };
  },
  mounted() {
    this.getList()
  },
  methods: {
    getList(page = "1") {
      if (this.materials_sel) {
         this.pageNo = page
        this.serach()
      } else {
        this.api.getProsInApproval({
        pageNo:page,
        pageSize:this.pageSize,
        materials_sel:this.materials_sel,
        proName:this.datalist
      }).then(data => {
        this.tableLoading = false;
        if(data.code === "000"){
          this.remindList = data.data.result;
          this.total = data.data.totalCount;
        }
      });
      }
    },
    serach(){
    let str = this.datalist
    if (this.materials_sel == undefined) {
        this.materials_sel = ""
      }
      axios.get('/api/snaker/secretary/getProsInApproval?proType='+this.materials_sel+'&pageNo='+this.pageNo+'&pageSize='+this.pageSize+'&proName='+str)
      .then( (res)=> {
          console.log(res.data);
            if(res.data.code === "000"){
            this.remindList = res.data.data.result;
            console.log(this.remindList);
            
            this.total = res.data.data.totalCount;
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    delData(selection){
    this.dataTable = selection
    },
    delDataList(selection){
    this.dataTable = selection
    },
    List(selection){
      this.dataTable = selection
    },
    Sub(){
      if (this.dataTable.length==0) {
         this.$Message.error({
          content: "请最少选择一条提醒",
          duration: 3
        });
        return false;
      }
        let mailArr = [];
        let Arr = {"orderIds":this.dataTable};
        console.log(Arr);
        loading();
        //审核提醒
        this.api.sendRemind(Arr).then(data => {
          loadingHide();
          if (data.code == "000") {
            window.successNotice("提交成功");
            this.$router.push({name:"Remind"})
          } else {
            serviceError(data.message);
          }
        });
      }
    }
  }
</script>

<style scoped lang="less">
.serach{
  float: right;
  width: 330px;
  height: 40px;
  padding-left: 10px;
  position: relative;
  right: 0px;
  outline: none;
  border:1px solid #BDC3CB;
  border-right:none; 
}
.bgserach{
  display: block;
  width: 40px;
  height: 40px;
  z-index: 10;
  position: absolute;
  right: -10px;
  top: 0px;
  z-index: 1;
  border: 1px solid #BDC3CB;
  border-left:none; 
  img{
    display: block;
    width: 30px;
    margin-top: 5px;
  }
}
input::-webkit-input-placeholder {
      /* placeholder颜色  */
      color: #aab2bd;
      /* placeholder字体大小  */
      font-size: 12px;
}
  .seracher_cha{
    width: 90px;
    height: 40px;
    text-align: center;
    line-height: 40px;
    position: absolute;
    right: 0px;
    background-color: #2D8CF0;
    border: none;
    border-radius: 5px;
    color: #fff;
    outline: none;
    font-size: 14px;
  }
  .Send{
    width: 150px;
    position: absolute;
    right: 0px;
    margin-top: 15px;
    outline: none;
    background-color: #2D8CF0;
    color: #fff;
    border-radius: 3px;
  }
</style>

