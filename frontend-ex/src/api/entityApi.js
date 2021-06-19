import {axios} from '../utils/request'
import request from "request";

const api = {
  graph: '/SEIII_Backend/api/graph',
  relation: '/SEIII_Backend/api/relation',
  entity: '/SEIII_Backend/api/entity',
}


export function addEntityApi(data) {
  return axios({
    url: `${api.entity}/addEntity`,
    method: 'post',
    data,
  })
}

export function deleteEntityApi(data) {
  return axios({
    url: `${api.entity}/deleteEntity`,
    method: 'post',
    data,
  })
}

export function getEntityByEidApi(data) {
  return axios({
    url: `${api.entity}/getEntityByEid/${data}`,
    method: 'get',
    data: request,
  })
}
