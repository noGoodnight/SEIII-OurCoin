package com.example.demo.dao;

import com.example.demo.po.Graph;
import com.example.demo.po.Relation;
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
public interface RelationMapper {

    ArrayList<Relation> getRelationsByGid(@Param("gid") Integer gid);

    Relation getRelationByRid(@Param("rid") Integer rid);

    void deleteRelationByRid(@Param("rid") Integer rid);

    void deleteRelationsByEid(@Param("eid") Integer eid);

    void deleteRelationsByGid(@Param("gid") Integer gid);

    void addRelation(Relation relation);

    void updateRelation(Relation relation);

    void insertRelation(Relation relation);

    ArrayList<Relation> getRelationsByEid(@Param("eid") Integer eid);

    ArrayList<Integer> getRidsByEid(@Param("eid") Integer eid);

    ArrayList<Integer> getSecondEidsByFirstEid(@Param("eid") Integer eid);

    ArrayList<Integer> getFirstEidsBySecondEid(@Param("eid") Integer eid);

}
