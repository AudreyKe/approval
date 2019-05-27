<template>
  <div class="main-area">
    <Header-nav></Header-nav>
    <scroll ref="scroll" class="scroll-wrapper" :data="list" :pullUpLoad="pullUpLoadObj" @pullingUp="onPullingUp">
      <div>
        <div class="topserach">
        <select id="sendSybol" v-model="searchDto">
              <option  v-for="(item,index)  in sendList" name="sendSymbolId" :value="item.value" :key="index">{{item.label}}</option>
          </select>
          <input class="serach" type="text" v-model="serach">
          <button class="serbtn" type="button" @click="seracher">搜索</button>
          </div>
        <State-item v-for="item in list" :data="item" :key="item.$index" :next="item.urlList+'?orderId='+item.orderId"></State-item>
      </div>
    </scroll>
  </div>
</template>

<script>
import axios from 'axios';
import HeaderNav from "@/components/HeaderNav";
import StateItem from "@/components/StateItem";
export default {
  name: "doneList",
  components: { HeaderNav, StateItem },
  props: {},
  data() {
    return {
      pullUpLoadThreshold: -10,
      pullUpLoadMoreTxt: "加载更多",
      pullUpLoadNoMoreTxt: "没有更多数据",
      pullUpLoad: true,
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
            label: '消金项会'
          },
          {
            value: '4',
            label: '数据使用申请'
          },
      ],
      queryParams: {
        pageNo: 1,
        pageSize: 10
      },
      list: []
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
  watch: {
    pullUpLoadObj: {
      handler(val) {
        const scroll = this.$refs.scroll.scroll;
        if (val) {
          scroll.openPullUp();
          
        } else {
          scroll.closePullUp();
          
        }
      },
      deep: true
    }
  },
  created() {
    this.$api
      .queryAllPro(this.queryParams)
      .then(res => {
      let dealt = res.data.result
      for (let i = 0; i < dealt.length; i++) {
        if (dealt[i].proType =="4") {
          dealt[i].urlList = "/detdataApplication"
          dealt[i].performType = "数据使用申请"
        }else if (dealt[i].proType =="3") {
          dealt[i].urlList = "/detmoney"
          dealt[i].performType = "消金"
        }else if(dealt[i].proType =="2"){
          dealt[i].urlList = "/detCast"
          dealt[i].performType = "投决"
        }else if(dealt[i].proType =="1"){
          dealt[i].urlList = "/detDetail"
          dealt[i].performType = "评审"
        }else if(dealt[i].proType =="0"){
          dealt[i].urlList = "/detDetail"
          dealt[i].performType = "立项"
        }
      }
      console.log(dealt);
      this.list = dealt;
        this.pullUpLoad = res.data.hasNext;
      })
      .catch(err => {
        console.log(err);
      });
      
  },
  methods: {
    onPullingUp() {
      //更新数据
      setTimeout(() => {
        if (this._isDestroyed) {
          return;
        }
        if (this.pullUpLoad) {
          // 如果有新数据
          this.queryParams.pageNo += 1;
          this.queryParams.proType = this.searchDto;
          this.queryParams.orderName = this.serach;
          this.$api.queryAllPro(this.queryParams).then(res => {
            this.list = this.list.concat(res.data.result);
            for (let i = 0; i < this.list.length; i++) {
              if (this.list[i].proType =="4") {
              this.list[i].urlList = "/detdataApplication"
              this.list[i].performType = "数据使用申请"
            }else if (this.list[i].proType =="3") {
              this.list[i].urlList = "/detmoney"
              this.list[i].performType = "消金"
            }else if(this.list[i].proType =="2"){
              this.list[i].urlList = "/detCast"
              this.list[i].performType = "投决"
            }else if(this.list[i].proType =="1"){
              this.list[i].urlList = "/detDetail"
              this.list[i].performType = "评审"
            }else if(this.list[i].proType =="0"){
              this.list[i].urlList = "/detDetail"
              this.list[i].performType = "立项"
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
      axios.get('/api/snaker/flow/order/queryAllPro?orderName='+this.serach+'&pageNo='+this.queryParams.pageNo+'&pageSize='+this.queryParams.pageSize+'&proType='+this.searchDto)
      .then( (res)=> {
        if(res.data.code === "000"){
            console.log(res.data.data);
            // if (res.data.data.length == 0) {
            //   this.$refs.scroll.forceUpdate();
            // }
            this.list = res.data.data.result
            this.total = res.data.data.totalCount;
          for (let i = 0; i < this.list.length; i++) {
            if (this.list[i].proType =="4") {
              this.list[i].urlList = "/detdataApplication"
              this.list[i].performType = "数据使用申请"
            }else if (this.list[i].proType =="3") {
              this.list[i].urlList = "/detmoney"
              this.list[i].performType = "消金"
            }else if(this.list[i].proType =="2"){
              this.list[i].urlList = "/detCast"
              this.list[i].performType = "投决"
            }else if(this.list[i].proType =="1"){
              this.list[i].urlList = "/detDetail"
              this.list[i].performType = "评审"
            }else if(this.list[i].proType =="0"){
              this.list[i].urlList = "/detDetail"
              this.list[i].performType = "立项"
            }
          }
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    }
  }
};
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
