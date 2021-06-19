const option = {
  title: {
    show: false,
    text: 'graph'
  },
  label: {
    show: true

  },
  tooltip: {
    pointer: 'shadow'
  },
  legend: {
    x: "",
    show: true,
    top: 'top',
    left: 'center',
    // orient: 'vertical',
    data: ["男性", "女性"]
  },
  series: [
    {
      type: 'graph',//关系图
      layout: 'force',//力导向图的布局
      symbolSize: 50,//节点大小
      // focusNodeAdjacency: true,//鼠标悬停在节点上时，会隐藏和当前节点非直接连接的节点
      categories: [{ //节点类别
        name: '男性',
        itemStyle: {
          color: "#009800",

        }
      }, {
        name: '女性',
        itemStyle: {
          color: "#4592FF",

        }
      }],
      label: { //节点名称
        show: true,
        fontSize: 20 //节点名称显示大小,

      },
      force: {
        repulsion: 1000 //节点之间的排斥力
      },
      edgeLabel: {
        position: 'middle',
        show: true,
        fontSize: 10,
        formatter: "{c}"
      },
      edgeSymbol: ['', 'arrow'],
      data: [{
        name: 'A',//节点名称
        category: 0,//节点类型
        draggable: true,//节点是否可拖动
      }, {
        name: 'B',
        category: 1,
        draggable: true,
      }, {
        name: 'C',
        category: 0,
        draggable: true,
      }, {
        name: 'D',
        category: 1,
        draggable: true,
      }, {
        name: 'E',
        category: 0,
        draggable: true,
      }, {
        name: 'F',
        category: 1,
        draggable: true,
      }, {
        name: 'G',
        category: 1,
        draggable: true,
      }, {
        name: 'H',
        category: 1,
        draggable: true,
      }],
      links: [{
        source: 0,//关系的起点
        target: 1,//关系的终点
        value: '夫妻'//关系类型
      }, {
        source: 0,
        target: 3,
        value: '父子'
      }, {
        source: 0,
        target: 5,
        value: '朋友'
      }, {
        source: 4,
        target: 5,
        value: '同事'
      }, {
        source: 2,
        target: 7,
        value: '夫妻'
      }, {
        source: 1,
        target: 7,
        value: '朋友'
      }, {
        source: 1,
        target: 4,
        value: '朋友'
      }, {
        source: 1,
        target: 6,
        value: '师生',
      }
      ],
      lineStyle: { //关系连接线的样式设置
        opacity: 0.9,//关系连接线的不透明度为0.9
        width: 3,//关系连接线的宽度
        curveness: 0.3//关系连接线的弯曲程度
      },
      zoom: 1.5,
      roam: true
    }
  ]
}
const nodeTemplate = {
  name: '新节点',
  category: '男性',
  draggable: true
}
const linkTemplate = {
  source: null,
  target: null,
  sourceName: '',
  targetName: '',
  value: '新关系'
}

export default {
  option,
  nodeTemplate,
  linkTemplate
}
