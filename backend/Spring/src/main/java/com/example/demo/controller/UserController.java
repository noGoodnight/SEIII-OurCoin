package com.example.demo.controller;

import com.example.demo.bl.UserService;
import com.example.demo.vo.GraphVO;
import com.example.demo.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * @Author:BlackQAQ
 * @Date:2021/3/15
 * @Description:The controller of relationservice
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/search/ack1/{uid}/{eid}")
    ResponseVO getSearchResultByEid(@PathVariable("eid") int eid, @PathVariable("uid") int uid){
        GraphVO graphVO=userService.searchByEid(eid,uid);
        if(graphVO.getEntities().size()>0)
            return ResponseVO.buildSuccess(graphVO);
        else
            return ResponseVO.buildFailure("No Data!");

    }

    @GetMapping("/search/ack/{uid}/{contents}")
    ResponseVO getSearchResult(@PathVariable("contents") ArrayList<String> contents, @PathVariable("uid") int uid){
        GraphVO graphVO=userService.search(contents,uid);
        if(graphVO.getEntities().size()>0)
            return ResponseVO.buildSuccess(graphVO);
        else
            return ResponseVO.buildFailure("No Data!");

    }

    @GetMapping("/search/key/{content}")
    ResponseVO getSearchKeywords(@PathVariable String content){
        ArrayList<String> results= userService.supplement(content);
        if(results.size()>0)
            return ResponseVO.buildSuccess(results);
        else
            return ResponseVO.buildFailure("Unknown keyword!");
    }

    @GetMapping("/answer/ack/{content}")
    ResponseVO getAnswerResult(@PathVariable String content){
        return ResponseVO.buildSuccess(userService.answer(content));
    }

    @GetMapping("/recommend/ack/{uid}")
    ResponseVO getRecommendation(@PathVariable int uid){
        ArrayList<String> results=userService.recommend(uid);
        System.out.println(results);
        return ResponseVO.buildSuccess(results);
    }

    @GetMapping("/history/ack/{uid}")
    ResponseVO getHistory(@PathVariable int uid){
        ArrayList<String> results=userService.getHistory(uid);
        if(results.isEmpty()) {
            return ResponseVO.buildSuccess("No history!");
        }
        else{
            System.out.println(results);
            return ResponseVO.buildSuccess(results);
        }
    }

    @PostMapping("/history/clear/{uid}")
    ResponseVO cleatHistories(@PathVariable int uid){
        int result= userService.clearHistories(uid);
        if(result==0){
            return ResponseVO.buildSuccess("Delete Success!");
        }
        else{
            return  ResponseVO.buildFailure("Delete Error!");
        }
    }

    @PostMapping("/history/delete/{uid}/{key}")
    ResponseVO deleteHistory(@PathVariable("uid") int uid, @PathVariable("key") String key){
        int result= userService.deleteHistory(uid,key);
        if(result==0){
            return ResponseVO.buildSuccess("Delete Success!");
        }
        else{
            return  ResponseVO.buildFailure("Delete Error!");
        }
    }
}
