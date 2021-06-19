import {axios} from '../utils/request'
import request from "request";

const api = {
  graph: '/SEIII_Backend/api/graph',
  relation: '/SEIII_Backend/api/relation',
  entity: '/SEIII_Backend/api/entity',
}

export function addRelationApi(data) {
  return axios({
    url: `${api.relation}/addRelation`,
    method: 'post',
    data,
  })
}

export function deleteRelationApi(data) {
  return axios({
    url: `${api.relation}/deleteRelation`,
    method: 'post',
    data,
  })
}

