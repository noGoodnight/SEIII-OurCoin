package com.example.demo.po;
import java.util.List;
import java.util.ArrayList;
/**
 * @Author:BlackQAQ
 * @Date:2021/3/14
 * @Description:The Persistence Object for graph
 */

public class Graph {
    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGid() {
        return gid;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public ArrayList<Relation> getRelations() {
        return relations;
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }

    public void setRelations(ArrayList<Relation> relations) {
        this.relations = relations;
    }

    /*public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }*/

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    private Integer gid;

    private String name;

    private ArrayList<Entity> entities=new ArrayList<>();

    private ArrayList<Relation> relations=new ArrayList<>();

    //private String directory;

    private Integer uid;
}
