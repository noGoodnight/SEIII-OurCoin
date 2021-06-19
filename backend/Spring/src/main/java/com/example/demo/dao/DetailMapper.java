package com.example.demo.dao;

import com.example.demo.po.Detail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: BlackQAQ
 * @Date: 2021/6/15
 * @Description:
 */
@Mapper
@Repository
public interface DetailMapper {

    Detail getDetailByEid(@Param("eid")int eid);

    void addDetail(Detail detail);

    void deleteDetail(@Param("eid") int eid);

}
