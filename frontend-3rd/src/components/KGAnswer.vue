<template>
  <div style="position: relative">
    <el-dropdown style="position: absolute;top: -3px;right: 213px">
      <el-button>
        推荐的问题类型<i class="el-icon-arrow-down el-icon--right"></i>
      </el-button>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item>作品中参演的角色</el-dropdown-item>
        <el-dropdown-item>作品中配音的声优</el-dropdown-item>
        <el-dropdown-item>角色的声优</el-dropdown-item>
        <el-dropdown-item>角色参演的作品</el-dropdown-item>
        <el-dropdown-item>声优配音的作品</el-dropdown-item>
        <el-dropdown-item>监督导演的作品</el-dropdown-item>
        <el-dropdown-item>声优配音的角色</el-dropdown-item>
        <el-dropdown-item>两个声优的合作作品</el-dropdown-item>
        <el-dropdown-item>声优在作品中配音的角色</el-dropdown-item>
        <el-dropdown-item>作品的监督</el-dropdown-item>
        <el-dropdown-item>作品的制作公司</el-dropdown-item>
        <el-dropdown-item>制作公司制作的作品</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
    <h1>智能问答</h1>
    <div class="div-chat">
      <div class="chat-view">
        <div class="chat-div">
          <div style="float: right;margin-left: 15px">
            <el-avatar id="userNode" :size="46" icon="el-icon-user"></el-avatar>
          </div>
          <div class="chat-question">
            <p align="left" class="chat-p">你好！我想问【丰崎爱生】在【轻音少女 第一季】中的角色？</p>
          </div>
          <div style="clear: both"></div>
        </div>
        <div class="chat-div">
          <div style="float: left;margin-right: 15px">
            <el-avatar id="sysNode" :size="46" icon="el-icon-user-solid"></el-avatar>
          </div>
          <div class="chat-answer">
            <p align="left" class="chat-p">你好！我是OurCoin。"丰崎爱生"在《轻音少女 第一季》中的角色有：平泽唯。</p>
          </div>
          <div style="clear: both"></div>
        </div>
      </div>
      <div class="chat-input">
        <el-input type="textarea" :rows="8" placeholder="请输入内容" v-model="questionContent" resize="none"
                  ref="input" @keydown.enter.native="handleQuestionAck">
        </el-input>
        <el-button style="position: absolute;right: 10px;bottom: 10px;background: #fb7299;color: white"
                   @click="handleQuestionAck">确认
        </el-button>
        <el-button style="position: absolute;right: 90px;bottom: 10px;" @click="toStandardSearch" type="primary">检索标准词
        </el-button>
        <span style="position:absolute;bottom: 25px;left: 10px;color: navy;">
          请准确输入并用<span style="color: #F56C6C">【】</span>装饰标准词；
        </span>
        <span style="position:absolute;bottom: 5px;left: 10px;color: navy;">
          如若不确定输入是否标准，可以在以【为前缀下按<span style="color: #F56C6C">Alt/Ctrl+Enter</span>或点击案件在右侧搜索标准词.
        </span>
      </div>
    </div>
    <div class="div-list">
      <!--      <h2 style="margin-top: 5px">标准词搜索</h2>-->
      <el-input
          style="margin-bottom: 10px"
          placeholder="标准词搜索"
          @change="handleStandardSearch"
          clearable
          v-model="input">
        <i slot="prefix" class="el-input__icon el-icon-search"></i>
      </el-input>
      <div
          style="width: 100%;height: 625px;box-shadow: 0px 0px 4px darkgrey inset;background: rgba(255,255,255,0.3); overflow-y: auto">
        <el-card v-for="(item, index) of possibleValue" :key="'pv-'+index"
                 style="margin: 5px;position: relative;background-color: rgba(255,255,255,0.3);">
          <p align="left" style="font-size: 16px">{{ item }}</p>
          <br/>
          <el-button size="mini" style="position: absolute;right: 5px;bottom: 5px;background: #fb7299;color: white"
                     @click="handleCopyValue(item)">复制
          </el-button>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script>
import * as userApi from "@/api/userApi";
import * as entityApi from "@/api/entityApi";

