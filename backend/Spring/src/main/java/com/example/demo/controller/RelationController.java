package com.example.demo.controller;

import com.example.demo.bl.RelationService;
import com.example.demo.vo.RelationVO;
import com.example.demo.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author:BlackQAQ
 * @Date:2021/3/15
 * @Description:The controller of relationservice
 */
@RestController
@RequestMapping("/api/relation")
public class RelationController {
    @Autowired
    RelationService relationService;

    @PostMapping("/addRelation")
    ResponseVO addRelation(@RequestBody RelationVO relationVO){
        System.out.println(relationVO);
        return ResponseVO.buildSuccess(relationService.addRelation(relationVO));
    }

    @PostMapping("/deleteRelation")
    ResponseVO deleteRelation(@RequestBody RelationVO relationVO){
        System.out.println(relationVO.toString());
        int isSuccess= relationService.deleteRelation(relationVO);
        if(isSuccess==1){
            return ResponseVO.buildFailure("删除失败！");
        }
        else{
            return ResponseVO.buildSuccess("删除成功！");
        }
    }
}
