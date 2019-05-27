<style lang="less" scoped>
[v-cloak] {
  display: none;
}
</style>

<template>
    <fund-info v-cloak fund-info :id="id" :isDetail="isDetail" :processId="processId" :taskId="taskId" :isEdit="!isEdit" :baseInfo="fundData"></fund-info>
</template>


<script>
import fundInfo from '../common/fund_info.vue'
export default {
    components:{fundInfo},
    data(){
        return {
            isEdit:false,
            fundData:{},
            id:"",
            processId: "",
            taskId: "",
            isDetail:false,//是否只查看
            showDataList:false
            // isReview:false//是否为评审
        }
    },
    created() {
        this.id = this.$route.query.orderId;
        this.processId = this.$route.query.processId;
        this.taskId = this.$route.query.taskId;
        this.isEdit = this.$route.query.isEdit || false;
        this.isDetail = this.$route.query.isDetail || false;
        this.showDataList = this.$route.query.showDataList || false;
    },
    mounted() {
        if (!this.showDataList) {
            this.api.getUpcomingDetail({
            id:this.id
        }).then(data =>{
            if(data.code === "000"){
                this.fundData = data.data;
                let proModelArr = []
                let brr = []
                console.log(this.fundData);
                let proModel = data.data.proModelList
                if (proModel != null) {
                    for (let i = 0; i < proModel.length; i++) {
                        proModelArr.push(proModel[i].name)
                    }
                }
                this.fundData.proModel = proModelArr.toString()
                
                    for (let i = 0; i < data.data.teamList.length; i++) {
                    brr.push(data.data.teamList[i].fullName)
                    }

                    this.fundData.teamList = brr.toString();
                for (let i = 0; i < data.data.layerList.length; i++){
                    this.fundData.layerList[i].amount = this.parseFormatNum(data.data.layerList[i].amount,2)
                }

            }else{
                window.serviceError(data.message);
            }
        })
        }
    },
 methods:{
    changeList(str){
      let brr =[]
      let arr = JSON.parse(str);
      for (let i = 0; i < arr.length; i++) {
      brr.push(arr[i].fullName)
      }
      return brr.toString()
    },
//金额格式转换
parseFormatNum(number,n){
    if(n != 0 ){
        n = (n > 0 && n <= 20) ? n : 2;
    }
    number = parseFloat((number + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
    var sub_val = number.split(".")[0].split("").reverse();
    var sub_xs = number.split(".")[1]; 
    var show_html = "";  
    for (var i = 0; i < sub_val.length; i++){  
        show_html += sub_val[i] + ((i + 1) % 3 == 0 && (i + 1) != sub_val.length ? "," : "");  
    }  
    
    if(n == 0 ){
        return show_html.split("").reverse().join("");  
            }else{
            return show_html.split("").reverse().join("") + "." + sub_xs;  
            }
        }
    }
}
</script>


