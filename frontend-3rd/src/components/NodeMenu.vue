<!--节点菜单组件-->

<template>
  <div class="ln-menu" :id="'nodeMenu-' + suffix" @contextmenu.prevent="" @click.prevent="">
    <ul>
      <li @click="handleLinkCreate">新建关系</li>
      <el-divider class="divider-ln-menu"/>
      <li @click="handleNodeDelete">删除</li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "NodeMenu",
  data() {
    return {
      isShow: false
    }
  },
  props: {
    suffix: String,
  },
  methods: {
    // 新建关系选项
    handleLinkCreate() {
      this.$emit("createLink")
    },

    // 删除节点选项
    handleNodeDelete() {
      this.$emit("deleteNode")
    },

    // 调整节点选项菜单位置
    locateNodeMenu(event) {
      const _this = this
      let div = document.getElementById('nodeMenu-' + _this.suffix)
      div.style.top = event.offsetY + "px"
      div.style.left = event.offsetX + "px"
    }
  },
}
</script>

<style scoped>
</style>
