<template>
    <div>
        <Row>
            <Col span="24" class="main_base_layout">
                <Row>
                    <div class="single_title base_nav border_bm" @keydown.enter="serach">我发起的 <input type="text" class="serach" placeholder="请您输入任务名称" v-model="datalist">
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
      orderId: "",
      modalActive: "1",
      datalist:'',
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
                    this.$router.push({
                    name:'audit-task',
                    query:{
                      orderId:params.row.orderId,
                      processId:params.row.processId,
                      taskId:params.row.taskId,
                      isDetail:true,
                      Exhibition:true
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
                      showDataList:true,
                      Exhibition:true
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
                        detailshow:true,
                        Approval:true
                        }
                    }) 
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
            if (params.row.orderState == '已终止') {
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
                      right: '10px'
                    },
                    on: {
                      click: () => {
                        if (params.row.proType == "0" || params.row.proType == "1") {
                        this.$router.push({
                          name:"add-product", 
                          query:{orderId:params.row.orderId,
                          isreader:true
                          }
                        })
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
                  }else if(params.row.proType == "4"){
                      this.$router.push({
                        name:'Dataliat',
                        query:{
                        orderId:params.row.orderId,
                        detailshow:true,
                        Restart:true
                        }
                    }) 
                        }
                        }
                    }
                }, "重新发起")
            ]);
            } else {
            return h('div', [
                h('span', {
                    style: {
                        display: 'block'
                    }
                },params.row.orderState)
            ]);
            }
          }
          
        }
      ],
      tableData: [],
      total:0,
       pageSize:10,
      pageNo:1,
    };
  },
  mounted() {
    this.getList()
  },
  methods: {
    getList(page = "1") {
      if (this.datalist) {
         this.pageNo = page
        this.serach()
      } else {
        this.api.getInitiateList({
        pageNo:page,
        pageSize:this.pageSize,
      }).then(data => {
        this.tableLoading = false;
        if(data.code === "000"){
          this.tableData = data.data.result;
          this.total = data.data.totalCount;
        }
      });
      }
    },
    serach(){

      let str = this.datalist
      axios.get('/api/snaker/flow/order?pageNo='+this.pageNo+'&pageSize='+this.pageSize+'&orderName='+str)
        .then( (res)=> {
          console.log(res.data);
          
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
      /* placeholder颜色  */
      color: #aab2bd;
      /* placeholder字体大小  */
      font-size: 12px;
  }
</style>

