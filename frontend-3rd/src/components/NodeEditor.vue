<template>
  <div class="node-editor">
    <!--名称-->
    <div style="width: 5%;float:left;height: 80%;margin-right: 5px;margin-top: 0.5%;">
      <span>名称 : </span>
    </div>
    <!--输入框-->
    <div style="width: 12%;float:left;height: 80%;margin-right: 20px;margin-top: 0.2%;">
      <el-input size="mini" v-model="name"></el-input>
    </div>
    <!--名称-->
    <div style="width: 5%;float:left;height: 80%;margin-right: 5px;margin-top: 0.5%;">
      <span>修改为 : </span>
    </div>
    <!--输入框-->
    <div style="width: 12%;float:left;height: 80%;margin-right: 20px;margin-top: 0.2%;">
      <el-input placeholder="请输入内容" size="mini" v-model="input"></el-input>
    </div>
    <!--颜色-->
    <div style="width: 5%;float:left;height: 80%;margin-right: 0;margin-top: 0.5%">
      <span>颜色</span>
    </div>
    <!--颜色选择-->
    <div style="float:left;height: 80%;margin-right: 20px;margin-left: 0;">
      <el-color-picker size="medium" v-model="color"></el-color-picker>
    </div>
    <div style="width: 5%;float:right;height: 80%;margin-top: 0.2%;">
      <el-button icon="el-icon-check" size="mini" circle @click="handleConfirm"/>
    </div>
  </div>
</template>

<script>
export default {
  name: "NodeEditor",
  data() {
    return {
      input: ""
    }
  },
  props: {
  },
  computed: {
    name: {
      get() {
        if (this.$store.state.editor.nodeInfo === null) {
          return "";
        } else {
          return this.$store.state.editor.nodeInfo.d.name;
        }
      }
    },
    color: {
      get() {
        if (this.$store.state.editor.nodeInfo === null) {
          return null;
        } else {
          return this.$store.state.editor.nodeInfo.d.color;
        }
      },
      set(value) {
        // console.log(value)
        this.$store.commit("editor/setNodeInfoColor", value)
      }
    },
  },
  methods: {
    handleConfirm() {
      if (this.input !== "") {
        this.$store.commit('editor/setNodeInfoName', this.input)
        this.input = ""
      }
      this.$store.commit('editor/ackNodeEdit')
    }
  },
}
</script>

<style scoped>
.node-editor {
  height: 35px;
  margin-bottom: 10px;
}
</style>
