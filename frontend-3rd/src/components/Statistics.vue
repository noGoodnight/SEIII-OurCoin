<template>
  <div>
    <br/>
    <h1>图谱统计</h1>
    <div>
      <h2>节点数量统计</h2>
      <h3>{{ nodeNum }}</h3>
    </div>
    <div>
      <h2>关系数量统计</h2>
      <h3>{{ linkNum }}</h3>
    </div>
  </div>
</template>

<script>
export default {
  name: "Statistics",
  data() {
    return {}
  },
  computed: {
    nodeNum() {
      return this.$store.state.nodeNum;
    },
    linkNum() {
      return this.$store.state.linkNum;
    },
  }
}
</script>

<style scoped>

</style>