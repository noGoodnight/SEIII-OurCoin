<template>
  <div :id="'kg-'+suffix" class="kg-d3" @contextmenu.prevent="">
    <div>
      <search-bar v-show="show2.searchBar" @closeSearch="closeSearch"></search-bar>
      <node-editor v-show="show2.nodeEditor"></node-editor>
      <link-editor v-show="show2.linkEditor"></link-editor>
    </div>
    <div class="kg-container">
      <span style="color: navy;position:absolute;top:4px;left: 7px">右击节点或关系显示菜单</span>
      <kg-tool @reduceKG="handleKGReduceImpl" @downloadKG="handleKGDownloadImpl"
               @renderKG="handleKGRenderImpl" @saveKG="handleKGSaveImpl" @searchKG="changeTopBar('searchBar')"
               @switchLabel="handleKGLabelSwitchImpl"></kg-tool>
      <node-menu ref="nodeMenu" :suffix="suffix" v-show="show.nodeMenu" @deleteNode="handleNodeDeleteImpl"
                 @createLink="createLink"></node-menu>
      <link-menu ref="linkMenu" :suffix="suffix" v-show="show.linkMenu" @deleteLink="handleLinkDeleteImpl"
                 @switchLabel="handleLinkLabelSwitchImpl"></link-menu>
    </div>
  </div>
</template>

<script>
import * as d3 from 'd3';
import KGTool from "@/components/KGTool";
import NodeMenu from "@/components/NodeMenu";
import LinkMenu from "@/components/LinkMenu";
import NodeEditor from "@/components/NodeEditor";
import LinkEditor from "@/components/LinkEditor";
import SearchBar from "@/components/SearchBar";

import * as relationApi from "@/api/relationApi"
import * as entityApi from "@/api/entityApi"
import * as graphApi from "@/api/graphApi"

