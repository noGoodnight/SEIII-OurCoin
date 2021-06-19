<!--图谱操作工具栏组件-->

<template>
  <div>
    <el-menu :collapse="true">
      <!--      <el-menu-item index="4">-->
      <!--        <i class="el-icon-s-operation"></i>-->
      <!--        <span slot="title">排版模式</span>-->
      <!--      </el-menu-item>-->
      <el-menu-item index="6" @click="handleKGLabelSwitch">
        <img :src="require('../assets/View.png')" style="height: 20px;width: 20px"/>
        <span slot="title">切换标签显示</span>
      </el-menu-item>
      <el-menu-item index="5" @click="handleKGSearch">
        <i class="el-icon-search"></i>
        <span slot="title">搜索</span>
      </el-menu-item>
      <el-menu-item index="2" @click="handleKGReduce">
        <i class="el-icon-refresh"></i>
        <span slot="title">还原</span>
      </el-menu-item>
      <el-menu-item index="7" @click="handleKGRender">
        <i class="el-icon-video-play"></i>
        <span slot="title">重新绘制图谱</span>
      </el-menu-item>
      <el-menu-item index="3" @click="handleKGSave">
        <i class="el-icon-finished"></i>
        <span slot="title">保存图谱</span>
      </el-menu-item>
      <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-download"></i>
        </template>
        <el-menu-item-group>
          <template slot="title">文件</template>
          <el-menu-item index="1-1" @click="handleKGDownload('json')">JSON</el-menu-item>
          <el-menu-item index="1-2" @click="handleKGDownload('xml')">XML</el-menu-item>
        </el-menu-item-group>
        <el-divider style="margin-top: 0;"/>
        <el-menu-item-group>
          <template slot="title">图片</template>
          <el-menu-item index="1-3" @click="handleKGDownload('png')">PNG</el-menu-item>
          <el-menu-item index="1-4" @click="handleKGDownload('jpg')">JPG</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: "KGTool",
  methods: {
    // 处理图谱还原
    handleKGReduce() {
      this.$emit('reduceKG')
    },

    // 处理图谱下载
    handleKGDownload(type) {
      this.$emit('downloadKG', type)
    },

    // 处理图谱重新绘制
    handleKGRender() {
      this.$emit('renderKG')
    },

    // 处理图谱内容搜索
    handleKGSearch() {
      this.$emit('searchKG')
    },

    // 处理图谱保存
    handleKGSave() {
      this.$emit('saveKG');
    },

    // 处理图谱的关系标签显示切换
    handleKGLabelSwitch() {
      this.$emit('switchLabel');
    }
  }
}
</script>

<style scoped>
.el-menu--collapse {
  position: absolute;
  right: -63px;
  /*background: whitesmoke;*/
  background-color: rgba(245,245,245,0.75);
  height: 715px;
  box-shadow: 0px 0px 2px darkgrey inset;
}
</style>
