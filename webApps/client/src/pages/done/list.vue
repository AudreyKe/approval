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
      searchDto:"",
      serach:"",
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
      .getAlreadyList(this.queryParams)
      .then(res => {
      let dealt = res.data.result
      for (let i = 0; i < dealt.length; i++) {
        if (dealt[i].proType =="4") {
          dealt[i].urlList = "/donedataApplication"
        }else if (dealt[i].proType =="3") {
          dealt[i].urlList = "/donemoney"
        }else if(dealt[i].proType =="2"){
          dealt[i].urlList = "/doneCast"
        }else if(dealt[i].proType =="1"){
          dealt[i].urlList = "/doneDetail"
        }else if(dealt[i].proType =="0"){
          dealt[i].urlList = "/doneDetail"
        }
      }
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
          this.$api.getAlreadyList(this.queryParams).then(res => {
            this.list = this.list.concat(res.data.result);
            for (let i = 0; i < this.list.length; i++) {
            if (this.list[i].proType =="4") {
              this.list[i].urlList = "/donedataApplication"
            }else if (this.list[i].proType =="3") {
              this.list[i].urlList = "/donemoney"
            }else if(this.list[i].proType =="2"){
              this.list[i].urlList = "/doneCast"
            }else if(this.list[i].proType =="1"){
              this.list[i].urlList = "/doneDetail"
            }else if(this.list[i].proType =="0"){
              this.list[i].urlList = "/doneDetail"
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
      axios.get('/api/snaker/flow/order?orderName='+this.serach+'&pageNo='+this.queryParams.pageNo+'&pageSize='+this.queryParams.pageSize+'&proType='+this.searchDto)
      .then( (res)=> {
        if(res.data.code === "000"){
            console.log(res.data.data);
            this.list = res.data.data.result
            this.total = res.data.data.totalCount;
            for (let i = 0; i < this.list.length; i++) {
             if (this.list[i].proType =="4") {
              this.list[i].urlList = "/donedataApplication"
            }else if (this.list[i].proType =="3") {
              this.list[i].urlList = "/donemoney"
            }else if(this.list[i].proType =="2"){
              this.list[i].urlList = "/doneCast"
            }else if(this.list[i].proType =="1"){
              this.list[i].urlList = "/doneDetail"
            }else if(this.list[i].proType =="0"){
              this.list[i].urlList = "/doneDetail"
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
