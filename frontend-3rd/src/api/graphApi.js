import {axios} from '../utils/request'
import request from "request";

const prefix = '/api/graph'

const graphApi = {
  getById: prefix + '/getGraphById',
  save: prefix + '/saveGraph',
  generate: prefix + '/generateGraph',
}

// 通过图ID获取图数据
export function getGraphById(data) {
  return axios({
    url: `${graphApi.getById}/${data}`,
    data: request,
    method: 'get'
  })
}

// 保存图谱数据
export function saveGraph(data) {
  return axios({
    url: `${graphApi.save}`,
    method: 'post',
    data: data
  })
}

// 点击获取图谱后生成一张新图
export function generateGraphApi(data) {
  return axios({
    url: `${graphApi.generate}`,
    method: 'post',
    data: data,
  })
}

// 根据用户uid获取图列表
export function getGraphsByUidApi(data) {
  return axios({
    url: `/api/graph/getGraphs/${data}`,
    method: 'get',
    data: request,
  })
}

// 删除图
export function deleteGraphByGidApi(data) {
  return axios({
    url: `/api/graph/deleteGraphByGid/${data}`,
    method: 'post',
    data: request,
  })
}
