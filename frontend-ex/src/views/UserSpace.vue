<template>
  <div @mouseup="handleDivMouseUp" @click="handleDivClick">
    <bg/>
    <header1></header1>
    <div class="div-main">
      <el-container>
        <el-aside width="250px">
          <h1>图文档列表</h1>
          <el-tree :data="treeData" :props="defaultProps" @node-click="handleTreeNodeClick"></el-tree>
        </el-aside>
        <el-divider direction="vertical"/>
        <el-main>
          <el-tabs v-model="currentTab.name" type="card" editable @edit="handleTabsEdit" @tab-click="handleTabClick"
                   @contextmenu.prevent="" v-show="(tabs.length !== 0)">
            <el-tab-pane v-for="item in tabs" :key="item.gid" :label="item.title" :name="item.name"
                         @contextmenu.prevent="">
              <!--新增节点-->
              <div class="div-edit" v-if="currentTab.isDivNodeCreateShow">
                <!--名称-->
                <div style="width: 5%;float:left;height: 80%;margin-right: 5px;margin-top: 0.5%;">
                  <span>名称 : </span>
                </div>
                <!--输入框-->
                <div style="width: 8%;float:left;height: 80%;margin-right: 20px;margin-top: 0.2%;">
                  <el-input v-model="nodePreset.name" size="mini"></el-input>
                </div>
                <!--形状-->
                <div style="width: 7%;float:left;height: 80%;margin-right: 20px;margin-top: 0.5%;">
                  <el-dropdown>
                    <span class="el-dropdown-link" style="color: black;">
                      {{ symbolEnum[nodePreset.symbol] }}<i class="el-icon-arrow-down el-icon--right"></i>
                    </span>
                    <template #dropdown>
                      <el-dropdown-menu>
                        <el-dropdown-item @click.native="nodePreset.symbol='circle'">圆形</el-dropdown-item>
                        <el-dropdown-item @click.native="nodePreset.symbol='rect'">方形</el-dropdown-item>
                        <el-dropdown-item @click.native="nodePreset.symbol='diamond'">菱形</el-dropdown-item>
                        <el-dropdown-item @click.native="nodePreset.symbol='arrow'">箭头</el-dropdown-item>
                        <el-dropdown-item @click.native="nodePreset.symbol='pin'">钉子</el-dropdown-item>
                        <el-dropdown-item @click.native="nodePreset.symbol='triangle'">三角形</el-dropdown-item>
                        <el-dropdown-item @click.native="nodePreset.symbol='roundRect'">圆角方形</el-dropdown-item>
                      </el-dropdown-menu>
                    </template>
                  </el-dropdown>
                </div>
                <!--大小-->
                <div style="width: 5%;float:left;height: 80%;margin-right: 20px;margin-top: 0.5%">
                  <el-dropdown>
                    <span class="el-dropdown-link" style="color: black;">
                      大小<i class="el-icon-arrow-down el-icon--right"></i>
                    </span>
                    <template #dropdown>
                      <el-dropdown-menu>
                        <div class="block">
                          <el-slider
                            v-model="nodePreset.symbolSize"
                            vertical
                            height="100px"
                            :min="15">
                          </el-slider>
                        </div>
                      </el-dropdown-menu>
                    </template>
                  </el-dropdown>
                </div>
                <!--颜色-->
                <div style="width: 5%;float:left;height: 80%;margin-right: 0;margin-top: 0.5%">
                  <span>颜色</span>
                </div>
                <!--颜色选择-->
                <div style="float:left;height: 80%;margin-right: 20px;margin-left: 0;">
                  <el-color-picker size="medium" v-model="nodePreset.itemStyle.color"></el-color-picker>
                </div>
                <!--标签大小-->
                <div style="width: 10%;float:left;height: 80%;margin-right: 10px;margin-top: 0.5%">
                  <el-dropdown>
                    <span class="el-dropdown-link" style="color: black;">
                      标签大小<i class="el-icon-arrow-down el-icon--right"></i>
                    </span>
                    <template #dropdown>
                      <el-dropdown-menu>
                        <div class="block">
                          <el-slider
                            v-model="nodePreset.label.fontSize"
                            vertical
                            height="100px"
                            :min="15">
                          </el-slider>
                        </div>
                      </el-dropdown-menu>
                    </template>
                  </el-dropdown>
                </div>
                <!--类型-->
                <div style="width: 5%;float:left;height: 80%;margin-right: 20px;margin-top: 0.5%">
                  <el-dropdown>
                    <span class="el-dropdown-link" style="color: black;">
                      {{ categoryEnum[nodePreset.category] }}<i
                      class="el-icon-arrow-down el-icon--right"></i>
                    </span>
                    <template #dropdown>
                      <el-dropdown-menu>
                        <el-dropdown-item @click.native="nodePreset.category=0">男性</el-dropdown-item>
                        <el-dropdown-item @click.native="nodePreset.category=1">女性</el-dropdown-item>
                      </el-dropdown-menu>
                    </template>
                  </el-dropdown>
                </div>
                <!--坐标-->
                <div style="width: 5%;float:left;height: 80%;margin-top: 0.5%">
                  <span>坐标 : </span>
                </div>
                <!--X输入框-->
                <div style="width: 8%;float:left;height: 80%;margin-top: 0.2%;">
                  <el-input v-model="nodePreset.x" size="mini"></el-input>
                </div>
                <!--逗号-->
                <div style="width: 1%;float:left;height: 80%;margin-right: 5px;margin-top: 0.5%">
                  <span>，</span>
                </div>
                <!--Y输入框-->
                <div style="width: 8%;float:left;height: 80%;margin-right: 20px;margin-top: 0.2%;">
                  <el-input v-model="nodePreset.y" size="mini"></el-input>
                </div>
                <!--确定按钮-->
                <div style="width: 5%;float:right;height: 80%;margin-top: 0.2%;margin-right:50px">
                  <el-button type="primary" plain size="mini" @click="handleNodeCreate">新增节点</el-button>
                </div>
              </div>
              <!--节点编辑-->
              <div class="div-edit" v-if="currentTab.isDivNodeEditShow">
                <!--名称-->
                <div style="width: 5%;float:left;height: 80%;margin-right: 5px;margin-top: 0.5%;">
                  <span>名称 : </span>
                </div>
                <!--输入框-->
                <div style="width: 8%;float:left;height: 80%;margin-right: 20px;margin-top: 0.2%;">
                  <el-input v-model="currentNode.name" size="mini"></el-input>
                </div>
                <!--形状-->
                <div style="width: 7%;float:left;height: 80%;margin-right: 20px;margin-top: 0.5%;">
                  <el-dropdown>
                    <span class="el-dropdown-link" style="color: black;">
                      {{ symbolEnum[currentNode.symbol] }}<i class="el-icon-arrow-down el-icon--right"></i>
                    </span>
                    <template #dropdown>
                      <el-dropdown-menu>
                        <el-dropdown-item @click.native="selectNodeShape('circle')">圆形</el-dropdown-item>
                        <el-dropdown-item @click.native="selectNodeShape('rect')">方形</el-dropdown-item>
                        <el-dropdown-item @click.native="selectNodeShape('diamond')">菱形</el-dropdown-item>
                        <el-dropdown-item @click.native="selectNodeShape('arrow')">箭头</el-dropdown-item>
                        <el-dropdown-item @click.native="selectNodeShape('pin')">钉子</el-dropdown-item>
                        <el-dropdown-item @click.native="selectNodeShape('triangle')">三角形</el-dropdown-item>
                        <el-dropdown-item @click.native="selectNodeShape('roundRect')">圆角方形</el-dropdown-item>
                      </el-dropdown-menu>
                    </template>
                  </el-dropdown>
                </div>
                <!--大小-->
                <div style="width: 5%;float:left;height: 80%;margin-right: 20px;margin-top: 0.5%">
                  <el-dropdown>
                    <span class="el-dropdown-link" style="color: black;">
                      大小<i class="el-icon-arrow-down el-icon--right"></i>
                    </span>
                    <template #dropdown>
                      <el-dropdown-menu>
                        <div class="block">
                          <el-slider
                            v-model="currentNode.symbolSize"
                            vertical
                            height="100px"
                            :min="15">
                          </el-slider>
                        </div>
                      </el-dropdown-menu>
                    </template>
                  </el-dropdown>
                </div>
                <!--颜色-->
                <div style="width: 5%;float:left;height: 80%;margin-right: 0;margin-top: 0.5%">
                  <span>颜色</span>
                </div>
                <!--颜色选择-->
                <div style="float:left;height: 80%;margin-right: 20px;margin-left: 0;">
                  <el-color-picker size="medium" v-model="currentNode.itemStyle.color"></el-color-picker>
                </div>
                <!--标签大小-->
                <div style="width: 10%;float:left;height: 80%;margin-right: 10px;margin-top: 0.5%">
                  <el-dropdown>
                    <span class="el-dropdown-link" style="color: black;">
                      标签大小<i class="el-icon-arrow-down el-icon--right"></i>
                    </span>
                    <template #dropdown>
                      <el-dropdown-menu>
                        <div class="block">
                          <el-slider
                            v-model="currentNode.label.fontSize"
                            vertical
                            height="100px"
                            :min="15">
                          </el-slider>
                        </div>
                      </el-dropdown-menu>
                    </template>
                  </el-dropdown>
                </div>
                <!--类型-->
                <div style="width: 5%;float:left;height: 80%;margin-right: 20px;margin-top: 0.5%">
                  <el-dropdown>
                    <span class="el-dropdown-link" style="color: black;">
                      {{ categoryEnum[parseInt(currentNode.category)] }}<i
                      class="el-icon-arrow-down el-icon--right"></i>
                    </span>
                    <template #dropdown>
                      <el-dropdown-menu>
                        <el-dropdown-item @click.native="selectNodeCategory(0)">男性</el-dropdown-item>
                        <el-dropdown-item @click.native="selectNodeCategory(1)">女性</el-dropdown-item>
                      </el-dropdown-menu>
                    </template>
                  </el-dropdown>
                </div>
                <!--坐标-->
                <div style="width: 5%;float:left;height: 80%;margin-top: 0.5%">
                  <span>坐标 : </span>
                </div>
                <!--X输入框-->
                <div style="width: 8%;float:left;height: 80%;margin-top: 0.2%;">
                  <el-input v-model="currentNode.x" size="mini"></el-input>
                </div>
                <!--逗号-->
                <div style="width: 1%;float:left;height: 80%;margin-right: 5px;margin-top: 0.5%">
                  <span>，</span>
                </div>
                <!--Y输入框-->
                <div style="width: 8%;float:left;height: 80%;margin-right: 20px;margin-top: 0.2%;">
                  <el-input v-model="currentNode.y" size="mini"></el-input>
                </div>
                <!--确定按钮-->
                <div style="width: 5%;float:right;height: 80%;margin-top: 0.2%;">
                  <el-button icon="el-icon-check" size="mini" @click="currentTab.isDivNodeCreateShow=setTopDiv()"
                             circle/>
                </div>
              </div>
              <!--关系编辑-->
              <div class="div-edit" v-if="currentTab.isDivLinkEditShow">
                <!--名称-->
                <div style="width: 5%;float:left;height: 80%;margin-right: 5px;margin-top: 0.5%;">
                  <span>名称 : </span>
                </div>
                <!--输入框-->
                <div style="width: 8%;float:left;height: 80%;margin-right: 20px;margin-top: 0.2%;">
                  <el-input v-model="currentLink.value" size="mini"></el-input>
                </div>
                <!--颜色-->
                <div style="width: 5%;float:left;height: 80%;margin-right: 0;margin-top: 0.5%">
                  <span>颜色</span>
                </div>
                <!--颜色选择-->
                <div style="float:left;height: 80%;margin-right: 20px;margin-left: 0;">
                  <el-color-picker size="medium" v-model="currentLink.lineStyle.color"></el-color-picker>
                </div>
                <!--类型-->
                <div style="width: 10%;float:left;height: 80%;margin-right: 20px;margin-top: 0.5%;">
                  <el-dropdown>
                    <span class="el-dropdown-link" style="color: black;">
                      {{ lineTypeEnum[currentLink.lineStyle.type] }}<i class="el-icon-arrow-down el-icon--right"></i>
                    </span>
                    <template #dropdown>
                      <el-dropdown-menu>
                        <el-dropdown-item @click.native="currentLink.lineStyle.type='solid'">————</el-dropdown-item>
                        <el-dropdown-item @click.native="currentLink.lineStyle.type='dashed'">----------
                        </el-dropdown-item>
                        <el-dropdown-item @click.native="currentLink.lineStyle.type='dotted'">···················
                        </el-dropdown-item>
                      </el-dropdown-menu>
                    </template>
                  </el-dropdown>
                </div>
                <!--确定按钮-->
                <div style="width: 5%;float:right;height: 80%;margin-top: 0.2%;">
                  <el-button icon="el-icon-check" size="mini" @click="currentTab.isDivNodeCreateShow=setTopDiv()"
                             circle/>
                </div>
              </div>
              <!--搜索框-->
              <div class="div-edit" v-if="currentTab.isDivSearchShow">
                <!--输入框-->
                <div style="width: 60%;float:left;height: 80%;margin-right: 3%;">
                  <el-autocomplete
                    class="inline-input"
                    v-model="currentTab.searchContent"
                    :fetch-suggestions="searchHistorySuggest"
                    placeholder="请输入内容"
                    :clearable=true
                    :debounce=0
                    prefix-icon="el-icon-search"
                    @select="handleSearchEnter"
                    @keyup.enter.native="handleSearchEnter"
                    style="width: 100%; float:left"
                  ></el-autocomplete>
                </div>
                <!--清空历史搜索记录-->
                <el-button type="text"
                           style="float:left;font-size: 15px;color: lightgrey;text-decoration:underline"
                           @click="currentTab.searchHistory=[]">
                  清空历史记录
                </el-button>
                <!--确定按钮-->
                <div style="width: 5%;float:right;height: 80%;margin-top: 0.2%;">
                  <el-button icon="el-icon-close" size="mini" @click="handleSearchClose"
                             circle/>
                </div>
              </div>
              <!--排版模式-->
              <div class="div-edit" v-show="!currentTab.mode" style="position: relative">
                <h1>排版模式</h1>
                <div style="position: absolute;bottom: 0px;right: 0px">ps:排版模式下无法修改图谱</div>
              </div>
              <div style="position:relative" @contextmenu.prevent="">
                <!--统计数据-->
                <div class="div-statistics" :id="item.title + '-statistics'">
                  <div>节点统计：{{ currentTab.nodeNum }}</div>
                  <div>关系统计：{{ currentTab.linkNum }}</div>
                </div>
                <!--图层-->
                <div class="div-graph" :id="item.title + '-graph'" v-show="currentTab.mode"
                     @mousemove="handleGraphMouseMove"/>
                <!--排版模式-->
                <div class="div-graph" :id="item.title + '-composition'" v-show="!currentTab.mode">
                </div>
                <!--右边菜单栏-->
                <el-menu :collapse="true">
                  <el-menu-item index="4" @click="handleGraphSwitch">
                    <i class="el-icon-s-operation"></i>
                    <span slot="title">{{ getGraphSwitchTitle() }}</span>
                  </el-menu-item>
                  <el-menu-item index="5" @click="handleGraphSearch">
                    <i class="el-icon-search"></i>
                    <span slot="title">搜索</span>
                  </el-menu-item>
                  <el-menu-item index="2" @click="handleGraphRestore">
                    <i class="el-icon-refresh"></i>
                    <span slot="title">还原位置</span>
                  </el-menu-item>
                  <el-menu-item index="3" @click="handleGraphSave">
                    <i class="el-icon-finished"></i>
                    <span slot="title">保存图谱</span>
                  </el-menu-item>
                  <el-submenu index="1">
                    <template slot="title">
                      <i class="el-icon-download"></i>
                    </template>
                    <el-menu-item-group>
                      <template slot="title">文件</template>
                      <el-menu-item index="1-1" @click="handleExportFile('json')">JSON</el-menu-item>
                      <el-menu-item index="1-2" @click="handleExportFile('xml')">XML</el-menu-item>
                    </el-menu-item-group>
                    <el-divider style="margin-top: 0;"/>
                    <el-menu-item-group>
                      <template slot="title">图片</template>
                      <el-menu-item index="1-3" @click="handleExportFile('png')">PNG</el-menu-item>
                      <el-menu-item index="1-4" @click="handleExportFile('jpg')">JPG</el-menu-item>
                    </el-menu-item-group>
                  </el-submenu>
                  <el-menu-item index="6" @click="handleGraphLabelShow">
                    <img :src="require('../assets/View.png')" style="height: 20px;width: 20px"/>
                    <span slot="title">切换标签显示</span>
                  </el-menu-item>
                </el-menu>
              </div>
              <!--节点右键菜单-->
              <div class="div-contextmenu" :id="item.title + '-node'" v-show="isDivNodeShow" @contextmenu.prevent="">
                <ul>
                  <li @click="handleLinkCreate">新建关系</li>
                  <el-divider class="divider-options"/>
                  <li @click="handleNodeDelete">删除</li>
                </ul>
              </div>
              <!--关系右键菜单-->
              <div class="div-contextmenu" :id="item.title + '-link'" v-show="isDivLinkShow" @contextmenu.prevent=""
                   @click.prevent="">
                <ul>
                  <li @click="handleLinkDelete">删除</li>
                  <el-divider class="divider-options"/>
                  <li @click="handleLinkLabelSwitch">标签</li>
                </ul>
              </div>
              <!--连线形成关系-->
              <div class="div-linking" :id="item.title + '-line'">
                <div class="div-triangle" :id="item.title + '-triangle'"/>
              </div>
              <!--修改值的文本框-->
              <label><input class="div-text" v-model="input.value" :id="item.title + '-text'" v-show="isDivTextShow"
                            @contextmenu.prevent="" @keydown.enter="handleInputEnter"/></label>
            </el-tab-pane>
          </el-tabs>
        </el-main>
      </el-container>
    </div>
  </div>
