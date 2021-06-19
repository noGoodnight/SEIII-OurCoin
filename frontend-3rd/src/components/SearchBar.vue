<template>
  <div class="node-editor">
    <div style="width: 60%;float:left;height: 60%">
      <el-autocomplete
          class="inline-input"
          v-model="searchContent"
          :fetch-suggestions="searchHistorySuggest"
          placeholder="请输入内容"
          :clearable=true
          :debounce=0
          prefix-icon="el-icon-search"
          @select="handleSearchEnter"
          @keyup.enter.native="handleSearchEnter"
          style="width: 100%; float:left"
      ></el-autocomplete>
    </div>
    <span style="float: left; margin-top: 10px;margin-left: 20px;color: navy">搜索结果将使用</span>
    <span style="float: left; margin-top: 10px;color: #fb7299">红色</span>
    <span style="float: left; margin-top: 10px;color: navy">标注</span>
    <div style="width: 5%;float:right;height: 80%;margin-top: 0.2%;">
      <el-button icon="el-icon-close" size="mini" circle @click="handleSearchClose"/>
    </div>
  </div>
</template>

<script>
export default {
  name: "SearchBar",
  data() {
    return {
      searchContent: "",
      searchHistory: [],
    }
  },
  methods: {
    // 清除搜索
    handleSearchClose() {
      this.searchContent = ""
      this.$store.commit("setSearchContent", "")
      this.$emit("closeSearch")
    },

    // 搜索历史纪录
    searchHistorySuggest(queryString, cb) {
      const _this = this;
      let result = [];
      for (let i = 0; i < _this.searchHistory.length; i++) {
        if (_this.searchHistory[i]["value"].indexOf(queryString) === 0) {
          result.push({"value": _this.searchHistory[i]["value"], "key": ""})
        }
      }
      cb(result)
    },

    // 回车触发搜索
    handleSearchEnter() {
      const _this = this
      _this.setSearchHistory(_this.searchContent)
      if (_this.searchContent === "") {
        this.$message.error({
          title: '错误',
          message: '请输入',
        });
        return
      }
      this.$store.commit("setSearchContent", _this.searchContent)
    },

    setSearchHistory(value) {
      const _this = this;
      let isExist = false
      for (let i = 0; i < _this.searchHistory.length; i++) {
        if (_this.searchHistory[i]["value"] === value) {
          isExist = true
        }
      }
      if (!isExist) {
        _this.searchHistory.push({"value": value, "key": ""})
      }
    },

  },
}
</script>

<style scoped>
.node-editor {
  height: 35px;
  margin-bottom: 10px;
}
</style>
