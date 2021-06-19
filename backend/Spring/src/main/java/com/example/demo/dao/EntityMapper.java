package com.example.demo.dao;

import com.example.demo.po.Entity;
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
public interface EntityMapper {

    ArrayList<Entity> getEntitiesByGid(@Param("gid") Integer gid);

    void deleteEntitiesByGid(@Param("gid") Integer gid);

    void deleteEntityByEid(@Param("eid") Integer eid);

    void addEntity(Entity entity);

    void updateEntity(Entity entity);

    Entity getEntityByEid(@Param("eid") Integer eid);

    ArrayList<String> getEntityNamesByKey(@Param("key") String key);

    ArrayList<Integer> getEidsByKey(@Param("key") String key);

    void insertEntity(Entity entity);

    //ArrayList<Entity> getEntitiesByKey(@Param("key") String key);

    ArrayList<Entity> getEntitiesByName(@Param("key") String key);

    ArrayList<Integer> getGraphEidByName(@Param("name") String name, @Param("gid") int gid);

    ArrayList<Integer> getEidsByGid(@Param("gid") int gid);

    ArrayList<String> getAllEntityNames();
}
