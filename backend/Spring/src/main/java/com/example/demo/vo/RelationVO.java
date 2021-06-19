package com.example.demo.vo;

import com.example.demo.po.Entity;

/**
 * @Author:BlackQAQ
 * @Date:2021/3/14
 * @Description:The Value Object for relation
 */
public class RelationVO {

//    public String getSource() {
//        return source;
//    }
//
//    public void setSource(String source) {
//        this.source = source;
//    }
//
//    public String getTarget() {
//        return target;
//    }
//
//    public void setTarget(String target) {
//        this.target = target;
//    }

    public void setTheFirstEid(Integer theFirstEid) {
        this.theFirstEid = theFirstEid;
    }

    public void setTheSecondEid(Integer theSecondEid) {
        this.theSecondEid = theSecondEid;
    }

    public Integer getTheFirstEid() {
        return theFirstEid;
    }

    public Integer getTheSecondEid() {
        return theSecondEid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRid() {
        return rid;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getName() {
        return name;
    }

    /*public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }*/

    //private String color;

    //private String type;

    private Integer rid;

    private Integer gid;

    private String name;

//    private String source;
//
//    private String target;

    private Integer theFirstEid;

    private Integer theSecondEid;

    @Override
    public String toString() {
        return "RelationVO{" +
                "rid=" + rid +
                ", gid=" + gid +
                ", name='" + name + '\'' +
                ", theFirstEid=" + theFirstEid +
                ", theSecondEid=" + theSecondEid +
                '}';
    }
}