</template>

<script>
import Background from "../components/Background";
import Header1 from "../components/Header1";
import * as echarts from 'echarts'
import * as graphApi from '../api/graphApi'
import * as entityApi from '../api/entityApi'
import * as relationApi from '../api/relationApi'
// import {watch} from "vue"

export default {
  name: "UserSpace",
  components: {
    bg: Background,
    header1: Header1,
  },
  data() {
    return {
      count: 0,
      treeData: [],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      // 标签页数据
      tabs: [
        // {
        //   title: graph.gname,
        //   gid: graph.gid,
        //   mode: true,
        //   dirty: false,
        //   name: graph.gid.toString(),
        // }
      ],
      currentTab: {
        index: -1,
        name: null,
        title: null,
        dirty: false,
        gid: null,
        path: null,
        uid: null,
        mode: true,
        nodeNum: 0,
        linkNum: 0,
        isDivNodeCreateShow: true,
        isDivNodeEditShow: true,
        isDivLinkEditShow: false,
        isDivSearchShow: false,
        searchContent: "",
        searchHistory: [],
        isLabelShow: true
      },
      // 节点数据
      currentNode: {
        id: null,
        index: -1,
        name: null,
        category: null,
        x: null,
        y: null,
        symbol: null,
        symbolSize: -1,
        itemStyle: {
          color: null,
        },
        label: {
          fontSize: -1
        },
        gid: null,
        cx: null,
        cy: null,
      },
      // 当前选中关系
      currentLink: {
        id: null,
        index: -1,
        source: null,
        target: null,
        gid: null,
        value: "",
        label: {
          show: true,
        },
        lineStyle: {
          color: null,
          type: null
        },
      },
      // 从一个节点拖拽到另一个节点的连线所需的数据
      drag: {
        toLink: false,
        startNode: null,
        startX: 0,
        startY: 0,
        endNode: null,
        endX: 0,
        endY: 0,
        lenX: 0,
        lenY: 0,
        lenZ: 0,
        angle: 0
      },
      // 节点菜单、关系菜单和文本修改是否显示
      isDivNodeShow: false,
      isDivLinkShow: false,
      isDivTextShow: false,
      // 双击修改节点或关系内容所需要的数据
      input: {
        index: -1,
        value: "",
        type: null,
        currentData: null,
      },
      // 节点编辑需要的数据
      nodeShape: "",
      symbolEnum: {
        'circle': "圆形",
        'rect': "方形",
        'roundRect': "圆角方形",
        'triangle': "三角形",
        'diamond': "菱形",
        'pin': "钉子",
        'arrow': "箭头"
      },
      categoryEnum: ["男性", "女性"],
      // 预设值
      nodePreset: {
        name: "新节点",
        category: 0,
        x: 0,
        y: 0,
        symbol: "circle",
        symbolSize: 15,
        itemStyle: {
          color: "#000000",
        },
        label: {
          fontSize: 15
        },
      },
      lineTypeEnum: {
        "solid": "————",
        "dashed": "------------",
        "dotted": "············",
      }
    }
  },
  methods: {
    load() {
      this.count = this.count + 2
    },
    setCurrentNode(param) {
      const _this = this
      _this.currentNode.id = param.data.id
      _this.currentNode.index = param.dataIndex
      _this.currentNode.name = param.data.name
      _this.currentNode.category = param.data.category
      _this.currentNode.x = param.data.x
      _this.currentNode.y = param.data.y
      _this.currentNode.symbol = param.data.symbol
      _this.currentNode.symbolSize = param.data.symbolSize
      _this.currentNode.itemStyle.color = param.data.itemStyle.color
      _this.currentNode.label.fontSize = param.data.label.fontSize
      _this.currentNode.gid = param.data.gid
      _this.currentNode.cx = param.data.cx
      _this.currentNode.cy = param.data.cy
    },
    setCurrentLink(param) {
      const _this = this
      _this.currentLink.index = param.dataIndex
      _this.currentLink.source = param.data.source
      _this.currentLink.target = param.data.target
      _this.currentLink.value = param.data.value
      _this.currentLink.id = param.data.id
      _this.currentLink.gid = param.data.gid
      _this.currentLink.label.show = param.data.label.show
      _this.currentLink.lineStyle.color = param.data.lineStyle.color
      _this.currentLink.lineStyle.type = param.data.lineStyle.type
    },
    setCurrentTab(param) {
      const _this = this
      _this.currentTab.name = param.name
      _this.currentTab.title = param.title
      _this.currentTab.dirty = param.dirty
      _this.currentTab.mode = param.mode
      _this.currentTab.gid = param.gid
      _this.currentTab.path = param.path
      _this.currentTab.uid = param.uid
      _this.currentTab.nodeNum = param.nodeNum
      _this.currentTab.linkNum = param.linkNum
      _this.currentTab.isDivNodeCreateShow = param.isDivNodeCreateShow
      _this.currentTab.isDivNodeEditShow = param.isDivNodeEditShow
      _this.currentTab.isDivLinkEditShow = param.isDivLinkEditShow
      _this.currentTab.isDivSearchShow = param.isDivSearchShow
      _this.currentTab.searchContent = param.searchContent
      _this.currentTab.searchHistory = param.searchHistory
      _this.currentTab.isLabelShow = param.isLabelShow
    },
    setOption(gname, nodes, links) {
      return {
        title: {
          text: gname,
          top: "5px",
          left: "5px",
          textStyle: {
            fontSize: "24px",
          }
        },
        legend: {
          show: true,
          top: "43px",
          left: "5px",
          orient: "vertical",
          data: [
            "男性",
            "女性"
          ],
        },
        series: [
          {
            type: "graph",
            layout: "none",
            circular: {
              rotateLabel: true
            },
            animation: false,
            emphasis: {
              scale: false,
            },
            categories: [
              {
                name: "男性",
                itemStyle: {
                  color: "#000000"
                },
              },
              {
                name: "女性",
                itemStyle: {
                  color: "#FF0000"
                }
              }
            ],
            edgeLabel: {
              position: "middle",
              show: true,
              fontSize: 12,
              formatter: "{c}"
            },
            edgeSymbol: [
              "",
              "arrow"
            ],
            data: nodes,
            links: links,
            autoCurveness: true,
            lineStyle: {
              opacity: 0.9,
              width: 2,
            },
            roam: true,
            nodeScaleRatio: 0,
          },
        ],
        // tooltip: [
        //   {
        //     trigger: 'item',
        //     shadowBlur: 10,
        //     shadowColor: 'rgba(0, 0, 0, .2)',
        //     textStyle: {
        //       color: '#666',
        //       fontSize: 10,
        //     },
        //   }
        // ],
      }
    },
    formatNode2Back(node) {
      return {
        name: node.name,
        category: node.category,
        x: node.x,
        y: node.y,
        symbol: node.symbol,
        symbolSize: node.symbolSize,
        color: node.itemStyle.color,
        labelFontSize: node.label.fontSize,
        borderColor: null,
        borderWidth: null,
        eid: node.id,
        gid: node.gid,
        cx: node.cx,
        cy: node.cy,
      }
    },
    formatLink2Back(link) {
      return {
        rid: link.id,
        theFirstEid: parseInt(link.source),
        theSecondEid: parseInt(link.target),
        name: link.value,
        gid: link.gid,
        color: link.lineStyle.color,
        type: link.lineStyle.type,
        lineWidth: null,
        lineType: null,
        labelFontSize: null
      }
    },
    setTopDiv() {
      const _this = this
      _this.currentTab.isDivNodeCreateShow = false
      _this.currentTab.isDivNodeEditShow = false
      _this.currentTab.isDivLinkEditShow = false
      _this.currentTab.isDivSearchShow = false
      return true
    },
    setSearchHistory(value) {
      const _this = this;
      let isExist = false
      for (let i = 0; i < _this.currentTab.searchHistory.length; i++) {
        if (_this.currentTab.searchHistory[i]["value"] === value) {
          isExist = true
        }
      }
      if (!isExist) {
        _this.currentTab.searchHistory.push({"value": value, "key": ""})
      }
    },

    //--------------------------------------------------------------------------------

    // 初始化图谱文件夹结构
    async initGraphDir() {
      const _this = this
      const graphs = await graphApi.getGraphsApi({
        data: {
          user: "admin"
        }
      })
      _this.treeData = []
      for (const graph of graphs) {
        let gid = graph.gid
        let gdir = graph.directory.split("/").slice(1)
        let gname = graph.name
        _this.treeData = locateGraph(_this.treeData, gid, gdir, gname)
      }

      function locateGraph(treeData, gid, gdir, gname) {
        if (gdir.length === 0) {
          treeData.push({
            label: gname,
            children: [],
            gid: gid
          })
        } else {
          let currentDir = gdir[0]
          let existence = false
          for (let i = 0; i < treeData.length; i++) {
            if (treeData[i].label === currentDir) {
              existence = true
              treeData[i].children = locateGraph(treeData[i].children, gid, gdir.slice(1), gname)
              break;
            }
          }
          if (!existence) {
            let newDir = {
              label: currentDir,
              children: locateGraph([], gid, gdir.slice(1), gname)
            }
            treeData.push(newDir)
          }
        }
        return treeData
      }
    },
    // 点击文件树当中的图
    async handleTreeNodeClick(node, nodeData, component) {
      const _this = this
      if (node.gid !== undefined) {
        // 该tab页已被打开
        for (let i = 0; i < _this.tabs.length; i++) {
          if (_this.tabs[i].gid === node.gid) {
            _this.currentTab.index = i
            _this.currentTab.name = _this.tabs[i].name
            _this.currentTab.title = _this.tabs[i].title
            return
          }
        }
        // 该tab页未被打开，模拟从后端获取图数据
        const res = await graphApi.getGraphByIdApi(node.gid)
        let newTab = {
          title: res.name,
          gid: res.gid,
          mode: true,
          dirty: false,
          name: res.gid.toString(),
          uid: res.uid,
          path: res.directory,
          nodeNum: res.entities.length,
          linkNum: res.relations.length,
          isDivNodeCreateShow: true,
          isDivNodeEditShow: false,
          isDivLinkEditShow: false,
          isDivSearchShow: false,
          searchContent: "",
          searchHistory: [],
          isLabelShow: true,
        }
        _this.tabs.push(newTab)
        _this.currentTab.index = _this.tabs.length - 1
        _this.setCurrentTab(newTab)
        await _this.$forceUpdate()
        _this.initGraph(res.name, res.entities, res.relations)
      }
    },
    // 初始化标签页的力导图
    initGraph(gname, nodes, links) {
      const _this = this
      const title = _this.currentTab.title
      let chart = echarts.init(document.getElementById(title + "-graph"))
      let option = _this.setOption(gname, nodes.map(function (node) {
        return formatNodes(node)
      }), links.map(function (link) {
        return formatLinks(link)
      }))
      let chart2 = echarts.init(document.getElementById(title + "-composition"))
      chart.setOption(option)
      chart.on("contextmenu", {dataType: "node"}, _this.handleNodeContextMenu)
      chart.on("contextmenu", {dataType: "edge"}, _this.handleLinkContextMenu)
      chart.on("dblclick", {dataType: "node"}, function (param) {
        _this.handleDblclick(param)
      })
      chart.on("dblclick", {dataType: "edge"}, function (param) {
        _this.handleDblclick(param)
      })
      chart.on("click", {dataType: "node"}, _this.handleNodeClick)
      chart.on("click", {dataType: "edge"}, _this.handleLinkClick)
      _this.initDraggable()

      // 从后端格式化数据到前端
      function formatNodes(node) {
        return {
          id: node.eid,
          name: node.name,
          category: parseInt(node.category),
          x: node.x,
          y: node.y,
          symbol: node.symbol,
          symbolSize: node.symbolSize,
          itemStyle: {
            color: node.color,
          },
          label: {
            show: true,
            fontSize: node.labelFontSize
          },
          gid: node.gid,
          cx: node.cx,
          cy: node.cy
        }
      }

      // 从前端格式化数据的后端
      function formatLinks(link) {
        return {
          id: link.rid,
          value: link.name,
          source: JSON.stringify(link.theFirstEid),
          target: JSON.stringify(link.theSecondEid),
          gid: link.gid,
          label: {
            show: true,
          },
          lineStyle: {
            type: link.type,
            color: link.color,
          },
        }
      }
    },
    // 初始化: 使用图形元素组件在节点上划出graphic层覆盖住节点
    initDraggable() {
      const _this = this
      const title = _this.currentTab.title
      let myChart = echarts.getInstanceByDom(document.getElementById(title + "-graph"))
      let option = myChart.getOption()
      option.graphic = []
      myChart.setOption(option, true)
      myChart.setOption({
        graphic: echarts.util.map(option.series[0].data, function (item, dataIndex) {
          let tmpPos = myChart.convertToPixel({'seriesIndex': 0}, [item.x, item.y]);
          return {
            type: 'circle',
            id: dataIndex,
            x: tmpPos[0],
            y: tmpPos[1],
            shape: {
              cx: 0,
              cy: 0,
              r: 7
            },
            invisible: true,
            draggable: true,
            ondrag: echarts.util.curry(_this.onPointDragging, dataIndex),
            z: 10000,
            cursor: "move",
          };
        })
      });
      // myChart.on('dataZoom', _this.updatePosition);
      myChart.on('graphRoam', _this.updatePosition)
    },
    // 根据graphic图层重定位
    updatePosition() {
      const _this = this
      const title = _this.currentTab.title
      let myChart = echarts.getInstanceByDom(document.getElementById(title + "-graph"))
      let option = myChart.getOption()
      myChart.setOption({
        graphic: echarts.util.map(option.series[0].data, function (item) {
          let tmpPos = myChart.convertToPixel({'seriesIndex': 0}, [item.x, item.y]);
          return {
            x: tmpPos[0],
            y: tmpPos[1]
          };
        })
      });
    },
    // 拖拽graphic图层
    onPointDragging(dataIndex, param) {
      const _this = this
      const title = _this.currentTab.title
      let myChart = echarts.getInstanceByDom(document.getElementById(title + "-graph"))
      let option = myChart.getOption()
      let tmpPos = myChart.convertFromPixel({'seriesIndex': 0}, [param.target.x, param.target.y]);
      option.series[0].data[dataIndex].x = tmpPos[0];
      option.series[0].data[dataIndex].y = tmpPos[1];
      myChart.setOption(option);
      _this.initDraggable()
    },
    // 单击tab页
    handleTabClick(tabInstance) {
      const _this = this
      const index = _this.currentTab.index
      _this.tabs[index].mode = _this.currentTab.mode
      _this.tabs[index].nodeNum = _this.currentTab.nodeNum
      _this.tabs[index].linkNum = _this.currentTab.linkNum
      _this.tabs[index].isDivNodeCreateShow = _this.currentTab.isDivNodeCreateShow
      _this.tabs[index].isDivNodeEditShow = _this.currentTab.isDivNodeEditShow
      _this.tabs[index].isDivLinkEditShow = _this.currentTab.isDivLinkEditShow
      _this.tabs[index].isDivSearchShow = _this.currentTab.isDivSearchShow
      _this.tabs[index].searchHistory = _this.currentTab.searchHistory
      _this.currentTab.index = parseInt(tabInstance.index)
      const tab = _this.tabs[_this.currentTab.index]
      _this.setCurrentTab(tab)
    },
    // 增删tab页
    handleTabsEdit(targetName, action) {
      const _this = this
      if (action === 'add') {
        //  TODO
        // let newname = ++this.index + '';
        // this.editableTabs.push({
        //   title: 'New Tab',
        //   name: newname,
        //   content: 'New Tab content'
        // });
        // this.editableTabsName = newname;
      }
      if (action === 'remove') {
        let tabs = this.tabs;
        let activeName = this.currentTab.name;
        if (activeName === targetName) {
          tabs.forEach((tab, index) => {
            if (tab.name === targetName) {
              let nextTab = tabs[index + 1] || tabs[index - 1];
              if (nextTab) {
                _this.setCurrentTab(nextTab)
                activeName = nextTab.name;
              }
            }
          });
        }
        this.tabs = tabs.filter(tab => tab.name !== targetName);
      }
    },
    // 右击节点
    handleNodeContextMenu(param) {
      const _this = this
      const title = _this.currentTab.title
      _this.isDivTextShow = false
      _this.isDivLinkShow = false

      _this.setCurrentNode(param)
      let nodeMenu = document.getElementById(title + '-node')
      nodeMenu.style.left = (param.event.offsetX) + "px"
      nodeMenu.style.top = (param.event.offsetY + 45) + "px"
      this.isDivNodeShow = true
    },
    // 新建关系
    handleLinkCreate() {
      const _this = this
      const title = _this.currentTab.title
      const chart = echarts.getInstanceByDom(document.getElementById(title + "-graph"))
      const currentNode = _this.currentNode
      let startPos = chart.convertToPixel({'seriesIndex': 0}, [currentNode.x, currentNode.y]);
      _this.drag.toLink = true
      _this.drag.startNode = currentNode.id
      _this.drag.startX = startPos[0]
      _this.drag.startY = startPos[1] + 45
      let div = document.getElementById(title + "-line")
      div.style.left = (_this.drag.startX) + "px"
      div.style.top = (_this.drag.startY) + "px"
      div.style.display = "unset"
    },
    // 鼠标在图中移动
    handleGraphMouseMove(event) {
      const _this = this
      if (_this.drag.toLink) {
        _this.drag.endX = event.offsetX
        _this.drag.endY = event.offsetY + 45
        _this.drag.angle = _this.getAngle()
        const title = _this.currentTab.title
        let line = document.getElementById(title + "-line")
        line.style.width = (_this.drag.lenZ - 20) + "px"
        line.style.transform = "rotate(" + _this.drag.angle + "deg)"
      }
    },
    // 获取红色箭头的角度
    getAngle() {
      const _this = this
      _this.drag.lenX = _this.drag.endX - _this.drag.startX
      _this.drag.lenY = _this.drag.endY - _this.drag.startY
      _this.drag.lenZ = Math.sqrt(_this.drag.lenX * _this.drag.lenX + _this.drag.lenY * _this.drag.lenY)
      if (_this.drag.lenX === 0 && _this.drag.lenY === 0) {
        return null
      } else if (_this.drag.lenX === 0) {
        if (_this.drag.lenY > 0) {
          return 90
        } else {
          return 270
        }
      } else if (_this.drag.lenY === 0) {
        if (_this.drag.lenX > 0) {
          return 0
        } else {
          return 180
        }
      } else {
        const sin = _this.drag.lenY / _this.drag.lenZ
        const rad = Math.asin(sin)
        const deg = (rad / Math.PI) * 180;
        if (_this.drag.lenX < 0) {
          return 180 - deg
        } else if (_this.drag.lenX > 0 && _this.drag.lenY < 0) {
          return 360 + deg
        } else {
          return deg
        }
      }
    },
    // 鼠标点击节点
    async handleNodeClick(param) {
      const _this = this
      const title = _this.currentTab.title
      let chart = echarts.getInstanceByDom(document.getElementById(title + "-graph"))
      let option = chart.getOption()
      if (_this.drag.toLink) {
        let endPos = chart.convertToPixel({'seriesIndex': 0}, [param.data.x, param.data.y]);
        _this.drag.endX = endPos[0]
        _this.drag.endY = endPos[1]
        _this.drag.endNode = param.data.id
        if (_this.drag.startNode !== _this.drag.endNode) {
          const res = await relationApi.addRelationApi(_this.formatLink2Back({
            id: -1,
            source: JSON.stringify(_this.drag.startNode),
            target: JSON.stringify(_this.drag.endNode),
            value: "新关系",
            gid: _this.currentTab.gid,
            lineStyle: {
              color: "#000000",
              type: "solid",
            },
          }))
          option.series[0].links.push({
            id: res,
            source: JSON.stringify(_this.drag.startNode),
            target: JSON.stringify(_this.drag.endNode),
            value: "新关系",
            gid: _this.currentTab.gid,
            label: {
              show: true,
            },
            lineStyle: {
              color: "#000000",
              type: "solid",
            },
          })
          _this.currentTab.linkNum++
          chart.setOption(option)
        }
        _this.drag.toLink = false
      } else {
        _this.setCurrentNode(param)
        _this.currentTab.isDivNodeEditShow = _this.setTopDiv()
      }
    },
    // 鼠标在页面上松开
    handleDivMouseUp(event) {
      const _this = this
      const title = _this.currentTab.title
      if (_this.drag.toLink) {
        let line = document.getElementById(title + "-line")
        line.style.display = "none"
        line.style.width = "0px"
      }
    },
    // 鼠标点击页面
    handleDivClick(event) {
      const mouseOn = event.path[0]["id"]
      const _this = this
      const title = _this.currentTab.title
      if (mouseOn !== title + '-link') {
        _this.isDivLinkShow = false
      }
      if (mouseOn !== title + '-node') {
        _this.isDivNodeShow = false
      }
      if (mouseOn !== title + '-text') {
        _this.isDivTextShow = false
      }
    },
    // 删除节点
    async handleNodeDelete() {
      const _this = this;
      const title = _this.currentTab.title
      let chart = echarts.getInstanceByDom(document.getElementById(title + "-graph"))
      let option = chart.getOption()
      const res = await entityApi.deleteEntityApi(_this.formatNode2Back(_this.currentNode))
      option.series[0].data = option.series[0].data.filter(node => node.id !== _this.currentNode.id)
      _this.currentTab.nodeNum--
      for (let i = 0; i < option.series[0].links.length; i++) {
        let link = option.series[0].links[i]
        if (parseInt(link.source) === _this.currentNode.id || parseInt(link.target) === _this.currentNode.id) {
          option.series[0].links.splice(i, 1)
          _this.currentTab.linkNum--
          i--
        }
      }
      chart.setOption(option)
      _this.initDraggable()
      _this.currentTab.isDivNodeCreateShow = _this.setTopDiv()
    },
    //鼠标点击搜索
    handleGraphSearch() {
      const _this = this
      if (!_this.currentTab.mode) {
        return
      }
      const title = _this.currentTab.title
      let chart = echarts.getInstanceByDom(document.getElementById(title + "-graph"))
      let option = chart.getOption()
      _this.currentTab.isDivSearchShow = _this.setTopDiv()
    },
    // 搜索框推荐
    searchHistorySuggest(queryString, cb) {
      const _this = this;
      let result = [];
      for (let i = 0; i < _this.currentTab.searchHistory.length; i++) {
        if (_this.currentTab.searchHistory[i]["value"].indexOf(queryString) === 0) {
          result.push({"value": _this.currentTab.searchHistory[i]["value"], "key": ""})
        }
      }
      cb(result)
    },
    // 搜索框回车
    handleSearchEnter() {
      const _this = this
      _this.setSearchHistory(_this.currentTab.searchContent)
      if (_this.currentTab.searchContent === "") {
        this.$notify.error({
          title: '错误',
          message: '请输入',
        });
        return
      }
      const title = _this.currentTab.title
      let chart = echarts.getInstanceByDom(document.getElementById(title + "-graph"))
      let option = chart.getOption()
      const reg = new RegExp(_this.currentTab.searchContent);
      option.series[0].data = option.series[0].data.map(function (node) {
        if (reg.test(node.name)) {
          node.label.backgroundColor = "yellow"
          node.label.borderColor = "yellow"
          node.label.borderWidth = 5
          node.label.color = "black"
        } else {
          if (node.label.backgroundColor !== undefined) {
            delete node.label.backgroundColor
          }
          if (node.label.borderColor !== undefined) {
            delete node.label.borderColor
          }
          if (node.label.borderColor !== undefined) {
            delete node.label.borderWidth
          }
          if (node.label.color !== undefined) {
            delete node.label.color
          }
        }
        return node
      })
      option.series[0].links = option.series[0].links.map(function (link) {
        if (reg.test(link.value)) {
          link.label.backgroundColor = "yellow"
          link.label.borderColor = "yellow"
          link.label.borderWidth = 5
          link.label.color = "black"
        } else {
          if (link.label.backgroundColor !== undefined) {
            delete link.label.backgroundColor
          }
          if (link.label.borderColor !== undefined) {
            delete link.label.borderColor
          }
          if (link.label.borderColor !== undefined) {
            delete link.label.borderWidth
          }
          if (link.label.color !== undefined) {
            delete link.label.color
          }
        }
        return link
      })
      chart.setOption(option)
      // if (_this.searchType === "请选择") {
      //   this.$notify.error({
      //     title: '错误',
      //     message: '请选择搜索范围',
      //   });
      //   return
      // }
      // const title = _this.currentTab.title
      // let graph = echarts.getInstanceByDom(document.getElementById(title + "-graph"))
      // let option = graph.getOption()
      // const reg = new RegExp(_this.searchContent);
      // switch (_this.searchType) {
      //   case '节点': {
      //     let nodes = []
      //     for (let i = 0; i < option.series[0].data.length; i++) {
      //       if (reg.test(option.series[0].data[i].name)) {
      //         nodes.push(i)
      //       }
      //       graph.dispatchAction({
      //         type: 'highlight',
      //         seriesIndex: 0,
      //         dataIndex: nodes,
      //       })
      //     }
      //     break;
      //   }
      //   case '关系': {
      //     let linkNameList = [];
      //     option.series[0].links.map(function (item) {
      //       linkNameList.push(item.name);
      //     });
      //     let res = [];
      //     for (let i = 0; i < linkNameList.length; i++) {
      //       if (reg.test(linkNameList[i])) {
      //         res.push(linkNameList[i]);
      //       }
      //     }
      //     // console.log(res)
      //     break;
      //   }
      //   case '类型': {
      //
      //     break;
      //   }
      // }
    },
    // 选择搜索类型
    // selectSearchType(type) {
    //   const _this = this;
    //   switch (type) {
    //     case 'node': {
    //       _this.searchType = "节点";
    //       break;
    //     }
    //     case 'link': {
    //       _this.searchType = "关系";
    //       break;
    //     }
    //     case 'category': {
    //       _this.searchType = "类型";
    //       break;
    //     }
    //   }
    // },
    // 右击关系
    handleLinkContextMenu(param) {
      const _this = this
      const title = _this.currentTab.title
      _this.isDivTextShow = false
      _this.isDivNodeShow = false

      _this.setCurrentLink(param)
      let linkMenu = document.getElementById(title + '-link')
      linkMenu.style.left = (param.event.offsetX) + "px"
      linkMenu.style.top = (param.event.offsetY + 45) + "px"
      this.isDivLinkShow = true
    },
    // 删除关系
    async handleLinkDelete() {
      const _this = this;
      const title = _this.currentTab.title
      let chart = echarts.getInstanceByDom(document.getElementById(title + "-graph"))
      let option = chart.getOption()
      const res = await relationApi.deleteRelationApi(_this.formatLink2Back(_this.currentLink))
      option.series[0].links.splice(_this.currentLink.index, 1)
      _this.currentTab.linkNum--
      chart.setOption(option)
    },
    // 双击节点或关系
    handleDblclick(param) {
      const _this = this
      const title = _this.currentTab.title
      _this.isDivLinkShow = false
      _this.isDivNodeShow = false
      let text = document.getElementById(title + "-text")
      text.style.top = (param.event.offsetY + 45) + "px"
      text.style.left = param.event.offsetX + "px"
      _this.input.type = param.dataType
      _this.input.index = param.dataIndex
      if (_this.input.type === "node") {
        _this.setCurrentNode(param)
        _this.input.currentData = _this.currentNode
        _this.input.value = _this.input.currentData.name
      } else {
        _this.setCurrentLink(param)
        _this.input.currentData = _this.currentLink
        _this.input.value = _this.input.currentData.value
      }
      _this.isDivTextShow = true
    },
    // 输入框中敲入回车
    handleInputEnter(event) {
      const _this = this
      const title = _this.currentTab.title
      let graph = echarts.getInstanceByDom(document.getElementById(title + "-graph"))
      let option = graph.getOption()
      if (_this.input.type === "node") {
        option.series[0].links = option.series[0].links.map(function (link) {
          if (link.source === _this.input.currentData.name) {
            link.source = _this.input.value
          }
          if (link.target === _this.input.currentData.name) {
            link.target = _this.input.value
          }
          return link
        })
        option.series[0].data[_this.input.index].name = _this.input.value
      } else {
        option.series[0].links[_this.input.index].value = _this.input.value
      }
      graph.setOption(option)
      _this.input.value = ""
      _this.input.index = -1
      _this.input.currentData = null
      let text = document.getElementById(title + "-text")
      _this.isDivTextShow = false
    },
    // 导出文件
    handleExportFile(format) {
      // 日后增加登录功能时候，保存文件的路径要相应改变。可能为(`/UserId/xxx.xxx`)
      const _this = this
      if (!_this.currentTab.mode) {
        return
      }
      const title = _this.currentTab.title
      const chart = echarts.getInstanceByDom(document.getElementById(title + "-graph"))
      const option = chart.getOption()
      switch (format) {
        case 'json': {
          let jsonData = {
            gname: _this.currentTab.title,
            data: option.series[0].data.map(function (node) {
              return _this.formatNode2Back(node)
            }),
            links: option.series[0].links.map(function (link) {
              return _this.formatLink2Back(link)
            })
          }
          let dataJSON = JSON.stringify(jsonData, null, "  ")
          let eleLink1 = document.createElement('a')
          eleLink1.download = `${jsonData.gname}.json`
          eleLink1.style.display = 'none'
          let blob1 = new Blob([dataJSON])
          eleLink1.href = URL.createObjectURL(blob1)
          document.body.appendChild(eleLink1)
          eleLink1.click()
          document.body.removeChild(eleLink1)
          break
        }
        case 'xml': {
          const xml2js = require('xml2js')
          let builder = new xml2js.Builder()
          let xmlData = {
            gname: _this.currentTab.title,
            data: option.series[0].data.map(function (node) {
              return _this.formatNode2Back(node)
            }),
            links: option.series[0].links.map(function (link) {
              return _this.formatLink2Back(link)
            })
          }
          let dataXML = builder.buildObject(xmlData)
          let eleLink = document.createElement('a')
          eleLink.download = `${xmlData.gname}.xml`
          eleLink.style.display = 'none'
          let blob = new Blob([dataXML])
          eleLink.href = URL.createObjectURL(blob)
          document.body.appendChild(eleLink)
          eleLink.click()
          document.body.removeChild(eleLink)
          break
        }
        case 'png': {
          let eleLink3 = document.createElement('a')
          eleLink3.download = `${_this.currentTab.title}.png`
          eleLink3.style.display = 'none'
          eleLink3.href = chart.getDataURL({
            pixelRatio: 2,
            backgroundColor: '#fff'
          })
          document.body.appendChild(eleLink3)
          eleLink3.click()
          document.body.removeChild(eleLink3)
          break
        }
        case 'jpg': {
          let eleLink4 = document.createElement('a')
          eleLink4.download = `${_this.currentTab.title}.jpg`
          eleLink4.style.display = 'none'
          eleLink4.href = chart.getDataURL({
            pixelRatio: 2,
            backgroundColor: '#fff'
          })
          document.body.appendChild(eleLink4)
          eleLink4.click()
          document.body.removeChild(eleLink4)
          break
        }
      }
    },
    // 还原图谱位置
    handleGraphRestore() {
      const _this = this
      if (!_this.currentTab.mode) {
        return
      }
      const title = _this.currentTab.title
      let chart = echarts.getInstanceByDom(document.getElementById(title + "-graph"))
      const option = chart.getOption()
      chart.clear()
      chart.setOption(_this.setOption(option.title[0].text, option.series[0].data, option.series[0].links))
      _this.initDraggable()
    },
    // 保存当前图谱数据
    async handleGraphSave() {
      const _this = this
      if (!_this.currentTab.mode) {
        return
      }
      const title = _this.currentTab.title
      const chart = echarts.getInstanceByDom(document.getElementById(title + "-graph"))
      const option = chart.getOption()
      const res = graphApi.saveGraphApi({
        gid: _this.currentTab.gid,
        name: _this.currentTab.title,
        entities: option.series[0].data.map(function (node) {
          return _this.formatNode2Back(node)
        }),
        relations: option.series[0].links.map(function (link) {
          return _this.formatLink2Back(link)
        }),
        uid: _this.currentTab.uid,
        directory: _this.currentTab.path
      })
    },
    // 切换当前图谱显示模式
    async handleGraphSwitch() {
      const _this = this
      const title = _this.currentTab.title
      let chart1 = echarts.getInstanceByDom(document.getElementById(title + "-graph"))
      let option1 = chart1.getOption()
      let chart2 = echarts.getInstanceByDom(document.getElementById(title + "-composition"))
      _this.currentTab.mode = !_this.currentTab.mode
      _this.setTopDiv()
      _this.currentTab.isDivNodeCreateShow = _this.currentTab.mode
      if (!_this.currentTab.mode) {
        chart2.clear()
        let categoryCount = new Array(option1.series[0].categories.length).fill(0)
        option1.series[0].data = option1.series[0].data.map(function (node) {
          if (node.cx === -1) {
            node.x = 500 * parseInt(node.category)
          } else {
            node.x = node.cx
          }
          if (node.cy === -1) {
            node.y = 100 * categoryCount[parseInt(node.category)]
          } else {
            node.y = node.cy
          }
          categoryCount[parseInt(node.category)]++
          return node
        })
        delete option1.series[0].roam
        option1.series[0].links = option1.series[0].links.map(function (link) {
          function getCategory(eid) {
            for (let i = 0; i < option1.series[0].data.length; i++) {
              if (option1.series[0].data[i].id === eid) {
                return option1.series[0].data[i].category
              }
            }
          }

          if (getCategory(parseInt(link.source)) === getCategory(parseInt(link.target))) {
            link.lineStyle.curveness = -0.3
          }
          return link
        })
        chart2.setOption(option1)
        _this.initDraggable2()
      } else {
        let option2 = chart2.getOption()
        for (let i = 0; i < option1.series[0].data.length; i++) {
          for (let j = 0; j < option2.series[0].data.length; j++) {
            if (option1.series[0].data[i].id === option2.series[0].data[j].id) {
              option1.series[0].data[i].cx = option2.series[0].data[j].x
              option1.series[0].data[i].cy = option2.series[0].data[j].y
            }
          }
        }
        chart1.clear()
        chart1.setOption(_this.setOption(option1.title[0].text, option1.series[0].data, option1.series[0].links))
        _this.initDraggable()
      }
    },
    // 获取当前切换模式按钮的标题文本
    getGraphSwitchTitle() {
      const _this = this
      if (_this.currentTab.mode) {
        return "排版模式"
      } else {
        return "力导图模式"
      }
    },
    // 切换关系标签显示与否
    handleLinkLabelSwitch() {
      const _this = this
      const title = _this.currentTab.title
      const chart = echarts.getInstanceByDom(document.getElementById(title + "-graph"))
      const option = chart.getOption()
      option.series[0].links[_this.currentLink.index].label.show = !option.series[0].links[_this.currentLink.index].label.show
      option.series[0].links[_this.currentLink.index].emphasis.label.show = !option.series[0].links[_this.currentLink.index].emphasis.label.show
      chart.setOption(option)
    },
    // 新建节点
    async handleNodeCreate() {
      const _this = this
      const title = _this.currentTab.title
      let graph = echarts.getInstanceByDom(document.getElementById(title + "-graph"))
      let option = graph.getOption()
      const res = await entityApi.addEntityApi({
        name: _this.nodePreset.name,
        category: JSON.stringify(_this.nodePreset.category),
        x: _this.nodePreset.x,
        y: _this.nodePreset.y,
        symbol: _this.nodePreset.symbol,
        symbolSize: _this.nodePreset.symbolSize,
        color: _this.nodePreset.itemStyle.color,
        labelFontSize: _this.nodePreset.label.fontSize,
        borderColor: null,
        borderWidth: null,
        eid: null,
        gid: _this.currentTab.gid,
      })
      option.series[0].data.push({
        name: _this.nodePreset.name,
        category: _this.nodePreset.category,
        x: _this.nodePreset.x,
        y: _this.nodePreset.y,
        symbol: _this.nodePreset.symbol,
        symbolSize: _this.nodePreset.symbolSize,
        itemStyle: {
          color: _this.nodePreset.itemStyle.color,
        },
        label: {
          show: true,
          fontSize: _this.nodePreset.label.fontSize,
        },
        borderColor: null,
        borderWidth: null,
        id: res,
        gid: _this.currentTab.gid,
        cx: -1,
        cy: -1,
      })
      _this.currentTab.nodeNum++
      graph.setOption(option)
      _this.initDraggable()
    },
    // 单击关系
    handleLinkClick(param) {
      const _this = this
      _this.setCurrentLink(param)
      _this.currentTab.isDivLinkEditShow = _this.setTopDiv()
    },
    // 选择节点的形状
    selectNodeShape(shape) {
      const _this = this;
      switch (shape) {
        case 'circle': {
          _this.currentNode.symbol = "circle";
          break;
        }
        case 'rect': {
          _this.currentNode.symbol = "rect";
          break;
        }
        case 'diamond': {
          _this.currentNode.symbol = "diamond";
          break;
        }
        case 'pin': {
          _this.currentNode.symbol = "pin";
          break;
        }
        case 'arrow': {
          _this.currentNode.symbol = "arrow";
          break;
        }
        case 'triangle': {
          _this.currentNode.symbol = "triangle";
          break;
        }
        case 'roundRect': {
          _this.currentNode.symbol = "roundRect";
          break;
        }
      }
    },
    // 选择节点的类型
    selectNodeCategory(category) {
      this.currentNode.category = category
    },
    // 关闭搜索框
    handleSearchClose() {
      const _this = this
      const title = _this.currentTab.title
      let chart = echarts.getInstanceByDom(document.getElementById(title + "-graph"))
      let option = chart.getOption()
      option.series[0].data = option.series[0].data.map(function (node) {
        if (node.label.backgroundColor !== undefined) {
          delete node.label.backgroundColor
        }
        if (node.label.borderColor !== undefined) {
          delete node.label.borderColor
        }
        if (node.label.borderColor !== undefined) {
          delete node.label.borderWidth
        }
        if (node.label.color !== undefined) {
          delete node.label.color
        }
        return node
      })
      option.series[0].links = option.series[0].links.map(function (link) {
        if (link.label.backgroundColor !== undefined) {
          delete link.label.backgroundColor
        }
        if (link.label.borderColor !== undefined) {
          delete link.label.borderColor
        }
        if (link.label.borderColor !== undefined) {
          delete link.label.borderWidth
        }
        if (link.label.color !== undefined) {
          delete link.label.color
        }
        return link
      })
      chart.setOption(option)
      _this.currentTab.isDivNodeCreateShow = _this.setTopDiv()
    },
    // 初始化: 使用图形元素组件在节点上划出graphic层覆盖住节点
    initDraggable2() {
      const _this = this
      const title = _this.currentTab.title
      let myChart = echarts.getInstanceByDom(document.getElementById(title + "-composition"))
      let option = myChart.getOption()
      option.graphic = []
      myChart.setOption(option, true)
      myChart.setOption({
        graphic: echarts.util.map(option.series[0].data, function (item, dataIndex) {
          let tmpPos = myChart.convertToPixel({'seriesIndex': 0}, [item.x, item.y]);
          return {
            type: 'circle',
            id: dataIndex,
            x: tmpPos[0],
            y: tmpPos[1],
            shape: {
              cx: 0,
              cy: 0,
              r: 7
            },
            invisible: true,
            draggable: true,
            ondrag: echarts.util.curry(_this.onPointDragging2, dataIndex),
            ondragend: echarts.util.curry(_this.onPointDragging3, dataIndex),
            z: 10000,
            cursor: "move",
          };
        })
      });
      // myChart.on('dataZoom', _this.updatePosition);
      // myChart.on('graphRoam', _this.updatePosition2)
    },
    // 根据graphic图层重定位
    updatePosition2() {
      const _this = this
      const title = _this.currentTab.title
      let myChart = echarts.getInstanceByDom(document.getElementById(title + "-composition"))
      let option = myChart.getOption()
      myChart.setOption({
        graphic: echarts.util.map(option.series[0].data, function (item) {
          let tmpPos = myChart.convertToPixel({'seriesIndex': 0}, [item.x, item.y]);
          return {
            // x: tmpPos[0],
            y: tmpPos[1]
          };
        })
      });
    },
    // 拖拽graphic图层
    onPointDragging2(dataIndex, param) {
      const _this = this
      const title = _this.currentTab.title
      let myChart = echarts.getInstanceByDom(document.getElementById(title + "-composition"))
      let option = myChart.getOption()
      let tmpPos = myChart.convertFromPixel({'seriesIndex': 0}, [param.target.x, param.target.y]);
      // option.series[0].data[dataIndex].x = tmpPos[0];
      option.series[0].data[dataIndex].y = tmpPos[1];
      // let ys = option.series[0].data.filter((node => node.category === option.series[0].data[dataIndex].category)).map(node => node.y)
      // ys.sort(function (a, b) {
      //   return a - b
      // })
      // option.series[0].data = option.series[0].data.map(function (node) {
      //   if (node.category === option.series[0].data[dataIndex].category) {
      //     node.y = ys.indexOf(node.y) * 100
      //   }
      //   return node
      // })
      // console.log(ys)
      myChart.setOption(option);
      _this.initDraggable2()
    },
    // 拖拽graphic图层
    onPointDragging3(dataIndex, param) {
      const _this = this
      const title = _this.currentTab.title
      let myChart = echarts.getInstanceByDom(document.getElementById(title + "-composition"))
      let option = myChart.getOption()
      let tmpPos = myChart.convertFromPixel({'seriesIndex': 0}, [param.target.x, param.target.y]);
      // option.series[0].data[dataIndex].x = tmpPos[0];
      option.series[0].data[dataIndex].y = tmpPos[1];
      let ys = option.series[0].data.filter((node => node.category === option.series[0].data[dataIndex].category)).map(node => node.y)
      ys.sort(function (a, b) {
        return a - b
      })
      option.series[0].data = option.series[0].data.map(function (node) {
        if (node.category === option.series[0].data[dataIndex].category) {
          node.y = ys.indexOf(node.y) * 100
        }
        return node
      })
      // console.log(ys)
      myChart.setOption(option);
      _this.initDraggable2()
    },
    // 切换标签显示
    handleGraphLabelShow() {
      const _this = this
      const title = _this.currentTab.title
      let chart = echarts.getInstanceByDom(document.getElementById(title + "-graph"))
      let option = chart.getOption()
      _this.currentTab.isLabelShow = !_this.currentTab.isLabelShow
      for (let i = 0; i < option.series[0].links.length; i++) {
        option.series[0].links[i].label.show = _this.currentTab.isLabelShow
      }
      chart.setOption(option)
    },
  },
  mounted() {
    const _this = this
    _this.initGraphDir()
  },
  watch: {
    "currentNode.name"() {
      // console.log("currentNode.name");
      const _this = this;
      const title = _this.currentTab.title;
      let chart = echarts.getInstanceByDom(document.getElementById(title + "-graph"))
      let option = chart.getOption()
      const originName = option.series[0].data[_this.currentNode.index].name;
      option.series[0].data[_this.currentNode.index].name = _this.currentNode.name;
      option.series[0].links = option.series[0].links.map(function (link) {
        if (link.source === originName) {
          link.source = _this.currentNode.name
        }
        if (link.target === originName) {
          link.target = _this.currentNode.name
        }
        return link
      });
      chart.setOption(option)
    },
    "currentNode.symbol"() {
      // console.log("currentNode.symbol");
      const _this = this;
      const title = _this.currentTab.title;
      let chart = echarts.getInstanceByDom(document.getElementById(title + "-graph"))
      let option = chart.getOption()
      option.series[0].data[_this.currentNode.index].symbol = _this.currentNode.symbol;
      chart.setOption(option)
    },
    "currentNode.symbolSize"() {
      // console.log("currentNode.symbolSize");
      const _this = this;
      const title = _this.currentTab.title;
      let chart = echarts.getInstanceByDom(document.getElementById(title + "-graph"))
      let option = chart.getOption()
      option.series[0].data[_this.currentNode.index].symbolSize = _this.currentNode.symbolSize;
      chart.setOption(option)
    },
    "currentNode.itemStyle.color"() {
      // console.log("currentNode.itemStyle.color");
      const _this = this;
      const title = _this.currentTab.title;
      let chart = echarts.getInstanceByDom(document.getElementById(title + "-graph"))
      let option = chart.getOption()
      if (_this.currentNode.itemStyle.color !== null) {
        option.series[0].data[_this.currentNode.index].itemStyle.color = _this.currentNode.itemStyle.color;
      } else {
        option.series[0].data[_this.currentNode.index].itemStyle.color = option.series[0].categories[option.series[0].data[_this.currentNode.index].category].itemStyle.color
      }
      chart.setOption(option)
    },
    "currentNode.label.fontSize"() {
      // console.log("currentNode.label.fontSize");
      const _this = this;
      const title = _this.currentTab.title;
      let chart = echarts.getInstanceByDom(document.getElementById(title + "-graph"))
      let option = chart.getOption()
      option.series[0].data[_this.currentNode.index].label.fontSize = _this.currentNode.label.fontSize;
      chart.setOption(option)
    },
    "currentNode.x"() {
      // console.log("currentNode.x");
      const _this = this;
      const title = _this.currentTab.title;
      let chart = echarts.getInstanceByDom(document.getElementById(title + "-graph"))
      let option = chart.getOption()
      option.series[0].data[_this.currentNode.index].x = _this.currentNode.x;
      chart.setOption(option)
    },
    "currentNode.y"() {
      // console.log("currentNode.y");
      const _this = this;
      const title = _this.currentTab.title;
      let chart = echarts.getInstanceByDom(document.getElementById(title + "-graph"))
      let option = chart.getOption()
      option.series[0].data[_this.currentNode.index].y = _this.currentNode.y;
      chart.setOption(option)
    },
    "currentLink.value"() {
      // console.log("currentLink.value");
      const _this = this;
      const title = _this.currentTab.title;
      let chart = echarts.getInstanceByDom(document.getElementById(title + "-graph"))
      let option = chart.getOption()
      option.series[0].links[_this.currentLink.index].value = _this.currentLink.value;
      chart.setOption(option)
    },
    "currentNode.category"() {
      // console.log("currentNode.category");
      const _this = this;
      const title = _this.currentTab.title;
      let chart = echarts.getInstanceByDom(document.getElementById(title + "-graph"))
      let option = chart.getOption()
      option.series[0].data[_this.currentNode.index].category = _this.currentNode.category;
      option.series[0].data = option.series[0].data.map(function (node) {
        node.cy = -1
        node.cx = -1
        return node
      })
      chart.setOption(option)
    },
    "currentLink.lineStyle.color"() {
      const _this = this;
      const title = _this.currentTab.title;
      let chart = echarts.getInstanceByDom(document.getElementById(title + "-graph"))
      let option = chart.getOption()
      option.series[0].links[_this.currentLink.index].lineStyle.color = _this.currentLink.lineStyle.color;
      chart.setOption(option)
    },
    "currentLink.lineStyle.type"() {
      const _this = this;
      const title = _this.currentTab.title;
      let chart = echarts.getInstanceByDom(document.getElementById(title + "-graph"))
      let option = chart.getOption()
      option.series[0].links[_this.currentLink.index].lineStyle.type = _this.currentLink.lineStyle.type;
      chart.setOption(option)
    }
  },
}
</script>

