import {axios} from '../utils/request'
import request from "request";

const api = {
  graph: '/SEIII_Backend/api/graph',
  relation: '/SEIII_Backend/api/relation',
  entity: '/SEIII_Backend/api/entity',
}

export function getGraphsApi(data) {
  return axios({
    url: `${api.graph}/getGraphs`,
    method: 'get',
    data
  })
}

export function getGraphByIdApi(data) {
  return axios({
    url: `${api.graph}/getGraphById/${data}`,
    data: request,
    method: 'get'
  })
}

export function saveGraphApi(data) {
  return axios({
    url: `${api.graph}/saveGraph`,
    method: 'post',
    data
  })
}
