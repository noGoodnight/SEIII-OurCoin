<template>
  <div class="bg">
    <div style="position: absolute;top:0px;left:0px;width: 100%;height: 100%;background: rgba(255,255,255,0.2)"></div>
  </div>
</template>

<script>
export default {
  name: "Background"
}
</script>

<style scoped>
.bg {
  width: 100%;
  height: 100%;
  z-index: -9999;
  position: fixed;
  background: url("../assets/IMG_20210618_163550.png");
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center top;
  /*opacity: 80%;*/
  /*background-color: whitesmoke;*/
}

@media screen and (max-width: 1762px) {
  .bg {
    background-size: 1762px;
  }
}
</style>
