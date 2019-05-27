<template>
    <div>
        <Row>
            <Col span="24" class="main_base_layout">
                <Row>
                    <div class="single_title base_nav border_bm" @keydown.enter="serach">资料上传
                        <Select v-model="materials_sel" placeholder="请选择任务类型" style="width:285px;height:40px;position: absolute;right: 370px;">
                            <Option v-for="item in materials_res" :value="item.value" :key="item.value" style="font-weight: normal;">{{ item.label }}</Option>
                        </Select>
                    <input type="text" class="serach" placeholder="请您输入任务名称" v-model="datalist">
                    <i class="bgserach" @click="serach"><img src="../../assets/images/serach.png" alt=""></i>
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
    </div>
</template>

<script>
import axios from 'axios';
export default {
  data() {
    return {
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
      ],
      tableLoading: true,
      assetModal: false,
      orderId: "",
      modalActive: "1",
      datalist:'',
      columns: [
        {
          title: "任务名称",
          key: "proName",
          render:(h,params) =>{
            return h('div',[
              h('a',{
              on:{
                click:()=>{
                  if (params.row.proType == "0"){
                    this.$router.push({
                    name:'audit-task',
                    query:{
                      orderId:params.row.orderId,
                      proId:params.row.proId,
                      isDetail:true,
                    }
                  })
                  }else if(params.row.proType == "1"){
                    this.$router.push({
                    name:'audit-task',
                    query:{
                      orderId:params.row.orderId,
                      proId:params.row.proId,
                      isDetail:true,
                      Exhibition:false,
                      showDataList:true
                    }
                  })
                  }else if(params.row.proType == "2"){
                    this.$router.push({
                    name:'Cast',
                    query:{
                      proId:params.row.proId,
                      orderId:params.row.orderId,
                      isDetail:true,
                      detailshow:true,
                      Exhibition:false
                    }
                  })
                  }else if(params.row.proType == "3"){
                    this.$router.push({
                    name:'consumption',
                    query:{
                      proId:params.row.proId,
                      orderId:params.row.orderId,
                      isDetail:true,
                      detailshow:true,
                      Exhibition:false
                    }
                  })
                  }
                }
              }
            },params.row.proName)])
          }
        },
         {
          title: "任务类型",
          key: "proType",
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
            }
          }
        },
        {
          title: "任务启动时间",
          key: "createTime"
        },
        {
          title: "项目审批状态",
          key: "orderState",
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
        },
         {
          title: "附件审批状态",
          key: "attachTaskState",
          render:(h, params) => {
            if (params.row.attachTaskState == undefined) {
              return h('div', [
                h('span', {
                    style: {
                        display: 'block'
                    }
                },"未上传附件")
            ]);
            }else{
                return h('div', [
                h('span', {
                    style: {
                        display: 'block'
                    }
                },params.row.attachTaskState)
            ]);
            }
          }
        },{
          title: "操作",
          key: "orderName",
          width: 150,
          render:(h,params) =>{
            return h('div',[
              h('Button',{
                  props: {
                      type: 'primary',
                      size: 'small'
                    },
                    style:{
                      borderRadius:'5px',
                    },
              on:{
                click:()=>{
                  if (params.row.proType == "0"){
                    this.$router.push({
                    name:'audit-task',
                    query:{
                      orderId:params.row.orderId,
                      proId:params.row.proId,
                      EnclosureShow:true,
                      isDetail:true,
                      // Exhibition:false
                    }
                  })
                  }else if(params.row.proType == "1"){
                    this.$router.push({
                    name:'audit-task',
                    query:{
                      orderId:params.row.orderId,
                      proId:params.row.proId,
                      EnclosureShow:true,
                      isDetail:true,
                      // Exhibition:false
                    }
                  })
                  }else if(params.row.proType == "2"){
                    this.$router.push({
                    name:'Cast',
                    query:{
                      proId:params.row.proId,
                      orderId:params.row.orderId,
                      isDetail:true,
                      detailshow:true,
                      Exhibition:false,
                      EnclosureShow:true,
                      pdfshow:true
                    }
                  })
                  }else if(params.row.proType == "3"){
                    this.$router.push({
                    name:'consumption',
                    query:{
                      proId:params.row.proId,
                      orderId:params.row.orderId,
                      isDetail:true,
                      detailshow:true,
                      Exhibition:false,
                      EnclosureShow:true,
                      pdfshow:true
                    }
                  })
                  }
                }
              }
            },"编辑附件")])
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
      if (this.materials_sel) {
         this.pageNo = page
        this.serach()
      } else {
        this.api.getProsForFileApproval({
        materials_sel:this.materials_sel,
        proName:this.datalist,
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
      if (this.materials_sel == undefined) {
        this.materials_sel = ""
      }
      axios.get('/api/snaker/secretary/getProsForFileApproval?proType='+this.materials_sel+'&pageNo='+this.pageNo+'&pageSize='+this.pageSize+'&proName='+this.datalist)
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
</style>

