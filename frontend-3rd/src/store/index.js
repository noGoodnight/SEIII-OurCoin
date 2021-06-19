import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

import editor from "@/store/editor";
import user from "@/store/user";

export default new Vuex.Store({
  state: {
    // 当前图谱仪表盘
    panelCurrent: "default",
    // 新建关系所需数据
    linkCreatorSource: null,
    linkCreatorTarget: null,
    linkCreatorAck: false,
    // 当前图谱节点和关系数量
    nodeNum: 0,
    linkNum: 0,
    // 当前图谱节点类别及是否显示
    category: {
      animation: true,
      person: true,
      actor: true,
      company: true,
      director: true,
    },
    // 编辑节点所需数据
    // nodeInfo: null,
    // nodeEditAck: false,
    // 搜索所需
    searchContent: null,
    // 编辑关系所需数据
    // linkInfo: null,
    // linkEditAck: false,

  },
  mutations: {
    // 设置图谱节点和关系数量
    setStatistics(state, payload) {
      state.nodeNum = payload.nodeNum
      state.linkNum = payload.linkNum
    },

    // 设置图谱类别过滤
    setCategory(state, payload) {
      for (let key in state.category) {
        state.category[key] = (payload.category.indexOf(key) !== -1);
      }
    },

    // 设置图谱仪表盘
    setPanel(state, payload) {
      state.panelCurrent = payload.current
    },

    // 设置新建关系的起点
    setLinkCreatorSource(state, payload) {
      state.linkCreatorSource = payload.d
    },

    // 设置新建关系的终点
    setLinkCreatorTarget(state, payload) {
      state.linkCreatorTarget = payload.d
    },

    // 确认新建关系
    ackLinkCreator(state) {
      state.linkCreatorAck = true
    },

    // 编辑搜索内容
    setSearchContent(state, payload) {
      state.searchContent = payload

    },

  },
  actions: {},
  modules: {
    editor: editor,
    user: user,
  },
})
