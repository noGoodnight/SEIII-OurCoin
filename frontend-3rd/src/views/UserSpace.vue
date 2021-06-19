<template>
  <div>
    <bg/>
    <kg-header/>
    <div class="user-main">
      <el-container>
        <el-aside class="main-block">
          <kg-option @changeOption="changeOption"></kg-option>
        </el-aside>
        <el-divider direction="vertical"></el-divider>
        <el-main class="main-block">
          <kg-search v-show="show.search">
          </kg-search>
          <kg-answer v-show="show.answer">
          </kg-answer>
          <div v-show="show.personal">
            <h2>图谱列表</h2>
            <span style="color: navy;">点击即可前往相应图谱页面，点击右侧按钮删除</span>
            <br/>
            <el-button-group v-for="(item,index) of graphList" :key="'graph-'+index" style="float:left;margin:10px;">
              <el-button style="background-color: #fb7299;color: white" @click="$router.push({name: 'Graph', params: {gid: item.toString()}})">{{ "Graph-" + item }}</el-button>
              <el-button style="color: #fb7299" circle icon="el-icon-close" @click="deleteGraph(item)"></el-button>
            </el-button-group>
          </div>
        </el-main>
      </el-container>
    </div>
  </div>
</template>

<script>
import Background from "@/components/Background";
import Header from "@/components/Header";
import KGOption from "@/components/KGOption";
import KGSearch from "@/components/KGSearch";
import KGAnswer from "@/components/KGAnswer";
import * as userApi from "@/api/userApi";
import * as graphApi from "@/api/graphApi";
import Graph from "@/views/Graph";

export default {
  name: "UserSpace",
  components: {
    Graph,
    bg: Background,
    "kg-header": Header,
    "kg-option": KGOption,
    "kg-search": KGSearch,
    "kg-answer": KGAnswer,
  },
  data() {
    return {
      uid: null,

      // user界面显示
      show: {
        search: true,
        answer: false,
        personal: false
      },

      // 该用户名下的图列表
      graphList: [],
    }
  },
  async created() {
    const _this = this;
    _this.uid = Number(_this.$route.params.uid);
    _this.$store.commit("user/setUid", _this.uid)
    const res = await graphApi.getGraphsByUidApi(_this.uid)
    _this.graphList = res.map(function (g) {
      return g.gid
    })
  },
  mounted() {
  },
  methods: {
    async changeOption(option) {
      const _this = this
      for (let showKey in _this.show) {
        _this.show[showKey] = showKey === option
      }
      if (this.show.personal) {
        const res = await graphApi.getGraphsByUidApi(_this.uid)
        _this.graphList = res.map(function (g) {
          return g.gid
        })
        _this.graphList = _this.graphList.filter(gid => gid !== 1)
      }
    },

    // 删除图谱
    async deleteGraph(gid){
      const res = graphApi.deleteGraphByGidApi(gid)
      this.graphList = this.graphList.filter(g => g !== gid)
      this.$message({
        message: '图谱删除成功！',
        type: 'success'
      });
    }
  },
}
</script>

<style scoped>
.el-divider--vertical {
  display: inline-block;
  width: 1px;
  height: 800px;
  margin: 0 8px;
  vertical-align: middle;
  position: relative;
}

.user-main {
  padding: 5px 20px;
  width: 1550px;
  margin: auto;
}

@media screen and (max-width: 1600px) {
  .user-main {
    margin: 0px 25px;
  }
}

.main-block {
  position: relative;
  height: 800px;
  border-radius: 7px;
  box-shadow: 0px 0px 5px darkgrey;
  background-color:rgba(240,248,255, 0.8);
}

.el-main {
  padding: 40px;
}
</style>
