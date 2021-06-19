<template>
  <div style="position: relative">
    <div v-show="!graphShow">
      <h1>语义搜索</h1>
      <el-input placeholder="请输入内容" v-model="searchContent" type="text" clearable @change="handleSearchAck">
        <el-button slot="append" icon="el-icon-search" @click="handleSearchAck"></el-button>
      </el-input>
      <!--      <div style="height: auto;overflow: auto;margin-top: 10px">-->
      <!--        <span class="selected-tag">已选择标签：</span>-->
      <!--        <span v-for="(item, index) in selectedKeys" :key="'select-'+index" class="selected-key">-->
      <!--        {{ item }}-->
      <!--        <a @click="removeKey">-->
      <!--          <i class="el-icon-close"></i>-->
      <!--        </a>-->
      <!--      </span>-->
      <!--      </div>-->
      <el-divider/>
      <div style="height: auto;overflow: auto">
        <el-button class="possible-key" v-for="(item, index) in possibleKeys" :key="'possible-'+index"
                   @click="selectKey">
          {{ item }}
        </el-button>
      </div>
      <div v-if="temp" style="height: auto;overflow: auto;position: relative">
        <h2>{{ tempGraph.name }}</h2>
        <span v-for="(item, index) in tempGraph.entities" :key="'entity-'+index" class="key-tempGraph">
        {{ item.name }}
      </span>
        <el-button style="position: absolute;top: 0px;right: 10px;background: #fb7299;color: white" size="mini"@click="switchToGraph">
          生成图谱
          <i class="el-icon-arrow-right el-icon--right"></i>
        </el-button>
      </div>
      <el-divider v-if="temp"/>
      <div style="height: auto;overflow: auto;position: relative">
        <el-button style="position: absolute;top: 10px;right: 5px;" @click="clearSearchHistory" size="mini"
                   type="danger">清空搜索记录
          <i class="el-icon-close"></i>
        </el-button>
        <span class="selected-tag">搜索历史：</span>
        <br/>
        <br/>
        <br/>
        <el-button-group v-for="(item,index) of searchHistory" :key="'history-'+index" style="float: left">
          <el-button class="possible-key" @click="quickSearch([item])">{{ item }}</el-button>
          <el-button icon="el-icon-close" circle @click="deleteSearchHistory(item)"></el-button>
        </el-button-group>
      </div>
      <el-divider/>
      <div style="height: auto;overflow: auto;position: relative">
        <span class="selected-tag">个性化推荐：</span>
        <el-button style="position: absolute;top: 10px;right: 5px" size="mini" @click="refreshRecommend" type="success">
          换一换
          <i class="el-icon-refresh-right"></i>
        </el-button>
        <br/>
        <br/>
        <br/>
        <el-button v-for="(item,index) of recommend" :key="'recommend-'+index" class="possible-key"
                   @click="quickSearch([item])">{{ item }}
        </el-button>
      </div>
      <el-divider/>
    </div>
    <kg-only v-if="graphShow" :graph="graphData" style="position: relative" ref="kgOnly" @cloneGraph="cloneGraph" @updateHistory="updateHistory"/>
    <el-button v-if="graphShow" icon="el-icon-arrow-left" style="position: absolute;left: 0px;top: -5px"
               @click="switchToSearch">返回
    </el-button>
    <el-button v-if="graphShow" style="position: absolute;right: 0px;top: -5px;background: #fb7299;color: white" @click="$refs.kgOnly.cloneGraphData()">
      获取该图
      <i class="el-icon-arrow-right el-icon--right"></i>
    </el-button>
  </div>
</template>

<script>
import * as userApi from '@/api/userApi';
import * as graphApi from '@/api/graphApi'
import KGOnly from '@/components/KGOnly'

