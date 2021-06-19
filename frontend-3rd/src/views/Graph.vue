<template>
  <div @click="handleGraphClick">
    <bg/>
    <kg-header/>
    <div class="kg-wrapper">
      <div class="kg-div">
        <el-button style="position: absolute;top:26px;left:-40px;background: #fb7299;color: white;border: #fb7299" icon="el-icon-back"
          @click="$router.go(-1)" circle size="mini">
        </el-button>
        <kg-d3 ref="kgD3" :suffix="gid.toString()" :src="kgData" v-if="kgData.ready"></kg-d3>
      </div>
      <div class="panel-wrapper">
        <kg-panel ref="kgPanel" @refresh="handleRefreshKG" @reload="handleReloadKG"></kg-panel>
      </div>
    </div>
  </div>
</template>

<script>
import Background from "@/components/Background";
import Header from "@/components/Header";
import KGd3 from "@/components/KGd3";
import KGPanel from "@/components/KGPanel";
import * as graphApi from '@/api/graphApi'

export default {
  name: "Graph",
  components: {
    "bg": Background,
    "kg-header": Header,
    "kg-d3": KGd3,
    "kg-panel": KGPanel,
  },
  data() {
    return {
      gid: null,
      kgData: {
        ready: false,
      },
    }
  },
  async created() {
    const _this = this;
    _this.gid = Number(_this.$route.params.gid);
    _this.$store.commit('user/setGid', _this.gid)
    const res = await graphApi.getGraphById(_this.gid)
    _this.kgData.uid = res.uid
    _this.kgData.gid = res.gid
    _this.kgData.gname = res.name
    _this.kgData.nodes = res.entities.map(function (d) {
      return {
        id: d.eid,
        color: d.color,
        x: d.x,
        y: d.y,
        name: d.name,
        category: d.category,
      }
    })
    _this.kgData.links = res.relations.map(function (d) {
      return {
        id: d.rid,
        source: d.theFirstEid,
        target: d.theSecondEid,
        name: d.name,
      }
    })
    _this.kgData.ready = true
  },
  mounted() {
  },
  methods: {
    // 外部刷新图谱
    handleRefreshKG() {
      this.$refs.kgD3.resetSvg();
      this.$refs.kgD3.initKG();
    },

    // 点击当前web页面
    handleGraphClick() {
      this.$refs.kgD3.clickGraphWeb();
    },

    // 重新加载图谱
    async handleReloadKG() {
      const _this = this
      const res = await graphApi.getGraphById(_this.gid)
      _this.kgData.uid = res.uid
      _this.kgData.gid = res.gid
      _this.kgData.gname = res.name
      _this.kgData.nodes = res.entities.map(function (d) {
        return {
          id: d.eid,
          color: d.color,
          x: d.x,
          y: d.y,
          name: d.name,
          category: d.category,
        }
      })
      _this.kgData.links = res.relations.map(function (d) {
        return {
          id: d.rid,
          source: d.theFirstEid,
          target: d.theSecondEid,
          name: d.name,
        }
      })
      _this.$refs.kgD3.resetData();
      _this.handleRefreshKG();
      this.$message({
        message: '导入节点成功！',
        type: 'success'
      });
    },
  },
}
</script>

<style scoped>
.kg-wrapper {
  padding: 5px 20px;
  width: 1550px;
  height: 800px;
  margin: auto;
}

@media screen and (max-width: 1600px) {
  .kg-wrapper {
    margin: 0px 25px;
  }
}

.kg-div {
  float: left;
  width: 1200px;
  height: 800px;
  position: relative;
  border-radius: 7px;
  box-shadow: 0px 0px 5px darkgrey;
  background-color:rgba(240,248,255, 0.8);
  padding: 20px;
  margin: auto;
}

.panel-wrapper {
  float: left;
  margin-left: 20px;
}
</style>
