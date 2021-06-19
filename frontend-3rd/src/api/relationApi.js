import {axios} from '../utils/request'
import request from "request";

const prefix = '/api/relation'

const relationApi = {
  del: prefix + '/deleteRelation',
  add: prefix + '/addRelation',
}

// 删除关系
export function deleteRelationApi(data) {
  return axios({
    url: `${relationApi.del}`,
    method: 'post',
    data,
  })
}

// 添加关系
export function addRelationApi(data) {
  return axios({
    url: `${relationApi.add}`,
    method: 'post',
    data,
  })
}