export default {
  name: "KGAnswer",
  data() {
    return {
      questionContent: "",
      dict: null,
      input: "",
      possibleValue: [],
    }
  },
  async mounted() {
    this.dict = await entityApi.getDictApi();
  },
  methods: {
    // 确认输入的问题
    async handleQuestionAck(e) {
      const _this = this

      if (e.ctrlKey || e.altKey) {
        _this.toStandardSearch()
        return
      }

      if (_this.questionContent !== "") {
        const chatView = document.getElementsByClassName('chat-view')[0]

        let questionDiv = document.createElement('div')
        questionDiv.className = 'chat-div'
        questionDiv.style.float = 'top'
        questionDiv.style.width = 'auto'
        questionDiv.style.height = 'auto'

        let userDiv1 = document.createElement('div')
        userDiv1.style.marginLeft = "15px"
        userDiv1.style.float = "right"

        let userNode = document.getElementById('userNode').cloneNode(true)

        let userDiv2 = document.createElement('div')
        userDiv2.className = 'chat-question'
        userDiv2.style.float = "right"
        userDiv2.style.backgroundColor = "#fb7299"
        userDiv2.style.color = "white"
        userDiv2.style.borderRadius = "10px"
        userDiv2.style.width = "auto"
        userDiv2.style.maxWidth = "550px"
        userDiv2.style.padding = "10px"
        userDiv2.style.height = "auto"
        userDiv2.style.marginBottom = "20px"

        let userP = document.createElement('p')
        userP.className = 'chat-p'
        userP.innerText = _this.questionContent
        userP.style.fontFamily = "font-family: \"微软雅黑\", serif"
        userP.style.fontSize = "18px"
        userP.style.margin = "0px"
        userP.style.padding = "0px 10px"
        userP.align = "left"
        userP.style.wordBreak = "break-word"

        let userDiv3 = document.createElement('div')
        userDiv3.style.clear = "both"

        userDiv1.appendChild(userNode)
        userDiv2.appendChild(userP)
        questionDiv.appendChild(userDiv1)
        questionDiv.appendChild(userDiv2)
        questionDiv.appendChild(userDiv3)
        chatView.appendChild(questionDiv)
        chatView.scrollTop = chatView.scrollHeight

        let content = _this.questionContent
        _this.questionContent = ""
        let res = ""
        try {
          res = await userApi.questionAck(content)
        }catch (e) {
          res = "问题错误！"
        }finally {
          _this.questionContent = ""
        }

        let answerDiv = document.createElement('div')
        answerDiv.className = 'chat-div'
        answerDiv.style.float = 'top'
        answerDiv.style.width = 'auto'
        answerDiv.style.height = 'auto'

        let sysDiv1 = document.createElement('div')
        sysDiv1.style.marginRight = "15px"
        sysDiv1.style.float = "left"

        let sysNode = document.getElementById('sysNode').cloneNode(true)

        let sysDiv2 = document.createElement('div')
        sysDiv2.className = 'chat-question'
        sysDiv2.style.float = "left"
        sysDiv2.style.backgroundColor = "#409EFF"
        sysDiv2.style.color = "white"
        sysDiv2.style.borderRadius = "10px"
        sysDiv2.style.width = "auto"
        sysDiv2.style.maxWidth = "550px"
        sysDiv2.style.padding = "10px"
        sysDiv2.style.height = "auto"
        sysDiv2.style.marginBottom = "20px"

        let sysP = document.createElement('p')
        sysP.className = 'chat-p'
        sysP.innerText = res
        sysP.style.fontFamily = "font-family: \"微软雅黑\", serif"
        sysP.style.fontSize = "18px"
        sysP.style.margin = "0px"
        sysP.style.padding = "0px 10px"
        sysP.align = "left"
        sysP.style.wordBreak = "break-word"

        let sysDiv3 = document.createElement('div')
        sysDiv3.style.clear = "both"

        sysDiv1.appendChild(sysNode)
        sysDiv2.appendChild(sysP)
        answerDiv.appendChild(sysDiv1)
        answerDiv.appendChild(sysDiv2)
        answerDiv.appendChild(sysDiv3)
        chatView.appendChild(answerDiv)
        chatView.scrollTop = chatView.scrollHeight
      } else {
        this.$notify({
          title: '警告',
          message: '未输入任何问题！',
          type: 'warning'
        });
      }
    },

    //
    handleStandardSearch() {
      if (this.input !== "") {
        const reg = new RegExp(this.input);
        this.possibleValue = this.dict.filter(function (word) {
          return reg.test(word)
        })
      }
    },

    //
    handleCopyValue(value) {
      let input = document.createElement('input');
      input.id = "temp_copy";
      input.setAttribute('readonly', 'readonly');
      input.setAttribute('value', "【" + value + "】");
      document.body.appendChild(input);
      input.select();
      document.execCommand("copy", false, null);
      document.body.removeChild(input);
      if (this.questionContent.split('【').length !== this.questionContent.split('】').length) {
        let words = this.questionContent.split('【')
        this.questionContent = words.slice(0, words.length - 1).join('【')
      }
      this.questionContent += "【" + value + "】"
      this.$refs.input.focus();
    },

    //
    toStandardSearch() {
      let words = this.questionContent.split('【')
      this.input = words[words.length - 1]
      this.handleStandardSearch()
    },
  },
}
</script>

<style scoped>
.div-chat {
  /*margin: auto;*/
  float: left;
  width: 900px;
  height: 675px;
  cursor: default;
  background: rgba(255, 255, 255, 0.3);
}

.chat-view {
  width: 100%;
  height: 495px;
  box-shadow: 0px 0px 4px darkgrey inset;
  position: relative;
  padding: 10px;
  overflow-y: auto;
}

.chat-input {
  position: relative;
}

.el-divider {
  margin-top: 10px;
  margin-bottom: 10px;
}

.chat-question {
  float: right;
  background-color: #fb7299;
  color: white;
  border-radius: 10px;
  width: auto;
  max-width: 550px;
  padding: 10px;
  height: auto;
  margin-bottom: 20px;
}

.chat-answer {
  float: left;
  background-color: #409EFF;
  color: white;
  border-radius: 10px;
  width: auto;
  max-width: 550px;
  padding: 10px;
  height: auto;
  margin-bottom: 20px;
}

p {
  font-family: "微软雅黑", serif;
  font-size: 18px;
  margin: 0px;
  padding: 0px 10px;
}

.div-list {
  float: left;
  margin-left: 30px;
  width: 183px;
  height: 673px;
}

.chat-div {
  float: top;
  width: auto;
  height: auto;
}

.chat-p {
  word-break: break-word;
}

</style>

<style>
.el-textarea__inner {
  background: rgba(255, 255, 255, 0.3);
}
</style>
