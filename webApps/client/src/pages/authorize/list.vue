<template>
  <div class="main-area">
    <Header-nav></Header-nav>
    <scroll ref="scroll" class="scroll-wrapper" :data="list" :pullUpLoad="pullUpLoadObj" @pullingUp="onPullingUp">
      <div>
        <Task-item v-for="item in list" :data="item" :key="item.$index" :deleteHandler="deleteTask"></Task-item>
      </div>
    </scroll>
    <div @click="addTask" class="add-btn">+</div>
  </div>
</template>

<script>
import HeaderNav from "@/components/HeaderNav";
import TaskItem from "@/components/TaskItem";
export default {
  name: "authorizeList",
  components: { HeaderNav, TaskItem },
  props: {},
  data() {
    return {
      pullUpLoadThreshold: -10,
      pullUpLoadMoreTxt: "加载更多",
      pullUpLoadNoMoreTxt: "没有更多数据",
      pullUpLoad: true,
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
  watch: {},
  created() {
    this.updateTaskList();
  },
  methods: {
    updateTaskList() {
      this.$api.getAuthList(this.queryParams).then(res => {
        console.log(res.data.result);
        
        this.list = res.data.result;
        this.pullUpLoad = res.data.hasNext;
      });
    },
    addTask(value) {
      this.$router.push("/addTask");
    },
    deleteTask(id) {
      this.$api.delAuth({ id: id }).then(() => {
        this.$toast({
          message: `删除成功`,
          position: "top",
          duration: 2000
        });
        this.updateTaskList();
      });
    },
    onPullingUp() {
      //更新数据
      setTimeout(() => {
        if (this._isDestroyed) {
          return;
        }
        if (this.pullUpLoad) {
          // 如果有新数据
          this.queryParams.pageNo += 1;
          this.$api.getAuthList(this.queryParams).then(res => {
            this.list = this.list.concat(res.data.result);
            this.pullUpLoad = res.data.hasNext;
          });
        } else {
          // 如果没有新数据
          this.$refs.scroll.forceUpdate();
        }
      }, 1500);
    }
  }
};
</script>

<style lang="less" scoped>
.add-btn {
  position: fixed;
  bottom: 49px;
  right: 20px;
  font-size: 50px;
  width: 50px;
  height: 50px;
  line-height: 50px;
  border-radius: 50%;
  text-align: center;
  color: #fff;
  background: rgba(23, 101, 240, 1);
  box-shadow: 0px 2px 8px 0px rgba(23, 101, 240, 0.47);
}
</style>
