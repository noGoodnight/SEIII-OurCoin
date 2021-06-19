package com.example.demo.blImpl;

import com.example.demo.bl.EntityService;
import com.example.demo.dao.DetailMapper;
import com.example.demo.dao.EntityMapper;
import com.example.demo.dao.RelationMapper;
import com.example.demo.po.Detail;
import com.example.demo.po.Entity;
import com.example.demo.vo.EntityVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @Author:BlackQAQ
 * @Date:2021/3/15
 * @Description:Implementations of EntityService
 */
@Service
public class EntityServiceImpl implements EntityService {

    @Autowired
    EntityMapper entityMapper;

    @Autowired
    RelationMapper relationMapper;

    @Autowired
    DetailMapper detailMapper;


    //搜索节点列表
    @Override
    public ArrayList<EntityVO> searchEntities(String name){
        ArrayList<Entity> entities=entityMapper.getEntitiesByName(name);
        if(entities.isEmpty()) return new ArrayList<>();
        ArrayList<EntityVO> entityVOS=new ArrayList<>();
        for(int i=0;i<entities.size();i++) {
            EntityVO entityVO = new EntityVO();
            entityVO.setCategory(entities.get(i).getCategory());
            entityVO.setColor(entities.get(i).getColor());
            entityVO.setName(entities.get(i).getName());
            entityVO.setX(entities.get(i).getX());
            entityVO.setY(entities.get(i).getY());
            entityVOS.add(entityVO);
        }
        return entityVOS;
    }

    //新建节点
    @Override
    public Integer addEntity(EntityVO entityVO){
        Entity entity=new Entity();
        entity.setGid(entityVO.getGid());
        entity.setName(entityVO.getName());
        entity.setCategory(entityVO.getCategory());
        entity.setColor(entityVO.getColor());
//        entity.setLabelFontSize(entityVO.getLabelFontSize());
//        entity.setSymbolSize(entityVO.getSymbolSize());
//        entity.setSymbol(entityVO.getSymbol());
        entity.setX(entityVO.getX());
        entity.setY(entityVO.getY());
//        entity.setCx(entityVO.getCx());
//        entity.setCy(entityVO.getCy());
        entityMapper.addEntity(entity);
//        System.out.println(entity.getEid());
        if(entity.getCategory().equals("animation")) {
            ArrayList<Integer> eids = entityMapper.getEidsByKey(entityVO.getName());
            if (eids.size() == 0) return -1;
            int eid=0;
            int isAnime=0;
            for(int i=0;i<eids.size();i++){
                Entity temp=entityMapper.getEntityByEid(eids.get(i));
                if(temp.getCategory().equals("animation")){
                    eid=eids.get(i);
                    isAnime=1;
                    break;
                }
            }
            if(isAnime==1) {
                Detail detail = new Detail();
                Detail oldDetail=detailMapper.getDetailByEid(eid);
                if(oldDetail==null) return -3;
                detail.setDid(entity.getEid());
                detail.setDescription(oldDetail.getDescription());
                detail.setJpg(oldDetail.getJpg());
                detail.setTags(oldDetail.getTags());
                detail.setUrl(oldDetail.getUrl());
                detailMapper.addDetail(detail);
            }
            else return -2;
        }
        return entity.getEid();
    }

    //删除节点
    @Override
    public Integer deleteEntity(EntityVO entityVO){
        try{
            entityMapper.deleteEntityByEid(entityVO.getEid());
            relationMapper.deleteRelationsByEid(entityVO.getEid());
        } catch(Exception e){
            System.out.println(e);
            return 1;
        }
        return 0;
    }

    //获取节点详细内容
    @Override
    public EntityVO getEntityByEid(Integer eid){
        Entity entity=entityMapper.getEntityByEid(eid);
        EntityVO entityVO=new EntityVO();
        entityVO.setEid(entity.getEid());
        entityVO.setGid(entity.getGid());
        entityVO.setName(entity.getName());
        entityVO.setCategory(entity.getCategory());
        entityVO.setColor(entity.getColor());
//        entityVO.setLabelFontSize(entity.getLabelFontSize());
//        entityVO.setSymbolSize(entity.getSymbolSize());
//        entityVO.setSymbol(entity.getSymbol());
        entityVO.setX(entity.getX());
        entityVO.setY(entity.getY());
//        entityVO.setCx(entity.getCx());
//        entityVO.setCy(entity.getCy());
//        System.out.println(entityVO);
        return entityVO;
    }


    //获取节点详情
    @Override
    public Detail getDetailByEid(int eid){
        return detailMapper.getDetailByEid(eid);
    }

    //获取所有节点全名（用于自动补全）
    @Override
    public ArrayList<String> getAllEntityNames(){
        return entityMapper.getAllEntityNames();
    }
}