export default {
  name: "KGd3",
  components: {
    "kg-tool": KGTool,
    "node-menu": NodeMenu,
    "link-menu": LinkMenu,
    "node-editor": NodeEditor,
    "link-editor": LinkEditor,
    "search-bar": SearchBar,
  },
  data() {
    return {
      // 初始化图谱所需配置
      links: null,
      nodes: null,
      width: 1096,
      height: 715,
      svg: null,
      simulation: null,
      zoom: null,
      link: null,
      linkText: null,
      linkPath: null,
      node: null,
      nodeText: null,
      marker1: null,
      marker2: null,

      // 菜单是否显示
      show: {
        nodeMenu: false,
        linkMenu: false,
      },

      // 节点编辑，关系编辑，搜索框是否显示
      show2: {
        nodeEditor: false,
        linkEditor: false,
        searchBar: true,
      },

      // 选中时相关数据
      current: null,
      currentd: null,

      // 图谱缩放数据
      zoomEvent: null,

      // 是否正在新建关系
      isLinkCreating: false,
    }
  },
  props: {
    suffix: String,
    src: Object,
  },
  created() {
  },
  mounted() {
    this.initKGData()
    this.initKG()
  },
  methods: {
    // 初始化图谱数据
    initKGData() {
      const _this = this
      _this.svg = d3.select(".kg-container").append("svg").attr("viewBox", [0, 0, _this.width, _this.height]);
      _this.links = _this.src.links.map(d => Object.create(d));
      _this.nodes = _this.src.nodes.map(d => Object.create(d));
      const g = _this.svg.append("g").attr("id", "g-" + _this.suffix)
    },

    // 初始化图谱设置
    initKG() {
      const _this = this

      const g = d3.select("#g-" + _this.suffix)

      _this.marker1 = g.append('g')
          .append("marker")
          .attr("id", 'arrowhead1')
          .attr('viewBox', '-0 -5 10 10')
          .attr('refX', 18)
          .attr('refY', 0)
          .attr('orient', 'auto')
          .attr('markerWidth', 8)
          .attr('markerHeight', 8)
          .attr('xoverflow', 'visible')
          .append('svg:path')
          .attr('d', 'M 0,-5 L 10 ,0 L 0,5')
          .attr("stroke-opacity", 0.8)
          .attr('fill', 'darkgrey');

      _this.marker2 = g.append('g')
          .append("marker")
          .attr("id", 'arrowhead2')
          .attr('viewBox', '-0 -5 10 10')
          .attr('refX', -8)
          .attr('refY', 0)
          .attr('orient', 'auto')
          .attr('markerWidth', 8)
          .attr('markerHeight', 8)
          .attr('xoverflow', 'visible')
          .append('svg:path')
          .attr('d', 'M 10,-5 L 0 ,0 L 10,5')
          .attr("stroke-opacity", 0.8)
          .attr('fill', 'darkgrey');

      _this.simulation = d3.forceSimulation(_this.nodes)
          .force("link", d3.forceLink(_this.links).id(d => d.id).distance(100))
          .force("charge", d3.forceManyBody().strength(-250))
          .force("center", d3.forceCenter(_this.width / 2, _this.height / 2));

      _this.link = g.append("g")
          .selectAll(".link")
          .data(_this.links.filter(function (d) {
            return _this.activeCategory[d.source.category] && _this.activeCategory[d.target.category]
          }))
          .join("path")
          .attr("stroke-width", 2)
          .attr("stroke", "darkgrey")
          .attr("stroke-opacity", 0.8)
          .attr("id", function (d) {
            return ("link-" + d.id)
          })
          .attr("class", "link")
          .on("click", function (event, d) {
            _this.current = d3.select(this)
            _this.currentd = d
            _this.handleLinkClick(event, d)
          })
          .on("contextmenu", function (event, d) {
            _this.current = d3.select(this)
            _this.currentd = d
            _this.handleLinkContextMenu(event, d)
          });

      _this.node = g.append("g")
          .selectAll("circle")
          .data(_this.nodes.filter(function (d) {
            return _this.activeCategory[d.category]
          }))
          .join("circle")
          .attr("r", 12)
          .attr("fill", d => d.color)
          .attr("stroke", "#fff")
          .attr("stroke-width", 1.5)
          .attr("id", d => "node-" + d.id)
          .attr("class", "node")
          .call(_this.drag(_this.simulation))
          .on("click", function (event, d) {
            _this.current = d3.select(this)
            _this.currentd = d
            _this.handleNodeClick(event, d)
          })
          .on("contextmenu", function (event, d) {
            _this.current = d3.select(this)
            _this.currentd = d
            _this.handleNodeContextMenu(event, d)
          });

      _this.node.append("title").text(d => d.category)

      // 节点名称显示
      _this.nodeText = g.append("g")
          .selectAll(".nodeText")
          .data(_this.nodes.filter(function (d) {
            return _this.activeCategory[d.category]
          }))
          .join("text")
          .text(d => d.name)
          .attr("dx", function () {
            return this.getBoundingClientRect().width / 2 * (-1)
          })
          .attr("dy", 25)
          .attr("font-size", 15)
          .attr("id", d => "nodeText" + d.id)
          .attr("class", "nodeText");

      _this.linkText = g.append("g")
          .selectAll(".linkText")
          .data(_this.links.filter(function (d) {
            return _this.activeCategory[d.source.category] && _this.activeCategory[d.target.category]
          }))
          .join("text")
          //.attr("dx", function () {
          //return this.getBoundingClientRect().width / 2 * (-1)
          //})
          .attr("dy", -5)
          .attr("id", d => "linkText" + d.id)
          .attr("font-size", 10)
          .attr("display", "unset")
          .attr("class", "linkText");

      _this.linkText
          .append("textPath")
          .attr("xlink:href", function (d) {
            return ("#link-" + d.id)
          })
          .attr("text-anchor", "middle")
          .attr("startOffset", "50%")
          .on("click", function (event, d) {
            _this.current = d3.select(this)
            _this.currentd = d
            _this.handleLinkClick(event, d)
          })
          .text(d => d.name);

      // 力导：节点、关系、节点文字、关系标签
      _this.simulation.on("tick", () => {
        _this.link
            .attr("marker-end", function (d) {
              if (d.source.x < d.target.x) {
                return "url(#arrowhead1)"
              } else {
                return null
              }
            })
            .attr("marker-start", function (d) {
              if (d.source.x < d.target.x) {
                return null
              } else {
                return "url(#arrowhead2)"
              }
            })
            .attr('d', function (d) {
              if (d.source.x < d.target.x) {
                return 'M ' + d.source.x + ' ' + d.source.y + ' L ' + d.target.x + ' ' + d.target.y;
              } else {
                return 'M ' + d.target.x + ' ' + d.target.y + ' L ' + d.source.x + ' ' + d.source.y;
              }
            });
        // .attr("x1", d => d.source.x)
        // .attr("y1", d => d.source.y)
        // .attr("x2", d => d.target.x)
        // .attr("y2", d => d.target.y);

        _this.node
            .attr("cx", d => d.x)
            .attr("cy", d => d.y);

        _this.nodeText
            .attr("x", d => d.x)
            .attr("y", d => d.y);

        // _this.linkText
        //     .attr("x", d => (d.source.x + d.target.x) / 2)
        //     .attr("y", d => (d.source.y + d.target.y) / 2)
      });

      // 设置图谱的缩放
      _this.zoom = d3.zoom()
          .extent([[0, 0], [_this.width, _this.height]])
          .scaleExtent([0.1, 8])
          .on("zoom", _this.zoomed);

      _this.svg.call(_this.zoom)
          .on("dblclick.zoom", null); // 屏蔽双击缩放

      if (_this.zoomEvent === null) {
        _this.svg.call(_this.zoom.transform, d3.zoomIdentity.scale(1)) // 设置一次不变的缩放抵消关系标签的文字偏移
      } else {
        _this.zoomed(_this.zoomEvent)
      }

      _this.$store.commit('setStatistics',
          {nodeNum: _this.nodes.length, linkNum: _this.links.length})
    },

    // 图谱拖拽
    drag(simulation) {
      function dragstarted(event) {
        if (!event.active) simulation.alphaTarget(0.3).restart();
        event.subject.fx = event.subject.x;
        event.subject.fy = event.subject.y;
      }

      function dragged(event) {
        event.subject.fx = event.x;
        event.subject.fy = event.y;
      }

      function dragended(event) {
        if (!event.active) simulation.alphaTarget(0);
        event.subject.fx = null;
        event.subject.fy = null;
      }

      return d3.drag()
          .on("start", dragstarted)
          .on("drag", dragged)
          .on("end", dragended);
    },

    // 图谱缩放
    zoomed(event) {
      const g = d3.select("#g-" + this.suffix)
      g.attr("transform", event.transform);
      if (event.transform.k >= 1) {
        g.selectAll("circle")
            .attr("r", 12 / event.transform.k)
            .attr("stroke-width", 1.5 / event.transform.k);
        g.selectAll("path")
            .attr("stroke-width", 2 / event.transform.k);
        g.selectAll(".nodeText")
            .attr("font-size", 15 / event.transform.k)
            .attr("dx", function () {
              return (this.getBoundingClientRect().width / 2 * (-1)) / event.transform.k
            })
            .attr("dy", 25 / event.transform.k);
        g.selectAll(".linkText")
            .attr("font-size", 10 / event.transform.k)
            .attr("dy", -5 / event.transform.k);
      }
      this.zoomEvent = event
    },

    // 还原图谱缩放和位置
    handleKGReduceImpl() {
      const _this = this
      _this.svg.call(_this.zoom.transform, d3.zoomIdentity.scale(1))
    },

    // 图谱下载为各种文件
    handleKGDownloadImpl(type) {
      const _this = this
      if (type === "png" || type === "jpg") {
        _this.svg2image(type)
      } else if (type === "json" || type === "xml") {
        _this.svg2text(type)
      }
    },

    // svg图片下载
    svg2image(type) {
      const _this = this
      let serializer = new XMLSerializer();
      let source = '<?xml version="1.0" standalone="no"?>\r\n' + serializer.serializeToString(_this.svg.node());
      let image = new Image;
      image.src = "data:image/svg+xml;charset=utf-8," + encodeURIComponent(source);
      let canvas = document.createElement("canvas");
      canvas.width = 2 * _this.width;
      canvas.height = 2 * _this.height;
      let context = canvas.getContext("2d");
      context.fillStyle = "#fff"
      context.fillRect(0, 0, 2 * _this.width, 2 * _this.height);
      image.onload = function () {
        context.drawImage(image, 0, 0);
        let a = document.createElement("a");
        a.download = "kg." + type;
        a.href = canvas.toDataURL("image/" + type);
        a.click();
      };
    },

    // 图谱文本下载
    svg2text(type) {
      const _this = this
      if (type === "json") {
        let dataJSON = JSON.stringify(_this.nodes, null, "  ")
        let eleLink1 = document.createElement('a')
        eleLink1.download = `kg.json`
        let blob1 = new Blob([dataJSON])
        eleLink1.href = URL.createObjectURL(blob1)
        eleLink1.click()
      } else if (type === "xml") {
        const xml2js = require('xml2js')
        let builder = new xml2js.Builder()
        let xmlData = _this.nodes
        let dataXML = builder.buildObject(xmlData)
        let eleLink = document.createElement('a')
        eleLink.download = `kg.xml`
        let blob = new Blob([dataXML])
        eleLink.href = URL.createObjectURL(blob)
        eleLink.click()
      }
    },

    // 右击节点
    handleNodeContextMenu(event, d) {
      const _this = this
      for (let showKey in _this.show) {
        _this.show[showKey] = false
      }
      _this.show.nodeMenu = true
      _this.$refs.nodeMenu.locateNodeMenu(event)
    },

    // 右击关系
    handleLinkContextMenu(event, d) {
      const _this = this
      for (let showKey in _this.show) {
        _this.show[showKey] = false
      }
      _this.show.linkMenu = true
      _this.$refs.linkMenu.locateLinkMenu(event)
    },

    // 删除节点
    async handleNodeDeleteImpl() {
      const _this = this
      let res = await entityApi.deleteEntityApi({
        eid: _this.currentd.id,
      })
      console.log(res)
      for (let i = 0; i < _this.links; i++) {
        if (_this.links[i].source === _this.currentd || _this.links[i].target === _this.currentd) {
          res = await relationApi.deleteRelationApi({
            rid: _this.links[i].id
          })
        }
      }
      _this.nodes = _this.nodes.filter(function (d) {
        return _this.currentd !== d
      });
      _this.links = await _this.links.filter(function (d) {
        return (d.source !== _this.currentd && d.target !== _this.currentd)
      });
      _this.resetSvg()
      _this.initKG()
      _this.show.nodeMenu = false
    },

    // 删除关系
    async handleLinkDeleteImpl() {
      const _this = this
      const res = await relationApi.deleteRelationApi({
        rid: _this.currentd.id,
      })
      _this.links = _this.links.filter(function (d) {
        return _this.currentd !== d
      })
      _this.resetSvg()
      _this.initKG()
      _this.show.linkMenu = false
    },

    // 新建关系
    createLink() {
      const _this = this
      _this.show.nodeMenu = false
      _this.isLinkCreating = true
      _this.$store.commit('setLinkCreatorSource', {d: _this.currentd})
      _this.$store.commit('setPanel', {current: "linkCreator"})
    },

    // 切换关系标签
    handleLinkLabelSwitchImpl() {
      const _this = this
      let labelView = d3.select("#linkText" + _this.currentd.id).attr("display");
      if (labelView === "unset") {
        d3.select("#linkText" + _this.currentd.id).attr("display", "none")
      } else {
        d3.select("#linkText" + _this.currentd.id).attr("display", "unset")
      }
      _this.show.linkMenu = false
    },

    // 点击节点
    handleNodeClick(event, d) {
      const _this = this
      if (_this.isLinkCreating) {
        _this.$store.commit('setLinkCreatorTarget', {d: _this.currentd})
      } else {
        const info = {
          d: _this.currentd,
        }
        // console.log(info)
        _this.$store.commit('editor/setNodeInfo', info)
        _this.changeTopBar('nodeEditor')
      }
    },

    // 点击关系
    handleLinkClick(event, d) {
      const _this = this
      _this.changeTopBar('linkEditor')
      const info = {
        d: _this.currentd
      }
      _this.$store.commit('editor/setLinkInfo', info)
    },

    // 重置svg当中的g标签
    resetSvg() {
      d3.select("#g-" + this.suffix).remove()
      this.svg.append("g").attr("id", "g-" + this.suffix)
    },

    // 点击graph界面
    clickGraphWeb() {
      for (let showKey in this.show) {
        this.show[showKey] = false
      }
    },

    // 重新绘制图谱
    handleKGRenderImpl() {
      const _this = this
      _this.nodes = _this.nodes.map(function (d) {
        return Object.create({
          id: d.id,
          category: d.category,
          name: d.name,
          color: d.color,
        })
      })
      _this.links = _this.links.map(function (d) {
        return Object.create({
          source: d.source.id,
          target: d.target.id,
          name: d.name,
          id: d.id,
        })
      })
      _this.resetSvg()
      _this.initKG()
      _this.svg.call(_this.zoom.transform, d3.zoomIdentity.scale(1))
    },

    // 添加新的关系
    async addLinK() {
      const _this = this
      if (_this.$store.state.linkCreatorSource === null || _this.$store.state.linkCreatorTarget === null) {
        this.$notify({
          title: '警告',
          message: '未选择新建关系的目标！',
          type: 'warning'
        });
      } else if (_this.$store.state.linkCreatorSource === _this.$store.state.linkCreatorTarget) {
        this.$notify({
          title: '警告',
          message: '起源和目标为相同节点！',
          type: 'warning'
        });
      } else if (_this.$store.state.linkCreatorSource !== null && _this.$store.state.linkCreatorTarget !== null) {
        const res = await relationApi.addRelationApi({
          gid: _this.src.gid,
          rid: null,
          name: 'new link',
          theFirstEid: _this.$store.state.linkCreatorSource.id,
          theSecondEid: _this.$store.state.linkCreatorTarget.id,
        })
        _this.links = _this.links.map(function (d) {
          return Object.create({
            id: d.id,
            source: d.source.id,
            target: d.target.id,
            name: d.name
          })
        });
        _this.links.push({
          id: res,
          source: _this.$store.state.linkCreatorSource.id,
          target: _this.$store.state.linkCreatorTarget.id,
          name: "new link"
        })
        _this.resetSvg()
        _this.initKG()
      }
      _this.$store.state.linkCreatorSource = null
      _this.$store.state.linkCreatorTarget = null
      _this.$store.state.linkCreatorAck = false
      _this.isLinkCreating = false
    },

    // 修改节点属性
    editNode() {
      const _this = this
      this.changeTopBar('searchBar')
      this.$store.state.editor.nodeEditAck = false
      _this.resetSvg()
      _this.initKG()
      this.$message({
        message: '节点修改成功！',
        type: 'success'
      });
    },

    // 修改关系属性
    editLink() {
      const _this = this
      this.changeTopBar('searchBar')
      this.$store.state.editor.linkEditAck = false
      _this.resetSvg()
      _this.initKG()
      this.$message({
        message: '关系修改成功！',
        type: 'success'
      });
    },

    // 在图内进行内容搜索
    handleSearch() {
      if (this.searchContent === "") {
        return;
      }
      const _this = this;
      const reg = new RegExp(this.searchContent);
      _this.nodeText.style("fill", d => {
        if (reg.test(d.name)) {
          return "#fb7299"
        } else {
          return "#000000"
        }
      });
      this.$store.state.searchContent = null
    },

    // 处理顶部栏改变
    changeTopBar(topBar) {
      const _this = this
      for (let showKey in _this.show2) {
        _this.show2[showKey] = showKey === topBar
      }
    },

    // 保存图谱到后端
    async handleKGSaveImpl() {
      const _this = this
      const res = await graphApi.saveGraph({
        uid: _this.uid,
        gid: _this.gid,
        name: _this.gname,
        entities: _this.nodes.map(function (d) {
          return {
            eid: d.id,
            name: d.name,
            gid: _this.src.gid,
            category: d.category,
            color: d.color,
            x: d.x,
            y: d.y
          }
        }),
        relations: _this.links.map(function (d) {
          return {
            rid: d.id,
            gid: _this.src.gid,
            theFirstEid: d.source.id,
            theSecondEid: d.target.id,
            name: d.name,
          }
        })
      })
      this.$message({
        message: '图谱保存成功！',
        type: 'success'
      });
    },

    // 切换整个图谱标签显示
    handleKGLabelSwitchImpl() {
      const _this = this
      let labelView = d3.selectAll(".linkText").attr("display");
      if (labelView === "none") {
        d3.selectAll(".linkText").attr("display", "unset")
      } else {
        d3.selectAll(".linkText").attr("display", "none")
      }
    },

    // 清除搜索内容
    closeSearch() {
      const _this = this
      _this.nodeText.style("fill", "#000000")
      _this.linkText.style("fill", "#000000")
    },

    //
    resetData(){
      const _this = this
      _this.links = _this.src.links.map(d => Object.create(d));
      _this.nodes = _this.src.nodes.map(d => Object.create(d));
    }
  },
  computed: {
    linkCreatorAck() {
      return this.$store.state.linkCreatorAck
    },
    nodeEditAck() {
      return this.$store.state.editor.nodeEditAck
    },
    linkEditAck() {
      return this.$store.state.editor.linkEditAck
    },
    searchContent() {
      return this.$store.state.searchContent
    },
    activeCategory() {
      return this.$store.state.category;
    },
  },
  watch: {
    linkCreatorAck() {
      if (this.$store.state.linkCreatorAck) {
        this.addLinK()
      }
    },
    nodeEditAck() {
      if (this.$store.state.editor.nodeEditAck) {
        this.editNode()
      }
    },
    linkEditAck() {
      if (this.$store.state.editor.linkEditAck) {
        this.editLink()
      }
    },
    searchContent() {
      if (this.$store.state.searchContent !== null) {
        this.handleSearch()
      }
    },
  },
}
</script>

