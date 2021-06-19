const editor = {
  namespaced: true,
  state: {
    test: 'our-coin',
    // 编辑节点所需数据
    nodeInfo: null,
    nodeEditAck: false,
    // 编辑关系所需数据
    linkInfo: null,
    linkEditAck: false,
  },
  mutations: {
    changeTest(state, s){
      state.test = s
    },

    setNodeInfo(state, payload) {
      state.nodeInfo = payload
    },

    setNodeInfoColor(state, payload) {
      // console.log(payload)
      state.nodeInfo.d.color = payload
    },

    setNodeInfoName(state, name){
      // console.log(state.nodeInfo.d.name)
      state.nodeInfo.d.name = name
    },

    ackNodeEdit(state) {
      state.nodeEditAck = true
    },

    setLinkInfo(state, payload) {
      state.linkInfo = payload
    },

    setLinkInfoName(state, name){
      state.linkInfo.d.name = name;
    },

    ackLinkEdit(state){
      state.linkEditAck = true
    },

  },
  actions: {},
}

export default editor