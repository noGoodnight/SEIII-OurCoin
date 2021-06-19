package com.example.demo.blImpl;

import com.example.demo.bl.GraphicService;
import com.example.demo.po.Entity;
import com.example.demo.po.Relation;
import com.example.demo.vo.GraphVO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class GraphicServiceImplTest {

    @Autowired
    GraphicService graphicService = null;

    GraphVO graphVO;
    ArrayList<Entity> entities=new ArrayList<>();
    ArrayList<Relation> relations=new ArrayList<>();

    @Test
    void getGraphsByUid() {
        Assert.assertNotNull(graphicService.getGraphsByUid(1));
    }

    @Test
    void getGraphByGid() {
        Assert.assertNotNull(graphicService.getGraphByGid(1));
    }

    @Test
    void saveGraph() {
        Entity entity1 = new Entity();
        entity1.setEid(30005);
        entity1.setGid(2);
        entity1.setName("jy");
        entity1.setX(600);
        entity1.setY(600);
        entity1.setColor("#3455EA");
        Entity entity2 = new Entity();
        entity2.setEid(30006);
        entity2.setGid(1);
        entity2.setName("dog");
        entity2.setX(700);
        entity2.setY(700);
        entity2.setColor("#3455EA");
        entities.add(entity1);
        entities.add(entity2);

        Relation relation1 = new Relation();
        relation1.setRid(40000);
        relation1.setName("f***");
        relation1.setGid(2);
        relation1.setTheFirstEid(30005);
        relation1.setTheSecondEid(30006);
        relations.add(relation1);

        graphVO = new GraphVO();
        graphVO.setGid(2);
        graphVO.setName("test_graph_2");
        graphVO.setUid(1);
        graphVO.setEntities(entities);
        graphVO.setRelations(relations);

        Assert.assertEquals(java.util.Optional.of(0), java.util.Optional.of(graphicService.saveGraph(graphVO)));
    }

    @Test
    void generateGraph() {
    }
}