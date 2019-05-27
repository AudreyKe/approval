<template>
    <div>
        <Row>
            <Col span="24" class="main_base_layout">
                <Row>
                    <div class="single_title base_nav border_bm" @keydown.enter="serach">待提交项目
                        <Select v-model="materials_sel" placeholder="请选择任务类型" style="width:285px;height:40px;position: absolute;right: 370px;">
                            <Option v-for="item in materials_res" :value="item.value" :key="item.value" style="font-weight: normal;">{{ item.label }}</Option>
                        </Select>
                    <input type="text" class="serach" placeholder="请您输入任务名称" v-model="datalist">
                    <i class="bgserach" @click="serach"><img src="../../assets/images/serach.png" alt=""></i>
                    </div>
                </Row>
                <Row>
                    <div class="main_table_layout">
                        <!-- <Table :columns="columns" :data="tableData" stripe :loading="tableLoading" :no-data-text="'暂无数据'" :no-filtered-data-text="'暂无数据'"></Table> -->
                          <Table :columns="columns" :data="tableData">
                            <template slot-scope="{ row }" slot="name">
                                <strong>{{ row.name }}</strong>
                            </template>
                            <template slot-scope="{ row, index }" slot="action">
                                <Button type="error" size="small" @click="remove(index)">Delete</Button>
                            </template>
                        </Table>
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
           {
            value: '4',
            label: '数据使用申请'
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
                  if (params.row.proType == "0") {
                    this.$router.push({
                    name:'View-info',
                    query:{
                    wsid:params.row.id,
                    processId:params.row.processId,
                    taskId:params.row.taskId,
                    // isDetail:true
                    }
                  })
                  }else if(params.row.proType == "1"){
                    this.$router.push({
                    name:'View-info',
                    query:{
                      wsid:params.row.id,
                      processId:params.row.processId,
                      taskId:params.row.taskId,
                    }
                  })
                  }else if(params.row.proType == "2"){
                    this.$router.push({
                    name:'Cast',
                    query:{
                    wsid:params.row.id,
                    isDetail:true,
                    detailshow:true,
                    }
                  })
                  }else if(params.row.proType == "3"){
                    this.$router.push({
                    name:'consumption',
                    query:{
                    wsid:params.row.id,
                    isDetail:true,
                    detailshow:true,
                    }
                  })
                  }else if(params.row.proType == "4"){
                    this.$router.push({
                    name:'Dataliat',
                    query:{
                    wsid:params.row.id,
                    isDetail:true,
                    draft:true,
                    // detailshow:true,
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
        render:(h,params) =>{
        if (params.row.proType == "0") {
          return h('div', [
          h('span', {
              style: {
                  display: 'block'
              }
          },"立项申请")
      ]);
        }else if(params.row.proType == "1"){
          return h('div', [
          h('span', {
              style: {
                  display: 'block'
              }
          },"项目评审申请")
      ]);
        }else if(params.row.proType == "2"){
          return h('div', [
          h('span', {
              style: {
                  display: 'block'
              }
          },"项目投决申请")
          ]);
        }else if(params.row.proType == "3"){
              return h('div', [
          h('span', {
              style: {
                  display: 'block'
              }
          },"消金立项申请")
          ]);
        }else if(params.row.proType == "4"){
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
          title: "修改时间",
          key: "createTime"
        },
        {
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
                  if (params.row.proType == "0") {
                  this.$router.push({
                    name:'add-product',
                    query:{
                      id:params.row.id,
                      isreader:true,
                      back:true,
                      Exhibition:true
                    }
                  })
                  }else if(params.row.proType == "1"){
                    this.$router.push({
                    name:'review-info',
                    query:{
                      orderId: params.row.orderId,
                      id:params.row.id,
                      wait:true,
                      back:true
                    }
                  })
                  }else if(params.row.proType == "2"){
                     this.$router.push({
                    name:'Cast',
                    query:{
                      id:params.row.id,
                      back:true,
                    }
                  })
                  }else if(params.row.proType == "3"){
                     this.$router.push({
                    name:'consumption',
                    query:{
                      id:params.row.id,
                      isreader:true,
                      back:true
                    }
                  })
                  }else if(params.row.proType == "4"){
                     this.$router.push({
                    name:'Dataliat',
                    query:{
                      draftid:params.row.id,
                      isreader:true,
                    }
                  })
                  }
                }
              }
            },"编辑"),
            h('Button', {
                props: {
                    type: 'error',
                    size: 'small'
                }, 
                style:{
                      borderRadius:'5px',
                      marginLeft:"10px"
                    },
                on: {
                    click: () => {
                      this.delStr = params.row.id
                      this.remove(params.index)
                    }
                }
            }, '删除')
            ])
          }
        }
      ],
      delStr:"",//删除的ID号
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
        this.api.getProDrafts({
        pageNo:page,
        pageSize:this.pageSize,
      }).then(data => {
        this.tableLoading = false;
        if(data.code === "000"){
          console.log(data);
          
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
      let str = this.datalist
      axios.get('/api/snaker/flow/getProDrafts?proType='+this.materials_sel+'&pageNo='+this.pageNo+'&pageSize='+this.pageSize+'&proName='+str)
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
      
    },
      remove (index) {
      this.tableData.splice(index, 1);
      loading();
      console.log(this.delStr);
        axios.get('/api/snaker/flow/unSubmitPro/delete?id='+this.delStr)
        .then( (res)=> {
          loadingHide();
          console.log(res.data);
         window.successNotice(res.data.message);
        })
        .catch(function (error) {
          console.log(error);
        });
    },
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

