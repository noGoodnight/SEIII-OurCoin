package com.example.demo.dao;

import com.example.demo.po.Graph;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * @Author: BlackQAQ
 * @Date: 2021/3/15
 * @Description:
 */
@Mapper
@Repository
public interface GraphicMapper {

    ArrayList<Graph> getGraphsByUid(@Param("uid") Integer uid);

    Graph getGraphByGid(@Param("gid") Integer gid);

    void deleteGraphByGid(@Param("gid") Integer gid);

    void deleteGraphsByUid(@Param("uid") Integer uid);


    void addGraph(Graph graph);

    void updateGraph(Graph graph);

}
