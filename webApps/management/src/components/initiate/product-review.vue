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

<template>
    <div>
        <Row>
            <Col span="24" class="main_base_layout">
                <Row>
                    <div class="single_title base_nav border_bm" @keydown.enter="serach">项目评审 
                    <Select v-model="materials_sel" placeholder="请选择任务类型" style="width:285px;height:40px;position: absolute;right: 370px;">
                        <Option v-for="item in materials_res" :value="item.value" :key="item.value" style="font-weight: normal;">{{ item.label }}</Option>
                    </Select>
                    <input type="text" class="serach" placeholder="请您输入任务名称" v-model="datalist">
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
      modalActive: "1",
      datalist:'',
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
      columns: [
        {
          title: "任务名称",
          key: "orderName",
          render:(h,params) =>{
            return h('div',[
              h('a',{
              on:{
                click:()=>{
                  if (params.row.proType == '0') {
                      this.$router.push({
                      name:'audit-task',
                      query:{
                        orderId:params.row.orderId,
                        processId:params.row.processId,
                        taskId:params.row.taskId,
                        isDetail:true,
                        Exhibition:true,
                        // showDataList:true,
                      }
                    })
                  }else if(params.row.proType == '1'){
                    this.$router.push({
                    name:'audit-task',
                    query:{
                      orderId:params.row.orderId,
                      processId:params.row.processId,
                      taskId:params.row.taskId,
                      isDetail:true,
                      Exhibition:true,
                      showDataList:true,
                    }
                  })
                  }
                }
              }
            },params.row.orderName)])
          }
        },
        {
          title: "审批结果",
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
          title: "任务类型",
          key: "proType",
          render:(h,params) =>{
        if (params.row.proType == "0") {
          return h('div', [
          h('span', {
              style: {
                  display: 'block'
              }
          },"立项")
      ]);
        }else if(params.row.proType == "1"){
          return h('div', [
          h('span', {
              style: {
                  display: 'block'
              }
          },"评审")
      ]);
        }else if(params.row.proType == "2"){
          return h('div', [
          h('span', {
              style: {
                  display: 'block'
              }
          },"投决")
          ]);
        }else if(params.row.proType == "3"){
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
          title: "操作",
          key: "",
          width: 150,
          render: (h, params) => {
            if (params.row.proType == "0"){
              return h("div", [
                h(
                  "Button",
                  {
                    props: {
                      type: "primary",
                      size: "small"
                    },
                    style: {
                      marginRight: "5px",
                      borderRadius: '2px'
                    },
                    on: {
                      click: () => {
                        this.$router.push({
                          name: "review-info",
                          query: {
                            orderId: params.row.orderId,
                            reviewFlase:true
                          }
                        });
                      }
                    }
                  },
                  "发起评审"
                )
              ]);
            } else {
            }
          }
        }
      ],
      tableData: [],
      total: 0,
       pageSize:10,
      pageNo:1,
    };
  },
  mounted() {
    this.getList();
  },
  methods: {
    getList(page = "1") {
      if (this.materials_sel) {
         this.pageNo = page
        this.serach()
      } else {
          this.api.reviewList({
          materials_sel:this.materials_sel,
          proName:this.datalist,
          pageNo:page,
          pageSize:this.pageSize,
        })
        .then(data => {
          this.tableLoading = false;
          if (data.code === "000") {
            this.tableData = data.data.result;
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
      axios.get('/api/snaker/flow/reviewList?proType='+this.materials_sel+'&pageNo='+this.pageNo+'&pageSize='+this.pageSize+'&orderName='+str)
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
};
</script>