export default {
  name: "KGSearch",
  components: {
    "kg-only": KGOnly,
  },
  data() {
    return {
      // 搜索
      searchContent: '薇尔莉特•伊芙加登',
      possibleKeys: [],
      selectedKeys: "",

      // 搜索结果
      tempGraph: null,
      temp: false,

      // 生成图谱
      graphShow: false,
      graphData: {},

      // 搜索历史
      searchHistory: [],
      // 个性化推荐
      recommend: [],
    }
  },
  async mounted() {
    const _this = this
    const uid = _this.$store.state.user.uid
    const res = await userApi.getSearchHistoryApi(uid)
    _this.searchHistory = res !== "No history!" ? res : [];
    _this.searchHistory = _this.searchHistory.filter(function (f) {
      return f !== "" && f !== '..';
    })
    _this.recommend = await userApi.getRecommendApi(uid)
    _this.recommend = _this.recommend.filter(function (f) {
      return f !== "" && f !== '..';
    })
  },
  methods: {
    // 确认搜索，结果为候选关键词
    async handleSearchAck() {
      const _this = this
      _this.temp = false
      _this.possibleKeys = await userApi.searchForKeyApi(_this.searchContent)
      // _this.possibleKeys = await _this.possibleKeys.filter(k => {
      //   return _this.selectedKeys.indexOf(k) === -1;
      // })
    },

    // 选择候选关键词
    selectKey(event) {
      let key = event.target.innerText;
      // this.selectedKeys = key;
      // this.possibleKeys = this.possibleKeys.filter(k => k !== key);
      this.possibleKeys = [];
      this.search([key])
    },

    // removeKey(event) {
    //   let key = event.path[2].innerText;
    //   key = key.substring(0, key.length - 1)
    //   this.selectedKeys = this.selectedKeys.filter(k => k !== key);
    //   this.temp = false
    // },

    // 关键词搜索
    async search(keys) {
      const _this = this
      const uid = _this.$store.state.user.uid;
      this.tempGraph = await userApi.searchAckApi({
        contents: keys,
        uid: uid,
      })
      this.searchHistory = await userApi.getSearchHistoryApi(uid)
      _this.searchHistory = _this.searchHistory.filter(function (f) {
        return f !== "" && f !== '..';
      })
      this.temp = true
    },

    // 生成图谱
    switchToGraph() {
      const _this = this
      _this.graphData.uid = _this.tempGraph.uid
      _this.graphData.gid = _this.tempGraph.gid
      _this.graphData.gname = _this.tempGraph.name
      _this.graphData.nodes = _this.tempGraph.entities.map(function (d) {
        return {
          id: d.eid,
          color: d.color,
          x: d.x,
          y: d.y,
          name: d.name,
          category: d.category,
        }
      })
      _this.graphData.links = _this.tempGraph.relations.map(function (d) {
        return {
          id: d.rid,
          source: d.theFirstEid,
          target: d.theSecondEid,
          name: d.name,
        }
      })
      _this.graphShow = true
    },

    // 注销图谱，返回搜索界面
    switchToSearch() {
      this.searchContent = ""
      this.possibleKeys = []
      this.selectedKeys = ""
      this.temp = false
      this.graphShow = false
    },

    // 当前用户获取当前临时生成图
    async cloneGraph(frontGraph) {
      let uid = this.$store.state.user.uid;
      const backendGraph = {}
      backendGraph.uid = uid
      backendGraph.gid = frontGraph.gid
      backendGraph.name = frontGraph.gname
      backendGraph.entities = frontGraph.nodes.map(function (d) {
        return {
          eid: d.id,
          gid: frontGraph.gid,
          name: d.name,
          category: d.category,
          x: d.x,
          y: d.y,
          color: d.color,
        }
      })
      backendGraph.relations = frontGraph.links.map(function (d) {
        return {
          rid: d.id,
          name: d.name,
          theFirstEid: d.source,
          theSecondEid: d.target,
          gid: frontGraph.gid,
        }
      })
      const res = await graphApi.generateGraphApi(backendGraph)
      this.$router.push({name: 'Graph', params: {gid: res}})
    },

    // 刷新推荐
    async refreshRecommend() {
      const _this = this
      const uid = _this.$store.state.user.uid
      _this.recommend = await userApi.getRecommendApi(uid)
      _this.recommend = _this.recommend.filter(function (f) {
        return f !== "";
      })
      this.$message({
        message: '推荐刷新成功！',
        type: 'success'
      });
    },

    // 根据历史记录和推荐快速搜索
    quickSearch(keys) {
      const _this = this
      // _this.selectedKeys = keys
      _this.searchContent = keys[0]
      _this.search(keys)
    },

    // 清除历史记录
    async clearSearchHistory() {
      const _this = this
      const uid = _this.$store.state.user.uid
      const res = await userApi.clearSearchHistoryApi(uid)
      _this.searchHistory = []
      this.$message({
        message: '历史记录删除成功！',
        type: 'success'
      });
    },

    // 删除某个历史记录
    async deleteSearchHistory(item) {
      const _this = this
      const uid = _this.$store.state.user.uid
      const res = await userApi.deleteSearchHistoryApi({
        uid: uid,
        key: item,
      })
      _this.searchHistory = await userApi.getSearchHistoryApi(uid)
      _this.searchHistory = _this.searchHistory.filter(function (f) {
        return f !== "" && f !== '..';
      })
    },

    // 刷新历史记录
    async updateHistory(){
      const _this = this
      const uid = _this.$store.state.user.uid;
      this.searchHistory = await userApi.getSearchHistoryApi(uid)
      _this.searchHistory = _this.searchHistory.filter(function (f) {
        return f !== "" && f !== '..';
      })
    },
  },
}
</script>

<style scoped>
.possible-key {
  float: left;
  margin-right: 10px;
  margin-left: 10px;
  margin-bottom: 10px;
}

span {
  font-size: larger;
  padding: 5px;
  margin: 5px;
}

.selected-key {
  float: left;
  background: white;
}

.selected-tag {
  float: left;
  margin-left: 0px;
  padding-left: 0px;
}

.key-tempGraph {
  float: left;
}

.el-divider {
  margin-top: 10px;
  margin-bottom: 10px;
}
</style>
