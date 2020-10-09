<template>
  <div class="dashboard-container">
    <div class="dashboard-text">name: {{ list }}</div>
  </div>
</template>

<script>
import { getList } from '@/api/device';
export default {
  data() {
      return {
          list: null,
          name:"device",
          deviceList:""
      }
  },
  name: 'device',
  computed: {

  },
   created() {
      this.fetchData();
  },
   methods: {
      fetchData() {
          getList().then(response => {

              this.list = response;
          });
          this.$message("Hello")
      }
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
