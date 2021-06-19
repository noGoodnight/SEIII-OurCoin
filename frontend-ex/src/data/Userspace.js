export let graphs = [
  {
    gid: 1,
    gname: "test-graph-1",
    nodes: [
      {
        name: "A",
        category: "男性",
        x: Math.random() * 1000,
        y: Math.random() * 1000,
        symbol: "roundRect",
        color: "blue",
        symbolSize: 50,
        labelFontSize: 20,
        // borderColer:null,
        // borderWidth:null,
      },
      {
        name: "B",
        category: "女性",
        x: Math.random() * 1000,
        y: Math.random() * 1000,
        symbol: "circle",
        color: "purple",
        symbolSize: 50,
        labelFontSize: 20,
      },
      {
        name: 'C',
        category: "女性",
        x: Math.random() * 1000,
        y: Math.random() * 1000,
        symbol: "rect",
        color: "green",
        symbolSize: 50,
        labelFontSize: 20,
      }
    ],
    links: [
      {
        source: 'A',
        target: 'B',
        value: '前夫妻',
        lineColor: "green",
        // lineWidth:null,
        // lineType:null,
        // lableFontSize:null,
      },
      {
        source: 'A',
        target: 'C',
        value: '现夫妻'
      }
    ],
    dirty: false,
  },
  {
    gid: 2,
    gname: "test-graph-2",
    nodes: [
      {
        name: "A",
        category: "男性",
        x: Math.random() * 1000,
        y: Math.random() * 1000,
        symbol: "roundRect",
        color: "blue",
        symbolSize: 50,
        labelFontSize: 20,
        // borderColer:null,
        // borderWidth:null,
      },
      {
        name: "B",
        category: "女性",
        x: Math.random() * 1000,
        y: Math.random() * 1000,
        symbol: "circle",
        color: "purple",
        symbolSize: 50,
        labelFontSize: 20,
      },
      {
        name: 'C',
        category: "女性",
        x: Math.random() * 1000,
        y: Math.random() * 1000,
        symbol: "rect",
        color: "green",
        symbolSize: 50,
        labelFontSize: 20,
      }
    ],
    links: [
      {
        source: 'A',
        target: 'B',
        value: '前夫妻'
      },
      {
        source: 'A',
        target: 'C',
        value: '现夫妻'
      }
    ],
    dirty: false,
  }
]
