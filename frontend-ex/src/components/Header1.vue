<template>
<div class="div-header">
  <el-header height=80>
    <h1 style="padding-top: 15px">OurCoin-KnowledgeGraph</h1>
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
  height: 80px;
  margin: 0;
  padding: 0;
}

.el-divider{
  margin: 0;
  margin-bottom: 10px;
}

.div-header{
  width: 1400px;
  margin: 0;
  background-color: aliceblue;
}

@media screen and (min-width: 1400px){
  .div-header{
    width: 100%;
  }
}
</style>
