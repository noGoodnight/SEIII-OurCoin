package com.example.demo.po;

/**
 * @Author:BlackQAQ
 * @Date:2021/6/14
 * @Description:The Persistence Object for HistoryEntry
 */
public class HistoryEntry {
    int hid;

    String history;

    int uid;

    public int getHid() {
        return hid;
    }

    public void setHid(int hid) {
        this.hid = hid;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
