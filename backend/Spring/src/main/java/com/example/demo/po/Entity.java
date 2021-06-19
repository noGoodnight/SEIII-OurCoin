package com.example.demo.po;

/**
 * @Author:BlackQAQ
 * @Date:2021/3/14
 * @Description:The Persistence Object for entity
 */
public class Entity {

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public Integer getGid() {
        return gid;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    /*public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }*/

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    /*public Integer getSymbolSize() {
        return symbolSize;
    }

    public void setSymbolSize(Integer symbolSize) {
        this.symbolSize = symbolSize;
    }

    public Integer getLabelFontSize() {
        return labelFontSize;
    }

    public void setLabelFontSize(Integer labelFontSize) {
        this.labelFontSize = labelFontSize;
    }*/

    private Integer eid;

    private String name;

    private Integer gid;

    private double x;

    private double y;

    private String category;

    //private String symbol;

    private String color;

    //private Integer symbolSize;

    //private Integer labelFontSize;

//    public double getCx() {
//        return cx;
//    }
//
//    public void setCx(double cx) {
//        this.cx = cx;
//    }
//
//    public double getCy() {
//        return cy;
//    }
//
//    public void setCy(double cy) {
//        this.cy = cy;
//    }
//
//    private double cx;
//
//    private double cy;

    @Override
    public String toString() {
        return "Entity{" +
                "eid=" + eid +
                ", name='" + name + '\'' +
                ", gid=" + gid +
                ", x=" + x +
                ", y=" + y +
                ", category='" + category + '\'' +
                //", symbol='" + symbol + '\'' +
                ", color='" + color + '\'' +
                //", symbolSize=" + symbolSize +
                //", labelFontSize=" + labelFontSize +
                '}';
    }
}
