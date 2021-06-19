<template>
  <div class="kg-panel">
    <el-button circle size="mini" style="position: absolute;top: 6px;right: 6px;background: #fb7299"
               @click="handlePanelChange('default')"
               v-show="!show.default"/>
    <div v-show="show.default">
      <h1>图谱仪表盘</h1>
      <el-card shadow="hover" style="margin-bottom: 20px">
        <el-button type="text" style="padding: 5px" @click="handlePanelChange('statistics')"><h2 style="margin: 0px">
          图谱统计
          ></h2></el-button>
      </el-card>
      <el-card shadow="hover" style="margin-bottom: 20px">
        <el-button type="text" style="padding: 5px" @click="handlePanelChange('categoryFilter')"><h2
            style="margin: 0px">
          类型过滤
          ></h2></el-button>
      </el-card>
      <el-card shadow="hover" style="margin-bottom: 20px">
        <el-button type="text" style="padding: 5px" @click="handlePanelChange('linkCreator')"><h2 style="margin: 0px">
          新建关系
          ></h2></el-button>
      </el-card>
      <el-card shadow="hover" style="margin-bottom: 20px">
        <el-button type="text" style="padding: 5px" @click="handlePanelChange('nodeImporter')"><h2 style="margin: 0px">
          引入节点 ></h2></el-button>
      </el-card>
    </div>
    <link-creator ref="linkCreator" v-show="show.linkCreator"></link-creator>
    <statistics ref="statistics" v-show="show.statistics"></statistics>
    <category-filter ref="categoryFilter" v-show="show.categoryFilter" @filter="handleFilter"></category-filter>
    <node-importer ref="nodeImporter" v-show="show.nodeImporter" @addNode="handleAddNode"></node-importer>
  </div>
</template>

<script>
import LinkCreator from "@/components/LinkCreator";
import Statistics from "@/components/Statistics";
import CategoryFilter from "@/components/CategoryFilter";
import NodeImporter from "@/components/NodeImporter";

export default {
  name: "KGPanel",
  components: {
    "link-creator": LinkCreator,
    "statistics": Statistics,
    "category-filter": CategoryFilter,
    "node-importer": NodeImporter,
  },
  data() {
    return {
      // 仪表盘显示内容
      show: {
        default: true,
        linkCreator: false,
        statistics: false,
        categoryFilter: false,
        nodeImporter: false,
      },
    }
  },
  computed: {
    panelCurrent() {
      return this.$store.state.panelCurrent;
    },
  },
  methods: {
    // 切换仪表盘显示
    handlePanelChange(current) {
      this.$store.commit("setPanel", {current: current})
    },

    // 类别筛选触发
    handleFilter() {
      this.$emit('refresh')
    },

    // 添加节点
    handleAddNode() {
      this.$emit('reload')
    },
  },
  watch: {
    panelCurrent() {
      const _this = this
      for (let showKey in _this.show) {
        _this.show[showKey] = showKey === _this.panelCurrent
      }
    },
  },
}
</script>

<style scoped>
.kg-panel {
  width: 290px;
  height: 800px;
  position: relative;
  border-radius: 7px;
  box-shadow: 0px 0px 5px darkgrey;
  background-color:rgba(240,248,255, 0.8);
  padding: 20px;
}

.el-card {
  background-color:rgba(240,248,255, 0);
  border: 1px solid darkgray;
}
</style>
