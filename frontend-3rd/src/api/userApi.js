import {axios} from '../utils/request'
import request from "request";

const prefix = "/SEIII_Backend/api/user"

const userApi = {
  search: prefix + "/search",
  answer: prefix + "/answer",
  graphList: prefix + "/graph",
  history: prefix + "//history",
  recommend: prefix + "/recommend",
}

// 语义搜索界面确认搜索
export function searchAckApi(data) {
  return axios({
    url: `${userApi.search}/ack/${data.uid}/${data.contents}`,
    method: 'get',
    data: request,
  })
}

export function searchForKeyApi(data) {
  return axios({
    url: `${userApi.search}/key/${data}`,
    method: 'get',
    data: request,
  })
}

// 智能问答界面确认问题输入
export function questionAck(data) {
  return axios({
    url: `${userApi.answer}/ack/${data}`,
    method: 'get',
    data: request,
  })
}

// 获得属于某位用户的图列表
export function getGraphListByUidApi(data) {
  return axios({
    url: `${userApi.graph}/getList/${data}`,
    method: "get",
    data: request,
  })
}

//
export function getSearchHistoryApi(data) {
  return axios({
    url: `${userApi.history}/ack/${data}`,
    method: 'get',
    data: request,
  })
}

//
export function getRecommendApi(data) {
  return axios({
    url: `${userApi.recommend}/ack/${data}`,
    method: 'get',
    data: request,
  })
}

//
export function clearSearchHistoryApi(data) {
  return axios({
    url: `${userApi.history}/clear/${data}`,
    method: 'post',
    data: request,
  })
}

//
export function deleteSearchHistoryApi(data) {
  return axios({
    url: `${userApi.history}/delete/${data.uid}/${data.key}`,
    method: 'post',
    data: request,
  })
}

//
export function searchByEidAckApi(data) {
  return axios({
    url: `${userApi.search}/ack1/${data.uid}/${data.eid}`,
    method: 'get',
    data: request,
  })
}
