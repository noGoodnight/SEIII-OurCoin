package com.example.demo.controller;

import com.example.demo.bl.GraphicService;
import com.example.demo.vo.GraphVO;
import com.example.demo.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:BlackQAQ
 * @Date:2021/3/14
 * @Description:The controller of graphicservice
 */
@RestController
@RequestMapping("/api/graph")
public class GraphicController {

    @Autowired
    GraphicService graphicService;
    /*@GetMapping("/addGraph")
    ResponseVO addGraph(GraphVO Graph){
        return ResponseVO.buildSuccess()
    }*/

    @PostMapping("/deleteGraphByGid/{gid}")
    ResponseVO deleteGraphByGid(@PathVariable int gid){
        return ResponseVO.buildSuccess(graphicService.deleteGraphByGid(gid));
    }

    @GetMapping("/getGraphs/{uid}")
    ResponseVO getGraphsByUid(@PathVariable int uid){
        return ResponseVO.buildSuccess(graphicService.getGraphsByUid(uid));
    }

    @GetMapping("/getGraphById/{gid}")
    ResponseVO getGraphByGid(@PathVariable("gid") Integer gid){
        System.out.println(gid);
        return ResponseVO.buildSuccess(graphicService.getGraphByGid(gid));
    }

    @PostMapping("/saveGraph")
    ResponseVO saveGraphByGid(@RequestBody GraphVO graphVO){
        int isSuccess=graphicService.saveGraph(graphVO);
        if(isSuccess==0){
            return ResponseVO.buildSuccess("保存成功！");
        }
        else if(isSuccess==1){
            return ResponseVO.buildFailure("数据错误！");
        }
        else{
            return ResponseVO.buildFailure("数据库错误！");
        }
    }

    @PostMapping("/generateGraph")
    ResponseVO generateGraph(@RequestBody GraphVO graphVO){
        int result= graphicService.generateGraph(graphVO);
        if(result==-1){
            ResponseVO.buildFailure("图数据错误！");
        }
        else if(result==-2){
            ResponseVO.buildFailure("数据库错误！");
        }
        return ResponseVO.buildSuccess(result);
    }
}
