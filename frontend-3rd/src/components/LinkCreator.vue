<template>
  <div style="position:relative;">
    <br/>
    <h1>新建关系</h1>
    <h2>右击节点选择新建关系</h2>
    <h1>source</h1>
    <div v-if="source !== null">{{ source.name }}</div>
    <div v-if="source === null">null</div>
    <el-divider/>
    <h2>再次点击节点选择节点</h2>
    <div>
      <h1>target</h1>
      <div v-if="target !== null">{{ target.name }}</div>
      <div v-if="target === null">null</div>
    </div>
    <div>
      <el-divider/>
      <el-button style="background: #fb7299;color: white" @click="handleLinkCreatorAck">确认</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "LinkCreator",
  data() {
    return {
      // source: null,
      // target: null,
    }
  },
  computed: {
    source() {
      return this.$store.state.linkCreatorSource
    },
    target() {
      return this.$store.state.linkCreatorTarget
    }
  },
  methods: {
    handleLinkCreatorAck() {
      const _this = this
      _this.$store.commit('ackLinkCreator')
    },

    // 清除数据
    clearData() {
      const _this = this
      _this.$store.commit('setLinkCreatorSource', {d: null})
      _this.$store.commit('setLinkCreatorTarget', {d: null})
    }
  },
}
</script>

<style scoped>

</style>
