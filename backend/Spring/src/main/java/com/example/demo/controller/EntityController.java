package com.example.demo.controller;

import com.example.demo.bl.EntityService;
import com.example.demo.po.Detail;
import com.example.demo.po.Relation;
import com.example.demo.vo.EntityVO;
import com.example.demo.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * @Author:BlackQAQ
 * @Date:2021/3/15
 * @Description:The controller of entityservice
 */
@RestController
@RequestMapping("/api/entity")
public class EntityController {
    @Autowired
    EntityService entityService;

    @PostMapping("/addEntity")
    ResponseVO addEntity(@RequestBody EntityVO entityVO){
        Integer result= entityService.addEntity(entityVO);
        if(result==-1) return ResponseVO.buildFailure("无节点信息！");
        else if(result==-2) return ResponseVO.buildFailure("节点类型错误！");
        else if(result==-3) return ResponseVO.buildFailure("无详细信息！");
        else return ResponseVO.buildSuccess(result);
    }

    @GetMapping("/searchEntities/{name}")
    ResponseVO searchEntities(@PathVariable("name") String name){
        ArrayList<EntityVO> results= entityService.searchEntities(name);
        if(results.isEmpty()) return ResponseVO.buildFailure("查询结果为空！");
        return ResponseVO.buildSuccess(results);
    }

    @PostMapping("/deleteEntity")
    ResponseVO deleteEntity(@RequestBody EntityVO entityVO){
        System.out.println(entityVO.toString());
        int isSuccess=entityService.deleteEntity(entityVO);
        if(isSuccess==1){
            return ResponseVO.buildFailure("删除失败！");
        }
        else
            return ResponseVO.buildSuccess("删除成功！");
    }

    @GetMapping("/getEntityByEid/{eid}")
    ResponseVO getEntityByEid(@PathVariable("eid") Integer eid){
        System.out.println(eid);
        return ResponseVO.buildSuccess(entityService.getEntityByEid(eid));
    }

    @GetMapping("/getDetailByEid/{eid}")
    ResponseVO getDetailByEid(@PathVariable("eid") Integer eid){
        Detail result= entityService.getDetailByEid(eid);
        if(result==null) return ResponseVO.buildFailure("No Detail!");
        else return ResponseVO.buildSuccess(result);
    }

    @GetMapping("/getDict")
    ResponseVO getAllEntityNames(){
        return ResponseVO.buildSuccess(entityService.getAllEntityNames());
    }
}
