<template>
  <div class="div-header">
    <el-header height="100">
      <h1 style="padding-top: 25px;opacity: 100%">OurCoin-KnowledgeGraph</h1>
    </el-header>
    <el-divider/>
  </div>
</template>

<script>
export default {
  name: "Header"
}
</script>

<style scoped>
.el-header {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  height: 100px;
  margin: 0px;
  padding: 0px;
}

.el-divider {
  margin: 0px;
  margin-bottom: 10px;
}

.div-header {
  width: 1600px;
  margin: 0px;
  color: #f0f8ff;
  background-color:rgba(240,248,255, 0.8);
}

@media screen and (min-width: 1600px) {
  .div-header {
    width: 100%;
  }
}
</style>
