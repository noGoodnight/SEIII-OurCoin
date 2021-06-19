<template>
  <div class="div-edit">
    <!--名称-->
    <div style="width: 5%;float:left;height: 80%;margin-right: 5px;margin-top: 0.5%;">
      <span>名称 : </span>
    </div>
    <!--输入框-->
    <div style="width: 8%;float:left;height: 80%;margin-right: 20px;margin-top: 0.2%;">
      <el-input v-model="name" size="mini"></el-input>
    </div>
    <!--名称-->
    <div style="width: 5%;float:left;height: 80%;margin-right: 5px;margin-top: 0.5%;">
      <span>修改为 : </span>
    </div>
    <!--输入框-->
    <div style="width: 8%;float:left;height: 80%;margin-right: 20px;margin-top: 0.2%;">
      <el-input placeholder="请输入内容" v-model="input" size="mini"></el-input>
    </div>
    <!--起点-->
    <div style="width: 5%;float:left;height: 80%;margin-right: 5px;margin-top: 0.5%;">
      <span>起点 : </span>
    </div>
    <!--输入框-->
    <div style="width: 8%;float:left;height: 80%;margin-right: 20px;margin-top: 0.2%;">
      <el-input v-model="sourceName" size="mini"></el-input>
    </div>
    <!--终点-->
    <div style="width: 5%;float:left;height: 80%;margin-right: 5px;margin-top: 0.5%;">
      <span>终点 : </span>
    </div>
    <!--输入框-->
    <div style="width: 8%;float:left;height: 80%;margin-right: 20px;margin-top: 0.2%;">
      <el-input v-model="targetName" size="mini"></el-input>
    </div>
    <div style="width: 5%;float:right;height: 80%;margin-top: 0.2%;">
      <el-button icon="el-icon-check" size="mini" circle @click="handleConfirm"/>
    </div>
  </div>
</template>

<script>
export default {
  name: "LinkEditor",
  data() {
    return {
      input: "",
    }
  },
  props: {},
  computed: {
    // color: {
    //   get() {
    //     if (this.$store.state.editor.linkInfo === null) {
    //       return null;
    //     } else {
    //       return this.$store.state.editor.linkInfo.color;
    //     }
    //   },
    // set(value) {
    //   // console.log(value)
    //   this.$store.commit("editor/setLinkInfoColor", value)
    // }
    // },
    name: {
      get() {
        if (this.$store.state.editor.linkInfo === null) {
          return null;
        } else {
          return this.$store.state.editor.linkInfo.d.name;
        }
      }
    },
    sourceName: {
      get() {
        if (this.$store.state.editor.linkInfo === null) {
          return null;
        } else {
          return this.$store.state.editor.linkInfo.d.source.name;
        }
      },
      // set(value) {
      //   // console.log(value)
      //   this.$store.commit("setLinkInfoSource", value)
      // }
    },
    targetName: {
      get() {
        if (this.$store.state.editor.linkInfo === null) {
          return null;
        } else {
          return this.$store.state.editor.linkInfo.d.target.name;
        }
      },
      // set(value) {
      //   // console.log(value)
      //   this.$store.commit("editor/setLinkInfoTarget", value)
      // }
    },
  },
  methods: {
    handleConfirm() {
      if (this.input !== "") {
        this.$store.commit('editor/setLinkInfoName', this.input)
        this.input = ""
      }
      this.$store.commit('editor/ackLinkEdit')
    }
  },
}
</script>

<style scoped>
.div-edit {
  height: 35px;
  margin-bottom: 10px;
}
</style>
