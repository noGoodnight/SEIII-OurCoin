package com.example.demo.po;


import java.util.ArrayList;

public class User {
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGraphs(ArrayList<Integer> graphs) {
        this.graphs = graphs;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Integer> getGraphs() {
        return graphs;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    private String userName;

    private String password;

    private ArrayList<Integer> graphs=new ArrayList<>();

    private Integer uid;

}
