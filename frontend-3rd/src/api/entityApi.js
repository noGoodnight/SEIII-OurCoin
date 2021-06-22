import {axios} from '../utils/request'
import request from "request";

const prefix = '/SEIII_Backend/api/entity'

const entityApi = {
  del: prefix + '/deleteEntity',
  search: prefix + '/searchEntities',
  add: prefix + '/addEntity',
  detail: prefix + '/getDetailByEid',
  dict: prefix + '/getDict',
}

// 删除节点
export function deleteEntityApi(data) {
  return axios({
    url: `${entityApi.del}`,
    method: 'post',
    data,
  })
}

// 搜索节点
export function searchEntityApi(data) {
  return axios({
    url: `${entityApi.search}/${data}`,
    method: 'get',
    data: request,
  })
}

// 克隆节点
export function addEntityApi(data) {
  return axios({
    url: `${entityApi.add}`,
    method: 'post',
    data: data
  })
}

// 获取番剧节点的详情
export function getAnimationDetailApi(data) {
  return axios({
    url: `${entityApi.detail}/${data}`,
    method: 'get',
    data: request,
  })
}

// 获取字典以输入补全
export function getDictApi() {
  return axios({
    url: `${entityApi.dict}`,
    method: 'get',
  })
}
