package com.example.demo.po;

/**
 * @Author:BlackQAQ
 * @Date:2021/3/14
 * @Description:The Persistence Object for relation
 */
public class Relation {
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

    /*public void setTheFirstEntity(Entity theFirstEntity) {
        this.theFirstEntity = theFirstEntity;
    }

    public void setTheSecondEntity(Entity theSecondEntity) {
        this.theSecondEntity = theSecondEntity;
    }*/
    public Integer getRid() {
        return rid;
    }

    public String getName() {
        return name;
    }

    /*public Entity getTheFirstEntity() {
        return theFirstEntity;
    }

    public Entity getTheSecondEntity() {
        return theSecondEntity;
    }*/

    private Integer rid;

    private String name;

    //private Entity theFirstEntity;

    //private Entity theSecondEntity;

    private Integer theFirstEid;

    private Integer theSecondEid;

    private Integer gid;

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

//    public String getColor() {
//        return color;
//    }
//
//    public void setColor(String color) {
//        this.color = color;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    private String color;
//
//    private String type;

    @Override
    public String toString() {
        return "Relation{" +
                "rid=" + rid +
                ", name='" + name + '\'' +
                //", theFirstEntity=" + theFirstEntity +
                //", theSecondEntity=" + theSecondEntity +
                ", theFirstEid=" + theFirstEid +
                ", theSecondEid=" + theSecondEid +
                ", gid=" + gid +
                '}';
    }
}
