<template>
    <div>
        <Row>
            <Col span="24" class="main_base_layout">
                <Row>
                    <div class="single_title base_nav border_bm" @keydown.enter="serach">数据使用申请
                        <button class="btnpro" @click="btnpro">新建项目</button>
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
          title: "应用系统名称",
          key: "systemName",
          render:(h,params) =>{
            return h('div',[
              h('a',{
              on:{
                click:()=>{
                  this.$router.push({
                    name:'Dataliat',
                    query:{
                      orderId:params.row.orderId,
                      isDetail:true,
                      Approval:true
                      // check:true
                    }
                  })
                }
              }
            },params.row.systemName)])
          }
        },
        {
          title: "当前节点-审批人",
          key: "nodeName",
        },
        {
          title: "申请部门",
          key: "applyDepartment",
        },
        {
          title: "建议完成时间",
          key: "suggestCompleteTime"
        },
        {
          title: "申请类别",
          key: "applyCategory",
          width: 500,
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
      // this.serach()
    } else {
      this.api.queryDataUseApplyList({
      pageNo:page,
      pageSize:this.pageSize,
       }).then(data => {
      this.tableLoading = false;
      if(data.code === "000"){
        this.tableData = data.data.result;
        //处理当前节点问题
        for (let i = 0; i < this.tableData.length; i++) {
         if (this.tableData[i].nowNode != null) {
           this.tableData[i].nodeName = this.tableData[i].nowNode.nodeName +"-"+this.tableData[i].nowNode.actors.toString()
         }else{
           this.tableData[i].nodeName = "已完成"
         }
        }

        this.total = data.data.totalCount;
        }
      });
      }
    },
    // serach(){
    //   let str = this.datalist
    //   axios.get('/apisnaker/eliminat/queryEliminatGoldList?pageNo='+this.pageNo+'&pageSize='+this.pageSize+'&proName='+str)
    //     .then( (res)=> {
    //         if(res.data.code === "000"){
    //         this.tableData = res.data.data.result;
    //         this.total = res.data.data.totalCount;
    //       }
    //     })
    //     .catch(function (error) {
    //       console.log(error);
    //     });
      
    // },
        btnpro(){
             this.$router.push({name:"Dataliat"})
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