<style scoped>
.el-container {
  width: 1400px;
}

.el-divider--vertical {
  display: inline-block;
  width: 1px;
  height: 795px;
  margin: 0 8px;
  vertical-align: middle;
  position: relative;
}

.el-menu--collapse {
  position: absolute;
  top: 0px;
  right: 0;
  background: whitesmoke;
  height: 674px;
  box-shadow: 0px 0px 2px darkgrey inset;
}

.el-tabs {
  /*margin: 10px;*/
  padding: 10px;
}

.el-tabs__content {
  /*width: 1000px;*/
  height: 680px;
}

.el-aside {
  height: 795px;
  background-color: aliceblue;
  border-radius: 7px;
  box-shadow: 0px 0px 5px darkgrey;
}

.el-tree {
  background-color: aliceblue;
}

.el-main {
  position: relative;
  width: 1286px;
  height: 795px;
  padding: 0;
  border-radius: 7px;
  box-shadow: 0px 0px 5px darkgrey;
  background-color: aliceblue;
}

.el-tab-pane {
  position: relative;
  width: 100%;
}

.div-main {
  width: 1400px;
  margin: auto;
  padding-bottom: 10px;;
}

.div-graph {
  width: 1050px;
  height: 674px;
  cursor: default;
  background-color: white;
  /*border: 2px solid whitesmoke;*/
  box-shadow: 0px 0px 4px darkgrey inset;
}

