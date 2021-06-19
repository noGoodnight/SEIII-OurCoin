<template>
  <div>
    <!--    <div style="height: 42px;padding-top:15px">-->
    <!--      <el-button type="text" @click="handleOptionChange('tree')">文件列表</el-button>-->
    <!--      <el-divider direction="vertical"/>-->
    <!--      <el-button type="text" @click="handleOptionChange('upload')">文件上传</el-button>-->
    <!--    </div>-->
    <!--    <el-divider/>-->
    <!--    <div>-->
    <!--      <kg-tree v-show="option.tree"></kg-tree>-->
    <!--      <kg-upload v-show="option.upload"></kg-upload>-->
    <!--    </div>-->
    <br/>
    <h1 style="font-size: 30px">图谱选项</h1>
    <el-card shadow="hover" style="margin-bottom: 20px">
      <el-button type="text" style="padding: 5px" @click="handleOptionChange('search')"><h2 style="margin: 0px">语义搜索
        ></h2></el-button>
    </el-card>
    <el-card shadow="hover" style="margin-bottom: 20px">
      <el-button type="text" style="padding: 5px" @click="handleOptionChange('answer')"><h2 style="margin: 0px">智能问答
        ></h2></el-button>
    </el-card>
    <el-card shadow="hover" style="margin-bottom: 20px">
      <el-button type="text" style="padding: 5px" @click="handleOptionChange('personal')"><h2 style="margin: 0px">我的图谱
        ></h2></el-button>
    </el-card>
  </div>
</template>

<script>
import KGTree from "@/components/KGTree";
import KGUpload from "@/components/KGUpload";

export default {
  name: "KGOption",
  components: {
    "kg-tree": KGTree,
    "kg-upload": KGUpload,
  },
  data() {
    return {
      option: {
        tree: true,
        upload: false,
      },
    }
  },
  methods: {
    handleOptionChange(option) {
      this.$emit('changeOption', option)
    }
  },
}
</script>

<style scoped>
.el-card {
  background-color:rgba(240,248,255, 0);
  margin-left: 10px;
  margin-right: 10px;
  border: 1px solid darkgray;
}
</style>
