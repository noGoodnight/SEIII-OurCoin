<template>
  <div>
    <bg/>
    <el-container>
      <el-header style="box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);height: 80px">
        <h1>OurCOIN</h1>
        <h1>知识图谱</h1>
      </el-header>
      <el-divider id="homepage-divider"/>
      <el-container>
        <el-aside style="width:360px;margin-left: 10px">
          <div style="height: 680px">
            <el-tabs v-model="activeName" type="card">
              <el-tab-pane label="文 件 操 作" name="first">
                <div>
                  <h2>上传文件</h2>
                  <el-upload drag action="#" accept="json"
                             :on-change="handleUploadChange"
                             :auto-upload="false"
                             :file-list="fileList">
                    <i class="el-icon-upload"></i>
                    <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                  </el-upload>
                  <el-button type="primary" @click="handleUploadAck" style="margin-top: 10px">确认上传</el-button>
                </div>
                <el-divider/>
                <div>
                  <h2>下载文件</h2>
                  <el-dropdown trigger="click">
                    <el-button type="success">导出文件
                      <i class="el-icon-arrow-down el-icon&#45;&#45;right"/>
                    </el-button>
                    <template #dropdown>
                      <el-dropdown-menu style="width: 120px;">

                        <el-dropdown-item>
                          <el-button style="border:none;width: 100%;" @click="exportXML">XML</el-button>
                        </el-dropdown-item>
                        <el-dropdown-item>
                          <el-button style="border:none;width: 100%;" @click="exportPNG">PNG</el-button>
                        </el-dropdown-item>
                        <el-dropdown-item>
                          <el-button style="border:none;width: 100%;" @click="exportJSON">JSON</el-button>
                        </el-dropdown-item>
                      </el-dropdown-menu>
                    </template>
                  </el-dropdown>
                </div>
              </el-tab-pane>
              <el-tab-pane label="编 辑 节 点" name="second">
                <div>
                  <h2>修改节点</h2>
                  <el-form :label-position="'left'" label-width="80px" :model="nodeData">
                    <el-form-item label="实体名称">
                      <el-input v-model="nodeData.name"></el-input>
                    </el-form-item>
                    <el-form-item label="实体类别">
                      <el-select id="categorySelect" v-model="nodeData.category" style="width: 280px">
                        <el-option value="男性"></el-option>
                        <el-option value="女性"></el-option>
                      </el-select>
                    </el-form-item>
                  </el-form>
                  <div style="margin-top: 20px">
                    <el-button type="primary" @click="handleNodeChange" style="margin-right: 10px">确认</el-button>
                    <el-button type="danger" @click="handleNodeDelete" style="margin-left: 10px;margin-right: 10px">删除
                    </el-button>
                    <el-button @click="handleNodeRestore" style="margin-left: 10px">还原</el-button>
                  </div>
                </div>
                <el-divider/>
                <div>
                  <h2>新增节点</h2>
                  <el-form :label-position="'left'" label-width="80px" :model="newNodeData">
                    <el-form-item label="实体名称">
                      <el-input v-model="newNodeData.name"></el-input>
                    </el-form-item>
                    <el-form-item label="实体类别">
                      <el-select id="categorySelect" v-model="newNodeData.category" style="width: 280px">
                        <el-option value="男性"></el-option>
                        <el-option value="女性"></el-option>
                      </el-select>
                    </el-form-item>
                  </el-form>
                  <div style="margin-top: 20px">
                    <el-button type="primary" @click="handleNodeCreate" style="margin-right: 10px">确认</el-button>
                    <el-button @click="handleNodeRestore2" style="margin-left: 10px">还原</el-button>
                  </div>
                </div>
              </el-tab-pane>
              <el-tab-pane label="编 辑 关 系" name="third">
                <div>
                  <h2>修改关系</h2>
                  <el-form :label-position="'left'" label-width="80px" :model="edgeData">
                    <el-form-item label="关系起点">
                      <el-input v-model="edgeData.source"></el-input>
                    </el-form-item>
                    <el-form-item label="关系终点">
                      <el-input v-model="edgeData.target"></el-input>
                    </el-form-item>
                    <el-form-item label="关系名称">
                      <el-input v-model="edgeData.value"></el-input>
                    </el-form-item>
                  </el-form>
                  <div style="margin-top: 20px">
                    <el-button type="primary" @click="handleLinkChange" style="margin-right: 10px">确认</el-button>
                    <el-button type="danger" @click="handleLinkDelete" style="margin-left: 10px;margin-right: 10px">删除
                    </el-button>
                    <el-button @click="handleLinkRestore" style="margin-left: 10px">还原</el-button>
                  </div>
                </div>
                <el-divider/>
                <div>
                  <h2>新增关系</h2>
                  <el-form :label-position="'left'" label-width="80px" :model="newLinkData">
                    <el-form-item label="关系起点">
                      <el-input v-model="newLinkData.sourceName"></el-input>
                    </el-form-item>
                    <el-form-item label="关系终点">
                      <el-input v-model="newLinkData.targetName"></el-input>
                    </el-form-item>
                    <el-form-item label="关系名称">
                      <el-input v-model="newLinkData.value"></el-input>
                    </el-form-item>
                  </el-form>
                  <div style="margin-top: 20px">
                    <el-button type="primary" @click="handleLinkCreate" style="margin-right: 10px">确认</el-button>
                    <el-button @click="handleLinkRestore2" style="margin-left: 10px">还原</el-button>
                  </div>
                </div>
              </el-tab-pane>
            </el-tabs>
          </div>
        </el-aside>
        <el-divider direction="vertical" style="height: 7em"/>
        <el-main id="chart"/>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import Background from "@/components/Background";