/*右键点击节点和关系*/
.div-contextmenu {
  /*位置*/
  position: absolute;
  top: 0;
  left: 0;
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

.div-contextmenu-edit {
  /*位置*/
  position: absolute;
  top: 0;
  left: 25%;
  border: 2px solid #409EFF;
  width: 500px;
  background-color: aliceblue;
}

.div-contextmenu ul {
  margin: 0;
  padding: 0;
  text-align: left;
  list-style-type: none;
}

.div-contextmenu ul li {
  padding: 4px 25px;
  font-size: 14px;
}

.div-contextmenu ul li:hover {
  background: #E7EBEE;
}

.div-contextmenu ul li a:link {
  color: #000;
  text-decoration: none;
}

.divider-options {
  margin: 4px 0px;
}

.div-linking {
  width: 0px;
  position: absolute;
  border-top: 2px solid red;
  top: 0px;
  left: 0px;
  transform: rotate(0deg);
  transform-origin: 0 50%;
  display: none;
}

.div-dialog {
  position: absolute;
  top: 25px;
  left: 25px;
}

.div-triangle {
  position: absolute;
  top: -7.05px;
  right: -2px;
  width: 0;
  height: 0;
  border-top: 6px solid transparent;
  border-left: 12px solid red;
  border-bottom: 6px solid transparent;
}

.div-text {
  position: absolute;
  top: 0px;
  left: 200px;
  width: 60px;
  height: 25px;
  font-size: 20px;
}

.div-statistics {
  position: absolute;
  top: 90px;
  left: 5px;
  padding: 5px
}

.div-edit {
  height: 35px;
  margin-bottom: 10px;
}
</style>
