<style lang="less" scoped>
</style>

<template>
    <fund-info  :isEdit="isEdit"></fund-info>
</template>

<script>
import fundInfo from "../common/fund_info.vue";
export default {
  components: { fundInfo },
  data() {
    return {
      isEdit: false
    };
  }
};
</script>