<style>
.kg-d3 {
  width: 1160px;
  height: 760px;
  position: relative;
}

.kg-container {
  width: 1096px;
  height: 715px;
  cursor: default;
  background: rgba(255,255,255,0.2);
  /*border: 2px solid whitesmoke;*/
  box-shadow: 0px 0px 4px darkgrey inset;
  position: relative;
}

.link {
}

.node {
}

.nodeText {
  fill: #000000;
}

.linkText {
}

.ln-menu {
  /*位置*/
  position: absolute;
  top: 0px;
  left: 0px;
  /*大小*/
  min-width: 80px;
  /*height: 100px;*/
  /*背景及边框*/
  background-color: white;
  border: 1px solid rgb(180, 180, 180);
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
  padding: 4px 0px;
  /*字体及字体大小*/
  font-size: 16px;
  font-family: "微软雅黑", serif;
  /*div高度*/
  z-index: 9999;
  /*光标*/
  cursor: default;
}

.ln-menu ul {
  margin: 0;
  padding: 0;
  text-align: left;
  list-style-type: none;
}

.ln-menu ul li {
  padding: 4px 25px;
  font-size: 14px;
}

.ln-menu ul li:hover {
  background: #E7EBEE;
}

.ln-menu ul li a:link {
  color: #000;
  text-decoration: none;
}

.divider-ln-menu {
  margin: 4px 0px;
}
</style>
