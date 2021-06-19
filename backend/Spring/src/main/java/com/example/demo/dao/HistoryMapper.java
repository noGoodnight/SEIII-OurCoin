package com.example.demo.dao;

import com.example.demo.po.HistoryEntry;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * @Author: BlackQAQ
 * @Date: 2021/6/15
 * @Description:
 */
@Mapper
@Repository
public interface HistoryMapper {
    ArrayList<String> getHistoriesByUid(@Param("uid") int uid);

    void addHistory(HistoryEntry historyEntry);

    HistoryEntry getHistoryByName(@Param("name") String name,@Param("uid") int uid);

    void updateTime(@Param("hid") int hid, @Param("df")String df);

    void deleteByName(@Param("name") String name, @Param("uid") int uid);

    void deleteByUid(@Param("uid") int uid);
}
