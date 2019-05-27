<template>
  <div class="main-area">
    <Header-nav></Header-nav>
    <scroll ref="scroll" class="scroll-wrapper" :data="list" :pullUpLoad="pullUpLoadObj" @pullingUp="onPullingUp">
      <div>
        <div class="topserach">
          <select id="sendSybol" v-model="searchDto">
              <option  v-for="(item,index)  in sendList" name="sendSymbolId" :value="item.value" :key="index" >{{item.label}}</option>
          </select>
          <input class="serach" type="text" v-model="serach">
          <button class="serbtn" type="button" @click="seracher">搜索</button>
        </div>
        <State-item v-for="item in list" :data="item" :key="item.$index" :next="item.urlList+'?orderId='+item.orderId+'&taskId='+item.taskId+'&processId='+item.processId+'&Conditional='+item.Conditional+'&Tips='+item.Tips+'&DateShowlist='+item.DateShowlist+'&Result='+item.Result"></State-item>
      </div>
    </scroll>
  </div>
</template>

<script>
import axios from 'axios';
import HeaderNav from "@/components/HeaderNav";
import StateItem from "@/components/StateItem";
export default {
  name: "todoList",
  components: { HeaderNav, StateItem },
  props: {},
  data() {
    return {
      pullUpLoad: true,
      pullUpLoadThreshold: -10,
      pullUpLoadMoreTxt: "加载更多",
      pullUpLoadNoMoreTxt: "没有更多数据",
      // pullUpLoad: false,
      serach:"",
      searchDto:"",
      sendList:[
           {
            value: '',
            label: '全部'
          },
         {
            value: '0',
            label: '立项会'
          },
          {
            value: '1',
            label: '评审会'
          },
          {
            value: '2',
            label: '投决会'
          },
          {
            value: '3',
            label: '消金会'
          },{
            value: '4',
            label: '数据使用申请'
          },
      ],
      queryParams: {
        pageNo: 1,
        pageSize: 10
      },
      list: [],
    };
  },
  computed: {
    
    pullUpLoadObj: function() {
      return this.pullUpLoad
        ? {
            threshold: parseInt(this.pullUpLoadThreshold),
            txt: {
              more: this.pullUpLoadMoreTxt,
              noMore: this.pullUpLoadNoMoreTxt
            }
          }
        : false;
    }
  },
  watch: {},
  created() {
    this.$api.getUpcomingList(this.queryParams).then(res => {
      var dealt = res.data.result
      for (let i = 0; i < dealt.length; i++) {
        if (dealt[i].proType =="4") {
         if (dealt[i].taskType == "申请验收") {
             dealt[i].urlList = "/tododataApplication"
             dealt[i].DateShowlist = "true"
             dealt[i].Conditional = "false"
             dealt[i].Tips = "false"
           }else{
             dealt[i].urlList = "/tododataApplication"
             dealt[i].DateShowlist = "false"
             dealt[i].Conditional = "false"
             dealt[i].Tips = "false"
           }
        }else if (dealt[i].proType =="3") {
          if (dealt[i].taskType == "委员决策") {
            dealt[i].urlList = "/todomoney"
            dealt[i].Conditional = "true"
          }else if(dealt[i].taskType == "秘书发起会议"){
            dealt[i].urlList = "/todomoney"
            dealt[i].Conditional = "false"
            dealt[i].Tips = "true"
          }else if(dealt[i].taskType == "秘书落实跟进事项"){
            dealt[i].urlList = "/todomoney"
            dealt[i].Result = "true"
          }else{
            dealt[i].urlList = "/todomoney"
            dealt[i].Conditional = "false"
          }
        }else if(dealt[i].proType =="2"){
             if (dealt[i].taskType == "委员决策") {
            dealt[i].urlList = "/todoCast"
            dealt[i].Conditional = "true"
          }else if(dealt[i].taskType == "秘书发起会议"){
            dealt[i].urlList = "/todoCast"
            dealt[i].Conditional = "false"
            dealt[i].Tips = "true"
          }else if(dealt[i].taskType == "秘书落实跟进事项"){
            dealt[i].urlList = "/todoCast"
            dealt[i].Result = "true"
          }else{
            dealt[i].urlList = "/todoCast"
            dealt[i].Conditional = "false"
          }
        }else if(dealt[i].proType =="1"){
          if (dealt[i].taskType == "委员决策") {
            dealt[i].urlList = "/todoDetail"
            dealt[i].Conditional = "true"
          }else if(dealt[i].taskType == "秘书发起会议"){
            dealt[i].urlList = "/todoDetail"
            dealt[i].Conditional = "false"
            dealt[i].Tips = "true"
          }else if(dealt[i].taskType == "秘书落实跟进事项"){
            dealt[i].urlList = "/todoDetail"
            dealt[i].Result = "true"
          }else{
            dealt[i].urlList = "/todoDetail"
            dealt[i].Conditional = "false"
          }
        }else if(dealt[i].proType =="0"){
          if (dealt[i].taskType == "委员决策") {
            dealt[i].urlList = "/todoDetail"
            dealt[i].Conditional = "true"
          }else if(dealt[i].taskType == "秘书发起会议"){
            dealt[i].urlList = "/todoDetail"
            dealt[i].Conditional = "false"
            dealt[i].Tips = "true"
          }else if(dealt[i].taskType == "秘书落实跟进事项"){
            dealt[i].urlList = "/todoDetail"
            dealt[i].Result = "true"
          }else{
            dealt[i].urlList = "/todoDetail"
            dealt[i].Conditional = "false"
          }
        }
      }
      this.list = dealt;
      console.log(this.list);
      
      this.pullUpLoad = res.data.hasNext;

    });
  },
  methods: {
    onPullingUp() {
      //更新数据
      setTimeout(() => {
        if (this._isDestroyed) {
          return ;
        }
        if (this.pullUpLoad) {
          // 如果有新数据
          this.queryParams.pageNo = this.queryParams.pageNo + 1;
          this.queryParams.proType = this.searchDto;
          this.queryParams.orderName = this.serach;
          this.$api.getUpcomingList(this.queryParams).then(res => {
          this.list = this.list.concat(res.data.result);
          console.log(this.list);
      for (let i = 0; i < this.list.length; i++) {
         if (this.list[i].proType =="4") {
           if (this.list[i].taskType == "申请验收") {
             dealt[i].urlList = "/tododataApplication"
             this.list[i].DateShowlist = "true"
           }else{
             dealt[i].urlList = "/tododataApplication"
             this.list[i].DateShowlist = "false"
           }
        }else if (this.list[i].proType =="3") {
          if (this.list[i].taskType == "委员决策") {
            this.list[i].urlList = "/todomoney"
            this.list[i].Conditional = "true"
          }else if(this.list[i].taskType == "秘书落实跟进事项"){
            this.list[i].urlList = "/todomoney"
            this.list[i].Result = "true"
          }else{
            this.list[i].urlList = "/todomoney"
            this.list[i].Conditional = "false"
          }
        }else if(this.list[i].proType =="2"){
             if (this.list[i].taskType == "委员决策") {
            this.list[i].urlList = "/todoCast"
            this.list[i].Conditional = "true"
          }else if(this.list[i].taskType == "秘书落实跟进事项"){
            this.list[i].urlList = "/todoCast"
            this.list[i].Result = "true"
          }else{
            this.list[i].urlList = "/todoCast"
            this.list[i].Conditional = "false"
          }
        }else if(this.list[i].proType =="1"){
             if (this.list[i].taskType == "委员决策") {
            this.list[i].urlList = "/todoDetail"
            this.list[i].Conditional = "true"
          }else if(this.list[i].taskType == "秘书落实跟进事项"){
            this.list[i].urlList = "/todoDetail"
            this.list[i].Result = "true"
          }else{
            this.list[i].urlList = "/todoDetail"
            this.list[i].Conditional = "false"
          }
        }else if(this.list[i].proType =="0"){
          if (this.list[i].taskType == "委员决策") {
            this.list[i].urlList = "/todoDetail"
            this.list[i].Conditional = "true"
          }else if(this.list[i].taskType == "秘书落实跟进事项"){
            this.list[i].urlList = "/todoDetail"
            this.list[i].Result = "true"
          }else{
            this.list[i].urlList = "/todoDetail"
            this.list[i].Conditional = "false"
          }
        }
      }
            this.pullUpLoad = res.data.hasNext;
          });
        } else {
          // 如果没有新数据
          this.$refs.scroll.forceUpdate();
        }
        
      }, 1500);
    },
    seracher(){
      if (this.searchDto == undefined) {
        this.searchDto = ""
      }
      this.queryParams.pageNo = 1;
      axios.get('/api/snaker/task/activeTasks?proType='+this.searchDto+'&pageNo='+this.queryParams.pageNo+'&pageSize='+this.queryParams.pageSize+'&orderName='+this.serach)
      .then( (res)=> {
        if(res.data.code === "000"){
            console.log(res.data.data);
            this.list = res.data.data.result
            this.total = res.data.data.totalCount;
      for (let i = 0; i < this.list.length; i++) {
      if (this.list[i].proType =="4") {
        if (this.list[i].taskType == "申请验收") {
             this.list[i].urlList = "/tododataApplication"
             this.list[i].DateShowlist = "true"
           }else{
             this.list[i].urlList = "/tododataApplication"
             this.list[i].DateShowlist = "false"
           }
        }else if (this.list[i].proType =="3") {
          if (this.list[i].taskType == "委员决策") {
            this.list[i].urlList = "/todomoney"
            this.list[i].Conditional = "true"
          }else if(this.list[i].taskType == "秘书落实跟进事项"){
            this.list[i].urlList = "/todomoney"
            this.list[i].Result = "true"
          }else{
            this.list[i].urlList = "/todomoney"
            this.list[i].Conditional = "false"
          }
        }else if(this.list[i].proType =="2"){
             if (this.list[i].taskType == "委员决策") {
            this.list[i].urlList = "/todoCast"
            this.list[i].Conditional = "true"
          }else if(this.list[i].taskType == "秘书落实跟进事项"){
            this.list[i].urlList = "/todoCast"
            this.list[i].Result = "true"
          }else{
            this.list[i].urlList = "/todoCast"
            this.list[i].Conditional = "false"
          }
        }else if(this.list[i].proType =="1"){
             if (this.list[i].taskType == "委员决策") {
            this.list[i].urlList = "/todoDetail"
            this.list[i].Conditional = "true"
          }else if(this.list[i].taskType == "秘书落实跟进事项"){
            this.list[i].urlList = "/todoDetail"
            this.list[i].Result = "true"
          }else{
            this.list[i].urlList = "/todoDetail"
            this.list[i].Conditional = "false"
          }
        }else if(this.list[i].proType =="0"){
          if (this.list[i].taskType == "委员决策") {
            this.list[i].urlList = "/todoDetail"
            this.list[i].Conditional = "true"
          }else if(this.list[i].taskType == "秘书落实跟进事项"){
            this.list[i].urlList = "/todoDetail"
            this.list[i].Result = "true"
          }else{
            this.list[i].urlList = "/todoDetail"
            this.list[i].Conditional = "false"
          }
        }
      }
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    }
  }
</script>
<style scoped>
.topserach{
  background-color: #fff;
  padding-bottom: 10px;
}
#sendSybol{
  width:100px;
  height:25px;
  font-size: 12PX;
  margin-left:10px;
}
.serach{
  width:170px;
  height:20px;
  position: absolute;
  top: 12px;
  margin-left: 10px;
  font-size: 12px;
}
.serbtn{
  width:50px;
  height:25px;
  font-size: 12px;
  border-radius:5px;
  border:none;
  border:1px solid #eee;
  position: absolute;
  top: 13px;
  right: 12px;
  background-color: #fff;
}
</style>


