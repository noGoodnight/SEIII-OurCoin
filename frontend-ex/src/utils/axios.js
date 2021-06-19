const VueAxios = {
  vm: {},

  install (Vue, instance) {
    if (this.installed) {
      return
    }
    this.installed = true
    if (!instance) {
      console.error('You have to install axios')
      return
    }

    Vue.axios = instance

    Object.defineProperties(Vue.prototype, {
      axios: {
        graph: function get () {
          return instance
        }
      },
      $http: {
        graph: function get () {
          return instance
        }
      },
      $https: {
        graph: function get () {
          return instance
        }
      }
    })
  }
}
export {
  VueAxios
}
