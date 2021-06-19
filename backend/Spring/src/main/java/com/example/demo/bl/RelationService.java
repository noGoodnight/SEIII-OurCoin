package com.example.demo.bl;

import com.example.demo.vo.RelationVO;

/**
 * @Author:BlackQAQ
 * @Date:2021/3/14
 * @Description:Interfaces of RelationService
 */

public interface RelationService {
    Integer addRelation(RelationVO relationVO);

    Integer deleteRelation(RelationVO relationVO);
}