import * as echarts from 'echarts'
import homedata from '@/data/HomePage'

export default {
  name: "Homepage",
  components: {
    bg: Background
  },
  data() {
    return {
      // option: JSON.parse(JSON.stringify(homedata.option)),
      option: null,
      isCollapse: true,
      activeName: 'first',
      mychart: null,
      formLabelAlign: {
        name: '',
        region: '',
        type: ''
      },
      nodeDataBackUp: null,
      nodeData: {},
      edgeDataBackUp: null,
      edgeData: {},
      newNodeData: JSON.parse(JSON.stringify(homedata.nodeTemplate)),
      newLinkData: JSON.parse(JSON.stringify(homedata.linkTemplate)),
      newNodeNum: 0,
      newLinkNum: 0,
      fileList: [],
      graphs: []
    }
  },
  mounted() {
    this.initGraph()
  },
  methods: {
    initGraph() {
      const _this = this
      _this.mychart = echarts.init(document.getElementById("chart"))
      // _this.mychart.setOption(this.option)

      _this.mychart.on('click', function (param) {
        if (param.dataType === 'node') {
          _this.handleClickNode(JSON.parse(JSON.stringify({
            data: param.data,
            dataIndex: param.dataIndex
          })))
        } else if (param.dataType === 'edge') {
          _this.handleClickEdge(JSON.parse(JSON.stringify({
            data: param.data,
            dataIndex: param.dataIndex
          })))
        }
      })
    },


    handleClickNode(nodeData) {
      this.nodeDataBackUp = nodeData
      this.nodeData = {
        category: this.option.legend.data[nodeData.data.category],
        name: nodeData.data.name,
      }
      this.activeName = 'second'
    },

    handleClickEdge(edgeData) {
      this.edgeDataBackUp = edgeData
      this.edgeData = {
        source: edgeData.data.source,
        target: edgeData.data.target,
        value: edgeData.data.value,
        // sourceName: this.option.series[0].data[edgeData.data.source].name,
        // targetName: this.option.series[0].data[edgeData.data.target].name
      }
      this.activeName = 'third'
    },

    handleNodeChange() {
      console.log(this.nodeData)
      this.option.series[0].data[this.nodeDataBackUp.dataIndex].name = this.nodeData.name
      this.option.series[0].data[this.nodeDataBackUp.dataIndex].category = this.option.legend.data.indexOf(this.nodeData.category)
      this.mychart.setOption(this.option)
    },

    handleNodeDelete() {
      // console.log(this.option)
      const index = this.nodeDataBackUp.dataIndex;
      // this.option.series[0].data[this.nodeDataBackUp.dataIndex].category = -1
      this.option.series[0].data.splice(index, 1);
      // this.nodeData = {
      //   name: '',
      //   category: '',
      //  }
      this.mychart.setOption(this.option)
    },

    handleNodeRestore() {
      // this.option.series[0].data[this.nodeDataBackUp.dataIndex].name = this.nodeDataBackUp.data.name
      // this.option.series[0].data[this.nodeDataBackUp.dataIndex].category = this.nodeDataBackUp.data.category
      // this.nodeData.name = this.nodeDataBackUp.data.name
      // this.nodeData.category = this.option.legend.data[this.nodeDataBackUp.data.category]
      // this.mychart.setOption(this.option)
      const data = {
        "name": this.nodeDataBackUp.data.name,
        "category": this.nodeDataBackUp.data.category,
        "draggable": true
      };
      this.option.series[0].data.splice(this.nodeDataBackUp.dataIndex, 0, data);
      this.mychart.setOption(this.option)
      console.log(this.option)
    },

    handleNodeCreate() {
      var nodeData = JSON.parse(JSON.stringify(this.newNodeData))
      nodeData.category = this.option.legend.data.indexOf(nodeData.category)
      this.option.series[0].data.push(nodeData)
      this.mychart.setOption(this.option)
      this.newNodeNum += 1
    },

    handleNodeRestore2() {
      if (this.newNodeNum > 0) {
        this.option.series[0].data.pop();
        this.mychart.setOption(this.option)
        this.newNodeNum -= 1
        console.log(this.option)
      }
    },

    handleLinkDelete() {
      // var _this = this
      // _this.option.series[0].links[_this.edgeDataBackUp.dataIndex].source = -1
      // _this.edgeData = {
      //   sourceName: '',
      //   targetName: '',
      //   source: -1,
      //   target: -1,
      //   value: ''
      // }
      const index = this.edgeDataBackUp.dataIndex;
      this.option.series[0].links.splice(index, 1);
      this.mychart.setOption(this.option)
    },

    handleLinkRestore() {
      var _this = this
      const data = {
        "source": _this.edgeDataBackUp.data.source,
        "target": _this.edgeDataBackUp.data.target,
        "value": _this.edgeDataBackUp.data.value
      }
      // _this.option.series[0].links[_this.edgeDataBackUp.dataIndex].value = _this.edgeDataBackUp.data.value
      // _this.option.series[0].links[_this.edgeDataBackUp.dataIndex].source = _this.edgeDataBackUp.data.source
      // _this.option.series[0].links[_this.edgeDataBackUp.dataIndex].target = _this.edgeDataBackUp.data.target
      _this.edgeData.source = _this.edgeDataBackUp.data.source
      _this.edgeData.target = _this.edgeDataBackUp.data.target
      _this.edgeData.value = _this.edgeDataBackUp.data.value
      this.option.series[0].links.splice(_this.edgeDataBackUp.dataIndex, 0, data);
      // _this.edgeData.sourceName = _this.option.series[0].data[_this.edgeDataBackUp.data.source].name,
      //   _this.edgeData.targetName = _this.option.series[0].data[_this.edgeDataBackUp.data.target].name,
      this.mychart.setOption(_this.option)
    },

    handleLinkChange() {
      this.option.series[0].links[this.edgeDataBackUp.dataIndex].value = this.edgeData.value
      this.option.series[0].links[this.edgeDataBackUp.dataIndex].source = this.getIndexOfNode(this.edgeData.sourceName)
      this.option.series[0].links[this.edgeDataBackUp.dataIndex].target = this.getIndexOfNode(this.edgeData.targetName)
      this.mychart.setOption(this.option)
    },

    handleLinkCreate() {
      var _this = this
      _this.newLinkData.source = _this.getIndexOfNode(_this.newLinkData.sourceName)
      _this.newLinkData.target = _this.getIndexOfNode(_this.newLinkData.targetName)
      var linkData = JSON.parse(JSON.stringify(_this.newLinkData))
      _this.option.series[0].links.push({
        source: linkData.source,
        target: linkData.target,
        value: linkData.value
      })
      _this.newLinkNum += 1
      _this.mychart.setOption(_this.option)
    },

    handleLinkRestore2() {
      var _this = this
      if (_this.newLinkNum > 0) {
        _this.option.series[0].links.pop();
        _this.mychart.setOption(_this.option)
        _this.newLinkNum -= 1
      }
    },

    exportXML() {
      const xml2js = require('xml2js')
      let builder = new xml2js.Builder()
      let jsonData = {
        graph: this.option.title.text,
        data: this.option.series[0].data,
        links: this.option.series[0].links
      }
      let dataXML = builder.buildObject(jsonData)
      let eleLink = document.createElement('a')
      eleLink.download = `graph.xml`
      eleLink.style.display = 'none'
      let blob = new Blob([dataXML])
      eleLink.href = URL.createObjectURL(blob)
      document.body.appendChild(eleLink)
      eleLink.click()
      document.body.removeChild(eleLink)
    },

    exportPNG() {
      let eleLink = document.createElement('a')
      eleLink.download = `graph.png`
      eleLink.style.display = 'none'
      eleLink.href = this.mychart.getDataURL({
        pixelRatio: 2,
        backgroundColor: '#fff'
      })
      document.body.appendChild(eleLink)
      eleLink.click()
      document.body.removeChild(eleLink)
    },

    exportJSON() {
      let jsonData = {
        graph: this.option.title.text,
        data: this.option.series[0].data,
        links: this.option.series[0].links
      }
      let dataJSON = JSON.stringify(jsonData, null, "  ")
      let eleLink = document.createElement('a')
      eleLink.download = `graph.json`
      eleLink.style.display = 'none'
      let blob = new Blob([dataJSON])
      eleLink.href = URL.createObjectURL(blob)
      document.body.appendChild(eleLink)
      eleLink.click()
      document.body.removeChild(eleLink)
    },

    getIndexOfNode(nodeName) {
      for (var i = 0; i < this.option.series[0].data.length; i++) {
        let node = this.option.series[0].data[i]
        if (nodeName === node.name) {
          return i;
        }
      }
    },

    handleUploadChange(file) {
      const _this = this
      if (file.raw.name.endsWith('.json')) {
        let reader = new FileReader()
        let content = null
        reader.onload = async function () {
          content = reader.result
          _this.graphs.push({
            gname: file.raw.name,
            goption: JSON.parse(content)
          })
        }
        reader.readAsText(file.raw)
      } else {
        this.$message.error('只能上传json文件！')
        _this.fileList.push(file)
        _this.fileList.pop()
      }
    },

    async handleUploadAck() {
      var _this = this
      _this.option = _this.graphs[0].goption
      _this.mychart.setOption(_this.option)
      // const res = await hello({
      //   data: JSON.stringify(_this.option)
      // })
      // console.log(res)
    }
  }
}
</script>

<style scoped>
.el-divider--vertical {
  display: inline-block;
  width: 1px;
  height: 675px;
  margin: 0 8px;
  vertical-align: middle;
  position: relative;
}

#homepage-divider {
  margin: 0;
}
</style>
