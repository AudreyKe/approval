<style lang="less">
    @import "./home.less";
    @import "../common/common.less";
</style>
<template>
    <div class="home-main main_base_layout">
        <Row :gutter="10"   >
            <Col :md="24" :lg="8" >
                <Row class-name="home-page-row1" :gutter="10">
                    <Col :md="12" :lg="24" :style="{marginBottom: '10px'}">
                        <Row type="flex" class="user-infor">
                            <!-- <Col span="8">
                                <Row class-name="made-child-con-middle" type="flex" align="middle">
                                    <img class="avator-img" :src="avatorPath" />
                                </Row>
                            </Col> -->
                            <Col span="16" style="padding-left:6px;">
                                <Row class-name="made-child-con-middle" type="flex" align="middle">
                                    <div>
                                        <b class="card-user-infor-name">{{setWelcome}}:</b><b class="card-user-infor-name">{{username}}</b>
                                    </div>
                                </Row>
                            </Col>
                        </Row>
                        <div class="line-gray"></div>
                        <Row class="margin-top-8">
                            <Col span="4"><p class="notwrap">当前时间:</p></Col>
                            <Col span="16" class="padding-left-8">{{setTime}}</Col>
                        </Row>
                    </Col>
                </Row>
            </Col>
        </Row>
    </div>
</template>

<script>
export default {
    name: 'home',
    data(){
        return {
            welcome:'',
            time:'',
            uname:''
        }
    },
    created(){
        if (sessionStorage.isok) {
            this.$router.push('/home')
        } else {
             this.$router.push('/login')
        }
    },
    computed:{
        setTime(){
            let time = new Date().format('yyyy-MM-dd hh:mm:ss');
            return this.time + time;
        },
        setWelcome(){
            let str = new Date().getHours();
            if(str < 12){
                return this.welcome + "上午好"
            }else if(str >= 12 && str <= 18){
                return this.welcome + "下午好"
            }else{
                return this.welcome + "晚上好"
            }
        },
        username(){
            let uname = localStorage.getItem('user');
            return this.uname = uname
        }
    }
};
</script>
