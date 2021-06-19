package com.example.demo.blImpl;

import com.example.demo.bl.RelationService;
import com.example.demo.dao.EntityMapper;
import com.example.demo.dao.RelationMapper;
import com.example.demo.po.Entity;
import com.example.demo.po.Relation;
import com.example.demo.vo.EntityVO;
import com.example.demo.vo.RelationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:BlackQAQ
 * @Date:2021/3/15
 * @Description:Implementations of RelationService
 */
@Service
public class RelationServiceImpl implements RelationService {

    @Autowired
    RelationMapper relationMapper;

    //新建关系
    @Override
    public Integer addRelation(RelationVO relationVO){
        Relation relation=new Relation();
        relation.setGid(relationVO.getGid());
        relation.setName(relationVO.getName());
        relation.setTheFirstEid(relationVO.getTheFirstEid());
        relation.setTheSecondEid(relationVO.getTheSecondEid());
//        relation.setType(relationVO.getType());
//        relation.setColor(relationVO.getColor());
        relationMapper.addRelation(relation);
        System.out.println(relation.getRid());
        return relation.getRid();
    }

    //删除关系
    @Override
    public Integer deleteRelation(RelationVO relationVO){
        try{
            relationMapper.deleteRelationByRid(relationVO.getRid());
        } catch(Exception e){
            System.out.println(e);
            return 1;
        }
        return 0;
    }
}
