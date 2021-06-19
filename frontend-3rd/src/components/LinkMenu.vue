<template>
  <div class="ln-menu" :id="'linkMenu-' +suffix" @contextmenu.prevent="" @click.prevent="">
    <ul>
      <li @click="handleLinkDelete">删除</li>
      <el-divider class="divider-ln-menu"/>
      <li @click="handleLinkLabelSwitch">标签</li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "LinkMenu",
  data() {
    return {}
  },
  props: {
    suffix: String,
  },
  methods: {
    // 删除关系选项
    handleLinkDelete() {
      this.$emit("deleteLink")
    },

    // 切换关系标签显示选项
    handleLinkLabelSwitch() {
      this.$emit("switchLabel")
    },

    // 调整关系选项菜单位置
    locateLinkMenu(event) {
      const _this = this
      let div = document.getElementById('linkMenu-' + _this.suffix)
      div.style.top = event.offsetY + "px"
      div.style.left = event.offsetX + "px"
    },
  },
}
</script>

<style scoped>
</style>
