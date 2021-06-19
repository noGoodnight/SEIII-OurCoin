<template>
  <div style="position: relative">
    <div class="home"/>
    <div style="position: absolute;left: 285px;top:180px">
      <el-image :src="require('../assets/OurCoin.png' )"></el-image>
    </div>
    <div id="des">
      <p align="left" style="font-size: 20px;font-family: '微软雅黑', serif;text-indent: 2em">
        简介：这里是OurCoin小组的知识图谱网站，知识图谱主题为Bilibili网站的番剧内容，其中包括作品，角色，声优，监督和公司五类节点以及将这些节点连接起来的各式关系。
      </p>
      <p align="left" style="font-size: 20px;font-family: '微软雅黑', serif;text-indent: 2em">
        OurCoin知识图谱网站提供<span style="color: #fb7299">语义搜索</span>和<span style="color: #fb7299">智能问答</span>功能；同时，OurCoin网站为用户提供了一个属于自己的知识图谱库，用户可以任意编辑自己的图谱。
      </p>
      <p align="left" style="font-size: 20px;font-family: '微软雅黑', serif;text-indent: 2em">
        朋友，如果你对动漫感兴趣，那快来探索吧！OurCoin欢迎你！
      </p>
      <br/>
      <el-button id="button" icon="el-icon-right" circle size="medium"
                 style="width: 60px;height: 60px;background: #fb7299;color: white;border: #fb7299;font-size: 28px;"
                 @click="$router.push({name: 'User', params: {uid: '1'}})"
      ></el-button>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'Home',
  components: {
    HelloWorld
  },
  created() {
    // this.$router.push({name: 'User', params: {uid: '1'}})
  },
}
</script>

<style scoped>
.home {
  width: 100%;
  height: 100%;
  position: fixed;
  background: url("../assets/IMG_20210618_194651.jpg");
  background-repeat: no-repeat;
  background-size: cover;
  background-position: 0px 0px;
  opacity: 80%;
}

#des {
  position: absolute;
  width: 600px;
  height: 500px;
  top: 350px;
  left: 191px;
  border-radius: 15px;
  box-shadow: 0px 0px 6px 0 rgba(0, 0, 0, 0.3);
  background: rgba(255, 255, 255, 0.5);
  z-index: 1;
  padding: 35px;
}

#des:hover {
  box-shadow: 0px 0px 25px 0 rgba(255, 255, 255, 0.3);
}

#button{
  box-shadow: 0px 0px 6px 0 rgba(0, 0, 0, 0.3);
}

#button:hover {
  box-shadow: 0px 0px 30px 0 rgba(255, 255, 255, 0.3);
}
</style>
