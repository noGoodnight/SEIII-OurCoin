const user = {
  namespaced: true,
  state: {
    uid: 1,
    gid: 0,
  },
  mutations: {
    setUid(state, uid){
      state.uid = uid
    },

    setGid(state, gid){
      state.gid = gid
    }
  },
  actions: {},
}

export default user