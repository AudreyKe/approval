<style scoped lang="less">
.serach{
  float: right;
  width: 330px;
  height: 40px;
  padding-left: 10px;
  position: relative;
  right: 28px;
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
      color: #aab2bd;
      font-size: 12px;
  }
</style>

<template>
    <div>
        <Row>
            <Col span="24" class="main_base_layout">
                <Row >
                    <div class="single_title base_nav border_bm" @keydown.enter="serach">待办任务列表 
                    <Select v-model="materials_sel" placeholder="请选择任务类型" style="width:285px;height:40px;position: absolute;right: 370px;">
                        <Option v-for="item in materials_res" :value="item.value" :key="item.value" style="font-weight: normal;">{{ item.label }}</Option>
                    </Select>
                    <input type="text" class="serach" placeholder="请您输入任务名称" v-model="orderName">
                    <i class="bgserach" @click="serach"><img src="../../assets/images/serach.png" alt=""></i></div>
                </Row>
                <Row>
                    <div class="main_table_layout">
                        <Table :columns="columns" :data="tableData" stripe :loading="tableLoading" :no-data-text="'暂无数据'" :no-filtered-data-text="'暂无数据'"></Table>
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
      tableLoading: true,
      assetModal: false,
      materials_sel:"",
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
      modalActive: "1",
      orderName:'',
      columns: [
        {
          title: "任务名称",
          key: "orderName",
          render:(h,params) =>{
            return h('div',[
              h('a',{
              on:{
                click:()=>{
                  if (params.row.proType == "0") {
                    if (params.row.taskType =="委员决策") {
                    this.$router.push({
                    name:'audit-task',
                    query:{
                      orderId:params.row.orderId,
                      processId:params.row.processId,
                      taskId:params.row.taskId,
                      Agreed:true,
                      Exhibition:true,
                    }
                  })
                    } else if(params.row.taskType =="秘书发起会议"){
                    this.$router.push({
                    name:'audit-task',
                    query:{
                      orderId:params.row.orderId,
                      processId:params.row.processId,
                      taskId:params.row.taskId,
                      Exhibition:true,
                      Decision:true
                    }
                  })
                } else if(params.row.taskType =="秘书落实跟进事项"){
                    this.$router.push({
                    name:'audit-task',
                    query:{
                      orderId:params.row.orderId,
                      processId:params.row.processId,
                      taskId:params.row.taskId,
                      Exhibition:true,
                      ResultData:true,
                    }
                  })
                }else{
                    this.$router.push({
                    name:'audit-task',
                    query:{
                      orderId:params.row.orderId,
                      processId:params.row.processId,
                      taskId:params.row.taskId,
                      Exhibition:true
                    }
                  })
                    }
                  }else if(params.row.proType == "1"){
                    if (params.row.taskType =="委员决策") {
                    this.$router.push({
                    name:'audit-task',
                    query:{
                      orderId:params.row.orderId,
                      processId:params.row.processId,
                      taskId:params.row.taskId,
                      Agreed:true,
                      Exhibition:true,
                      showDataList:true
                    }
                  })
                    }else if(params.row.taskType =="秘书发起会议"){
                    this.$router.push({
                    name:'audit-task',
                    query:{
                      orderId:params.row.orderId,
                      processId:params.row.processId,
                      taskId:params.row.taskId,
                      Exhibition:true,
                      Decision:true,
                      showDataList:true
                    }
                  })
                    }else if(params.row.taskType =="秘书落实跟进事项"){
                    this.$router.push({
                    name:'audit-task',
                    query:{
                      orderId:params.row.orderId,
                      processId:params.row.processId,
                      taskId:params.row.taskId,
                      Exhibition:true,
                      ResultData:true,
                      showDataList:true
                    }
                  })
                    }else {
                    this.$router.push({
                    name:'audit-task',
                    query:{
                      orderId:params.row.orderId,
                      processId:params.row.processId,
                      taskId:params.row.taskId,
                      Exhibition:true,
                      showDataList:true
                    }
                  })
                    }
                  }else if(params.row.proType == "2"){
                  if (params.row.taskType =="秘书发起会议") {
                    this.$router.push({
                    name:'Cast',
                    query:{
                      orderId:params.row.orderId,
                      processId:params.row.processId,
                      taskId:params.row.taskId,
                      isDetail:true,
                      detailshow:true,
                      Examination:true,
                      Approval:true,
                      Exhibition:true,
                      Decision:true
                    }
                  })
                    } else if(params.row.taskType =="委员决策"){
                    this.$router.push({
                    name:'Cast',
                    query:{
                      orderId:params.row.orderId,
                      processId:params.row.processId,
                      taskId:params.row.taskId,
                      isDetail:true,
                      detailshow:true,
                      Examination:true,
                      Agreed:true,
                      Approval:true,
                      Exhibition:true,
                        }
                      })
                    } else if(params.row.taskType =="秘书落实跟进事项"){
                    this.$router.push({
                    name:'Cast',
                    query:{
                      orderId:params.row.orderId,
                      processId:params.row.processId,
                      taskId:params.row.taskId,
                      isDetail:true,
                      detailshow:true,
                      Examination:true,
                      ResultData:true,
                      // Approval:true
                        }
                      })
                    }else {
                      this.$router.push({
                      name:'Cast',
                      query:{
                      orderId:params.row.orderId,
                      processId:params.row.processId,
                      taskId:params.row.taskId,
                      isDetail:true,
                      detailshow:true,
                      Examination:true,
                      Approval:true
                    }
                  })
                    }
                  }else if(params.row.proType == "3" && params.row.performType == "消金-秘书选择"){
                    this.$router.push({
                    name:'consumption',
                    query:{
                      orderId:params.row.orderId,
                      processId:params.row.processId,
                      taskId:params.row.taskId,
                      isDetail:true,
                      detailshow:true,
                      App:true,
                      Exhibition:true,
                      Approval:true
                    }
                  })
                  }else if(params.row.proType == "3" && params.row.performType == "消金-秘书发起会议"){
                    this.$router.push({
                    name:'consumption',
                    query:{
                      orderId:params.row.orderId,
                      processId:params.row.processId,
                      taskId:params.row.taskId,
                      isDetail:true,
                      detailshow:true,
                      Examination:true,
                      Exhibition:true,
                      Approval:true,
                      Decision:true
                    }
                  })
                  } else if(params.row.proType == "3" && params.row.performType != "消金-秘书选择"){
                    if (params.row.taskType =="委员决策") {
                    this.$router.push({
                    name:'consumption',
                    query:{
                      orderId:params.row.orderId,
                      processId:params.row.processId,
                      taskId:params.row.taskId,
                      isDetail:true,
                      detailshow:true,
                      Examination:true,
                      Agreed:true,
                      Approval:true
                    }
                  })
                }else if(params.row.proType == "3" && params.row.performType == "消金-秘书落实跟进事项"){
                    this.$router.push({
                    name:'consumption',
                    query:{
                      orderId:params.row.orderId,
                      processId:params.row.processId,
                      taskId:params.row.taskId,
                      isDetail:true,
                      detailshow:true,
                      Examination:true,
                      Approval:true,
                      ResultData:true
                    }
                  })
                  }  else {
                  this.$router.push({
                    name:'consumption',
                    query:{
                      orderId:params.row.orderId,
                      processId:params.row.processId,
                      taskId:params.row.taskId,
                      isDetail:true,
                      detailshow:true,
                      Examination:true,
                      Approval:true
                    }
                  })
                    }
                  }else if(params.row.proType == "4"){
                    if (params.row.performType == "数据使用申请-申请验收") {
                    this.$router.push({
                    name:'Dataliat',
                    query:{
                      orderId:params.row.orderId,
                      processId:params.row.processId,
                      taskId:params.row.taskId,
                      isDetail:true,
                      check:true,
                      checkshow:true,
                      Approval:true
                    }
                  })
                    } else {
                    this.$router.push({
                    name:'Dataliat',
                    query:{
                      orderId:params.row.orderId,
                      processId:params.row.processId,
                      taskId:params.row.taskId,
                      isDetail:true,
                      Exhibition:true,
                      Approval:true
                    }
                  })
                    }
                  }
                }
              }
            },params.row.orderName)])
          }
        },
        {
          title: "任务类型",
          key: "performType"
        },
        {
          title: "任务启动时间",
          key: "createTime"
        },
        {
          title: "状态",
          key: "orderState",
          width: 150,
          render:(h, params) => {
              if (params.row.editFlag == 1) {
                return h('div',[
                h('span', {
                    style: {
                        marginRight: '5px'
                    }
                },params.row.orderState),
                h('Button', {
                    props: {
                        type: 'primary',
                        size: 'small'
                    },
                    style:{
                      borderRadius:'5px',
                      position: 'absolute',
                      right: '15px',
                      width:"70px"
                    },
                    on: {
                    click: () => {
                        if (params.row.proType == "0" || params.row.proType == "1") {
                        this.$router.push({name:"add-product", query:{
                        orderId:params.row.orderId,
                        isreader:true,
                        Datashow:true,
                        showResult:true
                        }})
                        }else if(params.row.proType == "2"){
                        this.$router.push({
                        name:'Cast',
                        query:{
                        orderId:params.row.orderId,
                        isDetail:false,
                        detailshow:true,
                        process:true,
                        backshow:true
                        }
                    })
                        }else if(params.row.proType == "3"){
                      this.$router.push({
                        name:'consumptionList',
                        query:{
                        orderId:params.row.orderId,
                        isDetail:false,
                        detailshow:true,
                        }
                    }) 
                        }
                        }
                    }
                }, "编辑")
            ]);
            }else{
               return h('div',[
                h('span', {
                    style: {
                        marginRight: '5px'
                    }
                },params.row.orderState),
                ]);
            }
          }
        }
      ],
      tableData: [],
      total:0,//页码
      pageSize:10,
      pageNo:1,
    };
  },
  mounted() {
    this.getList();

    
  },
  methods: {
    getList(page = "1") {
     if (this.materials_sel){
      this.pageNo = page
    this.serach()
     } else {
      this.api.getUpcomingList({
        materials_sel:this.materials_sel,
        proName:this.datalist,
        pageNo:page,
       pageSize:this.pageSize,
       orderName:this.orderName
      }).then(data => {
        this.tableLoading = false;
        if(data.code === "000"){
          this.tableData = data.data.result;
          this.total = data.data.totalCount;
          // console.log(this.tableData);
        }
      });
     }
    },
    serach(){
      if (this.materials_sel == undefined) {
        this.materials_sel = ""
      }
      let str = this.orderName
      axios.get('/api/snaker/task/activeTasks?proType='+this.materials_sel+'&pageNo='+this.pageNo+'&pageSize='+this.pageSize+'&orderName='+str)
        .then( (res)=> {
            if(res.data.code === "000"){
            this.tableData = res.data.data.result;
            this.total = res.data.data.totalCount;
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    }
  }
}
</script>


