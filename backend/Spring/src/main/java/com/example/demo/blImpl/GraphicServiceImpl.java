package com.example.demo.blImpl;

import com.example.demo.bl.GraphicService;
import com.example.demo.dao.DetailMapper;
import com.example.demo.dao.EntityMapper;
import com.example.demo.dao.GraphicMapper;
import com.example.demo.dao.RelationMapper;
import com.example.demo.vo.EntityVO;
import com.example.demo.vo.GraphVO;
import com.example.demo.vo.RelationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.po.Graph;
import com.example.demo.po.Entity;
import com.example.demo.po.Relation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:BlackQAQ
 * @Date:2021/3/14
 * @Description:Implementations of GraphicService
 */

@Service
public class GraphicServiceImpl implements GraphicService {

    @Autowired
    EntityMapper entityMapper;

    @Autowired
    GraphicMapper graphicMapper;

    @Autowired
    RelationMapper relationMapper;

    @Autowired
    DetailMapper detailMapper;


    //保存修改后的图
    @Override
    public Integer saveGraph(GraphVO graphVO) {
        ArrayList<Entity> entities=graphVO.getEntities();
        ArrayList<Relation> relations=graphVO.getRelations();
        Graph graph=new Graph();
        try {
//            graph.setDirectory(graphVO.getDirectory());
            graph.setGid(graphVO.getGid());
            graph.setName(graphVO.getName());
            graph.setUid(graphVO.getUid());
        }catch (Exception e){
            System.out.println(e);
            return 1;
        }
        try{
            graphicMapper.updateGraph(graph);
            for(int i=0;i<entities.size();i++){
                entityMapper.updateEntity(entities.get(i));
            }
            for(int i=0;i<relations.size();i++){
                relationMapper.updateRelation(relations.get(i));
            }
        } catch (Exception e){
            System.out.println(e);
            return 2;
        }
        return 0;
    }

    //获取用户的图列表
    @Override
    public ArrayList<GraphVO> getGraphsByUid(Integer uid){
        ArrayList<Graph> graphs= graphicMapper.getGraphsByUid(uid);
        ArrayList<GraphVO> graphVOS=new ArrayList<>();
        for(int i=0;i<graphs.size();i++){
            GraphVO graphVO=new GraphVO();
//            graphVO.setDirectory(graphs.get(i).getDirectory());
            graphVO.setGid(graphs.get(i).getGid());
            graphVO.setName(graphs.get(i).getName());
            graphVO.setUid(graphs.get(i).getUid());
            graphVOS.add(graphVO);
        }
        return graphVOS;
    }

    //获取图详情
    @Override
    public GraphVO getGraphByGid(Integer gid){
        GraphVO graphVO=new GraphVO();
        Graph graph= graphicMapper.getGraphByGid(gid);
//        graphVO.setDirectory(graph.getDirectory());
        graphVO.setGid(graph.getGid());
        graphVO.setName(graph.getName());
        graphVO.setUid(graph.getUid());
        graphVO.setEntities(entityMapper.getEntitiesByGid(gid));
        graphVO.setRelations(relationMapper.getRelationsByGid(gid));
        return graphVO;
    }

    //生成并保存图
    @Override
    public Integer generateGraph(GraphVO graphVO){
        Graph graph=new Graph();
        try {
            graph.setUid(graphVO.getUid());
            graph.setName(graphVO.getName());
            graph.setEntities(graphVO.getEntities());
            graph.setRelations(graphVO.getRelations());
            graphicMapper.addGraph(graph);
        }catch (Exception e){
            System.out.println(e);
            return -1;
        }
        ArrayList<Integer> rawEids=new ArrayList<>();
        for(int i=0;i<graph.getEntities().size();i++){
            rawEids.add(graph.getEntities().get(i).getEid());
        }
        System.out.println(graph.getGid());
        try{
            for(int i=0;i<graph.getEntities().size();i++){
                graph.getEntities().get(i).setGid(graph.getGid());
                graph.getEntities().get(i).setEid(null);
                entityMapper.addEntity(graph.getEntities().get(i));
            }
            HashMap<Integer, Integer> entityHash=new HashMap<Integer, Integer>();
            for(int i=0;i<graph.getEntities().size();i++) {
                entityHash.put(rawEids.get(i),graph.getEntities().get(i).getEid());
            }
            for(int i=0;i<graph.getRelations().size();i++){
                graph.getRelations().get(i).setGid(graph.getGid());
                graph.getRelations().get(i).setRid(null);
                graph.getRelations().get(i).setTheFirstEid(entityHash.get(graph.getRelations().get(i).getTheFirstEid()));
                graph.getRelations().get(i).setTheSecondEid(entityHash.get(graph.getRelations().get(i).getTheSecondEid()));
                relationMapper.addRelation(graph.getRelations().get(i));
            }
        }catch (Exception e){
            System.out.println(e);
            return -2;
        }
        return graph.getGid();
    }

    //删除图
    @Override
    public String deleteGraphByGid(int gid){
        try{
            graphicMapper.deleteGraphByGid(gid);
            relationMapper.deleteRelationsByGid(gid);
            entityMapper.deleteEntitiesByGid(gid);
            ArrayList<Integer> eids= entityMapper.getEidsByGid(gid);
            for(int i=0;i<eids.size();i++){
                detailMapper.deleteDetail(eids.get(i));
            }
        }catch (Exception e){
            return "Delete Fail!";
        }
        return "Delete Success!";
    }
}
