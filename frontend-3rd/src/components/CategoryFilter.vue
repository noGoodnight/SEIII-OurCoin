<template>
  <div>
    <br/>
    <h1>类型过滤</h1>
    <el-checkbox-group v-model="checkedCategory" @change="handleFilterChange" style="margin: 0px 30px">
      <el-checkbox v-for="city in category" :label="city" :key="city" style="float: left;">{{ city }}
      </el-checkbox>
    </el-checkbox-group>
  </div>
</template>

<script>
export default {
  name: "CategoryFilter",
  data() {
    return {
      category: ['animation', 'person', 'actor', 'company', 'director'],
      checkedCategory: ['animation', 'person', 'actor', 'company', 'director'],
    }
  },
  methods: {
    // 图谱的类别过滤发生改变
    handleFilterChange() {
      const _this = this
      _this.$store.commit('setCategory', {category: _this.checkedCategory})
      _this.$emit('filter')
    },
  },
}
</script>

<style scoped>

</style>