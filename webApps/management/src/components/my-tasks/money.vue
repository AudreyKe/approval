<template>
    <div>
        <Row>
            <Col span="24" class="main_base_layout">
                <Row>
                    <div class="single_title base_nav border_bm" @keydown.enter="serach">消金会
                        <button class="btnpro" @click="btnpro">新建项目</button>
                        <!-- <Select v-model="materials_sel" placeholder="请选择任务类型" style="width:285px;height:40px;position: absolute;right: 370px;">
                            <Option v-for="item in materials_res" :value="item.value" :key="item.value" style="font-weight: normal;">{{ item.label }}</Option>
                        </Select> -->
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
            value: 'All',
            label: '全部'
          },
         {
            value: 'New York',
            label: '立即申请'
          },
          {
            value: 'London',
            label: '项目评审申请'
          },
          {
            value: 'Sydney',
            label: '投决申请'
          },
          {
            value: 'Ottawa',
            label: '消金立项申请'
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
                  this.$router.push({
                    name:'consumption',
                    query:{
                      orderId:params.row.orderId,
                      isDetail:true,
                      detailshow:true,
                      Approval:true
                    }
                  })
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
                    let index = params.index//获取点击当前的索引值
                    this.orderId = this.tableData[index].orderId
                    this.$store.commit('setorderId', this.orderId)
                    axios.get('/api/snaker/flow/order/'+this.orderId)
                    .then( (res)=> {
                    console.log(res.data);
                    if (res.status=='200') {//判断状态码是否为200如果为200为成功否者为失败
                      this.$router.push({name:"add-product", query:{
                        orderId:this.orderId,
                        isreader:true
                        }})
                      
                    } else {
                      alert('执行失败')
                    }
                  })
                  .catch(function (error) {
                    console.log(error);
                  });
                        }
                    }
                }, "编辑")
            ]);
            } else {
            return h('div', [
                h('span', {
                    style: {
                        display: 'block',
                        // marginRight: '10px'
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
      this.api.queryEliminatGoldList({
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
      axios.get('/apisnaker/eliminat/queryEliminatGoldList?pageNo='+this.pageNo+'&pageSize='+this.pageSize+'&proName='+str)
        .then( (res)=> {
            if(res.data.code === "000"){
            this.tableData = res.data.data.result;
            this.total = res.data.data.totalCount;
          }
        })
        .catch(function (error) {
          console.log(error);
        });
      
    },
        btnpro(){
             this.$router.push({name:"consumption"})
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
  .btnpro{
      border: none;
      outline: none;
      width: 75px;
      height: 35px;
      text-align: center;
      line-height: 35px;
      font-size: 12px;
      position: relative;
      bottom: 5px;
      left: 40px;
      background: #2D8CF0;
      border-radius:5px;
      color: #fff;
      border:1px solid#BDC3CB;
      cursor: pointer;
  }
</style>

