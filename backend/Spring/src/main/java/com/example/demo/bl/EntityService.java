package com.example.demo.bl;

import com.example.demo.po.Detail;
import com.example.demo.vo.EntityVO;

import java.util.ArrayList;

/**
 * @Author:BlackQAQ
 * @Date:2021/3/14
 * @Description:Interfaces of EntityService
 */

public interface EntityService {
    Integer addEntity(EntityVO entityVO);

    Integer deleteEntity(EntityVO entityVO);

    EntityVO getEntityByEid(Integer eid);

    ArrayList<EntityVO> searchEntities(String name);

    Detail getDetailByEid(int eid);

    ArrayList<String> getAllEntityNames();
}
