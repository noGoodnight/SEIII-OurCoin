<template>
  <div @contextmenu.prevent="">
    <h1>{{ graph.gname }}</h1>
    <div class="kg-only-wrapper">
      <span style="position: absolute;top:3px;right: 7px;color: navy">右击<span style="color: #F56C6C">番剧</span>节点查看详情，双击节点拓展图谱</span>
    </div>
    <el-drawer :visible.sync="drawer" direction="rtl" :with-header="false" :size="490">
      <div v-if="detail!==null" style="height: auto;overflow-y: auto;padding: 20px 0px;z-index: 100;filter: unset">
        <h1 style="font-size: 32px;color: white">番剧详情</h1>
        <h1 style="font-size: 24px;font-family: 楷体;color: white">{{ currentd.name }}</h1>
        <el-image
            :src="detail.jpg"
            fit="fill"
            style="width: 280px;border-radius: 8px;border: 4px white solid"
        ></el-image>
        <br/>
        <br>
        <span style="color:white;">标签：{{ detail.tags }}</span>
        <br/>
        <br/>
        <p align="left" style="padding: 0px 20px;color: white">{{ "简介：" + detail.description }}</p>
        <el-button @click="goToDetail(detail.url)" style="background: #fb7299;color: white;border: #fb7299">前往B站
        </el-button>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import * as d3 from "d3";
import * as userApi from '@/api/userApi';
import * as entityApi from "@/api/entityApi";

export default {
  name: "KGOnly",
  props: {
    graph: Object,
  },
  data() {
    return {
      // 初始化图谱所需配置
      links: null,
      nodes: null,
      width: 1113,
      height: 678,
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
      g: null,
      zoomEvent: null,

      // 侧边栏
      drawer: false,
      currentd: null,
      detail: null,
    }
  },
  created() {
  },
  mounted() {
    const _this = this
    _this.svg = d3.select(".kg-only-wrapper").append("svg").attr("viewBox", [0, 0, _this.width, _this.height]);
    this.initKGData();
    this.initKG();
  },
  methods: {
    // 初始化图谱数据
    initKGData() {
      const _this = this
      _this.links = _this.graph.links.map(d => Object.create(d));
      _this.nodes = _this.graph.nodes.map(d => Object.create(d));
      _this.g = _this.svg.append("g")
    },

    // 初始化图谱设置
    initKG() {
      const _this = this

      const g = _this.g

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
          .force("charge", d3.forceManyBody().strength(-30))
          .force("center", d3.forceCenter(_this.width / 2, _this.height / 2));

      _this.link = g.append("g")
          .selectAll(".link")
          .data(_this.links)
          .join("path")
          .attr("stroke-width", 2)
          .attr("stroke", "darkgrey")
          .attr("stroke-opacity", 0.8)
          .attr("id", function (d) {
            return ("link-" + d.index)
          })
          .attr("class", "link");

      _this.node = g.append("g")
          .selectAll("circle")
          .data(_this.nodes)
          .join("circle")
          .attr("r", 12)
          .attr("fill", d => d.color)
          .attr("stroke", "#fff")
          .attr("stroke-width", 1.5)
          .attr("class", "node")
          .on("contextmenu", function (event, d) {
            if (d.category === "animation") {
              _this.currentd = d
              _this.getNodeDetail(d)
              _this.drawer = true
            }
          })
          .on("dblclick", function (event, d) {
            _this.extendKG(event, d);
          })
          .call(_this.drag(_this.simulation));

      _this.node.append("title").text(d => d.category)

      // 节点名称显示
      _this.nodeText = g.append("g")
          .selectAll(".nodeText")
          .data(_this.nodes)
          .join("text")
          .text(d => d.name)
          .attr("dx", function () {
            return this.getBoundingClientRect().width / 2 * (-1)
          })
          .attr("dy", 25)
          .attr("font-size", 15)
          .attr("class", "nodeText");

      _this.linkText = g.append("g")
          .selectAll(".linkText")
          .data(_this.links)
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
            return ("#link-" + d.index)
          })
          .style("text-anchor", "middle")
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
        _this.svg.call(_this.zoom.transform, d3.zoomIdentity.scale(1))
      } else {
        _this.zoomed(_this.zoomEvent)
      } // 设置一次不变的缩放抵消关系标签的文字偏移
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
      const g = this.g
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

    // 双击节点扩展图谱
    async extendKG(event, d) {
      const _this = this
      const uid = this.$store.state.user.uid;
      const res = await userApi.searchByEidAckApi({
        uid: uid,
        eid: d.id,
      })
      _this.$emit('updateHistory')

      _this.graph.nodes.map(function (node, index) {
        node.x = _this.nodes[index].x
        node.y = _this.nodes[index].y
      })

      for (let entity of res.entities) {
        let existed = false;
        for (let node of _this.graph.nodes) {
          if (node.id === entity.eid) {
            existed = true;
            break;
          }
        }
        if (!existed) {
          _this.graph.nodes.push({
            id: entity.eid,
            name: entity.name,
            x: entity.x,
            y: entity.y,
            category: entity.category,
            color: entity.color
          })
        }
      }

      for (let relation of res.relations) {
        let existed = false;
        for (let link of _this.graph.links) {
          if (link.id === relation.rid) {
            existed = true;
            break;
          }
        }
        if (!existed) {
          _this.graph.links.push({
            id: relation.rid,
            name: relation.name,
            source: relation.theFirstEid,
            target: relation.theSecondEid,
          })
        }
      }

      _this.g.remove();
      _this.initKGData();
      this.initKG();
      this.$message({
        message: '图谱扩展成功！',
        type: 'success'
      });
    },

    // 准备图谱数据并触发cloneGraph事件
    cloneGraphData() {
      const _this = this
      _this.$emit("cloneGraph", _this.graph)
    },

    // 获取节点详情
    async getNodeDetail(d) {
      this.detail = await entityApi.getAnimationDetailApi(d.id)
      const div = document.getElementsByClassName('el-drawer__body')[0]
      div.style.background = "url(" + this.detail.jpg + ")"
    },

    //
    goToDetail(url) {
      window.open(url)
    },
  },
}
</script>

<style>
.kg-only-wrapper {
  background: rgba(255, 255, 255, 0.2);
  width: 1113px;
  height: 678px;
  box-shadow: 0px 0px 4px darkgrey inset;
  position: relative;
}

.el-drawer__body {
  position: relative;
  background: url("#");
  background-size: 100%;
  z-index: 1;
}

.el-drawer__body:after {
  content: "";
  width: 120%;
  height: 120%;
  position: absolute;
  left: -10%;
  top: -5%;
  background: inherit;
  background-size: cover;
  background-position: center;
  filter: blur(20px) brightness(75%);
  z-index: -1;
  float: left;
}
</style>
