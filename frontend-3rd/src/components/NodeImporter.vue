<template>
  <div>
    <br/>
    <h1>节点引入</h1>
    <el-input placeholder="请输入内容" v-model="input" @keyup.enter.native="handleEntitySearch">
      <el-button slot="append" icon="el-icon-search" @click="handleEntitySearch"></el-button>
    </el-input>
    <span style="color: navy">请搜索所需的节点</span>
    <br/>
    <!--    <el-button-group v-for="(item, index) of possibleNodes" :key="'possibleNode-'+index">-->
    <!--      <el-button>{{ item.name }}</el-button>-->
    <!--      <el-button icon="el-icon-plus" circle @click="cloneEntity(item)"></el-button>-->
    <!--    </el-button-group>-->
    <div style="height: 620px;overflow-y: auto">
      <el-card v-for="(item, index) of possibleNodes" :key="'pv-'+index" style="margin: 5px;position: relative;background: rgba(255,255,255,0.3);">
        <p align="left" style="font-size: 16px">{{ item.name }}</p>
        <el-button size="mini" style="position: absolute;right: 10px;bottom: 7px;background: #fb7299;color: white" circle
                   icon="el-icon-plus" @click="cloneEntity(item)">
        </el-button>
      </el-card>
    </div>
  </div>
</template>

<script>
import * as entityApi from "@/api/entityApi";

export default {
  name: "NodeImporter",
  data() {
    return {
      input: "",
      possibleNodes: [],
    }
  },
  methods: {
    async handleEntitySearch() {
      const _this = this
      _this.possibleNodes = await entityApi.searchEntityApi(_this.input)
    },

    async cloneEntity(entity) {
      const gid = this.$store.state.user.gid
      const data = JSON.parse(JSON.stringify(entity))
      data.gid = gid
      const res = await entityApi.addEntityApi(data)
      this.$emit('addNode')
    },
  },
}
</script>

<style scoped>
.el-button-group {
  margin: 5px;
}
</style>